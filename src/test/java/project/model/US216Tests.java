package test.java.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.project.model.Company;
import main.java.project.model.Project;
import main.java.project.model.Task;
import main.java.project.model.User;

class US216Tests {

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
	User user1;
	User user2;
	Project myProject;
	Task task1;
	Task task2;
	Task task3;
	Task task4;

	@BeforeEach
	void setUp() {
		myCompany = Company.getTheInstance();
		myCompany.getUsersList().clear();

		user1 = myCompany.createUser("Daniel", "daniel@gmail.com", "001", "Programador", "910000000", "Rua Azul",
				"5679-987", "braga", "braga", "portugal");
		user2 = myCompany.createUser("Rita", "rita@gmail.com", "002", "Gestora de Projeto", "920000000", "rua verde",
				"6789", "porto", "porto", "portugal");

		// New project: Project 1
		myProject = myCompany.createProject("Projecto I", "Projecto de Gestão", user1);

		// Four new tasks were created and added to project1
		task1 = myProject.createTask("Task 1");
		task2 = myProject.createTask("Task 2");
		task3 = myProject.createTask("Task 3");
		task4 = myProject.createTask("Task 4");

		// Users 1 and 2 added to the Company user list.
		myCompany.addUserToUserList(user1);
		myCompany.addUserToUserList(user2);

		// Project 1 added to the Company List.
		myCompany.addProjectToProjectList(myProject);

		// user2 added user 1 to the ProjectTeam
		myProject.addUserToProjectTeam(user1);

		// Add Tasks to project 1
		myProject.addProjectTask(task1);
		myProject.addProjectTask(task2);
		myProject.addProjectTask(task3);
		myProject.addProjectTask(task4);

		// Associates users to tasks
		task1.addUserToTask(user1);
		task2.addUserToTask(user1);
		task3.addUserToTask(user1);
		task4.addUserToTask(user1);

		// Generate a Start Calendar
		Calendar startDate = Calendar.getInstance();
		startDate.add(Calendar.MONTH, -3);

		// Start dates were attributed to each task
		task1.setStartDate(startDate);
		task2.setStartDate(startDate);
		task3.setStartDate(startDate);
		task4.setStartDate(startDate);

		// Generates a Finish Calendar
		Calendar finishDate = Calendar.getInstance();
		finishDate.add(Calendar.MONTH, -1);
		Calendar otherFinishDate = Calendar.getInstance();
		otherFinishDate.add(Calendar.MONTH, -2);

		// Finish dates were attributed to each task
		task1.setFinishDate(otherFinishDate);
		task2.setFinishDate(finishDate);
		task3.setFinishDate(finishDate);

		// Tasks were marked as Finished.
		task1.markTaskAsFinished();
		task2.markTaskAsFinished();
		task3.markTaskAsFinished();

		// List to compare to the getLastMonthFinishedUserTaskList.
		List<Task> expResult = new ArrayList<Task>();
		// expResult.add(t1);
		expResult.add(task2);
		expResult.add(task3);

	}

	@Test
	void getAverageTimeLastMonthFinishedTasksUser() {

		// Calculate expected Total time spent in task 2 and task3, by calling the
		// method getTimeSpentOnTask
		double expectTotalTime = (task2.getTimeSpentOnTask() + task3.getTimeSpentOnTask());
		// Calculate expected Average total time spent in task 2 and task3, by dividing
		// the total time spent on these tasks and
		// dividing by the number of tasks (in the case 2 tasks).
		double expectAverageTime = expectTotalTime / 2;

		assertEquals(expectAverageTime, myCompany.getAverageTimeLastMonthFinishedTasksUser(user1), 0.000000001);

	}
}
