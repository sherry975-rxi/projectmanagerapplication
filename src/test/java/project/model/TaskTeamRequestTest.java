package project.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TaskTeamRequestTest {
	
	@Mock
	ProjectCollaborator projCollab;
	
	@Mock
	Project proj;
	
	@Mock
	Task task;
	
	@Mock
	ProjectCollaborator projCollab2;
	
	@Mock
	Task task2;
	
	TaskTeamRequest request;
	
	ProjectCollaborator nullProjCollab = null;
	
	Task nullTask = null;
	
	@Before
	public void setUp() {
		request = new TaskTeamRequest(projCollab, task);
	}
	
	@After
	public void tearDown() {
		request = null;
	}
	
	@Test
	public void testSetAndGetTask() {
		assertEquals(task, request.getTask());
		
		request.setTask(task2);
		
		assertEquals(task2, request.getTask());
	}
	
	
	@Test
	public void testSetAndGetProjectCollaborator() {
		assertEquals(projCollab, request.getProjCollab());
		
		request.setProjCollab(projCollab2);
		
		assertEquals(projCollab2, request.getProjCollab());
	}

	@Test
	public void testSetAndGetType() {
		assertEquals("N/A", request.getType());
		
		request.setType(TaskTeamRequest.ASSIGNMENT);
		
		assertEquals("Assignment", request.getType());
		
		request.setType(TaskTeamRequest.REMOVAL);
		
		assertEquals("Removal", request.getType());
	}
	
	@Test
	public void testIsAssignmentRequest() {
		request.setType(TaskTeamRequest.ASSIGNMENT);
		
		assertTrue(request.isAssignmentRequest());
		
		assertFalse(request.isRemovalRequest());
	}

	@Test
	public void testIsRemovalRequest() {		
		request.setType(TaskTeamRequest.REMOVAL);
	
		assertTrue(request.isRemovalRequest());
	
		assertFalse(request.isAssignmentRequest());
	}
	
	// Given three requests (two equal and one different) validates if the Equals
		// override works as expected
		@Test
		public void testRequestEquals() {
			// given two Task Team Requests using the same collaborator and task
			// asserts if they're equal
			TaskTeamRequest firstRequest = new TaskTeamRequest(projCollab, task);
			TaskTeamRequest secondRequest = new TaskTeamRequest(projCollab, task);
			TaskTeamRequest thirdRequest = new TaskTeamRequest(nullProjCollab, task);
			TaskTeamRequest fourthRequest = new TaskTeamRequest(projCollab2, task);
			TaskTeamRequest fifthRequest = new TaskTeamRequest(projCollab2, task);
			TaskTeamRequest sixthRequest = new TaskTeamRequest(nullProjCollab, task);
			TaskTeamRequest seventhRequest = new TaskTeamRequest(nullProjCollab, nullTask);
			TaskTeamRequest eightRequest = new TaskTeamRequest(nullProjCollab, nullTask);

			assertTrue(firstRequest.equals(secondRequest));
			assertTrue(firstRequest.equals(secondRequest));
			assertFalse((firstRequest).equals(null));
			assertFalse((firstRequest).equals("anotherObject"));
			assertFalse((firstRequest).equals(thirdRequest));

			assertFalse((firstRequest).equals(fourthRequest));

			// given a different task, this test creates a new request
			// asserts the new request is different when compared to the first one

			TaskTeamRequest differentRequest = new TaskTeamRequest(projCollab, task2);

			assertFalse(firstRequest.equals(differentRequest));

			/*
			 * Sets Proj Collab to NULL
			 */
			projCollab = null;
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
		public void testHashCode() {
			/*
			 * Creates 4 taskRequests, two have the same atrributes, one has a null
			 * collaborator, and other a null task
			 * 
			 */

			TaskTeamRequest request = new TaskTeamRequest(projCollab, task);
			TaskTeamRequest request2 = new TaskTeamRequest(projCollab, task);
			TaskTeamRequest request3 = new TaskTeamRequest(nullProjCollab, task);
			TaskTeamRequest request4 = new TaskTeamRequest(nullProjCollab, nullTask);

			int requestHashCode = ((31*3 + projCollab.hashCode())) + task.hashCode();

			// Checks if both Hashcodes are the same
			assertTrue(request.hashCode() == request2.hashCode());
			assertTrue(request2.hashCode() == requestHashCode);

			// Collaborator is NULL, so it will return false
			assertFalse(request.hashCode() == request3.hashCode());

			// Task and collaborator are NULL, so it will return false
			assertFalse(request.hashCode() == request4.hashCode());
			assertFalse(request4.hashCode() == requestHashCode);

		}
		
		@Test
		public void testStringRepresentation() {
			
			User user = new User("Daniel", "daniel@gmail.com", "Id", "Função", "Telefone");
			
			ProjectCollaborator anotherProjCollab = new ProjectCollaborator(user, 5);
			
			Project proj = new Project("proj", "best description", user);
			
			Task anotherTask = new Task("best task", proj);
			
			TaskTeamRequest requestOther = new TaskTeamRequest(anotherProjCollab, anotherTask);
			String result = "Daniel" + "\n" + "daniel@gmail.com" + "\n" + task.getTaskID() + "\n" + "best task";
			assertTrue(result.equals(requestOther.viewStringRepresentation()));
		}

}
