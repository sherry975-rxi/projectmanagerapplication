package project.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.Services.ProjectService;
import project.Services.UserContainerService;
import project.model.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests the US357CancelRemovalTaskrequestController
 * 
 * @author Group 3
 *
 */
public class US357CancelRemovalTaskRequestControllerTest {

	User userDaniel;
	User userRui;
	ProjectService projectContainer;
	UserContainerService userContainer;
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


		// Creates the Project Repository and User Repository
		projectContainer = new ProjectService();
		userContainer = new UserContainerService();

		// Creates the users
		userDaniel = userContainer.createUser("Daniel", "daniel@gmail.com", "1234", "Arquitecto", "967387654", "Rua",
				"3700", "Porto", "Porto", "Portugal");
		userRui = userContainer.createUser("Rui", "rui@gmail.com", "12345", "Arquitecto", "967387654", "Rua", "3800",
				"Porto", "Porto", "Portugal");
		// Adds users to the user repository
		userContainer.addUserToUserRepository(userDaniel);
		userContainer.addUserToUserRepository(userRui);

		// Creates the Project and adds it to the project repository
		projectA = projectContainer.createProject("Museu Serralves", "Projecto do Museu de Serralves", userDaniel);
		projectContainer.addProjectToProjectContainer(projectA);

		// Adds the user to the project team
		projectA.addUserToProjectTeam(userRui, 20);
		userRuiProjectCollaborator = projectA.findProjectCollaborator(userRui);

		// Creates the tasks and adds them to the projectTasks
		taskA = projectA.getTaskRepository().createTask("Implementar US100");
		projectA.getTaskRepository().addTaskToProject(taskA);
		taskB = projectA.getTaskRepository().createTask("Implementar US200");
		projectA.getTaskRepository().addTaskToProject(taskB);
		taskC = projectA.getTaskRepository().createTask("Implementar US300");
		projectA.getTaskRepository().addTaskToProject(taskC);

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
		us357Controller = new US357CancelRemovalTaskRequestController(projectA);

		// Creates the taskCollaborator
		userRuiTaskCollaborator = taskA.getTaskTeam().get(0);

	}

	@After
	public void tearDown() {

		projectA = null;
		userDaniel = null;
		userRui = null;
		projectContainer = null;
		userContainer = null;
		userRuiProjectCollaborator = null;
		taskA = null;
		taskB = null;
		taskC = null;
		stringRequest1 = null;
		stringRequest2 = null;
		userRuiTaskCollaborator = null;
		pendingRemovalRequests = null;
		us357Controller = null;

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
	 * Tests the AcceptRemovalRequestFromTask method which has to remove the user
	 * from the task(the taskCollaborator gets a finishDate) and removes the request
	 * from the pending task removal request list.
	 */
	@Test
	public void testAcceptRemovalRequestFromTask() {

		// PreAssert: the taskCollaborator correspondent to the user does not have a
		// finish date and the pendingRemovalListSize is 2
		us357Controller.setTaskIDandUserEmailWithRequestString(stringRequest1);
		int pendingRemovalListSizeBefore = projectA.getPendingTaskRemovalRequests().size();
		assertTrue((userRuiTaskCollaborator.getFinishDate() == null));
		assertEquals(2, pendingRemovalListSizeBefore);

		// AcceptRemovalRequestFromTask method call.
		us357Controller.acceptRemovalRequestFromTask();

		// Assert: the taskCollaborator correspondent to the user has to have a finish
		// date and the pendinfRemovalListSize is 1
		int pendingRemovalListSizeAfter = projectA.getPendingTaskRemovalRequests().size();
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
		int pendingRemovalListSizeBefore = projectA.getPendingTaskRemovalRequests().size();
		assertTrue((userRuiTaskCollaborator.getFinishDate() == null));
		assertEquals(2, pendingRemovalListSizeBefore);

		// CancelRemvalRequestFromTask method call.
		us357Controller.cancelRemovalRequestFromTask();

		// Assert - the taskCollaborator still does'nt have a finish date and the
		// pendingRemovalList has one less request
		int pendingRemovalListSizeAfter = projectA.getPendingTaskRemovalRequests().size();
		assertTrue((userRuiTaskCollaborator.getFinishDate() == null));
		assertEquals(1, pendingRemovalListSizeAfter);

	}

}
