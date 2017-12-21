package project.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.model.Company;
import project.model.Profile;
import project.model.User;

public class UpdateUserInfoController_Test {

	Company c1;
	User u1;

	@Before
	public void setUp() {
		// create company and clear ProjectRepository and UsersRepository

		c1 = Company.getTheInstance();

		// create users
		u1 = c1.getUsersRepository().createUser("Daniel", "user2@gmail.com", "123", "Empregado", "930000000",
				"Rua Maria", "4444-444", "221234567", "Porto", "Portugal");

		// set user profile to collaborator
		u1.setUserProfile(Profile.COLLABORATOR);

		// add users to company
		c1.getUsersRepository().addUserToUserRepository(u1);

	}

	@After
	public void tearDown() {
		Company.clear();
		u1 = null;
	}

	@Test
	public void updateUserInfo() {

	}

	@Test
	public void addNewAddress() {

	}

}
