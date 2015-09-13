package com.euromoby.socialize.web;

import org.apache.velocity.tools.generic.EscapeTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RegisterController {
	
	private static final Logger log = LoggerFactory.getLogger(RegisterController.class);
	
	@Autowired
	private Session session;
	
    @RequestMapping(value="/register", method=RequestMethod.GET)
    public String register(ModelMap model) {
    	model.put("session", session);
		model.put("escape", new EscapeTool());
    	model.put("pageTitle", "Register");
        return "register";
    }	

    @RequestMapping(value="/register", method=RequestMethod.POST)
    public String registerPost(ModelMap model) {
    	log.info("register");
        return "redirect:/register";
    }
}
