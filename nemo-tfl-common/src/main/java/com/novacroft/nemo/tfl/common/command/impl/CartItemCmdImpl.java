package com.novacroft.nemo.tfl.common.command.impl;

import static com.novacroft.nemo.common.constant.DateConstant.SHORT_DATE_PATTERN;
import static com.novacroft.nemo.tfl.common.constant.RefundConstants.MAXIMUM_BACKDATEABLE_REFUND_PERIOD;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.format.annotation.DateTimeFormat;

import com.novacroft.nemo.common.utils.CurrencyUtil;
import com.novacroft.nemo.tfl.common.command.BusPassCmd;
import com.novacroft.nemo.tfl.common.command.PayAsYouGoCmd;
import com.novacroft.nemo.tfl.common.command.TravelCardCmd;
import com.novacroft.nemo.tfl.common.constant.CartType;
import com.novacroft.nemo.tfl.common.constant.HashCodeSeed;
import com.novacroft.nemo.tfl.common.domain.Refund;
import com.novacroft.nemo.tfl.common.formatter.PenceFormat;

public class CartItemCmdImpl implements PayAsYouGoCmd, BusPassCmd, TravelCardCmd, GoodwillPaymentCmd, ShippingMethodCmd, FailedAutoTopUpCaseCmd {

    protected Long id;
    protected String item;

    @PenceFormat
    protected Integer price;

    protected String startDate;
    protected String endDate;
    protected String emailReminder;
    protected String travelCardType;
    protected String productName;
    protected Integer startZone;
    protected Integer endZone;
    protected String refundCalculationBasis;
    protected Refund refund;

    protected Integer creditBalance;
    protected Integer autoTopUpCreditBalance;
    protected Integer autoTopUpAmt = 0;
    protected Long autoTopUpOrderId = 0L;
    protected String autoTopUpActivity;
    
    protected String formattedAutoTopUpAmount;
    protected String formattedAutoTopUpCreditBalance;
    protected Boolean autoTopUpVisible = Boolean.TRUE;
    protected Boolean autoTopUpUpdate = Boolean.FALSE;
    protected String cardNumber;
    protected Long cardId;
    protected String ticketType;
    protected String cartType = CartType.PURCHASE.code();
    protected String statusMessage = "";
    protected String rate = "";
    protected Long goodwillPaymentId;
    protected CartItemCmdImpl tradedTicket;

    protected String magneticTicketNumber;
    protected Boolean previouslyExchanged;
    protected Boolean deceasedCustomer = Boolean.FALSE;
    protected Boolean ticketUnused;
    protected Boolean backdated = Boolean.FALSE;
    protected Date dateOfCanceAndSurrender;
    protected String backdatedOtherReason;
    protected Boolean backdatedWarning;
    protected Long backdatedRefundReasonId;
    protected String passengerType;
    protected String discountType;
    protected Long prePaidTicketId;
    protected String concessionEndDate;

    protected Integer existingCreditBalance = 0;

    @DateTimeFormat(pattern = SHORT_DATE_PATTERN)
    protected DateTime exchangedDate;

    @PenceFormat
    protected Integer goodwillPrice;

    protected String formattedPrice;

    protected Long shippingMethodId;
    protected String shippingMethodType;

    protected String goodwillOtherText;

    protected Date dateOfRefund;

    protected Long cartId;
    protected Long stationId;

    protected String refundType;
    protected Date dateOfLastUsage;
    
    protected Long failedAutoTopUpCaseId;
    protected String FailedAutoTopUpCaseStatus;
    protected Boolean oysterCardWithFailedAutoTopUpCaseCheck=Boolean.FALSE;
    
    public Long getAutoTopUpOrderId() {
        return autoTopUpOrderId;
    }

    public void setAutoTopUpOrderId(Long autoTopUpOrderId) {
        this.autoTopUpOrderId = autoTopUpOrderId;
    }

    public Boolean getAutoTopUpUpdate() {
        return autoTopUpUpdate;
    }

