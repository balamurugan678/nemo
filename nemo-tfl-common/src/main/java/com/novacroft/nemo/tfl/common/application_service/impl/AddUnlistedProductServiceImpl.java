package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.common.utils.DateUtil.formatDate;
import static com.novacroft.nemo.common.utils.TravelCardDurationUtil.getTravelCardDurationFromStartAndEndDate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.constant.Durations;
import com.novacroft.nemo.tfl.common.application_service.AddUnlistedProductService;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.ProductItemType;
import com.novacroft.nemo.tfl.common.form_validator.AddUnlistedProductValidator;
import com.novacroft.nemo.tfl.common.form_validator.TravelCardRefundValidator;
import com.novacroft.nemo.tfl.common.form_validator.ZoneMappingRefundValidator;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;

@Service(value = "addUnlistedProductService")
public class AddUnlistedProductServiceImpl implements AddUnlistedProductService {

    private static final String BUS_PASS_FLAG = "Bus Pass";
    private static final String TRAVEL_CARD_REG_EX = " Travelcard$";
    private static final String BUS_PASS_REG_EX = " Bus Pass$";
    private static final String TRAVEL_CARD = " Travelcard";
    private static final String BUS_PASS = " Bus Pass";
    private static final String BLANK = "";
    private static final int TRAVEL_CARD_MIN_LENGTH = 9;

    @Autowired
    protected AddUnlistedProductValidator validator;
    @Autowired
    protected TravelCardRefundValidator travelCardRefundValidator;
    @Autowired
    protected ZoneMappingRefundValidator zoneMappingRefundValidator;

    @Override
    public void setTicketType(CartItemCmdImpl cmd) {
        if (cmd != null && cmd.getTravelCardType() != null) {
            if (cmd.getTravelCardType().length() > TRAVEL_CARD_MIN_LENGTH && cmd.getTravelCardType().endsWith(BUS_PASS_FLAG)) {
                cmd.setTicketType(ProductItemType.BUS_PASS.databaseCode());
            } else {
                cmd.setTicketType(ProductItemType.TRAVEL_CARD.databaseCode());
            }
        }
    }

    @Override
    public void setTravelcardTypeByFormTravelCardType(CartItemCmdImpl cmd) {
        if (cmd != null && cmd.getTravelCardType() != null && cmd.getTravelCardType().length() > TRAVEL_CARD_MIN_LENGTH) {
            String travelCardType = removeTravelCardSuffixFromTravelCardType(cmd.getTravelCardType());
            cmd.setTravelCardType(Durations.findByType(travelCardType).getDurationType());
        }
    }
    

    @Override
    public String removeTravelCardSuffixFromTravelCardType(String travelCardType) {
        StringBuilder travelCardTypeWithoutSuffix = new StringBuilder();
        if (travelCardType.endsWith(TRAVEL_CARD)) {
            travelCardTypeWithoutSuffix.append(travelCardType.replaceAll(TRAVEL_CARD_REG_EX, BLANK));
        } else if (travelCardType.endsWith(BUS_PASS)) {
            travelCardTypeWithoutSuffix.append(travelCardType.replaceAll(BUS_PASS_REG_EX, BLANK));
        } else {
            travelCardTypeWithoutSuffix.append(travelCardType);
        }

        return travelCardTypeWithoutSuffix.toString();
    }

    @Override
    public boolean isCartItemDuplicate(List<ItemDTO> cartItemList, CartItemCmdImpl cartItemCmdImpl) {
        for (ItemDTO item : cartItemList) {
            if (item instanceof ProductItemDTO) {
                ProductItemDTO productItem = (ProductItemDTO) item;
                if (areZonesEqual(productItem, cartItemCmdImpl) && isDurationEqual(productItem, cartItemCmdImpl)
                                && formatDate(productItem.getStartDate()).equals(cartItemCmdImpl.getStartDate())) {
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean isDurationEqual(ProductItemDTO productItem, CartItemCmdImpl cartItemCmdImpl) {
        return cartItemCmdImpl.getTravelCardType().equalsIgnoreCase(
                        getTravelCardDurationFromStartAndEndDate(formatDate(productItem.getStartDate()), formatDate(productItem.getEndDate())));
    }

    protected boolean areZonesEqual(ProductItemDTO productItem, CartItemCmdImpl cartItemCmdImpl) {
        return productItem.getStartZone().equals(cartItemCmdImpl.getStartZone()) && productItem.getEndZone().equals(cartItemCmdImpl.getEndZone());
    }
}
