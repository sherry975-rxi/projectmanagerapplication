package project.dto;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.hateoas.Link;
import project.model.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;


public class taskDTOTests {

    Project project;
    TaskDTO taskDTO;
    TaskDTO taskDTO1;
    TaskDTO taskDTO2;
    Task task;
    Task dependentTask;
    User user;
    Calendar date;
    ProjectCollaborator projectCollaborator;
    TaskCollaborator taskCollaborator;
    Report report;
    List<Task> expected;
    TaskTeamRequest request;
    List<TaskTeamRequest> taskTeamRequests;
    private List<String> actions;


    @Before
    public void setUp() {

        user = new User("José", "jp@gmail.com", "4455", "colaborador", "91111111");
        project = new Project("Project Managment", "Manage Team Tasks", user);
        project.setStartdate(Calendar.getInstance());
        dependentTask = new Task("dependent", project);
        projectCollaborator = new ProjectCollaborator(user, 3);
        taskCollaborator = new TaskCollaborator(projectCollaborator);
        report = new Report(taskCollaborator, Calendar.getInstance());
        expected = new ArrayList<>();
        expected.add(dependentTask);
        request = new TaskTeamRequest();
        taskTeamRequests = new ArrayList<>();
        taskTeamRequests.add(request);
        actions = new ArrayList<>();
        taskDTO1 = new TaskDTO();


    }

    @After
    public void tearDown() {
        user = null;
        project = null;
        dependentTask = null;
        projectCollaborator = null;
        taskCollaborator = null;
        report = null;
        expected = null;
        request = null;
        taskTeamRequests = null;
        actions = null;
        taskDTO1 = null;
    }

    /**
     * Tests the get description of the taskDTO class
     */
    @Test
    public void shouldGetDescription() {

        //GIVEN a task and all its parameters are set
        task = new Task("deded", project);
        task.setTaskID("1");
        task.setStartDateAndState(date);
        task.setFinishDate(date);
        task.addProjectCollaboratorToTask(projectCollaborator);
        report.setTask(task);
        task.setEstimatedTaskEffort(1);
        task.setTaskBudget(1);
        task.setTaskDeadline(date);
        task.setTaskDependency(expected);
        task.updateStartDateIntervalAndState(1);
        task.updateDeadlineIntervalAndState(2);
        task.updateDeadlineIntervalAndState(10);
        task.setPendingTaskTeamRequests(taskTeamRequests);
        task.setCurrentState(StateEnum.PLANNED);

        //WHEN a TaskDTO is instantiated from another task
        taskDTO = new TaskDTO(task);

        //THEN All parameters' values can be retrieved in the TaskDto and correspond to the respective parameter values
        // in the Task
        assertEquals(task.getDescription(), taskDTO.getDescription());
        assertEquals(task.getCreationDate(), taskDTO.getCreationDate());
    }

    /**
     * Tests the get description of the taskDTO class
     */
    @Test
    public void shouldGetCreationDate() {

        //GIVEN a task and all its parameters are set
        task = new Task("deded", project);
        task.setTaskID("1");
        task.setStartDateAndState(date);
        task.setFinishDate(date);
        task.addProjectCollaboratorToTask(projectCollaborator);
        report.setTask(task);
        task.setEstimatedTaskEffort(1);
        task.setTaskBudget(1);
        task.setTaskDeadline(date);
        task.setTaskDependency(expected);
        task.updateStartDateIntervalAndState(1);
        task.updateDeadlineIntervalAndState(2);
        task.updateDeadlineIntervalAndState(10);
        task.setPendingTaskTeamRequests(taskTeamRequests);

        //WHEN a TaskDTO is instantiated from another task
        taskDTO = new TaskDTO(task);

        //THEN All parameters' values can be retrieved in the TaskDto and correspond to the respective parameter values
        // in the Task
        assertEquals(task.getCreationDate(), taskDTO.getCreationDate());
    }

