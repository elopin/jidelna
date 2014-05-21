/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jidelna.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Třída pro autentizaci uživatele. Obsahuje metodu pro vytvoření
 * hashe hesla pro uložení do databáze.
 * 
 * @author Lukáš Janáček
 */
public class SecurityService {
   
    private MessageDigest md;
   
    
    public SecurityService() {
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(SecurityService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public byte[] getEncryptedPassword(String password) {
        byte[] digest = null;
        try {
            String saltedPassword = password.substring(0, 8)+password;
            md.update(saltedPassword.getBytes("UTF-8"));
            digest =  md.digest();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SecurityService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return digest;
    }
    
    public boolean authenticate(byte[] userPasswordHash, String password) {
        if(userPasswordHash == null) {
            return false;
        }
        byte[] digest = getEncryptedPassword(password);
        return MessageDigest.isEqual(digest, userPasswordHash);
    }
    
}

