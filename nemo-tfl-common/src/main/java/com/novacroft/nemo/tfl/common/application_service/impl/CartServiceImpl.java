package com.novacroft.nemo.tfl.common.application_service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.hibernate.StaleObjectStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.exception.ApplicationServiceStaleDataException;
import com.novacroft.nemo.tfl.common.action.ItemDTOActionDelegate;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.SecurityService;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartType;
import com.novacroft.nemo.tfl.common.data_service.AdministrationFeeDataService;
import com.novacroft.nemo.tfl.common.data_service.AdministrationFeeItemDataService;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CartDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.ItemDataService;
import com.novacroft.nemo.tfl.common.data_service.PayAsYouGoItemDataService;
import com.novacroft.nemo.tfl.common.transfer.AdministrationFeeItemDTO;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpConfigurationItemDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.GoodwillPaymentItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;

/**
 * New Cart service implementation
 */
@Service(value = "cartService")
public class CartServiceImpl implements CartService {
    static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    @Autowired
    protected CartDataService cartDataService;
    @Autowired
    protected PayAsYouGoItemDataService payAsYouGoItemDataService;
    @Autowired
    protected AdministrationFeeDataService administrationFeeDataService;
    @Autowired
    protected AdministrationFeeItemDataService administrationFeeItemDataService;
    @Autowired
    protected ItemDataService itemDataService;
    @Autowired
    protected ItemDTOActionDelegate cartItemActionDelegate;
    @Autowired
    protected SystemParameterService systemParameterService;
    @Autowired
    protected SecurityService securityService;
    @Autowired
    protected CustomerDataService customerDataService;
    @Autowired
    protected CardDataService cardDataService;

    @Override
    public CartDTO createCart() {
        CartDTO cartDTO = new CartDTO();
        try {
            cartDTO = this.cartDataService.createOrUpdate(cartDTO);
            return this.cartDataService.findById(cartDTO.getId());
        } catch (StaleObjectStateException | HibernateOptimisticLockingFailureException staleDataException) {
            throw new ApplicationServiceStaleDataException(staleDataException);
        }
    }

    @Override
    public CartDTO createCartFromCustomerId(Long customerId) {
        CartDTO cartDTO = checkCartExistsWithCustomerId(customerId);
        if (cartDTO != null) {
            return cartDTO;
        }
        cartDTO = new CartDTO();
        cartDTO.setCustomerId(customerId);
        try {
            cartDTO = this.cartDataService.createOrUpdate(cartDTO);
            return this.cartDataService.findById(cartDTO.getId());
        } catch (StaleObjectStateException | HibernateOptimisticLockingFailureException staleDataException) {
            throw new ApplicationServiceStaleDataException(staleDataException);
        }
    }

    protected CartDTO checkCartExistsWithCustomerId(Long customerId) {
        CartDTO cartDTO = findNotInWorkFlowFlightCartByCustomerId(customerId);
        if (cartDTO != null && cartDTO.getId() != null) {
            return cartDTO;
        }
        return null;
    }

    @Override
    public CartDTO createCartFromCardId(Long cardId) {
        CartDTO cartDTO = checkCartExistsWithCardId(cardId);
        if (cartDTO != null) {
            return cartDTO;
        } else {
            cartDTO = new CartDTO();
            cartDTO.setCardId(cardId);
            try {
                cartDTO = this.cartDataService.createOrUpdate(cartDTO);
                return this.cartDataService.findById(cartDTO.getId());
            } catch (StaleObjectStateException | HibernateOptimisticLockingFailureException staleDataException) {
                throw new ApplicationServiceStaleDataException(staleDataException);
            }
        }
    }

    protected CartDTO checkCartExistsWithCardId(Long cardId) {
        CartDTO cartDTO = findNotInWorkFlowFlightCartByCardId(cardId);
        if (cartDTO != null && cartDTO.getId() != null) {
            return cartDTO;
        }
        return null;
    }

    @Override
    public CartDTO updateCart(CartDTO cartDTO) {
        try {
            cartDTO = this.cartDataService.createOrUpdate(cartDTO);
            cartDTO = findById(cartDTO.getId());
            return postProcessAndSortCartDTOWithoutRefundRecalculation(cartDTO);
        } catch (StaleObjectStateException | HibernateOptimisticLockingFailureException staleDataException) {
            throw new ApplicationServiceStaleDataException(staleDataException);
        }
    }

