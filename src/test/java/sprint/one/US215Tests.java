package sprint.one;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project.model.*;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;

public class US215Tests {

	/**
	 * Tests US215
	 * 
	 * US215: Como colaborador, eu pretendo obter o total de tempo gasto nas tarefas
	 * que concluí no último mês.
	 * 
	 * Tests the getTotalTimeLastMonthFinishedTasksByUser method, which returns a
	 * double.
	 * 
	 */

	/**
	 * This method returns the sum of the time spent in all the tasks that were
	 * marked as finished during the last month. So it runs a cycle to get the time
	 * spent on every task finished on last month, and sum one by one.
	 */

	Company myCompany;
	UserRepository userRepository;
	User user1;
	User user2;
	ProjectCollaborator projectCollaborator1;
	ProjectCollaborator projectCollaborator2;
	TaskCollaborator taskWorker1;
	TaskCollaborator taskWorker2;
	ProjectRepository projectRepository;
	Project myProject;
	TaskRepository taskRepository;
	Task task1;
	Task task2;
	Task task3;
	Task task4;

	@Before
	public void setUp() {
		// create company
		myCompany = Company.getTheInstance();

		// create users
		user1 = myCompany.getUsersRepository().createUser("Daniel", "daniel@gmail.com", "001", "Programador",
				"910000000", "Rua Amarela ", "5552-767", "Porto", "Porto", "Portugal");
		user2 = myCompany.getUsersRepository().createUser("Rita", "rita@gmail.com", "002", "Gestora de Projeto",
				"920000000", "Rua Verde", "6789-765", "Matosinhos", "Porto", "Portugal");

		// Adds two users to the users repository.
		myCompany.getUsersRepository().addUserToUserRepository(user1);
		myCompany.getUsersRepository().addUserToUserRepository(user2);

		// create project
		myProject = myCompany.getProjectsRepository().createProject("Projecto I", "Projecto de Gestão", user1);

		// create project collaborators
		projectCollaborator1 = myProject.createProjectCollaborator(user1, 10);
		projectCollaborator2 = myProject.createProjectCollaborator(user2, 20);

		// Add User to ProjectTeam
		myProject.addProjectCollaboratorToProjectTeam(projectCollaborator1);
		myProject.addProjectCollaboratorToProjectTeam(projectCollaborator2);

		// Project 1 added to the project repository.
		myCompany.getProjectsRepository().addProjectToProjectRepository(myProject);

		// create taskRepository
		taskRepository = myProject.getTaskRepository();

		// Generate a Start Calendar (three months ago) and added to tasks start date
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.add(Calendar.MONTH, -3);

		// Generates a Finish Calendar and added to tasks finish date
		// Task t1 was finished more than a month ago, so it doesn't fit in the
		// LastMonthFinishedUserTaskList
		Calendar finishCalendar = Calendar.getInstance();
		finishCalendar.add(Calendar.MONTH, -1);
		Calendar otherCalendar = Calendar.getInstance();
		otherCalendar.add(Calendar.MONTH, -2);

		// create tasks
		task1 = myProject.getTaskRepository().createTask("Task 1", 1, startCalendar, otherCalendar, 10);
		task2 = myProject.getTaskRepository().createTask("Task 2", 2, startCalendar, finishCalendar, 10);
		task3 = myProject.getTaskRepository().createTask("Task 3", 3, startCalendar, finishCalendar, 10);
		task4 = myProject.getTaskRepository().createTask("Task 4", 4, startCalendar, finishCalendar, 10);

		// create task workers
		taskWorker1 = task1.createTaskCollaborator(projectCollaborator1);
		taskWorker2 = task2.createTaskCollaborator(projectCollaborator2);

		// Add Tasks to project
		myProject.getTaskRepository().addProjectTask(task1);
		myProject.getTaskRepository().addProjectTask(task2);
		myProject.getTaskRepository().addProjectTask(task3);
		myProject.getTaskRepository().addProjectTask(task4);

		// Associates users to tasks
		task1.addTaskCollaboratorToTask(taskWorker1);
		task2.addTaskCollaboratorToTask(taskWorker1);
		task3.addTaskCollaboratorToTask(taskWorker1);
		task4.addTaskCollaboratorToTask(taskWorker1);

		// Tasks were marked as Finished
		task1.markTaskAsFinished();
		task2.markTaskAsFinished();
		task3.markTaskAsFinished();

	}

	@After
	public void tearDown() {
		Company.clear();
		userRepository = null;
		user1 = null;
		user2 = null;
		projectCollaborator1 = null;
		projectCollaborator2 = null;
		taskWorker1 = null;
		taskWorker2 = null;
		projectRepository = null;
		myProject = null;
		taskRepository = null;
		task1 = null;
		task2 = null;
		task3 = null;
		task4 = null;
	}

	@Test
	public void testGetTotalTimeLastMonthFinishedTasksByUser() {

		// Sum of total time spend in finished tasks. The tasks finished in the last
		// month were t2 and t3
		double expectTotalTime = task2.getTimeSpentByProjectCollaboratorOntask(projectCollaborator1)
				+ task3.getTimeSpentByProjectCollaboratorOntask(projectCollaborator1);

		assertEquals(expectTotalTime,
				myCompany.getProjectsRepository().getTotalTimeOfFinishedTasksFromUserLastMonth(user1), 0.000000001);

	}

}
