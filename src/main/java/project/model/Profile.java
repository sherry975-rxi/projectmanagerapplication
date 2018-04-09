package project.model;

import java.util.Arrays;

/**
 * Profile Class for setting profiles in Users
 * 
 * @author Group3
 *
 */

public enum Profile {

	UNASSIGNED, COLLABORATOR, DIRECTOR;
	
	public static boolean contains(String test){
		for (Profile other: Profile.values()) {
			if (other.name().equals(test)){
				return true;
			}
			
		}
		return false;
	}

}