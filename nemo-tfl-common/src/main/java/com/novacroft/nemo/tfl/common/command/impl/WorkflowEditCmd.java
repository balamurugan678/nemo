package com.novacroft.nemo.tfl.common.command.impl;

import java.util.List;

import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.common.transfer.CountryDTO;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.constant.PaymentType;
import com.novacroft.nemo.tfl.common.formatter.PenceFormat;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.util.AddressFormatUtil;

public class WorkflowEditCmd {
    protected String title;
    protected String firstName;
    protected String initials;
    protected String lastName;
    protected String username;
    protected String houseNameNumber;
    protected String street;
    protected String town;
    protected CountryDTO country;
    protected String postcode;

    protected String refundScenarioType;
    protected String refundScenarioSubType;
    protected String refundablePeriodStartDate;

    protected List<RefundItemCmd> refundItems;
    @PenceFormat
    protected Integer totalTicketAmount;
    @PenceFormat
    protected Integer ticketDeposit;
    @PenceFormat
    protected Integer ticketAdminFee;

    protected String paymentName;
    protected String paymentMethod;
    protected PaymentType paymentType;
    protected String paymentTitle;
    protected String paymentFirstName;
    protected String paymentInitials;
    protected String paymentLastName;
    protected String paymentHouseNameNumber;
    protected String paymentStreet;
    protected String paymentTown;
    protected String paymentPostCode;
    protected CountryDTO paymentCountry;
    @PenceFormat
    protected Integer amount;

    protected String reason;
    protected Integer payAsYouGoCredit;
    protected List<GoodwillPaymentItemCmd> goodwillPaymentItems;
    protected String payeeSortCode;
    protected String payeeAccountNumber;
    protected Long stationId;
    protected String station;
    protected AddressDTO payeeAddress;
    protected String targetCardNumber;

    public WorkflowEditCmd() {

    }

    public WorkflowEditCmd(String title, String firstName, String initials, String lastName, String username, String houseNameNumber, String street,
                    String town, CountryDTO country, String refundScenarioType, String refundScenarioSubType, String refundablePeriodStartDate,
                    List<RefundItemCmd> refundItems, Integer ticketAmount, Integer ticketDeposit, Integer ticketAdminFee, String paymentMethod,
                    String paymentTitle, String paymentFirstName, String paymentInitials, String paymentLastName, String paymentHouseNameNumber,
                    String paymentStreet, String paymentTown, CountryDTO paymentCountry, Integer amount, String reason) {
        this.title = title;
        this.firstName = firstName;
        this.initials = initials;
        this.lastName = lastName;
        this.username = username;
        this.houseNameNumber = houseNameNumber;
        this.street = street;
        this.town = town;
        this.country = country;

        this.refundScenarioType = refundScenarioType;
        this.refundScenarioSubType = refundScenarioSubType;
        this.refundablePeriodStartDate = refundablePeriodStartDate;

        this.refundItems = refundItems;
        this.totalTicketAmount = ticketAmount;
        this.ticketDeposit = ticketDeposit;
        this.ticketAdminFee = ticketAdminFee;

        this.paymentMethod = paymentMethod;
        this.paymentTitle = paymentTitle;
        this.paymentFirstName = paymentFirstName;
        this.paymentInitials = paymentInitials;
        this.paymentLastName = paymentLastName;
        this.paymentName = paymentTitle + StringUtil.SPACE + paymentFirstName + StringUtil.SPACE + paymentInitials + StringUtil.SPACE
                        + paymentLastName;

        this.paymentHouseNameNumber = paymentHouseNameNumber;
        this.paymentStreet = paymentStreet;
        this.paymentTown = paymentTown;
        this.paymentCountry = paymentCountry;
        this.amount = amount;
        this.reason = reason;
    }

