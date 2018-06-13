package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.User;
import project.services.ProjectService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class US355ViewProjectTeamAndThenRemoveCollaboratorController {

	@Autowired
	private ProjectService projectService;

	private Project proj;


	public US355ViewProjectTeamAndThenRemoveCollaboratorController() {
		// Default constructor
	}

	/*
	 * Set Project
	 */

	public void setProj(Project proj) {
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

		ProjectCollaborator toRemove = projectService.findActiveProjectCollaborator(user, proj);

		if (toRemove != null) {
			remove = true;
			toRemove.setStatus(false);
			toRemove.setFinishDate(Calendar.getInstance());
			projectService.updateProjectCollaborator(toRemove);
		}

		return remove;
	}

	/**
	 * this method gets all active project collaborator in project
	 * 
	 * @return a list of user
	 */
	public List<User> getActiveProjectCollaboratorFromTeam() {

		return projectService.getActiveProjectTeam(proj).stream()
				.map(ProjectCollaborator::getUserFromProjectCollaborator).collect(Collectors.toList());
	}

	/**
	 * This methods gets all team collaborator name in the form of a List of
	 * Strings,
	 *
	 * 
	 * @return user name List
	 */
	public List<String> getProjectTeamName() {

		List<ProjectCollaborator> projectTeam = projectService.getActiveProjectTeam(proj);
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

		return partsTask[0];
	}

}
