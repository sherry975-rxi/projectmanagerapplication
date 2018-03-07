package project.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project.model.Company;
import project.model.Profile;
import project.model.User;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class US130ListUsersControllerTest {

	Company Critical;
	User user1, user2, newUser2, newUser3;
	List<String> testList;
	US130ListUsersController listUsersController;

	@Before
	public void setUp() {

		// create company
		Critical = Company.getTheInstance();

		// create a list to compare
		testList = new ArrayList<>();

		// creates user four users
		user1 = Critical.getUsersContainer().createUser("Daniel", "daniel@gmail.com", "001", "Porteiro", "920000000",
				"Testy Street", "2401-343", "Testburg", "Testo", "Testistan");
		user2 = Critical.getUsersContainer().createUser("DanielM", "danielM@gmail.com", "002", "Code Monkey",
				"920000000", "Testy Street", "2401-343", "Testburg", "Testo", "Testistan");
		newUser2 = Critical.getUsersContainer().createUser("Manel", "user2@gmail.com", "001", "Empregado", "930000000",
				"Testy Street", "2401-343", "Testburg", "Testo", "Testistan");
		newUser3 = Critical.getUsersContainer().createUser("Manelinho", "user3@gmail.com", "002", "Telefonista",
				"940000000", "Testy Street", "2401-343", "Testburg", "Testo", "Testistan");

		// adds all but newUser3 to the UserContainer and test List
		Critical.getUsersContainer().addUserToUserRepository(user1);
		Critical.getUsersContainer().addUserToUserRepository(user2);
		Critical.getUsersContainer().addUserToUserRepository(newUser2);

		listUsersController = new US130ListUsersController();
	}

	@After
	public void tearDown() {

		Company.clear();
		user1 = null;
		user2 = null;
		newUser2 = null;
		newUser3 = null;
		testList = null;
		listUsersController = null;

	}

	/**
	 * This test confirms the ListUsersController method is working correctly, and
	 * that it returns the Users as string of data and NOT an object
	 * 
	 */
	@Test
	public void testListUsersController() {

		// creates a string matching user1's data and asserts as true
		String user1String = "001 - Unassigned: Daniel (daniel@gmail.com; 920000000) - Porteiro";
		assertTrue(user1String.equals(listUsersController.userDataToString(user1)));

		// creates Strings for all Users and adds them to testList
		testList.add("[1] \n" + user1String);
		testList.add("[2] \n" + listUsersController.userDataToString(user2));
		testList.add("[3] \n" + listUsersController.userDataToString(newUser2));

		// finally, asserts the listUsersController returns only the three Users added
		assertEquals(testList, listUsersController.listUsersController());
		assertEquals(listUsersController.listUsersController().size(), 3);
	}

	/**
	 * Asserts the user selection method is working correctly, attempting to select
	 * the first indexed User from the list
	 * 
	 */
	@Test
	public void testListUsersController_SelectUser() {
		// calls the List Users controller to generate a List and add it to
		listUsersController.listUsersController();

		// selects user from visible Index number 1 (corresponding to the User List's
		// user 0)
		// it must be equal to User 1
		assertEquals(listUsersController.selectUser(1), user1);
	}

	@Test
	public void testUserDataToString() {

		String result = "001 - Unassigned: Daniel (daniel@gmail.com; 920000000) - Porteiro";
		assertTrue(result.equals(listUsersController.userDataToString(user1)));

		user1.setUserProfile(Profile.COLLABORATOR);
		result = "001 - Collaborator: Daniel (daniel@gmail.com; 920000000) - Porteiro";
		assertTrue(result.equals(listUsersController.userDataToString(user1)));

		user1.setUserProfile(Profile.DIRECTOR);
		result = "001 - Director: Daniel (daniel@gmail.com; 920000000) - Porteiro";
		assertTrue(result.equals(listUsersController.userDataToString(user1)));
	}

}
