package project.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import project.model.*;
import project.model.taskstateinterface.OnGoing;
import project.services.ProjectService;
import project.services.TaskService;
import project.services.UserService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan({ "project.services", "project.model", "project.controllers" })
public class US205MarkTaskAsFinishedCollaboratorTest {

	/**
	 * This class tests the methods that are called in Controller to execute the
	 * action of Canceling an OnGoing Task
	 * 
	 * @author Group3
	 *
	 */

	@Autowired
	UserService userContainer;
	@Autowired
	ProjectService projectContainer;
	@Autowired
	TaskService taskService;

	// TasksFiltersController tasksFiltersController;
	User user1, user2, projectManager, projectManager2;
	Project project1, project2, project3;
	ProjectCollaborator projCollab1, projCollab2, projCollab12, projCollab22, projCollab13, projCollab23;

	Task task1, task2, task3, task4, task5, task6;
	Calendar startDateTest;
	Calendar estimatedTaskStartDateTest;
	Calendar taskDeadlineDateTest;
	Calendar taskExpiredDeadlineDateTest;

	Integer project1ID;
	String task1ID;

	@Autowired
	US205MarkTaskAsFinishedCollaboratorController uS205MarkTaskAsFinishedCollaborator;

	@Before
	public void setUp() {


		// create users
		user1 = userContainer.createUser("Joe Smith", "jsmith@gmail.com", "001", "Junior Programmer", "930000000",
				"Rua da Caparica, 19", "7894-654", "Porto", "Porto", "Portugal");
		user2 = userContainer.createUser("John Smith", "johnsmith@gmail.com", "001", "General Manager", "930025000",
				"Rua Doutor Armando", "4455-654", "Rio Tinto", "Gondomar", "Portugal");
		projectManager = userContainer.createUser("Mary MacJohn", "mmacjohn@gmail.com", "003", "Product Manager",
				"930025000", "Rua Terceira, 44", "4455-122", "Leça da Palmeira", "Matosinhos", "Portugal");
		projectManager2 = userContainer.createUser("John MacMary", "jmacmary2@gmail.com", "003", "Product Manager2",
				"930025356", "Rua Segunda, 45", "4455-122", "Leça da Palmeira", "Matosinhos", "Portugal");

		// set user as collaborator
		user1.setUserProfile(Profile.COLLABORATOR);
		user2.setUserProfile(Profile.COLLABORATOR);
		projectManager.setUserProfile(Profile.COLLABORATOR);
		projectManager2.setUserProfile(Profile.COLLABORATOR);

		// update Database with users
		userContainer.addUserToUserRepositoryX(user1);
		userContainer.addUserToUserRepositoryX(user2);
		userContainer.addUserToUserRepositoryX(projectManager);
		userContainer.addUserToUserRepositoryX(projectManager2);

		// create project and establishes collaborator projectManager as project manager
		// of project 1
		project1 = projectContainer.createProject("Project Management software", "This software main goals are ....",
				projectManager);
		project2 = projectContainer.createProject("Project Management software", "This software main goals are ....",
				projectManager2);
		project3 = projectContainer.createProject("Project Management software", "This software main goals are ....",
				projectManager);

		// add users 1 and 2 to all projects
		projCollab1 = projectContainer.createProjectCollaborator(user1, project1, 2);
		projCollab2 = projectContainer.createProjectCollaborator(user2, project1, 2);
		projCollab12 = projectContainer.createProjectCollaborator(user1, project2, 2);
		projCollab22 = projectContainer.createProjectCollaborator(user2, project2, 2);
		projCollab13 = projectContainer.createProjectCollaborator(user1, project3, 2);
		projCollab23 = projectContainer.createProjectCollaborator(user2, project3, 2);

		// create tasks for all projects
		task1 = taskService.createTask("Create class User", project1);
		task2 = taskService.createTask("Create class User", project1);

		task3 = taskService.createTask("create test for method set name in class user", project2);
		task4 = taskService.createTask("Create class User", project2);

		task5 = taskService.createTask("Create class User", project3);
		task6 = taskService.createTask("create test for method set name in class user", project3);

		// update all projects
		projectContainer.updateProject(project1);
		projectContainer.updateProject(project2);
		projectContainer.updateProject(project3);

		// create a estimated Task Start Date
		startDateTest = Calendar.getInstance();

		// create a estimated Task Start Date
		estimatedTaskStartDateTest = Calendar.getInstance();
		estimatedTaskStartDateTest.add(Calendar.YEAR, -1);
		estimatedTaskStartDateTest.set(Calendar.MONTH, Calendar.SEPTEMBER);
		estimatedTaskStartDateTest.set(Calendar.DAY_OF_MONTH, 25);
		estimatedTaskStartDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// create a estimated Task Dead line Date
		taskDeadlineDateTest = Calendar.getInstance();
		taskDeadlineDateTest.add(Calendar.YEAR, 1);
		taskDeadlineDateTest.set(Calendar.MONTH, Calendar.JANUARY);
		taskDeadlineDateTest.set(Calendar.DAY_OF_MONTH, 29);
		taskDeadlineDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// create a expired estimated Task Dead line Date
		taskExpiredDeadlineDateTest = Calendar.getInstance();
		estimatedTaskStartDateTest.add(Calendar.YEAR, -1);
		taskExpiredDeadlineDateTest.set(Calendar.MONTH, Calendar.SEPTEMBER);
		taskExpiredDeadlineDateTest.set(Calendar.DAY_OF_MONTH, 29);
		taskExpiredDeadlineDateTest.set(Calendar.HOUR_OF_DAY, 14);

		int taskEffortAndBudget = 10;

		// set estimated task start date and task dead line to tasks
		task1.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task1.setTaskBudget(taskEffortAndBudget);
		task1.setEstimatedTaskEffort(taskEffortAndBudget);
		task1.setTaskDeadline(taskDeadlineDateTest);

		task2.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task2.setTaskBudget(taskEffortAndBudget);
		task2.setEstimatedTaskEffort(taskEffortAndBudget);
		task2.setTaskDeadline(taskDeadlineDateTest);

		task3.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task3.setTaskBudget(taskEffortAndBudget);
		task3.setEstimatedTaskEffort(taskEffortAndBudget);
		task3.setTaskDeadline(taskDeadlineDateTest);

		task4.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task4.setTaskBudget(taskEffortAndBudget);
		task4.setEstimatedTaskEffort(taskEffortAndBudget);
		task4.setTaskDeadline(taskDeadlineDateTest);

		task5.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task5.setTaskBudget(taskEffortAndBudget);
		task5.setEstimatedTaskEffort(taskEffortAndBudget);
		task5.setTaskDeadline(taskDeadlineDateTest);

		task6.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task6.setTaskBudget(taskEffortAndBudget);
		task6.setEstimatedTaskEffort(taskEffortAndBudget);
		task6.setTaskDeadline(taskDeadlineDateTest);

		// set active user for all tasks and give them a start date
		task1.addProjectCollaboratorToTask(projCollab1);
		task2.addProjectCollaboratorToTask(projCollab2);

		task3.addProjectCollaboratorToTask(projCollab12);
		task4.addProjectCollaboratorToTask(projCollab12);

		task5.addProjectCollaboratorToTask(projCollab23);
		task6.addProjectCollaboratorToTask(projCollab13);

		task1.setStartDate(estimatedTaskStartDateTest);
		task2.setStartDate(estimatedTaskStartDateTest);
		task3.setStartDate(estimatedTaskStartDateTest);
		task4.setStartDate(estimatedTaskStartDateTest);
		task5.setStartDate(estimatedTaskStartDateTest);
		task6.setStartDate(estimatedTaskStartDateTest);

		// force the task states, ensuring they're all ONGOING
		task1.setTaskState(new OnGoing());
		task2.setTaskState(new OnGoing());
		task3.setTaskState(new OnGoing());
		task4.setTaskState(new OnGoing());
		task5.setTaskState(new OnGoing());
		task6.setTaskState(new OnGoing());

		task1.setCurrentState(StateEnum.ONGOING);
		task2.setCurrentState(StateEnum.ONGOING);
		task3.setCurrentState(StateEnum.ONGOING);
		task4.setCurrentState(StateEnum.ONGOING);
		task5.setCurrentState(StateEnum.ONGOING);
		task6.setCurrentState(StateEnum.ONGOING);

		// update all tasks and project collaborators to database
		taskService.saveTask(task1);
		taskService.saveTask(task2);
		taskService.saveTask(task3);
		taskService.saveTask(task4);
		taskService.saveTask(task5);
		taskService.saveTask(task6);

		projectContainer.updateProjectCollaborator(projCollab1);
		projectContainer.updateProjectCollaborator(projCollab2);
		projectContainer.updateProjectCollaborator(projCollab12);
		projectContainer.updateProjectCollaborator(projCollab13);
		projectContainer.updateProjectCollaborator(projCollab23);

		project1ID = project1.getId();
		task1ID = task1.getTaskID();

	}

