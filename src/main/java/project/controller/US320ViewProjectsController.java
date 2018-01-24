package project.controller;

import java.util.ArrayList;
import java.util.List;

import project.model.Company;
import project.model.Project;
import project.model.ProjectRepository;

public class US320ViewProjectsController {

	ProjectRepository projRepository = Company.getTheInstance().getProjectsRepository();
	List<Project> chosenList;
	Project selectedProject = null;

	public List<String> viewAllProjects() {
		this.chosenList = projRepository.getAllProjects();
		List<String> allProjectsList = new ArrayList<>();

		for (int i = 0; i < chosenList.size(); i++) {
			Integer showIndex = i + 1;
			String toShowProject = "::Index " + showIndex.toString() + ":: \n"
					+ overviewProjectAsString(chosenList.get(i));
			allProjectsList.add(toShowProject);
		}

		return allProjectsList;
	}

	public List<String> viewActiveProjects() {
		this.chosenList = projRepository.getActiveProjects();
		List<String> activeProjectsList = new ArrayList<>();

		for (int i = 0; i < chosenList.size(); i++) {
			Integer showIndex = i + 1;
			String toShowProject = "::Index " + showIndex.toString() + ":: \n"
					+ overviewProjectAsString(chosenList.get(i));
			activeProjectsList.add(toShowProject);
		}

		return activeProjectsList;
	}

	public Project selectProject(int visibleIndex) {
		int actualIndex = visibleIndex - 1;
		if (actualIndex >= 0 && actualIndex < chosenList.size()) {
			selectedProject = chosenList.get(actualIndex);
		}
		return selectedProject;
	}

	/**
	 * This is a simple utility method that converts a project's basic data into a
	 * string to be displayed in the UI
	 * 
	 * @param selected
	 *            Project
	 * @return a String with the Project's data
	 */
	public String overviewProjectAsString(Project toView) {
		String output = "===== ";
		Integer ID = toView.getIdCode();
		String status = toView.getProjectStatusName();

		output += ID.toString();
		output += " - " + toView.getName() + " =====";
		output += "\n - Status: " + status;
		output += "\n - Manager: " + toView.getProjectManager().getName();
		output += "\n - Description: " + toView.getProjectDescription();
		output += "\n ==========";

		return output;
	}

}
