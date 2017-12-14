package sprint.two;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
 *         US380
 * 
 *         Como Gestor de projeto, quero obter uma lista de tarefas não
 *         concluídas e com data de conclusão vencida.
 *
 */
public class US380 {

	Company myCompany;
	User user1;
	User user2;
	User user3;
	Project project;
	Task testTask;
	Task testTask2;
	Task testTask3;
	Calendar estimatedStartDate;
	Calendar taskDeadline;
	List<Task> expResult;

	@Before
	public void setUp() {

		// Creates the Company
		myCompany = Company.getTheInstance();

		// Creates three users
		user1 = myCompany.getUsersRepository().createUser("Daniel", "daniel@gmail.com", "001", "collaborator",
				"910000000", "Rua", "2101-00", "Test", "Testo", "Testistan");
		user2 = myCompany.getUsersRepository().createUser("Joao", "joaoo@gmail.com", "001", "collaborator", "920000000",
				"Rua", "2301-00", "Test", "Testo", "Testistan");
		user3 = myCompany.getUsersRepository().createUser("Rita", "rita@gmail.com", "001", "collaborator", "930000000",
				"Rua", "2401-00", "Test", "Testo", "Testistan");

		// Creates a project
		project = myCompany.getProjectsRepository().createProject("Project A", "Project AA", user1);

		// Creates Project collaborators
		project.addUserRToProjectTeam(user2, 15);
		project.addUserRToProjectTeam(user3, 20);

		// Creates the dates
		estimatedStartDate = Calendar.getInstance();
		estimatedStartDate.set(2017, Calendar.JANUARY, 14);
		taskDeadline = Calendar.getInstance();
		taskDeadline.set(2017, Calendar.NOVEMBER, 17);

		// Creates the tasks and adds the tasks to the task repository
		project.getTaskRepository().addProjectTask(
				project.getTaskRepository().createTask("Task a", 2000, estimatedStartDate, taskDeadline, 2000));
		project.getTaskRepository().addProjectTask(
				project.getTaskRepository().createTask("Task b", 3000, estimatedStartDate, taskDeadline, 3000));
		project.getTaskRepository().addProjectTask(
				project.getTaskRepository().createTask("Task c", 4000, estimatedStartDate, taskDeadline, 4000));

		// Creates the expResult list
		expResult = new ArrayList<Task>();
	}

	@After
	public void tearDown() {
		myCompany.clear();
		user1 = null;
		user2 = null;
		user3 = null;
		project = null;
		testTask = null;
		testTask2 = null;
		testTask3 = null;
	}

	/**
	 * First, the second task (position 1 on the taskRepository list) is marked as
	 * finished. Then, the other tasks that were not marked as finished were added
	 * to the expResult list. The assert checks if the expResult is equal to the
	 * result of the getExpiredTasks method.
	 */
	@Test
	void US380_test() {

		project.getTaskRepository().getProjectTaskList().get(1).markTaskAsFinished();
		expResult.add(project.getTaskRepository().getProjectTaskList().get(0));
		expResult.add(project.getTaskRepository().getProjectTaskList().get(2));

		assertEquals(expResult, project.getTaskRepository().getExpiredTasks());

	}

}
