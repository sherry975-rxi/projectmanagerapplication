/**
 * 
 */
package project.controller;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.Task;
import project.model.TaskCollaborator;
import project.model.User;

/**
 * Tests the controller that allows removing a Project Collaborator from a
 * Project Team in a Project
 * 
 * @author Group3
 *
 */
public class RemoveCollaboratorFromProjectControllerTest {

	/**
	 * This method allows removing a Project Collaborator from a Project Team and
	 * includes removing that Project Collaborator from all Tasks in this Project
	 * 
	 * projectCollaborator1 is removed from ProjectTeam
	 * 
	 */
		
		User user1;
		User user2;
		User user3;
		Project p1;
		ProjectCollaborator projectCollaborator1;
		ProjectCollaborator projectCollaborator2; 
		Calendar estimatedStartDate;
		Calendar taskDeadline;
		Task task1;
		TaskCollaborator taskWorker1;
		TaskCollaborator taskWorker2; 
		
		@Before
		public void setUp() {

		// Creates the users
		user1 = new User("name", "email", "idNumber", "function", "123456789");
		user2 = new User("name2", "email2", "idNumber2", "function2", "987654321");
		user3 = new User("name3", "email3", "idNumber2", "function2", "987654321");


		// creates a Project and assigns a user as Project Manager
		p1 = new Project(1, "name3", "description4", user3);

		// Creates the project collaborators from users
		projectCollaborator1 = new ProjectCollaborator(user1, 1200);
		projectCollaborator2 = new ProjectCollaborator(user2, 1200);

		// Adds project collaborators to Project Team
		p1.addProjectCollaboratorToProjectTeam(projectCollaborator2);
		p1.addProjectCollaboratorToProjectTeam(projectCollaborator1);

		// Creates a task and sets the estimated Start Date and Task DeadLine
		estimatedStartDate = Calendar.getInstance();
		estimatedStartDate.set(2017, Calendar.JANUARY, 14);
		taskDeadline = Calendar.getInstance();
		taskDeadline.set(2017, Calendar.NOVEMBER, 17);
		task1 = p1.getTaskRepository().createTask("description", 0, estimatedStartDate, taskDeadline, 0);

		// adds a Task to Project
		p1.getTaskRepository().addProjectTask(task1);

		// Adds project collaborators to a task, which makes that ProjColl a
		// TaskCollaborator
		task1.addProjectCollaboratorToTask(projectCollaborator1);
		task1.addProjectCollaboratorToTask(projectCollaborator2);

		// creates task collaborators from project collaborators to add them to the
		// TaskTeam
		taskWorker1 = new TaskCollaborator(projectCollaborator1);
		taskWorker2 = new TaskCollaborator(projectCollaborator2);
		task1.addTaskCollaboratorToTask(taskWorker2);
		task1.addTaskCollaboratorToTask(taskWorker1);

		}	
		
		@After
		public void tearDown() {
		user1 = null;
		user2 = null;
		user3 = null;
		p1 = null;
		projectCollaborator1 = null;
		projectCollaborator2 = null;
		estimatedStartDate = null;
		taskDeadline = null;
		task1 = null;
		taskWorker1 = null;
		taskWorker2 = null; 	
			
		}
		
		@Test
		public void TestRemoveCollaboratorFromProjectController() {
			
		// creates controller to remove project collaborator from task
		RemoveCollaboratorFromProjectController controller = new RemoveCollaboratorFromProjectController();

		// removes collaborator using the controller
		controller.removeProjectCollaboratorFromProjectTeam(p1, user1);

		// asserts that after removal of project collaborator 1 (that corresponds to
		// user 1), the Project team
		assertEquals(2, p1.getProjectTeam().size());
		
		//Project collaborator 2 (the one removed) is still in Project Team
		assertTrue(projectCollaborator2.equals(p1.getProjectTeam().get(0)));
		
		//asserts that project collaborator 2 is not active in task (as it was removed from Project Team)
		assertFalse(p1.getTaskRepository().getAllTasksFromProjectCollaborator(projectCollaborator2).get(0).getTaskTeam()
				.get(0).isTaskCollaboratorActiveInTask());
		
		//asserts that project collaborator 1 is active in task (as only Project Collaborator 2 was removed/deactivated from Project Team)
				assertTrue(p1.getTaskRepository().getAllTasksFromProjectCollaborator(projectCollaborator1).get(0).getTaskTeam()
						.get(1).isTaskCollaboratorActiveInTask());
	}

}
