package project.controller;

import project.model.Project;
import project.model.ProjectRepository;

import java.util.List;

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
