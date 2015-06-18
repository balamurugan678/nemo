package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.test_support.CustomerTestUtil.CUSTOMER_ID_1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.hibernate.StaleObjectStateException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.exception.ApplicationServiceStaleDataException;
import com.novacroft.nemo.test_support.ApprovalTestUtil;
import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.test_support.CartItemTestUtil;
import com.novacroft.nemo.test_support.CartTestUtil;
import com.novacroft.nemo.test_support.CustomerTestUtil;
import com.novacroft.nemo.test_support.ItemTestUtil;
import com.novacroft.nemo.tfl.common.action.ItemDTOActionDelegate;
import com.novacroft.nemo.tfl.common.application_service.SecurityService;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartType;
import com.novacroft.nemo.tfl.common.data_service.AdministrationFeeDataService;
import com.novacroft.nemo.tfl.common.data_service.AdministrationFeeItemDataService;
import com.novacroft.nemo.tfl.common.data_service.CartDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.ItemDataService;
import com.novacroft.nemo.tfl.common.data_service.PayAsYouGoItemDataService;
import com.novacroft.nemo.tfl.common.transfer.AdministrationFeeItemDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;

public class CartServiceImplTest {

    private CartServiceImpl service;

    private CartDataService mockCartDataService;
    private PayAsYouGoItemDataService mockPayAsYouGoItemDataService;
    private AdministrationFeeDataService mockAdministrationFeeDataService;
    private AdministrationFeeItemDataService mockAdministrationFeeItemDataService;
    private ItemDataService mockItemDataService;
    private ItemDTOActionDelegate mockCartItemActionDelegate;
    private SystemParameterService mockSystemParameterService;
    private SecurityService mockSecurityService;

    private CartDTO mockCartDTO;
    private CartItemCmdImpl mockCartItemCmd;
    private ProductItemDTO mockProductItemDTO;
    private AdministrationFeeItemDTO mockAdministrationFeeItemDTO;
    private CustomerDataService mockCustomerDataService;

    private List<ItemDTO> testItems = new ArrayList<ItemDTO>();
    private StaleObjectStateException staleDataException;
    private HibernateOptimisticLockingFailureException springStaleDateException;

    @Before
    public void setUp() {
        service = mock(CartServiceImpl.class);

        mockCartDataService = mock(CartDataService.class);
        mockPayAsYouGoItemDataService = mock(PayAsYouGoItemDataService.class);
        mockAdministrationFeeDataService = mock(AdministrationFeeDataService.class);
        mockAdministrationFeeItemDataService = mock(AdministrationFeeItemDataService.class);
        mockItemDataService = mock(ItemDataService.class);
        mockCartItemActionDelegate = mock(ItemDTOActionDelegate.class);
        mockSystemParameterService = mock(SystemParameterService.class);
        mockSecurityService = mock(SecurityService.class);
        mockCustomerDataService = mock(CustomerDataService.class);

        service.cartDataService = mockCartDataService;
        service.payAsYouGoItemDataService = mockPayAsYouGoItemDataService;
        service.administrationFeeDataService = mockAdministrationFeeDataService;
        service.administrationFeeItemDataService = mockAdministrationFeeItemDataService;
        service.itemDataService = mockItemDataService;
        service.cartItemActionDelegate = mockCartItemActionDelegate;
        service.systemParameterService = mockSystemParameterService;
        service.securityService = mockSecurityService;
        service.customerDataService = mockCustomerDataService;

        mockCartDTO = mock(CartDTO.class);
        mockCartItemCmd = mock(CartItemCmdImpl.class);
        mockProductItemDTO = mock(ProductItemDTO.class);

        testItems.add(ItemTestUtil.getTestProductItemDTO1());
        testItems.add(ItemTestUtil.getTestPayAsYouGoItemDTO1());
        staleDataException = new StaleObjectStateException(null, null);
        springStaleDateException = new HibernateOptimisticLockingFailureException(staleDataException);
        
        when(service.postProcessAndSortCartDTOAndRecalculateRefund(any(CartDTO.class))).then(returnsFirstArg());
        when(service.postProcessAndSortCartDTOWithoutRefundRecalculation(any(CartDTO.class))).then(returnsFirstArg());
    }

    @Test
    public void shouldCreateCart() {
        when(service.createCart()).thenCallRealMethod();
        when(mockCartDataService.createOrUpdate(any(CartDTO.class))).thenReturn(mockCartDTO);

        service.createCart();

        verify(mockCartDataService).createOrUpdate(any(CartDTO.class));
    }
    @Test(expected = ApplicationServiceStaleDataException.class)
    public void shouldNotCreateCartDueStaleDataException() {
        when(service.createCart()).thenCallRealMethod();
        when(mockCartDataService.createOrUpdate(any(CartDTO.class))).thenThrow(staleDataException);

        service.createCart();

        verify(mockCartDataService).createOrUpdate(any(CartDTO.class));
    }

    @Test(expected = ApplicationServiceStaleDataException.class)
    public void shouldNotCreateCartDueSpringStaleDataException() {
        when(service.createCart()).thenCallRealMethod();
        when(mockCartDataService.createOrUpdate(any(CartDTO.class))).thenThrow(springStaleDateException);

        service.createCart();

        verify(mockCartDataService).createOrUpdate(any(CartDTO.class));
    }

    @Test
    public void shouldCreateCartFromCustomerIdForExistingCart() {
        when(service.createCartFromCustomerId(anyLong())).thenCallRealMethod();
        when(service.checkCartExistsWithCustomerId(anyLong())).thenReturn(mockCartDTO);
        when(mockCartDataService.createOrUpdate(any(CartDTO.class))).thenReturn(mockCartDTO);

        service.createCartFromCustomerId(CustomerTestUtil.CUSTOMER_ID_1);

        verify(service).checkCartExistsWithCustomerId(anyLong());
        verify(mockCartDataService, never()).createOrUpdate(any(CartDTO.class));
    }

    @Test
    public void shouldCreateCartFromCustomerIdForNonExistingCart() {
        when(service.createCartFromCustomerId(anyLong())).thenCallRealMethod();
        when(service.checkCartExistsWithCustomerId(anyLong())).thenReturn(null);
        when(mockCartDataService.createOrUpdate(any(CartDTO.class))).thenReturn(mockCartDTO);

        service.createCartFromCustomerId(CustomerTestUtil.CUSTOMER_ID_1);

        verify(service).checkCartExistsWithCustomerId(anyLong());
        verify(mockCartDataService).createOrUpdate(any(CartDTO.class));
    }

