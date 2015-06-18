package com.novacroft.nemo.tfl.common.action.impl;

import static com.novacroft.nemo.test_support.CartItemTestUtil.ANNUAL_START_DATE_1;
import static com.novacroft.nemo.test_support.GoodwillPaymentTestUtil.getGoodwillPaymentItemDTOItem1;
import static com.novacroft.nemo.test_support.GoodwillPaymentTestUtil.getGoodwillPaymentItemDTOItem2;
import static com.novacroft.nemo.test_support.GoodwillReasonTestUtil.GOODWILL_REASON_OTHER;
import static com.novacroft.nemo.test_support.GoodwillReasonTestUtil.getGoodwillReasonDTO1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.converter.impl.GoodwillPaymentItemDTOConverterImpl;
import com.novacroft.nemo.tfl.common.data_service.GoodwillReasonDataService;
import com.novacroft.nemo.tfl.common.transfer.GoodwillPaymentItemDTO;
import com.novacroft.nemo.tfl.common.transfer.GoodwillReasonDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;

public class GoodwillPaymentItemActionTest {

    private GoodwillPaymentItemActionImpl service;
    private CartItemCmdImpl cartItemCmd;
    private GoodwillReasonDataService mockGoodwillReasonDataService;
    private GoodwillPaymentItemDTOConverterImpl mockGoodwillPaymentItemDTOConverter;

    @Before
    public void setUp() {
        service = new GoodwillPaymentItemActionImpl();
        cartItemCmd = new CartItemCmdImpl();

        mockGoodwillReasonDataService = mock(GoodwillReasonDataService.class);
        mockGoodwillPaymentItemDTOConverter = mock(GoodwillPaymentItemDTOConverterImpl.class);

        service.goodwillReasonDataService = mockGoodwillReasonDataService;
        service.goodwillPaymentItemDTOConverter = mockGoodwillPaymentItemDTOConverter;
    }

    @Test
    public void createItemDTOForCmdItemShouldCallGoodwillReasonDataServiceAndReturnEmptyForGoodWillOtherText() {
        when(mockGoodwillPaymentItemDTOConverter.convertCmdToDto(any(CartItemCmdImpl.class), any(GoodwillPaymentItemDTO.class))).thenReturn(getGoodwillPaymentItemDTOItem1());
        when(mockGoodwillReasonDataService.findByReasonId(anyLong())).thenReturn(getGoodwillReasonDTO1());
        cartItemCmd.setStartDate(ANNUAL_START_DATE_1);
        cartItemCmd.setCreditBalance(20);
        cartItemCmd.setTravelCardType("");
        ItemDTO itemDTO = service.createItemDTO(cartItemCmd);
        verify(mockGoodwillPaymentItemDTOConverter, atLeastOnce()).convertCmdToDto(any(CartItemCmdImpl.class), any(GoodwillPaymentItemDTO.class));
        verify(mockGoodwillReasonDataService, atLeastOnce()).findByReasonId(anyLong());
        assertTrue(itemDTO instanceof GoodwillPaymentItemDTO);
        assertEquals(StringUtil.EMPTY_STRING, ((GoodwillPaymentItemDTO) itemDTO).getGoodwillOtherText());
    }

    @Test
    public void createItemDTOForCmdItemShouldCallGoodwillReasonDataServiceAndReturnSameValueForGoodWillOtherText() {
        GoodwillReasonDTO reasonDTO = getGoodwillReasonDTO1();
        reasonDTO.setDescription(GOODWILL_REASON_OTHER);
        GoodwillPaymentItemDTO goodwillItemDTO = getGoodwillPaymentItemDTOItem1();
        goodwillItemDTO.setGoodwillOtherText("Other Text");
        when(mockGoodwillPaymentItemDTOConverter.convertCmdToDto(any(CartItemCmdImpl.class), any(GoodwillPaymentItemDTO.class))).thenReturn(goodwillItemDTO);
        when(mockGoodwillReasonDataService.findByReasonId(anyLong())).thenReturn(reasonDTO);
        cartItemCmd.setStartDate(ANNUAL_START_DATE_1);
        cartItemCmd.setCreditBalance(20);
        cartItemCmd.setTravelCardType("");
        ItemDTO itemDTO = service.createItemDTO(cartItemCmd);
        verify(mockGoodwillPaymentItemDTOConverter, atLeastOnce()).convertCmdToDto(any(CartItemCmdImpl.class), any(GoodwillPaymentItemDTO.class));
        verify(mockGoodwillReasonDataService, atLeastOnce()).findByReasonId(anyLong());
        assertTrue(itemDTO instanceof GoodwillPaymentItemDTO);
        assertEquals(goodwillItemDTO.getGoodwillOtherText(), ((GoodwillPaymentItemDTO) itemDTO).getGoodwillOtherText());
    }

    @Test
    public void updateItemDTOForGoodwillPaymentItemDTOShouldReturnItemToBeUpdatedAsIs() {
        GoodwillPaymentItemDTO dto1 = getGoodwillPaymentItemDTOItem1();
        GoodwillPaymentItemDTO dto2 = getGoodwillPaymentItemDTOItem2();
        ItemDTO itemDTO = service.updateItemDTO(dto1, dto2);
        assertEquals(dto1.getId(), itemDTO.getId());
        assertTrue(itemDTO instanceof GoodwillPaymentItemDTO);
    }

    @Test
    public void postProcessCartItemDTOShouldReturnGoodwillPaymentItemDTO() {
        GoodwillPaymentItemDTO dto1 = getGoodwillPaymentItemDTOItem1();
        ItemDTO itemDTO = service.postProcessItemDTO(dto1,true);
        assertEquals(dto1.getId(), itemDTO.getId());
    }

    @Test
    public void hasItemExpiredShouldReturnFalse() {
        GoodwillPaymentItemDTO dto1 = getGoodwillPaymentItemDTOItem1();
        assertFalse(service.hasItemExpired(new Date(), dto1));
    }

    @Test
    public void updateItemDTOForBackDatedAndDeceasedShouldReturnItemToBeUpdatedAsIs() {
        GoodwillPaymentItemDTO dto1 = getGoodwillPaymentItemDTOItem1();
        GoodwillPaymentItemDTO dto2 = getGoodwillPaymentItemDTOItem2();
        ItemDTO itemDTO = service.updateItemDTOForBackDatedAndDeceased(dto1, dto2);
        assertEquals(dto1.getId(), itemDTO.getId());
        assertTrue(itemDTO instanceof GoodwillPaymentItemDTO);
    }
}
