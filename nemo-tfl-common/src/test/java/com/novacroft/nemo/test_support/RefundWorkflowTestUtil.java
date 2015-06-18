package com.novacroft.nemo.test_support;

import static com.novacroft.nemo.test_support.AddressTestUtil.POSTCODE_1;
import static com.novacroft.nemo.test_support.AddressTestUtil.STREET_1;
import static com.novacroft.nemo.test_support.AddressTestUtil.TOWN_1;
import static com.novacroft.nemo.test_support.CountryTestUtil.getTestCountryDTO1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.EMAIL_ADDRESS_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.FIRST_NAME_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.LAST_NAME_1;
import static com.novacroft.nemo.test_support.ItemTestUtil.ITEM_ID;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.GoodwillPaymentItemCmd;
import com.novacroft.nemo.tfl.common.command.impl.RefundItemCmd;
import com.novacroft.nemo.tfl.common.command.impl.WorkflowCmd;
import com.novacroft.nemo.tfl.common.command.impl.WorkflowEditCmd;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;
import com.novacroft.nemo.tfl.common.constant.PaymentType;
import com.novacroft.nemo.tfl.common.constant.RefundDepartmentEnum;
import com.novacroft.nemo.tfl.common.constant.RefundScenarioEnum;
import com.novacroft.nemo.tfl.common.domain.Refund;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.GoodwillPaymentItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;
import com.novacroft.nemo.tfl.innovator.workflow.processBeans.RefundDetailDTO;


public class RefundWorkflowTestUtil {

    public static final Long REFUNDAMOUNT = 1200L;
    public static final String CASENUMBER = "1";
    public static final String AGENT_NAME = "SMITH";
    public static final String TICKET_PRICE_11000 = "11000";
    public static final String TICKET_PRICE_15000 = "15000";
    public static final Long TICKET_REFUND_AMOUNT = 10200L;
    public static final String FIRST_INITIAL_LAST = "First Initial Last";
    public static final String END_ZONE = "2";
    public static final String START_ZONE = "1";
    public static final String HOUSENUMBER = "4";
    public static final Long DEPOSIT = 1200L;
    public static final String REASON = "Refund £1 above limit";
    public static final List<String> REASONS = new ArrayList<String>();
    
    public static List<String> getReason(){
        REASONS.add(REASON);
        return REASONS;
    } 

    public static WorkflowCmd getWorkflowCmd() {

        WorkflowCmd workflowCmd = new WorkflowCmd();
        workflowCmd.setPaymentName(FIRST_INITIAL_LAST);
        workflowCmd.setPaymentMethod(PaymentType.WEB_ACCOUNT_CREDIT.code());
        workflowCmd.setTotalRefund(new Long(10));
        workflowCmd.setWorkflowItem(getWorkflowItem());
        workflowCmd.setTicketDeposit(new Long(0));
        workflowCmd.setEdit(Boolean.TRUE);
        workflowCmd.setWorkflowItem(getWorkflowItem());
        workflowCmd.setCustomerAddressDTO(AddressTestUtil.getTestAddressDTO1());
        workflowCmd.setPaymentAddressDTO(AddressTestUtil.getTestAddressDTO1());

        return workflowCmd;
    }

    public static WorkflowEditCmd getWorkFlowEditcmd() {
        
        WorkflowEditCmd workflowEditCmd = new WorkflowEditCmd();
        workflowEditCmd.setPaymentName(FIRST_INITIAL_LAST);
        workflowEditCmd.setPaymentMethod(PaymentType.WEB_ACCOUNT_CREDIT.code());
        workflowEditCmd.setCountry(CartCmdTestUtil.CUSTOMER_ADDRESS_COUNTRY);
        workflowEditCmd.setRefundScenarioSubType(RefundScenarioEnum.FAILEDCARD.code());
        workflowEditCmd.setRefundScenarioType(RefundDepartmentEnum.OYSTER.code());
        return workflowEditCmd;
    }
    
    public static  List<RefundItemCmd> getRefundItemCmdList(){
        List<RefundItemCmd> refundItemCmdList = new ArrayList<RefundItemCmd>();
        refundItemCmdList.add(new RefundItemCmd(CartAttribute.ANNUAL_BUS_PASS, START_ZONE, END_ZONE,
                        CartItemTestUtil.REFUND_CALCULATION_BASIS_PRO_RATA, TICKET_PRICE_15000, TICKET_REFUND_AMOUNT));
        refundItemCmdList.add(new RefundItemCmd(CartAttribute.MONTHLY_BUS_PASS, START_ZONE, END_ZONE,
                        CartItemTestUtil.REFUND_CALCULATION_BASIS_PRO_RATA, TICKET_PRICE_11000, TICKET_REFUND_AMOUNT));
        return refundItemCmdList;
    }
    
