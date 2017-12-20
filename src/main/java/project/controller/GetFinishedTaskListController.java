package project.controller;

import java.util.List;

import project.model.Project;
import project.model.Task;;

public class GetFinishedTaskListController {

	public List<Task> getFinishedTaskListController(Project p1) {

		return p1.getTaskRepository().getFinishedTasks();

	}

}
