/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.calltag.event;

import com.calltag.model.User;
import org.springframework.context.ApplicationListener;

/**
 *
 * @author bek
 */
public class UserEventListener implements ApplicationListener<UserEvent>{

   public void onApplicationEvent(UserEvent event) {
       this.onUserEvent(event.getType(),event.getUser());
   }
   
   //should be overriden
   protected void onUserEvent(String type,User user){
   
   }
    
}
