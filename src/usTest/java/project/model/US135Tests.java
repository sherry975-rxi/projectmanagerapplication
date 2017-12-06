package test.java.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.project.model.Company;
import main.java.project.model.User;

class US135Tests {

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

	@BeforeEach
	void setUp() {
		myCompany = Company.getTheInstance();

		newUser2 = myCompany.createUser("Manel", "user2@gmail.com", "001", "Empregado", "930000000", "ruinha",
				"7040-531", "Bucareste", "Porto", "Portugal");
		newUser3 = myCompany.createUser("Manelinho", "user3@gmail.com", "002", "Telefonista", "940000000", "ruinha",
				"7040-531", "Bucareste", "Porto", "Portugal");
		newUser4 = myCompany.createUser("Emanuel", "user4@sapo.com", "003", "Faz tudo", "960000000", "ruinha",
				"7040-531", "Bucareste", "Porto", "Portugal");

		myCompany.getUsersList().clear();
		/* Adds the created users to the Company user list */
		myCompany.addUserToUserList(newUser2);
		myCompany.addUserToUserList(newUser3);
		myCompany.addUserToUserList(newUser4);
	}

	@AfterEach
	void tearDown() {
		myCompany = null;
		newUser2 = null;
		newUser3 = null;
		newUser4 = null;
	}

	@Test
	void searchEmailThatDoesntExists() {
		/* Compares a search of a mail that doesn't exist with a empty List */
		List<User> emptyList = new ArrayList<User>();
		assertEquals(myCompany.searchUsersByEmail("yahoo"), emptyList);
	}

	@Test
	void searchByEntireEmail() {
		/* Compares a search by entire mail address with a list with that mail */
		List<User> testUsersEmail1 = new ArrayList<User>();
		testUsersEmail1.add(newUser2);
		assertEquals(myCompany.searchUsersByEmail("user2@gmail.com"), testUsersEmail1);
	}

	@Test
	void searchBySomeCharacteresOfEmailAfter() {
		/*
		 * Compares a search by some characters (after @) of mail address with a list
		 * with two mails that contain that characters
		 */
		List<User> testUsersEmail2 = new ArrayList<User>();
		testUsersEmail2.add(newUser2);
		testUsersEmail2.add(newUser3);
		assertEquals(myCompany.searchUsersByEmail("gmail"), testUsersEmail2);
	}

	@Test
	void searchBySomeCharacteresOfEmailBefore() {
		/*
		 * Compares a search by some characters (before @) of mail address with a list
		 * with three mails that contain that characters
		 */
		List<User> testUsersEmail3 = new ArrayList<User>();
		testUsersEmail3.add(newUser2);
		testUsersEmail3.add(newUser3);
		testUsersEmail3.add(newUser4);
		assertEquals(myCompany.searchUsersByEmail("user"), testUsersEmail3);
	}
}