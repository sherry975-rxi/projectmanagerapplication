package project.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import project.Repository.ProjectsRepository;
import project.Services.ProjectService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class ProjectContainerTest {

	@Mock
	private ProjectsRepository projectRepositoryMock;

	@InjectMocks
	private ProjectService ProjectContainer = new ProjectService();

	User user1;
	User user2;
	User user3;

	Project project1;
	Project project2;
	Project project3;
	Project project4;
	Project project5;
	Project project6;
	Project projectA;
	Project projectB;

	List<Project> expResultProjectList;


	@Before
	public void setUp() {
		initMocks(this);

		user1 = new User("name", "email@gmail.com", "idNumber", "function", "123456789");
		user2 = new User("name2", "email2@gmail.com", "idNumber2", "function2", "987654321");
		user3 = new User("name6", "email6@gmail.com", "idNumber6", "function6", "987654271");

		project1 = new Project("name3", "description3", user1);
		project2 = new Project("name4", "description5", user2);
		project3 = new Project("name5", "description5", user3);
		project4 = new Project("project4", "description5", user3);
		project5 = new Project("project5", "description5", user3);
		project6 = new Project("project6", "description5", user3);

		expResultProjectList = new ArrayList<>();

	}

	@After
	public void tearDown() {

		user1 = null;
		user2 = null;
		user3 = null;
		project1 = null;
		project2 = null;
		project3 = null;
		project4 = null;
		project5 = null;
		project6 = null;

		projectA = null;
		projectB = null;


	}

	/**
	 * Tests the ProjectsRepository constructor.
	 */
	@Test
	public void test_Constructor() {

		assertEquals(expResultProjectList, ProjectContainer.getAllProjectsfromProjectsContainer());

	}

	/**
	 * Tests the CreateProject method by calling the method equals (project) to
	 * assert if the project created is equal to other project. If the equals
	 * returns TRUE means the two projects are equal, so the creatProject method
	 * worked.
	 */
	@Test
	public void testCreateProject() {

		assertTrue(project1.equals(ProjectContainer.createProject("name3", "description3", user1)));

	}

	/**
	 * Tests the addProjectToProjectContainer by asserting if the list within the
	 * projectContainer is equal to a new list of projects created, after adding
	 * the same project to both lists.
	 */
	@Test
	public void testAddProjectToProjectContainer() {

		ProjectContainer.addProjectToProjectContainer(project1);

		expResultProjectList.add(project1);

		assertEquals(expResultProjectList, ProjectContainer.getAllProjectsfromProjectsContainer());

		// Tried to add again the same project
		ProjectContainer.addProjectToProjectContainer(project1);

		/*
		 * The result will be the same as before, as the method doesn't allow to add
		 * projects that already exist in the projectContainer
		 */

		assertEquals(expResultProjectList, ProjectContainer.getAllProjectsfromProjectsContainer());

	}

	/**
	 * Tests the getAllProjectsfromProjectsContainer by asserting if the list within the
	 * projectContainer is equal to a new list of projects created.
	 */
	@Test
	public void testGetAllProjects() {

		ProjectContainer.addProjectToProjectContainer(project1);

		expResultProjectList.add(project1);

		assertEquals(expResultProjectList, ProjectContainer.getAllProjectsfromProjectsContainer());
	}

	/**
	 * This test compares a list (expResult) with only active projects to the list
	 * returned by the method getActiveProjects, which has to output only the active
	 * projects from the repository.
	 */
	@Test
	public void testGetActiveProjects() {

		// Adds the projects to the project repository
		ProjectContainer.addProjectToProjectContainer(project1);
		ProjectContainer.addProjectToProjectContainer(project2);
		ProjectContainer.addProjectToProjectContainer(project3);
		ProjectContainer.addProjectToProjectContainer(project4);
		ProjectContainer.addProjectToProjectContainer(project5);
		ProjectContainer.addProjectToProjectContainer(project6);

		// Sets the project status to Planning (0, Not active)
		project1.setProjectStatus(0);

		// Sets the project status to Initiation (1, Active)
		project2.setProjectStatus(1);

		// Sets the project status to Executing (2, Active)
		project3.setProjectStatus(2);

		// Sets the project status to Delivery (3, Active)
		project4.setProjectStatus(3);

		// Sets the project status to Review (4, Active)
		project5.setProjectStatus(4);

		// Sets the project status to Close (5, Active)
		project6.setProjectStatus(5);

		// Adds the projects to the expResult list to be compared to.
		expResultProjectList.add(project2);
		expResultProjectList.add(project3);
		expResultProjectList.add(project4);
		expResultProjectList.add(project5);

		assertEquals(expResultProjectList, ProjectContainer.getActiveProjects());
	}


	/**
	 * test to isProjectInProjectContainer.
	 */
	@Test
	public void test_isProjectInProjectContainer() {
		// before add the project, verify if the project1 is in project repository
		assertFalse(ProjectContainer.isProjectInProjectContainer(project1));

		// add project 1 to project repository
		ProjectContainer.addProjectToProjectContainer(project1);

		// verify if project1 it was add to project repository
		assertTrue(ProjectContainer.isProjectInProjectContainer(project1));
	}



	/**
	 * Tests the getProjById method
	 *
	 */
	@Test
	public void testGetProjectById() {

		// Adds two project to the projectContainer
		projectA = ProjectContainer.createProject("ProjA", "ProjectoA", user1);
		projectB = ProjectContainer.createProject("ProjB", "ProjectoA", user1);
		ProjectContainer.addProjectToProjectContainer(projectA);
		ProjectContainer.addProjectToProjectContainer(projectB);
		// projectContainer.addProjectToProjectContainer(projectC);

		// Asserts if the getProjById returns the expected Project
		assertEquals(ProjectContainer.getProjectById(0), projectA);
		assertEquals(ProjectContainer.getProjectById(1), projectB);
		assertEquals(ProjectContainer.getProjectById(3), null);

	}

	@Test
	public void testUpdateProjectContainer(){

		//GIVEN two projects in a project list

		expResultProjectList.add(project1);
		expResultProjectList.add(project2);

		//WHEN projectRepository.findAll() is directed to the mock project list
		Mockito.when(projectRepositoryMock.findAll()).thenReturn(expResultProjectList);

		//THEN the updateProjectContainer method must copy the mocked list
		ProjectContainer.updateProject();


		assertEquals(expResultProjectList, ProjectContainer.getAllProjectsfromProjectsContainer());
	}

}
