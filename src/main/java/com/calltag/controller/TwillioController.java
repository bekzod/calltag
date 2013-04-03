/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.calltag.controller;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.Authorization;

/**
 *
 * @author bek
 */

public class TwillioController extends ParameterizableViewController {
    @Autowired
    private TwitterFactory twitterFactory;
    
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {        
        String tweetid       = req.getParameter("tweetid");
        String tweetAuthorid = req.getParameter("tweetAuthorid");
        
        // should get AccessToken of tweetAuthorid for now I am putting my own
        AccessToken token = new AccessToken("39681000-kuY8WvDVCMXGOZl0Wl3gBrQEWO00gc0SsYBaEc6Vg","LiIc7aKFrcCwMHJkgIQrtUfOS8rKwnHzv0vEttU0Ng");
        Twitter twitter   = twitterFactory.getInstance(token);
        
        String text;
        
        try {
            Status status = twitter.showStatus(Long.parseLong(tweetid));
            text = status.getText();
        } catch (TwitterException e) {
            text = e.toString();
        }
        
        
        Set<String> phones = new HashSet<String>();
        Matcher m = Pattern.compile("(\\+\\d{12})").matcher(text);
        while (m.find()) {
            String phone = m.group();
            if (!phones.contains(phone)) {
                phones.add(phone);
            }
        }
        text = m.replaceAll("");
        text.replaceFirst("\\$call", "");
        text.replaceFirst("\\$text", "");
        
        ModelAndView  mv = new ModelAndView(getViewName());
        mv.addObject("say",text);
        return mv;
    }
    
}
