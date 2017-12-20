package project.controller;

import project.model.Company;
import project.model.Project;
import project.model.User;

public class addCollaboratorToProjectTeamController {

	private Company c1;
	private User u1;
	private Project p1;
	
	public addCollaboratorToProjectTeamController(Company c1, User u1, Project p1) {
		c1 = Company.getTheInstance();
		this.u1 = u1;
		this.p1 = p1;
		
		
	}

}
