package project.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.jackrabbit.webdav.version.report.Report;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.model.Company;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.ProjectRepository;
import project.model.Task;
import project.model.TaskCollaborator;
import project.model.User;
import project.model.UserRepository;
import project.model.taskStateInterface.OnGoing;

public class US207CreateTaskReportControllerTests {

	Company company;
	User userDaniel;
	User userRui;
	ProjectRepository projectRepository;
	UserRepository userRepository;
	Project projectA;
	ProjectCollaborator userRuiCollaborator;
	Task taskA;
	Task taskB;
	Task taskC;
	String stringRequest1;
	String stringRequest2;
	List<String> pendingRemovalRequests;
	US207CreateTaskReportController createReportController;
	OnGoing taskState;
	Report taskReport;
	TaskCollaborator taskCollab;

	@Before
	public void setUp() {

		// Sets the Company
		company = Company.getTheInstance();

		// Creates the Project Repository and User Repository
		projectRepository = Company.getTheInstance().getProjectsRepository();
		userRepository = Company.getTheInstance().getUsersRepository();

		// Creates the users
		userDaniel = userRepository.createUser("Daniel", "daniel@gmail.com", "1234", "Arquitecto", "967387654", "Rua",
				"3700", "Porto", "Porto", "Portugal");
		userRui = userRepository.createUser("Rui", "rui@gmail.com", "12345", "Arquitecto", "967387654", "Rua", "3800",
				"Porto", "Porto", "Portugal");
		// Adds users to the user repository
		userRepository.addUserToUserRepository(userDaniel);
		userRepository.addUserToUserRepository(userRui);

		// Creates the Project and adds it to the project repository
		projectA = projectRepository.createProject("Museu Serralves", "Projecto do Museu de Serralves", userDaniel);
		projectRepository.addProjectToProjectRepository(projectA);

		// Adds the user to the project team
		projectA.addUserToProjectTeam(userRui, 20);
		userRuiCollaborator = projectA.findProjectCollaborator(userRui);

		// Creates the tasks and adds them to the projectTasks
		taskA = projectA.getTaskRepository().createTask("Implementar US100");
		projectA.getTaskRepository().addProjectTask(taskA);

		taskB = projectA.getTaskRepository().createTask("Implementar US200");
		projectA.getTaskRepository().addProjectTask(taskB);

		taskC = projectA.getTaskRepository().createTask("Implementar US300");
		projectA.getTaskRepository().addProjectTask(taskC);

		// Adds the project collaborator to the tasks
		taskB.addProjectCollaboratorToTask(userRuiCollaborator);

		// Creates a taskCollaborator in taskA
		taskCollab = taskA.createTaskCollaborator(userRuiCollaborator);
		// Adds the taskCollaborator to taskA
		taskA.addTaskCollaboratorToTask(taskCollab);

		// taskC.addProjectCollaboratorToTask(userRuiCollaborator);

		// Creates a Task State
		taskState = new OnGoing(taskA);

		// Sets taskA to state "OnGoing"
		taskA.setTaskState(taskState);

		createReportController = new US207CreateTaskReportController("rui@gmail.com", taskA.getTaskID());

	}

	@After
	public void tearDown() {
		Company.clear();
		company = null;
		userDaniel = null;
		userRui = null;
		projectRepository = null;
		userRepository = null;
		projectA = null;
		userRuiCollaborator = null;
		taskA = null;
		taskB = null;
		taskC = null;
		stringRequest1 = null;
		stringRequest2 = null;
		pendingRemovalRequests = null;
		createReportController = null;
		taskState = null;
	}

	@Test
	public void testCreateReportController() {

		String getTaskId = taskA.getTaskID();

		/*
		 * Created a report sucessfully, because the taskID exists
		 */
		assertTrue(createReportController.createReportController(20));

	}

	@Test
	public void testGetReportedTimeByCollaborator() {
		/*
		 * Gets the Id of the task
		 */
		String getTaskId = taskA.getTaskID();

		/*
		 * Creates a report to TaskA
		 */
		createReportController.createReportController(20);

		// checks if the report returns the updated time
		assertEquals(createReportController.getReportedTimeByCollaborator(), 20);

		// Updates the report time
		createReportController.createReportController(40);
		// Checks that the report size didn't increase;
		assertEquals(taskA.getReports().size(), 1);

		// Checks that the report time got updated;
		assertEquals(createReportController.getReportedTimeByCollaborator(), 40);

	}

}
