package project.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project.model.Profile;
import project.model.Project;
import project.model.User;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AddColaboratorToProjectTeamController_Test {

	/**
	 * Tests US350v02
	 * 
	 * US350v02: - Como Gestor de projeto, quero poder adicionar colaboradores à
	 * equipa do projeto. Ao adicionar um colaborador tenho de definir: - custo do
	 * colaborador por unidade de esforço do projeto.
	 * 
	 * uses method getProjectTeam and addUserToProjectTeam
	 * 
	 */

	Company c1;
	User u1;
	User u2;
	User u3;
	User u4;
	User u5;
	Project p1;
	Project contextualProject;

	@Before
	public void setUp() {
		// create company and clear ProjectsRepository and UsersRepository

		c1 = Company.getTheInstance();

		// create users
		u1 = c1.getUsersContainer().createUser("Daniel", "user2@gmail.com", "123", "Empregado", "930000000",
				"Rua Maria", "4444-444", "221234567", "Porto", "Portugal");
		u2 = c1.getUsersContainer().createUser("Joaquim", "joaquim@gmail.com", "126", "Empregado", "940000000",
				"Rua Maria", "4444-444", "221234567", "Porto", "Portugal");
		u3 = c1.getUsersContainer().createUser("Maria", "maria@gmail.com", "127", "Empregado", "930000000",
				"Rua Maria", "4444-444", "221234567", "Porto", "Portugal");

		u4 = c1.getUsersContainer().createUser("Leonor", "leonor@gmail.com", "128", "Empregado", "930000000",
				"Rua Maria", "4444-444", "221234567", "Porto", "Portugal");

		u5 = c1.getUsersContainer().createUser("Raquel", "raquel@gmail.com", "129", "Empregado", "930000000",
				"Rua Maria", "4444-444", "221234567", "Porto", "Portugal");

		// add users to company
		c1.getUsersContainer().addUserToUserRepository(u1);
		c1.getUsersContainer().addUserToUserRepository(u2);
		c1.getUsersContainer().addUserToUserRepository(u3);
		c1.getUsersContainer().addUserToUserRepository(u4);
		c1.getUsersContainer().addUserToUserRepository(u5);

		// set user as Director
		u1.setUserProfile(Profile.DIRECTOR);

		// set user to collaborator
		u2.setUserProfile(Profile.COLLABORATOR);
		u3.setUserProfile(Profile.COLLABORATOR);
		u4.setUserProfile(Profile.COLLABORATOR);
		u5.setUserProfile(Profile.COLLABORATOR);

		// create the project and set a user to Project manager
		p1 = c1.getProjectsContainer().createProject("Teste", "blablabla", u2);
		p1.getProjectTeam().clear();
		p1.getTaskRepository().getProjectTaskRepository().clear();
		contextualProject = c1.getProjectsContainer().createProject("Teste", "blablabla", u2);

		// add project to project repository
		c1.getProjectsContainer().addProjectToProjectContainer(contextualProject);

	}

	@After
	public void tearDown() {
		Company.clear();
		u1 = null;
		u2 = null;
		u3 = null;
		u4 = null;
		u5 = null;
		p1 = null;
		contextualProject = null;
	}

	/**
	 * This test verify if the user was add to project team. first that the user
	 * isn't in the list and them added to project team, and confirm if the user is
	 * really there.
	 */
	@Test
	public void addColaboratorToProjectTeam_Test() {
		// create controller
		US351AddColaboratorToProjectTeamController controller = new US351AddColaboratorToProjectTeamController();

		assertFalse(contextualProject.isUserInProjectTeam(u1));

		// add user to project team
		controller.addUserToProjectTeam(u1, contextualProject, 0);

		assertTrue(contextualProject.isUserInProjectTeam(u1));

	}

}
