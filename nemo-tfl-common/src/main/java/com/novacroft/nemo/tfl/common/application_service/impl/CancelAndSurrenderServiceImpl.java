package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.common.constant.DateConstant.SHORT_DATE_PATTERN;
import static com.novacroft.nemo.tfl.common.constant.PageAttribute.BACKDATED_REFUND_TYPES;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.constant.Durations;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.common.transfer.SelectListOptionDTO;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.application_service.CancelAndSurrenderService;
import com.novacroft.nemo.tfl.common.application_service.RefundCalculationBasisService;
import com.novacroft.nemo.tfl.common.command.CancelAndSurrenderCmd;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CubicConstant;
import com.novacroft.nemo.tfl.common.constant.ProductItemType;
import com.novacroft.nemo.tfl.common.constant.RefundCalculationBasis;
import com.novacroft.nemo.tfl.common.constant.TicketType;
import com.novacroft.nemo.tfl.common.data_service.BackdatedRefundReasonDataService;
import com.novacroft.nemo.tfl.common.data_service.ProductDataService;
import com.novacroft.nemo.tfl.common.data_service.RefundEngineService;
import com.novacroft.nemo.tfl.common.domain.Refund;
import com.novacroft.nemo.tfl.common.transfer.BackdatedRefundReasonDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductDTO;

/**
 * Application service for the Cancel And Surrender ticket controller
 */
@Service("cancelAndSurrenderService")
public class CancelAndSurrenderServiceImpl implements CancelAndSurrenderService {

    private static final String BUS_PASS_FLAG = "Bus Pass";
    private static final String TRAVEL_CARD_REG_EX = " TravelCard$";
    private static final String BUS_PASS_REG_EX = " Bus Pass$";
    private static final String TRAVEL_CARD = " TravelCard";
    private static final String BUS_PASS = " Bus Pass";
    private static final String BLANK = "";
    private static final int TRAVEL_CARD_MIN_LENGTH = 9;
    protected static final String PAY_AS_YOU_GO_CREDIT_ITEM = "Pay as you go credit";
    protected static final String ADMINISTRATION_FEE_CANCEL_AND_SURRENDER_REFUND_ITEM =
            "administrationFeeCancelAndSurrenderRefund";
    protected DateTimeFormatter dateFormat = DateTimeFormat.forPattern(SHORT_DATE_PATTERN);

    @Autowired
    protected RefundEngineService refundEngineService;

    @Autowired
    protected RefundCalculationBasisService refundCalculationBasisService;

    @Autowired
    protected ProductDataService productDataService;

    @Autowired
    protected BackdatedRefundReasonDataService backdatedRefundReasonDataService;

    @Override
    public Refund processCancelOrSurrenderRefund(CancelAndSurrenderCmd cmd) {

        ProductDTO product =
                findProduct(Integer.valueOf(cmd.getRefundTicketStartZone()), Integer.valueOf(cmd.getRefundTicketEndZone()),
                        cmd.getTicketStartDate(), cmd.getTicketEndDate());
        

        if (cmd.getDeceasedCustomer()) {
            /**
             * Selection will result in following modifications to the standard refund calculations: Calculation basis: Pro Rata. Admin Fee: £0 (TBC)
             * Nominate payee by default. User notified that supporting documentation should be present in SAP/CRM before continuing
             */
            return refundEngineService
                    .calculateRefund(cmd.getTicketStartDate(), cmd.getTicketEndDate(), product.getId(), cmd.getRefundDate(),
                            RefundCalculationBasis.PRO_RATA);
        } else {
            return refundEngineService
                    .calculateRefund(cmd.getTicketStartDate(), cmd.getTicketEndDate(), product.getId(), cmd.getRefundDate(),
                            RefundCalculationBasis.ORDINARY);
        }

    }

    @Override
    public ProductDTO findProduct(Integer startZone, Integer endZone, DateTime startDate, DateTime endDate) {
        String travelCardType = refundEngineService.findTravelCardTypeByDuration(startDate, endDate);
        if (Durations.OTHER.getDurationType().equals(travelCardType)) {
            return new ProductDTO(startZone, endZone, startDate.toDateMidnight().toDate(), endDate.toDateMidnight().toDate(),
                            Durations.OTHER.getDurationType(), "Odd Period Ticket Zones " + startZone + " to " + endZone);
        } else {
            //TODO Remove constants for passenger type and discount type
            return productDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(travelCardType, startZone, endZone,
                            startDate.toDate(), CubicConstant.PASSENGER_TYPE_ADULT_CODE, CubicConstant.NO_DISCOUNT_TYPE_CODE, ProductItemType.TRAVEL_CARD.databaseCode());
        }
    }

    @Override
    public SelectListDTO getBackdatedRefundTypes() {
        List<BackdatedRefundReasonDTO> BackdatedRefundReasonDTOs = this.backdatedRefundReasonDataService.findAll();
        SelectListDTO selectListDTO =
                new SelectListDTO(BACKDATED_REFUND_TYPES, BACKDATED_REFUND_TYPES, new ArrayList<SelectListOptionDTO>());
        for (BackdatedRefundReasonDTO backdatedRefundReasonDTO : BackdatedRefundReasonDTOs) {
            selectListDTO.getOptions().add(new SelectListOptionDTO(backdatedRefundReasonDTO.getReasonId().toString(),
                    backdatedRefundReasonDTO.getDescription()));
        }
        return selectListDTO;
    }

