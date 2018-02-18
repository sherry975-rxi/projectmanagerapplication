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
			String toShowProject = "[" + showIndex.toString() + "] \n" + overviewProjectAsString(chosenList.get(i));
			allProjectsList.add(toShowProject);
		}

		return allProjectsList;
	}

	public List<String> viewActiveProjects() {
		this.chosenList = projRepository.getActiveProjects();
		List<String> activeProjectsList = new ArrayList<>();

		for (int i = 0; i < chosenList.size(); i++) {
			Integer showIndex = i + 1;
			String toShowProject = "[" + showIndex.toString() + "] \n" + overviewProjectAsString(chosenList.get(i));
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

		Integer numberID = toView.getIdCode();
		String ID = numberID.toString();
		String status = toView.getProjectStatusName();

		int headerSize = 15 + ID.length() + toView.getName().length();

		String headerChar = "=";
		String header = generateHeader(headerChar, headerSize);

		String output = header + "\n";
		output += "===== ";
		output += numberID.toString();
		output += " - " + toView.getName() + " =====\n";
		output += header;
		output += "\n - Status: " + status;
		output += "\n - Manager: " + toView.getProjectManager().getName();
		output += "\n - Description: " + toView.getProjectDescription();
		output += "\n" + header;

		return output;
	}

	public String generateHeader(String toRepeat, int repeat) {
        StringBuilder gen = new StringBuilder();

		for (int i = 0; i < repeat; i++) {
		    gen.append(toRepeat);
		}

		return gen.toString();
	}

}
