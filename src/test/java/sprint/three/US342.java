package sprint.three;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.model.Company;
import project.model.Project;
import project.model.Task;
import project.model.User;

/**
 * @author Group 3
 * 
 *         This class tests the US342
 * 
 *         "Como Gestor de projeto, quero poder definir dependências [entre
 *         tarefas] de uma tarefa não iniciada"
 *
 */
public class US342 {

	Company myCompany;
	User user1;
	Project project1;
	Task task1;
	Task task2;

	@Before
	public void setUp() {

		// Creates the company
		myCompany = Company.getTheInstance();

		// Creates a user
		user1 = myCompany.getUsersRepository().createUser("Manel", "user2@gmail.com", "001", "Empregado", "930000000",
				"rua cinzenta", "6789-654", "porto", "porto", "portugal");

		// Creates a project
		project1 = myCompany.getProjectsRepository().createProject("name", "description", user1);

		// Creates the dates to set as estimated start date and task deadline
		Calendar estimatedTaskStartDate = Calendar.getInstance();
		estimatedTaskStartDate.set(2017, Calendar.DECEMBER, 22);
		Calendar taskDeadline = Calendar.getInstance();

		// Creates the tasks
		task1 = project1.getTaskRepository().createTask("description", 10, estimatedTaskStartDate, taskDeadline, 1000);
		task2 = project1.getTaskRepository().createTask("descriptionA", 10, estimatedTaskStartDate, taskDeadline, 1000);

	}

	@After
	public void tearDown() {

		Company.clear();
		user1 = null;
		project1 = null;
		task1 = null;
		task2 = null;

	}

	/**
	 * This test asserts if the estimated start date of the task 2 is equal to the
	 * estimated start date plus 10 days of the task 1.
	 */
	@Test
	public void US342_implementDependencyInTask() {

		// set testTask estimated start date
		Calendar dateTask1 = Calendar.getInstance();
		dateTask1.set(2017, Calendar.DECEMBER, 22);
		task1.setEstimatedTaskStartDate(dateTask1);

		// instantiate dependence of Task2 to Task1 in parameter taskDependence and sets
		// the estimated task start date of testTask2 to the estimated task start date
		// of testTask plus 10 days
		task2.createTaskDependence(task1, 10);

		// set of the newEstimatedStartDateTestTask2 which corresponds to the estimated
		// task start date of testTask plus 10 days which corresponds to changing the
		// year from 2017 to 2018
		Calendar newEstimatedStartDateTestTask2 = Calendar.getInstance();
		newEstimatedStartDateTestTask2.set(2018, Calendar.JANUARY, 01);

		assertEquals(newEstimatedStartDateTestTask2, task2.getEstimatedTaskStartDate());

	}

}
