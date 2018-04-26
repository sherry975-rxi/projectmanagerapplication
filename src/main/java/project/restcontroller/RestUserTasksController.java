package project.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;
import project.dto.UserDTO;
import project.model.Task;
import project.model.User;
import project.services.TaskService;
import project.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.ArrayList;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/users/{userId}/tasks/")
public class RestUserTasksController {

    UserService userService;
    TaskService taskService;
    HttpServletRequest request;


    @Autowired
    public RestUserTasksController(TaskService taskService, HttpServletRequest request, UserService userService) {

        this.userService = userService;
        this.taskService = taskService;
        this.request = request;
    }

    /**
     * This method extracts the number of the user from the RequestMapping URI
     * In case the ID in the URI is not an Integer, it will return null;
     *
     * @return Integer
     * Returns an Integer in case the id of the project is valid and exists ELSE returns
     */
    @SuppressWarnings("unchecked")
    public Integer getUserIdByURI() {

        Map<String, String> variables = new HashMap<>();
        variables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);


        String userIDString = variables.get("userid");

        Integer userID;

        try {
            userID = Integer.parseInt(userIDString);
        } catch (NumberFormatException e) {
            userID = null;
        }

        return userID;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<Task>> getAllTasks(@PathVariable String userid) {

        Integer userId = getUserIdByURI();

        User user = userService.getUserByID(userId);

        List<Task> taskFromUser = taskService.getUserTasks(user);

        return ResponseEntity.ok().body(taskFromUser);
    }

    @RequestMapping(value = "totaltimespent/completed/lastmonth", method = RequestMethod.GET)
    public ResponseEntity<Double> getTotalTimeSpentOnTasksCompletedLastMonth(@PathVariable String userid) {

        Integer userId = getUserIdByURI();

        User user = userService.getUserByID(userId);

        List<Task> taskFromUser = taskService.getAllFinishedTasksFromUser(user);

        Double totalTime = taskService.getTotalTimeOfFinishedTasksFromUserLastMonth(user);

        return ResponseEntity.ok().body(totalTime);

    }

    /**
     * This method converts the string of a User's ID from the URI into an integer,
     * Locates the User's data, and then finds all Ongoing tasks associated with that user.
     * Tasks are returned as a list of Strings
     * <p>
     * It performs several checks to ensure consistency:
     * - ensuring the User's ID is an integer
     * - ensuring the User's ID actually exists
     * - ensuring the User's ID matches the logged in user (TO BE IMPLEMENTED)
     * <p>
     * All of these cases return a List containing only "401 Unauthorized" to ensure database privacy
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
                    .stream().map(task -> task.getTaskID() + " - " + task.getDescription()).collect(Collectors.toList());

            if (taskListString.isEmpty()) {
                taskListString.add("You have no ongoing tasks!");
            }

            return ResponseEntity.ok().body(taskListString);

        }
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
}

