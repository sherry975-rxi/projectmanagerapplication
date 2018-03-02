package project.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

//

public class ProjectTest {

	User user1;
	User user2;
	User user3;
	User user4;
	ProjectCollaborator projectCollaborator2;
	ProjectCollaborator projectCollaborator1;
	ProjectCollaborator projectCollaborator3;
	ProjectCollaborator projectCollaborator4;
	Calendar estimatedStartDate;
	Calendar taskDeadline;
	Task task1;
	Task task2;
	Task task3;
	Task task4;
	TaskCollaborator taskWorker1;
	TaskCollaborator taskWorker2;
	Project p1;
	Project p2;
	TaskRepository taskRepository;

	@Before
	public void setUp() {

		// create user
		user1 = new User("name", "email", "idNumber", "function", "123456789");
		user2 = new User("name2", "email2", "idNumber2", "function2", "987654321");
		user3 = new User("name3", "email3", "idNumber3", "function3", "123456389");
		user4 = new User("name4", "email4", "idNumber4", "function4", "123456489");
		// create project collaborator
		projectCollaborator2 = new ProjectCollaborator(user2, 1200);
		projectCollaborator1 = new ProjectCollaborator(user1, 1200);
		projectCollaborator3 = new ProjectCollaborator(user3, 1200);
		projectCollaborator4 = new ProjectCollaborator(user4, 1200);
		// create task worker
		taskWorker1 = new TaskCollaborator(projectCollaborator1);
		taskWorker2 = new TaskCollaborator(projectCollaborator2);
		// create project
		p1 = new Project(1, "name3", "description4", user1);
		p2 = new Project(2, "name1", "description4", user2);

		estimatedStartDate = Calendar.getInstance();
		estimatedStartDate.set(2017, Calendar.JANUARY, 14);

		taskDeadline = Calendar.getInstance();
		taskDeadline.set(2017, Calendar.NOVEMBER, 17);

		task1 = p1.getTaskRepository().createTask("description", 0, estimatedStartDate, taskDeadline, 0);
		p1.getTaskRepository().addProjectTask(task1);
		task1.addProjectCollaboratorToTask(projectCollaborator2);

		task2 = p1.getTaskRepository().createTask("description2", 0, null, null, 0);
		p1.getTaskRepository().addProjectTask(task2);

		// task2 state to Finished
		// necessary to pass from "Created" to "Planned"
		estimatedStartDate = Calendar.getInstance();
		estimatedStartDate.add(Calendar.MONTH, -1);
		task2.setEstimatedTaskStartDate(estimatedStartDate);
		taskDeadline = Calendar.getInstance();
		taskDeadline.add(Calendar.MONTH, 1);
		task2.setTaskDeadline(taskDeadline);

		task2.getTaskState().changeToPlanned();

		// necessary to pass from "Planned" to "Assigned"
		task2.addProjectCollaboratorToTask(projectCollaborator2);
		task2.getTaskState().changeToAssigned();

		// pass from "Assigned" to "Ready"
		task2.getTaskState().changeToReady();

		// necessary to pass from "Ready" to "OnGoing"
		Calendar projStartDate = (Calendar) estimatedStartDate.clone();
		task2.setStartDate(projStartDate);
		task2.getTaskState().changeToOnGoing();

		// pass from "OnGoing" to "Finished"
		Calendar testDate = (Calendar) estimatedStartDate.clone();
		task2.setFinishDate(testDate);
		task2.getTaskState().changeToFinished();

		// assures that the taskTest state is Finished
		assertEquals("Finished", task2.viewTaskStateName());

		task3 = p1.getTaskRepository().createTask("description3", 0, null, null, 0);

		// task3 state to Finished
		// necessary to pass from "Created" to "Planned"
		estimatedStartDate = Calendar.getInstance();
		estimatedStartDate.add(Calendar.MONTH, -1);
		task3.setEstimatedTaskStartDate(estimatedStartDate);
		taskDeadline = Calendar.getInstance();
		taskDeadline.add(Calendar.MONTH, 1);
		task3.setTaskDeadline(taskDeadline);

		task3.getTaskState().changeToPlanned();

		// necessary to pass from "Planned" to "Assigned"
		task3.addProjectCollaboratorToTask(projectCollaborator2);
		task3.getTaskState().changeToAssigned();

		// pass from "Assigned" to "Ready"
		task3.getTaskState().changeToReady();

		// necessary to pass from "Ready" to "OnGoing"
		Calendar projStartDate3 = (Calendar) estimatedStartDate.clone();
		task3.setStartDate(projStartDate3);
		task3.getTaskState().changeToOnGoing();

		// pass from "OnGoing" to "Finished"
		Calendar testDate3 = (Calendar) estimatedStartDate.clone();
		task3.setFinishDate(testDate3);
		task3.getTaskState().changeToFinished();

		// assures that the taskTest state is Finished
		assertEquals("Finished", task2.viewTaskStateName());
		task4 = p1.getTaskRepository().createTask("description11111", 0, estimatedStartDate, taskDeadline, 0);

	}

