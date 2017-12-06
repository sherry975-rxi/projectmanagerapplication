/**
 * 
 */
package test.java.project.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.project.model.Company;
import main.java.project.model.Profile;
import main.java.project.model.Project;
import main.java.project.model.ProjectRepository;
import main.java.project.model.Task;
import main.java.project.model.User;

/**
 * @author Group3
 *
 */
class UserRepositoryTests {
	
	User user1;
	User userAdmin;

	@BeforeEach
	void setUp() {
		// create user
		user1 = myCompany.createUser("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000", "Rua", "2401-00",
				"Test", "Testo", "Testistan");
		// create user admin
		userAdmin = myCompany.createUser("João", "joao@gmail.com", "001", "Admin", "920000000", "Rua", "2401-00",
				"Test", "Testo", "Testistan");
		// add user to user list
		myCompany.addUserToUserList(user1);
		myCompany.addUserToUserList(userAdmin);
		// set user as collaborator
		user1.setUserProfile(Profile.COLLABORATOR);
		userAdmin.setUserProfile(Profile.COLLABORATOR);
		// create project
		project = myCompany.getProjectRepository.createProject("name3", "description4", userAdmin);// !!!
		// create 4 tasks
		testTask = myCompany.getProjectRepository.getProject(project).getTaskRepository.createTask("Test dis agen pls");
		testTask2 = project.createTask("Test dis agen pls");
		testTask3 = project.createTask("Test moar yeh");
		testTask4 = project.createTask("TEST HARDER!");

	}

	@AfterEach
	void tearDown() {
		user1 = null;
			}

	/**
	 * Tests constructor for User Repository
	 */
	
	@Test
	void testUserRepository() {
		
	}
	
	/**
	 * Tests the creation of an instance of User
	 */
	
	@Test
	void testCreateUser() {
		
	}
	
	/**
	 * Tests addition of users to the User Repository, if the user is missing from the repository
	 */
	
	@Test
	void testAddUserToUserRepository() {
		
	}
	
	/**
	 * Tests the construction of a copy of the list of all users
	 */
	
	@Test
	void testGetAllUsersFromRepository() {
		
	}


	/**
	 * Tests if a given user already exists in a company
	 */
	@Test
	void testDoesUserExistInUserRepository() {
		
	}

	/**
	 * Tests the creation of a list of users with a common part of an e-mail.
	 */
	@Test
	void testSearchUsersByEmail() {
		
	}
	
	/**
	 * Tests the output of a users with searched full e-mail.
	 * 
	 */
	@Test
	void testGetUserByEmail() {
}
	
	/**
	 * Tests the creation of a list of users with a certain profile.
	 */
	@Test
	void testsearchUsersByProfile() {
}
}
