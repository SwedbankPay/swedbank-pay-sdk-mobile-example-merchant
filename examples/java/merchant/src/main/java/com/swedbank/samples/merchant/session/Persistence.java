package com.swedbank.samples.merchant.session;

import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;

@Repository
public class Persistence {
    private ConcurrentHashMap<String, UserSession> userSessions;

    public Persistence() {
        userSessions = new ConcurrentHashMap<>();
    }

    public UserSession getSession(String ssn) {
        return userSessions.get(ssn);
    }

    public void setSession(String ssn, UserSession session) {
        userSessions.put(ssn, session);
    }

    public void endSession(String ssn) {
        userSessions.remove(ssn);
    }
}
