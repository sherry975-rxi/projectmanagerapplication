package project.controller;

import java.util.List;

import project.model.Project;
import project.model.ProjectCollaborator;

public class ListProjectCollaboratorController {

	/**
	 *  This controller returns a list of all Collaborators in the Project Team
	 * 
	 * @return List<ProjectCollaborator> returns all collaborators on the team
	 */
	public List<ProjectCollaborator> listCollaboratorsController(Project toCheckTeam) {

		return toCheckTeam.getActiveProjectTeam();

	}
	
}
