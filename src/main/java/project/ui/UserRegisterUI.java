package project.ui;

import java.util.Scanner;

import project.controller.RegisterUserController;

public class UserRegisterUI extends MainMenuUI {
	
	private String name;
	private String email;
	private String password;
	private String idNumber;
	private String function;
	private String phone;
	private String street;
	private String zipCode;
	private String city;
	private String district;
	private String country;

	
	public UserRegisterUI() {
		// TODO Auto-generated constructor stub
	}

 	public void userRegister() {
		Scanner scannerInput = new Scanner(System.in);
		
 		System.out.println("Enter name: ");
		name = scannerInput.next();
		System.out.println("Name accepted " + name);
		
		System.out.println("Enter email: ");
		email = scannerInput.next();
		System.out.println("Email accepted " + email);
		
		System.out.println("Enter idNumber: ");
		idNumber = scannerInput.next();
		System.out.println("IdNumber accepted " + idNumber);
		
		System.out.println("Enter function: ");
		function = scannerInput.next();
		System.out.println("Function accepted " + function);
	
		System.out.println("Enter phone: ");
		phone = scannerInput.next();
		System.out.println("Phone accepted " + phone);
		
		System.out.println("Enter password: ");
		password = scannerInput.next();
		System.out.println("Password accepted " + password);
		
		System.out.println("Enter street: ");
		street = scannerInput.next();
		System.out.println("Street accepted " + street);
		
		System.out.println("Enter zipCode: ");
		zipCode = scannerInput.next();
		System.out.println("ZipCode accepted " + zipCode);
		
		System.out.println("Enter city: ");
		city = scannerInput.next();
		System.out.println("City accepted " + city);
		
		System.out.println("Enter district: ");
		district = scannerInput.next();
		System.out.println("District accepted " + district);
		
		System.out.println("Enter country: ");
		country = scannerInput.next();
		System.out.println("Country accepted " + country);
				
		System.out.println("ACESS CONDITIONS. To proceed with registration you must accept access conditions (y to confirm; n to deny).");
		String answer = scannerInput.next();
		
		if(answer.equalsIgnoreCase("y")) {
			System.out.println("Conditions accepted. Sucessful registration!");
			RegisterUserController controller = new RegisterUserController();
			controller.addNewUser(name, email, idNumber, function, phone, password, street, zipCode, city, district, country);
		}
		else if(answer.equalsIgnoreCase("n")){
			System.out.println("Conditions not accepted");
		}	
		else {
			System.out.println("Invalid answer");
		}
	}
}
