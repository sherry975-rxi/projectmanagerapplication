package project.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.model.*;
import project.services.ProjectService;
import project.services.TaskService;
import project.services.UserService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/projects/{projectId}/tasks/{taskId}")
public class US207CreateTaskReportRestController {

    private UserService userService;
    private TaskService taskService;
    private ProjectService projectService;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("d/MM/yyyy");

    @Autowired
    public US207CreateTaskReportRestController(UserService userService, TaskService taskService, ProjectService projectService) {
        this.taskService = taskService;
        this.userService = userService;
        this.projectService = projectService;
    }


    @RequestMapping(value = "/reports", method = RequestMethod.GET)
    public ResponseEntity<List<Report>> getTasksReportsFromUser(@PathVariable String taskId, @PathVariable int projectId, @RequestHeader String userEmail){

        Project project = projectService.getProjectById(projectId);

        Task task = taskService.getTaskByTaskID(taskId);

        User user = userService.getUserByEmail(userEmail);

        if(!task.doesTaskHaveReportByGivenUser(userEmail)){

            return new ResponseEntity<List<Report>>(HttpStatus.NOT_FOUND);
        }


        List<Report> userReportsList = task.getReportsFromGivenUser(userEmail);

        return new ResponseEntity<List<Report>>(userReportsList, HttpStatus.OK);

    }

    @RequestMapping(value = "/reportsString", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getTaskStringReportsFromUser(@PathVariable String taskId, @PathVariable int projectId, @RequestHeader String userEmail){

        Project project = projectService.getProjectById(projectId);

        Task task = taskService.getTaskByTaskID(taskId);

        User user = userService.getUserByEmail(userEmail);

        if(!task.doesTaskHaveReportByGivenUser(userEmail)){

            return new ResponseEntity<List<String>>(HttpStatus.NOT_FOUND);
        }

        List<String> reportsList = task.getReportsFromGivenUser(userEmail)
                .stream().map(Report -> dateToString(Report)).collect(Collectors.toList());

        return new ResponseEntity<List<String>>(reportsList, HttpStatus.OK);

    }


    @RequestMapping(value = "/reports/" , method = RequestMethod.POST)
    public ResponseEntity<?> createTaskReport (@RequestBody Report reportDTO, @PathVariable String taskId, @PathVariable int projectId){
        ResponseEntity<?> result = new ResponseEntity<>(HttpStatus.FORBIDDEN);

        Project project = projectService.getProjectById(projectId);

        Task task = taskService.getTaskByTaskID(taskId);

        TaskCollaborator taskCollabDTO = reportDTO.getTaskCollaborator();
        String userEmailDTO = taskCollabDTO.getProjCollaborator().getUserFromProjectCollaborator().getEmail();

        TaskCollaborator taskCollab = task.getTaskCollaboratorByEmail(userEmailDTO);

        Calendar dateOfReport = Calendar.getInstance();

        double timeReported = reportDTO.getReportedTime();


        if(task.createReport(taskCollab, dateOfReport, timeReported)){
            this.taskService.saveTask(task);

            String time = Double.toString(timeReported);

            System.out.println("Updating Task " + task.getTaskID());

            User user = userService.getUserByEmail(userEmailDTO);

            result = ResponseEntity.status(HttpStatus.OK).body("Report created!\nINFO:" + "\nTask ID: " + task.getTaskID() +"\nDescription: " + task.getDescription() + "\nUser: " + user.getName() + "\nTime reported: " + time);
            //result = new ResponseEntity<>(HttpStatus.OK);

        }

        return result;
    }


        public String dateToString(Report rep){

            Calendar firstDate = rep.getFirstDateOfReport();

            String datePrint = this.dateFormat.format(firstDate.getTime());

            Calendar updateDate = rep.getDateOfUpdate();

            String datePrintTwo = this.dateFormat.format(updateDate.getTime());


            return " Reported time: " + rep.getReportedTime() + " by " + rep.getTaskCollaborator().getProjectCollaboratorFromTaskCollaborator().getUserFromProjectCollaborator().getName() + " - Creation Date: " + datePrint + " - Update Date: " + datePrintTwo;
        }

    @RequestMapping(value = "/reports/{reportId}", method = RequestMethod.PUT)
    public ResponseEntity<Report> updateTaskReport(@RequestBody Report reportDTO, @PathVariable int reportId, @PathVariable String taskId, @PathVariable int projectId){

        ResponseEntity<Report> result = new ResponseEntity<>(HttpStatus.FORBIDDEN);

        Project project = projectService.getProjectById(projectId);

        Task task = taskService.getTaskByTaskID(taskId);

        TaskCollaborator taskCollabDTO = reportDTO.getTaskCollaborator();
        String userEmailDTO = taskCollabDTO.getProjCollaborator().getUserFromProjectCollaborator().getEmail();

        TaskCollaborator taskCollab = task.getTaskCollaboratorByEmail(userEmailDTO);


        double newTimeReported = reportDTO.getReportedTime();

        if(!task.doesTaskHaveReportByGivenUser(userEmailDTO)){

            return new ResponseEntity<Report>(HttpStatus.NOT_FOUND);
        }
        else {


            List<Report> taskReportsList = task.getReports();

            for (int reportIndex = 0; reportIndex < taskReportsList.size(); reportIndex++) {
                if (taskReportsList.get(reportIndex).getId() == reportId) {
                    task.updateReportedTime(newTimeReported, taskCollab, reportIndex);
                    Calendar dateOfReport = Calendar.getInstance();
                    Report reportUpdated = taskReportsList.get(reportIndex);
                    reportUpdated.setDateOfUpdate(dateOfReport);
                    this.taskService.saveTask(task);

                    return new ResponseEntity<Report>(reportUpdated, HttpStatus.OK);

                }
            }
        }

        return result;




    }


}
