package com.novacroft.nemo.tfl.common.action.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.test_support.DateTestUtil;
//import com.novacroft.nemo.test_support.DateTestUtil;
import com.novacroft.nemo.tfl.common.action.ItemDTOAction;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.transfer.AdministrationFeeItemDTO;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpConfigurationItemDTO;
import com.novacroft.nemo.tfl.common.transfer.CardRefundableDepositItemDTO;
import com.novacroft.nemo.tfl.common.transfer.GoodwillPaymentItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ShippingMethodItemDTO;

public class ItemDTOActionDelegateTest {
    private ItemDTOActionDelegateImpl delegate;

    private ItemDTOAction mockPayAsYouGoItemAction;
    private ItemDTOAction mockAutoTopUpConfigurationItemAction;
    private ItemDTOAction mockProductItemAction;
    private ItemDTOAction mockGoodwillPaymentItemAction;
    private ItemDTOAction mockAdministrationFeeItemAction;
    private ItemDTOAction mockShippingMethodItemAction;
    private ItemDTOAction mockCardRefundableDepositItemAction;

    private ItemDTOAction mockItemDTOAction;
    private ItemDTO mockItemDTO;
    private CartItemCmdImpl mockCartItemCmd;

    private List<ItemDTO> testItems;

    @Before
    public void setUp() {
        this.delegate = mock(ItemDTOActionDelegateImpl.class);

        this.mockPayAsYouGoItemAction = mock(PayAsYouGoItemActionImpl.class);
        this.mockAutoTopUpConfigurationItemAction = mock(AutoTopUpConfigurationItemActionImpl.class);
        this.mockProductItemAction = mock(ProductItemActionImpl.class);
        this.mockGoodwillPaymentItemAction = mock(GoodwillPaymentItemActionImpl.class);
        this.mockAdministrationFeeItemAction = mock(AdministrationFeeItemActionImpl.class);
        this.mockShippingMethodItemAction = mock(ShippingMethodItemActionImpl.class);
        this.mockCardRefundableDepositItemAction = mock(CardRefundableDepositItemActionImpl.class);

        this.delegate.payAsYouGoItemAction = this.mockPayAsYouGoItemAction;
        this.delegate.autoTopUpConfigurationItemAction = this.mockAutoTopUpConfigurationItemAction;
        this.delegate.productItemAction = this.mockProductItemAction;
        this.delegate.goodwillPaymentItemAction = this.mockGoodwillPaymentItemAction;
        this.delegate.administrationFeeItemAction = this.mockAdministrationFeeItemAction;
        this.delegate.shippingMethodItemAction = this.mockShippingMethodItemAction;
        this.delegate.cardRefundableDepositItemAction = this.mockCardRefundableDepositItemAction;

        this.mockItemDTOAction = mock(ItemDTOAction.class);
        this.mockItemDTO = mock(ItemDTO.class);
        this.mockCartItemCmd = mock(CartItemCmdImpl.class);

        this.testItems = new ArrayList<ItemDTO>();
        this.testItems.add(this.mockItemDTO);
    }

    @Test
    public void getItemDTOActionImplShouldReturnPayAsYouGoItemActionImpl() {
        doCallRealMethod().when(this.delegate).populateItemDtoClassToServiceActionMap();
        this.delegate.populateItemDtoClassToServiceActionMap();
        when(this.delegate.getItemDTOActionImpl(any(Class.class))).thenCallRealMethod();
        assertTrue(delegate.getItemDTOActionImpl(PayAsYouGoItemDTO.class) instanceof PayAsYouGoItemActionImpl);
    }

    @Test
    public void getItemDTOActionImplShouldReturnAutoTopUpItemActionImpl() {
        doCallRealMethod().when(this.delegate).populateItemDtoClassToServiceActionMap();
        this.delegate.populateItemDtoClassToServiceActionMap();
        when(this.delegate.getItemDTOActionImpl(any(Class.class))).thenCallRealMethod();
        assertTrue(delegate.getItemDTOActionImpl(AutoTopUpConfigurationItemDTO.class) instanceof AutoTopUpConfigurationItemActionImpl);
    }

    @Test
    public void getItemDTOActionImplShouldReturnProductItemActionImpl() {
        doCallRealMethod().when(this.delegate).populateItemDtoClassToServiceActionMap();
        this.delegate.populateItemDtoClassToServiceActionMap();
        when(this.delegate.getItemDTOActionImpl(any(Class.class))).thenCallRealMethod();
        assertTrue(delegate.getItemDTOActionImpl(ProductItemDTO.class) instanceof ProductItemActionImpl);
    }

    @Test
    public void getItemDTOActionImplShouldReturnGoodwillPaymentItemActionImpl() {
        doCallRealMethod().when(this.delegate).populateItemDtoClassToServiceActionMap();
        this.delegate.populateItemDtoClassToServiceActionMap();
        when(this.delegate.getItemDTOActionImpl(any(Class.class))).thenCallRealMethod();
        assertTrue(delegate.getItemDTOActionImpl(GoodwillPaymentItemDTO.class) instanceof GoodwillPaymentItemActionImpl);
    }

    @Test
    public void getItemDTOActionImplShouldReturnAdministrationFeeItemActionImpl() {
        doCallRealMethod().when(this.delegate).populateItemDtoClassToServiceActionMap();
        this.delegate.populateItemDtoClassToServiceActionMap();
        when(this.delegate.getItemDTOActionImpl(any(Class.class))).thenCallRealMethod();
        assertTrue(delegate.getItemDTOActionImpl(AdministrationFeeItemDTO.class) instanceof AdministrationFeeItemActionImpl);
    }