    @Override
    public void setTicketType(CartItemCmdImpl cmd) {
        cmd.setTicketType(TicketType.TRAVEL_CARD.code());
        if (cmd.getTravelCardType() != null && cmd.getTravelCardType().length() > TRAVEL_CARD_MIN_LENGTH &&
                cmd.getTravelCardType().endsWith(BUS_PASS_FLAG)) {
            cmd.setTicketType(TicketType.BUS_PASS.code());
        }
    }

    @Override
    public void setTravelcardTypeByFormTravelCardType(CartItemCmdImpl cmd) {
        if (cmd.getTravelCardType() != null && cmd.getTravelCardType().length() > TRAVEL_CARD_MIN_LENGTH) {
            String travelCardType = removeTravelCardSuffixFromTravelCardType(cmd.getTravelCardType());
            cmd.setTravelCardType(Durations.findByType(travelCardType).getDurationType());
        }
    }

    private String removeTravelCardSuffixFromTravelCardType(String travelCardType) {
        StringBuilder travelCardTypeWithoutSuffix = new StringBuilder();
        if (travelCardType.endsWith(TRAVEL_CARD)) {
            travelCardTypeWithoutSuffix.append(travelCardType.replaceAll(TRAVEL_CARD_REG_EX, BLANK));
        } else if (travelCardType.endsWith(BUS_PASS)) {
            travelCardTypeWithoutSuffix.append(travelCardType.replaceAll(BUS_PASS_REG_EX, BLANK));
        }

        return travelCardTypeWithoutSuffix.toString();
    }

    @Override
    public List<CartItemCmdImpl> processCancelOrSurrenderRefund(CartCmdImpl cmd) {
        List<CartItemCmdImpl> returnList = new ArrayList<CartItemCmdImpl>();
        for (CartItemCmdImpl cartItem : cmd.getCartItemList()) {
            returnList.add(processCancelOrSurrenderRefund(cartItem, cmd.getDateOfRefund()));
        }
        return returnList;
    }

    protected CartItemCmdImpl processCancelOrSurrenderRefund(CartItemCmdImpl itemCmd, Date refundDate) {
        if (isNotPayAsYouGoCreditItem(itemCmd) && isNotAdministrationFeeCancelAndSurrenderRefundItem(itemCmd)) {
            DateTime startDate = parseDateTime(itemCmd.getStartDate());
            DateTime endDate = parseDateTime(itemCmd.getEndDate());

            ProductDTO product = findProduct(itemCmd.getStartZone(), itemCmd.getEndZone(), startDate, endDate);
            product.setId(itemCmd.getId());
            product.setTicketPrice(itemCmd.getPrice());
            itemCmd.setItem(product.getProductName());

            if (isTradedTicketItem(itemCmd)) {
                processTradedTicket(itemCmd, product, startDate, endDate);
            } else {
                itemCmd.setRefund(refundEngineService.calculateRefund(startDate, endDate, new DateTime(refundDate),
                        RefundCalculationBasis.find(itemCmd.getRefundCalculationBasis()), product));
            }
        } else {
            Refund calculateRefund = new Refund();
            calculateRefund.setRefundAmount(Long.valueOf(itemCmd.getPrice()));
        }
        return itemCmd;
    }

    protected void processTradedTicket(CartItemCmdImpl itemCmd, ProductDTO product, DateTime startDate, DateTime endDate) {
        DateTime tradedStartDate = parseDateTime(itemCmd.getTradedTicket().getStartDate());
        DateTime tradedEndDate = parseDateTime(itemCmd.getTradedTicket().getEndDate());
        DateTime refundDate = DateTime.parse(DateUtil.formatDate(itemCmd.getDateOfRefund()), dateFormat);
        

        ProductDTO tradedProduct =
                findProduct(itemCmd.getTradedTicket().getStartZone(), itemCmd.getTradedTicket().getEndZone(), tradedStartDate,
                        tradedEndDate);
        tradedProduct.setId(itemCmd.getTradedTicket().getId());
        tradedProduct.setTicketPrice(itemCmd.getTradedTicket().getPrice());

        itemCmd.getTradedTicket().setItem(tradedProduct.getProductName());

        RefundCalculationBasis refundBasis = RefundCalculationBasis.find(refundCalculationBasisService
                .getRefundCalculationBasisForTradedTickets(product.getTicketPrice(), tradedProduct.getTicketPrice()));

        itemCmd.setRefund(refundEngineService
                .calculateRefundTradeUpOrDown(startDate, endDate, product.getId(), tradedProduct.getId(),
                        itemCmd.getExchangedDate(), refundDate,refundBasis));
    }

    protected DateTime parseDateTime(String value) {
        return DateTime.parse(value, dateFormat);
    }

    protected Boolean isPayAsYouGoCreditItem(CartItemCmdImpl itemCmd) {
        return PAY_AS_YOU_GO_CREDIT_ITEM.equals(itemCmd.getItem());
    }

    protected Boolean isNotPayAsYouGoCreditItem(CartItemCmdImpl itemCmd) {
        return !isPayAsYouGoCreditItem(itemCmd);
    }

    protected Boolean isAdministrationFeeCancelAndSurrenderRefundItem(CartItemCmdImpl itemCmd) {
        return ADMINISTRATION_FEE_CANCEL_AND_SURRENDER_REFUND_ITEM.equals(itemCmd.getItem());
    }

    protected Boolean isNotAdministrationFeeCancelAndSurrenderRefundItem(CartItemCmdImpl itemCmd) {
        return !isAdministrationFeeCancelAndSurrenderRefundItem(itemCmd);
    }

    protected Boolean isTradedTicketItem(CartItemCmdImpl itemCmd) {
        return null != itemCmd.getTradedTicket();
    }
}
