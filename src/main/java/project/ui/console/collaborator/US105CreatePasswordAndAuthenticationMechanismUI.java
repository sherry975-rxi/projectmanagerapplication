package project.ui.console.collaborator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.controllers.US105CreatePasswordAndAuthenticationMechanismController;
import project.model.User;

import java.util.Scanner;

@Component
public class US105CreatePasswordAndAuthenticationMechanismUI {

    @Autowired
    US105CreatePasswordAndAuthenticationMechanismController controller;

    public static void errorSendingEmail() {
        System.out.println("Something went wrong when trying to send you your validation code.\nPlease try again.");
    }

    /**
     * this method is so that in the first login it is mandatory to change the password.
     *
     * @param user
     */
    public void changePassword(User user) {
        Scanner input = new Scanner(System.in);
        String code;

        System.out.println();
        System.out.println("\nYou must change your password");
        System.out.println("Please enter a new password:");

        String newPassword;
        newPassword = input.nextLine();

        System.out.println("Choose an authentication method:\n");
        System.out.println("[1] Sms authentication");
        System.out.println("[2] Answer authentication");
        System.out.println("[3] Email authentication\n");

        String choice = input.nextLine();

        switch (choice) {
            case "1":
                System.out.println("Sending SMS...");
                controller.smsAuthentication(user.getPhone());
                System.out.println("SMS sent! Please input the code sent to you:");
                code = input.nextLine();
                if (controller.isCodeValid(code)) {
                    controller.setUserPassword(user, newPassword);
                    System.out.println("The password changed successfully!");
                } else {
                    System.out.println("The password wasn't changed. Please try again.");
                }
                break;

            case "2":
                String question = controller.questionAuthentication(user);
                System.out.println(question);

                String answer = input.nextLine();
                if (controller.isRightAnswer(answer, user)) {
                    controller.setUserPassword(user, newPassword);
                    System.out.println("The password changed successfully!");
                } else {
                    System.out.println("The password wasn't changed. Please try again.");
                }

                break;

            case"3":
                System.out.println("Sending e-mail...");
                controller.emailAuthentication(user.getEmail());

                System.out.println("E-mail sent! Please input the code sent to you:");
                code = input.nextLine();
                if (controller.isCodeValid(code)) {
                    controller.setUserPassword(user, newPassword);
                    System.out.println("The password changed successfully!");
                } else {
                    System.out.println("The password wasn't changed. Please try again.");
                }
                break;

        }

    }
}
