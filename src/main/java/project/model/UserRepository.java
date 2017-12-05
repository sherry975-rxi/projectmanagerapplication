package main.java.project.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class UserRepository that contains all lists and methods to build lists of users
 * 
 * @author Group3
 *
 */
public class UserRepository {

		private List<User> usersList;
		

		/**
		 * Constructor for UserRepository includes usersList creation 
		 */

		public UserRepository() {
			this.usersList = new ArrayList<User>();
		}

		/**
		 * Creates an instance of User
		 * 
		 * @param name
		 * @param email
		 * @param idNumber
		 * @param function
		 * @param phone
		 * 
		 * @return the project created
		 */
		public User createUser(String name, String email, String idNumber, String function, String phone, String street,
				String zipCode, String city, String district, String country) {
			User newUser = new User(name, email, idNumber, function, phone);
			Address newAddress = newUser.createAddress(street, zipCode, city, district, country);
			newUser.addAddress(newAddress);

			return newUser;

		}

		/**
		 * Creates an instance of Project
		 * 
		 * @param Company
		 *            (unique instance of Company)
		 * @param name
		 * @param description
		 * @param projectManager
		 *            (User Object)
		 * 
		 * @return the project created
		 */
		public Project createProject(String name, String description, User projectManager) {

			Project newProject = new Project(name, description, projectManager);

			return newProject;

		}

		/**
		 * Gets the counter of the number of Projects created
		 * 
		 * @return number of projects created
		 */
		public int getCounter() {
			return this.counter;
		}

		/**
		 * Sets the counter of the number of Projects created
		 * 
		 * @parameter number of projects created
		 */
		public void setCounter(int setCounter) {
			this.counter = setCounter;
		}

		/**
		 * This method adds project to projectsList if project doesn't exit
		 * 
		 * @param toAddProject
		 *            Project added to the List of Projects
		 */
		public boolean addProjectToProjectList(Project toAddProject) {
			if (!this.projectsList.contains(toAddProject)) {
				this.projectsList.add(toAddProject);
				return true;
			}
			return false;
		}

		/**
		 * This method adds users to the usersList if the user doesn't exists
		 * 
		 * @param toAddUsers
		 *            User added to the List of Users
		 */
		public boolean addUserToUserList(User toAddUsers) {
			if (!this.usersList.contains(toAddUsers)) {
				this.usersList.add(toAddUsers);
				return true;
			}
			return false;
		}

		/**
		 * This method returns the list of projects (projectsList)
		 * 
		 * @return projectsList This is the List of all Projects created
		 */
		public List<Project> getProjectsList() {
			return this.projectsList;
		}

		/**
		 * This method returns the list of users (usersList)
		 * 
		 * @return usersList This is the List of all Users created
		 */
		public List<User> getUsersList() {
			return this.usersList;
		}

		/**
		 * This method returns a list of active Projects. For each Project in the list
		 * of projects (projectList on the user task list), this method will check if
		 * the Project is active. If active equals true (the project is active) the
		 * projectsList list adds that Project.
		 * 
		 * @return activeProjectsList The List of active Projects
		 */
		public List<Project> getActiveProjectsList() {

			List<Project> activeProjectsList = new ArrayList<Project>();

			for (Project other : this.projectsList) {

				if (other.getProjectStatus() == 1 || other.getProjectStatus() == 2 || other.getProjectStatus() == 3
						|| other.getProjectStatus() == 4) {
					activeProjectsList.add(other);
				}

			}
			return activeProjectsList;
		}

		/**
		 * This method allows the administrator to see if a given user already exists in
		 * company
		 * 
		 * @param user
		 *            user
		 * @return boolean; TRUE if user exists in company FALSE if user doesn't exist
		 *         in company
		 * 
		 */

		public boolean doesUserExist(User user) {
			if (this.getUsersList().contains(user)) {
				return true;
			} else {
				return false;
			}

		}