	@After
	public void tearDown() {
		user1 = null;
		user2 = null;
		user3 = null;
		user4 = null;
		task1 = null;
		task2 = null;
		task3 = null;
		task4 = null;
		taskWorker1 = null;
		taskWorker2 = null;
		p1 = null;
		p2 = null;
		projectCollaborator2 = null;
		projectCollaborator1 = null;
		projectCollaborator3 = null;
		projectCollaborator4 = null;
		taskRepository = null;
	}

	/**
	 * Tests constructor for Project
	 */
	@Test
	public void testProject() {

		List<Task> tasksListofThisProject = new ArrayList<Task>();
		tasksListofThisProject.add(task1);
		tasksListofThisProject.add(task2);
		List<ProjectCollaborator> projectTeam = new ArrayList<ProjectCollaborator>();
		p1.setEffortUnit(EffortUnit.HOURS);
		p1.setProjectBudget(100);

		assertEquals(EffortUnit.HOURS, p1.getEffortUnit());
		assertEquals(100, p1.getProjectBudget());
		assertEquals(EffortUnit.HOURS, p1.getEffortUnit());
		assertEquals(1, p1.getIdCode());
		assertEquals(0, p1.getProjectStatus());
		assertTrue(p1.isProjectManager(user1));
		assertEquals(tasksListofThisProject, p1.getTaskRepository().getProjectTaskRepository());
		assertEquals(projectTeam, p1.getProjectTeam());
	}

	/**
	 * Tests the Creator of Project Collaborator that instantiates the object
	 * Project Collaborator
	 */
	@Test
	public void testCreateProjectCollaborator() {

		assertEquals(projectCollaborator2, p1.createProjectCollaborator(user2, 1200));
	}

	/**
	 * Tests addition of task to Project Task list
	 */
	@Test
	public void testAddTaskToProjectTaskList() {
		p1.getTaskRepository().addProjectTask(task4);
		assertEquals(task4, p1.getTaskRepository().getProjectTaskRepository().get(2));
	}

	/**
	 * This test checks the project manager of Project 1
	 */
	@Test
	public void testCheckProjectManagerTrue() {
		assertTrue(p1.isProjectManager(user1));
	}

	/**
	 * This test checks the project manager of Project 1
	 */
	@Test
	public void testCheckProjectManagerFalse() {
		assertFalse(p1.isProjectManager(user2));
	}

	/**
	 * This test changes project manager of Project 1 and checks if this change
	 * occurred.
	 */
	@Test
	public void testSetProjectManagerTrue() {

		p1.setProjectManager(user2);
		assertTrue(p1.isProjectManager(user2));
	}

	/**
	 * This test changes project manager of Project 1 and then checks if the
	 * previous Project Manager is no longer in the project manager.
	 */
	@Test
	public void testSetProjectManagerFalse() {
		p1.setProjectManager(user2);
		assertFalse(p1.isProjectManager(user1));
	}

