package sprint.two;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project.model.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;

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
	ProjectCollaborator projectCollab2;
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
		user1 = myCompany.getUsersContainer().createUser("Daniel", "daniel@gmail.com", "001", "collaborator",
				"910000000", "Rua", "2101-00", "Test", "Testo", "Testistan");
		user2 = myCompany.getUsersContainer().createUser("Joao", "joaoo@gmail.com", "001", "collaborator", "920000000",
				"Rua", "2301-00", "Test", "Testo", "Testistan");
		user3 = myCompany.getUsersContainer().createUser("Rita", "rita@gmail.com", "001", "collaborator", "930000000",
				"Rua", "2401-00", "Test", "Testo", "Testistan");

		// Creates a project
		project = myCompany.getProjectsContainer().createProject("Project A", "Project AA", user1);

		// Creates Project collaborators
		project.addUserToProjectTeam(user2, 15);
		project.addUserToProjectTeam(user3, 20);
		
		projectCollab2 = project.createProjectCollaborator(user2, 15);

		// Creates the tasks and adds the tasks to the task repository
		
		project.getTaskRepository().addProjectTask(
				project.getTaskRepository().createTask("Task a"));
		project.getTaskRepository().addProjectTask(
				project.getTaskRepository().createTask("Task b"));
		project.getTaskRepository().addProjectTask(
				project.getTaskRepository().createTask("Task c"));
		
		
		//create 3 tasks
		testTask = project.getTaskRepository().getProjectTaskRepository().get(0);
		testTask2 = project.getTaskRepository().getProjectTaskRepository().get(1);
		testTask3 = project.getTaskRepository().getProjectTaskRepository().get(2);

		// Creates the expResult list
		expResult = new ArrayList<Task>();
	}

	@After
	public void tearDown() {
		Company.clear();
		user1 = null;
		user2 = null;
		user3 = null;
		project = null;
		testTask = null;
		testTask2 = null;
		testTask3 = null;
	}

	/**
	 * First, the second task (position 1 on the taskContainer list) is marked as
	 * finished. Then, the other tasks that were not marked as finished were added
	 * to the expResult list. The assert checks if the expResult is equal to the
	 * result of the getExpiredTasks method.
	 */
	@Test
	public void US380_test() {

		//set testTask's state as Finished
		// necessary to pass from "Created" to "Planned"
		estimatedStartDate = Calendar.getInstance();
		estimatedStartDate.set(2017, Calendar.JANUARY, 14);
		testTask.setEstimatedTaskStartDate(estimatedStartDate);
		taskDeadline = Calendar.getInstance();
		taskDeadline.add(Calendar.MONTH, 1);
		testTask.setTaskDeadline(taskDeadline);
		testTask.getTaskState().changeToPlanned();
		// necessary to pass from "Planned" to "Assigned"
		testTask.addProjectCollaboratorToTask(projectCollab2);
		testTask.getTaskState().changeToAssigned();
		// pass from "Assigned" to "Ready"
		testTask.getTaskState().changeToReady();
		// necessary to pass from "Ready" to "OnGoing"
		Calendar projStartDate = (Calendar) estimatedStartDate.clone();
		testTask.setStartDate(projStartDate);
		testTask.getTaskState().changeToOnGoing();
		// pass from "OnGoing" to "Finished"
		Calendar testDate = Calendar.getInstance();
		testTask.setFinishDate(testDate);
		testTask.getTaskState().changeToFinished();
		// assures that the taskTest state is Finished
		assertEquals("Finished", testTask.viewTaskStateName());
		
		
		//set testTask2 as expired (add a deadline that is previous than today)
		// necessary to pass from "Created" to "Planned"
		estimatedStartDate = Calendar.getInstance();
		estimatedStartDate.add(Calendar.MONTH, -6);
		testTask2.setEstimatedTaskStartDate(estimatedStartDate);
		Calendar taskDeadline2 = Calendar.getInstance();
		taskDeadline2.set(2017, Calendar.NOVEMBER, 17);
		testTask2.setTaskDeadline(taskDeadline2);
		testTask2.getTaskState().changeToPlanned();
		// necessary to pass from "Planned" to "Assigned"
		testTask2.addProjectCollaboratorToTask(projectCollab2);
		testTask2.getTaskState().changeToAssigned();
		// pass from "Assigned" to "Ready"
		testTask2.getTaskState().changeToReady();
		// necessary to pass from "Ready" to "OnGoing"
		testTask2.setStartDate(projStartDate);
		testTask2.getTaskState().changeToOnGoing();
		// assures that the taskTest2 state is OnGoing
		assertEquals("OnGoing", testTask2.viewTaskStateName());
		
		//set testTask3 as expired (add a deadline that is previous than today)
		// necessary to pass from "Created" to "Planned"
		estimatedStartDate = Calendar.getInstance();
		estimatedStartDate.add(Calendar.MONTH, -6);
		testTask3.setEstimatedTaskStartDate(estimatedStartDate);
		taskDeadline2.set(2017, Calendar.NOVEMBER, 17);
		testTask3.setTaskDeadline(taskDeadline2);
		testTask3.getTaskState().changeToPlanned();
		// necessary to pass from "Planned" to "Assigned"
		testTask3.addProjectCollaboratorToTask(projectCollab2);
		testTask3.getTaskState().changeToAssigned();
		// pass from "Assigned" to "Ready"
		testTask3.getTaskState().changeToReady();
		// necessary to pass from "Ready" to "OnGoing"
		testTask3.setStartDate(projStartDate);
		testTask3.getTaskState().changeToOnGoing();
		// assures that the taskTest3 state is OnGoing
		assertEquals("OnGoing", testTask3.viewTaskStateName());
		
		//add expired tasks to list to compare
		expResult.add(testTask2);
		expResult.add(testTask3);

		assertEquals(expResult, project.getTaskRepository().getExpiredTasks());

	}

}
