package test.java.project.model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.project.model.Company;
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
		c1.getUsersList().clear();
		c1.setCounter(1);

		// User creation
		u1 = c1.createUser("Leonor", "leonor@gmail.com", "001", "Empregado", "930000000", "Rua Maria", "4444-444",
				"221234567", "Porto", "Portugal");
		u2 = c1.createUser("Maria", "maria@gmail.com", "002", "Empregado", "930000000", "Rua Maria", "4444-444",
				"221234567", "Porto", "Portugal");

		// create the project and set a user to Project manager
		p1 = c1.createProject("Teste", "blablabla", u2);

	}

	@AfterEach
	void tearDown() {
		Company c1 = null;
		User u1 = null;
		User u2 = null;
		Project p1 = null;
	}

	@Test

	// US301 - Como Diretor, quero poder criar um projeto e atribuir-lhe um Gestor
	// de projetos
	// Detalhes: Cada projeto deverá ter pelo menos as seguintes informações.

	void testUS301() {

		// add users to company
		c1.addUserToUserList(u1);
		c1.addUserToUserList(u2);

		// set user as Director
		u1.getProfile().setDirector();

		// set user to collaborator
		u2.getProfile().setCollaborator();

		boolean result = p1.isProjectManager(u2);
		int projectid = p1.getIdCode();
		assertTrue(result);
		assertEquals(1, projectid);
	}

}
