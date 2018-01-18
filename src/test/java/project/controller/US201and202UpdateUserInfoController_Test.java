package project.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.model.Address;
import project.model.Company;
import project.model.Profile;
import project.model.User;

public class US201and202UpdateUserInfoController_Test {

	Company c1;
	User u1;
	Address address1;
	Address address2;

	@Before
	public void setUp() {
		// create company and clear ProjectRepository and UsersRepository

		c1 = Company.getTheInstance();

		// create users
		u1 = c1.getUsersRepository().createUser("Daniel", "user2@gmail.com", "123", "Empregado", "930000000",
				"Rua Maria", "4444-444", "221234567", "Porto", "Portugal");

		// create a new address
		address1 = u1.createAddress("Testy Street", "2401-343", "Testburg", "Testo", "Testistan");
		address2 = u1.createAddress("Testy Street2", "2401-342", "Testburg2", "Testo2", "Testistan2");

		u1.addAddress(address1);
		// set user profile to collaborator
		u1.setUserProfile(Profile.COLLABORATOR);

		// add users to company
		c1.getUsersRepository().addUserToUserRepository(u1);

	}

	@After
	public void tearDown() {
		Company.clear();
		u1 = null;
		address1 = null;
	}

	/**
	 * this test update name
	 */
	@Test
	public void updateUserName() {
		// create controller
		US201and202UpdateUserInfoController controller = new US201and202UpdateUserInfoController();

		assertTrue(u1.getName() == "Daniel");

		controller.updateUserName(u1, "Pedro");

		assertTrue(u1.getName() == "Pedro");
	}

	/**
	 * this test update email
	 */
	@Test
	public void updateUserEmail() {
		// create controller
		US201and202UpdateUserInfoController controller = new US201and202UpdateUserInfoController();

		assertTrue(u1.getEmail() == "user2@gmail.com");

		controller.updateUserEmail(u1, "pedro@gmail.com");

		assertTrue(u1.getEmail() == "pedro@gmail.com");
	}

	/**
	 * this test update phone
	 */
	@Test
	public void updateUserPhone() {
		// create controller
		US201and202UpdateUserInfoController controller = new US201and202UpdateUserInfoController();

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
		US201and202UpdateUserInfoController controller = new US201and202UpdateUserInfoController();

		assertEquals(null, u1.searchUserAddress(address2.getStreet()));

		controller.addNewAddress(u1, address2);

		assertEquals(address2, u1.searchUserAddress(address2.getStreet()));
	}

	@Test
	public void updateUserStreet() {
		US201and202UpdateUserInfoController controller = new US201and202UpdateUserInfoController();

		assertEquals("Testy Street", u1.searchUserAddress(address1.getStreet()).getStreet());

		controller.updateUserStreet(u1, address1.getStreet(), "New Street");

		assertEquals("New Street", u1.searchUserAddress(address1.getStreet()).getStreet());
	}

	@Test
	public void updateUserZipCode() {
		US201and202UpdateUserInfoController controller = new US201and202UpdateUserInfoController();

		assertEquals("2401-343", u1.searchUserAddress(address1.getStreet()).getZipCode());

		controller.updateUserZipCode(u1, address1.getStreet(), "4510-532");

		assertEquals("4510-532", u1.searchUserAddress(address1.getStreet()).getZipCode());
	}

	@Test
	public void updateUserCity() {
		US201and202UpdateUserInfoController controller = new US201and202UpdateUserInfoController();

		assertEquals("Testburg", u1.searchUserAddress(address1.getStreet()).getCity());

		controller.updateUserCity(u1, address1.getStreet(), "Gondomar");

		assertEquals("Gondomar", u1.searchUserAddress(address1.getStreet()).getCity());
	}

	@Test
	public void updateUserDistrict() {
		US201and202UpdateUserInfoController controller = new US201and202UpdateUserInfoController();

		assertEquals("Testo", u1.searchUserAddress(address1.getStreet()).getDistrict());

		controller.updateUserDistrict(u1, address1.getStreet(), "Porto");

		assertEquals("Porto", u1.searchUserAddress(address1.getStreet()).getDistrict());
	}

	@Test
	public void updateUserCountry() {
		US201and202UpdateUserInfoController controller = new US201and202UpdateUserInfoController();

		assertEquals("Testistan", u1.searchUserAddress(address1.getStreet()).getCountry());

		controller.updateUserCountry(u1, address1.getStreet(), "Portugal");

		assertEquals("Portugal", u1.searchUserAddress(address1.getStreet()).getCountry());
	}
	
	/**
	 * This tests if address1 and andress2 are in user 1 list of addresses 
	 */
	@Test
	public void getAllAddressesTest() {
		US201and202UpdateUserInfoController controller = new US201and202UpdateUserInfoController();


		//controller.addNewAddress(u1, address2);
		
		//assertTrue(address1.equals(controller.getAllAddresses(u1).get(0)));
		//assertTrue(address2.equals(controller.getAllAddresses(u1).get(1)));
		assertEquals(controller.getAllAddresses(u1).size(), 2);
	}

}
