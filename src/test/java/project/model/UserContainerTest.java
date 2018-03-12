/**
 * 
 */
package project.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests all methods in UserContainerClass
 * 
 * @author Group3
 *
 */
public class UserContainerTest {

	// @Mock
	// private UserRepository userRepositoryMock;

	User user1;
	User user2;
	User user3;
	User user4;
	User user5;
	UserContainer userContainer = new UserContainer();

	@Before
	public void setUp() {
		initMocks(this);

		// instantiate users
		user1 = userContainer.createUser("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000", "Rua",
				"2401-00", "Test", "Testo", "Testistan");
		user2 = userContainer.createUser("João", "joao@gmail.com", "001", "Admin", "920000000", "Rua", "2401-00",
				"Test", "Testo", "Testistan");
		user4 = userContainer.createUser("DanielMM", "danielmm@gmail.com", "003", "collaborator", "910000000", "Rua",
				"2401-00", "Test", "Testo", "Testistan");

		user5 = userContainer.createUser("DanielMM", "danielmmgmail.com", "003", "collaborator", "910000000", "Rua",
				"2401-00", "Test", "Testo", "Testistan");
	}

	@After
	public void tearDown() {
		user1 = null;
		user2 = null;
		user3 = null;
		user4 = null;
		user5 = null;
		userContainer = null;
	}

	/**
	 * Tests constructor for User Repository
	 */

	@Test
	public void testUserRepository() {

		List<User> testUserRep = new ArrayList<>();

		assertEquals(testUserRep, userContainer.getAllUsersFromUserContainer());
	}

	/**
	 * Tests the creation of an instance of User (compares user1 created with
	 * createUser() and user3 created as a new object)
	 */

	@Test
	public void testCreateUser() {

		User user3 = new User("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000");
		Address newAddress = user3.createAddress("Rua", "2401-00", "Test", "Testo", "Testistan");
		user3.addAddress(newAddress);

		assertTrue(user3.equals(user1));
	}

	/**
	 * Tests the creation of an instance of User (compares user1 created with
	 * createUser())
	 */

	@Test
	public void testCreateUser_2() {
		User user3 = new User("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000");
		Address newAddress = user3.createAddress("Rua", "2401-00", "Test", "Testo", "Testistan");
		user3.addAddress(newAddress);

		assertEquals(user3, userContainer.createUser("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000",
				"Rua", "2401-00", "Test", "Testo", "Testistan"));
	}

	/**
	 * Tests the construction of a copy of the list of all users
	 */

	@Test
	public void testGetAllUsersFromRepository() {

		userContainer.addUserToUserRepository(user1);
		userContainer.addUserToUserRepository(user2);

		List<User> testUsersInRep = new ArrayList<>();
		testUsersInRep.add(user1);
		testUsersInRep.add(user2);

		assertEquals(testUsersInRep, userContainer.getAllUsersFromUserContainer());
	}

	/**
	 * Tests addition of users to the User Repository, if the user is missing from
	 * the repository
	 */
	@Test
	public void testAddUserToUserRepository() {

		// adds user twice
		userContainer.addUserToUserRepository(user1);
		userContainer.addUserToUserRepository(user1);

		assertTrue(user1.equals(userContainer.getAllUsersFromUserContainer().get(0)));
		assertEquals(1, userContainer.getAllUsersFromUserContainer().size()); // if it would add even if the user was
																				// already on
		// the list, the size of the repository would be
		// 2 instead of 1
	}

	/**
	 * Tests if a given user already exists in a repository
	 */
	@Test
	public void testIsUserinUserRepository() {

		userContainer.addUserToUserRepository(user1);

		assertTrue(userContainer.isUserinUserContainer(user1));
		assertFalse(userContainer.isUserinUserContainer(user2));
	}

	/**
	 * Tests the creation of a list of users with a common part of an e-mail.
	 */
	@Test
	public void testSearchUsersByEmail() {

		userContainer.addUserToUserRepository(user1);
		userContainer.addUserToUserRepository(user2);
		userContainer.addUserToUserRepository(user4);

		List<User> testUsersWithEmail = new ArrayList<>();
		testUsersWithEmail.add(user1);
		testUsersWithEmail.add(user4);

		assertEquals(testUsersWithEmail, userContainer.searchUsersByEmail("daniel"));

	}

