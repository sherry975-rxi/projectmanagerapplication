package project.controller;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import project.Repository.ProjCollabRepository;
import project.Repository.ProjectsRepository;
import project.Repository.TaskRepository;
import project.Repository.UserRepository;
import project.Services.ProjectService;
import project.Services.TaskService;
import project.Services.UserService;
import project.model.*;
import project.model.taskstateinterface.OnGoing;
import project.model.taskstateinterface.Planned;
import project.model.taskstateinterface.Ready;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class US380GetProjectExpiredTaskListTest {
	@Autowired
	ProjectsRepository projRepo;
	@Autowired
	UserRepository userRepo;
	@Autowired
	TaskRepository taskRepo;
	@Autowired
	ProjCollabRepository projCollabRepo;		
			
	US380GetProjectExpiredTaskListController tasksFiltersController;
	UserService userContainer;
	ProjectService projectContainer;
	TaskService taskContainer;
	User user1, user2, user3;
	Project project1;
	ProjectCollaborator projCollab1, projCollab2, projCollab3;
	Task task1, task2, task3, task4, task5, task6;
	TaskCollaborator taskCollab1, taskCollab2, taskCollab3, taskCollab4, taskCollab5, taskCollab6;

	@Before
	public void setUp() {
		// creates an UserContainer
		userContainer = new UserService();
		userContainer.setUserRepository(userRepo);
								
		// creates a Project Container
		projectContainer = new ProjectService();
		projectContainer.setProjectCollaboratorRepository(projCollabRepo);
		projectContainer.setProjectsRepository(projRepo);

		// creates a Task Container
		taskContainer = new TaskService();
		taskContainer.setTaskRepository(taskRepo);
		taskContainer.setProjectCollaboratorRepository(projCollabRepo);

		// create users in company
		user2 = userContainer.createUser("João", "user2@gmail.com", "001", "Manager", "930025000",
				"rua doutor antónio", "7689-654", "porto", "porto", "portugal");
		user1 = userContainer.createUser("Juni", "user3@gmail.com", "002", "Code Monkey", "930000000",
				"rua engenheiro joão", "789-654", "porto", "porto", "portugal");

		// change profiles of users from VISITOR (default) to COLLABORATOR
		user2.setUserProfile(Profile.COLLABORATOR);
		user1.setUserProfile(Profile.COLLABORATOR);

		// create project 1 in company 1
		project1 = projectContainer.createProject("name3", "description4", user2);

		// add project 1 to company 1
		projectContainer.addProjectToProjectContainer(project1);

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
		task1 = project1.createTask("Do this");
		task1.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task1.setTaskDeadline(taskDeadlineDateTest1);
		task2 = project1.createTask("Do that");
		task2.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task2.setTaskDeadline(taskDeadlineDateTest2);
		task3 = project1.createTask("Merge everything");
		task3.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task3.setTaskDeadline(taskDeadlineDateTest3);
		task4 = project1.createTask("Do this");
		task4.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task4.setTaskDeadline(taskDeadlineDateTest4);
		task5 = project1.createTask("Do this");
		task5.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task5.setTaskDeadline(taskExpiredDeadlineDateTest);
		task6 = project1.createTask("Do this");
		task6.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task6.setTaskDeadline(taskExpiredDeadlineDateTest);

		taskContainer.saveTask(task1);
		taskContainer.saveTask(task2);
		taskContainer.saveTask(task3);
		taskContainer.saveTask(task4);
		taskContainer.saveTask(task5);
		taskContainer.saveTask(task6);

		// add costPerEffort to users in project 1, resulting in a Project Collaborator
		// for each one
		projCollab1 = project1.createProjectCollaborator(user1, 250);
		projCollab2 = project1.createProjectCollaborator(user2, 120);
		projCollab3 = project1.createProjectCollaborator(user2, 200);


		// defines finish date to task, and mark it as Finished7
		task1.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task1.setTaskDeadline(taskDeadlineDateTest1);
		task1.setTaskState(new Planned());
		task1.addProjectCollaboratorToTask(projCollab1);
		task1.setTaskState(new Ready());
		Calendar startDateTask1 = estimatedTaskStartDateTest;
		startDateTask1.add(Calendar.DAY_OF_MONTH, 60);
		task1.setStartDate(startDateTask1);
		task1.setTaskState(new OnGoing());
		task1.markTaskAsFinished();

		// creates the controller
		tasksFiltersController = new US380GetProjectExpiredTaskListController();
		tasksFiltersController.setTaskService(taskContainer);
	}

	@After
	public void tearDown() {
		userContainer = null;
		projectContainer = null;
		taskContainer = null;
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

	@Test
	public final void testGetProjectExpiredTaskList() {

		assertEquals(2, tasksFiltersController.getUnfinishedTaskListWithExpiredDeadline(project1).size());

	}

	@Test
	public final void testSplitStringByFirstSpace() {
		String input = "Test me master!";
		assertTrue("Test".equals(tasksFiltersController.splitStringByFirstSpace(input)));
	}

}
