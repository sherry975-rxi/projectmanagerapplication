package project.restControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
import project.restcontroller.RestProjectController;
import project.services.ProjectService;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(MockitoJUnitRunner.class)
public class RestProjectControllerTest {
    @Mock
    private ProjectService projectServiceMock;
    @Mock
    private Project projectMock;

    @InjectMocks
    private RestProjectController victim;

    private MockMvc mvc;
    private Integer projectId;
    private JacksonTester<Project> jacksonProject;
    private User userRui;
    private ProjectCollaborator projectCollaborator;

    @Before
    public void setup(){
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(victim).build();
        userRui = new User("Rui", "rui@gmail.com", "02", "Simples colaborador", "638192945");
        projectId = 1;
        this.projectCollaborator = new ProjectCollaborator(userRui,  2);
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
}
