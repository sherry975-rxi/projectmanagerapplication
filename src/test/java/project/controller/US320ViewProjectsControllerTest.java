package project.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.model.Company;
import project.model.Project;
import project.model.User;

public class US320ViewProjectsControllerTest {

	Company testCompany;
	Project activeProject;
	Project inactiveProject;
	User activeManager;
	User inactiveManager;
	US320ViewProjectsController projectListsController;
	String activeProjectData;

	@Before
	public void setUp() {
		// Gets the company and creates Two users
		testCompany = Company.getTheInstance();

		activeManager = new User("Daniel", "email", "idNumber", "function", "123456789");
		inactiveManager = new User("Johnny", "email2", "idNumber2", "function2", "987654321");

		testCompany.getUsersRepository().addUserToUserRepository(activeManager);
		testCompany.getUsersRepository().addUserToUserRepository(inactiveManager);

		// creates both an active and an Inactive Project using their rewspective
		// Project Managers
		activeProject = new Project(1, "Active Project", "this Project is active", activeManager);
		inactiveProject = new Project(2, "Inactive Project", "this Project is inactive", inactiveManager);

		activeProject.setProjectStatus(Project.EXECUTION);
		inactiveProject.setProjectStatus(Project.PLANNING);

		testCompany.getProjectsRepository().addProjectToProjectRepository(activeProject);
		testCompany.getProjectsRepository().addProjectToProjectRepository(inactiveProject);

		// creates a string from activeProject's overview data, to be compared with the
		// various tests
		activeProjectData = "==============================\n";
		activeProjectData += "===== ";
		activeProjectData += "1";
		activeProjectData += " - Active Project =====\n";
		activeProjectData += "==============================";
		activeProjectData += "\n - Status: Execution";
		activeProjectData += "\n - Manager: Daniel";
		activeProjectData += "\n - Description: this Project is active";
		activeProjectData += "\n==============================";

	}

	@After
	public void tearDown() {
		Company.clear();
		testCompany = null;
		activeProject = null;
		inactiveProject = null;
		activeManager = null;
		inactiveManager = null;
		projectListsController = null;
	}

	/**
	 * This test confirms the header generation is working correctly, given "=" and
	 */
	@Test
	public void generateHeaderTest() {
		projectListsController = new US320ViewProjectsController();

		String headerChar = "=";
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

		projectListsController = new US320ViewProjectsController();

		assertTrue(activeProjectData.equals(projectListsController.overviewProjectAsString(activeProject)));
	}

	/**
	 * AThis test confirms the viewActiveProjectsList method returns a list smaller
	 * than the actual list of projects, containing only one entry
	 * 
	 */
	@Test
	public void activeProjectsListTest() {
		projectListsController = new US320ViewProjectsController();

		assertEquals(1, projectListsController.viewActiveProjects().size());

		// also asserts that the contents of index 0 matches the data of the only active
		// project
		assertTrue(projectListsController.viewAllProjects().get(0).equals("::Project nº1:: \n" + activeProjectData));
	}

	/**
	 * This test confirms the all Projects List test returns a list the same size
	 * 
	 */
	@Test
	public void allProjectsListTest() {
		projectListsController = new US320ViewProjectsController();
		int projectRepositorySize = testCompany.getProjectsRepository().getAllProjects().size();

		assertEquals(projectRepositorySize, projectListsController.viewAllProjects().size());

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
		projectListsController = new US320ViewProjectsController();
		projectListsController.viewAllProjects();

		assertEquals(projectListsController.selectProject(1), activeProject);

		// visible index 2 must match the index 1 of the actual list, "inactiveProject"
		projectListsController = new US320ViewProjectsController();
		projectListsController.viewAllProjects();

		assertEquals(projectListsController.selectProject(2), inactiveProject);

		// invalid visible index must return null
		projectListsController = new US320ViewProjectsController();
		projectListsController.viewAllProjects();

		assertEquals(projectListsController.selectProject(0), null);
		assertEquals(projectListsController.selectProject(-1), null);

	}

}
