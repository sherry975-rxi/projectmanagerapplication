package project.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.model.Company;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.Task;
import project.model.TaskCollaborator;
import project.model.User;
import project.model.taskStateInterface.OnGoing;
import project.model.taskStateInterface.TaskStateInterface;

public class ProjectCollaboratorAssociatedToTaskControllerTest {

	/**
	 * Tests Controller for US204v2 and 206v2, which contain functions a project
	 * collaborator can perform on Tasks he's associated to
	 */
	Company critical;

	User newUserA;
	Project project1;
	Task taskA;

	ProjectCollaborator projCollab1, projCollabNull;
	TaskCollaborator taskWorker1;
	ProjectCollaboratorAssociatedToTaskController taskController;

	@Before
	public void setUp() {
		critical = Company.getTheInstance();

		// create user
		newUserA = new User("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000");

		// Creation of one project and newUser4 set as the project manager
		project1 = new Project(0, "name3", "description4", newUserA);

		// adds both project and user to Company
		critical.getUsersRepository().addUserToUserRepository(newUserA);
		critical.getProjectsRepository().addProjectToProjectRepository(project1);

		// Creation of two tasks: taskA and taskB
		Calendar startDateA = Calendar.getInstance();
		startDateA.clear();
		startDateA.set(2017, 05, 15);
		Calendar finishDateA = Calendar.getInstance();
		finishDateA.clear();
		finishDateA.set(2017, 05, 16);
		taskA = project1.getTaskRepository().createTask("Test dis pls", 100, startDateA, finishDateA, 15000);
		taskA.setStartDate(startDateA);

		// Creates a project collaborator
		projCollab1 = project1.createProjectCollaborator(newUserA, 250);

		// Creates a taks worker
		taskWorker1 = taskA.createTaskCollaborator(projCollab1);

		TaskStateInterface taskAongoing = new OnGoing(taskA);
		taskA.setTaskState(taskAongoing);

		// creates the controller
		taskController = new ProjectCollaboratorAssociatedToTaskController(project1.getIdCode());

	}

	@After
	public void tearDown() {

		// Clears all the instances
		Company.clear();
		critical = null;
		newUserA = null;
		project1 = null;
		taskA = null;
		taskController = null;
		projCollab1 = null;
		taskWorker1 = null;

	}

	/*
	 * 
	 * This method tests the addProjectCollaboratorToTask Controller, asserting the
	 * creation of assignment requests
	 */
	@Test
	public void testCreateProjectCollaboratorAssignmentToTaskController() {

		// Expects that projCollab1 is not associated to the taskA and the project
		// contains no assignment requests
		assertFalse(taskA.isProjectCollaboratorActiveInTaskTeam(projCollab1));
		assertEquals(project1.getAssignmentRequestsList().size(), 0);

		// Given projCollab1's request to join task team, asserts the list now contains
		// one assignment request
		assertTrue(taskController.createTaskWorkerAssignmentRequestController(taskA, projCollab1));
		assertEquals(project1.getAssignmentRequestsList().size(), 1);

		// Attempts to add the same request, asserting false
		// then confirms the list still contains one assignment request

		assertFalse(taskController.createTaskWorkerAssignmentRequestController(taskA, projCollab1));

		assertEquals(project1.getAssignmentRequestsList().size(), 1);

		// Checks if projCollab1 remains not associated to the Task
		assertFalse(taskA.isProjectCollaboratorActiveInTaskTeam(projCollab1));

	}

	/*
	 * 
	 * This method tests the removeProjectCollaboratorFromTask Controller, asserting
	 * the creation of removal requests
	 */
	@Test
	public void testCreateProjectCollaboratorRemovalFromTaskController() {

		// Checks if projCollab1 is now associated to the Task
		// also asserts the project starts with 0 requests 0 requests
		taskA.addProjectCollaboratorToTask(projCollab1);
		assertTrue(taskA.isProjectCollaboratorActiveInTaskTeam(projCollab1));
		assertEquals(project1.getAssignmentRequestsList().size(), 0);

		// Given projCollab1's request to join task team, asserts the list now contains
		// one assignment request
		assertTrue(taskController.createTaskWorkerRemovalRequestController(taskA, projCollab1));
		assertEquals(project1.getRemovalRequestsList().size(), 1);

		// Then, asserts if the same project collaborator is unable to issue multiple
		// requests to leave the same task
		// one assignment request
		assertFalse(taskController.createTaskWorkerRemovalRequestController(taskA, projCollab1));
		assertEquals(project1.getRemovalRequestsList().size(), 1);

		// Finally, asserts the projectCollaborator is still active in the Task
		assertTrue(taskA.isProjectCollaboratorActiveInTaskTeam(projCollab1));

	}

	/*
	 * 
	 * This method tests the markTaskAsFinished Controller
	 */
	@Test
	public void testMarkTaskAsFinishedController() {

		// Adds projCollab1 to taskA and taskB
		taskA.addProjectCollaboratorToTask(projCollab1);
		taskController.createTaskWorkerAssignmentRequestController(taskA, projCollab1);

		// Checks if taskA is finished. Result is false
		assertFalse(taskA.viewTaskStateName().equals("Finished"));

		// Marks taskA as finished
		assertTrue(taskController.markTaskAsFinishedController(taskA));

		// Checks if taskA is finished. Result is true

		assertTrue(taskA.viewTaskStateName().equals("Finished"));
		assertFalse(taskA.doesTaskTeamHaveActiveUsers());

	}

}
