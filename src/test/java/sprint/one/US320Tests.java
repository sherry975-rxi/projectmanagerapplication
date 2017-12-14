package sprint.one;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.model.Company;
import project.model.Profile;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.ProjectRepository;
import project.model.User;
import project.model.UserRepository;

/**
 * 
 * @author group 3
 * 
 *         Test US 320
 * 
 *         US320 - As a Director, I want to see a list of active projects.
 *
 */
public class US320Tests {

	Company myCompany;
	UserRepository userRepository;
	User user1;
	User user2;
	Project project1;
	Project project2;
	Project project3;
	Project project4;
	Project project5;
	ProjectRepository projectRepository;
	ProjectCollaborator projectCollaborator;
	List<Project> testUs320;

	@Before
	public void setUp() {
		// Company creation
		myCompany = Company.getTheInstance();

		// creates an UserRepository
		userRepository = myCompany.getUsersRepository();

		// creates a ProjectRepository
		projectRepository = myCompany.getProjectsRepository();
		userRepository.getAllUsersFromRepository().clear();

		// User creation
		user1 = userRepository.createUser("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000", "Rua",
				"2401-00", "Test", "Testo", "Testistan");
		user2 = userRepository.createUser("Rita", "rita@gmail.com", "002", "Gestora de Projeto", "920000000", "Rua Bla",
				"BlaBla", "BlaBlaBla", "BlaBlaBlaBla", "Blalandia");

		// add user to user list
		userRepository.addUserToUserRepository(user1);
		userRepository.addUserToUserRepository(user2);

		// set user as collaborator
		user1.setUserProfile(Profile.DIRECTOR);
		user2.setUserProfile(Profile.COLLABORATOR);

		// create project and set a user to Project manager
		project1 = projectRepository.createProject("name3", "description4", user2);
		project2 = projectRepository.createProject("name3", "description4", user2);
		project3 = projectRepository.createProject("name3", "description4", user2);
		project4 = projectRepository.createProject("name3", "description4", user2);
		project5 = projectRepository.createProject("name3", "description4", user2);

		// add project to project repository
		projectRepository.addProjectToProjectRepository(project1);
		projectRepository.addProjectToProjectRepository(project2);
		projectRepository.addProjectToProjectRepository(project3);
		projectRepository.addProjectToProjectRepository(project4);
		projectRepository.addProjectToProjectRepository(project5);

		// create project collaborator
		projectCollaborator = project1.createProjectCollaborator(user1, 2);
		projectCollaborator = project2.createProjectCollaborator(user1, 2);
		projectCollaborator = project3.createProjectCollaborator(user1, 2);
		projectCollaborator = project4.createProjectCollaborator(user1, 2);
		projectCollaborator = project5.createProjectCollaborator(user1, 2);

		// add user to project team
		project1.addUserToProjectTeam(projectCollaborator);
		project2.addUserToProjectTeam(projectCollaborator);
		project3.addUserToProjectTeam(projectCollaborator);
		project4.addUserToProjectTeam(projectCollaborator);
		project5.addUserToProjectTeam(projectCollaborator);

		// Creates a new list and adds projects to that list, to compare with
		// projectList
		testUs320 = new ArrayList<Project>();

	}

	@After
	public void tearDown() {
		myCompany.clear();
		user1 = null;
		user2 = null;
		project1 = null;
		project2 = null;
		project3 = null;
		project4 = null;
		project5 = null;
		testUs320 = null;

	}

	@Test

	public void testUS320() {

		// set projects to active state
		project1.setProjectStatus(1);
		project2.setProjectStatus(1);
		project3.setProjectStatus(1);
		project4.setProjectStatus(1);
		project5.setProjectStatus(0);

		// add projects to projects list to create expected result
		testUs320.add(project1);
		testUs320.add(project2);
		testUs320.add(project3);
		testUs320.add(project4);

		assertEquals(testUs320, projectRepository.getActiveProjects());
	}
}