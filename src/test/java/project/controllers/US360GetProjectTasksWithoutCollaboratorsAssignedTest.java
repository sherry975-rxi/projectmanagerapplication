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

import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan({"project.model", "project.services", "project.repositories", "project.controllers"})
public class US360GetProjectTasksWithoutCollaboratorsAssignedTest {
	
	@Autowired
	US360GetProjectTasksWithoutCollaboratorsAssignedController tasksFiltersController;
	
	@Autowired
	UserService userContainer;
	
	@Autowired
	ProjectService projectContainer;
	
	@Autowired
	TaskService taskContainer;
	
	User user1, user2, user3;
	Project project1;
	ProjectCollaborator projCollab1, projCollab2, projCollab3;
	Task task1, task2, task3, task4, task5, task6;
	TaskCollaborator taskCollab1, taskCollab2, taskCollab3, taskCollab4, taskCollab5, taskCollab6;

	@Before
	public void setUp() {
		// create users in company
		user2 = userContainer.createUser("João", "user2@gmail.com", "001", "Manager", "930025000",
				"rua doutor antónio", "7689-654", "porto", "porto", "portugal");
		user1 = userContainer.createUser("Juni", "user3@gmail.com", "002", "Code Monkey", "930000000",
				"rua engenheiro joão", "789-654", "porto", "porto", "portugal");

		// change profiles of users from VISITOR (default) to COLLABORATOR
		user2.setUserProfile(Profile.COLLABORATOR);
		user1.setUserProfile(Profile.COLLABORATOR);

		// create project 1 in company 1
		project1 = projectContainer.createProject("name3", "description4", user2);

		// add project 1 to company 1
		projectContainer.addProjectToProjectContainer(project1);

		// create an estimated Task Start Date
		Calendar estimatedTaskStartDateTest = Calendar.getInstance();
		estimatedTaskStartDateTest.set(Calendar.YEAR, 2017);
		estimatedTaskStartDateTest.set(Calendar.MONTH, Calendar.SEPTEMBER);
		estimatedTaskStartDateTest.set(Calendar.DAY_OF_MONTH, 25);
		estimatedTaskStartDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// create an estimated Task Dead line Date
		Calendar taskDeadlineDateTest1 = Calendar.getInstance();
		taskDeadlineDateTest1.set(Calendar.YEAR, 2019);
		taskDeadlineDateTest1.set(Calendar.MONTH, Calendar.JANUARY);
		Calendar taskDeadlineDateTest2 = Calendar.getInstance();
		taskDeadlineDateTest2.set(Calendar.YEAR, 2019);
		taskDeadlineDateTest2.set(Calendar.MONTH, Calendar.FEBRUARY);
		Calendar taskDeadlineDateTest3 = Calendar.getInstance();
		taskDeadlineDateTest3.set(Calendar.YEAR, 2019);
		taskDeadlineDateTest3.set(Calendar.MONTH, Calendar.MARCH);
		Calendar taskDeadlineDateTest4 = Calendar.getInstance();
		taskDeadlineDateTest4.set(Calendar.YEAR, 2019);
		taskDeadlineDateTest4.set(Calendar.MONTH, Calendar.APRIL);

		// create a Date before to the previous Dead line created in order to result in
		// an expired Task
		Calendar taskExpiredDeadlineDateTest = Calendar.getInstance();
		taskExpiredDeadlineDateTest.set(Calendar.YEAR, 2017);
		taskExpiredDeadlineDateTest.set(Calendar.MONTH, Calendar.SEPTEMBER);
		taskExpiredDeadlineDateTest.set(Calendar.DAY_OF_MONTH, 29);
		taskExpiredDeadlineDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// create tasks in project 1

		//task1 = project1.createTask("Do this", 10, estimatedTaskStartDateTest, taskDeadlineDateTest1, 10);
		task1 = taskContainer.createTask("Do this", project1);
		task1.setEstimatedTaskEffort(10);
		task1.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task1.setTaskDeadline(taskDeadlineDateTest1);
		task1.setTaskBudget(10);
		
		//task2 = project1.createTask("Do that", 10, estimatedTaskStartDateTest, taskDeadlineDateTest2, 10);
		task2 = taskContainer.createTask("Do that", project1);
		task2.setEstimatedTaskEffort(10);
		task2.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task2.setTaskDeadline(taskDeadlineDateTest2);
		task2.setTaskBudget(10);
		
		
		//task3 = project1.createTask("Merge everything", 10, estimatedTaskStartDateTest, taskExpiredDeadlineDateTest, 10);
		task3 = taskContainer.createTask("Merge everything", project1);
		task3.setEstimatedTaskEffort(10);
		task3.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task3.setTaskDeadline(taskExpiredDeadlineDateTest);
		task3.setTaskBudget(10);
		
		//task4 = project1.getAllTasksFromTaskRepository().createTask("Do this", 10, estimatedTaskStartDateTest, taskDeadlineDateTest3, 10);
		task4 = taskContainer.createTask("Do this", project1);
		task4.setEstimatedTaskEffort(10);
		task4.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task4.setTaskDeadline(taskDeadlineDateTest3);
		task4.setTaskBudget(10);
		
		//task5 = project1.getAllTasksFromTaskRepository().createTask("Do this", 10, estimatedTaskStartDateTest, taskDeadlineDateTest4, 10);
		task5 = taskContainer.createTask("Do this", project1);
		task5.setEstimatedTaskEffort(10);
		task5.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task5.setTaskDeadline(taskDeadlineDateTest4);
		task5.setTaskBudget(10);
		
		//task6 = project1.getAllTasksFromTaskRepository().createTask("Do this", 10, estimatedTaskStartDateTest, taskExpiredDeadlineDateTest, 10);
		task6 = taskContainer.createTask("Do this", project1);
		task6.setEstimatedTaskEffort(10);
		task6.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task6.setTaskDeadline(taskExpiredDeadlineDateTest);
		task6.setTaskBudget(10);
		

//		// add tasks to task repository of project 1
//		project1.getTaskService().addTaskToProject(task1);
//		project1.getTaskService().addTaskToProject(task2);
//		project1.getTaskService().addTaskToProject(task3);
//		project1.getTaskService().addTaskToProject(task4);
//		project1.getTaskService().addTaskToProject(task5);
//		project1.getTaskService().addTaskToProject(task6);


		// add costPerEffort to users in project 1, resulting in a Project Collaborator
		// for each one
		projCollab1 = project1.createProjectCollaborator(user1, 250);
		projCollab2 = project1.createProjectCollaborator(user2, 120);
		projCollab3 = project1.createProjectCollaborator(user2, 200);

//		// associate Project Collaborators to project 1 (info user + costPerEffort)
//		project1.addProjectCollaboratorToProjectTeam(projCollab1);
//		project1.addProjectCollaboratorToProjectTeam(projCollab2);

		
	}

//	@After
//	public void tearDown() {
//		userContainer = null;
//		projectContainer = null;
//		user1 = null;
//		user2 = null;
//		user3 = null;
//		project1 = null;
//		projCollab1 = null;
//		projCollab2 = null;
//		projCollab3 = null;
//		task1 = null;
//		task2 = null;
//		task3 = null;
//		task4 = null;
//		task5 = null;
//		task6 = null;
//		taskCollab1 = null;
//		taskCollab2 = null;
//		taskCollab3 = null;
//		taskCollab4 = null;
//		taskCollab5 = null;
//		taskCollab6 = null;
//		tasksFiltersController = null;
//	}

	@Test
	public final void testGetProjectTasksWithoutCollaboratorsAssigned() {
		task2.addProjectCollaboratorToTask(projCollab1);
		task4.addProjectCollaboratorToTask(projCollab2);

		assertEquals(4, tasksFiltersController.getProjectNotAssignedTaskList(project1).size());
	}

	@Test
	public final void testSplitStringByFirstSpace() {
		String input = "Test me master!";
		assertTrue("Test".equals(tasksFiltersController.splitStringByFirstSpace(input)));
	}

	@Test
	public final void testGetTasksWithoutCollaboratorsAssigned() {
		assertEquals(6, tasksFiltersController.getProjectNotAssigned(project1).size());
	}

}
