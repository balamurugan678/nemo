package com.novacroft.nemo.tfl.batch.integration_test.step_definition;

import static com.novacroft.nemo.common.utils.CurrencyUtil.reverseSign;
import static com.novacroft.nemo.test_support.AddressTestUtil.getTestAddressDTO1;
import static com.novacroft.nemo.test_support.DateTestUtil.getAug19;
import static com.novacroft.nemo.test_support.OrderTestUtil.TOTAL_AMOUNT;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.novacroft.nemo.test_support.SettlementTestUtil;
import com.novacroft.nemo.tfl.common.constant.OrderStatus;
import com.novacroft.nemo.tfl.common.constant.SettlementStatus;
import com.novacroft.nemo.tfl.common.data_service.AdHocLoadSettlementDataService;
import com.novacroft.nemo.tfl.common.data_service.BACSSettlementDataService;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.transfer.AdHocLoadSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoItemDTO;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.BACSSettlementDTO;
import com.novacroft.nemo.tfl.common.util.AddressFormatUtil;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

@ContextConfiguration({"classpath:nemo-tfl-batch-context-integration-test.xml"})
public class OrderStepDefinition extends BaseBatchStepDefinition {
    private static final Integer REFUND_AMOUNT = reverseSign(TOTAL_AMOUNT);

    @Autowired
    private OrderDataService orderDataService;
    @Autowired
    private BACSSettlementDataService bacsSettlementDataService;
    
    @Autowired
    private AdHocLoadSettlementDataService adHocLoadSettlementDataService;
 
    public OrderStepDefinition() {
    }

    @Given("^the customer has a refund order$")
    public void theCustomerHasARefundOrder() throws Throwable {
        testContext.setOrderDTO(new OrderDTO());
        testContext.getOrderDTO().setCustomerId(testContext.getCustomerDTO().getId());
        testContext.getOrderDTO().setOrderDate(getAug19());
        testContext.getOrderDTO().setRefundDate(getAug19());
        testContext.getOrderDTO().setTotalAmount(REFUND_AMOUNT);
        testContext.getOrderDTO().setStatus(OrderStatus.PAID.code());
        testContext.getOrderDTO().setOrderItems(new ArrayList<ItemDTO>());
    }
    
    @Given("^the customer has a order with Paid status$")
    public void theCustomerHasAOrderWithPAIDStatus() throws Throwable {
        testContext.setOrderDTO(new OrderDTO());
        testContext.getOrderDTO().setCustomerId(testContext.getCustomerDTO().getId());
        testContext.getOrderDTO().setOrderDate(getAug19());
        testContext.getOrderDTO().setTotalAmount(REFUND_AMOUNT);
        testContext.getOrderDTO().setStatus(OrderStatus.PAID.code());
        testContext.getOrderDTO().setOrderItems(new ArrayList<ItemDTO>());
    }

    @Given("^the order has a pay as you go item$")
    public void theOrderHasAPayAsYouGoItem() throws Throwable {
        testContext.setPayAsYouGoItemDTO(new PayAsYouGoItemDTO());
        testContext.getPayAsYouGoItemDTO().setPrice(REFUND_AMOUNT);
        testContext.getOrderDTO().getOrderItems().add(testContext.getPayAsYouGoItemDTO());

        testContext.setOrderDTO(orderDataService.create(testContext.getOrderDTO()));
    }

    @Given("^the order has a AdHoc settlement with a requested status$")
    public void theOrderHasAAdHocSettlementWithARequestedStatus() throws Throwable {
        addAdhocSettlementToOrder(SettlementStatus.REQUESTED);
        testContext.setAdHocLoadSettlementDTO(adHocLoadSettlementDataService.createOrUpdate(testContext.getAdHocLoadSettlementDTO()));
    }
    
    @Given("^the order has a BACS settlement with a requested status$")
    public void theOrderHasABacsSettlementWithARequestedStatus() throws Throwable {
        addBacsSettlementToOrder(SettlementStatus.REQUESTED);
        testContext.setBacsSettlementDTO(bacsSettlementDataService.createOrUpdate(testContext.getBacsSettlementDTO()));
    }

    @Given("^the order has a BACS settlement with a successful status$")
    public void theOrderHasABacsSettlementWithASuccessfulStatus() throws Throwable {
        addBacsSettlementToOrder(SettlementStatus.SUCCESSFUL);
        testContext.getBacsSettlementDTO().setPaymentReference(testContext.getBacsSettlementDTO().getSettlementNumber());
        testContext.setBacsSettlementDTO(bacsSettlementDataService.createOrUpdate(testContext.getBacsSettlementDTO()));
    }

