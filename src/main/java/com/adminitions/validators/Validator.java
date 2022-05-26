package com.adminitions.validators;

public class Validator {
    private static final String REGEX_EMAIL = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$";
    private static final String REGEX_PASSWORD = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";

    public static boolean checkEmail(String email){
        return email.matches(REGEX_EMAIL);
    }

    public static boolean checkPassword(String password){
        return password.matches(REGEX_PASSWORD);
    }
}
