

package project.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.model.Profile;
import project.model.User;
import project.services.UserService;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
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

    @RequestMapping(value = "/email/{emailToSearch}", method = RequestMethod.GET)
    public ResponseEntity<List<User>> searchUsersByEmail(@PathVariable String emailToSeach) {

        List<User> foundUsers = userService.searchUsersByPartsOfEmail(emailToSeach);

        for(User other : foundUsers) {
            Link selfRef = linkTo(RestUserController.class).slash(other.getUserID()).withSelfRel();
            other.add(selfRef);
        }

        return new ResponseEntity<>(foundUsers, HttpStatus.OK);
    }

    @RequestMapping(value = "/profiles" , method = RequestMethod.POST)
    public ResponseEntity<?> changeUserProfile (@RequestBody User updatedProfile) {
        ResponseEntity<?> result = new ResponseEntity<>(HttpStatus.FORBIDDEN);

        Profile profileChange = updatedProfile.getUserProfile();

        User userToChange = userService.getUserByEmail(updatedProfile.getEmail());
        if(userToChange != null){
            userToChange.setUserProfile(profileChange);
            result = new ResponseEntity<>("User "+userToChange.getName()+"\nProfile changed sucessfully",HttpStatus.OK);
        }
        return result;
    }
}
