package AndreySemeynikov.tasks.encryption;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;

public class DirectoryFileEncryptor {

    private SecretKey secretKey;

    public DirectoryFileEncryptor() {
        // Генерация ключа при создании экземпляра класса
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            secretKey = keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    public void encryptFile(Path filePath) throws IOException {
        byte[] fileContent = Files.readAllBytes(filePath);
        byte[] encryptedContent = encryptData(fileContent);

        // Сохраняем зашифрованный контент обратно в файл
        Files.write(filePath, encryptedContent);
    }

    private byte[] encryptData(byte[] data) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void saveKeyToFile(Path keyFilePath) throws IOException {
        // Сохраняем ключ в файл
        Files.write(keyFilePath, secretKey.getEncoded());
    }
    public String getBase64EncodedKey() {
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }
    public void loadDecryptionKey(String key) {
        byte[] keyBytes = Base64.getDecoder().decode(key);
        secretKey = new SecretKeySpec(keyBytes, "AES");
    }
    public byte[] decryptData(byte[] data) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public boolean askUserForEncryption() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Were the objects encrypted? (yes/no): ");
        String response = scanner.nextLine().toLowerCase();
        return "yes".equals(response);
    }

    public String askUserForDecryptionKey() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the decryption key: ");
        return scanner.nextLine();
    }
}
