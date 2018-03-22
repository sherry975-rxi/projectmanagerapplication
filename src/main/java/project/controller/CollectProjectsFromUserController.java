package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import project.Services.ProjectService;
import project.model.Project;
import project.model.User;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CollectProjectsFromUserController {

	@Autowired
	private ProjectService projService;

	private User user;

	/**
	 * Constructor
	 * 
	 */
	public CollectProjectsFromUserController() {

	}

    public CollectProjectsFromUserController(User user) {

	    this.user=user;

    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
	 * This method returns a set of Projects where a certain user
	 * @return List of Projects from user
	 */
	public List<String> getProjectsFromUserAndProjectManager() {

		List<String> myProjects = new ArrayList<>();
		for (Project ii : projService.getAllProjectsfromProjectsContainer()) {
			if (ii.isProjectManager(user)) {
				myProjects.add("[" + ii.getIdCode() + "]" + " " + ii.getName() + " - PM");
			} else if (projService.isUserActiveInProject(user, ii)) {
				myProjects.add("[" + ii.getIdCode() + "]" + " " + ii.getName());
			}
		}
		return myProjects;
	}

	/**
	 * This method returns a set of Projects which a certain user has joined as a collaborator
	 * @return List of Projects of a user
	 */
	public List<Project> getProjectsFromUser() {
		return new ArrayList<>(projService.getProjectsFromUser(this.user));
	}

	/**
	 * This method returns a set of Projects where a certain user was defined as
	 * Project Manager
	 * @return List of Projects of a Project Manager
	 */
	public List<Project> getProjectsFromProjectManager() {
		return new ArrayList<>(projService.getProjectsFromProjectManager(this.user));
	}

}
