package project.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
import project.model.User;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan({ "project.services", "project.model", "project.controller" })

public class US351AddColaboratorToProjectTeamController_Test {

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

	@Autowired
	private ProjectService projContainer;
	@Autowired
	private UserService userContainer;
	private User u1;
	private User u2;
	private User u3;
	private User u4;
	private User u5;
	private Project p1;
	private Project contextualProject;
	@Autowired
	US351AddColaboratorToProjectTeamController controller;

	@Before
	public void setUp() {

		// create users
		u1 = userContainer.createUser("Daniel", "user2@gmail.com", "123", "Empregado", "930000000", "Rua Maria",
				"4444-444", "221234567", "Porto", "Portugal");
		u2 = userContainer.createUser("Joaquim", "joaquim@gmail.com", "126", "Empregado", "940000000", "Rua Maria",
				"4444-444", "221234567", "Porto", "Portugal");
		u3 = userContainer.createUser("Maria", "maria@gmail.com", "127", "Empregado", "930000000", "Rua Maria",
				"4444-444", "221234567", "Porto", "Portugal");

		u4 = userContainer.createUser("Leonor", "leonor@gmail.com", "128", "Empregado", "930000000", "Rua Maria",
				"4444-444", "221234567", "Porto", "Portugal");

		u5 = userContainer.createUser("Raquel", "raquel@gmail.com", "129", "Empregado", "930000000", "Rua Maria",
				"4444-444", "221234567", "Porto", "Portugal");

		// set user as Director
		u1.setUserProfile(Profile.DIRECTOR);

		// set user to collaborator
		u2.setUserProfile(Profile.COLLABORATOR);
		u3.setUserProfile(Profile.COLLABORATOR);
		u4.setUserProfile(Profile.COLLABORATOR);
		u5.setUserProfile(Profile.COLLABORATOR);

		// update Users
		userContainer.addUserToUserRepositoryX(u1);
		userContainer.addUserToUserRepositoryX(u2);
		userContainer.addUserToUserRepositoryX(u3);
		userContainer.addUserToUserRepositoryX(u4);
		userContainer.addUserToUserRepositoryX(u5);

		// create the project and set a user to Project manager
		p1 = projContainer.createProject("Teste", "blablabla", u2);

		contextualProject = projContainer.createProject("Teste", "blablabla", u2);

	}

	@After
	public void clear() {

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

		assertFalse(projContainer.isUserInProjectTeam(u1, contextualProject));

		// add user to project team
		controller.addUserToProjectTeam(u1, contextualProject, 0);

		assertTrue(projContainer.isUserInProjectTeam(u1, contextualProject));

	}

}
