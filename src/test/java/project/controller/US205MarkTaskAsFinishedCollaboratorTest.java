package project.controller;

import static org.junit.Assert.assertEquals;

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
import project.model.User;
import project.model.taskStateInterface.Assigned;
import project.model.taskStateInterface.OnGoing;
import project.model.taskStateInterface.Planned;
import project.model.taskStateInterface.Ready;

public class US205MarkTaskAsFinishedCollaboratorTest {

	/**
	 * This class tests the methods that are called in Controller to execute the
	 * action of Canceling an OnGoing Task
	 * 
	 * @author Group3
	 *
	 */

	// TasksFiltersController tasksFiltersController;
	Company company1;
	User user1, user2, projectManager, projectManager2;
	Project project1, project2, project3;
	ProjectCollaborator projCollab1, projCollab2;
	Task task1, task2, task3, task4, task5, task6;
	TaskCollaborator taskCollab1, taskCollab2;
	Planned PlannedTestTask;
	Planned PlannedTestTask2;
	Planned PlannedTestTask3;
	Planned PlannedTestTask4;
	Planned PlannedTestTask5;
	Planned PlannedTestTask6;
	Assigned AssignedTestTask;
	Assigned AssignedTestTask2;
	Assigned AssignedTestTask3;
	Assigned AssignedTestTask4;
	Assigned AssignedTestTask5;
	Assigned AssignedTestTask6;
	Ready ReadyTestTask;
	Ready ReadyTestTask2;
	Ready ReadyTestTask3;
	Ready ReadyTestTask4;
	Ready ReadyTestTask5;
	Ready ReadyTestTask6;
	OnGoing onGoingTestTask;
	OnGoing onGoingTestTask2;
	OnGoing onGoingTestTask3;
	OnGoing onGoingTestTask4;
	OnGoing onGoingTestTask5;
	OnGoing onGoingTestTask6;
	Calendar startDateTest;
	Calendar estimatedTaskStartDateTest;
	Calendar taskDeadlineDateTest;
	Calendar taskExpiredDeadlineDateTest;

