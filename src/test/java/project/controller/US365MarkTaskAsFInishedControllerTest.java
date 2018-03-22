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
import project.model.taskstateinterface.Finished;
import project.model.taskstateinterface.OnGoing;

import java.util.Calendar;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan({ "project.services", "project.model", "project.controller" })
public class US365MarkTaskAsFInishedControllerTest {

	@Autowired
	US365MarkTaskAsFinishedControllerProjectManager us365controller;

	@Autowired
	UserService userService;

	@Autowired
	ProjectService projectService;

	@Autowired
	TaskService taskService;

	/**
	 * This class tests the methods that are called in Controller to execute the
	 * action of Canceling an OnGoing Task
	 * 
	 * @author Group3
	 *
	 */

	// TasksFiltersController tasksFiltersController;

	User user1, user2, projectManager, projectManager2;
	Project project1, project2, project3;
	ProjectCollaborator projCollab1, projCollab2;
	Task task1OnGoing, task2OnGoing, task3, task4, task5, task6;
	TaskCollaborator taskCollab1, taskCollab2;
	Calendar startDateTest;
	Calendar estimatedTaskStartDateTest;
	Calendar taskDeadlineDateTest;
	Calendar taskExpiredDeadlineDateTest;

	@Before
	public void setUp() {

		// create users
		user1 = userService.createUser("Joe Smith", "jsmith@gmail.com", "001", "Junior Programmer", "930000000",
				"Rua da Caparica, 19", "7894-654", "Porto", "Porto", "Portugal");
		user2 = userService.createUser("John Smith", "johnsmith@gmail.com", "001", "General Manager", "930025000",
				"Rua Doutor Armando", "4455-654", "Rio Tinto", "Gondomar", "Portugal");
		projectManager = userService.createUser("Mary MacJohn", "mmacjohn@gmail.com", "003", "Product Manager",
				"930025000", "Rua Terceira, 44", "4455-122", "Leça da Palmeira", "Matosinhos", "Portugal");
		projectManager2 = userService.createUser("John MacMary", "jmacmary2@gmail.com", "003", "Product Manager2",
				"930025356", "Rua Segunda, 45", "4455-122", "Leça da Palmeira", "Matosinhos", "Portugal");

		// set user as collaborator
		user1.setUserProfile(Profile.COLLABORATOR);
		user2.setUserProfile(Profile.COLLABORATOR);
		projectManager.setUserProfile(Profile.COLLABORATOR);
		projectManager2.setUserProfile(Profile.COLLABORATOR);

		// add users to company
		userService.addUserToUserRepositoryX(user1);
		userService.addUserToUserRepositoryX(user2);
		userService.addUserToUserRepositoryX(projectManager);
		userService.addUserToUserRepositoryX(projectManager2);

		// create project and establishes collaborator projectManager as project manager
		// of project 1
		project1 = projectService.createProject("Project Management software", "This software main goals are ....",
				projectManager);
		project2 = projectService.createProject("Project Management software", "This software main goals are ....",
				projectManager2);
		project3 = projectService.createProject("Project Management software", "This software main goals are ....",
				projectManager);

		// add project to company
		projectService.addProjectToProjectContainer(project1);
		projectService.addProjectToProjectContainer(project2);
		projectService.addProjectToProjectContainer(project3);

		// add collaborators to Project
		projectService.createProjectCollaborator(user1, project1, 2);
		projectService.createProjectCollaborator(user2, project1, 2);
		projectService.createProjectCollaborator(user1, project2, 2);
		projectService.createProjectCollaborator(user2, project2, 2);
		projectService.createProjectCollaborator(user1, project3, 2);
		projectService.createProjectCollaborator(user2, project3, 2);

		// create tasks
		task1OnGoing = taskService.createTask("Create class User", project1);
		task2OnGoing = taskService.createTask("Create class User", project1);
		task3 = taskService.createTask("create test for method set name in class user", project2);
		task4 = taskService.createTask("Create class User", project2);
		task5 = taskService.createTask("Create class User", project3);
		task6 = taskService.createTask("create test for method set name in class user", project3);

		// create a estimated Task Start Date
		startDateTest = Calendar.getInstance();

		// create a estimated Task Start Date
		estimatedTaskStartDateTest = Calendar.getInstance();
		estimatedTaskStartDateTest.set(Calendar.YEAR, 2017);
		estimatedTaskStartDateTest.set(Calendar.MONTH, Calendar.SEPTEMBER);
		estimatedTaskStartDateTest.set(Calendar.DAY_OF_MONTH, 25);
		estimatedTaskStartDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// create a estimated Task Dead line Date
		taskDeadlineDateTest = Calendar.getInstance();
		taskDeadlineDateTest.set(Calendar.YEAR, 2018);
		taskDeadlineDateTest.set(Calendar.MONTH, Calendar.JANUARY);
		taskDeadlineDateTest.set(Calendar.DAY_OF_MONTH, 29);
		taskDeadlineDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// create a expired estimated Task Dead line Date
		taskExpiredDeadlineDateTest = Calendar.getInstance();
		taskExpiredDeadlineDateTest.set(Calendar.YEAR, 2017);
		taskExpiredDeadlineDateTest.set(Calendar.MONTH, Calendar.SEPTEMBER);
		taskExpiredDeadlineDateTest.set(Calendar.DAY_OF_MONTH, 29);
		taskExpiredDeadlineDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// set estimated task start date and task dead line to tasks
		task1OnGoing.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task1OnGoing.setStartDate(estimatedTaskStartDateTest);
		task1OnGoing.setTaskDeadline(taskDeadlineDateTest);
		task1OnGoing.setTaskState(new OnGoing());
		task1OnGoing.setCurrentState(StateEnum.ONGOING);

		task2OnGoing.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task2OnGoing.setStartDate(estimatedTaskStartDateTest);
		task2OnGoing.setTaskDeadline(taskDeadlineDateTest);

		task3.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task3.setStartDate(estimatedTaskStartDateTest);
		task3.setTaskDeadline(taskDeadlineDateTest);
		task3.setTaskState(new OnGoing());
		task3.setCurrentState(StateEnum.ONGOING);

		task4.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task4.setStartDate(estimatedTaskStartDateTest);
		task4.setTaskDeadline(taskDeadlineDateTest);

		task5.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task5.setStartDate(estimatedTaskStartDateTest);
		task5.setTaskDeadline(taskDeadlineDateTest);

		task6.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task6.setStartDate(estimatedTaskStartDateTest);
		task6.setTaskDeadline(taskDeadlineDateTest);

		// create task workers
		taskCollab1 = task3.getTaskCollaboratorByEmail("smith@gmail.com");
		taskCollab2 = task5.getTaskCollaboratorByEmail("johnsmith@gmail.com");

		task1OnGoing.addProjectCollaboratorToTask(projCollab1);
		task2OnGoing.addProjectCollaboratorToTask(projCollab2);
		task3.addProjectCollaboratorToTask(projCollab1);
		task4.addProjectCollaboratorToTask(projCollab1);
		task5.addProjectCollaboratorToTask(projCollab2);
		task6.addProjectCollaboratorToTask(projCollab1);

		// save Tasks
		taskService.saveTask(task1OnGoing);
		taskService.saveTask(task2OnGoing);
		taskService.saveTask(task3);
		taskService.saveTask(task4);
		taskService.saveTask(task5);
		taskService.saveTask(task6);
	}

