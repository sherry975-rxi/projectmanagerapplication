package project.controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import project.model.*;
import project.model.taskstateinterface.Finished;
import project.model.taskstateinterface.OnGoing;
import project.services.ProjectService;
import project.services.TaskService;
import project.services.UserService;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan({ "project.services", "project.model", "project.controllers", "project.repository"})
public class US372GetProjectUnfinishedTaskListTest {


	@Autowired
	UserService userContainer;
	@Autowired
	ProjectService projectContainer;
	@Autowired
	TaskService taskService;

	User user1, user2, user3;
	Project project1;

	ProjectCollaborator projCollab1, projCollab2;

	Task task1, task2, task3, task4, task5, task6;

	@Autowired
	US372GetProjectUnfinishedTaskListController tasksFiltersController;


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

		userContainer.addUserToUserRepositoryX(user2);
		userContainer.addUserToUserRepositoryX(user1);

		// create project 1 in company 1
		project1 = projectContainer.createProject("name3", "description4", user2);


		// create an estimated Task Start Date
		Calendar estimatedTaskStartDateTest = Calendar.getInstance();
		estimatedTaskStartDateTest.add(Calendar.YEAR, -1);
		estimatedTaskStartDateTest.set(Calendar.MONTH, Calendar.SEPTEMBER);
		estimatedTaskStartDateTest.set(Calendar.DAY_OF_MONTH, 25);
		estimatedTaskStartDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// create an estimated Task Dead line Date
		Calendar taskDeadlineDateTest1 = Calendar.getInstance();
		taskDeadlineDateTest1.add(Calendar.YEAR, 1);
		taskDeadlineDateTest1.set(Calendar.MONTH, Calendar.JANUARY);
		Calendar taskDeadlineDateTest2 = Calendar.getInstance();
		taskDeadlineDateTest2.add(Calendar.YEAR, 1);
		taskDeadlineDateTest2.set(Calendar.MONTH, Calendar.FEBRUARY);
		Calendar taskDeadlineDateTest3 = Calendar.getInstance();
		taskDeadlineDateTest3.add(Calendar.YEAR, 1);
		taskDeadlineDateTest3.set(Calendar.MONTH, Calendar.MARCH);
		Calendar taskDeadlineDateTest4 = Calendar.getInstance();
		taskDeadlineDateTest4.add(Calendar.YEAR, 1);
		taskDeadlineDateTest4.set(Calendar.MONTH, Calendar.APRIL);

		// create a Date before to the previous Dead line created in order to result in
		// an expired Task
		Calendar taskExpiredDeadlineDateTest = Calendar.getInstance();
		taskExpiredDeadlineDateTest.add(Calendar.YEAR, -1);
		taskExpiredDeadlineDateTest.set(Calendar.MONTH, Calendar.SEPTEMBER);
		taskExpiredDeadlineDateTest.set(Calendar.DAY_OF_MONTH, 29);
		taskExpiredDeadlineDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// create tasks in project 1
		task1 = taskService.createTask("Do this", project1);
		task2 = taskService.createTask("Do that", project1);
		task3 = taskService.createTask("Merge everything", project1);
		task4 = taskService.createTask("Do this x4", project1);
		task5 = taskService.createTask("Do this x5", project1);
		task6 = taskService.createTask("Do this x6", project1);



		// add costPerEffort to users in project 1, resulting in a Project Collaborator
		// for each one
		projCollab1 = projectContainer.createProjectCollaborator(user1,project1, 250);
		projCollab2 = projectContainer.createProjectCollaborator(user2,project1, 120);

		int taskEffortAndBudget = 10;

		// defines finish date to task, and mark it as Finished7
		task1.setTaskBudget(taskEffortAndBudget);
		task1.setEstimatedTaskEffort(taskEffortAndBudget);
		task1.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task1.setTaskDeadline(taskDeadlineDateTest1);
		task1.addProjectCollaboratorToTask(projCollab1);

		Calendar startDateTask1 = estimatedTaskStartDateTest;
		startDateTask1.add(Calendar.DAY_OF_MONTH, 60);
		task1.setStartDate(startDateTask1);

