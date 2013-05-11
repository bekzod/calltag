package com.calltag.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="user")
public class User implements Serializable {
    
    @Id
    private long id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "twitter_account_name")
    private String twitterAccountName;
    
    @Column(name = "access_token")
    private String accessToken;
    
    @Column(name = "access_token_secret")
    private String accessTokenSecret;
    
    @Column(name = "session_id")
    private String sessionId;
    
    @Column(name = "session_expiry_date")
    private long sessionExpiryDate;
    
    @Column(name = "is_call_enabled")
    private boolean isCallEnabled;
    
    @Column(name = "is_text_enabled")
    private boolean isTextEnabled;
    
    @Column(name = "profile_picture_url")
    private String profilePictureUrl;
                    
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
    
    public String getTwitterAccountName(){
        return twitterAccountName;
    }
    
    public void setTwitterAccountName(String name){
          twitterAccountName = name;
    }
    
    public String getProfilePictureUrl(){
        return profilePictureUrl;
    }
    
    public void setProfilePictureUrl(String url){
          profilePictureUrl = url;
    }
    
    
}