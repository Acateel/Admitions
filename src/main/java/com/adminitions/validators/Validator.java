package com.adminitions.validators;

public class Validator {
    private Validator(){}
    private static final String REGEX_EMAIL = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$";
    private static final String REGEX_PASSWORD = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";

    private static final String REGEX_NAME = "^[^\\(\\)\\{\\}\\[\\]\\=\\+\\*\\/0-9]+$";
    private static final String REGEX_NAME_INSTITUTION = "^[^\\(\\)\\{\\}\\[\\]\\=\\+\\*\\/]+$";

    public static boolean checkEmail(String email){
        return email.matches(REGEX_EMAIL);
    }

    public static boolean checkPassword(String password){
        return password.matches(REGEX_PASSWORD);
    }

    public static boolean checkName(String name){
        return name.matches(REGEX_NAME);
    }

    public static boolean checkInstitutionName(String name){
        return name.matches(REGEX_NAME_INSTITUTION);
    }
}