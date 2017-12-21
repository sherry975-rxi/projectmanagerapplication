package project.controller;

import static org.junit.Assert.assertFalse;
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

	@Before
	public void setUp() {
		// create company and clear ProjectRepository and UsersRepository

		c1 = Company.getTheInstance();

		// create users
		u1 = c1.getUsersRepository().createUser("Daniel", "user2@gmail.com", "123", "Empregado", "930000000",
				"Rua Maria", "4444-444", "221234567", "Porto", "Portugal");

		// create a new address
		address1 = u1.createAddress("Testy Street", "2401-343", "Testburg", "Testo", "Testistan");

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

		assertFalse(u1.getAddressList().contains(address1));

		controller.addNewAddress(u1, address1);

		assertTrue(u1.getAddressList().contains(address1));
	}

	@Test
	public void updateUserStreet() {
		UpdateUserInfoController controller = new UpdateUserInfoController();

		assertFalse(u1.getAddressList().get(0).getStreet() == "Testy Street");

		controller.updateUserStreet(u1, address1, "Testy Street2");

		assertFalse(u1.getAddressList().get(0).getStreet() == "Testy Street2");
	}

	@Test
	public void updateUserZipCode() {
		UpdateUserInfoController controller = new UpdateUserInfoController();
		
		
		controller.updateUser
	}

	@Test
	public void updateUserCity() {
		UpdateUserInfoController controller = new UpdateUserInfoController();
		
		
		controller.updateUser
	}

	@Test
	public void updateUserDistrict() {
		UpdateUserInfoController controller = new UpdateUserInfoController();
		
		
		controller.updateUser
	}

	@Test
	public void updateUserCountry() {
		UpdateUserInfoController controller = new UpdateUserInfoController();
		
		
		controller.updateUser
	}

}