    public static RefundDetailDTO getRefundDetails(){
        RefundDetailDTO refundDetail = new RefundDetailDTO();
        refundDetail.setPaymentType(PaymentType.WEB_ACCOUNT_CREDIT);
        refundDetail.setAddress(getAddressDto());
        refundDetail.setName(FIRST_INITIAL_LAST);
        refundDetail.setRefundItems(getRefundItemCmdList());
        refundDetail.setPayAsYouGoCredit(CartItemTestUtil.PAY_AS_YOU_GO_TICKET_PRICE_1);
        refundDetail.setGoodwillItems(getCardGoodwillPaymentItem());
        refundDetail.setCustomer(getCustomerDto()); 
        refundDetail.setTotalTicketPrice(1000L);
        refundDetail.setRefundScenario(RefundScenarioEnum.FAILEDCARD);
        refundDetail.setRefundDepartment(RefundDepartmentEnum.OYSTER);
        refundDetail.setRefundDate(new DateTime());
        refundDetail.setDeposit(0);
        refundDetail.setCardId(CommonCardTestUtil.CARD_ID_1);
        Refund refund = new Refund();
        refund.setRefundAmount(REFUNDAMOUNT);
        refund.setRefundableDays(1);
        refundDetail.setRefundEntity(refund);
        return refundDetail;
    }
    
    public static AddressDTO getAddressDto(){
        AddressDTO addressDto = new AddressDTO();
        addressDto.setHouseNameNumber(HOUSENUMBER);
        addressDto.setPostcode(POSTCODE_1);
        addressDto.setStreet(STREET_1);
        addressDto.setTown(TOWN_1);
        addressDto.setCountry(getTestCountryDTO1());
        return addressDto;    
    }   
    
    
    public static CustomerDTO getCustomerDto(){
        CustomerDTO customerDto = new CustomerDTO();
        customerDto.setTitle(CustomerTestUtil.TITLE_1);
        customerDto.setInitials(CustomerTestUtil.INITIALS_1);
        customerDto.setLastName(LAST_NAME_1);
        customerDto.setFirstName(FIRST_NAME_1);
        customerDto.setEmailAddress(EMAIL_ADDRESS_1);
        return customerDto;    
    }  
    
    public static WorkflowItemDTO getWorkflowItem(){
        WorkflowItemDTO workflowItem = new WorkflowItemDTO();
        workflowItem.setAgent(AGENT_NAME);
        workflowItem.setPaymentMethod(PaymentType.WEB_ACCOUNT_CREDIT.code()); 
        workflowItem.setApprovalReasons(REASONS);
        workflowItem.setCaseNumber(CASENUMBER);
        workflowItem.setRefundDetails(getRefundDetails());
        workflowItem.setCreatedTime(new DateTime());
        return workflowItem;
        
    }

    public static List<ItemDTO> getProductItemDto() {
        ProductItemDTO productItemDto = getProductItemDTO();
        List<ItemDTO> productItemDtoList = new ArrayList<ItemDTO>();
        productItemDtoList.add(productItemDto);
        return productItemDtoList;
    }

    public static ProductItemDTO getProductItemDTO() {
        ProductItemDTO productItemDto = new ProductItemDTO();
        Refund refund = new Refund();
        productItemDto.setName(CartItemTestUtil.SEVEN_DAY_TRAVEL_CARDS_1);
        productItemDto.setStartZone(1);
        productItemDto.setEndZone(10);
        productItemDto.setRefundCalculationBasis(CartItemTestUtil.REFUND_CALCULATION_BASIS_PRO_RATA);
        productItemDto.setPrice(15000);
        refund.setRefundAmount(REFUNDAMOUNT);
        productItemDto.setRefund(refund);
        return productItemDto;
    }

    public static CartCmdImpl getTestCartCmdWithTravelCardAndCartDto() {

        CartDTO cartDTO = new CartDTO();
        CartCmdImpl cartCmdImpl = new CartCmdImpl();
        cartDTO.setId(CartTestUtil.CART_ID_1);
        cartDTO.setCreatedUserId(CartTestUtil.TEST_USER_ID);
        cartDTO.setCreatedDateTime(new Date());
        cartDTO.setCardId(CartTestUtil.CARD_ID_1);
        cartDTO.addCartItem(getProductItemDTO());
        cartDTO.addCartItem(PayAsYouGoItemTestUtil.getTestPayAsYouGoItemDTO1());
        cartDTO.addCartItem(GoodwillPaymentTestUtil.getGoodwillPaymentItemDTOItem3());
        cartDTO.addCartItem(AdministrationFeeItemTestUtil.getTestAdministrationFeeItemDTO1());
        cartCmdImpl.setCartDTO(cartDTO);
        return cartCmdImpl;
    }

