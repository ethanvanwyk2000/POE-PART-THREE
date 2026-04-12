import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class LoginTest {
    @Test
    public void testCheckUsernameValid() {
        Login login = new Login();

        login.setUsername("kyl_1");
        assertTrue(login.checkUserName());
    }

    @Test
    public void testCheckUsernameInvalid() {
        Login login = new Login();

        login.username = "kyle!!!!!!!";
        assertFalse(login.checkUserName());
    }

    @Test
    public void testCheckPasswordValid() {
        assertTrue(Login.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    @Test
    public void testCheckPasswordInvalid() {
        assertFalse(Login.checkPasswordComplexity("password"));
    }

    @Test
    public void testPhoneNumberValid() {
        Login login = new Login();

        login.setCellNumber("+27838968976");
        assertTrue(login.checkCellPhoneNumber());
    }

    @Test
    public void testPhoneNumberInvalid() {
        Login login = new Login();

        login.cellNumber = "08966553";
        assertFalse(login.checkCellPhoneNumber());
    }
}