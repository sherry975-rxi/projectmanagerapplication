package project.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ProjectCollaboratorTest {

	User tester, testerTwo;
	ProjectCollaborator testMoar, testEvenMoar;

	@Before public
	void SetUp() {
		tester = new User("pepe", "trollolol@mail.com", "12", "hue", "12356");
		testerTwo = new User("pepe", "lolno@mail.com", "12", "hue", "12356");
		testMoar = new ProjectCollaborator(tester, 100000);
		testEvenMoar = new ProjectCollaborator(testerTwo, 100000);
	}

	@After public
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
	@Test public
	void testProjectCollaborator() {

		assertTrue(testMoar.isCollaboratorInProject());
		testMoar.setState(false);
		assertFalse(testMoar.isCollaboratorInProject());
		testMoar.setState(true);
		assertTrue(testMoar.isCollaboratorInProject());

		assertEquals(testMoar.getCollaboratorCost(), 100000);

		assertEquals(tester, testMoar.getCollaboratorUserData());

	}

	@Test public
	void testIsUser() {
		assertTrue(testMoar.isUser(tester));

		assertFalse(testMoar.isUser(testerTwo));
	}

	@Test public
	void testEquals() {
		assertTrue(testMoar.equals(testMoar));

		assertFalse(testMoar.equals(testEvenMoar));
	}

}
