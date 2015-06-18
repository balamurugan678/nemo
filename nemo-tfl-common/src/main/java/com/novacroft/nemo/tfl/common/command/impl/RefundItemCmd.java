package com.novacroft.nemo.tfl.common.command.impl;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.novacroft.nemo.common.utils.CurrencyUtil;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;
import com.novacroft.nemo.tfl.common.constant.HashCodeSeed;
import com.novacroft.nemo.tfl.common.constant.TicketType;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;

public class RefundItemCmd implements Serializable {

    private static final long serialVersionUID = -2599884930188881196L;
    protected String name;
    protected String startZone;
    protected String endZone;
    protected String calculationBasis;
    protected String price;
    protected Long ticketRefundAmount;
    private Boolean ticketOverlapped = Boolean.FALSE;

    public RefundItemCmd() {

    }

    public RefundItemCmd(String name, String price) {
        this.name = name;
        this.price = price;
    }
    
    public RefundItemCmd(String name, String startZone, String endZone, String calculationBasis, String price, Long refundAmount) {
        this.name = name;
        this.startZone = startZone;
        this.endZone = endZone;
        this.calculationBasis = calculationBasis;
        this.price = price;
        this.ticketRefundAmount = refundAmount;
    }

    public RefundItemCmd(ProductItemDTO productItemDTO) {
        this.name = productItemDTO.getName();
        if (!StringUtils.containsIgnoreCase(name, CartAttribute.BUS_PASS) && !StringUtils.containsIgnoreCase(name, TicketType.GOODWILL.code())) {
            this.startZone = productItemDTO.getStartZone().toString();
            this.endZone = productItemDTO.getEndZone().toString();
        }
        this.calculationBasis = productItemDTO.getRefundCalculationBasis();
        this.price = CurrencyUtil.formatPenceWithHtmlCurrencySymbol(productItemDTO.getPrice());
        this.ticketRefundAmount = productItemDTO.getRefund().getRefundAmount();
        this.ticketOverlapped = productItemDTO.isTicketOverlapped();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartZone() {
        return startZone;
    }

    public void setStartZone(String startZone) {
        this.startZone = startZone;
    }

    public String getEndZone() {
        return endZone;
    }

    public void setEndZone(String endZone) {
        this.endZone = endZone;
    }

    public String getCalculationBasis() {
        return calculationBasis;
    }

    public void setCalculationBasis(String calculationBasis) {
        this.calculationBasis = calculationBasis;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTicketRefundAmount() {
        return CurrencyUtil.formatPenceWithHtmlCurrencySymbol(ticketRefundAmount.intValue());
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        RefundItemCmd that = (RefundItemCmd) object;

        return new EqualsBuilder().append(name, that.name).append(startZone, that.startZone).append(endZone, that.endZone)
                        .append(calculationBasis, that.calculationBasis).append(price, that.price).append(ticketRefundAmount, that.ticketRefundAmount).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(HashCodeSeed.REFUND_ITEM.initialiser(), HashCodeSeed.REFUND_ITEM.multiplier()).append(name).append(startZone)
                        .append(endZone).append(calculationBasis).append(price).append(ticketRefundAmount).toHashCode();
    }

    @Override
    public String toString() {
        return name + StringUtil.SPACE + "Zones" + StringUtil.SPACE + startZone + StringUtil.SPACE + "to" + StringUtil.SPACE + endZone
                        + StringUtil.SPACE + calculationBasis + StringUtil.SPACE + price + StringUtil.SPACE + "Ticket Refund Amount"
                        + StringUtil.SPACE
                        + getTicketRefundAmount();
    }

    public Boolean isTicketOverlapped() {
        return ticketOverlapped;
    }

    public void setTicketOverlapped(Boolean ticketOverlapped) {
        this.ticketOverlapped = ticketOverlapped;
    }


}
