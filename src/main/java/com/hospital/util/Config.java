package com.hospital.util;

import java.util.ResourceBundle;

public class Config {
    private static final ResourceBundle bundle = ResourceBundle.getBundle("application");
    
    public static String getDbUrl() {
        return bundle.getString("db.url");
    }
    
    public static String getDbUsername() {
        return bundle.getString("db.username");
    }
    
    public static String getDbPassword() {
        return bundle.getString("db.password");
    }
}