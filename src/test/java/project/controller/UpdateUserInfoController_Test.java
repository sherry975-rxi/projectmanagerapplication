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

public class UpdateUserInfoController_Test {

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
		UpdateUserInfoController controller = new UpdateUserInfoController();

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
		UpdateUserInfoController controller = new UpdateUserInfoController();

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
		UpdateUserInfoController controller = new UpdateUserInfoController();

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
		UpdateUserInfoController controller = new UpdateUserInfoController();

		assertEquals(null, u1.searchUserAddress(address2));

		controller.addNewAddress(u1, address2);

		assertEquals(address2, u1.searchUserAddress(address2));
	}

	@Test
	public void updateUserStreet() {
		UpdateUserInfoController controller = new UpdateUserInfoController();

		assertEquals("Testy Street", u1.searchUserAddress(address1).getStreet());

		controller.updateUserStreet(u1, address1, "New Street");

		assertEquals("New Street", u1.searchUserAddress(address1).getStreet());
	}

	@Test
	public void updateUserZipCode() {
		UpdateUserInfoController controller = new UpdateUserInfoController();

		assertEquals("2401-343", u1.searchUserAddress(address1).getZipCode());

		controller.updateUserZipCode(u1, address1, "4510-532");

		assertEquals("4510-532", u1.searchUserAddress(address1).getZipCode());
	}

	@Test
	public void updateUserCity() {
		UpdateUserInfoController controller = new UpdateUserInfoController();

		assertEquals("Testburg", u1.searchUserAddress(address1).getCity());

		controller.updateUserCity(u1, address1, "Gondomar");

		assertEquals("Gondomar", u1.searchUserAddress(address1).getCity());
	}

	@Test
	public void updateUserDistrict() {
		UpdateUserInfoController controller = new UpdateUserInfoController();

		assertEquals("Testo", u1.searchUserAddress(address1).getDistrict());

		controller.updateUserDistrict(u1, address1, "Porto");

		assertEquals("Porto", u1.searchUserAddress(address1).getDistrict());
	}

	@Test
	public void updateUserCountry() {
		UpdateUserInfoController controller = new UpdateUserInfoController();

		assertEquals("Testistan", u1.searchUserAddress(address1).getCountry());

		controller.updateUserCountry(u1, address1, "Portugal");

		assertEquals("Portugal", u1.searchUserAddress(address1).getCountry());
	}

}
