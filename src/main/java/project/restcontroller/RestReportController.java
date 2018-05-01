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
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

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
     * <p>
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
        int userid = userService.getUserByEmail(email).getUserID();
        Calendar firstDateOfReport = Calendar.getInstance();


        //if taskCollaborator doesn't exist
        if (taskCollaborator == null) {
            responseEntity = new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        } else {
            task.createReport(taskCollaborator, firstDateOfReport, reportDto.getReportedTime());
            taskService.saveTask(task);

            for (Report reportCreated : task.getReports()) {
                if (reportCreated.getTaskCollaborator().equals(taskCollaborator) && Math.abs(reportCreated.getReportedTime() - reportDto.getReportedTime()) < 0.0000000001) {

                    Link reference = linkTo(methodOn(RestReportController.class).getTaskReports(taskid, projid)).withRel("Show all Reports from Task").withType(RequestMethod.GET.name());
                    reportCreated.add(reference);
                    Link reference1 = linkTo(methodOn(RestReportController.class).getTaskReportsFromUser(userid, taskid, projid)).withRel("Show Reports from User").withType(RequestMethod.GET.name());
                    reportCreated.add(reference1);
                    Link updateReport = linkTo(methodOn(RestReportController.class).updateTaskReport(reportCreated, taskid, projid, reportCreated.getDbId())).withRel("Update Report from User").withType(RequestMethod.PUT.name());
                    reportCreated.add(updateReport);

                    responseEntity = new ResponseEntity<>(reportCreated, HttpStatus.CREATED);
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

        responseEntity = ResponseEntity.ok().body(task.getReports());

        Link reference = linkTo(methodOn(RestReportController.class).getTaskReports(taskid, projid)).withSelfRel().withType(RequestMethod.GET.name());
        task.add(reference);

        return responseEntity;
    }

    /**
     * This method returns all Task Reports with a certain user
     * <p>
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
        if (!taskService.isCollaboratorActiveOnAnyTask(task.getTaskCollaboratorByEmail(userDto.getEmail()).getProjectCollaboratorFromTaskCollaborator())) {
            responseEntity = new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        } else {

            for (Report reportCreated : reports) {

                Link updateReport = linkTo(methodOn(RestReportController.class).updateTaskReport(reportCreated, taskid, projid, reportCreated.getDbId())).withRel("Update Report from User").withType(RequestMethod.PUT.name());
                reportCreated.add(updateReport);

            }
            responseEntity = ResponseEntity.ok().body(reports);

        }

        return responseEntity;
    }

    @RequestMapping(value = "{reportid}/update", method = RequestMethod.PUT)
    public ResponseEntity<Report> updateTaskReport(@RequestBody Report reportDto, @PathVariable String taskid, @PathVariable int projid, @PathVariable int reportid) {


        projectService.getProjectById(projid);
        ResponseEntity<Report> responseEntity = new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        Task task = taskService.getTaskByTaskID(taskid);

        double newTimeReported = reportDto.getReportedTime();

        String email = reportDto.getTaskCollaborator().getProjectCollaboratorFromTaskCollaborator().getUserFromProjectCollaborator().getEmail();
        TaskCollaborator taskCollaborator = task.getTaskCollaboratorByEmail(email);
        int userid = userService.getUserByEmail(email).getUserID();


        List<Report> taskReportsList = task.getReports();

        for (int reportIndex = 0; reportIndex < taskReportsList.size(); reportIndex++) {
            if (taskReportsList.get(reportIndex).getDbId() == reportid) {

                task.updateReportedTime(newTimeReported, taskCollaborator, reportIndex);
                Calendar dateOfUpdateReport = Calendar.getInstance();
                Report reportUpdated = taskReportsList.get(reportIndex);
                reportUpdated.setDateOfUpdate(dateOfUpdateReport);
                this.taskService.saveTask(task);

                Link reference1 = linkTo(methodOn(RestReportController.class).getTaskReportsFromUser(userid, taskid, projid)).withRel("Show Reports from User").withType(RequestMethod.GET.name());
                reportUpdated.add(reference1);

                responseEntity = new ResponseEntity<>(reportUpdated, HttpStatus.OK);
            }
        }

        return responseEntity;

    }
}






















