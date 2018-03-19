package project.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.runners.MockitoJUnitRunner;

import project.Repository.ProjCollabRepository;
import project.Repository.ProjectsRepository;
import project.Services.ProjectService;

@RunWith(MockitoJUnitRunner.class)
public class ProjectServiceTest {

	@Mock
	private ProjectsRepository projectRep;

	@Mock
	private ProjCollabRepository projectCollaboratorRepository;

	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();

	ProjectService projectService;
	Project project1, project2, project3, project4;
	ArrayList<Project> projectList;
	Calendar finishDate;

	User projectManager, user1, user2;
	ProjectCollaborator projCollab1, projCollab2;

	@Before
	public void setup() {

		projectManager = new User();
		user1 = new User();
		user2 = new User("Joao", "jmscrl@hotmail.com", "123", "Collab", "22222");
		projCollab1 = new ProjectCollaborator(user1, 10);
		projCollab2 = new ProjectCollaborator(user2, 20);

		projectService = new ProjectService(projectRep, projectCollaboratorRepository);
		project1 = new Project("Project1", "Descricao", projectManager);
		project2 = new Project("Project2", "Descricao", projectManager);
		project3 = new Project("Project3", "Descricao", projectManager);
		project4 = new Project("Project4", "Descricao", projectManager);
		projectList = new ArrayList<Project>();
		finishDate = Calendar.getInstance();

	}

	@After
	public void tearDown() {

		projectService = null;
		project1 = null;
		project2 = null;
		project3 = null;
		project4 = null;
		projectList = null;
		projectManager = null;
		finishDate = null;
		user1 = null;
		projCollab1 = null;
		projCollab2 = null;

	}

	@Test
	public void testCreateProject() {

		/*
		 * Verifies that the method createProject calls the method save in the
		 * ProjectRepository once
		 */
		Mockito.when(projectService.createProject("Project 1", "Descricao", projectManager)).thenReturn(project1);

		assertEquals(projectService.createProject("Project 1", "Descricao", projectManager), project1);

		Mockito.verify(projectRep, Mockito.times(1)).save(project1);

	}

	@Test
	public void testGetProjectById() {

		// Creates a project
		Project project1 = projectService.createProject("Project 1", "Descricao", projectManager);

		/*
		 * Verifies that the method getProjectById uses the method findById once from
		 * the ProjectRepository
		 */
		Mockito.when(projectService.getProjectById(project1.getId())).thenReturn(project1);

		assertEquals(projectService.getProjectById(project1.getId()), project1);

		Mockito.verify(projectRep, Mockito.times(1)).findById(project1.getId());

	}

	@Test
	public void testGetAllProjectsfromProjectsContainer() {

		// Creates a project list with 4 projects

		projectList.add(project1);
		projectList.add(project2);
		projectList.add(project3);
		projectList.add(project4);

		// creates a list with the 4 projects in it
		ArrayList<Project> allProjects = new ArrayList<Project>();
		allProjects.add(project1);
		allProjects.add(project2);
		allProjects.add(project3);
		allProjects.add(project4);

		/*
		 * Verifies that the method getAllProjectsFromProjectsContainets uses the method
		 * .findAll in the ProjectRepository once
		 */
		Mockito.when(projectService.getAllProjectsfromProjectsContainer()).thenReturn(allProjects);

		assertEquals(projectService.getAllProjectsfromProjectsContainer(), allProjects);

		Mockito.verify(projectRep, Mockito.times(1)).findAll();

	}

	@Test
	public void testGetActiveProjects() {

		// Creates a project list with 4 projects

		projectList.add(project1);
		projectList.add(project2);
		projectList.add(project3);
		projectList.add(project4);

		// Sets a finish date for project1 and project3
		project1.setFinishdate(finishDate);
		project1.setStatus(5);
		project3.setFinishdate(finishDate);
		project3.setStatus(5);

		// Creates a list with the active projects, project2 and project4
		ArrayList<Project> allActiveProjects = new ArrayList<Project>();

		allActiveProjects.add(project2);
		allActiveProjects.add(project4);

		/*
		 * Verifies that the method findAll in the projectRepository is used once when
		 * method getActiveProjects is called
		 */
		Mockito.when(projectService.getActiveProjects()).thenReturn(allActiveProjects);

		assertEquals(projectService.getActiveProjects(), allActiveProjects);

		Mockito.verify(projectRep, Mockito.times(1)).findAll();

	}

	@Test
	public void testsIsProjectInProjectContainer() {

		// Creates a project list with 4 projects
		projectList.add(project1);
		projectList.add(project2);
		projectList.add(project3);
		projectList.add(project4);

		/*
		 * Verifies that the method isProjectInProjectContainer uses the method "exists"
		 * in the ProjectRepository once
		 */
		Mockito.when(projectService.isProjectInProjectContainer(project1.getIdCode())).thenReturn(Boolean.TRUE);

		assertEquals(projectService.isProjectInProjectContainer(project1.getIdCode()), Boolean.TRUE);

		Mockito.verify(projectRep, Mockito.times(1)).exists(project1.getIdCode());

	}

