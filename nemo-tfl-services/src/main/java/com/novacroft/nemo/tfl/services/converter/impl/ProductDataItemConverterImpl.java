package com.novacroft.nemo.tfl.services.converter.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.transfer.ProductDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;
import com.novacroft.nemo.tfl.services.converter.ProductDataItemConverter;
import com.novacroft.nemo.tfl.services.transfer.Error;
import com.novacroft.nemo.tfl.services.transfer.ErrorResult;
import com.novacroft.nemo.tfl.services.transfer.Item;
import com.novacroft.nemo.tfl.services.transfer.PrePaidTicket;

@Service("productDataItemConverter")
public class ProductDataItemConverterImpl implements ProductDataItemConverter {

    @Override
    public PrePaidTicket convertToTravelCard(ProductDTO productDTO) {
        return new PrePaidTicket(productDTO.getDuration(), productDTO.getStartZone().toString(), productDTO.getEndZone().toString());
    }

    @Override
    public Item convertToItem(ProductDTO productDTO) {
        return new Item(productDTO.getStartZone(), productDTO.getEndZone(), productDTO.getDuration());
    }

    @Override
    public ProductDTO convertToProductDTO(PrePaidTicket prePaidTicket) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setDuration(prePaidTicket.getDuration());
        productDTO.setStartZone(Integer.parseInt(prePaidTicket.getStartZone()));
        productDTO.setEndZone(Integer.parseInt(prePaidTicket.getEndZone()));
        return productDTO;
    }

    @Override
    public ErrorResult convertToErrorResult(Errors errors) {
        List<ObjectError> allErrors = errors.hasErrors() ? errors.getAllErrors() : null;
        ErrorResult errorResult = null;
        if (allErrors != null && allErrors.size() > 0) {
            errorResult = new ErrorResult();
            List<Error> errorList = new ArrayList<>();
            Error error;
            for (ObjectError objectError : allErrors) {
                error = new Error();
                error.setDescription(objectError.toString());
                error.setField(objectError.getCode());
                errorList.add(error);
            }
            errorResult.setErrors(errorList);
        }
        return errorResult;
    }

    @Override
    public ProductItemDTO convertToProductItemDTO(PrePaidTicket prePaidTicket) {
        return new ProductItemDTO(DateUtil.parse(prePaidTicket.getStartDate()), DateUtil.parse(prePaidTicket.getEndDate()),
                        prePaidTicket.getDuration(), Integer.parseInt(prePaidTicket.getStartZone()), Integer.parseInt(prePaidTicket.getEndZone()),
                        prePaidTicket.getEmailReminder());
    }

    @Override
    public Item convertToItem(ProductDTO productDTO, ProductItemDTO productItemDTO) {
        Item item = new Item();
        if (productDTO != null) {
            item = new Item(productDTO.getStartZone(), productDTO.getEndZone(), productDTO.getDuration());
            item.setName(productDTO.getProductName());
            item.setPrice(productDTO.getTicketPrice());
            item.setPrePaidProductReference(productDTO.getExternalId());
        }
        item.setStartDate(productItemDTO.getStartDate());
        item.setFormattedStartDate(DateUtil.formatDate(productItemDTO.getStartDate()));
        item.setEndDate(productItemDTO.getEndDate());
        if (item.getEndDate() != null) {
            item.setFormattedEndDate(DateUtil.formatDate(item.getEndDate()));
        }
        item.setReminderDate(productItemDTO.getReminderDate());
        item.setProductType(productItemDTO.getProductType());
        return item;
    }

}
