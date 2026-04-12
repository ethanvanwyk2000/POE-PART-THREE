void main() {
    Scanner scanner = new Scanner(System.in);
    Login userAccount = new Login();

    // --- REGISTRATION PHASE ---
    System.out.println("--- REGISTRATION ---");
    System.out.print("Enter First Name: ");
    userAccount.setFirstName(scanner.nextLine());

    System.out.print("Enter Last Name: ");
    userAccount.setLastName(scanner.nextLine());

    do {
        System.out.print("Enter Username: ");
        userAccount.setUsername(scanner.nextLine());

        if (!userAccount.checkUserName()){
            System.out.println("Username incorrectly formatted, make sure it contains an underscore and is no more than 5 characters long");
        }else {
            System.out.println("Username captured successfully");
        }
    }while (!userAccount.checkUserName());


    do {
        System.out.print("Enter Password: ");
        userAccount.setPassword(scanner.nextLine());

        if (!Login.checkPasswordComplexity(userAccount.password)){
            System.out.println("Password is not formatted correctly, make sure contains a symbol and a capital letter");
        }else {
            System.out.println("Password captured");
        }
    }while (!Login.checkPasswordComplexity(userAccount.password));



    do {
        System.out.print("Enter Cell Phone Number (e.g., +27838968976): ");
        userAccount.setCellNumber(scanner.nextLine());

        if (!userAccount.checkCellPhoneNumber()){
            System.out.println("Number not entered correctly or does not contain international code");
        }else {
            System.out.println("Number captured successful");
        }
    }while (!userAccount.checkCellPhoneNumber());


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

        scanner.close();
    }
}