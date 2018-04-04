package project.restControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import project.model.Project;
import project.model.Task;
import project.model.User;
import project.services.ProjectService;
import project.services.TaskService;
import project.services.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("users/{userID}")
class US203FindPendingTaskRestRequest {

    private final UserService userService;
    private final TaskService taskService;
    private User user;
    private Task task;

    @Autowired
    public US203FindPendingTaskRestRequest(UserService userService, TaskService taskService){
        this.userService = userService;
        this.taskService = taskService;

    }

    @RequestMapping(method = RequestMethod.GET)
    List<String> findPendingTasksController(@PathVariable String userID) {


        Integer ID;

        List<String> userListString = new ArrayList<>();

        try {
            ID = Integer.parseInt(userID);
            this.user = userService.getUserByID(ID);
        } catch (NumberFormatException e) {
            userListString.add("401 Unauthorized");
            return userListString;
        }



        if (user == null) {

            userListString.add("401 Unauthorized");

            return userListString;
        } else {

            userListString.add("501 Not implemented");

            return userListString;

        }
    }


    /*
    @RequestMapping(method = RequestMethod.GET)
    public User getUserByEmail (String email){
        return this.user = userService.getUserByEmail(email);





    @RequestMapping(method = RequestMethod.GET)
    List<Task> viewPendingTasks(@PathVariable int userID){

        return this.taskService.getStartedNotFinishedUserTaskList(user);

    }
   */

   }

