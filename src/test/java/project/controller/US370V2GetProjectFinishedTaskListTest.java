package project.controller;

import org.junit.After;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * US370 v2
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan({ "project.services", "project.model", "project.controller", "project.Repository" })
public class US370V2GetProjectFinishedTaskListTest {


	@Autowired
	UserService myUsers;

	@Autowired
	ProjectService myProjects;

	@Autowired
	TaskService taskService;

	User user1, user2, user3;
	Project project1;
	ProjectCollaborator projCollab1, projCollab2;
	Task task1, task2, task3, task4, task5, task6;
	Calendar estimatedTaskStartDate, taskDeadline;


	@Autowired
	US370GetProjectFinishedTaskListController tasksFiltersController;

	@Before
	public void setUp() {

		// create users in company
		user2 = myUsers.createUser("João", "user2@gmail.com", "001", "Manager", "930025000",
				"rua doutor antónio", "7689-654", "porto", "porto", "portugal");

		user1 = myUsers.createUser("Juni", "user3@gmail.com", "002", "Code Monkey", "930000000",
				"rua engenheiro joão", "789-654", "porto", "porto", "portugal");


		// change profiles of users from VISITOR (default) to COLLABORATOR
		user2.setUserProfile(Profile.COLLABORATOR);
		user1.setUserProfile(Profile.COLLABORATOR);

		myUsers.addUserToUserRepositoryX(user2);
		myUsers.addUserToUserRepositoryX(user1);

		// create project 1 in company 1
		project1 = myProjects.createProject("name3", "description4", user2);



		// create tasks in project 1
		task1 = taskService.createTask("Do this", project1);
		task2 = taskService.createTask("Do that", project1);
		task3 = taskService.createTask("Merge everything", project1);
		task4 = taskService.createTask("Do this x4", project1);
		task5 = taskService.createTask("Do this x5", project1);
		task6 = taskService.createTask("Do this x6", project1);


		// add costPerEffort to users in project 1, resulting in a Project Collaborator
		// for each one
		projCollab1 = myProjects.createProjectCollaborator(user1,project1, 250);
		projCollab2 = myProjects.createProjectCollaborator(user2,project1, 120);

		// add ProjectCollaborators  to tasks
		task2.addProjectCollaboratorToTask(projCollab2);
		task3.addProjectCollaboratorToTask(projCollab2);

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
		estimatedTaskStartDate = null;
		taskDeadline = null;
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

		// necessary to pass from "Planned" to "Assigned"
		// and then to ready since there are no dependencies
		task1.addProjectCollaboratorToTask(projCollab1);

		// necessary to pass from "Ready" to "OnGoing"
		Calendar projStartDate = (Calendar) estimatedTaskStartDate.clone();
		task1.setStartDate(projStartDate);

		// pass from "OnGoing" to "Finished"
		Calendar testDate4 = Calendar.getInstance();
		testDate4.set(Calendar.YEAR, 2017);
		testDate4.set(Calendar.MONTH, Calendar.APRIL);
		testDate4.set(Calendar.DAY_OF_MONTH, 20);
		testDate4.set(Calendar.HOUR_OF_DAY, 14);
		task1.setFinishDate(testDate4);
		task1.getTaskState().doAction(task1);

		// temporary force state
        task1.setCurrentState(StateEnum.FINISHED);
        task1.setTaskState(new Finished());

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

		// necessary to pass from "Planned" to "Assigned"
		// pass from "Assigned" to "Ready"
		task6.addProjectCollaboratorToTask(projCollab2);

		// necessary to pass from "Ready" to "OnGoing"
		task6.setStartDate(projStartDate);

		// pass from "OnGoing" to "Finished"
		Calendar testDate3 = Calendar.getInstance();
		testDate3.set(Calendar.YEAR, 2017);
		testDate3.set(Calendar.MONTH, Calendar.MARCH);
		testDate3.set(Calendar.DAY_OF_MONTH, 20);
		testDate3.set(Calendar.HOUR_OF_DAY, 14);
		task6.setFinishDate(testDate3);
		task6.getTaskState().doAction(task6);

        // temporary force state!
        task6.setCurrentState(StateEnum.FINISHED);
        task6.setTaskState(new Finished());

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

		// necessary to pass from "Planned" to "Assigned"
		// pass from "Assigned" to "Ready"
		task5.addProjectCollaboratorToTask(projCollab2);

		// necessary to pass from "Ready" to "OnGoing"
		task5.setStartDate(projStartDate);

		// pass from "OnGoing" to "Finished"
		Calendar testDate2 = Calendar.getInstance();
		testDate2.set(Calendar.YEAR, 2017);
		testDate2.set(Calendar.MONTH, Calendar.FEBRUARY);
		testDate2.set(Calendar.DAY_OF_MONTH, 20);
		testDate2.set(Calendar.HOUR_OF_DAY, 14);
		task5.setFinishDate(testDate2);
		task5.getTaskState().doAction(task5);

        // temporary force state!
        task5.setCurrentState(StateEnum.FINISHED);
        task5.setTaskState(new Finished());

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

		// necessary to pass from "Planned" to "Assigned"
		// pass from "Assigned" to "Ready"
		task4.addProjectCollaboratorToTask(projCollab2);

		// necessary to pass from "Ready" to "OnGoing"
		task4.setStartDate(projStartDate);

		// pass from "OnGoing" to "Finished"
		Calendar testDate = Calendar.getInstance();
		testDate.set(Calendar.YEAR, 2017);
		testDate.set(Calendar.MONTH, Calendar.JANUARY);
		testDate.set(Calendar.DAY_OF_MONTH, 20);
		testDate.set(Calendar.HOUR_OF_DAY, 14);
		task4.setFinishDate(testDate);
		task4.getTaskState().doAction(task4);

        // temporary force state!
        task4.setCurrentState(StateEnum.FINISHED);
        task4.setTaskState(new Finished());

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
