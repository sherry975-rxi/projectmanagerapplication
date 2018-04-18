package project.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.model.Project;
import project.model.User;
import project.services.ProjectService;
import project.services.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("projects")
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
    @RequestMapping(value = "/{projectId}/" , method = RequestMethod.PATCH)
    public ResponseEntity<?> changeProjectManager(@RequestBody Project project, @PathVariable int projectId){

        Project projectToUpdate = projectService.getProjectById(projectId);

        User user = userService.getUserByEmail(project.getProjectManager().getEmail());


        if(user != null) {

            projectService.changeProjectManager(user, projectToUpdate);

            return new ResponseEntity<>(HttpStatus.OK);

        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
