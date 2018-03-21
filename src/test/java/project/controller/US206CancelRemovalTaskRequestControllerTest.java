package project.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import project.Repository.ProjCollabRepository;
import project.Repository.ProjectsRepository;
import project.Repository.TaskRepository;
import project.Repository.UserRepository;
import project.Services.ProjectService;
import project.Services.TaskService;
import project.Services.UserService;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.StateEnum;
import project.model.Task;
import project.model.User;
import project.model.taskstateinterface.OnGoing;

@RunWith(SpringRunner.class)
@DataJpaTest
public class US206CancelRemovalTaskRequestControllerTest {

	@Autowired
	UserRepository userRepository;

	@Autowired
	ProjectsRepository projectsRepository;

	@Autowired
	ProjCollabRepository projCollabRepository;

	@Autowired
	TaskRepository taskRepository;

	User userDaniel;
	User userRui;
	ProjectService projectService;
	UserService userService;
	TaskService taskService;
	Project projectA;
	ProjectCollaborator userRuiCollaborator;
	Task taskA;
	Task taskB;
	Task taskC;
	String stringRequest1;
	String stringRequest2;
	List<String> pendingRemovalRequests;
	US206RemovalTaskRequestController us206v2Controller;

	@Before
	public void setUp() {

		// Creates the Project Repository and User Repository
		projectService = new ProjectService();
		userService = new UserService();
		taskService = new TaskService();

		projectService.setProjectsRepository(projectsRepository);
		projectService.setProjectCollaboratorRepository(projCollabRepository);
		userService.setUserRepository(userRepository);
		taskService.setProjectCollaboratorRepository(projCollabRepository);
		taskService.setTaskRepository(taskRepository);

		// Creates the users
		userDaniel = userService.createUser("Daniel", "daniel@gmail.com", "1234", "Arquitecto", "967387654", "Rua",
				"3700", "Porto", "Porto", "Portugal");
		userRui = userService.createUser("Rui", "rui@gmail.com", "12345", "Arquitecto", "967387654", "Rua", "3800",
				"Porto", "Porto", "Portugal");

		// Creates the Project and adds it to the project repository
		projectA = projectService.createProject("Museu Serralves", "Projecto do Museu de Serralves", userDaniel);

		// Adds the user to the project team
		userRuiCollaborator = projectService.createProjectCollaborator(userRui, projectA, 20);

		// Creates the tasks and adds them to the projectTasks
		taskA = taskService.createTask("Implementar US100", projectA);
		taskA.setTaskState(new OnGoing());
		taskA.setCurrentState(StateEnum.ONGOING);
		taskB = taskService.createTask("Implementar US200", projectA);
		taskB.setTaskState(new OnGoing());
		taskB.setCurrentState(StateEnum.ONGOING);
		taskC = taskService.createTask("Implementar US300", projectA);

		// Adds the project collaborator to the tasks
		taskA.addProjectCollaboratorToTask(userRuiCollaborator);
		taskB.addProjectCollaboratorToTask(userRuiCollaborator);
		taskService.saveTask(taskA);
		taskService.saveTask(taskB);

		us206v2Controller = new US206RemovalTaskRequestController(userRui);
		us206v2Controller.taskService = taskService;
		us206v2Controller.projectContainer = projectService;

	}

	@Test
	public void testCreateRequest() {
		// Creates the US206V2RemovalTaskRequestController
		// to create a request to remove from first task added - taskA (Task ID = 1.1,
		// projectID = 1)
		us206v2Controller.setProjectID(projectA.getId());
		us206v2Controller.setTaskID(projectA.getId() + ".1");

		// checks the change in task ID
		assertEquals(us206v2Controller.getTaskID(), projectA.getId() + ".1");

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

		//// Creates the strings with a task description and task id
		String taskIDandDescription1 = "[" + projectA.getId() + ".1] Implementar US100";
		String taskIDandDescription2 = "[" + projectA.getId() + ".2] Implementar US200";

		// List with the expected result strings
		List<String> expResult = new ArrayList<>();
		expResult.add(taskIDandDescription1);
		expResult.add(taskIDandDescription2);

		// List that results from the call of the method getUnfinishedTaskListFromUser
		List<String> UnfinishedTasksFromUser = us206v2Controller.getUnfinishedTaskListFromUser();

		assertEquals(expResult, UnfinishedTasksFromUser);

	}

}
