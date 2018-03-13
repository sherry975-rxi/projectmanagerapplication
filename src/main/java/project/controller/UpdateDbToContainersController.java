package project.controller;

import project.Services.ProjectContainerService;
import project.Services.UserContainerService;

public class UpdateDbToContainersController {

    UserContainerService userContainer;
    ProjectContainerService projectContainer;

    public void updateDBtoContainer(){
        this.projectContainer = new ProjectContainerService();
        this.userContainer = new UserContainerService();
        projectContainer.updateProjectContainer();
        userContainer.updateUserContainer();

    }

}
