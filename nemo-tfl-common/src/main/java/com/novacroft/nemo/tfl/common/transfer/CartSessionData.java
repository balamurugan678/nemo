package com.novacroft.nemo.tfl.common.transfer;

/**
 * Data Transfer Object to hold attributes of the shopping cart stored in the http session.
 */
public class CartSessionData {

    private Long cartId;
    private Long orderId;
    private Long paymentCardSettlementId;
    private Long stationId;
    private String ticketType;
    private Long customerId;
    
    private Integer cartTotal = Integer.valueOf(0);
    private Integer toPayAmount = Integer.valueOf(0);
    private Integer webCreditApplyAmount = Integer.valueOf(0);
    private Integer webCreditAvailableAmount = Integer.valueOf(0);

    private Boolean quickBuyMode = Boolean.FALSE;
    private Boolean manageAutoTopUpMode = Boolean.FALSE;
    private Boolean autoTopUpVisible = Boolean.TRUE;
    private Boolean transferProductMode = Boolean.FALSE;
    private Boolean lostOrStolenMode = Boolean.FALSE;
    private Boolean firstIssueOrder = Boolean.FALSE;
    private Boolean replacementOrder = Boolean.FALSE;
    private String pageName = "";

    private String sourceCardNumber;
    private String targetCardNumber;
    
    private String securityQuestion;
    private String securityAnswer;

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public Boolean getQuickBuyMode() {
        return quickBuyMode;
    }

    public void setQuickBuyMode(Boolean quickBuyMode) {
        this.quickBuyMode = quickBuyMode;
    }

    public Long getPaymentCardSettlementId() {
        return paymentCardSettlementId;
    }

    public void setPaymentCardSettlementId(Long paymentCardSettlementId) {
        this.paymentCardSettlementId = paymentCardSettlementId;
    }

    public CartSessionData(Long cartId) {
        super();
        this.cartId = cartId;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getWebCreditApplyAmount() {
        return webCreditApplyAmount;
    }

    public void setWebCreditApplyAmount(Integer webCreditApplyAmount) {
        this.webCreditApplyAmount = webCreditApplyAmount;
    }

    public Integer getCartTotal() {
        return cartTotal;
    }

    public void setCartTotal(Integer cartTotal) {
        this.cartTotal = cartTotal;
    }

    public Integer getToPayAmount() {
        return toPayAmount;
    }

    public void setToPayAmount(Integer toPayAmount) {
        this.toPayAmount = toPayAmount;
    }

    public Boolean getManageAutoTopUpMode() {
        return manageAutoTopUpMode;
    }

    public void setManageAutoTopUpMode(Boolean manageAutoTopUpMode) {
        this.manageAutoTopUpMode = manageAutoTopUpMode;
    }

    public Boolean getTransferProductMode() {
        return transferProductMode;
    }

    public void setTransferProductMode(Boolean transferProductMode) {
        this.transferProductMode = transferProductMode;
    }

    public Boolean getAutoTopUpVisible() {
        return autoTopUpVisible;
    }

    public void setAutoTopUpVisible(Boolean autoTopUpVisible) {
        this.autoTopUpVisible = autoTopUpVisible;
    }

    public Integer getWebCreditAvailableAmount() {
        return webCreditAvailableAmount;
    }
    

    public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public void setWebCreditAvailableAmount(Integer webCreditAvailableAmount) {
        this.webCreditAvailableAmount = webCreditAvailableAmount;
    }

    public String getSourceCardNumber() {
        return sourceCardNumber;
    }

    public void setSourceCardNumber(String sourceCardNumber) {
        this.sourceCardNumber = sourceCardNumber;
    }

    public String getTargetCardNumber() {
        return targetCardNumber;
    }

    public void setTargetCardNumber(String targetCardNumber) {
        this.targetCardNumber = targetCardNumber;
    }

    public Boolean getLostOrStolenMode() {
        return lostOrStolenMode;
    }

    public void setLostOrStolenMode(Boolean lostOrStolenMode) {
        this.lostOrStolenMode = lostOrStolenMode;
    }

    public Boolean getFirstIssueOrder() {
        return firstIssueOrder;
    }

    public void setFirstIssueOrder(Boolean firstIssueOrder) {
        this.firstIssueOrder = firstIssueOrder;
    }

    public Boolean getReplacementOrder() {
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

}
