package project.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import project.services.ProjectService;
import project.services.TaskService;
import project.services.UserService;
import project.model.*;

import java.util.Calendar;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * This class tests the methods that are called in Controller to execute the
 * action of Canceling an OnGoing Task
 * 
 * @author Group3
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan({ "project.services", "project.model", "project.controller" })
public class US367MarkFinishedTaskAsUnfinishedControllerTest {

	

	@Autowired
	UserService myUsers;
	@Autowired
	ProjectService myProjects;
	@Autowired
	TaskService myTasks;

	User user1, user2, projectManager, projectManager2;
	Project project1, project2, project3;
	ProjectCollaborator projCollab11, projCollab12, projCollab21, projCollab22, projCollab31, projCollab32;
	Task task1, task2, task3, task4, task5, task6;
	TaskCollaborator taskCollab1, taskCollab2;

	Calendar startDateTest;
	Calendar estimatedTaskStartDateTest;
	Calendar taskDeadlineDateTest;
	Calendar taskExpiredDeadlineDateTest;

	@Autowired
	US367MarkFinishedTaskAsUnfinishedController controller;

	@Before
	public void setUp() {


		// create users
		user1 = myUsers.createUser("Joe Smith", "jsmith@gmail.com", "001", "Junior Programmer",
				"930000000", "Rua da Caparica, 19", "7894-654", "Porto", "Porto", "Portugal");
		user2 = myUsers.createUser("John Smith", "johnsmith@gmail.com", "001", "General Manager",
				"930025000", "Rua Doutor Armando", "4455-654", "Rio Tinto", "Gondomar", "Portugal");
		projectManager = myUsers.createUser("Mary MacJohn", "mmacjohn@gmail.com", "003",
				"Product Manager", "930025000", "Rua Terceira, 44", "4455-122", "Leça da Palmeira", "Matosinhos",
				"Portugal");
		projectManager2 = myUsers.createUser("John MacMary", "jmacmary2@gmail.com", "003",
				"Product Manager2", "930025356", "Rua Segunda, 45", "4455-122", "Leça da Palmeira", "Matosinhos",
				"Portugal");

		// add users to database
		myUsers.addUserToUserRepositoryX(user1);
		myUsers.addUserToUserRepositoryX(user2);
		myUsers.addUserToUserRepositoryX(projectManager);
		myUsers.addUserToUserRepositoryX(projectManager2);

		// set user as collaborator
		user1.setUserProfile(Profile.COLLABORATOR);
		user2.setUserProfile(Profile.COLLABORATOR);
		projectManager.setUserProfile(Profile.COLLABORATOR);
		projectManager2.setUserProfile(Profile.COLLABORATOR);

		// create project and establishes collaborator projectManager as project manager
		project1 = myProjects.createProject("Project Management software",
				"This software main goals are ....", projectManager);
		project2 = myProjects.createProject("Project Management software",
				"This software main goals are ....", projectManager2);
		project3 = myProjects.createProject("Project Management software",
				"This software main goals are ....", projectManager);

		projCollab11 = myProjects.createProjectCollaborator(user1, project1, 2);
		projCollab12 = myProjects.createProjectCollaborator(user2, project1, 2);
		projCollab21 = myProjects.createProjectCollaborator(user1, project1, 2);
		projCollab22 = myProjects.createProjectCollaborator(user2, project1, 2);
		projCollab31 = myProjects.createProjectCollaborator(user1, project1, 2);
		projCollab32 = myProjects.createProjectCollaborator(user2, project1, 2);

		// create tasks and save them
		task1 = myTasks.createTask("Create class User", project1);
		task2 = myTasks.createTask("Create class User", project1);
		task3 = myTasks.createTask("create test for method set name in class user", project2);
		task4 = myTasks.createTask("Create class User", project2);
		task5 = myTasks.createTask("Create class User", project3);
		task6 = myTasks.createTask("create test for method set name in class user", project3);

		// create a estimated Task Start Date
		estimatedTaskStartDateTest = Calendar.getInstance();
		estimatedTaskStartDateTest.set(Calendar.YEAR, 2017);
		estimatedTaskStartDateTest.set(Calendar.MONTH, Calendar.SEPTEMBER);
		estimatedTaskStartDateTest.set(Calendar.DAY_OF_MONTH, 25);
		estimatedTaskStartDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// create a estimated Task Dead line Date
		taskDeadlineDateTest = Calendar.getInstance();
		taskDeadlineDateTest.set(Calendar.YEAR, 2018);
		taskDeadlineDateTest.set(Calendar.MONTH, Calendar.JANUARY);
		taskDeadlineDateTest.set(Calendar.DAY_OF_MONTH, 29);
		taskDeadlineDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// create a expired estimated Task Dead line Date
		taskExpiredDeadlineDateTest = Calendar.getInstance();
		taskExpiredDeadlineDateTest.set(Calendar.YEAR, 2017);
		taskExpiredDeadlineDateTest.set(Calendar.MONTH, Calendar.SEPTEMBER);
		taskExpiredDeadlineDateTest.set(Calendar.DAY_OF_MONTH, 29);
		taskExpiredDeadlineDateTest.set(Calendar.HOUR_OF_DAY, 14);


		// set estimated task start date and task dead line to tasks
		task1.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task1.setTaskDeadline(taskDeadlineDateTest);

		task2.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task2.setStartDate(estimatedTaskStartDateTest);
		task2.setTaskDeadline(taskDeadlineDateTest);

		task3.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task3.setStartDate(estimatedTaskStartDateTest);
		task3.setTaskDeadline(taskDeadlineDateTest);

		task3.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task3.setStartDate(estimatedTaskStartDateTest);
		task3.setTaskDeadline(taskDeadlineDateTest);

		task4.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task4.setStartDate(estimatedTaskStartDateTest);
		task4.setTaskDeadline(taskDeadlineDateTest);

		task5.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task5.setStartDate(estimatedTaskStartDateTest);
		task5.setTaskDeadline(taskDeadlineDateTest);

		task6.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task6.setStartDate(estimatedTaskStartDateTest);
		task6.setTaskDeadline(taskDeadlineDateTest);

		// add project collaborator to task
		task1.addProjectCollaboratorToTask(projCollab11);
		task2.addProjectCollaboratorToTask(projCollab12);
		task3.addProjectCollaboratorToTask(projCollab21);
		task4.addProjectCollaboratorToTask(projCollab21);
		task5.addProjectCollaboratorToTask(projCollab32);
		task6.addProjectCollaboratorToTask(projCollab31);

		// create a estimated Task Start Date
		startDateTest = Calendar.getInstance();
		task1.setEstimatedTaskEffort(10);
		task1.setTaskBudget(10);
		task1.setStartDate(startDateTest);


		// Sets the tasks to "Finished"
		task1.markTaskAsFinished();
		task2.markTaskAsFinished();
		task3.markTaskAsFinished();
		task4.markTaskAsFinished();
		task5.markTaskAsFinished();
		task6.markTaskAsFinished();

	}

	@Test
	public void test() {

	    // given a task in "Finished" state
        assertTrue("Finished".equals(task1.viewTaskStateName()));

        //add again a project collaborator to task (finishing a task removes project collaborators)
        task1.addProjectCollaboratorToTask(projCollab12);

        // when the controller is created and its method called
		controller.markFinishedTaskAsUnfinished(task1.getTaskID());
		System.out.println(task1.viewTaskStateName());


		// then the chosen task must no longer have "Finished" state
		assertFalse("Finished".equals(task1.viewTaskStateName()));
	}
	
	@Test
	public void testGetter_Setter() {
		
		controller.setTaskID(task1.getTaskID());
		String expect = controller.getTaskID();
		assertEquals(expect, task1.getTaskID());
		
	}
	

}
