package project.restControllers;

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

@RestController
@RequestMapping("users/{userID}/viewPendingTasks")
class US203FindPendingTaskRestRequest {

    private final UserService userService;
    private final TaskService taskService;
    private User user;
    private Task task;
    private List<Task> taskList;

    @Autowired
    public US203FindPendingTaskRestRequest(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;

    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getPendingTasks(@PathVariable String userID) {

        Integer ID;

        List<String> userListString = new ArrayList<>();

        try {
            ID = Integer.parseInt(userID);
            this.user = userService.getUserByID(ID);
        } catch (NumberFormatException e) {
            userListString.add("401 Unauthorized");
            return ResponseEntity.ok().body(userListString);
        }
        if (user == null) {

            userListString.add("401 Unauthorized");

            return ResponseEntity.ok().body(userListString);
        } else {
    /*
      u


           this.taskList = taskService.getStartedNotFinishedUserTaskList(user);

            List<Task> taskListString = new ArrayList<>();

            for (int i = 0; i< taskList.size(); i++) {
                Integer visibleIndex = i+1;
                String toShowTask = "[" + visibleIndex.toString() + "] \n" + taskDataToString(taskList.get(i));
                taskListString.add(toShowTask);
            }
*/
            userListString.add("501 Not implemented");
            return ResponseEntity.ok().body(userListString);
           //return ResponseEntity.ok().body(taskListString);

        }
    }
}
/*
    }

    private String taskDataToString(Task toConvert){
        String
    }




    @RequestMapping(method = RequestMethod.GET)
    List<Task> viewPendingTasks(@PathVariable int userID){

        return this.taskService.getStartedNotFinishedUserTaskList(user);

    }
   */

