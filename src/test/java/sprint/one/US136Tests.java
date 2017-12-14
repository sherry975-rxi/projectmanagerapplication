package sprint.one;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.model.Company;
import project.model.Profile;
import project.model.User;

public class US136Tests {

	/**
	 * Tests US136
	 * 
	 * US136: Como Administrador, pretendo pesquisar utilizadores do sistema por
	 * perfil de utilizador.
	 * 
	 * uses methods searchUsersByProfile()
	 * 
	 */

	Company myCompany;
	User newUser2;
	User newUser3;

	@Before
	public void setUp() {
		myCompany = Company.getTheInstance();

		newUser2 = myCompany.getUsersRepository().createUser("Manel", "user2@gmail.com", "001", "Empregado",
				"930000000", "rua cinzenta", "6789-654", "porto", "porto", "portugal");
		newUser3 = myCompany.getUsersRepository().createUser("Manelinho", "user3@gmail.com", "002", "Telefonista",
				"940000000", "rua rosa", "6799-654", "porto", "porto", "portugal");

		/* Set the testCollab profile type to collaborator */

		/* Set the newUser2 and newUser3 profile type to collaborator */
		newUser2.setUserProfile(Profile.COLLABORATOR);
		newUser3.setUserProfile(Profile.COLLABORATOR);
	}

	@After
	public void tearDown() {
		myCompany.clear();
		newUser2 = null;
		newUser3 = null;

	}

	@Test
	public void testSearchUsersByProfile() {
		/* Compares a search of a profile type that doesn't exist with a empty List */
		List<User> emptyList = new ArrayList<User>();
		assertEquals(myCompany.getUsersRepository().searchUsersByProfile(Profile.COLLABORATOR), emptyList);
	}

	@Test
	public void testSearchUsersByProfile2() {
		/* Adds the created users to the Company user list */
		myCompany.getUsersRepository().addUserToUserRepository(newUser2);
		myCompany.getUsersRepository().addUserToUserRepository(newUser3);

		/*
		 * Compares a search of collaborator with a list where two collaborators exist
		 */
		List<User> collaboratorstest = new ArrayList<User>();
		collaboratorstest.add(newUser2);
		collaboratorstest.add(newUser3);

		assertEquals(myCompany.getUsersRepository().searchUsersByProfile(Profile.COLLABORATOR), collaboratorstest);
	}
}