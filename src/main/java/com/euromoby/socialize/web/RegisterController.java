package com.euromoby.socialize.web;

import org.apache.velocity.tools.generic.EscapeTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.euromoby.socialize.core.model.CheckEmailStatus;

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

	@RequestMapping(value = "/register/check-email/{email}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody    
    CheckEmailStatus registerCheckEmail(ModelMap model, @PathVariable("email") String email) {
		CheckEmailStatus status = new CheckEmailStatus();
		status.setEmail(email);
		if ("ss@ami.cz".equals(email)) {
			status.setExists(true);
		}
        return status;
    }    
    
}