	/**
	 * This test changes project manager of Project 1 and then checks if the
	 * previous Project Manager is no longer in the project manager.
	 */
	@Test
	public void testAddUserToProjectTeam() {
		p1.addProjectCollaboratorToProjectTeam(projectCollaborator2);
		assertEquals(user2, p1.getProjectTeam().get(0).getUserFromProjectCollaborator());

		// Deactivates user2(projectCollaborator2) from project
		p1.removeProjectCollaboratorFromProjectTeam(user2);
		// Adds user2 again to the project (this method checks for users that dont
		// belong to the team or that were deactivated from project
		p1.addProjectCollaboratorToProjectTeam(projectCollaborator2);
		assertEquals(user2, p1.getProjectTeam().get(0).getUserFromProjectCollaborator());

	}

	/**
	 * Tests that it is impossible to add the same object twice to the ProjectTeam
	 */
	@Test
	public void testTryToAddTheSameUserTwiceToProjectTeam() {
		p1.addProjectCollaboratorToProjectTeam(projectCollaborator1);
		p1.addProjectCollaboratorToProjectTeam(projectCollaborator1);
		assertEquals(1, p1.getProjectTeam().size());
	}

	// /**
	// * Tests the comparison between objects that are different and from different
	// * types
	// */
	// @Test public
	// void testEqualsDifferentObject() {
	// assertFalse(p1.equals(user1));
	// }

	/**
	 * Sets the status of a project to Execution
	 */
	@Test
	public void testProjectState() {
		p1.setProjectStatus(Project.EXECUTION);
		assertEquals(Project.EXECUTION, p1.getProjectStatus());
	}

	/**
	 * Tests the return of the unfinished tasks only of a user in a project.
	 */
	@Test
	public void testGetUnfinishedTasks() {
		task1.addTaskCollaboratorToTask(taskWorker1);
		assertEquals(task1,
				p1.getTaskRepository().getUnfinishedTasksFromProjectCollaborator(projectCollaborator1).get(0));
	}

	/**
	 * Tests the return of the finished tasks only of a user in a project. The same
	 * task worker can be associated with different tasks.
	 */
	@Test
	public void testGetFinishedTasks() {

		task2.addTaskCollaboratorToTask(taskWorker1);
		assertEquals(task2, p1.getTaskRepository().getFinishedTaskListofUserInProject(projectCollaborator1).get(0));
	}

	@Test
	public void testGetAllTasks() {// The order of tasks in the test list changes because the getAllTasks method
									// shows the finished tasks first.
		task1.addTaskCollaboratorToTask(taskWorker1);
		task2.addTaskCollaboratorToTask(taskWorker1);
		List<Task> test = new ArrayList<Task>();
		test.add(task1);
		test.add(task2);
		assertEquals(test, p1.getTaskRepository().getAllTasksFromProjectCollaborator(projectCollaborator1));
	}

	/**
	 * 
	 */
	@Test
	public void testGetFinishedTaskListLastMonth() {
		Calendar test = Calendar.getInstance();
		test.add(Calendar.MONTH, -1);
		task1.addTaskCollaboratorToTask(taskWorker1);
		task2.addTaskCollaboratorToTask(taskWorker1);
		task3.addTaskCollaboratorToTask(taskWorker1);
		p1.getTaskRepository().addProjectTask(task1);
		p1.getTaskRepository().addProjectTask(task2);
		p1.getTaskRepository().addProjectTask(task3);
		task2.markTaskAsFinished();
		task3.markTaskAsFinished();
		task2.getFinishDate().set(Calendar.MONTH, test.get(Calendar.MONTH) - 1);
		task3.getFinishDate().set(Calendar.MONTH, test.get(Calendar.MONTH));
		assertEquals(task3, p1.getTaskRepository()
				.getFinishedTasksFromProjectCollaboratorInGivenMonth(projectCollaborator1, 1).get(0));
	}

	@Test
	public void testAddUserToTask() {
		p1.getTaskRepository().addProjectTask(task1);
		p1.getTaskRepository().addProjectTask(task2);
		p1.getTaskRepository().addProjectTask(task3);
		task2.addTaskCollaboratorToTask(taskWorker1);
		assertTrue(task2.isProjectCollaboratorInTaskTeam(projectCollaborator1));
	}

