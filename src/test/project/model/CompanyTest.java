/**
 * 
 */
package test.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.project.model.Company;
import main.java.project.model.Project;
import main.java.project.model.ProjectRepository;
import main.java.project.model.User;
import main.java.project.model.UserRepository;

/**
 * 
 * 
 * @author Group3
 *
 */

class CompanyTest {

	Company myCompany;
	Company companyB;
	UserRepository userRepository;
	ProjectRepository projectRepository;
	User user1;
	User user2;
	User user3;
	User user4;
	Project project1;
	Project project2;
	Project project3;

	@BeforeEach
	void setUp() {

		myCompany = Company.getTheInstance();
		myCompany.getUsersRepository().getAllUsersFromRepository().clear();
		myCompany.getProjectsRepository().getAllProjects().clear();

		// instantiate users
		user1 = myCompany.getUsersRepository().createUser("Daniel", "daniel@gmail.com", "001", "collaborator",
				"910000000", "Rua", "2401-00", "Test", "Testo", "Testistan");
		user2 = myCompany.getUsersRepository().createUser("João", "joao@gmail.com", "001", "Admin", "920000000", "Rua",
				"2401-00", "Test", "Testo", "Testistan");
		user3 = myCompany.getUsersRepository().createUser("DanielMM", "danielmm@gmail.com", "003", "collaborator",
				"910000000", "Rua", "2401-00", "Test", "Testo", "Testistan");

		user4 = myCompany.getUsersRepository().createUser("DanielMM", "danielmmgmail.com", "003", "collaborator",
				"910000000", "Rua", "2401-00", "Test", "Testo", "Testistan");

		// user 1 and user 3 included in user repository
		myCompany.getUsersRepository().addUserToUserRepository(user1);
		myCompany.getUsersRepository().addUserToUserRepository(user2);

		// instantiate projects
		project1 = myCompany.getProjectsRepository().createProject("name3", "description4", user1);
		project2 = myCompany.getProjectsRepository().createProject("name3", "description4", user1);
		project3 = myCompany.getProjectsRepository().createProject("name3", "description4", user1);

		// project 1 and project 3 included in project repository
		myCompany.getProjectsRepository().addProjectToProjectRepository(project1);
		myCompany.getProjectsRepository().addProjectToProjectRepository(project3);
	}

	@AfterEach
	void tearDown() {
		myCompany = null;
		user1 = null;
		user2 = null;
		user3 = null;
		user4 = null;
		project1 = null;
		project2 = null;
		project3 = null;
		projectRepository = null;
	}

	/**
	 * Test to verify that the Company has user repository
	 * 
	 * @return
	 */
	@Test
	void testgetUserRepository() {

		assertEquals(myCompany.getUsersRepository().getAllUsersFromRepository().size(), 2);

	}

	/**
	 * Test to verify that the Company has project repository
	 */
	@Test
	void testgetProjectRepository() {

		assertEquals(myCompany.getProjectsRepository().getAllProjects().size(), 2);

	}
}
