package project.controller;

import org.junit.After;
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
import project.model.taskstateinterface.Created;
import project.model.taskstateinterface.Finished;
import project.model.taskstateinterface.OnGoing;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan({ "project.services", "project.model", "project.controller", "project.repository"})
public class US375GetProjectNotStartedTaskListTest {
	
	@Autowired	
	US375GetProjectNotStartedTaskListController tasksFiltersController;
	
	@Autowired	
	UserService userContainer;
	
	@Autowired
	ProjectService projectContainer;
	
	@Autowired
	TaskService taskContainer;
	
	User user1, user2, user3;
	Project project1;
	ProjectCollaborator projCollab1, projCollab2, projCollab3;
	Task task1, task2, task3, task4, task5, task6;

	@Before
	public void setUp() {

		// create users in company
		user2 = userContainer.createUser("João", "user2@gmail.com", "001", "Manager", "930025000",
				"rua doutor antónio", "7689-654", "porto", "porto", "portugal");
		user1 = userContainer.createUser("Juni", "user3@gmail.com", "002", "Code Monkey", "930000000",
				"rua engenheiro joão", "789-654", "porto", "porto", "portugal");

		// change profiles of users from VISITOR (default) to COLLABORATOR
		user2.setUserProfile(Profile.COLLABORATOR);
		user1.setUserProfile(Profile.COLLABORATOR);

		// create project 1 in company 1
		project1 = projectContainer.createProject("name3", "description4", user2);

		// add project 1 to company 1
		projectContainer.addProjectToProjectContainer(project1);
		
		// create tasks in project 1
		task1 = taskContainer.createTask("Do this", project1);
		task2 = taskContainer.createTask("Do that", project1);
		task3 = taskContainer.createTask("Merge everything", project1);
		task4 = taskContainer.createTask("Do this", project1);
		task5 = taskContainer.createTask("Do this", project1);
		task6 = taskContainer.createTask("Do this", project1);

		// add costPerEffort to users in project 1, resulting in a Project Collaborator
		// for each one
		projCollab1 = project1.createProjectCollaborator(user1, 250);
		projCollab2 = project1.createProjectCollaborator(user2, 120);
		projCollab3 = project1.createProjectCollaborator(user2, 200);

		// Sets current state for all the tasks
		task1.setTaskState(new OnGoing());
		task1.setCurrentState(StateEnum.ONGOING);

		task2.setTaskState(new Created());
		task2.setCurrentState(StateEnum.CREATED);
		
		task3.setTaskState(new Created());
		task3.setCurrentState(StateEnum.CREATED);
		
		task4.setTaskState(new Created());
		task4.setCurrentState(StateEnum.CREATED);
		
		task5.setTaskState(new Finished());
		task5.setCurrentState(StateEnum.FINISHED);
		
		task6.setTaskState(new OnGoing());
		task6.setCurrentState(StateEnum.ONGOING);
		

	}

	@After
	public void tearDown(){
		user1 = null;
		user2 = null;
		user3 = null;
		project1 = null;
		projCollab1= null;
		projCollab2 = null;
		projCollab3 = null;
		task1= null;
		task2= null;
		task3= null;
		task4= null;
		task5= null;
		task6= null;
	}

	@Test
	public final void testGetProjectNotStartedTaskList() {
		assertEquals(3, tasksFiltersController.getProjectNotStartedTaskList(project1).size());
	}
	
	@Test
	public final void testGetProjectNotStartedTasks() {
		List<Task> listToCompare = new ArrayList<Task>();
		listToCompare.add(task2);
		listToCompare.add(task3);
		listToCompare.add(task4);

		assertEquals(listToCompare, tasksFiltersController.getProjectNotStartedTasks(project1));
	}

}
