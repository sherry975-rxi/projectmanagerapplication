package project.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.Services.ProjectService;
import project.Services.TaskService;
import project.Services.UserService;
import project.model.*;
import project.model.taskstateinterface.Created;
import project.model.taskstateinterface.OnGoing;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class US342CreateTaskDependencyTest {

	ProjectService projRepo;
	UserService userRepo;
	TaskService taskRepo;
	Project proj;
	Task taskA;
	Task taskB;
	Task taskC;
	User user;
	US342CreateTaskDependencyController controller;

	@Before
	public void setUp() {


		// Initialize Project Repository
		projRepo = new ProjectService();

		// Initialize User Repository
		userRepo = new UserService();

		// Add user to User Repository
		userRepo.createUser("Fek Quin", "ugandan@nackls.com", "cluck1337", "Follower of da wae", "919898997",
				"Debil Strit", "SP1T-0N-H1M", "NacklsCiti", "QuinLend", "UGANDA");
		user = userRepo.getUserByEmail("ugandan@nackls.com");

		// Add a project to the project repository
		projRepo.addProjectToProjectContainer(
				projRepo.createProject("Best project", "Fainding da quin an spitting on de non-beleevahs!", user));
		proj = projRepo.getAllProjectsfromProjectsContainer().get(0);

		// Initialize Task Repository
		taskRepo = proj.getTaskRepository();

		// Create and add tasks to Task Repository
		// Task A isn't added to test the method that checks if the list contains a
		// certain task
		taskA = new Task(1, 1, "Faind fek quin!");
		taskB = new Task(2, 1, "Spit on non-beleevahs!");
		taskC = new Task(3, 1, "Follou da wae!");
		taskRepo.addTaskToProject(taskC);
		taskRepo.addTaskToProject(taskB);

		// Initialize Controller
		controller = new US342CreateTaskDependencyController(proj);

	}

	@After
	public void tearDown() {
		projRepo = null;
		userRepo = null;
		taskRepo = null;
		proj = null;
		taskA = null;
		taskB = null;
		taskC = null;
		user = null;
		controller = null;
	}

	@Test
	public final void testGetTasksFromAProject() {
		assertEquals(2, controller.getTasksFromAProject().size());
	}

	@Test
	public final void testCreateDependenceFromTaskWithEstimatedStartDate() {

		// Give estimated start date to task C
		taskC.setEstimatedTaskStartDate(Calendar.getInstance());
		taskC.setTaskDeadline(Calendar.getInstance());

		// Create dependency
		controller.createDependenceFromTask("1.2", "1.3", 20);

		assertTrue(taskB.hasDependencies());
		assertTrue(taskB.getEstimatedTaskStartDate() != null);
		/*
		 * // Make sure the date is set correctly Calendar referenceDate = (Calendar)
		 * taskC.getEstimatedTaskStartDate().clone();
		 * referenceDate.add(Calendar.DAY_OF_MONTH, 20);
		 * assertTrue(taskB.getEstimatedTaskStartDate().equals(referenceDate));
		 */
	}

	@Test
	public final void testGetTaskEstimatedStartDateString() {
		// Give estimated start date to task B
		taskB.setEstimatedTaskStartDate(Calendar.getInstance());

		// Set a specific date to avoid future failures
		taskB.getEstimatedTaskStartDate().set(Calendar.DAY_OF_MONTH, 13);
		taskB.getEstimatedTaskStartDate().set(Calendar.MONTH, 5);
		taskB.getEstimatedTaskStartDate().set(Calendar.YEAR, 2005);

		String date = controller.getTaskEstimatedStartDateString("1.2");

		assertTrue("13/06/2005".equals(date));
	}

	@Test
	public final void testGetTaskByID() {

		assertEquals(taskB, controller.getTaskByID("1.2"));
		assertEquals(taskC, controller.getTaskByID("1.3"));

	}

	@Test
	public final void projectContainsSelectedTask() {

		assertTrue(controller.projectContainsSelectedTask("1.2"));
		assertFalse(controller.projectContainsSelectedTask("1.1"));
	}

	@Test
	public void isTaskDependencyPossibleTest() {
		// Adds taskA to TaskContainer
		taskRepo.addTaskToProject(taskA);

		taskA.setTaskState(new OnGoing());
		taskB.setTaskState(new Created());
		taskA.setTaskDeadline(Calendar.getInstance());

		// Checks if transition is valid
		assertTrue(controller.isTaskDependencyPossible(taskB.getTaskID(), taskA.getTaskID()));

		/*
		 * Sets taskB to "OnGoing" Transition won't be valid
		 */
		taskB.setTaskState(new OnGoing());
		assertFalse(controller.isTaskDependencyPossible(taskB.getTaskID(), taskA.getTaskID()));

	}

	@Test
	public void removeTaskDependency() {
		// Adds taskA to TaskContainer
		taskRepo.addTaskToProject(taskA);


		taskA.setTaskState(new OnGoing());
		taskB.setTaskState(new Created());
		taskA.setTaskDeadline(Calendar.getInstance());

		// Creates a task dependency
		controller.createDependenceFromTask(taskB.getTaskID(), taskA.getTaskID(), 10);

		// Checks if it's possible to remove task dependency
		assertTrue(controller.removeDependenceFromTask(taskB.getTaskID(), taskA.getTaskID()));

		// Removes task dependency
		controller.removeDependenceFromTask(taskB.getTaskID(), taskA.getTaskID());

		/*
		 * Dependency doesn't exist anymore, so it will return false
		 */
		assertFalse(controller.removeDependenceFromTask(taskB.getTaskID(), taskA.getTaskID()));

	}

	@Test
	public void getTaskDeadlineString() {

		// Adds taskA to TaskContainer
		taskRepo.addTaskToProject(taskA);

		taskA.setTaskState(new OnGoing());
		taskB.setTaskState(new Created());
		taskA.setTaskDeadline(Calendar.getInstance());

		// Creates a task dependency
		controller.createDependenceFromTask(taskB.getTaskID(), taskA.getTaskID(), 10);

		// Creates a string with the date of the moment
		Calendar aaa = Calendar.getInstance();
		aaa = taskA.getTaskDeadline();
		String estimatedDeadlineString = new String();
		Date estimatedDeadline = aaa.getTime();

		SimpleDateFormat newDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		estimatedDeadlineString = newDateFormat.format(estimatedDeadline).toString();

		assertEquals(controller.getTaskDeadlineString(taskA.getTaskID()), estimatedDeadlineString);
		assertEquals(controller.getTaskDeadlineString(taskB.getTaskID()), "No estimated deadline");

	}

}
