package project.restControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import project.dto.CodeCheckDTO;
import project.dto.CredentialsDTO;
import project.dto.UserDTO;
import project.model.User;
import project.model.sendcode.ValidationMethod;
import project.restcontroller.RestAccountController;
import project.services.UserService;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class RestAccountControllerTest {

    @InjectMocks
    RestAccountController restAccountController;

    @Mock
    UserService userService;

    @Mock
    ValidationMethod validate;

    User userDaniel;

    private JacksonTester<UserDTO> jacksonUserDto;
    private JacksonTester<CredentialsDTO> jacksonCredentialsDto;
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
     * then sends the link to the user verification via email or sms
     */

    @Test
    public void shouldCreateUser() throws Exception {
        //GIVEN
        //given a set of user information
        UserDTO userDTO = new UserDTO("Alberto", "test@gmail.com", "70", "dev", "91000000",
                 "1", "test");
        userDTO.setPassword("qwerty");
        userDTO.setUserAddress("Rua", "4500-000", "Porto", "Porto", "Portugal");

        //WHEN
        //when you are prompted to create a system user
        when(userService.isUserEmailInUserContainer(anyString())).thenReturn(false);
        when(userService.isEmailAddressValid(anyString())).thenReturn(true);

        restAccountController.createUser(userDTO);

        MockHttpServletResponse response = mvc.perform(post("/account/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonUserDto.write(userDTO).getJson()))
                .andReturn().getResponse();

        //THEN
        //then sends the link to the user verification via email or sms
        assertEquals(HttpStatus.OK.value(), response.getStatus());

    }

    /**
     * GIVEN
     * given a set of user information with a email in wrong format
     *
     * WHEN
     * when you are prompted to create a system user
     *
     * THEN
     * then sends an information to inform that it is impossible to create the user
     */

    @Test
    public void shouldNotCreateUserBecauseInvalidEmail() throws Exception {

        //GIVEN
        //given a set of user information with a email in wrong format
        UserDTO userDTO = new UserDTO("Pedro", "testgmail.com", "70", "dev", "91000000",
                 "1", "test");
        userDTO.setPassword("qwerty");
        userDTO.setUserAddress("Rua", "4500-000", "Porto", "Porto", "Portugal");

        //WHEN
        //when you are prompted to create a system user
        when(userService.isUserEmailInUserContainer(anyString())).thenReturn(false);
        when(userService.isEmailAddressValid(anyString())).thenReturn(false);

        restAccountController.createUser(userDTO);

        MockHttpServletResponse response = mvc.perform(post("/account/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonUserDto.write(userDTO).getJson()))
                .andReturn().getResponse();

        //THEN
        //then sends an information to inform that it is impossible to create the user
        assertEquals(HttpStatus.NOT_ACCEPTABLE.value(), response.getStatus());
    }

    /**
     * GIVEN
     * given a set of user information with email that already exists
     *
     * WHEN
     * when you are prompted to create a system user
     *
     * THEN
     * then sends an information to inform that it is impossible to create the user
     */
    @Test
    public void shouldNotCreateUserBecauseEmailExists() throws Exception {

        //GIVEN
        //given a set of user information with email that already exists
        UserDTO userDTO = new UserDTO("Daniel", "testgmail.com", "70", "dev", "91000000",
                 "1", "test");
        userDTO.setPassword("qwerty");
        userDTO.setUserAddress("Rua", "4500-000", "Porto", "Porto", "Portugal");

        //WHEN
        //when you are prompted to create a system user
        when(userService.isUserEmailInUserContainer(anyString())).thenReturn(true);
        when(userService.isEmailAddressValid(anyString())).thenReturn(true);

        restAccountController.createUser(userDTO);

        MockHttpServletResponse response = mvc.perform(post("/account/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonUserDto.write(userDTO).getJson()))
                .andReturn().getResponse();

        //THEN
        //then sends an information to inform that it is impossible to create the user
        assertEquals(HttpStatus.CONFLICT.value(), response.getStatus());
    }

    @Test
    public void shouldLogin() throws Exception {
        //GIVEN a user with a valid password
        userDaniel.setPassword("exists");
        when(userService.getUserByEmail(any(String.class))).thenReturn(userDaniel);

        //WHEN performing a logIn post request using a UserDTO
        CredentialsDTO userDTO = new CredentialsDTO();
        userDTO.setEmail("test@gmail.com");
        userDTO.setPassword("exists");

        MockHttpServletResponse response = mvc.perform(post("/account/logIn")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonCredentialsDto.write(userDTO).getJson()))
                .andReturn().getResponse();

        //THEN the response entity must contain OK
        assertEquals(HttpStatus.OK.value(), response.getStatus());

    }

    @Test
    public void shouldNotLogin() throws Exception {
        // GIVEN a single user in the database
        when(userService.getUserByEmail(any(String.class))).thenReturn(null);

        // WHEN the email in the DTO doesn't match daniel's email
        CredentialsDTO userDTO = new CredentialsDTO();
        userDTO.setEmail("wrong@mail.mail");
        userDTO.setPassword("exists");

        //THEN the response entity must contain FORBIDDEN
        MockHttpServletResponse response = mvc.perform(post("/account/logIn")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonCredentialsDto.write(userDTO).getJson()))
                .andReturn().getResponse();

        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());

        // AND WHEN the user exists, but has no password
        when(userService.getUserByEmail(any(String.class))).thenReturn(userDaniel);

        userDTO.setEmail("test@gmail.com");
        userDTO.setPassword("exists");


        // THEN the response must contain "OK", and contain three links to choose a validation method
        response = mvc.perform(post("/account/logIn")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonCredentialsDto.write(userDTO).getJson()))
                .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());


        // AND WHEN the user has a password, but the DTO's password doesn't match

        userDaniel.setPassword("exists");
        when(userService.getUserByEmail(any(String.class))).thenReturn(userDaniel);

        userDTO.setEmail("test@gmail.com");
        userDTO.setPassword("wrong password");


        //THEN the response entity must contain FORBIDDEN
        response = mvc.perform(post("/account/logIn")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonCredentialsDto.write(userDTO).getJson()))
                .andReturn().getResponse();

        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());
    }

    @Test
    public void conditionsTest() throws Exception {
        //GIVEN these conditions
        String conditions = "TERMS AND CONDITIONS:"
                + "By using this application, you agree to be bound by, and to comply with these Terms and Conditions."
                + "If you do not agree to these Terms and Conditions, please do not use this application."
                + "To proceed with registration you must accept access conditions.";
        //WHEN the conditions link is accessed
        //THEN the message is returned as is, and the status is OK
        mvc.perform(get("/account/conditions").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$.textToReturn", is("TERMS AND CONDITIONS:"
                + "By using this application, you agree to be bound by, and to comply with these Terms and Conditions."
                + "If you do not agree to these Terms and Conditions, please do not use this application."
                + "To proceed with registration you must accept access conditions.")));
    }




    @Test
    public void performUserValidationAfterRegistryTest() throws Exception{

        String userEmail;
        String validationType;
        String userPhone;

        //GIVEN
        // an userEmail, userPhone, and a validationType type 2 (ByEmail)
        userEmail = "jmscrl@gmail.com";
        userPhone = "911111";
        validationType = "2";


        Link linkToReturn = linkTo(RestAccountController.class).slash("performValidation/verificateCode/jmscrl@gmail.com").withRel("verificateHuman");

        ResponseEntity<Link> expectedResponse = new ResponseEntity<>(linkToReturn, HttpStatus.OK);

        //WHEN
        //The method performValidationMethod, It's a mocked method, so it actually doesn't send the messages
        when(validate.performValidationMethod(anyString(),anyString(),anyString(), anyString())).thenReturn("test");

        //THEN the restAccountController performs the UserValidation and returns a string , because the validationType is not valid
        ResponseEntity<Link> actualResponse = restAccountController.performUserValidation(validationType, userEmail, userPhone);


        assertEquals(actualResponse, expectedResponse);


    }

    @Test
    public void performUserValidationAfterRegistryTestUnsuccess() throws Exception{

        String userEmail;
        String validationType;
        String userPhone;

        //GIVEN
        // an userEmail, userPhone, and a validationType type 2 (ByEmail)
        userEmail = "jmscrl@gmail.com";
        userPhone = "911111";
        validationType = "4";



        Link linkToReturn = linkTo(RestAccountController.class).slash("performValidation/verificateCode/jmscrl@hotmail.com").withRel("verificateHuman");

        ResponseEntity<Link> expectedResponse = new ResponseEntity<>(HttpStatus.FORBIDDEN);

        //WHEN
        //The method performValidationMethod, It's a mocked method, so it actually doesn't send the messages
        when(validate.performValidationMethod(anyString(),anyString(),anyString(), anyString())).thenReturn("test");

        //THEN the restAccountController performs the UserValidation and returns a forbidden message, because the validationType is not valid
        ResponseEntity<Link> actualResponse = restAccountController.performUserValidation(validationType, userEmail, userPhone);


        //Verifies that the result matches
        assertEquals(actualResponse, expectedResponse);


        //WHEN
        //We change the validationType to 3, although the factory returns a questions with this input type, it is not necessary on the Validation process

        validationType = "3";

        //THEN
        //The performUserValidation method will still return a HTTPStatus.FORBIDDEN
        actualResponse = restAccountController.performUserValidation(validationType, userEmail, userPhone);
        assertEquals(actualResponse, expectedResponse);


    }

    @Test
    public void performValidationVerificationCode(){

        //GIVEN
        // An an userEmail that it's still not registered in the system

        String userEmail = "test@mymail.com";
        CodeCheckDTO code = new CodeCheckDTO();
        code.setCodeToCheck("code");


        ResponseEntity<Link> expectedResponse = new ResponseEntity<>(HttpStatus.FORBIDDEN);

        //WHEN
        // When we perform the verificateCode method in the restAccountController
        ResponseEntity<Link> actualResponse =      restAccountController.verificateCode(code,userEmail);


        //THEN
        //The method will return a HTTPStatus.FORBIDDEN, because there isn't any record in the HASHMAP with the given email
        assertEquals(actualResponse, expectedResponse);


        //WHEN
        //We create a user, there will be a value that will be stored in the hashMap codeDTO MAP with a code

        UserDTO userDTO = new UserDTO("Alberto", userEmail, "70", "dev", "91000000",
                "1", "test");

        when(userService.isEmailAddressValid(anyString())).thenReturn(true);
        restAccountController.createUser(userDTO);



        //WHEN
        //We provided a wrong code
        code.setCodeToCheck("wrong");
        actualResponse =      restAccountController.verificateCode(code,userEmail);


        //THEN
        //The method will return a HTTPStatus.FORBIDDEN, because the passwords do not match
        assertEquals(actualResponse, expectedResponse);


        //WHEN
        //We provide a right code (The string "NotVerified" is stored in the hashmap
        // before the user selects a method to receive the code

        code.setCodeToCheck("NotVerified");
        actualResponse =      restAccountController.verificateCode(code,userEmail);


        //THEN
        //it returns a link a ResponseEntity with the link to the login and HttpSatus.OK
        Link okLink = linkTo(RestAccountController.class).slash("logIn").withRel("loginUser");

        expectedResponse = new ResponseEntity<>(okLink, HttpStatus.OK);

        assertEquals(actualResponse, expectedResponse);




    }

    @Test
    public void checkValidationTest(){

        Integer userID;
        String code;

        //GIVEN
        // An userID that doesn't exist in the Service
        userID = 1;
        code = "1234";



        //WHEN
        //The userService tries to get the user by ID, it will return null
        when(userService.getUserByID(userID)).thenReturn(null);

        //THEN
        // the restAccountController performs the checkValidation
        //method and will return a HttpStatus.FORDBIDDEN, because the user doesn't exist

        ResponseEntity<Link> expectedResponse = new ResponseEntity<>(HttpStatus.FORBIDDEN);

        ResponseEntity<Link> actualResponse = restAccountController.checkValidation(code, userID);

        assertEquals(actualResponse, expectedResponse);


        //WHEN
        //The userService tries to get the user by ID, it will returnt userDaniel
        when(userService.getUserByID(userID)).thenReturn(userDaniel);

        //THEN
        // the restAccountController performs the checkValidation
        //method and will return a HttpStatus.FORDBIDDEN, because the user exists but its not in the hashmap
        expectedResponse = new ResponseEntity<>(HttpStatus.FORBIDDEN);
        actualResponse = restAccountController.checkValidation(code, userID);
        assertEquals(actualResponse, expectedResponse);


    }



}
