package project.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import project.services.UserService;
import project.model.Address;
import project.model.Profile;
import project.model.User;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan({ "project.services", "project.model", "project.controller" })
public class US201and202UpdateUserInfoController_Test {

	@Autowired
	UserService userContainer;

	User u1;
	Address address1;
	Address address2;


	@Autowired
	US201and202UpdateUserInfoController controller;

	@Before
	public void setUp() {
		// create company and clear ProjectsRepository and UsersRepository

		// create users
		u1 = userContainer.createUser("Daniel", "user2@gmail.com", "123", "Empregado", "930000000", "Rua Maria",
				"4444-444", "221234567", "Porto", "Portugal");
		u1.getAddressList().clear();
		// create a new address
		address1 = u1.createAddress("Testy Street", "2401-343", "Testburg", "Testo", "Testistan");
		address1.setUser(u1);
		address2 = u1.createAddress("Testy Street2", "2401-342", "Testburg2", "Testo2", "Testistan2");

		u1.addAddress(address1);
		// set user profile to collaborator
		u1.setUserProfile(Profile.COLLABORATOR);

		// add users to company
		userContainer.addUserToUserRepositoryX(u1);


	}

	/**
	 * this tests database saving method
	 */
	@Test
	public void updateAndSaveToDB() {

		assertEquals(userContainer.getAllUsersFromUserContainer().size(), 1);

		assertTrue(u1.getName() == "Daniel");

		controller.updateUserName(u1, "Pedro");

		assertTrue(u1.getName() == "Pedro");

		controller.alterUser(u1);

		assertEquals(userContainer.getAllUsersFromUserContainer().size(), 1);

		assertTrue("Pedro".equals(userContainer.getAllUsersFromUserContainer().get(0).getName()));
	}

	/**
	 * this test update name
	 */
	@Test
	public void updateUserName() {

		assertTrue(u1.getName() == "Daniel");

		controller.updateUserName(u1, "Pedro");

		assertTrue(u1.getName() == "Pedro");
	}

	/**
	 * this test update email
	 */
	@Test
	public void updateUserEmail() {

		assertTrue(u1.getEmail() == "user2@gmail.com");

		controller.updateUserEmail(u1, "pedro@gmail.com");

		assertTrue(u1.getEmail() == "pedro@gmail.com");
	}

	/**
	 * this test update phone
	 */
	@Test
	public void updateUserPhone() {

		assertTrue(u1.getPhone() == "930000000");

		controller.updateUserPhone(u1, "950000000");

		assertTrue(u1.getPhone() == "950000000");

	}

	/**
	 * This test checks that address1 is not in user's address list and then add
	 * this address with controller. Last assertTrue checks if the address1 is in
	 * user's address list.
	 */
	@Test
	public void addNewAddressTest() {

		assertEquals(null, u1.searchUserAddress(address2.getStreet()));

		controller.addNewAddress(u1, address2);

		assertEquals(address2, u1.searchUserAddress(address2.getStreet()));
	}

	@Test
	public void updateUserStreet() {

		assertEquals("Testy Street", u1.searchUserAddress(address1.getStreet()).getStreet());

		controller.updateUserStreet(u1, "Testy Street", "New Street");

		assertEquals("New Street", u1.searchUserAddress("New Street").getStreet());
	}

	@Test
	public void updateUserZipCode() {

		assertEquals("2401-343", u1.searchUserAddress(address1.getStreet()).getZipCode());

		controller.updateUserZipCode(u1, address1.getStreet(), "4510-532");

		assertEquals("4510-532", u1.searchUserAddress(address1.getStreet()).getZipCode());
	}

	@Test
	public void updateUserCity() {

		assertEquals("Testburg", u1.searchUserAddress(address1.getStreet()).getCity());

		controller.updateUserCity(u1, address1.getStreet(), "Gondomar");

		assertEquals("Gondomar", u1.searchUserAddress(address1.getStreet()).getCity());
	}

	@Test
	public void updateUserDistrict() {

		assertEquals("Testo", u1.searchUserAddress(address1.getStreet()).getDistrict());

		controller.updateUserDistrict(u1, address1.getStreet(), "Porto");

		assertEquals("Porto", u1.searchUserAddress(address1.getStreet()).getDistrict());
	}

	@Test
	public void updateUserCountry() {

		assertEquals("Testistan", u1.searchUserAddress(address1.getStreet()).getCountry());

		controller.updateUserCountry(u1, address1.getStreet(), "Portugal");

		assertEquals("Portugal", u1.searchUserAddress(address1.getStreet()).getCountry());
	}

	/**
	 * This tests if address1 and andress2 are in user 1 list of addresses
	 */
	@Test
	public void getAllAddressesTest() {

		// controller.addNewAddress(u1, address2);

		// assertTrue(address1.equals(controller.getAllAddresses(u1).get(0)));
		// assertTrue(address2.equals(controller.getAllAddresses(u1).get(1)));
		assertEquals(controller.getAllAddresses(u1).size(), 1);
	}

