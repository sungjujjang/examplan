package com.sungjujjang.examplan.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256Util {

    // 1️⃣ 문자열을 SHA-256 해시 생성
    public static String hash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 알고리즘 사용 불가", e);
        }
    }

    // 2️⃣ 두 문자열 해시 비교
    public static boolean matches(String rawInput, String hashedValue) {
        String hashOfInput = hash(rawInput);
        return hashOfInput.equals(hashedValue);
    }
}
