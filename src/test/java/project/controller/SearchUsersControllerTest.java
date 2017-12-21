package project.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.model.Company;
import project.model.Profile;
import project.model.User;

public class SearchUsersControllerTest {

	/**
	 * Controller SearchUsersController
	 * 
	 * This controller allows the Administrator to search for users with a certain
	 * profile or by email
	 *
	 */

	Company myCompany;
	User newUser1;
	User newUser2;
	User newUser3;
	SearchUsersController searchController = new SearchUsersController();

	@Before
	public void setUp() {

		// Creates a new Company
		myCompany = Company.getTheInstance();

		// Clears the UsersRepository list
		myCompany.getUsersRepository().getAllUsersFromRepository().clear();

		// Creates 3 users
		newUser1 = myCompany.getUsersRepository().createUser("Manel", "user2@gmail.com", "001", "Empregado",
				"930000000", "ruinha", "7040-531", "Bucareste", "Porto", "Portugal");
		newUser2 = myCompany.getUsersRepository().createUser("Manelinho", "user3@gmail.com", "002", "Telefonista",
				"940000000", "ruinha", "7040-531", "Bucareste", "Porto", "Portugal");
		newUser3 = myCompany.getUsersRepository().createUser("Emanuel", "user4@sapo.com", "003", "Faz tudo",
				"960000000", "ruinha", "7040-531", "Bucareste", "Porto", "Portugal");

		/* Adds the created users to the Company user list */
		myCompany.getUsersRepository().addUserToUserRepository(newUser1);
		myCompany.getUsersRepository().addUserToUserRepository(newUser2);
		myCompany.getUsersRepository().addUserToUserRepository(newUser3);

	}

	@After
	public void tearDown() {
		Company.clear();
		newUser1 = null;
		newUser2 = null;
		newUser3 = null;
	}

	@Test
	public void testSearchUsersControllerByProfile() {

		/* Compares a search of a profile type that doesn't exist with a empty List */
		List<User> emptyList = new ArrayList<User>();
		assertEquals(searchController.searchUsersByProfileController(Profile.COLLABORATOR), emptyList);

	}

	@Test
	public void testSearchUsersControllerByProfile2() {
		/* Adds the created users to the Company user list */
		myCompany.getUsersRepository().addUserToUserRepository(newUser1);
		myCompany.getUsersRepository().addUserToUserRepository(newUser2);

		/* Set the newUser1 and newUser2 profile type to collaborator */
		newUser1.setUserProfile(Profile.COLLABORATOR);

		/*
		 * Compares a search of collaborator with a list where two collaborators exist
		 */
		List<User> collaboratorstest = new ArrayList<User>();
		collaboratorstest.add(newUser1);

		assertEquals(searchController.searchUsersByProfileController(Profile.COLLABORATOR), collaboratorstest);
	}

	@Test
	public void testSearchUsersControllerByEmailThatDoesntExist() {
		/* Compares a search of a mail that doesn't exist with a empty List */
		List<User> emptyList = new ArrayList<User>();
		assertEquals(searchController.searchUsersByEmailController("yahoo"), emptyList);

	}

	@Test
	public void searchByEntireEmail() {
		/* Compares a search by entire mail address with a list with that mail */
		List<User> testUsersEmail1 = new ArrayList<User>();
		testUsersEmail1.add(newUser1);
		assertEquals(searchController.searchUsersByEmailController("user2@gmail.com"), testUsersEmail1);
	}

}
