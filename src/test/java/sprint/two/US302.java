package sprint.two;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project.model.Project;
import project.model.User;

import static org.junit.Assert.assertTrue;

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
		user1 = myComp.getUsersContainer().createUser("myuser", "myemail@myemail.com", "myid", "myfunction", "myphone",
				"mystreet", "myzipcode", "mycity", "mydistrict", "mycountry");
		user2 = myComp.getUsersContainer().createUser("myuser2", "myemail2@myemail.com", "myid2", "myfunction2",
				"myphone2", "mystreet2", "myzipcode2", "mycity2", "mydistrict2", "mycountry2");
		// create project
		myComp.getProjectsContainer().addProjectToProjectContainer(
				myComp.getProjectsContainer().createProject("myProj", "dis ma project", user1));
		proj1 = myComp.getProjectsContainer().getAllProjectsfromProjectsContainer().get(0);
	}

	@After
	public void tearDown() {
		Company.clear();
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
