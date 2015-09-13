package com.euromoby.socialize.web;

import org.apache.velocity.tools.generic.EscapeTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.euromoby.socialize.core.twitter.TwitterProvider;

@Controller
public class ProfileController {
	
	@Autowired
	private Session session;
	@Autowired
	private TwitterProvider twitterProvider;
	
    @RequestMapping("/profile")
    public String profile(ModelMap model) {

    	model.put("session", session);
		model.put("escape", new EscapeTool());
    	model.put("pageTitle", "Profile");
        return "profile";
    }	
	
}
