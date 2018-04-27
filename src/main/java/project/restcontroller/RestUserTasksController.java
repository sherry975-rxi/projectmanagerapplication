package project.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import project.model.Task;
import project.services.TaskService;
import project.services.UserService;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

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

    @RequestMapping(value = "finished", method = RequestMethod.GET)
    public List<Task> getUserFinishedTasks(@PathVariable Integer userId) {
        List<Task> taskList = taskService.getAllFinishedUserTasksInDecreasingOrder(this.userService.getUserByID(userId));
        for(Task task: taskList) {
            Link reference = linkTo(getClass(), userId).slash("task").slash(task.getDbTaskId()).withSelfRel();
            task.add(reference);
        }
        return taskList;
    }

    @RequestMapping(value = "pending", method = RequestMethod.GET)
    public List<Task> getPendingTasks(@PathVariable Integer userId) {
        List<Task> taskList = taskService.getStartedNotFinishedUserTaskList(userService.getUserByID(userId));
        for(Task task: taskList) {
            Link reference = linkTo(getClass(), userId).slash("task").slash(task.getDbTaskId()).withSelfRel();
            task.add(reference);
        }

        return taskList;
    }


}
