package com.novacroft.nemo.tfl.common.converter.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.data_service.AddressDataService;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.RefundCalculationBasisService;
import com.novacroft.nemo.tfl.common.application_service.RefundService;
import com.novacroft.nemo.tfl.common.application_service.WorkFlowService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.GoodwillPaymentItemCmd;
import com.novacroft.nemo.tfl.common.command.impl.RefundItemCmd;
import com.novacroft.nemo.tfl.common.constant.CartType;
import com.novacroft.nemo.tfl.common.constant.PaymentType;
import com.novacroft.nemo.tfl.common.constant.RefundDepartmentEnum;
import com.novacroft.nemo.tfl.common.constant.RefundScenarioEnum;
import com.novacroft.nemo.tfl.common.data_service.AdministrationFeeDataService;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.domain.Refund;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.GoodwillPaymentItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;
import com.novacroft.nemo.tfl.common.util.CartUtil;
import com.novacroft.nemo.tfl.innovator.workflow.processBeans.RefundDetailDTO;

@Component("refundCartCommandObjectToWorkflowItemConverter")
public class RefundToWorkflowConverter {

    @Autowired
    protected GetCardService getCardService;
    @Autowired
    protected CardDataService cardService;
    @Autowired
    protected AdministrationFeeDataService administrationFeeDataService;
    @Autowired
    protected CustomerDataService customerDataService;
    @Autowired
    protected RefundService refundService;
    @Autowired
    protected CartService cartService;
    @Autowired
    protected RefundCalculationBasisService refundCalculationBasisService;
    @Autowired
    protected WorkFlowService workflowService;
    @Autowired
    protected AddressDataService addressService;

    public WorkflowItemDTO convert(CartCmdImpl cartCmdImpl) {

        assert (cartCmdImpl.getToPayAmount() != null);
        assert (cartCmdImpl.getCartDTO().getCartRefundTotal() != null);
        assert (cartCmdImpl.getCartType() != null);
        assert (cartCmdImpl.getPaymentType() != null);

        WorkflowItemDTO workflowItem = new WorkflowItemDTO();
        RefundDetailDTO refundDetail = new RefundDetailDTO();
        workflowItem.setRefundDetails(refundDetail);
        Refund refund = new Refund();
        refundDetail.setRefundEntity(refund);

        CartDTO cartDto = cartService.findById(cartCmdImpl.getCartDTO().getId());
        cartDto = cartService.postProcessAndSortCartDTOAndRecalculateRefund(cartDto);

        convertCardDetails(cartCmdImpl, refundDetail);

        convertItemDetails(cartDto, refundDetail);

        refundDetail.setCardNumber(cartCmdImpl.getCardNumber());
        setTotalRefundAmount(cartCmdImpl, refundDetail, refund);
        refundDetail.setRefundDate(new DateTime(cartCmdImpl.getDateOfRefund()));
        refundDetail.setRefundScenario(findScenarioByCartType(cartCmdImpl.getCartType()));
        refundDetail.setRefundOriginatingApplication(cartCmdImpl.getRefundOriginatingApplication());
        refundDetail.setDeposit(getCardDepositAmount(cartCmdImpl.getCardNumber()));
        refundDetail.setTotalTicketPrice(getTotalTicketPrice(cartDto.getCartItems()).longValue());
        refundDetail.setPreviouslyExchanged(cartCmdImpl.getCartItemCmd().getPreviouslyExchanged() != null ? cartCmdImpl.getCartItemCmd()
                        .getPreviouslyExchanged() : false);

        convertCustomerDetails(cartCmdImpl, refundDetail);

        hasChangedFromDefault(cartCmdImpl, refundDetail);

        refundDetail.setRefundDepartment(RefundDepartmentEnum.OYSTER);

        convertPaymentDetails(cartCmdImpl, refundDetail);

        refund.setRefundableDays(1);
        workflowItem.setCreatedTime(new DateTime());
        workflowItem.setCaseNumber(cartCmdImpl.getApprovalId().toString());

        return workflowItem;

    }

    protected void setTotalRefundAmount(CartCmdImpl cartCmdImpl, RefundDetailDTO refundDetail, Refund refund) {
        if (CartUtil.isDestroyedOrFaildCartType(cartCmdImpl.getCartType())) {
            Long totalRefundAmountWithDeposit = cartCmdImpl.getCartDTO().getCartRefundTotal().longValue() + cartCmdImpl.getCartDTO().getCardRefundableDepositAmount().longValue();
            refund.setRefundAmount(totalRefundAmountWithDeposit);
            refundDetail.setTotalRefundAmount(totalRefundAmountWithDeposit);
        } else {
            Long totalRefundAmount = cartCmdImpl.getCartDTO().getCartRefundTotal().longValue();
            refund.setRefundAmount(totalRefundAmount);
            refundDetail.setTotalRefundAmount(totalRefundAmount);
        }
    }

