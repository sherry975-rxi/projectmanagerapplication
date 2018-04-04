package project.restControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import project.model.User;
import project.services.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users/{profileNameToSearch}")
public class US136FindUserByProfile {

    private final UserService userService;

    private List<User> userList;

    @Autowired
    US136FindUserByProfile(UserService userService){

        this.userService = userService;

    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> searchUsersByProfileController(@PathVariable String profileNameToSearch) {

        this.userList = userService.searchUsersByProfileName(profileNameToSearch);

        return new ResponseEntity<>(userList, HttpStatus.OK);
//        List<String> userListString = new ArrayList<>();
//
//        for (int i = 0; i < userList.size(); i++) {
//            Integer visibleIndex = i + 1;
//            String toShowUser = "[" + visibleIndex.toString() + "] \n" + userDataToString(userList.get(i));
//            userListString.add(toShowUser);
//        }
//
//        return userListString;

    }

    /**
     * This is a utility method that converts a User object into a String of data,
     * to be displayed in the UI
     *
     * @param toConvert
     *            to be converted
     * @return String of the user's data
     */
    private String userDataToString(User toConvert) {
        String profile;
        switch (toConvert.getUserProfile()) {
            case DIRECTOR:
                profile = "Director";
                break;
            case COLLABORATOR:
                profile = "Collaborator";
                break;
            default:
                profile = "Unassigned";
        }

        return toConvert.getIdNumber() + " - " + profile + ": " + toConvert.getName() + " (" + toConvert.getEmail()
                + "; " + toConvert.getPhone() + ") - " + toConvert.getFunction();
    }

}
