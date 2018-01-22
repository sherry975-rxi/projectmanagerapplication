package project.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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

public class US208ChangeReportedTimeInTaskTests {

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
	US208ChangeReportedTimeInTaskController changeReportController;
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

		changeReportController = new US208ChangeReportedTimeInTaskController("rui@gmail.com");

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
		changeReportController = null;
		taskState = null;
	}

	@Test
	public void testUS208ChangeReportedTime() {

		String getTaskId = taskA.getTaskID();

		/*
		 * Cannot update a report, because the task doesnt have a report yet
		 */
		assertFalse(changeReportController.correctReportedTimeInTaskController(getTaskId, 20));

		// Creates a report;
		taskA.createReport(taskCollab);

		// checks the size list of reports in the task
		assertEquals(taskA.getReports().size(), 1);

		// checks if now its allowed to correct a report time
		int newReportTime = 40;
		assertTrue(changeReportController.correctReportedTimeInTaskController(getTaskId, newReportTime));
		assertEquals(taskA.getReports().get(0).getReportedTime(), newReportTime);
		assertEquals(changeReportController.getReportedTimeByCollaboratorController(getTaskId), newReportTime);

	}

}
