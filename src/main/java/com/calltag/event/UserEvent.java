package com.calltag.event;

import com.calltag.model.User;
import org.springframework.context.ApplicationEvent;

/**
 *
 * @author bek
 */
public class UserEvent extends ApplicationEvent {
   
    public static final String USER_ADDED   = "user_added";
    public static final String USER_REMOVED = "user_removed";
    public static final String USER_UPDATED = "user_updated";

    private String type;
    
    public UserEvent(String type,User user) {
       super(user);
       this.type = type;
    }
    
    public String getType(){
        return type;
    }
    
    public User getUser(){
        return (User) this.source;
    }
    
    
}
