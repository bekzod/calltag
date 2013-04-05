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
import twitter4j.auth.RequestToken;

/**
 *
 * @author bek
 */

@Controller
public class MainController {
    
    @Autowired
    private UserService service;
    @Autowired
    Twitter mainTwitter;
    
   
    @RequestMapping(value = "/index.htm", method = RequestMethod.GET)
    public String index(HttpServletRequest req,HttpServletResponse res) {
        //check if user logged in if so he will be redirected to main page
        //else user not logged go to index page;
        if(req.getAttribute("user") != null)return main(req,res);
        RequestToken requestToken = null;
        try {
            mainTwitter.setOAuthAccessToken(null);
            requestToken = mainTwitter.getOAuthRequestToken(null);
        } catch (TwitterException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String authUrl = requestToken!=null?requestToken.getAuthenticationURL():"/index.htm";
        req.setAttribute("twitter_url",authUrl);
        return "index";
    }
    
    //called by twitter when user attempts to login
    @RequestMapping(value = "/login.htm", method = RequestMethod.GET)
    public String login(HttpServletRequest req,HttpServletResponse res) {
        
        return main(req,res);
    }
    
    @RequestMapping(value = "/main.htm", method = RequestMethod.GET)
    public String main(HttpServletRequest req,HttpServletResponse res) {
        
        return "main";
    }
    
    @RequestMapping(value = "/twillo.htm", method = RequestMethod.GET)
    public String twillo(HttpServletRequest req,HttpServletResponse res) {

        return "twillo";
    }
}
