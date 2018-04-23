package project.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.model.Report;
import project.model.Task;
import project.model.TaskCollaborator;
import project.services.TaskService;
import project.services.UserService;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/projects/{projid}/tasks/{taskid}/reports/")
public class RestReportController {

    private final TaskService taskService;
    private final UserService userService;


    @Autowired
    public RestReportController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    /**
     * Creates a Report associated with a Task, if the TaskCollaborator exists
     *
     * @param reportDto
     * @param taskid
     * @return The Report that was created
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Report> createReport(@RequestBody Report reportDto, @PathVariable String taskid, @PathVariable int projid) {


        ResponseEntity<Report> responseEntity;
        Task task = taskService.getTaskByTaskID(taskid);
        String email = reportDto.getTaskCollaborator().getProjectCollaboratorFromTaskCollaborator().getUserFromProjectCollaborator().getEmail();
        TaskCollaborator taskCollaborator = task.getTaskCollaboratorByEmail(email);
        int userId = userService.getUserByEmail(email).getUserID();


        //if taskCollaborator doesn't exist
        if (taskCollaborator == null) {
            responseEntity = new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        } else {

            task.createReport(reportDto.getTaskCollaborator(), reportDto.getFirstDateOfReport(), reportDto.getCost());
            taskService.saveTask(task);
            responseEntity = ResponseEntity.ok().body(reportDto);

            Link reference = linkTo(RestProjectController.class).slash(projid).slash("tasks").slash(taskid).slash("reports").withRel("Show all Reports from Task");
            reportDto.add(reference);
            Link reference1 = linkTo(RestProjectController.class).slash(projid).slash("tasks").slash(taskid).slash("reports").slash("users").slash(userId).withRel("Show Reports from User");
            reportDto.add(reference1);
        }
        return responseEntity;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Report>> getTaskReports(@PathVariable String taskid) {

        ResponseEntity<List<Report>> responseEntity;

        Task task = taskService.getTaskByTaskID(taskid);

        //In case task doesn't have reports created,
        if (task.getReports() == null) {
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        else {
            responseEntity = ResponseEntity.ok().body(task.getReports());
            return responseEntity;
        }

        return responseEntity;
    }
}