    @Override
    public CartDTO updateCartWithoutRefundCalculationInPostProcess(CartDTO cartDTO) {
        try {
            cartDTO = this.cartDataService.createOrUpdate(cartDTO);
            return postProcessAndSortCartDTOWithoutRefundRecalculation(cartDTO);
        } catch (StaleObjectStateException | HibernateOptimisticLockingFailureException staleDataException) {
            throw new ApplicationServiceStaleDataException(staleDataException);
        }
    }

    @Override
    public CartDTO addItem(CartDTO cartDTO, CartItemCmdImpl cartItemCmd, Class<? extends ItemDTO> itemDTOSubclass) {
        ItemDTO processedItemDTO = buildItemDTO(cartItemCmd, itemDTOSubclass);
        if (null != processedItemDTO && null != processedItemDTO.getPrice()) {
            cartDTO.addCartItem(processedItemDTO);
        }
        cartDTO = updateCart(cartDTO);
        return cartDTO;
    }

    @Override
    public CartDTO addUpdateItem(CartDTO cartDTO, CartItemCmdImpl cartItemCmd, Class<? extends ItemDTO> itemDTOSubclass) {
    	ItemDTO newItemDTO = cartItemActionDelegate.createItemDTO(cartItemCmd, itemDTOSubclass);
        ItemDTO existingItemDTO = null;
        if (CartType.RENEW.code().equals(cartDTO.getCartType())) {
            existingItemDTO = getMatchedProductItemDTOFromCartDTO(cartDTO, itemDTOSubclass, cartItemCmd.getId());
        } else {
            existingItemDTO = getItemDTOFromCartDTO(cartDTO, itemDTOSubclass);
        }
        if (null == existingItemDTO) {
            cartDTO.addCartItem(newItemDTO);
        } else {
            cartItemActionDelegate.updateItemDTO(existingItemDTO, newItemDTO);
        }
        cartDTO = updateCart(cartDTO);
        return cartDTO;
    }

    protected ItemDTO getItemDTOFromCartDTO(CartDTO cartDTO, Class<? extends ItemDTO> itemDTOSubclass) {
        for (ItemDTO itemDTO : cartDTO.getCartItems()) {
            if (itemDTO.getClass().equals(itemDTOSubclass)) {
                return itemDTO;
            }
        }
        return null;
    }

    @Override
    public ItemDTO getMatchedProductItemDTOFromCartDTO(CartDTO cartDTO, Class<? extends ItemDTO> itemDTOSubclass, long cartItemid) {
        for (ItemDTO itemDTO : cartDTO.getCartItems()) {
            if (itemDTO.getClass().equals(itemDTOSubclass) && (itemDTO.getId().equals(cartItemid))) {
                return itemDTO;
            }
        }
        return null;
    }

    @Override
    public void updateRefundCalculationBasis(Long cartId, Long itemId, String refundCalculationBasis) {
        CartDTO cartDTO = findById(cartId);
        for (ItemDTO itemDTO : cartDTO.getCartItems()) {
            if (itemDTO.getId().equals(itemId) && itemDTO instanceof ProductItemDTO) {
                ProductItemDTO productItemDTO = (ProductItemDTO) itemDTO;
                productItemDTO.setRefundCalculationBasis(refundCalculationBasis);
            }
        }
        updateCart(cartDTO);
    }

    @Override
    public CartDTO updatePrice(CartDTO cartDTO, Long itemId, Integer updatedPrice) {
        cartDTO = findById(cartDTO.getId());
        for (ItemDTO itemDTO : cartDTO.getCartItems()) {
            if (itemDTO.getId().equals(itemId)) {
                itemDTO.setPrice(updatedPrice);
            }
        }
        return updateCart(cartDTO);
    }

