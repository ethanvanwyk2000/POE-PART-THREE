import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest {

    Message messageTest = new Message();

    @Test
    public void checkGenerateMessageHashMessage() {

        String result = Message.generateMessageHash(
                "0012345678",
                "Hi Mike, can you join us for dinner tonight?",
                1
        );

        String expected = "00:1:HITONIGHT";

        assertEquals(expected, result);
    }

    @Test
    public void checkMessageID() {

        String id = messageTest.generatedID();

        assertEquals(10, id.length());
    }

    @Test
    public void checkRecipientCellMessageSuccess() {

        assertTrue(messageTest.checkRecipientCell("+27718693002"));
    }

    @Test
    public void checkRecipientCellMessageFalse() {

        assertTrue(messageTest.checkRecipientCell("+27718693002"));
    }

    @Test
    public void checkRecipientCellMessageFail() {

        assertFalse(messageTest.checkRecipientCell("08575975889"));
    }

    @Test
    public void checkRecipientCellMessageTrue() {

        assertFalse(messageTest.checkRecipientCell("08575975889"));
    }

    @Test
    void testSendAction() {

        messageTest.recipientNumber.add("+27718693002");
        messageTest.message = "Hi Mike, can you join us for dinner tonight?";
        messageTest.currentMessageCount = 1;

        String id = "1234567890";
        String hash = Message.generateMessageHash(
                id,
                messageTest.message,
                messageTest.currentMessageCount
        );

        messageTest.ID.add(id);
        messageTest.messageHash.add(hash);

        String result = messageTest.sentMessage(1);

        assertEquals(
                """
                        Message ID: 1234567890
                        Message Hash: 12:1:HITONIGHT
                        Recipient: +27718693002
                        1
                        Message: Hi Mike, can you join us for dinner tonight?
                        """,
                result
        );
    }

    @Test
    void testDiscardAction() {

        messageTest.recipientNumber.add("08575975889");
        messageTest.message = "Hi Keegan, did you receive the payment?";

        String result = messageTest.sentMessage(0);

        assertEquals("Message discarded", result);
    }

    @BeforeEach

    void setUp() {
        messageTest = new Message();
        messageTest.storageFilePath = System.getProperty("java.io.tmpdir") + "/testMessages.json";
    }

    @AfterEach
    void tearDown() {
        new File(messageTest.storageFilePath).delete();
    }

    void seedMessages() {
        messageTest.message = "Did you get the cake?";
        messageTest.recipientNumber.add("+27834557896");
        messageTest.ID.add("1111111111");
        messageTest.messageHash.add("11:1:DIDCAKE");
        messageTest.sentMessage(2);

        messageTest.message = "Where are you? You are late! I have asked you to be on time.";
        messageTest.recipientNumber.add("+27838884567");
        messageTest.ID.add("2222222222");
        messageTest.messageHash.add("22:2:WHEREON");
        messageTest.sentMessage(2);

        messageTest.message = "It is dinner time !";
        messageTest.recipientNumber.add("+27834484567");
        messageTest.ID.add("0838884567");
        messageTest.messageHash.add("08:4:DINNERTIME");
        messageTest.sentMessage(2);

        messageTest.message = "Ok, I am leaving without you.";
        messageTest.recipientNumber.add("+27838884567");
        messageTest.ID.add("3333333333");
        messageTest.messageHash.add("33:5:OKLEAVING");
        messageTest.sentMessage(2);
    }

    @Test
    void testSentStoredAndDisregardFlow() {

        messageTest.message = "Did you get the cake?";
        messageTest.recipientNumber.add("+27834557896");
        messageTest.ID.add("1111111111");
        messageTest.messageHash.add("11:1:DIDCAKE");
        messageTest.sentMessage(1);

        messageTest.message = "Where are you? You are late! I have asked you to be on time.";
        messageTest.recipientNumber.add("+27838884567");
        messageTest.ID.add("2222222222");
        messageTest.messageHash.add("22:2:WHEREON");
        messageTest.sentMessage(2);

        messageTest.message = "Yohoooo, I am at your gate.";
        messageTest.sentMessage(0);

        messageTest.message = "It is dinner time !";
        messageTest.recipientNumber.add("+27834484567");
        messageTest.ID.add("0838884567");
        messageTest.messageHash.add("08:4:DINNERTIME");
        messageTest.sentMessage(2);

        messageTest.message = "Ok, I am leaving without you.";
        messageTest.recipientNumber.add("+27838884567");
        messageTest.ID.add("3333333333");
        messageTest.messageHash.add("33:5:OKLEAVING");
        messageTest.sentMessage(2);

        String result1 = messageTest.searchByID("0838884567");
        assertTrue(result1.contains("It is dinner time !"));

        String longest = messageTest.findLongestMessage();
        assertEquals(
                "Where are you? You are late! I have asked you to be on time.",
                longest
        );

        String deleteResult = messageTest.deleteByHash("22:2:WHEREON");
        assertEquals("Message discarded", deleteResult);
    }

    @Test
    void testRecipientSearch() {
        seedMessages();

        String result = messageTest.messageFromSpecRecipient("+27834557896");
        assertTrue(result.contains("cake") || result.contains("Cake"));
    }

    @Test
    void testFullReport() {
        seedMessages();

        String report = messageTest.displayFullReport();
        assertTrue(report.contains("ID:"));
        assertTrue(report.contains("Recipient:"));
        assertTrue(report.contains("Hash:"));
    }
}