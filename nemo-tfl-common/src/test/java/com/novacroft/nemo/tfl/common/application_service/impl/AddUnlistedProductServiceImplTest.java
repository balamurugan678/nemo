package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.common.utils.DateUtil.parse;
import static com.novacroft.nemo.test_support.CartItemTestUtil.TRAVEL_END_DATE_2;
import static com.novacroft.nemo.test_support.CartItemTestUtil.TRAVEL_END_ZONE_2;
import static com.novacroft.nemo.test_support.CartItemTestUtil.TRAVEL_START_DATE_2;
import static com.novacroft.nemo.test_support.CartItemTestUtil.TRAVEL_START_ZONE_2;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.constant.Durations;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.test_support.AdministrationFeeItemTestUtil;
import com.novacroft.nemo.test_support.CartItemTestUtil;
import com.novacroft.nemo.test_support.ProductItemTestUtil;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.ProductItemType;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;

public class AddUnlistedProductServiceImplTest {

    private AddUnlistedProductServiceImpl addUnlistedProductService;
    private CartItemCmdImpl cmd;

    @Before
    public void setup() {
        addUnlistedProductService = new AddUnlistedProductServiceImpl();
        cmd = new CartItemCmdImpl();

    }

    @Test
    public void setTicketTypeTest() {

        cmd.setTravelCardType("");
        addUnlistedProductService.setTicketType(cmd);
        assertEquals(cmd.getTicketType(), ProductItemType.TRAVEL_CARD.databaseCode());

        cmd.setTravelCardType("Weekly Bus Pass");
        addUnlistedProductService.setTicketType(cmd);
        assertEquals(cmd.getTicketType(), ProductItemType.BUS_PASS.databaseCode());

        cmd.setTravelCardType("Weekly TravelCard");
        addUnlistedProductService.setTicketType(cmd);
        assertEquals(cmd.getTicketType(), ProductItemType.TRAVEL_CARD.databaseCode());

    }

    @Test
    public void setTravelcardTypeTest() {
        CartItemCmdImpl cartitem = CartItemTestUtil.getTestTravelCardCmd1();
        cartitem.setTravelCardType(Durations.SEVEN_DAYS.getDurationType() + " Travelcard");
        addUnlistedProductService.setTravelcardTypeByFormTravelCardType(cartitem);
        assertEquals(cartitem.getTravelCardType(), Durations.SEVEN_DAYS.getDurationType());

        cartitem.setTravelCardType(Durations.MONTH.getDurationType() + " Bus Pass");
        addUnlistedProductService.setTravelcardTypeByFormTravelCardType(cartitem);
        assertEquals(cartitem.getTravelCardType(), Durations.MONTH.getDurationType());

        cartitem.setTravelCardType(Durations.OTHER.getDurationType() + " Travelcard");
        addUnlistedProductService.setTravelcardTypeByFormTravelCardType(cartitem);
        assertEquals(cartitem.getTravelCardType(), Durations.OTHER.getDurationType());
    }

    @Test
    public void shouldFindDuplicateInList() {
        ArrayList<ItemDTO> list = new ArrayList<ItemDTO>();
        list.add(ProductItemTestUtil.getTestTravelCardProductDTO1());

        boolean checkDuplicateUsingAdd = addUnlistedProductService.isCartItemDuplicate(list, CartItemTestUtil.getTestTravelCard1());
        assertTrue(checkDuplicateUsingAdd);
    }

    @Test
    public void shouldNotFindDuplicateInList() {
        ArrayList<ItemDTO> list = new ArrayList<ItemDTO>();
        list.add(ProductItemTestUtil.getTestTravelCardProductDTO1());

        assertFalse(addUnlistedProductService.isCartItemDuplicate(list, CartItemTestUtil.getTestTravelCard2()));
    }

    @Test
    public void shouldNotFindDuplicateIfDurationIsDifferent() {
        ArrayList<ItemDTO> list = new ArrayList<ItemDTO>();
        ProductItemDTO productItem = ProductItemTestUtil.getTestTravelCardProductDTO1();
        productItem.setEndDate(new Date());
        list.add(productItem);

        assertFalse(addUnlistedProductService.isCartItemDuplicate(list, CartItemTestUtil.getTestTravelCard1()));
    }

