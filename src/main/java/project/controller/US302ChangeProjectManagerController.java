package project.controller;

import java.util.ArrayList;
import java.util.List;

import project.model.Company;
import project.model.Project;
import project.model.User;

/**
 * @author Group 3
 * 
 *         This class implements the controller that allows a user to create a
 *         Project.
 *
 */
public class US302ChangeProjectManagerController {

	Company myCompany = Company.getTheInstance();
	List<User> activeCollaboratorList;
	User selectedManager;
	Project selectedProject;

	/**
	 * Constructor for project creation controller, it receives a project whose
	 * manager is to be changed and stores its project manager
	 */
	public US302ChangeProjectManagerController(Project toChangeManager) {
		selectedProject = toChangeManager;
		selectedManager = toChangeManager.getProjectManager();
	}

	/**
	 * This controller returns a list of all activeCollaborators in the User
	 * Repository
	 * 
	 * @return List<User> a copy of the User database
	 */
	public List<String> listPossibleManagers() {
		this.activeCollaboratorList = myCompany.getUsersRepository().getAllActiveCollaboratorsFromRepository();
		List<String> collabListAsString = new ArrayList<>();

		for (int i = 0; i < activeCollaboratorList.size(); i++) {
			Integer showIndex = i + 1;
			String toShowUser = "[" + showIndex.toString() + "] \n"
					+ collaboratorInfoToString(activeCollaboratorList.get(i));
			collabListAsString.add(toShowUser);
		}

		return collabListAsString;

	}

	/**
	 * This method selects a User and returns it to the UI, to be assigned as
	 * ProjectManager by the Director
	 * 
	 * @return User to be Returned and handled by the Director
	 */
	public User selectNewManager(int index) {
		int actualIndex = index - 1;
		if (actualIndex >= 0 && actualIndex < activeCollaboratorList.size()) {
			selectedManager = activeCollaboratorList.get(actualIndex);
		}
		return selectedManager;

	}

	/**
	 * This method selects a User calls the selectUser method, returning false if
	 * the selected user is already the project manager. If not, it sets the
	 * selected user as the new ProjectManger and returns true
	 * 
	 * @return User to be Returned and handled by the Director
	 */
	public boolean isNewManagerDifferentFromFirst(int index) {
		User newManager = selectNewManager(index);

		if (newManager.equals(selectedProject.getProjectManager())) {
			return false;
		}

		selectedProject.setProjectManager(selectedManager);
		return true;

	}

	/**
	 * This is a utility method that converts a User object into a String of data,
	 * to be displayed in the UI
	 * 
	 * @param User
	 *            to be converted
	 * @return String of the user's data
	 */
	public String collaboratorInfoToString(User toConvert) {

		String info = toConvert.getIdNumber() + ": " + toConvert.getName() + " (" + toConvert.getEmail() + "; "
				+ toConvert.getPhone() + ") - " + toConvert.getFunction();

		return info;
	}

}
