package test.java.project.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.project.model.Company;
import main.java.project.model.User;

class US101Tests {

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

	Company Critical;
	User user1, uFalse, uRepeat, user2;

	@BeforeEach
	void setUp() {
		Critical = Company.getTheInstance();
		Critical.getUsersList().clear();

		user1 = Critical.createUser("Daniel", "daniel@gmail.com", "Porteiro", "920000000", "Testy Street", "2401-343",
				"Testburg", "Testo", "Testistan");
		uFalse = Critical.createUser("DanielA", "daniel&gmail,com", "Porteiro", "910000000", "Testy Street", "2401-343",
				"Testburg", "Testo", "Testistan");
		uRepeat = Critical.createUser("DanielC", "daniel@gmail.com", "Porteiro", "930000000", "Testy Street",
				"2401-343", "Testburg", "Testo", "Testistan");
		user2 = Critical.createUser("DanielM", "danielM@gmail.com", "Code Monkey", "920000000", "Testy Street",
				"2401-343", "Testburg", "Testo", "Testistan");

	}

	@AfterEach
	void tearDown() {

		Critical = null;
		user1 = null;
		user2 = null;
		uFalse = null;
		uRepeat = null;
	}

	/**
	 * Tests if created user's email is valid and unique, then adds the user. It
	 * must have an "@" and "."; Simulates the addition of any User with valid email
	 */
	@Test
	void testUserAdditionEmailValidation() {

		assertEquals(Critical.getUsersList().size(), 0);

		assertFalse(Critical.isEmailAddressValid(uFalse.getEmail()));

		assertTrue(Critical.isEmailAddressValid(user1.getEmail()));

		assertTrue(Critical.addUserToUserList(user1));

		assertTrue(Critical.isEmailAddressValid(uRepeat.getEmail()));

		assertFalse(Critical.addUserToUserList(uRepeat));

		assertEquals(user1, Critical.getUsersList().get(0));
	}

	/**
	 * Test creation of a second user, first comparing it to the first index of
	 * userList (False); Then adds it to usersList in Company and tries to add the
	 * duplicated first user again to the list. Then compares the userLists
	 *
	 */
	@Test
	void testUS101RepeatedUser() {

		assertTrue(Critical.addUserToUserList(user1));

		assertFalse(user2.equals(Critical.getUsersList().get(0)));

		assertFalse(Critical.addUserToUserList(user1));
		assertTrue(Critical.addUserToUserList(user2));

		assertEquals(Critical.getUsersList().size(), 2);

		List<User> testList = new ArrayList<User>();

		testList.add(user1);
		testList.add(user2);

		assertTrue(Critical.getUsersList().equals(testList));
	}

}
