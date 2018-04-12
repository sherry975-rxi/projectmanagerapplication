package project.ui.console.collaborator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.controllers.US180DoLoginController;
import project.model.User;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Scanner;

@Component
public class US180LoginUI {

	@Autowired
	private US180DoLoginController login;

	@Autowired
	private US105CreatePasswordAndAuthenticationMechanismUI us105Controller;

	public User doLogin() throws IOException, MessagingException {

		Scanner input = new Scanner(System.in);
		System.out.println("Username: ");
		String user = input.nextLine();

		if(!(login.findUserByEmail(user).hasPassword())){
			us105Controller.changePassword(login.findUserByEmail(user));
			return login.findUserByEmail(user);
		}else {
			System.out.println("Password: ");
			String pass = input.nextLine();

		if (login.doLogin(user, pass)) {
			System.out.println(" Login Successful! ");
			return login.findUserByEmail(user);
		} else {
			System.out.println(" Login Failed! ");
			return null;
		}

	}

}
}
