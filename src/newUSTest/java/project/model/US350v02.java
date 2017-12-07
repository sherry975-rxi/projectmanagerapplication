package newUSTest.java.project.model;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.project.model.Company;
import main.java.project.model.Profile;
import main.java.project.model.Project;
import main.java.project.model.ProjectCollaborator;
import main.java.project.model.User;

class US350v02 {
	Company c1;
	User u1;
	User u2;
	User u3;
	User u4;
	User u5;
	Project p1;

	@BeforeEach
	void setUp() {
		c1 = Company.getTheInstance();
		c1.getProjectsRepository().getAllProjects().clear();
		c1.getUsersRepository().getAllUsersFromRepository().clear();
		
		u1 = c1.getUsersRepository().createUser("Daniel", "user2@gmail.com", "123", "Empregado", "930000000", "Rua Maria", "4444-444",
				"221234567", "Porto", "Portugal");
		u2 = c1.getUsersRepository().createUser("Joaquim", "joaquim@gmail.com", "126", "Empregado", "940000000", "Rua Maria", "4444-444",
				"221234567", "Porto", "Portugal");
		u3 = c1.getUsersRepository().createUser("Maria", "maria@gmail.com", "127", "Empregado", "930000000", "Rua Maria", "4444-444",
				"221234567", "Porto", "Portugal");

		u4 = c1.getUsersRepository().createUser("Leonor", "leonor@gmail.com", "128", "Empregado", "930000000", "Rua Maria", "4444-444",
				"221234567", "Porto", "Portugal");

		u5 = c1.getUsersRepository().createUser("Raquel", "raquel@gmail.com", "129", "Empregado", "930000000", "Rua Maria", "4444-444",
				"221234567", "Porto", "Portugal");
		
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
	 */
	@Test
	public void test() {

		// add collaborators to project
		p1.addUserToProjectTeam(u3, 120);
		p1.addUserToProjectTeam(u4, 130);
		p1.addUserToProjectTeam(u5, 150);

		// add project to the Company Project list
		c1.getProjectsRepository().addProjectToProjectRepository(p1);
		
		//create project collabotors with u3, u4 and u5 users
		ProjectCollaborator collaborator1 = new ProjectCollaborator(u3, 120);
		ProjectCollaborator collaborator2 = new ProjectCollaborator(u4, 130);
		ProjectCollaborator collaborator3 = new ProjectCollaborator(u5, 150);
		
		
		// Creates a new list and adds user to that list, to compare with userList
		// inside ProjectTeam
		List<ProjectCollaborator> testUs350 = new ArrayList<ProjectCollaborator>();

		testUs350.add(collaborator1);
		testUs350.add(collaborator2);
		testUs350.add(collaborator3);
		
		//TODO override equals
		//assertEquals(testUs350, p1.getProjectTeam());
		assertEquals(testUs350.size(), p1.getProjectTeam().size());
		assertEquals(testUs350.get(0).getCollaboratorUserData(), p1.getProjectTeam().get(0).getCollaboratorUserData());
		assertEquals(testUs350.get(1).getCollaboratorUserData(), p1.getProjectTeam().get(1).getCollaboratorUserData());
		assertEquals(testUs350.get(2).getCollaboratorUserData(), p1.getProjectTeam().get(2).getCollaboratorUserData());

	}

}
