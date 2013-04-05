/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.calltag.auth;

/**
 *
 * @author bek
 */
import com.calltag.model.User;
import com.calltag.service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
 
/**
 *
 * @author bek
 */
public class SessionInterceptor extends HandlerInterceptorAdapter{
 
 @Autowired 
 private UserService userService;
    
 @Override
 public boolean preHandle(HttpServletRequest req,HttpServletResponse res, Object handler){
     HttpSession session = req.getSession();
     if(!session.isNew()){
        User user = userService.getUserBySession(session.getId());
        long expiry = session.getCreationTime() + session.getMaxInactiveInterval();
        if(user!=null && user.getSessionExpiryDate() == expiry ){
           req.setAttribute("user", user);
        }
        session.getLastAccessedTime();
     }
     return true;
 }
 
}