    /**
     * Tests the get taskID of the taskDTO class
     */
    @Test
    public void shouldGetTaskId() {

        //GIVEN a task and all its parameters are set
        task = new Task("deded", project);
        task.setTaskID("1");
        task.setStartDateAndState(date);
        task.setFinishDate(date);
        task.addProjectCollaboratorToTask(projectCollaborator);
        report.setTask(task);
        task.setEstimatedTaskEffort(1);
        task.setTaskBudget(1);
        task.setTaskDeadline(date);
        task.setTaskDependency(expected);
        task.updateStartDateIntervalAndState(1);
        task.updateDeadlineIntervalAndState(2);
        task.updateDeadlineIntervalAndState(10);
        task.setPendingTaskTeamRequests(taskTeamRequests);

        //WHEN a TaskDTO is instantiated from another task
        taskDTO = new TaskDTO(task);

        //THEN All parameters' values can be retrieved in the TaskDto and correspond to the respective parameter values
        // in the Task
        assertEquals(task.getTaskID(), taskDTO.getTaskID());

    }

    /**
     * Tests the get StartDate of the taskDTO class
     */
    @Test
    public void shouldGetStartDate() {

        //GIVEN a task and all its parameters are set
        task = new Task("deded", project);
        task.setTaskID("1");
        task.setStartDateAndState(date);
        task.setFinishDate(date);
        task.addProjectCollaboratorToTask(projectCollaborator);
        report.setTask(task);
        task.setEstimatedTaskEffort(1);
        task.setTaskBudget(1);
        task.setTaskDeadline(date);
        task.setTaskDependency(expected);
        task.updateStartDateIntervalAndState(1);
        task.updateDeadlineIntervalAndState(2);
        task.updateDeadlineIntervalAndState(10);
        task.setPendingTaskTeamRequests(taskTeamRequests);

        //WHEN a TaskDTO is instantiated from another task
        taskDTO = new TaskDTO(task);

        //THEN All parameters' values can be retrieved in the TaskDto and correspond to the respective parameter values
        // in the Task

        assertEquals(date, taskDTO.getStartDate());
    }

    /**
     * Tests the get FinishDate of the taskDTO class
     */
    @Test
    public void shouldGetFinishDate() {

        //GIVEN a task and all its parameters are set
        task = new Task("deded", project);
        task.setTaskID("1");
        task.setStartDateAndState(date);
        task.setFinishDate(date);
        task.addProjectCollaboratorToTask(projectCollaborator);
        report.setTask(task);
        task.setEstimatedTaskEffort(1);
        task.setTaskBudget(1);
        task.setTaskDeadline(date);
        task.setTaskDependency(expected);
        task.updateStartDateIntervalAndState(1);
        task.updateDeadlineIntervalAndState(2);
        task.updateDeadlineIntervalAndState(10);
        task.setPendingTaskTeamRequests(taskTeamRequests);

        //WHEN a TaskDTO is instantiated from another task
        taskDTO = new TaskDTO(task);

        //THEN All parameters' values can be retrieved in the TaskDto and correspond to the respective parameter values
        // in the Task
        assertEquals(date, taskDTO.getFinishDate());
    }

    /**
     * Tests the get TaskTeam of the taskDTO class
     */
    @Test
    public void shouldGetTaskTeam() {

        //GIVEN a task and all its parameters are set
        task = new Task("deded", project);
        task.setTaskID("1");
        task.setStartDateAndState(date);
        task.setFinishDate(date);
        task.addProjectCollaboratorToTask(projectCollaborator);
        report.setTask(task);
        task.setEstimatedTaskEffort(1);
        task.setTaskBudget(1);
        task.setTaskDeadline(date);
        task.setTaskDependency(expected);
        task.updateStartDateIntervalAndState(1);
        task.updateDeadlineIntervalAndState(2);
        task.updateDeadlineIntervalAndState(10);
        task.setPendingTaskTeamRequests(taskTeamRequests);

        //WHEN a TaskDTO is instantiated from another task
        taskDTO = new TaskDTO(task);

        //THEN All parameters' values can be retrieved in the TaskDto and correspond to the respective parameter values
        // in the Task
        assertEquals(task.getTaskTeam(), taskDTO.getTaskTeam());
    }

    /**
     * Tests the get Reports from Task of the taskDTO class
     */
    @Test
    public void shouldGetReports() {

        //GIVEN a task and all its parameters are set
        task = new Task("deded", project);
        task.setTaskID("1");
        task.setStartDateAndState(date);
        task.setFinishDate(date);
        task.addProjectCollaboratorToTask(projectCollaborator);
        report.setTask(task);
        task.setEstimatedTaskEffort(1);
        task.setTaskBudget(1);
        task.setTaskDeadline(date);
        task.setTaskDependency(expected);
        task.updateStartDateIntervalAndState(1);
        task.updateDeadlineIntervalAndState(2);
        task.updateDeadlineIntervalAndState(10);
        task.setPendingTaskTeamRequests(taskTeamRequests);

        //WHEN a TaskDTO is instantiated from another task
        taskDTO = new TaskDTO(task);

        //THEN All parameters' values can be retrieved in the TaskDto and correspond to the respective parameter values
        // in the Task
        assertEquals(task.getReports(), taskDTO.getReports());
    }

