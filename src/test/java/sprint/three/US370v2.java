package sprint.three;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.controller.TasksFiltersController;
import project.model.Company;
import project.model.Profile;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.Task;
import project.model.TaskCollaborator;
import project.model.User;

/**
 * US370 v2
 *
 */
public class US370v2 {

	TasksFiltersController tasksFiltersController;
	Company company1;
	User user1, user2, user3;
	Project project1;
	ProjectCollaborator projCollab1, projCollab2, projCollab3;
	Task task1, task2, task3, task4, task5, task6;
	TaskCollaborator taskCollab1, taskCollab2, taskCollab3, taskCollab4, taskCollab5, taskCollab6;

	@Before
	public void setUp() {
		// create company 1
		company1 = Company.getTheInstance();

		// create users in company
		user2 = company1.getUsersRepository().createUser("João", "user2@gmail.com", "001", "Manager", "930025000",
				"rua doutor antónio", "7689-654", "porto", "porto", "portugal");
		company1.getUsersRepository().addUserToUserRepository(user2);
		user1 = company1.getUsersRepository().createUser("Juni", "user3@gmail.com", "002", "Code Monkey", "930000000",
				"rua engenheiro joão", "789-654", "porto", "porto", "portugal");
		company1.getUsersRepository().addUserToUserRepository(user1);

		// change profiles of users from VISITOR (default) to COLLABORATOR
		user2.setUserProfile(Profile.COLLABORATOR);
		user1.setUserProfile(Profile.COLLABORATOR);

		// create project 1 in company 1
		project1 = company1.getProjectsRepository().createProject("name3", "description4", user2);

		// add project 1 to company 1
		company1.getProjectsRepository().addProjectToProjectRepository(project1);

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
		task1 = project1.getTaskRepository().createTask("Do this", 10, estimatedTaskStartDateTest,
				taskDeadlineDateTest1, 10);
		task2 = project1.getTaskRepository().createTask("Do that", 10, estimatedTaskStartDateTest,
				taskDeadlineDateTest2, 10);
		task3 = project1.getTaskRepository().createTask("Merge everything", 10, estimatedTaskStartDateTest,
				taskExpiredDeadlineDateTest, 10);
		task4 = project1.getTaskRepository().createTask("Do this", 10, estimatedTaskStartDateTest,
				taskDeadlineDateTest3, 10);
		task5 = project1.getTaskRepository().createTask("Do this", 10, estimatedTaskStartDateTest,
				taskDeadlineDateTest4, 10);
		task6 = project1.getTaskRepository().createTask("Do this", 10, estimatedTaskStartDateTest,
				taskExpiredDeadlineDateTest, 10);

		// add tasks to task repository of project 1
		project1.getTaskRepository().addProjectTask(task1);
		project1.getTaskRepository().addProjectTask(task2);
		project1.getTaskRepository().addProjectTask(task3);
		project1.getTaskRepository().addProjectTask(task4);
		project1.getTaskRepository().addProjectTask(task5);
		project1.getTaskRepository().addProjectTask(task6);

		// add costPerEffort to users in project 1, resulting in a Project Collaborator
		// for each one
		projCollab1 = project1.createProjectCollaborator(user1, 250);
		projCollab2 = project1.createProjectCollaborator(user2, 120);
		projCollab3 = project1.createProjectCollaborator(user2, 200);

		// associate Project Collaborators to project 1 (info user + costPerEffort)
		project1.addProjectCollaboratorToProjectTeam(projCollab1);
		project1.addProjectCollaboratorToProjectTeam(projCollab2);

		// create Task Collaborator to register the period that the user was in the task
		// while he was active in project 1
		taskCollab1 = task1.createTaskCollaborator(projCollab1);
		taskCollab2 = task2.createTaskCollaborator(projCollab2);
		taskCollab3 = task3.createTaskCollaborator(projCollab2);
		taskCollab4 = task4.createTaskCollaborator(projCollab2);
		taskCollab5 = task5.createTaskCollaborator(projCollab2);
		taskCollab6 = task6.createTaskCollaborator(projCollab2);

		// associate Task Collaborators to task (info project collaborator + period he
		// was in the task)
		task1.addTaskCollaboratorToTask(taskCollab1);
		task2.addTaskCollaboratorToTask(taskCollab2);
		task3.addTaskCollaboratorToTask(taskCollab3);
		task4.addTaskCollaboratorToTask(taskCollab4);
		task5.addTaskCollaboratorToTask(taskCollab5);
		task6.addTaskCollaboratorToTask(taskCollab6);

		// defines finish date to task, and mark it as Finished
		task1.setFinishDate();
		task1.markTaskAsFinished();

		// creates the controller
		tasksFiltersController = new TasksFiltersController();
		tasksFiltersController.setMyCompany(company1);
	}

