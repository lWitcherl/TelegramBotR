package com.sikoraton.telegrambotr.util;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.springframework.stereotype.Component;

@Component
public class SessionCache implements Serializable {
    private static final String FILE_NAME = "sessioncache.txt";
    private final Logger logger = LogManager.getLogger(SessionCache.class);
    private static final Marker SESSION_CACHE = MarkerManager.getMarker("SESSION_CACHE");
    private static final Marker DESERIALIZATION = MarkerManager.getMarker("DESERIALIZATION")
                                                                .addParents(SESSION_CACHE);
    private static final Marker SERIALISATION = MarkerManager.getMarker("SERIALISATION")
                                                                .addParents(SESSION_CACHE);

    private static final HashMap<Long, UserCache> sessionCache = new HashMap<>();

    @PostConstruct
    void init(){
        sessionCache.clear();
        try {
            FileInputStream fileInputStream
                    = new FileInputStream(FILE_NAME);
            ObjectInputStream objectInputStream
                    = new ObjectInputStream(fileInputStream);
            sessionCache.putAll((HashMap<Long, UserCache>) objectInputStream.readObject());
            objectInputStream.close();
        } catch (ClassNotFoundException | IOException  e) {
            logger.error(DESERIALIZATION, e.getMessage(), e);
        }
    }

    @PreDestroy
    void preDestroy(){
        try {
            FileOutputStream fileOutputStream
                    = new FileOutputStream(FILE_NAME);
            ObjectOutputStream objectOutputStream
                    = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(sessionCache);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException e) {
            logger.error(SERIALISATION, e.getMessage(), e);
        }
    }

    public UserCache getUserCache(Long userId){
        if(sessionCache.containsKey(userId))
            return sessionCache.get(userId);
        UserCache newUser = new UserCache();
        sessionCache.put(userId, newUser);
        return newUser;
    }
}
