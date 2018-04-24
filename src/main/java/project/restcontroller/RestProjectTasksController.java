package project.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import project.model.Project;
import project.model.Task;
import project.services.ProjectService;
import project.services.TaskService;

import javax.xml.ws.Response;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("projects/{projid}/tasks/")
public class RestProjectTasksController {

    TaskService taskService;

    ProjectService projectService;

    @Autowired
    public RestProjectTasksController(TaskService taskService, ProjectService projectService) {
        this.taskService = taskService;
        this.projectService = projectService;
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
     * Tis methods gets the list of finished tasks from a project
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

        return new ResponseEntity<>(finishedTasks, HttpStatus.OK);

    }

}