    /**
     * Tests the get Cancel Date of the taskDTO class
     */
    @Test
    public void shouldGetCancelDate() {

        //GIVEN a task and all its parameters are set
        task = new Task("deded", project);
        task.setTaskID("1");
        task.setStartDateAndState(date);
        task.setFinishDate(date);
        task.addProjectCollaboratorToTask(projectCollaborator);
        report.setTask(task);
        task.setEstimatedTaskEffort(1);
        task.setTaskBudget(1);
        task.setTaskDeadline(date);
        task.setTaskDependency(expected);
        task.updateStartDateIntervalAndState(1);
        task.updateDeadlineIntervalAndState(2);
        task.updateDeadlineIntervalAndState(10);
        task.setPendingTaskTeamRequests(taskTeamRequests);

        //WHEN a TaskDTO is instantiated from another task
        taskDTO = new TaskDTO(task);

        //THEN All parameters' values can be retrieved in the TaskDto and correspond to the respective parameter values
        // in the Task
        assertEquals(task.getCancelDate(),taskDTO.getCancelDate());
    }

    /**
     * Tests the get Estimated Task Effort of the taskDTO class
     */
    @Test
    public void shouldGetEstimatedTaskEffort() {

        //GIVEN a task and all its parameters are set
        task = new Task("deded", project);
        task.setTaskID("1");
        task.setStartDateAndState(date);
        task.setFinishDate(date);
        task.addProjectCollaboratorToTask(projectCollaborator);
        report.setTask(task);
        task.setEstimatedTaskEffort(1);
        task.setTaskBudget(1);
        task.setTaskDeadline(date);
        task.setTaskDependency(expected);
        task.updateStartDateIntervalAndState(1);
        task.updateDeadlineIntervalAndState(2);
        task.updateDeadlineIntervalAndState(10);
        task.setPendingTaskTeamRequests(taskTeamRequests);

        //WHEN a TaskDTO is instantiated from another task
        taskDTO = new TaskDTO(task);

        //THEN All parameters' values can be retrieved in the TaskDto and correspond to the respective parameter values
        // in the Task
        assertEquals(task.getEstimatedTaskEffort(), taskDTO.getEstimatedTaskEffort(), 0.01);
    }

    /**
     * Tests the get Task Budget of the taskDTO class
     */
    @Test
    public void shouldGetTaskBudget() {

        //GIVEN a task and all its parameters are set
        task = new Task("deded", project);
        task.setTaskID("1");
        task.setStartDateAndState(date);
        task.setFinishDate(date);
        task.addProjectCollaboratorToTask(projectCollaborator);
        report.setTask(task);
        task.setEstimatedTaskEffort(1);
        task.setTaskBudget(1);
        task.setTaskDeadline(date);
        task.setTaskDependency(expected);
        task.updateStartDateIntervalAndState(1);
        task.updateDeadlineIntervalAndState(2);
        task.updateDeadlineIntervalAndState(10);
        task.setPendingTaskTeamRequests(taskTeamRequests);

        //WHEN a TaskDTO is instantiated from another task
        taskDTO = new TaskDTO(task);

        //THEN All parameters' values can be retrieved in the TaskDto and correspond to the respective parameter values
        // in the Task
        assertEquals(task.getTaskBudget(), taskDTO.getTaskBudget(), 0.01);
    }

    /**
     * Tests the get TaskDeadLine of the taskDTO class
     */
    @Test
    public void shouldGetTaskDeadLine() {

        //GIVEN a task and all its parameters are set
        task = new Task("deded", project);
        task.setTaskID("1");
        task.setStartDateAndState(date);
        task.setFinishDate(date);
        task.addProjectCollaboratorToTask(projectCollaborator);
        report.setTask(task);
        task.setEstimatedTaskEffort(1);
        task.setTaskBudget(1);
        task.setTaskDeadline(date);
        task.setTaskDependency(expected);
        task.updateStartDateIntervalAndState(1);
        task.updateDeadlineIntervalAndState(2);
        task.updateDeadlineIntervalAndState(10);
        task.setPendingTaskTeamRequests(taskTeamRequests);

        //WHEN a TaskDTO is instantiated from another task
        taskDTO = new TaskDTO(task);

        //THEN All parameters' values can be retrieved in the TaskDto and correspond to the respective parameter values
        // in the Task
        assertEquals(task.getTaskDeadline(), task.getTaskDeadline());
    }

