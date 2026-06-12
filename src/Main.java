import java.util.Scanner;

public class Main {
     static void main() {
        Scanner scanner = new Scanner(System.in);
        Login userAccount = new Login();
        Message message = new Message();

        // --- REGISTRATION PHASE ---
        System.out.println("--- REGISTRATION ---");
        System.out.print("Enter First Name: ");
        userAccount.setFirstName(scanner.nextLine());

        System.out.print("Enter Last Name: ");
        userAccount.setLastName(scanner.nextLine());

        do {
            System.out.print("Enter Username: ");
            userAccount.setUsername(scanner.nextLine());

            if (!userAccount.checkUserName()) {
                System.out.println("Username incorrectly formatted, make sure it contains an underscore and is no more than 5 characters long");
            } else {
                System.out.println("Username captured successfully");
            }
        } while (!userAccount.checkUserName());

        do {
            System.out.print("Enter Password: ");
            userAccount.setPassword(scanner.nextLine());

            if (!userAccount.checkPasswordComplexity(userAccount.password)) {
                System.out.println("Password is not formatted correctly, make sure contains a symbol and a capital letter");
            } else {
                System.out.println("Password captured");
            }
        } while (!userAccount.checkPasswordComplexity(userAccount.password));

        do {
            System.out.print("Enter Cell Phone Number (e.g., +27838968976): ");
            userAccount.setCellNumber(scanner.nextLine());

            if (userAccount.checkCellPhoneNumber()) {
                System.out.println("Number not entered correctly or does not contain international code");
            } else {
                System.out.println("Number captured successful");
            }
        } while (userAccount.checkCellPhoneNumber());

        String registrationResult = userAccount.registerUser();
        System.out.println("\n" + registrationResult);

        if (registrationResult.equals("The two above conditions have been met, and the user has been registered successfully.")) {

            System.out.println("\n--- LOGIN ---");
            System.out.print("Enter Username: ");
            String loginUser = scanner.nextLine();

            System.out.print("Enter Password: ");
            String loginPass = scanner.nextLine();

            boolean isSuccess = userAccount.loginUser(loginUser, loginPass);
            System.out.println(userAccount.returnLoginStatus(isSuccess));

            if (isSuccess) {

                do {
                    System.out.println("\nWelcome to Quick Chat");
                    System.out.println("1 - Select QuickChat");
                    System.out.println("2 - Send QuickChat");
                    System.out.println("3 - Quit");
                    System.out.println("4 - Stored Messages");

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
                            scanner.nextLine(); // ← flush newline after nextInt

                            String intendedRecipient = "";
                            do {
                                System.out.print("Enter recipient number: ");
                                String num = scanner.nextLine();
                                if (num.trim().isEmpty()) continue; // ← skip empty lines from buffer
                                if (!message.checkRecipientCell(num)) {
                                    System.out.println("Invalid recipient number");
                                } else {
                                    intendedRecipient = num;
                                    message.recipientNumber.add(num);
                                }
                            } while (intendedRecipient.isEmpty());

                            for (int i = 1; i <= message.currentMessageCount; i++) {

                                if (i > 1) {
                                    message.recipientNumber.add(intendedRecipient);
                                }

                                do {
                                    System.out.print("Enter QuickChat (max 250 chars): ");
                                    message.message = scanner.nextLine();
                                    if (message.message.trim().isEmpty()) continue;
                                    if (message.message.length() > 250) {
                                        System.out.println("Message too long");
                                    }
                                } while (message.message.trim().isEmpty() || message.message.length() > 250);

                                System.out.println("\nChoose an option");
                                System.out.println("1 - Send QuickChat");
                                System.out.println("0 - Disregard QuickChat");
                                System.out.println("2 - Store QuickChat");

                                System.out.print("Enter choice: ");
                                message.subChoice = scanner.nextInt();
                                scanner.nextLine();

                                if (message.subChoice != 0) {
                                    String id = message.generatedID();
                                    String hash = Message.generateMessageHash(id, message.message, message.currentMessageCount);
                                    message.ID.add(id);
                                    message.messageHash.add(hash);
                                }

                                String result = message.sentMessage(message.subChoice);
                                System.out.println(result);
                            }
                            break;

                        case 3:
                            break;

                        case 4:
                            System.out.println("1 - Display sender and recipient of all stored messages");
                            System.out.println("2 - Display longest message");
                            System.out.println("3 - Search message ID and display corresponding recipient and message");
                            System.out.println("4 - Search for message from particular recipient");
                            System.out.println("5 - Delete message using message Hash");
                            System.out.println("6 - Display full report");

                            message.subSubChoice = scanner.nextInt();
                            scanner.nextLine();

                            if (message.subSubChoice == 1) {
                                System.out.println(message.recipientAndSenderAllMessages(
                                ));

                            } else if (message.subSubChoice == 2) {
                                System.out.println(message.findLongestMessage());

                            } else if (message.subSubChoice == 3) {
                                System.out.print("Please enter the Message ID to search: ");
                                String searchID;
                                searchID = scanner.nextLine();
                                System.out.println(message.searchByID(searchID));

                            } else if (message.subSubChoice == 4) {
                                System.out.print("Enter Recipient: ");
                                System.out.println(message.messageFromSpecRecipient(scanner.nextLine()));

                            } else if (message.subSubChoice == 5) {
                                System.out.print("Please enter the Message Hash to delete: ");
                                String searchHash = scanner.nextLine();
                                System.out.println(message.deleteByHash(searchHash));

                            } else if (message.subSubChoice == 6) {
                                System.out.println(message.displayFullReport());
                            }
                            break;

                        default:
                            System.out.println("Invalid choice");
                    }

                } while (message.choice != 3);
            }
        }
    }
}