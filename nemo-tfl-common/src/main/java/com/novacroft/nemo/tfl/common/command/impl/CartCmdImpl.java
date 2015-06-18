package com.novacroft.nemo.tfl.common.command.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.novacroft.nemo.common.command.impl.CommonOrderCardCmd;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.common.transfer.ConverterNullable;
import com.novacroft.nemo.common.utils.CurrencyUtil;
import com.novacroft.nemo.tfl.common.command.AddPaymentCardActionCmd;
import com.novacroft.nemo.tfl.common.command.PaymentTermsCmd;
import com.novacroft.nemo.tfl.common.command.PickUpLocationCmd;
import com.novacroft.nemo.tfl.common.command.UserCredentialsCmd;
import com.novacroft.nemo.tfl.common.command.WebCreditPurchaseCmd;
import com.novacroft.nemo.tfl.common.constant.ApplicationName;
import com.novacroft.nemo.tfl.common.constant.CartType;
import com.novacroft.nemo.tfl.common.formatter.PenceFormat;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;

/**
 * TfL online Oyster Card implementation
 */
public class CartCmdImpl extends CommonOrderCardCmd
        implements UserCredentialsCmd, PaymentTermsCmd, PickUpLocationCmd, WebCreditPurchaseCmd,
        ConverterNullable, AddPaymentCardActionCmd {
    protected String pageName;
    protected String addressForPostcode;
    protected Boolean autoTopUpVisible = Boolean.TRUE;
    protected Boolean autoTopUpUpdate = Boolean.FALSE;
    protected Long cardId;
    protected Long paymentCardId;
    protected String sourceCardNumber;
    protected String targetCardNumber;
    protected Integer previousCreditAmountOnCard;
    protected Long stationId;
    protected String cartType = CartType.PURCHASE.code();
    protected String stationName;
    protected String ticketType;
    protected String shippingType;
    protected String selectedShippingType;
    protected Integer subTotalAmt;
    protected String formattedSubTotalAmt;
    protected Integer shippingCost;
    protected String formattedShippingCost;
    protected Integer refundableDepositAmt;
    protected String formattedRefundableDepositAmount;
    protected Integer totalAmt;
    protected String formattedTotalAmount;
    protected Integer autoTopUpAmount;
    protected Date dateOfRefund;
    protected String goodwillRefundType;
    protected String startDate;
    protected String endDate;
    protected ApplicationName refundOriginatingApplication;
    protected Boolean sourceCardNotEligible = Boolean.FALSE; 
    protected Integer hotListReasonId;
    protected List<CartItemCmdImpl> cartItemList = new ArrayList<CartItemCmdImpl>();

    protected Boolean paymentTermsAccepted = Boolean.FALSE;

    protected Long webAccountId;
    
    protected CartItemCmdImpl cartItemCmd;

    protected Integer webCreditAvailableAmount;
    @PenceFormat
    protected Integer webCreditApplyAmount = 0;
    protected Integer toPayAmount = 0;
    protected String formattedToPayAmount;
    protected Boolean nullable = Boolean.TRUE;

    protected String paymentCardAction;
    protected Integer travelCardRefundLimit;

    protected String stolenRefundMessage;
    protected Long customerId;
    protected Boolean innovatorPurchase = Boolean.FALSE;
    protected String paymentType;
    protected AddressDTO payeeAddress;
    protected boolean overwriteAddress;
    protected String payeeSortCode;
    protected String payeeAccountNumber;
    protected Long approvalId;
    private String travelCardIdentical;
    @Deprecated
    protected Long cartId;
    @Deprecated
    protected CartDTO cartDTO;

    @PenceFormat
    protected Integer payAsYouGoValue;

    @PenceFormat
    protected Integer administrationFeeValue;
    
    protected Date pickUpStartDate;
    protected Date pickUpEndDate;
    protected int targetPickupLocationId=0;
    protected boolean isLostOrStolenMode= Boolean.FALSE;
    private Boolean firstIssueOrder = Boolean.FALSE;
    private Boolean replacementOrder = Boolean.FALSE;
    private String securityQuestion;
    private String securityAnswer;
    protected List<String> ruleBreaches;
    protected String autoTopUpActivity;

    protected CartDTO renewItemCartDTO; 
    
	public CartCmdImpl() {
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public CartCmdImpl(String targetCardNumber, Long webAccountId, int totalAmt, int toPayAmount, Long stationId,
                       boolean autoTopUpUpdate, Long cardId, String ticketType, Integer autoTopUpAmount, Long paymentCardId,
                       String startDate, String endDate) {
        this.targetCardNumber = targetCardNumber;
        this.webAccountId = webAccountId;
        this.totalAmt = totalAmt;
        this.toPayAmount = toPayAmount;
        this.stationId = stationId;
        this.autoTopUpUpdate = autoTopUpUpdate;
        this.cardId = cardId;
        this.ticketType = ticketType;
        this.autoTopUpAmount = autoTopUpAmount;
        this.paymentCardId = paymentCardId;
        this.startDate = startDate;
        this.endDate = endDate;

    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDateAutoTopUp(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public ApplicationName getRefundOriginatingApplication() {
        return refundOriginatingApplication;
    }

    public void setRefundOriginatingApplication(ApplicationName refundOriginatingApplication) {
        this.refundOriginatingApplication = refundOriginatingApplication;
    }

    public void setEndDateAutoTopUp(String endDate) {
        this.endDate = endDate;
    }

    public Long getPaymentCardId() {
        return paymentCardId;
    }

    public void setPaymentCardId(Long paymentCardId) {
        this.paymentCardId = paymentCardId;
    }

    public Integer getAutoTopUpAmount() {
        return autoTopUpAmount;
    }

    public void setAutoTopUpAmount(Integer autoTopUpAmount) {
        this.autoTopUpAmount = autoTopUpAmount;
    }

    public Boolean getAutoTopUpUpdate() {
        return autoTopUpUpdate;
    }

    public void setAutoTopUpUpdate(Boolean autoTopUpUpdate) {
        this.autoTopUpUpdate = autoTopUpUpdate;
    }

    public String getPageName() {

        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String name) {
        firstName = name;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String name) {
        lastName = name;
    }

    @Override
    public String getInitials() {
        return initials;
    }

    @Override
    public void setInitials(String i) {
        initials = i;
    }

    public String getAddressForPostcode() {
        return addressForPostcode;
    }

    public void setAddressForPostcode(String addressForPostcode) {
        this.addressForPostcode = addressForPostcode;
    }

    @Override
    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public String getTargetCardNumber() {
        return targetCardNumber;
    }

    public void setTargetCardNumber(String cardNumber) {
        this.targetCardNumber = cardNumber;
    }

    public String getSourceCardNumber() {
        return sourceCardNumber;
    }

    public void setSourceCardNumber(String sourceCardNumber) {
        this.sourceCardNumber = sourceCardNumber;
    }

    public Boolean getSourceCardNotEligible() {
        return sourceCardNotEligible;
    }

    public void setSourceCardNotEligible(Boolean sourceCardNotEligible) {
        this.sourceCardNotEligible = sourceCardNotEligible;
    }

    @Override
    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public String getCartType() {
        return cartType;
    }

    public void setCartType(String cartType) {
        this.cartType = cartType;
    }

    public Boolean getAutoTopUpVisible() {
        return autoTopUpVisible;
    }

    public void setAutoTopUpVisible(Boolean autoTopUpVisible) {
        this.autoTopUpVisible = autoTopUpVisible;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public String getShippingType() {
        return shippingType;
    }

    public void setShippingType(String shippingType) {
        this.shippingType = shippingType;
    }

    public String getSelectedShippingType() {
        return selectedShippingType;
    }

    public void setSelectedShippingType(String selectedShippingType) {
        this.selectedShippingType = selectedShippingType;
    }

    public List<CartItemCmdImpl> getCartItemList() {
        return cartItemList;
    }
    
    public CartItemCmdImpl getCartItem(int index) {
        return cartItemList.get(index);
    }

    public void setCartItemList(List<CartItemCmdImpl> cartItemList) {
        this.cartItemList = cartItemList;
    }

    public String getStolenRefundMessage() {
        return stolenRefundMessage;
    }

    public void setStolenRefundMessage(String stolenRefundMessage) {
        this.stolenRefundMessage = stolenRefundMessage;
    }

    public Integer getSubTotalAmt() {
        return subTotalAmt;
    }

    public void setSubTotalAmt(Integer subTotalAmt) {
        this.subTotalAmt = subTotalAmt;
        this.formattedSubTotalAmt = CurrencyUtil.formatPenceWithHtmlCurrencySymbol(subTotalAmt);
    }

    public Integer getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(Integer shippingCost) {
        this.shippingCost = shippingCost;
        this.formattedShippingCost = CurrencyUtil.formatPenceWithHtmlCurrencySymbol(shippingCost);
    }

    public Integer getRefundableDepositAmt() {
        return refundableDepositAmt;
    }

    public void setRefundableDepositAmt(Integer refundableDepositAmt) {
        this.refundableDepositAmt = refundableDepositAmt;
        this.formattedRefundableDepositAmount = CurrencyUtil.formatPenceWithHtmlCurrencySymbol(refundableDepositAmt);
    }

    @Override
    public Boolean getPaymentTermsAccepted() {
        return paymentTermsAccepted;
    }

    public void setPaymentTermsAccepted(Boolean paymentTermsAccepted) {
        this.paymentTermsAccepted = paymentTermsAccepted;
    }

    public Long getWebAccountId() {
        return webAccountId;
    }

    public void setWebAccountId(Long webAccountId) {
        this.webAccountId = webAccountId;
    }

    public Date getDateOfRefund() {
        return dateOfRefund;
    }

    public void setDateOfRefund(Date dateOfRefund) {
        this.dateOfRefund = dateOfRefund;
    }

    public String getGoodwillRefundType() {
        return goodwillRefundType;
    }

    public void setGoodwillRefundType(String goodwillRefundType) {
        this.goodwillRefundType = goodwillRefundType;
    }

    public CartItemCmdImpl getCartItemCmd() {
        return cartItemCmd;
    }

    public void setCartItemCmd(CartItemCmdImpl cartItemCmd) {
        this.cartItemCmd = cartItemCmd;
    }

    @Override
    public Integer getWebCreditAvailableAmount() {
        return webCreditAvailableAmount;
    }

    public void setWebCreditAvailableAmount(Integer webCreditAvailableAmount) {
        this.webCreditAvailableAmount = webCreditAvailableAmount;
    }

    @Override
    public Integer getWebCreditApplyAmount() {
        return webCreditApplyAmount;
    }

    @Override
    public Integer getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(Integer totalAmt) {
        this.totalAmt = totalAmt;
        this.formattedTotalAmount = CurrencyUtil.formatPenceWithHtmlCurrencySymbol(totalAmt);
    }

    public void setWebCreditApplyAmount(Integer webCreditApplyAmount) {
        this.webCreditApplyAmount = webCreditApplyAmount;
    }

    public Integer getToPayAmount() {
        return toPayAmount;
    }

    public void setToPayAmount(Integer toPayAmount) {
        this.toPayAmount = toPayAmount;
        this.formattedToPayAmount = CurrencyUtil.formatPenceWithHtmlCurrencySymbol(toPayAmount);
    }

    public String getFormattedSubTotalAmt() {
        return formattedSubTotalAmt;
    }

    public String getFormattedShippingCost() {
        return formattedShippingCost;
    }

    public String getFormattedRefundableDepositAmount() {
        return formattedRefundableDepositAmount;
    }

    public String getFormattedTotalAmount() {
        return formattedTotalAmount;
    }

    public String getFormattedToPayAmount() {
        return formattedToPayAmount;
    }

    @Override
    public void setNullable(Boolean nullable) {
        this.nullable = nullable;
    }

    @Override
    public Boolean isNullable() {
        return nullable;
    }

    @Override
    public String getPaymentCardAction() {
        return paymentCardAction;
    }

    public void setPaymentCardAction(String paymentCardAction) {
        this.paymentCardAction = paymentCardAction;
    }

    public Integer getTravelCardRefundLimit() {
        return travelCardRefundLimit;
    }

    public void setTravelCardRefundLimit(Integer travelCardRefundLimit) {
        this.travelCardRefundLimit = travelCardRefundLimit;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Boolean getInnovatorPurchase() {
        return innovatorPurchase;
    }

    public void setInnovatorPurchase(Boolean innovatorPurchase) {
        this.innovatorPurchase = innovatorPurchase;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getTravelCardIdentical() {
        return travelCardIdentical;
    }

    public void setTravelCardIdentical(String travelCardIdentical) {
        this.travelCardIdentical = travelCardIdentical;
    }

    public AddressDTO getPayeeAddress() {
        return payeeAddress;
    }

    public void setPayeeAddress(AddressDTO address) {
        this.payeeAddress = address;
    }

    public boolean getOverwriteAddress() {
        return overwriteAddress;
    }

    public void setOverwriteAddress(boolean value) {
        this.overwriteAddress = value;
    }

    public String getPayeeSortCode() {
        return payeeSortCode;
    }

    public void setPayeeSortCode(String sc) {
        payeeSortCode = sc;
    }

    public String getPayeeAccountNumber() {
        return payeeAccountNumber;
    }

    public void setPayeeAccountNumber(String an) {
        payeeAccountNumber = an;
    }

    public Long getApprovalId() {
        return approvalId;
    }

    public void setApprovalId(Long approvalId) {
        this.approvalId = approvalId;
    }

    public Integer getPreviousCreditAmountOnCard() {
        return previousCreditAmountOnCard;
    }

    public void setPreviousCreditAmountOnCard(Integer previousCreditAmountOnCard) {
        this.previousCreditAmountOnCard = previousCreditAmountOnCard;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public CartDTO getCartDTO() {
        return cartDTO;
    }

    public void setCartDTO(CartDTO cartDTO) {
        this.cartDTO = cartDTO;
    }

    public Integer getPayAsYouGoValue() {
        return payAsYouGoValue;
    }

    public void setPayAsYouGoValue(Integer payAsYouGoValue) {
        this.payAsYouGoValue = payAsYouGoValue;
    }

    public Integer getAdministrationFeeValue() {
        return administrationFeeValue;
    }

    public void setAdministrationFeeValue(Integer administrationFeeValue) {
        this.administrationFeeValue = administrationFeeValue;
    }

    public Boolean getNullable() {
        return nullable;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Date getPickUpStartDate() {
        return pickUpStartDate;
    }

    public void setPickUpStartDate(Date pickUpStartDate) {
        this.pickUpStartDate = pickUpStartDate;
    }

    public Date getPickUpEndDate() {
        return pickUpEndDate;
    }

    public void setPickUpEndDate(Date pickUpEndDate) {
        this.pickUpEndDate = pickUpEndDate;
    }

    public int getTargetPickupLocationId() {
        return targetPickupLocationId;
    }

    public void setTargetPickupLocationId(int targetPickupLocationId) {
        this.targetPickupLocationId = targetPickupLocationId;
    }
    
    public boolean isLostOrStolenMode() {
        return isLostOrStolenMode;
    }

    public void setLostOrStolenMode(boolean isLostOrStolenMode) {
        this.isLostOrStolenMode = isLostOrStolenMode;
    }

    public Boolean isFirstIssueOrder() {
        return firstIssueOrder;
    }

    public void setFirstIssueOrder(Boolean firstIssueOrder) {
        this.firstIssueOrder = firstIssueOrder;
    }

    public Boolean isReplacementOrder() {
        return replacementOrder;
    }

    public void setReplacementOrder(Boolean replacementOrder) {
        this.replacementOrder = replacementOrder;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public List<String> getRuleBreaches() {
		return ruleBreaches;
	}

	public void setRuleBreaches(List<String> ruleBreaches) {
		this.ruleBreaches = ruleBreaches;
	}

	public CartDTO getRenewItemCartDTO() {
		return renewItemCartDTO;
	}

	public void setRenewItemCartDTO(CartDTO renewItemCartDTO) {
		this.renewItemCartDTO = renewItemCartDTO;
	}

    public Integer getHotListReasonId() {
        return hotListReasonId;
    }

    public void setHotListReasonId(Integer hotListReasonId) {
        this.hotListReasonId = hotListReasonId;
    }

    public String getAutoTopUpActivity() {
        return autoTopUpActivity;
    }

    public void setAutoTopUpActivity(String autoTopUpActivity) {
        this.autoTopUpActivity = autoTopUpActivity;
    }

    
}