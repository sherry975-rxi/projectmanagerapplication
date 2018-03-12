package sprint.one;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project.model.ProjectContainer;
import project.model.User;
import project.model.UserContainer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class US135Tests {

	/**
	 * Tests US135
	 * 
	 * US135: Como Administrador, pretendo pesquisar utilizadores do sistema por
	 * e-mail.
	 * 
	 * uses methods searchUsersByEmail()
	 * 
	 * Tests the searchUsersByEmail method, which searches users by their email. If
	 * the particular string in the contains method is found, an usersEmail list is
	 * returned. If no email matches the search criteria, returns an empty list.
	 */

	User newUser2;
	User newUser3;
	User newUser4;
	UserContainer userContainer;
	ProjectContainer projectContainer;

	@Before
	public void setUp() {

		newUser2 = userContainer.createUser("Manel", "user2@gmail.com", "001", "Empregado",
				"930000000", "ruinha", "7040-531", "Bucareste", "Porto", "Portugal");
		newUser3 = userContainer.createUser("Manelinho", "user3@gmail.com", "002", "Telefonista",
				"940000000", "ruinha", "7040-531", "Bucareste", "Porto", "Portugal");
		newUser4 = userContainer.createUser("Emanuel", "user4@sapo.com", "003", "Faz tudo",
				"960000000", "ruinha", "7040-531", "Bucareste", "Porto", "Portugal");

		userContainer.getAllUsersFromUserContainer().clear();

		/* Adds the created users to the Company user list */
		userContainer.addUserToUserRepository(newUser2);
		userContainer.addUserToUserRepository(newUser3);
		userContainer.addUserToUserRepository(newUser4);
	}

	@After
	public void tearDown() {
		newUser2 = null;
		newUser3 = null;
		newUser4 = null;
		userContainer = null;
		projectContainer = null;
	}

	@Test
	public void searchEmailThatDoesntExists() {
		/* Compares a search of a mail that doesn't exist with a empty List */
		List<User> emptyList = new ArrayList<User>();
		assertEquals(userContainer.searchUsersByEmail("yahoo"), emptyList);
	}

	@Test
	public void searchByEntireEmail() {
		/* Compares a search by entire mail address with a list with that mail */
		List<User> testUsersEmail1 = new ArrayList<User>();
		testUsersEmail1.add(newUser2);
		assertEquals(userContainer.searchUsersByEmail("user2@gmail.com"), testUsersEmail1);
	}

	@Test
	public void searchBySomeCharacteresOfEmailAfter() {
		/*
		 * Compares a search by some characters (after @) of mail address with a list
		 * with two mails that contain that characters
		 */
		List<User> testUsersEmail2 = new ArrayList<User>();
		testUsersEmail2.add(newUser2);
		testUsersEmail2.add(newUser3);
		assertEquals(userContainer.searchUsersByEmail("gmail"), testUsersEmail2);
	}

	@Test
	public void searchBySomeCharacteresOfEmailBefore() {
		/*
		 * Compares a search by some characters (before @) of mail address with a list
		 * with three mails that contain that characters
		 */
		List<User> testUsersEmail3 = new ArrayList<User>();
		testUsersEmail3.add(newUser2);
		testUsersEmail3.add(newUser3);
		testUsersEmail3.add(newUser4);
		assertEquals(userContainer.searchUsersByEmail("user"), testUsersEmail3);
	}
}