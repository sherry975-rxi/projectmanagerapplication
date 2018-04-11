package project.restcontroller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.model.Project;
import project.model.Task;
import project.services.ProjectService;
import project.services.TaskService;

import java.util.List;

@RestController
@RequestMapping("/projects/{projectId}")
public class US390CalculateReportedProjectCostRestController {

    private final ProjectService projectService;
    private final TaskService taskService;

    public US390CalculateReportedProjectCostRestController(TaskService taskService, ProjectService projectsService) {
        this.taskService = taskService;
        this.projectService = projectsService;
    }

    @RequestMapping
    public ResponseEntity<Project> getProjectbyId(@PathVariable int projectId) {
        Project project = this.projectService.getProjectById(projectId);
        return ResponseEntity.ok(project);
    }

    @RequestMapping(value = "/calculationmethod", method = RequestMethod.PUT)
    public ResponseEntity<Project> setCalculationMethod(@RequestBody Project project, @PathVariable int projectId) {
        Project myProject = this.projectService.getProjectById(projectId);
        myProject.setCalculationMethod(project.getCalculationMethod());
        projectService.addProjectToProjectContainer(myProject);
        return ResponseEntity.ok().body(myProject);
    }
}