    /**
     * Tests the get TaskDependency of the taskDTO class
     */
    @Test
    public void shouldGetTaskDependency() {

        //GIVEN a task and all its parameters are set
        task = new Task("deded", project);
        task.setTaskID("1");
        task.setStartDateAndState(date);
        task.setFinishDate(date);
        task.addProjectCollaboratorToTask(projectCollaborator);
        report.setTask(task);
        task.setEstimatedTaskEffort(1);
        task.setTaskBudget(1);
        task.setTaskDeadline(date);
        task.setTaskDependency(expected);
        task.updateStartDateIntervalAndState(1);
        task.updateDeadlineIntervalAndState(2);
        task.updateDeadlineIntervalAndState(10);
        task.setPendingTaskTeamRequests(taskTeamRequests);

        //WHEN a TaskDTO is instantiated from another task
        taskDTO = new TaskDTO(task);

        //THEN All parameters' values can be retrieved in the TaskDto and correspond to the respective parameter values
        // in the Task
        assertEquals(expected, taskDTO.getTaskDependency());
    }

    /**
     * Tests the get StartDateInterval of the taskDTO class
     */
    @Test
    public void shouldGetStartDateInterval() {

        //GIVEN a task and all its parameters are set
        task = new Task("deded", project);
        task.setTaskID("1");
        task.setStartDateAndState(date);
        task.setFinishDate(date);
        task.addProjectCollaboratorToTask(projectCollaborator);
        report.setTask(task);
        task.setEstimatedTaskEffort(1);
        task.setTaskBudget(1);
        task.setTaskDeadline(date);
        task.setTaskDependency(expected);
        task.updateStartDateIntervalAndState(1);
        task.updateDeadlineIntervalAndState(2);
        task.updateDeadlineIntervalAndState(10);
        task.setPendingTaskTeamRequests(taskTeamRequests);

        //WHEN a TaskDTO is instantiated from another task
        taskDTO = new TaskDTO(task);

        //THEN All parameters' values can be retrieved in the TaskDto and correspond to the respective parameter values
        // in the Task
        assertEquals(task.getStartDateInterval(), taskDTO.getStartDateInterval());
    }

    /**
     * Tests the get DeadLine Interval of the taskDTO class
     */
    @Test
    public void shouldGetDeadLineInterval() {

        //GIVEN a task and all its parameters are set
        task = new Task("deded", project);
        task.setTaskID("1");
        task.setStartDateAndState(date);
        task.setFinishDate(date);
        task.addProjectCollaboratorToTask(projectCollaborator);
        report.setTask(task);
        task.setEstimatedTaskEffort(1);
        task.setTaskBudget(1);
        task.setTaskDeadline(date);
        task.setTaskDependency(expected);
        task.updateStartDateIntervalAndState(1);
        task.updateDeadlineIntervalAndState(2);
        task.updateDeadlineIntervalAndState(10);
        task.setPendingTaskTeamRequests(taskTeamRequests);

        //WHEN a TaskDTO is instantiated from another task
        taskDTO = new TaskDTO(task);

        //THEN All parameters' values can be retrieved in the TaskDto and correspond to the respective parameter values
        // in the Task
        assertEquals(taskDTO.getDeadlineInterval(), taskDTO.getDeadlineInterval());
    }

    /**
     * Tests the get TskState of the taskDTO class
     */
    @Test
    public void shouldGetTaskState() {

        //GIVEN a task and all its parameters are set
        task = new Task("deded", project);
        task.setTaskID("1");
        task.setStartDateAndState(date);
        task.setFinishDate(date);
        task.addProjectCollaboratorToTask(projectCollaborator);
        report.setTask(task);
        task.setEstimatedTaskEffort(1);
        task.setTaskBudget(1);
        task.setTaskDeadline(date);
        task.setTaskDependency(expected);
        task.updateStartDateIntervalAndState(1);
        task.updateDeadlineIntervalAndState(2);
        task.updateDeadlineIntervalAndState(10);
        task.setPendingTaskTeamRequests(taskTeamRequests);

        //WHEN a TaskDTO is instantiated from another task
        taskDTO = new TaskDTO(task);

        //THEN All parameters' values can be retrieved in the TaskDto and correspond to the respective parameter values
        // in the Task
        assertEquals(task.getTaskState(), taskDTO.getTaskState());
    }

