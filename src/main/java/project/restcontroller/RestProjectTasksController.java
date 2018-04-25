package project.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;
import project.model.Project;
import project.model.Task;
import project.services.ProjectService;
import project.services.TaskService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("projects/{projid}/tasks/")
public class RestProjectTasksController {

    TaskService taskService;

    ProjectService projectService;

    HttpServletRequest req;


    @Autowired
    public RestProjectTasksController(TaskService taskService, ProjectService projectService, HttpServletRequest req) {
        this.taskService = taskService;
        this.projectService = projectService;
        this.req = req;
    }


    /**
     * This method extracts the number of the project from the RequestMapping URI
     *         In case the ID in the URI is not an Integer, it will return null;
     *
     * @return Integer
     * Returns an Integer in case the id of the project is valid and exists ELSE returns
     */
    @SuppressWarnings("unchecked")
    public Integer getProjectIdByURI(){

        Map<String, String> variables = new HashMap<>();
        variables = (Map<String, String>) req.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);



        String projIDString = variables.get("projid");

        Integer projID;

        try {
            projID = Integer.parseInt(projIDString);
        } catch(NumberFormatException e){
            projID = null;
        }

        return projID;


    }


    /**
     * Creates a Task with a description, associated to a project thats in the URI of the controller.
     * If the project doesn't exist or it's an invalid ID, it will return HttpStatus.NOT_FOUND
     */
    @RequestMapping(value = "" , method = RequestMethod.POST)
    public ResponseEntity<Task> createTask(@RequestBody Task taskDTO) {

        ResponseEntity<Task> result = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Integer projID = getProjectIdByURI();

        if(projID != null) {

            Project projectTask = projectService.getProjectById(projID);
            Task task = taskService.createTask(taskDTO.getDescription(), projectTask);
            result =  ResponseEntity.ok().body(task);

        }

        return result;

    }


    /**
     * Refers to US 360: Como Gestor de projeto, quero obter a lista de tarefas do projeto sem
     * colaboradores ativos atribuídos.
     *
     * @param projid
     * @return
     */

    @RequestMapping(value="withoutCollaborators", method = RequestMethod.GET)
    public ResponseEntity<List<Task>> getTasksWithoutCollaborators(@PathVariable int projid){

        Project project = projectService.getProjectById(projid);

        List<Task> tasksWithoutCollabs = new ArrayList<>();

        tasksWithoutCollabs.addAll(taskService.getProjectTasksWithoutCollaboratorsAssigned(project));

        for(Task task: tasksWithoutCollabs){

            Link taskLink = linkTo(RestProjectController.class).slash(projid).slash("tasks").withSelfRel();
            task.add(taskLink);
        }

        return new ResponseEntity<>(tasksWithoutCollabs, HttpStatus.OK);
    }

    /**
     *  This method deletes a task from the database if that task has a state that allows its deletion
     *
     * @param taskId Task id of the task to be deleted
     *
     * @return ResponseBody with 202-ACCEPTED if deleted or 409-CONFLICT if not
     */
    @RequestMapping(value = "{taskId}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteTask(@PathVariable String taskId) {

        boolean deleted = taskService.deleteTask(taskId);
        ResponseEntity<Boolean> response = new ResponseEntity<>(deleted, HttpStatus.ACCEPTED);

        if(!deleted) {

            response = new ResponseEntity<>(deleted, HttpStatus.CONFLICT);

        }
        return response;
    }

    /**
     * This methods gets the list of finished tasks from a project
     *
     * @param projid Id of the project to search for finished tasks
     *
     * @return List of finished tasks from the project
     */
    @RequestMapping(value = "finished", method = RequestMethod.GET)
    public ResponseEntity<List<Task>> getFinishedTasks (@PathVariable int projid) {

        Project project = this.projectService.getProjectById(projid);
        List<Task> finishedTasks = new ArrayList<>();

        finishedTasks.addAll(taskService.getProjectFinishedTasksInDecreasingOrder(project));

        for(Task task : finishedTasks) {
            Link selfRel = linkTo(RestProjectController.class).slash(projid).slash("tasks").slash(task.getTaskID()).withSelfRel();
            task.add(selfRel);
        }

        return new ResponseEntity<>(finishedTasks, HttpStatus.OK);

    }


    /**
     * This methods gets the list of unfinished tasks from a project
     *
     * @param projid Id of the project to search for finished tasks
     *
     * @return List of finished tasks from the project
     */
    @RequestMapping(value = "unfinished", method = RequestMethod.GET)
    public ResponseEntity<List<Task>> getUnfinishedTasks (@PathVariable int projid) {


        Project project = this.projectService.getProjectById(projid);
        List<Task> unfinishedTasks = new ArrayList<>();

        unfinishedTasks.addAll(taskService.getProjectUnFinishedTasks(project));

        for(Task task : unfinishedTasks) {
            Link selfRel = linkTo(RestProjectController.class).slash(projid).slash("tasks").slash(task.getTaskID()).withSelfRel();
            task.add(selfRel);
        }

        return new ResponseEntity<>(unfinishedTasks, HttpStatus.OK);

    }

}