package project.model;

public class TaskTeamRequest {

	private ProjectCollaborator projCollab;
	private Task task;

	public TaskTeamRequest(ProjectCollaborator projCollab, Task task) {
		this.projCollab = projCollab;
		this.task = task;
	}

	public ProjectCollaborator getProjCollab() {
		return projCollab;
	}

	public Task getTask() {
		return task;
	}

	public boolean equals(TaskTeamRequest request) {
		boolean result = false;
		if (request.getProjCollab().equals(this.projCollab) && request.getTask().equals(this.task)) {
			result = true;
		}
		return result;
	}
}
