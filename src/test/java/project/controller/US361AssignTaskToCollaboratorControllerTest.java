package project.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import project.Services.ProjectService;
import project.Services.TaskService;
import project.Services.UserService;
import project.model.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan({"project.model", "project.services", "project.repositories", "project.controller"})
public class US361AssignTaskToCollaboratorControllerTest {

	@Autowired
	US361AssignTaskToCollaboratorsController controller;

	@Autowired
	private UserService userContainer;
	
	@Autowired
	private ProjectService projectContainer;
	
	@Autowired
	private TaskService taskContainer;
	
	User user1;
	User userAdmin;
	Project project;
	Task testTask, testTask2, testTask3, testTask4, testTask5, testTask6, testTask7;
	ProjectCollaborator projCollaborator;
	ProjectCollaborator nullProjectCollaborator;
	
	@Before
	public void setUp() {
	
		// create user
		user1 = userContainer.createUser("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000", "Rua",
				"2401-00", "Test", "Testo", "Testistan");
		
		// create user administrator
		userAdmin = userContainer.createUser("João", "joao@gmail.com", "001", "Admin", "920000000", "Rua", "2401-00",
				"Test", "Testo", "Testistan");
		
		// set user as collaborator
		user1.setUserProfile(Profile.COLLABORATOR);
		userAdmin.setUserProfile(Profile.COLLABORATOR);
		
		// add user to user list
		userContainer.addUserToUserRepositoryX(user1);
		userContainer.addUserToUserRepositoryX(userAdmin);
		
		// create project, added it to the ProjectContainer and add projectCollaborator
		project = projectContainer.createProject("name3", "description4", userAdmin);// !!!
		
		projCollaborator = projectContainer.createProjectCollaborator(user1, project, 100);
	
		// creates the task
		testTask = taskContainer.createTask("Fix tests", project);
		testTask2 = taskContainer.createTask("Fix controller", project);
		
		
		taskContainer.saveTask(testTask);
		taskContainer.saveTask(testTask2);
		
		//controller.setTask(testTask);
		//controller.setTask(testTask2);
		
	}

	/**
	 * Tests the assignCollaboratorToTask
	 */
	@Test
	public void assignTaskToCollaboratorControllerTest() {
		
		controller.setProject(project);
		controller.setTask(testTask);
			
		controller.setUserToAddToTask(0);
		assertTrue(controller.assignCollaboratorToTask());

		controller.setUserToAddToTask(0);
		assertFalse(controller.assignCollaboratorToTask());
	}

	/**
	 * Tests the setProjectCollaborator and getProjectCollaborator
	 */
	@Test
	public void setProjectCollaborator() {
		
		controller.setProject(project);
		
		// asserts that the projectCollaborator is null
		assertEquals(controller.getUserToAddToTask(), null);

		// sets the projectCollaborator and then checks if is equal to the
		// projCollaborator
		controller.setUserToAddToTask(0);
		assertEquals(controller.getUserToAddToTask(), projCollaborator);
	}

	/**
	 * Tests the getProjectActiveTeam
	 */
	@Test
	public void getProjectActiveTeam() {
		
		controller.setProject(project);

		String info = "Name: " + user1.getName() + "\n" + "Email: " + user1.getEmail() + "\n" + "Function: "
				+ user1.getFunction();

		List<String> expResult = new ArrayList<>();
		expResult.add(info);

		assertEquals(expResult, controller.getProjectActiveTeam());
	}
}
