package com.example.projetandroid.Entity;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {

    private int id;
    private String name;
    private String login;
    private String password;
    private String role;

    public User(String name, String login, String password, String role) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        this.name = name;
        this.login = login;
        this.password = this.SHA1(password);
        this.role = role;
    }

    public User(String name, String login, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        this.name = name;
        this.login = login;
        this.password = this.SHA1(password);
    }

    public User(String login, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        this.login = login;
        this.password = this.SHA1(password);
    }

    public User(int id, String name, String login, String password, String role) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = this.SHA1(password);
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException { ;
        this.password = this.SHA1(password);
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] textBytes = text.getBytes("iso-8859-1");
        md.update(textBytes, 0, textBytes.length);
        byte[] sha1hash = md.digest();
        return convertToHex(sha1hash);
    }
}
