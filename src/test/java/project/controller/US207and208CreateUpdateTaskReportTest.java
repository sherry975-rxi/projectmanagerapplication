package project.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project.model.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class US207and208CreateUpdateTaskReportTest {
	Company myCompany;
	UserRepository userRepository;
	User user1;
	User userAdmin;
	ProjectCollaborator collab1;
	TaskCollaborator taskCollab1;
	Project project1;
	ProjectRepository projectRepository;
	Task testTask;
	US207and208CreateUpdateTaskReportControllers controller;

	@Before
	public void setUp() {
		// create company

		myCompany = Company.getTheInstance();

		// creates an UserRepository
		userRepository = myCompany.getUsersRepository();

		// creates a ProjectsRepository
		projectRepository = myCompany.getProjectsRepository();

		userRepository.getAllUsersFromRepository().clear();

		// create user
		user1 = userRepository.createUser("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000", "Rua",
				"2401-00", "Test", "Testo", "Testistan");
		// add user to user list
		userRepository.addUserToUserRepository(user1);
		// set user as collaborator
		user1.setUserProfile(Profile.COLLABORATOR);
		// create project
		project1 = projectRepository.createProject("name3", "description4", userAdmin);
		// add project to project repository
		projectRepository.addProjectToProjectRepository(project1);
		// create project collaborator
		collab1 = project1.createProjectCollaborator(user1, 0);
		// add project collaborator to project team
		project1.addProjectCollaboratorToProjectTeam(collab1);
		// create task
		testTask = project1.getTaskRepository().createTask("Tarefa para testar controlador");
		// add task to project
		project1.getTaskRepository().addProjectTask(testTask);
		// create task collaborator
		taskCollab1 = project1.getTaskRepository().getAllTasksWithoutCollaboratorsAssigned().get(0)
				.createTaskCollaborator(collab1);
		// assign task collaborator to task
		project1.getTaskRepository().getAllTasksWithoutCollaboratorsAssigned().get(0)
				.addTaskCollaboratorToTask(taskCollab1);

	}

	@After
	public void tearDown() {
		Company.clear();
		myCompany = null;
		userRepository = null;
		user1 = null;
		userAdmin = null;
		collab1 = null;
		taskCollab1 = null;
		project1 = null;
		projectRepository = null;
		testTask = null;
		controller = null;

	}

	/*
	 * This test checks if the reported time changed in the set up was done and
	 * validated
	 */
	@Test
	public void test() {

	}

	/*
	 * This test checks if the reported times is changed when the method is called
	 * in extent
	 */
	@Test
	public void test2() {

	}

	/*
	 * This test checks if the controller is working by changing the report time to
	 * a new given time, and if the controller returns the boolean true for sucess
	 * in changing
	 */
	@Test
	public void testController() {
		// create controller
		controller = new US207and208CreateUpdateTaskReportControllers("daniel@gmail.com", testTask.getTaskID());
		// Creates int time to report
		int timeToReport = 50;

		// Creates a report to the task
		controller.createReportController(timeToReport);

		// Checks if both values are the same
		assertEquals(controller.getReportedTimeByCollaborator(), timeToReport);

		// Changes the time report to 20
		project1.getTaskRepository().getAllTasksFromProjectCollaborator(collab1).get(0).changeReportedTime(20,
				"daniel@gmail.com");

		// Checks if the method to check report time returns the updated time set
		// previously
		assertEquals(20, project1.getTaskRepository().getAllTasksFromProjectCollaborator(collab1).get(0).getReports()
				.get(0).getReportedTime());

		// create controller with an invalid ID
		controller = new US207and208CreateUpdateTaskReportControllers("daniel@gmail.com", "invalidID");

	}

	@Test
	public void testGetReportCollaboratorName() {
		// create controller
		controller = new US207and208CreateUpdateTaskReportControllers("daniel@gmail.com", testTask.getTaskID());
		// Creates a report to the task
		controller.createReportController(20);
		// Checks if the method returns the name of the collaborator
		assertEquals(controller.getReportedCollaboratorName(), "Daniel");
	}

	@Test

	public void testCreateReportController() {
		// create controller
		controller = new US207and208CreateUpdateTaskReportControllers("daniel@gmail.com", testTask.getTaskID());

		// Creates a report
		assertTrue(controller.createReportController(20));

		// updates the report, because a report already exists by the same TaskCollabor
		assertTrue(controller.createReportController(30));
	}

}
