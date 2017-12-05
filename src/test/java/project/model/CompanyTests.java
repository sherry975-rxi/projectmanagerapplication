/**
 * 
 */
package test.java.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.Test;

import main.java.project.model.Company;
import main.java.project.model.Profile;
import main.java.project.model.Project;
import main.java.project.model.Task;
import main.java.project.model.User;

/**
 * 
 * 
 * @author Group3
 *
 */
class CompanyTests {

	Company myComp = Company.getTheInstance();

	/**
	 * Tests the addition of a Projects to a list of Projects
	 */
	@Test
	void testAddProjectToProjectList() {

		// Creates Company
		// Creates User
		User manager = new User("Joao", "abc@gmail.com", "123455", "Admin", "123456789");
		// Creates Project
		Project p1 = new Project("descricao", "descricao", manager);

		assertEquals(myComp.addProjectToProjectList(p1), true);
		assertEquals(myComp.addProjectToProjectList(p1), false);

	}

	/**
	 * Tests the addition of Users to a list of Users
	 */
	@Test
	void testAddUserToProjectList() {

		// Creates User
		User manager = new User("Joao", "abc@gmail.com", "123455", "Admin", "123456789");
		// Creates User
		User user = new User("Miguel", "xyzd@gmail.com", "12345", "Admin", "123456789");

		assertEquals(myComp.addUserToUserList(manager), true);
		assertEquals(myComp.addUserToUserList(manager), false);
		assertEquals(myComp.addUserToUserList(user), true);

	}

	/**
	 * Tests return of a list of Projects
	 */
	@Test
	void testGetProjectsList() {

		// Creates User
		User manager = new User("Joao", "abc@gmail.com", "123455", "Admin", "123456789");

		// Creates Project
		Project p1 = new Project("descricao", "descricao", manager);

		// add Project to Company Project List
		myComp.addProjectToProjectList(p1);
		List<Project> projectList = new ArrayList<Project>();

		// adds Project to arrayList, to compare withCompany Projects List
		projectList.add(p1);

		assertEquals(myComp.getProjectsList(), projectList);

	}

	/**
	 * Tests the return of a list of Users
	 */
	@Test
	void testGetUsersList() {

		// Creates User
		User manager = new User("Joao", "abc@gmail.com", "123455", "Admin", "123456789");

		// Creates another User
		User userTest = new User("Miguel", "xyz@gmail.com", "123255", "Admin", "123426789");

		// Adds user to list of users on Company
		myComp.addUserToUserList(manager);

		List<User> userList = new ArrayList<User>();
		// Creates a new list and adds user to that list, to compare with userList
		// inside Company
		userList.add(manager);

		assertEquals(myComp.getUsersList(), userList);

		// Adds another user to Company List
		myComp.addUserToUserList(userTest);

		// Adds user to compare list
		userList.add(userTest);

		assertEquals(myComp.getUsersList(), userList);

	}

	/**
	 * Tests the return of a list of active Projects
	 */
	@Test
	void testGetActiveProjectList() {

		// Creates new User
		User manager = new User("Joao", "abc@gmail.com", "123455", "Admin", "123456789");

		// Creates new Project
		Project p1 = new Project("descricao", "descricao", manager);

		// Creates list of Project
		List<Project> projectList = new ArrayList<Project>();

		// adds Project to projectList inside company.
		myComp.addProjectToProjectList(p1);

		assertEquals(myComp.getActiveProjectsList(), projectList);

		// adds project to a Project List, now that the project is active
		projectList.add(p1);

		p1.setProjectStatus(2);
		assertEquals(myComp.getActiveProjectsList(), projectList);

	}

	/**
	 * This tests if a given user exists in a company. This method is used in other
	 * methods
	 */

	@Test
	void testDoesUserExist() {
		User col = new User("Colab", "abc@gmail.com", "12333455", "Admin", "1234356789");

		User manager = new User("Joao", "xyz@gmail.com", "123455", "Admin", "123456789");

		assertEquals(myComp.doesUserExist(col), false);
		myComp.addUserToUserList(col);
		assertEquals(myComp.doesUserExist(col), true);
		assertEquals(myComp.doesUserExist(manager), false);
		myComp.addUserToUserList(manager);
		assertEquals(myComp.doesUserExist(manager), true);

	}