    /**
     * Tests the get Task current state of the taskDTO class
     */
    @Test
    public void shouldGetTaskCurrentState() {

        //GIVEN a task and all its parameters are set
        task = new Task("deded", project);
        task.setTaskID("1");
        task.setStartDateAndState(date);
        task.setFinishDate(date);
        task.addProjectCollaboratorToTask(projectCollaborator);
        report.setTask(task);
        task.setEstimatedTaskEffort(1);
        task.setTaskBudget(1);
        task.setTaskDeadline(date);
        task.setTaskDependency(expected);
        task.updateStartDateIntervalAndState(1);
        task.updateDeadlineIntervalAndState(2);
        task.updateDeadlineIntervalAndState(10);
        task.setPendingTaskTeamRequests(taskTeamRequests);

        //WHEN a TaskDTO is instantiated from another task
        taskDTO = new TaskDTO(task);

        //THEN All parameters' values can be retrieved in the TaskDto and correspond to the respective parameter values
        // in the Task
        assertEquals(StateEnum.PLANNED, task.getCurrentState());
    }

    /**
     * Tests the get the Project that matches that Task of the taskDTO class
     */
    @Test
    public void shouldGetTaskProject() {

        //GIVEN a task and all its parameters are set
        task = new Task("deded", project);
        task.setTaskID("1");
        task.setStartDateAndState(date);
        task.setFinishDate(date);
        task.addProjectCollaboratorToTask(projectCollaborator);
        report.setTask(task);
        task.setEstimatedTaskEffort(1);
        task.setTaskBudget(1);
        task.setTaskDeadline(date);
        task.setTaskDependency(expected);
        task.updateStartDateIntervalAndState(1);
        task.updateDeadlineIntervalAndState(2);
        task.updateDeadlineIntervalAndState(10);
        task.setPendingTaskTeamRequests(taskTeamRequests);

        //WHEN a TaskDTO is instantiated from another task
        taskDTO = new TaskDTO(task);

        //THEN All parameters' values can be retrieved in the TaskDto and correspond to the respective parameter values
        // in the Task
        assertEquals(task.getProject(), taskDTO.getProject());
    }

    /**
     * Tests the get PendingTaskTeamRequests of the taskDTO class
     */
    @Test
    public void shouldGetPendingTaskTeamRequests() {

        //GIVEN a task and all its parameters are set
        task = new Task("deded", project);
        task.setTaskID("1");
        task.setStartDateAndState(date);
        task.setFinishDate(date);
        task.addProjectCollaboratorToTask(projectCollaborator);
        report.setTask(task);
        task.setEstimatedTaskEffort(1);
        task.setTaskBudget(1);
        task.setTaskDeadline(date);
        task.setTaskDependency(expected);
        task.updateStartDateIntervalAndState(1);
        task.updateDeadlineIntervalAndState(2);
        task.updateDeadlineIntervalAndState(10);
        task.setPendingTaskTeamRequests(taskTeamRequests);

        //WHEN a TaskDTO is instantiated from another task
        taskDTO = new TaskDTO(task);

        //THEN All parameters' values can be retrieved in the TaskDto and correspond to the respective parameter values
        // in the Task
        assertTrue(task.getPendingTaskTeamRequests().equals(taskDTO.getPendingTaskTeamRequests()));
    }

    /**
     * Tests the get Task Actions of the taskDTO class
     */
    @Test
    public void shouldGetTaskActions() {

        //GIVEN a task and all its parameters are set
        task = new Task("deded", project);
        task.setTaskID("1");
        task.setStartDateAndState(date);
        task.setFinishDate(date);
        task.addProjectCollaboratorToTask(projectCollaborator);
        report.setTask(task);
        task.setEstimatedTaskEffort(1);
        task.setTaskBudget(1);
        task.setTaskDeadline(date);
        task.setTaskDependency(expected);
        task.updateStartDateIntervalAndState(1);
        task.updateDeadlineIntervalAndState(2);
        task.updateDeadlineIntervalAndState(10);
        task.setPendingTaskTeamRequests(taskTeamRequests);

        //WHEN a TaskDTO is instantiated from another task
        taskDTO = new TaskDTO(task);

        //THEN All parameters' values can be retrieved in the TaskDto and correspond to the respective parameter values
        // in the Task
        assertEquals(actions, taskDTO.getActions());
    }


