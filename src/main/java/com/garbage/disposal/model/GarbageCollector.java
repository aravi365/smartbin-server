package com.garbage.disposal.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity(name = "Garbage_Collector")
public class GarbageCollector {

	@Id
	@GeneratedValue
	@Column(name = "garbage_collector_id")
	private Integer garbageCollectorID;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "email")
	private String email;

	@Column(name = "contact_number")
	private String mobileNumber;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "security_id")
	private GarbageSecurity garbageSecurity;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "garbage_collector_id")
	private List<GarbageBin> garbageBins;

	public GarbageCollector() {

	}

	public GarbageCollector(Integer garbageBinID, String firstName, String lastName, String email, String mobileNumber,
			GarbageSecurity garbageSecurity, List<GarbageBin> garbageBins) {
		super();
		this.garbageCollectorID = garbageBinID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.mobileNumber = mobileNumber;
		this.garbageSecurity = garbageSecurity;
		this.garbageBins = garbageBins;
	}

	public Integer getGarbageCollectorID() {
		return garbageCollectorID;
	}

	public void setGarbageCollectorID(Integer garbageCollectorID) {
		this.garbageCollectorID = garbageCollectorID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public GarbageSecurity getGarbageSecurity() {
		return garbageSecurity;
	}

	public void setGarbageSecurity(GarbageSecurity garbageSecurity) {
		this.garbageSecurity = garbageSecurity;
	}

	public List<GarbageBin> getGarbageBins() {
		return garbageBins;
	}

	public void setGarbageBins(List<GarbageBin> garbageBins) {
		this.garbageBins = garbageBins;
	}

	@Override
	public String toString() {
		return "GarbageCollector [garbageCollectorID=" + garbageCollectorID + ", firstName=" + firstName + ", lastName="
				+ lastName + ", email=" + email + ", mobileNumber=" + mobileNumber + ", garbageSecurity="
				+ garbageSecurity + ", garbageBins=" + garbageBins + "]";
	}

}