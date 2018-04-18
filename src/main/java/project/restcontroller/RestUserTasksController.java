package project.restcontroller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users/{userid}/tasks/")
public class RestUserTasksController {
}
