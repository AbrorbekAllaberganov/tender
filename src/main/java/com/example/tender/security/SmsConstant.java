package com.example.demo.security;

public class SmsConstant {
    private static String token;
    public static String getToken() { return token; }

    public static void setToken(String token) { SmsConstant.token = token; }
}
