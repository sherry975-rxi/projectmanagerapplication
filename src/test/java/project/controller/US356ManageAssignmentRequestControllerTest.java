package project.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.model.Company;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.Task;
import project.model.User;
import project.model.taskstateinterface.Planned;
import project.model.taskstateinterface.StandBy;
import project.model.taskstateinterface.TaskStateInterface;

public class US356ManageAssignmentRequestControllerTest {

	Company spaceX;

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
		spaceX = Company.getTheInstance();
		// creates test users for a manager and collaborator.
		// declares the collaborator's relevant data as Strings to facilitate assertions
		managerTester = new User("menager", "manager@mail.mail", "11111", "function", "123456789");

		teamTesterName = "testMan";
		teamTesterID = "22222";

		teamTester = new User(teamTesterName, "collab@mail.mail", teamTesterID, "function", "123456789");

		teamPermanentMember = new User("Mr permanent", "permie@mail.mail", "33333", "placeholding", "98644");

		spaceX.getUsersRepository().addUserToUserRepository(managerTester);
		spaceX.getUsersRepository().addUserToUserRepository(teamTester);
		spaceX.getUsersRepository().addUserToUserRepository(teamPermanentMember);

		// creates a new test project, and adds the test Collaborator to the team
		testProject = new Project(1, "testing proj", "shoot rocket... again", managerTester);
		teamTesterCollaborator = new ProjectCollaborator(teamTester, 2000);
		teamPermanentCollaborator = new ProjectCollaborator(teamPermanentMember, 2000);
		testProject.addProjectCollaboratorToProjectTeam(teamTesterCollaborator);
		testProject.addProjectCollaboratorToProjectTeam(teamPermanentCollaborator);
		spaceX.getProjectsRepository().addProjectToProjectRepository(testProject);

		// creates two estimated dates and uses them to generate a task
		// declares strings for the task's ID and description to facilitate assertion
		estimatedStartDate = Calendar.getInstance();
		estimatedStartDate.add(Calendar.DAY_OF_YEAR, -10);

		estimatedTaskDeadline = Calendar.getInstance();
		estimatedTaskDeadline.add(Calendar.DAY_OF_YEAR, 10);

		taskDescription = "dont blow up rocket";
		taskWithNoTeam = testProject.getTaskRepository().createTask(taskDescription, 2000, estimatedStartDate,
				estimatedTaskDeadline, 200000);
		taskIDnumber = taskWithNoTeam.getTaskID();

		testingTaskState = new Planned(taskWithNoTeam);
		taskWithNoTeam.setTaskState(testingTaskState);

		testProject.createTaskAssignementRequest(teamTesterCollaborator, taskWithNoTeam);
		assignmentRequestsController = new US356ManageAssigmentRequestController(testProject);

		String taskDescriptionB = "do blow up rocket!";
		standByTask = testProject.getTaskRepository().createTask(taskDescriptionB, 2000, estimatedStartDate,
				estimatedTaskDeadline, 200000);

		testingTaskStateB = new StandBy(standByTask);
		standByTask.setTaskState(testingTaskStateB);

		testProject.createTaskAssignementRequest(teamTesterCollaborator, standByTask);
	}

	@After
	public void breakDown() {
		Company.clear();
		spaceX = null;

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

		assertEquals(testProject.getAssignmentRequestsList().size(), 2);
		assertEquals(assignmentRequestsController.showAllAssignmentRequests().size(), 2);

		assertTrue(assignmentRequestsController.showAllAssignmentRequests().get(0).equals(expectedAssignmentRequest));

	}

	/**
	 * Tests the approveAssignmentRequest
	 */
	@Test
	public void acceptAssignmentRequestTest() {

		// first, confirms that there is only one assignment request in the list
		assertEquals(testProject.getAssignmentRequestsList().size(), 2);

		// Sets the controller request
		assignmentRequestsController.setSelectedAdditionRequest(0);

		// accepts the requests
		assignmentRequestsController.approveAssignmentRequest();

		// first, confirms that the list is now empty
		assertEquals(testProject.getAssignmentRequestsList().size(), 1);

		// Now we test the same for a StandBy Task
		// Sets the controller request
		assignmentRequestsController.setSelectedAdditionRequest(0);

		// accepts the requests
		assignmentRequestsController.approveAssignmentRequest();

		// first, confirms that the list is now empty
		assertEquals(testProject.getAssignmentRequestsList().size(), 0);

	}

	/**
	 * Tests the approveAssignmentRequest with a null object
	 */
	@Test
	public void acceptNullRequest() {

		// Sets the controller request as null
		testProject.getAssignmentRequestsList().add(null);

		// Tries to approve and fails
		assertFalse(assignmentRequestsController.approveAssignmentRequest());

	}

	/**
	 * Tests the rejectAssignmentRequest with a null object
	 */
	@Test
	public void rejectNullRequest() {

		// Sets the controller request as null
		testProject.getAssignmentRequestsList().add(null);

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
		assertEquals(testProject.getAssignmentRequestsList().size(), 2);
		assertFalse(taskWithNoTeam.isProjectCollaboratorActiveInTaskTeam(teamTesterCollaborator));
		assignmentRequestsController.setSelectedAdditionRequest(0);
		;

		// given the rejection method, confirms that the collaborator wasn't added to
		// the
		// team, and the request was deleted from the list
		assertTrue(assignmentRequestsController.rejectAssignmentRequest());
		assertFalse(taskWithNoTeam.isProjectCollaboratorActiveInTaskTeam(teamTesterCollaborator));
		assertEquals(testProject.getAssignmentRequestsList().size(), 1);

	}
}