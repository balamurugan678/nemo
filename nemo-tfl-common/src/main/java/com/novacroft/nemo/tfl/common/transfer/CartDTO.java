package com.novacroft.nemo.tfl.common.transfer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.novacroft.nemo.common.transfer.AbstractBaseDTO;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourceReplyDTO;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourceRequestDTO;
import com.novacroft.nemo.tfl.common.util.CartUtil;

/**
 * TfL cart transfer implementation
 */

public class CartDTO extends AbstractBaseDTO implements PaymentGatewayReplyDTO {
    private static final long serialVersionUID = 4207201389431025667L;
    protected Long webaccountId;
    protected Long cardId;
    protected Date dateOfRefund;
    protected String cartType;
    protected Long approvalId;
    protected Long customerId;
    protected Boolean workFlowInFlight = Boolean.FALSE;

    protected OrderDTO order;
    protected PaymentCardSettlementDTO paymentCardSettlement;
    protected CyberSourceRequestDTO cyberSourceRequest;
    protected CyberSourceReplyDTO cyberSourceReply;

    protected List<ItemDTO> cartItems = new ArrayList<ItemDTO>();

    protected boolean ppvPickupLocationAddFlag = false;
    protected String ppvPickupLocationName;

    protected Boolean ppvRenewItemAddFlag = Boolean.FALSE;
    
    protected Long version;

    public Integer getCartTotal() {
        return CartUtil.getCartTotal(cartItems);
    }

    public Integer getCartRefundTotal() {
        return CartUtil.getCartRefundTotal(cartItems);
    }

    public Integer getCardRefundableDepositAmount() {
        return CartUtil.getCardRefundableDepositAmount(cartItems);
    }

    public Integer getCartRefundTotalWithDeposit() {
        return  CartUtil.getCartRefundTotal(cartItems) + CartUtil.getCardRefundableDepositAmount(cartItems);
    }
    
    public CardRefundableDepositItemDTO getCardRefundableDepositItem() {
        return CartUtil.getCardRefundableDepositItem(cartItems);
    }

    public boolean isAutoTopUpPresent() {
        return CartUtil.isAutoTopUpPresent(cartItems);
    }

    public Integer getAutoTopUpAmount() {
        return CartUtil.getAutoTopUpAmount(cartItems);
    }

    public PayAsYouGoItemDTO getPayAsYouGoItem() {
        return CartUtil.getPayAsYouGoItem(cartItems);
    }

    public AutoTopUpConfigurationItemDTO getAutoTopUpItem() {
        return CartUtil.getAutoTopUpItem(cartItems);
    }

    public Integer getShippingMethodAmount() {
        return CartUtil.getShippingMethodAmount(cartItems);
    }

    public ShippingMethodItemDTO getShippingMethodItem() {
        return CartUtil.getShippingMethodItem(cartItems);
    }

    public AdministrationFeeItemDTO getAdministrationFeeItem() {
        return CartUtil.getAdministrationFeeItem(cartItems);
    }

    public List<ItemDTO> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<ItemDTO> items) {
        this.cartItems = items;
    }

    public void addCartItem(ItemDTO itemDTO) {
        this.getCartItems().add(itemDTO);
    }

    public Long getWebaccountId() {
        return webaccountId;
    }

    public void setWebaccountId(Long webaccountId) {
        this.webaccountId = webaccountId;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public Date getDateOfRefund() {
        return dateOfRefund;
    }

    public void setDateOfRefund(Date dateOfRefund) {
        this.dateOfRefund = dateOfRefund;
    }

    public String getCartType() {
        return cartType;
    }

    public void setCartType(String cartType) {
        this.cartType = cartType;
    }

    public Long getApprovalId() {
        return approvalId;
    }

    public void setApprovalId(Long approvalId) {
        this.approvalId = approvalId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long id) {
        this.customerId = id;
    }

    public OrderDTO getOrder() {
        return order;
    }

    public void setOrder(OrderDTO order) {
        this.order = order;
    }

    @Override
    public PaymentCardSettlementDTO getPaymentCardSettlement() {
        return paymentCardSettlement;
    }

    public void setPaymentCardSettlement(PaymentCardSettlementDTO paymentCardSettlement) {
        this.paymentCardSettlement = paymentCardSettlement;
    }

    @Override
    public CyberSourceReplyDTO getCyberSourceReply() {
        return cyberSourceReply;
    }

    public void setCyberSourceReply(CyberSourceReplyDTO cyberSourceReply) {
        this.cyberSourceReply = cyberSourceReply;
    }

    public CyberSourceRequestDTO getCyberSourceRequest() {
        return cyberSourceRequest;
    }

    public void setCyberSourceRequest(CyberSourceRequestDTO cyberSourceRequest) {
        this.cyberSourceRequest = cyberSourceRequest;
    }

	public boolean isPpvPickupLocationAddFlag() {
		return ppvPickupLocationAddFlag;
	}

	public void setPpvPickupLocationAddFlag(boolean ppvPickupLocationAddFlag) {
		this.ppvPickupLocationAddFlag = ppvPickupLocationAddFlag;
	}

	public String getPpvPickupLocationName() {
		return ppvPickupLocationName;
	}

	public void setPpvPickupLocationName(String ppvPickupLocationName) {
		this.ppvPickupLocationName = ppvPickupLocationName;
	}

	public Boolean getWorkFlowInFlight() {
		return workFlowInFlight;
	}

	public void setWorkFlowInFlight(Boolean workFlowInFlight) {
		this.workFlowInFlight = workFlowInFlight;
	}

	public Boolean getPpvRenewItemAddFlag() {
		return ppvRenewItemAddFlag;
	}

	public void setPpvRenewItemAddFlag(Boolean ppvRenewItemAddFlag) {
		this.ppvRenewItemAddFlag = ppvRenewItemAddFlag;
	}

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
	
}
