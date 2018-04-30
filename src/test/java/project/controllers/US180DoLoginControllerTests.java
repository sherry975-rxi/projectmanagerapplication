package project.controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import project.model.Profile;
import project.model.User;
import project.services.UserService;

import static com.sun.javaws.JnlpxArgs.verify;
import static com.sun.tools.doclint.Entity.times;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan({ "project.services", "project.model", "project.controllers" })
public class US180DoLoginControllerTests {

	@Mock
	private UserService userServiceMock;

	@Mock
	private User userMock;

	@InjectMocks
	private US180DoLoginController doLoginController;

	@Before
	public void setUp() {


	}

	@After
	public void clear() {
	}

	/**
	 *GIVEN a valid email and password
	 *WHEN user exists in the repository and the password is correct
	 *THEN the login is performed
	 */
	@Test
	public void DoValidLoginControllerTest() {
		//GIVEN
		String validEmail = "daniel@gmail.com";
		String validPassword = "123456";

		//WHEN
		when(userServiceMock.getUserByEmail(anyString())).thenReturn(userMock);
		when(userMock.checkLogin(anyString())).thenReturn(true);
		when(userServiceMock.isUserinUserContainer(any(User.class))).thenReturn(true);

		//THEN
		assertTrue(doLoginController.doLogin(validEmail, validPassword));
	}

	@Test
	public void DoInvalidPasswordLoginControllerTest() {

		String validEmail = "daniel@gmail.com";
		String invalidPassword = "12345";
		assertFalse(doLoginController.doLogin(validEmail, invalidPassword));
	}

	/**
	 * GIVEN a string "email"
	 * WHEN perform the method findUserByEmail
	 * THEN the method call the getUserByEmail one time with the string passed in method
	 */
	@Test
	public void shouldReturnUserFoundByEmail(){
		//GIVEN
		String email = "mail";

		//WHEN
		when(userServiceMock.getUserByEmail(anyString())).thenReturn(userMock);
		doLoginController.findUserByEmail(email);

		//THEN
		Mockito.verify(userServiceMock, Mockito.times(1)).getUserByEmail(email);
	}

}
