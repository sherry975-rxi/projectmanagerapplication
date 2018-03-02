package project.controller;

import project.model.Company;
import project.model.Project;
import project.model.User;

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

		project.addUserToProjectTeam(user, effort);

	}

	/**
	 * Returns all the available users
	 *
	 * @return List with all the available users
	 */
	public List<User> getAllUsers() {
		return Company.getTheInstance().getUsersRepository().getAllActiveCollaboratorsFromRepository();
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