	////////////////////////
	// @Test
	public void testsGetProjectsFromUser() {

		// Creates a project list with 4 projects

		projectList.add(project1);
		projectList.add(project2);
		projectList.add(project3);
		projectList.add(project4);

		// Creates a projectCollaborator to project2 and 3 (From user 1)
		project2.createProjectCollaborator(user1, 10);
		project3.createProjectCollaborator(user1, 10);

		// Creates a list of Projects with project2 and project3
		ArrayList<Project> projectsFromUser = new ArrayList<Project>();
		projectsFromUser.add(project2);
		projectsFromUser.add(project3);

		/*
		 * Verifies if the method findAllByCollaborator is called once when method
		 * getProjectsFromUser is called
		 */
		Mockito.when(projectService.getProjectsFromUser(user2)).thenReturn(projectsFromUser);
		assertEquals(projectService.getProjectsFromUser(user2), projectsFromUser);
		// Mockito.verify(projectCollaboratorRepository,
		// Mockito.times(1)).findAllByCollaborator(user1);

	}

	@Test
	public void testsGetProjectsFromProjectManager() {

		// Creates a project list with 4 projects
		projectList.add(project1);
		projectList.add(project2);
		projectList.add(project3);
		projectList.add(project4);

		// Sets user1 to be PM of project1 and project3
		project2.setProjectManager(user1);
		project3.setProjectManager(user1);

		// Creates a list with project 2 and 3
		ArrayList<Project> projectsFromProjectManager = new ArrayList<Project>();
		projectsFromProjectManager.add(project2);
		projectsFromProjectManager.add(project3);

		/*
		 * Verifies if the method findAllByProjectManager is called once when the method
		 * getProjectsFromProjectManager is called
		 */
		Mockito.when(projectService.getProjectsFromProjectManager(user1)).thenReturn(projectsFromProjectManager);
		assertEquals(projectService.getProjectsFromProjectManager(user1), projectsFromProjectManager);
		Mockito.verify(projectRep, Mockito.times(1)).findAllByProjectManager(user1);

	}

	@Test
	public void testUpdateProject() {

		/*
		 * Verifies that the method updateProject calls the method save from the
		 * ProjectRepository when called
		 */
		projectService.updateProject(project1);
		Mockito.verify(projectRep, Mockito.times(1)).save(project1);

	}

	@Test
	public void testAddProjectCollaborator() {

		// Adds a ProjectCollaborator to the Repository
		projectService.addProjectCollaborator(projCollab1);

		// Verifies that the method save was called once
		Mockito.verify(projectCollaboratorRepository, Mockito.times(1)).save(projCollab1);

	}

	@Test
	public void testCreateProjectCollaborator() {

		/*
		 * Verifies that the method save is called once when the method
		 * createProjectCollaborator is used
		 */

		projectService.createProjectCollaborator(user1, project1, 10);
		Mockito.verify(projectCollaboratorRepository, Mockito.times(1)).save(projCollab1);

		Mockito.when(projectService.createProjectCollaborator(user1, project1, 10)).thenReturn(projCollab1);
		assertEquals(projectService.createProjectCollaborator(user1, project1, 10), projCollab1);
	}

	@Test
	public void testGetActiveProjectTeam() {

		ArrayList<ProjectCollaborator> activeTeam = new ArrayList<>();

		project1.createProjectCollaborator(user1, 10);
		project1.createProjectCollaborator(user2, 20);
		ProjectCollaborator testProjCollab1 = project1.getProjectCollaboratorFromUser(user1);
		ProjectCollaborator testProjCollab2 = project1.getProjectCollaboratorFromUser(user2);

		activeTeam.add(projCollab2);

		/*
		 * Verifies that the method findAllByProject is called once when the method
		 * getActiveProjectTeam is used
		 */
		projectService.getActiveProjectTeam(project1);
		Mockito.verify(projectCollaboratorRepository, Mockito.times(1)).findAllByProject(project1);
		Mockito.when(projectService.getActiveProjectTeam(project1)).thenReturn(activeTeam);
		assertEquals(projectService.getActiveProjectTeam(project1), activeTeam);

	}

	@Test
	public void testGetProjectTeam() {

		ArrayList<ProjectCollaborator> projectTeam = new ArrayList<>();

		project1.createProjectCollaborator(user1, 10);
		project1.createProjectCollaborator(user2, 20);
		ProjectCollaborator testProjCollab1 = project1.getProjectCollaboratorFromUser(user1);
		ProjectCollaborator testProjCollab2 = project1.getProjectCollaboratorFromUser(user2);

		projectTeam.add(testProjCollab1);
		projectTeam.add(testProjCollab2);

		/*
		 * Verifies that the method findAllByProject is called once when the method
		 * getProjectTeam is used
		 */
		projectService.getProjectTeam(project1);
		Mockito.verify(projectCollaboratorRepository, Mockito.times(1)).findAllByProject(project1);

		Mockito.when(projectService.getProjectTeam(project1)).thenReturn(projectTeam);
		assertEquals(projectService.getProjectTeam(project1), projectTeam);

	}

	@Test
	public void testIsUserInProjectTeam() {

		/*
		 * Creates two projectCollaborators
		 */
		project1.createProjectCollaborator(user1, 10);
		project1.createProjectCollaborator(user2, 20);

		ProjectCollaborator testProjCollab1 = project1.getProjectCollaboratorFromUser(user1);
		ProjectCollaborator testProjCollab2 = project1.getProjectCollaboratorFromUser(user2);

		/*
		 * Verifies that the method findAllByProject is called once when the method
		 * isUserInProject is used
		 */

		ArrayList<Project> projectList = new ArrayList<>();
		projectList.add(project1);

		projectService.isUserInProjectTeam(user1, project1);
		Mockito.verify(projectCollaboratorRepository, Mockito.times(1)).findAllByProject(project1);

	}

	@Test
	public void testIsUserActiveInProject() {

	}

	@Test
	public void testFindProjectCollaborator() {

	}

}