	/**
	 * This tests if the email that the user typed is valid
	 */
	@Test
	public void isEmailValid() {
		// create controller
		// checks if the email is valid
		assertTrue(controller.isEmailValid(u1.getEmail()));
		// Sets the email to an invalid email
		u1.setEmail("invalidEmail");
		// Checks that the email is invalid
		assertFalse(controller.isEmailValid(u1.getEmail()));

	}

	/**
	 * This tests if the email that the user typed is already in use
	 */
	@Test
	public void isEmailAlreadyInUse() {
		// create controller
		// Creates a new string with an email that is already in use
		String newEmail = new String("user2@gmail.com");
		// Checks that the email is already in use
		assertTrue(controller.isEmailAlreadyInUse(newEmail));
		// Sets the email to a email that its not in use
		newEmail = "newemail@gmail.com";
		// Checks that the email is invalid
		assertFalse(controller.isEmailAlreadyInUse(newEmail));

	}

	/**
	 * This tests if the update User Password
	 */
	@Test
	public void updateUserPassword() {
		// create controller
		String newPassword = new String("newPassword");
		controller.updateUserPassword(u1, "newPassword");

	}

	/**
	 * This tests the get methods of the controller
	 */
	@Test
	public void getMethodsTests() {
		// Gets the name and see if it matches with the user name
		assertEquals(controller.getName(u1), "Daniel");
		// Gets the user email and see if it matches with the user email
		assertEquals(controller.getEmail(u1), "user2@gmail.com");
		// Gets the user id and see if it matches with the user's id
		assertEquals(controller.getIdNumber(u1), "123");
		// Gets the user function and see if it matches with the user's function
		assertEquals(controller.getFunction(u1), "Empregado");
		// Gets the user phone number and see if it matches with the user's phone number
		assertEquals(controller.getPhone(u1), "930000000");
		// Gets the user Street and see if it matches with the user's street
		assertEquals(controller.getStreet(address1), "Testy Street");
		// Gets the user city and see if it matches with the user's city
		assertEquals(controller.getCity(address1), "Testburg");
		// Gets the user Country number and see if it matches with the user's Country
		assertEquals(controller.getCountry(address1), "Testistan");
		// Gets the user Country number and see if it matches with the user's Country
		assertEquals(controller.getDistrict(address1), "Testo");
		// Gets the user zip code and see if it matches with the user's zip-code
		assertEquals(controller.getZipCode(address1), "2401-343");

	}

	@Test
	public void getAddressList() {

		u1.getAddressList().clear();

		// create a new address
		address1 = u1.createAddress("Testy Street", "2401-343", "Testburg", "Testo", "Testistan");
		address2 = u1.createAddress("Testy Street2", "2401-342", "Testburg2", "Testo2", "Testistan2");

		u1.addAddress(address1);
		// set user profile to collaborator
		u1.setUserProfile(Profile.COLLABORATOR);

		userContainer.addUserToUserRepositoryX(u1);

		// Creates a list with the addresses of the user
		List<Address> userAddresses = new ArrayList<>();

		// Adds address 1 to the user address list
		userAddresses.add(address1);

		// checks that both objects contain the same addresses (same size and matching
		// street)
		assertEquals(controller.getAllAddresses(u1).size(), userAddresses.size());
		assertTrue("Testy Street".equals(controller.getAllAddresses(u1).get(0).getStreet()));

		// adds a new address to User 1
		u1.addAddress(address2);

		// adds a new address to the comparison list
		userAddresses.add(address2);

		// check if both results are the same, both lists having the same size and
		// streets matching
		assertEquals(controller.getAllAddresses(u1).size(), userAddresses.size());
		assertTrue("Testy Street".equals(controller.getAllAddresses(u1).get(0).getStreet()));
		assertTrue("Testy Street2".equals(controller.getAllAddresses(u1).get(1).getStreet()));

	}

	@Test
	public void searchUserAddress() {
		// create a new address
		address1 = u1.createAddress("Testy Street", "2401-343", "Testburg", "Testo", "Testistan");
		address2 = u1.createAddress("Testy Street2", "2401-342", "Testburg2", "Testo2", "Testistan2");

		// Adds address 1 to User1
		u1.addAddress(address1);

		// Checks if the searchUserAddress method in returns the address
		assertEquals(controller.searchUserAddress(u1, "Testy Street"), address1);

		/*
		 * Checks if the method in the controller returns null when it doesn't find a
		 * street;
		 */
		assertEquals(controller.searchUserAddress(u1, "NullStreet"), null);

	}

	/**
	 * Tests the creator of an address in controller
	 */
	@Test
	public void testCreateNewAddress() {
		// create a new address in user u1
		address2 = u1.createAddress("Testy Street2", "2401-342", "Testburg2", "Testo2", "Testistan2");
		// create an identical address in controller
		address1 = controller.createNewAddress("Testy Street2", "2401-342", "Testburg2", "Testo2", "Testistan2");
		// compares similar objects
		assertTrue(address2.equals(address1));

	}

}
