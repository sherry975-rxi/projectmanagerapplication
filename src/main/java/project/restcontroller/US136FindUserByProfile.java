package project.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import project.model.User;
import project.services.UserService;

import java.util.List;

/**
 * This controller allows the administrator to search system user by profile
 */
@RestController
@RequestMapping("/users/{profileNameToSearch}")
public class US136FindUserByProfile {

    private final UserService userService;

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
    public List<User> searchUsersByProfileController(@PathVariable String profileNameToSearch) {


        return userService.searchUsersByProfileName(profileNameToSearch);

    }

}
