/**
 * 
 */
package test.java.project.model;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.project.model.UserRepository;
import main.java.project.model.Address;
import main.java.project.model.User;

/**
 * @author Group3
 *
 */
class UserRepositoryTests {
	
	User user1;
	User user2;
	User user3;
	UserRepository userRep = new UserRepository();

	@BeforeEach
	void setUp() {
		
		// instantiate users
		user1 = userRep.createUser ("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000", "Rua", "2401-00",
				"Test", "Testo", "Testistan");
		user2 = userRep.createUser("João", "joao@gmail.com", "001", "Admin", "920000000", "Rua", "2401-00",
				"Test", "Testo", "Testistan");
		User user3 = new User ("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000");


	}

	@AfterEach
	void tearDown() {
		user1 = null;
		user2 = null;
		user3 = null;
		userRep = null;
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

		Address newAddress = user3.createAddress("Rua", "2401-00","Test", "Testo", "Testistan");
		user3.addAddress(newAddress);
		
		assertEquals(user3, user1);
		
	}
	
	/**
	 * Tests addition of users to the User Repository, if the user is missing from the repository
	 */
	
	@Test
	void testAddUserToUserRepository() {
		
		userRep.addUserToUserRepository(user1);
		
		assertTrue(userRep.isUserinUserRepository(user1));
		assertFalse(userRep.isUserinUserRepository(user2));
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