		/**
		 * This method allows the administrator to access the user list and search users
		 * by email. This is achieved by using the .contains() method.
		 */
		/**
		 * @param email
		 *            user email
		 * 
		 * @return list with users that have the required email
		 */
		public List<User> searchUsersByEmail(String email) {

			List<User> userListThatContainsEmailString = new ArrayList<User>();

			for (int i = 0; i < this.usersList.size(); i++) {
				if (usersList.get(i).getEmail().contains(email)) {
					userListThatContainsEmailString.add(usersList.get(i));
				}

			}

			return userListThatContainsEmailString;

		}

		/**
		 * This method allows the administrator to access the user list and search users
		 * by profile.
		 * 
		 * @param searchProfile
		 *            Profile of a user
		 * @return A list of users with a certain profile
		 * 
		 */
		public List<User> searchUsersByProfile(Profile searchProfile) {

			List<User> usersByProfileList = new ArrayList<User>();

			for (int i = 0; i < this.getUsersList().size(); i++) {
				if (searchProfile.getProfileInt() == this.getUsersList().get(i).getProfile().getProfileInt()) {
					usersByProfileList.add(this.getUsersList().get(i));

				}
			}

			return usersByProfileList;

		}

		/**
		 * This method returns all the tasks from all the projects, that has a specific
		 * user associated to that task, no matter if the project is active or not, if
		 * the task is finished or not.
		 * 
		 * @param specificUser
		 *            user to search the tasks in which it is included
		 * 
		 * @return Returns the user task list.
		 */
		public List<Task> getUserTaskList(User specificUser) {
			List<Task> tasksOfSpecificUser = new ArrayList<Task>();

			for (int indexProject = 0; indexProject < this.getProjectsList().size(); indexProject++) {
				if (this.getProjectsList().get(indexProject).containsUser(specificUser)) {
					tasksOfSpecificUser.addAll(this.getProjectsList().get(indexProject).getAllTasks(specificUser));
				}
			}

			return tasksOfSpecificUser;

		}

		/**
		 * This method returns all the tasks with state "finished" from all the
		 * projects, that has a specific user associated to that task, no matter if the
		 * project is active or not.
		 * 
		 * @param user
		 * 
		 * @return List of finished tasks of a specific user
		 */
		public List<Task> getFinishedUserTaskList(User user) {

			List<Task> finishedTasksOfSpecificUser = new ArrayList<Task>();
			for (Project test : this.projectsList) {
				if (test.containsUser(user)) {
					finishedTasksOfSpecificUser.addAll(test.getFinishedTaskListofUserInProject(user));
				}
			}
			return finishedTasksOfSpecificUser;
		}

		/**
		 * This method returns all the tasks with state "unfinished" from all the
		 * projects, that has a specific user associated to that task, no matter if the
		 * project is active or not.
		 * 
		 * @param user
		 * @return List of unfinished tasks of a specific user
		 */
		public List<Task> getUnfinishedUserTaskList(User user) {

			List<Task> unfinishedTasksOfSpecificUser = new ArrayList<Task>();
			for (Project test : this.projectsList) {
				if (test.containsUser(user)) {
					unfinishedTasksOfSpecificUser.addAll(test.getUnFinishedTaskList(user));
				}
			}
			return unfinishedTasksOfSpecificUser;
		}

		/**
		 * This method returns a list with the finished tasks of a certain user by
		 * decreasing order of date. First, this method creates a list which is a copy
		 * of the finished task list of the user. This method just reverses the initial
		 * order of the finishedTaskList. It does not runs a cycle to compare the tasks
		 * finish dates, neither analysis the finishedTaskList in any way.
		 * 
		 * @param user
		 * 
		 * @return Returns a list with the all the user finished tasks sorted by
		 *         decreasing order.
		 */

		public List<Task> getLastMonthFinishedUserTaskList(User user) {

			List<Task> lastMonthFinishedTaskListByUser = new ArrayList<Task>();
			for (Project test : this.projectsList) {
				if (test.containsUser(user)) {
					lastMonthFinishedTaskListByUser.addAll(test.getFinishedTaskListLastMonth(user));
				}
			}
			return lastMonthFinishedTaskListByUser;
		}

