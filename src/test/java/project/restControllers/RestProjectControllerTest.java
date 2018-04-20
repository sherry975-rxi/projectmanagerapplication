package project.restControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import project.model.EffortUnit;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.User;
import project.restcontroller.RestProjectController;
import project.services.ProjectService;
<<<<<<< HEAD
import project.services.UserService;
=======
import project.services.TaskService;
>>>>>>> 2e3c89027c939c091146ab38e29706d9db2857b9

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
<<<<<<< HEAD
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
=======
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
>>>>>>> 2e3c89027c939c091146ab38e29706d9db2857b9

@RunWith(MockitoJUnitRunner.class)
public class RestProjectControllerTest {
    @Mock
    private TaskService taskServiceMock;

    @Mock
    private ProjectService projectServiceMock;
    @Mock
    private Project projectMock;
    @Mock
    private UserService userServiceMock;

    @InjectMocks
    private RestProjectController victim;

    private MockMvc mvc;
    private Integer projectId;
    private JacksonTester<Project> jacksonProject;
    private User userRui;
    private ProjectCollaborator projectCollaborator;

    @Before
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(victim).build();
        userRui = new User("Rui", "rui@gmail.com", "02", "Simples colaborador", "638192945");
        projectId = 1;
        this.projectCollaborator = new ProjectCollaborator(userRui, 2);
        projectCollaborator.setProject(projectMock);
    }

    @Test
    public void getProjectDetailsTest() throws Exception {
        Project projectTest = new Project("Project", "description", userRui);
        //GIVEN

        //WHEN
        when(projectServiceMock.getProjectById(any(Integer.class))).thenReturn(projectTest);
        MockHttpServletResponse response = mvc.perform(get("/projects/" + projectId).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        //THEN
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(jacksonProject.write(projectTest).getJson(), response.getContentAsString());
        verify(projectServiceMock, times(1)).getProjectById(projectId);
    }


    @Test
    public void testUpdateCalculationMethod() throws Exception{
        //given the project is running
        when(projectServiceMock.getProjectById(any(Integer.class))).thenReturn(new Project("Project", "description", userRui));
        Mockito.doNothing().when(projectServiceMock).saveProject(any(Project.class));

        //when
        MockHttpServletResponse response = mvc.perform(put("/projects/" + projectId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"calculationMethod\":3}")
                .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        //then
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void testGetProjectCost() throws Exception {
        //given the project is running
        when(projectServiceMock.getProjectById(any(Integer.class))).thenReturn(projectMock);
        when(taskServiceMock.getTotalCostReportedToProjectUntilNow(projectMock)).thenReturn(7.0);

        //when we perform a get request to url /projects/<projectId>/cost
        MockHttpServletResponse response = mvc.perform(get("/projects/" + projectId + "/cost").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        //then we receive a valid message
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("{\"projectCost\":7.0}", response.getContentAsString());
        verify(projectServiceMock, times(1)).getProjectById(projectId);
    }


    /**
     * Given
     * A set of parameters to create a project
     *
     * When
     * One creates a project
     *
     * Then
     * It is expected to be successfully created
     *
     * @throws Exception
     */
    @Test
    public void createProjectTest() throws Exception {

        //GIVEN
        //A set of parameters to create a project
        String projName = "Projeto para construcao de site";
        String projDescrip = "Site para venda de casas";
        when(userServiceMock.getUserByEmail(any(String.class))).thenReturn(userRui);

        //WHEN
        //One creates a project
        Project projectDto = new Project(projName, projDescrip, userRui);
        when(projectServiceMock.createProject(any(String.class),any(String.class), any(User.class))).thenReturn(projectDto);
        projectServiceMock.addProjectToProjectContainer(projectDto);
        verify(projectServiceMock, times(1)).addProjectToProjectContainer(projectDto);
        MockHttpServletResponse response = mvc.perform(post("/projects/").contentType(MediaType.APPLICATION_JSON)
                .content(jacksonProject.write(projectDto).getJson()))
                .andReturn().getResponse();

        //THEN
        //It is expected to be successfully created
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(jacksonProject.write(projectDto).getJson(), response.getContentAsString());

    }

    /**
     * Given
     * A set of parameters to create a project
     *
     * When
     * One creates a project
     *
     * Then
     * It is expected to be successfully created
     *
     * @throws Exception
     */
    @Test
    public void createProjectTestwithEffortUnitandBudget() throws Exception {

        //GIVEN
        //A set of parameters to create a project
        String projName = "Projeto para construcao de site";
        String projDescrip = "Site para venda de casas";
        when(userServiceMock.getUserByEmail(any(String.class))).thenReturn(userRui);
        EffortUnit effortUnit = EffortUnit.PM;
        Double budget = 30.0;

        //WHEN
        //One creates a project
        Project projectDto = new Project(projName, projDescrip, userRui);
        when(projectServiceMock.createProject(any(String.class),any(String.class), any(User.class))).thenReturn(projectDto);
        projectServiceMock.addProjectToProjectContainer(projectDto);
        verify(projectServiceMock, times(1)).addProjectToProjectContainer(projectDto);
        MockHttpServletResponse response = mvc.perform(post("/projects/").contentType(MediaType.APPLICATION_JSON)
                .content(jacksonProject.write(projectDto).getJson()))
                .andReturn().getResponse();

        //THEN
        //It is expected to be successfully created
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(jacksonProject.write(projectDto).getJson(), response.getContentAsString());

    }

}
