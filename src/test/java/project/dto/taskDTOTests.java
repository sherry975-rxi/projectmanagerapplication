package project.dto;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project.model.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class taskDTOTests {

    Project project;
    TaskDTO taskDTO;
    TaskDTO taskDTO1;
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
     * Tests the getters of the userDTO class
     */
    @Test
    public void shouldGetValues() {

        //GIVEN a task and all its parameters are set
        task = new Task("deded", project);
        task.setTaskID("1");
        task.setStartDate(date);
        task.setFinishDate(date);
        task.addProjectCollaboratorToTask(projectCollaborator);
        report.setTask(task);
        task.setEstimatedTaskEffort(1);
        task.setTaskBudget(1);
        task.setTaskDeadline(date);
        task.setTaskDependency(expected);
        task.setStartDateInterval(1);
        task.setDeadlineInterval(2);
        task.setDeadlineInterval(10);
        task.setPendingTaskTeamRequests(taskTeamRequests);




        //WHEN a TaskDTO is instantiated from another task
        taskDTO = new TaskDTO(task);



        //THEN All parameters' values can be retrived in the TaskDto and correspond to the respective parameter values
        // in the Task
        assertEquals(task.getDescription(), taskDTO.getDescription());
        assertEquals(task.getTaskID(), taskDTO.getTaskID());
        assertEquals(date, taskDTO.getStartDate());
        assertEquals(date, taskDTO.getFinishDate());
        assertEquals(task.getTaskTeam(), taskDTO.getTaskTeam());
        assertEquals(task.getReports(), taskDTO.getReports());
        assertEquals(task.getStartDate(),taskDTO.getStartDate());
        assertEquals(task.getCancelDate(),taskDTO.getCancelDate());
        assertEquals(task.getEstimatedTaskEffort(), taskDTO.getEstimatedTaskEffort(), 0.01);
        assertEquals(task.getTaskBudget(), taskDTO.getTaskBudget(), 0.01);
        assertEquals(task.getTaskDeadline(), task.getTaskDeadline());
        assertEquals(expected, taskDTO.getTaskDependency());
        assertEquals(task.getStartDateInterval(), taskDTO.getStartDateInterval());
        assertEquals(taskDTO.getDeadlineInterval(), taskDTO.getDeadlineInterval());
        assertEquals(task.getTaskState(), taskDTO.getTaskState());
        assertEquals(task.getCurrentState(), task.getCurrentState());
        assertEquals(task.getProject(), taskDTO.getProject());
        assertEquals(task.getCurrentState(), task.getCurrentState());
        assertTrue(task.getPendingTaskTeamRequests().equals(taskDTO.getPendingTaskTeamRequests()));
        assertEquals(actions, taskDTO.getActions());
        assertEquals(task.getTaskDeadline(), taskDTO.getTaskDeadline());
        assertEquals(task.getEstimatedTaskStartDate(), taskDTO.getEstimatedTaskStartDate());

    }

}
