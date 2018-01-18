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
	Calendar estimatedTaskStartDate, taskDeadline;

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

		// create tasks in project 1
		task1 = project1.getTaskRepository().createTask("Do this");
		task2 = project1.getTaskRepository().createTask("Do that");
		task3 = project1.getTaskRepository().createTask("Merge everything");
		task4 = project1.getTaskRepository().createTask("Do this");
		task5 = project1.getTaskRepository().createTask("Do this");
		task6 = project1.getTaskRepository().createTask("Do this");

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
		task2.addTaskCollaboratorToTask(taskCollab2);
		task3.addTaskCollaboratorToTask(taskCollab3);


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
		
		// set testTask1 as finished
		// necessary to pass from "Created" to "Planned"
		estimatedTaskStartDate = Calendar.getInstance();
		estimatedTaskStartDate.add(Calendar.MONTH, -1);
		task1.setEstimatedTaskStartDate(estimatedTaskStartDate);
		taskDeadline = Calendar.getInstance();
		taskDeadline.add(Calendar.MONTH, -5);
		task1.setTaskDeadline(taskDeadline);
		task1.getTaskState().changeToPlanned();
		// necessary to pass from "Planned" to "Assigned"
		task1.addProjectCollaboratorToTask(projCollab1);
		task1.getTaskState().changeToAssigned();
		// pass from "Assigned" to "Ready"
		task1.getTaskState().changeToReady();
		// necessary to pass from "Ready" to "OnGoing"
		Calendar projStartDate = (Calendar) estimatedTaskStartDate.clone();
		task1.setStartDate(projStartDate);
		task1.getTaskState().changeToOnGoing();
		// pass from "OnGoing" to "Finished"
		Calendar testDate4 = (Calendar) estimatedTaskStartDate.clone();
		testDate4.set(Calendar.YEAR, 2017);
		testDate4.set(Calendar.MONTH, Calendar.APRIL);
		testDate4.set(Calendar.DAY_OF_MONTH, 20);
		testDate4.set(Calendar.HOUR_OF_DAY, 14);
		task1.setFinishDate(testDate4);
		task1.getTaskState().changeToFinished();
		// assures that the taskTest state is Finished
		assertEquals("Finished", task1.viewTaskStateName());
				
		
		// set testTask6 as finished
		// necessary to pass from "Created" to "Planned"
		estimatedTaskStartDate = Calendar.getInstance();
		estimatedTaskStartDate.add(Calendar.MONTH, -4);
		task6.setEstimatedTaskStartDate(estimatedTaskStartDate);
		taskDeadline = Calendar.getInstance();
		taskDeadline.add(Calendar.MONTH, 1);
		task6.setTaskDeadline(taskDeadline);
		task6.getTaskState().changeToPlanned();
		// necessary to pass from "Planned" to "Assigned"
		task6.addProjectCollaboratorToTask(projCollab2);
		task6.getTaskState().changeToAssigned();
		// pass from "Assigned" to "Ready"
		task6.getTaskState().changeToReady();
		// necessary to pass from "Ready" to "OnGoing"
		task6.setStartDate(projStartDate);
		task6.getTaskState().changeToOnGoing();
		// pass from "OnGoing" to "Finished"
		Calendar testDate3 = (Calendar) estimatedTaskStartDate.clone();
		testDate3.set(Calendar.YEAR, 2017);
		testDate3.set(Calendar.MONTH, Calendar.MARCH);
		testDate3.set(Calendar.DAY_OF_MONTH, 20);
		testDate3.set(Calendar.HOUR_OF_DAY, 14);
		task6.setFinishDate(testDate3);
		task6.getTaskState().changeToFinished();
		// assures that the taskTest state is Finished
		assertEquals("Finished", task6.viewTaskStateName());
		
		
		// set testTask5 as finished
		// necessary to pass from "Created" to "Planned"
		estimatedTaskStartDate = Calendar.getInstance();
		estimatedTaskStartDate.add(Calendar.MONTH, -3);
		task5.setEstimatedTaskStartDate(estimatedTaskStartDate);
		taskDeadline = Calendar.getInstance();
		taskDeadline.add(Calendar.MONTH, 1);
		task5.setTaskDeadline(taskDeadline);
		task5.getTaskState().changeToPlanned();

		// necessary to pass from "Planned" to "Assigned"
		task5.addProjectCollaboratorToTask(projCollab2);
		task5.getTaskState().changeToAssigned();

		// pass from "Assigned" to "Ready"
		task5.getTaskState().changeToReady();

		// necessary to pass from "Ready" to "OnGoing"
		task5.setStartDate(projStartDate);
		task5.getTaskState().changeToOnGoing();

		// pass from "OnGoing" to "Finished"
		Calendar testDate2 = (Calendar) estimatedTaskStartDate.clone();
		testDate2.set(Calendar.YEAR, 2017);
		testDate2.set(Calendar.MONTH, Calendar.FEBRUARY);
		testDate2.set(Calendar.DAY_OF_MONTH, 20);
		testDate2.set(Calendar.HOUR_OF_DAY, 14);
		task5.setFinishDate(testDate2);
		task5.getTaskState().changeToFinished();
		// assures that the taskTest state is Finished
		assertEquals("Finished", task5.viewTaskStateName());
		

		// set testTask4 as finished
		// necessary to pass from "Created" to "Planned"
		estimatedTaskStartDate = Calendar.getInstance();
		estimatedTaskStartDate.add(Calendar.MONTH, -2);
		task4.setEstimatedTaskStartDate(estimatedTaskStartDate);
		taskDeadline = Calendar.getInstance();
		taskDeadline.add(Calendar.MONTH, 1);
		task4.setTaskDeadline(taskDeadline);
		task4.getTaskState().changeToPlanned();
		// necessary to pass from "Planned" to "Assigned"
		task4.addProjectCollaboratorToTask(projCollab2);
		task4.getTaskState().changeToAssigned();
		// pass from "Assigned" to "Ready"
		task4.getTaskState().changeToReady();
		// necessary to pass from "Ready" to "OnGoing"
		task4.setStartDate(projStartDate);
		task4.getTaskState().changeToOnGoing();
		// pass from "OnGoing" to "Finished"
		Calendar testDate = (Calendar) estimatedTaskStartDate.clone();
		testDate.set(Calendar.YEAR, 2017);
		testDate.set(Calendar.MONTH, Calendar.JANUARY);
		testDate.set(Calendar.DAY_OF_MONTH, 20);
		testDate.set(Calendar.HOUR_OF_DAY, 14);
		task4.setFinishDate(testDate);
		task4.getTaskState().changeToFinished();
		// assures that the taskTest state is Finished
		assertEquals("Finished", task4.viewTaskStateName());
		
		
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
