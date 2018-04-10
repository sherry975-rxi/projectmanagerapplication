package project.ui.console.collaborator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.controllers.US105CreatePasswordAndAuthenticationMechanism;
import project.model.User;

import java.util.Scanner;

@Component
public class US105CreatePasswordAndAuthenticationMechanismUI {

    @Autowired
    US105CreatePasswordAndAuthenticationMechanism controller;

    /**
     * this method is so that in the first login it is mandatory to change the password.
     *
     * @param user
     */
    public void changePassword(User user) {
        Scanner input = new Scanner(System.in);

        System.out.println("");
        System.out.println("\nYou must change your password");
        System.out.println("Please enter a new password:");

        String newPassword;
        newPassword = input.nextLine();

        System.out.println("Choose an authentication method:\n");
        System.out.println("[1] Sms authentication");
        System.out.println("[2] Email authentication");
        System.out.println("[3] Answer authentication\n");

        String choice = input.nextLine();

        switch (choice) {
            case "1":
                controller.smsAuthentication();

                System.out.println("Please enter the code sent to the associated phone number:");
                String code = input.nextLine();
               // controller.smsCodeValid(code);

                break;

            case "2":
                String question = controller.questionAuthentication(user);
                System.out.println(question);

                String answer = input.nextLine();
                if(controller.isRightAnswer(answer, user))
                    controller.setUserPassword(user, newPassword);
                System.out.println("Password changed successfully!");

                break;

            case"3":
                controller.emailAuthentication();

                System.out.println("");

                break;

        }

    }
}
