package project.controller;

import org.springframework.stereotype.Service;
import project.model.Project;
import project.model.User;
import project.model.UserContainer;

import java.util.List;

@Service
public class US351AddColaboratorToProjectTeamController {

	UserContainer userContainer = new UserContainer();

	public US351AddColaboratorToProjectTeamController(){
    }
	/**
	 * This controller add user to project team
	 * 
	 * respond to US 350
	 * 
	 * @param user user to add to project
	 * @param project project to which to add the user
	 * @param costPerEffort the cost per unit of effort this particular user requires
	 */
	public void addUserToProjectTeam(User user, Project project, int costPerEffort) {

		project.addUserToProjectTeam(user, costPerEffort);

	}

	/**
	 * Returns all the available users
	 *
	 * @return List with all the available users
	 */
	public List<User> getAllUsers() {
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
