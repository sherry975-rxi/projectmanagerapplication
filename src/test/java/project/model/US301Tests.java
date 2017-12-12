package test.java.project.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.project.model.Company;
import main.java.project.model.Profile;
import main.java.project.model.Project;
import main.java.project.model.User;

class US301Tests {

	Company c1;
	User u1;
	User u2;
	Project p1;

	@BeforeEach
	void setUp() {
		// Company creation
		c1 = Company.getTheInstance();
		c1.getUsersRepository().getAllUsersFromRepository().clear();
		c1.getProjectsRepository().getAllProjects().clear();

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

		// create the project and set a user to Project manager
		p1 = c1.getProjectsRepository().createProject("Teste", "blablabla", u2);

	}

	@AfterEach
	void tearDown() {
		c1 = null;
		u1 = null;
		u2 = null;
		p1 = null;
	}

	@Test

	// US301 - Como Diretor, quero poder criar um projeto e atribuir-lhe um Gestor
	// de projetos
	// Detalhes: Cada projeto deverá ter pelo menos as seguintes informações.

	void testUS301ProjectManager() {

		boolean truth = p1.isProjectManager(u2);
		boolean wrong = p1.isProjectManager(u1);

		int projectid = p1.getIdCode();
		assertTrue(truth);
		assertFalse(wrong);
		assertEquals(1, projectid);
	}

}
