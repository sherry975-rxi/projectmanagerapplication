package project.model;

/**
 * Profile Class for setting profiles in Users
 * 
 * @author Group3
 *
 */

public enum Profile {

	VISITANT, COLLABORATOR, DIRECTOR;
	
	public static boolean contains(String test){
		for (Profile other: Profile.values()) {
			if (other.name().equals(test)){
				return true;
			}
			
		}
		return false;
	}

}