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

public class ManageAssignmentRequestControllerTest {

	Company spaceX;

	User managerTester;

	String teamTesterName, teamTesterID;
	User teamTester;

	Project testProject;

	ProjectCollaborator teamTesterCollaborator;

	Calendar estimatedStartDate;
	Calendar estimatedTaskDeadline;

	String taskDescription;
	String taskIDnumber;
	Task taskWithNoTeam;

	ManageAssigmentRequestController assignmentRequestsController;

	@Before
	public void setUp() {
		Company.clear();
		spaceX = Company.getTheInstance();
		// creates test users for a manager and collaborator.
		// declares the collaborator's relevant data as Strings to facilitate assertions
		managerTester = new User("menager", "manager@mail.mail", "11111", "function", "123456789");

		teamTesterName = "testMan";
		teamTesterID = "22222";

		teamTester = new User(teamTesterName, "collab@mail.mail", teamTesterID, "function", "123456789");

		spaceX.getUsersRepository().addUserToUserRepository(managerTester);
		spaceX.getUsersRepository().addUserToUserRepository(teamTester);

		// creates a new test project, and adds the test Collaborator to the team
		testProject = new Project(1, "testing proj", "shoot rocket... again", managerTester);
		teamTesterCollaborator = new ProjectCollaborator(teamTester, 2000);
		testProject.addProjectCollaboratorToProjectTeam(teamTesterCollaborator);
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

		testProject.createTaskAssignementRequest(teamTesterCollaborator, taskWithNoTeam);
		assignmentRequestsController = new ManageAssigmentRequestController(testProject.getIdCode());

	}

	@After
	public void breakDown() {
		spaceX = null;

		managerTester = null;

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

		assignmentRequestsController = null;
	}

	// this test asserts the showAssignmentRequests() Method returns a list of
	// requests converted to String
	@Test
	public void showAssignmentRequestsTest() {
		System.out.println("====== Testing showAssignmentRequests() Method =======");
		System.out.println();

		String expectedAssignmentRequest = teamTesterName + "\n" + "collab@mail.mail" + "\n" + taskIDnumber + "\n"
				+ "dont blow up rocket";

		assertEquals(testProject.getAssignmentRequestsList().size(), 1);
		assertEquals(assignmentRequestsController.showAllAssignmentRequests().size(), 1);

		assertTrue(assignmentRequestsController.showAllAssignmentRequests().get(0).equals(expectedAssignmentRequest));

		// asserts that an invalid project ID will return an empty List instead of a
		// Null
		int invalidProjectID = 0;
		assignmentRequestsController = new ManageAssigmentRequestController(invalidProjectID);

		assertEquals(assignmentRequestsController.showAllAssignmentRequests().size(), 0);

		System.out.println("");
	}

	// given an existing project with at least one pending request
	@Test
	public void selectAssignmentRequestsTest() {
		System.out.println("====== Testing selectAssignmentRequests() Method =======");
		System.out.println("");

		// when the index number exists, asserts the request can be selected and printed
		// onto the console
		assertTrue(assignmentRequestsController.selectAssignmentRequest(0));

		// when the index number doesn't exist, asserts the selection method returns
		// false
		assertFalse(assignmentRequestsController.selectAssignmentRequest(5));
		System.out.println("");
	}

	// given an existing project with at least one pending request, attempts to
	// approve the request
	@Test
	public void approveAssignmentRequestsTest() {
		System.out.println("====== Testing approveAssignmentRequests() Method =======");
		System.out.println("");

		// first, confirms if the requesting collaborator isn't in the team, and at
		// least one assignment request exists
		assertEquals(testProject.getAssignmentRequestsList().size(), 1);
		assertFalse(taskWithNoTeam.isProjectCollaboratorActiveInTaskTeam(teamTesterCollaborator));

		// given the approval method, confirms that the collaborator was added to the
		// team, and the request was deleted from the list
		assertTrue(assignmentRequestsController.approveAssignmentRequest(0));
		assertTrue(taskWithNoTeam.isProjectCollaboratorActiveInTaskTeam(teamTesterCollaborator));
		assertEquals(testProject.getAssignmentRequestsList().size(), 0);

		// then, attempts to approve a request when none should exist
		assertFalse(assignmentRequestsController.approveAssignmentRequest(0));
		System.out.println("");
	}

	// given an existing project with at least one pending request, attempts to
	// reject the request
	@Test
	public void rejectAssignmentRequestsTest() {
		System.out.println("====== Testing rejectAssignmentRequests() Method =======");
		System.out.println("");

		// first, confirms if the requesting collaborator isn't in the team, and at
		// least one assignment request exists
		assertEquals(testProject.getAssignmentRequestsList().size(), 1);
		assertFalse(taskWithNoTeam.isProjectCollaboratorActiveInTaskTeam(teamTesterCollaborator));

		// given the rejection method, confirms that the collaborator wasn't added to
		// the
		// team, and the request was deleted from the list
		assertTrue(assignmentRequestsController.rejectAssignmentRequest(0));
		assertFalse(taskWithNoTeam.isProjectCollaboratorActiveInTaskTeam(teamTesterCollaborator));
		assertEquals(testProject.getAssignmentRequestsList().size(), 0);

		// then, attempts to reject a request when none should exist
		assertFalse(assignmentRequestsController.approveAssignmentRequest(0));
		System.out.println("");
	}

}
