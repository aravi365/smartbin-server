package com.garbage.disposal.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Location implements Serializable {

	@Id
	@Column(name = "location_id")
	@GeneratedValue
	private Integer locationID;

	@Column
	private String location;

	public Location() {

	}

	public Location(String location) {
		super();
		this.location = location;
	}

	public Location(Integer locationID, String location) {
		super();
		this.locationID = locationID;
		this.location = location;
	}

	public Integer getLocationID() {
		return locationID;
	}

	public void setLocationID(Integer locationID) {
		this.locationID = locationID;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "Location [locationID=" + locationID + ", location=" + location + "]";
	}

}
