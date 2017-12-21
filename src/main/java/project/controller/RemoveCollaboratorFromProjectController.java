/**
 * 
 */
package project.controller;

import project.model.Project;
import project.model.User;

/**
 * This Class implements the Controller for the Removal of a Project Collaborator from a Project Team in a Project
 * 
 * @author Group3
 *
 */
public class RemoveCollaboratorFromProjectController {

	
	/**
	 * Constructor for removal of Project Collaborator from ProjectTeam in a
	 * specific Project
	 */
	public RemoveCollaboratorFromProjectController() {
		
	}

	
	/**
	 * This method controls the removal of a Project Collaborator from a ProjectTeam
	 * in a specific Project, by calling the method that
	 * removes a ProjectCollaborator from a ProjectTeam that exists in Project class.
	 * This removal becomes effective by deactivating the User from a ProjectTeam in a Project.
	 * 
	 * @param projectToRemoveCollaborator
	 *            This is the Project that contains the Project Team from which the
	 *            user is being removed
	 * 
	 * @param userToRemoveFromProjectTeam
	 * This is the User that is being removed from the ProjectTeam in a specific Project
	 */
	public void removeProjectCollaboratorFromProjectTeam(Project projectToRemoveCollaborator,
			User userToRemoveFromProjectTeam) {

		projectToRemoveCollaborator.removeProjectCollaboratorFromProjectTeam(userToRemoveFromProjectTeam);
	
	}

}