    @Test(expected = ApplicationServiceStaleDataException.class)
    public void shouldNotCreateCartFromCustomerIdDueStaleDataException() {
        when(service.createCartFromCustomerId(anyLong())).thenCallRealMethod();
        when(service.checkCartExistsWithCustomerId(anyLong())).thenReturn(null);
        when(mockCartDataService.createOrUpdate(any(CartDTO.class))).thenThrow(staleDataException);

        service.createCartFromCustomerId(CustomerTestUtil.CUSTOMER_ID_1);
    }

    @Test(expected = ApplicationServiceStaleDataException.class)
    public void shouldNotCreateCartFromCustomerIdDueSpringStaleDataException() {
        when(service.createCartFromCustomerId(anyLong())).thenCallRealMethod();
        when(service.checkCartExistsWithCustomerId(anyLong())).thenReturn(null);
        when(mockCartDataService.createOrUpdate(any(CartDTO.class))).thenThrow(springStaleDateException);

        service.createCartFromCustomerId(CustomerTestUtil.CUSTOMER_ID_1);
    }

    @Test
    public void checkCartExistsWithCustomerIdForExistingCart() {
        when(service.checkCartExistsWithCustomerId(anyLong())).thenCallRealMethod();
        when(service.findNotInWorkFlowFlightCartByCustomerId(anyLong())).thenReturn(mockCartDTO);
        when(mockCartDTO.getId()).thenReturn(CartTestUtil.CART_ID_1);

        assertNotNull(service.checkCartExistsWithCustomerId(CustomerTestUtil.CUSTOMER_ID_1));

        verify(service).findNotInWorkFlowFlightCartByCustomerId(anyLong());
    }

    @Test
    public void checkCartExistsWithCustomerIdForNonExistingCart() {
        when(service.checkCartExistsWithCustomerId(anyLong())).thenCallRealMethod();
        when(service.findNotInWorkFlowFlightCartByCustomerId(anyLong())).thenReturn(null);
        when(mockCartDTO.getId()).thenReturn(CartTestUtil.CART_ID_1);

        assertNull(service.checkCartExistsWithCustomerId(CustomerTestUtil.CUSTOMER_ID_1));

        verify(service).findNotInWorkFlowFlightCartByCustomerId(anyLong());
    }

    @Test
    public void shouldCreateCartFromCardIdForExistingCart() {
        when(service.createCartFromCardId(anyLong())).thenCallRealMethod();
        when(service.checkCartExistsWithCardId(anyLong())).thenReturn(mockCartDTO);
        when(mockCartDataService.createOrUpdate(any(CartDTO.class))).thenReturn(mockCartDTO);

        service.createCartFromCardId(CardTestUtil.CARD_ID_1);

        verify(service).checkCartExistsWithCardId(anyLong());
        verify(mockCartDataService, never()).createOrUpdate(any(CartDTO.class));
    }

    @Test
    public void shouldCreateCartFromCardIdForNonExistingCart() {
        when(service.createCartFromCardId(anyLong())).thenCallRealMethod();
        when(service.checkCartExistsWithCardId(anyLong())).thenReturn(null);
        when(mockCartDataService.createOrUpdate(any(CartDTO.class))).thenReturn(mockCartDTO);

        service.createCartFromCardId(CardTestUtil.CARD_ID_1);

        verify(service).checkCartExistsWithCardId(anyLong());
        verify(mockCartDataService).createOrUpdate(any(CartDTO.class));
    }

    @Test(expected = ApplicationServiceStaleDataException.class)
    public void shouldNotCreateCartFromCardIdDueToStaleDataException() {
        when(service.createCartFromCardId(anyLong())).thenCallRealMethod();
        when(service.checkCartExistsWithCardId(anyLong())).thenReturn(null);
        when(mockCartDataService.createOrUpdate(any(CartDTO.class))).thenThrow(staleDataException);

        service.createCartFromCardId(CardTestUtil.CARD_ID_1);
    }

    @Test(expected = ApplicationServiceStaleDataException.class)
    public void shouldNotCreateCartFromCardIdDueToSpringStaleDataException() {
        when(service.createCartFromCardId(anyLong())).thenCallRealMethod();
        when(service.checkCartExistsWithCardId(anyLong())).thenReturn(null);
        when(mockCartDataService.createOrUpdate(any(CartDTO.class))).thenThrow(springStaleDateException);

        service.createCartFromCardId(CardTestUtil.CARD_ID_1);
    }


    @Test
    public void checkCartExistsWithCardIdForExistingCart() {
        when(service.checkCartExistsWithCardId(anyLong())).thenCallRealMethod();
        when(service.findNotInWorkFlowFlightCartByCardId(anyLong())).thenReturn(mockCartDTO);
        when(mockCartDTO.getId()).thenReturn(CartTestUtil.CART_ID_1);

        assertNotNull(service.checkCartExistsWithCardId(CardTestUtil.CARD_ID_1));

        verify(service).findNotInWorkFlowFlightCartByCardId(anyLong());
    }

    @Test
    public void checkCartExistsWithCardIdForNonExistingCart() {
        when(service.checkCartExistsWithCardId(anyLong())).thenCallRealMethod();
        when(service.findNotInWorkFlowFlightCartByCardId(anyLong())).thenReturn(null);
        when(mockCartDTO.getId()).thenReturn(CartTestUtil.CART_ID_1);

        assertNull(service.checkCartExistsWithCardId(CardTestUtil.CARD_ID_1));

        verify(service).findNotInWorkFlowFlightCartByCardId(anyLong());
    }

    @Test
    public void shouldUpdateCart() {
        when(service.updateCart(any(CartDTO.class))).thenCallRealMethod();
        when(mockCartDataService.createOrUpdate(any(CartDTO.class))).thenReturn(mockCartDTO);
        when(mockCartDTO.getId()).thenReturn(CartTestUtil.CART_ID_1);
        when(service.findById(anyLong())).thenReturn(mockCartDTO);

        service.updateCart(mockCartDTO);

        verify(mockCartDataService).createOrUpdate(any(CartDTO.class));
        verify(mockCartDTO).getId();
        verify(service).findById(anyLong());
        verify(service).postProcessAndSortCartDTOWithoutRefundRecalculation(mockCartDTO);
    }

