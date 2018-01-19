package project.controller;

import java.util.ArrayList;
import java.util.List;

import project.model.Company;
import project.model.Task;
import project.model.User;

public class US203GetUnfinishedTaskByUser {

	private User username;

	public List<Task> getUnfinishedTasksOfProjectCollaborator(User user) {
		this.username = user;
		List<Task> unfinishedTaskFromUser = new ArrayList<>();
		unfinishedTaskFromUser
				.addAll(Company.getTheInstance().getProjectsRepository().getUnfinishedUserTaskList(username));
		return unfinishedTaskFromUser;
	}

}
