package project.controller;

import project.Services.ProjectContainerService;
import project.model.Project;

public class US367MarkFinishedTaskAsUnfinishedController {
	private ProjectContainerService projContainer;
	private Project project;
	private String taskID;

	/**
	 * This constructor receives a Project and a Task's ID, storing these values and allowing them to be used when removing a task's finished state
	 *
	 * @param proj
	 * @param idTask
	 */
	public US367MarkFinishedTaskAsUnfinishedController(Project proj, String idTask) {
		this.project = proj;
		this.taskID = idTask;
	}


	/**
	 *
	 * If this option is selected by the UI, it removes the selected task's finished state and returns it to StandBy state
	 *
	 */

	public void markFinishedTaskAsUnfinished() {

		this.project.getTaskRepository().getTaskByID(this.taskID).removeFinishDate();
		projContainer.saveProjectInRepository(project);

	}

}
