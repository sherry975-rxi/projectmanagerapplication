package project.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.Task;
import project.model.User;

import java.util.List;

import static org.junit.Assert.*;

public class US360ViewCollaboratorsWithoutTasksTests {

	User userManager;
	User activeCollaborator;
	User idleCollaborator;
	Project testStuff;
	ProjectCollaborator activeProjCollab;
	ProjectCollaborator idleProjCollab;
	Task taskWithTeam;
	Task taskWithNoTeam;

	US360ViewCollaboratorsWithoutTasksController us360controller;

	String idleProjCollabInfo;

	@Before
	public void setUp() {

		// creates two users, one manager, and two collaborators
		userManager = new User("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000");
		activeCollaborator = new User("João", "joao@gmail.com", "002", "Admin", "930000000");
		idleCollaborator = new User("Lazy Boi", "nope@gmail.com", "003", "Slacker", "920000000");

		// creates a project and adds both active and idle Collaborators to the team
		testStuff = new Project(1, "Testing controllers", "for great testing", userManager);
		activeProjCollab = testStuff.createProjectCollaborator(activeCollaborator, 400);
		idleProjCollab = testStuff.createProjectCollaborator(idleCollaborator, 420);
		testStuff.addProjectCollaboratorToProjectTeam(activeProjCollab);
		testStuff.addProjectCollaboratorToProjectTeam(idleProjCollab);

		// creates two tasks and adds the to the project's task repository
		taskWithTeam = testStuff.getTaskRepository().createTask("This one has a team");
		taskWithNoTeam = testStuff.getTaskRepository().createTask("This one does not");
		testStuff.getTaskRepository().addProjectTask(taskWithTeam);
		testStuff.getTaskRepository().addProjectTask(taskWithNoTeam);

		// adds the active team member to a task, and creates an expected String of data
		// belonging to the idle team member
		taskWithTeam.addProjectCollaboratorToTask(activeProjCollab);

		idleProjCollabInfo = "003: Lazy Boi (nope@gmail.com; 920000000) - Slacker [COST/EFFORT: 420]";

		us360controller = new US360ViewCollaboratorsWithoutTasksController(testStuff);
	}

	@After
	public void tearDown() {
		userManager = null;
		activeCollaborator = null;
		idleCollaborator = null;

		activeProjCollab = null;
		idleProjCollab = null;
		taskWithTeam = null;
		taskWithNoTeam = null;

		us360controller = null;

	}

	/**
	 * 
	 * This test asserts the Utility method that converts a Project Collaborator
	 * information into a string for display is working correctly. Given the
	 * expected String generated during setup, it then generates the same output
	 * using the controller's own method
	 * 
	 * Then, compares both expected and actual String data and asserts true
	 * 
	 */
	@Test
	public void testCollaboratorToString() {

		String testResult = us360controller.collaboratorDataAsString(idleProjCollab);

		assertTrue(testResult.equals(idleProjCollabInfo));
	}

	/**
	 * 
	 * This test asserts the showCollaboratorsWithoutTasks() method works correctly.
	 * Given only one idle team member, assert the resulting list contains ONE
	 * entry, and that it matches "idleProjCollaborator"'s personal inforamtion.
	 * 
	 * Then, confirms that list becomes empty only when "idleProjCollaborator" is
	 * assigned a task
	 * 
	 */
	@Test
	public void testListIdleProjectCollaborators() {

		// calls the showCollaboratorsWithoutTasks() method from the controller
		// then saves the output in a testing list
		List<String> testingList = us360controller.showCollaboratorsWithoutTasks();

		// given that activeProjectCollaborator has a Task assigned
		// and idleProjectCollaborator has no tasks assigned
		// asserts both those conditions are correct
		assertFalse(testStuff.getTaskRepository().isCollaboratorActiveOnAnyTask(idleProjCollab));
		assertTrue(testStuff.getTaskRepository().isCollaboratorActiveOnAnyTask(activeProjCollab));

		// then, asserts the list of idle team members contains only one entry
		// and that the Idle Team member's information is inside the list
		assertEquals(testingList.size(), 1);
		assertTrue(testingList.contains("[1] \n" + idleProjCollabInfo));

		// finally, asserts the list produced by the controller becomes empty only when
		// the Idle Team member is added to a task

		taskWithTeam.addProjectCollaboratorToTask(idleProjCollab);
		assertTrue(us360controller.showCollaboratorsWithoutTasks().isEmpty());

		taskWithTeam.removeAllCollaboratorsFromTaskTeam();
		assertFalse(us360controller.showCollaboratorsWithoutTasks().isEmpty());
	}

}
