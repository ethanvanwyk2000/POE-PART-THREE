import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Message {

    public String recipientNumber;
    public String message;
    public String ID = "";
    public String messageHash;
    public int currentMessageCount = 0;
    public int choice;
    public int subChoice;

    // Generates Random 10 Digit ID number
    public String generatedID() {
        Random random = new Random();
        String ID = "";

        for (int i = 0; i < 10; i++) {
            ID += random.nextInt(10);
        }
        return ID;
    }

    // Ensures recipient number meets format
    public boolean checkRecipientCell(String recipientNumber) {
        return recipientNumber.matches("\\+27\\d{9}") || recipientNumber.matches("0\\d{9}");
    }

    public int returnTotalMessages(int currentMessageCount) {
        return currentMessageCount;
    }

    // Generates message hash
    public static String generateMessageHash(String ID, String message, int currentMessageCount) {
        String firstTwoID = ID.substring(0, 2);

        String[] words = message.trim().split("\\s+");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];

        String combinedWords =
                (firstWord + lastWord).replaceAll("[^a-zA-Z]", "").toUpperCase();

        return firstTwoID + ":" + currentMessageCount + ":" + combinedWords;
    }


    public String sentMessage(int subChoice) {

        if (subChoice == 1) {
            return printMessage(ID, messageHash, recipientNumber, currentMessageCount, message);

        } else if (subChoice == 0) {
            return "Message discarded";

        } else if (subChoice == 2) {
            storeMessage(ID, recipientNumber, message, messageHash);
            return "Message successfully stored";

        } else {
            return "Invalid option";
        }
    }


    public String printMessage(String ID, String messageHash,
                               String recipientNumber,
                               int currentMessageCount,
                               String message) {

        String display = "";

        display += "Message ID: " + ID + "\n";
        display += "Message Hash: " + messageHash + "\n";
        display += "Recipient: " + recipientNumber + "\n";
        display += returnTotalMessages(currentMessageCount) + "\n";

        for (int i = 0; i < currentMessageCount; i++) {
            display += "Message: " + message + "\n";
        }

        return display;
    }

    // FIXED storeMessage (inside class)
    public void storeMessage(String ID, String recipientNumber,
                             String message, String hash) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        File file = new File("storedMessage.json");

        try {
            JsonArray array;

            if (file.exists() && file.length() != 0) {
                try (FileReader reader = new FileReader(file)) {
                    array = gson.fromJson(reader, JsonArray.class);
                    if (array == null) array = new JsonArray();
                }
            } else {
                array = new JsonArray();
            }

            JsonObject obj = new JsonObject();
            obj.addProperty("ID", ID);
            obj.addProperty("recipientNumber", recipientNumber);

            JsonArray msgArray = new JsonArray();
            msgArray.add(message);

            obj.add("message", msgArray);
            obj.addProperty("hash", hash);

            array.add(obj);

            try (FileWriter writer = new FileWriter(file)) {
                gson.toJson(array, writer);
            }

            System.out.println("Message stored successfully in JSON file.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}