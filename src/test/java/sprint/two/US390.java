package sprint.two;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.model.Company;
import project.model.Profile;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.ProjectRepository;
import project.model.Task;
import project.model.TaskRepository;
import project.model.TaskWorker;
import project.model.User;
import project.model.UserRepository;

public class US390 {

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

	@Before
	public void setUp() {
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

		// creates 4 Project Collaborators and adds them to the project
		projectUser1 = project.createProjectCollaborator(user1, 10);
		projectUser2 = project.createProjectCollaborator(user2, 20);
		projectUser3 = project.createProjectCollaborator(user3, 5);
		projectUser4 = project.createProjectCollaborator(user4, 3);

		project.addUserToProjectTeam(projectUser1);
		project.addUserToProjectTeam(projectUser2);
		project.addUserToProjectTeam(projectUser3);
		project.addUserToProjectTeam(projectUser4);

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

	@After
	public void tearDown() {

		myCompany.clear();
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
	public void testUS390() {

		// Adds users to the respective tasks
		testTask.addUserToTask(taskWorker1);
		testTask.addUserToTask(taskWorker2);
		testTask2.addUserToTask(taskWorker3);
		testTask2.addUserToTask(taskWorker4);
		// Task worker sets the hours spent on the task
		testTask.createReport(taskWorker1);
		testTask.getReports().get(0).setReportedTime(5);
		testTask.createReport(taskWorker2);
		testTask.getReports().get(0).setReportedTime(10);
		testTask2.createReport(taskWorker3);
		testTask2.getReports().get(0).setReportedTime(2);
		testTask2.createReport(taskWorker4);
		testTask2.getReports().get(0).setReportedTime(3);

		// Calculates the value of the project - Equals to to the sum of the total hours
		// spent times the cost of the TaskWorker

		totalCost += testTask.getReports().get(0).getReportedTime()
				* taskWorker1.getProjectCollaboratorFromTaskWorker().getCollaboratorCost();
		totalCost += testTask.getReports().get(1).getReportedTime()
				* taskWorker2.getProjectCollaboratorFromTaskWorker().getCollaboratorCost();
		totalCost += testTask2.getReports().get(0).getReportedTime()
				* taskWorker3.getProjectCollaboratorFromTaskWorker().getCollaboratorCost();
		totalCost += testTask2.getReports().get(1).getReportedTime()
				* taskWorker4.getProjectCollaboratorFromTaskWorker().getCollaboratorCost();

		// Compares the 2 values
		assertEquals(totalCost, project.getTotalCostReportedToProjectUntilNow(), 0.01);

		// removes two users and adds them again with a different cost
		project.removeCollaboratorFromProjectTeam(user1);
		project.removeCollaboratorFromProjectTeam(user2);
		project.addUserRToProjectTeam(user1, 100);
		project.addUserRToProjectTeam(user2, 200);

		// Confirms that total cost remains unchanged after some collaborators get a
		// raise
		assertEquals(totalCost, project.getTotalCostReportedToProjectUntilNow(), 0.01);

	}

}