	/**
	 * Tests if a string of text is contained in the field email of a user this
	 * method returns a list of users that contains the string typed
	 */
	@Test
	void testSearchUserByEmail() {

		User col = new User("Colab", "abc@gmail.com", "12333455", "Admin", "1234356789");
		User manager = new User("Joao", "adbc@gmail.com", "123455", "Admin", "123456789");

		List<User> usersList = new ArrayList<User>();

		usersList.add(col);
		myComp.addUserToUserList(col);
		myComp.addUserToUserList(manager);

		assertEquals(myComp.searchUsersByEmail("ab"), usersList);

	}

	@Test

	/**
	 * This tests the return
	 */
	void testSearchUsersByProfile() {

		User col = new User("Colab", "abc@gmail.com", "12333455", "Admin", "1234356789");
		User manager = new User("Joao", "abcdf@gmail.com", "123455", "Admin", "123456789");
		User noProfileUser = new User("Joao", "abcd@gmail.com", "123455", "Admin", "123456789");
		User nullTwo = new User("Josao", "abcedd@gmail.com", "1234a55", "Admina", "123a45s6789");

		myComp.addUserToUserList(manager);
		myComp.addUserToUserList(col);
		myComp.addUserToUserList(noProfileUser);
		myComp.addUserToUserList(nullTwo);

		List<User> listDirector = new ArrayList<User>();
		List<User> listCollaborator = new ArrayList<User>();
		List<User> listNoProfile = new ArrayList<User>();

		Profile p1 = new Profile();
		p1.setCollaborator();
		col.setUserProfile(Profile.COLLABORATOR);
		listCollaborator.add(col);

		Profile p2 = new Profile();
		p2.setDirector();
		manager.setUserProfile(Profile.DIRECTOR);
		listDirector.add(manager);

		Profile p3 = new Profile();
		p3.setVisitor();
		noProfileUser.setUserProfile(Profile.VISITOR);
		nullTwo.setUserProfile(Profile.VISITOR);
		listNoProfile.add(noProfileUser);
		listNoProfile.add(nullTwo);

		assertEquals(myComp.searchUsersByProfile(p1), listCollaborator);
		assertEquals(myComp.searchUsersByProfile(p2), listDirector);
		assertEquals(myComp.searchUsersByProfile(p3), listNoProfile);

	}

	@Test
	void testAddUserToTask() {

		User u1 = new User("Daniel", "daniel@gmail.com", "01", "Programador", "910000000");
		User u2 = new User("Daniel", "daniel@gmail.com", "01", "Programador", "910000000");

		Project p1 = new Project("descricao", "descricao", u1);
		Project p2 = new Project("descricao", "descricao", u1);

		Task t1 = new Task(p1, "Task 1"); // associates to u1 e u2

		myComp.addProjectToProjectList(p1);
		assertEquals(t1.addUserToTask(u1), true);
		assertEquals(t1.addUserToTask(u2), false);

	}

	/*
	 * /** This test creates a company, projects, users and tasks. Adds projects and
	 * users to that company Adds users to tasks and add those tasks to projects
	 * Then gets all the tasks of a specific user
	 */
	@Test
	void testGetTaskListOfSpecificUser() {

		User u1 = new User("Daniel", "daniel@gmail.com", "01", "Programador", "910000000");
		User u2 = new User("Rita", "rita@gmail.com", "02", "Gestora de Projeto", "920000000");
		User u3 = new User("André", "andre@gmail.com", "03", "Programador", "930000000");
		User u4 = new User("Ricardo", "ricardo@gmail.com", "04", "Gestor de Projeto", "940000000");

		// Adds two users to the Company user list.
		myComp.addUserToUserList(u1);
		myComp.addUserToUserList(u2);
		myComp.addUserToUserList(u3);
		myComp.addUserToUserList(u4);

		// Two projects created
		Project p1 = new Project("descricao", "descricao", u3);
		Project p2 = new Project("descricao2", "descricao2", u3);

		// Project 1 and Project 2 were added to the Company List.
		myComp.addProjectToProjectList(p1);
		myComp.addProjectToProjectList(p2);

		p1.addUserToProjectTeam(u1);

		// Four tasks were created and the users were added to them.
		Task t1 = new Task(p1, "Task 1"); // associates to u1 e u2

		Task t2 = new Task(p1, "Task 2"); // associates to u1
		Task t3 = new Task(p1, "Task 3"); // associates to u3 e u4

		Task t4 = new Task(p1, "Task 4"); // associates to u2

		// Tasks t1 and t4 were added to project 1
		p1.addProjectTask(t1);
		p1.addProjectTask(t4);

		// Tasks t2 and t3 were added to project 2.
		p2.addProjectTask(t2);
		p2.addProjectTask(t3);
		t1.addUserToTask(u1);

		/*
		 * p1.addUserToTaskInProject(u2, t4); p1.addUserToTaskInProject(u1, t1);
		 * p1.addUserToTaskInProject(u2, t4); p1.addUserToTaskInProject(u1, t1);
		 * 
		 * p1.addUserToTaskInProject(u2, t1); p1.addUserToTaskInProject(u3, t1);
		 * p1.addUserToTaskInProject(u4, t3);
		 */

		t1.addUserToTask(u1);
		Calendar s = Calendar.getInstance();
		Calendar t = Calendar.getInstance();

		s.add(Calendar.MONTH, -3);
		t.add(Calendar.MONTH, -2);

		t1.setStartDate(s);
		t1.setFinishDate(t);

		// Tasks were marked as Finished.
		t1.markTaskAsFinished();
		t3.markTaskAsFinished();

		// List to compare to the getMytaskListSortedDecreasingOrder.
		List<Task> expResult = new ArrayList<Task>();
		expResult.add(t1);
		assertEquals(expResult, myComp.getUserTaskList(u1));
	}

