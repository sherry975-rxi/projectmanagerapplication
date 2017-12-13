package test.usTest.project.model;

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
		u1 = c1.getUsersRepository().createUser("Daniel", "user2@gmail.com", "123", "Empregado", "930000000",
				"Rua Maria", "4444-444", "221234567", "Porto", "Portugal");
		u2 = c1.getUsersRepository().createUser("Joaquim", "joaquim@gmail.com", "123", "Empregado", "940000000",
				"Rua Maria", "4444-444", "221234567", "Porto", "Portugal");
		u3 = c1.getUsersRepository().createUser("Maria", "maria@gmail.com", "123", "Empregado", "930000000",
				"Rua Maria", "4444-444", "221234567", "Porto", "Portugal");

		u4 = c1.getUsersRepository().createUser("Leonor", "leonor@gmail.com", "123", "Empregado", "930000000",
				"Rua Maria", "4444-444", "221234567", "Porto", "Portugal");

		u5 = c1.getUsersRepository().createUser("Raquel", "raquel@gmail.com", "123", "Empregado", "930000000",
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
		// add project to the Company Project list
		c1.getProjectsRepository().addProjectToProjectRepository(p1);

	}

	@AfterEach
	void tearDown() {
		c1.clear();
		u1 = null;
		u2 = null;
		u3 = null;
		u4 = null;
		u5 = null;
		p1 = null;
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
		p1.addUserToProjectTeam(p1.createProjectCollaborator(u3, 50));
		p1.addUserToProjectTeam(p1.createProjectCollaborator(u4, 50));
		p1.addUserToProjectTeam(p1.createProjectCollaborator(u5, 50));

		// Creates a new list and adds user to that list, to compare with userList
		// inside ProjectTeam
		List<ProjectCollaborator> testUs350 = new ArrayList<ProjectCollaborator>();

		testUs350.add(p1.createProjectCollaborator(u3, 50));
		testUs350.add(p1.createProjectCollaborator(u4, 50));
		testUs350.add(p1.createProjectCollaborator(u5, 50));

		assertTrue(testUs350.equals(p1.getProjectTeam()));

	}

}
