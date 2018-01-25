package project.controller;

import java.util.ArrayList;
import java.util.List;

import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.User;

public class US355ViewProjectTeamAndThenRemoveCollaboratorController {
	Project proj;

	public US355ViewProjectTeamAndThenRemoveCollaboratorController(Project proj) {
		this.proj = proj;
	}

	/**
	 * this method remove project a active collaborator from project team
	 * 
	 * @param user
	 * @return true if remove false if not
	 */
	public boolean removeCollaboratorFromProjectTeam(User user) {
		boolean remove = false;

		if (proj.removeProjectCollaboratorFromProjectTeam(user)) {
			remove = true;
		}

		return remove;
	}

	/**
	 * this method gets all active project collaborator in project
	 * 
	 * @return a list of user
	 */
	public List<User> getActiveProjectCollaboratorFromTeam() {
		List<ProjectCollaborator> projectTeam = this.proj.getActiveProjectTeam();
		List<User> listOfUser = new ArrayList<User>();

		for (int i = 0; i < projectTeam.size(); i++) {
			User user = projectTeam.get(i).getUserFromProjectCollaborator();
			listOfUser.add(user);
		}

		return listOfUser;
	}

	/**
	 * This methods gets all team collaborator name in the form of a List of
	 * Strings,
	 * 
	 * @param proj
	 * 
	 * 
	 * @return user name List
	 */
	public List<String> getProjectTeamName() {

		List<ProjectCollaborator> projectTeam = this.proj.getActiveProjectTeam();
		List<String> projectTeamToPrint = new ArrayList<>();

		for (int i = 0; i < projectTeam.size(); i++) {

			String projectCollabName = projectTeam.get(i).getUserFromProjectCollaborator().getName();

			projectTeamToPrint.add("[" + (i + 1) + "]" + projectCollabName);
		}

		return projectTeamToPrint;
	}

	/**
	 * This method splits a Sting by the space and only return the left part of the
	 * string until the first space
	 * 
	 * @param string
	 *            String to split
	 */
	public String splitStringByFirstSpace(String string) {

		String[] partsTask = string.split(" ");
		String firstPartOfString = partsTask[0];

		return firstPartOfString;
	}

}
