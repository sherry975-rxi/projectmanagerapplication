package test.java.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.project.model.Company;
import main.java.project.model.Profile;
import main.java.project.model.User;

class US136Tests {

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

	@BeforeEach
	void setUp() {
		myCompany = Company.getTheInstance();
		newUser2 = myCompany.createUser("Manel", "user2@gmail.com", "001", "Empregado", "930000000", "rua cinzenta",
				"6789-654", "porto", "porto", "portugal");
		newUser3 = myCompany.createUser("Manelinho", "user3@gmail.com", "002", "Telefonista", "940000000", "rua rosa",
				"6799-654", "porto", "porto", "portugal");

		myCompany.getUsersList().clear();
		/* Set the testCollab profile type to collaborator */

		/* Set the newUser2 and newUser3 profile type to collaborator */
		newUser2.setUserProfile(Profile.COLLABORATOR);
		newUser3.setUserProfile(Profile.COLLABORATOR);
	}

	@AfterEach
	void tearDown() {
		myCompany = null;
		newUser2 = null;
		newUser2 = null;

	}

	@Test
	void testSearchUsersByProfile() {
		/* Compares a search of a profile type that doesn't exist with a empty List */
		List<User> emptyList = new ArrayList<User>();
		assertEquals(myCompany.searchUsersByProfile(Profile.COLLABORATOR), emptyList);
	}

	@Test
	void testSearchUsersByProfile2() {
		/* Adds the created users to the Company user list */
		myCompany.addUserToUserList(newUser2);
		myCompany.addUserToUserList(newUser3);

		/*
		 * Compares a search of collaborator with a list where two collaborators exist
		 */
		List<User> collaboratorstest = new ArrayList<User>();
		collaboratorstest.add(newUser2);
		collaboratorstest.add(newUser3);

		assertEquals(myCompany.searchUsersByProfile(Profile.COLLABORATOR), collaboratorstest);
	}
}