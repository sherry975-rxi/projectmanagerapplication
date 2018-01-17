package project.controller;

import static org.junit.Assert.assertEquals;
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
import project.model.TaskCollaborator;
import project.model.User;
import project.model.UserRepository;

/**
 * Tests the US357CancelRemovalTaskrequestController
 * 
 * @author Group 3
 *
 */
public class US357CancelRemovalTaskRequestControllerTest {

	Company company;
	User userDaniel;
	User userRui;
	ProjectRepository projectRepository;
	UserRepository userRepository;
	Project projectA;
	ProjectCollaborator userRuiProjectCollaborator;
	Task taskA;
	Task taskB;
	Task taskC;
	String stringRequest1;
	String stringRequest2;
	TaskCollaborator userRuiTaskCollaborator;
	List<String> pendingRemovalRequests;
	US357CancelRemovalTaskRequestController us357Controller;

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
		userRuiProjectCollaborator = projectA.findProjectCollaborator(userRui);

		// Creates the tasks and adds them to the projectTasks
		taskA = projectA.getTaskRepository().createTask("Implementar US100");
		projectA.getTaskRepository().addProjectTask(taskA);
		taskB = projectA.getTaskRepository().createTask("Implementar US200");
		projectA.getTaskRepository().addProjectTask(taskB);
		taskC = projectA.getTaskRepository().createTask("Implementar US300");
		projectA.getTaskRepository().addProjectTask(taskC);

		// Adds the project collaborator to the tasks
		taskA.addProjectCollaboratorToTask(userRuiProjectCollaborator);
		taskB.addProjectCollaboratorToTask(userRuiProjectCollaborator);
		taskC.addProjectCollaboratorToTask(userRuiProjectCollaborator);

		// Creates the removal requests from userRui and TaskA and TaskB
		projectA.createTaskRemovalRequest(userRuiProjectCollaborator, taskA);
		projectA.createTaskRemovalRequest(userRuiProjectCollaborator, taskB);

		// Creates the list of strings with the information of the pending removal
		// requests
		stringRequest1 = "Rui" + "\n" + "rui@gmail.com" + "\n" + "1.1" + "\n" + "Implementar US100";
		stringRequest2 = "Rui" + "\n" + "rui@gmail.com" + "\n" + "1.2" + "\n" + "Implementar US200";
		pendingRemovalRequests = new ArrayList<>();
		pendingRemovalRequests.add(stringRequest1);
		pendingRemovalRequests.add(stringRequest2);

		// Creates the US357CancelRemovalTaskRequestController
		us357Controller = new US357CancelRemovalTaskRequestController(1);

		// Creates the taskCollaborator
		userRuiTaskCollaborator = taskA.getTaskTeam().get(0);

	}

	@After
	public void tearDown() {

		Company.clear();
		projectA = null;

	}

	/**
	 * Tests the ViewPendingRemovalRequests method. The result has to be a list of
	 * strings, with the details of the task removal requests.
	 */
	@Test
	public void testViewPendingRemovalRequests() {

		assertEquals(pendingRemovalRequests, us357Controller.viewPendingRemovalRequests());
	}

	/**
	 * Tests the isTaskIDValid. First the controller calls the
	 * setRequestDataFromString method, that sets the parameters taskID and
	 * userEmail of the US357 controller. Then it asserts if isTaskIDValid returns
	 * TRUE.
	 */
	@Test
	public void isTaskIDValid() {

		us357Controller.setTaskIDandUserEmailWithRequestString(stringRequest1);
		assertTrue(us357Controller.isTaskIDValid());

	}

	/**
	 * Tests the setRequestDataString method by calling the method and asserting if
	 * the taskID corresponds to a real task and if the user email corresponds to a
	 * user. By returning true, it means that the parameters were set by the method
	 * setTaskIDandUserEmailWithRequestString
	 */
	@Test
	public void testSetRequestDataString() {

		us357Controller.setTaskIDandUserEmailWithRequestString(stringRequest1);
		assertTrue(us357Controller.isTaskIDValid());
		assertTrue(us357Controller.isEmailFromAUser());
	}

	/**
	 * Tests the AcceptRemovalRequestFromTask method which has to remove the user
	 * from the task(the taskCollaborator gets a finishDate) and removes the request
	 * from the pending task removal request list.
	 */
	@Test
	public void testAcceptRemovalRequestFromTask() {

		// PreAssert: the taskCollaborator correspondent to the user does not have a
		// finish date and the pendingRemovalListSize is 2
		us357Controller.setTaskIDandUserEmailWithRequestString(stringRequest1);
		int pendingRemovalListSizeBefore = projectA.getRemovalRequestsList().size();
		assertTrue((userRuiTaskCollaborator.getFinishDate() == null));
		assertEquals(2, pendingRemovalListSizeBefore);

		// AcceptRemovalRequestFromTask method call.
		us357Controller.acceptRemovalRequestFromTask(stringRequest1);

		// Assert: the taskCollaborator correspondent to the user has to have a finish
		// date and the pendinfRemovalListSize is 1
		int pendingRemovalListSizeAfter = projectA.getRemovalRequestsList().size();
		assertTrue((userRuiTaskCollaborator.getFinishDate() != null));
		assertEquals(1, pendingRemovalListSizeAfter);

	}

	/**
	 * Tests the CancelRemovalRequestFromTask method which does not removes the user
	 * from the task(the taskCollaborator finish date keeps equal to Null) and
	 * removes the request from the pending removal task list.
	 */
	@Test
	public void testCancelRemovalRequestFromTask() {

		// PreAssert- the taskCollaborator correspondent to the user does not have a
		// finish date and the pendingRemovalListSize is 2
		us357Controller.setTaskIDandUserEmailWithRequestString(stringRequest1);
		int pendingRemovalListSizeBefore = projectA.getRemovalRequestsList().size();
		assertTrue((userRuiTaskCollaborator.getFinishDate() == null));
		assertEquals(2, pendingRemovalListSizeBefore);

		// CancelRemvalRequestFromTask method call.
		us357Controller.cancelRemovalRequestFromTask();

		// Assert - the taskCollaborator still does'nt have a finish date and the
		// pendingRemovalList has one less request
		int pendingRemovalListSizeAfter = projectA.getRemovalRequestsList().size();
		assertTrue((userRuiTaskCollaborator.getFinishDate() == null));
		assertEquals(1, pendingRemovalListSizeAfter);

	}

}
