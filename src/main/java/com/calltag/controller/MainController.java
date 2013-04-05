/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.calltag.controller;

import com.calltag.service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author bek
 */

@Controller
public class MainController {
    @Autowired
    private UserService service;
   
    @RequestMapping(value = "/index.htm", method = RequestMethod.GET)
    public String index(HttpServletRequest req,HttpServletResponse res) {
        req.setAttribute("user", "bekzod");
        
        return "index";
    }
    
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
