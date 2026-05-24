import org.junit.jupiter.api.Test;


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

        String id = (messageTest.generatedID());

        assertEquals(10, id.length());
    }

    @Test
    public void checkRecipientCellMessageSuccess() {
        String expected = "Recipient number captured successful";
        assertEquals(expected, "Recipient number captured successful");
    }

    @Test
    public void checkRecipientCellMessageFalse() {
        assertTrue(messageTest.checkRecipientCell("+27718693002"));

    }

    @Test
    public void checkRecipientCellMessageFail() {
        String expected = "Recipient number not entered correctly or does not contain international code";
        assertEquals(expected, "Recipient number not entered correctly or does not contain international code");
    }

    @Test
    public void checkRecipientCellMessageTrue() {
        assertFalse(messageTest.checkRecipientCell("08575975889"));
    }
    @Test
    void testSendAction() {

        messageTest.recipientNumber = "+27718693002";
        messageTest.message = "Hi Mike, can you join us for dinner tonight?";
        messageTest.currentMessageCount = 1;
        messageTest.ID = "1234567890";
        messageTest.messageHash = Message.generateMessageHash(
                messageTest.ID,
                messageTest.message,
                messageTest.currentMessageCount
        );

        String result = messageTest.sentMessage(1);

        assertEquals("Message ID: 1234567890\nMessage Hash: 12:1:HITONIGHT\nRecipient: +27718693002\n1\nMessage: Hi Mike, can you join us for dinner tonight?\n", result);
    }
    @Test
    void testDiscardAction() {

        messageTest.recipientNumber = "08575975889";
        messageTest.message = "Hi Keegan, did you receive the payment?";

        String result = messageTest.sentMessage(0);

        assertEquals("Message discarded", result);
    }
}
