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

	User u1;
	User u2;
	ProjectCollaborator projectCollaborator1;
	ProjectCollaborator projectCollaborator2;
	Calendar estimatedStartDate;
	Calendar taskDeadline;
	Task t1;
	Task t2;
	Task t3;
	Task t4;
	TaskWorker taskWorker1;
	TaskWorker taskWorker2;
	Project p1;
	Project p2;
	TaskRepository taskRepository;

	@BeforeEach
	public void setUp() {

		u1 = new User("name", "email", "idNumber", "function", "123456789");
		u2 = new User("name2", "email2", "idNumber2", "function2", "987654321");
		projectCollaborator1 = new ProjectCollaborator(u2, 1200);
		projectCollaborator2 = new ProjectCollaborator(u1, 1200);
		taskWorker1 = new TaskWorker(projectCollaborator2);
		taskWorker2 = new TaskWorker(projectCollaborator1);
		p1 = new Project(1, "name3", "description4", u1);
		estimatedStartDate = Calendar.getInstance();
		estimatedStartDate.set(2017, Calendar.JANUARY, 14);
		taskDeadline = Calendar.getInstance();
		taskDeadline.set(2017, Calendar.NOVEMBER, 17);
		t1 = p1.getTaskRepository().createTask("description", 0, estimatedStartDate, taskDeadline, 0);
		p1.getTaskRepository().addProjectTask(t1);
		t2 = p1.getTaskRepository().createTask("description2", 0, null, null, 0);
		p1.getTaskRepository().addProjectTask(t2);
		t2.markTaskAsFinished();
		p2 = new Project(2, "name1", "description4", u2);
		t3 = p1.getTaskRepository().createTask("description3", 0, null, null, 0);
		t3.markTaskAsFinished();
		t4 = p1.getTaskRepository().createTask("description11111", 0, estimatedStartDate, taskDeadline, 0);

	}

	@AfterEach
	void tearDown() {
		u1 = null;
		u2 = null;
		t1 = null;
		t2 = null;
		t3 = null;
		t4 = null;
		taskWorker1 = null;
		taskWorker2 = null;
		p1 = null;
		p2 = null;
		projectCollaborator1 = null;
		projectCollaborator2 = null;
		taskRepository = null;
	}

	/**
	 * Tests constructor for Project
	 */
	@Test
	void testProject() {

		List<Task> tasksListofThisProject = new ArrayList<Task>();
		tasksListofThisProject.add(t1);
		tasksListofThisProject.add(t2);
		List<ProjectCollaborator> projectTeam = new ArrayList<ProjectCollaborator>();

		assertEquals(EffortUnit.HOURS, p1.getEffortUnit());
		assertEquals(1, p1.getIdCode());
		assertEquals(0, p1.getProjectStatus());
		assertTrue(p1.isProjectManager(u1));
		assertEquals(tasksListofThisProject, p1.getTaskRepository().getProjectTaskList());
		assertEquals(projectTeam, p1.getProjectTeam());
	}

	/**
	 * Tests the Creator of Project Collaborator that instantiates the object
	 * Project Collaborator
	 */
	@Test
	void testCreateProjectCollaborator() {

		assertEquals(projectCollaborator1, p1.createProjectCollaborator(u2, 1200));
	}

	/**
	 * Tests addition of task to Project Task list
	 */
	@Test
	void testAddTaskToProjectTaskList() {
		p1.getTaskRepository().addProjectTask(t4);
		assertEquals(t4, p1.getTaskRepository().getProjectTaskList().get(2));
	}

	/**
	 * This test checks the project manager of Project 1
	 */
	@Test
	void testCheckProjectManagerTrue() {
		assertTrue(p1.isProjectManager(u1));
	}

	/**
	 * This test checks the project manager of Project 1
	 */
	@Test
	void testCheckProjectManagerFalse() {
		assertFalse(p1.isProjectManager(u2));
	}

	/**
	 * This test changes project manager of Project 1 and checks if this change
	 * occurred.
	 */
	@Test
	void testSetProjectManagerTrue() {

		p1.setProjectManager(u2);
		assertTrue(p1.isProjectManager(u2));
	}

	/**
	 * This test changes project manager of Project 1 and then checks if the
	 * previous Project Manager is no longer in the project manager.
	 */
	@Test
	void testSetProjectManagerFalse() {
		p1.setProjectManager(u2);
		assertFalse(p1.isProjectManager(u1));
	}

	/**
	 * This test changes project manager of Project 1 and then checks if the
	 * previous Project Manager is no longer in the project manager.
	 */
	@Test
	void testAddUserToProjectTeam() {
		p1.addUserToProjectTeam(projectCollaborator1);
		assertEquals(u2, p1.getProjectTeam().get(0).getCollaboratorUserData());
	}

	/**
	 * Tests that it is impossible to add the same object twice to the ProjectTeam
	 */
	@Test
	void testTryToAddTheSameUserTwiceToProjectTeam() {
		p1.addUserToProjectTeam(projectCollaborator2);
		p1.addUserToProjectTeam(projectCollaborator2);
		assertEquals(1, p1.getProjectTeam().size());
	}

	/**
	 * Tests the comparison between objects that are different
	 */
	@Test
	void testEqualsFalse() {
		assertFalse(p1.equals(p2));
	}

	/**
	 * Tests the comparison between objects that are different and from different
	 * types
	 */
	@Test
	void testEqualsDifferentObject() {
		assertFalse(p1.equals(u1));
	}

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
		t1.addUserToTask(taskWorker1);
		assertEquals(t1, p1.getTaskRepository().getUnFinishedTasksFromUser(u1).get(0));
	}

	/**
	 * Tests the return of the finished tasks only of a user in a project. The same
	 * task worker can be associated with different tasks.
	 */
	@Test
	void testGetFinishedTasks() {

		t2.addUserToTask(taskWorker1);
		assertEquals(t2, p1.getTaskRepository().getFinishedTaskListofUserInProject(u1).get(0));
	}

	@Test
	void testGetAllTasks() {// The order of tasks in the test list changes because the getAllTasks method
							// shows the finished tasks first.
		t1.addUserToTask(taskWorker1);
		t2.addUserToTask(taskWorker1);
		List<Task> test = new ArrayList<Task>();
		test.add(t1);
		test.add(t2);
		assertEquals(test, p1.getTaskRepository().getAllTasks(u1));
	}

	/**
	 * 
	 */
	@Test
	void testGetFinishedTaskListLastMonth() {
		Calendar test = Calendar.getInstance();
		test.add(Calendar.MONTH, -1);
		t1.addUserToTask(taskWorker1);
		t2.addUserToTask(taskWorker1);
		t3.addUserToTask(taskWorker1);
		p1.getTaskRepository().addProjectTask(t1);
		p1.getTaskRepository().addProjectTask(t2);
		p1.getTaskRepository().addProjectTask(t3);
		t2.setFinishDate();
		t3.setFinishDate();
		t2.getFinishDate().set(Calendar.MONTH, test.get(Calendar.MONTH) - 1);
		t3.getFinishDate().set(Calendar.MONTH, test.get(Calendar.MONTH));
		assertEquals(t3, p1.getTaskRepository().getFinishedTasksGivenMonth(u1, 1).get(0));
	}

	@Test
	void testAddUserToTask() {
		p1.getTaskRepository().addProjectTask(t1);
		p1.getTaskRepository().addProjectTask(t2);
		p1.getTaskRepository().addProjectTask(t3);
		t2.addUserToTask(taskWorker1);
		assertTrue(t2.taskTeamContainsUser(u1));
	}

	@Test
	void testFailToAddUserToTask() {
		p1.getTaskRepository().addProjectTask(t1);
		p1.getTaskRepository().addProjectTask(t2);
		p1.getTaskRepository().addProjectTask(t3);
		t2.addUserToTask(taskWorker1);
		assertFalse(t1.taskTeamContainsUser(u1));
	}

	@Test
	void testProjectContainsTask() {
		assertTrue(p1.getTaskRepository().containsTask(t2));
	}

	@Test
	void testProjectContainsTaskFalse() {
		assertFalse(p1.getTaskRepository().containsTask(t3));
	}

	/**
	 * This method allows removing a Project Collaborator from a Project Team and
	 * includes removing that Project Collaborator from all Tasks in this Project
	 * 
	 * projectCollaborator1 is removed from ProjectTeam
	 */
	@Test
	void testRemoveCollaboratorFromProjectTeam() {

		p1.addUserToProjectTeam(projectCollaborator1);
		p1.addUserToProjectTeam(projectCollaborator2);
		t1.addUserToTask(taskWorker2);
		t1.addUserToTask(taskWorker1);

		p1.removeCollaboratorFromProjectTeam(projectCollaborator1);

		assertEquals(1, p1.getProjectTeam().size());
		assertFalse(projectCollaborator1.equals(p1.getProjectTeam().get(0)));
		assertFalse(p1.getTaskRepository().getAllTasks(u2).get(0).getTaskTeam().get(0).isTaskWorkerActiveInTask());
	}

	/**
	 * Tests the calculation of the project cost (the sum of the values reported to
	 * the task until the moment)
	 */
	@Test
	void testGetTotalCostReportedToProjectUntilNow() {
		
		t1.addUserToTask(taskWorker2);
		t1.addUserToTask(taskWorker1);
		
		//The TaskWorker is not finished because it has not a finishDate, 
		// and the current info about the TimeSpent is by default zero.
		// The following instructions will change that TimeSpent for each TaskWorker
		t1.getTaskTeam().get(0).setHoursSpent(5);
		t1.getTaskTeam().get(1).setHoursSpent(7);
		
		
		// The TaskWorker has by default the Cost of the ProjectCollaborator
		// and now it has also a TimeSpent in each TaskWorker.
		//So it can be calculated the TaskCost of Task 1, that has the TaskWorker 1 and 2
		// and so, get the Project Cost Until Now.
		//t1.getTaskCost();
		
		// Task 4 will be added to Project 1 and associated to a new created TaskWorker
		p1.getTaskRepository().addProjectTask(t4);
		//t4.createTaskWorker(projectCollaborator1);
		t4.addUserToTask(t4.createTaskWorker(projectCollaborator1));
		t4.getTaskTeam().get(0).setHoursSpent(7);
		
		
		
		double espectres_1 = 14400; // cost of Task 1
		double espectres_2 = 8400; // cost of Task 4
		double espectres_total = espectres_1 + espectres_2;
		
		
		
		assertEquals( espectres_total, p1.getTotalCostReportedToProjectUntilNow(), 0.0001);
		
		
		
		
		


		
		
	}
}