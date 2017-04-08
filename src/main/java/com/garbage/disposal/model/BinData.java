package com.garbage.disposal.model;

import org.springframework.stereotype.Component;

@Component
public class BinData {

	private String location;
	private String currentCapacity;
	private String lastCleaned;
	private String authorized;

	public BinData() {

	}

	public BinData(String location, String currentCapacity, String lastCleaned, String authorized) {
		super();
		this.location = location;
		this.currentCapacity = currentCapacity;
		this.lastCleaned = lastCleaned;
		this.authorized = authorized;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCurrentCapacity() {
		return currentCapacity;
	}

	public void setCurrentCapacity(String currentCapacity) {
		this.currentCapacity = currentCapacity;
	}

	public String getLastCleaned() {
		return lastCleaned;
	}

	public void setLastCleaned(String lastCleaned) {
		this.lastCleaned = lastCleaned;
	}

	public String getAuthorized() {
		return authorized;
	}

	public void setAuthorized(String authorized) {
		this.authorized = authorized;
	}

	@Override
	public String toString() {
		return "BinData [location=" + location + ", currentCapacity=" + currentCapacity + ", lastCleaned=" + lastCleaned
				+ ", authorized=" + authorized + "]";
	}

}
