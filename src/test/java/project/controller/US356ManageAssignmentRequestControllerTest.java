package project.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.Services.ProjectService;
import project.Services.UserService;
import project.model.*;
import project.model.taskstateinterface.Planned;
import project.model.taskstateinterface.StandBy;
import project.model.taskstateinterface.TaskStateInterface;

import java.util.Calendar;

import static org.junit.Assert.*;

public class US356ManageAssignmentRequestControllerTest {

	UserService userContainer;
	ProjectService projectContainer;

	String teamTesterName, teamTesterID;

	User managerTester, teamTester, teamPermanentMember;

	Project testProject;

	ProjectCollaborator teamTesterCollaborator;
	ProjectCollaborator teamPermanentCollaborator;

	Calendar estimatedStartDate;
	Calendar estimatedTaskDeadline;

	String taskDescription;
	String taskIDnumber;
	Task taskWithNoTeam, standByTask;
	TaskStateInterface testingTaskState, testingTaskStateB;

	US356ManageAssigmentRequestController assignmentRequestsController;

	@Before
	public void setUp() {
		
		// creates an UserContainer
		userContainer = new UserService();
						
		// creates a Project Container
		projectContainer = new ProjectService();
		
		// creates test users for a manager and collaborator.
		// declares the collaborator's relevant data as Strings to facilitate assertions
		managerTester = new User("menager", "manager@mail.mail", "11111", "function", "123456789");

		teamTesterName = "testMan";
		teamTesterID = "22222";

		teamTester = new User(teamTesterName, "collab@mail.mail", teamTesterID, "function", "123456789");

		teamPermanentMember = new User("Mr permanent", "permie@mail.mail", "33333", "placeholding", "98644");

		userContainer.addUserToUserRepository(managerTester);
		userContainer.addUserToUserRepository(teamTester);
		userContainer.addUserToUserRepository(teamPermanentMember);

		// creates a new test project, and adds the test Collaborator to the team
		testProject = new Project(1, "testing proj", "shoot rocket... again", managerTester);
		teamTesterCollaborator = new ProjectCollaborator(teamTester, 2000);
		teamPermanentCollaborator = new ProjectCollaborator(teamPermanentMember, 2000);
		testProject.addProjectCollaboratorToProjectTeam(teamTesterCollaborator);
		testProject.addProjectCollaboratorToProjectTeam(teamPermanentCollaborator);
		projectContainer.addProjectToProjectContainer(testProject);

		// creates two estimated dates and uses them to generate a task
		// declares strings for the task's ID and description to facilitate assertion
		estimatedStartDate = Calendar.getInstance();
		estimatedStartDate.add(Calendar.DAY_OF_YEAR, -10);

		estimatedTaskDeadline = Calendar.getInstance();
		estimatedTaskDeadline.add(Calendar.DAY_OF_YEAR, 10);

		taskDescription = "dont blow up rocket";
		taskWithNoTeam = testProject.getTaskService().createTask(taskDescription, 2000, estimatedStartDate,
				estimatedTaskDeadline, 200000);
		taskIDnumber = taskWithNoTeam.getTaskID();


		taskWithNoTeam.setTaskState(new Planned());

		testProject.createTaskAssignementRequest(teamTesterCollaborator, taskWithNoTeam);
		assignmentRequestsController = new US356ManageAssigmentRequestController(testProject);

		String taskDescriptionB = "do blow up rocket!";
		standByTask = testProject.getTaskService().createTask(taskDescriptionB, 2000, estimatedStartDate,
				estimatedTaskDeadline, 200000);


		standByTask.setTaskState(new StandBy());

		testProject.createTaskAssignementRequest(teamTesterCollaborator, standByTask);
	}

	@After
	public void breakDown() {
		userContainer = null;
		projectContainer = null;

		managerTester = null;

		teamPermanentMember = null;

		teamTesterName = null;
		teamTesterID = null;
		teamTester = null;

		testProject = null;

		teamTesterCollaborator = null;

		estimatedStartDate = null;
		estimatedTaskDeadline = null;

		taskDescription = null;
		taskIDnumber = null;
		taskWithNoTeam = null;

		testingTaskState = null;

		assignmentRequestsController = null;
	}

	// this test asserts the showAssignmentRequests() Method returns a list of
	// requests converted to String
	@Test
	public void showAssignmentRequestsTest() {

		String expectedAssignmentRequest = teamTesterName + "\n" + "collab@mail.mail" + "\n" + taskIDnumber + "\n"
				+ "dont blow up rocket";

		assertEquals(testProject.getPendingTaskAssignementRequests().size(), 2);
		assertEquals(assignmentRequestsController.showAllAssignmentRequests().size(), 2);

		assertTrue(assignmentRequestsController.showAllAssignmentRequests().get(0).equals(expectedAssignmentRequest));

	}

	/**
	 * Tests the approveAssignmentRequest
	 */
	@Test
	public void acceptAssignmentRequestTest() {

		// first, confirms that there is only one assignment request in the list
		assertEquals(testProject.getPendingTaskAssignementRequests().size(), 2);

		// Sets the controller request
		assignmentRequestsController.setSelectedAdditionRequest(0);

		// accepts the requests
		assignmentRequestsController.approveAssignmentRequest();

		// first, confirms that the list is now empty
		assertEquals(testProject.getPendingTaskAssignementRequests().size(), 1);

		// Now we test the same for a StandBy Task
		// Sets the controller request
		assignmentRequestsController.setSelectedAdditionRequest(0);

		// accepts the requests
		assignmentRequestsController.approveAssignmentRequest();

		// first, confirms that the list is now empty
		assertEquals(testProject.getPendingTaskAssignementRequests().size(), 0);

	}

	/**
	 * Tests the approveAssignmentRequest with a null object
	 */
	@Test
	public void acceptNullRequest() {

		// Sets the controller request as null
		testProject.getPendingTaskAssignementRequests().add(null);

		// Tries to approve and fails
		assertFalse(assignmentRequestsController.approveAssignmentRequest());

	}

	/**
	 * Tests the rejectAssignmentRequest with a null object
	 */
	@Test
	public void rejectNullRequest() {

		// Sets the controller request as null
		testProject.getPendingTaskAssignementRequests().add(null);

		// Tries to reject and fails
		assertFalse(assignmentRequestsController.rejectAssignmentRequest());

	}

	/**
	 * Tests the reject assignment request
	 */
	@Test
	public void rejectAssignmentRequestsTest() {

		// first, confirms if the requesting collaborator isn't in the team, and at
		// least one assignment request exists
		assertEquals(testProject.getPendingTaskAssignementRequests().size(), 2);
		assertFalse(taskWithNoTeam.isProjectCollaboratorActiveInTaskTeam(teamTesterCollaborator));
		assignmentRequestsController.setSelectedAdditionRequest(0);
		;

		// given the rejection method, confirms that the collaborator wasn't added to
		// the
		// team, and the request was deleted from the list
		assertTrue(assignmentRequestsController.rejectAssignmentRequest());
		assertFalse(taskWithNoTeam.isProjectCollaboratorActiveInTaskTeam(teamTesterCollaborator));
		assertEquals(testProject.getPendingTaskAssignementRequests().size(), 1);

	}
}