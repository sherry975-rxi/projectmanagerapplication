package project.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.model.Profile;
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
     * This method returns the complete user details when given the
     * @param userId
     * it also generates the links to change the details or go to the users tasks and projects
     * @return
     */
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity<User> seeUserDetails(@PathVariable int userId){
        ResponseEntity<User> result = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        User userToReturn;
        userToReturn = userService.getUserByID(userId);
        if(userToReturn != null){
            result = new ResponseEntity<>(userToReturn , HttpStatus.OK);
            Link selfLink = linkTo(RestUserController.class).slash(userToReturn.getUserID()).withSelfRel();
            userToReturn.add(selfLink);
            Link editInfoLink = linkTo(RestUserController.class).slash(userToReturn.getUserID()).slash("details").withRel("Edit User Information");
            userToReturn.add(editInfoLink);
            Link seeProjectsLink = linkTo(RestUserController.class).slash(userToReturn.getUserID()).slash("projects").withRel("See My Projects");
            userToReturn.add(seeProjectsLink);
            Link seePendingTasksLink = linkTo(RestUserController.class).slash(userToReturn.getUserID()).slash("tasks").slash("pending").withRel("See My Pending Tasks");
            userToReturn.add(seePendingTasksLink);
            Link seeFinishedTasksLink = linkTo(RestUserController.class).slash(userToReturn.getUserID()).slash("tasks").slash("finished").withRel("See My Pending Tasks");
            userToReturn.add(seeFinishedTasksLink);
        }
        return result;
    }


    /**
     * Refers to US130: As an administrator, I want to list all users in the system.
     * This method will return a list of all the users registered in the system.
     */

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/allUsers", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllUsers(){

        List<User> allUsers = userService.getAllUsersFromUserContainer();

        for(User user : allUsers) {

            Link userLink = linkTo(RestUserController.class).withRel("allUsers");
            user.add(userLink);
            Link detailsLinks = linkTo(RestUserController.class).slash(user.getUserID()).withRel("User Details");
            user.add(detailsLinks);

        }
        return new ResponseEntity<>(allUsers, HttpStatus.OK);


    }


    /**
     * Refers to US135: This method allows the administrator to search users by a part of the email.
     *
     * @param emailToSearch
     * @return ResponseEntity
     */
    @RequestMapping(value = "/email/{emailToSearch}", method = RequestMethod.GET)
    public ResponseEntity<List<User>> searchUsersByEmail(@PathVariable String emailToSearch) {

        List<User> foundUsers = userService.searchUsersByPartsOfEmail(emailToSearch);

        for(User other : foundUsers) {
            Link selfRef = linkTo(RestUserController.class).slash("email").slash(other.getEmail()).withSelfRel();
            other.add(selfRef);
            Link detailsLinks = linkTo(RestUserController.class).slash(other.getUserID()).withRel("User Details");
            other.add(detailsLinks);
        }

        return new ResponseEntity<>(foundUsers, HttpStatus.OK);
    }

    /**
     * Refers to US136: This method allows the administrator to search users by profile.
     *
     * @param profileNameToSearch
     * @return ResponseEntity
     */
    @RequestMapping(value = "/profiles/{profileNameToSearch}", method = RequestMethod.GET)
    public ResponseEntity<List<User>> searchUsersByProfile(@PathVariable String profileNameToSearch) {

        List<User> foundUsersProfile = userService.searchUsersByProfileName(profileNameToSearch.toUpperCase());

        for(User other : foundUsersProfile){
            Link selfRef = linkTo(RestUserController.class).slash(other.getUserID()).withSelfRel();
            other.add(selfRef);
        }

        return new ResponseEntity<>(foundUsersProfile, HttpStatus.OK);

    }

    /**
     * @param updatedProfile
     * Given a body updatedProfile containing the profile information and user email to update in a user
     * it searches for the user in the database and updates its profile to the new given profile in the @param
     * @return Http.Status.Ok when done sucessfully and Http.Status.404_Not_Found when a user doesn't exist.
     *
     */
    @RequestMapping(value = "/profiles" , method = RequestMethod.PATCH)
    public ResponseEntity<User> changeUserProfile (@RequestBody User updatedProfile) {

        ResponseEntity<User> result = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Profile profileChange = updatedProfile.getUserProfile();

        User userToChange = userService.getUserByEmail(updatedProfile.getEmail());

        if(userToChange != null){
            userToChange.setUserProfile(profileChange);
            userService.updateUser(userToChange);
            Link selfLink = linkTo(RestUserController.class).slash(userToChange.getUserID()).withRel("See User Info");
            userToChange.add(selfLink);
            result = new ResponseEntity<>(userToChange ,HttpStatus.OK);
        }
        return result;

    }

}
