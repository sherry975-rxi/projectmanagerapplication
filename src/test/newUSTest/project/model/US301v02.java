package test.newUSTest.project.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.project.model.Company;
import main.project.model.Profile;
import main.project.model.Project;
import main.project.model.User;

class US301v02 {
	
	/**
	* US301v02 Test - Como Diretor, quero poder criar um projeto e atribuir-lhe um Gestor de projetos.
	* Além dos elementos anteriores, devo poder definir:
	*	- unidade de esforço a usar no projeto (horas ou PM - Pessoa Mês, mas pode haver outras no futuro)
	*	- orçamento global do projeto
	* 
	*/
	
	Company c1;
	User u1;
	User u2;
	Project p1;

	@BeforeEach
	void setUp() {
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

		// create the project and set a user to Project manager
		p1 = c1.getProjectsRepository().createProject("Teste", "blablabla", u2);

	}

	@AfterEach
	void tearDown() {
		c1.clear();
		u1 = null;
		u2 = null;
		p1 = null;
	}

	@Test

	// US301 - Como Diretor, quero poder criar um projeto e atribuir-lhe um Gestor
	// de projetos

	void testIfTheUserIsProjectManager() {

		boolean truth = p1.isProjectManager(u2);
		boolean wrong = p1.isProjectManager(u1);

		int projectid = p1.getIdCode();
		assertTrue(truth);
		assertFalse(wrong);
		assertEquals(1, projectid);
	}

}
