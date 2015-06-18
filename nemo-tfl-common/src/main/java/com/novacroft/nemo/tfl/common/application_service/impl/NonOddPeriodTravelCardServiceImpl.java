package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.common.utils.DateUtil.formatDate;
import static com.novacroft.nemo.common.utils.DateUtil.parse;
import static com.novacroft.nemo.common.utils.TravelCardDurationUtil.getEndDate;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.DAYS_BEFORE_EXPIRY;
import static com.novacroft.nemo.tfl.common.constant.CubicConstant.JCP_DISCOUNT_TYPE_CODE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.tfl.common.application_service.AddUnlistedProductService;
import com.novacroft.nemo.tfl.common.application_service.NonOddPeriodTravelCardService;
import com.novacroft.nemo.tfl.common.application_service.ProductService;
import com.novacroft.nemo.tfl.common.application_service.RefundCalculationBasisService;
import com.novacroft.nemo.tfl.common.application_service.TradedTravelCardService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.JobCentrePlusInvestigationDataService;
import com.novacroft.nemo.tfl.common.data_service.ZoneIdDescriptionDataService;
import com.novacroft.nemo.tfl.common.transfer.ProductDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;

@Service("nonOddPeriodTravelCardService")
public class NonOddPeriodTravelCardServiceImpl implements NonOddPeriodTravelCardService {
    static final Logger logger = LoggerFactory.getLogger(NonOddPeriodTravelCardServiceImpl.class);

    @Autowired
    protected CardDataService cardDataService;

    @Autowired
    protected SystemParameterService systemParameterService;

    @Autowired
    protected ProductService productService;

    @Autowired
    protected RefundCalculationBasisService refundCalculationBasisService;

    @Autowired
    protected ZoneIdDescriptionDataService zoneIdDescriptionDataService;

    @Autowired
    protected GetCardService getCardService;

    @Autowired
    protected JobCentrePlusInvestigationDataService jobCentrePlusInvestigationDataService;

    @Autowired
    protected JobCentrePlusTravelCardServiceImpl jobCentrePlusTravelCardService;

    @Autowired
    protected AddUnlistedProductService addUnlistedProductService;
    
    @Autowired
    protected TradedTravelCardService tradedTravelCardService;

    @Override
    public ProductItemDTO convertCartItemCmdImplToProductItemDTO(CartItemCmdImpl cmd) {
        ProductItemDTO productItemDTO = getProductItemDTOForTicket(cmd);
        if (isTradedTicketAvailable(cmd)) {
            return tradedTravelCardService.getTravelCardItemForTradedTicket(cmd, productItemDTO);
        }
        return productItemDTO;
    }

    protected ProductItemDTO getProductItemDTOForTicket(CartItemCmdImpl cmd) {
        ProductItemDTO productItem = null;

        ProductDTO product = productService.getProductByFromDurationAndZonesAndPassengerTypeAndDiscountType(cmd);
        if (product != null) {
            updateEndDateInCartItemCmdImpl(cmd);
            if(!JCP_DISCOUNT_TYPE_CODE.equals(cmd.getDiscountType())){
                product.setTicketPrice(getUpdatedTicketPriceForJobCentrePlusDiscount(cmd, product));
            }
            productItem = getNewProductItem(cmd, product.getTicketPrice(), product);
        }
        return productItem;
    }

    protected void updateEndDateInCartItemCmdImpl(CartItemCmdImpl cmd) {
        cmd.setEndDate(getEndDateFromStartDateAndTravelCardType(cmd.getStartDate(), cmd.getTravelCardType()));
    }

    protected String getEndDateFromStartDateAndTravelCardType(String startDate, String travelCardType) {
        return formatDate(getEndDate(startDate, getTravelCardTypeWithoutTravelCardSuffix(travelCardType)));
    }

    protected Integer getUpdatedTicketPriceForJobCentrePlusDiscount(CartItemCmdImpl cmd, ProductDTO product) {
        return jobCentrePlusTravelCardService.checkJobCentrePlusDiscountAndUpdateTicketPrice(cmd, product.getTicketPrice());
    }

    protected ProductItemDTO getNewProductItem(CartItemCmdImpl cmd, Integer ticketPrice, ProductDTO product) {
        ProductItemDTO productItemDTO = new ProductItemDTO(cmd.getId(), cmd.getCardId(), ticketPrice, product.getId(), parse(cmd.getStartDate()),
                parse(cmd.getEndDate()), Integer.valueOf(cmd.getStartZone()), Integer.valueOf(cmd.getEndZone()),
                cmd.getEmailReminder() + DAYS_BEFORE_EXPIRY, refundCalculationBasisService
                .getRefundCalculationBasis(cmd.getRefundCalculationBasis(), cmd.getEndDate(), cmd.getRefundType(),
                        cmd.getCardId(),cmd.getTravelCardType() , cmd.getStartDate(), cmd.getStartZone(), cmd.getEndZone(),
                        cmd.getDeceasedCustomer()), cmd.getMagneticTicketNumber(), cmd.getTicketUnused(), cmd.getBackdated(),
                cmd.getBackdatedRefundReasonId(), cmd.getDeceasedCustomer(), null, cmd.getDateOfRefund(), cmd.getRefundType(), cmd.getDateOfCanceAndSurrender(), product.getType());
        return productItemDTO;
    }

    protected String getTravelCardTypeWithoutTravelCardSuffix(String travelCardType) {
        return travelCardType.replaceAll(" Travelcard", "");
    }

    protected boolean isTradedTicketAvailable(CartItemCmdImpl cmd) {
        return cmd.getTradedTicket() != null && cmd.getTradedTicket().getStartZone() != null &&
                cmd.getTradedTicket().getStartZone() > 0;
    }
}
