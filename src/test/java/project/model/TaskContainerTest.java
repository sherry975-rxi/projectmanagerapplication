package project.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import project.model.taskstateinterface.Cancelled;
import project.model.taskstateinterface.Finished;
import project.model.taskstateinterface.OnGoing;
import project.model.taskstateinterface.TaskStateInterface;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static java.util.Calendar.MONTH;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class TaskContainerTest {

	@Mock
	private Task taskMock;

	@Mock
	private Task task2Mock;

	private Calendar startDate;
	private Project project;
	private ProjectContainer projectContainer;
	private UserContainer userContainer;
	private User user;
	private ProjectCollaborator projectCollaborator;

	@InjectMocks
	private TaskContainer victim;

	@Before
	public void setUp(){
		initMocks(this);

		this.victim = new TaskContainer(1);

		this.startDate = Calendar.getInstance();
		this.startDate.set(Calendar.YEAR, 2017);
		this.startDate.set(MONTH, Calendar.SEPTEMBER);
		this.startDate.set(Calendar.DAY_OF_MONTH, 25);
		this.startDate.set(Calendar.HOUR_OF_DAY, 14);


		this.projectContainer = new ProjectContainer();
		this.userContainer = new UserContainer();
		this.user = userContainer.createUser("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000", "Rua",
				"2401-00", "Test", "Testo", "Testistan");
		this.project = projectContainer.createProject("name3", "description4", user);
		project.setTaskRepository(victim);
		this.projectCollaborator = new ProjectCollaborator(user, 10);

	}

	@After
	public void tearDown() {
		this.victim = null;

	}

	@Test
	public void testConstructor(){
		assertEquals(1, victim.getProjectId());
		assertEquals(1, victim.getTaskCounter());
	}

	@Test
	public void testCreateTask() {
		Task expectedTask = new Task(victim.getTaskCounter(), victim.getProjectId(), "firstTask", 1, this.startDate, this.startDate, 10);
		assertEquals(1, victim.getTaskCounter());
		Task actualTask= victim.createTask("firstTask", 1, this.startDate, this.startDate, 10 );
		assertEquals(expectedTask, actualTask);
		assertEquals(2, victim.getTaskCounter());

		expectedTask = new Task(victim.getTaskCounter(), victim.getProjectId(),"firstTask");
		actualTask= victim.createTask("firstTask");
		assertEquals(expectedTask, actualTask);
		assertEquals(3, victim.getTaskCounter());
	}

	@Test
	public void testGetAllTasksfromProject(){
		Task task = new Task(victim.getTaskCounter(), victim.getProjectId(),"firstTask");
		victim.addTaskToProject(task);

		List<Task> expectedTasks = new ArrayList<>();
		expectedTasks.add(task);

		assertEquals(expectedTasks, victim.getAllTasksfromProject());
	}

	@Test
	public void testSettersAndGetters(){
		victim.setProject(project);
		assertEquals(project, victim.getProject());

		victim.setTaskCounter(2);
		assertEquals(2, victim.getTaskCounter());

		assertEquals(1, victim.getProjectId());
	}

	@Test
	public void testGetUnFinishedTasksFromProjectCollaborator() {
		when(taskMock.isTaskFinished()).thenReturn(false);
		when(taskMock.isProjectCollaboratorInTaskTeam(any(ProjectCollaborator.class))).thenReturn(true);

		victim.addTaskToProject(taskMock);

		List<Task> expectedTaskList = new ArrayList<>();
		expectedTaskList.add(taskMock);
		assertEquals(expectedTaskList, victim.getUnfinishedTasksFromProjectCollaborator(projectCollaborator));
	}

	@Test
	public void testGetStartedNotFinishedTasksFromProjectCollaborator(){
		when(taskMock.isTaskFinished()).thenReturn(false);
		when(taskMock.viewTaskStateName()).thenReturn("Canceled");
		when(taskMock.getStartDate()).thenReturn(startDate);
		when(taskMock.isProjectCollaboratorInTaskTeam(any(ProjectCollaborator.class))).thenReturn(true);

		victim.addTaskToProject(taskMock);

		List<Task> expectedTaskList = new ArrayList<>();
		expectedTaskList.add(taskMock);
		assertEquals(expectedTaskList, victim.getStartedNotFinishedTasksFromProjectCollaborator(projectCollaborator));
	}

	@Test
	public void testFinishedTaskListOfUserInProject() {
		when(taskMock.isTaskFinished()).thenReturn(true);
		when(taskMock.isProjectCollaboratorInTaskTeam(any(ProjectCollaborator.class))).thenReturn(true);
		victim.addTaskToProject(taskMock);

		List<Task> expectedTaskList = new ArrayList<>();
		expectedTaskList.add(taskMock);
		assertEquals(expectedTaskList, victim.getFinishedTaskListofUserInProject(projectCollaborator));
	}

	@Test
	public void testGetFinishedTasksFromProjectCollaboratorInGivenMonth() {
		Calendar calendar = Calendar.getInstance();

		when(taskMock.isTaskFinished()).thenReturn(true);
		when(taskMock.getFinishDate()).thenReturn(calendar);
		when(taskMock.isProjectCollaboratorInTaskTeam(any(ProjectCollaborator.class))).thenReturn(true);
		victim.addTaskToProject(taskMock);


		List<Task> expectedTaskList = new ArrayList<>();
		expectedTaskList.add(taskMock);
		assertEquals(expectedTaskList, victim.getFinishedTasksFromProjectCollaboratorInGivenMonth(projectCollaborator, 0));
		assertEquals(expectedTaskList, victim.getFinishedTasksFromProjectCollaboratorInGivenMonth(projectCollaborator, -1));


		calendar.set(MONTH, Calendar.FEBRUARY);
		when(taskMock.getFinishDate()).thenReturn(calendar);
		assertEquals(new ArrayList<>(), victim.getFinishedTasksFromProjectCollaboratorInGivenMonth(projectCollaborator, 0));
	}

	@Test
	public void testIsTaskInTaskContainer(){
		victim.addTaskToProject(taskMock);

		assertTrue(victim.isTaskInTaskContainer(taskMock));
		assertFalse(victim.isTaskInTaskContainer(new Task()));
	}

	@Test
	public void testGetAllTasksFromProjectCollaborator(){
		when(taskMock.isProjectCollaboratorInTaskTeam(any(ProjectCollaborator.class))).thenReturn(true);
		victim.addTaskToProject(taskMock);

		List<Task> expectedTaskList = new ArrayList<>();
		expectedTaskList.add(taskMock);

		assertEquals(expectedTaskList, victim.getAllTasksFromProjectCollaborator(projectCollaborator));
	}

	@Test
	public void testIsCollaboratorActiveOnAnyTask(){
		when(taskMock.isProjectCollaboratorActiveInTaskTeam(any(ProjectCollaborator.class))).thenReturn(true);
		victim.addTaskToProject(taskMock);
		assertTrue(victim.isCollaboratorActiveOnAnyTask(projectCollaborator));


		when(taskMock.isProjectCollaboratorActiveInTaskTeam(any(ProjectCollaborator.class))).thenReturn(false);
		assertFalse(victim.isCollaboratorActiveOnAnyTask(projectCollaborator));
	}

	@Test
	public void testGetAllTasksWithoutCollaboratorsAssigned(){
		when(taskMock.isTaskTeamEmpty()).thenReturn(true);
		victim.addTaskToProject(taskMock);
		List<Task> expectedTaskList = new ArrayList<>();
		expectedTaskList.add(taskMock);
		assertEquals(expectedTaskList, victim.getAllTasksWithoutCollaboratorsAssigned());

		when(taskMock.isTaskTeamEmpty()).thenReturn(false);
		when(taskMock.doesTaskTeamHaveActiveUsers()).thenReturn(false);
		assertEquals(expectedTaskList, victim.getAllTasksWithoutCollaboratorsAssigned());

		when(taskMock.isTaskTeamEmpty()).thenReturn(false);
		when(taskMock.doesTaskTeamHaveActiveUsers()).thenReturn(true);
		expectedTaskList.remove(taskMock);
		assertEquals(expectedTaskList, victim.getAllTasksWithoutCollaboratorsAssigned());
	}

	@Test
	public void testGetFinishedTasks(){
		when(taskMock.isTaskFinished()).thenReturn(true);
		victim.addTaskToProject(taskMock);

		List<Task> expectedTaskList = new ArrayList<>();
		expectedTaskList.add(taskMock);

		assertEquals(expectedTaskList, victim.getFinishedTasks());
	}

	@Test
	public void testGetTimeSpentByProjectCollaboratorInAllTasksLastMonth(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);

		when(taskMock.isTaskFinished()).thenReturn(true);
		when(taskMock.getFinishDate()).thenReturn(calendar);
		when(taskMock.isProjectCollaboratorInTaskTeam(any(ProjectCollaborator.class))).thenReturn(true);
		when(taskMock.getTimeSpentByProjectCollaboratorOntask(any(ProjectCollaborator.class))).thenReturn(10.0);
		victim.addTaskToProject(taskMock);

		when(task2Mock.isTaskFinished()).thenReturn(true);
		when(task2Mock.getFinishDate()).thenReturn(calendar);
		when(task2Mock.isProjectCollaboratorInTaskTeam(any(ProjectCollaborator.class))).thenReturn(true);
		when(task2Mock.getTimeSpentByProjectCollaboratorOntask(any(ProjectCollaborator.class))).thenReturn(5.0);
		victim.addTaskToProject(task2Mock);

		assertEquals(15.0, victim.getTimeSpentByProjectCollaboratorInAllTasksLastMonth(new ProjectCollaborator()), 0.1);
	}

	@Test
	public void testGetFinishedTasksInDecreasingOrder(){
		Calendar calendar = Calendar.getInstance();
		when(taskMock.getFinishDate()).thenReturn(calendar);
		when(taskMock.isTaskFinished()).thenReturn(true);
		victim.addTaskToProject(taskMock);

		Calendar calendar2 = Calendar.getInstance();
		calendar2.add(Calendar.MONTH, -1);
		when(task2Mock.getFinishDate()).thenReturn(calendar2);
		when(task2Mock.isTaskFinished()).thenReturn(true);
		victim.addTaskToProject(task2Mock);

		List<Task> expectedTaskList = new ArrayList<>();
		expectedTaskList.add(taskMock);
		expectedTaskList.add(task2Mock);

		assertEquals(expectedTaskList, victim.getFinishedTasksInDecreasingOrder());
	}

	@Test
	public void testGetUnFinishedTasks(){
		Calendar calendar2 = Calendar.getInstance();
		calendar2.add(Calendar.MONTH, -1);
		when(taskMock.isTaskFinished()).thenReturn(false);
		when(taskMock.viewTaskStateName()).thenReturn("OnGoing");
		when(taskMock.getStartDate()).thenReturn(calendar2);
		victim.addTaskToProject(taskMock);

		List<Task> expectedTaskList = new ArrayList<>();
		expectedTaskList.add(taskMock);
		assertEquals(expectedTaskList, victim.getUnFinishedTasks());
	}

	@Test
	public void testGetUnstartedTasks(){
		when(taskMock.getStartDate()).thenReturn(null);
		victim.addTaskToProject(taskMock);

		List<Task> expectedTaskList = new ArrayList<>();
		expectedTaskList.add(taskMock);
		assertEquals(expectedTaskList, victim.getUnstartedTasks());

		Calendar calendar2 = Calendar.getInstance();
		when(taskMock.getStartDate()).thenReturn(calendar2);
		expectedTaskList.remove(taskMock);
		assertEquals(expectedTaskList, victim.getUnstartedTasks());
	}

	@Test
	public void testGetExpiredTasks(){
		Calendar calendar2 = Calendar.getInstance();
		calendar2.add(Calendar.MONTH, -1);

		//all conditions on if are true
		when(taskMock.isTaskFinished()).thenReturn(false);
		when(taskMock.getTaskDeadline()).thenReturn(calendar2);
		victim.addTaskToProject(taskMock);

		List<Task> expectedTaskList = new ArrayList<>();
		expectedTaskList.add(taskMock);
		assertEquals(expectedTaskList, victim.getExpiredTasks());

		//the condition other.getTaskDeadline().before(today) is false
		when(taskMock.isTaskFinished()).thenReturn(false);
		Calendar calendar = Calendar.getInstance();
		when(taskMock.getTaskDeadline()).thenReturn(calendar);
		expectedTaskList.remove(taskMock);
		assertEquals(expectedTaskList, victim.getExpiredTasks());

		//the condition other.getTaskDeadline() != null is false
		when(taskMock.isTaskFinished()).thenReturn(false);
		when(taskMock.getTaskDeadline()).thenReturn(null);
		assertEquals(expectedTaskList, victim.getExpiredTasks());

		//the condition !other.isTaskFinished() is false
		when(taskMock.isTaskFinished()).thenReturn(true);
		when(taskMock.getTaskDeadline()).thenReturn(calendar2);
		assertEquals(expectedTaskList, victim.getExpiredTasks());
	}

	@Test
	public void testGetTaskByID(){
		when(taskMock.getTaskID()).thenReturn("12");
		victim.addTaskToProject(taskMock);

		assertEquals(taskMock, victim.getTaskByID("12"));
		assertEquals(null, victim.getTaskByID("32"));
	}

	@Test
	public void testDeleteTask(){
		when(taskMock.viewTaskStateName()).thenReturn("Planned");
		victim.addTaskToProject(taskMock);
		assertTrue(victim.deleteTask(taskMock));

		when(taskMock.viewTaskStateName()).thenReturn("Created");
		assertTrue(victim.deleteTask(taskMock));

		when(taskMock.viewTaskStateName()).thenReturn("Ready");
		assertTrue(victim.deleteTask(taskMock));

		when(taskMock.viewTaskStateName()).thenReturn("Finished");
		assertFalse(victim.deleteTask(taskMock));
	}


	@Test
	public void testGetCancelledTasksFromProject(){
		TaskStateInterface cancelled = new Cancelled(taskMock);
		when(taskMock.getTaskState()).thenReturn(cancelled);
		victim.addTaskToProject(taskMock);

		List<Task> expectedTaskList = new ArrayList<>();
		expectedTaskList.add(taskMock);
		assertEquals(expectedTaskList, victim.getCancelledTasksFromProject());

		TaskStateInterface finished = new Finished(taskMock);
		when(taskMock.getTaskState()).thenReturn(finished);
		expectedTaskList.remove(taskMock);
		assertEquals(expectedTaskList, victim.getCancelledTasksFromProject());

	}

	@Test
	public void testGetReportedCostOfEachTask(){
		when(taskMock.getTaskCost()).thenReturn(10.0);
		victim.addTaskToProject(taskMock);

		List<String> expectedList = new ArrayList<>();
		expectedList.add("10.0");

		assertEquals(expectedList, victim.getReportedCostOfEachTask());
	}

	@Test
	public void testGetTaskListOfWhichDependenciesCanBeCreated(){
		//test case taskState is finished
		TaskStateInterface finished = new Finished(taskMock);
		when(taskMock.getTaskState()).thenReturn(finished);

		List<Task> expectedTaskList = new ArrayList<>();
		assertEquals(expectedTaskList, victim.getTaskListOfWhichDependenciesCanBeCreated());

		//test case taskState is cancelled
		TaskStateInterface cancelled = new Cancelled(taskMock);
		when(taskMock.getTaskState()).thenReturn(cancelled);
		assertEquals(expectedTaskList, victim.getTaskListOfWhichDependenciesCanBeCreated());

		//test case taskState isn't finished neither cancelled
		TaskStateInterface onGoing = new OnGoing(taskMock);
		when(taskMock.getTaskState()).thenReturn(onGoing);
		victim.addTaskToProject(taskMock);
		expectedTaskList.add(taskMock);
		assertEquals(expectedTaskList, victim.getTaskListOfWhichDependenciesCanBeCreated());

	}
}
