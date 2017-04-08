package com.garbage.disposal.model;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.springframework.security.crypto.bcrypt.BCrypt;

@Entity(name = "Garbage_Security")
public class GarbageSecurity {

	@Id
	@GeneratedValue
	@Column(name = "security_id")
	private Integer securityID;

	@Column(name = "password")
	private char[] password;

	@OneToOne(mappedBy = "garbageSecurity")
	private GarbageCollector garbageCollector;

	public GarbageSecurity() {

	}

	public GarbageSecurity(Integer securityID, char[] password, GarbageCollector garbageCollector) {
		super();
		this.securityID = securityID;
		this.password = password;
		this.garbageCollector = garbageCollector;
	}

	public Integer getSecurityID() {
		return securityID;
	}

	public void setSecurityID(Integer securityID) {
		this.securityID = securityID;
	}

	public char[] getPassword() {
		return password;
	}

	public void setPassword(char[] password) {
		this.password = BCrypt.hashpw(new String(password), BCrypt.gensalt(12)).toCharArray();
	}

	public GarbageCollector getGarbageCollector() {
		return garbageCollector;
	}

	public void setGarbageCollector(GarbageCollector garbageCollector) {
		this.garbageCollector = garbageCollector;
	}

	/*@Override
	public String toString() {
		return "GarbageSecurity [securityID=" + securityID + ", password=" + Arrays.toString(password)
				+ ", garbageCollector=" + garbageCollector + "]";
	}*/


	
	

}
