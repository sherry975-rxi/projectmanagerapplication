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

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/projects/{projid}/tasks/{taskid}/reports/")
public class RestReportController {



    private final TaskService taskService;


    @Autowired
    public RestReportController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Creates a Report associated with a Task, if the TaskCollaborator exists
     *
     * @param reportDto
     * @param taskID
     * @param projectID
     * @return The Report that was created
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Report> createReport(@RequestBody Report reportDto, @PathVariable String taskID, @PathVariable int projectID) {


        ResponseEntity<Report> responseEntity;
        Task task = taskService.getTaskByTaskID(taskID);
        String email = reportDto.getTaskCollaborator().getProjectCollaboratorFromTaskCollaborator().getUserFromProjectCollaborator().getEmail();
        TaskCollaborator taskCollaborator = task.getTaskCollaboratorByEmail(email);


        //if taskCollaborator doesn't exist
        if (taskCollaborator == null) {
            responseEntity = new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        } else {

            task.createReport(reportDto.getTaskCollaborator(), reportDto.getFirstDateOfReport(), reportDto.getCost());
            taskService.saveTask(task);
            responseEntity = ResponseEntity.ok().body(reportDto);

            Link reference = linkTo(RestReportController.class).slash(task.getReports()).slash("Reports").withRel("Task");
            task.add(reference);
        }
        return responseEntity;
    }
}