    public WorkflowEditCmd(WorkflowCmd workflowCmd) {

        CustomerDTO customer = setCustomerNameFromWorkflowCmd(workflowCmd);
        this.username = workflowCmd.getRefundUserName();
        setCustomerAddressFromWorkflowcmd(workflowCmd);

        this.refundScenarioType = workflowCmd.getRefundScenarioType();
        this.refundScenarioSubType = workflowCmd.getRefundScenarioSubType();
        this.refundablePeriodStartDate = workflowCmd.getRefundablePeriodStartDate();

        this.refundItems = workflowCmd.getRefundItems();
        this.totalTicketAmount = workflowCmd.getTotalTicketAmount() != null ? workflowCmd.getTotalTicketAmount().intValue() : 0;
        this.ticketDeposit = workflowCmd.getTicketDeposit() != null ? workflowCmd.getTicketDeposit().intValue() : 0;
        this.ticketAdminFee = workflowCmd.getTicketAdminFee() != null ? workflowCmd.getTicketAdminFee().intValue() : 0;

        this.paymentMethod = workflowCmd.getPaymentMethod();
        this.paymentType = workflowCmd.getWorkflowItem().getRefundDetails().getPaymentType();

        if (paymentType.equals(PaymentType.AD_HOC_LOAD)) {
            this.stationId = workflowCmd.getWorkflowItem().getRefundDetails().getStationId();
            this.station = workflowCmd.getStation();
            this.targetCardNumber = workflowCmd.getTargetCardNumber();
        }

        this.payAsYouGoCredit = workflowCmd.getPayAsYouGoCredit();
        this.goodwillPaymentItems = workflowCmd.getGoodwillPaymentItems();
        CustomerDTO altPayee = workflowCmd.getWorkflowItem().getRefundDetails().getAlternativeRefundPayee();
        if (altPayee == null) {
            altPayee = customer;
        }
        setPaymentDetails(altPayee);
        setPaymentAddressFromWorkflowCmd(workflowCmd);
        this.amount = workflowCmd.getTotalRefund().intValue();
    }

    public WorkflowEditCmd(WorkflowCmd workflowCmd, CartDTO cartDTO) {

        setCustomerNameFromWorkflowCmd(workflowCmd);
        this.username = workflowCmd.getRefundUserName();
        setCustomerAddressFromWorkflowcmd(workflowCmd);
        setPaymentAddressFromWorkflowCmd(workflowCmd);
        this.amount = cartDTO.getCartRefundTotal();
        this.totalTicketAmount = workflowCmd.getTotalTicketAmount() != null ? workflowCmd.getTotalTicketAmount().intValue() : 0;
    }

    protected WorkflowEditCmd(WorkflowEditCmd workflowEditCmd, CartCmdImpl cartCmdimpl) {

        setNameDetailsFromWorkflowEditCmd(workflowEditCmd);
        setAddressDetailsFromWorkflowEditCmd(workflowEditCmd);
        this.refundScenarioType = workflowEditCmd.getRefundScenarioType();
        this.refundScenarioSubType = workflowEditCmd.getRefundScenarioSubType();
        this.refundablePeriodStartDate = workflowEditCmd.getRefundablePeriodStartDate();
        setPaymentInfoFromCart(cartCmdimpl);
        this.amount = cartCmdimpl.getCartDTO().getCartRefundTotal();
        this.reason = workflowEditCmd.getReason();
    }

    protected final void setPaymentDetails(CustomerDTO altPayee) {
   		if (altPayee != null) {
			this.paymentTitle = altPayee.getTitle();
			this.paymentFirstName = altPayee.getFirstName();
			this.paymentInitials = altPayee.getInitials();
			this.paymentLastName = altPayee.getLastName();
			if (paymentTitle != null) {
				this.paymentName = AddressFormatUtil.formatName(paymentTitle,
						paymentFirstName, paymentInitials, paymentLastName);
			} else {
				this.paymentName = AddressFormatUtil.formatName(
						paymentFirstName, paymentInitials, paymentLastName);
			}
		}
    }

    protected final CustomerDTO setCustomerNameFromWorkflowCmd(WorkflowCmd workflowcmd) {

        CustomerDTO customer = workflowcmd.getWorkflowItem().getRefundDetails().getCustomer();
        if (null != customer) {
			this.title = customer.getTitle();
			this.firstName = customer.getFirstName();
			this.initials = customer.getInitials();
			this.lastName = customer.getLastName();
		}
		return customer;
    }

