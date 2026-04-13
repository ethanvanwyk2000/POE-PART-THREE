import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class LoginTest {

    Login login = new Login();

    @Test
    public void testCheckUsernameValid() {

        login.setUsername("kyl_1");
        String expected = "Welcome <user first name> ,<user second name> it is great to see you.";
        assertEquals(expected, "Welcome <user first name> ,<user second name> it is great to see you.");
    }

    @Test
    public void testCheckUsernameInvalid() {

        login.username = "kyle!!!!!!!";
        String expected = "Username incorrectly formatted, make sure it contains an underscore and is no more than 5 characters long";
        assertEquals(expected, "Username incorrectly formatted, make sure it contains an underscore and is no more than 5 characters long");
    }

    @Test
    public void testCheckPasswordInValid() {

        login.password = "Ch&&sec@ke99!";
        String expected = "Password captured";
        assertEquals(expected, "Password captured");
    }

    @Test
    public void testCheckPasswordInvalid() {

        login.password = "Password";
        String expected = "Password is not formatted correctly, make sure contains a symbol and a capital letter";
        assertEquals(expected, "Password is not formatted correctly, make sure contains a symbol and a capital letter");
    }

    @Test
    public void testPhoneNumberValid() {

        login.cellNumber = "+27838968976";
        String expected = "Number captured successful";
        assertEquals(expected, "Number captured successful");
    }

    @Test
    public void testPhoneNumberInvalid() {

        login.cellNumber = "08966553";
        String expected = "Number not entered correctly or does not contain international code";
        assertEquals(expected, "Number not entered correctly or does not contain international code");
    }

    @Test
    public void testCheckUserNameTrue() {

        login.username = "kyl_1";
        assertTrue(login.checkUserName());
    }

    @Test
    public void testCheckUserNameFalse() {

        login.username = "kyle!!!!!!!";
        assertFalse(login.checkUserName());
    }
    @Test
    public void testPasswordTrue(){

        login.password = "Ch&&sec@ke99!";
        assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!"));
    }
    @Test
    public void testPasswordFalse(){

        login.password = "Password";
        assertFalse(login.checkPasswordComplexity("Password"));
    }
    @Test
    public void loginSuccessTrue(){
        login.setUsername("kyl_1");
        login.setPassword("Ch&&sec@ke99!");
        assertTrue(login.loginUser("kyl_1","Ch&&sec@ke99!"));
    }
    @Test
    public void loginSuccessFalse(){
        assertFalse(login.loginUser("kyle!!!!!!!","Password"));
    }
}