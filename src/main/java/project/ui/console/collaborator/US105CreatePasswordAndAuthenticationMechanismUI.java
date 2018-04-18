package project.ui.console.collaborator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.controllers.US105CreatePasswordAndAuthenticationMechanismController;
import project.model.User;

import java.util.Scanner;
import java.util.logging.Logger;

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
     */
    public void changePassword() {
        Scanner input = new Scanner(System.in);
        boolean loopPasswordReenter = true;
        String newPass = "";

        while (loopPasswordReenter) {

            System.out.println("\nYou must create a password");
            System.out.println("Please enter a password:");

            newPass = input.nextLine();

            System.out.println("Re-enter the password:");

            String reEnterPassword = input.nextLine();

            if (!(newPass.equals(reEnterPassword))) {
                System.out.println("The passwords do not match!");
                System.out.println("Try again? Y/N");
                String answer = input.nextLine();
                if (!("Y".equalsIgnoreCase(answer)))
                    loopPasswordReenter = false;

            } else if ("".equals(newPass)) {
                System.out.println("The password can't be empty!");
                System.out.println("Try again? Y/N");
                String answer = input.nextLine();
                if (!("Y".equalsIgnoreCase(answer)))
                    loopPasswordReenter = false;

            } else {
                loopPasswordReenter = false;
            }


            displayAndChooseAuthenticationMethod(newPass);

        }
    }

    /**
     * This method displays the authentication method options available
     *
      * @param newPass New password of the user
     */
    private void displayAndChooseAuthenticationMethod(String newPass) {

        Scanner input = new Scanner(System.in);
        String userInput;
        boolean loop = true;
        while (loop) {
            System.out.println("Choose an authentication method:\n");
            System.out.println("[1] Sms authentication");
            System.out.println("[2] Email authentication");
            System.out.println("[3] Question authentication\n");

            String choice = input.nextLine();

            try {

                System.out.println(controller.performAuthentication(user.getPhone(), user.getEmail(), user.getQuestion(), choice));

                if (controller.getValidation() != null) {
                    userInput = input.nextLine();

                    if (controller.isCodeValid(userInput, user)) {
                        controller.setUserPassword(user, newPass);
                        System.out.println("The password changed successfully!");
                        loop = false;

                    } else {
                        System.out.println("The password wasn't changed. Try again? Y/N");
                        String answer = input.nextLine();
                        if (!("Y".equalsIgnoreCase(answer)))
                            loop = false;

                    }
                }
            } catch (Exception e) {
                Logger log = Logger.getAnonymousLogger();
                log.info(e.getMessage() + "\nSomething went wrong. Please try again.\n");
            }
       }
    }
}
