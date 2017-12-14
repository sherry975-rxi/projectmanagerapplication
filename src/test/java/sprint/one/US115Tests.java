package sprint.one;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.model.Company;
import project.model.User;

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

		newUser2 = myCompany.getUsersRepository().createUser("Manel", "user2@gmail.com", "001", "Empregado",
				"930000000", "Rua Bla", "BlaBla", "BlaBlaBla", "BlaBlaBlaBla", "Blalandia");
		newUser3 = myCompany.getUsersRepository().createUser("Manelinho", "user3@gmail.com", "002", "Telefonista",
				"940000000", "Rua Bla", "BlaBla", "BlaBlaBla", "BlaBlaBlaBla", "Blalandia");

		myCompany.getUsersRepository().addUserToUserRepository(newUser2);
		myCompany.getUsersRepository().addUserToUserRepository(newUser3);

	}

	@After
	public void tearDown() {
		myCompany.clear();
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

		newUser2.setSystemUserState(false);
		newUser3.setSystemUserState(false);

		assertFalse(newUser2.isSystemUserStateActive());
		assertFalse(newUser3.isSystemUserStateActive());

		newUser2.setSystemUserState(true);
		newUser3.setSystemUserState(true);

		assertTrue(newUser2.isSystemUserStateActive());
		assertTrue(newUser3.isSystemUserStateActive());
	}

}