    @Test
    public void getItemDTOActionImplShouldReturnShippingMethodItemActionImpl() {
        doCallRealMethod().when(this.delegate).populateItemDtoClassToServiceActionMap();
        this.delegate.populateItemDtoClassToServiceActionMap();
        when(this.delegate.getItemDTOActionImpl(any(Class.class))).thenCallRealMethod();
        assertTrue(delegate.getItemDTOActionImpl(ShippingMethodItemDTO.class) instanceof ShippingMethodItemActionImpl);
    }

    @Test
    public void getItemDTOActionImplShouldReturnCardRefundableDepositItemActionImpl() {
        doCallRealMethod().when(this.delegate).populateItemDtoClassToServiceActionMap();
        this.delegate.populateItemDtoClassToServiceActionMap();
        when(this.delegate.getItemDTOActionImpl(any(Class.class))).thenCallRealMethod();
        assertTrue(delegate.getItemDTOActionImpl(
                CardRefundableDepositItemDTO.class) instanceof CardRefundableDepositItemActionImpl);
    }

    @Test
    public void shouldCreateItemDTO() {
        when(this.delegate.createItemDTO(any(CartItemCmdImpl.class), any(Class.class))).thenCallRealMethod();
        when(this.delegate.getItemDTOActionImpl(any(Class.class))).thenReturn(this.mockItemDTOAction);
        when(this.mockItemDTOAction.createItemDTO(any(CartItemCmdImpl.class))).thenReturn(this.mockItemDTO);

        assertEquals(this.mockItemDTO, this.delegate.createItemDTO(this.mockCartItemCmd, ProductItemDTO.class));

        verify(this.delegate).getItemDTOActionImpl(any(Class.class));
        verify(this.mockItemDTOAction).createItemDTO(any(CartItemCmdImpl.class));
    }

    @Test
    public void shouldPostProcessItems() {
        when(this.delegate.postProcessItems(anyList(),any(Boolean.class))).thenCallRealMethod();
        when(this.delegate.getItemDTOActionImpl(any(Class.class))).thenReturn(this.mockItemDTOAction);
        when(this.mockItemDTOAction.postProcessItemDTO(any(ItemDTO.class),any(Boolean.class))).thenReturn(this.mockItemDTO);
        when(this.delegate.sortItemDTOsInCartDTO(anyList())).thenCallRealMethod();

        this.delegate.postProcessItems(this.testItems,true);

        verify(this.delegate).getItemDTOActionImpl(any(Class.class));
        verify(this.mockItemDTOAction).postProcessItemDTO(any(ItemDTO.class),any(Boolean.class));
        verify(this.delegate).sortItemDTOsInCartDTO(anyList());
    }

    @Test
    public void shouldUpdateItemDTO() {
        when(this.delegate.updateItemDTO(any(ItemDTO.class), any(ItemDTO.class))).thenCallRealMethod();
        when(this.delegate.getItemDTOActionImpl(any(Class.class))).thenReturn(this.mockItemDTOAction);
        when(this.mockItemDTOAction.updateItemDTO(any(ItemDTO.class), any(ItemDTO.class))).thenReturn(this.mockItemDTO);

        assertEquals(this.mockItemDTO, this.delegate.updateItemDTO(this.mockItemDTO, this.mockItemDTO));

        verify(this.delegate).getItemDTOActionImpl(any(Class.class));
        verify(this.mockItemDTOAction).updateItemDTO(any(ItemDTO.class), any(ItemDTO.class));
    }

    @Test
    public void shouldHasItemExpired() {
        when(this.delegate.hasItemExpired(any(Date.class), any(ItemDTO.class))).thenCallRealMethod();
        when(this.delegate.getItemDTOActionImpl(any(Class.class))).thenReturn(this.mockItemDTOAction);
        when(this.mockItemDTOAction.hasItemExpired(any(Date.class), any(ItemDTO.class))).thenReturn(Boolean.TRUE);

        assertTrue(this.delegate.hasItemExpired(DateTestUtil.getAug20(), this.mockItemDTO));

        verify(this.delegate).getItemDTOActionImpl(any(Class.class));
        verify(this.mockItemDTOAction).hasItemExpired(any(Date.class), any(ItemDTO.class));
    }
    
    @Test
    public void shouldUpdateItemDTOForBackDatedAndDeceased() {
        when(this.delegate.updateItemDTOForBackDatedAndDeceased(any(ItemDTO.class), any(ItemDTO.class))).thenCallRealMethod();
        when(this.delegate.getItemDTOActionImpl(any(Class.class))).thenReturn(this.mockItemDTOAction);
        when(this.mockItemDTOAction.updateItemDTOForBackDatedAndDeceased(any(ItemDTO.class), any(ItemDTO.class))).thenReturn(this.mockItemDTO);

        assertEquals(this.mockItemDTO, this.delegate.updateItemDTOForBackDatedAndDeceased(this.mockItemDTO, this.mockItemDTO));

        verify(this.delegate).getItemDTOActionImpl(any(Class.class));
        verify(this.mockItemDTOAction).updateItemDTOForBackDatedAndDeceased(any(ItemDTO.class), any(ItemDTO.class));
    }
}
