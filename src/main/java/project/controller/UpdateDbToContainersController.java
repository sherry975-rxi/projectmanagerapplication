package project.controller;

import project.Services.ProjectService;
import project.Services.UserService;

public class UpdateDbToContainersController {

    UserService userContainer;
    ProjectService projectContainer;

    public void updateDBtoContainer(){
//        this.projectContainer = new ProjectService();
//        this.userContainer = new UserService();
        //projectContainer.updateProjectContainer();
        userContainer.updateUserContainer();

    }

    public void setUserContainer(UserService userContainer) {
        this.userContainer = userContainer;
    }

    public void setProjectContainer(ProjectService projectContainer) {
        this.projectContainer = projectContainer;
    }

}
