package ru.andreycherenkov.digitasignature.services;

import org.springframework.context.annotation.PropertySource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
@PropertySource("classpath:application.properties")
public class EncryptorService {

    private static final int ALPHABET_SIZE = 26;

    //Хеширование методом SHA-256
    public static String hashData(@NonNull String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    //Шифруем шифром Цезаря (самый простой вариант)
    public static String encrypt(String plainText, int shiftKey) {
        StringBuilder cipherText = new StringBuilder();
        for (char character : plainText.toCharArray()) {
            if (Character.isLetter(character)) {
                boolean isUpperCase = Character.isUpperCase(character);
                char base = isUpperCase ? 'A' : 'a';
                char shiftedChar = (char) ((character - base + shiftKey) % ALPHABET_SIZE + base);
                cipherText.append(shiftedChar);
            } else {
                cipherText.append(character); // Non-letters remain unchanged
            }
        }
        return cipherText.toString();
    }

    public static String decrypt(String cipherText, int shiftKey) {
        return encrypt(cipherText, ALPHABET_SIZE - shiftKey); // Reverse shift for decryption
    }
}