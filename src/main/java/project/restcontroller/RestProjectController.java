package project.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.model.Project;
import project.model.User;
import project.services.ProjectService;
import project.services.UserService;

@RestController
@RequestMapping("projects/")
public class RestProjectController {

    private final ProjectService projectService;
    private final UserService userService;

    @Autowired
    public RestProjectController(ProjectService projectService, UserService userService){

        this.projectService = projectService;
        this.userService = userService;
    }

    /**
     * This method change the project manager of a given project
     *
     * @param project
     * @param projectId
     * @return
     */
    @RequestMapping(value = "/{projectId}" , method = RequestMethod.PUT)
    public ResponseEntity<String> changeProjectManager(@RequestBody Project project, @PathVariable int projectId){

        Project projectToUpdate = projectService.getProjectById(projectId);

        String userEmail = project.getProjectManager().getEmail();

        User projectManager = userService.getUserByEmail(userEmail);

        if(!(projectManager == null)) {

            projectToUpdate.setProjectManager(projectManager);

            return new ResponseEntity<>("Project manager successfully changed!",HttpStatus.OK);

        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
