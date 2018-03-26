package project.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import project.model.*;
import project.model.taskstateinterface.OnGoing;
import project.services.ProjectService;
import project.services.TaskService;
import project.services.UserService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan({"project.model", "project.services", "project.repositories", "project.controller"})
public class US207CreateTaskReportControllerTest {

	@Autowired
	US207CreateTaskReportController controller;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private TaskService taskService;
	
	
	User user1, user2;
	Project project;
	ProjectCollaborator projCollab1, projCollab2;
	Task task1, task2;
	TaskCollaborator taskCollab1, taskCollab2;
	
	//US207CreateTaskReportController controller2;

	
	@Before
	public void setUp() {

		// create users
		user2 = userService.createUser("João", "user2@gmail.com", "001", "Manager", "930025000", "rua doutor antónio",
				"7689-654", "porto", "porto", "portugal");
		user1 = userService.createUser("Juni", "user3@gmail.com", "002", "Code Monkey", "930000000",
				"rua engenheiro joão", "789-654", "porto", "porto", "portugal");

		userService.addUserToUserRepositoryX(user1);
		userService.addUserToUserRepositoryX(user2);

		// change profiles of users from VISITOR (default) to COLLABORATOR
		user2.setUserProfile(Profile.COLLABORATOR);
		user1.setUserProfile(Profile.COLLABORATOR);

		// create project 
		project = projectService.createProject("name3", "description4", user2);

		// add project 1 to ProjectService
		projectService.addProjectToProjectContainer(project);
		
		//create tasks		
		task1 = taskService.createTask("Create tests", project);
		task2 = taskService.createTask("Create UI", project);
		
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
		task1 = taskService.createTask("Do this", project);
		task2 = taskService.createTask("Do that", project);

		// add costPerEffort to users in project 1, resulting in a Project Collaborator
		// for each one
		projCollab1 = projectService.createProjectCollaborator(user1, project, 250);
		projCollab2 = projectService.createProjectCollaborator(user2, project, 120);

		// defines finish date to task, and mark it as Finished7
		task1.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task1.setTaskDeadline(taskDeadlineDateTest1);
		task1.addProjectCollaboratorToTask(projCollab1);
		Calendar startDateTask1 = estimatedTaskStartDateTest;
		startDateTask1.add(Calendar.DAY_OF_MONTH, 60);
		task1.setStartDate(startDateTask1);
		task1.setTaskState(new OnGoing());
		task1.setCurrentState(StateEnum.ONGOING);
		

		task2.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task2.setTaskDeadline(taskDeadlineDateTest1);
		task2.addProjectCollaboratorToTask(projCollab1);
		Calendar startDateTask2 = estimatedTaskStartDateTest;
		startDateTask2.add(Calendar.DAY_OF_MONTH, 60);
		task2.setStartDate(startDateTask1);
		task2.setTaskState(new OnGoing());
		task2.setCurrentState(StateEnum.ONGOING);
		
		taskService.saveTask(task1);
		taskService.saveTask(task2);
		
		controller.setTask(task1);
		
		
		}

	/*
	 * Tests the constructor
	 */
	
	@Test
	public void testUS207CreateTaskReportControllerConstructor() {
		taskCollab1 = task1.createTaskCollaborator(projCollab1);
		task1.addTaskCollaboratorToTask(taskCollab1);

	}

	@Test
	public void testUS207GetEmail() {

		taskCollab1 = task1.createTaskCollaborator(projCollab1);
		task1.addTaskCollaboratorToTask(taskCollab1);

		assertEquals(controller.getTaskCollaboratorByEmail(user1.getEmail()), taskCollab1);

		// Tries to check for an invalid email
		assertEquals(null, controller.getTaskCollaboratorByEmail("invalidEmail"));

	}

	@Test
	public void testUS207CreateReport() {

		taskCollab1 = task1.createTaskCollaborator(projCollab1);
		task1.addTaskCollaboratorToTask(taskCollab1);

		assertTrue(controller.createReportController(20, Calendar.getInstance()));

	}

	@Test
	public void testUS207getReportsIndexByGivenUser() {
		
		controller.setUsername(user1);
		controller.setEmail(user1.getEmail());

		/*
		 * Creates a list of integer, to compare with the list returned by the method
		 */
		List<Integer> reportIndex = new ArrayList<>();

		/*
		 * Reports 0, 1 and 4 belongs to User1
		 */
		controller.createReportController(10, Calendar.getInstance());
		controller.createReportController(20, Calendar.getInstance());
		controller.createReportController(9, Calendar.getInstance());

		reportIndex.add(0);
		reportIndex.add(1);
		reportIndex.add(2);

		assertEquals(reportIndex, controller.getReportsIndexByGivenUser());

	}

	@Test
	public void testUS207UpdateReport() {

		controller.setUsername(user1);
		controller.setEmail(user1.getEmail());
		
		/*
		 * Creates a report
		 */
		controller.createReportController(10, Calendar.getInstance());

		/*
		 * Checks that the report date is 10;
		 */
		assertEquals(controller.getReportTimeByGivenUser(task1.getTaskCollaboratorByEmail(user1.getEmail())).get(0), 10, 0.0);

		/*
		 * Updates TaskReport to 30
		 */
		assertTrue(controller.updateTaskReport(30, task1.getTaskCollaboratorByEmail(user1.getEmail()), 0));

		/*
		 * Checks that the report date is 30;
		 */
		assertEquals(controller.getReportTimeByGivenUser(task1.getTaskCollaboratorByEmail(user1.getEmail())).get(0), 30, 0.0);

	}

	@Test
	public void testUS207getReportsCreationDateByGivenUser() {
		
		controller.setUsername(user1);
		controller.setEmail(user1.getEmail());
		
		Calendar dayOfReport = Calendar.getInstance();
		/*
		 * Creates a report
		 */
		controller.createReportController(10, dayOfReport);

		assertEquals(controller.getReportsCreationDateByGivenUser(task1.getActiveTaskCollaboratorByEmail(user1.getEmail())).get(0), dayOfReport);
	}

	@Test
	public void testUS207getReportUpdateDateByGivenUser() {

		controller.setUsername(user1);
		controller.setEmail(user1.getEmail());
		
		Calendar dayOfReport = Calendar.getInstance();

		/*
		 * Creates a report
		 */
		controller.createReportController(10, dayOfReport);

		controller.updateTaskReport(20, task1.getActiveTaskCollaboratorByEmail(user1.getEmail()), 0);

		assertEquals(controller.getReportsUpdateDateByGivenUser(task1.getActiveTaskCollaboratorByEmail(user1.getEmail())).get(0), dayOfReport);
	}

	@Test
	public void testGettersAndSetters() {
		controller.setUsername(user1);
		assertEquals(user1, controller.getUsername());
		
		controller.setTask(task1);
		assertEquals(task1, controller.getTask());
		
		controller.setEmail("email");
		assertEquals("email", controller.getEmail());	
	}
}
