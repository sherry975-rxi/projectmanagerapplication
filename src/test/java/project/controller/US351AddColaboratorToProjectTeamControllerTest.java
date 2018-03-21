package project.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import project.Repository.ProjectsRepository;
import project.Repository.TaskRepository;
import project.Repository.UserRepository;
import project.Services.ProjectService;
import project.Services.UserService;
import project.model.Profile;
import project.model.Project;
import project.model.User;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class US351AddColaboratorToProjectTeamControllerTest {
	
	UserService userContainer;
	ProjectService projectContainer;
	Project activeProj, inactiveProj;
	User activeUser, inactiveUser, projectManager;
	US351AddColaboratorToProjectTeamController controller;


	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProjectsRepository projectRepository;
	
	@Before
	public void setUp() {
		// creates an UserContainer
		userContainer = new UserService();
		userContainer.setUserRepository(userRepository);
		
		// creates a Project Container
		projectContainer = new ProjectService();
		projectContainer.setProjectsRepository(projectRepository);
		
		// Create controller
		controller = new US351AddColaboratorToProjectTeamController();
		controller.projectService = projectContainer;

		// Creates and add users to company's user repository
		activeUser = userContainer.createUser("Daniel", "daniel@gmail.com", "1234", "Arquitecto",
				"967387654", "Rua", "3700", "Porto", "Porto", "Portugal");
//		userContainer.addUserToUserRepositoryX(activeUser);
		activeUser.setUserProfile(Profile.COLLABORATOR);

		inactiveUser = userContainer.createUser("Rui", "rui@gmail.com", "12345", "Arquitecto",
				"967387654", "Rua", "3800", "Porto", "Porto", "Portugal");
//		userContainer.addUserToUserRepositoryX(inactiveUser);
		inactiveUser.setUserProfile(Profile.COLLABORATOR);

		projectManager = userContainer.createUser("Manel", "user2@gmail.com", "001", "Empregado",
				"930000000", "Testy Street", "2401-343", "Testburg", "Testo", "Testistan");
//		userContainer.addUserToUserRepositoryX(projectManager);
		projectManager.setUserProfile(Profile.COLLABORATOR);

		// Add project to company's project repository
		activeProj = projectContainer.createProject("Quin serch!", "Wii must faind aur quin!",
				projectManager);
//		projectContainer.addProjectToProjectContainer(activeProj);
		activeProj.setProjectStatus(Project.EXECUTION);

		inactiveProj = projectContainer.createProject("Wae follouing!", "Wii must follou da wae!",
				projectManager);
//		projectContainer.addProjectToProjectContainer(inactiveProj);
		inactiveProj.setProjectStatus(Project.PLANNING);

		// Deactivate inactive user
		inactiveUser.changeUserState();

		
	}

//	@After
//	public void tearDown() {
//		projectContainer = null;
//		userContainer = null;
//		activeProj = null;
//		activeUser = null;
//		inactiveUser = null;
//		projectManager = null;
//		controller = null;
//	}

	@Test
	public final void testAddUserToProjectTeam() {
		controller.addUserToProjectTeam(activeUser, activeProj, 5);
		assertTrue(projectContainer.isUserInProjectTeam(activeUser, activeProj));
		
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

	@Test
	public final void testIsUserAlreadyInProject() {
		assertFalse(controller.isUserAlreadyInProject(activeUser, activeProj));

		controller.addUserToProjectTeam(activeUser, activeProj, 23);

		assertTrue(controller.isUserAlreadyInProject(activeUser, activeProj));
	}

}