    @Test
    public void shouldNotFindDuplicateIfStartDateIsDifferent() {
        ArrayList<ItemDTO> list = new ArrayList<ItemDTO>();
        ProductItemDTO productItem = ProductItemTestUtil.getTestTravelCardProductDTO1();
        productItem.setStartDate(parse(TRAVEL_START_DATE_2));
        productItem.setEndDate(parse(TRAVEL_END_DATE_2));
        list.add(productItem);

        assertFalse(addUnlistedProductService.isCartItemDuplicate(list, CartItemTestUtil.getTestTravelCard1()));
    }

    @Test
    public void shouldNotFindDuplicateIfStartZoneIsDifferent() {
        ArrayList<ItemDTO> list = new ArrayList<ItemDTO>();
        ProductItemDTO productItem = ProductItemTestUtil.getTestTravelCardProductDTO1();
        productItem.setStartZone(TRAVEL_START_ZONE_2);
        list.add(productItem);

        assertFalse(addUnlistedProductService.isCartItemDuplicate(list, CartItemTestUtil.getTestTravelCard1()));
    }

    @Test
    public void shouldNotFindDuplicateIfEndZoneIsDifferent() {
        ArrayList<ItemDTO> list = new ArrayList<ItemDTO>();
        ProductItemDTO productItem = ProductItemTestUtil.getTestTravelCardProductDTO1();
        productItem.setEndZone(TRAVEL_END_ZONE_2);
        list.add(productItem);

        assertFalse(addUnlistedProductService.isCartItemDuplicate(list, CartItemTestUtil.getTestTravelCard1()));
    }

    @Test
    public void shouldNotFindDuplicateIfDurationAndZoneAreDifferent() {
        ArrayList<ItemDTO> list = new ArrayList<ItemDTO>();
        ProductItemDTO productItem = ProductItemTestUtil.getTestTravelCardProductDTO2();
        productItem.setStartDate(DateUtil.parse(CartItemTestUtil.TRAVEL_START_DATE_1));
        list.add(productItem);

        assertFalse(addUnlistedProductService.isCartItemDuplicate(list, CartItemTestUtil.getTestTravelCard1()));
    }

    @Test
    public void shouldNotFindDuplicateIfDurationAndStartDateAreDifferent() {
        ArrayList<ItemDTO> list = new ArrayList<ItemDTO>();
        ProductItemDTO productItem = ProductItemTestUtil.getTestTravelCardProductDTO1();
        productItem.setEndDate(DateUtil.parse(CartItemTestUtil.TRAVEL_END_DATE_2));
        productItem.setStartDate(DateUtil.parse(CartItemTestUtil.TRAVEL_START_DATE_1));
        list.add(productItem);

        assertFalse(addUnlistedProductService.isCartItemDuplicate(list, CartItemTestUtil.getTestTravelCard1()));
    }

    @Test
    public void shouldNotFindDuplicateIfZonesAndStartDateAreDifferent() {
        ArrayList<ItemDTO> list = new ArrayList<ItemDTO>();
        ProductItemDTO productItem = ProductItemTestUtil.getTestTravelCardProductDTO1();
        productItem.setEndZone(TRAVEL_END_ZONE_2);
        productItem.setStartDate(new Date());
        list.add(productItem);

        assertFalse(addUnlistedProductService.isCartItemDuplicate(list, CartItemTestUtil.getTestTravelCard1()));
    }

    @Test
    public void shouldNotFindDuplicateIfNoTravelcardsInCartItemList() {
        ArrayList<ItemDTO> list = new ArrayList<ItemDTO>();
        list.add(AdministrationFeeItemTestUtil.getTestAdministrationFeeItemDTO1());
        assertFalse(addUnlistedProductService.isCartItemDuplicate(list, CartItemTestUtil.getTestTravelCard1()));
    }
}
