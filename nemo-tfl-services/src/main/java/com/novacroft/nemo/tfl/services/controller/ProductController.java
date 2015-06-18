package com.novacroft.nemo.tfl.services.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.novacroft.nemo.tfl.services.application_service.ProductService;
import com.novacroft.nemo.tfl.services.transfer.Item;
import com.novacroft.nemo.tfl.services.transfer.ListContainer;
import com.novacroft.nemo.tfl.services.transfer.PayAsYouGo;
import com.novacroft.nemo.tfl.services.transfer.PrePaidTicket;

@Controller
@RequestMapping(value = "/product")
public class ProductController extends BaseServicesController {

    @Autowired
    protected ProductService productExternalService;

    @RequestMapping(value = "/referenceData", method = RequestMethod.GET, produces = JSON_MEDIA)
    @ResponseBody
    public List<ListContainer> getReferenceData() {
        return productExternalService.getReferenceData();
    }

    @RequestMapping(value = "/getTravelcard", method = RequestMethod.POST, produces = JSON_MEDIA, headers = "content-type=" + JSON_MEDIA)
    @ResponseBody
    public Item getTravelCard(@RequestBody PrePaidTicket travelCard) {
        return productExternalService.getTravelCard(travelCard);
    }
    
    @RequestMapping(value = "/getPayAsYouGo", method = RequestMethod.POST, consumes = JSON_MEDIA,  produces = JSON_MEDIA)
    @ResponseBody
    public Item getPayAsYouGo(@RequestBody PayAsYouGo payAsYouGo) {
        return productExternalService.getPayAsYouGo(payAsYouGo);
    }
    
    @RequestMapping(value = "/getBusPass", method = RequestMethod.POST, produces = JSON_MEDIA, headers = "content-type=" + JSON_MEDIA)
    @ResponseBody
    public Item getBusPass(@RequestBody PrePaidTicket busPass) {
        return productExternalService.getBusPass(busPass);
    }
}
