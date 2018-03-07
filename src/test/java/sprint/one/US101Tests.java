package sprint.one;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project.model.Company;
import project.model.User;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

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
public class US101Tests {

	Company Critical;
	User user1, uFalse, uRepeat, user2;
	List<User> testList;

	@Before
	public void setUp() {

		// create company
		Critical = Company.getTheInstance();

		// create user
		user1 = Critical.getUsersContainer().createUser("Daniel", "daniel@gmail.com", "001", "Porteiro", "920000000",
				"Testy Street", "2401-343", "Testburg", "Testo", "Testistan");
		uFalse = Critical.getUsersContainer().createUser("DanielA", "daniel&gmail,com", "001", "Porteiro", "910000000",
				"Testy Street", "2401-343", "Testburg", "Testo", "Testistan");
		uRepeat = Critical.getUsersContainer().createUser("DanielC", "daniel@gmail.com", "002", "Porteiro",
				"930000000", "Testy Street", "2401-343", "Testburg", "Testo", "Testistan");
		user2 = Critical.getUsersContainer().createUser("DanielM", "danielM@gmail.com", "002", "Code Monkey",
				"920000000", "Testy Street", "2401-343", "Testburg", "Testo", "Testistan");

		// create a list to compare
		testList = new ArrayList<User>();
	}

	@After
	public void tearDown() {

		Company.clear();
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
	public void testUserAdditionEmailValidation() {

		// Confirms the UserList starts with 0 users, then checks test Users' emails
		// before adding them
		assertEquals(Critical.getUsersContainer().getAllUsersFromUserContainer().size(), 0);
		assertFalse(Critical.getUsersContainer().isEmailAddressValid(uFalse.getEmail()));

		assertTrue(Critical.getUsersContainer().isEmailAddressValid(user1.getEmail()));
		Critical.getUsersContainer().addUserToUserRepository(user1);
		assertTrue(Critical.getUsersContainer().getAllUsersFromUserContainer().contains(user1));
		assertEquals(Critical.getUsersContainer().getAllUsersFromUserContainer().size(), 1);

		assertTrue(Critical.getUsersContainer().isEmailAddressValid(uRepeat.getEmail()));
		Critical.getUsersContainer().addUserToUserRepository(uRepeat);
		assertEquals(Critical.getUsersContainer().getAllUsersFromUserContainer().size(), 1);

		assertEquals(user1, Critical.getUsersContainer().getAllUsersFromUserContainer().get(0));
	}

	/**
	 * Test creation of a second user, first comparing it to the first index of
	 * userList (False); Then adds it to usersList in Company and tries to add the
	 * duplicated first user again to the list. Then compares the userLists
	 *
	 */
	@Test
	public void testUS101RepeatedUser() {

		// Adds first user to List and asserts its presence using various methods
		Critical.getUsersContainer().addUserToUserRepository(user1);
		assertFalse(user2.equals(Critical.getUsersContainer().getAllUsersFromUserContainer().get(0)));
		assertTrue(Critical.getUsersContainer().getAllUsersFromUserContainer().contains(user1));

		// Attempts to add the same user again (false) and add a new one
		// then confirms the Userlist size, and compares it to a test List
		Critical.getUsersContainer().addUserToUserRepository(user1);
		assertEquals(Critical.getUsersContainer().getAllUsersFromUserContainer().size(), 1);
		Critical.getUsersContainer().addUserToUserRepository(user2);
		assertEquals(Critical.getUsersContainer().getAllUsersFromUserContainer().size(), 2);

		testList.add(user1);
		testList.add(user2);

		assertTrue(Critical.getUsersContainer().getAllUsersFromUserContainer().equals(testList));
	}

}
