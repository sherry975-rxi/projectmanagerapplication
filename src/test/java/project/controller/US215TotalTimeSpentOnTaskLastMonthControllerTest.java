package project.controller;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.model.Company;
import project.model.Project;
import project.model.ProjectRepository;
import project.model.Report;
import project.model.Task;
import project.model.TaskRepository;
import project.model.User;
import project.model.UserRepository;
import project.model.taskStateInterface.Finished;

public class US215TotalTimeSpentOnTaskLastMonthControllerTest {

	Company myComp;
	ProjectRepository projRepo;
	UserRepository userRepo;
	TaskRepository taskRepo;
	Project proj;
	Task taskA;
	Task taskB;
	Task taskC;
	User user;
	User nullUser;
	US215TotalTimeSpentOnTaskLastMonthController controller;

	@Before
	public void setUp() {
		// Initialize company
		myComp = Company.getTheInstance();

		// Initialize Project Repository
		projRepo = myComp.getProjectsRepository();

		// Initialize User Repository
		userRepo = myComp.getUsersRepository();

		// Add user to User Repository
		userRepo.addUserToUserRepository(userRepo.createUser("Fek Quin", "ugandan@nackls.com", "cluck1337",
				"Follower of da wae", "919898997", "Debil Strit", "SP1T-0N-H1M", "NacklsCiti", "QuinLend", "UGANDA"));
		user = userRepo.getUserByEmail("ugandan@nackls.com");

		// Add a project to the project repository
		projRepo.addProjectToProjectRepository(
				projRepo.createProject("Best project", "Fainding da quin an spitting on de non-beleevahs!", user));
		proj = projRepo.getAllProjects().get(0);

		// Add user to proj
		proj.addUserToProjectTeam(user, 5);

		// Initialize Task Repository
		taskRepo = proj.getTaskRepository();

		// Create and add tasks to Task Repository
		taskA = new Task(1, 1, "Faind fek quin!");
		taskB = new Task(2, 1, "Spit on non-beleevahs!");
		taskC = new Task(3, 1, "Follou da wae!");
		taskRepo.addProjectTask(taskA);
		taskRepo.addProjectTask(taskB);
		taskRepo.addProjectTask(taskC);

		// Add User to tasks
		taskA.addProjectCollaboratorToTask(proj.getProjectCollaboratorFromUser(user));
		taskB.addProjectCollaboratorToTask(proj.getProjectCollaboratorFromUser(user));
		taskC.addProjectCollaboratorToTask(proj.getProjectCollaboratorFromUser(user));

		// Create reports in the tasks
		taskA.createReport(taskA.getTaskCollaboratorByEmail("ugandan@nackls.com"));
		taskB.createReport(taskB.getTaskCollaboratorByEmail("ugandan@nackls.com"));
		taskC.createReport(taskC.getTaskCollaboratorByEmail("ugandan@nackls.com"));

		// Initialize Controller
		controller = new US215TotalTimeSpentOnTaskLastMonthController();

		// Set task state as finished last month
		Calendar finishDate = Calendar.getInstance();
		finishDate.add(Calendar.MONTH, -1);

		Finished stateA = new Finished(taskA);
		taskA.setTaskState(stateA);
		taskA.setFinishDate(finishDate);

		Finished stateB = new Finished(taskB);
		taskB.setTaskState(stateB);
		taskB.setFinishDate(finishDate);

		Finished stateC = new Finished(taskC);
		taskC.setTaskState(stateC);
		taskC.setFinishDate(finishDate);
	}

	@After
	public void tearDown() {
		Company.clear();
		projRepo = null;
		userRepo = null;
		taskRepo = null;
		proj = null;
		taskA = null;
		taskB = null;
		taskC = null;
		user = null;
		controller = null;
	}

	@Test
	public final void testGetTotalTimeOfFinishedTasksFromUserLastMonth() {
		assertEquals(0, controller.getTotalTimeOfFinishedTasksFromUserLastMonth(user), 0.000001);
		Report reportA = taskA.getReports().get(0);
		reportA.setReportedTime(10);
		Report reportB = taskB.getReports().get(0);
		reportB.setReportedTime(15);
		Report reportC = taskC.getReports().get(0);
		reportC.setReportedTime(25);
		assertEquals(50, controller.getTotalTimeOfFinishedTasksFromUserLastMonth(user), 0.000001);

		// Test for null user
		assertEquals(controller.getTotalTimeOfFinishedTasksFromUserLastMonth(nullUser), 0, 0.0001);

	}

}
