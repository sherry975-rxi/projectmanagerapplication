package project.restControllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import project.model.User;
import project.repository.UserRepository;
import project.restcontroller.RestUserController;
import project.services.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RunWith(MockitoJUnitRunner.class)
public class RestUserControllerTest {


    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    User mike;
    User justin;

     Link mikeLink, justinLink;

    ResponseEntity<List<User>> expectedResponse;

    RestUserController restUserController;

    List<User> expected;


    @Before
    public void setUp() throws Exception {

        initMocks(this);

        mike = userService.createUser("Mike", "mike@gmail.com", "876", "function", "987", "here", "there", "porto", "porto", "portugal");

        justin = userService.createUser("Justin", "justin@gmail.com", "878", "function", "9877", "here", "there", "porto", "porto", "portugal");

        restUserController = new RestUserController(userService);

        mikeLink = linkTo(RestUserController.class).slash(mike.getUserID()).withSelfRel();
        mike.add(mikeLink);

        justinLink = linkTo(RestUserController.class).slash(justin.getUserID()).withSelfRel();
        justin.add(justinLink);

        expected = new ArrayList<>();
    }

    @After
    public void tearDown(){

        mike = null;
        mikeLink = null;
        justin = null;
        justinLink = null;
        expected = null;
    }

    @Test
    public void getAllUsers(){

        //GIVEN an empty user list

        //WHEN 1 user is added to the user list
        expected.add(mike);
        expected.add(justin);

        when(userRepository.findAll()).thenReturn(expected);

        //THEN the method should return a list with the two users and the status "200 ok"

        expectedResponse = new ResponseEntity<>(expected, HttpStatus.OK);

        assertEquals(expectedResponse, restUserController.getAllUsers());
    }
}