	@Test
	public void testFailToAddUserToTask() {
		p1.getTaskRepository().addProjectTask(task1);
		p1.getTaskRepository().addProjectTask(task2);
		p1.getTaskRepository().addProjectTask(task3);
		task2.addTaskCollaboratorToTask(taskWorker1);
		assertFalse(task1.isProjectCollaboratorInTaskTeam(projectCollaborator1));
	}

	@Test
	public void testProjectContainsTask() {
		assertTrue(p1.getTaskRepository().isTaskInRTaskRepository(task2));
	}

	@Test
	public void testProjectContainsTaskFalse() {
		assertFalse(p1.getTaskRepository().isTaskInRTaskRepository(task3));
	}

	/**
	 * This method allows removing a Project Collaborator from a Project Team and
	 * includes removing that Project Collaborator from all Tasks in this Project
	 * 
	 * projectCollaborator1 is removed from ProjectTeam
	 */
	@Test
	public void testRemoveCollaboratorFromProjectTeam() {

		// given user 2 not in Project 1, then attempting to remove it must return false
		assertFalse(p1.removeProjectCollaboratorFromProjectTeam(user2));

		p1.addProjectCollaboratorToProjectTeam(projectCollaborator2);
		p1.addProjectCollaboratorToProjectTeam(projectCollaborator1);
		task1.addTaskCollaboratorToTask(taskWorker2);
		task1.addTaskCollaboratorToTask(taskWorker1);

		assertTrue(p1.removeProjectCollaboratorFromProjectTeam(user2));

		// asserts the project team still contains both users
		assertEquals(2, p1.getProjectTeam().size());
		assertTrue(projectCollaborator2.equals(p1.getProjectTeam().get(0)));

		// asserts the active project team contains only user1
		assertEquals(1, p1.getActiveProjectTeam().size());

		// asserts the removed user was also removed from their tasks
		assertTrue(p1.getTaskRepository().getAllTasksFromProjectCollaborator(projectCollaborator1).get(0).getTaskTeam()
				.get(1).isTaskCollaboratorActiveInTask());
		assertFalse(p1.getTaskRepository().getAllTasksFromProjectCollaborator(projectCollaborator2).get(0).getTaskTeam()
				.get(0).isTaskCollaboratorActiveInTask());
	}

	/**
	 * Tests the calculation of the project cost (the sum of the values reported to
	 * the task until the moment)
	 */
	@Test
	public void testGetTotalCostReportedToProjectUntilNow() {

		task1.addTaskCollaboratorToTask(taskWorker2);
		task1.addTaskCollaboratorToTask(taskWorker1);

		// task1.addUserToTask(taskWorker1);
		// task1.createReport(taskWorker1);
		// task1.getReports().get(0).setReportedTime(5);

		// The TaskWorker is not finished because it has not a finishDate,
		// and the current info about the TimeSpent is by default zero.
		// The following instructions will change that TimeSpent for each TaskWorker

		task1.createReport(taskWorker2);
		task1.createReport(taskWorker1);

		task1.getReports().get(0).setReportedTime(5);
		task1.getReports().get(1).setReportedTime(7);
		// task1.getTaskTeam().get(0).setHoursSpent(5);
		// task1.getTaskTeam().get(1).setHoursSpent(7);

		// The TaskWorker has by default the Cost of the ProjectCollaborator
		// and now it has also a TimeSpent in each TaskWorker.
		// So it can be calculated the TaskCost of Task 1, that has the TaskWorker 1
		// and
		// 2
		// and so, get the Project Cost Until Now.
		// t1.getTaskCost();

		// Task 4 will be added to Project 1 and associated to a new created
		p1.getTaskRepository().addProjectTask(task4);
		// t4.createTaskWorker(projectCollaborator1);
		// task4.addUserToTask(task4.createTaskWorker(projectCollaborator2));
		task4.addProjectCollaboratorToTask(projectCollaborator2);
		task4.createReport(task4.getTaskTeam().get(0));
		// task4.getTaskTeam().get(0).setHoursSpent(7);
		task4.getReports().get(0).setReportedTime(7);

		double espectres_1 = 14400; // cost of Task 1
		double espectres_2 = 8400; // cost of Task 4
		double espectres_total = espectres_1 + espectres_2;

		assertEquals(espectres_total, p1.getTotalCostReportedToProjectUntilNow(), 0.0001);
	}