	@Test
	void getFinishedTasksByUser() {

		User u1 = new User("Daniel", "daniel@gmail.com", "01", "Programador", "910000000");
		User u2 = new User("Rita", "rita@gmail.com", "02", "Gestora de Projeto", "920000000");

		// Adds two users to the Company user list.
		myComp.addUserToUserList(u1);
		myComp.addUserToUserList(u2);

		// Project 1 created
		Project p1 = new Project("Projecto 1", "Software de Gestão", u2);

		// Project 1 added to the Company List.
		myComp.addProjectToProjectList(p1);

		// Add User to TeamList
		p1.addUserToProjectTeam(u1);

		// Create four tasks
		Task t1 = new Task(p1, "Task 1");
		Task t2 = new Task(p1, "Task 2");
		Task t3 = new Task(p1, "Task 3");
		Task t4 = new Task(p1, "Task 4");

		// Add Tasks to project 1
		p1.addProjectTask(t1);
		p1.addProjectTask(t2);
		p1.addProjectTask(t3);
		p1.addProjectTask(t4);

		// Associates users to task
		t1.addUserToTask(u1);
		t2.addUserToTask(u1);
		t3.addUserToTask(u1);
		t4.addUserToTask(u1);

		// Generate a Start Calendar
		Calendar s = Calendar.getInstance();
		s.add(Calendar.MONTH, -3);

		// Tasks start date added.
		t1.setStartDate(s);
		t2.setStartDate(s);
		t3.setStartDate(s);
		t4.setStartDate(s);

		// Generates a Finish Calendar
		Calendar f = Calendar.getInstance();
		f.add(Calendar.MONTH, -1);
		Calendar h = Calendar.getInstance();
		h.add(Calendar.MONTH, -2);

		// Tasks finish date added .
		// t1.setFinishDate(h);
		t2.setFinishDate(f);
		t3.setFinishDate(f);

		// Tasks were marked as Finished.
		// t1.markTaskAsFinished();
		t2.markTaskAsFinished();
		t3.markTaskAsFinished();

		// List to compare to the getLastMonthFinishedUserTaskList.
		List<Task> expResult = new ArrayList<Task>();
		// expResult.add(t1);
		expResult.add(t2);
		expResult.add(t3);

		double expectTotalTime = t2.getTimeSpentOnTask() + t3.getTimeSpentOnTask();

		assertEquals(expResult, myComp.getFinishedUserTaskList(u1));
		// assertEquals(expectTotalTime,
		// c1.getTotalTimeLastMonthFinishedTasksByUser(u1), 0.000000001);

	}

