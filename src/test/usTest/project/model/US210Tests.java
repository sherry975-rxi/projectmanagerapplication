package test.usTest.project.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.project.model.Company;
import main.project.model.Profile;
import main.project.model.Project;
import main.project.model.ProjectCollaborator;
import main.project.model.Task;
import main.project.model.TaskWorker;
import main.project.model.User;

class US210Tests {
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
	User newUserA;
	User newUserB;
	Project project;
	ProjectCollaborator newCollaboratorB;
	Task testTask;
	Task testTask2;
	Task testTask3;
	Task testTask4;

	@BeforeEach
	void setUp() {
		company = Company.getTheInstance();

		newUserA = company.getUsersRepository().createUser("João", "user2@gmail.com", "123", "Maneger", "940000000",
				"StreetA", "ZipCodeA", "CityA", "DistrictA", "CountryA");
		newUserB = company.getUsersRepository().createUser("Juni", "user3@gmail.com", "132", "Code Monkey", "930000000",
				"StreetB", "ZipCodeB", "CityB", "DistrictB", "CountryB");

		newUserA.setUserProfile(Profile.COLLABORATOR);
		newUserB.setUserProfile(Profile.COLLABORATOR);

		company.getUsersRepository().addUserToUserRepository(newUserA);
		company.getUsersRepository().addUserToUserRepository(newUserB);

		project = company.getProjectsRepository().createProject("name3", "description4", newUserA);
		project.getProjectTeam().clear();
		company.getProjectsRepository().addProjectToProjectRepository(project);

		// create Collaborator from User and add it to team

		newCollaboratorB = project.createProjectCollaborator(newUserB, 50);

		project.addUserToProjectTeam(newCollaboratorB);

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

	@AfterEach
	void tearDown() {
		company.clear();
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
	void testGetFinishedTasksDecreasingOrder() {

		TaskWorker Task1CollaboratorB = testTask.createTaskWorker(newCollaboratorB);
		TaskWorker Task2CollaboratorB = testTask2.createTaskWorker(newCollaboratorB);
		TaskWorker Task3CollaboratorB = testTask3.createTaskWorker(newCollaboratorB);
		TaskWorker Task4CollaboratorB = testTask4.createTaskWorker(newCollaboratorB);

		testTask.addUserToTask(Task1CollaboratorB);
		testTask2.addUserToTask(Task2CollaboratorB);
		testTask3.addUserToTask(Task3CollaboratorB);
		testTask4.addUserToTask(Task4CollaboratorB);

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
		assertEquals(company.getProjectsRepository().getFinishedTaskListByDecreasingOrder(newUserB), testList);
	}

}
