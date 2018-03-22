package project.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import project.Services.ProjectService;
import project.Services.UserService;
import project.model.Project;
import project.model.User;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan({ "project.services", "project.model", "project.controller" })
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

	String activeProjectData, activeProjectData2;

	@Before
	public void setUp() {

		activeProjectData = null;
		activeProjectData2 = null;

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

		// creates a string from activeProject's overview data, to be compared with the
		// various tests
		activeProjectData = "==============================\n";
		activeProjectData += "===== ";
		activeProjectData += activeProject.getId();
		activeProjectData += " - Active Project =====\n";
		activeProjectData += "==============================";
		activeProjectData += "\n - Status: Execution";
		activeProjectData += "\n - Manager: Manel";
		activeProjectData += "\n - Description: this Project is active";
		activeProjectData += "\n==============================";

		activeProjectData2 = "===============================\n";
		activeProjectData2 += "===== ";
		activeProjectData2 += activeProject.getId();
		activeProjectData2 += " - Active Project =====\n";
		activeProjectData2 += "===============================";
		activeProjectData2 += "\n - Status: Execution";
		activeProjectData2 += "\n - Manager: Manel";
		activeProjectData2 += "\n - Description: this Project is active";
		activeProjectData2 += "\n===============================";

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

		assertTrue(activeProjectData.equals(projectListsController.overviewProjectAsString(activeProject)));
	}

	/**
	 * AThis test confirms the viewActiveProjectsList method returns a list smaller
	 * than the actual list of projects, containing only one entry
	 * 
	 */
	@Test
	public void activeProjectsListTest() {

		assertEquals(1, projectListsController.viewActiveProjects().size());

		// also asserts that the contents of index 0 matches the data of the only active
		// project
		assertTrue(projectListsController.viewAllProjects().get(0).equals("[1] \n" + activeProjectData2));
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

}
