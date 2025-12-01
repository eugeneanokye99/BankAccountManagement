package utils;

import java.util.Scanner;
import java.util.regex.Pattern;

public class InputValidator {

    // Name validation
    public static boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }

        String trimmedName = name.trim();

        // Check minimum length
        if (trimmedName.length() < 2) {
            return false;
        }

        // Check for numbers
        if (Pattern.compile(".*\\d.*").matcher(trimmedName).matches()) {
            return false;
        }

        // Check for only letters and spaces
        return Pattern.compile("^[a-zA-Z\\s]+$").matcher(trimmedName).matches();
    }

    // Age validation
    public static boolean isValidAge(int age) {
        return age >= 18 && age <= 120;
    }

    public static boolean isValidAge(String ageInput) {
        try {
            int age = Integer.parseInt(ageInput.trim());
            return isValidAge(age);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Contact validation
    public static boolean isValidContact(String contact) {
        if (contact == null || contact.trim().isEmpty()) {
            return false;
        }

        String trimmedContact = contact.trim();

        // Basic phone validation
        if (!Pattern.compile("^[+]?[\\d\\s\\-\\(\\)]+$").matcher(trimmedContact).matches()) {
            return false;
        }

        // Minimum length check
        return trimmedContact.length() >= 7;
    }

    // Address validation
    public static boolean isValidAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            return false;
        }

        return address.trim().length() >= 5;
    }

    // Amount validation
    public static boolean isValidAmount(double amount) {
        return amount > 0;
    }

    public static boolean isValidAmount(String amountInput) {
        try {
            double amount = Double.parseDouble(amountInput.trim());
            return isValidAmount(amount) && isValidDecimalPlaces(amountInput);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Check decimal places (max 2)
    public static boolean isValidDecimalPlaces(String amount) {
        if (!amount.contains(".")) {
            return true;
        }

        String decimalPart = amount.split("\\.")[1];
        return decimalPart.length() <= 2;
    }

    // Menu choice validation
    public static boolean isValidChoice(int choice, int min, int max) {
        return choice >= min && choice <= max;
    }

    public static boolean isValidChoice(String choiceInput, int min, int max) {
        try {
            int choice = Integer.parseInt(choiceInput.trim());
            return isValidChoice(choice, min, max);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Confirmation validation
    public static boolean isValidConfirmation(String input) {
        if (input == null || input.trim().isEmpty()) {
            return false;
        }

        String trimmed = input.trim().toLowerCase();
        return trimmed.equals("y") || trimmed.equals("n");
    }

    // Account number validation (format: ACC001)
    public static boolean isValidAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            return false;
        }

        return Pattern.compile("^ACC\\d{3}$").matcher(accountNumber.trim()).matches();
    }

    // Email validation (optional)
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return Pattern.compile(emailRegex).matcher(email.trim()).matches();
    }

    // Get valid input with prompt and validation
    public static String getValidInput(Scanner scanner, String prompt, ValidationRule validator, String errorMessage) {
        while (true) {
            CustomUtils.printInline(prompt);
            String input = scanner.nextLine().trim();

            if (validator.isValid(input)) {
                return input;
            }

            CustomUtils.printError(errorMessage);
        }
    }

    // Get valid integer input
    public static int getValidInt(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            CustomUtils.printInline(prompt);
            String input = scanner.nextLine().trim();

            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                }
                CustomUtils.printError("Please enter a number between " + min + " and " + max);
            } catch (NumberFormatException e) {
                CustomUtils.printError("Please enter a valid number");
            }
        }
    }

    // Get valid double input
    public static double getValidDouble(Scanner scanner, String prompt, double min) {
        while (true) {
            CustomUtils.printInline(prompt);
            String input = scanner.nextLine().trim();

            try {
                double value = Double.parseDouble(input);
                if (value >= min) {
                    return value;
                }
                CustomUtils.printError("Amount must be at least $" + min);
            } catch (NumberFormatException e) {
                CustomUtils.printError("Please enter a valid amount");
            }
        }
    }

    // Interface for custom validation rules
    public interface ValidationRule {
        boolean isValid(String input);
    }

    // Pre-defined validation rules
    public static class ValidationRules {
        public static final ValidationRule NAME_RULE = InputValidator::isValidName;
        public static final ValidationRule AGE_RULE = InputValidator::isValidAge;
        public static final ValidationRule CONTACT_RULE = InputValidator::isValidContact;
        public static final ValidationRule ADDRESS_RULE = InputValidator::isValidAddress;
        public static final ValidationRule AMOUNT_RULE = InputValidator::isValidAmount;
        public static final ValidationRule ACCOUNT_NUMBER_RULE = InputValidator::isValidAccountNumber;
    }
}