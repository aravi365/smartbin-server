package com.garbage.disposal.configuration;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;


public class CsrfSecurityRequestMatcher implements RequestMatcher {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	// Enabled CSFR protection on the following urls:
    private AntPathRequestMatcher[] disableCsrfMatchers = {
            new AntPathRequestMatcher("/database/**"),new AntPathRequestMatcher("/garbagecollector/**")
    };


    public boolean matches(HttpServletRequest request) {
    	logger.info("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
        // If the request match one url the CSFR protection will not be enabled
        for (AntPathRequestMatcher rm : disableCsrfMatchers) {
            if (rm.matches(request)) {
                return false;
            }
        }
        return true;
    } // method matches
}
