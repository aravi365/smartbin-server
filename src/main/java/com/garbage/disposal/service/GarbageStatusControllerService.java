package com.garbage.disposal.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.garbage.disposal.datalayer.BinRecordsDAO;
import com.garbage.disposal.datalayer.GarbageStatusDAO;
import com.garbage.disposal.model.BinRecords;
import com.garbage.disposal.model.GarbageBin;

@Service
public class GarbageStatusControllerService {

	@Autowired
	private GarbageStatusDAO garbageStatusDAO;

	@Autowired
	private BinRecordsDAO binRecordsDAO;
	
	@Autowired
	private ServletContext servletContext;

	public String getBinStatus(Integer id) {
		return "Current Capacity : " + this.garbageStatusDAO.findOne(id).getCurrentCapacity();
	}

	// Service method to set bin status using bin id
	public String setBinStatus(Integer binID, Integer currentCapacity) {
		if (currentCapacity < 0 || currentCapacity > 100)
			return "Invalid status updation detected, blocked the request for security reasons";
		GarbageBin currentBin = this.garbageStatusDAO.findOne(binID);
		List<BinRecords> binRecords = this.binRecordsDAO.findTop2ByLocationOrderByCleanedDateDesc(currentBin.getLocation());
		System.err.println("Last record : " +this.binRecordsDAO.findTopByLocationOrderByCleanedDateDesc(currentBin.getLocation()));
		if(binRecords.size()>1)
		{
		if(currentCapacity<binRecords.get(0).getPreviousStatus())
		{
			this.servletContext.setAttribute(currentBin.getLocation()," ");;
		}else
		{
			this.servletContext.setAttribute(currentBin.getLocation(), "Bin located at " +currentBin.getLocation() + " has not been collected ");
		}
		}
		currentBin.setCurrentCapacity(currentCapacity);
		GarbageBin updatedBin = this.garbageStatusDAO.save(currentBin);
		if (updatedBin.getCurrentCapacity().equals(currentCapacity)) {

			if (updatedBin.getCurrentCapacity() >= 85) {
				//sentSMS(updatedBin.getLocation());
			}
			return "Status updation successful";
		} else {
			return "Status updation failed";
		}

	}

	// Service method to clear selected bin based on bin id
	public String clearBin(Integer id) {
		GarbageBin garbageBin = this.garbageStatusDAO.findOne(id);
		int previousStatus = garbageBin.getCurrentCapacity();
		if (garbageBin.getCurrentCapacity() >= 0 || garbageBin.getCurrentCapacity() <= 100) {
			garbageBin.setCurrentCapacity(0);
			GarbageBin updatedBin = this.garbageStatusDAO.save(garbageBin);
			if (updatedBin.getCurrentCapacity() == 0) {
				BinRecords binRecords = binRecordsDAO.save(new BinRecords(garbageBin.getQrCode(),
						garbageBin.getLocation(), LocalDate.now(), previousStatus));
				if (binRecords.getRecordID() != null) {
					return "Status updation successful";
				} else {
					return "Unable to update status";
				}
			} else {
				return "Status updation rejected";
			}

		} else {
			return "Status updation failed, please try again";
		}

	}

	public void sentSMS(String location) {
		// "Please collect bin located at " +location;

		// Replace with your username
		String user = "shebingm";

		// Replace with your API KEY (We have sent API KEY on activation email,
		// also available on panel)
		String apikey = "5Q9DUq4QL5zrw4yBFyya";

		// Replace with the destination mobile Number to which you want to send
		// sms
		String mobile = "8281888143";

		// Replace if you have your own Sender ID, else donot change
		String senderid = "MYTEXT";

		int a = ThreadLocalRandom.current().nextInt(99999, 1000000);
		System.out.println(a);

		// Replace with your Message content
		String message = "SmartBin alert : ,\n" + " Bin installed at " + location
				+ " is nearing its limit. Please collect it at the earliest \n Thank You.";

		// For Plain Text, use "txt" ; for Unicode symbols or regional Languages
		// like hindi/tamil/kannada use "uni"
		String type = "txt";

		// Prepare Url
		URLConnection myURLConnection = null;
		URL myURL = null;
		BufferedReader reader = null;

		// encoding message
		String encoded_message = URLEncoder.encode(message);

		// Send SMS API
		String mainUrl = "http://smshorizon.co.in/api/sendsms.php?";

		// Prepare parameter string
		StringBuilder sbPostData = new StringBuilder(mainUrl);
		sbPostData.append("user=" + user);
		sbPostData.append("&apikey=" + apikey);
		sbPostData.append("&message=" + encoded_message);
		sbPostData.append("&mobile=" + mobile);
		sbPostData.append("&senderid=" + senderid);
		sbPostData.append("&type=" + type);

		// final string
		mainUrl = sbPostData.toString();
		try {
			// prepare connection
			myURL = new URL(mainUrl);
			myURLConnection = myURL.openConnection();
			myURLConnection.connect();
			reader = new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));
			// reading response
			String response;
			while ((response = reader.readLine()) != null)
				// print response
				System.out.println(response);

			// finally close connection
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