	/**
	 * RUI
	 */
	@Test
	void getUnfinishedTasksByUser() {

		User u1 = new User("Daniel", "daniel@gmail.com", "01", "Programador", "910000000");
		User u2 = new User("Rita", "rita@gmail.com", "02", "Gestora de Projeto", "920000000");

		// Adds two users to the Company user list.
		myComp.addUserToUserList(u1);
		myComp.addUserToUserList(u2);

		// Project 1 created
		Project p1 = new Project("Projecto 1", "Software de Gestão", u2);

		// Project 1 added to the Company List.
		myComp.addProjectToProjectList(p1);

		// Add User to TeamList
		p1.addUserToProjectTeam(u2);

		// Create four tasks
		Task t1 = new Task(p1, "Task 1");
		Task t2 = new Task(p1, "Task 2");
		Task t3 = new Task(p1, "Task 3");
		Task t4 = new Task(p1, "Task 4");

		// Add Tasks to project 1
		p1.addProjectTask(t1);
		p1.addProjectTask(t2);
		p1.addProjectTask(t3);
		p1.addProjectTask(t4);

		// Associates users to task
		t1.addUserToTask(u1);
		t2.addUserToTask(u1);
		t3.addUserToTask(u1);
		t4.addUserToTask(u1);

		// Generate a Start Calendar
		Calendar s = Calendar.getInstance();
		s.add(Calendar.MONTH, -3);

		// Tasks start date added.
		t1.setStartDate(s);
		t2.setStartDate(s);
		t3.setStartDate(s);
		t4.setStartDate(s);

		// Generates a Finish Calendar
		Calendar f = Calendar.getInstance();
		f.add(Calendar.MONTH, -1);
		Calendar h = Calendar.getInstance();
		h.add(Calendar.MONTH, -2);

		// Tasks finish date added .
		// t1.setFinishDate(h);
		t2.setFinishDate(f);
		t3.setFinishDate(f);

		// Tasks were marked as Finished.
		// t1.markTaskAsFinished();
		t2.markTaskAsFinished();
		t3.markTaskAsFinished();

		// List to compare to the getLastMonthFinishedUserTaskList.
		List<Task> expResult = new ArrayList<Task>();
		expResult.add(t1);
		// expResult.add(t2);
		// expResult.add(t3);

		// double expectTotalTime = t2.getTimeSpentOnTask() + t3.getTimeSpentOnTask();

		assertEquals(expResult, myComp.getUnfinishedUserTaskList(u1));
		// assertEquals(expectTotalTime,
		// c1.getTotalTimeLastMonthFinishedTasksByUser(u1), 0.000000001);

	}

	/**
	 * RUI Changes Months This test creates a company, projects, users and tasks.
	 * Adds projects and users to that company Adds users to tasks and add those
	 * tasks to projects Defines a start date for tasks and defines also a finish
	 * date Then gets the tasks marked as "finished" of a specific user Changes the
	 * finish date of some tasks Gets the sum of the Time Spent in each task
	 * finished by a specific user
	 */
	@Test
	void getTotalTimeLastMonthFinishedTasksByUser() {

		User u1 = new User("Daniel", "daniel@gmail.com", "01", "Programador", "910000000");
		User u2 = new User("Rita", "rita@gmail.com", "02", "Gestora de Projeto", "920000000");

		// Adds two users to the Company user list.
		myComp.addUserToUserList(u1);
		myComp.addUserToUserList(u2);

		// Project 1 created
		Project p1 = new Project("Projecto 1", "Software de Gestão", u2);

		// Project 1 added to the Company List.
		myComp.addProjectToProjectList(p1);

		// Add User to TeamList
		p1.addUserToProjectTeam(u1);

		// Create four tasks
		Task t1 = new Task(p1, "Task 1");
		Task t2 = new Task(p1, "Task 2");
		Task t3 = new Task(p1, "Task 3");
		Task t4 = new Task(p1, "Task 4");

		// Add Tasks to project 1
		p1.addProjectTask(t1);
		p1.addProjectTask(t2);
		p1.addProjectTask(t3);
		p1.addProjectTask(t4);

		// Associates users to task
		t1.addUserToTask(u1);
		t2.addUserToTask(u1);
		t3.addUserToTask(u1);
		t4.addUserToTask(u1);

		// Generate a Start Calendar
		Calendar s = Calendar.getInstance();
		s.add(Calendar.MONTH, -3);

		// Tasks start date added.
		t1.setStartDate(s);
		t2.setStartDate(s);
		t3.setStartDate(s);
		t4.setStartDate(s);

		// Generates a Finish Calendar
		Calendar f = Calendar.getInstance();
		f.add(Calendar.MONTH, -1);
		Calendar h = Calendar.getInstance();
		h.add(Calendar.MONTH, -2);

		// Tasks finish date added .
		t1.setFinishDate(h);
		t2.setFinishDate(f);
		t3.setFinishDate(f);

		// Tasks were marked as Finished.
		t1.markTaskAsFinished();
		t2.markTaskAsFinished();
		t3.markTaskAsFinished();

		// List to compare to the getLastMonthFinishedUserTaskList.
		List<Task> expResult = new ArrayList<Task>();
		// expResult.add(t1);
		expResult.add(t2);
		expResult.add(t3);

		double expectTotalTime = t2.getTimeSpentOnTask() + t3.getTimeSpentOnTask();

		// assertEquals(expResult, c1.getLastMonthFinishedUserTaskList(u1));
		assertEquals(expectTotalTime, myComp.getTotalTimeLastMonthFinishedTasksByUser(u1), 0.000000001);

	}

