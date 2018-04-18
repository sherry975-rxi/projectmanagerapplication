package project.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.model.Profile;
import project.model.User;
import project.services.ProjectService;
import project.services.TaskService;
import project.services.UserService;

@RestController
@RequestMapping("users/")
public class RestUserController {

    private UserService userService;

    @Autowired
    public RestUserController (UserService userService, TaskService taskService, ProjectService projectService) {
        this.userService = userService;

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
