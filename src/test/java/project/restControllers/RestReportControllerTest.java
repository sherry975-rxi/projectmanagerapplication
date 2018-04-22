package project.restControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import project.model.Report;
import project.restcontroller.RestReportController;
import project.services.ProjectService;
import project.services.TaskService;
import project.services.UserService;


@RunWith(MockitoJUnitRunner.class)
public class RestReportControllerTest {

    @Mock
    private TaskService taskServiceMock;
    @Mock
    private ProjectService projectServiceMock;
    @Mock
    private UserService userServiceMock;

    @InjectMocks
    private RestReportController victim;

    private MockMvc mvc;
    private JacksonTester<Report> jacksonReport;


    @Before
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(victim).build();

        /**
         * Given
         * A set of parameters to create a report and a taskID and a ProjectID and a UserID
         *
         * When
         * One creates a Report associated with the Task that matches a taskID and only if the taskCollaborator that creates the report exists in TaskTeam
         *
         * Then
         * It is expected to be successfully created
         *
         * @throws Exception
         */
        @Test
        public void createReportTest () throws Exception {

            //Given
            //A set of parameters to create a report and a taskID and a ProjectID and a UserID


        }
    }
}
