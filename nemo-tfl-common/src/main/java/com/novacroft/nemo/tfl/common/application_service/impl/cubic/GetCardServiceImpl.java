package com.novacroft.nemo.tfl.common.application_service.impl.cubic;

import static com.novacroft.nemo.common.utils.DateUtil.isBefore;
import static com.novacroft.nemo.common.utils.DateUtil.parse;
import static com.novacroft.nemo.tfl.common.constant.CubicAttribute.JOB_CENTRE_PLUS;
import static com.novacroft.nemo.tfl.common.constant.SystemParameterCode.CUBIC_PASSWORD;
import static com.novacroft.nemo.tfl.common.constant.SystemParameterCode.CUBIC_USERID;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.domain.cubic.HolderDetails;
import com.novacroft.nemo.common.domain.cubic.HotListReasons;
import com.novacroft.nemo.common.domain.cubic.PendingItems;
import com.novacroft.nemo.common.domain.cubic.PrePayTicket;
import com.novacroft.nemo.common.domain.cubic.PrePayTicketDetails;
import com.novacroft.nemo.common.domain.cubic.PrePayTicketSlot;
import com.novacroft.nemo.common.domain.cubic.PrePayValue;
import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.common.transfer.JobCentrePlusDiscountDTO;
import com.novacroft.nemo.common.transfer.PassengerAndDiscountTypeDTO;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.constant.CubicConstant;
import com.novacroft.nemo.tfl.common.constant.PrivateError;
import com.novacroft.nemo.tfl.common.constant.RefundConstants;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.cubic.GetCardRequestDataService;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoRequestV2DTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;

/**
 * Get the oyster card information from the cubic
 */

@Service("getCardService")
public class GetCardServiceImpl extends BaseCubicService implements GetCardService {

    @Autowired
    protected SystemParameterService systemParameterService;
    @Autowired
    protected GetCardRequestDataService getCardRequestDataService;
    @Autowired
    protected CardDataService cardDataService;

    @Override
    public CardInfoResponseV2DTO getCard(String cardNumber) {
        CardInfoResponseV2DTO responseDTO = null;
        if (isNotOrderCardFunctionality(cardNumber)) {
            CardInfoRequestV2DTO requestDTO =
                    new CardInfoRequestV2DTO(cardNumber, this.systemParameterService.getParameterValue(CUBIC_USERID.code()),
                            this.systemParameterService.getParameterValue(CUBIC_PASSWORD.code()));
            responseDTO = this.getCardRequestDataService.getCard(requestDTO);
            responseDTO = this.checkAndPopulateNodesExcludingLeafNodes(responseDTO);
            responseDTO = this.populateDefaultPPVValues(responseDTO);
            if (isErrorResponse(responseDTO)) {
                throw new ApplicationServiceException(responseDTO.getErrorDescription(),
                        String.format(PrivateError.CARD_UPDATE_REQUEST_FAILED.message(), responseDTO.getErrorCode(),
                                responseDTO.getErrorDescription()));
            }
        }
        return responseDTO;
    }

    protected Boolean isNotOrderCardFunctionality(String cardNumber) {
        return cardNumber != null;
    }

    @Override
    public JobCentrePlusDiscountDTO getJobCentrePlusDiscountDetails(String cardNumber) {
        return populateJobCentrePlusDiscountDetails(getCard(cardNumber));
    }

    @Override
    public JobCentrePlusDiscountDTO getJobCentrePlusDiscountDetails(Long cardId) {
        String cardNumber = cardDataService.findById(cardId).getCardNumber();
        return getJobCentrePlusDiscountDetails(cardNumber);
    }

    protected JobCentrePlusDiscountDTO populateJobCentrePlusDiscountDetails(CardInfoResponseV2DTO cardInfoResponseV2DTO) {
        JobCentrePlusDiscountDTO jobCentrePlusDiscount = null;
        if (cardInfoResponseV2DTO != null) {
            jobCentrePlusDiscount = new JobCentrePlusDiscountDTO();
            jobCentrePlusDiscount.setPrestigeId(cardInfoResponseV2DTO.getPrestigeId());
            jobCentrePlusDiscount.setPaygBalance(cardInfoResponseV2DTO.getPpvDetails().getBalance());
            if (isCardProductHasJobCentrePlusDiscount(cardInfoResponseV2DTO.getDiscountEntitlement1())) {
                setDiscountExpiry(jobCentrePlusDiscount, cardInfoResponseV2DTO.getDiscountExpiry1());
            } else if (isCardProductHasJobCentrePlusDiscount(cardInfoResponseV2DTO.getDiscountEntitlement2())) {
                setDiscountExpiry(jobCentrePlusDiscount, cardInfoResponseV2DTO.getDiscountExpiry2());
            } else if (isCardProductHasJobCentrePlusDiscount(cardInfoResponseV2DTO.getDiscountEntitlement3())) {
                setDiscountExpiry(jobCentrePlusDiscount, cardInfoResponseV2DTO.getDiscountExpiry3());
            }
        }
        return jobCentrePlusDiscount;
    }

    protected boolean isCardProductHasJobCentrePlusDiscount(String discountEntitleMent) {
        return discountEntitleMent != null && discountEntitleMent.equals(JOB_CENTRE_PLUS);
    }

