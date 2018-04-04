package project.restControllers.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import project.services.TaskService;
import project.services.UserService;

@RestController
@RequestMapping
public class US203FindPendingTaskRestController {

    private final UserService userService;

    private final TaskService taskService;

    @Autowired
    US203FindPendingTaskRestController(UserService userService, TaskService taskService){
        this.userService = userService;
        this.taskService = taskService;
    }

    @RequestMapping(method = RequestMethod.GET)
    List<User> readUsers (@PathVariable String userID){


        this.validadeUSer(userID);
        return this.userService.getUserByID(userID);
    }




}
