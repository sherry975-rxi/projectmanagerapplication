package project.controller;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

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
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.Task;
import project.model.TaskCollaborator;
import project.model.User;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan({ "project.services", "project.model", "project.controller" })
public class US211GetFinishedUserTasksFromLastMonthInDecreasingOrderTest {

	User user1, user2, user3;
	Project project1;
	ProjectCollaborator projCollab1, projCollab2, projCollab3;
	Task task1, task2, task3, task4, task5, task6;
	TaskCollaborator taskCollab1, taskCollab2, taskCollab3, taskCollab4, taskCollab5, taskCollab6;

	@Autowired
	US211GetFinishedUserTasksFromLastMonthInDecreasingOrderController tasksFiltersController;
	@Autowired
	UserService userService;
	@Autowired
	ProjectService projectService;
	@Autowired
	TaskService taskService;

	@Before
	public void setUp() {
		// create users in company
		user2 = userService.createUser("João", "user2@gmail.com", "001", "Manager", "930025000", "rua doutor antónio",
				"7689-654", "porto", "porto", "portugal");
		user1 = userService.createUser("Juni", "user3@gmail.com", "002", "Code Monkey", "930000000",
				"rua engenheiro joão", "789-654", "porto", "porto", "portugal");

		// create project 1 in company 1
		project1 = projectService.createProject("name3", "description4", user2);

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
		task1 = taskService.createTask("first task", project1);
		task2 = taskService.createTask("second task", project1);
		task3 = taskService.createTask("third task", project1);
		task4 = taskService.createTask("fourth task", project1);
		task5 = taskService.createTask("fifth task", project1);
		task6 = taskService.createTask("sixth task", project1);

		// create projectCollaborator of project1
		projCollab1 = project1.createProjectCollaborator(user1, 250);
		projCollab2 = project1.createProjectCollaborator(user2, 120);
		projCollab3 = project1.createProjectCollaborator(user2, 200);

		// defines finish date to task, and mark it as Finished
		task1.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task1.setTaskDeadline(taskDeadlineDateTest1);
		task1.addProjectCollaboratorToTask(projCollab1);
		task1.setTaskBudget(55);
		task1.setEstimatedTaskEffort(45);
		Calendar startDateTask1 = estimatedTaskStartDateTest;
		startDateTask1.add(Calendar.DAY_OF_MONTH, 60);
		task1.setStartDate(startDateTask1);
		task1.markTaskAsFinished();

		task2.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task2.setTaskDeadline(taskDeadlineDateTest1);
		task2.addProjectCollaboratorToTask(projCollab1);
		Calendar startDateTask2 = estimatedTaskStartDateTest;
		startDateTask2.add(Calendar.DAY_OF_MONTH, 60);
		task2.setStartDate(startDateTask1);
		task2.markTaskAsFinished();

		task3.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task3.setTaskDeadline(taskDeadlineDateTest1);
		task3.addProjectCollaboratorToTask(projCollab1);
		task3.setTaskBudget(55);
		task3.setEstimatedTaskEffort(45);
		Calendar startDateTask3 = estimatedTaskStartDateTest;
		startDateTask3.add(Calendar.DAY_OF_MONTH, 60);
		task3.setStartDate(startDateTask1);
		task3.markTaskAsFinished();
	}

	/**
	 * US211 - Como colaborador, eu pretendo obter uma lista das tarefas que concluí
	 * no último mês, ordenadas por ordem temporal decrescente.
	 */
	@Test
	public void testGetFinishedUserTasksFromLastMonthInDecreasingOrder() {

		// Adds Collaborator 1 to all tasks
		task1.addProjectCollaboratorToTask(projCollab1);
		task2.addProjectCollaboratorToTask(projCollab1);
		task3.addProjectCollaboratorToTask(projCollab1);

		// Tasks completed x days ago
		Calendar finishOverwrite = Calendar.getInstance();
		finishOverwrite.set(Calendar.DAY_OF_MONTH, 15); // five days before
		finishOverwrite.add(Calendar.MONTH, -1);
		task1.setFinishDate(finishOverwrite);
		finishOverwrite.set(Calendar.DAY_OF_MONTH, 20); // fifteen days before
		finishOverwrite.add(Calendar.MONTH, 5);
		task2.setFinishDate(finishOverwrite);
		finishOverwrite.set(Calendar.DAY_OF_MONTH, 10); // ten days before
		finishOverwrite.add(Calendar.MONTH, -5);
		task3.setFinishDate(finishOverwrite);

		String task1String = tasksFiltersController.convertCalendarToString(task1.getFinishDate()) + ": name3 - "
				+ task1.getDescription();
		String task3String = tasksFiltersController.convertCalendarToString(task3.getFinishDate()) + ": name3 - "
				+ task3.getDescription();

		assertEquals(task1String,
				tasksFiltersController.getFinishedUserTasksFromLastMonthInDecreasingOrder(user1).get(0));
		assertEquals(task3String,
				tasksFiltersController.getFinishedUserTasksFromLastMonthInDecreasingOrder(user1).get(1));
		assertEquals(2, tasksFiltersController.getFinishedUserTasksFromLastMonthInDecreasingOrder(user1).size());
	}

}
