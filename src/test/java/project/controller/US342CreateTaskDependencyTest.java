package project.controller;

import static org.junit.Assert.fail;

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
		projRepo.createProject("Best project", "Fainding da quin an spitting on de non-beleevahs!", user);
		proj = projRepo.getAllProjects().get(0);

		// Initialize Task Repository
		taskRepo = proj.getTaskRepository();

		// Create and add tasks to Task Repository
		taskA = new Task(1, 1, "Faind fek quin!");
		taskB = new Task(1, 2, "Spit on non-beleevahs!");
		taskC = new Task(1, 3, "Follou da wae!");
		taskRepo.addProjectTask(taskA);
		taskRepo.addProjectTask(taskB);
		taskRepo.addProjectTask(taskC);

	}

	@After
	public void tearDown() {
		myComp.clear();
		projRepo = null;
		userRepo = null;
		taskRepo = null;
		proj = null;
		taskA = null;
		taskB = null;
		taskC = null;
		user = null;
	}

	@Test
	public final void testGetTasksFromAProject() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testCreateDependenceFromTask() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetTaskDependentEstimatedStartDate() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetTaskReferenceEstimatedStartDate() {
		fail("Not yet implemented"); // TODO
	}

}
