

package project.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import project.model.User;
import project.services.UserService;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("users")
public class RestUserController {

    private final UserService userService;

    @Autowired
    public RestUserController(UserService userService) {
        this.userService=userService;
    }


    /**
     * This method allows the administrator to search users by a part of the email.
     *
     * @param emailToSearch
     * @return ResponseEntity
     */
    @RequestMapping(value = "/email/{emailToSearch}", method = RequestMethod.GET)
    public ResponseEntity<List<User>> searchUsersByEmail(@PathVariable String emailToSearch) {

        List<User> foundUsers = userService.searchUsersByPartsOfEmail(emailToSearch);

        for(User other : foundUsers) {
            Link selfRef = linkTo(RestUserController.class).slash(other.getUserID()).withSelfRel();
            other.add(selfRef);
        }

        return new ResponseEntity<>(foundUsers, HttpStatus.OK);
    }


}
