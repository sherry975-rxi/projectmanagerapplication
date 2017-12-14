package test.project.model;

//
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.project.model.EffortUnit;
import main.project.model.Project;
import main.project.model.ProjectCollaborator;
import main.project.model.Task;
import main.project.model.TaskRepository;
import main.project.model.TaskWorker;
import main.project.model.User;

class ProjectTests {

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
	TaskWorker taskWorker1;
	TaskWorker taskWorker2;
	Project p1;
	Project p2;
	TaskRepository taskRepository;

	@BeforeEach
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
		taskWorker1 = new TaskWorker(projectCollaborator1);
		taskWorker2 = new TaskWorker(projectCollaborator2);
		// create project
		p1 = new Project(1, "name3", "description4", user1);
		p2 = new Project(2, "name1", "description4", user2);

		estimatedStartDate = Calendar.getInstance();
		estimatedStartDate.set(2017, Calendar.JANUARY, 14);

		taskDeadline = Calendar.getInstance();
		taskDeadline.set(2017, Calendar.NOVEMBER, 17);

		task1 = p1.getTaskRepository().createTask("description", 0, estimatedStartDate, taskDeadline, 0);
		p1.getTaskRepository().addProjectTask(task1);
		task1.addNewTaskWorker(projectCollaborator2);

		task2 = p1.getTaskRepository().createTask("description2", 0, null, null, 0);
		p1.getTaskRepository().addProjectTask(task2);

		task2.markTaskAsFinished();

		task3 = p1.getTaskRepository().createTask("description3", 0, null, null, 0);
		task3.markTaskAsFinished();