	@Test
	public void testGetProjectsFromProjectCollaborator1() {

		// create list of tasks to compare to taskContainer of project
		List<Project> allProjectsInTest = new ArrayList<>();

		// add task to the list allTasksInTest
		allProjectsInTest.add(project1);
		allProjectsInTest.add(project2);
		allProjectsInTest.add(project3);

		// compares the list of tasks created to compare with the list of tasks in the
		// task repository obtained by using the controllers
		assertEquals(allProjectsInTest, uS205MarkTaskAsFinishedCollaborator.getProjectsThatIAmCollaborator(user1));
	}

	@Test
	public void testGetTasksFromProject1Collaborator1() {

		uS205MarkTaskAsFinishedCollaborator.getProjectsThatIAmCollaborator(user1);

		// create list of tasks to compare to taskContainer of project
		List<Task> allTasksInProject1Test = new ArrayList<>();
		List<Task> allTasksInProject1 = uS205MarkTaskAsFinishedCollaborator
				.getUnfinishedTasksOfProjectFromCollaborator(project1ID);

		// add task to the list allTasksInTest
		allTasksInProject1Test.add(task1);

		// compares the list of tasks created to compare with the list of tasks in the
		// task repository obtained by using the controllers
		assertEquals(allTasksInProject1Test, allTasksInProject1);

	}

