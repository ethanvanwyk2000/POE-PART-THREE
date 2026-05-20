import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import com.google.gson.Gson;

public class Message {

    Random random = new Random();
    Scanner scanner = new Scanner(System.in);

    String recipientNumber;
    String[] message;
    String ID = "";
    String messageHash;
    int currentMessageCount;
    String hashMessage = "";

    private String checkMessageID() {
        ID = "";

        for (int i = 0; i < 10; i++) {
            ID = ID + random.nextInt(10);
        }

        return ID;
    }

    private boolean checkRecipientCell(String recipientNumber) {
        return recipientNumber.matches("\\+27\\d{9}") || recipientNumber.matches("0\\d{9}");
    }

    private static String generateMessageHash(String messageID, int count, String message) {
        String[] words = message.trim().split("\\s+");
        String first = words.length > 0 ? words[0] : "";
        String last = words.length > 1 ? words[words.length - 1] : first;

        return messageID.substring(0, 2) + ":" + count + ":" + first.toUpperCase() + last.toUpperCase();
    }

    public void sentMessage() {

        while (true) {

            System.out.println("Welcome to Quick Chat");
            System.out.println("Select transaction");

            System.out.println("Option 1 - Select QuickChat");
            System.out.println("Option 2 - Send QuickChat");
            System.out.println("Option 3 - Quit");

            System.out.println("Enter your choice(1,2, or 3): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    System.out.println("You Selected: Select QuickChat");
                    System.out.println("This feature coming soon");
                    break;

                case 2:

                    System.out.println("You Selected: Send QuickChat");

                    System.out.println("Enter total number of messages you wish to send");
                    currentMessageCount = scanner.nextInt();
                    scanner.nextLine();

                    message = new String[currentMessageCount];

                    do {
                        System.out.println("Enter recipient number (must start with +27 and be exactly 12 characters)");
                        recipientNumber = scanner.nextLine();

                    } while (!checkRecipientCell(recipientNumber));

                    for (int i = 0; i < currentMessageCount; i++) {

                        System.out.println("Enter your QuickChat (must be 250 characters or less): ");
                        message[i] = scanner.nextLine();

                        if (message[i].length() > 250) {
                            System.out.println("Please enter a message of less than 250 characters.");
                        } else {
                            System.out.println("Message sent");
                        }
                    }

                    ID = checkMessageID();

                    hashMessage = message[0];

                    messageHash = generateMessageHash(ID, currentMessageCount, hashMessage);

                    printMessage();

                    System.out.println("Choose an option");
                    System.out.println("Press 1 - Send QuickChat");
                    System.out.println("Press 0 - Disregard QuickChat");
                    System.out.println("Press 2 - Store QuickChat to send later");

                    int subChoice = scanner.nextInt();

                    scanner.nextLine();

                    switch (subChoice) {

                        case 1:
                            System.out.println("Message sent and stored");
                            break;

                        case 0:
                            System.out.println("Message discarded");
                            break;

                        case 2:
                            storeMessage(ID, recipientNumber, message, messageHash);
                            break;

                        default:
                            System.out.println("invalid option");
                    }

                    break;

                case 3:
                    return;
            }
        }
    }

    private void printMessage() {

        System.out.println("Message ID: " + ID);
        System.out.println("Message Hash: " + messageHash);
        System.out.println("Recipient: " + recipientNumber);

        for (int i = 0; i < currentMessageCount; i++) {
            System.out.println("Message " + (i + 1) + ": " + message[i]);
        }

        returnTotalMessages(currentMessageCount);
    }

    private int returnTotalMessages(int currentMessageCount) {
        return currentMessageCount;
    }

    private void storeMessage(String ID, String recipientNumber, String[] message, String hash) {

        Gson gson = new Gson();

        String json =
                "{"
                        + "\"ID\":" + gson.toJson(ID) + ","
                        + "\"recipientNumber\":" + gson.toJson(recipientNumber) + ","
                        + "\"message\":" + gson.toJson(message) + ","
                        + "\"hash\":" + gson.toJson(hash)
                        + "}";

        try (FileWriter writer = new FileWriter("messages.json", true)) {
            writer.write(json + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}