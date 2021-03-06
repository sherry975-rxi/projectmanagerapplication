package project.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;
import project.model.Profile;
import project.model.User;
import project.ui.console.loadfiles.filestorage.StorageService;
import project.services.UserService;
import project.ui.console.loadfiles.loaduser.UserReader;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("users")
public class RestUserController {

    private final UserService userService;
    private final StorageService storageService;
    private String userDetails = "User Details";
    private final UserReader userReader;

    @Autowired
    public RestUserController(UserService userService, StorageService storageService, UserReader userReader) {
        this.userService = userService;
        this.storageService = storageService;
        this.userReader = userReader;
    }

    /**
     * This method returns the complete user details when given the
     * @param userId
     * it also generates the links to change the details or go to the users tasks and projects
     * @return
     */
    @PreAuthorize("hasRole('ROLE_COLLABORATOR') and #userId == principal.id or hasRole('ROLE_ADMIN') or hasRole('ROLE_DIRECTOR')")
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

    @PreAuthorize("hasAnyRole('ROLE_ADMIN') or hasRole('ROLE_DIRECTOR')")
    @RequestMapping(value = "/allUsers", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllUsers(){

        List<User> allUsers = userService.getAllUsersFromUserContainer();

        for(User user : allUsers) {

            Link userLink = linkTo(RestUserController.class).withRel("allUsers");
            user.add(userLink);
            Link detailsLinks = linkTo(RestUserController.class).slash(user.getUserID()).withRel(userDetails);
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
    @PreAuthorize("hasRole('ROLE_COLLABORATOR') and #emailToSearch == principal.username or hasRole('ROLE_ADMIN') or hasRole('ROLE_DIRECTOR')")
    @RequestMapping(value = "/email/{emailToSearch}", method = RequestMethod.GET)
    public ResponseEntity<List<User>> searchUsersByEmail(@PathVariable String emailToSearch) {

        List<User> foundUsers = userService.searchUsersByPartsOfEmail(emailToSearch);

        for(User other : foundUsers) {
            Link selfRef = linkTo(RestUserController.class).slash("email").slash(other.getEmail()).withSelfRel();
            other.add(selfRef);
            Link detailsLinks = linkTo(RestUserController.class).slash(other.getUserID()).withRel(userDetails);
            other.add(detailsLinks);
        }

        return new ResponseEntity<>(foundUsers, HttpStatus.OK);
    }

    /**
     *  This method allows the administrator search users by Id
     * @param idToSearch
     * @return ResponseEntity
     */
    @PreAuthorize("hasRole('ROLE_COLLABORATOR') or hasRole('ROLE_ADMIN') or hasRole('ROLE_DIRECTOR')")
    @RequestMapping(value = "/id/{idToSearch}", method = RequestMethod.GET)
    public ResponseEntity<User> searchUsersById(@PathVariable int idToSearch) {

        User user = userService.getUserByID(idToSearch);

            Link selfRef = linkTo(RestUserController.class).slash("id").slash(idToSearch).withSelfRel();
            user.add(selfRef);
            Link detailsLinks = linkTo(RestUserController.class).slash(user.getUserID()).withRel(userDetails);
            user.add(detailsLinks);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Refers to US136: This method allows the administrator to search users by profile.
     *
     * @param profileNameToSearch
     * @return ResponseEntity
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') or hasRole('ROLE_DIRECTOR')")
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
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') or hasRole('ROLE_DIRECTOR')")
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/uploadUserFile", method = RequestMethod.POST)
    public void updateUsersFromFile(@RequestParam("myFile") MultipartFile file) throws IOException, SAXException, ParserConfigurationException {

        storageService.init();
        storageService.store(file);
        userReader.readFile("uploadFiles/" + file.getOriginalFilename());
        storageService.deleteAll();


    }

}
