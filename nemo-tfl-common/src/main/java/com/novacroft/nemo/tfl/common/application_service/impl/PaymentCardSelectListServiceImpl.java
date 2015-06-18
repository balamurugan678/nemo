package com.novacroft.nemo.tfl.common.application_service.impl;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.application_service.impl.BaseService;
import com.novacroft.nemo.common.constant.DateConstant;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.common.transfer.SelectListOptionDTO;
import com.novacroft.nemo.tfl.common.application_service.PaymentCardSelectListService;
import com.novacroft.nemo.tfl.common.constant.AddPaymentCardAction;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.constant.PageSelectList;
import com.novacroft.nemo.tfl.common.constant.SystemParameterCode;
import com.novacroft.nemo.tfl.common.data_service.PaymentCardDataService;
import com.novacroft.nemo.tfl.common.transfer.PaymentCardDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.novacroft.nemo.tfl.common.util.PaymentCardUtil.createDisplayName;
import static com.novacroft.nemo.tfl.common.util.PaymentCardUtil.expiresInNMonths;

/**
 * Get payment card select list service
 */
@Service("paymentCardSelectListService")
public class PaymentCardSelectListServiceImpl extends BaseService implements PaymentCardSelectListService {
    @Autowired
    protected PaymentCardDataService paymentCardDataService;
    @Autowired
    protected SelectListService selectListService;
    @Autowired
    protected SystemParameterService systemParameterService;

    @Override
    public SelectListDTO getPaymentCardSelectList(Long customerId) {
        List<PaymentCardDTO> paymentCardDtoList = this.paymentCardDataService.findByCustomerId(customerId);
        SelectListDTO selectListDTO = new SelectListDTO();
        selectListDTO.setName(PageSelectList.PAYMENT_CARDS);
        selectListDTO.setOptions(new ArrayList<SelectListOptionDTO>());
        for (PaymentCardDTO paymentCardDTO : paymentCardDtoList) {
            if (!expiresInNMonths(DateConstant.MONTHS_LIMIT_FOR_REGULAR_PAYMENT, paymentCardDTO)) {
                selectListDTO.getOptions().add(new SelectListOptionDTO(paymentCardDTO.getId().toString(),
                        getPaymentCardDisplayName(paymentCardDTO)));
            }
        }
        return selectListDTO;
    }

    @Override
    public SelectListDTO getPaymentCardSelectListForAdHocLoad(Long customerId) {
    	List<PaymentCardDTO> paymentCardDtoList = paymentCardDataService.findByCustomerId(customerId);
    	SelectListDTO selectListDTO = new SelectListDTO();
    	selectListDTO.setName(PageSelectList.PAYMENT_CARDS);
    	selectListDTO.setOptions(new ArrayList<SelectListOptionDTO>());
    	for (PaymentCardDTO paymentCardDTO : paymentCardDtoList) {
    		if (!expiresInNMonths(DateConstant.MONTHS_LIMIT_FOR_ATU_PAYMENT, paymentCardDTO)) {
    			selectListDTO.getOptions().add(new SelectListOptionDTO(paymentCardDTO.getId().toString(),
    					getPaymentCardDisplayName(paymentCardDTO)));
    		}
    	}
    	return selectListDTO;
    }
    
    @Override
    public SelectListDTO getPaymentCardSelectListForAdHocLoadWithAllOptions(Long customerId) {
    	SelectListDTO selectListDTO = getPaymentCardSelectListForAdHocLoad(customerId);
        selectListDTO.getOptions()
		        .addAll(selectListService.getSelectList(PageSelectList.ADD_PAYMENT_CARD_ACTIONS).getOptions());
		updateOptionMeaning(selectListDTO, AddPaymentCardAction.ADD.code(),
		        getContent(ContentCode.ADD_PAYMENT_CARD.optionCode()));
		updateOptionMeaning(selectListDTO, AddPaymentCardAction.ADD_AND_SAVE.code(),
		        getContent(ContentCode.ADD_AND_SAVE_PAYMENT_CARD.optionCode()));
		return removeSaveOptionIfAtMaximumNumberOfSavedCards(selectListDTO);
    }

    @Override
    public SelectListDTO getPaymentCardSelectListWithAllOptions(Long customerId) {
        SelectListDTO selectListDTO = getPaymentCardSelectList(customerId);
        selectListDTO.getOptions()
                .addAll(this.selectListService.getSelectList(PageSelectList.ADD_PAYMENT_CARD_ACTIONS).getOptions());
        updateOptionMeaning(selectListDTO, AddPaymentCardAction.ADD.code(),
                getContent(ContentCode.ADD_PAYMENT_CARD.optionCode()));
        updateOptionMeaning(selectListDTO, AddPaymentCardAction.ADD_AND_SAVE.code(),
                getContent(ContentCode.ADD_AND_SAVE_PAYMENT_CARD.optionCode()));
        return removeSaveOptionIfAtMaximumNumberOfSavedCards(selectListDTO);
    }

    @Override
    public SelectListDTO getPaymentCardSelectListWithOnlySaveOption(Long customerId) {
        SelectListDTO selectListDTO = getPaymentCardSelectListWithAllOptions(customerId);
        selectListDTO.getOptions().remove(new SelectListOptionDTO(AddPaymentCardAction.ADD.code()));
        return removeSaveOptionIfAtMaximumNumberOfSavedCards(selectListDTO);
    }

    protected void updateOptionMeaning(SelectListDTO selectListDTO, String value, String meaning) {
        for (SelectListOptionDTO selectListOptionDTO : selectListDTO.getOptions()) {
            if (value.equalsIgnoreCase(selectListOptionDTO.getValue())) {
                selectListOptionDTO.setMeaning(meaning);
            }
        }
    }

    protected String getPaymentCardDisplayName(PaymentCardDTO paymentCardDTO) {
        return createDisplayName(paymentCardDTO);
    }

    protected SelectListDTO removeSaveOptionIfAtMaximumNumberOfSavedCards(SelectListDTO selectListDTO) {
        if (isAtMaximumNumberOfSavedCards(selectListDTO)) {
            selectListDTO.getOptions().remove(new SelectListOptionDTO(AddPaymentCardAction.ADD_AND_SAVE.code()));
        }
        return selectListDTO;
    }

    protected boolean isAtMaximumNumberOfSavedCards(SelectListDTO selectListDTO) {
        int maximumNumberOfSavedCards =
                this.systemParameterService.getIntegerParameterValue(SystemParameterCode.SAVED_PAYMENT_CARD_MAX_NUMBER.code());
        int numberOfSavedCards = 0;
        for (SelectListOptionDTO selectListOptionDTO : selectListDTO.getOptions()) {
            if (isOptionASavedCard(selectListOptionDTO)) {
                numberOfSavedCards++;
            }
        }
        return numberOfSavedCards >= maximumNumberOfSavedCards;
    }

    protected boolean isOptionASavedCard(SelectListOptionDTO selectListOptionDTO) {
        for (AddPaymentCardAction addPaymentCardAction : AddPaymentCardAction.values()) {
            if (addPaymentCardAction.code().equals(selectListOptionDTO.getValue())) {
                return false;
            }
        }
        return true;
    }
}
