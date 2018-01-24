package project.controller;

import project.model.Project;

public class US367MarkFinishedTaskAsUnfinishedController {
	private Project project;
	private String taskID;

	public US367MarkFinishedTaskAsUnfinishedController(Project proj, String idTask) {
		this.project = proj;
		this.taskID = idTask;
	}

	public void markFinishedTaskAsUnfinished() {

		this.project.getTaskRepository().getTaskByID(this.taskID).removeFinishDate();

	}

}
