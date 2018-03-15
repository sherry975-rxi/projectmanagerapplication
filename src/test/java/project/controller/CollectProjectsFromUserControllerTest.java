package project.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.Services.ProjectService;
import project.Services.UserService;
import project.model.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * 
 * @author Group 3
 *
 *
 *         tests to controller collectProjectsFromUserController
 */
public class CollectProjectsFromUserControllerTest {

	private ProjectService projContainer = new ProjectService();
	UserService userContainer = new UserService();
	User user1;
	private User userAdmin;

	private ProjectCollaborator collab1;

	Project project;
	private Project project2;

	@Before
	public void setUp() {

		// create user
		user1 = userContainer.createUser("Daniel", "daniel@gmail.com", "001", "collaborator",
				"910000000", "Rua", "2401-00", "Test", "Testo", "Testistan");

		// create user admin
		userAdmin = userContainer.createUser("João", "joao@gmail.com", "001", "Admin", "920000000",
				"Rua", "2401-00", "Test", "Testo", "Testistan");

		// add user to user list
		userContainer.addUserToUserRepository(user1);
		userContainer.addUserToUserRepository(userAdmin);

		// Creates one Project
		project = projContainer.createProject("name3", "description4", userAdmin);
		project2 = projContainer.createProject("name1", "description4", userAdmin);

		// add project to project repository
		projContainer.addProjectToProjectContainer(project);
		projContainer.addProjectToProjectContainer(project2);

		// create project collaborators
		collab1 = new ProjectCollaborator(user1, 2);

		// create taskContainer

		// create task workers

		// set user as collaborator
		user1.setUserProfile(Profile.COLLABORATOR);

		userAdmin.setUserProfile(Profile.COLLABORATOR);

		// add user to project team
		project.addProjectCollaboratorToProjectTeam(collab1);
		project2.addProjectCollaboratorToProjectTeam(collab1);

	}

	@After
	public void tearDown() {
		projContainer = null;
		userContainer = null;
		user1 = null;
		userAdmin = null;
		project = null;
		project2 = null;
		collab1 = null;
	}

	/**
	 * this test verify if the list of projects is equals to the list created.
	 */
	@Test
	public final void testGetProjectsFromUser() {

		// create controller

		CollectProjectsFromUserController controller = new CollectProjectsFromUserController(this.user1);

		// create list with cancelled task to compare
		List<Project> projectsFromUser = new ArrayList<>();

		// add task to the list
		projectsFromUser.add(project);
		projectsFromUser.add(project2);

		assertEquals(projectsFromUser, controller.getProjectsFromUser());

	}

	/**
	 * this test verify if the list of projects is equals to the list created.
	 */
	@Test
	public final void testGetProjectsFromProjectManager() {

		// create controller

		CollectProjectsFromUserController controller2 = new CollectProjectsFromUserController(userAdmin);

		// create list with cancelled task to compare
		List<Project> projectsFromUser = new ArrayList<>();

		// add task to the list
		projectsFromUser.add(project);
		projectsFromUser.add(project2);

		assertEquals(projectsFromUser, controller2.getProjectsFromProjectManager());

	}

	/**
	 * 
	 */
	@Test
	public final void testGetProjectsFromUserAndProjectManager() {

		// create controller
		CollectProjectsFromUserController controller3 = new CollectProjectsFromUserController(this.userAdmin);

		// create an expected list with projects of userAdmin
		List<String> projectsToString = new ArrayList<>();

		projectsToString.add("[1] name3 - PM ");
		projectsToString.add("[2] name1 - PM ");
		//checks the similarity of expected list and the real list of userAdmin's projects
		assertEquals(projectsToString, controller3.getProjectsFromUserAndProjectManager());


		CollectProjectsFromUserController controller4 = new CollectProjectsFromUserController(this.user1);

		// create list with cancelled task to compare
		List<String> projectsToString2 = new ArrayList<>();

		projectsToString2.add("[1] name3");
		projectsToString2.add("[2] name1");
		assertEquals(projectsToString2, controller4.getProjectsFromUserAndProjectManager());



	}
}
