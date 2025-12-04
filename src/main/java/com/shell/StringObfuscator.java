package com.shell;

import java.util.Base64;

public class StringObfuscator {

    public static String d(String s) {
        try {
            byte[] decoded = Base64.getDecoder().decode(s);
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < decoded.length; i++) {
                result.append((char) (decoded[i] ^ 42));
            }
            return result.toString();
        } catch (Exception e) {
            return "";
        }
    }
}