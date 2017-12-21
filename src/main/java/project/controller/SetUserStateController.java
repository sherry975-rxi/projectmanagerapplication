package project.controller;

import project.model.User;

public class SetUserStateController {

	User toChangeState;
	
	
	/**
	 * This constructor receives a user whose state will be changed
	 * 
	 * @param User
	 */
	public SetUserStateController(User toChangeState) {
		
		this.toChangeState=toChangeState;
	}
	
	
	/**
	 * This method sets the assigned user as Inactive
	 * 
	 */
	public void setUserAsInactive() {
		this.toChangeState.setUserState(false);
	}
	
	/**
	 * This method sets the assigned user as Active
	 * 
	 */
	public void setUserAsActive() {
		this.toChangeState.setUserState(true);
	}
}
