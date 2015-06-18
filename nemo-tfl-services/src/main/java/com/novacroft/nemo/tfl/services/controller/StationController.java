package com.novacroft.nemo.tfl.services.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.novacroft.nemo.tfl.services.application_service.StationService;
import com.novacroft.nemo.tfl.services.transfer.Station;

/**
 * Controller to expose station(s) services
 */
@Controller
@RequestMapping(value = "/station")
public class StationController extends BaseServicesController {
    
    @Autowired
    protected StationService stationService;

    @RequestMapping(value = "/allstations", method = RequestMethod.GET, produces = JSON_MEDIA)
    @ResponseBody
    public List<Station> getAllStations() {
        return this.stationService.getStations();
    }
    
    @RequestMapping(value = "/stations", method = RequestMethod.GET, produces = JSON_MEDIA)
    @ResponseBody
    public List<Station> getStations() {
        return this.stationService.getActiveStations();
    }

}