	/**
	 * Tests the method getCollaboratorsWithoutTasks that returns a list of
	 * collaborators without task in the project.
	 */
	@Test
	public void testgetCollaboratorsWithoutTasks() {

		// add all project colllaborators to Project Team
		p1.addProjectCollaboratorToProjectTeam(projectCollaborator2);
		p1.addProjectCollaboratorToProjectTeam(projectCollaborator1);
		p1.addProjectCollaboratorToProjectTeam(projectCollaborator3);
		p1.addProjectCollaboratorToProjectTeam(projectCollaborator4);

		// add task t1 and t2 to Project
		p1.getTaskRepository().addProjectTask(task1);
		p1.getTaskRepository().addProjectTask(task2);

		// add taskWorkers to tasks
		task1.addProjectCollaboratorToTask(projectCollaborator2);
		task2.addProjectCollaboratorToTask(projectCollaborator1);
		task1.addTaskCollaboratorToTask(taskWorker1);
		task2.addTaskCollaboratorToTask(taskWorker2);

		// create list to compare
		List<ProjectCollaborator> collaboratorsWithoutTasksCompare = new ArrayList<ProjectCollaborator>();
		collaboratorsWithoutTasksCompare.add(projectCollaborator3);
		collaboratorsWithoutTasksCompare.add(projectCollaborator4);

		assertEquals(collaboratorsWithoutTasksCompare, p1.getCollaboratorsWithoutTasks());

	}

	/**
	 * Tests if the user is confirmed as active and in Project correctly
	 */
	@Test
	public void testIsUserActiveInProjectTrue() {
		p1.addUserToProjectTeam(user1, 5);
		assertTrue(p1.isUserActiveInProject(user1));
	}

	/**
	 * Tests if the user is confirmed as not active if it hasn't been added to the
	 * project
	 */
	@Test
	public void testIsUserActiveInProjectNotInTheTeam() {
		assertFalse(p1.isUserActiveInProject(user1));
	}

	/**
	 * Tests if the user is confirmed as not active if it has been removed from the
	 * project
	 */
	@Test
	public void testIsUserActiveInProjectUserInactive() {
		// Adds user1 to ProjectTeam
		p1.addUserToProjectTeam(user1, 5);
		// Expected result is true. user1 is in Project team
		assertTrue(p1.isUserActiveInProject(user1));

		// Expected False. User2 is not in Project Team
		assertFalse(p1.isUserActiveInProject(user2));

		// remover user1 from ProjectTeam
		p1.removeProjectCollaboratorFromProjectTeam(user1);
		// Expected result is false. user1 is no longer in Project team
		assertFalse(p1.isUserActiveInProject(user1));
	}

	/**
	 * Tests if the method retrieves the Project Collaborator that exists in Project
	 * 1, generated from a Specific User
	 */
	@Test
	public void getProjectCollaboratorFromUser() {
		p1.addProjectCollaboratorToProjectTeam(projectCollaborator2);
		p1.addProjectCollaboratorToProjectTeam(projectCollaborator1);
		assertEquals(projectCollaborator2, p1.findProjectCollaborator(user2));

		// Tries to find an user that doesnt belong to a project. The method returns
		// null
		assertEquals(null, p1.findProjectCollaborator(user3));
	}

	/**
	 * Tests the Set and Get Finish Dates of a project
	 */

	@Test
	public void testSetAndGetFinishDate() {
		p1.setFinishdate(taskDeadline);
		assertEquals(p1.getFinishdate(), taskDeadline);

	}

	/**
	 * Tests several combinations of the Equals override
	 * 
	 */
	@Test
	public void testEquals() {
		assertTrue(p1.equals(p1));// same object
		Project p3 = null;
		assertFalse(p1.equals(p3));// null object
		assertFalse(p1.equals(task1));// different classes
		assertFalse(p1.equals(p2));// different counter
		p3 = new Project(1, "name3", "description4", user1);
		assertTrue(p1.equals(p3));// same counter
	}

