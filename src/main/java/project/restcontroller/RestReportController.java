package project.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.model.Project;
import project.model.Report;
import project.model.Task;
import project.model.TaskCollaborator;
import project.services.ProjectService;
import project.services.TaskService;
import project.services.UserService;

import java.util.Calendar;

@RestController
@RequestMapping("projects/{projid}/tasks/{taskid}/reports/")
public class RestReportController {


    private final ProjectService projectService;
    private final TaskService taskService;
    private final UserService userService;

    @Autowired
    public RestReportController(ProjectService projectService, TaskService taskService, UserService userService) {
        this.projectService = projectService;
        this.taskService = taskService;
        this.userService = userService;
    }

    /**
     * Creates a Report associated with a Task, if the TaskCollaborator exists
     *
     * @param reportDto
     * @param taskID
     * @param projectID
     * @param email
     * @return The Report that was created
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Report> createReport(@RequestBody Report reportDto, @PathVariable String taskID, @PathVariable int projectID, @PathVariable String email ){

        Project project = projectService.getProjectById(projectID);
        Task task = taskService.getTaskByTaskID(taskID);
        TaskCollaborator taskCollaborator = task.getTaskCollaboratorByEmail(email);

        double reportedTime;
        Calendar firstDateOfReport;
        firstDateOfReport = Calendar.getInstance();

        if (taskCollaborator == null){
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        } else {
            task.createReport(reportDto.getTaskCollaborator(), reportDto.getFirstDateOfReport(), reportDto.getCost());
            taskService.saveTask(task);
            return ResponseEntity.ok().body(reportDto);
        }
    }
}
