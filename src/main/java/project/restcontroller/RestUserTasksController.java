package project.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import project.model.Task;
import project.model.User;
import project.services.TaskService;
import project.services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/users/{userId}/tasks/")
public class RestUserTasksController {
    private final TaskService taskService;
    private final UserService userService;

    @Autowired
    public RestUserTasksController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    /**
     * This method returns a ResponseEntity that contains all the user's finished tasks by decreasing order
     *
     * @return
     */
    @RequestMapping(value = "finished", method = RequestMethod.GET)
    public List<Task> getUserFinishedTasks(@PathVariable int userId) {

        return taskService.getAllFinishedUserTasksInDecreasingOrder(this.userService.getUserByID(userId));
    }


    /**
     *
     * This method converts the string of a User's ID from the URI into an integer,
     * Locates the User's data, and then finds all Ongoing tasks associated with that user.
     * Tasks are returned as a list of Strings
     *
     * It performs several checks to ensure consistency:
     *  - ensuring the User's ID is an integer
     *  - ensuring the User's ID actually exists
     *  - ensuring the User's ID matches the logged in user (TO BE IMPLEMENTED)
     *
     *  All of these cases return a List containing only "401 Unauthorized" to ensure database privacy
     */
    @RequestMapping(value = "pending",method = RequestMethod.GET)

    public ResponseEntity<List<String>> getPendingTasks(@PathVariable String userId) {

        Integer id;

        User user;

        List<String> userListString = new ArrayList<>();

        try {
            id = Integer.parseInt(userId);
            user = userService.getUserByID(id);
        } catch (NumberFormatException e) {
            userListString.add("401 Unauthorized");
            return ResponseEntity.ok().body(userListString);
        }
        if (user == null) {

            userListString.add("401 Unauthorized");

            return ResponseEntity.ok().body(userListString);

        } else {

            List<String> taskListString = taskService.getStartedNotFinishedUserTaskList(user)
                    .stream().map(task -> task.getTaskID()+ " - " + task.getDescription()).collect(Collectors.toList());

            if(taskListString.isEmpty()) {
                taskListString.add("You have no ongoing tasks!");
            }

            return ResponseEntity.ok().body(taskListString);

        }
    }

}
