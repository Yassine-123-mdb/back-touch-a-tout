package com.yassine.users.security;

public interface SecParams {
    public static final long EXP_TIME = 10 * 24 * 60 * 60 * 1000; // 10 days
    public static final String SECRET = "yassinmeddeb564@gmail.com"; // Replace with a secure secret
    public static final String PREFIX = "Bearer ";
}
