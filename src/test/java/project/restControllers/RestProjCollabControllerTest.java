package project.restControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
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
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.User;
import project.restcontroller.RestProjCollabController;
import project.services.ProjectService;
import project.services.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(MockitoJUnitRunner.class)
public class RestProjCollabControllerTest {

    @Mock
    private ProjectService projectServiceMock;

    @Mock
    private UserService userServiceMock;

    @Mock
    private Project projectMock;

    @InjectMocks
    private RestProjCollabController victim;

    private MockMvc mvc;
    private JacksonTester<ProjectCollaborator> jacksonProjectCollaborator;
    private JacksonTester<List<ProjectCollaborator>> jacksonProjectCollaboratorList;
    private User uDaniel;
    private User uInes;
    private ProjectCollaborator pcDaniel;
    private ProjectCollaborator pcInes;
    List<ProjectCollaborator> projectTeam;



    @Before
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(victim).build();
        uDaniel = new User("Daniel", "daniel@gmail.com", "01", "Arquitecto", "967387654");
        uInes = new User("Ines", "ines@gmail.com", "02", "Veterinaria", "917897458");
        projectMock = new Project("Project teste", "Teste ao controller", uDaniel);
        projectMock.setProjectId(1);
        pcDaniel = new ProjectCollaborator(uDaniel, 10);
        pcInes = new ProjectCollaborator(uInes, 20);
        pcDaniel.setProject(projectMock);
        pcInes.setProject(projectMock);
        projectTeam = new ArrayList<>();
        projectTeam.add(pcDaniel);
        projectTeam.add(pcInes);

    }

    @After
    public void tearDown() {
        projectServiceMock = null;
        userServiceMock = null;
        projectMock = null;
        victim = null;
        mvc = null;
        pcInes = null;
        jacksonProjectCollaborator = null;
        jacksonProjectCollaboratorList = null;
    }


    /**
     * GIVEN a project ID
     * WHEN we perform a get request to url /projects/<projectId>/team
     * THEN we receive a valid message and a list of project collaborators
     * @throws Exception
     */
    @Test
    public void shouldReturnTheProjectTeam() throws Exception {

        //GIVEN a project id
        int projectId = 1;

        //WHEN we performs a get request to url /projects/<projectId>/team
        when(projectServiceMock.getProjectById(projectId)).thenReturn(projectMock);
        when(projectServiceMock.getActiveProjectTeam(projectMock)).thenReturn(projectTeam);
        MockHttpServletResponse response = mvc.perform(get("/projects/" + projectId + "/team").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        //THEN we receive a valid message and a list of project collaborators
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(jacksonProjectCollaboratorList.write(projectTeam).getJson(), response.getContentAsString());
        verify(projectServiceMock, times(1)).getActiveProjectTeam(projectMock);

    }

    /**
     * GIVEN a project collaborator id
     * WHEN we perform a get request to url /projects/<projectId>/team/<projectCollaboratorId>
     * THEN we receive a valid message and the details of the projectCollaborator
     * @throws Exception
     */
    @Test
    public void shouldReturnTheProjectCollaboratorDetails() throws Exception {


        //GIVEN a project collaborator id
        int projectCollaboratorId = 1;

        //When we we perform a get request to url /projects/<projectId>/team/1
        when(projectServiceMock.getProjectCollaboratorById(projectCollaboratorId)).thenReturn(pcDaniel);
        MockHttpServletResponse response = mvc.perform(get("/projects/1/team/1").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        //THEN we receive a valid message and a list of project collaborators
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(jacksonProjectCollaborator.write(pcDaniel).getJson(), response.getContentAsString());
        verify(projectServiceMock, times(1)).getProjectCollaboratorById(projectCollaboratorId);

    }

    /**
     * GIVEN a project ID
     * WHEN we perform a post request to url /projects/<projectId>/team
     * THEN we we receive status OK and the new Project Collaborator created
     * @throws Exception
     */
    @Test
    public void shouldCreateProjectCollaborator() throws Exception{

        //GIVEN a project ID
        int projectId = 1;

        //WHEN we perform a post request to url /projects/<projectId>/team
        when(projectServiceMock.getProjectById(projectId)).thenReturn(projectMock);
        when(userServiceMock.getUserByEmail(uDaniel.getEmail())).thenReturn(uDaniel);
        when(projectServiceMock.createProjectCollaboratorWithEmail(any(String.class),any(Integer.class), any(Double.class))).thenReturn(pcDaniel);

        MockHttpServletResponse response = mvc.perform(post("/projects/" + projectId + "/team")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonProjectCollaborator.write(pcDaniel).getJson())).andReturn().getResponse();


        //THEN we receive status OK and the project info updated
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }
}
