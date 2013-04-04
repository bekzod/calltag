package com.calltag.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name="user")
public class User implements Serializable {
    
    @Id
    private long id;
    private String name;
    private String accessToken;
    private String accessTokenSecret;
    private String sessionId;
    private long sessionExpiryDate;
    private boolean isCallEnabled;
    private boolean isTextEnabled;    
                    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setIsTextEnabled(boolean bool){
        isTextEnabled = bool;
    }
    
    public boolean getIsTextEnabled(){
        return isTextEnabled;
    }
    
    public void setIsCallEnabled(boolean bool){
        isCallEnabled = bool;
    }
    
    public boolean getIsCallEnabled(){
        return isCallEnabled;
    }
    
    public void setAccessToken(String token) {
        accessToken = token;
    }

    public String getAccessToken() {
        return accessToken;
    }
    
     public void setAccessTokenSecret(String token){
        accessTokenSecret = token;
    }
    
    public String getAccessTokenSecret(){
        return accessTokenSecret;
    }
    
    public String getSessionId(){
        return sessionId;
    }
    
    public void setSessionId(String id){
        sessionId = id;
    }
    
    public long getSessionExpiryDate(){
        return sessionExpiryDate;
    }
    
    public void setSessionExpiryDate(long date){
          sessionExpiryDate = date;
    }
    
    
}