	@After
	public void tearDown() {
		Company.clear();
		user1 = null;
		user2 = null;
		user3 = null;
		project1 = null;
		projCollab1 = null;
		projCollab2 = null;
		projCollab3 = null;
		task1 = null;
		task2 = null;
		task3 = null;
		task4 = null;
		task5 = null;
		task6 = null;
		taskCollab1 = null;
		taskCollab2 = null;
		taskCollab3 = null;
		taskCollab4 = null;
		taskCollab5 = null;
		taskCollab6 = null;
		tasksFiltersController = null;
	}

	/**
	 * 
	 * US370v02 - As Project Manager, I want to get a list of completed tasks in
	 * descending order of completion date.
	 */
	@Test
	public void test_getFinishedTasksInDescreasingOrder() {

		// create an Task Finished Dates
		Calendar taskFinihedDateTest1 = Calendar.getInstance();
		taskFinihedDateTest1.set(Calendar.YEAR, 2017);
		taskFinihedDateTest1.set(Calendar.MONTH, Calendar.DECEMBER);
		taskFinihedDateTest1.set(Calendar.DAY_OF_MONTH, 18);
		taskFinihedDateTest1.set(Calendar.HOUR_OF_DAY, 14);

		Calendar taskFinihedDateTest2 = Calendar.getInstance();
		taskFinihedDateTest2.set(Calendar.YEAR, 2017);
		taskFinihedDateTest2.set(Calendar.MONTH, Calendar.DECEMBER);
		taskFinihedDateTest2.set(Calendar.DAY_OF_MONTH, 19);
		taskFinihedDateTest2.set(Calendar.HOUR_OF_DAY, 14);

		Calendar taskFinihedDateTest3 = Calendar.getInstance();
		taskFinihedDateTest3.set(Calendar.YEAR, 2017);
		taskFinihedDateTest3.set(Calendar.MONTH, Calendar.DECEMBER);
		taskFinihedDateTest3.set(Calendar.DAY_OF_MONTH, 20);
		taskFinihedDateTest3.set(Calendar.HOUR_OF_DAY, 14);

		// Sets all 4 tasks as finished
		task4.setFinishDate(taskFinihedDateTest1);
		task4.markTaskAsFinished();

		task5.setFinishDate(taskFinihedDateTest2);
		task5.markTaskAsFinished();

		task6.setFinishDate(taskFinihedDateTest3);
		task6.markTaskAsFinished();

		// create a list and add task in creation order, to compare with the list
		// given by method getProjectFinishedTaskList
		List<Task> finishedTasks = new ArrayList<>();

		finishedTasks.add(task1);
		finishedTasks.add(task4);
		finishedTasks.add(task5);
		finishedTasks.add(task6);

		// this assert confirm that the list of all finished task is in creation date
		// order.
		assertEquals(finishedTasks, tasksFiltersController.getProjectFinishedTaskList(project1));

		// Create a list and add task in decreasing order, to compare with the list
		// given by method getFinishedTasksInDescreasingOrder
		List<Task> tasksInOrder = new ArrayList<>();

		tasksInOrder.add(task1);
		tasksInOrder.add(task6);
		tasksInOrder.add(task5);
		tasksInOrder.add(task4);

		// this assert confirm that the list of all finished task is in finished date
		// in decreasing order.
		assertEquals(tasksInOrder, tasksFiltersController.getFinishedTasksInDescreasingOrder(project1));
	}

}
