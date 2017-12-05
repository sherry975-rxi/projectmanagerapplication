package test.java.project.model;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.project.model.Company;
import main.java.project.model.Project;
import main.java.project.model.User;

public class US350Tests {

	Company c1;
	User u1;
	User u2;
	User u3;
	User u4;
	User u5;
	Project p1;

	@BeforeEach
	void setUp() {

		// Company creation
		c1 = Company.getTheInstance();
		// User creation
		u1 = c1.createUser("Daniel", "user2@gmail.com", "123", "Empregado", "930000000", "Rua Maria", "4444-444",
				"221234567", "Porto", "Portugal");
		u2 = c1.createUser("Joaquim", "joaquim@gmail.com", "123", "Empregado", "940000000", "Rua Maria", "4444-444",
				"221234567", "Porto", "Portugal");
		u3 = c1.createUser("Maria", "maria@gmail.com", "123", "Empregado", "930000000", "Rua Maria", "4444-444",
				"221234567", "Porto", "Portugal");

		u4 = c1.createUser("Leonor", "leonor@gmail.com", "123", "Empregado", "930000000", "Rua Maria", "4444-444",
				"221234567", "Porto", "Portugal");

		u5 = c1.createUser("Raquel", "raquel@gmail.com", "123", "Empregado", "930000000", "Rua Maria", "4444-444",
				"221234567", "Porto", "Portugal");
		// add users to company
		c1.addUserToUserList(u1);
		c1.addUserToUserList(u2);
		c1.addUserToUserList(u3);
		c1.addUserToUserList(u4);
		c1.addUserToUserList(u5);
		// set user as Director
		u1.getProfile().setDirector();
		// set user to collaborator
		u2.getProfile().setCollaborator();
		u3.getProfile().setCollaborator();
		u4.getProfile().setCollaborator();
		u5.getProfile().setCollaborator();
		// create the project and set a user to Project manager
		p1 = c1.createProject("Teste", "blablabla", u2);
		// add project to the Company Project list
		c1.addProjectToProjectList(p1);
	}

	@AfterEach
	void tearDown() {
		Company c1 = null;
		User u1 = null;
		User u2 = null;
		User u3 = null;
		User u4 = null;
		User u5 = null;
		Project p1 = null;
	}

	/**
	 * This test will respond to Us350:
	 * 
	 * US350 - As Project Manager, I want to be able to add collaborators to the
	 * project team.
	 * 
	 * 
	 * 
	 * 
	 * @author Group 3
	 */
	@Test
	public void test() {

		// add collaborators to project
		p1.addUserToProjectTeam(u3);
		p1.addUserToProjectTeam(u4);
		p1.addUserToProjectTeam(u5);

		// Creates a new list and adds user to that list, to compare with userList
		// inside ProjectTeam
		List<User> testUs350 = new ArrayList<User>();

		testUs350.add(u3);
		testUs350.add(u4);
		testUs350.add(u5);

		assertTrue(testUs350.equals(p1.getProjectTeam()));

	}

}
