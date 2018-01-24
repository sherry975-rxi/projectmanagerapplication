package project.controller;

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
public class US301CreateProjectController {

	Company myCompany;

	/**
	 * Constructor for project creation controller
	 */
	public US301CreateProjectController() {
	}

	/**
	 * This method creates a project from the controller by calling the create
	 * project method in project repository class, and adds it to the project
	 * repository.
	 * 
	 * @param name
	 *            Name to be set as the project name
	 * @param description
	 *            Description to be set as the project description
	 * @param projectManager
	 *            User to be set as the project manager
	 * 
	 * @return Returns the project created
	 */
	public Project createProject(String name, String description, User projectManager) {

		Project newProject = myCompany.getProjectsRepository().createProject(name, description, projectManager);

		myCompany.getProjectsRepository().addProjectToProjectRepository(newProject);

		return newProject;
	}

	/**
	 * Sets the company used in this class.
	 * 
	 * @param companyToSet
	 */
	public void setMyCompany(Company companyToSet) {
		this.myCompany = companyToSet;
	}
}
