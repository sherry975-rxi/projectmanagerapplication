package project.controller;

import java.util.List;

import project.model.Project;
import project.model.ProjectRepository;

public class GetActiveProjectsController {

	/**
	 * Controller GetActiveProjectsControllers
	 * 
	 * This controller allows a director to see a list of active projects in a
	 * Project Repository
	 *
	 */

	public List<Project> getActiveProjectsController(ProjectRepository projectRepository) {

		return projectRepository.getActiveProjects();

	}

}
