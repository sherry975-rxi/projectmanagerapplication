package project.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.model.*;
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

    @RequestMapping(value = "/CreateReport" , method = RequestMethod.PUT)
    public ResponseEntity<?> createTaskReport (@RequestBody String timeToReport, @PathVariable String taskId, @PathVariable int projectId, @RequestHeader String userEmail){
        ResponseEntity<?> result = new ResponseEntity<>(HttpStatus.FORBIDDEN);

        double timeReported = Double.valueOf(timeToReport);

        Project project = projectService.getProjectById(projectId);

        Task task = taskService.getTaskByTaskID(taskId);

        User user = userService.getUserByEmail(userEmail);

        Calendar dateOfReport = Calendar.getInstance();

        if(task.createReport(task.getTaskCollaboratorByEmail(userEmail), dateOfReport, timeReported)){
            this.taskService.saveTask(task);
            result = ResponseEntity.status(HttpStatus.OK).body("Report created!\nINFO:" + "\nTask ID: " + task.getTaskID() +"\nDescription: " + task.getDescription() + "\nUser: " + user.getName() + "\nTime reported: " + timeToReport);
            //result = new ResponseEntity<>(HttpStatus.OK);

        }


        return result;
    }

    @RequestMapping(value = "/CreateReportTwo" , method = RequestMethod.PUT)
    public ResponseEntity<?> createTaskReportTwo (@RequestBody Report reportOfTime, @PathVariable String taskId, @PathVariable int projectId, @RequestHeader String userEmail){
        ResponseEntity<?> result = new ResponseEntity<>(HttpStatus.FORBIDDEN);

        Project project = projectService.getProjectById(projectId);

        Task task = taskService.getTaskByTaskID(taskId);

        User user = userService.getUserByEmail(userEmail);

        TaskCollaborator taskCollab = task.getTaskCollaboratorByEmail(userEmail);

        Calendar dateOfReport = Calendar.getInstance();

        double timeReported = reportOfTime.getReportedTime();

        String time = Double.toString(timeReported);

        System.out.println("Updating Task " + task.getTaskID());




        if(task.createReport(taskCollab, dateOfReport, timeReported)){
            this.taskService.saveTask(task);
            System.out.println();
            result = ResponseEntity.status(HttpStatus.OK).body("Report created!\nINFO:" + "\nTask ID: " + task.getTaskID() +"\nDescription: " + task.getDescription() + "\nUser: " + user.getName() + "\nTime reported: " + time);
            //result = new ResponseEntity<>(HttpStatus.OK);

        }


        return result;
    }




}