    public static ProductItemDTO setProductItemBusPass() {
        ProductItemDTO productItemDto = (ProductItemDTO) CartTestUtil.getNewAnnualBusPassProductItemDTO();
        return productItemDto;

    }

    public static CartDTO getTestCartCmdWithBusPass() {

        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(CartTestUtil.CART_ID_1);
        cartDTO.setCreatedUserId(CartTestUtil.TEST_USER_ID);
        cartDTO.setCreatedDateTime(new Date());
        cartDTO.setCardId(CartTestUtil.CARD_ID_1);
        cartDTO.addCartItem(CartTestUtil.getNewAnnualBusPassProductItemDTO());
        return cartDTO;
    }
    
    public static List<GoodwillPaymentItemCmd> getCardGoodwillPaymentItem() {
        List<GoodwillPaymentItemCmd> goodwillPaymentList = new ArrayList<GoodwillPaymentItemCmd>();
        GoodwillPaymentItemCmd goodwillItem1 = new GoodwillPaymentItemCmd((GoodwillPaymentItemDTO) getNewGoodwillPaymentItemDTO());
        GoodwillPaymentItemCmd goodwillItem2 = new GoodwillPaymentItemCmd((GoodwillPaymentItemDTO) getNewGoodwillPaymentItemDTO());
        goodwillPaymentList.add(goodwillItem1);
        goodwillPaymentList.add(goodwillItem2);
        return goodwillPaymentList;

    }

    public static ItemDTO getNewGoodwillPaymentItemDTO() {
        GoodwillPaymentItemDTO goodwillPaymentItemDTO = new GoodwillPaymentItemDTO();
        goodwillPaymentItemDTO.setId(ITEM_ID);
        goodwillPaymentItemDTO.setPrice(CartTestUtil.TEST_GOODWILL_PAYMENT_FEE_PRICE);
        goodwillPaymentItemDTO.setGoodwillReasonDTO(CartTestUtil.getNewGoodwillReasonDTO());
        return goodwillPaymentItemDTO;
    }
    
    public static ItemDTO getNewGoodwillPaymentItemDTO2() {
        GoodwillPaymentItemDTO goodwillPaymentItemDTO = new GoodwillPaymentItemDTO();
        goodwillPaymentItemDTO.setId(ITEM_ID);
        goodwillPaymentItemDTO.setPrice(CartTestUtil.TEST_GOODWILL_PAYMENT_FEE_PRICE2);
        goodwillPaymentItemDTO.setGoodwillReasonDTO(CartTestUtil.getNewGoodwillReasonDTO());
        return goodwillPaymentItemDTO;
    }

    public static WorkflowCmd getWorkflowCmdWithBACSPaymentType() {
        WorkflowCmd workflowCmd = new WorkflowCmd();
        workflowCmd.setPaymentName(FIRST_INITIAL_LAST);
        workflowCmd.setPaymentMethod(PaymentType.BACS.code());
        workflowCmd.setTotalRefund(new Long(10));
        workflowCmd.setWorkflowItem(getWorkflowItem());
        workflowCmd.setTicketDeposit(new Long(0));
        workflowCmd.setEdit(Boolean.TRUE);
        workflowCmd.setWorkflowItem(getWorkflowItem());
        workflowCmd.setCustomerAddressDTO(AddressTestUtil.getTestAddressDTO1());
        workflowCmd.setPaymentAddressDTO(AddressTestUtil.getTestAddressDTO1());

        return workflowCmd;
    }
    
    public static WorkflowCmd getWorkflowCmdWithChequePaymentType() {
        WorkflowCmd workflowCmd = new WorkflowCmd();
        workflowCmd.setPaymentName(FIRST_INITIAL_LAST);
        workflowCmd.setPaymentMethod(PaymentType.CHEQUE.code());
        workflowCmd.setTotalRefund(new Long(10));
        workflowCmd.setWorkflowItem(getWorkflowItem());
        workflowCmd.setTicketDeposit(new Long(0));
        workflowCmd.setEdit(Boolean.TRUE);
        workflowCmd.setWorkflowItem(getWorkflowItem());
        workflowCmd.setCustomerAddressDTO(AddressTestUtil.getTestAddressDTO1());
        workflowCmd.setPaymentAddressDTO(AddressTestUtil.getTestAddressDTO1());

        return workflowCmd;
    }
}
