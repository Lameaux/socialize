package com.euromoby.socialize.web;

import org.apache.velocity.tools.generic.EscapeTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WelcomeController {
	
	@Autowired
	private Session session;
	
    @RequestMapping("/")
    public String welcome(ModelMap model) {
    	
    	model.put("session", session);
		model.put("escape", new EscapeTool());
    	model.put("pageTitle", "Welcome");
        return "welcome";
    }	
 
	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public String error(ModelMap model) {
    	model.put("session", session);
    	model.put("pageTitle", "Error");
		return "error";
	}    
	
}
