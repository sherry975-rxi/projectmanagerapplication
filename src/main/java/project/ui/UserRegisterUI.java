package project.ui;

import java.util.Scanner;

import project.controller.RegisterUserController;

public class UserRegisterUI extends MainMenuUI {

 	public void userRegister() {

 		String name;
 		String email;
 		String password;
 		String idNumber;
 		String function;
 		String phone;
 		String street;
 		String zipCode;
 		String city;
 		String district;
 		String country;
		
 		Scanner scannerInput = new Scanner(System.in);
		
 		System.out.println("Enter name: ");
		name = scannerInput.nextLine();
		System.out.println("Name accepted " + name);
		
		System.out.println("Enter email: ");
		email = scannerInput.nextLine();
		System.out.println("Email accepted " + email);
		
		System.out.println("Enter idNumber: ");
		idNumber = scannerInput.nextLine();
		System.out.println("IdNumber accepted " + idNumber);
		
		System.out.println("Enter function: ");
		function = scannerInput.nextLine();
		System.out.println("Function accepted " + function);
	
		System.out.println("Enter phone: ");
		phone = scannerInput.nextLine();
		System.out.println("Phone accepted " + phone);
		
		System.out.println("Enter password: ");
		password = scannerInput.nextLine();
		System.out.println("Password accepted " + password);
		
		System.out.println("Enter street: ");
		street = scannerInput.nextLine();
		System.out.println("Street accepted " + street);
		
		System.out.println("Enter zipCode: ");
		zipCode = scannerInput.nextLine();
		System.out.println("ZipCode accepted " + zipCode);
		
		System.out.println("Enter city: ");
		city = scannerInput.nextLine();
		System.out.println("City accepted " + city);
		
		System.out.println("Enter district: ");
		district = scannerInput.nextLine();
		System.out.println("District accepted " + district);
		
		System.out.println("Enter country: ");
		country = scannerInput.nextLine();
		System.out.println("Country accepted " + country);
				
		System.out.println("ACCESS CONDITIONS.\nTo proceed with registration you must accept access conditions (y to confirm; n to deny).");
		String answer = scannerInput.nextLine();
		
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