    @Test(expected = ApplicationServiceStaleDataException.class)
    public void shouldNotUpdateCartDueToStaleDataException() {
        when(service.updateCart(any(CartDTO.class))).thenCallRealMethod();
        when(mockCartDataService.createOrUpdate(any(CartDTO.class))).thenThrow(staleDataException);
        service.updateCart(mockCartDTO);
    }

    @Test(expected = ApplicationServiceStaleDataException.class)
    public void shouldNotUpdateCartDueToSpringStaleDataException() {
        when(service.updateCart(any(CartDTO.class))).thenCallRealMethod();
        when(mockCartDataService.createOrUpdate(any(CartDTO.class))).thenThrow(springStaleDateException);
        service.updateCart(mockCartDTO);
    }
    
    @Test
    public void shouldAddItemWithNotNullItem() {
        when(service.addItem(any(CartDTO.class), any(CartItemCmdImpl.class), any(Class.class))).thenCallRealMethod();
        when(service.buildItemDTO(any(CartItemCmdImpl.class), any(Class.class))).thenReturn(mockProductItemDTO);
        doNothing().when(mockCartDTO).addCartItem(any(ProductItemDTO.class));
        when(service.updateCart(any(CartDTO.class))).thenReturn(mockCartDTO);

        service.addItem(mockCartDTO, mockCartItemCmd, ProductItemDTO.class);

        verify(service).buildItemDTO(any(CartItemCmdImpl.class), any(Class.class));
        verify(mockCartDTO).addCartItem(any(ProductItemDTO.class));
        verify(service).updateCart(any(CartDTO.class));
    }

    @Test
    public void shouldAddItemWithNullItem() {
        when(service.addItem(any(CartDTO.class), any(CartItemCmdImpl.class), any(Class.class))).thenCallRealMethod();
        when(service.buildItemDTO(any(CartItemCmdImpl.class), any(Class.class))).thenReturn(null);
        doNothing().when(mockCartDTO).addCartItem(any(ProductItemDTO.class));
        when(service.updateCart(any(CartDTO.class))).thenReturn(mockCartDTO);

        service.addItem(mockCartDTO, mockCartItemCmd, ProductItemDTO.class);

        verify(service).buildItemDTO(any(CartItemCmdImpl.class), any(Class.class));
        verify(mockCartDTO, never()).addCartItem(any(ProductItemDTO.class));
        verify(service).updateCart(any(CartDTO.class));
    }

    @Test
    public void shouldAddUpdateItemWithExistingItem() {
        when(service.addUpdateItem(any(CartDTO.class), any(CartItemCmdImpl.class), any(Class.class))).thenCallRealMethod();
        when(mockCartItemActionDelegate.createItemDTO(any(CartItemCmdImpl.class), any(Class.class))).thenReturn(mockProductItemDTO);
        when(service.getItemDTOFromCartDTO(any(CartDTO.class), any(Class.class))).thenReturn(mockProductItemDTO);
        doNothing().when(mockCartDTO).addCartItem(any(ProductItemDTO.class));
        when(mockCartItemActionDelegate.updateItemDTO(any(ProductItemDTO.class), any(ProductItemDTO.class))).thenReturn(mockProductItemDTO);
        when(service.updateCart(any(CartDTO.class))).thenReturn(mockCartDTO);
        when(mockCartDTO.getCartType()).thenReturn(CartType.PURCHASE.code());
        service.addUpdateItem(mockCartDTO, mockCartItemCmd, ProductItemDTO.class);

        verify(mockCartItemActionDelegate).createItemDTO(any(CartItemCmdImpl.class), any(Class.class));
        verify(service).getItemDTOFromCartDTO(any(CartDTO.class), any(Class.class));
        verify(mockCartDTO, never()).addCartItem(any(ProductItemDTO.class));
        verify(mockCartItemActionDelegate).updateItemDTO(any(ProductItemDTO.class), any(ProductItemDTO.class));
        verify(service).updateCart(any(CartDTO.class));
    }

    @Test
    public void shouldAddUpdateItemWithRenewExistingItem() {
        when(service.addUpdateItem(any(CartDTO.class), any(CartItemCmdImpl.class), any(Class.class))).thenCallRealMethod();
        when(mockCartItemActionDelegate.createItemDTO(any(CartItemCmdImpl.class), any(Class.class))).thenReturn(mockProductItemDTO);
        when(service.getMatchedProductItemDTOFromCartDTO(any(CartDTO.class), any(Class.class), anyLong())).thenReturn(mockProductItemDTO);
        doNothing().when(mockCartDTO).addCartItem(any(ProductItemDTO.class));
        when(mockCartItemActionDelegate.updateItemDTO(any(ProductItemDTO.class), any(ProductItemDTO.class))).thenReturn(mockProductItemDTO);
        when(service.updateCart(any(CartDTO.class))).thenReturn(mockCartDTO);
        when(mockCartDTO.getCartType()).thenReturn(CartType.RENEW.code());
        service.addUpdateItem(mockCartDTO, mockCartItemCmd, ProductItemDTO.class);

        verify(mockCartItemActionDelegate).createItemDTO(any(CartItemCmdImpl.class), any(Class.class));
        verify(service).getMatchedProductItemDTOFromCartDTO(any(CartDTO.class), any(Class.class), anyLong());
        verify(mockCartDTO, never()).addCartItem(any(ProductItemDTO.class));
        verify(mockCartItemActionDelegate).updateItemDTO(any(ProductItemDTO.class), any(ProductItemDTO.class));
        verify(service).updateCart(any(CartDTO.class));
    }

