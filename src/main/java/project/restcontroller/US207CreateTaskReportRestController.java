package project.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.model.*;
import project.services.ProjectService;
import project.services.TaskService;
import project.services.UserService;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
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
                .stream().map(Report -> repDataToString(Report)).collect(Collectors.toList());

        return new ResponseEntity<List<String>>(reportsList, HttpStatus.OK);




    }


    @RequestMapping(value = "/reports/" , method = RequestMethod.PUT)
    public ResponseEntity<?> createTaskReport (@RequestBody Report reportDTO, @PathVariable String taskId, @PathVariable int projectId, @RequestHeader String userEmail){
        ResponseEntity<?> result = new ResponseEntity<>(HttpStatus.FORBIDDEN);

        Project project = projectService.getProjectById(projectId);

        Task task = taskService.getTaskByTaskID(taskId);

        User user = userService.getUserByEmail(userEmail);

        TaskCollaborator taskCollab = reportDTO.getTaskCollaborator();

        //TaskCollaborator taskCollab = task.getTaskCollaboratorByEmail(userEmail);

        Calendar dateOfReport = Calendar.getInstance();

        double timeReported = reportDTO.getReportedTime();

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



        public String repDataToString (Report rep){

            Calendar firstDate = rep.getFirstDateOfReport();

            String datePrint = this.dateFormat.format(firstDate.getTime());

            Calendar updateDate = rep.getDateOfUpdate();

            String datePrintTwo = this.dateFormat.format(updateDate.getTime());


            return " Reported time: " + rep.getReportedTime() + " by " + rep.getTaskCollaborator().getProjectCollaboratorFromTaskCollaborator().getUserFromProjectCollaborator().getName() + " - Creation Date: " + datePrint + " - Update Date: " + datePrintTwo;
        }




/*            List<Integer> reportsOfGivenUser;

            reportsOfGivenUser = task.getReportsIndexOfTaskCollaborator(userEmail);

            List<String> reportsList;

            for(int i = 0; i < reportsOfGivenUser.size(); i++){
                reportsList.add(reportsOfGivenUser.get(i).toString() + " - " + )
            }*//*
            return new ResponseEntity<List<String>>(HttpStatus.NO_CONTENT);
}
//        List<Integer> reportsOfGivenUser = task.getReportsIndexOfTaskCollaborator(userEmail);

    List<Report> reportsList = task.getReportsFromGivenUser(userEmail)
            .stream().map(Report -> repDataToString(Report).collect(Collectors.toList());



        return new ResponseEntity<List<String>>(reportsList,HttpStatus.OK);*/





}
