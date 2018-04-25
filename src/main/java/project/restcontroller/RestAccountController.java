package project.restcontroller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import project.dto.UserDTO;
import project.model.User;
import project.services.UserService;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/account/")
public class RestAccountController {

    private final UserService userService;


    @Autowired
    public RestAccountController(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping(value="logIn", method= RequestMethod.POST)
    public ResponseEntity<User> doLogin(@RequestBody UserDTO logInDTO) {

        ResponseEntity<User> response = new ResponseEntity<>(HttpStatus.FORBIDDEN);

        User toLogIn = userService.getUserByEmail(logInDTO.getEmail());

        if (toLogIn == null) {
            return response;
        } else if (!toLogIn.hasPassword()) {
            //TODO Implement user validation
            response = new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
            return response;
        } else if (!toLogIn.checkLogin(logInDTO.getPassword())) {
            return response;
        }

        Link userDetails = linkTo(RestUserController.class).slash("users").slash(toLogIn.getUserID()).withSelfRel();
        toLogIn.add(userDetails);

        response = new ResponseEntity<>(toLogIn, HttpStatus.OK);

        return response;

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

}
