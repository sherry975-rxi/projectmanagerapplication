package project.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import project.model.Profile;
import project.model.User;
import project.model.UserContainer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class US110andUS112SetUserProfileControllerTests {

    @InjectMocks
    private UserContainer userContainer;

    @Mock
    private US110andUS112SetUserProfileController us110andUS112SetUserProfileController;

    private User newUser2, newUser3;

    @Before
    public void setUp() {

        initMocks(this);

        when(us110andUS112SetUserProfileController.getUserContainer()).thenReturn(userContainer);

        newUser2 = us110andUS112SetUserProfileController.getUserContainer().createUser("Manel", "user2@gmail.com",
                "001", "Empregado", "930000000",
                "Testy Street", "2401-343", "Testburg", "Testo", "Testistan");

        newUser3 = us110andUS112SetUserProfileController.getUserContainer().createUser("Manelinho", "user3@gmail.com",
                "002", "Telefonista",
                "940000000", "Testy Street", "2401-343", "Testburg", "Testo", "Testistan");

        us110andUS112SetUserProfileController.getUserContainer().addUserToUserRepository(newUser2);
        us110andUS112SetUserProfileController.getUserContainer().addUserToUserRepository(newUser3);

    }

    @After
    public void breakDown() {
        newUser2 = null;
        newUser3 = null;
        us110andUS112SetUserProfileController = null;

    }

    @Test
    public void testSetUserAsDirectorController() {
        // given a User - newUser2 - asserts they start as Visitor when created
        // and then creates the controller
        assertEquals(newUser2.getUserProfile(), Profile.UNASSIGNED);

        // sets newUser2 as Director and asserts its profile
        us110andUS112SetUserProfileController.setUserAsDirector(newUser2);
        assertEquals(newUser2.getUserProfile(), Profile.DIRECTOR);

        // finally, asserts that no other Users were changed
        // and that user repository contains the new director
        assertTrue(us110andUS112SetUserProfileController.getUserContainer().searchUsersByProfile(Profile.DIRECTOR).contains(newUser2));
        assertEquals(newUser3.getUserProfile(), Profile.UNASSIGNED);
    }

    @Test
    public void testSetUserAsCollaboratorController() {
        // given a User - newUser2 - asserts they start as Visitor when created
        // and then creates the controller
        assertEquals(newUser2.getUserProfile(), Profile.UNASSIGNED);

        // sets newUser2 as Collaborator and asserts its profile
        us110andUS112SetUserProfileController.setUserAsCollaborator(newUser2);
        assertEquals(newUser2.getUserProfile(), Profile.COLLABORATOR);

        // finally, asserts that no other Users were changed
        // and that user repository contains the new director
        assertTrue(us110andUS112SetUserProfileController.getUserContainer().searchUsersByProfile(Profile.COLLABORATOR).contains(newUser2));
        assertEquals(newUser3.getUserProfile(), Profile.UNASSIGNED);
    }

    @Test
    public void testUserProfileAsString() {
        assertTrue("Unassigned".equals(us110andUS112SetUserProfileController.userProfileAsString(newUser2)));
        us110andUS112SetUserProfileController.setUserAsCollaborator(newUser2);

        assertTrue("Collaborator".equals(us110andUS112SetUserProfileController.userProfileAsString(newUser2)));
        us110andUS112SetUserProfileController.setUserAsDirector(newUser2);

        assertTrue("Director".equals(us110andUS112SetUserProfileController.userProfileAsString(newUser2)));
    }


}