    @Then("^the BACS settlement status should be updated to successful$")
    public void theBacsSettlementStatusShouldBeUpdatedToSuccessful() throws Throwable {
        assertEquals(SettlementStatus.SUCCESSFUL.code(),
                bacsSettlementDataService.findById(testContext.getBacsSettlementDTO().getId()).getStatus());
    }
    
    @Then("^the AdHoc settlement status should be updated to Picked Up$")
    public void theAdHocLoadSettlementStatusShouldBeUpdatedToPickedUp() throws Throwable {
        assertEquals(SettlementStatus.PICKED_UP.code(),
                        adHocLoadSettlementDataService.findById(testContext.getAdHocLoadSettlementDTO().getId()).getStatus());
    }
    
    @Then("^the AdHoc settlement status should remain as requested$")
    public void theAdHocLoadSettlementStatusShouldRemainAsRequested() throws Throwable {
        assertEquals(SettlementStatus.REQUESTED.code(),
                        adHocLoadSettlementDataService.findById(testContext.getAdHocLoadSettlementDTO().getId()).getStatus());
    }
    
    @Then("^the Order status should be updated to complete$")
    public void theOrderStatusShouldBeUpdatedToCompleted() throws Throwable {
        assertEquals(OrderStatus.COMPLETE.code(),
                orderDataService.findById(testContext.getOrderDTO().getId()).getStatus());
    }

    @Then("^the BACS settlement status should be updated to failed$")
    public void theBacsSettlementStatusShouldBeUpdatedToFailed() throws Throwable {
        assertEquals(SettlementStatus.FAILED.code(),
                bacsSettlementDataService.findById(testContext.getBacsSettlementDTO().getId()).getStatus());
    }

    @Then("^the BACS settlement status should remain as requested$")
    public void theBacsSettlementStatusShouldRemainAsRequested() throws Throwable {
        assertEquals(SettlementStatus.REQUESTED.code(),
                bacsSettlementDataService.findById(testContext.getBacsSettlementDTO().getId()).getStatus());
    }

    @Then("^the BACS settlement status should remain as successful$")
    public void theBacsSettlementStatusShouldRemainAsSuccessful() throws Throwable {
        assertEquals(SettlementStatus.SUCCESSFUL.code(),
                bacsSettlementDataService.findById(testContext.getBacsSettlementDTO().getId()).getStatus());
    }
    
    @And("^the order status should remain as paid$")
    public void theOrderStatusShouldRemainAsPaid() throws Throwable {
        assertEquals(OrderStatus.PAID.code(),
                orderDataService.findById(testContext.getOrderDTO().getId()).getStatus());
    }

    protected void addBacsSettlementToOrder(SettlementStatus settlementStatus) {
        testContext.setBacsSettlementDTO(new BACSSettlementDTO());
        testContext.getBacsSettlementDTO().setOrderId(testContext.getOrderDTO().getId());
        testContext.getBacsSettlementDTO().setAddressDTO(getTestAddressDTO1());
        testContext.getBacsSettlementDTO().setStatus(settlementStatus.code());
        testContext.getBacsSettlementDTO().setSettlementDate(getAug19());
        testContext.getBacsSettlementDTO().setAmount(REFUND_AMOUNT);
        testContext.getBacsSettlementDTO().setPayeeName(AddressFormatUtil.formatName(testContext.getCustomerDTO()));
    }
    
    protected void addAdhocSettlementToOrder(SettlementStatus settlementStatus) {
        testContext.setAdHocLoadSettlementDTO(new AdHocLoadSettlementDTO());
        testContext.getAdHocLoadSettlementDTO().setOrderId(this.testContext.getOrderDTO().getId());
        testContext.getAdHocLoadSettlementDTO().setStatus(settlementStatus.code());
        testContext.getAdHocLoadSettlementDTO().setSettlementDate(getAug19());
        testContext.getAdHocLoadSettlementDTO().setAmount(TOTAL_AMOUNT);
        testContext.setRequestSequenceNumber(new org.joda.time.DateTime().getSecondOfDay());
        testContext.getAdHocLoadSettlementDTO().setRequestSequenceNumber(testContext.getRequestSequenceNumber());
        testContext.getAdHocLoadSettlementDTO().setCardId(this.testContext.getCardDTO().getId());
        testContext.getAdHocLoadSettlementDTO().setPickUpNationalLocationCode(SettlementTestUtil.PICK_UP_NATIONAL_LOCATION_CODE);
    }
}
