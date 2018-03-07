/**
 * 
 */
package project.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * 
 * 
 * @author Group3
 *
 */

public class CompanyTest {

	Company myCompany;
	Company companyB;
	UserContainer userContainer;
	ProjectContainer projectContainer;
	User user1;
	User user2;
	User user3;
	User user4;
	Project project1;
	Project project2;
	Project project3;

	@Before
	public void setUp() {

		myCompany = Company.getTheInstance();

		// instantiate users
		user1 = myCompany.getUsersContainer().createUser("Daniel", "daniel@gmail.com", "001", "collaborator",
				"910000000", "Rua", "2401-00", "Test", "Testo", "Testistan");
		user2 = myCompany.getUsersContainer().createUser("João", "joao@gmail.com", "001", "Admin", "920000000", "Rua",
				"2401-00", "Test", "Testo", "Testistan");
		user3 = myCompany.getUsersContainer().createUser("DanielMM", "danielmm@gmail.com", "003", "collaborator",
				"910000000", "Rua", "2401-00", "Test", "Testo", "Testistan");

		user4 = myCompany.getUsersContainer().createUser("DanielMM", "danielmmgmail.com", "003", "collaborator",
				"910000000", "Rua", "2401-00", "Test", "Testo", "Testistan");

		// user 1 and user 3 included in user repository
		myCompany.getUsersContainer().addUserToUserRepository(user1);
		myCompany.getUsersContainer().addUserToUserRepository(user2);

		// instantiate projects
		project1 = myCompany.getProjectsContainer().createProject("name3", "description4", user1);
		project2 = myCompany.getProjectsContainer().createProject("name3", "description4", user1);
		project3 = myCompany.getProjectsContainer().createProject("name3", "description4", user1);

		// project 1 and project 3 included in project repository
		myCompany.getProjectsContainer().addProjectToProjectContainer(project1);
		myCompany.getProjectsContainer().addProjectToProjectContainer(project3);
	}

	@After
	public void tearDown() {
		Company.clear();
		user1 = null;
		user2 = null;
		user3 = null;
		user4 = null;
		project1 = null;
		project2 = null;
		project3 = null;
		myCompany.getUsersContainer().getAllUsersFromUserContainer().clear();
		myCompany.getProjectsContainer().getAllProjectsfromProjectsContainer().clear();
	}

	/**
	 * Test to verify that the Company has user repository
	 * 
	 * @return
	 */
	@Test
	public void testgetUserRepository() {

		assertEquals(myCompany.getUsersContainer().getAllUsersFromUserContainer().size(), 2);

	}

	// /**
	// * Test to verify that the Company has project repository
	// */
	// @Test
	// public void testgetProjectContainer() {
	//
	// assertEquals(myCompany.getProjectsContainer().getAllProjectsfromProjectsContainer().size(), 2);
	//
	// }
}