    @Test
    public void shouldAddUpdateItemWithoutExistingItem() {
        when(service.addUpdateItem(any(CartDTO.class), any(CartItemCmdImpl.class), any(Class.class))).thenCallRealMethod();
        when(mockCartItemActionDelegate.createItemDTO(any(CartItemCmdImpl.class), any(Class.class))).thenReturn(mockProductItemDTO);
        when(service.getItemDTOFromCartDTO(any(CartDTO.class), any(Class.class))).thenReturn(null);
        doNothing().when(mockCartDTO).addCartItem(any(ProductItemDTO.class));
        when(mockCartItemActionDelegate.updateItemDTO(any(ProductItemDTO.class), any(ProductItemDTO.class))).thenReturn(mockProductItemDTO);
        when(service.updateCart(any(CartDTO.class))).thenReturn(mockCartDTO);
        when(mockCartDTO.getCartType()).thenReturn(CartType.PURCHASE.code());
        service.addUpdateItem(mockCartDTO, mockCartItemCmd, ProductItemDTO.class);

        verify(mockCartItemActionDelegate).createItemDTO(any(CartItemCmdImpl.class), any(Class.class));
        verify(service).getItemDTOFromCartDTO(any(CartDTO.class), any(Class.class));
        verify(mockCartDTO).addCartItem(any(ProductItemDTO.class));
        verify(mockCartItemActionDelegate, never()).updateItemDTO(any(ProductItemDTO.class), any(ProductItemDTO.class));
        verify(service).updateCart(any(CartDTO.class));
    }

    @Test
    public void shouldGetItemDTOFromCartDTOForExistingItem() {
        when(service.getItemDTOFromCartDTO(any(CartDTO.class), any(Class.class))).thenCallRealMethod();
        when(mockCartDTO.getCartItems()).thenReturn(testItems);

        assertNotNull(service.getItemDTOFromCartDTO(mockCartDTO, ProductItemDTO.class));

        verify(mockCartDTO).getCartItems();
    }

    @Test
    public void shouldGetItemDTOFromCartDTOForNonExistingItem() {
        when(service.getItemDTOFromCartDTO(any(CartDTO.class), any(Class.class))).thenCallRealMethod();
        when(mockCartDTO.getCartItems()).thenReturn(testItems);

        assertNull(service.getItemDTOFromCartDTO(mockCartDTO, AdministrationFeeItemDTO.class));

        verify(mockCartDTO).getCartItems();
    }

    @Test
    public void shouldUpdateRefundCalculationBasis() {
        doCallRealMethod().when(service).updateRefundCalculationBasis(anyLong(), anyLong(), anyString());
        when(service.findById(anyLong())).thenReturn(mockCartDTO);
        when(mockCartDTO.getCartItems()).thenReturn(testItems);
        when(service.updateCart(any(CartDTO.class))).thenReturn(mockCartDTO);

        service.updateRefundCalculationBasis(CartTestUtil.CART_ID_1, ItemTestUtil.getTestProductItemDTO1().getId(),
                        CartItemTestUtil.REFUND_CALCULATION_BASIS_ORDINARY);

        verify(service).findById(anyLong());
        verify(mockCartDTO).getCartItems();
        verify(service).updateCart(any(CartDTO.class));
    }

    @Test
    public void shouldUpdatePrice() {
        doCallRealMethod().when(service).updatePrice(any(CartDTO.class), anyLong(), anyInt());
        when(service.findById(anyLong())).thenReturn(mockCartDTO);
        when(mockCartDTO.getCartItems()).thenReturn(testItems);
        when(service.updateCart(any(CartDTO.class))).thenReturn(mockCartDTO);

        service.updatePrice(mockCartDTO, ItemTestUtil.getTestProductItemDTO1().getId(), CartItemTestUtil.ANNUAL_PRICE_1);

        verify(service).findById(anyLong());
        verify(mockCartDTO).getCartItems();
        verify(service).updateCart(any(CartDTO.class));
    }

    @Test
    public void shouldDeleteItem() {
        when(service.deleteItem(any(CartDTO.class), anyLong())).thenCallRealMethod();
        when(mockCartDTO.getCartItems()).thenReturn(testItems);
        when(service.updateCart(any(CartDTO.class))).thenReturn(mockCartDTO);

        service.deleteItem(mockCartDTO, ItemTestUtil.getTestProductItemDTO1().getId());

        verify(mockCartDTO, times(1)).getCartItems();
        verify(service).updateCart(any(CartDTO.class));
    }

    @Test
    public void shouldDeleteItemWithTradedTicket() {
        testItems = new ArrayList<ItemDTO>();

        ProductItemDTO productItemDTO = ItemTestUtil.getTestProductItemDTO1();
        ProductItemDTO tradedProductItemDTO = ItemTestUtil.getTestProductItemDTO2();
        tradedProductItemDTO.setTradedDate(new Date());
        productItemDTO.setRelatedItem(tradedProductItemDTO);
        testItems.add(productItemDTO);
        testItems.add(tradedProductItemDTO);
        when(service.deleteItem(any(CartDTO.class), anyLong())).thenCallRealMethod();
        when(service.getMatchedProductItemDTOFromCartDTO(any(CartDTO.class), any(Class.class), anyLong())).thenReturn(tradedProductItemDTO);
        doCallRealMethod().when(service).deleteTradedTicketProductItemDTOIfExistsinCart(any(CartDTO.class), any(ProductItemDTO.class));
        doCallRealMethod().when(service).removeItemFromCartDTO(any(CartDTO.class), any(ProductItemDTO.class));
        when(mockCartDTO.getCartItems()).thenReturn(testItems);
        when(service.updateCart(any(CartDTO.class))).thenReturn(mockCartDTO);
        
        service.deleteItem(mockCartDTO, ItemTestUtil.getTestProductItemDTO1().getId());

        verify(mockCartDTO, times(3)).getCartItems();
        verify(service).updateCart(any(CartDTO.class));
    }

    @Test
    public void deleteItemShouldNotDeleteForNonExistingItem() {
        when(service.deleteItem(any(CartDTO.class), anyLong())).thenCallRealMethod();
        when(mockCartDTO.getCartItems()).thenReturn(testItems);
        when(service.updateCart(any(CartDTO.class))).thenReturn(mockCartDTO);
        service.deleteItem(mockCartDTO, -99L);
        verify(mockCartDTO, times(1)).getCartItems();
    }

    @Test
    public void shouldEmptyCart() {
        when(service.emptyCart(any(CartDTO.class))).thenCallRealMethod();
        when(mockCartDTO.getCartItems()).thenReturn(Collections.EMPTY_LIST);
        when(service.updateCart(any(CartDTO.class))).thenReturn(mockCartDTO);

        service.emptyCart(mockCartDTO);

        verify(mockCartDTO).getCartItems();
        verify(service).updateCart(any(CartDTO.class));
    }

