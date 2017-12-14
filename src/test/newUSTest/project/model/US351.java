package test.newUSTest.project.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.project.model.Company;
import main.project.model.Project;
import main.project.model.User;

/**
 * US351 - As a project Manager, I want to remove collaboratos from the project
 * team.
 */
class US351 {

	Company myComp;
	User user1;
	User user2;
	Project proj1;

	@BeforeEach
	void setUp() {
		// create company
		myComp = Company.getTheInstance();
		// create users
		user1 = myComp.getUsersRepository().createUser("myuser", "myemail@myemail.com", "myid", "myfunction", "myphone",
				"mystreet", "myzipcode", "mycity", "mydistrict", "mycountry");
		user2 = myComp.getUsersRepository().createUser("myuser2", "myemail2@myemail.com", "myid2", "myfunction2",
				"myphone2", "mystreet2", "myzipcode2", "mycity2", "mydistrict2", "mycountry2");
		// create project
		myComp.getProjectsRepository().addProjectToProjectRepository(
				myComp.getProjectsRepository().createProject("myProj", "dis ma project", user1));
		proj1 = myComp.getProjectsRepository().getAllProjects().get(0);
		// add user to project as project collaborator
		proj1.addUserRToProjectTeam(user2, 5);
	}

	@AfterEach
	void tearDown() {
		myComp.clear();
		user1 = null;
		user2 = null;
		proj1 = null;
	}

	@Test
	final void testRemoveProjectCollaborator() {
		assertTrue(proj1.containsUser(user2));
		proj1.removeCollaboratorFromProjectTeam(user2);
		assertFalse(proj1.containsUser(user2));

	}

}
