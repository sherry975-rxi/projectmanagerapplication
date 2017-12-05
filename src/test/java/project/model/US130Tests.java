package test.java.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.project.model.Company;
import main.java.project.model.User;

class US130Tests {

	/**
	 * Tests US130
	 * 
	 * US130: Como Administrador, pretendo listar todos os utilizadores do sistema.
	 */

	Company myCompany;
	User user1;
	User user2;
	User user3;
	User user4;
	User user5;

	@BeforeEach
	void clearCompany() {
		myCompany = Company.getTheInstance();
		myCompany.getUsersList().clear();

		user1 = myCompany.createUser("Dani", "daniel@gmail.com", "Programador", "910000000", "ruinha", "7040-531",
				"Bucareste", "Porto", "Portugal");
		user2 = myCompany.createUser("Rita", "rita@gmail.com", "Gestora de Projeto", "920000000", "ruinha", "7040-531",
				"Bucareste", "Porto", "Portugal");
		user3 = myCompany.createUser("Joao", "joao@gmail.com", "Programador", "910000000", "ruinha", "7040-531",
				"Bucareste", "Porto", "Portugal");
		user4 = myCompany.createUser("Maria", "maria@gmail.com", "Gestora de Projeto", "920000000", "ruinha",
				"7040-531", "Bucareste", "Porto", "Portugal");
		user5 = myCompany.createUser("Manel", "manel@gmail.com", "Programador", "910000000", "ruinha", "7040-531",
				"Bucareste", "Porto", "Portugal");

		// Adds the created users to the Company user list.
		myCompany.addUserToUserList(user1);
		myCompany.addUserToUserList(user2);
		myCompany.addUserToUserList(user3);
		myCompany.addUserToUserList(user4);
		myCompany.addUserToUserList(user5);

		// Set users's profile type to collaborator
		user1.getProfile().setCollaborator();
		user2.getProfile().setCollaborator();
		user3.getProfile().setCollaborator();
		user4.getProfile().setCollaborator();
		user5.getProfile().setCollaborator();
	}

	@AfterEach
	void tearDown() {
		myCompany = null;
		user1 = null;
		user2 = null;
		user3 = null;
		user4 = null;
		user5 = null;
	}

	@Test
	void US130test() {
		// Create the list of all users in the system
		List<User> result = myCompany.getUsersList();

		// Create list of users to compare and add users to it
		List<User> listOfusers = new ArrayList<User>();

		listOfusers.add(user1);
		listOfusers.add(user2);
		listOfusers.add(user3);
		listOfusers.add(user4);
		listOfusers.add(user5);

		assertEquals(listOfusers, result);
	}

}