    private void convertPaymentDetails(CartCmdImpl cartCmdImpl, RefundDetailDTO refundDetail) {
        refundDetail.setPaymentType(PaymentType.lookUpPaymentType(cartCmdImpl.getPaymentType()));
        if (StringUtils.isNotEmpty(cartCmdImpl.getLastName())) {
            CustomerDTO alternativeRefundPayee = new CustomerDTO(StringUtils.EMPTY, cartCmdImpl.getFirstName(), cartCmdImpl.getInitials(),
                            cartCmdImpl.getLastName());
            refundDetail.setAlternativeRefundPayee(alternativeRefundPayee);
        }

        refundDetail.setTargetCardNumber(cartCmdImpl.getTargetCardNumber());
        refundDetail.setPayeeSortCode(cartCmdImpl.getPayeeSortCode());
        refundDetail.setPayeeAccountNumber(cartCmdImpl.getPayeeAccountNumber());
        refundDetail.setStationId(cartCmdImpl.getStationId());
    }

    private void convertCardDetails(CartCmdImpl cartCmdImpl, RefundDetailDTO refundDetail) {
        CardDTO card = cardService.findByCardNumber(cartCmdImpl.getCardNumber());
        if (null != card) {
            refundDetail.setCardId(card.getId());
        }
    }

    protected void convertItemDetails(CartDTO cartDto, RefundDetailDTO refundDetail) {
        List<ItemDTO> cartItemsDto = cartDto.getCartItems();

        List<RefundItemCmd> refundItems = new ArrayList<RefundItemCmd>();
        List<GoodwillPaymentItemCmd> goodWillPaymentItemsList = new ArrayList<GoodwillPaymentItemCmd>();

        for (ItemDTO itemDto : cartItemsDto) {
            if (workflowService.isTravelCardOrBusPass(itemDto)) {
                RefundItemCmd refundItem = new RefundItemCmd((ProductItemDTO) itemDto);
                refundItems.add(refundItem);
            } else if (workflowService.isItemDtoTypeOfAdministrationFeeItemDto(itemDto)) {
                refundDetail.setAdministrationFee(itemDto.getPrice().longValue());
            } else if (workflowService.isItemDtoTypeOfGoodwillPaymentDTO(itemDto)) {
                GoodwillPaymentItemCmd goodwillPaymentItem = new GoodwillPaymentItemCmd((GoodwillPaymentItemDTO) itemDto);
                goodWillPaymentItemsList.add(goodwillPaymentItem);
            } else if (workflowService.isItemDtoTypeOfPayAsYouGoDTO(itemDto)) {
                refundDetail.setPayAsYouGoCredit(itemDto.getPrice());

            }
        }

        refundDetail.setRefundItems(refundItems);
        refundDetail.setGoodwillItems(goodWillPaymentItemsList);
    }

    protected void convertCustomerDetails(CartCmdImpl cartCmdImpl, RefundDetailDTO refundDetail) {
        CustomerDTO customer = cartService.findCustomerForCart(cartCmdImpl.getCartDTO().getId());
        if (null != customer) {
            refundDetail.setCustomer(customer);
            refundDetail.setCustomerId(customer.getId());
            refundDetail.setName(customer.getUsername());
        }
        refundDetail.setAddress(cartCmdImpl.getPayeeAddress());
        refundDetail.setAlternativeAddress(cartCmdImpl.getPayeeAddress());
    }

    public AddressDTO findAddressForCardHolder(Long cardId) {
        CustomerDTO customer = customerDataService.findByCardId(cardId);
        return addressService.findById(customer.getAddressId());
    }

    protected void hasChangedFromDefault(CartCmdImpl cmd, RefundDetailDTO refundDetail) {
        if (workflowService.hasAdminFeeChangedFromDefault(cmd)) {
            refundDetail.setAdminFeeChanged(Boolean.TRUE);
        }

        if (workflowService.hasCalculationBasisChangedFromDefault(cmd)) {
            refundDetail.setCalculationBasisChanged(Boolean.TRUE);
        }

        if (workflowService.hasPayAsYouGoChangedFromDefault(cmd)) {
            refundDetail.setPayAsYouGoChanged(Boolean.TRUE);
        }
    }