    @Test
    public void shouldBuildItemDTO() {
        when(service.buildItemDTO(any(CartItemCmdImpl.class), any(Class.class))).thenCallRealMethod();
        when(mockCartItemActionDelegate.createItemDTO(any(CartItemCmdImpl.class), any(Class.class))).thenReturn(mockProductItemDTO);

        service.buildItemDTO(mockCartItemCmd, ProductItemDTO.class);

        verify(mockCartItemActionDelegate).createItemDTO(any(CartItemCmdImpl.class), any(Class.class));
    }

    @Test
    public void shouldFindById() {
        when(service.findById(anyLong())).thenCallRealMethod();
        when(mockCartDataService.findById(anyLong())).thenReturn(mockCartDTO);

        service.findById(CartTestUtil.CART_ID_1);

        verify(mockCartDataService).findById(anyLong());
        verify(service).postProcessAndSortCartDTOWithoutRefundRecalculation(mockCartDTO);
    }

    @Test
    public void shouldInvokeDelegatePostProcessItemsForNotNullCart() {
        when(service.postProcessItems(any(CartDTO.class), any(Boolean.class))).thenCallRealMethod();
        when(mockCartItemActionDelegate.postProcessItems(anyList(), any(Boolean.class))).thenReturn(Collections.EMPTY_LIST);
        when(mockCartDTO.getCartItems()).thenReturn(Collections.EMPTY_LIST);
        doNothing().when(mockCartDTO).setCartItems(anyList());

        service.postProcessItems(mockCartDTO, true);

        verify(mockCartItemActionDelegate).postProcessItems(anyList(), any(Boolean.class));
        verify(mockCartDTO).getCartItems();
        verify(mockCartDTO).setCartItems(anyList());
    }

    @Test
    public void shouldNotInvokeDelegatePostProcessItemsForNotNullCart() {
        when(service.postProcessItems(any(CartDTO.class), any(Boolean.class))).thenCallRealMethod();
        when(mockCartItemActionDelegate.postProcessItems(anyList(), any(Boolean.class))).thenReturn(Collections.EMPTY_LIST);
        when(mockCartDTO.getCartItems()).thenReturn(Collections.EMPTY_LIST);
        doNothing().when(mockCartDTO).setCartItems(anyList());

        service.postProcessItems(null, true);

        verify(mockCartItemActionDelegate, never()).postProcessItems(anyList(), any(Boolean.class));
        verify(mockCartDTO, never()).getCartItems();
        verify(mockCartDTO, never()).setCartItems(anyList());
    }

    @Test
    public void shouldFindByCustomerId() {
        when(service.findNotInWorkFlowFlightCartByCustomerId(anyLong())).thenCallRealMethod();
        when(mockCartDataService.findByCustomerId(anyLong())).thenReturn(mockCartDTO);

        service.findNotInWorkFlowFlightCartByCustomerId(CustomerTestUtil.CUSTOMER_ID_1);

        verify(service).postProcessAndSortCartDTOWithoutRefundRecalculation(any(CartDTO.class));
    }

    @Test
    public void shouldFindByCardId() {
        when(service.findNotInWorkFlowFlightCartByCardId(anyLong())).thenCallRealMethod();
        when(mockCartDataService.findByCardId(anyLong())).thenReturn(mockCartDTO);

        service.findNotInWorkFlowFlightCartByCardId(CardTestUtil.CARD_ID_1);

        verify(service).postProcessAndSortCartDTOWithoutRefundRecalculation(any(CartDTO.class));
    }

    @Test
    public void shouldDeleteCartWithExistingCart() {
        doCallRealMethod().when(service).deleteCart(anyLong());
        when(service.findById(anyLong())).thenReturn(mockCartDTO);
        doNothing().when(mockCartDataService).delete(any(CartDTO.class));

        service.deleteCart(CartTestUtil.CART_ID_1);

        verify(service).findById(anyLong());
        verify(mockCartDataService).delete(any(CartDTO.class));
    }
    
    @Test(expected = ApplicationServiceStaleDataException.class)
    public void shouldNotDeleteCartWithExistingCartDueToStaleDataException() {
        doCallRealMethod().when(service).deleteCart(anyLong());
        when(service.findById(anyLong())).thenReturn(mockCartDTO);
        doThrow(staleDataException).when(mockCartDataService).delete(any(CartDTO.class));

        service.deleteCart(CartTestUtil.CART_ID_1);
    }

    @Test(expected = ApplicationServiceStaleDataException.class)
    public void shouldNotDeleteCartWithExistingCartDueToSpringStaleDataException() {
        doCallRealMethod().when(service).deleteCart(anyLong());
        when(service.findById(anyLong())).thenReturn(mockCartDTO);
        doThrow(springStaleDateException).when(mockCartDataService).delete(any(CartDTO.class));

        service.deleteCart(CartTestUtil.CART_ID_1);
    }
    
    @Test
    public void shouldDeleteCartWithoutExistingCart() {
        doCallRealMethod().when(service).deleteCart(anyLong());
        when(service.findById(anyLong())).thenReturn(null);
        doNothing().when(mockCartDataService).delete(any(CartDTO.class));

        service.deleteCart(CartTestUtil.CART_ID_1);

        verify(service).findById(anyLong());
        verify(mockCartDataService, never()).delete(any(CartDTO.class));
    }

    @Test
    public void shouldDeleteCartForCardIdWithExistingCart() {
        doCallRealMethod().when(service).deleteCartForCardId(anyLong());
        when(service.checkCartExistsWithCardId(anyLong())).thenReturn(CartTestUtil.getTestCartDTO1());
        when(mockCartDataService.findNotInWorkFlowFlightCartByCardId(anyLong())).thenReturn(CartTestUtil.getTestCartDTO1());
        doNothing().when(mockCartDataService).delete(any(CartDTO.class));

        service.deleteCartForCardId(CardTestUtil.CARD_ID_1);

        verify(mockCartDataService).delete(any(CartDTO.class));
    }
    
  @Test(expected = ApplicationServiceStaleDataException.class)
    public void shouldNotDeleteCartForCardIdDueToStaleDataException() {
        doCallRealMethod().when(service).deleteCartForCardId(anyLong());
        when(service.checkCartExistsWithCardId(anyLong())).thenReturn(CartTestUtil.getTestCartDTO1());
        when(mockCartDataService.findNotInWorkFlowFlightCartByCardId(anyLong())).thenReturn(CartTestUtil.getTestCartDTO1());
        doThrow(staleDataException).when(mockCartDataService).delete(any(CartDTO.class));

        service.deleteCartForCardId(CardTestUtil.CARD_ID_1);

    }

