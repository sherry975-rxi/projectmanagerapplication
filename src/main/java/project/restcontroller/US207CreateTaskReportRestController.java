package project.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.model.Project;
import project.model.Task;
import project.model.User;
import project.services.ProjectService;
import project.services.TaskService;
import project.services.UserService;

import java.util.Calendar;

@RestController
@RequestMapping("/projects/{projectId}/tasks/{taskId}")
public class US207CreateTaskReportRestController {

    private UserService userService;
    private TaskService taskService;
    private ProjectService projectService;

    @Autowired
    public US207CreateTaskReportRestController(UserService userService, TaskService taskService, ProjectService projectService) {
        this.taskService = taskService;
        this.userService = userService;
        this.projectService = projectService;
    }

    @RequestMapping(value = "/CreateReport" , method = RequestMethod.POST)
    public ResponseEntity<?> createTaskReport (@RequestHeader double timeToReport, @PathVariable String taskId, @PathVariable int projectId, @RequestHeader String userEmail){
        ResponseEntity<?> result = new ResponseEntity<>(HttpStatus.FORBIDDEN);


        Project project = projectService.getProjectById(projectId);

        Task task = taskService.getTaskByTaskID(taskId);

        Calendar dateOfReport = Calendar.getInstance();

        if(task.createReport(task.getTaskCollaboratorByEmail(userEmail), dateOfReport, timeToReport)){
            this.taskService.saveTask(task);
            result = ResponseEntity.status(HttpStatus.OK).body("Report created!" + "\nValue of: " + timeToReport);
            //result = new ResponseEntity<>(HttpStatus.OK);

        }


        return result;
    }




}
