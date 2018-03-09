package project.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

public class TaskTeamRequestTests {

	User managerTester;

	String teamTesterName, teamTesterID;
	User teamTester, teamTester2;

	Project testProject;

	ProjectCollaborator teamTesterCollaborator;
	ProjectCollaborator teamTesterCollaboratorNull;
	ProjectCollaborator teamTesterCollaboratorOtherNull;

	ProjectCollaborator teamTesterOtherCollab;

	Calendar estimatedStartDate;
	Calendar estimatedTaskDeadline;

	String taskDescription;
	String taskIDnumber;
	Task chosenTask;
	Task nullTask;
	Task simpleTask;

	@Before
	public void setUp() {
		// creates test users for a manager and collaborator.
		// declares the collaborator's relevant data as Strings to facilitate assertions
		managerTester = new User("menager", "manager@mail.mail", "11111", "function", "123456789");

		teamTesterName = "testMan";
		teamTesterID = "22222";

		teamTester = new User(teamTesterName, "collab@mail.mail", teamTesterID, "function", "123456789");
		teamTester2 = new User("name2", "colla2b@mail.mail", teamTesterID, "function", "123456789");

		// creates a new test project, and adds the test Collaborator to the team
		testProject = new Project(1, "testing requests", "description4", managerTester);
		teamTesterCollaborator = new ProjectCollaborator(teamTester, 2000);
		teamTesterOtherCollab = new ProjectCollaborator(teamTester2, 2000);
		teamTesterCollaboratorNull = null;
		ProjectCollaborator teamTesterCollaboratorNull;

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

	// Given a Project Collaborator and task:
	// this test validates the data when a new Request is created

	@Test
	public void settersAndGetters() {

		TaskTeamRequest taskRequest = new TaskTeamRequest();
		int taskRequestId = 1;
		taskRequest.setId(taskRequestId);
		assertEquals(taskRequest.getId(), taskRequestId);

		taskRequest.setProjCollab(teamTesterOtherCollab);
		assertEquals(taskRequest.getProjCollab(), teamTesterOtherCollab);

		taskRequest.setProject(testProject);
		assertEquals(taskRequest.getProject(), testProject);

		taskRequest.setType(10);
		assertEquals(taskRequest.getType(), "N/A");

		taskRequest.setType(0);
		assertEquals(taskRequest.getType(), "Assignment");

		taskRequest.setType(1);
		assertEquals(taskRequest.getType(), "Removal");



	}
	@Test
	public void createRequest() {
		TaskTeamRequest firstRequest = new TaskTeamRequest(teamTesterCollaborator, chosenTask);

		assertTrue(firstRequest.getProjCollab().getUserFromProjectCollaborator().getName().equals(teamTesterName));
		assertTrue(firstRequest.getProjCollab().getUserFromProjectCollaborator().getIdNumber().equals(teamTesterID));
		assertFalse(firstRequest.getProjCollab().getUserFromProjectCollaborator().getIdNumber().equals(teamTesterName));

		assertTrue(firstRequest.getTask().getTaskID().equals(taskIDnumber));
		assertTrue(firstRequest.getTask().getDescription().equals(taskDescription));

		//Defining data for task "simpleTask"
		simpleTask = testProject.getTaskRepository().createTask(taskDescription, 2000, estimatedStartDate,
				estimatedTaskDeadline, 200000);
		simpleTask.setTaskBudget(20);
		//changing from task "chosenTask" to "simpleTask" in request "firstRequest"
		firstRequest.setTask(simpleTask);
		//getting the budget of the new task associated in firstRequest
		assertEquals(firstRequest.getTask().getTaskBudget(), 20);

	}

	// Given three requests (two equal and one different) validates if the Equals
	// override works as expected
	@Test
	public void testRequestEquals() {
		// given two Task Team Requests using the same collaborator and task
		// asserts if they're equal
		TaskTeamRequest firstRequest = new TaskTeamRequest(teamTesterCollaborator, chosenTask);
		TaskTeamRequest secondRequest = new TaskTeamRequest(teamTesterCollaborator, chosenTask);
		TaskTeamRequest thirdRequest = new TaskTeamRequest(teamTesterCollaboratorNull, chosenTask);
		TaskTeamRequest fourthRequest = new TaskTeamRequest(teamTesterOtherCollab, chosenTask);
		TaskTeamRequest fifthRequest = new TaskTeamRequest(teamTesterOtherCollab, chosenTask);
		TaskTeamRequest sixthRequest = new TaskTeamRequest(teamTesterCollaboratorNull, chosenTask);
		TaskTeamRequest seventhRequest = new TaskTeamRequest(teamTesterCollaboratorNull, nullTask);
		TaskTeamRequest eightRequest = new TaskTeamRequest(teamTesterCollaboratorNull, nullTask);

		assertTrue(firstRequest.equals(secondRequest));
		assertTrue(firstRequest.equals(secondRequest));
		assertFalse((firstRequest).equals(null));
		assertFalse((firstRequest).equals("anotherObject"));
		assertFalse((firstRequest).equals(thirdRequest));

		assertFalse((firstRequest).equals(fourthRequest));

		// given a different task, this test creates a new request
		// asserts the new request is different when compared to the first one
		Task differentTask = testProject.getTaskRepository().createTask(taskDescription, 2000, estimatedStartDate,
				estimatedTaskDeadline, 200000);
		TaskTeamRequest differentRequest = new TaskTeamRequest(teamTesterCollaborator, differentTask);

		assertFalse(firstRequest.equals(differentRequest));

		/*
		 * Sets Proj Collab to NULL
		 */
		teamTesterCollaborator = null;
		assertFalse(firstRequest.equals(fourthRequest));

		// tests with two nulls project collaborators
		assertTrue(thirdRequest.equals(sixthRequest));

		/*
		 * Asserts with a null task to check if returns false with a request with a not
		 * NULL task
		 */

		assertFalse(seventhRequest.equals(sixthRequest));

		/*
		 * Checks if it returns true when both requests have NULL tasks
		 */
		assertTrue(seventhRequest.equals(eightRequest));

	}

	@Test
	public void testStringRepresentation() {
		TaskTeamRequest request = new TaskTeamRequest(teamTesterCollaborator, chosenTask);
		String result = teamTesterName + "\n" + "collab@mail.mail" + "\n" + taskIDnumber + "\n" + taskDescription;
		assertTrue(request.viewStringRepresentation().equals(result));
	}

	@Test
	public void testHashCode() {
		/*
		 * Creates 4 taskRequests, two have the same atrributes, one has a null
		 * collaborator, and other a null task
		 * 
		 */

		TaskTeamRequest request = new TaskTeamRequest(teamTesterCollaborator, chosenTask);
		TaskTeamRequest request2 = new TaskTeamRequest(teamTesterCollaborator, chosenTask);
		TaskTeamRequest request3 = new TaskTeamRequest(teamTesterCollaboratorNull, chosenTask);
		TaskTeamRequest request4 = new TaskTeamRequest(teamTesterCollaboratorNull, nullTask);

		int requestHashCode = ((31*3 + teamTesterCollaborator.hashCode())) + chosenTask.hashCode();

		// Checks if both Hashcodes are the same
		assertTrue(request.hashCode() == request2.hashCode());
		assertTrue(request2.hashCode() == requestHashCode);

		// Collaborator is NULL, so it will return false
		assertFalse(request.hashCode() == request3.hashCode());

		// Task and collaborator are NULL, so it will return false
		assertFalse(request.hashCode() == request4.hashCode());
		assertFalse(request4.hashCode() == requestHashCode);

	}

}