	@Test
	public void testSelectTask1FromProject1Manager1Finished() {

		uS205MarkTaskAsFinishedCollaborator.getProjectsThatIAmCollaborator(user1);
		uS205MarkTaskAsFinishedCollaborator.getUnfinishedTasksOfProjectFromCollaborator(project1ID);

		Task taskToBeMarked = uS205MarkTaskAsFinishedCollaborator.getTaskToBeMarkedFinished(task1ID);

		assertEquals(task1, taskToBeMarked);
	}

	@Test
	public void testSetTask1FromProject1Manager1Finished() {

		uS205MarkTaskAsFinishedCollaborator.getProjectsThatIAmCollaborator(user1);
		uS205MarkTaskAsFinishedCollaborator.getUnfinishedTasksOfProjectFromCollaborator(project1ID);
		uS205MarkTaskAsFinishedCollaborator.getTaskToBeMarkedFinished(task1ID);
		uS205MarkTaskAsFinishedCollaborator.markTaskAsFinished();

		assertEquals("Finished", task1.viewTaskStateName());
	}
	
	@Test
	public final void testGetters_Setters() {
		uS205MarkTaskAsFinishedCollaborator.setUsername(user1);
		uS205MarkTaskAsFinishedCollaborator.setProjectIndex(1);
		uS205MarkTaskAsFinishedCollaborator.setTaskToBeMarked(task1);
		uS205MarkTaskAsFinishedCollaborator.setUnfinishedTaskFromProject(taskService.getProjectUnFinishedTasks(project1));

		
		assertEquals(task1, taskService.getProjectUnFinishedTasks(project1).get(0));
	}
}
