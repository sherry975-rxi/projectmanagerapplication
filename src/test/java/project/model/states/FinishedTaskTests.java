package project.model.states;

import static org.junit.Assert.assertTrue;

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
import project.model.taskStateInterface.TaskStateInterface;

public class FinishedTaskTests {
	Company myCompany;
	ProjectRepository myProjRep;
	User user1, user2;
	Project myProject;
	Task testTask, testTask2, testTask3;
	ProjectCollaborator collab1, collab2, collab3;
	Calendar estimatedTaskStartDate, taskDeadline;
	TaskCollaborator tWorker1, tWorker2, tWorker3;
	double expectedCost;
	TaskStateInterface antigo;
	TaskStateInterface novo;

	/**
	 * 
	 */
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

		/*
		 * estimatedTaskStartDate = Calendar.getInstance();
		 * estimatedTaskStartDate.add(Calendar.MONTH, -1); taskDeadline =
		 * Calendar.getInstance(); taskDeadline.add(Calendar.MONTH, 1);
		 */

		/*
		 * Calendar projStartDate = (Calendar) estimatedTaskStartDate.clone();
		 * myProject.setStartdate(projStartDate);
		 */

		testTask = new Task(1, 1, "Tarefa para teste de finish date state");
		testTask.addProjectCollaboratorToTask(collab2);
		/*
		 * testTask2 = new Task(2, 1, "Task 1", 1, estimatedTaskStartDate, taskDeadline,
		 * 0); testTask3 = new Task(3, 3, "Task Hue", 1, estimatedTaskStartDate,
		 * taskDeadline, 0);
		 */

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

	@Test
	public final void test1() {
		antigo = testTask.getTaskState();
		testTask.getTaskState().changeToFinished();
		novo = testTask.getTaskState();
		assertTrue(antigo.equals(novo));
	}

	@Test
	public final void test2() {
		antigo = testTask.getTaskState();
		testTask.setFinishDate();
		testTask.getTaskState().changeToFinished();
		novo = testTask.getTaskState();
		assertTrue(antigo.equals(novo));
	}
}
