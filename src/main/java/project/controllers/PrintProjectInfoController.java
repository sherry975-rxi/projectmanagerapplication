package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.Task;
import project.services.ProjectService;
import project.services.TaskService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Controller
public class PrintProjectInfoController {

	@Autowired
	private ProjectService projService;

	@Autowired
	private TaskService taskService;

	private Project project;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
	int projID;

	public PrintProjectInfoController() {
		//Empty constructor created for JPA integration tests

	}



	public PrintProjectInfoController(Project project) {

		this.project = project;
		

	}

	public PrintProjectInfoController(int projID) {

		this.projID = projID;
	}

	public void setProject() {
		this.project = projService.getProjectById(this.projID);
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
		List<ProjectCollaborator> projectTeam =  projService.getProjectTeam(project);

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
			toPrint = String.valueOf(this.project.getBudget());
		}

		return toPrint;
	}

    /**
     * Generates a printable string from an array of strings. Each index of the array of strings corresponds to an enum
     * from the Project cost calculation methods.
     *
     * For each of the calculation methods, it calls a boolean confirmation to output "Enabled" or "Disabled" depending on the method's availability
     *
     *
     * @return
     */
	public String printCostCalculationMethods() {

        String output = "";
	    String first ="[1] - The user's first cost ";
		String last ="[2] - The user's last cost ";
		String average="[3] - Average between all of the user's costs ";
        String [] calculationMethods = new String [] {output,first,last,average};


        for(int i=1; i<4;i++) {
		    boolean enabled = project.isCalculationMethodAllowed(i);
		    output += calculationMethods[i] + booleanToEnabledDisabled(enabled) + "\n";
        }
        output+="(Currently: " + project.getCalculationMethod() + ")";

        return output;
	}

    /**
     * this method converts a boolean true/false to a string output of Enabled/Disabled
     *
     *
     * @param contains
     * @return
     */
	public String booleanToEnabledDisabled(boolean contains) {
		if(contains) {
			return "[ENABLED]";
		}
		return "[DISABLED]";

	}

	/**
	 * This method get the project's task list and return it as a list of Strings
	 *
	 * @return List of Strings of project's task (task ID + task description)
	 */
	public List<String> getProjectTaskList() {
		List<String> projectTaskList = new ArrayList<>();

		for (Task projectTask : getTasks()) {
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

		List<String> projectTasksID = new ArrayList<>();

		for (Task projectTask : getTasks()) {
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
		return taskService.getProjectTasks(project);
	}


	public void setProject(Project project) {
		this.project = project;
	}

	public void setDateFormat(SimpleDateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	public void setProjID(int projID) {
		this.projID = projID;
	}

}