    protected final void setCustomerAddressFromWorkflowcmd(WorkflowCmd workflowCmd) {

        AddressDTO customerAddress = workflowCmd.getCustomerAddressDTO();
        if (null != customerAddress) {
			this.houseNameNumber = customerAddress.getHouseNameNumber();
			this.street = customerAddress.getStreet();
			this.town = customerAddress.getTown();
			this.country = customerAddress.getCountry();
			this.postcode = customerAddress.getPostcode();
		}
    }

    protected final void setPaymentAddressFromWorkflowCmd(WorkflowCmd workflowCmd) {
    	if(PaymentType.BACS.code().equalsIgnoreCase(workflowCmd.getPaymentMethod()) 
    			|| PaymentType.CHEQUE.code().equalsIgnoreCase(workflowCmd.getPaymentMethod())){
	        AddressDTO paymentAddress = workflowCmd.getPaymentAddressDTO();
	        if (null != paymentAddress) {
				this.paymentHouseNameNumber = paymentAddress
						.getHouseNameNumber();
				this.paymentStreet = paymentAddress.getStreet();
				this.paymentTown = paymentAddress.getTown();
				this.paymentPostCode = paymentAddress.getPostcode();
				this.paymentCountry = paymentAddress.getCountry();
			}
	    }
    }


    protected final void setPaymentInfoFromCart(CartCmdImpl cartCmdimpl) {
    	if(PaymentType.BACS.code().equalsIgnoreCase(cartCmdimpl.getPaymentType()) 
    			|| PaymentType.CHEQUE.code().equalsIgnoreCase(cartCmdimpl.getPaymentType())){
	        AddressDTO paymentAddress = cartCmdimpl.getPayeeAddress();
	        if (paymentAddress != null) {
	            this.paymentHouseNameNumber = paymentAddress.getHouseNameNumber();
	            this.paymentStreet = paymentAddress.getStreet();
	            this.paymentTown = paymentAddress.getTown();
	            this.paymentPostCode = paymentAddress.getPostcode();
	            this.paymentCountry = paymentAddress.getCountry();
	        }
    	}
    }

    public final void setAddressDetailsFromWorkflowEditCmd(WorkflowEditCmd workflowEditCmd) {
        this.houseNameNumber = workflowEditCmd.getHouseNameNumber();
        this.street = workflowEditCmd.getStreet();
        this.town = workflowEditCmd.getTown();
        this.country = workflowEditCmd.getCountry();
        this.postcode = workflowEditCmd.getPostcode();
    }

    public final void setNameDetailsFromWorkflowEditCmd(WorkflowEditCmd workflowEditCmd) {
        this.title = workflowEditCmd.getTitle();
        this.firstName = workflowEditCmd.getFirstName();
        this.initials = workflowEditCmd.getInitials();
        this.lastName = workflowEditCmd.getLastName();
        this.username = workflowEditCmd.getUsername();
    }

