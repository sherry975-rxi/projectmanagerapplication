package project.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import project.Repository.ProjectsRepository;
import project.Repository.TaskRepository;
import project.Repository.UserRepository;
import project.Services.ProjectService;
import project.Services.TaskService;
import project.Services.UserService;
import project.model.Profile;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.Task;
import project.model.TaskCollaborator;
import project.model.User;
import project.model.taskstateinterface.Cancelled;
import project.model.taskstateinterface.OnGoing;
import project.model.taskstateinterface.Planned;
import project.model.taskstateinterface.Ready;

public class US377CollectionOfCancelledTasksFromAProjectTest {

	@Autowired
	private UserService userService;

	private UserRepository userRepository;

	@Autowired
	private ProjectService projectService;

	private ProjectsRepository projectRepository;

	@Autowired
	private TaskService taskService;

	private TaskRepository taskRepository;

	User user1;
	User userAdmin;

	TaskCollaborator taskWorker1;

	ProjectCollaborator collab1;

	Project project;
	Project project2;

	Task testTask;
	Task testTask2;
	Task testTask3;

	Calendar estimatedTaskStartDateTest;
	Calendar taskDeadlineDateTest;
	Calendar startDateTest;

	@Autowired
	US377CollectionOfCancelledTasksFromAProjectController controller;

	@Before
	public void setUp() {
		// creates an User service
		userService = new UserService();

		// creates a Project service
		projectService = new ProjectService();

		// creates a Task service
		taskService = new TaskService();

		// Creates the controller to be tested
		controller = new US377CollectionOfCancelledTasksFromAProjectController(project);

		// create user
		user1 = userService.createUser("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000", "Rua",
				"2401-00", "Test", "Testo", "Testistan");

		// create user admin
		userAdmin = userService.createUser("João", "joao@gmail.com", "001", "Admin", "920000000", "Rua", "2401-00",
				"Test", "Testo", "Testistan");

		// Creates one Project
		project = projectService.createProject("name3", "description4", userAdmin);
		project2 = projectService.createProject("name1", "description4", userAdmin);

		// create project collaborators
		collab1 = projectService.createProjectCollaborator(user1, project, 2);

		// create taskContainer

		taskService = project.getTaskService();

		// create task workers
		taskWorker1 = new TaskCollaborator(collab1);

		// set user as collaborator
		user1.setUserProfile(Profile.COLLABORATOR);

		userAdmin.setUserProfile(Profile.COLLABORATOR);

		// add user to project team

		project2.addProjectCollaboratorToProjectTeam(collab1);

		// create a estimated Task Start Date
		Calendar startDateTest = Calendar.getInstance();

		// create a estimated Task Start Date
		Calendar estimatedTaskStartDateTest = Calendar.getInstance();
		estimatedTaskStartDateTest.set(Calendar.YEAR, 2017);
		estimatedTaskStartDateTest.set(Calendar.MONTH, Calendar.SEPTEMBER);
		estimatedTaskStartDateTest.set(Calendar.DAY_OF_MONTH, 25);
		estimatedTaskStartDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// create a estimated Task Dead line Date
		Calendar taskDeadlineDateTest = Calendar.getInstance();
		taskDeadlineDateTest.set(Calendar.YEAR, 2018);
		taskDeadlineDateTest.set(Calendar.MONTH, Calendar.JANUARY);
		taskDeadlineDateTest.set(Calendar.DAY_OF_MONTH, 29);
		taskDeadlineDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// create a expired estimated Task Dead line Date
		Calendar taskExpiredDeadlineDateTest = Calendar.getInstance();
		taskExpiredDeadlineDateTest.set(Calendar.YEAR, 2017);
		taskExpiredDeadlineDateTest.set(Calendar.MONTH, Calendar.SEPTEMBER);
		taskExpiredDeadlineDateTest.set(Calendar.DAY_OF_MONTH, 29);
		taskExpiredDeadlineDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// create 4 tasks
		testTask = taskService.createTask("Test dis agen pls");
		testTask2 = taskService.createTask("Test dis agen pls");
		testTask3 = taskService.createTask("Test moar yeh");

		// Adds 5 tasks to the TaskContainer
		taskService.addTaskToProject(testTask);
		taskService.addTaskToProject(testTask2);
		taskService.addTaskToProject(testTask3);

		// set estimated task start date and task dead line to tasks
		testTask.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		testTask.setTaskDeadline(taskDeadlineDateTest);

		testTask2.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		testTask2.setTaskDeadline(taskDeadlineDateTest);

		testTask3.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		testTask3.setTaskDeadline(taskDeadlineDateTest);

		// set active user
		testTask.addTaskCollaboratorToTask(taskWorker1);
		testTask2.addTaskCollaboratorToTask(taskWorker1);
		testTask3.addTaskCollaboratorToTask(taskWorker1);

		// Sets the tasks to "Planned"
		testTask.setTaskState(new Planned());
		testTask2.setTaskState(new Planned());
		testTask3.setTaskState(new Planned());

		// set start date
		testTask.setStartDate(startDateTest);
		testTask2.setStartDate(startDateTest);
		testTask3.setStartDate(startDateTest);

		// Sets the tasks to "Ready"
		testTask.setTaskState(new Ready());
		testTask2.setTaskState(new Ready());
		testTask3.setTaskState(new Ready());

		// Sets the tasks to "onGoing"
		testTask.setTaskState(new OnGoing());
		testTask2.setTaskState(new OnGoing());
		testTask3.setTaskState(new OnGoing());

		// Sets the tasks to "cancelled"
		testTask.setTaskState(new Cancelled());
		testTask2.setTaskState(new Cancelled());
	}

	/**
	 * this test verify if the list of canceled projects is equals to the list
	 * created.
	 */
	@Test
	public final void testGetCancelledTasksFromAProject() {

		// create list with cancelled task to compare
		List<Task> cancelledTaskToCompare = new ArrayList<Task>();

		// add task to the list
		cancelledTaskToCompare.add(testTask);
		cancelledTaskToCompare.add(testTask2);

		assertEquals(cancelledTaskToCompare, controller.getCancelledTasksFromAProject());

	}

	@Test
	public final void testGetCancelledTaskListId() {
		String result = "[1.1] Test dis agen pls";
		assertTrue(result.equals(controller.getCancelledTaskListId(project).get(0)));
		result = "[1.2] Test dis agen pls";
		assertTrue(result.equals(controller.getCancelledTaskListId(project).get(1)));
	}

	@Test
	public final void testSplitStringByFirstSpace() {
		String input = "Test me master!";
		assertTrue("Test".equals(controller.splitStringByFirstSpace(input)));
	}

	@Test
	public final void getProjectCancelledTasks() {
		assertEquals(2, controller.getCancelledTasksFromAProject().size());
	}

}
