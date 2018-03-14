package project.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.Services.ProjectService;
import project.Services.UserContainerService;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.User;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class US355ViewProjectTeamAndThenRemoveCollaboratorControllerTest {

	UserContainerService userContainer;
	ProjectService projectContainer;

	User managerTester, teamPermanentMember;

	Project testProject;

	ProjectCollaborator teamPermanentCollaborator;

	US355ViewProjectTeamAndThenRemoveCollaboratorController viewProjectTeamAndThenRemoveCollaborator;

	@Before
	public void setUp() {
		// creates an UserContainer
		userContainer = new UserContainerService();
				
		// creates a Project Container
		projectContainer = new ProjectService();
		
		// creates test users for a manager and collaborator.
		// declares the collaborator's relevant data as Strings to facilitate assertions

		managerTester = userContainer.createUser("menager", "manager@mail.mail",
				"11111", "function", "123456789", "rua", "0000", "porto", "porto", "PORTO");

		teamPermanentMember = userContainer.createUser("Mr permanent",
				"permie@mail.mail", "33333", "placeholding", "98644", "rua", "0000", "porto", "porto", "PORTO");

		userContainer.addUserToUserRepository(managerTester);
		userContainer.addUserToUserRepository(teamPermanentMember);

		// creates a new test project, and adds the test Collaborator to the team
		testProject = projectContainer.createProject("testing proj",
				"shoot rocket... again", managerTester);

		teamPermanentCollaborator = new ProjectCollaborator(teamPermanentMember, 2000);

		testProject.addProjectCollaboratorToProjectTeam(teamPermanentCollaborator);
		projectContainer.addProjectToProjectContainer(testProject);

		viewProjectTeamAndThenRemoveCollaborator = new US355ViewProjectTeamAndThenRemoveCollaboratorController(
				this.testProject);
	}

	@After
	public void breakDown() {
		userContainer = null;
		projectContainer = null;

		managerTester = null;

		teamPermanentMember = null;

		testProject = null;

	}

	@Test
	public final void testRemoveCollaboratorFromProjectTeam() {
		assertEquals(1, testProject.getActiveProjectTeam().size());

		assertTrue(viewProjectTeamAndThenRemoveCollaborator.removeCollaboratorFromProjectTeam(teamPermanentMember));

		assertEquals(0, testProject.getActiveProjectTeam().size());
	}

	@Test
	public final void testGetUserByProjectCollaborator() {

		List<User> listToCompare = new ArrayList<User>();
		listToCompare.add(teamPermanentMember);

		assertEquals(listToCompare, viewProjectTeamAndThenRemoveCollaborator.getActiveProjectCollaboratorFromTeam());
	}

	@Test
	public final void testGetProjectTeamName() {

		List<String> listToCampere = new ArrayList<String>();
		listToCampere.add("[1]Mr permanent");
		assertEquals(listToCampere, viewProjectTeamAndThenRemoveCollaborator.getProjectTeamName());
	}

	@Test
	public final void testSplitStringByFirstSpace() {
		String toCompere = "1 qwerty";

		assertEquals("1", viewProjectTeamAndThenRemoveCollaborator.splitStringByFirstSpace(toCompere));
	}

}
