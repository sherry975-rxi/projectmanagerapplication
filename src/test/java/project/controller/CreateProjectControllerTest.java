package project.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;

import project.model.Company;
import project.model.Profile;
import project.model.Project;
import project.model.User;

/**
 * @author Group 3
 * 
 *         Tests the Create Project Controller.
 *
 */
public class CreateProjectControllerTest {

	Company c1;
	User u1;

	@Before
	public void setUp() {

		// Company creation
		c1 = Company.getTheInstance();

		// User creation
		u1 = c1.getUsersRepository().createUser("Leonor", "leonor@gmail.com", "001", "Empregado", "930000000",
				"Rua Maria", "4444-444", "221234567", "Porto", "Portugal");
		// add users to company
		c1.getUsersRepository().addUserToUserRepository(u1);

		// set user as Director
		u1.setUserProfile(Profile.DIRECTOR);
	}

	@After
	public void tearDown() {
		Company.clear();
		u1 = null;
		u2 = null;
		p1 = null;
	}

	/**
	 * This method asserts if the project is sucessfully created by asserting that
	 * the project list is empty and then asserting that the project list has a new
	 * project.
	 * 
	 * Also it checks if the user u1 is the project manager.
	 */
	public void CreateProject() {

		// Empty List to compare to the project repository with no project inside
		List<Project> emptyProjectList = new ArrayList<Project>();

		// Asserts if the project list is empty
		assertEquals(emptyProjectList, c1.getProjectsRepository());

		// Creates the controller to create a project
		CreateProjectController createNewProject = new CreateProjectController();

		// Creates the project using the controller
		Project newProject = createNewProject.createProject("name", "description", u2);

		// List with a project to compare
		List<Project> projectList = new ArrayList<Project>();
		projectList.add(newProject);

		// Asserts if the project repository has the new project in it
		assertEquals(projectList, c1.getProjectsRepository());

		// Asserts if the user u1 is the project manager
		assertTrue(newProject.isProjectManager(u1));

	}
}