    @Override
    public CartDTO deleteItem(CartDTO cartDTO, Long cartItemId) {
        ItemDTO foundItem = null;
        CartDTO deletedDTO = null;
        for (ItemDTO itemDTO : cartDTO.getCartItems()) {
            if (itemDTO.getId().equals(cartItemId)) {
                foundItem = itemDTO;
                break;
            }
        }
        if (null != foundItem) {
            if (foundItem.getClass().equals(ProductItemDTO.class)) {
                deleteTradedTicketProductItemDTOIfExistsinCart(cartDTO, foundItem);
                removeItemFromCartDTO(cartDTO, foundItem);
                deletedDTO = updateCart(cartDTO);
            } else if (foundItem.getClass().equals(GoodwillPaymentItemDTO.class) || foundItem.getClass().equals(AdministrationFeeItemDTO.class)) {
                removeItemFromCartDTO(cartDTO, foundItem);
                deletedDTO = updateCart(cartDTO);
            } else {
                deletedDTO = validatePayAsYouGoDTOAndAutoTopUpDTOIfExistsinCart(cartDTO);
            }
        }
        return deletedDTO;
    }

    protected CartDTO validatePayAsYouGoDTOAndAutoTopUpDTOIfExistsinCart(CartDTO cartDTO) {
        List<ItemDTO> originalListOfItemsToBeDeleted = cartDTO.getCartItems();
        List<ItemDTO> listOfDeletedItemsToBeAddedInCartDTO = new CopyOnWriteArrayList<ItemDTO>();

        for (final Iterator<ItemDTO> iterator = originalListOfItemsToBeDeleted.iterator(); iterator.hasNext();) {
            ItemDTO itemDTO = iterator.next();
            if ((itemDTO.getClass().equals(PayAsYouGoItemDTO.class)) || (itemDTO.getClass().equals(AutoTopUpConfigurationItemDTO.class))) {
                listOfDeletedItemsToBeAddedInCartDTO.add(itemDTO);
            }
        }
        for (ItemDTO itemDTO : listOfDeletedItemsToBeAddedInCartDTO) {
            originalListOfItemsToBeDeleted.remove(itemDTO);
            cartDTO.setCartItems(originalListOfItemsToBeDeleted);
            cartDTO = updateCart(cartDTO);
            cartDTO = postProcessAndSortCartDTOAndRecalculateRefund(cartDTO);
        }
        return cartDTO;
    }

    protected void removeItemFromCartDTO(CartDTO cartDTO, ItemDTO toBedeletedItemDTO) {
        cartDTO.getCartItems().remove(toBedeletedItemDTO);
    }

    protected void deleteTradedTicketProductItemDTOIfExistsinCart(CartDTO cartDTO, ItemDTO foundItem) {
        ProductItemDTO existingProductItemDTO = (ProductItemDTO) foundItem;
        ProductItemDTO tradedProductItemDTO = (ProductItemDTO) existingProductItemDTO.getRelatedItem();
        if (null != tradedProductItemDTO && null != tradedProductItemDTO.getTradedDate()) {
            ProductItemDTO tradedProductItemDTOInCartDTO = (ProductItemDTO) getMatchedProductItemDTOFromCartDTO(cartDTO, ProductItemDTO.class,
                            tradedProductItemDTO.getId());
            if (null != tradedProductItemDTOInCartDTO) {
                removeItemFromCartDTO(cartDTO, tradedProductItemDTOInCartDTO);
            }
        }
    }

    @Override
    public CartDTO emptyCart(CartDTO cartDTO) {
        cartDTO.getCartItems().clear();
        cartDTO = updateCart(cartDTO);
        return cartDTO;
    }

    protected ItemDTO buildItemDTO(CartItemCmdImpl cartItemCmd, Class<? extends ItemDTO> itemDTOSubclass) {
        return cartItemActionDelegate.createItemDTO(cartItemCmd, itemDTOSubclass);
    }

    @Override
    public CartDTO findById(Long id) {
        CartDTO cartDTO = cartDataService.findById(id);
        return postProcessAndSortCartDTOWithoutRefundRecalculation(cartDTO);
    }
    
    @Override
    public CartDTO postProcessAndSortCartDTOAndRecalculateRefund(CartDTO cartDTO) {
        return postProcessItems(cartDTO, true);
    }

    @Override
    public CartDTO postProcessAndSortCartDTOWithoutRefundRecalculation(CartDTO cartDTO) {
        return postProcessItems(cartDTO, false);
    }

