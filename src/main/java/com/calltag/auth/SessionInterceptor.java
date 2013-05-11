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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
 

public class SessionInterceptor extends HandlerInterceptorAdapter{
    @Autowired 
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest req,HttpServletResponse res, Object handler){
        //getting session object, if does not exist then it is created
        HttpSession session = req.getSession(true);
        
        if(!session.isNew()){
           User user = userService.getUserBySession(session.getId());
           long expiryDate = session.getCreationTime() + session.getMaxInactiveInterval()*1000;
           
           // checking if expiry dates match with one in database
           if(user !=null && user.getSessionExpiryDate()==expiryDate){
              req.setAttribute("user", user);
           }
        }

        return true;
    }
    
        
    @Override
    public void postHandle(
                HttpServletRequest req,
                HttpServletResponse res,
                Object handler,
                ModelAndView modelAndView){

        User user = (User) req.getAttribute("user");
        if(user!=null){
            HttpSession session = req.getSession();
            
            int timeout     = 24*60*60;//one day interval in seconds 
            long expiryDate = session.getCreationTime(); 
                    
//                    + timeout*1000; // expiry date in milliseconds
            
            session.setMaxInactiveInterval(timeout);

            user.setSessionId(session.getId());
            user.setSessionExpiryDate(expiryDate); //storing expiry date as well in database
            userService.updateUser(user);
        }
        
    }
}
 