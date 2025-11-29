package com.hospital.util;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;

public class FileUtil {
    
    public static void writeToFile(String filename, String content) {
        try {
            Path filePath = Paths.get("logs", filename);
            Files.createDirectories(filePath.getParent());
            
            // Append timestamp and content
            String entry = LocalDateTime.now() + ":\n" + content + "\n";
            Files.write(filePath, entry.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
    
    public static void writeObjectToFile(String filename, Object obj) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
             new FileOutputStream("data/" + filename))) {
            oos.writeObject(obj);
        } catch (IOException e) {
            System.err.println("Error writing object to file: " + e.getMessage());
        }
    }
    
    public static Object readObjectFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(
             new FileInputStream("data/" + filename))) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error reading object from file: " + e.getMessage());
            return null;
        }
    }
    
    public static List<String> readLines(String filename) {
        try {
            return Files.readAllLines(Paths.get(filename));
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return List.of();
        }
    }
}