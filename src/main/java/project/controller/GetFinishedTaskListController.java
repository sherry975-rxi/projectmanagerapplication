package project.controller;

import java.util.ArrayList;
import java.util.List;

import project.model.Company;
import project.model.Project;
import project.model.ProjectRepository;
import project.model.Task;
import project.model.UserRepository;;

public class GetFinishedTaskListController {

	Company myCompany = Company.getTheInstance();
	UserRepository userRepository;
	Project project;
	ProjectRepository projectRepository;
	List<Task> finishedTaskList = new ArrayList<>();

	/**
	 * @param p1
	 *            The project to search for it's finished tasks
	 * @return This method returns a list of tasks of a project that were marked as
	 *         finished if the project doesn't exist, the method catches an
	 *         IllegalArgumentException
	 */
	public List<Task> getFinishedTaskListController(Project p1) {

		try {
			projectRepository = myCompany.getProjectsRepository();
			for (Project other : projectRepository.getAllProjects()) {
				if (other.equals(p1)) {
					finishedTaskList = p1.getTaskRepository().getFinishedTasks();
				}
			}
		} catch (IllegalArgumentException e) {
			System.out.println("Project does not exist!");
		}

		return finishedTaskList;

	}

}