	/**
	 * RUI Changes Months and hours
	 */
	@Test
	void getTotalTimeLastMonthFinishedTasksByUser_1() {

		User u1 = new User("Daniel", "daniel@gmail.com", "01", "Programador", "910000000");
		User u2 = new User("Rita", "rita@gmail.com", "02", "Gestora de Projeto", "920000000");

		// Adds two users to the Company user list.
		myComp.addUserToUserList(u1);
		myComp.addUserToUserList(u2);

		// Project 1 created
		Project p1 = new Project("Projecto 1", "Software de Gestão", u2);

		// Project 1 added to the Company List.
		myComp.addProjectToProjectList(p1);

		// Add User to TeamList
		p1.addUserToProjectTeam(u1);

		// Create four tasks
		Task t1 = new Task(p1, "Task 1");
		Task t2 = new Task(p1, "Task 2");
		Task t3 = new Task(p1, "Task 3");
		Task t4 = new Task(p1, "Task 4");

		// Add Tasks to project 1
		p1.addProjectTask(t1);
		p1.addProjectTask(t2);
		p1.addProjectTask(t3);
		p1.addProjectTask(t4);

		// Associates users to task
		t1.addUserToTask(u1);
		t2.addUserToTask(u1);
		t3.addUserToTask(u1);
		t4.addUserToTask(u1);

		// Generate a Start Calendar
		Calendar s = Calendar.getInstance();
		s.add(Calendar.MONTH, -3);

		// Tasks start date added.
		t1.setStartDate(s);
		t2.setStartDate(s);
		t3.setStartDate(s);
		t4.setStartDate(s);

		// Generates a Finish Calendar
		Calendar h = Calendar.getInstance();
		h.add(Calendar.MONTH, -2);
		Calendar f = Calendar.getInstance();
		f.add(Calendar.MONTH, -1);
		Calendar g = Calendar.getInstance();
		g.add(Calendar.MONTH, -1);
		g.add(Calendar.HOUR_OF_DAY, 1);

		// Tasks finish date added .
		t1.setFinishDate(h);
		t2.setFinishDate(f);
		t3.setFinishDate(g);

		// Tasks were marked as Finished.
		t1.markTaskAsFinished();
		t2.markTaskAsFinished();
		t3.markTaskAsFinished();

		// List to compare to the getLastMonthFinishedUserTaskList.
		List<Task> expResult = new ArrayList<Task>();
		// expResult.add(t1);
		expResult.add(t2);
		expResult.add(t3);

		double expectTotalTime = t2.getTimeSpentOnTask() + t3.getTimeSpentOnTask();

		// assertEquals(expResult, c1.getFinishedUserTaskList(u1));
		assertEquals(expectTotalTime, myComp.getTotalTimeLastMonthFinishedTasksByUser(u1), 0.000000001);

	}

