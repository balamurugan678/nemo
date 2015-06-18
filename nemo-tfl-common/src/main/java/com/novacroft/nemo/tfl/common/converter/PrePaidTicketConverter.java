package com.novacroft.nemo.tfl.common.converter;

import java.util.Date;

import com.novacroft.nemo.tfl.common.transfer.PrePaidTicketDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductDTO;

public interface PrePaidTicketConverter {
    ProductDTO convertToProductDto(PrePaidTicketDTO prePaidTicketDTO, Date effectiveDate);
}