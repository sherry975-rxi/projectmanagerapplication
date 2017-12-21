package project.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.model.Company;
import project.model.Project;
import project.model.User;

public class ListProjectCollaboratorsTest {

	
	Company Critical;
	User userManager, user2, newUser2, newUser3;
	Project project1;
	ListProjectCollaboratorController listCollaboratorsController = new ListProjectCollaboratorController();
	RegisterUserController usersController = new RegisterUserController();
	CreateProjectController projectController = new CreateProjectController();
	AddColaboratorToProjectTeamController addCollaboratorController = new AddColaboratorToProjectTeamController();
	RemoveCollaboratorFromProjectController removeCollaboratorController = new RemoveCollaboratorFromProjectController();

	@Before
	public void setUp() {

		// create company
		Critical = Company.getTheInstance();
		
		// creates user four users
		userManager = usersController.addNewUser("Daniel", "daniel@gmail.com", "001", "Porteiro", "920000000", "MuhPass",
				"Testy Street", "2401-343", "Testburg", "Testo", "Testistan");
		user2 = usersController.addNewUser("DanielM", "danielM@gmail.com", "002", "Code Monkey",
				"920000000", "MuhPass", "Testy Street", "2401-343", "Testburg", "Testo", "Testistan");
		newUser2 = usersController.addNewUser("Manel", "user2@gmail.com", "001", "Empregado", "930000000", "MuhPass",
				"Testy Street", "2401-343", "Testburg", "Testo", "Testistan");
		newUser3 = usersController.addNewUser("Manelinho", "user3@gmail.com", "002", "Telefonista",
				"940000000", "MuhPass", "Testy Street", "2401-343", "Testburg", "Testo", "Testistan");
		
		project1 = projectController.createProject("testing", "testing a lot", userManager);
		
		
	}

	@After
	public void tearDown() {

		Company.clear();
		userManager = null;
		user2 = null;
		newUser2 = null;
		newUser3 = null;
		project1 = null;

	}
	
	
	@Test
	public void testListCollaboratorsController() {
		// asserts the project team starts at Size 0
		assertEquals(listCollaboratorsController.listCollaboratorsController(project1).size(), 0);
		
		// adds 3 collaborators to Project Team
		addCollaboratorController.addUserToProjectTeam(user2, project1, 10);
		addCollaboratorController.addUserToProjectTeam(newUser2, project1, 15);
		addCollaboratorController.addUserToProjectTeam(newUser3, project1, 20);
		
		// Confirms all three users were added to Project Team (team size must be 3)
		assertEquals(listCollaboratorsController.listCollaboratorsController(project1).size(), 3);
		assertFalse(project1.isUserInProjectTeam(userManager));
		assertTrue(project1.isUserInProjectTeam(user2));
		assertTrue(project1.isUserInProjectTeam(newUser2));
		assertTrue(project1.isUserInProjectTeam(newUser3));
		
		// removes user2 from project Team and asserts the team size (must be 2)
		removeCollaboratorController.removeProjectCollaboratorFromProjectTeam(project1, user2);
		assertEquals(listCollaboratorsController.listCollaboratorsController(project1).size(), 2);
		assertFalse(project1.isUserActiveInProject(user2));
	}
}