    protected Integer getTotalTicketPrice(List<ItemDTO> cartItemsDto) {
        return refundService.getTotalTicketPrice(cartItemsDto);
    }

    protected CardInfoResponseV2DTO getCardDetailsFromCubic(String cardNumber) {
        return getCardService.getCard(cardNumber);
    }

    protected Integer getCardDepositAmount(String cardNumber) {
        if (StringUtils.isNotBlank(cardNumber)) {
            return getCardDetailsFromCubic(cardNumber).getCardDeposit();
        }
        return null;
    }

    public static AddressDTO createAddressFromCartCmd(CartCmdImpl cmd) {
        return new AddressDTO(cmd.getHouseNameNumber(), cmd.getStreet(), cmd.getTown(), cmd.getPostcode(), cmd.getCountry());
    }

    protected boolean isGoodwillPayment(ItemDTO cartItemDto) {
        return cartItemDto instanceof GoodwillPaymentItemDTO;
    }

    protected RefundScenarioEnum findScenarioByCartType(String cartType) {
        switch (CartType.find(cartType)) {
        case FAILED_CARD_REFUND:
            return RefundScenarioEnum.FAILEDCARD;
        case DESTROYED_CARD_REFUND:
            return RefundScenarioEnum.DESTROYED;
        case LOST_REFUND:
            return RefundScenarioEnum.LOST;
        case STOLEN_REFUND:
            return RefundScenarioEnum.STOLEN;
        case CANCEL_SURRENDER_REFUND:
            return RefundScenarioEnum.CANCEL_AND_SURRENDER;
        case ANONYMOUS_GOODWILL_REFUND:
            return RefundScenarioEnum.ANONYMOUS_GOODWILL_REFUND;
        case STANDALONE_GOODWILL_REFUND:
            return RefundScenarioEnum.STANDALONE_GOODWILL_REFUND;
        default:
            return null;
        }

    }

    public CartCmdImpl convert(WorkflowItemDTO workflowItem) {
        CartCmdImpl cmd = new CartCmdImpl();

        RefundDetailDTO refundDetails = workflowItem.getRefundDetails();
        if (refundDetails.getCardId() != null) {
            CardDTO card = cardService.findById(refundDetails.getCardId());
            cmd.setCardId(card.getId());
        }

        cmd.setCardNumber(refundDetails.getCardNumber());

        cmd.setToPayAmount(refundDetails.getTotalRefundAmount().intValue());
        cmd.setTotalAmt(refundDetails.getTotalRefundAmount().intValue());
        cmd.setPayeeAddress(refundDetails.getAlternativeAddress());

        setAddressDetails(cmd, refundDetails);
        setCustomerDetails(cmd, refundDetails);

        cmd.setCartType(refundDetails.getRefundScenario().code());
        cmd.setApprovalId(Long.valueOf(workflowItem.getCaseNumber()));
        cmd.setPaymentType(refundDetails.getPaymentType().code());
        cmd.setDateOfRefund(refundDetails.getRefundDate().toDate());
        cmd.setRefundOriginatingApplication(refundDetails.getRefundOriginatingApplication());

        CartDTO cart = cartService.findByApprovalId(cmd.getApprovalId());
        cmd.setCartDTO(cart);

        cmd.setTargetCardNumber(workflowItem.getRefundDetails().getTargetCardNumber());
        cmd.setStationId(refundDetails.getStationId());
        return cmd;
    }

    private void setCustomerDetails(CartCmdImpl cmd, RefundDetailDTO refundDetails) {
        CustomerDTO customer = refundDetails.getCustomer();
        if (customer != null) {
            cmd.setFirstName(customer.getFirstName());
            cmd.setInitials(customer.getInitials());
            cmd.setLastName(customer.getLastName());
            cmd.setTitle(customer.getTitle());
        }
    }

    private void setAddressDetails(CartCmdImpl cmd, RefundDetailDTO refundDetails) {
        AddressDTO address = refundDetails.getAddress();
        if (address != null) {
            cmd.setHouseNameNumber(address.getHouseNameNumber());
            cmd.setStreet(address.getStreet());
            cmd.setTown(address.getTown());
            cmd.setPostcode(address.getPostcode());
            cmd.setCounty(address.getCounty());
            cmd.setCountry(address.getCountry());
        }
    }

}
