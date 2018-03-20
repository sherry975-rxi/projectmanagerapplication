package project.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.Repository.ProjCollabRepository;
import project.Repository.TaskRepository;
import project.model.*;
import project.model.taskstateinterface.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {


	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private ProjCollabRepository projectCollaboratorRepository;

	public TaskService() { }

	/**
	 * Constructor created for JPA purposes.
	 * 
	 * @param taskRepository
	 */
	public TaskService(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

	/**
	 * Create a Task using Creation Pattern and saves the Task created in the DB
	 * 
	 * @param description
	 *            of the task
	 * @param selectedProject
	 *            is the project where the task will be associated
	 * @return A new Task object
	 */
	public Task createTask(String description, Project selectedProject) {

		Task newTask = selectedProject.createTask(description);

		int projectID = selectedProject.getId();
		int taskNumber = getProjectTasks(selectedProject).size()+1;
		String taskID = projectID  + "." + taskNumber;

		newTask.setTaskID(taskID);

		this.taskRepository.save(newTask);
		return newTask;
	}
	
	/**
	 * Get the complete task list for the project
	 * 
	 * @return Project Task List
	 */
	public List<Task> getTaskRepository() {
		return this.taskRepository.findAll();
	}

	/**
	 * Get the complete task list for the project
	 *
	 * @return Project Task List
	 */
	public List<Task> getProjectTasks(Project project) {
		return this.taskRepository.findAllByProject(project);
	}



	/**
	 * This method returns all the tasks from a certain user.
	 *
	 * @param user
	 *            user to search the tasks in which it is included
	 *
	 * @return Returns the user task list.
	 */
	public List<Task> getUserTasks(User user) {

		List<Task> userTasks = new ArrayList<>();

		// Iterates through the taskRepository
		for (Task task : this.getTaskRepository()) {
			// Iterates through the task team of every task in taskRepository
			for (TaskCollaborator taskCollab : task.getTaskTeam()) {
				User toCompare = taskCollab.getProjectCollaboratorFromTaskCollaborator().getCollaborator();
				// checks if the user is in the task and if the task was not already added to
				// the list
				if (user.equals(toCompare) && !userTasks.contains(task)) {
					userTasks.add(task);
				}
			}
		}

		return userTasks;
	}

	/**
	 * Gets all the tasks from a certain user, that have their current state as
	 * Finished.
	 * 
	 * @param user
	 *            user to search the tasks in which it is included.
	 *
	 * @return List of finished tasks of a specific user
	 */
	public List<Task> getAllFinishedTasksFromUser(User user) {

		List<Task> finishedTasks = new ArrayList<>();

		for (Task task : this.getUserTasks(user)) {
			if (task.getCurrentState() == StateEnum.FINISHED) {
				finishedTasks.add(task);
			}
		}

		return finishedTasks;
	}


	/**
	 * This method returns all the tasks from a certain user that do not have their
	 * current state equal to Finished.
	 *
	 * @param user
	 *            user to search the tasks in which it is included
	 *
	 * @return List of unfinished tasks of a specific user
	 */

	public List<Task> getUnfinishedUserTaskList(User user) {

		List<Task> unFinishedTasks = new ArrayList<>();

		for (Task task : this.getUserTasks(user)) {
			if (task.getCurrentState() != StateEnum.FINISHED) {
				unFinishedTasks.add(task);
			}
		}

		return unFinishedTasks;
	}

	/**
	 * This method returns all the Started tasks with state "unfinished" from all
	 * the projects, that has a specific user associated to that task.
	 *
	 * @param user
	 *            user to search the tasks in which it is included
	 *
	 * @return List of started but not finished tasks of a specific user
	 */
	public List<Task> getStartedNotFinishedUserTaskList(User user) {

		List<Task> startedNotFinishedTasks = new ArrayList<>();

		for (Task task : this.getUserTasks(user)) {
			if (task.getCurrentState() == StateEnum.ONGOING) {
				startedNotFinishedTasks.add(task);
			}
		}

		return startedNotFinishedTasks;
	}

	/*
	 * This method returns a list with the finished tasks of a certain user by
	 * decreasing order of date.
	 *
	 * @param user user to search the tasks in which it is included
	 *
	 * @return Returns a list with the all the user finished tasks sorted by
	 * decreasing order.
	 */

	public List<Task> getLastMonthFinishedUserTaskList(User user) {

		List<Task> lastMonthFinishedTasks = new ArrayList<>();
		Calendar givenMonth = Calendar.getInstance();
		givenMonth.add(Calendar.MONTH, -1);

		for (Task task : this.getAllFinishedTasksFromUser(user)) {
			if(task.getFinishDate().get(Calendar.MONTH) == givenMonth.get(Calendar.MONTH)) { 
				lastMonthFinishedTasks.add(task);
			}
		}
		return lastMonthFinishedTasks;
	}

	public List<Task> sortTaskListDecreasingOrder(List<Task> toSort) {		
	
		Collections.sort(toSort, new Comparator<Task>() {
            @Override
            public int compare(Task t1, Task t2) {
                return t2.getFinishDate().compareTo(t1.getFinishDate());
            }
        });
		return toSort;
	}

	public List<Task> sortTaskListByDeadline(List<Task> toSort) {
		Collections.sort(toSort, new Comparator<Task>() {
            @Override
            public int compare(Task t1, Task t2) {
                return t2.getTaskDeadline().compareTo(t1.getTaskDeadline());
            }
        });
		return toSort;
	}

	/**
	 * This method returns the sum of the time spent in all the tasks that were
	 * marked as finished during the last month.
	 *
	 * @param user
	 *            user to search the tasks in which it is included
	 *
	 * @return Returns total time spent doing tasks in the last month.
	 */
	public double getTotalTimeOfFinishedTasksFromUserLastMonth(User user) {

		double totalTime = 0;

		for (ProjectCollaborator projCollab : this.projectCollaboratorRepository.findAll()) {
			User toCompare = projCollab.getCollaborator();
			if(user.equals(toCompare)) {
						totalTime = totalTime + getTimeSpentByProjectCollaboratorInAllTasksLastMonth(projCollab);
			}
		}
		return totalTime;
	}

	/**
	 * This method returns the total time spent by a user in tasks from a project
	 * Last month
	 *
	 * @param collab ProjectCollaborator to search
	 * @return Time spent on last month project user tasks
	 */
	public double getTimeSpentByProjectCollaboratorInAllTasksLastMonth(ProjectCollaborator collab) {
		List<Task> lastMonth = new ArrayList<>();
		lastMonth.addAll(this.getLastMonthFinishedUserTaskList(collab.getUserFromProjectCollaborator()));
		double totalTime = 0;
		for (Task task : lastMonth) {
			totalTime += task.getTimeSpentByProjectCollaboratorOntask(collab);
		}
		return totalTime;
	}

	/**
	 * This method returns the average time spent by task during last month. This
	 * method gets the total time spent on every task finished on last month. Then
	 * it will divide that time by the number of tasks.
	 *
	 * @param user user for which to get the average time spent on tasks that were finished last month
	 *
	 * @return Returns the average time spent by finished task in the last month.
	 */
	public double getAverageTimeOfFinishedTasksFromUserLastMonth(User user) {

		double totalTime = this.getTotalTimeOfFinishedTasksFromUserLastMonth(user);

		return totalTime / this.getLastMonthFinishedUserTaskList(user).size();

	}

	/**
	 * This method returns a list with the tasks finished last month by decreasing
	 * order.
	 *
	 * @param user user for which to get the tasks that were finished last month in decreasing order of finish date
	 *
	 * @return Returns a list with the tasks finished last month by decreasing
	 *         order.
	 */
	public List<Task> getFinishedUserTasksFromLastMonthInDecreasingOrder(User user) {

		List<Task> lastMonthTasksDecOrder = new ArrayList<>();
		lastMonthTasksDecOrder.addAll(getLastMonthFinishedUserTaskList(user));

		return this.sortTaskListDecreasingOrder(lastMonthTasksDecOrder);
	}

	/**
	 * This method returns a list with the finished tasks of a certain user by
	 * decreasing order of date.
	 *
	 * @param user user for which to get the finished tasks in decreasing order of finish date
	 *
	 * @return Returns a list with the all the user finished tasks sorted by
	 *         decreasing order.
	 */
	public List<Task> getAllFinishedUserTasksInDecreasingOrder(User user) {

		List<Task> finishedTasksDecOrder = new ArrayList<>();
		finishedTasksDecOrder.addAll(getUserTasks(user));

		return this.sortTaskListDecreasingOrder(finishedTasksDecOrder);
	}

	/**
	 *
	 * This method returns a list with the started but not finished tasks of a
	 * certain user by increasing order of deadline.
	 *
	 * @param user user for which to get the started but not finished tasks in increasing order of deadline
	 *
	 * @return Returns a list with the all the user started, unfinished tasks sorted
	 *         by increasing Deadline order.
	 */
	public List<Task> getStartedNotFinishedUserTasksInIncreasingDeadlineOrder(User user) {

		List<Task> incompleteUserTaskListIncreasingOrder = new ArrayList<>(this.getStartedNotFinishedUserTaskList(user));

		return this.sortTaskListByDeadline(incompleteUserTaskListIncreasingOrder);
	}

	/**
		 * This method saves the task in the Repository
		 * @param task
		 * @return
		 */
		public Task saveTask (Task task) {

			return this.taskRepository.save(task);
		}

		/**
		 * This method returns only the unfinished tasks in a project.
		 *
		 * @return UnfinishedTaskList The list if tasks that are not finished
		 */
		public List<Task> getUnfinishedTasksFromProjectCollaborator(ProjectCollaborator collab) {

			List<Task> unfinishedTaskList = new ArrayList<>();
			unfinishedTaskList.addAll(this.getAllTasksFromProjectCollaborator(collab));

			for (Task other : this.getAllTasksFromProjectCollaborator(collab)) {
				if (other.isTaskFinished()) {
					unfinishedTaskList.remove(other);
				}
			}

			return unfinishedTaskList;

		}

		/**
		 * This method returns only the started but not finished tasks assigned to a
		 * ProjectCollaborator.
		 * 
		 * @return incompleteTaskList The list if tasks that are started but not
		 *         finished
		 */
		public List<Task> getStartedNotFinishedTasksFromProjectCollaborator(ProjectCollaborator collab) {

			List<Task> incompleteTaskList = new ArrayList<>();
			incompleteTaskList.addAll(this.getAllTasksFromProjectCollaborator(collab));

			for (Task other : this.getAllTasksFromProjectCollaborator(collab)) {
				if (other.isTaskFinished() || "Cancelled".equals(other.viewTaskStateName())
						|| other.getStartDate() == null) {
					incompleteTaskList.remove(other);
				}
			}

			return incompleteTaskList;

		}
		
		/**
		 * This method returns only the finished tasks of a user in a project.
		 * 
		 * @return FinishedTaskList The list if tasks that are finished
		 */
		public List<Task> getFinishedTaskListOfUserInProject(ProjectCollaborator collab) {

			List<Task> finishedTaskList = new ArrayList<>();
			
			for (Task other : this.getTaskRepository()) {
				if (other.isTaskFinished() && other.isProjectCollaboratorInTaskTeam(collab)) {
					finishedTaskList.add(other);
				}
			}

			return finishedTaskList;
		}
		
		/**
		 * This method returns a list of all tasks finished a number of months ago by
		 * given user. Given a negative â€œmonthsAgoâ€� input, Returns ALL finished tasks of
		 * said user
		 * 
		 * @param collab
		 *            user who is in the tasks
		 * @param monthsAgo
		 *            how many months to subtract
		 * @return lastMonthFinishedTaskList List of all tasks finished the previous
		 *         month, by the user
		 */
		public List<Task> getFinishedTasksFromProjectCollaboratorInGivenMonth(ProjectCollaborator collab, int monthsAgo) {
			Calendar givenMonth = Calendar.getInstance();
			givenMonth.add(Calendar.MONTH, -monthsAgo);
			List<Task> lastMonthFinishedTaskList = new ArrayList<>();

			for (Task other : this.getAllTasksFromProjectCollaborator(collab)) {
				if (other.isTaskFinished()) {
					if (monthsAgo < 0) {
						lastMonthFinishedTaskList.add(other);
					} else if (other.getFinishDate().get(Calendar.MONTH) == givenMonth.get(Calendar.MONTH)) {
						lastMonthFinishedTaskList.add(other);
					}
				}
			}
			return lastMonthFinishedTaskList;
		}
		
		/**
		 * Checks if the task exist in the task list
		 * 
		 * @param task
		 *            to check
		 * @return TRUE if task exists in the task list FALSE if task does not exist in
		 *         the task list
		 */
		public boolean isTaskInTaskRepository(Task task) {
			for (Task other : this.getTaskRepository()) {
				if (task.equals(other)) {
					return true;
				}
			}
			return false;
		}

		/**
		 * This method returns a list with all the tasks of a certain user in the
		 * project
		 * 
		 * @param collab
		 *            User (to be able to return its tasks)
		 * 
		 * @return AllTasksList List if all tasks from a user
		 */
		public List<Task> getAllTasksFromProjectCollaborator(ProjectCollaborator collab) {
			List<Task> allTasks = new ArrayList<>();
			for (Task other : this.getTaskRepository()) {
				if (other.isProjectCollaboratorInTaskTeam(collab)) {
					allTasks.add(other);
				}
			}
			return allTasks;
		}
		
		/**
		 * 
		 * This method checks if a given user doesnt have any task assigned to him
		 * 
		 * @param collab
		 *            Project Collaborator
		 * @return true if the user doesnt have a task. False if he has at least one
		 *         task
		 */
		public boolean isCollaboratorActiveOnAnyTask(ProjectCollaborator collab) {
			for (Task otherTask : this.getTaskRepository()) {
				if (otherTask.isProjectCollaboratorActiveInTaskTeam(collab))
					return true;
			}
			return false;
		}
		
		/**
		 * This method tells every task within the project task repository to check if
		 * its team is empty.
		 * 
		 * @return listOfTasksWithoutCollaboratorsAssigned List with all the tasks with
		 *         no collaborators assigned.
		 */
		public List<Task> getProjectTasksWithoutCollaboratorsAssigned(Project project) {

			List<Task> listOfTasksWithoutCollaboratorsAssigned = new ArrayList<>();

			for (Task other :  this.getProjectTasks(project)) {

				if (other.isTaskTeamEmpty()) {
					listOfTasksWithoutCollaboratorsAssigned.add(other);
				} else if (!other.doesTaskTeamHaveActiveUsers()) {
					listOfTasksWithoutCollaboratorsAssigned.add(other);
				}
			}
			return listOfTasksWithoutCollaboratorsAssigned;
		}

		/**
		 * This method creates a list with all finished tasks in project.
		 * 
		 * @return allFinishedTasks
		 */
		public List<Task> getProjectFinishedTasks(Project project) {
			List<Task> allFinishedTasks = new ArrayList<>();

			for (Task other : this.getProjectTasks(project)) {
				if (other.isTaskFinished()) {
					allFinishedTasks.add(other);
				}
			}
			return allFinishedTasks;
		}
		

		
		/**
		 * This method create a list of all tasks finished from project in decreasing
		 * order.
		 * 
		 * @return a list of tasks finished by decreasing order
		 */
		public List<Task> getProjectFinishedTasksInDecreasingOrder(Project project) {

			List<Task> finishedTaskListDecreasingOrder = new ArrayList<>();

			finishedTaskListDecreasingOrder.addAll(this.getProjectTasks(project));

			return sortTaskListDecreasingOrder(finishedTaskListDecreasingOrder);
		}
		
		/**
		 * this method create a list whit all unfinished tasks in project.
		 * 
		 * @return allUnFinishedTasks
		 */
		public List<Task> getProjectUnFinishedTasks(Project project) {
			List<Task> allUnFinishedTasks = new ArrayList<>();

			for (Task other : this.getProjectTasks(project)) {
				if (!other.isTaskFinished() && !"Cancelled".equals(other.viewTaskStateName())
						&& other.getStartDate() != null) {
					allUnFinishedTasks.add(other);
				}
			}
			return allUnFinishedTasks;
		}
		
		/**
		 * This method returns all OnGoing Tasks
		 * 
		 * @return List with the tasks set to â€œOnGoingâ€� state
		 */
		public List<Task> getProjectOnGoingTasks(Project project) {
			List<Task> allOnGoing = new ArrayList<>();

			for (Task other : this.getProjectTasks(project)) {
				if ("OnGoing".equals(other.viewTaskStateName())) {
					allOnGoing.add(other);
				}
			}
			return allOnGoing;
		}
		
		/**
		 * this method create a list with all unstarted tasks in project.
		 * 
		 * @return allUnstartedTasks
		 */
		public List<Task> getProjectUnstartedTasks(Project project) {
			List<Task> allUnstartedTasks = new ArrayList<>();

			for (Task other : this.getProjectTasks(project)) {
				if (other.getTaskState() instanceof Created || other.getTaskState() instanceof Planned || other.getTaskState() instanceof Ready) {
					allUnstartedTasks.add(other);
				}
			}
			return allUnstartedTasks;
		}
		/**
		 * Returns a list of the tasks which are unfinished but which deadline has
		 * already passed
		 * 
		 * @return expiredTasks
		 */
		public List<Task> getProjectExpiredTasks(Project project) {
			Calendar today = Calendar.getInstance();
			List<Task> projTasks = this.getProjectTasks(project);
			List<Task> expiredTasks = new ArrayList<>();
			for (Task other : projTasks) {
				if (!other.isTaskFinished() && other.getTaskDeadline() != null && other.getTaskDeadline().before(today)) {
						expiredTasks.add(other);

				}
			}
			return expiredTasks;
		}


		/**
		 * This method returns the a Task by string taskID
		 *
		 * @param id taskId
		 *
		 * @return A task by a Task ID
		 */
		public Task getTaskByTaskID(String id) {

			return this.taskRepository.findByTaskID(id);

		}


		/**
		 * This method returns the a Task by database ID
		 * 
		 * @param id taskId
		 * 
		 * @return A task by a Task ID
		 */
		public Task getTaskByID(Long id) {

			return this.taskRepository.findById(id);

		}

		/**
		 * This method deletes a task from the task the repository if the state if the
		 * task hasnâ€™t started
		 * 
		 * @param taskToDelete
		 *            the task that will be removed from the task Repository
		 * 
		 */
		public boolean deleteTask(Task taskToDelete) {

			boolean wasTaskDeleted = false;

			switch (taskToDelete.viewTaskStateName()) {
			case "Assigned": case "Planned" : case "Created" : case "Ready":
	 			this.taskRepository.delete(taskToDelete);
				wasTaskDeleted = true;
				break;
			default:
				break;
			}
			return wasTaskDeleted;
		}

		/**
		 * This method returns the cancelled tasks from this project
		 * 
		 * @return List of cancelled Tasks
		 */
		public List<Task> getProjectCancelledTasks(Project project) {
			List<Task> cancelledTasksFromProject = new ArrayList<>();

			for (Task other : this.getProjectTasks(project)) {
				if (other.getTaskState() instanceof Cancelled) {
					cancelledTasksFromProject.add(other);
				}
			}
			return cancelledTasksFromProject;
		}



		/**
		 * This method returns a list of tasks that can be associated to
		 * TaskDependencies in a given project
		 *
		 * @param chosenProj
		 * 		the chosen Project
		 * 
		 * @return A list of tasks that can be associated to a TaskDependency
		 */
		public List<Task> getTaskListOfWhichDependenciesCanBeCreated(Project chosenProj) {
			List<Task> validTasks = new ArrayList<>();
			validTasks.addAll(getProjectTasks(chosenProj));
			for (Task other : this.getTaskRepository()) {
				if (other.getTaskState() instanceof Finished || other.getTaskState() instanceof Cancelled) {
					validTasks.remove(other);
				}
			}
			return validTasks;
		}


	/**
	 * This method returns the List of Collaborators from a specific task
	 *
	 * @return Returns a list with the project collaborators that are in the task team
	 */
	public List<ProjectCollaborator> getProjectCollaboratorsFromTask(Project project, Task task) {

		List<ProjectCollaborator> collaboratorsFromTask = new ArrayList<>();
		collaboratorsFromTask.addAll(this.projectCollaboratorRepository.findAllByProject(project));

		return collaboratorsFromTask.stream()
				.filter(projCollab -> projCollab.isProjectCollaboratorActive())
				.filter(projCollab -> task.isProjectCollaboratorActiveInTaskTeam(projCollab))
				.collect(Collectors.toList());

	}


	/**
	 * @return The cost reported to each task in the TaskContainer
	 */

	public List<String> getReportedCostOfEachTask(Project project) {
		List<String> reportTaskCost = new ArrayList<>();

		for (Task other : this.taskRepository.findAllByProject(project)) {
			reportTaskCost.add(String.valueOf(other.getTaskCost()));

		}

		return reportTaskCost;
	}


	/**
	 * This method calculates the sum of the values reported to the task until the
	 * moment
	 * 
	 * @return The cost value reported for the Project until the moment
	 */

	public double getTotalCostReportedToProjectUntilNow(Project project) {
		double reportedCost = 0.0;

		for (Task task : this.taskRepository.findAllByProject(project)) {
			reportedCost += task.getTaskCost();
		}

		return reportedCost;
	}


	/**
	 * This method gathers all Project task assignment requests from a given project
	 *
	 * @param project
	 * @return
	 */
	public List <TaskTeamRequest> getAllProjectTaskAssignmentRequests(Project project) {
		List<TaskTeamRequest> assignmentRequests = new ArrayList<>();

		getProjectTasks(project).stream().forEach(Task -> assignmentRequests.addAll(Task.getPendingTaskAssignementRequests()));

		return assignmentRequests;
	}



	/**
	 * This method gathers all Project task removal requests from a given project
	 *
	 * @param project
	 * @return
	 */
	public List <TaskTeamRequest> getAllProjectTaskRemovalRequests(Project project) {
		List<TaskTeamRequest> removalRequests = new ArrayList<>();

		getProjectTasks(project).stream().forEach(Task -> removalRequests.addAll(Task.getPendingTaskRemovalRequests()));

		return removalRequests;
	}


	/**
	 * This method displays all Project task assignment requests from a given project
	 *
	 * @param project
	 * @return
	 * 	A list of strings of all the Project task assignment requests
	 */
	public List <String> viewAllProjectTaskAssignmentRequests(Project project) {
		List<String> assignmentRequests = new ArrayList<>();

		getProjectTasks(project).stream().forEach(Task -> assignmentRequests.addAll(Task.viewPendingTaskAssignementRequests()));

		return assignmentRequests;
	}


	/**
	 * This method displays all Project task assignment requests from a given project
	 *
	 * @param project
	 * @return
	 * 	A list of strings of all the Project task assignment requests
	 */
	public List <String> viewAllProjectTaskRemovalRequests(Project project) {
		List<String> removalRequests = new ArrayList<>();

		getProjectTasks(project).stream().forEach(Task -> removalRequests.addAll(Task.viewPendingTaskRemovalRequests()));

		return removalRequests;
	}

	public void setTaskRepository(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

	public void setProjectCollaboratorRepository(ProjCollabRepository projectCollaboratorRepository) {
		this.projectCollaboratorRepository = projectCollaboratorRepository;
	}


}
