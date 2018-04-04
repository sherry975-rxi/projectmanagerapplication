package project.restControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import project.model.User;
import project.services.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * This controller allows the administrator to search system user by profile
 */
@RestController
@RequestMapping("/users/{profileNameToSearch}")
public class US136FindUserByProfile {

    private final UserService userService;

    private List<User> userList;

    @Autowired
    US136FindUserByProfile(UserService userService){

        this.userService = userService;

    }

    /**
     * This method allows the administrator to search users by profile.
     *
     * @param profileNameToSearch
     * @return ResponseEntity
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> searchUsersByProfileController(@PathVariable String profileNameToSearch) {

        this.userList = userService.searchUsersByProfileName(profileNameToSearch);

        return new ResponseEntity<>(userList, HttpStatus.OK);

    }

}