	/**
	 * Tests the getName method
	 * 
	 */
	@Test
	public void testGetName() {

		// p1 name was set to "name3" on the setUp

		String projectName = "name3";
		assertEquals(p1.getName(), projectName);

	}

	/**
	 * Tests the hashcode class
	 * 
	 */
	@Test
	public void testHashcode() {
		assertTrue(p1.hashCode() == p1.hashCode());
		assertFalse(p1.hashCode() == p2.hashCode());

		// asserts the Hashcode equals 31 + ProjectID
		assertTrue (p1.hashCode() == 31*3 + p1.getIdCode());

		assertFalse(p1.hashCode() == 0);

		int result = 3 * 31 + p1.getIdCode();
		assertEquals(p1.hashCode(), result);

	}

	/**
	 * Tests the creation and addition of a new request to the Assignment Request
	 * List
	 * 
	 */
	@Test
	public void testAddTaskAssignementRequest() {
		assertTrue(p1.createTaskAssignementRequest(projectCollaborator1, task1));
	}

	/**
	 * Tests the deletion of a request from the Assignment Request List
	 * 
	 */
	@Test
	public void testDeleteTaskAssignementRequest() {
		p1.createTaskAssignementRequest(projectCollaborator1, task1);
		assertTrue(p1.isAssignementRequestAlreadyCreated(projectCollaborator1, task1));
		TaskTeamRequest req = p1.getAssignementTaskTeamRequest(projectCollaborator1, task1);
		p1.deleteTaskAssignementRequest(req);
		assertFalse(p1.isAssignementRequestAlreadyCreated(projectCollaborator1, task1));
	}

	/**
	 * Tests if the isAssignementAlreadyCreated returns the correct value for each
	 * situation
	 */
	@Test
	public void testIsAssignementAlreadyCreated() {
		p1.createTaskAssignementRequest(projectCollaborator1, task1);
		assertTrue(p1.isAssignementRequestAlreadyCreated(projectCollaborator1, task1));
		assertFalse(p1.isAssignementRequestAlreadyCreated(projectCollaborator3, task4));
	}

	/**
	 * Tests the string representation of the Assignement Request List
	 * 
	 */
	@Test
	public void testViewTaskAssignementRequest() {
		p1.createTaskAssignementRequest(projectCollaborator1, task1);
		String result = "name" + "\n" + "email" + "\n" + "1.1" + "\n" + "description";
		assertTrue(p1.viewPendingTaskAssignementRequests().get(0).equals(result));
	}

	/**
	 * Test the getter of a removal from task request
	 */
	@Test
	public void testGetRemovalRequestsList() {
		// no request added, so size should be zero
		assertEquals(0, p1.getRemovalRequestsList().size());

	}

	/**
	 * Tests the creation and addition of a new request to the Removal Request List
	 * 
	 */
	@Test
	public void testCreateTaskRemovalRequest() {
		// if doesn't exist allows to create the request
		assertTrue(p1.createTaskRemovalRequest(projectCollaborator1, task1));
		// if exists it doesn't allow to create the request
		assertFalse(p1.createTaskRemovalRequest(projectCollaborator1, task1));

	}

	/**
	 * If a Removal request already exists then the method confirms that it exists
	 * 
	 */
	@Test
	public void testIsRemovalRequestAlreadyCreated() {

		// create and add removal request of certain project collaborator and certain
		// task
		p1.createTaskRemovalRequest(projectCollaborator1, task1);

		// checks if the removal request was added to the list
		assertTrue(p1.isRemovalRequestAlreadyCreated(projectCollaborator1, task1));

	}

