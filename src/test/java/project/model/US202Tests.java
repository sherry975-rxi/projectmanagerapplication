package test.java.project.model;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.project.model.Company;
import main.java.project.model.User;

class US202Tests {

	Company myCompany;
	User user1;
	Address address1;

	@BeforeEach
	public void setUp() {

		// create company
		myCompany = Company.getTheInstance();
		myCompany.getUsersList().clear();
		// create user
		user1 = myCompany.createUser("Daniel", "daniel@gmail.com", "Director", "910000000", "Rua", "2401-00", "Test",
				"Testo", "Testistan");
		// create a new address

		address1 = user1.createAddress("Testy Street", "2401-343", "Testburg", "Testo", "Testistan");
		// add user to user list
		myCompany.addUserToUserList(user1);
		// set user as collaborator
		user1.getProfile().setCollaborator();
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
	 * Tests US202
	 * 
	 * US202: Como colaborador, eu pretendo associar um novo endereço postal ao meu
	 * perfil.
	 * 
	 * uses methods addAddress(), Address toString()
	 * 
	 * new method: isApprovedUser > if(User.getProfile != 0), return true;
	 * 
	 */

	@Test
	void testAddAddress() {
		// Creates a new list and adds address to that list, to compare with
		// userAddresslist
		List<Address> testList = new ArrayList<Address>();
		testList.add(user1.getAddressList().get(0));
		testList.add(address1);

		assertTrue(testList.equals(user1.getAddressList()));
	}
}
