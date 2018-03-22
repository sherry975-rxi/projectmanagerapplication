package project.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import project.Services.ProjectService;
import project.Services.UserService;
import project.model.Profile;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.User;

/**
 * 
 * @author Group 3
 *
 *
 *         tests to controller collectProjectsFromUserController
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan(basePackages = { "project.Services", "project.controller", "project.model" })
public class CollectProjectsFromUserControllerTest {

	@Autowired
	ProjectService projContainer;
	@Autowired
	UserService userContainer;

	User user1;
	private User userAdmin;

	private ProjectCollaborator collab1;

	Project project;
	private Project project2;

	@Autowired
	CollectProjectsFromUserController controller;

	@Before
	public void setUp() {

		// create user
		user1 = userContainer.createUser("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000", "Rua",
				"2401-00", "Test", "Testo", "Testistan");

		// create user admin
		userAdmin = userContainer.createUser("João", "joao@gmail.com", "001", "Admin", "920000000", "Rua", "2401-00",
				"Test", "Testo", "Testistan");

		// set user as collaborator
		user1.setUserProfile(Profile.COLLABORATOR);

		userAdmin.setUserProfile(Profile.COLLABORATOR);

		// Creates one Project
		project = projContainer.createProject("name3", "description4", userAdmin);
		project2 = projContainer.createProject("name1", "description4", userAdmin);

		// create project collaborators
		collab1 = projContainer.createProjectCollaborator(user1, project, 2);
		projContainer.addProjectCollaborator(project2.createProjectCollaborator(user1, 2));

	}

	@After
	public void clear() {

		user1 = null;
		userAdmin = null;
		collab1 = null;
		project = null;
		project2 = null;

	}

	/**
	 * this test verify if the list of projects is equals to the list created.
	 */
	@Test
	public void testGetProjectsFromUserAndManager() {

		// create controller for user 1

		controller.setUser(this.user1);
		// create list with cancelled task to compare
		List<Project> projectsFromUser = new ArrayList<>();

		// add task to the list
		projectsFromUser.add(project);
		projectsFromUser.add(project2);

		assertEquals(projectsFromUser, controller.getProjectsFromUser());

		List<String> projectsToString2 = new ArrayList<>();

		projectsToString2.add("[" + project.getId() + "] name3");
		projectsToString2.add("[" + project2.getId() + "] name1");
		assertEquals(projectsToString2, controller.getProjectsFromUserAndProjectManager());

		// create controller

		controller.setUser(userAdmin);

		// create list with cancelled task to compare
		List<Project> projectsFromManager = new ArrayList<>();

		// add task to the list
		projectsFromManager.add(project);
		projectsFromManager.add(project2);

		assertEquals(projectsFromManager, controller.getProjectsFromProjectManager());

		// create an expected list with projects of userAdmin
		List<String> projectsToString = new ArrayList<>();

		projectsToString.add("[" + project.getId() + "] name3 - PM");
		projectsToString.add("[" + project2.getId() + "] name1 - PM");
		// checks the similarity of expected list and the real list of userAdmin's
		// projects
		assertEquals(projectsToString, controller.getProjectsFromUserAndProjectManager());

	}
}
