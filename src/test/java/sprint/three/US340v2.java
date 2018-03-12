package sprint.three;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project.model.*;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;

/**
 * US340 v2 - Como Gestor de projeto quero poder criar uma tarefa,
 *  incluindo eventualmente as datas previstas de início e fim em 
 *  relação à data de início do projeto.
 *  
 * @author Group 3
 *
 */
public class US340v2 {


	UserContainer myProjCont;
	ProjectContainer myProjRep;
	User user1, user2;
	Project myProject;
	Task testTask, testTask2, testTask3;
	ProjectCollaborator collab1, collab2, collab3;
	Calendar estimatedTaskStartDate, taskDeadline;
	TaskCollaborator tCollab1, tCollab2, tWorker3;
	double expectedCost;

	@Before
	public void setUp() {

		//create project container
		myProjRep = new ProjectContainer();
		// create users
		user1 = new User("pepe", "huehue@mail.com", "66", "debugger", "1234567");
		user2 = new User("doge", "suchmail@mail.com", "666", "debugger", "1234567");
		//create project
		myProject = new Project(1, "Projecto 1", "Projecto Abcd", user1);
		//add project to project repository
		myProjRep.addProjectToProjectContainer(myProject);
		// add cost per effort in users, creating Project Collaborators for each one
		collab1 = myProject.createProjectCollaborator(user1, 5);
		collab2 = myProject.createProjectCollaborator(user2, 5);

		// create dates for task
		estimatedTaskStartDate = Calendar.getInstance();
		estimatedTaskStartDate.add(Calendar.MONTH, -1);
		taskDeadline = Calendar.getInstance();
		taskDeadline.add(Calendar.MONTH, 1);
		// create a new start date for project, (same of the startdate of task)
		Calendar projStartDate = (Calendar) estimatedTaskStartDate.clone();
		myProject.setStartdate(projStartDate);
		// create tasks with the dates previously prepared
		testTask = new Task(1, 1, "Task 1", 1, estimatedTaskStartDate, taskDeadline, 0);
		testTask.setProject(myProject);
		testTask2 = new Task(2, 1, "Task 1", 1, estimatedTaskStartDate, taskDeadline, 0);
        testTask2.setProject(myProject);
        testTask3 = new Task(3, 3, "Task Hue", 1, estimatedTaskStartDate, taskDeadline, 0);
        testTask3.setProject(myProject);
        // add user to task
		testTask.addProjectCollaboratorToTask(collab1);

	}

	@After
	public void tearDown() {

		myProjCont=null;
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
		tCollab1 = null;
		tCollab2 = null;
		expectedCost = 0;

	}

	/**
	 * This test defines an interval between the Task start date and 
	 * the Project start date.
	 * Checks if that interval is the same that was introduced.
	 * 
	 */
	@Test
	public void testGetStartDateInterval() {

		testTask.setStartDateInterval(10);
		assertEquals(10, (int) testTask.getStartDateInterval());
	}
	
	/**
	 * Checks if the Task start date is the same that was introduced in
	 * the beginning.
	 */
	@Test
	public void testGetEstimatedStartDateWithoutInterval() {
		assertEquals(estimatedTaskStartDate, testTask.getEstimatedTaskStartDate());
		
	}
	/**
	 * This test defines an interval between the start date of the project and
	 * the task estimated start date.
	 * Then changes the start date of Project.
	 * Checks if the Task start date was also changed.
	 */
	@Test
	public void testGetEstimatedStartDateWithInterval() {

		// adds interval of time between the start date of the project and the task
		// estimated start date.
		testTask.setStartDateInterval(10);

		// create expected new Task start date after define the interval
		// Start date of project and task were previously before
		// now the task start date is 10 days after the project
		Calendar expectedStartDate = Calendar.getInstance();
        expectedStartDate.add(Calendar.MONTH, -1);
		expectedStartDate.add(Calendar.DAY_OF_YEAR, 10);

		// compares the simulated date to the one that the method returns
		assertEquals(expectedStartDate.get(Calendar.DAY_OF_YEAR),
				testTask.getEstimatedTaskStartDate().get(Calendar.DAY_OF_YEAR));
	}
	

	/**
	 * This test defines an interval between the Task deadline and 
	 * the Project start date.
	 * Checks if that interval is the same that was introduced.
	 */
	@Test
	public void testGetDeadlineInterval() {
		testTask.setDeadlineInterval(15);
		assertEquals(15, (int) testTask.getDeadlineInterval());
	}


	/**
	 * Checks if the task deadline is the same that was introduced in
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

		// simulates the new estimated deadline for the task. It copies the system date and adds 1 month to match the created deadline
        // then adds 15 days
		Calendar expectedDeadline = Calendar.getInstance();
		expectedDeadline.add(Calendar.MONTH, -1);
		expectedDeadline.add(Calendar.DAY_OF_YEAR, 15);

		// compares the simulated date to the one that the method returns
		assertEquals(expectedDeadline.get(Calendar.DAY_OF_YEAR), testTask.getTaskDeadline().get(Calendar.DAY_OF_YEAR));
	}

}
