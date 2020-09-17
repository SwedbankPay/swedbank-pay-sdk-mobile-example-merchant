package com.swedbank.samples.merchant.session;

import java.util.HashMap;

public class UserSession {
    private HashMap<String, String> paymentTokens;
    
    public UserSession() {
        paymentTokens = new HashMap<>();
    }
    
    public synchronized void setToken(String id, String token) {
        paymentTokens.put(id, token);
    }
    
    public synchronized String getToken(String id) {
        return paymentTokens.get(id);
    }
    
    public synchronized void voidToken(String id) {
        paymentTokens.remove(id);
    }
}
