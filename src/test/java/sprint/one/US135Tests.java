package sprint.one;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.model.Company;
import project.model.ProjectRepository;
import project.model.User;
import project.model.UserRepository;

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

	Company myCompany;
	User newUser2;
	User newUser3;
	User newUser4;
	UserRepository userRepository;
	ProjectRepository projectRepository;

	@Before
	public void setUp() {
		myCompany = Company.getTheInstance();

		newUser2 = myCompany.getUsersRepository().createUser("Manel", "user2@gmail.com", "001", "Empregado",
				"930000000", "ruinha", "7040-531", "Bucareste", "Porto", "Portugal");
		newUser3 = myCompany.getUsersRepository().createUser("Manelinho", "user3@gmail.com", "002", "Telefonista",
				"940000000", "ruinha", "7040-531", "Bucareste", "Porto", "Portugal");
		newUser4 = myCompany.getUsersRepository().createUser("Emanuel", "user4@sapo.com", "003", "Faz tudo",
				"960000000", "ruinha", "7040-531", "Bucareste", "Porto", "Portugal");

		myCompany.getUsersRepository().getAllUsersFromRepository().clear();

		/* Adds the created users to the Company user list */
		myCompany.getUsersRepository().addUserToUserRepository(newUser2);
		myCompany.getUsersRepository().addUserToUserRepository(newUser3);
		myCompany.getUsersRepository().addUserToUserRepository(newUser4);
	}

	@After
	public void tearDown() {
		Company.clear();
		newUser2 = null;
		newUser3 = null;
		newUser4 = null;
	}

	@Test
	public void searchEmailThatDoesntExists() {
		/* Compares a search of a mail that doesn't exist with a empty List */
		List<User> emptyList = new ArrayList<User>();
		assertEquals(myCompany.getUsersRepository().searchUsersByEmail("yahoo"), emptyList);
	}

	@Test
	public void searchByEntireEmail() {
		/* Compares a search by entire mail address with a list with that mail */
		List<User> testUsersEmail1 = new ArrayList<User>();
		testUsersEmail1.add(newUser2);
		assertEquals(myCompany.getUsersRepository().searchUsersByEmail("user2@gmail.com"), testUsersEmail1);
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
		assertEquals(myCompany.getUsersRepository().searchUsersByEmail("gmail"), testUsersEmail2);
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
		assertEquals(myCompany.getUsersRepository().searchUsersByEmail("user"), testUsersEmail3);
	}
}