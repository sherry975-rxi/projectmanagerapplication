package test.java.project.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.project.model.Company;
import main.java.project.model.Profile;
import main.java.project.model.Project;
import main.java.project.model.ProjectRepository;
import main.java.project.model.Task;
import main.java.project.model.TaskRepository;
import main.java.project.model.User;

class TaskRepositoryTests {

	Company myCompany;
	User user1;
	User userAdmin;
	Project project;
	ProjectRepository projectRepository;
	TaskRepository taskRepository;
	Task testTask;
	Task testTask2;
	Task testTask3;
	Task testTask4;

	@BeforeEach
	void setUp() {
		// create company
		myCompany = Company.getTheInstance();
		myCompany.getUsersList().clear();
		// create user
		user1 = myCompany.createUser("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000", "Rua", "2401-00",
				"Test", "Testo", "Testistan");
		// create user admin
		userAdmin = myCompany.createUser("João", "joao@gmail.com", "001", "Admin", "920000000", "Rua", "2401-00",
				"Test", "Testo", "Testistan");
		// add user to user list
		myCompany.addUserToUserList(user1);
		myCompany.addUserToUserList(userAdmin);
		// set user as collaborator
		user1.setUserProfile(Profile.COLLABORATOR);
		userAdmin.setUserProfile(Profile.COLLABORATOR);
		// create project
		project = myCompany.getProjectsRepository().createProject("name3", "description4", userAdmin);// !!!
		// create taskRepository
		taskRepository = project.getTaskRepository();
		// create 4 tasks
		testTask = taskRepository.createTask("Test dis agen pls");
		testTask2 = taskRepository.createTask("Test dis agen pls");
		testTask3 = taskRepository.createTask("Test moar yeh");
		testTask4 = taskRepository.createTask("TEST HARDER!");

	}

	@AfterEach
	void tearDown() {
		myCompany = null;
		user1 = null;
		testTask = null;
		testTask2 = null;
		testTask3 = null;
		testTask4 = null;
		project = null;
		projectRepository = null;
		taskRepository = null;
	}

	@Test
	void testTaskRepository() {

	}

	@Test
	void testCreateTask() {
		taskRepository.addProjectTask(testTask);
		taskRepository.addProjectTask(testTask2);
		taskRepository.addProjectTask(testTask3);
		taskRepository.addProjectTask(testTask4);

		List<Task> taskListToCompare = new ArrayList<Task>();
		taskListToCompare.add(testTask);
		taskListToCompare.add(testTask2);
		taskListToCompare.add(testTask3);
		taskListToCompare.add(testTask4);

		assertEquals(taskRepository.getProjectTaskList(), taskListToCompare);

	}

	@Test
	void testAddProjectTasks() {

	}

	@Test
	void testGetProjectTaskList() {

	}

	@Test
	void testGetUnFinishedTasks() {
		// add task to task repository of the project
		taskRepository.addProjectTask(testTask);
		taskRepository.addProjectTask(testTask2);
		taskRepository.addProjectTask(testTask3);
		taskRepository.addProjectTask(testTask4);
		// add de user to the task
		testTask.addUserToTask(user1);
		testTask2.addUserToTask(user1);
		testTask3.addUserToTask(user1);
		testTask4.addUserToTask(user1);

		// create a list and add task to compare to unfinished task list
		List<Task> test = new ArrayList<Task>();
		test.add(testTask);
		test.add(testTask2);
		test.add(testTask3);
		test.add(testTask4);

		// verify if test list is the same as the user unfinished task list
		assertEquals(test, taskRepository.getUnFinishedTasks(user1));
	}

	@Test
	void testFinishedTaskListOfUserInProject() {

	}

	@Test
	void testGetFinishedTaskGivenMonth() {
		// add task to task repository of the project
		taskRepository.addProjectTask(testTask);
		taskRepository.addProjectTask(testTask2);
		taskRepository.addProjectTask(testTask3);
		taskRepository.addProjectTask(testTask4);
		// add de user to the task
		testTask.addUserToTask(user1);
		testTask2.addUserToTask(user1);
		testTask3.addUserToTask(user1);
		testTask4.addUserToTask(user1);
		// create finished date to test
		Calendar finishDateTest = Calendar.getInstance();
		finishDateTest.set(Calendar.YEAR, 2017);
		finishDateTest.set(Calendar.MONTH, Calendar.NOVEMBER);
		finishDateTest.set(Calendar.DAY_OF_MONTH, 29);
		finishDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// finish tasks
		testTask.setFinishDate(finishDateTest);

		// create a list and add finished tasks to compare to finished task list
		List<Task> test = new ArrayList<Task>();
		test.add(testTask);
		test.add(testTask2);
		test.add(testTask3);
		test.add(testTask4);

		// verify if test list is the same as the user unfinished task list
		assertEquals(test, taskRepository.getFinishedTasksGivenMonth(user1, 1));
	}

	@Test
	void testContainsTask() {

	}

	@Test
	void testGetTimeOnLastMonthProjectUserTask() {
		// add task to task repository of the project
		taskRepository.addProjectTask(testTask);
		taskRepository.addProjectTask(testTask2);
		taskRepository.addProjectTask(testTask3);
		taskRepository.addProjectTask(testTask4);
		// add de user to the task
		testTask.addUserToTask(user1);
		testTask2.addUserToTask(user1);
		testTask3.addUserToTask(user1);
		testTask4.addUserToTask(user1);

	}

	@Test
	void testSetTaskCounter() {

	}

	@Test
	void testGetTaskCounter() {

	}

	@Test
	void testProjectId() {

	}

	@Test
	void testAllTasks() {

	}

}
