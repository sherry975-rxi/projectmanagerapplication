package sprint.two;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project.model.*;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;

/**
 * 
 * @author Grupo 3 Test US207
 * 
 *         US207 - As a collaborator, I intend to register / update the time
 *         spent on a task.
 * 
 *         use method getTimeSpentOntask.
 *
 */
public class US207 {

	Company myCompany;
	UserRepository userRepository;
	User user1;
	User userAdmin;
	Project project;
	ProjectRepository projectRepository;
	TaskRepository taskRepository;
	Task testTask;
	ProjectCollaborator projectCollaborator;
	TaskCollaborator taskWorker;
	TaskCollaborator taskWorker1;

	@Before
	public void setUp() {
		// create company
		myCompany = Company.getTheInstance();

		// creates an UserRepository
		userRepository = myCompany.getUsersRepository();

		// creates a ProjectsRepository
		projectRepository = myCompany.getProjectsRepository();

		userRepository.getAllUsersFromRepository().clear();
		// create user
		user1 = userRepository.createUser("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000", "Rua",
				"2401-00", "Test", "Testo", "Testistan");
		// create user admin
		userAdmin = userRepository.createUser("João", "joao@gmail.com", "001", "Admin", "920000000", "Rua", "2401-00",
				"Test", "Testo", "Testistan");
		// add user to user list
		userRepository.addUserToUserRepository(user1);
		userRepository.addUserToUserRepository(userAdmin);

		// set user as collaborator
		user1.setUserProfile(Profile.COLLABORATOR);
		userAdmin.setUserProfile(Profile.COLLABORATOR);

		// create project
		project = projectRepository.createProject("name3", "description4", userAdmin);

		// create project collaborator
		projectCollaborator = project.createProjectCollaborator(user1, 2);

		// add user to project team
		project.addProjectCollaboratorToProjectTeam(projectCollaborator);

		// create taskRepository
		taskRepository = project.getTaskRepository();

	}

	@After
	public void tearDown() {
		Company.clear();
		user1 = null;
		testTask = null;
		project = null;
		projectRepository = null;
		taskRepository = null;
		userRepository = null;
	}

	@Test
	public void test207() {

		// create a estimated Task Start Date
		Calendar estimatedTaskStartDateTest = Calendar.getInstance();
		estimatedTaskStartDateTest.set(Calendar.YEAR, 2017);
		estimatedTaskStartDateTest.set(Calendar.MONTH, Calendar.DECEMBER);
		estimatedTaskStartDateTest.set(Calendar.DAY_OF_MONTH, 29);
		estimatedTaskStartDateTest.set(Calendar.HOUR_OF_DAY, 14);
		// create a estimated Task Start Date
		Calendar taskDeadlineDateTest = Calendar.getInstance();
		taskDeadlineDateTest.set(Calendar.YEAR, 2018);
		taskDeadlineDateTest.set(Calendar.MONTH, Calendar.JANUARY);
		taskDeadlineDateTest.set(Calendar.DAY_OF_MONTH, 29);
		taskDeadlineDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// create 1 task
		testTask = taskRepository.createTask("Test dis agen pls", 10, estimatedTaskStartDateTest, taskDeadlineDateTest,
				10);

		// Adds Tasks to TaskRepository
		taskRepository.addProjectTask(testTask);

		// create task Worker
		taskWorker = testTask.createTaskCollaborator(projectCollaborator);

		// Adds user1 to the Task
		testTask.addTaskCollaboratorToTask(taskWorker);

		// Updates the time spent on task by user
		testTask.createReport(taskWorker);
		testTask.getReports().get(0).setReportedTime(1);

		// Checks if both times are the same
		assertEquals(1.0, testTask.getTimeSpentByProjectCollaboratorOntask(projectCollaborator), 0.001);

		// Updates the time spent on task by user
		testTask.getReports().get(0).setReportedTime(2);

		// Checks if both times are the same

		assertEquals(2.0, testTask.getTimeSpentByProjectCollaboratorOntask(projectCollaborator), 0.001);

	}

}
