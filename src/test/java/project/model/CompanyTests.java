/**
 * 
 */
package test.java.project.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.project.model.Address;
import main.java.project.model.Company;
import main.java.project.model.Profile;
import main.java.project.model.Project;
import main.java.project.model.ProjectRepository;
import main.java.project.model.Task;
import main.java.project.model.User;
import main.java.project.model.UserRepository;

/**
 * 
 * 
 * @author Group3
 *
 */

		
class CompanyTests {
	
	Company myCompany;
	Company companyB;
	UserRepository userRepository = new UserRepository();
	ProjectRepository projectRepository = new ProjectRepository();
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
		
		// instantiate users
		user1 = userRepository.createUser ("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000", "Rua", "2401-00",
						"Test", "Testo", "Testistan");
		user2 = userRepository.createUser("João", "joao@gmail.com", "001", "Admin", "920000000", "Rua", "2401-00",
						"Test", "Testo", "Testistan");
		user3 = userRepository.createUser ("DanielMM", "danielmm@gmail.com", "003", "collaborator", "910000000", "Rua", "2401-00",
						"Test", "Testo", "Testistan");
				
		user4 = userRepository.createUser ("DanielMM", "danielmmgmail.com", "003", "collaborator", "910000000", "Rua", "2401-00",
						"Test", "Testo", "Testistan");
		
		myCompany.getUsersRepository();		
		
		//user 1 and user 3 included in user repository
		userRepository.addUserToUserRepository(user1);
		userRepository.addUserToUserRepository(user2);		
				
		//instantiate projects		
		project1 = projectRepository.createProject("name3", "description4", user1);
		project2 = projectRepository.createProject("name3", "description4", user1);
		project3 = projectRepository.createProject("name3", "description4", user1);
		
		//project 1 and project 3 included in project repository
		projectRepository.addProjectToProjectRepository(project1);
		projectRepository.addProjectToProjectRepository(project3);
	}
	
		
	/**
	 * Test to verify that the Company has user repository
	 * @return 
	 */	
	@Test
	void testgetUserRepository() {
		
		assertEquals(myCompany.getUsersRepository().getAllUsersFromRepository().size(), 0);
		
	}
		
		
	/**
	 * Test to verify that the Company has project repository
	 */	
	@Test
	void testgetProjectRepository() {
	
		assertEquals(myCompany.getProjectsRepository().getAllProjects().size(), 0);

}	
}
