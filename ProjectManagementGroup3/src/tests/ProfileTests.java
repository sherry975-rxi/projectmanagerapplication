/**
 * 
 */
package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import code.Company;
import code.Profile;
import code.User;

class ProfileTests {
	User user;
	Company comp;
	Profile visitor;
	Profile collaborator;
	Profile director;

	@BeforeEach
	public void setUp() {
		comp = Company.getTheInstance();
		user = new User("name", "email", "idNumber", "function", "123456789");
		visitor = new Profile();
		visitor.setVisitor();
		collaborator = new Profile();
		collaborator.setCollaborator();
		director = new Profile();
		director.setDirector();

	}

	/**
	 * Tests the setDirector method by asserting if the user profile after been set
	 * to director equals DIRECTOR.
	 */
	@Test
	void setDirector() {
		user.getProfile().setDirector();
		assertEquals(Profile.DIRECTOR, user.getProfile().getProfileInt());
	}

}
