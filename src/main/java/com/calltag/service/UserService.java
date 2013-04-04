/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.calltag.service;

import com.calltag.event.UserEvent;
import com.calltag.model.User;
import com.calltag.model.UserDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

/**
 *
 * @author bek
 */
public class UserService implements ApplicationEventPublisherAware   {
   @Autowired
   private UserDao userDao;
//   private ApplicationEventPublish er epublisher;
   
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher pub) {
//        epublisher = pub;
    }

   
    public void addUser(User user) {
        userDao.addUser(user);
//        epublisher.publishEvent(new UserEvent(UserEvent.USER_ADDED,user));
    }

    public void updateUser(User user) {
        userDao.updateUser(user);
//        epublisher.publishEvent(new UserEvent(UserEvent.USER_UPDATED, user));
    }

    public void removeUser(User user) {
        userDao.removeUser(user);
//        epublisher.publishEvent(new UserEvent(UserEvent.USER_REMOVED, user));
    }

    public User getUserById(long id) {
        return userDao.getUserById(id);
    }
    
    public List<User> getUsers() {
        return userDao.getUsers();
    }

 
}
