package project.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import project.Repository.ProjectsRepository;
import project.Repository.TaskRepository;
import project.Repository.UserRepository;
import project.Services.ProjectService;
import project.Services.TaskService;
import project.Services.UserService;
import project.model.*;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan({ "project.services", "project.model", "project.controller", "project.Repository" })
public class US204v2createRequestAddCollaboratorToTaskTeamControllerTest {
	

	@Autowired
	ProjectService projectContainer;
	@Autowired
	UserService userContainer;
	@Autowired
	TaskService taskRepo;
	Project proj;
	Task taskA;
	Task taskB;
	Task taskC;
	User user;
	ProjectCollaborator projCollab;
	@Autowired
	US204v2createRequestAddCollaboratorToTaskTeamController controller;

	@Before
	public void setUp() {


		// Add user to User Container
		user = userContainer.createUser("Fek Quin", "ugandan@nackls.com", "cluck1337", "Follower of da wae", "919898997",
				"Debil Strit", "SP1T-0N-H1M", "NacklsCiti", "QuinLend", "UGANDA");
//		user = userContainer.getUserByEmail("ugandan@nackls.com");

		// Add a project to the project repository
		proj = projectContainer.createProject("Best project", "Fainding da quin an spitting on de non-beleevahs!", user);
//		proj = projectContainer.getAllProjectsfromProjectsContainer().get(0);

		projCollab = projectContainer.createProjectCollaborator(user, proj, 0);

		// Create and add tasks to Task Repository
//		taskA = taskRepo.createTask("Faind fek quin!", proj);
		taskA = new Task(1, 1, "Faind fek quin!");
		taskA.setProject(proj);
		taskRepo.saveTask(taskA);
		
		taskB = new Task(2, 1, "Spit on non-beleevahs!");
		taskB.setProject(proj);
		taskRepo.saveTask(taskB);
		
		taskC = new Task(3, 1, "Follou da wae!");
		taskC.setProject(proj);
		taskRepo.saveTask(taskC);
		
//		taskRepo.addTaskToProject(taskA);	
//		taskRepo.addTaskToProject(taskB);
//		taskRepo.addTaskToProject(taskC);
		



	}

//	@After
//	public void tearDown() {
//
//		projectContainer = null;
//		userContainer = null;
//		taskRepo = null;
//		proj = null;
//		taskA = null;
//		taskB = null;
//		taskC = null;
//		user = null;
//		controller = null;
//	}

	@Test
	public final void testCreateTaskTeamRequest() {

		
		assertEquals(0, taskA.getPendingTaskAssignementRequests().size());
		controller.createTaskTeamRequest("1.1", user);
		assertEquals(1, taskA.getPendingTaskAssignementRequests().size());
		
	}

	@Test
	public final void testSetProjectIDFromTaskID() {
		controller.setProjectIDFromTaskID("1.1");
		int projID = (int) controller.getProjectID();
		assertEquals(1, projID);
	}

	@Test
	public final void testSetProjectID() {
		controller.setProjectID(5);
		int projID = (int) controller.getProjectID();
		assertEquals(5, projID);
	}

	@Test
	public final void testGetTaskByTaskID() {
		assertEquals(taskA, controller.getTaskByTaskID("1.1"));
	}

}
