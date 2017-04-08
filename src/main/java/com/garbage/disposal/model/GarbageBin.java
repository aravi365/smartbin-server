package com.garbage.disposal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity(name = "Garbage_Bin")
public class GarbageBin {

	@Id
	@GeneratedValue
	@Column(name = "qr_id")
	private Integer qrID;

	@Column(name = "qr_code")
	private String qrCode;

	@Column(name = "location")
	private String location;

	@Column(name = "capacity")
	private Integer currentCapacity;

	public GarbageBin() {

	}

	public GarbageBin(Integer qrID, String qrCode, String location, Integer currentCapacity) {
		super();
		this.qrID = qrID;
		this.qrCode = qrCode;
		this.location = location;
		this.currentCapacity = currentCapacity;
	}

	public Integer getQrID() {
		return qrID;
	}

	public void setQrID(Integer qrID) {
		this.qrID = qrID;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getCurrentCapacity() {
		return currentCapacity;
	}

	public void setCurrentCapacity(Integer currentCapacity) {
		this.currentCapacity = currentCapacity;
	}

	@Override
	public String toString() {
		return "GarbageBin [qrID=" + qrID + ", qrCode=" + qrCode + ", location=" + location + ", currentCapacity="
				+ currentCapacity + "]";
	}

}
