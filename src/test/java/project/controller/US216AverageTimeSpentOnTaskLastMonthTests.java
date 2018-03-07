package project.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class US216AverageTimeSpentOnTaskLastMonthTests {

	/**
	 * Tests US216
	 * 
	 * US216: Como colaborador, eu pretendo obter a média de tempo gasto por tarefa,
	 * nas tarefas que concluí no último mês.
	 * 
	 * uses methods getAverageTimeLastMonthFinishedTasksUser() and
	 * getTimeSpentOnTask();
	 */

	/**
	 * This method returns the average time spent by task during the last month.
	 * This method gets the total time spent on every task finished on last month.
	 * Then it will divide that time by the number of tasks.
	 * 
	 * @return Returns the average time spent by finished task in the last month.
	 * 
	 */

	Company myCompany;
	UserRepository userRepository;
	User user1;
	User user2;
	ProjectCollaborator projectCollaborator1;
	TaskCollaborator taskWorker1;
	ProjectRepository projectRepository;
	Project myProject;
	TaskRepository taskRepository;
	Task task1;
	Task task2;
	Task task3;
	Task task4;

	@Before
	public void setUp() {

		myCompany = Company.getTheInstance();

		user1 = myCompany.getUsersRepository().createUser("Daniel", "daniel@gmail.com", "001", "Programador",
				"910000000", "Rua Azul", "5679-987", "braga", "braga", "portugal");
		user2 = myCompany.getUsersRepository().createUser("Rita", "rita@gmail.com", "002", "Gestora de Projeto",
				"920000000", "rua verde", "6789", "porto", "porto", "portugal");

		// create myProject
		myProject = myCompany.getProjectsRepository().createProject("Projecto I", "Projecto de Gestão", user1);

		// Generate a Start Calendar
		Calendar startDate = Calendar.getInstance();
		startDate.set(Calendar.YEAR, 2017);

		// Generates a Finish Calendar
		Calendar finishDate = Calendar.getInstance();
		finishDate.add(Calendar.MONTH, -2);
		Calendar otherFinishDate = Calendar.getInstance();
		otherFinishDate.add(Calendar.MONTH, -1);

		// Four new tasks were created and added to project1
		task1 = myProject.getTaskRepository().createTask("Task 1", 1, startDate, finishDate, 10);
		task2 = myProject.getTaskRepository().createTask("Task 2", 2, startDate, finishDate, 10);
		task3 = myProject.getTaskRepository().createTask("Task 3", 3, startDate, finishDate, 10);
		task4 = myProject.getTaskRepository().createTask("Task 4", 4, startDate, finishDate, 10);

		// Users 1 and 2 added to the users repository.
		myCompany.getUsersRepository().addUserToUserRepository(user1);
		myCompany.getUsersRepository().addUserToUserRepository(user2);

		// Project 1 added to the project repository.
		myCompany.getProjectsRepository().addProjectToProjectRepository(myProject);

		// create project collaborators
		projectCollaborator1 = myProject.createProjectCollaborator(user1, 10);

		// user2 added user 1 to the ProjectTeam
		myProject.addProjectCollaboratorToProjectTeam(projectCollaborator1);

		// Add Tasks to project 1
		myProject.getTaskRepository().addProjectTask(task1);
		myProject.getTaskRepository().addProjectTask(task2);
		myProject.getTaskRepository().addProjectTask(task3);
		myProject.getTaskRepository().addProjectTask(task4);

		// create task workers
		taskWorker1 = task1.createTaskCollaborator(projectCollaborator1);

		// defines finish date to task, and mark it as Finished
		task1.setEstimatedTaskStartDate(startDate);
		task1.setTaskDeadline(finishDate);
		task1.getTaskState().changeToPlanned();
		task1.addProjectCollaboratorToTask(projectCollaborator1);
		task1.getTaskState().changeToAssigned();
		task1.getTaskState().changeToReady();
		Calendar startDateTask1 = startDate;
		startDateTask1.add(Calendar.DAY_OF_MONTH, 60);
		task1.setStartDate(startDateTask1);
		task1.getTaskState().changeToOnGoing();
		task1.setFinishDate(finishDate);
		task1.getTaskState().changeToFinished();

		task2.setEstimatedTaskStartDate(startDate);
		task2.setTaskDeadline(finishDate);
		task2.getTaskState().changeToPlanned();
		task2.addProjectCollaboratorToTask(projectCollaborator1);
		task2.getTaskState().changeToAssigned();
		task2.getTaskState().changeToReady();
		Calendar startDateTask2 = startDate;
		startDateTask2.add(Calendar.DAY_OF_MONTH, 60);
		task2.setStartDate(startDateTask1);
		task2.getTaskState().changeToOnGoing();
		task2.setFinishDate(otherFinishDate);
		task2.getTaskState().changeToFinished();

		task3.setEstimatedTaskStartDate(startDate);
		task3.setTaskDeadline(finishDate);
		task3.getTaskState().changeToPlanned();
		task3.addProjectCollaboratorToTask(projectCollaborator1);
		task3.getTaskState().changeToAssigned();
		task3.getTaskState().changeToReady();
		Calendar startDateTask3 = startDate;
		startDateTask3.add(Calendar.DAY_OF_MONTH, 60);
		task3.setStartDate(startDateTask1);
		task3.getTaskState().changeToOnGoing();
		task3.setFinishDate(otherFinishDate);
		task3.getTaskState().changeToFinished();

		// List to compare to the getLastMonthFinishedUserTaskList.
		List<Task> expResult = new ArrayList<Task>();
		// expResult.add(t1);
		expResult.add(task2);
		expResult.add(task3);

	}

	@After
	public void tearDown() {
		Company.clear();
		userRepository = null;
		user1 = null;
		user2 = null;
		projectCollaborator1 = null;
		taskWorker1 = null;
		projectRepository = null;
		myProject = null;
		taskRepository = null;
		task1 = null;
		task2 = null;
		task3 = null;
		task4 = null;
	}

	@Test
	public void getAverageTimeLastMonthFinishedTasksUser() {

		US216AverageTimeSpentOnTaskLastMonthController controller = new US216AverageTimeSpentOnTaskLastMonthController();
		task2.addTaskCollaboratorToTask(taskWorker1);
		task3.addTaskCollaboratorToTask(taskWorker1);

		Report report2 = new Report(taskWorker1, LocalDate.now());
		task2.getReports().add(report2);
		task2.updateReportedTime(5, taskWorker1, 0);

		Report report3 = new Report(taskWorker1, LocalDate.now());
		task3.getReports().add(report3);
		task3.updateReportedTime(10, taskWorker1, 0);

		double expectAverageTime = 7.5;

		assertEquals(expectAverageTime, controller.getAverageTimeOfFinishedTasksFromUserLastMonth(user1), 0.000000001);

	}
}
