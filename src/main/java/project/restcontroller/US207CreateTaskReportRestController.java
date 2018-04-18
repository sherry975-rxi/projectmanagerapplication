package project.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/users/{userId}/projects/{projectId}/tasks/{taskId}")
public class US207CreateTaskReportRestController {

    private UserService userService;
    private TaskService taskService;
    private ProjectService projectService;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("d/MM/yyyy HH:mm");

    @Autowired
    public US207CreateTaskReportRestController(UserService userService, TaskService taskService, ProjectService projectService) {
        this.taskService = taskService;
        this.userService = userService;
        this.projectService = projectService;
    }


    @RequestMapping(value = "/reports", method = RequestMethod.GET)
    public ResponseEntity<List<Report>> getTasksReportsFromUser(@PathVariable String taskId, @PathVariable int projectId, @PathVariable  int userId){

        projectService.getProjectById(projectId);

        Task task = taskService.getTaskByTaskID(taskId);

        User user = userService.getUserByID(userId);

        if(!task.doesTaskHaveReportByGivenUser(user.getEmail())){

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


        List<Report> userReportsList = task.getReportsFromGivenUser(user.getEmail());

        return new ResponseEntity<>(userReportsList, HttpStatus.OK);

    }

    @RequestMapping(value = "/reportsString", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getTaskStringReportsFromUser(@PathVariable String taskId, @PathVariable int projectId, @PathVariable  int userId){

        projectService.getProjectById(projectId);

        Task task = taskService.getTaskByTaskID(taskId);

        User user = userService.getUserByID(userId);

        if(!task.doesTaskHaveReportByGivenUser(user.getEmail())){

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<String> reportsList;
        List<String> list = new ArrayList<>();
        for (project.model.Report Report : task.getReportsFromGivenUser(user.getEmail())) {
            String s = dateToString(Report);
            list.add(s);
        }
        reportsList = list;

        return new ResponseEntity<>(reportsList, HttpStatus.OK);

    }


    @RequestMapping(value = "/reports/" , method = RequestMethod.POST)
    public ResponseEntity<Object> createTaskReport (@RequestBody Report reportDTO, @PathVariable String taskId, @PathVariable int projectId, @PathVariable  int userId){

        projectService.getProjectById(projectId);

        Task task = taskService.getTaskByTaskID(taskId);

        User user = userService.getUserByID(userId);

        TaskCollaborator taskCollab = task.getTaskCollaboratorByEmail(user.getEmail());

        if(taskCollab==null){

            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }

        Calendar dateOfReport = Calendar.getInstance();

        double timeReported = reportDTO.getReportedTime();


        if(task.createReport(taskCollab, dateOfReport, timeReported)){
            this.taskService.saveTask(task);

            String time = Double.toString(timeReported);

            return ResponseEntity.ok().body("Report created!\nINFO:" + "\nTask ID: " + task.getTaskID() +"\nDescription: " + task.getDescription() + "\nUser: " + user.getName() + "\nTime reported: " + time);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }


        public String dateToString(Report rep){

            Calendar firstDate = rep.getFirstDateOfReport();

            String datePrint = this.dateFormat.format(firstDate.getTime());

            Calendar updateDate = rep.getDateOfUpdate();

            String datePrintTwo = this.dateFormat.format(updateDate.getTime());


            return " Reported time: " + rep.getReportedTime() + " by " + rep.getTaskCollaborator().getProjectCollaboratorFromTaskCollaborator().getUserFromProjectCollaborator().getName() + " - Creation Date: " + datePrint + " - Update Date: " + datePrintTwo;
        }

    @RequestMapping(value = "/reports/{reportId}", method = RequestMethod.PUT)
    public ResponseEntity<Report> updateTaskReport(@RequestBody Report reportDTO, @PathVariable int reportId, @PathVariable String taskId, @PathVariable int projectId, @PathVariable  int userId){

        projectService.getProjectById(projectId);

        Task task = taskService.getTaskByTaskID(taskId);

        User user = userService.getUserByID(userId);

        TaskCollaborator taskCollab = task.getTaskCollaboratorByEmail(user.getEmail());
        
        double newTimeReported = reportDTO.getReportedTime();

        if(!task.doesTaskHaveReportByGivenUser(user.getEmail()) || taskCollab==null){

            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }


        List<Report> taskReportsList = task.getReports();

        for (int reportIndex = 0; reportIndex < taskReportsList.size(); reportIndex++) {
            if (taskReportsList.get(reportIndex).getDbId() == reportId) {

                task.updateReportedTime(newTimeReported, taskCollab, reportIndex);
                Calendar dateOfReport = Calendar.getInstance();
                Report reportUpdated = taskReportsList.get(reportIndex);
                reportUpdated.setDateOfUpdate(dateOfReport);
                this.taskService.saveTask(task);

                return new ResponseEntity<>(reportUpdated, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }


}
