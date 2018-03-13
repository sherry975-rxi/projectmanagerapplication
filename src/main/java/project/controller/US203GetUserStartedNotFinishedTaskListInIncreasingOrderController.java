package project.controller;

import project.Services.ProjectContainerService;
import project.model.Task;
import project.model.User;

import java.util.ArrayList;
import java.util.List;

public class US203GetUserStartedNotFinishedTaskListInIncreasingOrderController {

	ProjectContainerService projectContainer;

	public US203GetUserStartedNotFinishedTaskListInIncreasingOrderController() {
		this.projectContainer = new ProjectContainerService();
	}

	/**
	 * This method returns all the started not finished Tasks the User has. -
	 * US203v2
	 * 
	 * @param myUser
	 *            The User to search for it's unfinished tasks
	 * @return Task List
	 */
	public List<Task> getUserStartedNotFinishedTaskListInIncreasingOrder(User myUser) {

		List<Task> myUnfinishedAllTasks = projectContainer.getStartedNotFinishedUserTaskList(myUser);
		List<Task> myUnfinishedTasksWDeadline = new ArrayList<>();
		List<Task> myUnfinishedAllTasksWODeadline = new ArrayList<>();

		for (Task other : myUnfinishedAllTasks) {
			if (other.getTaskDeadline() != null) {
				myUnfinishedTasksWDeadline.add(other);
			} else {
				myUnfinishedAllTasksWODeadline.add(other);
			}
		}
		myUnfinishedAllTasks = projectContainer.sortTaskListByDeadline(myUnfinishedTasksWDeadline);
		myUnfinishedAllTasks.addAll(myUnfinishedAllTasksWODeadline);
		return myUnfinishedAllTasks;
	}

}
