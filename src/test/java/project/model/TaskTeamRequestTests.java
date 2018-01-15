package project.model;

import static org.junit.Assert.fail;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class TaskTeamRequestTests {

	User managerTester;

	String teamTesterName, teamTesterID;
	User teamTester;

	Project testProject;

	ProjectCollaborator teamTesterCollaborator;

	Calendar estimatedStartDate;
	Calendar estimatedTaskDeadline;

	String taskDescription;
	String taskIDnumber;
	Task chosenTask;

	@Before
	public void setUp() {
		// creates test users for a manager and collaborator.
		// declares the collaborator's relevant data as Strings to facilitate assertions
		managerTester = new User("menager", "manager@mail.mail", "11111", "function", "123456789");

		teamTesterName = "testMan";
		teamTesterID = "22222";

		teamTester = new User(teamTesterName, "collab@mail.mail", teamTesterID, "function", "123456789");

		// creates a new test project, and adds the test Collaborator to the team
		testProject = new Project(1, "testing requests", "description4", managerTester);
		teamTesterCollaborator = new ProjectCollaborator(teamTester, 2000);
		testProject.addProjectCollaboratorToProjectTeam(teamTesterCollaborator);

		// creates two estimated dates and uses them to generate a task
		// declares strings for the task's ID and description to facilitate assertion
		estimatedStartDate = Calendar.getInstance();
		estimatedStartDate.add(Calendar.DAY_OF_YEAR, -10);

		estimatedTaskDeadline = Calendar.getInstance();
		estimatedTaskDeadline.add(Calendar.DAY_OF_YEAR, 10);

		taskDescription = "testing requests";
		chosenTask = testProject.getTaskRepository().createTask(taskDescription, 2000, estimatedStartDate,
				estimatedTaskDeadline, 200000);
		taskIDnumber = chosenTask.getTaskID();
	}

	@After
	public void breakDown() {
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
		chosenTask = null;
	}

	// TODO validate names of methods

	// Given a Project Collaborator and task:
	// this test validates the data when a new Request is created
	@Ignore
	@Test
	public void createRequest() {
		// TaskTeamRequest firstRequest= new TaskTeamRequest(teamTesterCollaborator,
		// chosenTask);
		//
		//
		// assertTrue(firstRequest.viewCollaboratorName().equals(teamTesterName));
		// assertTrue(firstRequest.viewCollaboratorID().equals(teamTesterID));
		// assertFalse(firstRequest.viewCollaboratorID().equals(teamTesterName));
		//
		// assertTrue(firstRequest.viewTaskID().equals(taskIDnumber));
		// assertTrue(firstRequest.viewTaskName().equals(taskDescription));
		//
		fail("Not yet implemented");
	}

	// TODO validate names of methods

	// Given three requests (two equal and one different) validates if the Equals
	// override works as expected
	@Ignore
	@Test
	public void testRequestEquals() {
		// // given two Task Team Requests using the same collaborator and task
		// // asserts if they're equal
		// TaskTeamRequest firstRequest= new TaskTeamRequest(teamTesterCollaborator,
		// chosenTask);
		// TaskTeamRequest secondRequest= new TaskTeamRequest(teamTesterCollaborator,
		// chosenTask);
		//
		// assertTrue(firstRequest.equals(secondRequest));
		// assertTrue(firstRequest.equals(secondRequest));
		//
		// // given a different task, this test creates a new request
		// // asserts the new request is different when compared to the first one
		// Task differentTask =
		// testProject.getTaskRepository().createTask(taskDescription, 2000,
		// estimatedStartDate,
		// estimatedTaskDeadline, 200000);
		// TaskTeamRequest differentRequest= new TaskTeamRequest(teamTesterCollaborator,
		// differentTask);
		//
		// assertFalse(firstRequest.equals(differentRequest));

		fail("Not yet implemented");

	}

}
