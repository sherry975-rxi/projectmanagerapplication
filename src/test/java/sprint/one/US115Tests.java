package sprint.one;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project.model.User;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class US115Tests {

	/**
	 * Tests US115
	 * 
	 * US115: Como Administrador, pretendo desativar utilizadores do sistema.
	 * 
	 * uses methods setSystemUserState
	 * 
	 */

	Company myCompany;
	User newUser2;
	User newUser3;

	@Before
	public void setUp() {

		myCompany = Company.getTheInstance();

		newUser2 = myCompany.getUsersContainer().createUser("Manel", "user2@gmail.com", "001", "Empregado",
				"930000000", "Rua Bla", "BlaBla", "BlaBlaBla", "BlaBlaBlaBla", "Blalandia");
		newUser3 = myCompany.getUsersContainer().createUser("Manelinho", "user3@gmail.com", "002", "Telefonista",
				"940000000", "Rua Bla", "BlaBla", "BlaBlaBla", "BlaBlaBlaBla", "Blalandia");

		myCompany.getUsersContainer().addUserToUserRepository(newUser2);
		myCompany.getUsersContainer().addUserToUserRepository(newUser3);

	}

	@After
	public void tearDown() {
		Company.clear();
		newUser2 = null;
		newUser3 = null;
	}

	/**
	 * Tests the setSystemUserState method to check if the active or deactivate
	 * state was correctly attributed to the user. Asserts if UserStateActive starts
	 * True; then, Asserts if UserStateActive if False, after setting it as such;
	 * Finally, asserts if UserState is true after resetting it.
	 */
	@Test
	public void testAssertSystemUserState() {

		assertTrue(newUser2.isSystemUserStateActive());

		newUser2.changeUserState();
		newUser3.changeUserState();

		assertFalse(newUser2.isSystemUserStateActive());
		assertFalse(newUser3.isSystemUserStateActive());

		newUser2.changeUserState();
		newUser3.changeUserState();

		assertTrue(newUser2.isSystemUserStateActive());
		assertTrue(newUser3.isSystemUserStateActive());
	}

}
