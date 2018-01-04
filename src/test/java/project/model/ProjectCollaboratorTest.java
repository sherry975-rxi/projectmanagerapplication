package project.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ProjectCollaboratorTest {

	User tester, testerTwo;
	ProjectCollaborator testMoar, testEvenMoar, testCollaboratorNull, testCollaboratorNull2;

	@Before
	public void SetUp() {
		tester = new User("pepe", "trollolol@mail.com", "12", "hue", "12356");
		testerTwo = new User("pepe", "lolno@mail.com", "12", "hue", "12356");

		testMoar = new ProjectCollaborator(tester, 100000);
		testEvenMoar = new ProjectCollaborator(testerTwo, 100000);
		testCollaboratorNull = new ProjectCollaborator(null, 100000);
		testCollaboratorNull2 = new ProjectCollaborator(null, 100000);

	}

	@After
	public void BreakDown() {
		tester = null;
		testerTwo = null;
		testMoar = null;
		testEvenMoar = null;
		testCollaboratorNull = null;
		testCollaboratorNull2 = null;
	}

	/**
	 * This test creates a user, then a Project Collaborator using its data. First
	 * tests if the Collaborator starts as In Project and if it can be changed to
	 * false, and back to true
	 * 
	 * Second, tests both getters for the Collaborator's cost and personal Data
	 */
	@Test
	public void testProjectCollaborator() {

		assertTrue(testMoar.isProjectCollaboratorActive());
		testMoar.setState(false);
		assertFalse(testMoar.isProjectCollaboratorActive());
		testMoar.setState(true);
		assertTrue(testMoar.isProjectCollaboratorActive());

		assertEquals(testMoar.getCollaboratorCost(), 100000);

		assertEquals(tester, testMoar.getUserFromProjectCollaborator());

	}

	@Test
	public void testIsUser() {
		assertTrue(testMoar.isUser(tester));

		assertFalse(testMoar.isUser(testerTwo));
	}

	/**
	 * Tests several combinations of the Equals override
	 * 
	 */
	@Test
	public void testEquals() {
		assertTrue(testMoar.equals(testMoar));// same object
		ProjectCollaborator testingNonStop = null;
		assertFalse(testMoar.equals(testingNonStop));// null object
		assertFalse(testMoar.equals(testerTwo));// different classes
		assertFalse(testMoar.equals(testEvenMoar));// different user
		testingNonStop = new ProjectCollaborator(tester, 100000);
		assertTrue(testMoar.equals(testingNonStop));// same user
	}

	/**
	 * Tests hashcode class
	 * 
	 */
	@Test
	public void testHashCode() {
		assertTrue(testMoar.hashCode() == testMoar.hashCode());
		assertFalse(tester.hashCode() == testMoar.hashCode());

		assertTrue(testCollaboratorNull.hashCode() == testCollaboratorNull2.hashCode());
		assertFalse(testMoar.hashCode() == testCollaboratorNull.hashCode());
	}

}
