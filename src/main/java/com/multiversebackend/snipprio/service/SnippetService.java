package com.multiversebackend.snipprio.service;

import com.multiversebackend.snipprio.model.Snippet;
import com.multiversebackend.snipprio.repository.SnippetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import java.security.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class SnippetService {
    @Autowired
    SnippetRepository snippetRepository;

    public static Map<String, Object> map = new HashMap<>();

    private KeyPair keyPair;

    public KeyPair createKeys() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(4096);
            keyPair = keyPairGenerator.generateKeyPair();
            return keyPair;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Snippet encryptSnippet(Snippet snippet) {
        try {
            if (keyPair == null) {
                keyPair = createKeys();
            }
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-512ANDMGF1PADDING");
            PublicKey publicKey = keyPair.getPublic();
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encrypt = cipher.doFinal(snippet.getCode().getBytes());
            String encryptedSnippet = Base64.getEncoder().encodeToString(encrypt);

            Snippet encryptedSnippetObject = new Snippet();
            encryptedSnippetObject.setCode(encryptedSnippet);

            Snippet savedSnippet = snippetRepository.save(encryptedSnippetObject);
            return savedSnippet;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Snippet decryptSnippet(Long id) {
        try {
            Snippet snippet = snippetRepository.findById(id).orElse(null);
            if (snippet != null) {
                Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-512ANDMGF1PADDING");
                PrivateKey privateKey = keyPair.getPrivate();
                cipher.init(Cipher.DECRYPT_MODE, privateKey);
                byte[] decrypt = cipher.doFinal(Base64.getDecoder().decode(snippet != null ? snippet.getCode() : null));
                String decryptedCode = new String(decrypt);
                snippet.setCode(decryptedCode);
                return snippet;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}