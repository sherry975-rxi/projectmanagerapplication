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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((projCollab == null) ? 0 : projCollab.hashCode());
		result = prime * result + ((task == null) ? 0 : task.hashCode());
		return result;
	}

	/**
	 * Checks if two requests are the equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof TaskTeamRequest)) {
			return false;
		}
		TaskTeamRequest other = (TaskTeamRequest) obj;
		if (projCollab == null) {
			if (other.projCollab != null) {
				return false;
			}
		} else if (!projCollab.equals(other.projCollab)) {
			return false;
		}
		if (task == null) {
			if (other.task != null) {
				return false;
			}
		} else if (!task.equals(other.task)) {
			return false;
		}
		return true;
	}

}
