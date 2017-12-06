package test.java.project.model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.project.model.Company;
import main.java.project.model.Profile;
import main.java.project.model.Project;
import main.java.project.model.Task;
import main.java.project.model.User;

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
	Task testTask;
	Task testTask2;
	Task testTask3;
	Task testTask4;

	@BeforeEach
	void setUp() {
		company = Company.getTheInstance();
		company.getUsersList().clear();
		company.getProjectsList().clear();

		newUserA = company.createUser("João", "user2@gmail.com", "123", "Maneger", "940000000", "StreetA", "ZipCodeA",
				"CityA", "DistrictA", "CountryA");
		newUserB = company.createUser("Juni", "user3@gmail.com", "132", "Code Monkey", "930000000", "StreetB",
				"ZipCodeB", "CityB", "DistrictB", "CountryB");
		project = company.createProject("name3", "description4", newUserA);
		testTask = project.getTaskRepository().createTask("Test dis pls");
		testTask2 = project.getTaskRepository().createTask("Test dis agen pls");
		testTask3 = project.getTaskRepository().createTask("Test moar yeh");
		testTask4 = project.getTaskRepository().createTask("TEST HARDER!");
	}

	@AfterEach
	void tearDown() {
		Company company = null;
		User newUserA = null;
		User newUserB = null;
		Project project = null;
		Task testTask = null;
		Task testTask2 = null;
		Task testTask3 = null;
		Task testTask4 = null;
	}

	@Test
	void testGetFinishedTasksDecreasingOrder() {

		assertTrue(company.addUserToUserList(newUserA));
		assertTrue(company.addUserToUserList(newUserB));
		newUserA.setUserProfile(Profile.COLLABORATOR);
		newUserB.setUserProfile(Profile.COLLABORATOR);

		company.addProjectToProjectList(project);

		// Adds User 3 to all tasks and project, adds tasks to project
		project.addUserToProjectTeam(newUserB);
		project.getTaskRepository().addProjectTask(testTask);
		project.getTaskRepository().addProjectTask(testTask2);
		project.getTaskRepository().addProjectTask(testTask3);
		project.getTaskRepository().addProjectTask(testTask4);

		testTask.addUserToTask(newUserB);
		testTask2.addUserToTask(newUserB);
		testTask3.addUserToTask(newUserB);
		testTask4.addUserToTask(newUserB);

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
		assertEquals(company.getFinishedTaskListByDecreasingOrder(newUserB), testList);
	}

}
