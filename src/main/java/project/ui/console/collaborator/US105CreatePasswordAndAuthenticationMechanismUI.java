package project.ui.console.collaborator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.controllers.US105CreatePasswordAndAuthenticationMechanismController;
import project.model.User;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Scanner;

@Component
public class US105CreatePasswordAndAuthenticationMechanismUI {

    @Autowired
    US105CreatePasswordAndAuthenticationMechanismController controller;

    User user;

    public static void errorSendingEmail() {
        System.out.println("Something went wrong when trying to send you your validation code.\nPlease try again.");
    }

    /**
     * this method is so that in the first login it is mandatory to change the password.
     *
     * @param user
     */
    public void changePassword(User user) throws IOException, MessagingException {
        Scanner input = new Scanner(System.in);
        String userInput;
        boolean loopPasswordReenter = true;
        boolean loop = false;
        String newPassword = "";

        while (loopPasswordReenter) {

            System.out.println("\nYou must create a password");
            System.out.println("Please enter a password:");

            newPassword = input.nextLine();
            System.out.println("Re-enter the password:");

            String reEnterPassword = input.nextLine();

            if (!(newPassword.equals(reEnterPassword))) {
                System.out.println("The passwords do not match!");
                System.out.println("Try again? Y/N");
                String answer = input.nextLine();
                if (!("Y".equalsIgnoreCase(answer)))
                    loopPasswordReenter = false;

            } else if ("".equals(newPassword)) {
                System.out.println("The password can't be empty!");
                System.out.println("Try again? Y/N");
                String answer = input.nextLine();
                if (!("Y".equalsIgnoreCase(answer)))
                    loopPasswordReenter = false;

            } else {
                loopPasswordReenter = false;
                loop = true;
            }

            while (loop) {
                System.out.println("Choose an authentication method:\n");
                System.out.println("[1] Sms authentication");
                System.out.println("[2] Answer authentication");
                System.out.println("[3] Email authentication\n");

                String choice = input.nextLine();

                System.out.println(controller.performAuthentication(user.getPhone(), user.getEmail(), user.getQuestion(), choice));

                if (controller.getValidation() != null) {
                    userInput = input.nextLine();

                    if (controller.isCodeValid(userInput, user)) {
                        controller.setUserPassword(user, newPassword);
                        System.out.println("The password changed successfully!");
                        loop = false;

                    } else {
                        System.out.println("The password wasn't changed. Try again? Y/N");
                        String answer = input.nextLine();
                        if (!("Y".equalsIgnoreCase(answer)))
                            loop = false;

                    }
                }

            }

        }
    }


}
