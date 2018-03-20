package project.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import project.Repository.UserRepository;
import project.Services.UserService;
import project.model.Profile;
import project.model.User;

@RunWith(SpringRunner.class)
@DataJpaTest
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
	UserService userService;

	@Autowired
	public UserRepository userRepo;

	US135andUS136SearchUsersController searchController;

	@Before
	public void setUp() {

		// creates an UserContainer
		userService = new UserService();
		userService.setUserRepository(userRepo);
		// Creates a searchController
		searchController = new US135andUS136SearchUsersController();
		searchController.userContainer = userService;

		// create user
		newUser1 = userService.createUser("Ana", "ana@gmail.com", "01", "collaborator", "221238442", "Rua Porto",
				"4480", "Porto", "Porto", "Portugal");
		// create user2
		newUser2 = userService.createUser("Joao", "joao@gmail.com", "01", "collaborator", "221238442", "Rua Porto",
				"4480", "Porto", "Porto", "Portugal");
		// create user3
		newUser3 = userService.createUser("Rui", "rui@gmail.com", "01", "collaborator", "221238442", "Rua Porto",
				"4480", "Porto", "Porto", "Portugal");

		/* Adds the created users to the user Repository */
		userService.addUserToUserRepositoryX(newUser1);
		userService.addUserToUserRepositoryX(newUser2);
		userService.addUserToUserRepositoryX(newUser3);

		userService.updateUserContainer();

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
		String newUser1String = "01 - Collaborator: Ana (ana@gmail.com; 221238442) - collaborator";
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

		/*
		 * Checks if the method UserDataToString returns the expected values
		 */

		String result = "01 - Unassigned: Ana (ana@gmail.com; 221238442) - collaborator";
		assertTrue(searchController.userDataToString(newUser1).equals(result));

		/*
		 * Updates the userProfile to Collaborator
		 */
		newUser1.setUserProfile(Profile.COLLABORATOR);
		result = "01 - Collaborator: Ana (ana@gmail.com; 221238442) - collaborator";

		/*
		 * Checks that the method returns its new data updated as Collaborator
		 */
		assertTrue(result.equals(searchController.userDataToString(newUser1)));

		/*
		 * Updates the userProfile to Director
		 */
		newUser1.setUserProfile(Profile.DIRECTOR);

		/*
		 * Checks that the method returns its new data updated as Collaborator
		 */
		result = "01 - Director: Ana (ana@gmail.com; 221238442) - collaborator";
		assertTrue(result.equals(searchController.userDataToString(newUser1)));

	}

}
