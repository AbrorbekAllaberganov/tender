package com.example.tender.security;

public class SmsConstant {
    private static String token;
    public static String getToken() { return token; }

    public static void setToken(String token) { SmsConstant.token = token; }
}
