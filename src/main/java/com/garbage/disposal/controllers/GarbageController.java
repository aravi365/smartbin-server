package com.garbage.disposal.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.garbage.disposal.model.BinData;
import com.garbage.disposal.model.GarbageBin;
import com.garbage.disposal.model.GarbageCollector;
import com.garbage.disposal.service.GarbageCollectorService;
import com.stormpath.sdk.impl.http.MediaType;

@Controller
@RequestMapping(value = "/garbagecollector")
public class GarbageController {

	@Autowired
	private GarbageCollectorService garbageCollectorService;

	@Autowired
	private ServletContext servletContext;

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String start() {
		return "index";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registration() {
		return "register";
	}

	@RequestMapping(value = "/verify", method = RequestMethod.POST)
	public String verify(@RequestParam("username") String username, @RequestParam("password") String password,
			Model model) {
		// System.out.println("Username : " +username + " Password : "
		// +password);
		if (username.equals("admin") && password.equals("admin")) {
			List<GarbageCollector> garbageCollectors = this.garbageCollectorService.garbageCollectorDAO.findAll();
			model.addAttribute("gc", garbageCollectors);
			return "bins";
		} else {
			return "login";
		}
	}

	// Controller method which handles the registration of a user from android
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerBin(@ModelAttribute GarbageCollector garbageCollector) {
		/*
		 * System.out.println("Inside register"); System.out.println(register);
		 * ObjectMapper objectMapper = new ObjectMapper(); GarbageCollector
		 * garbageCollector = null; try { garbageCollector =
		 * objectMapper.readValue(register, GarbageCollector.class); } catch
		 * (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
		System.out.println(garbageCollector);

		return this.garbageCollectorService.persistUser(garbageCollector);

	}

	// Controller method to validate username and password
	@RequestMapping(value = "/validate", method = RequestMethod.POST)
	@ResponseBody
	public String validateUser(@RequestParam Map<String, String> requestParams) {

		return this.garbageCollectorService.validateUser(requestParams.get("email"), requestParams.get("password"));
	}

	// Controller method to new bin to a registered user
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public String addBin(@RequestParam Map<String, String> requestParams) {

		return this.garbageCollectorService.addBin(requestParams.get("email"), requestParams.get("qrCode"),
				Integer.valueOf(requestParams.get("currentCapacity")), requestParams.get("location"));
	}

	// Controller method to fetch bin locations based on user email
	@RequestMapping(value = "/binlocations", method = RequestMethod.POST)
	@ResponseBody
	public String binLocations(@RequestParam("email") String email) {

		return this.garbageCollectorService.getGarbageBins(email);
	}

	@RequestMapping(value = "/mock", method = RequestMethod.GET)
	@ResponseBody
	public String mock() {
		return this.garbageCollectorService.addBin("jeev@testing.com", "ZYXWV3", 0, "Ernakulam");
	}

	@RequestMapping(value = "/bindata", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<BinData> syncBinData(@RequestParam("email") String email) {

		return this.garbageCollectorService.getBinData(email);
	}

	@RequestMapping(value = "/map", method = RequestMethod.GET)
	@ResponseBody
	public String getMappedLocations(@RequestParam("email") String email) {

		return this.garbageCollectorService.getMapOfNearestBin(email);
	}

	@RequestMapping(value = "/webview", method = RequestMethod.GET)
	public String webView(Model model) {
		List<GarbageCollector> garbageCollectors = this.garbageCollectorService.garbageCollectorDAO.findAll();
		model.addAttribute("gc", garbageCollectors);
		return "bins";
	}

	@RequestMapping(value = "/individualview", method = RequestMethod.POST)
	public String individualWebView(@RequestParam("email") String email, Model model) {
		List<GarbageCollector> garbageCollectors = new ArrayList<>();

		garbageCollectors.add(this.garbageCollectorService.garbageCollectorDAO.findByEmail(email));
		model.addAttribute("gc", garbageCollectors);
		return "bins";
	}

	@RequestMapping(value = "/monitor", method = RequestMethod.GET)
	@ResponseBody
	public String monitorGarbageCollectors(@RequestParam("email") String email) {
		System.out.println(email);
		GarbageCollector garbageCollector = this.garbageCollectorService.garbageCollectorDAO.findByEmail(email);
		StringBuilder monitoredLog = new StringBuilder(" ");
		for (GarbageBin garbageBin : garbageCollector.getGarbageBins()) {
			String record = (String) this.servletContext.getAttribute(garbageBin.getLocation());
			if(record!=null)
			{
			if (record.length() > 5) {
				monitoredLog.append(record + " \n");
			}
			}
		}
		System.err.println("Monitored : " +monitoredLog.toString());
		return monitoredLog.toString().length() < 5 ? "No irregularities detected " : monitoredLog.toString();
	}

	@RequestMapping(value = "/git/https", method = RequestMethod.GET)
	@ResponseBody
	public String getGitURL() {
		return "https://github.com/lijoibs/testing.git";
	}

}
