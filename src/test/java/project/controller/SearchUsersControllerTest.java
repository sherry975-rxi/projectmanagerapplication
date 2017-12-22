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
import project.model.UserRepository;

public class SearchUsersControllerTest {

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
	UserRepository userRepository;
	SearchUsersController searchController;
	Company company;

	@Before
	public void setUp() {

		// Creates new company
		company = Company.getTheInstance();

		// Creates an UserRepository
		userRepository = company.getUsersRepository();

		// Creates a searchController
		searchController = new SearchUsersController();

		// create user
		newUser1 = new User("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000");
		// create user2
		newUser2 = new User("João", "joao@gmail.com", "001", "Admin", "920000000");

		// create user3
		newUser3 = new User("Miguel", "miguel@gmail.com", "001", "Admin", "920000000");

	}

	@After
	public void tearDown() {
		Company.clear();
		userRepository = null;
		searchController = null;
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

		/* Adds the created users to the user Repository */
		userRepository.addUserToUserRepository(newUser1);
		userRepository.addUserToUserRepository(newUser2);
		userRepository.addUserToUserRepository(newUser3);

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

		/* Adds the created users to the user Repository */
		userRepository.addUserToUserRepository(newUser1);
		userRepository.addUserToUserRepository(newUser2);
		userRepository.addUserToUserRepository(newUser3);
		/* Compares a search of a mail that doesn't exist with a empty List */
		List<User> emptyList = new ArrayList<User>();
		assertEquals(searchController.searchUsersByEmailController("yahoo"), emptyList);

	}

	@Test
	public void searchByEntireEmail() {

		/* Adds the created users to the user Repository */
		userRepository.addUserToUserRepository(newUser1);
		userRepository.addUserToUserRepository(newUser2);
		userRepository.addUserToUserRepository(newUser3);
		/* Compares a search by entire mail address with a list with that mail */
		List<User> testUsersEmail1 = new ArrayList<User>();
		testUsersEmail1.add(newUser2);
		assertEquals(searchController.searchUsersByEmailController("joao@gmail.com"), testUsersEmail1);
	}

}
