package project.restcontroller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.model.Project;
import project.services.ProjectService;
import project.services.TaskService;


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

    /**
     * This controller's method uses PUT to set the calculation method of project's cost. The method of calculation is defined by a int (FIRST_COLLABORATOR = 1;
     * LAST_COLLABORATOR = 2; FIRST_LAST_COLLABORATOR = 3; AVERAGE_COLLABORATOR = 4).
     */
    @RequestMapping(value = "/calculationmethod", method = RequestMethod.PUT)
    public ResponseEntity<Project> setCalculationMethod(@RequestBody Project project, @PathVariable int projectId) {
        Project myProject = this.projectService.getProjectById(projectId);
        myProject.setCalculationMethod(project.getCalculationMethod());
        projectService.updateProject(myProject);
        taskService.calculateReportEffortCost(myProject);
        projectService.addProjectToProjectContainer(myProject);
        return ResponseEntity.ok().body(myProject);
    }
    /**
     * This controller's method uses GET to get the cost of the project through the calculation method defined previously.
     */
    @RequestMapping(value = "/cost", method = RequestMethod.GET)
    public ResponseEntity<String> getProjectCost(@PathVariable int projectId) {
        Project project = this.projectService.getProjectById(projectId);
        taskService.getTotalCostReportedToProjectUntilNow(project);
        return  ResponseEntity.ok().body("Project Cost: " + Double.toString(taskService.getTotalCostReportedToProjectUntilNow(project)));
    }
}