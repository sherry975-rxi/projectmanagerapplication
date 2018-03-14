package project.controller;

import project.Services.ProjectService;
import project.Services.UserContainerService;

public class UpdateDbToContainersController {

    UserContainerService userContainer;
    ProjectService projectContainer;

    public void updateDBtoContainer(){
        this.projectContainer = new ProjectService();
        this.userContainer = new UserContainerService();
        projectContainer.updateProjectContainer();
        userContainer.updateUserContainer();

    }

}
