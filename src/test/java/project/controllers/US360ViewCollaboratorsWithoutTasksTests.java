package project.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.Task;
import project.model.User;
import project.services.ProjectService;
import project.services.TaskService;
import project.services.UserService;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan({ "project.services", "project.model", "project.controllers" })
public class US360ViewCollaboratorsWithoutTasksTests {

	@Autowired
	UserService userService;

	@Autowired
	ProjectService projectService;

	@Autowired
	TaskService taskService;

	@Autowired
	US360ViewCollaboratorsWithoutTasksController us360controller;

	User userManager;
	User activeCollaborator;
	User idleCollaborator;
	Project testStuff;
	ProjectCollaborator activeProjCollab;
	ProjectCollaborator idleProjCollab;
	Task taskWithTeam;
	Task taskWithNoTeam;

	String idleProjCollabInfo;

	@Before
	public void setUp() {

		// creates two users, one manager, and two collaborators
		userManager = userService.createUser("Daniel", "daniel@gmail.com", "001", "Porteiro", "920000000",
				"Testy Street", "2401-343", "Testburg", "Testo", "Testistan");
		activeCollaborator = userService.createUser("João", "joao@gmail.com", "002", "Admin", "930000000",
				"Testy Street", "2401-343", "Testburg", "Testo", "Testistan");
		idleCollaborator = userService.createUser("Lazy Boi", "nope@gmail.com", "003", "Slacker", "920000000",
				"Testy Street", "2401-343", "Testburg", "Testo", "Testistan");

		// saves users in database
		userService.addUserToUserRepositoryX(userManager);
		userService.addUserToUserRepositoryX(activeCollaborator);
		userService.addUserToUserRepositoryX(idleCollaborator);

		// creates a project

		testStuff = projectService.createProject("1", "Testing controllers", userManager);

		// save project "testStuff" in database
		projectService.addProjectToProjectContainer(testStuff);

		// Add both active and idle Collaborators to the project team
		activeProjCollab = testStuff.createProjectCollaborator(activeCollaborator, 400);
		idleProjCollab = testStuff.createProjectCollaborator(idleCollaborator, 420);

		// save the project team in database
		projectService.addProjectCollaborator(activeProjCollab);
		projectService.addProjectCollaborator(idleProjCollab);

		// creates two tasks and adds the to the project's task repository
		taskWithTeam = taskService.createTask("This one has a team", testStuff);
		taskWithNoTeam = taskService.createTask("This one does not", testStuff);

		// adds the active team member to a task, and creates an expected String of data
		// belonging to the idle team member
		taskWithTeam.addProjectCollaboratorToTask(activeProjCollab);

		idleProjCollabInfo = "003: Lazy Boi (nope@gmail.com; 920000000) - Slacker [COST/EFFORT: 420.0]";

	}

	/**
	 * 
	 * This test asserts the Utility method that converts a Project Collaborator
	 * information into a string for display is working correctly. Given the
	 * expected String generated during setup, it then generates the same output
	 * using the controllers's own method
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

		// calls the showCollaboratorsWithoutTasks() method from the controllers
		// then saves the output in a testing list
		List<String> testingList = us360controller.showCollaboratorsWithoutTasks(testStuff);

		// given that activeProjectCollaborator has a Task assigned
		// and idleProjectCollaborator has no tasks assigned
		// asserts both those conditions are correct
		assertFalse(taskService.isCollaboratorActiveOnAnyTask(idleProjCollab));
		assertTrue(taskService.isCollaboratorActiveOnAnyTask(activeProjCollab));

		// then, asserts the list of idle team members contains only one entry
		// and that the Idle Team member's information is inside the list
		assertEquals(testingList.size(), 1);
		assertTrue(testingList.contains("[1] \n" + idleProjCollabInfo));

		// finally, asserts the list produced by the controllers becomes empty only when
		// the Idle Team member is added to a task

		taskWithTeam.addProjectCollaboratorToTask(idleProjCollab);
		assertTrue(us360controller.showCollaboratorsWithoutTasks(testStuff).isEmpty());

		taskWithTeam.removeAllCollaboratorsFromTaskTeam();
		assertFalse(us360controller.showCollaboratorsWithoutTasks(testStuff).isEmpty());
	}

}