	/**
	 * RUI Changes Months
	 */
	@Test
	void getAverageTimeLastMonthFinishedTasksUser() {

		User u1 = new User("Daniel", "daniel@gmail.com", "01", "Programador", "910000000");
		User u2 = new User("Rita", "rita@gmail.com", "02", "Gestora de Projeto", "920000000");

		// Adds two users to the Company user list.
		myComp.addUserToUserList(u1);
		myComp.addUserToUserList(u2);

		// Project 1 created
		Project p1 = new Project("Projecto 1", "Software de Gestão", u2);

		// Project 1 added to the Company List.
		myComp.addProjectToProjectList(p1);

		// Add User to TeamList
		p1.addUserToProjectTeam(u1);

		// Create four tasks
		Task t1 = new Task(p1, "Task 1");
		Task t2 = new Task(p1, "Task 2");
		Task t3 = new Task(p1, "Task 3");
		Task t4 = new Task(p1, "Task 4");

		// Add Tasks to project 1
		p1.addProjectTask(t1);
		p1.addProjectTask(t2);
		p1.addProjectTask(t3);
		p1.addProjectTask(t4);

		// Associates users to task
		t1.addUserToTask(u1);
		t2.addUserToTask(u1);
		t3.addUserToTask(u1);
		t4.addUserToTask(u1);

		// Generate a Start Calendar
		Calendar s = Calendar.getInstance();
		s.add(Calendar.MONTH, -3);

		// Tasks start date added.
		t1.setStartDate(s);
		t2.setStartDate(s);
		t3.setStartDate(s);
		t4.setStartDate(s);

		// Generates a Finish Calendar
		Calendar f = Calendar.getInstance();
		f.add(Calendar.MONTH, -1);
		Calendar h = Calendar.getInstance();
		h.add(Calendar.MONTH, -2);

		// Tasks finish date added .
		t1.setFinishDate(h);
		t2.setFinishDate(f);
		t3.setFinishDate(f);

		// Tasks were marked as Finished.
		t1.markTaskAsFinished();
		t2.markTaskAsFinished();
		t3.markTaskAsFinished();

		// List to compare to the getLastMonthFinishedUserTaskList.
		List<Task> expResult = new ArrayList<Task>();
		// expResult.add(t1);
		expResult.add(t2);
		expResult.add(t3);

		double expectTotalTime = (t2.getTimeSpentOnTask() + t3.getTimeSpentOnTask()) / 2;

		// assertEquals(expResult, c1.getFinishedUserTaskList(u1));
		assertEquals(expectTotalTime, myComp.getAverageTimeLastMonthFinishedTasksUser(u1), 0.000000001);

	}

	@Test
	void testGetLastMonthFinishedUserTaskList() {

		User u1 = new User("Daniel", "daniel@gmail.com", "01", "Programador", "910000000");
		User u2 = new User("Rita", "rita@gmail.com", "02", "Gestora de Projeto", "920000000");

		// Adds two users to the Company user list.
		myComp.addUserToUserList(u1);
		myComp.addUserToUserList(u2);

		// Projects created
		Project p1 = new Project("Projecto 1", "Software de Gestão", u2);

		// Add User to projectList
		p1.addUserToProjectTeam(u1);

		// Project 1 was added to the Company List.
		myComp.addProjectToProjectList(p1);

		// Four tasks were created and the users were added to them.
		Task t1 = new Task(p1, "tasking"); // associates to u1
		t1.addUserToTask(u1);

		Task t2 = new Task(p1, "tasking"); // associates to u1
		t2.addUserToTask(u1);
		Task t3 = new Task(p1, "tasking"); // associates to u1
		t3.addUserToTask(u1);
		Task t4 = new Task(p1, "tasking"); // associates to u2
		t4.addUserToTask(u1);

		// Tasks t1 to t4 were added to project 1
		p1.addProjectTask(t1);
		p1.addProjectTask(t2);
		p1.addProjectTask(t3);
		p1.addProjectTask(t4);

		t1.addUserToTask(u1);
		t2.addUserToTask(u1);
		t3.addUserToTask(u1);
		t4.addUserToTask(u1);

		// Start Calendar
		Calendar s = Calendar.getInstance();
		s.add(Calendar.MONTH, -2);

		// Tasks creation date added last month.
		t1.setStartDate(s);
		t2.setStartDate(s);

		// Finish Calendar
		Calendar f = Calendar.getInstance();
		f.add(Calendar.MONTH, -1);

		// Tasks finish date added last month.
		t1.setFinishDate(f);
		t2.setFinishDate(f);

		// Tasks were marked as Finished.
		t1.markTaskAsFinished();
		t2.markTaskAsFinished();
		t3.markTaskAsFinished();

		// List to compare to the getMytaskListSortedDecreasingOrder.
		List<Task> expResult = new ArrayList<Task>();
		expResult.add(t1);
		expResult.add(t2);

		assertEquals(expResult, myComp.getLastMonthFinishedUserTaskList(u1));

	}

	@Test

	void addUserToTask() {

	}

