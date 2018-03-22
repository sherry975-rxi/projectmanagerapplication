package project.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import project.Repository.ProjCollabRepository;
import project.Repository.ProjectsRepository;
import project.Repository.TaskRepository;
import project.Repository.UserRepository;
import project.Services.ProjectService;
import project.Services.TaskService;
import project.Services.UserService;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.Task;
import project.model.User;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class US360ViewCollaboratorsWithoutTasksTests {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ProjectsRepository projectRepository;
	
	@Autowired
	TaskRepository taskRepository;
	
	@Autowired
	ProjCollabRepository projectCollaboratorRepository;
	
	US360ViewCollaboratorsWithoutTasksController us360controller;
	UserService userContainer;
	ProjectService projectContainer;
	TaskService taskContainer;
	User userManager;
	User activeCollaborator;
	User idleCollaborator;
	Project testStuff;
	ProjectCollaborator activeProjCollab;
	ProjectCollaborator idleProjCollab;
	Task taskWithTeam;
	Task taskWithNoTeam;


	String idleProjCollabInfo;

	@Before
	public void setUp() {
		
		// creates an UserContainer
		userContainer = new UserService();
		userContainer.setUserRepository(userRepository);
								
		// creates a Project Container
		projectContainer = new ProjectService();
		projectContainer.setProjectsRepository(projectRepository);
		projectContainer.setProjectCollaboratorRepository(projectCollaboratorRepository);
		
		// creates a Task Container
		taskContainer = new TaskService();
		taskContainer.setTaskRepository(taskRepository);
		
//		us360controller = new US360ViewCollaboratorsWithoutTasksController(testStuff);
		// creates the controller
		us360controller = new US360ViewCollaboratorsWithoutTasksController();
		us360controller.taskService = taskContainer;
		us360controller.projectService = projectContainer;

		// creates two users, one manager, and two collaborators
		userManager = new User("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000");
		activeCollaborator = new User("João", "joao@gmail.com", "002", "Admin", "930000000");
		idleCollaborator = new User("Lazy Boi", "nope@gmail.com", "003", "Slacker", "920000000");
		
		// saves users in database
		userContainer.addUserToUserRepositoryX(userManager);
		userContainer.addUserToUserRepositoryX(activeCollaborator);
		userContainer.addUserToUserRepositoryX(idleCollaborator);

		// creates a project 
//		testStuff = new Project(1, "Testing controllers", "for great testing", userManager);
		testStuff = projectContainer.createProject("1", "Testing controllers", userManager);
		// save project "testStuff" in database
		projectContainer.addProjectToProjectContainer(testStuff);
		// Add both active and idle Collaborators to the project team
		activeProjCollab = testStuff.createProjectCollaborator(activeCollaborator, 400);
		idleProjCollab = testStuff.createProjectCollaborator(idleCollaborator, 420);
//		testStuff.addProjectCollaboratorToProjectTeam(activeProjCollab);
//		testStuff.addProjectCollaboratorToProjectTeam(idleProjCollab);
		
		//save the project team in database
		projectContainer.addProjectCollaborator(activeProjCollab);
		projectContainer.addProjectCollaborator(idleProjCollab);

		// creates two tasks and adds the to the project's task repository
		taskWithTeam = taskContainer.createTask("This one has a team", testStuff);
		taskWithNoTeam = taskContainer.createTask("This one does not", testStuff);
//		testStuff.getTaskService().addTaskToProject(taskWithTeam);
//		testStuff.getTaskService().addTaskToProject(taskWithNoTeam);

		// adds the active team member to a task, and creates an expected String of data
		// belonging to the idle team member
		taskWithTeam.addProjectCollaboratorToTask(activeProjCollab);

		idleProjCollabInfo = "003: Lazy Boi (nope@gmail.com; 920000000) - Slacker [COST/EFFORT: 420]";


	}

//	@After
//	public void tearDown() {
//		userManager = null;
//		activeCollaborator = null;
//		idleCollaborator = null;
//
//		activeProjCollab = null;
//		idleProjCollab = null;
//		taskWithTeam = null;
//		taskWithNoTeam = null;
//
//		us360controller = null;
//
//	}

	/**
	 * 
	 * This test asserts the Utility method that converts a Project Collaborator
	 * information into a string for display is working correctly. Given the
	 * expected String generated during setup, it then generates the same output
	 * using the controller's own method
	 * 
	 * Then, compares both expected and actual String data and asserts true
	 * 
	 */
	@Test
	public void testCollaboratorToString() {

		String testResult = us360controller.collaboratorDataAsString(idleProjCollab);

		assertTrue(testResult.equals(idleProjCollabInfo));
	}

	/**
	 * 
	 * This test asserts the showCollaboratorsWithoutTasks() method works correctly.
	 * Given only one idle team member, assert the resulting list contains ONE
	 * entry, and that it matches "idleProjCollaborator"'s personal inforamtion.
	 * 
	 * Then, confirms that list becomes empty only when "idleProjCollaborator" is
	 * assigned a task
	 * 
	 */
	@Test
	public void testListIdleProjectCollaborators() {

		// calls the showCollaboratorsWithoutTasks() method from the controller
		// then saves the output in a testing list
		List<String> testingList = us360controller.showCollaboratorsWithoutTasks(testStuff);

		// given that activeProjectCollaborator has a Task assigned
		// and idleProjectCollaborator has no tasks assigned
		// asserts both those conditions are correct
		assertFalse(taskContainer.isCollaboratorActiveOnAnyTask(idleProjCollab));
		assertTrue(taskContainer.isCollaboratorActiveOnAnyTask(activeProjCollab));

		// then, asserts the list of idle team members contains only one entry
		// and that the Idle Team member's information is inside the list
		assertEquals(testingList.size(), 1);
		assertTrue(testingList.contains("[1] \n" + idleProjCollabInfo));

		// finally, asserts the list produced by the controller becomes empty only when
		// the Idle Team member is added to a task

		taskWithTeam.addProjectCollaboratorToTask(idleProjCollab);
		assertTrue(us360controller.showCollaboratorsWithoutTasks(testStuff).isEmpty());

		taskWithTeam.removeAllCollaboratorsFromTaskTeam();
		assertFalse(us360controller.showCollaboratorsWithoutTasks(testStuff).isEmpty());
	}

}
