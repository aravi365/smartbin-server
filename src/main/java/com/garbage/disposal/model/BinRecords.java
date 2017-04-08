package com.garbage.disposal.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class BinRecords {

	@Id
	@GeneratedValue
	@Column(name = "record_id")
	private Integer recordID;

	@Column(name = "bin_id")
	private String binID;

	@Column(name = "bin_location")
	private String location;

	@Column(name = "cleaned_date")
	private LocalDate cleanedDate;

	@Column(name = "previous_status")
	private Integer previousStatus;

	public BinRecords() {

	}

	public BinRecords(String qrCode, String location, LocalDate date, Integer previousStatus) {
		this.binID = qrCode;
		this.location = location;
		cleanedDate = date;
		this.previousStatus = previousStatus;
	}

	public BinRecords(Integer recordID, String binID, String location, LocalDate cleanedDate) {
		super();
		this.recordID = recordID;
		this.binID = binID;
		this.location = location;
		this.cleanedDate = cleanedDate;

	}

	public Integer getRecordID() {
		return recordID;
	}

	public void setRecordID(Integer recordID) {
		this.recordID = recordID;
	}

	public String getBinID() {
		return binID;
	}

	public void setBinID(String binID) {
		this.binID = binID;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public LocalDate getCleanedDate() {
		return cleanedDate;
	}

	public void setCleanedDate(LocalDate cleanedDate) {
		this.cleanedDate = cleanedDate;
	}

	public Integer getPreviousStatus() {
		return previousStatus;
	}

	public void setPreviousStatus(Integer previousStatus) {
		this.previousStatus = previousStatus;
	}

	@Override
	public String toString() {
		return "BinRecords [recordID=" + recordID + ", binID=" + binID + ", location=" + location + ", cleanedDate="
				+ cleanedDate + ", previousStatus=" + previousStatus + "]";
	}

}
