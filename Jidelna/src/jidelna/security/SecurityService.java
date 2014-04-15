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
import jidelna.connection.DataRepository;

/**
 *
 * @author elopin
 */
public class SecurityService {
    
    private final String SALT = "ě+á-š*í/č`ř~á!";
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
            String saltedPassword = SALT+password;
            md.update(saltedPassword.getBytes("UTF-8"));
            digest =  md.digest();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SecurityService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return digest;
    }
    
    public boolean authenticate(byte[] userPasswordHash, String password) {
        byte[] digest = getEncryptedPassword(password);
        return MessageDigest.isEqual(digest, userPasswordHash);
    }
    
}

