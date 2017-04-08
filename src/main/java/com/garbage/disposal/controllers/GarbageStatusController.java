package com.garbage.disposal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.garbage.disposal.model.GarbageCollector;
import com.garbage.disposal.service.GarbageStatusControllerService;

@Controller
@RequestMapping(value = "/garbagecollector/status")
public class GarbageStatusController {

	@Autowired
	private GarbageStatusControllerService garbageStatusControllerService;

	// Fetching current bin status

	@RequestMapping(value = "/current/fetch", method = RequestMethod.GET)
	@ResponseBody
	public String getBinStatus(@RequestParam("binID") Integer binID) {

		return this.garbageStatusControllerService.getBinStatus(binID);
	}

	// Updating the current capacity of a bin

	@RequestMapping(value = "/current/update", method = RequestMethod.GET)
	@ResponseBody
	public String setBinStatus(@RequestParam("binID") Integer binID,
			@RequestParam("currentCapacity") Integer currentCapacity) {

		return this.garbageStatusControllerService.setBinStatus(binID, currentCapacity);
	}

	// Clear bin based on id
	@RequestMapping(value = "/clear", method = RequestMethod.POST)
	@ResponseBody
	public String clearBin(@RequestParam("binID") Integer binID) {

		return this.garbageStatusControllerService.clearBin(binID);
	}
	
	
	
}
