package project.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.model.Report;
import project.model.Task;
import project.model.TaskCollaborator;
import project.model.User;
import project.services.ProjectService;
import project.services.TaskService;
import project.services.UserService;

import java.util.Calendar;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/projects/{projid}/tasks/{taskid}/reports/")
public class RestReportController {

    private final TaskService taskService;
    private final UserService userService;
    private final ProjectService projectService;


    @Autowired
    public RestReportController(TaskService taskService, UserService userService, ProjectService projectService) {
        this.taskService = taskService;
        this.userService = userService;
        this.projectService = projectService;
    }

    /**
     * Creates a Report associated with a Task, if the TaskCollaborator exists
     *
     * Contains Links to "Show all Reports from Task" and to "Show Reports from User"
     *
     * @param reportDto
     * @param taskid
     * @param projid
     * @return The Report that was created
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Report> createReport(@RequestBody Report reportDto, @PathVariable String taskid, @PathVariable int projid) {


        projectService.getProjectById(projid);
        ResponseEntity<Report> responseEntity = new ResponseEntity<>(HttpStatus.FORBIDDEN);
        Task task = taskService.getTaskByTaskID(taskid);
        String email = reportDto.getTaskCollaborator().getProjectCollaboratorFromTaskCollaborator().getUserFromProjectCollaborator().getEmail();
        TaskCollaborator taskCollaborator = task.getTaskCollaboratorByEmail(email);
        int userId = userService.getUserByEmail(email).getUserID();
        Calendar firstDateOfReport = Calendar.getInstance();


        //if taskCollaborator doesn't exist
        if (taskCollaborator == null) {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        } else {
            task.createReport(taskCollaborator, firstDateOfReport, reportDto.getReportedTime());
            taskService.saveTask(task);

            for (Report reportCreated : task.getReports()) {
                if (reportCreated.getTaskCollaborator().equals(taskCollaborator) && reportCreated.getReportedTime() == reportDto.getReportedTime()) {

                    Link reference = linkTo(RestProjectController.class).slash(projid).slash("tasks").slash(taskid).slash("reports").withRel("Show all Reports from Task");
                    reportCreated.add(reference);
                    Link reference1 = linkTo(RestProjectController.class).slash(projid).slash("tasks").slash(taskid).slash("reports").slash("users").slash(userId).withRel("Show Reports from User");
                    reportCreated.add(reference1);

                    return ResponseEntity.ok().body(reportCreated);
                }
            }
        }
        return responseEntity;
    }


    /**
     * This method returns all Reports from a Task
     *
     * @param projid
     * @param taskid Identifier (String) of Task to retrieve Reports from
     * @return All Reports from Task
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Report>> getTaskReports(@PathVariable String taskid, @PathVariable int projid) {

        projectService.getProjectById(projid);
        ResponseEntity<List<Report>> responseEntity;

        Task task = taskService.getTaskByTaskID(taskid);

        //In case task doesn't have reports created,
        if (task.getReports() == null) {
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            responseEntity = ResponseEntity.ok().body(task.getReports());
        }

        return responseEntity;
    }

    /**
     * This method returns all Task Reports with a certain user
     *
     * Contains Link to "Show Reports from User"
     *
     * @param taskid Identifier (String) of Task to retrieve Reports from
     * @param projid
     * @param userid
     * @return All Reports from Task
     */
    @RequestMapping(value = "users/{userid}", method = RequestMethod.GET)
    public ResponseEntity<List<Report>> getTaskReportsFromUser(@PathVariable int userid, @PathVariable String taskid, @PathVariable int projid) {

        projectService.getProjectById(projid);
        User userDto = userService.getUserByID(userid);
        ResponseEntity<List<Report>> responseEntity;

        Task task = taskService.getTaskByTaskID(taskid);
        List<Report> reports = task.getReportsFromGivenUser(userDto.getEmail());

        //In case task doesn't have reports from a given user
        if (task.getReportsFromGivenUser(userDto.getEmail())== null) {
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            responseEntity = ResponseEntity.ok().body(task.getReportsFromGivenUser(userDto.getEmail()));

            for (Report reportCreated : reports) {
                Link reference = linkTo(RestProjectController.class).slash(projid).slash("tasks").slash(taskid).slash("reports").slash("users").slash(userid).slash("update").withRel("Update Report from User");
                reportCreated.add(reference);
                }
            return ResponseEntity.ok().body(reports);
            }

        return responseEntity;
    }
}






















