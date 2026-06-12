import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.ArrayList;

public class Message {

    public ArrayList<String> recipientNumber = new ArrayList<>();
    public String message;
    public ArrayList<String> sentMessages = new ArrayList<>();
    public ArrayList<String> disregardMessages = new ArrayList<>();
    public ArrayList<String> storedMessages = new ArrayList<>();
    public ArrayList<String> ID = new ArrayList<>();
    public ArrayList<String> messageHash = new ArrayList<>();
    public LinkedList<String> allMessageContent = new LinkedList<>();

    public int currentMessageCount = 0;
    public int choice;
    public int subChoice;
    public int subSubChoice;
    private int index = 0;

    public String storageFilePath = "storedMessage.json";

    public String generatedID() {
        Random random = new Random();
        String id = "";
        for (int i = 0; i < 10; i++) {
            id += random.nextInt(10);
        }
        return id;
    }

    public boolean checkRecipientCell(String recipientNumber) {
        return recipientNumber.matches("\\+27\\d{9}") || recipientNumber.matches("0\\d{9}");
    }

    public static String generateMessageHash(String ID, String message, int currentMessageCount) {
        String firstTwoID = ID.substring(0, 2);
        String[] words = message.trim().split("\\s+");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];
        String combinedWords = (firstWord + lastWord)
                .replaceAll("[^a-zA-Z]", "")
                .toUpperCase();
        return firstTwoID + ":" + currentMessageCount + ":" + combinedWords;
    }

    public String sentMessage(int subChoice) {

        if (subChoice == 0) {
            disregardMessages.add(message);
            return "Message discarded";
        }

        // Only reach here if actually sending or storing
        allMessageContent.add(message);

        String id = ID.isEmpty() ? "" : ID.get(ID.size() - 1);
        String recipient = recipientNumber.isEmpty() ? "" : recipientNumber.get(recipientNumber.size() - 1);
        String hash = messageHash.isEmpty() ? "" : messageHash.get(messageHash.size() - 1);

        if (subChoice == 1) {
            sentMessages.add(message);
            return printMessage(id, hash, recipient, currentMessageCount, message);
        }

        if (subChoice == 2) {
            storedMessages.add(message);
            if (!id.isEmpty() && !recipient.isEmpty() && !hash.isEmpty()) {
                storeMessage(id, recipient, message, hash);
            }
            return "Message successfully stored";
        }

        return "Invalid option";
    }

    public String printMessage(String ID, String messageHash,
                               String recipientNumber,
                               int currentMessageCount,
                               String message) {
        String display = "";
        display += "Message ID: " + ID + "\n";
        display += "Message Hash: " + messageHash + "\n";
        display += "Recipient: " + recipientNumber + "\n";
        display += currentMessageCount + "\n";
        display += "Message: " + message + "\n";
        return display;
    }

    public void storeMessage(String ID, String recipientNumber,
                             String message, String hash) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        File file = new File(storageFilePath);

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

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public String recipientAndSenderAllMessages() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ID.size(); i++) {
            sb.append("Recipient: ").append(recipientNumber.get(i))
                    .append(" | Message: ").append(allMessageContent.get(i))
                    .append("\n");
        }
        return sb.length() > 0 ? sb.toString() : "No messages found";
    }

    public String searchByID(String idSearch) {

        for (int i = 0; i < ID.size(); i++) {

            if (ID.get(i).equals(idSearch)) {

                return "Recipient: " + recipientNumber.get(i)
                        + "\nMessage: " + allMessageContent.get(i) + "\n";
            }
        }

        return "ID not found";
    }

    public String messageFromSpecRecipient(String recipient) {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < recipientNumber.size(); i++) {

            if (recipientNumber.get(i).equals(recipient)) {
                sb.append(allMessageContent.get(i)).append("\n");
            }
        }

        return sb.length() > 0 ? sb.toString() : "No messages found";
    }

    public String findLongestMessage() {

        String longest = "";

        for (String msg : allMessageContent) {
            if (msg.length() > longest.length()) {
                longest = msg;
            }
        }

        return longest;
    }

    public String deleteByHash(String hashSearch) {

        for (int i = 0; i < messageHash.size(); i++) {

            if (messageHash.get(i).equals(hashSearch)) {

                String msg = allMessageContent.get(i);

                ID.remove(i);
                recipientNumber.remove(i);
                messageHash.remove(i);
                allMessageContent.remove(i);

                return "Message discarded";
            }
        }

        return "Hash not found";
    }

    public JsonArray readJSON() {
        Gson gson = new Gson();
        File file = new File(storageFilePath);

        if (!file.exists() || file.length() == 0) {
            return new JsonArray();
        }

        try (FileReader reader = new FileReader(file)) {
            JsonArray array = gson.fromJson(reader, JsonArray.class);
            return array == null ? new JsonArray() : array;
        } catch (IOException e) {
            return new JsonArray();
        }
    }

    public String displayFullReport() {
        JsonArray array = readJSON();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < array.size(); i++) {
            JsonObject obj = array.get(i).getAsJsonObject();

            sb.append("ID: ").append(obj.get("ID").getAsString()).append("\n");
            sb.append("Recipient: ").append(obj.get("recipientNumber").getAsString()).append("\n");
            sb.append("Hash: ").append(obj.get("hash").getAsString()).append("\n");

            JsonArray messages = obj.getAsJsonArray("message");
            for (int j = 0; j < messages.size(); j++) {
                sb.append("Message: ")
                        .append(messages.get(j).getAsString())
                        .append("\n\n");
            }
        }
        return sb.toString();
    }
}