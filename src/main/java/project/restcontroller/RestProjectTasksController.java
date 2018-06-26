package project.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.dto.TaskAction;
import project.dto.TaskDTO;
import project.model.*;
import project.services.ProjectService;
import project.services.TaskService;
import project.services.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("projects/{projid}/tasks/")
public class RestProjectTasksController {

    TaskService taskService;

    ProjectService projectService;

    UserService userService;

    String tasks = "tasks";


    @Autowired
    public RestProjectTasksController(TaskService taskService, ProjectService projectService, UserService userService) {
        this.taskService = taskService;
        this.projectService = projectService;
        this.userService = userService;
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
    @PreAuthorize("hasRole('ROLE_COLLABORATOR')and principal.id==@projectService.getProjectById(#projid).projectManager.userID or hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "" , method = RequestMethod.POST)
    public ResponseEntity<Task> createTask(@RequestBody Task taskDTO, @PathVariable int projid) {



        Project projectTask = projectService.getProjectById(projid);
        Task task = taskService.createTask(taskDTO.getDescription(), projectTask);
        task.setEstimatedTaskStartDate(taskDTO.getEstimatedTaskStartDate());
        task.setTaskDeadline(taskDTO.getTaskDeadline());
        taskService.saveTask(task);

        if (taskDTO.getEstimatedTaskEffort() <= 0.00000001 && taskDTO.getTaskBudget() <= 0.00000001
                && taskDTO.getEstimatedTaskStartDate() != null && taskDTO.getTaskDeadline() != null) {

            task.setEstimatedTaskEffort(taskDTO.getEstimatedTaskEffort());
            task.setTaskBudget(taskDTO.getTaskBudget());



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
    @PreAuthorize("hasRole('ROLE_COLLABORATOR') and @projectService.isUserActiveInProject(@userService.getUserByEmail(principal.username),@projectService.getProjectById(#projid)) " +
            "or hasRole('ROLE_COLLABORATOR') and principal.id==@projectService.getProjectById(#projid).projectManager.userID or hasRole('ROLE_ADMIN')" + "or hasRole('ROLE_DIRECTOR')")
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
     * @return ResponseBody with 202-ACCEPTED if deleted or 406-NOT_ACCEPTABLE if not
     */

    @PreAuthorize("hasRole('ROLE_COLLABORATOR')and principal.id==@projectService.getProjectById(#projid).projectManager.getUserID() or hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "{taskId}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteTask(@PathVariable String taskId, @PathVariable int projid) {

        boolean deleted = taskService.deleteTask(taskId);

        Project project = projectService.getProjectById(projid);

        List<Task> projectTasks = taskService.getProjectTasks(project);

        ResponseEntity<Boolean> response = new ResponseEntity<>(deleted, HttpStatus.NOT_ACCEPTABLE);
        for( Task other : projectTasks) {
            if (other.getTaskID().equals(taskId)) {
                deleted =  taskService.deleteTask(taskId);
            }
        }
        if(deleted){
            response = new ResponseEntity<>(deleted, HttpStatus.ACCEPTED);
        }
        return response;
    }


    /**
     * This method adds a ProjectCollaborator to a Task to become a TaskCollaborator
     *
     * @param projid Project id of the project whose task needs a task collaborator
     * @param taskid Task id of the task to add a project collaborator to its team
     * @param userDTO user whose ID is going to refer the project collaborator to be added as task
     *                collaborator of task with id taskid
     *
     * @return ResponseBody with 200-OK if projectCollab was added to the task team or
     *          403-METHOD_NOT_ALLOWED the projectCollab to be added to the team is not active in Project
     */
    @PreAuthorize ("hasRole('ROLE_COLLABORATOR') and principal.id==@projectService.getProjectById(#projid).projectManager.userID")
    @RequestMapping(value = "{taskid}/addCollab", method = RequestMethod.POST)
    public ResponseEntity<Task> addCollabToTask (@RequestBody User userDTO, @PathVariable int projid, @PathVariable String taskid) {

        Project project = this.projectService.getProjectById(projid);

        User user = userService.getUserByEmail(userDTO.getEmail());

        ResponseEntity<Task> response = new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);

        if(this.projectService.isUserActiveInProject(user, project)) {

            Task task = taskService.getTaskByTaskID(taskid);

            ProjectCollaborator projectCollaborator = projectService.findActiveProjectCollaborator(user,project);

            task.addProjectCollaboratorToTask(projectCollaborator);

            taskService.saveTask(task);

            response = new ResponseEntity<>(task, HttpStatus.OK);

        }
        return response;
    }

    /**
     * This method removes a TaskCollaborator from a Task to become a TaskCollaborator
     *
     * @param projid Project id of the project whose task needs a task collaborator
     * @param taskid Task id of the task to add a project collaborator to its team
     * @param taskCollabDTO user who should be removed from the task
     *
     * @return ResponseBody with 200-OK if taskCollab was removed from the task team or
     *          403-METHOD_NOT_ALLOWED the taskCollaborator was not removed from the task
     */
    @PreAuthorize ("hasRole('ROLE_COLLABORATOR') and principal.id==@projectService.getProjectById(#projid).projectManager.userID")
    @RequestMapping(value = "{taskid}/removeCollab", method = RequestMethod.PATCH)
    public ResponseEntity<Task> removeCollabFromTask (@RequestBody TaskCollaborator taskCollabDTO, @PathVariable int projid, @PathVariable String taskid) {

        ResponseEntity<Task> response = new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);

        Task task = taskService.getTaskByTaskID(taskid);

        if(task.removeProjectCollaboratorFromTask(taskCollabDTO.getProjCollaborator())) {
            taskService.saveTask(task);
            response = new ResponseEntity<>(task, HttpStatus.OK);
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
    @PreAuthorize("hasRole('ROLE_COLLABORATOR') and @projectService.isUserActiveInProject(@userService.getUserByEmail(principal.username),@projectService.getProjectById(#projid)) " +
            "or hasRole('ROLE_COLLABORATOR') and principal.id==@projectService.getProjectById(#projid).projectManager.userID or hasRole('ROLE_ADMIN')" + "or hasRole('ROLE_DIRECTOR')")
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
    @PreAuthorize("hasRole('ROLE_COLLABORATOR') and @projectService.isUserActiveInProject(@userService.getUserByEmail(principal.username),@projectService.getProjectById(#projid)) " +
            "or hasRole('ROLE_COLLABORATOR') and principal.id==@projectService.getProjectById(#projid).projectManager.userID or hasRole('ROLE_ADMIN')" + "or hasRole('ROLE_DIRECTOR')")
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
     * This methods gets the list of cancelled tasks from a project
     *
     * @param projid Id of the project to search for cancelled tasks
     *
     * @return List of cancelled tasks from the project
     */
    @PreAuthorize("hasRole('ROLE_COLLABORATOR') and @projectService.isUserActiveInProject(@userService.getUserByEmail(principal.username),@projectService.getProjectById(#projid)) " +
            "or hasRole('ROLE_COLLABORATOR') and principal.id==@projectService.getProjectById(#projid).projectManager.userID or hasRole('ROLE_ADMIN')" + "or hasRole('ROLE_DIRECTOR')")
    @RequestMapping(value = "cancelled", method = RequestMethod.GET)
    public ResponseEntity<List<Task>> getCancelledTasks (@PathVariable int projid) {


        Project project = this.projectService.getProjectById(projid);
        List<Task> cancelledTasks = new ArrayList<>();

        cancelledTasks.addAll(taskService.getProjectCancelledTasks(project));

        for(Task task : cancelledTasks) {
            for(String action : task.getTaskState().getActions()) {
                Link reference = TaskAction.getLinks(projid, task.getTaskID()).get(action);
                task.add(reference);
            }
        }

        return new ResponseEntity<>(cancelledTasks, HttpStatus.OK);
    }


    /**
     * This method gets a task by its id
     *
     * @param taskId Task id
     *
     * @return The task found by the id
     */
    @PreAuthorize("hasRole('ROLE_COLLABORATOR') and @projectService.isUserActiveInProject(@userService.getUserByEmail(principal.username),@projectService.getProjectById(#projid)) " +
            "or hasRole('ROLE_COLLABORATOR') and principal.id==projectService.getProjectById(#projid).projectManager.userID or hasRole('ROLE_ADMIN')" + "or hasRole('ROLE_DIRECTOR')")
    @RequestMapping(value = "{taskId}", method = RequestMethod.GET)
    public ResponseEntity<TaskDTO> getTask (@PathVariable String taskId) {

        TaskDTO taskDTO = taskService.getTaskDtoByTaskId(taskId);

        for(String action : taskDTO.getTaskState().getActions()) {
            Link reference = TaskAction.getLinks(taskDTO.getProject().getProjectId(), taskId).get(action);
            taskDTO.add(reference);
        }

        return new ResponseEntity<>(taskDTO, HttpStatus.OK);

    }


    /**
     * This method gets a list of task dependencies by their ID
     *
     * @param taskId Task id
     *
     * @return The task dependenciues found by ID
     */
    @PreAuthorize("hasRole('ROLE_COLLABORATOR') and @projectService.isUserActiveInProject(@userService.getUserByEmail(principal.username),@projectService.getProjectById(#projid)) " +
            "or hasRole('ROLE_COLLABORATOR') and principal.id==projectService.getProjectById(#projid).projectManager.userID or hasRole('ROLE_ADMIN')" + "or hasRole('ROLE_DIRECTOR')")
    @RequestMapping(value = "{taskId}/dependencies", method = RequestMethod.GET)
    public ResponseEntity<List<Task>> getTaskDependencies (@PathVariable String taskId) {

        Task task = taskService.getTaskByTaskID(taskId);
        List<Task> taskDependencies = task.getTaskDependency();

        for(Task dependency : taskDependencies) {
            for(String action : dependency.getTaskState().getActions()) {
                Link reference = TaskAction.getLinks(dependency.getProject().getProjectId(), dependency.getTaskID()).get(action);
                dependency.add(reference);
            }
        }


        return new ResponseEntity<>(taskDependencies, HttpStatus.OK);

    }


    @PreAuthorize("hasRole('ROLE_COLLABORATOR') and @taskService.getTaskByTaskID(#taskId).isProjectCollaboratorActiveInTaskTeam(@projectService.findActiveProjectCollaborator(@userService.getUserByID(principal.id), @projectService.getProjectById(#projid))) " +
            "or hasRole('ROLE_COLLABORATOR') and principal.id==@projectService.getProjectById(#projid).projectManager.userID or hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "{taskId}", method = RequestMethod.PATCH)
    public ResponseEntity<TaskDTO> markTaskAsFinished(@PathVariable String taskId, @PathVariable String projid) {

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

    /**
     * This methods gets the list of not started tasks from a project
     *
     * @param projid Id of the project to search for not started tasks
     *
     * @return List of not started tasks from the project
     */
    @PreAuthorize("hasRole('ROLE_COLLABORATOR') and @projectService.isUserActiveInProject(@userService.getUserByEmail(principal.username),@projectService.getProjectById(#projid)) " +
            "or hasRole('ROLE_COLLABORATOR') and principal.id==@projectService.getProjectById(#projid).projectManager.userID or hasRole('ROLE_ADMIN')" + "or hasRole('ROLE_DIRECTOR')")
    @RequestMapping(value = "notstarted", method = RequestMethod.GET)
    public ResponseEntity<List<Task>> getNotStartedTasks (@PathVariable int projid) {


        Project project = this.projectService.getProjectById(projid);
        List<Task> notStartedTasks = new ArrayList<>();

        notStartedTasks.addAll(taskService.getProjectUnstartedTasks(project));

        for(Task task : notStartedTasks) {
            for(String action : task.getTaskState().getActions()) {
                Link reference = TaskAction.getLinks(projid, task.getTaskID()).get(action);
                task.add(reference);
            }
        }

        return new ResponseEntity<>(notStartedTasks, HttpStatus.OK);
    }


    /**
     * This methods gets the list of expired tasks from a project
     *
     * @param projid Id of the project to search for expired tasks
     *
     * @return List of expired from the project
     */
    @PreAuthorize("hasRole('ROLE_COLLABORATOR') and @projectService.isUserActiveInProject(@userService.getUserByEmail(principal.username),@projectService.getProjectById(#projid)) " +
            "or hasRole('ROLE_COLLABORATOR') and principal.id==@projectService.getProjectById(#projid).projectManager.userID or hasRole('ROLE_ADMIN')" + "or hasRole('ROLE_DIRECTOR')")
    @RequestMapping(value = "expired", method = RequestMethod.GET)
    public ResponseEntity<List<Task>> getExpiredTasks (@PathVariable int projid) {


        Project project = this.projectService.getProjectById(projid);
        List<Task> expiredTasks = new ArrayList<>();

        expiredTasks.addAll(taskService.getProjectExpiredTasks(project));

        for(Task task : expiredTasks) {
            for(String action : task.getTaskState().getActions()) {
                Link reference = TaskAction.getLinks(projid, task.getTaskID()).get(action);
                task.add(reference);
            }
        }

        return new ResponseEntity<>(expiredTasks, HttpStatus.OK);
    }


    /**
     * This methods gets the list of all tasks from a project
     *
     * @param projid Id of the project to search for all tasks
     *
     * @return List of all tasks from the project
     */
    @PreAuthorize("hasRole('ROLE_COLLABORATOR') and @projectService.isUserActiveInProject(@userService.getUserByEmail(principal.username),@projectService.getProjectById(#projid)) " +
            "or hasRole('ROLE_COLLABORATOR') and principal.id==@projectService.getProjectById(#projid).projectManager.userID or hasRole('ROLE_ADMIN')" + "or hasRole('ROLE_DIRECTOR')")
    @RequestMapping(value = "all", method = RequestMethod.GET)
    public ResponseEntity<List<TaskDTO>> getAllTasks (@PathVariable int projid) {

        Project project = projectService.getProjectById(projid);

        List<Task> allTasks = new ArrayList<>();

        allTasks.addAll(taskService.getProjectTasks(project));

        List<TaskDTO> allProjectTasksDTO = new ArrayList<>();

        for(Task other : allTasks) {
            TaskDTO taskDTO = new TaskDTO(other);
            allProjectTasksDTO.add(taskDTO);
        }

        for(TaskDTO taskDto : allProjectTasksDTO) {
            for(String action : taskDto.getTaskState().getActions()) {
                Link actionLink = TaskAction.getLinks(projid, taskDto.getTaskID()).get(action);
                taskDto.add(actionLink);

            }
        }

        return new ResponseEntity<>(allProjectTasksDTO, HttpStatus.OK);

    }

    /**
     * This methods gets the list of active task collaborators in a specific task from a project
     *
     * @param projid Id of the project to search for a task
     * @param taskid Id of the task to search for active task collaborators
     *
     * @return List of all active task collaborators in a task from the project
     */
    @PreAuthorize("hasRole('ROLE_COLLABORATOR') and @projectService.isUserActiveInProject(@userService.getUserByEmail(principal.username),@projectService.getProjectById(#projid)) " +
            "or hasRole('ROLE_COLLABORATOR') and principal.id==@projectService.getProjectById(#projid).projectManager.userID or hasRole('ROLE_ADMIN')" + "or hasRole('ROLE_DIRECTOR')")
    @RequestMapping(value = "{taskid}/activeTeam", method = RequestMethod.GET)
    public ResponseEntity<List<TaskCollaborator>> getActiveTaskTeam (@PathVariable int projid, @PathVariable String taskid) {

        projectService.getProjectById(projid);
        Task task = taskService.getTaskByTaskID(taskid);

        List<TaskCollaborator> team = task.getTaskTeam();
        List<TaskCollaborator> activeTeam = new ArrayList<>();


        for (TaskCollaborator other : team) {
            if (other.isTaskCollaboratorActiveInTask()) {

                activeTeam.add(other);
            }
        }


        return new ResponseEntity<>(activeTeam, HttpStatus.OK);
    }


    /**
     * This method returns the list of Project collaborators that are not assigned to a certain task
     * and is not the project Manager of that project
     * @param projid
     * @param taskid
     * @return
     */
    @PreAuthorize ("hasRole('ROLE_COLLABORATOR') and principal.id==@projectService.getProjectById(#projid).projectManager.userID")
    @RequestMapping(value = "{taskid}/collabsAvailableForTask", method = RequestMethod.GET)
    public ResponseEntity<List<ProjectCollaborator>> getProjectTeamNotAddedToTask(@PathVariable int projid,  @PathVariable String taskid) {

        Project project = projectService.getProjectById(projid);
        Task task = taskService.getTaskByTaskID(taskid);

        List <ProjectCollaborator> projCollabs = projectService.getActiveProjectTeam(project);
        List<ProjectCollaborator> activeTeam = new ArrayList<>();

        ResponseEntity <List<ProjectCollaborator>> response ;

        for (ProjectCollaborator other : projCollabs) {
            if (!task.isProjectCollaboratorActiveInTaskTeam(other) && !project.isProjectManager(other.getUserFromProjectCollaborator())) {
                activeTeam.add(other);
            }
        }

        if(activeTeam.isEmpty()){

           response  = new ResponseEntity<>(activeTeam, HttpStatus.EXPECTATION_FAILED);

        }   else {
              response  = new ResponseEntity<>(activeTeam, HttpStatus.OK);
        }

        return response;
    }

    /**
     * This method returns the list of Project collaborators that are not assigned to any task of that Project
     * @param projid
     * @return
     */
    @PreAuthorize ("hasRole('ROLE_COLLABORATOR') and principal.id==@projectService.getProjectById(#projid).projectManager.userID")
    @RequestMapping(value = "collabsAvailableForTask", method = RequestMethod.GET)
    public ResponseEntity<List<ProjectCollaborator>> getProjectTeamNotAddedToAnyTaskOfProject(@PathVariable int projid) {

        Project project = projectService.getProjectById(projid);

        List <ProjectCollaborator> projCollabs = projectService.getActiveProjectTeam(project);
        List<ProjectCollaborator> unassignedTeam = new ArrayList<>();

        ResponseEntity <List<ProjectCollaborator>> response ;

        for (ProjectCollaborator other : projCollabs) {
            if (!taskService.isCollaboratorActiveOnAnyTask(other)) {
                unassignedTeam.add(other);
            }
        }
            response  = new ResponseEntity<>(unassignedTeam, HttpStatus.OK);

        return response;
    }

}

