package project.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import project.Services.ProjectService;
import project.Services.TaskService;
import project.Services.UserService;
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
@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan({ "project.services", "project.model", "project.controller" })
public class US357CancelRemovalTaskRequestControllerTest {

	@Autowired
	US357CancelRemovalTaskRequestController us357Controller;
	@Autowired
	ProjectService projectService;
	@Autowired
	UserService userService;
	@Autowired
	TaskService taskService;

	User userDaniel;
	User userRui;
	Project projectA;
	ProjectCollaborator userRuiProjectCollaborator;
	Task taskA;
	Task taskB;
	Task taskC;
	String stringRequest1;
	String stringRequest2;
	TaskCollaborator userRuiTaskCollaborator;
	List<String> pendingRemovalRequests;

	@Before
	public void setUp() {
		// Creates the users
		userDaniel = userService.createUser("Daniel", "daniel@gmail.com", "1234", "Arquitecto", "967387654", "Rua",
				"3700", "Porto", "Porto", "Portugal");
		userRui = userService.createUser("Rui", "rui@gmail.com", "12345", "Arquitecto", "967387654", "Rua", "3800",
				"Porto", "Porto", "Portugal");
		// Creates the Project and adds it to the project repository
		projectA = projectService.createProject("Museu Serralves", "Projecto do Museu de Serralves", userDaniel);
		//projectService.addProjectToProjectContainer(projectA);

		userRuiProjectCollaborator = projectA.createProjectCollaborator(userRui, 25);
		projectService.addProjectCollaborator(userRuiProjectCollaborator);


		// create tasks in project A
		taskA = taskService.createTask("task A", projectA);
		taskB = taskService.createTask("task B", projectA);
		taskC = taskService.createTask("task C", projectA);

		// Adds the project collaborator to the tasks
		taskA.addProjectCollaboratorToTask(userRuiProjectCollaborator);
		taskB.addProjectCollaboratorToTask(userRuiProjectCollaborator);
		taskC.addProjectCollaboratorToTask(userRuiProjectCollaborator);

		// Creates the removal requests from userRui and TaskA and TaskB
		taskA.createTaskRemovalRequest(userRuiProjectCollaborator);
		taskB.createTaskRemovalRequest(userRuiProjectCollaborator);

		// Creates the list of strings with the information of the pending removal
		// requests
		stringRequest1 = "Rui" + "\n" + "rui@gmail.com" + "\n" + "19.1" + "\n" + "task A";
		stringRequest2 = "Rui" + "\n" + "rui@gmail.com" + "\n" + "19.2" + "\n" + "task B";
		pendingRemovalRequests = new ArrayList<>();
		pendingRemovalRequests.add(stringRequest1);
		pendingRemovalRequests.add(stringRequest2);

		// Creates the taskCollaborator
		userRuiTaskCollaborator = taskA.getTaskTeam().get(0);

		us357Controller.setProject(projectA);

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
		us357Controller.setTask(taskA);
		int pendingRemovalListSizeBefore = taskA.getPendingTaskRemovalRequests().size();

		assertTrue((userRuiTaskCollaborator.getFinishDate() == null));
		assertEquals(1, pendingRemovalListSizeBefore);

		// AcceptRemovalRequestFromTask method call.
		us357Controller.acceptRemovalRequestFromTask();

		// Assert: the taskCollaborator correspondent to the user has to have a finish
		// date and the pendinfRemovalListSize is 1
		int pendingRemovalListSizeAfter = taskA.getPendingTaskRemovalRequests().size();
		assertTrue((userRuiTaskCollaborator.getFinishDate() != null));
		assertEquals(0, pendingRemovalListSizeAfter);

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
		us357Controller.setTask(taskA);
		int pendingRemovalListSizeBefore = taskA.getPendingTaskRemovalRequests().size();
		assertTrue((userRuiTaskCollaborator.getFinishDate() == null));
		assertEquals(1, pendingRemovalListSizeBefore);

		// CancelRemvalRequestFromTask method call.
		us357Controller.cancelRemovalRequestFromTask();

		// Assert - the taskCollaborator still does'nt have a finish date and the
		// pendingRemovalList has one less request
		int pendingRemovalListSizeAfter = taskA.getPendingTaskRemovalRequests().size();
		assertTrue((userRuiTaskCollaborator.getFinishDate() == null));
		assertEquals(0, pendingRemovalListSizeAfter);

	}

}