	@Before
	public void setUp() {

		// create company
		company1 = Company.getTheInstance();

		// create users
		user1 = company1.getUsersRepository().createUser("Joe Smith", "jsmith@gmail.com", "001", "Junior Programmer",
				"930000000", "Rua da Caparica, 19", "7894-654", "Porto", "Porto", "Portugal");
		user2 = company1.getUsersRepository().createUser("John Smith", "johnsmith@gmail.com", "001", "General Manager",
				"930025000", "Rua Doutor Armando", "4455-654", "Rio Tinto", "Gondomar", "Portugal");
		projectManager = company1.getUsersRepository().createUser("Mary MacJohn", "mmacjohn@gmail.com", "003",
				"Product Manager", "930025000", "Rua Terceira, 44", "4455-122", "Leça da Palmeira", "Matosinhos",
				"Portugal");
		projectManager2 = company1.getUsersRepository().createUser("John MacMary", "jmacmary2@gmail.com", "003",
				"Product Manager2", "930025356", "Rua Segunda, 45", "4455-122", "Leça da Palmeira", "Matosinhos",
				"Portugal");

		// add users to company
		company1.getUsersRepository().addUserToUserRepository(user1);
		company1.getUsersRepository().addUserToUserRepository(user2);
		company1.getUsersRepository().addUserToUserRepository(projectManager);
		company1.getUsersRepository().addUserToUserRepository(projectManager2);

		// set user as collaborator
		user1.setUserProfile(Profile.COLLABORATOR);
		user2.setUserProfile(Profile.COLLABORATOR);
		projectManager.setUserProfile(Profile.COLLABORATOR);
		projectManager2.setUserProfile(Profile.COLLABORATOR);

		// create project and establishes collaborator projectManager as project manager
		// of project 1
		project1 = company1.getProjectsRepository().createProject("Project Management software",
				"This software main goals are ....", projectManager);
		project2 = company1.getProjectsRepository().createProject("Project Management software",
				"This software main goals are ....", projectManager2);
		project3 = company1.getProjectsRepository().createProject("Project Management software",
				"This software main goals are ....", projectManager);

		// add project to company
		company1.getProjectsRepository().addProjectToProjectRepository(project1);
		company1.getProjectsRepository().addProjectToProjectRepository(project2);
		company1.getProjectsRepository().addProjectToProjectRepository(project3);

		// create project collaborators
		projCollab1 = new ProjectCollaborator(user1, 2);
		projCollab2 = new ProjectCollaborator(user2, 2);

		// add collaborators to Project
		project1.addProjectCollaboratorToProjectTeam(projCollab1);
		project1.addProjectCollaboratorToProjectTeam(projCollab2);
		project2.addProjectCollaboratorToProjectTeam(projCollab1);
		project2.addProjectCollaboratorToProjectTeam(projCollab2);
		project3.addProjectCollaboratorToProjectTeam(projCollab1);
		project3.addProjectCollaboratorToProjectTeam(projCollab2);

		// create tasks
		task1 = project1.getTaskRepository().createTask("Create class User");
		task2 = project1.getTaskRepository().createTask("Create class User");
		task3 = project2.getTaskRepository().createTask("create test for method set name in class user");
		task4 = project2.getTaskRepository().createTask("Create class User");
		task5 = project3.getTaskRepository().createTask("Create class User");
		task6 = project3.getTaskRepository().createTask("create test for method set name in class user");

		// add tasks to task repository
		project1.getTaskRepository().addProjectTask(task1);
		project1.getTaskRepository().addProjectTask(task2);
		project2.getTaskRepository().addProjectTask(task3);
		project2.getTaskRepository().addProjectTask(task4);
		project3.getTaskRepository().addProjectTask(task5);
		project3.getTaskRepository().addProjectTask(task6);

		// create a estimated Task Start Date
		startDateTest = Calendar.getInstance();

		// create a estimated Task Start Date
		estimatedTaskStartDateTest = Calendar.getInstance();
		estimatedTaskStartDateTest.set(Calendar.YEAR, 2017);
		estimatedTaskStartDateTest.set(Calendar.MONTH, Calendar.SEPTEMBER);
		estimatedTaskStartDateTest.set(Calendar.DAY_OF_MONTH, 25);
		estimatedTaskStartDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// create a estimated Task Dead line Date
		taskDeadlineDateTest = Calendar.getInstance();
		taskDeadlineDateTest.set(Calendar.YEAR, 2018);
		taskDeadlineDateTest.set(Calendar.MONTH, Calendar.JANUARY);
		taskDeadlineDateTest.set(Calendar.DAY_OF_MONTH, 29);
		taskDeadlineDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// create a expired estimated Task Dead line Date
		taskExpiredDeadlineDateTest = Calendar.getInstance();
		taskExpiredDeadlineDateTest.set(Calendar.YEAR, 2017);
		taskExpiredDeadlineDateTest.set(Calendar.MONTH, Calendar.SEPTEMBER);
		taskExpiredDeadlineDateTest.set(Calendar.DAY_OF_MONTH, 29);
		taskExpiredDeadlineDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// Creates State Objects planned for task.
		PlannedTestTask = new Planned(task1);
		PlannedTestTask2 = new Planned(task2);
		PlannedTestTask3 = new Planned(task3);
		PlannedTestTask4 = new Planned(task4);
		PlannedTestTask5 = new Planned(task5);
		PlannedTestTask6 = new Planned(task6);

		// set estimated task start date and task dead line to tasks
		task1.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task1.setStartDate(estimatedTaskStartDateTest);
		task1.setTaskDeadline(taskDeadlineDateTest);

		task2.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task2.setStartDate(estimatedTaskStartDateTest);
		task2.setTaskDeadline(taskDeadlineDateTest);

		task3.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task3.setStartDate(estimatedTaskStartDateTest);
		task3.setTaskDeadline(taskDeadlineDateTest);

		task4.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task4.setStartDate(estimatedTaskStartDateTest);
		task4.setTaskDeadline(taskDeadlineDateTest);

		task5.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task5.setStartDate(estimatedTaskStartDateTest);
		task5.setTaskDeadline(taskDeadlineDateTest);

		task6.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task6.setStartDate(estimatedTaskStartDateTest);
		task6.setTaskDeadline(taskDeadlineDateTest);

		// Sets the tasks to "Planned"
		task1.setTaskState(PlannedTestTask);
		task2.setTaskState(PlannedTestTask2);
		task3.setTaskState(PlannedTestTask3);
		task4.setTaskState(PlannedTestTask4);
		task5.setTaskState(PlannedTestTask5);
		task6.setTaskState(PlannedTestTask6);

		// create task workers
		taskCollab1 = new TaskCollaborator(projCollab1);
		taskCollab2 = new TaskCollaborator(projCollab2);

		// set active user
		task1.addTaskCollaboratorToTask(taskCollab1);
		task2.addTaskCollaboratorToTask(taskCollab2);
		task3.addTaskCollaboratorToTask(taskCollab1);
		task4.addTaskCollaboratorToTask(taskCollab1);
		task5.addTaskCollaboratorToTask(taskCollab2);
		task6.addTaskCollaboratorToTask(taskCollab1);

		// Creates State Objects assigned for task.
		AssignedTestTask = new Assigned(task1);
		AssignedTestTask2 = new Assigned(task2);
		AssignedTestTask3 = new Assigned(task3);
		AssignedTestTask4 = new Assigned(task4);
		AssignedTestTask5 = new Assigned(task5);
		AssignedTestTask6 = new Assigned(task6);

		// Sets the tasks to "Assigned"
		task1.setTaskState(AssignedTestTask);
		task2.setTaskState(AssignedTestTask2);
		task3.setTaskState(AssignedTestTask3);
		task4.setTaskState(AssignedTestTask4);
		task5.setTaskState(AssignedTestTask5);
		task6.setTaskState(AssignedTestTask6);

		// Creates State Objects Ready for task.
		ReadyTestTask = new Ready(task1);
		ReadyTestTask2 = new Ready(task2);
		ReadyTestTask3 = new Ready(task3);
		ReadyTestTask4 = new Ready(task4);
		ReadyTestTask5 = new Ready(task5);
		ReadyTestTask6 = new Ready(task6);

		// Sets the tasks to "Ready"
		task1.setTaskState(ReadyTestTask);
		task2.setTaskState(ReadyTestTask2);
		task3.setTaskState(ReadyTestTask3);
		task4.setTaskState(ReadyTestTask4);
		task5.setTaskState(ReadyTestTask5);
		task6.setTaskState(ReadyTestTask6);

		// Creates State Objects OnGoing for task.
		onGoingTestTask = new OnGoing(task1);
		onGoingTestTask2 = new OnGoing(task2);
		onGoingTestTask3 = new OnGoing(task3);
		onGoingTestTask4 = new OnGoing(task4);
		onGoingTestTask5 = new OnGoing(task5);
		onGoingTestTask6 = new OnGoing(task6);

		// Sets the tasks to "onGoing"
		task1.setTaskState(onGoingTestTask);
		task2.setTaskState(onGoingTestTask2);
		task3.setTaskState(onGoingTestTask3);
		task4.setTaskState(onGoingTestTask4);
		task5.setTaskState(onGoingTestTask5);
		task6.setTaskState(onGoingTestTask6);
	}

