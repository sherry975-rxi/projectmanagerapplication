package sprint.one;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.model.Company;
import project.model.Profile;
import project.model.User;

public class US201Tests {

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

	@Before
	public void setUp() {
		company = Company.getTheInstance();

		newUserA = company.getUsersRepository().createUser("João", "user2@gmail.com", "123", "Empregado", "930000000",
				"StreetA", "ZipCodeA", "CityA", "DistrictA", "CountryA");
		newUserB = company.getUsersRepository().createUser("Jonny", "user3@gmail.com", "132", "Telefonista",
				"940000000", "StreetB", "ZipCodeB", "CityB", "DistrictB", "CountryB");
		newUserC = company.getUsersRepository().createUser("Juuni", "user4@sapo.com", "321", "Faz tudo", "960000000",
				"StreetC", "ZipCodeC", "CityC", "DistrictC", "CountryC");

	}

	@After
	public void tearDown() {
		Company.clear();
		newUserA = null;
		newUserB = null;
		newUserC = null;

	}

	/**
	 * This test attempts to change a registered user's name, and compares it
	 * against the original one (False)
	 * 
	 */
	@Test
	public void testSetName() {
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
	public void testSetEmail() {
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
	public void testSetNumber() {
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
	public void testChangeNonCollaboratorDate() {
		String Number1 = newUserA.getPhone();
		String Number2 = "91919199191";

		assertEquals(newUserA.getUserProfile(), Profile.UNASSIGNED);

		newUserA.setPhone(Number2);
		assertFalse(newUserA.getPhone().equals(Number1));
	}

	/**
	 * This test attempts to change a non registered user's email to an email
	 * address that already exists, then compares it against the original (Must
	 * return true as Email was not changed)
	 */
	@Test
	public void testAttemptChangeEmailToExistingEmail() {
		newUserA.setUserProfile(Profile.COLLABORATOR);
		newUserB.setUserProfile(Profile.COLLABORATOR);
		;

		String Email2 = newUserA.getEmail();
		String Email3 = newUserB.getEmail();
		String FalseMail = "huehue€troll*org";

		assertTrue(company.getUsersRepository().isEmailAddressValid(Email3));
		assertFalse(company.getUsersRepository().isEmailAddressValid(FalseMail));

		assertTrue(newUserA.getEmail().equals(Email2));
	}

}
