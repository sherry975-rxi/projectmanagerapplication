package project.ui;

import java.util.List;
import java.util.Scanner;
import project.model.Address;
import project.model.Profile;

public class UserRegisterUI {
	private static String name;
	private static String email;
	private String idNumber;
	private String function;
	private static String phone;
	private static String street;
	private static String zipCode;
	private static String city;
	private static String district;
	private static String country;
	private Profile userProfile;
	private boolean systemUserStateActive;
	
	public UserRegisterUI() {
		// TODO Auto-generated constructor stub
	}

	public static void scannerName() {
		
	}
	
	public static void main(String[] args) {
		Scanner scannerInPut = new Scanner(System.in);
		
		System.out.println("Enter name: ");
		name = scannerInPut.next();
		System.out.println("Name accepted " + name);
		
		System.out.println("Enter email: ");
		email = scannerInPut.next();
		System.out.println("Email accepted " + email);
		
		System.out.println("Enter phone: ");
		phone = scannerInPut.next();
		System.out.println("Phone accepted " + phone);
		
		System.out.println("Enter street: ");
		street = scannerInPut.next();
		System.out.println("Street accepted " + street);
		
		System.out.println("Enter zipCode: ");
		zipCode = scannerInPut.next();
		System.out.println("ZipCode accepted " + zipCode);
		
		System.out.println("Enter city: ");
		city = scannerInPut.next();
		System.out.println("City accepted " + city);
		
		System.out.println("Enter district: ");
		district = scannerInPut.next();
		System.out.println("District accepted " + district);
		
		System.out.println("Enter country: ");
		country = scannerInPut.next();
		System.out.println("Country accepted " + country);
		
		
		
	}
	
	
}
