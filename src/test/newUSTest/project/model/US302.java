package test.newUSTest.project.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.project.model.Company;
import main.project.model.Project;
import main.project.model.User;

class US302 {

	Company myComp;
	User user1;
	User user2;
	Project proj1;

	@BeforeEach
	void setUp() {
		myComp = Company.getTheInstance();
		user1 = myComp.getUsersRepository().createUser("myuser", "myemail@myemail.com", "myid", "myfunction", "myphone",
				"mystreet", "myzipcode", "mycity", "mydistrict", "mycountry");
		user2 = myComp.getUsersRepository().createUser("myuser2", "myemail2@myemail.com", "myid2", "myfunction2",
				"myphone2", "mystreet2", "myzipcode2", "mycity2", "mydistrict2", "mycountry2");
		myComp.getProjectsRepository().addProjectToProjectRepository(
				myComp.getProjectsRepository().createProject("myProj", "dis ma project", user1));
		proj1 = myComp.getProjectsRepository().getAllProjects().get(0);
	}

	@AfterEach
	void tearDown() {
		myComp.clear();
		user1 = null;
		user2 = null;
		proj1 = null;
	}

	@Test
	final void testChangeProjectManager() {
		assertTrue(proj1.isProjectManager(user1));
		proj1.setProjectManager(user2);
		assertTrue(proj1.isProjectManager(user2));
	}

}
