package com.novacroft.nemo.tfl.common.command.impl;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.novacroft.nemo.common.utils.CurrencyUtil;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.constant.HashCodeSeed;
import com.novacroft.nemo.tfl.common.transfer.GoodwillPaymentItemDTO;

public class GoodwillPaymentItemCmd implements Serializable {

    public static final String GOODWILL_PAYMENT = "GoodwillPayment";
    private static final long serialVersionUID = 8918817110571806021L;
    private Integer price;
    private String goodwillPaymentReason;

    public GoodwillPaymentItemCmd(GoodwillPaymentItemDTO goodwillPaymentItemDTO) {
        this.price = goodwillPaymentItemDTO.getPrice();
        this.goodwillPaymentReason = goodwillPaymentItemDTO.getGoodwillReasonDTO().getDescription() != null ? goodwillPaymentItemDTO
                        .getGoodwillReasonDTO().getDescription() : goodwillPaymentItemDTO.getGoodwillOtherText();

    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getGoodwillPaymentReason() {
        return goodwillPaymentReason;
    }

    public void setGoodwillPaymentReason(String goodwillPaymentReason) {
        this.goodwillPaymentReason = goodwillPaymentReason;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        GoodwillPaymentItemCmd that = (GoodwillPaymentItemCmd) object;

        return new EqualsBuilder().append(price, that.price).append(goodwillPaymentReason, that.goodwillPaymentReason).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(HashCodeSeed.GOODWILL_ITEM.initialiser(), HashCodeSeed.GOODWILL_ITEM.multiplier()).append(price)
                        .append(goodwillPaymentReason).toHashCode();
    }

    @Override
    public String toString() {
        return GOODWILL_PAYMENT + StringUtil.SPACE + "Price" + StringUtil.SPACE + CurrencyUtil.formatPenceWithHtmlCurrencySymbol(this.price)
                        + StringUtil.SPACE + "Goodwill Payment Reason"
                        + StringUtil.SPACE + goodwillPaymentReason;
    }

}