	@After
	public void tearDown() {
		Company.clear();
		user1 = null;
		user2 = null;
		projectManager = null;
		projectManager2 = null;
		project1 = null;
		project2 = null;
		project3 = null;
		projCollab1 = null;
		projCollab2 = null;
		task1 = null;
		task2 = null;
		task3 = null;
		task4 = null;
		task5 = null;
		task6 = null;
		taskCollab1 = null;
		taskCollab2 = null;
		PlannedTestTask = null;
		PlannedTestTask2 = null;
		PlannedTestTask3 = null;
		PlannedTestTask4 = null;
		PlannedTestTask5 = null;
		PlannedTestTask6 = null;
		AssignedTestTask = null;
		AssignedTestTask2 = null;
		AssignedTestTask3 = null;
		AssignedTestTask4 = null;
		AssignedTestTask5 = null;
		AssignedTestTask6 = null;
		ReadyTestTask = null;
		ReadyTestTask2 = null;
		ReadyTestTask3 = null;
		ReadyTestTask4 = null;
		ReadyTestTask5 = null;
		ReadyTestTask6 = null;
		onGoingTestTask = null;
		onGoingTestTask2 = null;
		onGoingTestTask3 = null;
		onGoingTestTask4 = null;
		onGoingTestTask5 = null;
		onGoingTestTask6 = null;
		startDateTest = null;
		estimatedTaskStartDateTest = null;
		taskDeadlineDateTest = null;
		taskExpiredDeadlineDateTest = null;
	}

