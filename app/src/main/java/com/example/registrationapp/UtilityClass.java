package com.example.registrationapp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilityClass {

    // Email validation function:
    public static boolean isValidEmail(String email) {
        // email doesn't contain capital letter.
        for(int i = 0;i<email.length();i++){
            if(email.charAt(i) >= 'A' && email.charAt(i) <= 'Z'){
                return false;
            }
        }
        String emailRegex = "^[\\p{L}0-9_.-]+@[a-z0-9.-]+\\.[a-z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    // Password validation function:
    public static boolean isValidPassword(String password) {
        // min Password len is 8.
        int minLength = 8;
        if (password.length() < minLength) {
            return false;
        }
        // minimum 1 upperCase, 1 lowerCase and 1 Digit is required for valid password.
        boolean IsUpperCase = false;
        boolean IsLowerCase = false;
        boolean IsDigit = false;
        boolean IsSpecialChar = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                IsUpperCase = true;
            } else if (Character.isLowerCase(c)) {
                IsLowerCase = true;
            } else if (Character.isDigit(c)) {
                IsDigit = true;
            }
            else if (isSpecialCharacter(c)) {
                IsSpecialChar = true;
            }
        }
        return IsUpperCase && IsLowerCase && IsDigit && IsSpecialChar;
    }
    private static boolean isSpecialCharacter(char c) {
        // Define your set of special characters
        String specialCharacters = "!@#$%^&*()-_=+[]{}|;:'\",.<>/?";
        return specialCharacters.contains(String.valueOf(c));
    }
}