    /**
     * Tests the get description of the taskDTO class
     */
    @Test
    public void shouldGetEstimatedTaskStartDate() {

        //GIVEN a task and all its parameters are set
        task = new Task("deded", project);
        task.setTaskID("1");
        task.setStartDateAndState(date);
        task.setFinishDate(date);
        task.addProjectCollaboratorToTask(projectCollaborator);
        report.setTask(task);
        task.setEstimatedTaskEffort(1);
        task.setTaskBudget(1);
        task.setTaskDeadline(date);
        task.setTaskDependency(expected);
        task.updateStartDateIntervalAndState(1);
        task.updateDeadlineIntervalAndState(2);
        task.updateDeadlineIntervalAndState(10);
        task.setPendingTaskTeamRequests(taskTeamRequests);

        //WHEN a TaskDTO is instantiated from another task
        taskDTO = new TaskDTO(task);

        //THEN All parameters' values can be retrieved in the TaskDto and correspond to the respective parameter values
        // in the Task

        assertEquals(task.getEstimatedTaskStartDate(), taskDTO.getEstimatedTaskStartDate());

    }

    /**
     * Test method that the hashCode of a taskDto created from a Task is the same
     */
    @Test
    public void shouldHaveSameHashCode() {


        //GIVEN a task and all its parameters are set
        task = new Task("deded", project);
        task.setTaskID("1");

        //WHEN a TaskDTO is instantiated from another task
        taskDTO = new TaskDTO(task);


        int result = 3 * 31 + task.getTaskID().hashCode();
        assertEquals(task.hashCode(), result);
    }

    /**
     * Test method if a taskDto created from a Task has the same hashCode
     */
    @Test
    public void shouldHaveDifferentHashCode() {


        //GIVEN a task and all its parameters are set
        task = new Task("deded", project);
        task.setTaskID("1");

        //WHEN a TaskDTO is instantiated from another task
        taskDTO = new TaskDTO(task);

        assertFalse(task.hashCode() == taskDTO.hashCode());
    }

    /**
     * Test if the same taskDto is compared, it is equal
     */
    @Test
    public void shouldObjectsBeEquals() {

        //GIVEN a task and all its parameters are set
        task = new Task("deded", project);
        task.setTaskID("1");

        //WHEN a TaskDTO is instantiated from another task
        taskDTO = new TaskDTO(task);


        //THEN if the same task is compared, it is equal
        assertTrue(taskDTO.equals(taskDTO));// same object
    }

    /**
     * Test if a task created from another not null task, is not equal to a null task
     */
    @Test
    public void shouldNOTObjectsBeEqualsIfObjectIsNUll() {

        //GIVEN a task and all its parameters are set
        task = new Task("deded", project);
        task.setTaskID("1");

        //WHEN a TaskDTO is instantiated from another task
        taskDTO = new TaskDTO(task);
        Task testTask = null;

        //THEN a task created from another not null task, is not equal to a null task
        assertFalse(taskDTO.equals(testTask));// null object
    }


    /**
     * Test if two tasks are equal even if they are a copy from each other
     */
    @Test
    public void shouldNOTObjectsBeEqualsEvenIfTheyAreCopyOfEachOther() {

        //GIVEN a task and all its parameters are set
        task = new Task("deded", project);
        task.setTaskID("1");

        //WHEN a TaskDTO is instantiated from another task
        taskDTO = new TaskDTO(task);

        //THEN they are not equal
        assertFalse(taskDTO.equals(task));// same counter
    }

    /**
     * Test method for {@link project.model.Task#equals(java.lang.Object)}.
     */
    @Test
    public void shouldNOTObjectsBeEqualsifFromDifferentClasses() {

        //GIVEN a task and all its parameters are set
        task = new Task("deded", project);
        task.setTaskID("1");

        //WHEN a TaskDTO is instantiated from another task
        taskDTO = new TaskDTO(task);
        Link link = linkTo(getClass()).withSelfRel();
        assertFalse(taskDTO.equals(link));// different classes

    }
}
