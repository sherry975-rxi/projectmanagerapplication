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
 *         Test to US370
 * 
 *         US370 - As Project Manager, I want to get a list of completed tasks.
 */
public class US370 {

	Company myCompany;
	UserRepository userRepository;
	User user1;
	User user2;
	Project project;
	ProjectRepository projectRepository;
	TaskRepository taskRepository;
	Task testTask;
	Task testTask2;
	ProjectCollaborator projectCollaborator;
	TaskCollaborator taskWorker;
	TaskCollaborator taskWorker1;
	Calendar estimatedTaskStartDate, taskDeadline;

	@Before
	public void setUp() {
		// create company

		myCompany = Company.getTheInstance();

		// creates an UserRepository
		userRepository = myCompany.getUsersRepository();

		// creates a ProjectsRepository
		projectRepository = myCompany.getProjectsRepository();

		// creates a UserRepository
		userRepository.getAllUsersFromRepository().clear();

		// clean list ProjectsRepository
		myCompany.getProjectsRepository().getAllProjects().clear();

		// clean list URepository
		myCompany.getUsersRepository().getAllUsersFromRepository().clear();

		// create user
		user1 = myCompany.getUsersRepository().createUser("Daniel", "daniel@gmail.com", "001", "collaborator",
				"910000000", "Rua", "2401-00", "Test", "Testo", "Testistan");

		// create user admin
		user2 = myCompany.getUsersRepository().createUser("João", "joao@gmail.com", "001", "Admin", "920000000", "Rua",
				"2401-00", "Test", "Testo", "Testistan");

		// add user to user list
		userRepository.addUserToUserRepository(user1);
		userRepository.addUserToUserRepository(user2);

		// set user as collaborator
		user1.setUserProfile(Profile.COLLABORATOR);
		user2.setUserProfile(Profile.COLLABORATOR);

		// create project
		project = projectRepository.createProject("name3", "description4", user2);

		// create project collaborator
		projectCollaborator = project.createProjectCollaborator(user1, 2);

		// add user to project team
		project.addProjectCollaboratorToProjectTeam(projectCollaborator);

		// create taskRepository
		taskRepository = project.getTaskRepository();

	}

	@After
	public void tearDown() {
		Company.clear();
		user1 = null;
		testTask = null;
		project = null;
		projectRepository = null;
		taskRepository = null;
		userRepository = null;
	}

	@Test
	public void testUS370() {
		
		// create 2 task
		testTask = taskRepository.createTask("Test dis agen pls");
		testTask2 = taskRepository.createTask("Test dis agen pls");

		// Adds Tasks to TaskRepository
		taskRepository.addProjectTask(testTask);
		taskRepository.addProjectTask(testTask2);

		// create task Worker
		taskWorker = testTask.createTaskCollaborator(projectCollaborator);
		taskWorker1 = testTask2.createTaskCollaborator(projectCollaborator);

		// set testTask as finished
		// necessary to pass from "Created" to "Planned"
		estimatedTaskStartDate = Calendar.getInstance();
		estimatedTaskStartDate.add(Calendar.MONTH, -1);
		testTask.setEstimatedTaskStartDate(estimatedTaskStartDate);
		taskDeadline = Calendar.getInstance();
		taskDeadline.add(Calendar.MONTH, 1);
		testTask.setTaskDeadline(taskDeadline);
		testTask.getTaskState().changeToPlanned();
		// necessary to pass from "Planned" to "Assigned"
		testTask.addProjectCollaboratorToTask(projectCollaborator);
		testTask.getTaskState().changeToAssigned();
		// pass from "Assigned" to "Ready"
		testTask.getTaskState().changeToReady();
		// necessary to pass from "Ready" to "OnGoing"
		Calendar projStartDate = (Calendar) estimatedTaskStartDate.clone();
		testTask.setStartDate(projStartDate);
		testTask.getTaskState().changeToOnGoing();
		// pass from "OnGoing" to "Finished"
		Calendar testDate = (Calendar) estimatedTaskStartDate.clone();
		testTask.setFinishDate(testDate);
		testTask.getTaskState().changeToFinished();
		// assures that the taskTest state is Finished
		assertEquals("Finished", testTask.viewTaskStateName());
		
		
		// set testTask2 as finished
		// necessary to pass from "Created" to "Planned"
		estimatedTaskStartDate = Calendar.getInstance();
		estimatedTaskStartDate.add(Calendar.MONTH, -1);
		testTask2.setEstimatedTaskStartDate(estimatedTaskStartDate);
		taskDeadline = Calendar.getInstance();
		taskDeadline.add(Calendar.MONTH, 1);
		testTask2.setTaskDeadline(taskDeadline);

		testTask2.getTaskState().changeToPlanned();

		// necessary to pass from "Planned" to "Assigned"
		testTask2.addProjectCollaboratorToTask(projectCollaborator);
		testTask2.getTaskState().changeToAssigned();

		// pass from "Assigned" to "Ready"
		testTask2.getTaskState().changeToReady();

		// necessary to pass from "Ready" to "OnGoing"
		testTask2.setStartDate(projStartDate);
		testTask2.getTaskState().changeToOnGoing();

		// pass from "OnGoing" to "Finished"
		testTask2.setFinishDate(testDate);
		testTask2.getTaskState().changeToFinished();
		// assures that the taskTest state is Finished
		assertEquals("Finished", testTask2.viewTaskStateName());

		// Creates a new list, and then added the tasks without any user assigned to
		// them
		List<Task> listFinishedTasks = new ArrayList<Task>();
		listFinishedTasks.add(testTask);
		listFinishedTasks.add(testTask2);

		assertEquals(listFinishedTasks, taskRepository.getFinishedTasks());

	}

}
