package com.euromoby.socialize.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.euromoby.socialize.web.Session;

@Controller
public class WelcomeController {
	
	@Autowired
	private Session session;
	
    @RequestMapping("/")
    public String welcome(ModelMap model) {
    	model.put("pageTitle", "Welcome");
    	model.put("session", session);
        return "welcome";
    }	
 
	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public String error(ModelMap model) {
    	model.put("pageTitle", "Error");
		return "error";
	}    

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test(ModelMap model) throws Exception {
    	model.put("pageTitle", "Test");		
		return "empty";
	}	
	
}
