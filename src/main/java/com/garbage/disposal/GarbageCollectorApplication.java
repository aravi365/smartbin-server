package com.garbage.disposal;

import javax.servlet.ServletContext;

import org.h2.server.web.WebServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.client.ClientBuilder;
import com.stormpath.sdk.client.Clients;

@SpringBootApplication
public class GarbageCollectorApplication implements CommandLineRunner {

	@Autowired
	private ServletContext servletContext;

	public static void main(String[] args) {
		SpringApplication.run(GarbageCollectorApplication.class, args);
	}

	@Bean
	ServletRegistrationBean h2servletRegistration() {

		ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebServlet());

		registrationBean.addUrlMappings("/database/*");

		return registrationBean;

	}

	// This method will execute when the application starts
	@Override
	public void run(String... arg0) throws Exception {
		// Instantiate a builder for your client and set required properties
		// This is used for Stormpath
		ClientBuilder builder = Clients.builder();

		// Build the client instance that you will use throughout your
		// application code
		Client client = builder.build();

		// Storing the client object in servlet context so that we can reuse the
		// same later on
		this.servletContext.setAttribute("stormpath", client);

	}
}
