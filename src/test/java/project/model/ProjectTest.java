package project.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project.services.TaskService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import static org.junit.Assert.*;

public class ProjectTest {

	User user1;
	User user2;
	User user3;
	User user4;
	ProjectCollaborator projectCollaborator1;
	ProjectCollaborator projectCollaborator2;
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
	TaskService taskContainer;

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

		// create project
		p1 = new Project("name3", "description4", user1);
		p2 = new Project("name1", "description4", user2);

	}

	@After
	public void tearDown() {
		user1 = null;
		user2 = null;
		user3 = null;
		user4 = null;
		p1 = null;
		p2 = null;
		projectCollaborator2 = null;
		projectCollaborator1 = null;
		projectCollaborator3 = null;
		projectCollaborator4 = null;
	}

	/**
	 * Tests constructor for Project
	 */
	@Test
	public void testProject() {

		p1.setEffortUnit(EffortUnit.HOURS);
		p1.setProjectBudget(100);
		assertEquals(EffortUnit.HOURS, p1.getEffortUnit());
		assertEquals(100, p1.getProjectBudget(), 0.001);
		assertEquals(EffortUnit.HOURS, p1.getEffortUnit());
		assertEquals(0, p1.getProjectStatus());
		assertTrue(p1.isProjectManager(user1));

	}

	/**
	 * Tests the Creator of Project Collaborator that instantiates the object
	 * Project Collaborator
	 */
	@Test
	public void testCreateProjectCollaborator() {

		ProjectCollaborator testingUser2Collab = p1.createProjectCollaborator(user2, 1200);
		assertTrue(testingUser2Collab.isUser(user2));
		assertEquals(p1, testingUser2Collab.getProject());

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
	 * Tests the calculation of the project cost (the sum of the values reported to
	 * the task until the moment)
	 */
//	@Test
//	public void testGetTotalCostReportedToProjectUntilNow() {
//
//		task1.addTaskCollaboratorToTask(taskWorker2);
//		task1.addTaskCollaboratorToTask(taskWorker1);
//
//		// task1.addUserToTask(taskWorker1);
//		// task1.createReport(taskWorker1);
//		// task1.getReports().get(0).setReportedTime(5);
//
//		// The TaskWorker is not finished because it has not a finishDate,
//		// and the current info about the TimeSpent is by default zero.
//		// The following instructions will change that TimeSpent for each TaskWorker
//
//		task1.createReport(taskWorker2, Calendar.getInstance(), 0);
//		task1.createReport(taskWorker1, Calendar.getInstance(), 0);
//
//		task1.getReports().get(0).setReportedTime(5);
//		task1.getReports().get(1).setReportedTime(7);
//		// task1.getTaskTeam().get(0).setHoursSpent(5);
//		// task1.getTaskTeam().get(1).setHoursSpent(7);
//
//		// The TaskWorker has by default the Cost of the ProjectCollaborator
//		// and now it has also a TimeSpent in each TaskWorker.
//		// So it can be calculated the TaskCost of Task 1, that has the TaskWorker 1
//		// and
//		// 2
//		// and so, get the Project Cost Until Now.
//		// t1.getTaskCost();
//
//		// Task 4 will be added to Project 1 and associated to a new created
//		p1.getTaskRepository().addTaskToProject(task4);
//		// t4.createTaskWorker(projectCollaborator1);
//		// task4.addUserToTask(task4.createTaskWorker(projectCollaborator2));
//		task4.addProjectCollaboratorToTask(projectCollaborator2);
//		task4.createReport(task4.getTaskTeam().get(0), Calendar.getInstance(), 0);
//		// task4.getTaskTeam().get(0).setHoursSpent(7);
//		task4.getReports().get(0).setReportedTime(7);
//
//		double espectres_1 = 14400; // cost of Task 1
//		double espectres_2 = 8400; // cost of Task 4
//		double espectres_total = espectres_1 + espectres_2;
//
//		assertEquals(espectres_total, p1.getTotalCostReportedToProjectUntilNow(), 0.0001);
//	}


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
		p3 = new Project("name3", "description4", user1);
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
		p1.setProjectId(1);
		assertTrue(p1.hashCode() == p1.hashCode());
		assertTrue (p1.hashCode() == 31*3 + p1.getIdCode());

		assertFalse(p1.hashCode() == 0);
		int result = 3 * 31 + p1.getIdCode();
		assertEquals(p1.hashCode(), result);

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
		p1.setProjectStatus(99);
		assertEquals("Unknown", p1.getProjectStatusName());

	}

	@Test
	public void testGettersAndSetters(){
		p1.setProjectId(123);
		assertEquals(123, p1.getProjectId());
		
		p1.setProjectId(1);
		assertEquals(1, p1.getProjectId());
		
		Calendar newDate = Calendar.getInstance(); 
		p1.setStartdate(newDate);
		assertEquals(newDate, p1.getStartdate());

		p1.setStatus(1);
		assertEquals(1, p1.getStatus());

		p1.setName("Projecto de desenvolvimento");
		assertEquals("Projecto de desenvolvimento", p1.getName());

		p1.setDescription("Desenvolver código");
		assertEquals("Desenvolver código", p1.getDescription());
		
		p1.setBudget(10);
		assertEquals(10, p1.getBudget(), 0.001); 		

	}
	
	@Test 
	public void testIsProjectActive() { 
		assertTrue(p1.isProjectActive()); 
		p1.setStatus(6);
		assertFalse(p1.isProjectActive()); 
	}
	
	@Test 
	public void createTask() { 
		
		Task task = new Task("Description", p1); 	
		task.setTaskID("1");
		Task taskResult = p1.createTask("Description");
		taskResult.setTaskID("1");
		
		assertTrue(task.equals(taskResult)); 
	}

	/**
	 * Tests the getters and setters for permittedCalculationMethods
	 * as well as the boolean method determining whether or not they're allowed
	 */
	@Test
	public void testSetAndGetPermittedCalculationMethods() {
		// given a new project, the list of allowed methods should contain four entries, matching 1,2,3,4
		assertEquals(3, p1.getAvailableCalculationMethods().size());
		assertEquals((new ArrayList<>(Arrays.asList(1, 2, 3))), p1.getAvailableCalculationMethods());
		assertTrue(p1.isCalculationMethodAllowed(2));
		// when the list of permitted methods is reset as 1,3,4
		p1.setAvailableCalculationMethods((new ArrayList<>(Arrays.asList(1, 3))));
		// then the list must contain 3 entries, and 2 must not be allowed
		assertFalse(p1.isCalculationMethodAllowed(2));
		assertEquals(2, p1.getAvailableCalculationMethods().size());
	}


}