package sprint.two;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.model.Company;
import project.model.Project;
import project.model.User;

public class US302 {

	/**
	 * US302 - As a director, I want to be able to change a project's Project
	 * Manager.
	 */

	Company myComp;
	User user1;
	User user2;
	Project proj1;

	@Before
	public void setUp() {
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
	}

	@After
	public void tearDown() {
		myComp.clear();
		user1 = null;
		user2 = null;
		proj1 = null;
	}

	@Test
	public final void testChangeProjectManager() {
		assertTrue(proj1.isProjectManager(user1));
		proj1.setProjectManager(user2);
		assertTrue(proj1.isProjectManager(user2));
	}

}
