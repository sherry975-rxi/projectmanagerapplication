package project.ui;

import java.util.List;
import java.util.Scanner;
import project.model.Address;
import project.model.Profile;

public class UserRegisterUI {
	private static String name;
	private String email;
	private String idNumber;
	private String function;
	private String phone;
	private String street;
	private String zipCode;
	private String city;
	private String district;
	private String country;
	private Profile userProfile;
	private boolean systemUserStateActive;
	
	public UserRegisterUI() {
		// TODO Auto-generated constructor stub
	}

	public static void scannerName() {
		
	}
	
	public static void main(String[] args) {
		Scanner scannerName = new Scanner(System.in);
		System.out.println("Enter name: ");
		name = scannerName.next();
		System.out.println("Name accepted " + name);
	}
	
	
}
