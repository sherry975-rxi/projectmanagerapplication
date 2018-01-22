package project.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.model.Company;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.ProjectRepository;
import project.model.Task;
import project.model.User;
import project.model.UserRepository;

public class US206V2RemovalTaskRequestControllerTest {

	Company company;
	User userDaniel;
	User userRui;
	ProjectRepository projectRepository;
	UserRepository userRepository;
	Project projectA;
	ProjectCollaborator userRuiCollaborator;
	Task taskA;
	Task taskB;
	Task taskC;
	String stringRequest1;
	String stringRequest2;
	List<String> pendingRemovalRequests;
	US206V2RemovalTaskRequestController us206v2Controller;

	@Before
	public void setUp() {

		// Sets the Company
		company = Company.getTheInstance();

		// Creates the Project Repository and User Repository
		projectRepository = Company.getTheInstance().getProjectsRepository();
		userRepository = Company.getTheInstance().getUsersRepository();

		// Creates the users
		userDaniel = userRepository.createUser("Daniel", "daniel@gmail.com", "1234", "Arquitecto", "967387654", "Rua",
				"3700", "Porto", "Porto", "Portugal");
		userRui = userRepository.createUser("Rui", "rui@gmail.com", "12345", "Arquitecto", "967387654", "Rua", "3800",
				"Porto", "Porto", "Portugal");
		// Adds users to the user repository
		userRepository.addUserToUserRepository(userDaniel);
		userRepository.addUserToUserRepository(userRui);

		// Creates the Project and adds it to the project repository
		projectA = projectRepository.createProject("Museu Serralves", "Projecto do Museu de Serralves", userDaniel);
		projectRepository.addProjectToProjectRepository(projectA);

		// Adds the user to the project team
		projectA.addUserToProjectTeam(userRui, 20);
		userRuiCollaborator = projectA.findProjectCollaborator(userRui);

		// Creates the tasks and adds them to the projectTasks
		taskA = projectA.getTaskRepository().createTask("Implementar US100");
		projectA.getTaskRepository().addProjectTask(taskA);

		taskB = projectA.getTaskRepository().createTask("Implementar US200");
		projectA.getTaskRepository().addProjectTask(taskB);

		taskC = projectA.getTaskRepository().createTask("Implementar US300");
		projectA.getTaskRepository().addProjectTask(taskC);

		// Adds the project collaborator to the tasks
		taskA.addProjectCollaboratorToTask(userRuiCollaborator);
		taskB.addProjectCollaboratorToTask(userRuiCollaborator);
		// taskC.addProjectCollaboratorToTask(userRuiCollaborator);

	}

	@After
	public void tearDown() {
		Company.clear();
		company = null;
		userDaniel = null;
		userRui = null;
		projectRepository = null;
		userRepository = null;
		projectA = null;
		userRuiCollaborator = null;
		taskA = null;
		taskB = null;
		taskC = null;
		stringRequest1 = null;
		stringRequest2 = null;
		pendingRemovalRequests = null;
		us206v2Controller = null;
	}

	@Test
	public void testCreateRequest() {
		// Creates the US206V2RemovalTaskRequestController
		// to create a request to remove from first task added - taskA (Task ID = 1.1,
		// projectID = 1)
		us206v2Controller = new US206V2RemovalTaskRequestController(userRui);
		us206v2Controller.setProjectID(1);
		us206v2Controller.setTaskID("1.1");

		// Creates the removal requests from userRui and TaskA
		assertTrue(us206v2Controller.createRequest());
		// Tries to create again the same request
		// it should not be allowed
		assertFalse(us206v2Controller.createRequest());
	}

	/**
	 * Tests the setTaskIDandProjectID
	 */
	@Test
	public void testSetProjectIDFromTask() {

		// Creates the string with a task description and task id
		String taskID = "5.3";

		// Instantiates the controller
		us206v2Controller = new US206V2RemovalTaskRequestController(userRui);
		// Calls the method setTaskIDandProjectID
		us206v2Controller.setProjectIDFromTaskID(taskID);

		// Expected result
		Integer projectID = 5;

		assertEquals(projectID, us206v2Controller.getProjectID());
	}

	/**
	 * Tests the getUnfinishedTasksFromUser method that has to return a list from
	 * string
	 */
	@Test
	public void testGetUnfinishedTaskFromUser() {

		// Instantiates the controller
		us206v2Controller = new US206V2RemovalTaskRequestController(userRui);

		//// Creates the strings with a task description and task id
		String taskIDandDescription1 = "[1.1] Implementar US100";
		String taskIDandDescription2 = "[1.2] Implementar US200";

		// List with the expected result strings
		List<String> expResult = new ArrayList<>();
		expResult.add(taskIDandDescription1);
		expResult.add(taskIDandDescription2);

		// List that results from the call of the method getUnfinishedTaskListFromUser
		List<String> UnfinishedTasksFromUser = us206v2Controller.getUnfinishedTaskListFromUser();

		assertEquals(expResult, UnfinishedTasksFromUser);

	}

}
