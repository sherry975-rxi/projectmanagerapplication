/**
 * 
 */
package project.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import project.Repository.UserRepository;
import project.Services.UserService;
import project.model.Profile;
import project.model.User;

/**
 * Tests all methods in UserService
 * 
 * @author Group3
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	@Mock
	private UserRepository userRepositoryMock;

	@InjectMocks
	private UserService userContainer = new UserService();
	private UserService userContainerWithRepository = new UserService(userRepositoryMock);

	User user1;
	User user2;
	User user3;
	User user4;
	User user5;
	List<User> mockUsers;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

		initMocks(this);

		// instantiate users
		user1 = userContainer.createUser("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000", "Rua",
				"2401-00", "Porto", "Porto", "Portugal");
		user2 = userContainer.createUser("João", "joao@gmail.com", "001", "Admin", "920000000", "Rua", "2401-00",
				"Porto", "Porto", "Portugal");
		user4 = userContainer.createUser("DanielMM", "danielmm@gmail.com", "003", "collaborator", "910000000", "Rua",
				"2401-00", "Porto", "Porto", "Portugal");

		user5 = userContainer.createUser("DanielMM", "danielmmgmail.com", "003", "collaborator", "910000000", "Rua",
				"2401-00", "Porto", "Porto", "Portugal");

		mockUsers = new ArrayList<>();

	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {

		user1 = null;
		user2 = null;
		user3 = null;
		user4 = null;
		user5 = null;
		mockUsers = null;
	}

	/**
	 * Test method for
	 * {@link project.Services.UserService#createUserWithDTO(project.dto.UserDTO)}.
	 */
	@Test
	public final void testCreateUserWithDTO() {

	}

	/**
	 * Test method call addUserToUserRepository
	 */
	@Test
	public final void testAddUserToUserRepositoryX() {

		// when(userRepositoryMock.save(any(User.class))).thenReturn(user1);

		userContainer.addUserToUserRepositoryX(user1);

		verify(userRepositoryMock, times(2)).save(user1);
	}

	/**
	 * Test method call for isUserinUserContainer
	 */
	@Test
	public final void testIsUserinUserContainer() {

		when(userRepositoryMock.findByEmail(user2.getEmail())).thenReturn(user2);

		assertEquals(true, userContainer.isUserinUserContainer(user2));
	}

	/**
	 * Test method call getAllUsersFromUserContainer.
	 */
	@Test
	public final void testGetAllUsersFromUserContainer() {

		List<User> listToCompare = new ArrayList<>();
		listToCompare.add(user1);

		List<User> userInDB = new ArrayList<>();
		userInDB.add(user1);

		when(userRepositoryMock.findAll()).thenReturn(userInDB);

		assertEquals(listToCompare, userContainer.getAllUsersFromUserContainer());
	}

	/**
	 * Test method for
	 * {@link project.Services.UserService#getUserByEmail(java.lang.String)}.
	 */
	@Test
	public final void testGetUserByEmail() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link project.Services.UserService#updateUserContainer()}.
	 */
	@Test
	public final void testUpdateUserContainer() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link project.Services.UserService#getAllActiveCollaboratorsFromRepository()}.
	 */
	@Test
	public final void testGetAllActiveCollaboratorsFromRepository() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link project.Services.UserService#searchUsersByPartsOfEmail(java.lang.String)}.
	 */
	@Test
	public final void testSearchUsersByPartsOfEmail() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link project.Services.UserService#searchUsersByProfile(project.model.Profile)}.
	 */
	@Test
	public final void testSearchUsersByProfile() {

		List<User> listOfUsersByProfile = new ArrayList<>();
		user1.setUserProfile(Profile.COLLABORATOR);
		listOfUsersByProfile.add(user1);

		when(userRepositoryMock.findAllByUserProfile(Profile.COLLABORATOR)).thenReturn(listOfUsersByProfile);

		assertEquals(listOfUsersByProfile, userContainer.searchUsersByProfile(Profile.COLLABORATOR));
	}

	/**
	 * Test method for
	 * {@link project.Services.UserService#isEmailAddressValid(java.lang.String)}.
	 */
	@Test
	public final void testIsEmailAddressValid() {
		fail("Not yet implemented"); // TODO
	}

}
