package project.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.dto.TaskAction;
import project.dto.TaskDTO;
import project.model.Project;
import project.model.Task;
import project.services.ProjectService;
import project.services.TaskService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("projects/{projid}/tasks/")
public class RestProjectTasksController {

    TaskService taskService;

    ProjectService projectService;


    String tasks = "tasks";


    @Autowired
    public RestProjectTasksController(TaskService taskService, ProjectService projectService) {
        this.taskService = taskService;
        this.projectService = projectService;

    }

    /**
     * Creates a Task with a description, associated to a project thats in the URI of the controller.
     * If the project doesn't exist or it's an invalid ID, it will return HttpStatus.NOT_FOUND
     *
     * It can also create a Task using another Task constructor, that includes the parameters:
     *
     * estimatedTaskEffort
     * taskBudget
     * estimatedStartDate
     * taskDeadLine
     */
    @RequestMapping(value = "" , method = RequestMethod.POST)
    public ResponseEntity<Task> createTask(@RequestBody Task taskDTO, @PathVariable int projid) {



            Project projectTask = projectService.getProjectById(projid);
            Task task = taskService.createTask(taskDTO.getDescription(), projectTask);

        if (taskDTO.getEstimatedTaskEffort() <= 0.00000001 && taskDTO.getTaskBudget() <= 0.00000001
             && taskDTO.getEstimatedTaskStartDate() != null && taskDTO.getTaskDeadline() != null) {

                task.setEstimatedTaskEffort(taskDTO.getEstimatedTaskEffort());
                task.setTaskBudget(taskDTO.getTaskBudget());
                task.setEstimatedTaskStartDate(taskDTO.getEstimatedTaskStartDate());
                task.setTaskDeadline(taskDTO.getTaskDeadline());


            }

        for(String action : task.getTaskState().getActions()) {

                Link reference = TaskAction.getLinks(projid, task.getTaskID()).get(action);
                task.add(reference);

            }

        return ResponseEntity.ok().body(task);

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
            for(String action : task.getTaskState().getActions()) {

            Link reference = TaskAction.getLinks(projid, task.getTaskID()).get(action);
            task.add(reference);

            }
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
    public ResponseEntity<List<TaskDTO>> getFinishedTasks (@PathVariable int projid) {

        List<TaskDTO> finishedTasks = new ArrayList<>();

        finishedTasks.addAll(taskService.getProjectFinishedTasksDecOrder(projid));

        for(TaskDTO taskDto : finishedTasks) {
            for(String action : taskDto.getTaskState().getActions()) {
            Link actionLink = TaskAction.getLinks(projid, taskDto.getTaskID()).get(action);
            taskDto.add(actionLink);

             }
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
            for(String action : task.getTaskState().getActions()) {
            Link reference = TaskAction.getLinks(projid, task.getTaskID()).get(action);
            task.add(reference);
            }
        }

        return new ResponseEntity<>(unfinishedTasks, HttpStatus.OK);
    }


    /**
     * This method gets a task by its id
     *
     * @param taskId Task id
     *
     * @return The task found by the id
     */
    @RequestMapping(value = "{taskId}", method = RequestMethod.GET)
    public ResponseEntity<TaskDTO> getTask (@PathVariable String taskId) {

        TaskDTO taskDTO = taskService.getTaskDtoByTaskId(taskId);

        for(String action : taskDTO.getTaskState().getActions()) {
            Link reference = TaskAction.getLinks(taskDTO.getProject().getProjectId(), taskId).get(action);
            taskDTO.add(reference);
        }

        return new ResponseEntity<>(taskDTO, HttpStatus.OK);

    }


    @RequestMapping(value = "{taskId}", method = RequestMethod.PATCH)
    public ResponseEntity<TaskDTO> markTaskAsFinished(@PathVariable String taskId) {

        Task toFinish = taskService.getTaskByTaskID(taskId);

        toFinish.markTaskAsFinished();

        TaskDTO taskDTO = new TaskDTO(toFinish);

        for (String action : taskDTO.getTaskState().getActions()) {
            Link reference = TaskAction.getLinks(taskDTO.getProject().getProjectId(), taskId).get(action);
            taskDTO.add(reference);
        }

        taskService.saveTask(toFinish);

        return new ResponseEntity<>(taskDTO, HttpStatus.OK);

    }
}

