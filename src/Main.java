import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login userAccount = new Login();
        Message message = new Message();

        // --- REGISTRATION PHASE ---
        System.out.println("--- REGISTRATION ---");
        System.out.print("Enter First Name: ");
        userAccount.setFirstName(scanner.nextLine());

        System.out.print("Enter Last Name: ");
        userAccount.setLastName(scanner.nextLine());

        // Registration loop for username validation
        do {
            System.out.print("Enter Username: ");
            userAccount.setUsername(scanner.nextLine());

            if (!userAccount.checkUserName()) {
                System.out.println("Username incorrectly formatted, make sure it contains an underscore and is no more than 5 characters long");
            } else {
                System.out.println("Username captured successfully");
            }
        } while (!userAccount.checkUserName());

        // Registration loop for password validation
        do {
            System.out.print("Enter Password: ");
            userAccount.setPassword(scanner.nextLine());

            if (!userAccount.checkPasswordComplexity(userAccount.password)) {
                System.out.println("Password is not formatted correctly, make sure contains a symbol and a capital letter");
            } else {
                System.out.println("Password captured");
            }
        } while (!userAccount.checkPasswordComplexity(userAccount.password));


        // Registration loop for phone number validation
        do {
            System.out.print("Enter Cell Phone Number (e.g., +27838968976): ");
            userAccount.setCellNumber(scanner.nextLine());

            if (userAccount.checkCellPhoneNumber()) {
                System.out.println("Number not entered correctly or does not contain international code");
            } else {
                System.out.println("Number captured successful");
            }
        } while (userAccount.checkCellPhoneNumber());


        // Process registration and display status
        String registrationResult = userAccount.registerUser();
        System.out.println("\n" + registrationResult);

        // Only proceed if registration was successful
        if (registrationResult.equals("The two above conditions have been met, and the user has been registered successfully.")) {

            //LOGIN PHASE
            System.out.println("\n--- LOGIN ---");
            System.out.print("Enter Username: ");
            String loginUser = scanner.nextLine();

            System.out.print("Enter Password: ");
            String loginPass = scanner.nextLine();

            // Check credentials and display final login status
            boolean isSuccess = userAccount.loginUser(loginUser, loginPass);
            System.out.println(userAccount.returnLoginStatus(isSuccess));

            if (isSuccess) {

                do {
                    System.out.println("\nWelcome to Quick Chat");
                    System.out.println("1 - Select QuickChat");
                    System.out.println("2 - Send QuickChat");
                    System.out.println("3 - Quit");

                    System.out.print("Enter your choice: ");
                    message.choice = scanner.nextInt();
                    scanner.nextLine();

                    switch (message.choice) {

                        case 1:
                            System.out.println("You Selected: Select QuickChat");
                            System.out.println("This feature coming soon");
                            break;

                        case 2:
                            System.out.println("You Selected: Send QuickChat");

                            System.out.print("Enter total number of messages: ");
                            message.currentMessageCount = scanner.nextInt();
                            scanner.nextLine();

                            // Recipient validation loop
                            do {
                                System.out.print("Enter recipient number: ");
                                message.recipientNumber = scanner.nextLine();

                                if (!message.checkRecipientCell(message.recipientNumber)) {
                                    System.out.println("Invalid recipient number");
                                }

                            } while (!message.checkRecipientCell(message.recipientNumber));

                            for (int i = 1; i <= message.currentMessageCount; i++) {

                                do {
                                    System.out.print("Enter QuickChat (max 250 chars): ");
                                    message.message = scanner.nextLine();

                                    if (message.message.length() > 250) {
                                        System.out.println("Message too long");
                                    }

                                } while (message.message.length() > 250);

                                message.ID = message.generatedID();
                                message.messageHash = Message.generateMessageHash(
                                        message.ID,
                                        message.message,
                                        message.currentMessageCount
                                );

                                System.out.println("\nChoose an option");
                                System.out.println("1 - Send QuickChat");
                                System.out.println("0 - Disregard QuickChat");
                                System.out.println("2 - Store QuickChat");

                                System.out.print("Enter choice: ");
                                message.subChoice = scanner.nextInt();
                                scanner.nextLine();

                                String result = message.sentMessage(message.subChoice);
                                System.out.println(result);
                            }
                            break;

                        case 3:
                            break;

                        default:
                            System.out.println("Invalid choice");
                    }

                } while (message.choice != 3);
            }
        }
    }
}