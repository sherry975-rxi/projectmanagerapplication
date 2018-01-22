package project.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.model.Company;
import project.model.Profile;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.TaskCollaborator;
import project.model.TaskRepository;
import project.model.User;

/**
 * 
 * @author Group 3
 *
 *
 *         tests to controller collectProjectsFromUserController
 */
public class CollectProjectsFromUserControllerTest {

	Company myCompany;

	User user1;
	User userAdmin;

	TaskRepository taskRepository;

	TaskCollaborator taskWorker1;

	ProjectCollaborator collab1;

	Project project;
	Project project2;

	@Before
	public void setUp() {
		// create company
		myCompany = Company.getTheInstance();

		// create user
		user1 = myCompany.getUsersRepository().createUser("Daniel", "daniel@gmail.com", "001", "collaborator",
				"910000000", "Rua", "2401-00", "Test", "Testo", "Testistan");

		// create user admin
		userAdmin = myCompany.getUsersRepository().createUser("João", "joao@gmail.com", "001", "Admin", "920000000",
				"Rua", "2401-00", "Test", "Testo", "Testistan");

		// add user to user list
		myCompany.getUsersRepository().addUserToUserRepository(user1);
		myCompany.getUsersRepository().addUserToUserRepository(userAdmin);

		// Creates one Project
		project = myCompany.getProjectsRepository().createProject("name3", "description4", userAdmin);
		project2 = myCompany.getProjectsRepository().createProject("name1", "description4", userAdmin);

		// add project to project repository
		myCompany.getProjectsRepository().addProjectToProjectRepository(project);
		myCompany.getProjectsRepository().addProjectToProjectRepository(project2);

		// create project collaborators
		collab1 = new ProjectCollaborator(user1, 2);

		// create taskRepository

		taskRepository = project.getTaskRepository();

		// create task workers
		taskWorker1 = new TaskCollaborator(collab1);

		// set user as collaborator
		user1.setUserProfile(Profile.COLLABORATOR);

		userAdmin.setUserProfile(Profile.COLLABORATOR);

		// add user to project team
		project.addProjectCollaboratorToProjectTeam(collab1);
		project2.addProjectCollaboratorToProjectTeam(collab1);

	}

	@After
	public void tearDown() {
		Company.clear();
		user1 = null;
		userAdmin = null;
		project = null;
		project2 = null;
		taskRepository = null;
		taskWorker1 = null;
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
		List<Project> projectsFromUser = new ArrayList<Project>();

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

		CollectProjectsFromUserController controller2 = new CollectProjectsFromUserController(this.userAdmin);

		// create list with cancelled task to compare
		List<Project> projectsFromUser = new ArrayList<Project>();

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

		// create list with cancelled task to compare
		List<String> projectsToString = new ArrayList<String>();

		projectsToString.add("[1] name3 - PM ");
		projectsToString.add("[2] name1 - PM ");

		assertEquals(projectsToString, controller3.getProjectsFromUserAndProjectManager());
	}
}
