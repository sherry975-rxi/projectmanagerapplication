package project.controller;

import project.model.Company;
import project.model.Project;
import project.model.ProjectContainer;

import java.util.ArrayList;
import java.util.List;

public class US320ViewProjectsController {

	ProjectContainer projRepository = Company.getTheInstance().getProjectsContainer();
	List<Project> chosenList;
	Project selectedProject = null;

    /**
     * This method generates lists all project and converts them to a list of strings
     * to be displayed in the UI
     *
     * @return a list of String with Projects' data
     */
	public List<String> viewAllProjects() {
		this.chosenList = projRepository.getAllProjectsfromProjectsContainer();
		List<String> allProjectsList = new ArrayList<>();

		for (int i = 0; i < chosenList.size(); i++) {
			Integer showIndex = i + 1;
			String toShowProject = "[" + showIndex.toString() + "] \n" + overviewProjectAsString(chosenList.get(i));
			allProjectsList.add(toShowProject);
		}

		return allProjectsList;
	}

    /**
     * This method generates lists all active project and converts them to
     * a list of strings to be displayed in the UI
     *
     * @return a list of String with Projects' data
     */
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

    /**
     * This method is called when the user selects a project from the visible index listed
     * in the UI
     *
     * @param integer of the visible index
     *
     * @return the selected Project, to be stored by the UI and handled by other controllers
     */
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
	 * @param the project to view data in the UI
	 *
	 * @return a String with the Project's data
	 */
	public String overviewProjectAsString(Project toView) {

		Integer numberID = toView.getIdCode();
		String id = numberID.toString();
		String status = toView.getProjectStatusName();

		int headerSize = 15 + id.length() + toView.getName().length();


		char headerChar = '=';
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

/**
 * This is a utility method to generate a header with the selected character and size, to be called by the project view
 *
 */
 public String generateHeader(char toRepeat, int repeat) {
        StringBuilder gen = new StringBuilder();

		for (int i = 0; i < repeat; i++) {
		    gen.append(toRepeat);
		}

		return gen.toString();
	}

}