    public final void setPaymentDetailsFromCartCmd(CartCmdImpl cartCmdimpl, WorkflowCmd workflowCmd) {
        this.ticketDeposit = workflowCmd.getWorkflowItem().getRefundDetails().getDeposit();
        this.totalTicketAmount = cartCmdimpl.getToPayAmount();
        this.paymentMethod = workflowCmd.getPaymentMethod();
        this.paymentName = workflowCmd.getPaymentName();
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRefundablePeriodStartDate() {
        return refundablePeriodStartDate;
    }

    public Integer getTotalTicketAmount() {
        return totalTicketAmount;
    }

    public void setTotalTicketAmount(Integer ticketAmount) {
        this.totalTicketAmount = ticketAmount;
    }

    public Integer getTicketDeposit() {
        return ticketDeposit;
    }

    public void setTicketDeposit(Integer ticketDeposit) {
        this.ticketDeposit = ticketDeposit;
    }

    public Integer getTicketAdminFee() {
        return ticketAdminFee;
    }

    public void setTicketAdminFee(Integer ticketAdminFee) {
        this.ticketAdminFee = ticketAdminFee;
    }

    public String getRefundScenarioType() {
        return refundScenarioType;
    }

    public void setRefundScenarioType(String refundScenarioType) {
        this.refundScenarioType = refundScenarioType;
    }

    public String getRefundScenarioSubType() {
        return refundScenarioSubType;
    }

    public void setRefundScenarioSubType(String refundScenarioSubType) {
        this.refundScenarioSubType = refundScenarioSubType;
    }

    public void setRefundablePeriodStartDate(String refundablePeriodStartDate) {
        this.refundablePeriodStartDate = refundablePeriodStartDate;
    }

    public String getHouseNameNumber() {
        return houseNameNumber;
    }

    public void setHouseNameNumber(String houseNameNumber) {
        this.houseNameNumber = houseNameNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public CountryDTO getCountry() {
        return country;
    }

    public void setCountry(CountryDTO country) {
        this.country = country;
    }

    public String getPaymentTitle() {
        return paymentTitle;
    }

    public void setPaymentTitle(String paymentTitle) {
        this.paymentTitle = paymentTitle;
    }

    public String getPaymentFirstName() {
        return paymentFirstName;
    }

    public void setPaymentFirstName(String paymentFirstName) {
        this.paymentFirstName = paymentFirstName;
    }

    public String getPaymentInitials() {
        return paymentInitials;
    }

    public void setPaymentInitials(String paymentInitials) {
        this.paymentInitials = paymentInitials;
    }

    public String getPaymentLastName() {
        return paymentLastName;
    }

    public void setPaymentLastName(String paymentLastName) {
        this.paymentLastName = paymentLastName;
    }

    public String getPaymentHouseNameNumber() {
        return paymentHouseNameNumber;
    }

    public void setPaymentHouseNameNumber(String paymentHouseNameNumber) {
        this.paymentHouseNameNumber = paymentHouseNameNumber;
    }

    public String getPaymentStreet() {
        return paymentStreet;
    }

    public void setPaymentStreet(String paymentStreet) {
        this.paymentStreet = paymentStreet;
    }

    public String getPaymentTown() {
        return paymentTown;
    }

    public void setPaymentTown(String paymentTown) {
        this.paymentTown = paymentTown;
    }

    public CountryDTO getPaymentCountry() {
        return paymentCountry;
    }

    public void setPaymentCountry(CountryDTO paymentCountry) {
        this.paymentCountry = paymentCountry;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<RefundItemCmd> getRefundItems() {
        return refundItems;
    }

    public void setRefundItems(List<RefundItemCmd> refundItems) {
        this.refundItems = refundItems;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public Integer getPayAsYouGoCredit() {
        return payAsYouGoCredit;
    }

    public void setPayAsYouGoCredit(Integer payAsYouGoCredit) {
        this.payAsYouGoCredit = payAsYouGoCredit;
    }

    public List<GoodwillPaymentItemCmd> getGoodwillPaymentItems() {
        return goodwillPaymentItems;
    }

    public void setGoodwillPaymentItems(List<GoodwillPaymentItemCmd> goodwillPaymentItems) {
        this.goodwillPaymentItems = goodwillPaymentItems;
    }

	public String getPayeeSortCode() {
		return payeeSortCode;
	}

	public void setPayeeSortCode(String payeeSortCode) {
		this.payeeSortCode = payeeSortCode;
	}

	public String getPayeeAccountNumber() {
		return payeeAccountNumber;
	}

	public void setPayeeAccountNumber(String payeeAccountNumber) {
		this.payeeAccountNumber = payeeAccountNumber;
	}

	public Long getStationId() {
		return stationId;
	}

	public void setStationId(Long stationId) {
		this.stationId = stationId;
	}

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public AddressDTO getPayeeAddress() {
		return payeeAddress;
	}

	public void setPayeeAddress(AddressDTO address) {
		this.payeeAddress = address;
	}

	public String getTargetCardNumber() {
		return targetCardNumber;
	}

	public void setTargetCardNumber(String targetCardNumber) {
		this.targetCardNumber = targetCardNumber;
	}

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

	public String getPaymentPostCode() {
		return paymentPostCode;
	}

	public void setPaymentPostCode(String paymentPostCode) {
		this.paymentPostCode = paymentPostCode;
	}

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }
}
