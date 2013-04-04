/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.calltag.controller;

import com.calltag.model.User;
import com.calltag.service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

/**
 *
 * @author bek
 */
public class IndexController extends ParameterizableViewController {
    @Autowired
    private UserService service;
    
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
        
//        OAuthAuthorization auth = new OAuthAuthorization(twitterConf);
//        auth.setOAuthAccessToken(null);
////        
//        try {
//            phone.call("447414651686","http://calltag.heroku.com/twillio.htm?tweetid=318124365143224320");
//        } catch (TwilioRestException ex) {
//            Logger.getLogger(TwitterListener.class.getName()).log(Level.SEVERE, null, ex);
//        }
////    

//        req.getSession().getMaxInactiveInterval();
        
        ModelAndView  mv = new ModelAndView(getViewName());
        mv.addObject("url",req.getSession().getId());
        return mv;
    }
}
