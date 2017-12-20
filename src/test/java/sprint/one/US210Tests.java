package sprint.one;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.controller.GetAllFinishedUserTasksInDecreasingOrderController;
import project.model.Company;
import project.model.Profile;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.ProjectRepository;
import project.model.Task;
import project.model.TaskCollaborator;
import project.model.User;
import project.model.UserRepository;

public class US210Tests {
	/**
	 * Tests US210
	 * 
	 * US210: Como colaborador, eu pretendo obter uma lista das tarefas que concluí,
	 * ordenadas por ordem temporal decrescente.
	 * 
	 * uses methods addProjectTask, addUserToTask, markTaskAsFinished,
	 * getFinishedTaskListByDecreasingOrder
	 * 
	 * 
	 */
	Company company;
	ProjectRepository projRep;
	UserRepository userRep;
	User newUserA;
	User newUserB;
	Project project;
	ProjectCollaborator newCollaboratorB;
	Task testTask;
	Task testTask2;
	Task testTask3;
	Task testTask4;

	@Before
	public void setUp() {
		company = Company.getTheInstance();

		projRep = company.getProjectsRepository();
		userRep = company.getUsersRepository();

		newUserA = userRep.createUser("João", "user2@gmail.com", "123", "Maneger", "940000000", "StreetA", "ZipCodeA",
				"CityA", "DistrictA", "CountryA");
		newUserB = userRep.createUser("Juni", "user3@gmail.com", "132", "Code Monkey", "930000000", "StreetB",
				"ZipCodeB", "CityB", "DistrictB", "CountryB");

		newUserA.setUserProfile(Profile.COLLABORATOR);
		newUserB.setUserProfile(Profile.COLLABORATOR);

		userRep.addUserToUserRepository(newUserA);
		userRep.addUserToUserRepository(newUserB);

		project = projRep.createProject("name3", "description4", newUserA);
		project.getProjectTeam().clear();
		projRep.addProjectToProjectRepository(project);

		// create Collaborator from User and add it to team

		newCollaboratorB = project.createProjectCollaborator(newUserB, 50);

		project.addProjectCollaboratorToProjectTeam(newCollaboratorB);

		// create a estimated Task Start Date
		Calendar estimatedTaskStartDateTest = Calendar.getInstance();

		estimatedTaskStartDateTest.set(Calendar.YEAR, 2017);
		estimatedTaskStartDateTest.set(Calendar.MONTH, Calendar.DECEMBER);
		estimatedTaskStartDateTest.set(Calendar.DAY_OF_MONTH, 29);
		estimatedTaskStartDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// create a estimated Task Deadline
		Calendar taskDeadlineDateTest = Calendar.getInstance();

		taskDeadlineDateTest.set(Calendar.YEAR, 2018);
		taskDeadlineDateTest.set(Calendar.MONTH, Calendar.JANUARY);
		taskDeadlineDateTest.set(Calendar.DAY_OF_MONTH, 29);
		taskDeadlineDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// create four Tasks and add to Repository
		testTask = project.getTaskRepository().createTask("Test dis", 10, estimatedTaskStartDateTest,
				taskDeadlineDateTest, 10);
		testTask2 = project.getTaskRepository().createTask("Test dis agen pls", 10, estimatedTaskStartDateTest,
				taskDeadlineDateTest, 10);

		testTask3 = project.getTaskRepository().createTask("Test moar yeh", 10, estimatedTaskStartDateTest,
				taskDeadlineDateTest, 10);
		testTask4 = project.getTaskRepository().createTask("TEST HARDER!", 10, estimatedTaskStartDateTest,
				taskDeadlineDateTest, 10);

		// Adds all tasks to project,
		project.getTaskRepository().addProjectTask(testTask);
		project.getTaskRepository().addProjectTask(testTask2);
		project.getTaskRepository().addProjectTask(testTask3);
		project.getTaskRepository().addProjectTask(testTask4);

	}

	@After
	public void tearDown() {
		Company.clear();
		newUserA = null;
		newUserB = null;
		project = null;
		newCollaboratorB = null;
		testTask = null;
		testTask2 = null;
		testTask3 = null;
		testTask4 = null;
	}

	@Test
	public void testGetFinishedTasksDecreasingOrder() {

		TaskCollaborator Task1CollaboratorB = testTask.createTaskCollaborator(newCollaboratorB);
		TaskCollaborator Task2CollaboratorB = testTask2.createTaskCollaborator(newCollaboratorB);
		TaskCollaborator Task3CollaboratorB = testTask3.createTaskCollaborator(newCollaboratorB);
		TaskCollaborator Task4CollaboratorB = testTask4.createTaskCollaborator(newCollaboratorB);

		testTask.addTaskCollaboratorToTask(Task1CollaboratorB);
		testTask2.addTaskCollaboratorToTask(Task2CollaboratorB);
		testTask3.addTaskCollaboratorToTask(Task3CollaboratorB);
		testTask4.addTaskCollaboratorToTask(Task4CollaboratorB);

		// Sets all 4 tasks as cleared
		testTask.setFinishDate();
		testTask.markTaskAsFinished();
		testTask2.setFinishDate();
		testTask2.markTaskAsFinished();
		testTask3.setFinishDate();
		testTask3.markTaskAsFinished();
		testTask4.setFinishDate();
		testTask4.markTaskAsFinished();

		// Tasks completed -x days ago (from oldest to most recent completion date)
		Calendar finishOverwrite = Calendar.getInstance();
		finishOverwrite.add(Calendar.DAY_OF_MONTH, -5); // five days before
		testTask.setFinishDate(finishOverwrite);
		finishOverwrite.add(Calendar.DAY_OF_MONTH, -10); // fifteen days before
		testTask2.setFinishDate(finishOverwrite);
		finishOverwrite.add(Calendar.DAY_OF_MONTH, 5); // ten days before
		testTask3.setFinishDate(finishOverwrite);
		finishOverwrite.add(Calendar.DAY_OF_MONTH, -10); // twenty days before
		testTask4.setFinishDate(finishOverwrite);
		// Creates test List of completed tasks ordered decreasingly
		List<Task> testList = new ArrayList<Task>();
		testList.add(testTask);
		testList.add(testTask3);
		testList.add(testTask2);
		testList.add(testTask4);

		// Compares expected results with TaskList
		GetAllFinishedUserTasksInDecreasingOrderController controller = new GetAllFinishedUserTasksInDecreasingOrderController();
		controller.setMyCompany(company);

		assertEquals(testList, controller.getAllFinishedUserTasksInDecreasingOrder(newUserB));
	}

}
