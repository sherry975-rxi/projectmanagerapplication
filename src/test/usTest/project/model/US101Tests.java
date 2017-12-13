package test.usTest.project.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.project.model.Company;
import main.project.model.User;

/**
 * Test the registration of a User, and its incorporation in a list of Users
 * 
 * US101 - Como Visitante da aplicação de gestão de projetos, eu pretendo
 * registar-me como utilizador de forma a poder usufruir das suas várias
 * funcionalidades. (PS.: Reescrita relativamente à semana anterior).
 * 
 * uses methods addUserToUserList(toAddUsers), createUser(), isEmailValid(),
 * getUserList
 * 
 * 
 * @author Rita Machado
 * @author João Bessa
 * 
 */
class US101Tests {

	Company Critical;
	User user1, uFalse, uRepeat, user2;
	List<User> testList;

	@BeforeEach
	void setUp() {

		// create company
		Critical = Company.getTheInstance();
		Critical.getUsersRepository().getAllUsersFromRepository().clear();
		Critical.getProjectsRepository().getAllProjects().clear();

		// create user
		user1 = Critical.getUsersRepository().createUser("Daniel", "daniel@gmail.com", "001", "Porteiro", "920000000",
				"Testy Street", "2401-343", "Testburg", "Testo", "Testistan");
		uFalse = Critical.getUsersRepository().createUser("DanielA", "daniel&gmail,com", "001", "Porteiro", "910000000",
				"Testy Street", "2401-343", "Testburg", "Testo", "Testistan");
		uRepeat = Critical.getUsersRepository().createUser("DanielC", "daniel@gmail.com", "002", "Porteiro",
				"930000000", "Testy Street", "2401-343", "Testburg", "Testo", "Testistan");
		user2 = Critical.getUsersRepository().createUser("DanielM", "danielM@gmail.com", "002", "Code Monkey",
				"920000000", "Testy Street", "2401-343", "Testburg", "Testo", "Testistan");

		// create a list to compare
		testList = new ArrayList<User>();
	}

	@AfterEach
	void tearDown() {

		Critical = null;
		user1 = null;
		user2 = null;
		uFalse = null;
		uRepeat = null;
		testList = null;
	}

	/**
	 * Tests if created user's email is valid and unique, then adds the user. It
	 * must have an "@" and "."; Simulates the addition of any User with valid email
	 */
	@Test
	void testUserAdditionEmailValidation() {

		// Confirms the UserList starts with 0 users, then checks test Users' emails
		// before adding them
		assertEquals(Critical.getUsersRepository().getAllUsersFromRepository().size(), 0);
		assertFalse(Critical.getUsersRepository().isEmailAddressValid(uFalse.getEmail()));

		assertTrue(Critical.getUsersRepository().isEmailAddressValid(user1.getEmail()));
		Critical.getUsersRepository().addUserToUserRepository(user1);
		assertTrue(Critical.getUsersRepository().getAllUsersFromRepository().contains(user1));
		assertEquals(Critical.getUsersRepository().getAllUsersFromRepository().size(), 1);

		assertTrue(Critical.getUsersRepository().isEmailAddressValid(uRepeat.getEmail()));
		Critical.getUsersRepository().addUserToUserRepository(uRepeat);
		assertEquals(Critical.getUsersRepository().getAllUsersFromRepository().size(), 1);

		assertEquals(user1, Critical.getUsersRepository().getAllUsersFromRepository().get(0));
	}

	/**
	 * Test creation of a second user, first comparing it to the first index of
	 * userList (False); Then adds it to usersList in Company and tries to add the
	 * duplicated first user again to the list. Then compares the userLists
	 *
	 */
	@Test
	void testUS101RepeatedUser() {

		// Adds first user to List and asserts its presence using various methods
		Critical.getUsersRepository().addUserToUserRepository(user1);
		assertFalse(user2.equals(Critical.getUsersRepository().getAllUsersFromRepository().get(0)));
		assertTrue(Critical.getUsersRepository().getAllUsersFromRepository().contains(user1));

		// Attempts to add the same user again (false) and add a new one
		// then confirms the Userlist size, and compares it to a test List
		Critical.getUsersRepository().addUserToUserRepository(user1);
		assertEquals(Critical.getUsersRepository().getAllUsersFromRepository().size(), 1);
		Critical.getUsersRepository().addUserToUserRepository(user2);
		assertEquals(Critical.getUsersRepository().getAllUsersFromRepository().size(), 2);

		testList.add(user1);
		testList.add(user2);

		assertTrue(Critical.getUsersRepository().getAllUsersFromRepository().equals(testList));
	}

}
