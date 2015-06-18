package com.novacroft.nemo.tfl.common.action.impl;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.novacroft.nemo.tfl.common.action.ItemDTOAction;
import com.novacroft.nemo.tfl.common.action.ItemDTOActionDelegate;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.comparator.ItemDTOComparator;
import com.novacroft.nemo.tfl.common.transfer.AdministrationFeeItemDTO;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpConfigurationItemDTO;
import com.novacroft.nemo.tfl.common.transfer.CardRefundableDepositItemDTO;
import com.novacroft.nemo.tfl.common.transfer.FailedAutoTopUpResettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.GoodwillPaymentItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ShippingMethodItemDTO;

@Component("cartItemActionDelegate")
public class ItemDTOActionDelegateImpl implements ItemDTOActionDelegate {
    protected Map<Class<? extends ItemDTO>, ItemDTOAction> itemDtoClassToServiceActionMap;

    @Autowired
    protected ItemDTOAction payAsYouGoItemAction;
    @Autowired
    protected ItemDTOAction autoTopUpConfigurationItemAction;
    @Autowired
    protected ItemDTOAction productItemAction;
    @Autowired
    protected ItemDTOAction goodwillPaymentItemAction;
    @Autowired
    protected ItemDTOAction administrationFeeItemAction;
    @Autowired
    protected ItemDTOAction shippingMethodItemAction;
    @Autowired
    protected ItemDTOAction cardRefundableDepositItemAction;
    @Autowired
    protected ItemDTOAction failedAutoTopUpResettlementAction;

    @PostConstruct
    public void populateItemDtoClassToServiceActionMap() {
        itemDtoClassToServiceActionMap = new HashMap<Class<? extends ItemDTO>, ItemDTOAction>();
        itemDtoClassToServiceActionMap.put(PayAsYouGoItemDTO.class, payAsYouGoItemAction);
        itemDtoClassToServiceActionMap.put(AutoTopUpConfigurationItemDTO.class, autoTopUpConfigurationItemAction);
        itemDtoClassToServiceActionMap.put(ProductItemDTO.class, productItemAction);
        itemDtoClassToServiceActionMap.put(GoodwillPaymentItemDTO.class, goodwillPaymentItemAction);
        itemDtoClassToServiceActionMap.put(AdministrationFeeItemDTO.class, administrationFeeItemAction);
        itemDtoClassToServiceActionMap.put(ShippingMethodItemDTO.class, shippingMethodItemAction);
        itemDtoClassToServiceActionMap.put(CardRefundableDepositItemDTO.class, cardRefundableDepositItemAction);
        itemDtoClassToServiceActionMap.put(FailedAutoTopUpResettlementDTO.class, failedAutoTopUpResettlementAction);
    }

    @Override
    public ItemDTO createItemDTO(CartItemCmdImpl cartItemCmd, Class<? extends ItemDTO> itemDTOSubclass) {
        return getItemDTOActionImpl(itemDTOSubclass).createItemDTO(cartItemCmd);
    }

    @Override
    public List<ItemDTO> postProcessItems(List<ItemDTO> items,boolean isRefundCalculationRequired) {
        for (ItemDTO item : items) {
            item = getItemDTOActionImpl(item.getClass()).postProcessItemDTO(item,isRefundCalculationRequired);
        }
        return sortItemDTOsInCartDTO(items);
    }

    protected List<ItemDTO> sortItemDTOsInCartDTO(List<ItemDTO> items) {
        Collections.sort(items, new ItemDTOComparator());
        return items;
    }

    @Override
    public ItemDTO updateItemDTO(ItemDTO existingItemDTO, ItemDTO newItemDTO) {
        return getItemDTOActionImpl(existingItemDTO.getClass()).updateItemDTO(existingItemDTO, newItemDTO);
    }

    @Override
    public Boolean hasItemExpired(Date currentDate, ItemDTO itemDTO) {
        return getItemDTOActionImpl(itemDTO.getClass()).hasItemExpired(currentDate, itemDTO);
    }

    @Override
    public ItemDTOAction getItemDTOActionImpl(Class<? extends ItemDTO> itemDTOSubclass) {
        return itemDtoClassToServiceActionMap.get(itemDTOSubclass);
    }
    
    @Override
    public ItemDTO updateItemDTOForBackDatedAndDeceased(ItemDTO existingItemDTO, ItemDTO newItemDTO) {
        return getItemDTOActionImpl(existingItemDTO.getClass()).updateItemDTOForBackDatedAndDeceased(existingItemDTO, newItemDTO);
    }
}