    protected CartDTO postProcessItems(CartDTO cartDTO, boolean isRefundCalculationRequired) {
        if (cartDTO != null) {
            cartDTO.setCartItems(cartItemActionDelegate.postProcessItems(cartDTO.getCartItems(), isRefundCalculationRequired));
        }
        return cartDTO;
    }

    @Override
    public CartDTO findNotInWorkFlowFlightCartByCustomerId(Long customerId) {
        CartDTO cartDTO = cartDataService.findNotInWorkFlowFlightCartByCustomerId(customerId);
        return postProcessAndSortCartDTOWithoutRefundRecalculation(cartDTO);
    }

    @Override
    public CartDTO findNotInWorkFlowFlightCartByCardId(Long cardId) {
        CartDTO cartDTO = cartDataService.findNotInWorkFlowFlightCartByCardId(cardId);
        return postProcessAndSortCartDTOWithoutRefundRecalculation(cartDTO);
    }

    @Override
    public void deleteCart(Long cartId) {
        CartDTO cartDTO = findById(cartId);
        if (null != cartDTO) {
            try {
                this.cartDataService.delete(cartDTO);
            } catch (StaleObjectStateException | HibernateOptimisticLockingFailureException staleDataException) {
                throw new ApplicationServiceStaleDataException(staleDataException);
            }
        }
    }

    @Override
    public void deleteCartForCardId(Long cardId) {
        CartDTO cartDTO = checkCartExistsWithCardId(cardId);
        if (cartDTO != null) {
            try {
                this.cartDataService.delete(cartDTO);
            } catch (StaleObjectStateException | HibernateOptimisticLockingFailureException staleDataException) {
                throw new ApplicationServiceStaleDataException(staleDataException);
            }
        }
    }

    @Override
    public void deleteCartForCustomerId(Long customerId) {
        CartDTO cartDTO = checkCartExistsWithCustomerId(customerId);
        if (cartDTO != null) {
            try {
                this.cartDataService.delete(cartDTO);
            } catch (StaleObjectStateException | HibernateOptimisticLockingFailureException staleDataException) {
                throw new ApplicationServiceStaleDataException(staleDataException);
            }
        }
    }

    @Override
    public CartDTO removeExpiredCartItems(CartDTO cartDTO) {
        Date currentDate = new Date();
        List<ItemDTO> expiredItems = new ArrayList<ItemDTO>();
        for (ItemDTO itemDTO : cartDTO.getCartItems()) {
            if (cartItemActionDelegate.hasItemExpired(currentDate, itemDTO)) {
                expiredItems.add(itemDTO);
            }
        }
        if (!expiredItems.isEmpty()) {
            cartDTO.getCartItems().removeAll(expiredItems);
            cartDTO = updateCart(cartDTO);
        }
        return cartDTO;
    }

    @Override
    public CartDTO addUpdateItems(Long cartId, CartItemCmdImpl cartItemCmd, Class<? extends ItemDTO> itemDTOSubclass) {
        CartDTO cartDTO = findById(cartId);
        ItemDTO newItemDTO = cartItemActionDelegate.createItemDTO(cartItemCmd, itemDTOSubclass);
        for (ItemDTO existingItemDTO : cartDTO.getCartItems()) {
            if (existingItemDTO.getClass().equals(itemDTOSubclass)) {
                cartItemActionDelegate.updateItemDTOForBackDatedAndDeceased(existingItemDTO, newItemDTO);
            }
        }
        cartDTO = updateCart(cartDTO);
        return cartDTO;
    }

    @Override
    public CustomerDTO findCustomerForCart(Long cartId) {
        CartDTO cartDTO = findById(cartId);
        if (cartDTO.getCustomerId() != null) {
            return customerDataService.findById(cartDTO.getCustomerId());
        }
        if (cartDTO.getCardId() != null) {
            return customerDataService.findByCardId(cartDTO.getCardId());
        }
        return null;
    }

    @Override
    public CartDTO findByApprovalId(Long approvalId) {
        CartDTO cartDTO = cartDataService.findByApprovalId(approvalId);
        return postProcessAndSortCartDTOAndRecalculateRefund(cartDTO);
    }
}