		task1.setTaskState(new OnGoing());
		task1.markTaskAsFinished();
		task1.setTaskState(new Finished());
		task1.setCurrentState(StateEnum.FINISHED);


		task2.setTaskBudget(taskEffortAndBudget);
		task2.setEstimatedTaskEffort(taskEffortAndBudget);
		task2.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task2.setTaskDeadline(taskDeadlineDateTest1);
		task2.addProjectCollaboratorToTask(projCollab1);

		Calendar startDateTask2 = estimatedTaskStartDateTest;
		startDateTask2.add(Calendar.DAY_OF_MONTH, 60);
		task2.setStartDate(startDateTask2);
		task2.setTaskState(new OnGoing());
		task2.markTaskAsFinished();
		task2.setTaskState(new Finished());
		task2.setCurrentState(StateEnum.FINISHED);

		task3.setTaskBudget(taskEffortAndBudget);
		task3.setEstimatedTaskEffort(taskEffortAndBudget);
		task3.setStartDate(Calendar.getInstance());
		task3.setTaskState(new OnGoing());
		task3.setCurrentState(StateEnum.ONGOING);

		task4.setTaskBudget(taskEffortAndBudget);
		task4.setEstimatedTaskEffort(taskEffortAndBudget);
		task4.setStartDate(Calendar.getInstance());
		task4.setTaskState(new OnGoing());
		task4.setCurrentState(StateEnum.ONGOING);

		task5.setTaskBudget(taskEffortAndBudget);
		task5.setEstimatedTaskEffort(taskEffortAndBudget);
		task5.setStartDate(Calendar.getInstance());
		task5.setTaskState(new OnGoing());
		task5.setCurrentState(StateEnum.ONGOING);

		task6.setTaskBudget(taskEffortAndBudget);
		task6.setEstimatedTaskEffort(taskEffortAndBudget);
		task6.setStartDate(Calendar.getInstance());
		task6.setTaskState(new OnGoing());
		task6.setCurrentState(StateEnum.ONGOING);

	}

	@After
	public void tearDown(){
		user1 = null;
		user2 = null;
		user3 = null;
		project1 = null;
		projCollab1= null;
		projCollab2 = null;
		task1= null;
		task2= null;
		task3= null;
		task4= null;
		task5= null;
		task6= null;
	}


	/**
	 * Given four unfinished tasks in project 1
	 *
	 * when the controllers is called to return the list of unfinished tasks
	 *
	 * then the list must contain 4 entries
	 */

	@Test
	public final void testGetProjectUnfinishedTaskList() {
		assertEquals(4, tasksFiltersController.getProjectUnfinishedTaskList(project1).size());
	}


	/**
	 * Given four unfinished tasks in project 1
	 *
	 * when the controllers is called to return the list of unfinished tasks as strings
	 *
	 * then the list contain the id's of all the unfinished tasks + description
	 */
	@Test
	public final void testGetOnGoingTaskListId() {
		String result = "["+task3.getTaskID()+"] Merge everything";
		assertTrue(result.equals(tasksFiltersController.getUnfinishedTaskListId(project1).get(0)));
		result = "["+task4.getTaskID()+"] Do this x4";
		assertTrue(result.equals(tasksFiltersController.getUnfinishedTaskListId(project1).get(1)));
		result = "["+task5.getTaskID()+"] Do this x5";
		assertTrue(result.equals(tasksFiltersController.getUnfinishedTaskListId(project1).get(2)));
		result = "["+task6.getTaskID()+"] Do this x6";
		assertTrue(result.equals(tasksFiltersController.getUnfinishedTaskListId(project1).get(3)));
	}


	/**
	 *
	 * Given a single string input
	 *
	 * when the splitByFirstSpace Method is called
	 *
	 * then the result must contain only the first word
	 */
	@Test
	public final void testSplitStringByFirstSpace() {
		String input = "Test me master!";
		assertTrue("Test".equals(tasksFiltersController.splitStringByFirstSpace(input)));
	}

}
