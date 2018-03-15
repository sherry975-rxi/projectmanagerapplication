package project.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.Services.ProjectService;
import project.Services.TaskService;
import project.Services.UserService;
import project.model.*;

import static org.junit.Assert.assertEquals;

public class US204v2createRequestAddCollaboratorToTaskTeamControllerTest {

	ProjectService projectContainer;
	UserService userContainer;
	TaskService taskRepo;
	Project proj;
	Task taskA;
	Task taskB;
	Task taskC;
	User user;
	US204v2createRequestAddCollaboratorToTaskTeamController controller;

	@Before
	public void setUp() {

		// Initialize Project Repository
		projectContainer = new ProjectService();


		// Initialize User Repository
		userContainer = new UserService();

		// Add user to User Repository
		userContainer.createUser("Fek Quin", "ugandan@nackls.com", "cluck1337", "Follower of da wae", "919898997",
				"Debil Strit", "SP1T-0N-H1M", "NacklsCiti", "QuinLend", "UGANDA");
		user = userContainer.getUserByEmail("ugandan@nackls.com");

		// Add a project to the project repository
		projectContainer.addProjectToProjectContainer(
				projectContainer.createProject("Best project", "Fainding da quin an spitting on de non-beleevahs!", user));
		proj = projectContainer.getAllProjectsfromProjectsContainer().get(0);

		// Initialize Task Repository
		taskRepo = proj.getTaskRepository();

		// Create and add tasks to Task Repository
		taskA = new Task(1, 1, "Faind fek quin!");
		taskB = new Task(2, 1, "Spit on non-beleevahs!");
		taskC = new Task(3, 1, "Follou da wae!");
		taskRepo.addTaskToProject(taskA);
		taskRepo.addTaskToProject(taskB);
		taskRepo.addTaskToProject(taskC);

		// Create controller to be used
		controller = new US204v2createRequestAddCollaboratorToTaskTeamController("1.1", user);

	}

	@After
	public void tearDown() {

		projectContainer = null;
		userContainer = null;
		taskRepo = null;
		proj = null;
		taskA = null;
		taskB = null;
		taskC = null;
		user = null;
		controller = null;
	}

	@Test
	public final void testCreateTaskTeamRequest() {
		assertEquals(0, proj.getPendingTaskAssignementRequests().size());
		controller.createTaskTeamRequest();
		assertEquals(1, proj.getPendingTaskAssignementRequests().size());
	}

	@Test
	public final void testSetProjectIDFromTaskID() {
		controller.setProjectIDFromTaskID("1.1");
		int projID = (int) controller.getProjectID();
		assertEquals(1, projID);
	}

	@Test
	public final void testSetProjectID() {
		controller.setProjectID(5);
		int projID = (int) controller.getProjectID();
		assertEquals(5, projID);
	}

	@Test
	public final void testGetTaskByTaskID() {
		assertEquals(taskA, controller.getTaskByTaskID("1.1"));
	}

}
