package project.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import project.Repository.ProjCollabRepository;
import project.Repository.ProjectsRepository;
import project.Repository.TaskRepository;
import project.Repository.UserRepository;
import project.Services.ProjectService;
import project.Services.TaskService;
import project.Services.UserService;
import project.model.*;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PrintTaskInfoControllerTest {


	@Autowired
	UserRepository userRepository;

	@Autowired
	ProjectsRepository projectsRepository;

	@Autowired
	ProjCollabRepository projCollabRepository;

	@Autowired
    TaskRepository taskRepository;

	ProjectService projContainer;
	UserService userContainer;

	TaskService taskService;


	User user1;
	private User joaoPM;
	private ProjectCollaborator collab1, collab2;
	Project project;
	private Calendar startDate, finishDate;
	Task task1, task2, task3;

	Integer projectID;

	PrintTaskInfoController controller;

	@Before
	public void setUp() {

		projContainer = new ProjectService(projectsRepository, projCollabRepository);

		userContainer = new UserService(userRepository);

		taskService = new TaskService(taskRepository);
        taskService.setProjectCollaboratorRepository(projCollabRepository);

		// create user
		user1 = userContainer.createUser("Daniel", "daniel@gmail.com", "001", "collaborator",
				"910000000", "Rua", "2401-00", "Test", "Testo", "Testistan");

		// create user admin
		joaoPM = userContainer.createUser("João", "joao@gmail.com", "001", "Admin", "920000000", "Rua",
				"2401-00", "Test", "Testo", "Testistan");

        // set user as collaborator
        user1.setUserProfile(Profile.COLLABORATOR);
        joaoPM.setUserProfile(Profile.COLLABORATOR);

		// add user to user list
		userContainer.addUserToUserRepositoryX(user1);
		userContainer.addUserToUserRepositoryX(joaoPM);


		// Creates one Project
		project = projContainer.createProject("Projeto de gestão",
				"Este projeto está focado na gestão.", joaoPM);
		project.setProjectBudget(3000);


		// add start date to project
		startDate = Calendar.getInstance();
		startDate.set(2017, Calendar.JANUARY, 2, 12, 31, 0);
		project.setStartdate(startDate);

		// add finish date to project
		finishDate = Calendar.getInstance();
		finishDate.set(2017, Calendar.FEBRUARY, 2, 12, 31, 0);
		project.setFinishdate(finishDate);

        // add project to project repository
        projContainer.updateProject(project);

		// create project collaborators
		collab1 = projContainer.createProjectCollaborator(user1, project, 2);
		collab2 = projContainer.createProjectCollaborator(joaoPM, project, 2);



		// create three tasks
		task1 = taskService.createTask("First task", project);
		task2 = taskService.createTask("Second task", project);
		task3 = taskService.createTask("Third task", project);

		// add project's collaborators to tasks
		task1.addProjectCollaboratorToTask(collab1);
		task3.addProjectCollaboratorToTask(collab2);
		task2.addProjectCollaboratorToTask(collab1);
		task2.addProjectCollaboratorToTask(collab2);

		projectID = project.getId();

		// Instantiates the controller
		controller = new PrintTaskInfoController(task1, project);
		controller.taskService=this.taskService;
		controller.projService=this.projContainer;

	}


	/**
	 * Tests if the method of controller gets the task's name
	 */
	@Test
	public void testPrintTaskNameInfo() {

		assertEquals(controller.printTaskNameInfo(), "First task");
	}

	// @Test
	// public void testPrintProjectNameInfo() {
	// // create controller
	// PrintTaskInfoController controller = new PrintTaskInfoController(task1);
	//
	// assertEquals(controller.printProjectNameInfo(), "Projeto de gestão");
	// }

	/**
	 * Tests if the method of controller gets the task's ID
	 */
	@Test
	public void testPrintTaskIDCodeInfo() {
		assertEquals(controller.printTaskIDCodeInfo(), (projectID+".1"));
	}

	/**
	 * Tests if the method of controller gets the task's state
	 */
	@Test
	public void testPrintTaskStateInfo() {
		assertEquals(controller.printTaskStateInfo(), "Planned");
	}

	/**
	 * Tests if the method of controller gets the task's estimated start date
	 */
	@Test
	public void testPrintEstimatedTaskStartDateInfo() {
		// create controller
		controller = new PrintTaskInfoController(task1);

		assertEquals(controller.printTaskEstimatedStartDateInfo(), "---");

		// asserts that the task1 don't have a estimated start date
		assertEquals(controller.printTaskEstimatedStartDateInfo(), "---");

		// create a calendar date to set task's estimated start date
		Calendar estimatedStartDate = Calendar.getInstance();
		estimatedStartDate.set(2017, Calendar.JANUARY, 2, 12, 31, 0);
		task1.setEstimatedTaskStartDate(estimatedStartDate);
		// asserts that the task1 have the estimated start date that are previous set
		assertEquals(controller.printTaskEstimatedStartDateInfo(), "Mon, 2 Jan 2017 12:31:00");
	}

	/**
	 * Tests if the method of controller gets the task's start date
	 */
	@Test
	public void testPrintTaskStartDateInfo() {
		// create controller
		controller = new PrintTaskInfoController(task1);

		assertEquals(controller.printTaskStartDateInfo(), "---");

		// asserts that the task1 don't have a start date
		assertEquals(controller.printTaskStartDateInfo(), "---");

		// create a calendar date to set task's start date
		Calendar startDate = Calendar.getInstance();
		startDate.set(2017, Calendar.JANUARY, 2, 12, 31, 0);
		task1.setStartDate(startDate);
		// asserts that the task1 have the start date that are previous set
		assertEquals(controller.printTaskStartDateInfo(), "Mon, 2 Jan 2017 12:31:00");
	}

	/**
	 * Tests if the method of controller gets the task's deadline
	 */
	@Test
	public void testPrintTaskDeadlineInfo() {
		// create controller
		controller = new PrintTaskInfoController(task1);

		// asserts that the task1 don't have a deadline
		assertEquals(controller.printTaskDeadlineInfo(), "---");

		// create a calendar date to set task's deadline
		Calendar estimatedFinishDate = Calendar.getInstance();
		estimatedFinishDate.set(2017, Calendar.FEBRUARY, 2, 12, 31, 0);
		task1.setTaskDeadline(estimatedFinishDate);
		// asserts that the task1 have the deadline that are previous set
		assertEquals(controller.printTaskDeadlineInfo(), "Thu, 2 Feb 2017 12:31:00");
	}

	/**
	 * Tests if the method of controller gets the task's finish date
	 */
	@Test
	public void testPrintTaskFinishDateInfo() {
		// create controller
		controller = new PrintTaskInfoController(task1);

		// asserts that the task1 don't have a finish date
		assertEquals(controller.printTaskDeadlineInfo(), "---");

		// create a calendar date to set task's finish date
		Calendar finishDate = Calendar.getInstance();
		finishDate.set(2017, Calendar.FEBRUARY, 2, 12, 31, 0);
		task1.setFinishDate(finishDate);
		// asserts that the task1 have the finish date that are previous set
		assertEquals(controller.printTaskFinishDateInfo(), "Thu, 2 Feb 2017 12:31:00");
	}

	/**
	 * Tests if the method of controller gets the task's team
	 */
	@Test
	public void testPrintTaskTeamInfo() {

		assertEquals(controller.printTaskTeamInfo(), "Daniel");
	}
	/**
	 * Tests if the method of controller gets the project name where the task is associated
	 */
	@Test
	public void testPrintInfoFromTask() {

		String projectName = controller.printProjectNameInfo();
		assertEquals("Projeto de gestão", projectName);
	}
	/**
	 * Tests if the method of controller gets the task's budget
	 */
	@Test
	public void testPrintTaskBudgetInfo(){
		task1.setTaskBudget(20);
		assertEquals(controller.printTaskBudgetInfo(), "20");

	}
}
