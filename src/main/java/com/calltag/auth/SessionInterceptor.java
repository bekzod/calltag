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
        if(user!=null){
           req.setAttribute("user", user);
        }
     }
     return true;
 }
 
  public boolean postHandle(HttpServletRequest req,HttpServletResponse res, Object handler){
      User user = (User) req.getAttribute("user");
      HttpSession session = req.getSession(true);
      if(user!=null){
          session.setMaxInactiveInterval(24*60*60);//one day interval
          long expiry = session.getCreationTime() + session.getMaxInactiveInterval();
          user.setSessionExpiryDate(expiry);
          user.setSessionId(session.getId());
          userService.updateUser(user);
      }
      
      return true;
  }
 
}
