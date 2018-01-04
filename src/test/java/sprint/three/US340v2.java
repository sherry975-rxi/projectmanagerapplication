package sprint.three;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.model.Company;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.ProjectRepository;
import project.model.Task;
import project.model.TaskCollaborator;
import project.model.User;

public class US340v2 {

	Company myCompany;
	ProjectRepository myProjRep;
	User user1, user2;
	Project myProject;
	Task testTask, testTask2, testTask3;
	ProjectCollaborator collab1, collab2, collab3;
	Calendar estimatedTaskStartDate, taskDeadline;
	TaskCollaborator tWorker1, tWorker2, tWorker3;
	double expectedCost;

	@Before
	public void setUp() {

		myCompany = Company.getTheInstance();
		myProjRep = myCompany.getProjectsRepository();

		user1 = new User("pepe", "huehue@mail.com", "66", "debugger", "1234567");
		user2 = new User("doge", "suchmail@mail.com", "666", "debugger", "1234567");
		myProject = new Project(1, "Projecto 1", "Projecto Abcd", user1);

		myProjRep.addProjectToProjectRepository(myProject);

		collab1 = myProject.createProjectCollaborator(user1, 5);
		collab2 = myProject.createProjectCollaborator(user2, 5);

		tWorker1 = new TaskCollaborator(collab1);
		tWorker2 = new TaskCollaborator(collab2);

		estimatedTaskStartDate = Calendar.getInstance();
		estimatedTaskStartDate.add(Calendar.MONTH, -1);
		taskDeadline = Calendar.getInstance();
		taskDeadline.add(Calendar.MONTH, 1);

		Calendar projStartDate = (Calendar) estimatedTaskStartDate.clone();
		myProject.setStartdate(projStartDate);

		testTask = new Task(1, 1, "Task 1", 1, estimatedTaskStartDate, taskDeadline, 0);
		testTask.addProjectCollaboratorToTask(collab1);
		testTask2 = new Task(2, 1, "Task 1", 1, estimatedTaskStartDate, taskDeadline, 0);
		testTask3 = new Task(3, 3, "Task Hue", 1, estimatedTaskStartDate, taskDeadline, 0);

	}

	@After
	public void tearDown() {
		Company.clear();
		myProjRep = null;
		user1 = null;
		user2 = null;
		myProject = null;
		testTask = null;
		testTask2 = null;
		testTask3 = null;
		collab1 = null;
		collab2 = null;
		collab3 = null;
		tWorker1 = null;
		tWorker2 = null;
		expectedCost = 0;

	}

	/**
	 * Checks if the task start date interval is the same that was introduced.
	 * 
	 */
	@Test
	public void testGetStartDateInterval() {

		testTask.setStartDateInterval(10);
		assertEquals(10, (int) testTask.getStartDateInterval());
	}

	/**
	 * This test checks if the estimated star date of task was changed because of
	 * the interval that was introduced between the start date of the project and
	 * the task estimated start date.
	 */
	@Test
	public void testGetEstimatedStartDateWithInterval() {

		// adds interval of time between the start date of the project and the task
		// estimated start date.
		testTask.setStartDateInterval(10);

		// simulates the new estimated start date for the task
		Calendar expectedStartDate = (Calendar) myProject.getStartdate().clone();
		expectedStartDate.add(Calendar.DAY_OF_YEAR, 10);

		// compares the simulated date to the one that the method returns
		assertEquals(expectedStartDate.get(Calendar.DAY_OF_YEAR),
				testTask.getEstimatedTaskStartDate().get(Calendar.DAY_OF_YEAR));
	}
	
	/**
	 * Checks if the estimated task start date is the same that was introduced.
	 */
	@Test
	public void testGetEstimatedStartDateWithoutInterval() {
		assertEquals(estimatedTaskStartDate, testTask.getEstimatedTaskStartDate());
	}

	/**
	 * Checks if the task deadline interval is the same that was introduced.
	 */
	@Test
	public void testGetDeadlineInterval() {
		testTask.setDeadlineInterval(15);
		assertEquals(15, (int) testTask.getDeadlineInterval());
	}

	/**
	 * Checks if the task deadline date is the same that was introduced.
	 */
	@Test
	public void testTaskDeadline() {
		Calendar newTaskDeadline = Calendar.getInstance();
		newTaskDeadline.add(Calendar.DAY_OF_MONTH, 5);
		testTask2.setTaskDeadline(newTaskDeadline);
		assertEquals(newTaskDeadline, testTask2.getTaskDeadline());
	}

	/**
	 * Checks if the estimated task start date is the same that was introduced in
	 * the beginning.
	 */
	@Test
	public void testGetDeadlineWithoutInterval() {
		assertEquals(taskDeadline, testTask.getTaskDeadline());
	}

	/**
	 * This test checks if the estimated deadline date of task was changed because
	 * of the interval that was introduced between the start date of the project and
	 * the task estimated deadline.
	 */
	@Test
	public void testGetDeadlineWithInterval() {

		// adds interval of time between the start date of the project and the task
		// estimated deadline.
		testTask.setDeadlineInterval(15);

		// simulates the new estimated deadline for the task
		Calendar expectedDeadline = (Calendar) myProject.getStartdate().clone();
		expectedDeadline.add(Calendar.DAY_OF_YEAR, 15);

		// compares the simulated date to the one that the method returns
		assertEquals(expectedDeadline.get(Calendar.DAY_OF_YEAR), testTask.getTaskDeadline().get(Calendar.DAY_OF_YEAR));
	}

}
