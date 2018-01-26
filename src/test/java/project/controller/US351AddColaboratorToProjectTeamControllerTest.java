package project.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.model.Company;
import project.model.Profile;
import project.model.Project;
import project.model.User;

public class US351AddColaboratorToProjectTeamControllerTest {

	Company myCompany;
	Project activeProj, inactiveProj;
	User activeUser, inactiveUser, projectManager;
	US351AddColaboratorToProjectTeamController controller;

	@Before
	public void setUp() {
		// Initializes company
		myCompany = Company.getTheInstance();

		// Creates and add users to company's user repository
		activeUser = myCompany.getUsersRepository().createUser("Daniel", "daniel@gmail.com", "1234", "Arquitecto",
				"967387654", "Rua", "3700", "Porto", "Porto", "Portugal");
		myCompany.getUsersRepository().addUserToUserRepository(activeUser);
		activeUser.setUserProfile(Profile.COLLABORATOR);

		inactiveUser = myCompany.getUsersRepository().createUser("Rui", "rui@gmail.com", "12345", "Arquitecto",
				"967387654", "Rua", "3800", "Porto", "Porto", "Portugal");
		myCompany.getUsersRepository().addUserToUserRepository(inactiveUser);
		inactiveUser.setUserProfile(Profile.COLLABORATOR);

		projectManager = myCompany.getUsersRepository().createUser("Manel", "user2@gmail.com", "001", "Empregado",
				"930000000", "Testy Street", "2401-343", "Testburg", "Testo", "Testistan");
		myCompany.getUsersRepository().addUserToUserRepository(projectManager);
		projectManager.setUserProfile(Profile.COLLABORATOR);

		// Add project to company's project repository
		activeProj = myCompany.getProjectsRepository().createProject("Quin serch!", "Wii must faind aur quin!",
				projectManager);
		myCompany.getProjectsRepository().addProjectToProjectRepository(activeProj);
		activeProj.setProjectStatus(Project.EXECUTION);

		inactiveProj = myCompany.getProjectsRepository().createProject("Wae follouing!", "Wii must follou da wae!",
				projectManager);
		myCompany.getProjectsRepository().addProjectToProjectRepository(inactiveProj);
		inactiveProj.setProjectStatus(Project.PLANNING);

		// Deactivate inactive user
		inactiveUser.changeUserState();

		// Create controller
		controller = new US351AddColaboratorToProjectTeamController();
	}

	@After
	public void tearDown() {
		Company.clear();
		activeProj = null;
		activeUser = null;
		inactiveUser = null;
		projectManager = null;
		controller = null;
	}

	@Test
	public final void testAddUserToProjectTeam() {
		controller.addUserToProjectTeam(activeUser, activeProj, 5);
		assertTrue(activeProj.isUserInProjectTeam(activeUser));
	}

	@Test
	public final void testGetAllUsers() {
		assertEquals(2, controller.getAllUsers().size());
	}

	@Test
	public final void testSearchUserByID() {
		assertEquals(activeUser, controller.searchUserByID("1234"));
		assertEquals(null, controller.searchUserByID("1264"));
	}

}