    public void setAutoTopUpUpdate(Boolean autoTopUpUpdate) {
        this.autoTopUpUpdate = autoTopUpUpdate;
    }

    public CartItemCmdImpl() {
        super();
        deceasedCustomer = Boolean.FALSE;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    // Constructor for Bus Pass
    public CartItemCmdImpl(Long id, String item, String startDate, String endDate, String emailReminder, Integer price) {
        this.deceasedCustomer = Boolean.FALSE;
        this.id = id;
        this.item = item;
        this.startDate = startDate;
        this.endDate = endDate;
        this.emailReminder = emailReminder;
        this.price = price;
        this.formattedPrice = CurrencyUtil.formatPenceWithHtmlCurrencySymbol(price);
    }

    // Constructor for Pay As You Go
    public CartItemCmdImpl(Long id, String item, String startDate, String endDate, String emailReminder, Integer price,
                           Integer autoTopUpAmt) {
        this.deceasedCustomer = Boolean.FALSE;
        this.id = id;
        this.item = item;
        this.startDate = startDate;
        this.endDate = endDate;
        this.emailReminder = emailReminder;
        this.price = price;
        this.formattedPrice = CurrencyUtil.formatPenceWithHtmlCurrencySymbol(price);
        this.creditBalance = price;
        this.autoTopUpCreditBalance = price;
        this.autoTopUpAmt = autoTopUpAmt;
        this.formattedAutoTopUpAmount = CurrencyUtil.formatPenceWithHtmlCurrencySymbol(autoTopUpAmt);
        this.formattedAutoTopUpCreditBalance = CurrencyUtil.formatPenceWithHtmlCurrencySymbol(price);

    }

    // Constructor for Travelcard
    public CartItemCmdImpl(Long id, String item, String startDate, String endDate, String emailReminder, Integer price,
                           Integer startZone, Integer endZone, String refundCalculationBasis) {
        this.deceasedCustomer = Boolean.FALSE;
        this.id = id;
        this.item = item;
        this.startDate = startDate;
        this.endDate = endDate;
        this.emailReminder = emailReminder;
        this.price = price;
        this.formattedPrice = CurrencyUtil.formatPenceWithHtmlCurrencySymbol(price);
        this.startZone = startZone;
        this.endZone = endZone;
        this.refundCalculationBasis = refundCalculationBasis;
    }

    // Constructor for GoodwillPayment
    public CartItemCmdImpl(Integer price, Long goodwillPaymentId, String ticketType, Long cardId, String goodwillOtherText) {
        this.deceasedCustomer = Boolean.FALSE;
        this.price = price;
        this.goodwillPaymentId = goodwillPaymentId;
        this.ticketType = ticketType;
        this.cardId = cardId;
        this.goodwillOtherText = goodwillOtherText;
    }

    // Constructor for Administration Fee
    public CartItemCmdImpl(Long id, String item, Integer price) {
        this.deceasedCustomer = Boolean.FALSE;
        this.id = id;
        this.item = item;
        this.price = price;
        this.formattedPrice = CurrencyUtil.formatPenceWithHtmlCurrencySymbol(price);
    }

    // Constructor for Administration Fee
    public CartItemCmdImpl(Long id, String item, Integer price, Long cardId, String ticketType, String cartType) {
        this.deceasedCustomer = Boolean.FALSE;
        this.id = id;
        this.item = item;
        this.price = price;
        this.formattedPrice = CurrencyUtil.formatPenceWithHtmlCurrencySymbol(price);
        this.cardId = cardId;
        this.ticketType = ticketType;
        this.cartType = cartType;
    }

    //Constructor for Shipping Method
    public CartItemCmdImpl(Long id) {
        this.shippingMethodId = id;
        this.deceasedCustomer = Boolean.FALSE;
    }

    public CartItemCmdImpl(Long id, String item, String startDate, String endDate, String emailReminder, Integer price,
                           Integer startZone, Integer endZone, String refundCalculationBasis, String magneticTicketNumber,
                           Boolean ticketBackdated, Boolean ticketUnused, Boolean deceasedCustomer, String backdatedOtherReason) {
        this.id = id;
        this.item = item;
        this.startDate = startDate;
        this.endDate = endDate;
        this.emailReminder = emailReminder;
        this.price = price;
        this.formattedPrice = CurrencyUtil.formatPenceWithHtmlCurrencySymbol(price);
        this.startZone = startZone;
        this.endZone = endZone;
        this.refundCalculationBasis = refundCalculationBasis;
        this.magneticTicketNumber = magneticTicketNumber;
        this.ticketUnused = ticketUnused;
        this.backdated = ticketBackdated;
        this.deceasedCustomer = deceasedCustomer;
        this.backdatedOtherReason = backdatedOtherReason;
    }

    public CartItemCmdImpl(Long id, String item, String startDate, String endDate, String emailReminder, Integer price,
                           Integer startZone, Integer endZone, String refundCalculationBasis,
                           CartItemCmdImpl associatedProduct) {
        this.deceasedCustomer = Boolean.FALSE;
        this.id = id;
        this.item = item;
        this.startDate = startDate;
        this.endDate = endDate;
        this.emailReminder = emailReminder;
        this.price = price;
        this.formattedPrice = CurrencyUtil.formatPenceWithHtmlCurrencySymbol(price);
        this.startZone = startZone;
        this.endZone = endZone;
        this.refundCalculationBasis = refundCalculationBasis;
        this.tradedTicket = associatedProduct;
    }

    public CartItemCmdImpl(String item, Integer creditBalance, Long cardId, String ticketType) {
        this.item = item;
        this.creditBalance = creditBalance;
        this.cardId = cardId;
        this.ticketType = ticketType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
        this.formattedPrice = CurrencyUtil.formatPenceWithHtmlCurrencySymbol(price);
    }

    @Override
    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @Override
    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String getEmailReminder() {
        return emailReminder;
    }

    public void setEmailReminder(String emailReminder) {
        this.emailReminder = emailReminder;
    }

    @Override
    public String getTravelCardType() {
        return travelCardType;
    }

    public void setTravelCardType(String travelCardType) {
        this.travelCardType = travelCardType;
    }

    @Override
    public String getProductName() {
        return productName;
    }

    @Override
    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public Integer getStartZone() {
        return startZone;
    }

    @Override
    public void setStartZone(Integer startZone) {
        this.startZone = startZone;
    }

    @Override
    public Integer getEndZone() {
        return endZone;
    }

    @Override
    public void setEndZone(Integer endZone) {
        this.endZone = endZone;
    }

    @Override
    public Integer getCreditBalance() {
        return creditBalance;
    }

    public void setCreditBalance(Integer creditBalance) {
        this.creditBalance = creditBalance;
    }

    @Override
    public Integer getAutoTopUpCreditBalance() {
        return autoTopUpCreditBalance;
    }

    public void setAutoTopUpCreditBalance(Integer autoTopUpCreditBalance) {
        this.autoTopUpCreditBalance = autoTopUpCreditBalance;
        this.formattedAutoTopUpCreditBalance = CurrencyUtil.formatPenceWithHtmlCurrencySymbol(autoTopUpCreditBalance);
    }

    @Override
    public Integer getAutoTopUpAmt() {
        return autoTopUpAmt;
    }

    public void setAutoTopUpAmt(Integer autoTopUpAmt) {
        this.autoTopUpAmt = autoTopUpAmt;
        this.formattedAutoTopUpAmount = CurrencyUtil.formatPenceWithHtmlCurrencySymbol(autoTopUpAmt);

    }

    public Boolean getAutoTopUpVisible() {
        return autoTopUpVisible;
    }

    public void setAutoTopUpVisible(Boolean autoTopUpVisible) {
        this.autoTopUpVisible = autoTopUpVisible;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public String getCartType() {
        return cartType;
    }

    public void setCartType(String cartType) {
        this.cartType = cartType;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getRefundCalculationBasis() {
        return refundCalculationBasis;
    }

    public void setRefundCalculationBasis(String refundCalculationBasis) {
        this.refundCalculationBasis = refundCalculationBasis;
    }

    @Override
    public Long getGoodwillPaymentId() {
        return goodwillPaymentId;
    }

    @Override
    public void setGoodwillPaymentId(Long goodwillPaymentId) {
        this.goodwillPaymentId = goodwillPaymentId;
    }

    public Integer getGoodwillPrice() {
        return goodwillPrice;
    }

    public void setGoodwillPrice(Integer goodwillPrice) {
        this.goodwillPrice = goodwillPrice;
    }

    public String getFormattedPrice() {
        return formattedPrice;
    }

    public String getFormattedAutoTopUpAmount() {
        return formattedAutoTopUpAmount;
    }

    public String getFormattedAutoTopUpCreditBalance() {
        return formattedAutoTopUpCreditBalance;
    }

    @Override
    public Long getShippingMethodId() {
        return this.shippingMethodId;
    }

    @Override
    public void setShippingMethodId(Long shippingMethodId) {
        this.shippingMethodId = shippingMethodId;
    }

    public CartItemCmdImpl getTradedTicket() {
        return tradedTicket;
    }

    public void setTradedTicket(CartItemCmdImpl tradedTicket) {
        this.tradedTicket = tradedTicket;
    }

    public String getMagneticTicketNumber() {
        return magneticTicketNumber;
    }

    public Boolean getPreviouslyExchanged() {
        return previouslyExchanged;
    }

    public Boolean getDeceasedCustomer() {
        return deceasedCustomer;
    }

    public Boolean getTicketUnused() {
        return ticketUnused;
    }

    public Boolean getBackdated() {
        return backdated;
    }

    public void setMagneticTicketNumber(String magneticTicketNumber) {
        this.magneticTicketNumber = magneticTicketNumber;
    }

    public void setPreviouslyExchanged(Boolean previouslyExchanged) {
        this.previouslyExchanged = previouslyExchanged;
    }

    public void setDeceasedCustomer(Boolean deceasedCustomer) {
        this.deceasedCustomer = deceasedCustomer;
    }

    public void setTicketUnused(Boolean ticketUnused) {
        this.ticketUnused = ticketUnused;
    }

    public void setBackdated(Boolean backdated) {
        this.backdated = backdated;
    }

    public DateTime getExchangedDate() {
        return exchangedDate;
    }

    public void setExchangedDate(DateTime exchangeDate) {
        this.exchangedDate = exchangeDate;
    }

    public String getBackdatedOtherReason() {
        return backdatedOtherReason;
    }

    public void setBackdatedOtherReason(String backdatedOtherReason) {
        this.backdatedOtherReason = backdatedOtherReason;
    }

    public Long getBackdatedRefundReasonId() {
        return backdatedRefundReasonId;
    }

    public void setBackdatedRefundReasonId(Long backdatedRefundReasonId) {
        this.backdatedRefundReasonId = backdatedRefundReasonId;
    }

    public String getGoodwillOtherText() {
        return goodwillOtherText;
    }

    public void setGoodwillOtherText(String goodwillOtherText) {
        this.goodwillOtherText = goodwillOtherText;
    }

    public Boolean getBackdatedWarning() {
        if (this.backdated != null && this.startDate != null && this.backdated) {
            DateTime now = new DateTime();
            DateTime maxBackdateableThreshold = now.minusDays(MAXIMUM_BACKDATEABLE_REFUND_PERIOD);

            DateTimeFormatter formatter = org.joda.time.format.DateTimeFormat.forPattern(SHORT_DATE_PATTERN);
            DateTime thisStartDate = formatter.parseDateTime(this.startDate);

            return maxBackdateableThreshold.isAfter(thisStartDate);
        } else {
            return false;
        }
    }

    public Refund getRefund() {
        return refund;
    }

    public void setRefund(Refund refund) {
        this.refund = refund;
    }

    public String getShippingMethodType() {
        return shippingMethodType;
    }

    public void setShippingMethodType(String shippingMethodType) {
        this.shippingMethodType = shippingMethodType;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(HashCodeSeed.CART_ITEM.initialiser(), HashCodeSeed.CART_ITEM.multiplier()).append(startDate)
                .append(endDate).append(startZone).append(endZone).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof CartItemCmdImpl)) {
            return false;
        }
        CartItemCmdImpl other = (CartItemCmdImpl) obj;

        return new EqualsBuilder().append(endDate, other.endDate).append(endZone, other.endZone)
                .append(startDate, other.startDate).append(startZone, other.startZone).append(price, other.price).isEquals();
    }

    public Date getDateOfRefund() {
        return dateOfRefund;
    }

    public void setDateOfRefund(Date dateOfRefund) {
        this.dateOfRefund = dateOfRefund;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public String getRefundType() {
        return refundType;
    }

    public void setRefundType(String refundType) {
        this.refundType = refundType;
    }

	public Date getDateOfCanceAndSurrender() {
		return dateOfCanceAndSurrender;
	}

	public void setDateOfCanceAndSurrender(Date dateOfCanceAndSurrender) {
		this.dateOfCanceAndSurrender = dateOfCanceAndSurrender;
	}

	@Override
	public String getPassengerType() {
		return passengerType;
	}
    
	@Override
	public void setPassengerType(String passengerType) {
		this.passengerType = passengerType;
	}

	@Override
	public String getDiscountType() {
		return discountType;
	}

	@Override
	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

	@Override
	public Integer getExistingCreditBalance() {
		return existingCreditBalance ;
	}

	public void setExistingCreditBalance(Integer existingCreditBalance) {
		this.existingCreditBalance = existingCreditBalance;
	}

	@Override
    public Long getPrePaidTicketId() {
		return prePaidTicketId;
	}

	@Override
    public void setPrePaidTicketId(Long prePaidTicketId) {
		this.prePaidTicketId = prePaidTicketId;
	}

	public Date getDateOfLastUsage() {
        return dateOfLastUsage;
    }

    public void setDateOfLastUsage(Date dateOfLastUsage) {
        this.dateOfLastUsage = dateOfLastUsage;
    }

    public String getConcessionEndDate() {
        return concessionEndDate;
    }

    public void setConcessionEndDate(String concessionEndDate) {
        this.concessionEndDate = concessionEndDate;
    }
    
    public void setFailedAutoTopUpCaseId(Long failedAutoTopUpCaseId) {
    	this.failedAutoTopUpCaseId = failedAutoTopUpCaseId;
    }

	@Override
    public Long getFailedAutoTopUpCaseId() {
		return failedAutoTopUpCaseId;
	}

	@Override
    public String getFailedAutoTopUpCaseStatus() {
		return FailedAutoTopUpCaseStatus;
	}

	public void setFailedAutoTopUpCaseStatus(String failedAutoTopUpCaseStatus) {
		FailedAutoTopUpCaseStatus = failedAutoTopUpCaseStatus;
	}

	@Override
    public Boolean getOysterCardWithFailedAutoTopUpCaseCheck() {
		return oysterCardWithFailedAutoTopUpCaseCheck;
	}

	public void setOysterCardWithFailedAutoTopUpCaseCheck(Boolean oysterCardWithFailedAutoTopUpCaseCheck) {
		this.oysterCardWithFailedAutoTopUpCaseCheck = oysterCardWithFailedAutoTopUpCaseCheck;
	}

    public String getAutoTopUpActivity() {
        return autoTopUpActivity;
    }

    public void setAutoTopUpActivity(String autoTopUpActivity) {
        this.autoTopUpActivity = autoTopUpActivity;
    }
}
