package project.controller;

import project.model.*;

import java.util.List;

public class US351AddColaboratorToProjectTeamController {

	/**
	 * This controller add user to project team
	 * 
	 * respond to US 350
	 * 
	 * @param user
	 * @param project
	 * @param effort
	 */
	public void addUserToProjectTeam(User user, Project project, int effort) {
		ProjectContainer projectContainer = new ProjectContainer();
		project.addUserToProjectTeam(user, effort);
		projectContainer.saveProjectInRepository(project);

	}

	/**
	 * Returns all the available users
	 *
	 * @return List with all the available users
	 */
	public List<User> getAllUsers() {
		UserContainer userContainer = new UserContainer();
		return userContainer.getAllActiveCollaboratorsFromRepository();
	}

	public boolean isUserAlreadyInProject(User user, Project project) {

		boolean result = false;
		if (user != null) {
			result = project.isUserActiveInProject(user);
		}
		return result;

	}

	public User searchUserByID(String id) {
		for (User other : this.getAllUsers()) {
			if (id.equals(other.getIdNumber())) {
				return other;
			}
		}
		return null;
	}

}
