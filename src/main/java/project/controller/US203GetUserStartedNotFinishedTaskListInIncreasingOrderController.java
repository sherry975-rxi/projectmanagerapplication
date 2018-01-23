package project.controller;

import java.util.ArrayList;
import java.util.List;

import project.model.Company;
import project.model.ProjectRepository;
import project.model.Task;
import project.model.User;

public class US203GetUserStartedNotFinishedTaskListInIncreasingOrderController {

	ProjectRepository myProjRepo;

	public US203GetUserStartedNotFinishedTaskListInIncreasingOrderController() {
		this.myProjRepo = Company.getTheInstance().getProjectsRepository();
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

		List<Task> myUnfinishedAllTasks = myProjRepo.getStartedNotFinishedUserTaskList(myUser);
		List<Task> myUnfinishedTasksWDeadline = new ArrayList<>();
		List<Task> myUnfinishedAllTasksWODeadline = new ArrayList<>();

		for (Task other : myUnfinishedAllTasks) {
			if (other.getTaskDeadline() != null) {
				myUnfinishedTasksWDeadline.add(other);
			} else {
				myUnfinishedAllTasksWODeadline.add(other);
			}
		}
		myUnfinishedAllTasks = myProjRepo.sortTaskListByDeadline(myUnfinishedTasksWDeadline);
		myUnfinishedAllTasks.addAll(myUnfinishedAllTasksWODeadline);
		return myUnfinishedAllTasks;
	}

}