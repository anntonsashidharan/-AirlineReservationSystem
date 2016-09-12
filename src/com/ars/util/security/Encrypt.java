package com.ars.util.security;

import java.security.MessageDigest;

/**
 * Created by JJ on 9/11/16.
 */
public class Encrypt {
    
    public static String hashFunction(String string)throws  Exception{
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(string.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            sb.append(Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
    
}
