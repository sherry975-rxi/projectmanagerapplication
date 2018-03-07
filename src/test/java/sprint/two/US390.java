package sprint.two;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project.model.*;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;

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
	TaskCollaborator taskWorker1;
	TaskCollaborator taskWorker2;
	TaskCollaborator taskWorker3;
	TaskCollaborator taskWorker4;

	double totalCost;

	@Before
	public void setUp() {
		// create company

		myCompany = Company.getTheInstance();

		// creates an UserRepository
		userRepository = myCompany.getUsersRepository();

		// creates a ProjectsRepository
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

		project.addProjectCollaboratorToProjectTeam(projectUser1);
		project.addProjectCollaboratorToProjectTeam(projectUser2);
		project.addProjectCollaboratorToProjectTeam(projectUser3);
		project.addProjectCollaboratorToProjectTeam(projectUser4);

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
		taskWorker1 = testTask.createTaskCollaborator(projectUser1);
		taskWorker2 = testTask2.createTaskCollaborator(projectUser2);
		taskWorker3 = testTask.createTaskCollaborator(projectUser3);
		taskWorker4 = testTask2.createTaskCollaborator(projectUser4);

		// create variable to calculate total cost reported to project
		totalCost = 0.0;

	}

	@After
	public void tearDown() {

		Company.clear();
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
		testTask.addTaskCollaboratorToTask(taskWorker1);
		testTask.addTaskCollaboratorToTask(taskWorker2);
		testTask2.addTaskCollaboratorToTask(taskWorker3);
		testTask2.addTaskCollaboratorToTask(taskWorker4);
		// Task worker sets the hours spent on the task
		testTask.createReport(taskWorker1, Calendar.getInstance(), 5);
		testTask.getReports().get(0).setReportedTime(5);
		testTask.createReport(taskWorker2, Calendar.getInstance(), 10);
		testTask.getReports().get(0).setReportedTime(10);
		testTask2.createReport(taskWorker3, Calendar.getInstance(), 2);
		testTask2.getReports().get(0).setReportedTime(2);
		testTask2.createReport(taskWorker4, Calendar.getInstance(), 3);
		testTask2.getReports().get(0).setReportedTime(3);

		// Calculates the value of the project - Equals to to the sum of the total hours
		// spent times the cost of the TaskWorker

		totalCost += testTask.getReports().get(0).getReportedTime()
				* taskWorker1.getProjectCollaboratorFromTaskCollaborator().getCollaboratorCost();
		totalCost += testTask.getReports().get(1).getReportedTime()
				* taskWorker2.getProjectCollaboratorFromTaskCollaborator().getCollaboratorCost();
		totalCost += testTask2.getReports().get(0).getReportedTime()
				* taskWorker3.getProjectCollaboratorFromTaskCollaborator().getCollaboratorCost();
		totalCost += testTask2.getReports().get(1).getReportedTime()
				* taskWorker4.getProjectCollaboratorFromTaskCollaborator().getCollaboratorCost();

		// Compares the 2 values
		assertEquals(totalCost, project.getTotalCostReportedToProjectUntilNow(), 0.01);

		// removes two users and adds them again with a different cost
		project.removeProjectCollaboratorFromProjectTeam(user1);
		project.removeProjectCollaboratorFromProjectTeam(user2);
		project.addUserToProjectTeam(user1, 100);
		project.addUserToProjectTeam(user2, 200);

		// Confirms that total cost remains unchanged after some collaborators get a
		// raise
		assertEquals(totalCost, project.getTotalCostReportedToProjectUntilNow(), 0.01);

	}

}
