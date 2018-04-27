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
import project.dto.UserDTO;
import project.model.User;
import project.restcontroller.RestAccountController;
import project.services.UserService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(MockitoJUnitRunner.class)
public class RestAccountControllerTest {

    @InjectMocks
    RestAccountController restAccountController;

    @Mock
    UserService userService;

    User userDaniel;

    private JacksonTester<UserDTO> jacksonUserDto;
    private MockMvc mvc;
    private JacksonTester<String> jacksonString;

    @Before
    public void setup() {

        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(restAccountController).build();

        userDaniel = new User("Rui", "rui@gmail.com", "02", "Simples colaborador", "638192945");

    }

    @After
    public void tearDown() {

    }

    /**
     * GIVEN
     * given a set of user information
     *
     * WHEN
     * when you are prompted to create a system user
     *
     * THEN
     * then a user is created in the database with the information provided
     */

    /*

    @Test
    public void shouldCreateUser() throws Exception {
        //GIVEN
        //given a set of user information
        UserDTO userDTO = new UserDTO("Alberto", "test@gmail.com", "70", "dev", "91000000",
                "qwerty", "1", "test");
        userDTO.setUserAddress("Rua", "4500-000", "Porto", "Porto", "Portugal");

        //WHEN
        //when you are prompted to create a system user
        when(userService.isUserEmailInUserContainer(anyString())).thenReturn(false);
        when(userService.isEmailAddressValid(anyString())).thenReturn(true);
        Mockito.doNothing().when(userService).addUserToUserRepositoryX(any(User.class));
        when(userService.getUserByEmail(anyString())).thenReturn(userDaniel);

        restAccountController.createUser(userDTO);

        MockHttpServletResponse response = mvc.perform(post("/account/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonUserDto.write(userDTO).getJson()))
                .andReturn().getResponse();

        //THEN
        //then a user is created in the database with the information provided
        verify(userService,times(1)).createUserWithDTO(userDTO);

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

    }

    */

    /**
     * GIVEN
     * given a set of user information with a email in wrong format
     *
     * WHEN
     * when you are prompted to create a system user
     *
     * THEN
     * then a user isn't created in the database
     */


    /*




    @Test
    public void shouldNotCreateUserBecauseInvalidEmail() throws Exception {

        //GIVEN
        //given a set of user information with a email in wrong format
        UserDTO userDTO = new UserDTO("Pedro", "testgmail.com", "70", "dev", "91000000",
                "qwerty", "1", "test");
        userDTO.setUserAddress("Rua", "4500-000", "Porto", "Porto", "Portugal");

        //WHEN
        //when you are prompted to create a system user
        when(userService.isUserEmailInUserContainer(anyString())).thenReturn(false);
        when(userService.isEmailAddressValid(anyString())).thenReturn(false);

        restAccountController.createUser(userDTO);

        MockHttpServletResponse response = mvc.perform(post("/account/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonUserDto.write(userDTO).getJson()))
                .andReturn().getResponse();

        //THEN
        //then a user isn't created in the database
        verify(userService,times(0)).createUserWithDTO(userDTO);

        assertEquals(HttpStatus.NOT_ACCEPTABLE.value(), response.getStatus());
    }
     */



    /**
     * GIVEN
     * given a set of user information with email that already exists
     *
     * WHEN
     * when you are prompted to create a system user
     *
     * THEN
     * then a user isn't created in the database
     */

    /*



    @Test
    public void shouldNotCreateUserBecauseEmailExists() throws Exception {

        //GIVEN
        //given a set of user information with email that already exists
        UserDTO userDTO = new UserDTO("Daniel", "testgmail.com", "70", "dev", "91000000",
                "qwerty", "1", "test");
        userDTO.setUserAddress("Rua", "4500-000", "Porto", "Porto", "Portugal");

        //WHEN
        //when you are prompted to create a system user
        when(userService.isUserEmailInUserContainer(anyString())).thenReturn(true);
        when(userService.isEmailAddressValid(anyString())).thenReturn(true);

        restAccountController.createUser(userDTO);

        MockHttpServletResponse response = mvc.perform(post("/account/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonUserDto.write(userDTO).getJson()))
                .andReturn().getResponse();

        //THEN
        //then a user isn't created in the database
        verify(userService,times(0)).createUserWithDTO(userDTO);

        assertEquals(HttpStatus.CONFLICT.value(), response.getStatus());
    }

    */

    @Test
    public void shouldLogin() throws Exception {
        //GIVEN a user with a valid password
        userDaniel.setPassword("exists");
        when(userService.getUserByEmail(any(String.class))).thenReturn(userDaniel);

        //WHEN performing a logIn post request using a UserDTO
        UserDTO userDTO = new UserDTO("", "test@gmail.com", "", "", "",
                "exists", "", "");

        MockHttpServletResponse response = mvc.perform(post("/account/logIn")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonUserDto.write(userDTO).getJson()))
                .andReturn().getResponse();

        //THEN the response entity must contain OK
        assertEquals(HttpStatus.OK.value(), response.getStatus());

    }

    @Test
    public void shouldNotLogin() throws Exception {
        // GIVEN a single user in the database
        when(userService.getUserByEmail(any(String.class))).thenReturn(null);

        // WHEN the email in the DTO doesn't match daniel's email
        UserDTO userDTO = new UserDTO("", "wrong@mail.mail", "", "", "",
                "exists", "", "");

        //THEN the response entity must contain FORBIDDEN
        MockHttpServletResponse response = mvc.perform(post("/account/logIn")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonUserDto.write(userDTO).getJson()))
                .andReturn().getResponse();

        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());

        // AND WHEN the user exists, but has no password
        when(userService.getUserByEmail(any(String.class))).thenReturn(userDaniel);

        userDTO = new UserDTO("", "test@gmail.com", "", "", "",
                "exists", "", "");


        // THEN the response must contain "OK", and contain three links to choose a validation method
        response = mvc.perform(post("/account/logIn")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonUserDto.write(userDTO).getJson()))
                .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());


        // AND WHEN the user has a password, but the DTO's password doesn't match

        userDaniel.setPassword("exists");
        when(userService.getUserByEmail(any(String.class))).thenReturn(userDaniel);

        userDTO = new UserDTO("", "test@gmail.com", "", "", "",
                "wrong password", "", "");


        //THEN the response entity must contain FORBIDDEN
        response = mvc.perform(post("/account/logIn")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonUserDto.write(userDTO).getJson()))
                .andReturn().getResponse();

        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());
    }

    @Test
    public void conditionsTest() throws Exception {
        //GIVEN these conditions
        String conditions = "TERMS AND CONDITIONS: \r\n" + "\r\n"
                + "By using this application, you agree to be bound by, and to comply with these Terms and Conditions.\r\n"
                + "If you do not agree to these Terms and Conditions, please do not use this application.\r\n"
                + "To proceed with registration you must accept access conditions (y to confirm; n to deny).";
        //WHEN the conditions link is accessed
        MockHttpServletResponse response = mvc.perform(get("/account/conditions").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        //THEN the conditions are returned and the status is OK
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(conditions, response.getContentAsString());
    }


}
