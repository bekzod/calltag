/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.calltag.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**
 *
 * @author bek
 */

public class TwillioController extends ParameterizableViewController {
    private Twitter twitter;
    
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {        
        twitter = new TwitterFactory().getInstance();
        String tweetid = req.getParameter("tweetid");
        String say = null;
        try {
            Status status = twitter.showStatus(Long.parseLong(tweetid));
            say = status.getText();
        } catch (TwitterException e) {
        }        
        
        ModelAndView  mv = new ModelAndView(getViewName());
        mv.addObject("say",say);
        return mv;
    }
    
}
