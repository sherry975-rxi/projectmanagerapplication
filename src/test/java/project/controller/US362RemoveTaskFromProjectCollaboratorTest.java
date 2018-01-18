package project.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.model.Company;
import project.model.Profile;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.Task;
import project.model.TaskCollaborator;
import project.model.TaskRepository;
import project.model.User;

public class US362RemoveTaskFromProjectCollaboratorTest {

	Company myCompany;

	User user1;
	User userAdmin;

	TaskRepository taskRepository;

	TaskCollaborator taskWorker1;

	ProjectCollaborator collab1;

	Project project;
	Project project2;

	Task testTask;
	Task testTask2;
	Task testTask3;

	Calendar estimatedTaskStartDateTest;
	Calendar taskDeadlineDateTest;
	Calendar startDateTest;

	@Before
	public void setUp() {
		// create company
		myCompany = Company.getTheInstance();

		// create user
		user1 = myCompany.getUsersRepository().createUser("Daniel", "daniel@gmail.com", "001", "collaborator",
				"910000000", "Rua", "2401-00", "Test", "Testo", "Testistan");

		// create user admin
		userAdmin = myCompany.getUsersRepository().createUser("João", "joao@gmail.com", "001", "Admin", "920000000",
				"Rua", "2401-00", "Test", "Testo", "Testistan");

		// add user to user list
		myCompany.getUsersRepository().addUserToUserRepository(user1);
		myCompany.getUsersRepository().addUserToUserRepository(userAdmin);

		// Creates one Project
		project = myCompany.getProjectsRepository().createProject("name3", "description4", userAdmin);
		project2 = myCompany.getProjectsRepository().createProject("name1", "description4", userAdmin);

		// add project to project repository
		myCompany.getProjectsRepository().addProjectToProjectRepository(project);
		myCompany.getProjectsRepository().addProjectToProjectRepository(project2);

		// create project collaborators
		collab1 = new ProjectCollaborator(user1, 2);

		// create taskRepository

		taskRepository = project.getTaskRepository();

		// create 4 tasks
		testTask = taskRepository.createTask("Test dis agen pls");
		testTask2 = taskRepository.createTask("Test dis agen pls");
		testTask3 = taskRepository.createTask("Test moar yeh");

		// Adds 5 tasks to the TaskRepository
		taskRepository.addProjectTask(testTask);
		taskRepository.addProjectTask(testTask2);
		taskRepository.addProjectTask(testTask3);

		// create task workers
		taskWorker1 = new TaskCollaborator(collab1);

		// set user as collaborator
		user1.setUserProfile(Profile.COLLABORATOR);

		userAdmin.setUserProfile(Profile.COLLABORATOR);

		// add user to project team
		project.addProjectCollaboratorToProjectTeam(collab1);
		project2.addProjectCollaboratorToProjectTeam(collab1);

		// create a estimated Task Start Date
		Calendar startDateTest = Calendar.getInstance();

		// create a estimated Task Start Date
		Calendar estimatedTaskStartDateTest = Calendar.getInstance();
		estimatedTaskStartDateTest.set(Calendar.YEAR, 2017);
		estimatedTaskStartDateTest.set(Calendar.MONTH, Calendar.SEPTEMBER);
		estimatedTaskStartDateTest.set(Calendar.DAY_OF_MONTH, 25);
		estimatedTaskStartDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// create a estimated Task Dead line Date
		Calendar taskDeadlineDateTest = Calendar.getInstance();
		taskDeadlineDateTest.set(Calendar.YEAR, 2018);
		taskDeadlineDateTest.set(Calendar.MONTH, Calendar.JANUARY);
		taskDeadlineDateTest.set(Calendar.DAY_OF_MONTH, 29);
		taskDeadlineDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// create a expired estimated Task Dead line Date
		Calendar taskExpiredDeadlineDateTest = Calendar.getInstance();
		taskExpiredDeadlineDateTest.set(Calendar.YEAR, 2017);
		taskExpiredDeadlineDateTest.set(Calendar.MONTH, Calendar.SEPTEMBER);
		taskExpiredDeadlineDateTest.set(Calendar.DAY_OF_MONTH, 29);
		taskExpiredDeadlineDateTest.set(Calendar.HOUR_OF_DAY, 14);

	}

	@After
	public void tearDown() {
		user1 = null;
		testTask = null;
		testTask2 = null;
		testTask3 = null;
		project = null;
		taskRepository = null;
		taskWorker1 = null;
		collab1 = null;
		estimatedTaskStartDateTest = null;
		taskDeadlineDateTest = null;
		startDateTest = null;
	}

	@Test
	public final void testGetTasksFromAProject() {

		// create controller
		US362RemoveTaskFromProjectCollaborator us362Controller = new US362RemoveTaskFromProjectCollaborator(
				project.getIdCode());

		// create list with cancelled task to compare
		List<Task> taskFromProject = new ArrayList<Task>();

		// add task to the list
		taskFromProject.add(testTask);
		taskFromProject.add(testTask2);
		taskFromProject.add(testTask3);

		assertEquals(taskFromProject, us362Controller.getTasksFromAProject());
	}

	@Test
	public final void testGetTaskCollaboratorsFromTask() {

		// add task collaborator to task
		testTask.addTaskCollaboratorToTask(taskWorker1);

		// create controller
		US362RemoveTaskFromProjectCollaborator us362Controller = new US362RemoveTaskFromProjectCollaborator(
				project.getIdCode());

		// create list with cancelled task to compare
		List<TaskCollaborator> taskCollaborators = new ArrayList<TaskCollaborator>();

		// add task to the list
		taskCollaborators.add(taskWorker1);

		assertEquals(taskCollaborators, us362Controller.getTaskCollaboratorsFromTask(testTask.getTaskID()));
	}

	@Test
	public final void testRemoveCollaboratorFromTask() {

		// add task collaborator to task
		testTask.addTaskCollaboratorToTask(taskWorker1);

		// create controller
		US362RemoveTaskFromProjectCollaborator us362Controller = new US362RemoveTaskFromProjectCollaborator(
				project.getIdCode());

		assertTrue(us362Controller.removeCollaboratorFromTask(taskWorker1, testTask.getTaskID()));

	}

}
