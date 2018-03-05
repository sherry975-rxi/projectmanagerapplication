package sprint.one;

import org.junit.After;
import org.junit.Before;
import project.controller.US301CreateProjectController;
import project.model.Company;
import project.model.Profile;
import project.model.Project;
import project.model.User;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Grupo 3
 * 
 *         US301 - Como Diretor, quero poder criar um projeto e atribuir-lhe um
 *         Gestorde projetos Detalhes: Cada projeto deverá ter pelo menos as
 *         seguintes informações: Nome, descrição e Project Manager (user).
 *
 */
public class US301Tests {

	Company c1;
	User u1;
	User u2;
	Project p1;

	@Before
	public void setUp() {
		// Company creation
		c1 = Company.getTheInstance();

		// User creation
		u1 = c1.getUsersRepository().createUser("Leonor", "leonor@gmail.com", "001", "Empregado", "930000000",
				"Rua Maria", "4444-444", "221234567", "Porto", "Portugal");
		u2 = c1.getUsersRepository().createUser("Maria", "maria@gmail.com", "002", "Empregado", "930000000",
				"Rua Maria", "4444-444", "221234567", "Porto", "Portugal");

		// add users to company
		c1.getUsersRepository().addUserToUserRepository(u1);
		c1.getUsersRepository().addUserToUserRepository(u2);

		// set user as Director
		u1.setUserProfile(Profile.DIRECTOR);

		// set user to collaborator
		u2.setUserProfile(Profile.COLLABORATOR);

	}

	@After
	public void tearDown() {
		Company.clear();
		u1 = null;
		u2 = null;
		p1 = null;
	}

	/**
	 * This method asserts if the project is sucessfully created by pre-asserting
	 * that the project list is empty and then asserting that the project list has a
	 * new project.
	 * 
	 * Also it checks if the user u1 is the project manager.
	 */
	public void US301CreateProject() {

		// Empty List to compare to the project repository with no project inside
		List<Project> emptyProjectList = new ArrayList<Project>();

		// Asserts if the project list is empty
		assertEquals(emptyProjectList, c1.getProjectsRepository());

		// Creates the controller to create a project
		US301CreateProjectController createNewProject = new US301CreateProjectController();

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
