package com.calltag.controller;

import com.calltag.model.User;
import com.calltag.service.TwitterListener;
import com.calltag.service.UserService;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;

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
    Twitter mainTwitter; //twitter api wrapper provided by twitter4j
    @Autowired 
    Configuration twitterConf;
    
   
    
    //Index Page
    @RequestMapping(value = "/index.htm", method = RequestMethod.GET)
    public String index(HttpServletRequest req,HttpServletResponse res) {
        if(req.getAttribute("user") != null) return main(req,res);//already loged go to main

        //constructing twitter signin url
        RequestToken requestToken = null;
        String authUrl = "#";
        try {
            mainTwitter.setOAuthAccessToken(null);
            requestToken = mainTwitter.getOAuthRequestToken();
        } catch (TwitterException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        if(requestToken != null){
            authUrl = requestToken.getAuthenticationURL();
            //storing request token in cookie
            req.getSession().setAttribute(REQUEST_TOKEN,requestToken);
        }
               
        req.setAttribute("twitter_url",authUrl);
        return "index";
    }
    
    
    
    
    //called by twitter when user attempts to login
    @RequestMapping(value = "/login.htm", method = RequestMethod.GET)
    public String login(HttpServletRequest req,HttpServletResponse res) {
        if(req.getAttribute("user") != null) return "redirect:/main.htm";//already loged go to main
        
        //retrieving oath tokens returned from twitter which are used to create accesstoken
        String oauthToken    = req.getParameter("oauth_token");
        String oauthVerifier = req.getParameter("oauth_verifier");
        
        RequestToken requestToken = (RequestToken)req.getSession().getAttribute(REQUEST_TOKEN);
        
        if(requestToken == null||oauthToken==null||oauthVerifier==null) return "redirect:/index.htm?error=&oauthToken="+oauthToken+"  oauthVerifier= "+oauthVerifier + "requestToken"+requestToken;//couldn't retrieve requestToken go to index

        twitter4j.User twitterUser = null;
        AccessToken accessToken    = null;
        try {
           mainTwitter.setOAuthAccessToken(null);
           accessToken = mainTwitter.getOAuthAccessToken(requestToken, oauthVerifier);
           mainTwitter.setOAuthAccessToken(accessToken);
           twitterUser = mainTwitter.showUser(accessToken.getUserId());
        } catch (TwitterException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(accessToken==null||twitterUser==null)return "redirect:/index.htm?error=2";//failed return to index
        
        long userId = accessToken.getUserId();            
        User user   = service.getUserById(userId);
        
        if(user == null){ //user does not exist create new user
            user = new User();
            user.setId(userId);
            user.setProfilePictureUrl(twitterUser.getBiggerProfileImageURL());
            user.setName(twitterUser.getName());
            user.setAccessToken(accessToken.getToken());
            user.setAccessTokenSecret(accessToken.getTokenSecret());
            user.setTwitterAccountName(accessToken.getScreenName());
            service.addUser(user);
        }
        
        req.setAttribute("user",user);//attaching user to request
        return "redirect:/main.htm";
    }
        
    
    
    
    //clears cookies redirects user to main page
    @RequestMapping(value = "/logout.htm", method = RequestMethod.GET)
    public String logout(HttpServletRequest req,HttpServletResponse res) {
        User user = (User)req.getAttribute("user");
        
        //desroying  session
        if(user !=null){
            req.setAttribute("user",null);
            user.setSessionId(null);
            service.updateUser(user);
            req.getSession().invalidate();
        }
                
        return "redirect:/index.htm";
    }
    
    
    //main page of the app
    @RequestMapping(value = "/main.htm", method = {RequestMethod.GET, RequestMethod.POST })
    public String main(HttpServletRequest req,HttpServletResponse res){
        User user = (User) req.getAttribute("user");
        if(user==null) return "redirect:/index.htm";
        
        //user can enable disable texting and calling features
        if(req.getMethod().equals("POST")){
            String isCallEnabled = req.getParameter("is_call_enabled");
            String isTextEnabled = req.getParameter("is_text_enabled");
            if(isCallEnabled==null)isCallEnabled="";
            if(isTextEnabled==null)isTextEnabled="";
            
            user.setIsCallEnabled(isCallEnabled.equals("1"));
            user.setIsTextEnabled(isTextEnabled.equals("1"));
        }

        //user updated in post handler so need to update user in database
        return "main";
    }
    
    
    
    
    // path for twillio callback, twillio calles the url when user picks up the phone
    @RequestMapping(value = "/twillio.htm", method = RequestMethod.GET)
    public String twillo(HttpServletRequest req,HttpServletResponse res) {
        String tweetid   = req.getParameter("tweet_id");
        String authorid  = req.getParameter("author_id");
        
        if(tweetid != null && authorid != null){
            Status status = null;
         
            try {
                AccessToken token = new AccessToken(twitterConf.getOAuthAccessToken(),twitterConf.getOAuthAccessTokenSecret());
                mainTwitter.setOAuthAccessToken(token);
                status = mainTwitter.showStatus(Long.parseLong(tweetid));// getting the tweet
            } catch (TwitterException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if(status!=null){
                String text = status.getText();
                //escaping phone numbers and call,text trigger keywords
                text = text.replaceFirst(TwitterListener.CALL_TRIGGER, "").replaceFirst(TwitterListener.TEXT_TRIGGER, "");
                text = Pattern.compile("(\\+\\d{12})").matcher(text).replaceAll("");
                req.setAttribute("text", text);
                req.setAttribute("author", status.getUser().getName());
            }
           
        }       
        return "twillio";
    }
}
