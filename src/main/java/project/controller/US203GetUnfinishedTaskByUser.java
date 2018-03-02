package project.controller;

import project.model.Company;
import project.model.Task;
import project.model.User;

import java.util.ArrayList;
import java.util.List;

public class US203GetUnfinishedTaskByUser {

	public List<Task> getUnfinishedTasksOfProjectCollaborator(User user1) {
		User username = user1;
		List<Task> unfinishedTaskFromUser = new ArrayList<>();
		unfinishedTaskFromUser
				.addAll(Company.getTheInstance().getProjectsRepository().getUnfinishedUserTaskList(username));
		return unfinishedTaskFromUser;
	}

}