	/**
	 * Tests the output of a users with searched full e-mail.
	 * 
	 */
	@Test
	public void testGetUserByEmail() {

		userContainer.addUserToUserRepository(user1);
		userContainer.addUserToUserRepository(user2);
		userContainer.addUserToUserRepository(user4);

		assertTrue(user1.equals(userContainer.getUserByEmail("daniel@gmail.com")));
		assertTrue(user4.equals(userContainer.getUserByEmail("danielmm@gmail.com")));
		assertFalse(user1.equals(userContainer.getUserByEmail("danielmm@gmail.com")));

		// This email doesnt exist in the list, so the method returns null;
		List<User> testUsersWithEmail = new ArrayList<>();

		assertEquals(null, userContainer.getUserByEmail("doesntExist"));

	}

	/**
	 * Tests the creation of a list of users with a certain profile.
	 */
	@Test
	public void testsearchUsersByProfile() {

		userContainer.addUserToUserRepository(user1);
		userContainer.addUserToUserRepository(user2);
		userContainer.addUserToUserRepository(user4);

		user2.setUserProfile(Profile.DIRECTOR);

		List<User> testUsersWithProfileVisitor = new ArrayList<>();
		testUsersWithProfileVisitor.add(user1);
		testUsersWithProfileVisitor.add(user4);

		List<User> testUsersWithProfileDirector = new ArrayList<>();
		testUsersWithProfileDirector.add(user2);

		assertEquals(testUsersWithProfileVisitor, userContainer.searchUsersByProfile(Profile.UNASSIGNED));
		assertEquals(testUsersWithProfileDirector, userContainer.searchUsersByProfile(Profile.DIRECTOR));
	}

	/**
	 * This tests checks if an email that is typed by the user is valid or not.
	 */

	@Test
	public void testUserEmailValid() {

		assertEquals(userContainer.isEmailAddressValid(user1.getEmail()), true);
		assertEquals(userContainer.isEmailAddressValid(user5.getEmail()), false);
	}

	/**
	 * This tests checks if the getAllActiveCollaborators() method returns only
	 * active Collaborators
	 */
	@Test
	public void testGetAllActiveCollaborators() {

		userContainer.addUserToUserRepository(user1);
		userContainer.addUserToUserRepository(user2);
		userContainer.addUserToUserRepository(user4);
		userContainer.addUserToUserRepository(user5);

		// given four users, sets the first three users as collaborator, and the fourth
		// as director
		user1.setUserProfile(Profile.COLLABORATOR);
		user2.setUserProfile(Profile.COLLABORATOR);
		user4.setUserProfile(Profile.COLLABORATOR);
		user5.setUserProfile(Profile.DIRECTOR);

		// when three of the users are active collaborators, asserts the total list
		// contains 4 users
		// and that the active Collaborators list has 3 users
		assertEquals(userContainer.getAllUsersFromUserContainer().size(), 4);
		assertEquals(userContainer.getAllActiveCollaboratorsFromRepository().size(), 3);

		// then, sets the first user as inactive, asserts its state was change
		// and finally, confirms the active collaborators list contains 2 users
		user1.changeUserState();
		assertFalse(user1.isSystemUserStateActive());
		assertEquals(userContainer.getAllActiveCollaboratorsFromRepository().size(), 2);
	}

	/**
	 * @Test public void testAddUserToUserRepositoryX(){
	 *       when(userRepositoryMock.save(any(User.class))).thenReturn(user1);
	 *       UserContainer victim = new UserContainer(userRepositoryMock);
	 * 
	 *       victim.addUserToUserRepositoryX(user1);
	 * 
	 *       verify(userRepositoryMock, times(1)).save(user1); }
	 * 
	 * @Test public void testCreateUserWithDTO(){
	 *       when(userRepositoryMock.save(any(User.class))).thenReturn(user1);
	 *       UserContainer victim = new UserContainer(userRepositoryMock);
	 * 
	 *       UserDTO userDto = new UserDTO(user1);
	 *       victim.createUserWithDTO(userDto); verify(userRepositoryMock,
	 *       times(1)).save(user1); }
	 * 
	 * @Test public void testUpdateUserContainer(){
	 * 
	 *       List<User> expectedUserList = new ArrayList<>();
	 *       expectedUserList.add(user1); expectedUserList.add(user2);
	 * 
	 *       when(userRepositoryMock.findAll()).thenReturn(expectedUserList);
	 *       UserContainer victim = new UserContainer(userRepositoryMock);
	 * 
	 *       victim.updateUserContainer();
	 * 
	 *       assertEquals(expectedUserList,victim.getAllUsersFromUserContainer()); }
	 */

}
