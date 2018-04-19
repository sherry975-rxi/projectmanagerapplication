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
     * @param projectInfoToUpdate
     * @param projectId
     * @return
     */
    @RequestMapping(value = "/{projectId}/" , method = RequestMethod.PATCH)
    public ResponseEntity<?> updateProject(@RequestBody Project projectInfoToUpdate, @PathVariable int projectId){

        if(projectService.isProjectInProjectContainer(projectId)) {

            projectService.updateProject(projectInfoToUpdate, projectId);

            return new ResponseEntity<>(HttpStatus.OK);

        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