	/**
	 * 
	 * this test asserts that a task starting as Ongoing can be marked as finish and
	 * given a finish date
	 * 
	 */
	@Test
	public void testSetTaskAsFinished() {

		assertFalse(task1OnGoing.isTaskFinished());
		assertTrue(task1OnGoing.getFinishDate() == null);
		assertEquals("OnGoing", task1OnGoing.viewTaskStateName());

		// create controller for ongoing task 1 (of project 1), and asserts Task1Ongoing
		// has been properly marked as finished
		us365controller.setSelectedProject(project1);
		us365controller.setTaskToBeMarked(task1OnGoing);
		task1OnGoing.setTaskState(new Finished());
		task1OnGoing.setCurrentState(StateEnum.FINISHED);
		taskService.saveTask(task1OnGoing);
		assertTrue(us365controller.setTaskAsFinished());

		assertTrue(task1OnGoing.isTaskFinished());
		assertTrue(task1OnGoing.getFinishDate() != null);
		assertEquals("Finished", task1OnGoing.viewTaskStateName());

		// create controller for ready task 3 (of project 2), then asserts task3 cannot
		// be marked as finished, since it's not ongoing
		us365controller.setSelectedProject(project2);
		us365controller.setTaskToBeMarked(task3);
		taskService.saveTask(task3);
		us365controller.setTaskAsFinished();

		assertFalse(task3.isTaskFinished());

	}

}
