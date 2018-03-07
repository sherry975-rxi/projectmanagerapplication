package project.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project.model.Project;
import project.model.ProjectContainer;
import project.model.User;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GetActiveProjectsControllerTest {

	User projectManager;
	Project project1;
	Project project2;
	Project project3;
	Project project4;
	Project project5;
	ProjectContainer projectContainer;
	List<Project> activeProjectsList;
	GetActiveProjectsController controller;

	@Before
	public void setUp() {

		// Creates a new GetActiveProjectsController
		controller = new GetActiveProjectsController();

		// create projectManager
		projectManager = new User("João", "joao@gmail.com", "001", "Admin", "920000000");

		projectContainer = new ProjectContainer();

		// create project and set a user to Project manager
		project1 = new Project(0, "name3", "description4", projectManager);
		project2 = projectContainer.createProject("name3", "description4", projectManager);
		project3 = projectContainer.createProject("name3", "description4", projectManager);
		project4 = projectContainer.createProject("name3", "description4", projectManager);
		project5 = projectContainer.createProject("name3", "description4", projectManager);

		// add project to project repository
		projectContainer.addProjectToProjectContainer(project1);
		projectContainer.addProjectToProjectContainer(project2);
		projectContainer.addProjectToProjectContainer(project3);
		projectContainer.addProjectToProjectContainer(project4);
		projectContainer.addProjectToProjectContainer(project5);

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
		projectContainer = null;

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
		assertEquals(activeProjectsList, controller.getActiveProjectsController(projectContainer));
	}

}
