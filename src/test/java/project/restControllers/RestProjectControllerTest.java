package project.restControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import project.restcontroller.RestProjectController;
import project.services.ProjectService;

import java.util.ArrayList;
import java.util.List;

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
    @Mock
    private Project projectMock2;
    @Mock
    private Project projectMock3;

    @InjectMocks
    private RestProjectController victim;

    private MockMvc mvc;
    private Integer projectId;
    private JacksonTester<Project> jacksonProject;
    private JacksonTester<List<Project>> jacksonProjectList;
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
    public void getActiveProjectsRestTest() throws Exception{

        //GIVEN
        /*
        Given 3 projects that will be added to a list of Projects
         */
        List<Project> activeProjects = new ArrayList<>();

        Project projectTestActive1 = new Project("Project", "description", userRui);
        Project projectTestActive2 = new Project("Project2", "description 2", userRui);
        Project projectTestActive3 = new Project("Project3", "description 3", userRui);

        activeProjects.add(projectTestActive1);
        activeProjects.add(projectTestActive2);
        activeProjects.add(projectTestActive3);



        //WHEN
        /*
        When the method getActiveProjects from the projectService is called, the activeProjects list will be returned and it will return a MockHttpServ
         */
        Mockito.when(projectServiceMock.getActiveProjects()).thenReturn(activeProjects);
        MockHttpServletResponse response = mvc.perform(get("/projects/active").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();


        //THEN
        /*
        The response status will return ok, and verifies that the method getActiveProjects from the projectService is called once
         */
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(jacksonProjectList.write(activeProjects).getJson(), response.getContentAsString());
        verify(projectServiceMock, times(1)).getActiveProjects();
    }



}

