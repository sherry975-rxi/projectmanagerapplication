package sprint.two;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project.model.Company;
import project.model.Project;
import project.model.User;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * US351 - As a project Manager, I want to remove collaboratos from the project
 * team.
 */
public class US351 {

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
		// add user to project as project collaborator
		proj1.addUserToProjectTeam(user2, 5);
	}

	@After
	public void tearDown() {
		Company.clear();
		user1 = null;
		user2 = null;
		proj1 = null;
	}

	@Test
	public final void testRemoveProjectCollaborator() {
		assertTrue(proj1.isUserActiveInProject(user2));
		proj1.removeProjectCollaboratorFromProjectTeam(user2);
		assertFalse(proj1.isUserActiveInProject(user2));

	}

}
