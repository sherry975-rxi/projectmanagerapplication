package project.restcontroller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.dto.UserDTO;
import project.model.CodeGenerator;
import project.model.User;
import project.model.sendcode.SendCodeFactory;
import project.model.sendcode.ValidationMethod;
import project.services.UserService;

import javax.mail.MessagingException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/account/")
public class RestAccountController {

    private final UserService userService;

    Map<User,String> pendingValidation = new HashMap<>();

    @Autowired
    public RestAccountController(UserService userService) {
        this.userService = userService;
    }



    /**
     * This method returns a response entity with the conditions
     *
     * @return the conditions
     */
    @RequestMapping(value = "conditions", method = RequestMethod.GET)
    public ResponseEntity<String> termsAndConditions() {

        String conditions = "TERMS AND CONDITIONS: \r\n" + "\r\n"
                + "By using this application, you agree to be bound by, and to comply with these Terms and Conditions.\r\n"
                + "If you do not agree to these Terms and Conditions, please do not use this application.\r\n"
                + "To proceed with registration you must accept access conditions (y to confirm; n to deny).";

        return new ResponseEntity<>(conditions, HttpStatus.OK);

    }




    /**
     * This method receives the information of the user to be created in a dto, verifies if there is a user in the
     * database with the same email, also verifies if the email is valid. and only if these two verifications are
     * not confirmed will a user be created with the information of the dto.
     *
     * @param userDTO
     * @return response entity
     */
    @RequestMapping(value = "" , method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) {

        if(userService.isUserEmailInUserContainer(userDTO.getEmail())){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        if(!(userService.isEmailAddressValid(userDTO.getEmail()))){
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        userService.createUserWithDTO(userDTO);

        User createdUser = userService.getUserByEmail(userDTO.getEmail());

        Link reference = linkTo(RestUserController.class).slash(createdUser.getUserID())
                .withRel("User details");
        createdUser.add(reference);

        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);

    }



    @RequestMapping(value="logIn", method= RequestMethod.POST)
    public ResponseEntity<User> doLogin(@RequestBody UserDTO logInDTO) {

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
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    @RequestMapping(value="{userId}/validate/inputCode", method = RequestMethod.POST)
    public ResponseEntity<Link> checkValidation(@RequestBody String code, @PathVariable Integer userId) {

        User toValidate = userService.getUserByID(userId);

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


 }
