package project.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.model.Company;
import project.model.Project;
import project.model.ProjectRepository;
import project.model.Task;
import project.model.TaskRepository;
import project.model.User;
import project.model.UserRepository;

public class US342CreateTaskDependencyTest {

	Company myComp;
	ProjectRepository projRepo;
	UserRepository userRepo;
	TaskRepository taskRepo;
	Project proj;
	Task taskA;
	Task taskB;
	Task taskC;
	User user;
	US342CreateTaskDependencyController controller;

	@Before
	public void setUp() {
		// Initialize company
		myComp = Company.getTheInstance();

		// Initialize Project Repository
		projRepo = myComp.getProjectsRepository();

		// Initialize User Repository
		userRepo = myComp.getUsersRepository();

		// Add user to User Repository
		userRepo.createUser("Fek Quin", "ugandan@nackls.com", "cluck1337", "Follower of da wae", "919898997",
				"Debil Strit", "SP1T-0N-H1M", "NacklsCiti", "QuinLend", "UGANDA");
		user = userRepo.getUserByEmail("ugandan@nackls.com");

		// Add a project to the project repository
		projRepo.addProjectToProjectRepository(
				projRepo.createProject("Best project", "Fainding da quin an spitting on de non-beleevahs!", user));
		proj = projRepo.getAllProjects().get(0);

		// Initialize Task Repository
		taskRepo = proj.getTaskRepository();

		// Create and add tasks to Task Repository
		taskA = new Task(1, 1, "Faind fek quin!");
		taskB = new Task(2, 1, "Spit on non-beleevahs!");
		taskC = new Task(3, 1, "Follou da wae!");
		taskRepo.addProjectTask(taskA);
		taskRepo.addProjectTask(taskB);
		taskRepo.addProjectTask(taskC);

		// Initialize Controller
		controller = new US342CreateTaskDependencyController(proj);

	}

	@After
	public void tearDown() {
		Company.clear();
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
		assertEquals(3, controller.getTasksFromAProject().size());
	}

	@Test
	public final void testCreateDependenceFromTaskWithEstimatedStartDate() {

		// Give estimated start date to task C
		taskC.setEstimatedTaskStartDate(Calendar.getInstance());

		// Create dependency
		controller.createDependenceFromTask("1.2", "1.3", 20);

		assertTrue(taskB.hasDependencies());
		assertTrue(taskB.getEstimatedTaskStartDate() != null);

		// Make sure the date is set correctly
		Calendar referenceDate = (Calendar) taskC.getEstimatedTaskStartDate().clone();
		referenceDate.add(Calendar.DAY_OF_MONTH, 20);
		assertTrue(taskB.getEstimatedTaskStartDate().equals(referenceDate));
	}

	@Test
	public final void testCreateDependenceFromTaskNoEstimatedStartDate() {
		// Create dependency without estimated start date
		controller.createDependenceFromTaskWithoutEstimatedStartDate("1.2", "1.3");
		assertTrue(taskB.hasDependencies());
		assertTrue(taskB.getEstimatedTaskStartDate() == null);
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

		assertEquals(taskA, controller.getTaskByID("1.1"));
		assertEquals(taskB, controller.getTaskByID("1.2"));
		assertEquals(taskC, controller.getTaskByID("1.3"));
	}

}