	/**
	 * Test the getter of a removal from task request
	 */
	@Test
	public void testGetRemovalTaskTeamRequest() {
		// create and add a request of certain project collaborator and certain task OK
		p1.createTaskRemovalRequest(projectCollaborator1, task1);
		// create a similar request of the one created
		TaskTeamRequest expectedRequest = new TaskTeamRequest(projectCollaborator1, task1);
		TaskTeamRequest req = p1.getRemovalTaskTeamRequest(projectCollaborator1, task1);
		// if the info are the same, the objects should be considered the same
		assertTrue(req.equals(expectedRequest));

	}

	/**
	 * Tests the remove of an existing request in the Task Removal Request List
	 */
	@Test
	public void testDeleteTaskRemovalRequest() {
		// create a removal request of certain project collaborator and certain task
		p1.createTaskRemovalRequest(projectCollaborator1, task1);
		// delete the request added to the task removal list
		assertTrue(p1.deleteTaskRemovalRequest(projectCollaborator1, task1));
	}

	/**
	 * Tests the string representation of a request from the Removal Request List
	 * 
	 */
	@Test
	public void testViewTaskRemovalRequest() {
		p1.createTaskRemovalRequest(projectCollaborator1, task1);
		String result = "name" + "\n" + "email" + "\n" + "1.1" + "\n" + "description";
		assertTrue(p1.viewPendingTaskRemovalRequests().get(0).equals(result));
	}

	/**
	 * Tests if removeAllRequestsWithASpecificTaskFromAssignementRequests() actually
	 * removes the requests chosen from the assignement requests list
	 */
	@Test
	public void testRemoveAssignementRequestsWithACertainTask() {
		p1.createTaskAssignementRequest(projectCollaborator1, task1);
		p1.createTaskAssignementRequest(projectCollaborator2, task1);
		p1.createTaskAssignementRequest(projectCollaborator3, task1);
		p1.createTaskAssignementRequest(projectCollaborator4, task1);
		p1.createTaskAssignementRequest(projectCollaborator1, task2);
		p1.createTaskAssignementRequest(projectCollaborator1, task3);
		p1.createTaskAssignementRequest(projectCollaborator2, task2);
		p1.createTaskAssignementRequest(projectCollaborator3, task2);

		/*
		 * No tasks were removed from the list yet
		 */
		assertEquals(8, p1.getAssignmentRequestsList().size());

		p1.removeAllRequestsWithASpecificTaskFromAssignementRequests(task1);

		assertEquals(4, p1.getAssignmentRequestsList().size());
	}

	/**
	 * Tests if removeAllRequestsWithASpecificTaskFromRemovalRequests() actually
	 * removes the requests chosen from the removal requests list
	 */
	@Test
	public void testRemoveRemovalRequestsWithACertainTask() {
		p1.createTaskRemovalRequest(projectCollaborator1, task1);
		p1.createTaskRemovalRequest(projectCollaborator2, task1);
		p1.createTaskRemovalRequest(projectCollaborator3, task1);
		p1.createTaskRemovalRequest(projectCollaborator4, task1);
		p1.createTaskRemovalRequest(projectCollaborator1, task2);
		p1.createTaskRemovalRequest(projectCollaborator1, task3);
		p1.createTaskRemovalRequest(projectCollaborator2, task2);
		p1.createTaskRemovalRequest(projectCollaborator3, task2);

		/*
		 * No tasks were removed from the list yet
		 */
		assertEquals(8, p1.getRemovalRequestsList().size());


		p1.removeAllRequestsWithASpecificTaskFromRemovalRequests(task1);

		assertEquals(4, p1.getRemovalRequestsList().size());
	}

