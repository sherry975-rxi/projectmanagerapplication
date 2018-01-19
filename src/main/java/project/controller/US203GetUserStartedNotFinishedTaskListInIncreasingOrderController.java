package project.controller;

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
		return this.myProjRepo.getStartedNotFinishedUserTasksInIncreasingDeadlineOrder(myUser);
	}

}