	@Test
	public void testGetProjectsFromProjectCollaborator1() {
		// create controller
		US205MarkTaskAsFinishedCollaborator uS205MarkTaskAsFinishedCollaborator = new US205MarkTaskAsFinishedCollaborator();

		// create list of tasks to compare to taskRepository of project
		List<Project> allProjectsInTest = new ArrayList<>();

		// add task to the list allTasksInTest
		allProjectsInTest.add(project1);
		allProjectsInTest.add(project2);
		allProjectsInTest.add(project3);

		// compares the list of tasks created to compare with the list of tasks in the
		// task repository obtained by using the controller
		assertEquals(allProjectsInTest, uS205MarkTaskAsFinishedCollaborator.getProjectsThatIAmCollaborator(user1));
	}

	@Test
	public void testGetTasksFromProject1Collaborator1() {
		// create controller
		US205MarkTaskAsFinishedCollaborator uS205MarkTaskAsFinishedCollaborator = new US205MarkTaskAsFinishedCollaborator();
		uS205MarkTaskAsFinishedCollaborator.getProjectsThatIAmCollaborator(user1);

		// create list of tasks to compare to taskRepository of project
		List<Task> allTasksInProject1Test = new ArrayList<>();
		List<Task> allTasksInProject1 = uS205MarkTaskAsFinishedCollaborator
				.getUnfinishedTasksOfProjectFromCollaboratorr(0);

		// add task to the list allTasksInTest
		allTasksInProject1Test.add(task1);

		// compares the list of tasks created to compare with the list of tasks in the
		// task repository obtained by using the controller
		assertEquals(allTasksInProject1Test, allTasksInProject1);

	}

	@Test
	public void testSelectTask1FromProject1Manager1Finished() {
		// create controller
		US205MarkTaskAsFinishedCollaborator uS205MarkTaskAsFinishedCollaborator = new US205MarkTaskAsFinishedCollaborator();
		uS205MarkTaskAsFinishedCollaborator.getProjectsThatIAmCollaborator(user1);
		uS205MarkTaskAsFinishedCollaborator.getUnfinishedTasksOfProjectFromCollaboratorr(0);

		Task taskToBeMarked = uS205MarkTaskAsFinishedCollaborator.getTaskToBeMarkedFinished(0);

		assertEquals(task1, taskToBeMarked);
	}

	@Test
	public void testSetTask1FromProject1Manager1Finished() {
		// create controller
		US205MarkTaskAsFinishedCollaborator uS205MarkTaskAsFinishedCollaborator = new US205MarkTaskAsFinishedCollaborator();
		uS205MarkTaskAsFinishedCollaborator.getProjectsThatIAmCollaborator(user1);
		uS205MarkTaskAsFinishedCollaborator.getUnfinishedTasksOfProjectFromCollaboratorr(0);
		uS205MarkTaskAsFinishedCollaborator.getTaskToBeMarkedFinished(0);
		uS205MarkTaskAsFinishedCollaborator.markTaskAsFinished();

		assertEquals("Finished", task1.viewTaskStateName());
	}
}