    @Test(expected = ApplicationServiceStaleDataException.class)
    public void shouldNotDeleteCartForCardIdDueToSpringStaleDataException() {
        doCallRealMethod().when(service).deleteCartForCardId(anyLong());
        when(service.checkCartExistsWithCardId(anyLong())).thenReturn(CartTestUtil.getTestCartDTO1());
        when(mockCartDataService.findNotInWorkFlowFlightCartByCardId(anyLong())).thenReturn(CartTestUtil.getTestCartDTO1());
        doThrow(springStaleDateException).when(mockCartDataService).delete(any(CartDTO.class));

        service.deleteCartForCardId(CardTestUtil.CARD_ID_1);

    }

    @Test
    public void shouldNotDeleteCartForCardIdWithoutExistingCart() {
        doCallRealMethod().when(service).deleteCartForCardId(anyLong());
        when(mockCartDataService.findByCardId(anyLong())).thenReturn(null);
        service.deleteCartForCardId(CardTestUtil.CARD_ID_1);
    }

    @Test
    public void shouldDeleteCartForCustomerIdWithExistingCart() {
        doCallRealMethod().when(service).deleteCartForCustomerId(anyLong());
        when(service.checkCartExistsWithCustomerId(anyLong())).thenReturn(mockCartDTO);
        doNothing().when(mockCartDataService).delete(any(CartDTO.class));

        service.deleteCartForCustomerId(CustomerTestUtil.CUSTOMER_ID_1);

        verify(service).checkCartExistsWithCustomerId(anyLong());
        verify(mockCartDataService).delete(any(CartDTO.class));
    }


    @Test(expected = ApplicationServiceStaleDataException.class)
    public void shouldNotDeleteCartForCustomerIdDueToStaleDataException() {
        doCallRealMethod().when(service).deleteCartForCustomerId(anyLong());
        when(service.checkCartExistsWithCustomerId(anyLong())).thenReturn(mockCartDTO);
        doThrow(staleDataException).when(mockCartDataService).delete(any(CartDTO.class));

        service.deleteCartForCustomerId(CustomerTestUtil.CUSTOMER_ID_1);
    }

    @Test(expected = ApplicationServiceStaleDataException.class)
    public void shouldNotDeleteCartForCustomerIdDueToSpringStaleDataException() {
        doCallRealMethod().when(service).deleteCartForCustomerId(anyLong());
        when(service.checkCartExistsWithCustomerId(anyLong())).thenReturn(mockCartDTO);
        doThrow(springStaleDateException).when(mockCartDataService).delete(any(CartDTO.class));

        service.deleteCartForCustomerId(CustomerTestUtil.CUSTOMER_ID_1);
    }


    @Test
    public void shouldDeleteCartForCustomerIdWithoutExistingCart() {
        doCallRealMethod().when(service).deleteCartForCustomerId(anyLong());
        when(service.checkCartExistsWithCustomerId(anyLong())).thenReturn(null);
        doNothing().when(mockCartDataService).delete(any(CartDTO.class));

        service.deleteCartForCustomerId(CustomerTestUtil.CUSTOMER_ID_1);

        verify(service).checkCartExistsWithCustomerId(anyLong());
        verify(mockCartDataService, never()).delete(any(CartDTO.class));
    }
    
    @Test(expected = ApplicationServiceStaleDataException.class)
    public void shouldNotDeleteCartDueToStaleDataException() {
        doCallRealMethod().when(service).deleteCart(anyLong());
        when(service.findById(anyLong())).thenReturn(mockCartDTO);
        doThrow(staleDataException).when(mockCartDataService).delete(any(CartDTO.class));
        service.deleteCart(CartTestUtil.CART_ID_1);
    }

    @Test(expected = ApplicationServiceStaleDataException.class)
    public void shouldNotDeleteCartDueToSpringStaleDataException() {
        doCallRealMethod().when(service).deleteCart(anyLong());
        when(service.findById(anyLong())).thenReturn(mockCartDTO);
        doThrow(springStaleDateException).when(mockCartDataService).delete(any(CartDTO.class));
        service.deleteCart(CartTestUtil.CART_ID_1);
    }

    @Test
    public void shouldRemoveExpiredCartItems() {
        when(service.removeExpiredCartItems(any(CartDTO.class))).thenCallRealMethod();
        when(mockCartDTO.getCartItems()).thenReturn(testItems);
        when(mockCartItemActionDelegate.hasItemExpired(any(Date.class), any(ItemDTO.class))).thenReturn(Boolean.TRUE);

        service.removeExpiredCartItems(mockCartDTO);

        verify(mockCartDTO, times(2)).getCartItems();
        verify(mockCartItemActionDelegate, atLeastOnce()).hasItemExpired(any(Date.class), any(ItemDTO.class));
    }

    @Test
    public void removeExpiredCartItemsShouldNotRemoveWithNoExpiredItems() {
        when(service.removeExpiredCartItems(any(CartDTO.class))).thenCallRealMethod();
        when(mockCartDTO.getCartItems()).thenReturn(testItems);
        when(mockCartItemActionDelegate.hasItemExpired(any(Date.class), any(ItemDTO.class))).thenReturn(Boolean.FALSE);

        service.removeExpiredCartItems(mockCartDTO);

        verify(mockCartDTO, times(1)).getCartItems();
        verify(mockCartItemActionDelegate, atLeastOnce()).hasItemExpired(any(Date.class), any(ItemDTO.class));
    }

    @Test
    public void shouldAddUpdateItemsWithExistingItem() {
        when(service.addUpdateItems(anyLong(), any(CartItemCmdImpl.class), any(Class.class))).thenCallRealMethod();
        when(service.findById(anyLong())).thenReturn(mockCartDTO);
        when(mockCartItemActionDelegate.createItemDTO(any(CartItemCmdImpl.class), any(Class.class))).thenReturn(mockProductItemDTO);
        when(mockCartDTO.getCartItems()).thenReturn(testItems);
        doNothing().when(mockCartDTO).addCartItem(any(ProductItemDTO.class));
        when(mockCartItemActionDelegate.updateItemDTOForBackDatedAndDeceased(any(ProductItemDTO.class), any(ProductItemDTO.class))).thenReturn(
                        mockProductItemDTO);
        when(service.updateCart(any(CartDTO.class))).thenReturn(mockCartDTO);

        service.addUpdateItems(mockCartDTO.getId(), mockCartItemCmd, ProductItemDTO.class);

        verify(mockCartItemActionDelegate).createItemDTO(any(CartItemCmdImpl.class), any(Class.class));
        verify(mockCartDTO, never()).addCartItem(any(ProductItemDTO.class));
        verify(mockCartItemActionDelegate).updateItemDTOForBackDatedAndDeceased(any(ProductItemDTO.class), any(ProductItemDTO.class));
        verify(service).updateCart(any(CartDTO.class));
    }

