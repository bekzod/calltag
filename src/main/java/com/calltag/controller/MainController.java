/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.calltag.controller;

import com.calltag.model.User;
import com.calltag.service.UserService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
        if(req.getAttribute("user") != null) return main(req,res);//already loged go to main

        String oauthToken    = req.getParameter("oauth_token");
        String oauthVerifier = req.getParameter("oauth_verifier");
        
        AccessToken accessToken   = null;
        RequestToken requestToken = (RequestToken)req.getSession().getAttribute(REQUEST_TOKEN);
        
        if(requestToken == null||oauthToken==null||oauthVerifier==null) return index(req,res);//couldn't retrieve requestToken go to index
        
        try {
           accessToken = mainTwitter.getOAuthAccessToken(requestToken, oauthVerifier);
        } catch (TwitterException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(accessToken==null)return index(req,res);//failed return to index
        
        long userId = accessToken.getUserId();            
        User user   = service.getUserById(userId);
        
        if(user == null){
            user = new User();
            user.setId(userId);
            user.setAccessToken(accessToken.getToken());
            user.setAccessTokenSecret(accessToken.getTokenSecret());
            user.setName(accessToken.getScreenName());
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
                
        return "index";
    }
    
    @RequestMapping(value = "/main.htm", method = RequestMethod.GET)
    public String main(HttpServletRequest req,HttpServletResponse res) {
        if(req.getAttribute("user")==null) return index(req,res);
        
        return "main";
    }
    
    @RequestMapping(value = "/twillo.htm", method = RequestMethod.GET)
    public String twillo(HttpServletRequest req,HttpServletResponse res) {

        return "twillo";
    }
}
