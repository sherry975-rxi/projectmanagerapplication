/**
 * 
 */
package test.java.project.model;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.project.model.UserRepository;
import main.java.project.model.Address;
import main.java.project.model.Profile;
import main.java.project.model.User;

/**
 * @author Group3
 *
 */
class UserRepositoryTests {

	User user1;
	User user2;
	User user3;
	User user4;
	User user5;
	UserRepository userRep = new UserRepository();

	@BeforeEach
	void setUp() {

		// instantiate users
		user1 = userRep.createUser ("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000", "Rua", "2401-00",
				"Test", "Testo", "Testistan");
		user2 = userRep.createUser("João", "joao@gmail.com", "001", "Admin", "920000000", "Rua", "2401-00",
				"Test", "Testo", "Testistan");
		user4 = userRep.createUser ("DanielMM", "danielmm@gmail.com", "003", "collaborator", "910000000", "Rua", "2401-00",
				"Test", "Testo", "Testistan");
		
		user5 = userRep.createUser ("DanielMM", "danielmmgmail.com", "003", "collaborator", "910000000", "Rua", "2401-00",
				"Test", "Testo", "Testistan");
	}

	@AfterEach
	void tearDown() {
		user1 = null;
		user2 = null;
		user3 = null;
		user4 = null;
		user5 = null;
		userRep = null;
	}

	/**
	 * Tests constructor for User Repository
	 */

	@Test
	void testUserRepository() {

		List <User> testUserRep = new ArrayList<>();

		assertEquals(testUserRep, userRep.getAllUsersFromRepository());
	}

	/**
	 * Tests the creation of an instance of User (compares user1 created with createUser() and user3 created as a new object)
	 */

	@Test
	void testCreateUser() {

		User user3 = new User ("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000");
		Address newAddress = user3.createAddress("Rua", "2401-00","Test", "Testo", "Testistan");
		user3.addAddress(newAddress);

		assertTrue(user3.equals(user1));
	}
	/**
	 * Tests the creation of an instance of User (compares user1 created with createUser())
	 */

	@Test
	void testCreateUser_2() {
		User user3 = new User ("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000");
		Address newAddress = user3.createAddress("Rua", "2401-00","Test", "Testo", "Testistan");
		user3.addAddress(newAddress);		

		assertEquals(user3 , userRep.createUser("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000","Rua", "2401-00","Test", "Testo", "Testistan"));
	}

	/**
	 * Tests the construction of a copy of the list of all users
	 */

	@Test
	void testGetAllUsersFromRepository() {

		userRep.addUserToUserRepository(user1);
		userRep.addUserToUserRepository(user2);

		List <User> testUsersInRep = new ArrayList<>();
		testUsersInRep.add(user1);
		testUsersInRep.add(user2);

		assertEquals(testUsersInRep, userRep.getAllUsersFromRepository());
	}

	/**
	 * Tests addition of users to the User Repository, if the user is missing from the repository
	 */
	@Test
	void testAddUserToUserRepository() {

		//adds user twice
		userRep.addUserToUserRepository(user1);
		userRep.addUserToUserRepository(user1);

		assertTrue(user1.equals(userRep.getAllUsersFromRepository().get(0)));
		assertEquals(1,userRep.getAllUsersFromRepository().size()); //if it would add even if the user was already on the list, the size of the repository would be 2 instead of 1
	}

	/**
	 * Tests if a given user already exists in a repository
	 */
	@Test
	void testIsUserinUserRepository() {
		
		userRep.addUserToUserRepository(user1);
		
		
		assertTrue(userRep.isUserinUserRepository(user1));
		assertFalse(userRep.isUserinUserRepository(user2));
	}

	/**
	 * Tests the creation of a list of users with a common part of an e-mail.
	 */
	@Test
	void testSearchUsersByEmail() {
		
		userRep.addUserToUserRepository(user1);
		userRep.addUserToUserRepository(user2);
		userRep.addUserToUserRepository(user4);
		
		List <User> testUsersWithEmail = new ArrayList<>();
		testUsersWithEmail.add(user1);
		testUsersWithEmail.add(user4);
		
		assertEquals(testUsersWithEmail, userRep.searchUsersByEmail("daniel"));
	}

	/**
	 * Tests the output of a users with searched full e-mail.
	 * 
	 */
	@Test
	void testGetUserByEmail() {

		userRep.addUserToUserRepository(user1);
		userRep.addUserToUserRepository(user2);
		userRep.addUserToUserRepository(user4);
		
		assertTrue(user1.equals(userRep.getUserByEmail("daniel@gmail.com")));
		assertTrue(user4.equals(userRep.getUserByEmail("danielmm@gmail.com")));
		assertFalse(user1.equals(userRep.getUserByEmail("danielmm@gmail.com")));
	}

	/**
	 * Tests the creation of a list of users with a certain profile.
	 */
	@Test
	void testsearchUsersByProfile() {
		
		userRep.addUserToUserRepository(user1);
		userRep.addUserToUserRepository(user2);
		userRep.addUserToUserRepository(user4);
		
		user2.setUserProfile(Profile.DIRECTOR);
		
		List <User> testUsersWithProfileVisitor = new ArrayList<>();
		testUsersWithProfileVisitor.add(user1);
		testUsersWithProfileVisitor.add(user4);
		
		List <User> testUsersWithProfileDirector = new ArrayList<>();
		testUsersWithProfileDirector.add(user2);
		
		assertEquals(testUsersWithProfileVisitor, userRep.searchUsersByProfile(Profile.VISITOR));	
		assertEquals(testUsersWithProfileDirector, userRep.searchUsersByProfile(Profile.DIRECTOR));		
	}
	
	/**
	 * This tests checks if an email that is typed by the user is valid or not. 
	 */

	@Test
	void testUserEmailValid() {

		assertEquals(userRep.isEmailAddressValid(user1.getEmail()), true);
		assertEquals(userRep.isEmailAddressValid(user5.getEmail()), false);
	}
	
	
}
