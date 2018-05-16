package project.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.dto.CredentialsDTO;
import project.dto.UserDTO;
import project.model.CodeGenerator;
import project.model.JsonAsString;
import project.model.User;
import project.model.sendcode.SendCodeFactory;
import project.model.sendcode.ValidationMethod;
import project.security.JWTUtil;
import project.security.UserSecurity;
import project.services.UserService;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/account/")
public class RestAccountController {

    private final JWTUtil jwtUtil;

    private final UserService userService;

    private Map<User,String> pendingValidation = new HashMap<>();

    /*
    This map stores temporarily the userEmail as Key and the generatedCode that will be sent to the provided phone/email
    When the user verificates the code correctly, the pair Key/Value is deleted from the HashMap
     */
    private Map<String, String> codeDTOMap = new HashMap<>();


    /*
    This map stores temporarily the userEmail as Key and the userDTO that will be saved when the user puts the verification code correctly.
    When the user verificates the code, the pair Key/Value is deleted from the HashMap
     */
    private Map<String, UserDTO> userDTOPMap = new HashMap<>();

    Logger logs = Logger.getAnonymousLogger();


    @Autowired
    public RestAccountController(UserService userService, JWTUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * This method returns a response entity with the conditions
     *
     * @return the conditions
     */
    @RequestMapping(value = "conditions", method = RequestMethod.GET)
    public ResponseEntity<JsonAsString> termsAndConditions() {

        String conditions = "TERMS AND CONDITIONS:"
                + "By using this application, you agree to be bound by, and to comply with these Terms and Conditions."
                + "If you do not agree to these Terms and Conditions, please do not use this application."
                + "To proceed with registration you must accept access conditions.";

        JsonAsString jsonConditions = new JsonAsString(conditions);

        Link register = linkTo(RestAccountController.class).slash("register")
                .withRel("Register user");
        Link termsAndConditions = linkTo(methodOn(RestAccountController.class).termsAndConditions()).withSelfRel();

        jsonConditions.add(register);
        jsonConditions.add(termsAndConditions);

        return new ResponseEntity<>(jsonConditions, HttpStatus.OK);

    }

    /**
     * This method receives the information of the user to be created in a dto, verifies if there is a user in the
     * database with the same email, also verifies if the email is valid. and only if these two verifications are
     * not confirmed will a user be created with the information of the dto.
     *
     * @param userDTO
     * @return response entity
     */
    @PreAuthorize("permitAll()")
    @RequestMapping(value = "register" , method = RequestMethod.POST)
    public ResponseEntity<List<Link>> createUser(@RequestBody UserDTO userDTO) {

        if(userService.isUserEmailInUserContainer(userDTO.getEmail())){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        if(!(userService.isEmailAddressValid(userDTO.getEmail()))){
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

          /*
            Stores the userEmail and the code will be generated in the next method
         */

        codeDTOMap.put(userDTO.getEmail(), "NotVerified");

         /*
            Stores the userEmail and userDTO in the HashMap
         */
        userDTOPMap.put(userDTO.getEmail(), userDTO);

        /*
            Link to send verification code by SMS
         */

        Link sendEmail = linkTo(RestAccountController.class).slash("performValidation/1/"
                + userDTO.getEmail() + "/" + userDTO.getPhone())
                .withRel("smsValidation");

         /*
            Link to send verification code by Email
         */


        Link sendSMS = linkTo(RestAccountController.class).slash("performValidation/2/"  + userDTO.getEmail() + "/" + userDTO.getPhone())
                .withRel("emailValidation");

        List<Link> linkList = new ArrayList<>();
        linkList.add(sendEmail);
        linkList.add(sendSMS);

        /*
            Returns a ResponseEntity with a string of links
         */

        return new ResponseEntity<>(linkList, HttpStatus.OK);

    }

    @RequestMapping(value="logIn", method= RequestMethod.POST)
    public ResponseEntity<User> doLogin(@RequestBody CredentialsDTO logInDTO) {

        ResponseEntity<User> response = new ResponseEntity<>(HttpStatus.FORBIDDEN);

        User toLogIn = userService.getUserByEmail(logInDTO.getEmail());

        if (toLogIn == null) {
            return response;

        } else if (!toLogIn.hasPassword()) {

            Link validatePhone = linkTo(RestAccountController.class).slash(toLogIn.getUserID()+"/validate/1").withRel("phoneValidation");
            Link validateEmail = linkTo(RestAccountController.class).slash(toLogIn.getUserID()+"/validate/2").withRel("emailValidation");
            Link validateQuestion = linkTo(RestAccountController.class).slash(toLogIn.getUserID()+"/validate/3").withRel("questionValidation");

            toLogIn.add(validatePhone);
            toLogIn.add(validateEmail);
            toLogIn.add(validateQuestion);

            response = new ResponseEntity<>(toLogIn, HttpStatus.OK);
            return response;


        } else if (!toLogIn.checkLogin(logInDTO.getPassword())) {
            return response;
        }


        Link userDetails = linkTo(RestUserController.class).slash("users").slash(toLogIn.getUserID()).withSelfRel();
        toLogIn.add(userDetails);

        response = new ResponseEntity<>(toLogIn, HttpStatus.OK);

        return response;

    }

    @RequestMapping(value="{userId}/validate/{validationMethod}", method = RequestMethod.GET)
    public ResponseEntity<Link> performValidation(@PathVariable Integer userId, @PathVariable String validationMethod) {

        SendCodeFactory codeFactory = new SendCodeFactory();

        CodeGenerator codeGenerator = new CodeGenerator();

        User toValidate = userService.getUserByID(userId);

        ValidationMethod validation = codeFactory.getCodeSenderType(validationMethod).orElse(null);

        if(toValidate==null || validation == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        try {
            String code = codeGenerator.generateCode();

            pendingValidation.put(toValidate, code);

            validation.performValidationMethod(toValidate.getPhone(), toValidate.getEmail(), toValidate.getQuestion(), code);

            String output = generateValidationQuestion(validationMethod, toValidate.getQuestion());

            Link attemptValidation = linkTo(RestAccountController.class).slash(toValidate.getUserID()+"/validate/inputCode").withRel(output);

            return new ResponseEntity<>(attemptValidation, HttpStatus.OK);

        } catch (MessagingException| IOException e) {
            logs.info("Error sending message!");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value="{userId}/validate/inputCode", method = RequestMethod.POST)
    public ResponseEntity<Link> checkValidation(@RequestBody String code, @PathVariable Integer userId) {

        User toValidate = userService.getUserByID(userId);

        if(toValidate==null || !pendingValidation.containsKey(toValidate)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Link output;

        if(pendingValidation.get(toValidate).equals(code) || toValidate.getAnswer().equals(code)) {
            output = linkTo(RestUserController.class).slash("users").slash(userId).slash("details").withRel("createPassword");
            pendingValidation.remove(toValidate);
            return new ResponseEntity<>(output, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    private static String generateValidationQuestion(String method, String question) {
        switch(method) {
            case"1":
                return "Check your phone and input the validation code:";
            case"2":
                return "Check your email and input the validation code:";
            case"3":
                return question;
            default:
                return "Something went wrong, try again!";
        }
    }

    /**
     *
     * @param validation
     * The validation type
     * @param userEmail
     * @param userPhone
     * @return
     */
    @RequestMapping(value = "performValidation/{validation}/{userEmail}/{userPhone}", method = RequestMethod.GET)
    public ResponseEntity<Link> performUserValidation(@PathVariable String validation,
                                                      @PathVariable String userEmail, @PathVariable String userPhone) {

        SendCodeFactory codeFactoryValidation = new SendCodeFactory();

        CodeGenerator codeGenerator = new CodeGenerator();

        ValidationMethod validationType = codeFactoryValidation.getCodeSenderType(validation).orElse(null);

        if(validationType == null || validation.contains("3")) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        else{

            try {
                String codeValidation = codeGenerator.generateCode();

                codeDTOMap.replace(userEmail, codeValidation);

                validationType.performValidationMethod(userPhone, userEmail, "validateHuman", codeValidation);

                Link attemptValidation = linkTo(RestAccountController.class).slash("performValidation/verificateCode/" + userEmail).withRel("verificateHuman");

                return new ResponseEntity<>(attemptValidation, HttpStatus.OK);

            } catch (MessagingException| IOException e) {
                logs.info("Error sending message!");
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }
    }

    @RequestMapping(value = "performValidation/verificateCode/{userEmail}", method = RequestMethod.POST)
    public ResponseEntity<Link> verificateCode(@RequestBody String codeToCheck, @PathVariable String userEmail){

        Boolean doesCodeMatch = false;
        if(codeDTOMap.containsKey(userEmail)){
            doesCodeMatch = codeDTOMap.get(userEmail).equals(codeToCheck);
        }

        Link login = linkTo(RestAccountController.class).slash("logIn").withRel("loginUser");

        if(doesCodeMatch) {
            userService.createUserWithDTO(userDTOPMap.get(userEmail));
            codeDTOMap.remove(userEmail);
            userDTOPMap.remove(userEmail);

            return new ResponseEntity<>(login, HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
        UserSecurity userSec = UserService.authenticated();
        String token = jwtUtil.generateToken(userSec.getUsername());
        response.addHeader("Authorization", "Bearer " + token);
        return ResponseEntity.noContent().build();
    }
}
