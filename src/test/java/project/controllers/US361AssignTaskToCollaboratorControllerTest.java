package project.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import project.model.*;
import project.services.ProjectService;
import project.services.TaskService;
import project.services.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan({"project.model", "project.services", "project.repositories", "project.controllers"})
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
		userAdmin = userContainer.createUser("Jo�o", "joao@gmail.com", "001", "Admin", "920000000", "Rua", "2401-00",
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
		testTask2 = taskContainer.createTask("Fix controllers", project);
		
		
		taskContainer.saveTask(testTask);
		taskContainer.saveTask(testTask2);
		
		//controllers.setTask(testTask);
		//controllers.setTask(testTask2);
		
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
	
	@Test
	public void testGettersAndSetters() {
		controller.setProjectCollaborator(projCollaborator);
		assertEquals(projCollaborator, controller.getProjectCollaborator());
		
		controller.setTask(testTask2);
		assertEquals(testTask2, controller.getTask());
		
		controller.setProject(project);
		assertEquals(project, controller.getProject());
		
	}
}
