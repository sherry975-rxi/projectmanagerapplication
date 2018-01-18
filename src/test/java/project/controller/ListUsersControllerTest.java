package project.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.model.Company;
import project.model.User;

public class ListUsersControllerTest {

	Company Critical;
	User user1, user2, newUser2, newUser3;
	List<User> testList;
	ListUsersController listUsersController = new ListUsersController();

	@Before
	public void setUp() {

		// create company
		Critical = Company.getTheInstance();

		// create a list to compare
		testList = new ArrayList<User>();
	}

	@After
	public void tearDown() {

		Company.clear();
		user1 = null;
		user2 = null;
		newUser2 = null;
		newUser3 = null;
		testList = null;
		listUsersController = null;

	}

	@Test
	public void testListUsersController() {

		Company.clear();
		Critical = Company.getTheInstance();
		Critical.getUsersRepository().getAllUsersFromRepository().clear();

		// creates user four users
		user1 = Critical.getUsersRepository().createUser("Daniel", "daniel@gmail.com", "001", "Porteiro", "920000000",
				"Testy Street", "2401-343", "Testburg", "Testo", "Testistan");
		user2 = Critical.getUsersRepository().createUser("DanielM", "danielM@gmail.com", "002", "Code Monkey",
				"920000000", "Testy Street", "2401-343", "Testburg", "Testo", "Testistan");
		newUser2 = Critical.getUsersRepository().createUser("Manel", "user2@gmail.com", "001", "Empregado", "930000000",
				"Testy Street", "2401-343", "Testburg", "Testo", "Testistan");
		newUser3 = Critical.getUsersRepository().createUser("Manelinho", "user3@gmail.com", "002", "Telefonista",
				"940000000", "Testy Street", "2401-343", "Testburg", "Testo", "Testistan");

		// adds all but newUser3 to the UserRepository and test List
		Critical.getUsersRepository().addUserToUserRepository(user1);
		Critical.getUsersRepository().addUserToUserRepository(user2);
		Critical.getUsersRepository().addUserToUserRepository(newUser2);

		testList.add(user1);
		testList.add(user2);
		testList.add(newUser2);

		// finally, asserts the listUsersController returns only the three Users added
		assertEquals(testList, listUsersController.listUsersController());
		assertEquals(listUsersController.listUsersController().size(), 3);
	}

}
