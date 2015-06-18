package com.novacroft.nemo.tfl.common.transfer;

import org.codehaus.plexus.util.StringUtils;


/*
 * A cart item extension type of goodwill payment
 */
public class GoodwillPaymentItemDTO extends ItemDTO {

    protected static final String HYPHEN = " - ";
    protected static final String EMPTY_STRING = "";
    
    protected GoodwillReasonDTO goodwillReasonDTO;

    protected String goodwillOtherText;

    public GoodwillPaymentItemDTO() {
    }

    public GoodwillPaymentItemDTO(Long cardId, String createdUserId, Long id, Integer price, Boolean nullable, GoodwillReasonDTO goodwillReasonDTO) {
        this.cardId = cardId;
        this.createdUserId = createdUserId;
        this.id = id;
        this.price = price;
        this.nullable = nullable;
        this.goodwillReasonDTO = goodwillReasonDTO;
    }
    
    public GoodwillReasonDTO getGoodwillReasonDTO() {
        return goodwillReasonDTO;
    }

    public void setGoodwillReasonDTO(GoodwillReasonDTO goodwillReasonDTO) {
        this.goodwillReasonDTO = goodwillReasonDTO;
    }

    public String getGoodwillOtherText() {
        return goodwillOtherText;
    }

    public void setGoodwillOtherText(String goodwillOtherText) {
        this.goodwillOtherText = goodwillOtherText;
    }
    
    public String getName() {
	if (StringUtils.isNotEmpty(goodwillOtherText)) {
	    String name = goodwillReasonDTO.getDescription() + HYPHEN + goodwillOtherText;
	    return name;
	}
	return goodwillReasonDTO.getDescription();
    }
}
