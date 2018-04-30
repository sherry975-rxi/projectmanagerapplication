package project.controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import project.model.Project;
import project.model.Task;
import project.model.User;
import project.services.ProjectService;
import project.services.TaskService;
import project.services.UserService;

import java.util.Calendar;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan({ "project.services", "project.model", "project.controllers" })
public class US340CreateTaskControllerTest {

	@Mock
	UserService userService;
	@Mock
	TaskService taskService;
	User user1;
	User userAdmin;
	Project project;
	@Mock
	ProjectService projectService;
	@Mock
	Task testTask;
	@InjectMocks
	US340CreateTaskController victim;

	Calendar startDate;

	@Before
	public void setUp() {

		Calendar startDate = Calendar.getInstance();

		// create user
		/*user1 = userService.createUser("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000", "Rua",
				"2401-00", "Test", "Testo", "Testistan");
		// create user admin
		userAdmin = userService.createUser("João", "joao@gmail.com", "001", "Admin", "920000000", "Rua", "2401-00",
				"Test", "Testo", "Testistan");
*/
		/*// set user as collaborator
		user1.setUserProfile(Profile.COLLABORATOR);
		userAdmin.setUserProfile(Profile.COLLABORATOR);
		// create project
		project = projectService.createProject("name3", "description4", userAdmin);// !!!*/
	}

	@After
	public void tearDown() {
		/*user1 = null;
		userAdmin = null;
		project = null;
		testTask = null;*/

	}

	@Test
	public void shouldAddTheTask() {
		//GIVEN a request to create a task
		//WHEN the information insert is correct
		when(taskService.createTask(anyString(), any(Project.class))).thenReturn(testTask);

		//THEN task is created and the method returns true
		assertTrue(victim.addTask("description", project));
	}

	@Test
	public void shouldntdAddTheTask() {
		//GIVEN a request to create a task
		//WHEN the information insert is incorrect
		when(taskService.createTask(anyString(), any(Project.class))).thenReturn(null);
		//THEN task isn´t created and the method returns false
		assertFalse(victim.addTask("description", project));
	}

	@Test
	public void shouldSetEstimatedStartDate() {
		//GIVEN a request to set a task
		//WHEN the information insert is incorrect
		victim.setEstimatedStartDate(testTask, startDate);
		//THEN method setEstimatedTaskStartDate is called one time
		verify(testTask, times(1)).setEstimatedTaskStartDate(any(Calendar.class));
	}

	@Test
	public void shouldSetDeadline() {
		//GIVEN a request to set a task
		//WHEN the information insert is incorrect
		victim.setDeadline(testTask, startDate);
		//THEN method setEstimatedTaskStartDate is called one time
		verify(testTask, times(1)).setTaskDeadline(any(Calendar.class));
	}
}
