package com.multiversebackend.snipprio.service;

import com.multiversebackend.snipprio.model.Snippet;
import com.multiversebackend.snipprio.repository.SnippetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class SnippetService {
    @Autowired
    SnippetRepository snippetRepository;

    private static final int MAX_CONTENT_LENGTH = 255;

    public Snippet createSnippet(Snippet snippet) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException {
        //generate secret key
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128);
        SecretKey secretKey = keyGen.generateKey();

        //initialize cipher for encryption
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        //serialize snippet to bytes
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(snippet);
        byte[] snippetBytes = baos.toByteArray();

        //encrypt the snippet
        byte[] encryptedBytes = cipher.doFinal(snippetBytes);

        //convert encrypted bytes to string
        String encryptedSnippet = Base64.getEncoder().encodeToString(encryptedBytes);

        //truncate the content
        if (encryptedSnippet.length() > MAX_CONTENT_LENGTH) {
            encryptedSnippet = encryptedSnippet.substring(0, MAX_CONTENT_LENGTH);
        }

        //create new snippet instance to set the encrypted value
        Snippet encryptedSnippetObject = new Snippet();
        encryptedSnippetObject.setCode(encryptedSnippet);

        //return encrypted snippet
        return snippetRepository.save(encryptedSnippetObject);
    }

    public Snippet getSnippetById(Long id) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException, ClassNotFoundException {
        Snippet encryptedSnippet = snippetRepository.findById(id).orElse(null);
        if (encryptedSnippet != null) {
            //retrieving the encrypted snippet
            String encryptedContent = encryptedSnippet.getCode();

            System.out.println("Encrypted snippet: " + encryptedContent); //works - displays the code
        try {
            //decrypt the snippet
            byte[] encryptedBytes = new byte[0];
            if (encryptedContent != null) {
                encryptedBytes = Base64.getDecoder().decode(encryptedContent.getBytes(StandardCharsets.UTF_8));

                //generate secret key
                KeyGenerator keyGen = KeyGenerator.getInstance("AES");
                keyGen.init(128);
                SecretKey secretKey = keyGen.generateKey();

                //initialize cipher
                Cipher cipher = Cipher.getInstance("AES");
                cipher.init(Cipher.DECRYPT_MODE, secretKey);

                //decrypt snippet bytes
                byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

                //deserialize the bytes to snippet object
                ByteArrayInputStream bais = new ByteArrayInputStream(decryptedBytes);
                ObjectInputStream ois = new ObjectInputStream(bais);
                Snippet decryptedSnippet = (Snippet) ois.readObject();

                return decryptedSnippet;
            } else {
                System.out.println("Error: Encrypted content is null.");
                return null;
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Error: Invalid base64 character found.");
            e.printStackTrace();
            return null;
            }
        }
        return null;
    }
}