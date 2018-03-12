package sprint.one;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project.model.Profile;
import project.model.User;
import project.model.UserContainer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class US130Tests {

	/**
	 * Tests US130
	 * 
	 * US130: Como Administrador, pretendo listar todos os utilizadores do sistema.
	 */

	UserContainer userContainer;
	User user1;
	User user2;
	User user3;
	User user4;
	User user5;

	@Before
	public void setUpCompany() {

		userContainer = new UserContainer();

		user1 = userContainer.createUser("Dani", "daniel@gmail.com", "001", "Programador", "910000000",
				"ruinha", "7040-531", "Bucareste", "Porto", "Portugal");
		user2 = userContainer.createUser("Rita", "rita@gmail.com", "002", "Gestora de Projeto",
				"920000000", "ruinha", "7040-531", "Bucareste", "Porto", "Portugal");
		user3 = userContainer.createUser("Joao", "joao@gmail.com", "003", "Programador", "910000000",
				"ruinha", "7040-531", "Bucareste", "Porto", "Portugal");
		user4 = userContainer.createUser("Maria", "maria@gmail.com", "004", "Gestora de Projeto",
				"920000000", "ruinha", "7040-531", "Bucareste", "Porto", "Portugal");
		user5 = userContainer.createUser("Manel", "manel@gmail.com", "005", "Programador", "910000000",
				"ruinha", "7040-531", "Bucareste", "Porto", "Portugal");

		// Adds the created users to the Company user list.
		userContainer.addUserToUserRepository(user1);
		userContainer.addUserToUserRepository(user2);
		userContainer.addUserToUserRepository(user3);
		userContainer.addUserToUserRepository(user4);
		userContainer.addUserToUserRepository(user5);

		// Set users's profile type to collaborator
		user1.setUserProfile(Profile.COLLABORATOR);
		user2.setUserProfile(Profile.COLLABORATOR);
		user3.setUserProfile(Profile.COLLABORATOR);
		user4.setUserProfile(Profile.COLLABORATOR);
		user5.setUserProfile(Profile.COLLABORATOR);
	}

	@After
	public void tearDown() {

		userContainer = null;
		user1 = null;
		user2 = null;
		user3 = null;
		user4 = null;
		user5 = null;
	}

	@Test
	public void US130test() {
		// Create the list of all users in the system
		List<User> result = userContainer.getAllUsersFromUserContainer();

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
