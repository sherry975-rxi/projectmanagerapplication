package project.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project.model.Company;
import project.model.Profile;
import project.model.User;
import project.model.UserContainer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class US135andUS136SearchUsersControllerTest {

	/**
	 * Controller SearchUsersController
	 * 
	 * This controller allows the Administrator to search for users with a certain
	 * profile or by email
	 *
	 */
	User newUser1;
	User newUser2;
	User newUser3;
	UserContainer userContainer;
	US135andUS136SearchUsersController searchController;
	Company company;

	@Before
	public void setUp() {

		// Creates new company
		company = Company.getTheInstance();

		// Creates an UserContainer
		userContainer = company.getUsersContainer();

		// create user
		newUser1 = new User("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000");
		// create user2
		newUser2 = new User("João", "joao@gmail.com", "001", "Admin", "920000000");

		// create user3
		newUser3 = new User("Miguel", "miguel@gmail.com", "001", "Admin", "920000000");

		/* Adds the created users to the user Repository */
		userContainer.addUserToUserRepository(newUser1);
		userContainer.addUserToUserRepository(newUser2);
		userContainer.addUserToUserRepository(newUser3);

		// Creates a searchController
		searchController = new US135andUS136SearchUsersController();

	}

	@After
	public void tearDown() {
		Company.clear();
		userContainer = null;
		searchController = null;
		newUser1 = null;
		newUser2 = null;
		newUser3 = null;
	}

	@Test
	public void testSearchUsersControllerByProfile() {

		/* Compares a search of a profile type that doesn't exist with a empty List */
		List<String> emptyList = new ArrayList<>();
		assertEquals(searchController.searchUsersByProfileController(Profile.COLLABORATOR), emptyList);

	}

	/**
	 * This tests the basic search by profile method and asserts the UserData to
	 * String conversion is working as intended
	 * 
	 * 
	 */
	@Test
	public void testSearchUsersControllerByProfile2() {

		/* Set the newUser1 and newUser2 profile type to collaborator */
		newUser1.setUserProfile(Profile.COLLABORATOR);

		// Creates a String data for newUser1 and compares it to the the ToString Method
		// from the controller
		String newUser1String = "001 - Collaborator: Daniel (daniel@gmail.com; 910000000) - collaborator";
		assertTrue(searchController.userDataToString(newUser1).equals(newUser1String));

		/*
		 * Compares a search of collaborator with a list where two collaborators exist
		 */
		List<String> collaboratorstest = new ArrayList<>();
		collaboratorstest.add("[1] \n" + newUser1String);

		assertEquals(searchController.searchUsersByProfileController(Profile.COLLABORATOR), collaboratorstest);
	}

	/**
	 * This test verifies that searching a nonexistent email will return no users
	 * 
	 */
	@Test
	public void testSearchUsersControllerByEmailThatDoesntExist() {

		/* Compares a search of a mail that doesn't exist with a empty List */
		List<String> emptyList = new ArrayList<>();
		assertEquals(searchController.searchUsersByEmailController("yahoo"), emptyList);

	}

	/**
	 * This test asserts that searching by an entire, existing email returns a
	 * single user
	 * 
	 */
	@Test
	public void searchByEntireEmail() {

		/* Compares a search by entire mail address with a list with that mail */
		List<String> testUsersEmail1 = new ArrayList<>();
		testUsersEmail1.add("[1] \n" + searchController.userDataToString(newUser2));
		assertEquals(searchController.searchUsersByEmailController("joao@gmail.com"), testUsersEmail1);

		assertEquals(searchController.searchUsersByEmailController("joao@gmail.com").size(), 1);
	}

	/**
	 * Asserts the user selection method is working correctly, attempting to select
	 * the first indexed User from the list
	 * 
	 */
	@Test
	public void testListUsersController_SelectUser() {
		// calls the Search Users controller to generate a List
		searchController.searchUsersByEmailController("joao@gmail.com");

		// selects user from visible Index number 1 (corresponding to the User List's
		// user 0)
		// it must be equal to newUser2
		assertEquals(searchController.selectUser(1), newUser2);
	}

	@Test
	public void testUserDataToString() {

		String result = "001 - Unassigned: Daniel (daniel@gmail.com; 910000000) - collaborator";
		assertTrue(result.equals(searchController.userDataToString(newUser1)));

		newUser1.setUserProfile(Profile.COLLABORATOR);
		result = "001 - Collaborator: Daniel (daniel@gmail.com; 910000000) - collaborator";
		assertTrue(result.equals(searchController.userDataToString(newUser1)));

		newUser1.setUserProfile(Profile.DIRECTOR);
		result = "001 - Director: Daniel (daniel@gmail.com; 910000000) - collaborator";
		assertTrue(result.equals(searchController.userDataToString(newUser1)));
	}

}
