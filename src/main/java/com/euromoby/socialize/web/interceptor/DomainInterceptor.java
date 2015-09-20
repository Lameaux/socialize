package com.euromoby.socialize.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.euromoby.socialize.core.Config;
import com.euromoby.socialize.web.Session;

public class DomainInterceptor extends HandlerInterceptorAdapter {
 
    @Autowired
    private Session session;
    
    @Autowired
    private Config config;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	
    	String serverName = request.getServerName();
    	String dotAppHost = "." + config.getAppHost();
    	String domain = null;
    	int pos =  serverName.indexOf(dotAppHost);
    	if (pos != -1) {
    		domain = serverName.substring(0, pos);
    	}
    	session.setWebsite(domain);
        return true;
    }

}
