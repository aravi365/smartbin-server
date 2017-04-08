package com.garbage.disposal.configuration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import static com.stormpath.spring.config.StormpathWebSecurityConfigurer.stormpath;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class SpringBootWebAppConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {

	
	
		http.authorizeRequests().antMatchers("/").permitAll().and().authorizeRequests().antMatchers("/database/**")
				.permitAll().and().authorizeRequests().antMatchers("/garbagecollector/**")
				.permitAll();
		//http.authorizeRequests().antMatchers("/damsecurity/**").permitAll();
		//http.authorizeRequests().antMatchers("/damsecurity/lock/**").fullyAuthenticated();
		//http.authorizeRequests().antMatchers("/damsecurity/home").authenticated();
		//http.authorizeRequests().antMatchers("/damsecurity/shutterlog/*").fullyAuthenticated();
		http.csrf().disable();
	
		
	
		 http.csrf().requireCsrfProtectionMatcher(new RequestMatcher() {
			 private Pattern allowedMethods = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");
			 private RegexRequestMatcher apiMatcher = new RegexRequestMatcher("/database/.*", null);
			 private RegexRequestMatcher apiMatcher2 = new RegexRequestMatcher("/garbagecollector/.*", null);

			 @Override
			 public boolean matches(HttpServletRequest request) {
			 if(allowedMethods.matcher(request.getMethod()).matches())
			 return false;
			 if(apiMatcher.matches(request))
			 return false;
			 if(apiMatcher2.matches(request))
				 return false;
			 return true;
			 }
			 });
		

		http.headers().frameOptions().disable();
	}
	
	
	

}