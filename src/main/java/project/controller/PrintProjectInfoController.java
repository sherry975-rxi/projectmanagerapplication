package project.controller;

import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PrintProjectInfoController {

	private Project project;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
	Integer projID;

	public PrintProjectInfoController(Project project) {
		this.project = project;
	}

	public PrintProjectInfoController(Integer projID) {
		this.projID = projID;
		project = Company.getTheInstance().getProjectsContainer().getProjById(this.projID);
	}

	public void setProject() {
		this.project = Company.getTheInstance().getProjectsContainer().getProjById(this.project.getIdCode());
	}

	/**
	 * This method get the project's name and return it as a String
	 *
	 * @return String project's name
	 */
	public String printProjectNameInfo() {

		return this.project.getName();
	}

	/**
	 * This method get the project's ID and return it as a String
	 *
	 * @return String project's ID
	 */
	public String printProjectIDCodeInfo() {

		return String.valueOf(this.project.getIdCode());
	}

	/**
	 * This method get the project's status and return it as a String
	 *
	 * @return String project's status
	 */
	public String printProjectStatusInfo() {
		String toPrint = "---";

		if (String.valueOf(this.project.getProjectStatus()) != null) {
			toPrint = String.valueOf(this.project.getProjectStatus());
		}

		return toPrint;
	}

	/**
	 * This method get the project's description and return it as a String
	 *
	 * @return String project's description
	 */
	public String printProjectDescriptionInfo() {

		return this.project.getProjectDescription();
	}

	/**
	 * This method get the project's start date and return it as a String
	 *
	 * @return String project's start date
	 */
	public String printProjectStartDateInfo() {
		Calendar startDate = this.project.getStartdate();

		String toPrint = "---";

		if (startDate != null) {
			toPrint = this.dateFormat.format(startDate.getTime());
		}

		return toPrint;
	}

	/**
	 * This method get the project's finish date and return it as a String
	 *
	 * @return String project's finish date
	 */
	public String printProjectFinishDateInfo() {
		Calendar finishDate = this.project.getFinishdate();

		String toPrint = "---";

		if (finishDate != null) {
			toPrint = this.dateFormat.format(finishDate.getTime());
		}

		return toPrint;
	}

	/**
	 * This method get the project's manager and return it as a String
	 *
	 * @return String project's manager
	 */
	public String printProjectManagerInfo() {

		return this.project.getProjectManager().getName();
	}

	/**
	 * This method get the project's team and return it as a String (names of
	 * members separeted by commas)
	 *
	 * @return String project's team
	 */
	public String printProjectTeamInfo() {
		List<ProjectCollaborator> projectTeam = this.project.getProjectTeam();

		List<String> team = new ArrayList<>();

		for (ProjectCollaborator projectMember : projectTeam) {
			String collaboratorActive;
			if (projectMember.isProjectCollaboratorActive())
				collaboratorActive = " [ACTIVE]";
			else
				collaboratorActive = " [INACTIVE]";

			team.add(projectMember.getUserFromProjectCollaborator().getName() + collaboratorActive);
		}
		return String.join(", ", team);
	}

	/**
	 * This method get the project's budget and return it as a String
	 *
	 * @return String project's budget
	 */
	public String printProjectBudgetInfo() {
		String toPrint = "---";

		if (String.valueOf(this.project.getProjectBudget()) != null) {
			toPrint = String.valueOf(this.project.getProjectBudget());
		}

		return toPrint;
	}

	/**
	 * This method get the project's task list and return it as a list of Strings
	 *
	 * @return List of Strings of project's task (task ID + task description)
	 */
	public List<String> getProjectTaskList() {
		List<Task> taskList = Company.getTheInstance().getProjectsContainer().getProjById(this.project.getIdCode())
				.getTaskRepository().getProjectTaskRepository();
		List<String> projectTaskList = new ArrayList<>();
		for (Task projectTask : taskList) {
			String[] stringList = projectTask.getTaskID().split("\\.");
			projectTaskList.add("[" + stringList[0] + "." + stringList[1] + "]" + " " + projectTask.getDescription());
		}
		return projectTaskList;
	}

	/**
	 * This method get the project's task list IDs and return it as a list of
	 * Strings
	 *
	 * @return List of Strings of project's task IDs
	 */
	public List<String> getTasksIDs() {

		List<Task> taskList = Company.getTheInstance().getProjectsContainer().getProjById(this.project.getIdCode())

				.getTaskRepository().getProjectTaskRepository();
		List<String> projectTasksID = new ArrayList<>();
		for (Task projectTask : taskList) {
			projectTasksID.add(projectTask.getTaskID());
		}
		return projectTasksID;
	}

	/**
	 * This method get the project's task list
	 *
	 * @return List of project's task
	 */
	public List<Task> getTasks() {
		return Company.getTheInstance().getProjectsContainer().getProjById(this.project.getIdCode())
				.getTaskRepository().getProjectTaskRepository();
	}

}
