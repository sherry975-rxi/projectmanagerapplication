package test.usTest.java.project.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.project.model.Address;
import main.java.project.model.Company;
import main.java.project.model.Profile;
import main.java.project.model.User;

class US202Tests {

	/**
	 * Tests US202
	 * 
	 * US202: Como colaborador, eu pretendo associar um novo endereço postal ao meu
	 * perfil.
	 * 
	 * uses methods addAddress(), Address toString()
	 * 
	 * 
	 */

	Company myCompany;
	User user1;
	Address address1;

	@BeforeEach
	public void setUp() {

		// create company
		myCompany = Company.getTheInstance();
		myCompany.getUsersRepository().getAllUsersFromRepository().clear();
		
		// create user
		user1 = myCompany.getUsersRepository().createUser("Daniel", "daniel@gmail.com", "001", "Director", "910000000", "Rua", "2401-00",
				"Test", "Testo", "Testistan");
		
		// create a new address
		address1 = user1.createAddress("Testy Street", "2401-343", "Testburg", "Testo", "Testistan");
		
		// add user to user list		
		myCompany.getUsersRepository().addUserToUserRepository(user1);
		
		// set user as collaborator
		user1.setUserProfile(Profile.COLLABORATOR);
		
		// add address to collaborator
		user1.addAddress(address1);

	}

	@AfterEach
	void tearDown() {
		myCompany = null;
		user1 = null;
		address1 = null;
	}

	/**
	 * This test verifies if the user is collaborator, then tests the addition of
	 * addresses
	 */
	@Test
	void testAddAddress() {
		// Verifies if User is Collaborator
		assertEquals(user1.getUserProfile(), Profile.COLLABORATOR);

		// Creates a new list and adds address to that list, to compare with
		// userAddresslist
		List<Address> testList = new ArrayList<Address>();
		testList.add(user1.getAddressList().get(0));
		testList.add(address1);

		assertTrue(testList.equals(user1.getAddressList()));
	}
}
