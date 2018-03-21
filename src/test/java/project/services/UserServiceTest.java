/**
 * 
 */
package project.services;

import static org.junit.Assert.assertEquals;
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
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import project.Repository.UserRepository;
import project.Services.UserService;
import project.dto.UserDTO;
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

	User user1;
	User user2;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

		initMocks(this);

		// instantiate users
		user1 = userContainer.createUser("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000", "Rua",
				"2401-00", "Porto", "Porto", "Portugal");
		user2 = userContainer.createUser("João", "joaogmail.com", "001", "Admin", "920000000", "Rua", "2401-00",
				"Porto", "Porto", "Portugal");

	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {

		user1 = null;
		user2 = null;

	}

	/**
	 * Test method call of testCreateUserWithDTO
	 */
	@Test
	public final void testCreateUserWithDTO() {
		// given a user DTO for user1
		UserDTO userDto = new UserDTO(user1);

		// when the .save method is mocked
		Mockito.when(userRepositoryMock.save(Mockito.any(User.class))).thenReturn(user1);

		// then the create User with DTO method must call the save method once for user1

		userContainer.createUserWithDTO(userDto);
		Mockito.verify(userRepositoryMock, Mockito.times(2)).save(user1);
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
	 * Test method call of testGetUserByEmail
	 */
	@Test
	public final void testGetUserByEmail() {

		when(userRepositoryMock.findByEmail("daniel@gmail.com")).thenReturn(user1);

		assertEquals(user1, userContainer.getUserByEmail("daniel@gmail.com"));
	}

	/**
	 * Test method call of testGetAllActiveCollaboratorsFromRepository
	 */
	@Test
	public final void testGetAllActiveCollaboratorsFromRepository() {
		List<User> list = new ArrayList<>();
		user1.setUserProfile(Profile.COLLABORATOR);
		user2.setUserProfile(Profile.COLLABORATOR);
		list.add(user1);
		list.add(user2);

		when(userRepositoryMock.findAll()).thenReturn(list);

		assertEquals(list, userContainer.getAllActiveCollaboratorsFromRepository());

	}

	/**
	 * Test method call of testSearchUsersByPartsOfEmail
	 */
	@Test
	public final void testSearchUsersByPartsOfEmail() {

		List<User> list = new ArrayList<>();
		list.add(user1);
		list.add(user2);

		when(userRepositoryMock.findAll()).thenReturn(list);

		assertEquals(list, userContainer.searchUsersByPartsOfEmail("gmail"));
	}

	/**
	 * Test method call of testSearchUsersByProfile
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
	 * Test method call of testIsEmailAddressValid
	 */
	@Test
	public final void testIsEmailAddressValid() {
		assertEquals(userContainer.isEmailAddressValid(user1.getEmail()), true);
		assertEquals(userContainer.isEmailAddressValid(user2.getEmail()), false);
	}

}