    @Test
    public void shouldAddUpdateItemsWithoutExistingItem() {
        when(service.addUpdateItems(anyLong(), any(CartItemCmdImpl.class), any(Class.class))).thenCallRealMethod();
        when(service.findById(anyLong())).thenReturn(mockCartDTO);
        when(mockCartItemActionDelegate.createItemDTO(any(CartItemCmdImpl.class), any(Class.class))).thenReturn(mockProductItemDTO);
        doNothing().when(mockCartDTO).addCartItem(any(ProductItemDTO.class));
        when(mockCartItemActionDelegate.updateItemDTOForBackDatedAndDeceased(any(ProductItemDTO.class), any(ProductItemDTO.class))).thenReturn(
                        mockProductItemDTO);
        when(service.updateCart(any(CartDTO.class))).thenReturn(mockCartDTO);

        service.addUpdateItems(mockCartDTO.getId(), mockCartItemCmd, ProductItemDTO.class);

        verify(mockCartItemActionDelegate).createItemDTO(any(CartItemCmdImpl.class), any(Class.class));
        verify(mockCartItemActionDelegate, never()).updateItemDTOForBackDatedAndDeceased(any(ProductItemDTO.class), any(ProductItemDTO.class));
        verify(service).updateCart(any(CartDTO.class));
    }

    @Test
    public void shouldNotGetMatchedProductItemDTOFromCartDTODueToNoMatchingClass() {
        CartDTO cartDTO = CartTestUtil.getNewCartDTOWithPAYGAndATUItems();
        Long cartItemId = cartDTO.getCartItems().get(0).getId();
        when(service.getMatchedProductItemDTOFromCartDTO(any(CartDTO.class), any(Class.class), anyLong())).thenCallRealMethod();
        when(mockCartItemCmd.getId()).thenReturn(cartItemId);
        ItemDTO itemDTO = service.getMatchedProductItemDTOFromCartDTO(cartDTO, ProductItemDTO.class, mockCartItemCmd.getId());
        assertNull(itemDTO);
    }

    @Test
    public void shouldGetMatchedProductItemDTOFromCartDTODueToNoMatchingId() {
        CartDTO cartDTO = CartTestUtil.getNewCartDTOWithPAYGAndATUItems();
        cartDTO.addCartItem(CartTestUtil.getNewProductItemDTO());
        Long cartItemId = ItemTestUtil.ITEM_ID1;
        when(service.getMatchedProductItemDTOFromCartDTO(any(CartDTO.class), any(Class.class), anyLong())).thenCallRealMethod();
        when(mockCartItemCmd.getId()).thenReturn(cartItemId);
        ItemDTO itemDTO = service.getMatchedProductItemDTOFromCartDTO(cartDTO, ProductItemDTO.class, mockCartItemCmd.getId());
        assertNull(itemDTO);
    }

    @Test
    public void shouldGetMatchedProductItemDTOFromCartDTO() {
        CartDTO cartDTO = CartTestUtil.getNewCartDTOWithPAYGAndATUItems();
        cartDTO.addCartItem(CartTestUtil.getNewProductItemDTO());
        Long cartItemId = cartDTO.getCartItems().get(1).getId();
        when(service.getMatchedProductItemDTOFromCartDTO(any(CartDTO.class), any(Class.class), anyLong())).thenCallRealMethod();
        when(mockCartItemCmd.getId()).thenReturn(cartItemId);
        ItemDTO itemDTO = service.getMatchedProductItemDTOFromCartDTO(cartDTO, ProductItemDTO.class, mockCartItemCmd.getId());
        assertNotNull(itemDTO);
        assertEquals(itemDTO.getId().longValue(), cartItemId.longValue());
    }

    @Test
    public void shouldFindCustomerForCartWithCustomerId() {
        doCallRealMethod().when(service).findCustomerForCart(anyLong());
        when(service.findById(anyLong())).thenReturn(mockCartDTO);
        when(mockCartDTO.getCustomerId()).thenReturn(CustomerTestUtil.CUSTOMER_ID_1);
        when(mockCustomerDataService.findById(any(Long.class))).thenReturn(CustomerTestUtil.getTestCustomerDTO1());
        assertEquals(CustomerTestUtil.getTestCustomerDTO1().getId(), service.findCustomerForCart(CartTestUtil.CART_ID_1).getId());
        verify(mockCustomerDataService).findById(any(Long.class));
    }

    @Test
    public void shouldFindCustomerForCartWithCardId() {
        doCallRealMethod().when(service).findCustomerForCart(anyLong());
        when(service.findById(anyLong())).thenReturn(mockCartDTO);
        when(mockCartDTO.getCustomerId()).thenReturn(null);
        when(mockCartDTO.getCardId()).thenReturn(CardTestUtil.CARD_ID);
        when(mockCustomerDataService.findByCardId(any(Long.class))).thenReturn(CustomerTestUtil.getTestCustomerDTO1());
        assertEquals(CustomerTestUtil.getTestCustomerDTO1().getId(), service.findCustomerForCart(CartTestUtil.CART_ID_1).getId());
        verify(mockCustomerDataService).findByCardId(any(Long.class));
    }
    
    @Test
    public void findCustomerForCartShouldReturnNull() {
        doCallRealMethod().when(service).findCustomerForCart(anyLong());
        when(service.findById(anyLong())).thenReturn(mockCartDTO);
        when(mockCartDTO.getCustomerId()).thenReturn(null);
        when(mockCartDTO.getCardId()).thenReturn(null);
        
        assertNull(service.findCustomerForCart(CUSTOMER_ID_1));
    }

    @Test
    public void shouldFindByApprovalId() {
        doCallRealMethod().when(service).findByApprovalId(anyLong());
        when(mockCartDataService.findByApprovalId(anyLong())).thenReturn(mockCartDTO);
        
        service.findByApprovalId(ApprovalTestUtil.APPROVAL_ID);
        
        verify(mockCartDataService).findByApprovalId(anyLong());
        verify(service).postProcessAndSortCartDTOAndRecalculateRefund(mockCartDTO);
    }

