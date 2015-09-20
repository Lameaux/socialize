package com.euromoby.socialize.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.euromoby.socialize.core.Config;
import com.euromoby.socialize.core.model.Website;
import com.euromoby.socialize.core.service.WebsiteService;
import com.euromoby.socialize.web.Session;
import com.euromoby.socialize.web.dto.CheckDomainStatusDto;
import com.euromoby.socialize.web.dto.WebsiteDto;
import com.euromoby.socialize.web.dto.WebsiteStatusDto;
import com.euromoby.socialize.web.exception.BadRequestException;
import com.euromoby.socialize.web.transform.WebsiteTransformer;

@Controller
public class WebsiteController {

	@Autowired
	private Session session;

	@Autowired
	private WebsiteService websiteService;
	
	@Autowired
	private WebsiteTransformer websiteTransformer;
	
	@Autowired
	private Config config;

	
	@RequestMapping(value = "/add-website", method = RequestMethod.GET)
	public String add(ModelMap model) {
		if (session.getUserAccount() == null) {
			return "redirect:/login";
		}
		
		model.put("pageTitle", "Add Website");
		model.put("appDomain", config.getAppHost());
		return "website-add";
	}

	@RequestMapping(value = "/check-domain/{domain}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody
	CheckDomainStatusDto checkDomain(ModelMap model, @PathVariable("domain") String domain) {

		if (session.getUserAccount() == null) {
			throw new BadRequestException();
		}
		
		CheckDomainStatusDto status = new CheckDomainStatusDto();
		
		Website website = websiteService.findByDomain(domain.toLowerCase());
		if (website != null) {
			status.setExists(true);
		}
		return status;
	}	

	@RequestMapping(value = "/add-website", method = RequestMethod.POST, consumes = "application/json; charset=utf-8", produces = "application/json; charset=utf-8")
	public @ResponseBody
	WebsiteStatusDto addPost(@Valid @RequestBody(required = true) WebsiteDto dto) {
		if (session.getUserAccount() == null) {
			throw new BadRequestException();
		}
		
		WebsiteStatusDto status = new WebsiteStatusDto();
		
		if (!dto.getDomain().toLowerCase().matches("^[a-z0-9]+[a-z0-9-]+[a-z0-9]+$")) {
			status.setError(true);
			return status;			
		}
		if (websiteService.findByDomain(dto.getDomain().toLowerCase()) != null) {
			status.setError(true);
			return status;
		}

		Website website = websiteTransformer.newWebsite(dto, session.getUserAccount());
		websiteService.save(website);

		String redirectUrl = config.getAppUrl() + "/website/" + website.getDomain();
		status.setRedirectUrl(redirectUrl);
		status.setError(false);
		return status;
	}

	@RequestMapping(value = "/website/{domain}", method = RequestMethod.GET)
	public String website(ModelMap model, @PathVariable("domain") String domain) throws Exception {
		if (session.getUserAccount() == null) {
			throw new BadRequestException();
		}
		
		model.put("pageTitle", domain);		
		return "empty";
	}	
	
}
