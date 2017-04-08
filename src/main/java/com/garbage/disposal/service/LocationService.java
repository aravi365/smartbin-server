package com.garbage.disposal.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.garbage.disposal.datalayer.LocationDAO;
import com.garbage.disposal.model.Location;

@Service
public class LocationService {

	@Autowired
	private LocationDAO locationDAO;

	// Logger factory for loggig purpose
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	// Fetch all locations from database
	public List<Location> getAllLocations() {

		return this.locationDAO.findAll();
	}

	// Persisting a particular location to the database
	public void addLocation(Location location) {
		Location persistedLocation = this.locationDAO.save(location);
		if (persistedLocation.getLocationID() > 0) {
			logger.info(persistedLocation.getLocation() + " added successfully");
		}

	}

}
