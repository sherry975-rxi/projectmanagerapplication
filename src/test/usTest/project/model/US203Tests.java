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

class US203Tests {

	/**
	 * Tests US203
	 * 
	 * US203: Como colaborador, eu pretendo consultar a minha lista de tarefas
	 * pendentes de modo a saber o que tenho para fazer hoje.
	 * 
	 * uses methods addProjectTask, addUserToTask, markTaskAsFinished,
	 * setFinishDate, getUnfinishedTaskList()
	 * 
	 * 
	 */

	/**
	 * This creates two users, three tasks and a project, and tests if the User can
	 * see all unfinished tasks tagged with his name, then compares it against a
	 * test Task List; Finally, attempts to mark one task as completed and verify it
	 * no longer appears in Unfinished Task List
	 * 
	 */

	Company myCompany;
	User user2;
	User user3;
	ProjectCollaborator projCollab1;
	ProjectCollaborator projCollab2;
	ProjectCollaborator projCollab3;
	TaskWorker taskWorker1;
	TaskWorker taskWorker2;
	Project project1;
	Task task1;
	Task task2;
	Task task3;

	@BeforeEach
	void setUp() {

		myCompany = Company.getTheInstance();

		user2 = myCompany.getUsersRepository().createUser("João", "user2@gmail.com", "001", "Manager", "930000000",
				"rua doutor antónio", "7689-654", "porto", "porto", "portugal");
		user3 = myCompany.getUsersRepository().createUser("Juni", "user3@gmail.com", "002", "Code Monkey", "930000000",
				"rua engenheiro joão", "789-654", "porto", "porto", "portugal");

		project1 = myCompany.getProjectsRepository().createProject("name3", "description4", user2);

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

		task1 = project1.getTaskRepository().createTask("Test dis agen pls", 10, estimatedTaskStartDateTest,
				taskDeadlineDateTest, 10);
		task2 = project1.getTaskRepository().createTask("Test dis agen pls", 10, estimatedTaskStartDateTest,
				taskDeadlineDateTest, 10);
		task3 = project1.getTaskRepository().createTask("Test moar yeh", 10, estimatedTaskStartDateTest,
				taskDeadlineDateTest, 10);

		user2.setUserProfile(Profile.COLLABORATOR);
		user3.setUserProfile(Profile.COLLABORATOR);

		projCollab1 = project1.createProjectCollaborator(user3, 250);

		projCollab2 = project1.createProjectCollaborator(user2, 120);

		projCollab3 = project1.createProjectCollaborator(user2, 200);

		taskWorker1 = task1.createTaskWorker(projCollab1);

		taskWorker2 = task2.createTaskWorker(projCollab2);

		myCompany.getProjectsRepository().addProjectToProjectRepository(project1);

		project1.addUserToProjectTeam(projCollab2);
		project1.getTaskRepository().addProjectTask(task1);
		project1.getTaskRepository().addProjectTask(task2);

		task1.addUserToTask(taskWorker1);
		task2.addUserToTask(taskWorker2);

		// sets testTask as Finished, removes it from the test List, then compares again
		task1.setFinishDate();
		task1.markTaskAsFinished();

	}

	@AfterEach
	void tearDown() {

		myCompany.clear();
		user2 = null;
		user3 = null;
		projCollab1 = null;
		projCollab2 = null;
		projCollab3 = null;
		taskWorker1 = null;
		taskWorker2 = null;
		project1 = null;
		task1 = null;
		task2 = null;
		task3 = null;

	}

	@Test
	void testGetUserTaskList() {

		// List<User> userList = new ArrayList<User>();
		// userList.add(user2);

		// assertEquals(userList, userRepository.addUserToUserRepository(user2));
		// assertEquals(myCompany.getUsersRepository().addUserToUserRepository(user3),
		// userList);

		// Creates testList and compares it to the Unfinished task List
		List<Task> testList = new ArrayList<Task>();
		testList.add(task1);
		testList.add(task2);

		testList.remove(task1);

		assertEquals(myCompany.getProjectsRepository().getUnfinishedUserTaskList(user2), testList);

	}

}
