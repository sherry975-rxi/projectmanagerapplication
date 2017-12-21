package project.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.model.Project;
import project.model.ProjectRepository;
import project.model.User;

public class GetActiveProjectsControllerTest {

	User projectManager;
	Project project1;
	Project project2;
	Project project3;
	Project project4;
	Project project5;
	ProjectRepository projectRepository;
	List<Project> activeProjectsList;
	GetActiveProjectsController controller;

	@Before
	public void setUp() {

		// Creates a new GetActiveProjectsController
		controller = new GetActiveProjectsController();

		// create projectManager
		projectManager = new User("João", "joao@gmail.com", "001", "Admin", "920000000");

		projectRepository = new ProjectRepository();

		// create project and set a user to Project manager
		project1 = new Project(0, "name3", "description4", projectManager);
		project2 = projectRepository.createProject("name3", "description4", projectManager);
		project3 = projectRepository.createProject("name3", "description4", projectManager);
		project4 = projectRepository.createProject("name3", "description4", projectManager);
		project5 = projectRepository.createProject("name3", "description4", projectManager);

		// add project to project repository
		projectRepository.addProjectToProjectRepository(project1);
		projectRepository.addProjectToProjectRepository(project2);
		projectRepository.addProjectToProjectRepository(project3);
		projectRepository.addProjectToProjectRepository(project4);
		projectRepository.addProjectToProjectRepository(project5);

		// Creates a new list and adds projects to that list, to compare with
		// projectList
		activeProjectsList = new ArrayList<Project>();

	}

	@After
	public void tearDown() {

		project1 = null;
		project2 = null;
		project3 = null;
		project4 = null;
		project5 = null;
		activeProjectsList = null;
		controller = null;

	}

	@Test
	public void getActiveProjectsControllerTest() {

		// set projects to active state
		project1.setProjectStatus(1);
		project2.setProjectStatus(1);
		project3.setProjectStatus(1);
		project4.setProjectStatus(1);
		project5.setProjectStatus(0);

		// add projects to projects list to create expected result
		activeProjectsList.add(project1);
		activeProjectsList.add(project2);
		activeProjectsList.add(project3);
		activeProjectsList.add(project4);

		// Checks if both lists are the same
		assertEquals(activeProjectsList, controller.getActiveProjectsController(projectRepository));
	}

}
