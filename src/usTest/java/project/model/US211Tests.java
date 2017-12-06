package usTest.java.project.model;

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

class US211Tests {
	/**
	 * Tests US211
	 * 
	 * US211: Como colaborador, eu pretendo obter uma lista das tarefas que concluí
	 * no ultimo mes, ordenadas por ordem temporal decrescente.
	 * 
	 * uses methods addProjectTask, addUserToTask, markTaskAsFinished
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
	void test() {

		assertTrue(company.addUserToUserList(newUserA));
		assertTrue(company.addUserToUserList(newUserB));
		newUserA.setUserProfile(Profile.COLLABORATOR);
		newUserB.setUserProfile(Profile.COLLABORATOR);

		company.addProjectToProjectList(project);

		// Adds User 3 to all tasks and project, adds tasks to project
		project.addUserToProjectTeam(newUserA);
		project.getTaskRepository().addProjectTask(testTask);
		project.getTaskRepository().addProjectTask(testTask2);
		project.getTaskRepository().addProjectTask(testTask3);
		project.getTaskRepository().addProjectTask(testTask4);

		testTask.addUserToTask(newUserA);
		testTask2.addUserToTask(newUserA);
		testTask3.addUserToTask(newUserA);
		testTask4.addUserToTask(newUserA);

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
		finishOverwrite.add(Calendar.MONTH, -1);
		finishOverwrite.set(Calendar.DAY_OF_MONTH, 25); // five days ago
		testTask3.setFinishDate(finishOverwrite);
		finishOverwrite.set(Calendar.DAY_OF_MONTH, 15); // ten days ago
		testTask2.setFinishDate(finishOverwrite);
		finishOverwrite.set(Calendar.DAY_OF_MONTH, 20); // fifteen days ago
		testTask.setFinishDate(finishOverwrite);

		// Creates test List of completed tasks ordered decreasingly
		List<Task> testList = new ArrayList<Task>();
		testList.add(testTask3);
		testList.add(testTask);
		testList.add(testTask2);

		// Compares expected results with TaskList
		// assertEquals(myCompany.getLastMonthFinishedUserTaskListDecreasingOrder(newUser3),
		// testList);
		assertEquals(company.getLastMonthFinishedUserTaskListDecreasingOrder(newUserA), testList);
	}

}