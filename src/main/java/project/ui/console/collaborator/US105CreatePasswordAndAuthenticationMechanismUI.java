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

    User user;

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
        boolean loopPasswordReenter = true;
        boolean loop = false;
        String newPassword="";

        while (loopPasswordReenter){

                System.out.println("\nYou must create a password");
                System.out.println("Please enter a password:");

                newPassword = input.nextLine();
                System.out.println("Re-enter the password:");

                String reEnterPassword = input.nextLine();

            if(!(newPassword.equals(reEnterPassword))) {
                System.out.println("The passwords do not match!");
                System.out.println("Try again? Y/N");
                String answer = input.nextLine();
                if(!("Y".equalsIgnoreCase(answer)))
                    loopPasswordReenter = false;

            }else if ("".equals(newPassword)) {
                System.out.println("The password can't be empty!");
                System.out.println("Try again? Y/N");
                String answer = input.nextLine();
                if(!("Y".equalsIgnoreCase(answer)))
                    loopPasswordReenter = false;
                
            }else{
                loopPasswordReenter = false;
                loop = true;
            }

            while (loop) {
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
                        loop = validatePassword(code, user, newPassword);

                        break;

                    case "2":
                        String question = controller.questionAuthentication(user);
                        if ("".equals(question)) {
                            System.out.println("You haven't defined a question. Please choose another validation method.");
                        } else {

                            System.out.println(question);

                            String answer = input.nextLine();
                            if (controller.isRightAnswer(answer, user)) {
                                controller.setUserPassword(user, newPassword);
                                System.out.println("The password changed successfully!");
                                loop = false;
                            } else {
                                System.out.println("The password wasn't changed. Please try again.");
                            }

                            break;
                        }
                    case "3":
                        System.out.println("Sending e-mail...");
                        controller.emailAuthentication(user.getEmail());

                        System.out.println("E-mail sent! Please input the code sent to you:");
                        code = input.nextLine();

                        loop = validatePassword(code, user, newPassword);

                        break;

                }
            }

        }

    }

    private boolean validatePassword(String code,User user,String newPassword){
        if (controller.isCodeValid(code)) {
            controller.setUserPassword(user, newPassword);
            System.out.println("The password changed successfully!");
             return false;
        } else {
            System.out.println("The password wasn't changed. Please try again.");
            return true;
        }
    }



}
