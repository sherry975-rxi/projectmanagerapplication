package userStoryTests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import code.Company;
import code.Project;
import code.User;

class US320Tests {

	Company c1;
	User u1;
	User u2;
	Project p1;
	Project p2;
	Project p3;
	Project p4;
	Project p5;
	List<Project> testUs320;

	@BeforeEach
	void setUp() {
		// Company creation
		c1 = Company.getTheInstance();
		c1.getUsersList().clear();
		c1.getProjectsList().clear();

		// User creation
		u1 = c1.createUser("Daniel", "daniel@gmail.com", "Director", "910000000", "Rua Bla", "BlaBla", "BlaBlaBla",
				"BlaBlaBlaBla", "Blalandia");
		u2 = c1.createUser("Rita", "rita@gmail.com", "Gestora de Projeto", "920000000", "Rua Bla", "BlaBla",
				"BlaBlaBla", "BlaBlaBlaBla", "Blalandia");

		// create the project and set a user to Project manager
		p1 = c1.createProject("Teste", "blablabla", u2);
		p2 = c1.createProject("Teste", "blablabla", u2);
		p3 = c1.createProject("Teste", "blablabla", u2);
		p4 = c1.createProject("Teste", "blablabla", u2);
		p5 = c1.createProject("Teste", "blablabla", u2);

		// Creates a new list and adds projects to that list, to compare with
		// projectList
		testUs320 = new ArrayList<Project>();

	}

	@AfterEach
	void tearDown() {
		Company c1 = null;
		User u1 = null;
		User u2 = null;
		Project p1 = null;
		Project p2 = null;
		Project p3 = null;
		Project p4 = null;
		Project p5 = null;
		List<Project> testUs320 = null;

	}

	@Test

	// US320 - Como Diretor, quero poder ver uma lista dos projetos ativos.

	void testUS320() {

		// add users to company
		c1.addUserToUserList(u1);
		c1.addUserToUserList(u2);

		// set user as Director
		u1.getProfile().setDirector();

		// add project to project list
		c1.addProjectToProjectList(p1);
		c1.addProjectToProjectList(p2);
		c1.addProjectToProjectList(p3);
		c1.addProjectToProjectList(p4);
		c1.addProjectToProjectList(p5);

		// set projects to active state
		p1.setProjectStatus(1);
		p2.setProjectStatus(1);
		p3.setProjectStatus(1);
		p4.setProjectStatus(1);
		p5.setProjectStatus(1);

		// add projects to projects list to create expected result
		testUs320.add(p1);
		testUs320.add(p2);
		testUs320.add(p3);
		testUs320.add(p4);
		testUs320.add(p5);

		assertTrue(testUs320.equals(c1.getActiveProjectsList()));
	}
}
