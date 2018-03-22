package project.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import project.Services.ProjectService;
import project.Services.TaskService;
import project.Services.UserService;
import project.model.Project;
import project.model.StateEnum;
import project.model.Task;
import project.model.User;
import project.model.taskstateinterface.Created;
import project.model.taskstateinterface.OnGoing;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan(basePackages = {"project.Services", "project.controller", "project.model"})
public class US342CreateTaskDependencyTest {

	@Autowired
	ProjectService projRepo;
	@Autowired
	UserService userRepo;
	@Autowired
	TaskService taskRepo;
	Project proj;
	Task taskA;
	Task taskB;
	Task taskC;
	User user;
	@Autowired
	US342CreateTaskDependencyController controller;

	@Before
	public void setUp() {


		// Add user to User Repository
		user = userRepo.createUser("Fek Quin", "ugandan@nackls.com", "cluck1337", "Follower of da wae", "919898997",
				"Debil Strit", "SP1T-0N-H1M", "NacklsCiti", "QuinLend", "UGANDA");

		// Add a project to the project repository
		proj = projRepo.createProject("Best project", "Fainding da quin an spitting on de non-beleevahs!", user);


		// Create and add tasks to Task Repository
		// Task A isn't added to test the method that checks if the list contains a
		// certain task

		// Create and add tasks to Task Repository
		taskA = taskRepo.createTask("Faind fek quin!", proj);
		taskB = taskRepo.createTask("Spit on non-beleevahs!", proj);
		taskC = taskRepo.createTask("Follou da wae!", proj);


		controller.setProject(proj);
	}


	@Test
	public final void testGetTasksFromAProject() {
		assertEquals(3, controller.getTasksFromAProject().size());
	}

	@Test
	public final void testCreateDependenceFromTaskWithEstimatedStartDate() {

		// Give estimated start date to task C
		taskC.setEstimatedTaskStartDate(Calendar.getInstance());
		taskC.setTaskDeadline(Calendar.getInstance());
		taskRepo.saveTask(taskC);

		// Create dependency
		controller.createDependenceFromTask(taskB.getTaskID(), taskC.getTaskID(), 20);

		taskRepo.saveTask(taskB);

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

		taskRepo.saveTask(taskB);

		String date = controller.getTaskEstimatedStartDateString(taskB.getTaskID());

		assertTrue("13/06/2005".equals(date));
	}

	@Test
	public final void testGetTaskByID() {

		Integer projectID = proj.getId();

		assertEquals(taskB, controller.getTaskByID((projectID+".2")));
		assertEquals(taskC, controller.getTaskByID((projectID+".3")));

	}

	@Test
	public final void projectContainsSelectedTask() {

		Integer projectID = proj.getId();

		assertTrue(controller.projectContainsSelectedTask(projectID+".2"));
		assertFalse(controller.projectContainsSelectedTask(projectID+".4"));
	}

	@Test
	public void isTaskDependencyPossibleTest() {

		taskA.setTaskState(new OnGoing());
		taskA.setCurrentState(StateEnum.ONGOING);
		taskB.setTaskState(new Created());
		taskB.setCurrentState(StateEnum.CREATED);
		taskA.setTaskDeadline(Calendar.getInstance());

		// Checks if transition is valid
		assertTrue(controller.isTaskDependencyPossible(taskB.getTaskID(), taskA.getTaskID()));

		/*
		 * Sets taskB to "OnGoing" Transition won't be valid
		 */
		taskB.setTaskState(new OnGoing());
		taskB.setCurrentState(StateEnum.ONGOING);
		assertFalse(controller.isTaskDependencyPossible(taskB.getTaskID(), taskA.getTaskID()));

	}

	@Test
	public void removeTaskDependency() {


		taskA.setTaskState(new OnGoing());
		taskA.setCurrentState(StateEnum.ONGOING);
		taskB.setTaskState(new Created());
		taskB.setCurrentState(StateEnum.CREATED);
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



		taskA.setTaskState(new OnGoing());
		taskA.setCurrentState(StateEnum.ONGOING);
		taskB.setTaskState(new Created());
		taskB.setCurrentState(StateEnum.CREATED);
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
