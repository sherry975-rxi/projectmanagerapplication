package test.java.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.project.model.ProjectCollaborator;
import main.java.project.model.User;

class ProjectCollaboratorTests {

	User tester, testerTwo;
	ProjectCollaborator testMoar, testEvenMoar;

	@BeforeEach
	void SetUp() {
		tester = new User("pepe", "trollolol@mail.com", "12", "hue", "12356");
		testerTwo = new User("pepe", "lolno@mail.com", "12", "hue", "12356");
		testMoar = new ProjectCollaborator(tester, 100000);
		testEvenMoar = new ProjectCollaborator(testerTwo, 100000);
	}

	@AfterEach
	void BreakDown() {
		tester = null;
		testerTwo = null;
		testMoar = null;
		testEvenMoar = null;
	}

	/**
	 * This test creates a user, then a Project Collaborator using its data. First
	 * tests if the Collaborator starts as In Project and if it can be changed to
	 * false, and back to true
	 * 
	 * Second, tests both getters for the Collaborator's cost and personal Data
	 */
	@Test
	void testProjectCollaborator() {

		assertTrue(testMoar.isCollaboratorInProject());
		testMoar.setState(false);
		assertFalse(testMoar.isCollaboratorInProject());
		testMoar.setState(true);
		assertTrue(testMoar.isCollaboratorInProject());

		assertEquals(testMoar.getCollaboratorCost(), 100000);

		assertEquals(tester, testMoar.getCollaboratorUserData());

	}

	@Test
	void testIsUser() {
		assertTrue(testMoar.isUser(tester));

		assertFalse(testMoar.isUser(testerTwo));
	}

	@Test
	void testEquals() {
		assertTrue(testMoar.equals(testMoar));

		assertFalse(testMoar.equals(testEvenMoar));
	}

}
