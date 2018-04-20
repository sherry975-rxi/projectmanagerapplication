package project.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.User;
import project.services.ProjectService;
import project.services.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan(basePackages = {"project.services", "project.controllers", "project.model"})
public class US355ViewProjectTeamAndThenRemoveCollaboratorControllerTest {

	@Autowired
	UserService userContainer;

	@Autowired
	ProjectService projectContainer;

	User managerTester, teamPermanentMember;

	Project testProject;

	ProjectCollaborator teamPermanentCollaborator;

	@Autowired
	US355ViewProjectTeamAndThenRemoveCollaboratorController viewProjectTeamAndThenRemoveCollaborator;

	@Before
	public void setUp() {

		
		// creates test users for a manager and collaborator.
		// declares the collaborator's relevant data as Strings to facilitate assertions

		managerTester = userContainer.createUser("menager", "manager@mail.mail",
				"11111", "function", "123456789", "rua", "0000", "porto", "porto", "PORTO");

		teamPermanentMember = userContainer.createUser("Mr permanent",
				"permie@mail.mail", "33333", "placeholding", "98644", "rua", "0000", "porto", "porto", "PORTO");


		// creates a new test project, and adds the test Collaborator to the team
		testProject = projectContainer.createProject("testing proj",
				"shoot rocket... again", managerTester);

		teamPermanentCollaborator = projectContainer.createProjectCollaborator(teamPermanentMember, testProject, 2000);

		projectContainer.updateProject(testProject);

		viewProjectTeamAndThenRemoveCollaborator.setProj(testProject);
	}



	@Test
	public final void testRemoveCollaboratorFromProjectTeam() {
		//given a project with one team member
		assertEquals(1, projectContainer.getActiveProjectTeam(testProject).size());
		assertTrue(teamPermanentCollaborator.isStatus());
		assertNull(teamPermanentCollaborator.getFinishDate());


		//when the team member is removed via the controllers
		assertTrue(viewProjectTeamAndThenRemoveCollaborator.removeCollaboratorFromProjectTeam(teamPermanentMember));
        assertFalse(teamPermanentCollaborator.isStatus());
		assertNotNull(teamPermanentCollaborator.getFinishDate());

		// then the active team must be empty
		assertEquals(0, projectContainer.getActiveProjectTeam(testProject).size());
		//and the list of all collaborators (active/inactive) must contain 1
        assertEquals(1, projectContainer.getProjectTeam(testProject).size());
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
