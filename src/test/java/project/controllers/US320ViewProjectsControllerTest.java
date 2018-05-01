package project.controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import project.model.Project;
import project.model.User;
import project.services.ProjectService;
import project.services.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan({ "project.services", "project.model", "project.controllers" })
public class US320ViewProjectsControllerTest {
	@Autowired
	UserService userService;
	@Autowired
	ProjectService projectService;
	@Autowired
	US320ViewProjectsController projectListsController;
	Project activeProject;
	Project inactiveProject;
	User activeManager;
	User inactiveManager;
	String activeProjectData;

	@Before
	public void setUp() {
		activeProjectData = null;
		activeManager = userService.createUser("Manel", "user1@gmail.com", "001", "Empregado", "930000000", "Rua Bla",
				"BlaBla", "BlaBlaBla", "BlaBlaBlaBla", "Blalandia");
		inactiveManager = userService.createUser("Joaquim", "user2@gmail.com", "002", "Inactive", "930000000",
				"Rua Bla", "BlaBla", "BlaBlaBla", "BlaBlaBlaBla", "Blalandia");
		// creates both an active and an Inactive Project using their respective
		// Project Managers
		activeProject = projectService.createProject("Active Project", "this Project is active", activeManager);
		inactiveProject = projectService.createProject("Inactive Project", "this Project is inactive", inactiveManager);
		activeProject.setProjectStatus(Project.EXECUTION);
		inactiveProject.setProjectStatus(Project.CLOSE);
		projectService.updateProject(activeProject);
		projectService.updateProject(inactiveProject);
		// creates expected headers for the project's name
		// first creates a title header
		String activeProjNameHeader = "===== " + activeProject.getProjectId() + " - Active Project =====";
		// then generates the top and bottom headers from the same length as the title
		String activeProjHeader = projectListsController.generateHeader('=', activeProjNameHeader.length());
		// creates a string from activeProject's overview data, to be compared with the
		// various tests
		activeProjectData = activeProjHeader;
		activeProjectData += "\n";
		activeProjectData += activeProjNameHeader;
		activeProjectData += "\n";
		activeProjectData += activeProjHeader;
		activeProjectData += "\n - Status: Execution";
		activeProjectData += "\n - Manager: Manel";
		activeProjectData += "\n - Description: this Project is active\n";
		activeProjectData += activeProjHeader;
	}

	@After
	public void clear() {

		activeProject = null;
		inactiveProject = null;
		activeManager = null;
		inactiveManager = null;
		activeProjectData = null;

	}

	/**
	 * This test confirms the header generation is working correctly, given "=" and
	 */
	@Test
	public void generateHeaderTest() {
		char headerChar = '=';
		int headerSize = 20;
		String expectedHeader = "====================";
		assertTrue(expectedHeader.equals(projectListsController.generateHeader(headerChar, headerSize)));
	}

	/**
	 * This test confirms the overviewProjectAsString() method properly converts the
	 * Project's basic data into a String
	 *
	 */
	@Test
	public void projectToStringTest() {
		assertEquals(activeProjectData, (projectListsController.overviewProjectAsString(activeProject)));
	}

	/**
	 * AThis test confirms the viewActiveProjectsList method returns a list smaller
	 * than the actual list of projects, containing only one entry
	 *
	 */
	@Test
	public void activeProjectsListTest() {
		assertEquals(1, projectListsController.viewActiveProjects().size());
		assertEquals(2, projectListsController.viewAllProjects().size());
		// also asserts that the contents of index 0 matches the data of the only active
		// project
		assertEquals(("[1] \n" + activeProjectData), projectListsController.viewAllProjects().get(0));
	}

	/**
	 * This test confirms the all Projects List test returns a list the same size
	 *
	 */
	@Test
	public void allProjectsListTest() {
		int ProjectContainerSize = projectService.getAllProjectsfromProjectsContainer().size();
		assertEquals(ProjectContainerSize, projectListsController.viewAllProjects().size());
		// also asserts that the contents of index 0 and 1 are different, and weren't
		// duplicated
		assertFalse(projectListsController.viewAllProjects().get(0)
				.equals(projectListsController.viewAllProjects().get(1)));
	}

	/**
	 * This test confirms the selectProject method is working correctly, returning a
	 * project to be handled by the director
	 *
	 */
	@Test
	public void selectProjectTest() {
		// visible index 1 must match the index 0 of the actual list, "activeProject"
		projectListsController.viewAllProjects();
		assertEquals(projectListsController.selectProject(1), activeProject);
		// visible index 2 must match the index 1 of the actual list, "inactiveProject"
		projectListsController.viewAllProjects();
		assertEquals(projectListsController.selectProject(2), inactiveProject);
		assertEquals(projectListsController.selectProject(0), null);
	}

	/**
	 * This test the setter of controller from a Project List
	 *
	 */
	@Test
	public void testSetterProjectsList() {

		Project activeProject2 = projectService.createProject("Active Project", "this Project is active", activeManager);

		activeProject2.setProjectStatus(Project.EXECUTION);

		List<Project> selectedProjectList = new ArrayList<>();
		selectedProjectList.add(activeProject2);
		projectListsController.setChosenList(selectedProjectList);

		assertEquals(selectedProjectList, projectListsController.getChosenList());


	}

	/**
	 * This test the setter of controller from a Specific Project
	 *
	 */
	@Test
	public void testSetterProject() {

		Project activeProject3 = projectService.createProject("Active Project", "this Project is active", activeManager);

		activeProject3.setProjectStatus(Project.EXECUTION);


		projectListsController.setSelectedProject(activeProject3);

		assertEquals(activeProject3, projectListsController.getSelectedProject());


	}
}