		/**
		 * This method returns the sum of the time spent in all the tasks that were
		 * marked as finished during the last month. So it runs a cycle to get the time
		 * spent on every task finished on last month, and sum one by one.
		 * 
		 * @param user
		 * 
		 * @return Returns total time spent doing tasks in the last month.
		 */
		public double getTotalTimeLastMonthFinishedTasksByUser(User user) {

			double totalTime = 0;
			for (Project test : this.projectsList) {
				if (test.containsUser(user)) {
					totalTime = totalTime + test.getTimeSpentOnLastMonthProjectUserTasks(user);
				}
			}

			return totalTime;

		}

		/**
		 * This method returns the average time spent by task during last month. This
		 * method gets the total time spent on every task finished on last month. Then
		 * it will divide that time by the number of tasks.
		 * 
		 * @param user
		 * 
		 * @return Returns the average time spent by finished task in the last month.
		 */
		public double getAverageTimeLastMonthFinishedTasksUser(User user) {

			double totalTime = this.getTotalTimeLastMonthFinishedTasksByUser(user);

			double average = totalTime / this.getLastMonthFinishedUserTaskList(user).size();

			return average;
		}

		/**
		 * This method returns a list with the tasks finished last month by decreasing
		 * order.
		 * 
		 * @param user
		 * 
		 * @return Returns a list with the tasks finished last month by decreasing
		 *         order.
		 */
		public List<Task> getLastMonthFinishedUserTaskListDecreasingOrder(User user) {

			List<Task> LastMonthFinishedUserTaskListDecreasingOrder = new ArrayList<Task>();
			LastMonthFinishedUserTaskListDecreasingOrder.addAll(this.getLastMonthFinishedUserTaskList(user));

			return this.sortTaskListDecreasingOrder(LastMonthFinishedUserTaskListDecreasingOrder);

		}

		/**
		 * This method returns a list with the finished tasks of a certain user by
		 * decreasing order of date. First, this method creates a list which is a copy
		 * of the finished task list of the user. This method just reverses the initial
		 * order of the finishedTaskList. It does not runs a cycle to compare the tasks
		 * finish dates, neither analysis the finishedTaskList in any way.
		 * 
		 * @param user
		 * 
		 * @return Returns a list with the all the user finished tasks sorted by
		 *         decreasing order.
		 */
		public List<Task> getFinishedTaskListByDecreasingOrder(User user) {

			List<Task> FinishedUserTaskListDecreasingOrder = new ArrayList<Task>();
			FinishedUserTaskListDecreasingOrder.addAll(this.getFinishedUserTaskList(user));

			return this.sortTaskListDecreasingOrder(FinishedUserTaskListDecreasingOrder);
		}

		/**
		 * This method returns a list with the tasks of a certain user by decreasing
		 * order of date. First, this method creates a list which is a copy of the task
		 * list of the user. This method just reverses the initial order of the
		 * TaskList. It does not runs a cycle to compare the tasks finish dates, neither
		 * analysis the TaskList in any way.
		 * 
		 * @param toSort
		 *            List of tasks to sort
		 * 
		 * @return sorted list
		 * 
		 */
		public List<Task> sortTaskListDecreasingOrder(List<Task> toSort) {
			List<Task> result = new ArrayList<>();
			result.addAll(toSort);
			for (int i = 0; i < result.size() - 1; i++) {
				for (int j = i + 1; j < result.size(); j++) {
					if (result.get(i).getFinishDate().before(result.get(j).getFinishDate())) {
						Task h = new Task(result.get(i));
						result.set(i, result.get(j));
						result.set(j, h);
					}
				}

			}
			return result;
		}

		/**
		 * This method checks if an email inserted by the user is valid or not
		 * 
		 * @param String
		 *            email
		 * @return TRUE if email is valid FALSE if email is invalid
		 */
		public boolean isEmailAddressValid(String email) {
			boolean result = true;

			try {
				InternetAddress emailAddr = new InternetAddress(email);
				emailAddr.validate();
			} catch (AddressException ex) {
				result = false;
			}
			return result;
		}

	}	
}
