package com.garbage.disposal.listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.garbage.disposal.model.Location;

import com.garbage.disposal.service.LocationService;

@Component
public class LocationManager {

	// Logger factory for loggig purpose
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private List<Location> locationList = new ArrayList<>();

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private LocationService locationService;
	
	
	private Map<String,String> nearestLocationMap = new HashMap<String,String>();

	// This method will be fired at the start of application and will check
	// whether there is any new location to add to the existing location list
	@EventListener(ContextRefreshedEvent.class)
	private void addLocations() {

		initializeLocations();

		// Java 8 feature of streams used here to get all the locations from the
		// database
		List<String> persistedLocations = this.locationService.getAllLocations().stream().map(a -> a.getLocation())
				.collect(Collectors.toList());
		locationList.forEach((location) -> {

			if (persistedLocations.contains(location.getLocation())) {

			} else {
				logger.info("Trying to add a new Location");
				this.locationService.addLocation(location);
			}

		});
	}

	// Initializing Locations
	private void initializeLocations() {
		locationList.add(new Location("Chitoor"));
		locationList.add(new Location("Ravipuram"));
		locationList.add(new Location("Vytilla"));
		locationList.add(new Location("Eroor"));
		
		
		servletContext.setAttribute("locationList",
				locationList.stream().map(a -> a.getLocation()).collect(Collectors.toList()));

		populateNearestLocationMap();
	}
	
	
	private void populateNearestLocationMap()
	{
		nearestLocationMap.put("Chitoor", "Ravipuram");
		nearestLocationMap.put("Ravipuram", "Vytilla");
		nearestLocationMap.put("Vytilla", "Eroor");
		nearestLocationMap.put("Eroor", "Vytilla");
		servletContext.setAttribute("nearestLocationFinder",nearestLocationMap);
	}

}