    @Test
    public void shouldDeletePayAsYouGoItem() {
        when(service.deleteItem(any(CartDTO.class), anyLong())).thenCallRealMethod();
        List<ItemDTO> payasyougoTestItemList = new ArrayList<ItemDTO>();
        payasyougoTestItemList.add(ItemTestUtil.getTestPayAsYouGoItemDTO1());
        when(mockCartDTO.getCartItems()).thenReturn(payasyougoTestItemList);
        when(service.validatePayAsYouGoDTOAndAutoTopUpDTOIfExistsinCart(any(CartDTO.class))).thenCallRealMethod();
        when(service.updateCart(any(CartDTO.class))).thenReturn(mockCartDTO);
        
        service.deleteItem(mockCartDTO, ItemTestUtil.getTestPayAsYouGoItemDTO1().getId());
        verify(service).updateCart(any(CartDTO.class));
    }

    @Test
    public void shouldDeleteAutoTopUpItem() {
        when(service.deleteItem(any(CartDTO.class), anyLong())).thenCallRealMethod();
        List<ItemDTO> autoTopUpItemList = new ArrayList<ItemDTO>();
        autoTopUpItemList.add(ItemTestUtil.getTestAutoTopUpItemDTO());
        when(mockCartDTO.getCartItems()).thenReturn(autoTopUpItemList);
        when(service.validatePayAsYouGoDTOAndAutoTopUpDTOIfExistsinCart(any(CartDTO.class))).thenCallRealMethod();
        when(service.updateCart(any(CartDTO.class))).thenReturn(mockCartDTO);
        
        service.deleteItem(mockCartDTO, ItemTestUtil.getTestAutoTopUpItemDTO().getId());
        
        verify(service).updateCart(any(CartDTO.class));
    }

    @Test
    public void shouldUpdateCartWithoutRefundCalculationInPostProcess() {
        when(service.updateCartWithoutRefundCalculationInPostProcess(mockCartDTO)).thenCallRealMethod();
        when(mockCartDataService.createOrUpdate(any(CartDTO.class))).thenReturn(mockCartDTO);
        
        service.updateCartWithoutRefundCalculationInPostProcess(mockCartDTO);
        
        verify(service).postProcessAndSortCartDTOWithoutRefundRecalculation(mockCartDTO);
    }
    
    @Test(expected = ApplicationServiceStaleDataException.class)
    public void shouldNotUpdateCartWithoutRefundCalcDueToStaleDataException() {
        when(service.updateCartWithoutRefundCalculationInPostProcess(mockCartDTO)).thenCallRealMethod();
        when(mockCartDataService.createOrUpdate(any(CartDTO.class))).thenThrow(staleDataException);
        service.updateCartWithoutRefundCalculationInPostProcess(mockCartDTO);
    }

    @Test(expected = ApplicationServiceStaleDataException.class)
    public void shouldNotUpdateCartWithoutRefundCalcDueToSpringStaleDataException() {
        when(service.updateCartWithoutRefundCalculationInPostProcess(mockCartDTO)).thenCallRealMethod();
        when(mockCartDataService.createOrUpdate(any(CartDTO.class))).thenThrow(springStaleDateException);
        service.updateCartWithoutRefundCalculationInPostProcess(mockCartDTO);
    }

    @Test
    public void shouldDeleteGoodWillItem() {
        when(service.deleteItem(any(CartDTO.class), anyLong())).thenCallRealMethod();
        List<ItemDTO> goodWillItemList = new ArrayList<ItemDTO>();
        goodWillItemList.add(ItemTestUtil.getTestGoodwillItem1());
        when(mockCartDTO.getCartItems()).thenReturn(goodWillItemList);
        when(service.updateCart(any(CartDTO.class))).thenReturn(mockCartDTO);
        service.deleteItem(mockCartDTO, ItemTestUtil.getTestGoodwillItem1().getId());
        verify(service).updateCart(any(CartDTO.class));
    }

    @Test
    public void shouldDeleteItemWithAdministationFeeItemDTOTest() {
        testItems.add(ItemTestUtil.getTestAdministrationFeeItemDTO1());
        when(service.deleteItem(any(CartDTO.class), anyLong())).thenCallRealMethod();
        when(mockCartDTO.getCartItems()).thenReturn(testItems);
        when(service.updateCart(any(CartDTO.class))).thenReturn(mockCartDTO);

        service.deleteItem(mockCartDTO, ItemTestUtil.getTestAdministrationFeeItemDTO1().getId());

        verify(mockCartDTO, times(1)).getCartItems();
        verify(service).updateCart(any(CartDTO.class));
    }

    @Test
    public void findCustomerForCartWithNoCustomerIdShouldReturnNullTest() {
        when(service.findById(any(Long.class))).thenReturn(new CartDTO());
        assertNull(service.findCustomerForCart(CustomerTestUtil.CUSTOMER_ID_1));
    }
    
    @Test
    public void shouldInvokeDelegateWithRecalculateRefundFlagIsTrue() {
        when(service.postProcessAndSortCartDTOAndRecalculateRefund(mockCartDTO)).thenCallRealMethod();
        when(mockCartItemActionDelegate.postProcessItems(anyListOf(ItemDTO.class), anyBoolean())).thenReturn(testItems);
        doNothing().when(mockCartDTO).setCartItems(anyListOf(ItemDTO.class));
        
        service.postProcessAndSortCartDTOAndRecalculateRefund(mockCartDTO);
        
        verify(service).postProcessItems(mockCartDTO, true);
    }
    
    @Test
    public void shouldInvokeDelegateWithRecalculateRefundFlagIsFalse() {
        when(service.postProcessAndSortCartDTOWithoutRefundRecalculation(mockCartDTO)).thenCallRealMethod();
        when(mockCartItemActionDelegate.postProcessItems(anyListOf(ItemDTO.class), anyBoolean())).thenReturn(testItems);
        doNothing().when(mockCartDTO).setCartItems(anyListOf(ItemDTO.class));
        
        service.postProcessAndSortCartDTOWithoutRefundRecalculation(mockCartDTO);
        
        verify(service).postProcessItems(mockCartDTO, false);
    }
}