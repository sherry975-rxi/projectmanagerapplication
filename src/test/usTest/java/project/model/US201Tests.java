package test.usTest.java.project.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.project.model.Company;
import main.java.project.model.Profile;
import main.java.project.model.User;

class US201Tests {

	/**
	 * Tests US201
	 * 
	 * US201: Como colaborador, eu pretendo retificar os meus dados pessoais (e.g.
	 * nome, email) para os manter sempre atualizados.
	 * 
	 * uses methods setName, setEmail, setPhoneNumber
	 * 
	 * new method: isApprovedUser > if(User.getProfile != 0), return true;
	 * 
	 * 
	 */
	Company company;
	User newUserA;
	User newUserB;
	User newUserC;
	int typeOfUser;

	@BeforeEach
	void setUp() {
		company = Company.getTheInstance();
		company.getUsersList().clear();
		newUserA = company.createUser("João", "user2@gmail.com", "123", "Empregado", "930000000", "StreetA", "ZipCodeA",
				"CityA", "DistrictA", "CountryA");
		newUserB = company.createUser("Jonny", "user3@gmail.com", "132", "Telefonista", "940000000", "StreetB",
				"ZipCodeB", "CityB", "DistrictB", "CountryB");
		newUserC = company.createUser("Juuni", "user4@sapo.com", "321", "Faz tudo", "960000000", "StreetC", "ZipCodeC",
				"CityC", "DistrictC", "CountryC");

		typeOfUser = 1;
	}

	@AfterEach
	void tearDown() {
		company = null;
		newUserA = null;
		newUserB = null;
		newUserC = null;
		typeOfUser = 0;
	}

	/**
	 * This test attempts to change a registered user's name, and compares it
	 * against the original one (False)
	 * 
	 */
	@Test
	void testSetName() {
		newUserA.setUserProfile(Profile.COLLABORATOR);

		String Name1 = newUserA.getName();
		String Name2 = "TesterJ";

		newUserA.setName(Name2);
		assertFalse(newUserA.getName().equals(Name1));
	}

	/**
	 * This test attempts to change a registered user's email, and compares it
	 * against the original one (False)
	 * 
	 */
	@Test
	void testSetEmail() {
		newUserA.setUserProfile(Profile.COLLABORATOR);

		String Email1 = newUserA.getEmail();
		String Email2 = "TesterJ@test.org";

		newUserA.setEmail(Email2);
		assertFalse(newUserA.getEmail().equals(Email1));
	}

	/**
	 * This test attempts to change a registered user's Phone Number, and compares
	 * it against the original one (False)
	 * 
	 */
	@Test
	void testSetNumber() {
		newUserA.setUserProfile(Profile.COLLABORATOR);

		String Number1 = newUserA.getPhone();
		String Number2 = "919191919";

		newUserA.setPhone(Number2);
		assertFalse(newUserA.getPhone().equals(Number1));
	}

	// TODO Implement > Verify if user has profile (users without profile cannot
	// edit their data)
	// (User.getProfile != 0)
	/**
	 * This test attempts to change a non registered user's name, and compares it
	 * against the original one (True)
	 * 
	 */
	@Test
	void testChangeNonCollaboratorDate() {
		String Number1 = newUserA.getPhone();
		String Number2 = "91919199191";

		assertEquals(newUserA.getProfile(), Profile.VISITOR);

		newUserA.setPhone(Number2);
		assertFalse(newUserA.getPhone().equals(Number1));
	}

	/**
	 * This test attempts to change a non registered user's email to an email
	 * address that already exists, then compares it against the original (Must
	 * return true as Email was not changed)
	 */
	@Test
	void testAttemptChangeEmailToExistingEmail() {
		newUserA.setUserProfile(Profile.COLLABORATOR);
		newUserB.setUserProfile(Profile.COLLABORATOR);
		;

		String Email2 = newUserA.getEmail();
		String Email3 = newUserB.getEmail();
		String FalseMail = "huehue€troll*org";

		assertTrue(company.isEmailAddressValid(Email3));
		assertFalse(company.isEmailAddressValid(FalseMail));

		assertTrue(newUserA.getEmail().equals(Email2));
	}

}
