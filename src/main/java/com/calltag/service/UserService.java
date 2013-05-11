/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.calltag.service;

import com.calltag.event.UserEvent;
import com.calltag.event.UserEventPublisher;
import com.calltag.model.User;
import com.calltag.model.UserDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author bek
 */
public class UserService extends UserEventPublisher{
   @Autowired
   private UserDao userDao;
   
    public void addUser(User user) {
        userDao.addUser(user);
        publishEvent(new UserEvent(UserEvent.USER_ADDED,user));
    }

    public void updateUser(User user) {
        userDao.updateUser(user);
        publishEvent(new UserEvent(UserEvent.USER_UPDATED, user));
    }

    public void removeUser(User user) {
        userDao.removeUser(user);
        publishEvent(new UserEvent(UserEvent.USER_REMOVED, user));
    }

    public User getUserById(long id) {
        return userDao.getUserById(id);
    }
    
    public List<User> getUsers() {
        return userDao.getUsers();
    }
    
    public User getUserBySession(String sessionId) {
        return userDao.getUserBySession(sessionId);
    }
 
}
