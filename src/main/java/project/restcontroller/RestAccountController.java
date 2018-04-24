package project.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.model.User;
import project.services.UserService;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;


@RestController
@RequestMapping("account/")
public class RestAccountController {

    private final UserService userService;

    @Autowired
    public RestAccountController(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping(value="login", method= RequestMethod.GET)
    public ResponseEntity<User> doLogin(@RequestBody User logInDTO) {

        ResponseEntity<User> response = new ResponseEntity<>(HttpStatus.FORBIDDEN);

        User toLogIn = userService.getUserByEmail(logInDTO.getEmail());

        if(toLogIn == null || !toLogIn.hasPassword()) {
            return response;
        } else if(!toLogIn.hasPassword()) {
            //TODO Implement user validation
            response = new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
            return response;
        } else if(!toLogIn.checkLogin(logInDTO)) {
            return response;
        }

        Link userDetails = linkTo(RestUserController.class).slash("users").slash(toLogIn.getUserID()).withSelfRel();
        toLogIn.add(userDetails);

        response = new ResponseEntity<>(toLogIn, HttpStatus.OK);

        return response;
    }
}
