/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.calltag.controller;

import com.calltag.model.User;
import static com.calltag.service.TwitterListener.TEXT_TRIGGER;
import com.calltag.service.UserService;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

/**
 *
 * @author bek
 */

@Controller
public class MainController {
    
    public static final String REQUEST_TOKEN = "request_token";
    
    @Autowired
    private UserService service;
    @Autowired
    Twitter mainTwitter;
    
   
    @RequestMapping(value = "/index.htm", method = RequestMethod.GET)
    public String index(HttpServletRequest req,HttpServletResponse res) {
        if(req.getAttribute("user") != null) return main(req,res);//already loged go to main

        RequestToken requestToken = null;
        try {
            mainTwitter.setOAuthAccessToken(null);
            requestToken = mainTwitter.getOAuthRequestToken();
        } catch (TwitterException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String authUrl = "/index.htm";
        if(requestToken != null){
            authUrl = requestToken.getAuthenticationURL();
            req.getSession().setAttribute(REQUEST_TOKEN,requestToken);
        }
        
        req.setAttribute("twitter_url",authUrl);
        return "index";
    }
    
    
    
    
    //called by twitter when user attempts to login
    @RequestMapping(value = "/login.htm", method = RequestMethod.GET)
    public String login(HttpServletRequest req,HttpServletResponse res) {
        if(req.getAttribute("user") != null) return "redirect:/main.htm";;//already loged go to main

        String oauthToken    = req.getParameter("oauth_token");
        String oauthVerifier = req.getParameter("oauth_verifier");
        
        RequestToken requestToken = (RequestToken)req.getSession().getAttribute(REQUEST_TOKEN);
        if(requestToken == null||oauthToken==null||oauthVerifier==null) return "redirect:/index.htm?error=1";//couldn't retrieve requestToken go to index

        twitter4j.User twitterUser = null;
        AccessToken accessToken    = null;

        try {
           accessToken = mainTwitter.getOAuthAccessToken(requestToken, oauthVerifier);
           twitterUser = mainTwitter.showUser(accessToken.getUserId());
        } catch (TwitterException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(accessToken==null||twitterUser==null)return "redirect:/index.htm?error=1";//failed return to index
        
        long userId = accessToken.getUserId();            
        User user   = service.getUserById(userId);
        
        if(user == null){ //use doens't exist create new user
            user = new User();
            user.setId(userId);
            user.setProfilePictureUrl(twitterUser.getBiggerProfileImageURL());
            user.setName(twitterUser.getName());
            user.setAccessToken(accessToken.getToken());
            user.setAccessTokenSecret(accessToken.getTokenSecret());
            user.setTwitterAccountName(accessToken.getScreenName());
            service.addUser(user);
        }
        
        req.setAttribute("user",user);//saving user for session save
        return main(req,res);
    }
        
    
    
    
    //called by twitter when user attempts to login
    @RequestMapping(value = "/logout.htm", method = RequestMethod.GET)
    public String logout(HttpServletRequest req,HttpServletResponse res) {
        User user = (User)req.getAttribute("user");
        if(user !=null){
            req.setAttribute("user",null);
            user.setSessionId(null);
            service.updateUser(user);
            req.getSession().invalidate();
        }
                
        return "redirect:/index.htm";
    }
    
    
    
    
    
    
    
    @RequestMapping(value = "/main.htm", method = RequestMethod.GET)
    public String main(HttpServletRequest req,HttpServletResponse res){
        User user = (User) req.getAttribute("user");
        if(user==null) return "redirect:/index.htm";
        
        //user can enable disable texting and calling feature
        String isCallEnabled = req.getParameter("is_call_enabled");
        String isTextEnabled = req.getParameter("is_text_enabled");
        
        if(isCallEnabled!=null){
            boolean isEnabled = Integer.parseInt(isCallEnabled)==1;
            user.setIsCallEnabled(isEnabled);
        }
        if(isTextEnabled!=null){
            boolean isEnabled = Integer.parseInt(isTextEnabled)==1;
            user.setIsCallEnabled(isEnabled);
        }
        //user updated in post handler so need to update here
        
        return "main";
    }
    
    
    
    
    
    @RequestMapping(value = "/twillo.htm", method = RequestMethod.GET)
    public String twillo(HttpServletRequest req,HttpServletResponse res) {
        String tweetid   = req.getParameter("tweet_id");
        String authorid  = req.getParameter("author_id");
        
        if(tweetid != null && authorid != null){
            Status status = null;
            try {
                status = mainTwitter.showStatus(Long.parseLong(tweetid));
            } catch (TwitterException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if(status!=null){
                String text = status.getText();
                //escaping phone numbers and call,text trigger keywords
                text = text.replaceFirst(TEXT_TRIGGER, "").replace(TEXT_TRIGGER, "");
                text = Pattern.compile("(\\+\\d{12})").matcher(text).replaceAll("");
                req.setAttribute("text", res);
            }
           
        }       
        return "twillo";
    }
}
