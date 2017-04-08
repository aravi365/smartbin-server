package com.garbage.disposal.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.garbage.disposal.datalayer.BinRecordsDAO;
import com.garbage.disposal.datalayer.GarbageCollectorDAO;
import com.garbage.disposal.model.BinData;
import com.garbage.disposal.model.BinRecords;
import com.garbage.disposal.model.GarbageBin;
import com.garbage.disposal.model.GarbageCollector;
import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.application.Applications;
import com.stormpath.sdk.authc.AuthenticationRequest;
import com.stormpath.sdk.authc.AuthenticationResult;
import com.stormpath.sdk.authc.UsernamePasswordRequests;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.resource.ResourceException;

@Service
public class GarbageCollectorService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	public GarbageCollectorDAO garbageCollectorDAO;

	@Autowired
	private BinRecordsDAO binRecordsDAO;

	@Autowired
	private ServletContext servletContext;

	// Service method to persist a GarbageBin Object
	public String persistUser(GarbageCollector garbageCollector) {

		if (garbageCollector != null) {
			logger.info("Bin details persisted successfully in h2 database ");
			new Thread(() -> {
				logger.info(
						"Invoking a new thread to externalize the process of storing the account details to stormpath ");
				Client client = (Client) this.servletContext.getAttribute("stormpath");
				// Storing the account credentials in stormpath
				Application application = client
						.getApplications(Applications.where(Applications.name().eqIgnoreCase("SmartBin"))).single();

				// Create the account object
				Account account = client.instantiate(Account.class);

				// Set the account properties
				account.setGivenName(garbageCollector.getFirstName());
				account.setSurname(garbageCollector.getLastName());
				// username is optional, defaults to email if unset
				account.setEmail(garbageCollector.getEmail());
				account.setPassword(new String(garbageCollector.getGarbageSecurity().getPassword()));

				// Create the account using the existing Application object
				account = application.createAccount(account);
				// System.err.println(account.getEmailVerificationStatus());
				logger.info("Bin details persisted successfully in stormpath ");
			}).start();
		}
		// System.out.println("Inside service mdthod");
		if (this.garbageCollectorDAO.save(garbageCollector) != null)
			return "login";
		return "Unable to register now! Please try again later";

	}

	// Service method to validate the user
	public String validateUser(String email, String password) {
		GarbageCollector garbageCollector = this.garbageCollectorDAO.findByEmail(email);

		if (garbageCollector != null) {

			Client client = (Client) this.servletContext.getAttribute("stormpath");

			Application application = client
					.getApplications(Applications.where(Applications.name().eqIgnoreCase("SmartBin"))).single();

			// Create an authentication request using the credentials
			AuthenticationRequest request = UsernamePasswordRequests.builder().setUsernameOrEmail(email)
					.setPassword(new String(garbageCollector.getGarbageSecurity().getPassword())).build();

			// Now let's authenticate the account with the application:
			try {

				AuthenticationResult result = application.authenticateAccount(request);
				Account account = result.getAccount();
				System.err.println("Verification Status " + account.getEmailVerificationStatus());
				if (account.getEmailVerificationStatus().toString().equals("VERIFIED")) {

					logger.info("verified account");

					List<String> locationList = (List<String>) this.servletContext.getAttribute("locationList");
					StringBuilder locations = new StringBuilder();
					locationList.forEach((a) -> locations.append(a + " "));

					return "VERIFIED " + account.getEmail() + " " + locations.toString();

				}
			} catch (ResourceException ex) {
				System.err.println("Validation Error");
				logger.error(ex.getMessage());
				return "Your account is not verified, Please verify your account before signing in";
			}

			Map<String, String> binLocations = new LinkedHashMap<>();
			for (GarbageBin garbageBin : garbageCollector.getGarbageBins()) {
				binLocations.put(garbageBin.getQrCode(), garbageBin.getLocation());
			}
			if (BCrypt.checkpw(password, new String(garbageCollector.getGarbageSecurity().getPassword()))) {

			} else {
				return "Invalid Security Credentials";
			}
		} else {
			return "Invalid username";
		}
		return null;
	}

	public GarbageCollector findUser(Integer id) {
		return this.garbageCollectorDAO.findOne(id);
	}

	// Service method to fetch the registered garbage bins of a specific user
	public String getGarbageBins(String email) {
		GarbageCollector garbageCollector = this.garbageCollectorDAO.findByEmail(email);
		if (garbageCollector != null) {

			Map<String, String> binLocations = new LinkedHashMap<>();
			for (GarbageBin garbageBin : garbageCollector.getGarbageBins()) {
				binLocations.put(garbageBin.getQrID().toString(), garbageBin.getLocation());
			}

			String garbageBinData = null;
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				garbageBinData = objectMapper.writeValueAsString(binLocations);
			} catch (JsonProcessingException e) {
				System.err.println("Execption occured while converting garbage bin map to json string");
				e.printStackTrace();
			}

			if (garbageBinData != null)
				return garbageBinData;
			return "No location data found";
		} else {
			return "No bins registered";
		}

	}

	// Service method to add a new bin to a registered user
	public String addBin(String email, String qrCode, Integer currentCapacity, String location) {
		GarbageCollector garbageCollector = this.garbageCollectorDAO.findByEmail(email);

		int registeredBins = garbageCollector.getGarbageBins().size();
		if (registeredBins > 4) {
			return "Your bin registration limit has been exhaused, please contact the administrator";
		}

		for (GarbageBin garbageBin : garbageCollector.getGarbageBins()) {
			if (garbageBin.getLocation().equalsIgnoreCase(location))
				return "A bin has been already installed at this location, please select another location";
		}

		GarbageBin garbageBin = new GarbageBin();
		garbageBin.setQrCode(qrCode);
		garbageBin.setCurrentCapacity(currentCapacity);
		garbageBin.setLocation(location);
		garbageCollector.getGarbageBins().add(garbageBin);
		GarbageCollector updatedGarbageCollector = this.garbageCollectorDAO.save(garbageCollector);
		if (updatedGarbageCollector.getGarbageBins().size() > registeredBins)
			return "Bin registered successfully";
		return "Unable to register new bin at this point of time. Please try again later";

	}

	public List<BinData> getBinData(String email) {
		GarbageCollector garbageCollector = this.garbageCollectorDAO.findByEmail(email);
		List<BinData> binData = new ArrayList<>();

		for (GarbageBin gb : garbageCollector.getGarbageBins()) {
			BinRecords binRecords = this.binRecordsDAO.findTopByLocationOrderByCleanedDateDesc(gb.getLocation());
			binData.add(new BinData(gb.getLocation(), String.valueOf(gb.getCurrentCapacity()),
					binRecords.getCleanedDate().toString(), garbageCollector.getFirstName()));
		}

		return binData;
	}

	public String getMapOfNearestBin(String email) {
		GarbageCollector garbageCollector = this.garbageCollectorDAO.findByEmail(email);
System.out.println(garbageCollector);
		if (garbageCollector == null)
			return "invalid request";
		boolean analyzer = false;
		StringBuilder result = new StringBuilder();
		for (GarbageBin garbageBin : garbageCollector.getGarbageBins()) {

			if (!analyzer) {
				if (garbageBin.getCurrentCapacity() >= 85) {
					Map<String, String> nearestLocation = (Map<String, String>) servletContext
							.getAttribute("nearestLocationFinder");
					result.append(garbageBin.getLocation() + " " + garbageBin.getCurrentCapacity() + " "
							+ nearestLocation.get(garbageBin.getLocation()) + " ");
					analyzer = true;
				}

			} else {
				return result.append(garbageBin.getCurrentCapacity()).toString();
			}
		}
		return "All bins are below the required limit";
	}

}