	@Test
	void testGetLastMonthFinishedUserTaskListDecreasingOrder() {

		User u1 = new User("Daniel", "daniel@gmail.com", "01", "Programador", "910000000");
		User u2 = new User("Rita", "rita@gmail.com", "02", "Gestora de Projeto", "920000000");
		myComp.getFinishedTaskListByDecreasingOrder(u1).clear();

		// Adds two users to the Company user list.
		myComp.addUserToUserList(u1);
		myComp.addUserToUserList(u2);

		// Projects created
		Project p1 = new Project("Projecto 1", "Software de Gestão", u2);

		// Add User to projectList
		p1.addUserToProjectTeam(u1);

		// Project 1 was added to the Company List.
		myComp.addProjectToProjectList(p1);

		// Ten tasks were created and the users were added to them.
		Task t1 = new Task(p1, "tasking"); // associates to u1
		Task t2 = new Task(p1, "tasking"); // associates to u1
		Task t3 = new Task(p1, "tasking"); // associates to u1
		Task t4 = new Task(p1, "tasking"); // associates to u2
		Task t5 = new Task(p1, "tasking"); // associates to u1
		Task t6 = new Task(p1, "tasking"); // associates to u1
		Task t7 = new Task(p1, "tasking"); // associates to u1
		Task t8 = new Task(p1, "tasking"); // associates to u1
		Task t9 = new Task(p1, "tasking"); // associates to u1
		Task t10 = new Task(p1, "tasking"); // associates to u1

		// Tasks t1 to t4 were added to project 1
		p1.addProjectTask(t1);
		p1.addProjectTask(t2);
		p1.addProjectTask(t3);
		p1.addProjectTask(t4);
		p1.addProjectTask(t5);
		p1.addProjectTask(t6);
		p1.addProjectTask(t7);
		p1.addProjectTask(t8);
		p1.addProjectTask(t9);
		p1.addProjectTask(t10);

		t1.addUserToTask(u1);
		t2.addUserToTask(u1);
		t3.addUserToTask(u1);
		t4.addUserToTask(u1);
		t5.addUserToTask(u1);
		t6.addUserToTask(u1);
		t7.addUserToTask(u1);
		t8.addUserToTask(u1);
		t9.addUserToTask(u1);
		t10.addUserToTask(u1);

		// Start Calendar
		Calendar s = Calendar.getInstance();
		s.add(Calendar.MONTH, -2);

		// Tasks creation date added last month.
		t1.setStartDate(s);
		t2.setStartDate(s);
		t3.setStartDate(s);
		t6.setStartDate(s);
		t8.setStartDate(s);
		t10.setStartDate(s);

		// Finish Calendar
		Calendar f = Calendar.getInstance();
		f.add(Calendar.MONTH, -1);

		// Tasks finish date added last month.
		t10.setFinishDate(f);
		t8.setFinishDate(f);
		t6.setFinishDate(f);
		t3.setFinishDate(f);
		t2.setFinishDate(f);
		t1.setFinishDate(f);

		// Tasks were marked as Finished.
		t10.markTaskAsFinished();
		t8.markTaskAsFinished();
		t6.markTaskAsFinished();
		t3.markTaskAsFinished();
		t2.markTaskAsFinished();
		t1.markTaskAsFinished();

		// List to compare to the getMytaskListSortedDecreasingOrder.
		List<Task> expResult = new ArrayList<Task>();
		expResult.clear();
		expResult.add(t1);

		expResult.add(t2);
		expResult.add(t3);
		expResult.add(t6);
		expResult.add(t8);
		expResult.add(t10);

		assertEquals(expResult, myComp.getFinishedTaskListByDecreasingOrder(u1));

	}

	/**
	 * This tests checks if an email that is typed by the user is valid or not. It
	 * uses an external library, called JavaMailAPI
	 */

	@Test
	void testUserEmailValid() {

		// Creates users

		User u1 = new User("Daniel", "daniel@gmail.com", "01", "Programador", "910000000");
		User u2 = new User("Joao", "joaogmail.com", "01", "Programador", "910000000");
		User u3 = new User("Maria", "maria@mail", "01", "Programador", "910000000");
		User u4 = new User("Maria", "@mail.com", "01", "Programador", "910000000");
		User u5 = new User("Carolina", "carol|>/ina@mail.com", "02", "Programador", "910000000");

		assertEquals(myComp.isEmailAddressValid(u1.getEmail()), true);
		assertEquals(myComp.isEmailAddressValid(u2.getEmail()), false);
		assertEquals(myComp.isEmailAddressValid(u3.getEmail()), true);
		assertEquals(myComp.isEmailAddressValid(u4.getEmail()), false);
		assertEquals(myComp.isEmailAddressValid(u5.getEmail()), false);

	}

