package test.java.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import main.java.project.model.ProjectCollaborator;
import main.java.project.model.User;

class ProjectCollaboratorTests {

	/**
	 * This test creates a user, then a Project Collaborator using its data. First
	 * tests if the Collaborator starts as In Project and if it can be changed to
	 * false, and back to true
	 * 
	 * Second, tests both getters for the Collaborator's cost and personal Data
	 */
	@Test
	void testProjectCollaborator() {

		User tester = new User("pepe", "trollolol@mail.com", "12", "hue", "12356");

		ProjectCollaborator testMoar = new ProjectCollaborator(tester, 100000);

		assertTrue(testMoar.isCollaboratorInProject());
		testMoar.setState(false);
		assertFalse(testMoar.isCollaboratorInProject());
		testMoar.setState(true);
		assertTrue(testMoar.isCollaboratorInProject());

		assertEquals(testMoar.getCollaboratorCost(), 100000);

		assertEquals(tester, testMoar.getCollaboratorUserData());

	}

}
