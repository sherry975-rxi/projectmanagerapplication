package test.java.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Calendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.project.model.Company;
import main.java.project.model.Project;
import main.java.project.model.Task;
import main.java.project.model.User;

class US215Tests {

	/**
	 * Tests US215
	 * 
	 * US215: Como colaborador, eu pretendo obter o total de tempo gasto nas tarefas
	 * que concluí no último mês.
	 * 
	 * Uses method getTotalTimeLastMonthFinishedTasksByUser().
	 * 
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

		user1 = myCompany.createUser("Daniel", "daniel@gmail.com", "001", "Programador", "910000000", "Rua Amarela ",
				"5552-767", "Porto", "Porto", "Portugal");
		user2 = myCompany.createUser("Rita", "rita@gmail.com", "002", "Gestora de Projeto", "920000000", "Rua Verde",
				"6789-765", "Matosinhos", "Porto", "Portugal");

		myProject = myCompany.createProject("Projecto I", "Projecto de Gestão", user1);

		task1 = myProject.createTask("Task 1");
		task2 = myProject.createTask("Task 2");
		task3 = myProject.createTask("Task 3");
		task4 = myProject.createTask("Task 4");

		/**
		 * Tests the getTotalTimeLastMonthFinishedTasksByUser method, which returns a
		 * double.
		 * 
		 */

		/**
		 * This method returns the sum of the time spent in all the tasks that were
		 * marked as finished during the last month. So it runs a cycle to get the time
		 * spent on every task finished on last month, and sum one by one.
		 */

		// Adds two users to the Company user list.
		myCompany.addUserToUserList(user1);
		myCompany.addUserToUserList(user2);

		// Project 1 added to the Company List.
		myCompany.addProjectToProjectList(myProject);

		// Add User to TeamList
		myProject.addUserToProjectTeam(user1);

		// Add Tasks to project 1
		myProject.addProjectTask(task1);
		myProject.addProjectTask(task2);
		myProject.addProjectTask(task3);
		myProject.addProjectTask(task4);

		// Associates users to task
		task1.addUserToTask(user1);
		task2.addUserToTask(user1);
		task3.addUserToTask(user1);
		task4.addUserToTask(user1);

		// Generate a Start Calendar (three months ago) and added to tasks start date
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.add(Calendar.MONTH, -3);

		task1.setStartDate(startCalendar);
		task2.setStartDate(startCalendar);
		task3.setStartDate(startCalendar);
		task4.setStartDate(startCalendar);

		// Generates a Finish Calendar and added to tasks finish date
		// Task t1 was finished more than a month ago, so it doesn't fit in the
		// LastMonthFinishedUserTaskList
		Calendar finishCalendar = Calendar.getInstance();
		finishCalendar.add(Calendar.MONTH, -1);
		Calendar otherCalendar = Calendar.getInstance();
		otherCalendar.add(Calendar.MONTH, -2);

		task1.setFinishDate(otherCalendar);
		task2.setFinishDate(finishCalendar);
		task3.setFinishDate(finishCalendar);

		// Tasks were marked as Finished
		task1.markTaskAsFinished();
		task2.markTaskAsFinished();
		task3.markTaskAsFinished();

	}

	@Test
	void testGetTotalTimeLastMonthFinishedTasksByUser() {

		// Sum of total time spend in finished tasks. The tasks finished in the last
		// month were t2 and t3
		double expectTotalTime = task2.getTimeSpentOnTask() + task3.getTimeSpentOnTask();

		assertEquals(expectTotalTime, myCompany.getTotalTimeLastMonthFinishedTasksByUser(user1), 0.000000001);

	}

}