	@Test
	void testSortFinishedUserTaskListDecreasingOrder() {

		User u1 = new User("Daniel", "daniel@gmail.com", "01", "Programador", "910000000");
		User u2 = new User("Rita", "rita@gmail.com", "02", "Gestora de Projeto", "920000000");

		// Adds two users to the Company user list.
		myComp.addUserToUserList(u1);
		myComp.addUserToUserList(u2);

		// Projects created
		Project p1 = new Project("Projecto 1", "Software de Gestão", u2);

		// Add User to projectList
		p1.addUserToProjectTeam(u1);

		// Project 1 was added to the Company List.
		myComp.addProjectToProjectList(p1);

		// Ten tasks were created and the users were added to them.
		Task t1 = new Task(p1, "Task 1"); // associates to u1

		Task t2 = new Task(p1, "Task 2"); // associates to u1
		Task t3 = new Task(p1, "Task 3"); // associates to u1
		Task t4 = new Task(p1, "Task 4"); // associates to u2
		Task t5 = new Task(p1, "Task 5"); // associates to u1
		Task t6 = new Task(p1, "Task 6"); // associates to u1
		Task t7 = new Task(p1, "Task 7"); // associates to u1
		Task t8 = new Task(p1, "Task 8"); // associates to u1
		Task t9 = new Task(p1, "Task 9"); // associates to u1
		Task t10 = new Task(p1, "Task 10"); // associates to u1

		// All tasks were added to project 1
		p1.addProjectTask(t1);
		p1.addProjectTask(t2);
		p1.addProjectTask(t3);
		p1.addProjectTask(t4);
		p1.addProjectTask(t5);
		p1.addProjectTask(t6);
		p1.addProjectTask(t7);
		p1.addProjectTask(t8);
		p1.addProjectTask(t9);
		p1.addProjectTask(t10);

		t1.addUserToTask(u1);
		t2.addUserToTask(u1);
		t3.addUserToTask(u1);
		t4.addUserToTask(u1);
		t5.addUserToTask(u1);
		t6.addUserToTask(u1);
		t7.addUserToTask(u1);
		t8.addUserToTask(u1);
		t9.addUserToTask(u1);
		t10.addUserToTask(u1);

		// Start Calendar
		Calendar s = Calendar.getInstance();
		s.set(2017, 11, 14);

		// Tasks creation date added last month.
		t1.setStartDate(s);// 14-11-2017
		t2.setStartDate(s);//
		s.add(Calendar.DAY_OF_MONTH, 1);
		t3.setStartDate(s);// 15-11-2017
		t6.setStartDate(s);
		s.add(Calendar.DAY_OF_MONTH, 1);// 16-11-2017
		t8.setStartDate(s);
		s.add(Calendar.YEAR, -1);// 16-11-2016
		t10.setStartDate(s);

		// Finish Calendar
		s.set(2017, 10, 17);

		// Tasks finish date added last month.
		t1.setFinishDate(s);// 17-11-2017
		s.add(Calendar.DAY_OF_MONTH, 1);
		t2.setFinishDate(s);// 17-11-2017
		s.add(Calendar.DAY_OF_MONTH, 1);
		t3.setFinishDate(s);// 19-11-2017
		s.add(Calendar.DAY_OF_MONTH, 1);
		t6.setFinishDate(s);// 20-11-2017
		s.add(Calendar.DAY_OF_MONTH, 1);
		t8.setFinishDate(s);// 21-11-2017
		s.add(Calendar.YEAR, -1);
		t10.setFinishDate(s);// 21-11-2016

		// Tasks were marked as Finished.
		t10.markTaskAsFinished();
		t8.markTaskAsFinished();
		t6.markTaskAsFinished();
		t3.markTaskAsFinished();
		t2.markTaskAsFinished();
		t1.markTaskAsFinished();

		// List to compare to the getMytaskListSortedDecreasingOrder.
		List<Task> Result = new ArrayList<Task>();

		Result.add(t8);
		Result.add(t6);
		Result.add(t3);
		Result.add(t2);
		Result.add(t1);
		Result.add(t10);
		List<Task> actual = myComp.getFinishedTaskListByDecreasingOrder(u1);
		assertEquals(Result, actual);

	}

}