    protected void setDiscountExpiry(JobCentrePlusDiscountDTO jobCentrePlusDiscount, String discountExpiry) {
        if (discountExpiry != null) {
            jobCentrePlusDiscount.setDiscountExpiryDate(parse(discountExpiry));
            jobCentrePlusDiscount.setJobCentrePlusDiscountAvailable(true);
        }
    }

    @Override
    public CardInfoResponseV2DTO checkAndPopulateNodesExcludingLeafNodes(CardInfoResponseV2DTO cardInfoResponseV2DTO) {
        if (cardInfoResponseV2DTO.getHolderDetails() == null) {
            cardInfoResponseV2DTO.setHolderDetails(new HolderDetails());
        }
        if (cardInfoResponseV2DTO.getHotListReasons() == null) {
            cardInfoResponseV2DTO.setHotListReasons(new HotListReasons());
        }

        if (cardInfoResponseV2DTO.getHotListReasons().getHotListReasonCodes() == null) {
            cardInfoResponseV2DTO.getHotListReasons().setHotListReasonCodes(new ArrayList<Integer>());
        }
        if (cardInfoResponseV2DTO.getPendingItems() == null) {
            cardInfoResponseV2DTO.setPendingItems(new PendingItems());
        }
        if (cardInfoResponseV2DTO.getPendingItems().getPpts() == null) {
            cardInfoResponseV2DTO.getPendingItems().setPpts(new ArrayList<PrePayTicket>());
        }
        if (cardInfoResponseV2DTO.getPendingItems().getPpvs() == null) {
            cardInfoResponseV2DTO.getPendingItems().setPpvs(new ArrayList<PrePayValue>());
        }
        if (cardInfoResponseV2DTO.getPptDetails() == null) {
            cardInfoResponseV2DTO.setPptDetails(new PrePayTicketDetails());
        }
        if (cardInfoResponseV2DTO.getPptDetails().getPptSlots() == null) {
            cardInfoResponseV2DTO.getPptDetails().setPptSlots(new ArrayList<PrePayTicketSlot>());
        }
        return cardInfoResponseV2DTO;
    }

    public CardInfoResponseV2DTO populateDefaultPPVValues(CardInfoResponseV2DTO cardInfoResponseV2DTO) {
        if (cardInfoResponseV2DTO.getPendingItems().getPpvs() == null) {
            cardInfoResponseV2DTO.getPendingItems().setPpvs(new ArrayList<PrePayValue>());
        }
        if (cardInfoResponseV2DTO.getPpvDetails() == null) {
            cardInfoResponseV2DTO.setPpvDetails(new PrePayValue());
        }
        if (cardInfoResponseV2DTO.getPpvDetails().getBalance() == null) {
            cardInfoResponseV2DTO.getPpvDetails().setBalance(RefundConstants.PREPAY_VALUE_INITIAL_ZERO);
        }
        if (cardInfoResponseV2DTO.getPpvDetails().getCurrency() == null) {
            cardInfoResponseV2DTO.getPpvDetails().setCurrency(CubicConstant.DEFAULT_CURRENCY_VALUE_STERLING);
        }
        return cardInfoResponseV2DTO;
    }
    
	@Override
	public PassengerAndDiscountTypeDTO getPassengerAndDiscountType(String cardNumber, Date effectiveDate) {
		 CardInfoResponseV2DTO responseDTO = getCard(cardNumber);
		 PassengerAndDiscountTypeDTO passengerAndDiscountTypeDTO = new PassengerAndDiscountTypeDTO();
		 if(responseDTO != null){
			 passengerAndDiscountTypeDTO.setPassengerType(StringUtil.isBlank(responseDTO.getPassengerType()) ? CubicConstant.PASSENGER_TYPE_ADULT : responseDTO.getPassengerType());
			 passengerAndDiscountTypeDTO.setDiscountType(findApplicableDiscountType(effectiveDate, responseDTO));
		 }
		return passengerAndDiscountTypeDTO;
	}
	
	 
	protected String findApplicableDiscountType(Date effectiveDate, CardInfoResponseV2DTO responseDTO ) {
		if(StringUtil.isNotBlank(responseDTO.getDiscountEntitlement1()) && StringUtil.isNotBlank(responseDTO.getDiscountExpiry1())){
			Date expiryDate = DateUtil.parse(responseDTO.getDiscountExpiry1());
            if (isBefore(effectiveDate, expiryDate)) {
					return responseDTO.getDiscountEntitlement1();
			}
		}
		if(StringUtil.isNotBlank(responseDTO.getDiscountEntitlement2()) && StringUtil.isNotBlank(responseDTO.getDiscountExpiry2())){
			Date expiryDate = DateUtil.parse(responseDTO.getDiscountExpiry2());
            if (isBefore(effectiveDate, expiryDate)) {
					return responseDTO.getDiscountEntitlement2();
			}
		}
		if(StringUtil.isNotBlank(responseDTO.getDiscountEntitlement3()) && StringUtil.isNotBlank(responseDTO.getDiscountExpiry3())){
			Date expiryDate = DateUtil.parse(responseDTO.getDiscountExpiry3());
            if (isBefore(effectiveDate, expiryDate)) {
					return responseDTO.getDiscountEntitlement3();
			}
		}
		
        return CubicConstant.NO_DISCOUNT_TYPE;
	}
	
    

}