	/**
	 * Tests if removeAllRequestsWithASpecificTask() actually removes the requests
	 * chosen from the both the requests list
	 */
	@Test
	public void testRemoveAllRequestsWithACertainTask() {
		p1.createTaskAssignementRequest(projectCollaborator1, task1);
		p1.createTaskAssignementRequest(projectCollaborator2, task1);
		p1.createTaskAssignementRequest(projectCollaborator3, task1);
		p1.createTaskAssignementRequest(projectCollaborator4, task1);
		p1.createTaskAssignementRequest(projectCollaborator1, task2);
		p1.createTaskAssignementRequest(projectCollaborator1, task3);
		p1.createTaskAssignementRequest(projectCollaborator2, task2);
		p1.createTaskAssignementRequest(projectCollaborator3, task2);

		p1.createTaskRemovalRequest(projectCollaborator1, task1);
		p1.createTaskRemovalRequest(projectCollaborator2, task1);
		p1.createTaskRemovalRequest(projectCollaborator3, task1);
		p1.createTaskRemovalRequest(projectCollaborator4, task1);
		p1.createTaskRemovalRequest(projectCollaborator1, task2);
		p1.createTaskRemovalRequest(projectCollaborator1, task3);
		p1.createTaskRemovalRequest(projectCollaborator2, task2);
		p1.createTaskRemovalRequest(projectCollaborator3, task2);

		p1.removeAllRequestsWithASpecificTask(task1);

		assertEquals(4, p1.getAssignmentRequestsList().size());
		assertEquals(4, p1.getRemovalRequestsList().size());
	}

	/**
	 * Tests the Set and Get of project's description
	 */
	@Test
	public void testSetAndGetProjectDescription() {
		// asserts that the project's description is "description4", which
		// is defined when the project is created
		assertEquals(p1.getProjectDescription(), "description4");

		// sets the project's description to "Projecto de tratamento de dados"
		p1.setProjectDescription("Projecto de tratamento de dados");

		// asserts that project's description change to "Projecto de tratamento de
		// dados"
		assertEquals(p1.getProjectDescription(), "Projecto de tratamento de dados");

	}

	/**
	 * Tests the Set and Get of project's description
	 */
	@Test
	public void testSetAndGetProjectManager() {
		// asserts that the project's manager is user1, which
		// is defined when the project is created
		assertEquals(p1.getProjectManager(), user1);

		// sets the project's manager to user2
		p1.setProjectManager(user2);

		// asserts that project's manager change to user2
		assertEquals(p1.getProjectManager(), user2);

	}

	/**
	 * Tests the Get of project's status name as String
	 */
	@Test
	public void testGetProjectNameString() {
		// asserts that new Project's name starts as Planning
		assertTrue(p1.getProjectStatusName().equals("Planning"));

		// then changes its state to execution and asserts the String changes
		// appropriately
		p1.setProjectStatus(Project.EXECUTION);
		assertTrue(p1.getProjectStatusName().equals("Execution"));
		p1.setProjectStatus(Project.CLOSE);
		assertTrue(p1.getProjectStatusName().equals("Closed"));
		p1.setProjectStatus(Project.DELIVERY);
		assertTrue(p1.getProjectStatusName().equals("Delivery"));
		p1.setProjectStatus(Project.EXECUTION);
		assertTrue(p1.getProjectStatusName().equals("Execution"));
		p1.setProjectStatus(Project.REVIEW);
		assertTrue(p1.getProjectStatusName().equals("Review"));
		p1.setProjectStatus(Project.INITIATION);
		assertTrue(p1.getProjectStatusName().equals("Initiation"));
		p1.setProjectStatus(Project.REVIEW);
		assertTrue(p1.getProjectStatusName().equals("Review"));

	}

	@Test
	public void getPendingTaskRemovalRequests() {
		List<TaskTeamRequest> listOfPendingRequests = new ArrayList<>();
		assertEquals(p1.getPendingTaskAssignementRequests(), listOfPendingRequests);

		// Creates a TaskAssignementRequest
		p1.createTaskAssignementRequest(projectCollaborator1, task1);
		TaskTeamRequest newReq = new TaskTeamRequest(projectCollaborator1, task1);
	}

	/**
	 * Tests the getProjectCollaboratorsFromTask
	 */
	@Test
	public void getProjectCollaboratorsFromTask() {

		// Add projectCollaborator to task
		p1.addProjectCollaboratorToProjectTeam(projectCollaborator2);
		List<ProjectCollaborator> listOfProjectCollaboratorsFromTask = new ArrayList<>();
		listOfProjectCollaboratorsFromTask.add(projectCollaborator2);

		assertEquals(listOfProjectCollaboratorsFromTask, p1.getProjectCollaboratorsFromTask(task1));

	}

}