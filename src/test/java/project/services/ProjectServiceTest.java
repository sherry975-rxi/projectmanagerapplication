package project.services;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.runners.MockitoJUnitRunner;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.Task;
import project.model.User;
import project.repository.ProjCollabRepository;
import project.repository.ProjectsRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class ProjectServiceTest {

	@Mock
	private ProjectsRepository projectRep;

	@Mock
	private ProjCollabRepository projectCollaboratorRepository;

	@Mock
	private User mockedUser, mockedUser2, mockedUser3;

	@Mock
	private Task mockedTask;

	@Mock
	private Project mockedProject1, mockedProject2, mockedProject3, mockedProject4;

	@Mock
	private ProjectCollaborator projCollabMockedUser, projCollabMockedUser2, projCollabMockedUser3;

	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();

	@InjectMocks
	private ProjectService projectService;

	private Project project1, project2, project3, project4;
	private ArrayList<Project> projectList;
	private Calendar finishDate;

	private User projectManager, user1, user2, otherUser;

	private ProjectCollaborator projCollab1, projCollab2, projCollab3;

	@Before
	public void setup() {
		initMocks(this);

		projectManager = new User("Joao", "mail@gmail.com", "01", "Project Manager", "22182939");

		user1 = new User("Joao", "joao@gmail.com", "01", "Project Manager", "22182939");
		user2 = new User("Ana", "ana@gmail.com", "01", "Project Manager", "22182939");
		mockedUser = new User("Rui", "rui@gmail.com", "01", "Project Manager", "22182939");
		mockedUser2 = new User("Miguel", "miguel@hotmail.com", "123", "Collab", "22222");

		otherUser = new User("Joao", "otherUser@gmail.com", "01", "Project Manager", "22182939");
		projCollab1 = new ProjectCollaborator(user1, 10);
		projCollab2 = new ProjectCollaborator(user2, 20);
		projCollab3 = new ProjectCollaborator(user1, 10);
		projCollabMockedUser = new ProjectCollaborator(mockedUser, 10);
		projCollabMockedUser2 = new ProjectCollaborator(mockedUser2, 10);
		projCollabMockedUser3 = new ProjectCollaborator(mockedUser, 10);

		mockedTask = new Task();

		projectService = new ProjectService(projectRep, projectCollaboratorRepository);
		project1 = new Project("Project1", "Descricao", projectManager);
		project2 = new Project("Project2", "Descricao", projectManager);
		project3 = new Project("Project3", "Descricao", projectManager);
		project4 = new Project("Project4", "Descricao", projectManager);
		mockedProject1 = new Project("MockedProject", "Descricao", projectManager);
		mockedProject2 = new Project("MockedProject2", "Descricao", projectManager);
		mockedProject3 = new Project("MockedProject3", "Descricao", projectManager);
		mockedProject4 = new Project("MockedProject4", "Descricao", projectManager);

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
		otherUser = null;
		projCollabMockedUser = null;
		projCollabMockedUser2 = null;
		projCollabMockedUser3 = null;
		mockedProject1 = null;
		mockedProject2 = null;
		mockedProject3 = null;
		mockedProject4 = null;

	}

	@Test
	public void testCreateProject() {

		/*
		 * Verifies that the method createProject calls the method save in the
		 * ProjectRepository once
		 * 
		 * 
		 */

		Mockito.when(projectService.createProject("Project 1", "Descricao", projectManager)).thenReturn(project1);
		projectService.createProject("Project 1", "Descricao", projectManager);
		Mockito.verify(projectRep, Mockito.times(1)).save(project1);

	}

	

	@Test
	public void testAddProjectToProjectContainer() {

		/*
		 * Verifies that the method addProjectToProjectContainer uses the method .save
		 * once
		 */
		projectService.addProjectToProjectContainer(project1);
		Mockito.verify(projectRep, Mockito.times(1)).save(project1);

	}

	@Test
	public void testGetAllProjectsfromProjectsContainer() {

		// creates a list with the 4 projects in it
		ArrayList<Project> allProjects = new ArrayList<Project>();

		allProjects.add(project1);
		allProjects.add(project2);
		allProjects.add(project3);
		allProjects.add(project4);

		/*
		 * When the method getAllProjectsFromProjectsContainer is user, the
		 * ProjectRepository will return a list wit the 4 projects
		 */

		Mockito.when(projectRep.findAll()).thenReturn(allProjects);
		assertEquals(projectService.getAllProjectsfromProjectsContainer(), allProjects);

		/*
		 * Verifies that the method getAllProjectsFromProjectsContainets uses the method
		 * .findAll in the ProjectRepository once
		 */
		Mockito.verify(projectRep, Mockito.times(1)).findAll();

	}

	@Test
	public void testGetActiveProjects() {

		// Creates a project list with 4 projects

		projectList.add(mockedProject1);
		projectList.add(mockedProject2);
		projectList.add(mockedProject3);
		projectList.add(mockedProject4);

		// Sets a finish date for project1 and project3
		mockedProject1.setFinishdate(finishDate);
		mockedProject1.setStatus(5);
		mockedProject3.setFinishdate(finishDate);
		mockedProject3.setStatus(5);

		// Creates a list with all the projects
		ArrayList<Project> allProjects = new ArrayList<Project>();

		allProjects.add(mockedProject1);
		allProjects.add(mockedProject2);
		allProjects.add(mockedProject3);
		allProjects.add(mockedProject4);

		// Creates a list with the active projects, project2 and project4
		ArrayList<Project> allActiveProjects = new ArrayList<Project>();

		allActiveProjects.add(mockedProject2);
		allActiveProjects.add(mockedProject4);

		/*
		 * when the method getActiveProjects is called, it will call the method
		 * getAllProjectsFromProjectsContainer
		 */
		Mockito.when(projectService.getAllProjectsfromProjectsContainer()).thenReturn(allProjects);

		assertEquals(projectService.getActiveProjects(), allActiveProjects);

		/*
		 * Verifies that the method findAll in the projectRepository is used once when
		 * method getActiveProjects is called
		 */
		Mockito.verify(projectRep, Mockito.times(1)).findAll();

	}

	@Test
	public void testsIsProjectInProjectContainer() {

		Integer projectNotExist = 14;

		Mockito.when(projectRep.existsById(project1.getIdCode())).thenReturn(Boolean.TRUE);
		Mockito.when(projectRep.existsById(projectNotExist)).thenReturn(Boolean.FALSE);

		assertEquals(projectService.isProjectInProjectContainer(project1.getIdCode()), Boolean.TRUE);
		assertEquals(projectService.isProjectInProjectContainer(projectNotExist), Boolean.FALSE);

		/*
		 * Verifies that the method isProjectInProjectContainer uses the method "exists"
		 * in the ProjectRepository once
		 */
		Mockito.verify(projectRep, Mockito.times(1)).existsById(project1.getIdCode());

	}

	// TODO
	@Test
	public void testsGetProjectsFromUser() {

		/*
		 * Creates a list of projectCollaboratorsFromUser
		 */
		ArrayList<ProjectCollaborator> projectCollaboratorFromUser = new ArrayList<>();

		/*
		 * Adds a projectCollaborator to the team
		 */
		projectCollaboratorFromUser.add(projCollabMockedUser);
		projectCollaboratorFromUser.add(projCollabMockedUser3);

		/*
		 * When the method findallByCollaborator is called, it will return the previous
		 * list of projectCollaborator
		 */
		Mockito.when(projectCollaboratorRepository.findAllByCollaborator(mockedUser))
				.thenReturn(projectCollaboratorFromUser);

		/*
		 * Creates a projectCollaborator from mockedUser
		 */
		project1.createProjectCollaborator(mockedUser, 10);
		project3.createProjectCollaborator(mockedUser3, 15);

		/*
		 * Sets project1 to projCollabMockedUser(mockedUser)
		 */
		projCollabMockedUser.setProject(project1);

		/*
		 * Sets project1 to projCollabMockedUser3(mockedUser)
		 */
		projCollabMockedUser3.setProject(project3);

		/*
		 * Creates a list with 4 projects in it
		 */
		ArrayList<Project> projectsList = new ArrayList<>();

		/*
		 * Sets the ID of the projects
		 */
		project1.setId(0);
		project2.setId(1);
		project3.setId(2);
		project4.setId(3);

		/*
		 * adds the 4 projects to the list
		 */
		projectsList.add(project1);
		projectsList.add(project2);
		projectsList.add(project3);
		projectsList.add(project4);

		/*
		 * When the method getAllProjectsFromProjectsContainer is called, it will return
		 * the previous list of projects created
		 */

		Mockito.when(projectService.getAllProjectsfromProjectsContainer()).thenReturn(projectsList);

		/*
		 * Creates a list of projects that is supposed to be returned by the method
		 */
		ArrayList<Project> projectsFromUser = new ArrayList<>();

		/*
		 * adds the project1 to the list
		 */
		projectsFromUser.add(project1);
		projectsFromUser.add(project3);

		/*
		 * Checks if list previously created and the method getProjectsFromUser contain
		 * the same projects
		 */

		assertEquals(projectsFromUser, projectService.getProjectsFromUser(mockedUser));

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
		List<Project> projectsOfPM = new ArrayList<>();

		Mockito.when(projectRep.findAllByProjectManager(user1)).thenReturn(projectsFromProjectManager);
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

		// Adds a ProjectCollaborator to the repository
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

	// TODO
	@Test
	public void testGetActiveProjectTeam() {

		ArrayList<ProjectCollaborator> activeTeam = new ArrayList<>();

		project1.createProjectCollaborator(user1, 10);
		project1.createProjectCollaborator(user2, 20);

		activeTeam.add(projCollabMockedUser);

		/*
		 * Verifies that the method findAllByProject is called once when the method
		 * getActiveProjectTeam is used
		 */
		Mockito.when(projectService.getActiveProjectTeam(project1)).thenReturn(activeTeam);

		assertEquals(projectService.getActiveProjectTeam(project1), activeTeam);
		Mockito.verify(projectCollaboratorRepository, Mockito.times(1)).findAllByProject(project1);

	}

	@Test
	public void testGetProjectTeam() {

		ArrayList<ProjectCollaborator> projectTeam = new ArrayList<>();

		ProjectCollaborator testProjCollab1 = project1.createProjectCollaborator(user1, 10);
		ProjectCollaborator testProjCollab2 = project1.createProjectCollaborator(user2, 20);

		projectTeam.add(testProjCollab1);
		projectTeam.add(testProjCollab2);

		Mockito.when(projectCollaboratorRepository.findAllByProject(project1)).thenReturn(projectTeam);
		assertEquals(projectService.getProjectTeam(project1), projectTeam);

		/*
		 * Verifies that the method findAllByProject is called once when the method
		 * getProjectTeam is used
		 */
		Mockito.verify(projectCollaboratorRepository, Mockito.times(1)).findAllByProject(project1);

	}

	@Test
	public void testIsUserInProjectTeam() {

		/*
		 * Creates an arrayList of ProjectCollaborators
		 */

		ArrayList<ProjectCollaborator> projectTeam = new ArrayList<>();

		/*
		 * Creates a projectCollaborator from mockedUser
		 */
		project1.createProjectCollaborator(mockedUser, 10);

		/*
		 * Sets projCollabMockedUser to mockedUser
		 */
		projCollabMockedUser = new ProjectCollaborator(mockedUser, 10);

		/*
		 * adds two ProjectCollaborators to the projectTeam list
		 */
		projectTeam.add(projCollabMockedUser);
		projectTeam.add(projCollabMockedUser2);

		Mockito.when(projectCollaboratorRepository.findAllByProject(project1)).thenReturn(projectTeam);

		/*
		 * Verifies that the mockedUser belongs to the projectTeam of Project1
		 */
		assertTrue(projectService.isUserInProjectTeam(mockedUser, project1));
		Mockito.verify(projectCollaboratorRepository, Mockito.times(1)).findAllByProject(project1);

		/*
		 * Verifies that the user1 doesn't belong to project1
		 */
		assertFalse(projectService.isUserInProjectTeam(user1, project1));

	}

	@Test
	public void testIsUserActiveInProject() {

		/*
		 * Creates a list of ProjectCollaborator and adds two active
		 * ProjectCollaborators to it
		 */
		List<ProjectCollaborator> projectTeam = new ArrayList<>();
		projectTeam.add(projCollab1);
		projectTeam.add(projCollab2);

		/*
		 * when the method .findAllByProject is called, returns a list wit the
		 * projectTean
		 */
		Mockito.when(projectCollaboratorRepository.findAllByProject(project1)).thenReturn(projectTeam);
		/*
		 * Checks if the user is Active in the Project
		 */
		assertTrue(projectService.isUserActiveInProject(user1, project1));

		/*
		 * Verifies that the method findAllByProject was called once
		 */
		Mockito.verify(projectCollaboratorRepository, Mockito.times(1)).findAllByProject(project1);

		/*
		 * Deactive projCollab1 from the project team
		 */
		projCollab1.setStatus(false);

		/*
		 * Checks that the user1 is no longer active in project1
		 */
		assertFalse(projectService.isUserActiveInProject(user1, project1));

	}

	@Test
	public void testFindProjectCollaborator() {

		/*
		 * Creates a list of ProjectCollaborator and adds two active
		 * ProjectCollaborators to it
		 */
		List<ProjectCollaborator> projectTeam = new ArrayList<>();
		projectTeam.add(projCollab1);
		projectTeam.add(projCollab2);

		/*
		 * when the method .findAllByProject is called, returns a list wit the
		 * projectTean
		 */
		Mockito.when(projectCollaboratorRepository.findAllByProject(project1)).thenReturn(projectTeam);

		assertEquals(projCollab1, (projectService.findProjectCollaborator(user1, project1).get()));

		Mockito.verify(projectCollaboratorRepository, Mockito.times(1)).findAllByProject(project1);

	}

	@Test
	public void testFindActiveProjectCollaborator() {

		/*
		 * Creates a list of ProjectCollaborator and adds two active
		 * ProjectCollaborators to it
		 */
		List<ProjectCollaborator> projectTeam = new ArrayList<>();
		projectTeam.add(projCollab1);
		projectTeam.add(projCollab2);

		/*
		 * Returns a list with all users from Project1
		 */
		Mockito.when(projectCollaboratorRepository.findAllByProject(project1)).thenReturn(projectTeam);

		/*
		 * Checks that the projCollab1 is active and belongs to user1
		 */
		assertEquals(projCollab1, (projectService.findActiveProjectCollaborator(user1, project1)));

		Mockito.verify(projectCollaboratorRepository, Mockito.times(1)).findAllByProject(project1);

		/*
		 * Set projCollab1 status to false
		 */
		projCollab1.setStatus(false);

		/*
		 * findActiveProjectCollaborator will return null, as user1 doesnt have any
		 * projectCollaborator active in project1
		 */
		assertEquals(null, (projectService.findActiveProjectCollaborator(user1, project1)));

	}

	@Test
	public void testUpdateProjectCollaborator() {

		/*
		 * Verifies that the method updateProjectCollaborator uses the method .save once
		 * with the same ProjectCollaborator
		 */

		projectService.updateProjectCollaborator(projCollabMockedUser);

		Mockito.verify(projectCollaboratorRepository, Mockito.times(1)).save(projCollabMockedUser);

	}

}
