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
        ModelAndView  mv = new ModelAndView(getViewName());
        User user = (User) req.getAttribute("user");
        if(user!=null){
            mv.addObject("url", "logged in");
        }else{
            mv.addObject("url", "not logged");
        }
        
        return mv;
    }
}
