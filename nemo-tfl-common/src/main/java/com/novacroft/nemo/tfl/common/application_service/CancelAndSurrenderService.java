package com.novacroft.nemo.tfl.common.application_service;

import java.util.List;

import org.joda.time.DateTime;

import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.tfl.common.command.CancelAndSurrenderCmd;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.domain.Refund;
import com.novacroft.nemo.tfl.common.transfer.ProductDTO;

public interface CancelAndSurrenderService {

    Refund processCancelOrSurrenderRefund(CancelAndSurrenderCmd cmd);

    ProductDTO findProduct(Integer startZone, Integer endZone, DateTime startDate, DateTime endDate);
    
    SelectListDTO getBackdatedRefundTypes();

    void setTravelcardTypeByFormTravelCardType(CartItemCmdImpl cmd);

    void setTicketType(CartItemCmdImpl cmd);

    List<CartItemCmdImpl> processCancelOrSurrenderRefund(CartCmdImpl cmd);

}
