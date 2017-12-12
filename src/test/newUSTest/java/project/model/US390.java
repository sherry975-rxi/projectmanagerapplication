package test.newUSTest.java.project.model;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.project.model.Company;
import main.java.project.model.Profile;
import main.java.project.model.Project;
import main.java.project.model.ProjectCollaborator;
import main.java.project.model.ProjectRepository;
import main.java.project.model.Task;
import main.java.project.model.TaskRepository;
import main.java.project.model.TaskWorker;
import main.java.project.model.User;
import main.java.project.model.UserRepository;

class US390 {

	/**
	 * 
	 * @author Group 3
	 * 
	 *         test to US390
	 * 
	 *         US390 - Como Gestor de projeto quero poder calcular o custo total
	 *         reportado no projeto até ao momento.
	 *
	 */

	Company myCompany;
	UserRepository userRepository;
	User user1;
	User user2;
	User user3;
	User user4;
	User projectManager;
	Project project;
	ProjectCollaborator projectUser1;
	ProjectCollaborator projectUser2;
	ProjectCollaborator projectUser3;
	ProjectCollaborator projectUser4;
	ProjectRepository projectRepository;
	TaskRepository taskRepository;
	Task testTask;
	Task testTask2;
	TaskWorker taskWorker1;
	TaskWorker taskWorker2;
	TaskWorker taskWorker3;
	TaskWorker taskWorker4;

	double totalCost;

	@BeforeEach
	void setUp() {
		// create company
		myCompany = Company.getTheInstance();

		// creates an UserRepository
		userRepository = myCompany.getUsersRepository();

		// creates a ProjectRepository
		projectRepository = myCompany.getProjectsRepository();

		// creates a UserRepository
		userRepository.getAllUsersFromRepository().clear();

		// create user
		user1 = userRepository.createUser("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000", "Rua",
				"2401-00", "Test", "Testo", "Testistan");

		// create user2
		user2 = userRepository.createUser("João", "joao@gmail.com", "001", "Admin", "920000000", "Rua", "2401-00",
				"Test", "Testo", "Testistan");

		// create user3
		user3 = userRepository.createUser("Miguel", "miguel@gmail.com", "001", "Admin", "920000000", "Rua", "2401-00",
				"Test", "Testo", "Testistan");

		// create user4
		user4 = userRepository.createUser("Ana", "ana@gmail.com", "001", "Admin", "920000000", "Rua", "2401-00", "Test",
				"Testo", "Testistan");

		// create projectManager
		projectManager = userRepository.createUser("João", "joao@gmail.com", "001", "Admin", "920000000", "Rua",
				"2401-00", "Test", "Testo", "Testistan");

		// add user to user list
		userRepository.addUserToUserRepository(user1);
		userRepository.addUserToUserRepository(user2);
		userRepository.addUserToUserRepository(user3);
		userRepository.addUserToUserRepository(user4);

		// set user as collaborator
		user1.setUserProfile(Profile.COLLABORATOR);
		user2.setUserProfile(Profile.COLLABORATOR);
		user3.setUserProfile(Profile.COLLABORATOR);
		user4.setUserProfile(Profile.COLLABORATOR);

		// create project
		project = projectRepository.createProject("name3", "description4", projectManager);

		// creates 2 Project Collaborators
		projectUser1 = project.createProjectCollaborator(user1, 10);
		projectUser2 = project.createProjectCollaborator(user2, 20);
		projectUser3 = project.createProjectCollaborator(user3, 5);
		projectUser4 = project.createProjectCollaborator(user4, 3);

		// create a estimated Task Start Date
		Calendar estimatedTaskStartDateTest = Calendar.getInstance();
		estimatedTaskStartDateTest.set(Calendar.YEAR, 2017);
		estimatedTaskStartDateTest.set(Calendar.MONTH, Calendar.DECEMBER);
		estimatedTaskStartDateTest.set(Calendar.DAY_OF_MONTH, 10);
		estimatedTaskStartDateTest.set(Calendar.HOUR_OF_DAY, 14);
		// create a estimated Task Start Date
		Calendar taskDeadlineDateTest = Calendar.getInstance();
		taskDeadlineDateTest.set(Calendar.YEAR, 2017);
		taskDeadlineDateTest.set(Calendar.MONTH, Calendar.DECEMBER);
		taskDeadlineDateTest.set(Calendar.DAY_OF_MONTH, 20);
		taskDeadlineDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// create taskRepository
		taskRepository = project.getTaskRepository();

		testTask = taskRepository.createTask("Test dis agen pls", 10, estimatedTaskStartDateTest, taskDeadlineDateTest,
				10);
		testTask2 = taskRepository.createTask("Test dis agen pls", 10, estimatedTaskStartDateTest, taskDeadlineDateTest,
				10);

		// Adds Tasks to TaskRepository
		taskRepository.addProjectTask(testTask);
		taskRepository.addProjectTask(testTask2);

		// Creates 4 Task Workers
		taskWorker1 = testTask.createTaskWorker(projectUser1);
		taskWorker2 = testTask2.createTaskWorker(projectUser2);
		taskWorker3 = testTask.createTaskWorker(projectUser3);
		taskWorker4 = testTask2.createTaskWorker(projectUser4);

		// create variable to calculate total cost reported to project
		totalCost = 0.0;

	}

	@AfterEach
	void tearDown() {

		myCompany = null;
		user1 = null;
		user2 = null;
		user3 = null;
		user4 = null;
		projectUser1 = null;
		projectUser2 = null;
		projectUser3 = null;
		projectUser4 = null;
		taskWorker1 = null;
		taskWorker2 = null;
		taskWorker3 = null;
		taskWorker4 = null;
		testTask = null;
		testTask2 = null;
		project = null;
		projectRepository = null;
		taskRepository = null;
		userRepository = null;
		totalCost = 0.0;

	}

	@Test
	void testUS390() {

		// Adds users to the respective tasks
		testTask.addUserToTask(taskWorker1);
		testTask.addUserToTask(taskWorker2);
		testTask2.addUserToTask(taskWorker3);
		testTask2.addUserToTask(taskWorker4);
		// Task worker sets the hours spent on the task
		taskWorker1.setHoursSpent(5);
		taskWorker2.setHoursSpent(10);
		taskWorker3.setHoursSpent(2);
		taskWorker4.setHoursSpent(3);

		// Calculates the value of the project - Equals to to the sum of the total hours
		// spent times the cost of the TaskWorker
		totalCost += taskWorker1.getTotalHoursSpent() * taskWorker1.getCost(0);
		totalCost += taskWorker2.getTotalHoursSpent() * taskWorker2.getCost(0);
		totalCost += taskWorker3.getTotalHoursSpent() * taskWorker3.getCost(0);
		totalCost += taskWorker4.getTotalHoursSpent() * taskWorker4.getCost(0);

		// Compares the 2 values
		assertEquals(totalCost, project.getTotalCostReportedToProjectUntilNow(), 0.01);

	}

}
