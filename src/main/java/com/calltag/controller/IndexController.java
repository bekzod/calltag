/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.calltag.controller;

import com.calltag.service.TwitterListener;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.conf.ConfigurationContext;
import twitter4j.conf.PropertyConfiguration;

/**
 *
 * @author bek
 */
public class IndexController extends ParameterizableViewController {
//    @Autowired
    private TwitterListener twitterListener;
    
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest hsr, HttpServletResponse hsr1) throws Exception {

        
        twitterListener = new TwitterListener();
        
        String[] keywords = {"Easter"};
        AccessToken token = new AccessToken("39681000-snjnMp3uqhVVZBikcjt51XrCWQpYHGe1uOYtS1qDc","ERBsTFTKgoqQ9mbBLZRYY5BcU8OBMFoZrk8YiqU9ooQ");
        
        twitterListener.track(token, keywords, null);
        
        ModelAndView  mv = new ModelAndView(getViewName());
        return mv;
    }
    
}
