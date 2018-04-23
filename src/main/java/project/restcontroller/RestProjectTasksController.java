package project.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import project.model.Task;
import project.services.TaskService;

@RestController
@RequestMapping("projects/{projid}/tasks/")
public class RestProjectTasksController {

    TaskService taskService;

    @Autowired
    public RestProjectTasksController(TaskService taskService) {
        this.taskService = taskService;
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

        if(deleted == false) {

            response = new ResponseEntity<>(deleted, HttpStatus.CONFLICT);

                  }
       return response;
    }
}
