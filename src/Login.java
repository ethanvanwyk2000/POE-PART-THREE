public class Login {
    // Instance variables to store user data
    public String firstName;
    public String lastName;
    public String username;
    public String password;
    public String cellNumber;

    // Setters to be used during registration
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setCellNumber(String cellNumber) { this.cellNumber = cellNumber; }

    // Ensures username contains an underscore and is no more than 5 characters
    public boolean checkUserName() {
        return username.contains("_") && username.length() <= 5;
    }

    // Ensures password meets the 4 complexity rules
    public boolean checkPasswordComplexity(String password) {
        return password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[0-9].*") &&
                password.matches(".*[!@#$%^&*()].*");
    }

    // Regex-based cell phone checker for international code and length
    // Matches the test data +27838968976
    public boolean checkCellPhoneNumber() {
        return !cellNumber.matches("\\+27\\d{9}") && !cellNumber.matches("0\\d{9}");
    }

    // Returns the specific registration messages
    public String registerUser() {
        if (!checkUserName()) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        } else if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        } else {
            return "The two above conditions have been met, and the user has been registered successfully.";
        }
    }

    // Verifies if login details match stored registration details
    public boolean loginUser(String enteredUser, String enteredPass) {
        return enteredUser.equals(this.username) && enteredPass.equals(this.password);
    }

    // Returns necessary messaging for a successful or failed login
    public String returnLoginStatus(boolean loginSuccess) {
        if (loginSuccess) {
            return "Welcome " + firstName +" " + lastName + " ,it's great to see you again.";
        } else {
            return "Failed login";
        }
    }
}