		task4 = p1.getTaskRepository().createTask("description11111", 0, estimatedStartDate, taskDeadline, 0);

	}

	@AfterEach
	void tearDown() {
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
	void testProject() {

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
		assertEquals(tasksListofThisProject, p1.getTaskRepository().getProjectTaskList());
		assertEquals(projectTeam, p1.getProjectTeam());
	}

	/**
	 * Tests the Creator of Project Collaborator that instantiates the object
	 * Project Collaborator
	 */
	@Test
	void testCreateProjectCollaborator() {

		assertEquals(projectCollaborator2, p1.createProjectCollaborator(user2, 1200));
	}

	/**
	 * Tests addition of task to Project Task list
	 */
	@Test
	void testAddTaskToProjectTaskList() {
		p1.getTaskRepository().addProjectTask(task4);
		assertEquals(task4, p1.getTaskRepository().getProjectTaskList().get(2));
	}

	/**
	 * This test checks the project manager of Project 1
	 */
	@Test
	void testCheckProjectManagerTrue() {
		assertTrue(p1.isProjectManager(user1));
	}

	/**
	 * This test checks the project manager of Project 1
	 */
	@Test
	void testCheckProjectManagerFalse() {
		assertFalse(p1.isProjectManager(user2));
	}

	/**
	 * This test changes project manager of Project 1 and checks if this change
	 * occurred.
	 */
	@Test
	void testSetProjectManagerTrue() {

		p1.setProjectManager(user2);
		assertTrue(p1.isProjectManager(user2));
	}

	/**
	 * This test changes project manager of Project 1 and then checks if the
	 * previous Project Manager is no longer in the project manager.
	 */
	@Test
	void testSetProjectManagerFalse() {
		p1.setProjectManager(user2);
		assertFalse(p1.isProjectManager(user1));
	}

	/**
	 * This test changes project manager of Project 1 and then checks if the
	 * previous Project Manager is no longer in the project manager.
	 */
	@Test
	void testAddUserToProjectTeam() {
		p1.addUserToProjectTeam(projectCollaborator2);
		assertEquals(user2, p1.getProjectTeam().get(0).getCollaboratorUserData());
	}

	/**
	 * Tests that it is impossible to add the same object twice to the ProjectTeam
	 */
	@Test
	void testTryToAddTheSameUserTwiceToProjectTeam() {
		p1.addUserToProjectTeam(projectCollaborator1);
		p1.addUserToProjectTeam(projectCollaborator1);
		assertEquals(1, p1.getProjectTeam().size());
	}

	/**
	 * Tests the comparison between objects that are different
	 */
	@Test
	void testEqualsFalse() {
		assertFalse(p1.equals(p2));
	}

//	/**
//	 * Tests the comparison between objects that are different and from different
//	 * types
//	 */
//	@Test
//	void testEqualsDifferentObject() {
//		assertFalse(p1.equals(user1));
//	}

	/**
	 * Sets the status of a project to Execution
	 */
	@Test
	void testProjectState() {
		p1.setProjectStatus(Project.EXECUTION);
		assertEquals(Project.EXECUTION, p1.getProjectStatus());
	}

	/**
	 * Tests the return of the unfinished tasks only of a user in a project.
	 */
	@Test
	void testGetUnfinishedTasks() {
		task1.addUserToTask(taskWorker1);
		assertEquals(task1, p1.getTaskRepository().getUnFinishedTasksFromUser(projectCollaborator1).get(0));
	}

	/**
	 * Tests the return of the finished tasks only of a user in a project. The same
	 * task worker can be associated with different tasks.
	 */
	@Test
	void testGetFinishedTasks() {

		task2.addUserToTask(taskWorker1);
		assertEquals(task2, p1.getTaskRepository().getFinishedTaskListofUserInProject(projectCollaborator1).get(0));
	}

	@Test
	void testGetAllTasks() {// The order of tasks in the test list changes because the getAllTasks method
							// shows the finished tasks first.
		task1.addUserToTask(taskWorker1);
		task2.addUserToTask(taskWorker1);
		List<Task> test = new ArrayList<Task>();
		test.add(task1);
		test.add(task2);
		assertEquals(test, p1.getTaskRepository().getAllTasks(projectCollaborator1));
	}

	/**
	 * 
	 */
	@Test
	void testGetFinishedTaskListLastMonth() {
		Calendar test = Calendar.getInstance();
		test.add(Calendar.MONTH, -1);
		task1.addUserToTask(taskWorker1);
		task2.addUserToTask(taskWorker1);
		task3.addUserToTask(taskWorker1);
		p1.getTaskRepository().addProjectTask(task1);
		p1.getTaskRepository().addProjectTask(task2);
		p1.getTaskRepository().addProjectTask(task3);
		task2.setFinishDate();
		task3.setFinishDate();
		task2.getFinishDate().set(Calendar.MONTH, test.get(Calendar.MONTH) - 1);
		task3.getFinishDate().set(Calendar.MONTH, test.get(Calendar.MONTH));
		assertEquals(task3, p1.getTaskRepository().getFinishedTasksGivenMonth(projectCollaborator1, 1).get(0));
	}

	@Test
	void testAddUserToTask() {
		p1.getTaskRepository().addProjectTask(task1);
		p1.getTaskRepository().addProjectTask(task2);
		p1.getTaskRepository().addProjectTask(task3);
		task2.addUserToTask(taskWorker1);
		assertTrue(task2.taskTeamContainsUser(projectCollaborator1));
	}

	@Test
	void testFailToAddUserToTask() {
		p1.getTaskRepository().addProjectTask(task1);
		p1.getTaskRepository().addProjectTask(task2);
		p1.getTaskRepository().addProjectTask(task3);
		task2.addUserToTask(taskWorker1);
		assertFalse(task1.taskTeamContainsUser(projectCollaborator1));
	}

	@Test
	void testProjectContainsTask() {
		assertTrue(p1.getTaskRepository().containsTask(task2));
	}

	@Test
	void testProjectContainsTaskFalse() {
		assertFalse(p1.getTaskRepository().containsTask(task3));
	}

	/**
	 * This method allows removing a Project Collaborator from a Project Team and
	 * includes removing that Project Collaborator from all Tasks in this Project
	 * 
	 * projectCollaborator1 is removed from ProjectTeam
	 */
	@Test
	void testRemoveCollaboratorFromProjectTeam() {

		p1.addUserToProjectTeam(projectCollaborator2);
		p1.addUserToProjectTeam(projectCollaborator1);
		task1.addUserToTask(taskWorker2);
		task1.addUserToTask(taskWorker1);

		p1.removeCollaboratorFromProjectTeam(user2);

		assertEquals(2, p1.getProjectTeam().size());
		assertTrue(projectCollaborator2.equals(p1.getProjectTeam().get(0)));
		assertTrue(p1.getTaskRepository().getAllTasks(projectCollaborator1).get(0).getTaskTeam().get(1).isTaskWorkerActiveInTask());
		assertFalse(p1.getTaskRepository().getAllTasks(projectCollaborator2).get(0).getTaskTeam().get(0).isTaskWorkerActiveInTask());
	}

	/**
	 * Tests the calculation of the project cost (the sum of the values reported to
	 * the task until the moment)
	 */
	@Test
	void testGetTotalCostReportedToProjectUntilNow() {

		 task1.addUserToTask(taskWorker2);
		 task1.addUserToTask(taskWorker1);
		 
		 
//		 task1.addUserToTask(taskWorker1);
//		 task1.createReport(taskWorker1);
//		 task1.getReports().get(0).setReportedTime(5);
		
		 // The TaskWorker is not finished because it has not a finishDate,
		 // and the current info about the TimeSpent is by default zero.
		 // The following instructions will change that TimeSpent for each TaskWorker
		 
		 task1.createReport(taskWorker2);
		 task1.createReport(taskWorker1);
		 
		 task1.getReports().get(0).setReportedTime(5);
		 task1.getReports().get(1).setReportedTime(7);
//		 task1.getTaskTeam().get(0).setHoursSpent(5);
//		 task1.getTaskTeam().get(1).setHoursSpent(7);
		
		 // The TaskWorker has by default the Cost of the ProjectCollaborator
		 // and now it has also a TimeSpent in each TaskWorker.
		 // So it can be calculated the TaskCost of Task 1, that has the TaskWorker 1
		 //and
		 // 2
		 // and so, get the Project Cost Until Now.
		 // t1.getTaskCost();
		
		 // Task 4 will be added to Project 1 and associated to a new created
		 p1.getTaskRepository().addProjectTask(task4);
		 // t4.createTaskWorker(projectCollaborator1);
		 //task4.addUserToTask(task4.createTaskWorker(projectCollaborator2));
		 task4.addNewTaskWorker(projectCollaborator2);
		 task4.createReport(task4.getTaskTeam().get(0));
		 //task4.getTaskTeam().get(0).setHoursSpent(7);
		 task4.getReports().get(0).setReportedTime(7);
		
		 double espectres_1 = 14400; // cost of Task 1
		 double espectres_2 = 8400; // cost of Task 4
		 double espectres_total = espectres_1 + espectres_2;
		
		 assertEquals(espectres_total, p1.getTotalCostReportedToProjectUntilNow(),
		 0.0001);
	}

	/**
	 * Tests the method getCollaboratorsWithoutTasks that returns a list of
	 * collaborators without task in the project.
	 */
	@Test
	void testgetCollaboratorsWithoutTasks() {

		// add all project colllaborators to Project Team
		p1.addUserToProjectTeam(projectCollaborator2);
		p1.addUserToProjectTeam(projectCollaborator1);
		p1.addUserToProjectTeam(projectCollaborator3);
		p1.addUserToProjectTeam(projectCollaborator4);

		// add task t1 and t2 to Project
		p1.getTaskRepository().addProjectTask(task1);
		p1.getTaskRepository().addProjectTask(task2);

		// add taskWorkers to tasks
		task1.addNewTaskWorker(projectCollaborator2);
		task2.addNewTaskWorker(projectCollaborator1);
		task1.addUserToTask(taskWorker1);
		task2.addUserToTask(taskWorker2);

		// create list to compare
		List<ProjectCollaborator> collaboratorsWithoutTasksCompare = new ArrayList<ProjectCollaborator>();
		collaboratorsWithoutTasksCompare.add(projectCollaborator3);
		collaboratorsWithoutTasksCompare.add(projectCollaborator4);

		assertEquals(collaboratorsWithoutTasksCompare, p1.getCollaboratorsWithoutTasks());

	}
}