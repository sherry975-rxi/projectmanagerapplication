package test.newUSTest.project.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.project.model.Company;
import main.project.model.Profile;
import main.project.model.Project;
import main.project.model.ProjectCollaborator;
import main.project.model.User;

class US350v02 {

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

	@BeforeEach
	void setUp() {
		// create company and clear ProjectRepository and UsersRepository
		c1.clear();
		c1 = Company.getTheInstance();

		// create users
		u1 = c1.getUsersRepository().createUser("Daniel", "user2@gmail.com", "123", "Empregado", "930000000",
				"Rua Maria", "4444-444", "221234567", "Porto", "Portugal");
		u2 = c1.getUsersRepository().createUser("Joaquim", "joaquim@gmail.com", "126", "Empregado", "940000000",
				"Rua Maria", "4444-444", "221234567", "Porto", "Portugal");
		u3 = c1.getUsersRepository().createUser("Maria", "maria@gmail.com", "127", "Empregado", "930000000",
				"Rua Maria", "4444-444", "221234567", "Porto", "Portugal");

		u4 = c1.getUsersRepository().createUser("Leonor", "leonor@gmail.com", "128", "Empregado", "930000000",
				"Rua Maria", "4444-444", "221234567", "Porto", "Portugal");

		u5 = c1.getUsersRepository().createUser("Raquel", "raquel@gmail.com", "129", "Empregado", "930000000",
				"Rua Maria", "4444-444", "221234567", "Porto", "Portugal");

		// add users to company
		c1.getUsersRepository().addUserToUserRepository(u1);
		c1.getUsersRepository().addUserToUserRepository(u2);
		c1.getUsersRepository().addUserToUserRepository(u3);
		c1.getUsersRepository().addUserToUserRepository(u4);
		c1.getUsersRepository().addUserToUserRepository(u5);

		// set user as Director
		u1.setUserProfile(Profile.DIRECTOR);

		// set user to collaborator
		u2.setUserProfile(Profile.COLLABORATOR);
		u3.setUserProfile(Profile.COLLABORATOR);
		u4.setUserProfile(Profile.COLLABORATOR);
		u5.setUserProfile(Profile.COLLABORATOR);

		// create the project and set a user to Project manager
		p1 = c1.getProjectsRepository().createProject("Teste", "blablabla", u2);
		p1.getProjectTeam().clear();
		p1.getTaskRepository().getProjectTaskList().clear();
	}

	@AfterEach
	void tearDown() {
		c1 = null;
		u1 = null;
		u2 = null;
		u3 = null;
		u4 = null;
		u5 = null;
		p1 = null;
	}

	@Test
	public void test() {

		// tests that project manager is u2 and not other user (for example u4)
		assertTrue(p1.isProjectManager(u2));
		assertFalse(p1.isProjectManager(u4));

		// create project collabotors with u3, u4 and u5 users
		ProjectCollaborator collaborattor1 = p1.createProjectCollaborator(u3, 120);
		ProjectCollaborator collaborattor2 = p1.createProjectCollaborator(u4, 130);
		ProjectCollaborator collaborattor3 = p1.createProjectCollaborator(u5, 150);

		// add collaborators to project
		p1.addUserToProjectTeam(collaborattor1);
		p1.addUserToProjectTeam(collaborattor2);
		p1.addUserToProjectTeam(collaborattor3);

		// add project to the Company Project list
		c1.getProjectsRepository().addProjectToProjectRepository(p1);

		// Creates a new list and adds user to that list, to compare with userList
		// inside ProjectTeam
		List<ProjectCollaborator> testUs350 = new ArrayList<ProjectCollaborator>();

		testUs350.add(collaborattor1);
		testUs350.add(collaborattor2);
		testUs350.add(collaborattor3);

		assertEquals(testUs350, p1.getProjectTeam());
	}

}
