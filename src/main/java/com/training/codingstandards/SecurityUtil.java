package com.training.codingstandards;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HexFormat;
import java.util.Objects;

public final class SecurityUtil {

    private SecurityUtil() {
    }

    private static final String API_KEY = System.getenv().getOrDefault("APP_API_KEY", "");
    private static final String ADMIN_PASSWORD = System.getenv().getOrDefault("APP_ADMIN_PASSWORD", "");

    public static String hashIdentifier(String value) {
        if (value == null || value.isBlank()) {
            return "";
        }
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(value.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(digest);
        } catch (NoSuchAlgorithmException e) {
            return Integer.toHexString(value.hashCode());
        }
    }

    public static String sessionToken() {
        byte[] bytes = new byte[16];
        new SecureRandom().nextBytes(bytes);
        return HexFormat.of().formatHex(bytes);
    }

    public static boolean isAdmin(String password) {
        return Objects.equals(password, ADMIN_PASSWORD);
    }

    public static String getApiKey() {
        return API_KEY;
    }
}
