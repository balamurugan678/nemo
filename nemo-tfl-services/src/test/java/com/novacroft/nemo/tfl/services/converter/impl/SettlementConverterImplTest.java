package com.novacroft.nemo.tfl.services.converter.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.test_support.BACSSettlementTestUtil;
import com.novacroft.nemo.test_support.ChequeSettlementTestUtil;
import com.novacroft.nemo.test_support.ProductItemTestUtil;
import com.novacroft.nemo.test_support.SettlementTestUtil;
import com.novacroft.nemo.test_support.WebAccountCreditRefundPaymentTestUtil;
import com.novacroft.nemo.tfl.common.data_service.ItemDataService;
import com.novacroft.nemo.tfl.common.transfer.AdHocLoadSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.AutoLoadChangeSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.PaymentCardSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.SettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.WebCreditSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.BACSSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.ChequeSettlementDTO;
import com.novacroft.nemo.tfl.services.converter.AddressConverter;
import com.novacroft.nemo.tfl.services.transfer.Settlement;

public class SettlementConverterImplTest {

    private static final long MOCKITEMID = 1L;
    private static final long MOCK_EXTERNAL_ID = 100L;
    private SettlementConverterImpl settlementConverter;
    private ItemDataService mockItemDataService;
    private AddressConverter mockaddressConverter;

    @Before
    public void init() {
        settlementConverter = new SettlementConverterImpl();
        mockItemDataService = mock(ItemDataService.class);
        mockaddressConverter = mock(AddressConverter.class);
        settlementConverter.itemDataService = mockItemDataService;
        settlementConverter.addressConverter = mockaddressConverter;
    }

    @Test
    public void convertWebCreditSettlement() {

        WebCreditSettlementDTO settlementDTO = WebAccountCreditRefundPaymentTestUtil.getWebCreditSettlementDTO1();
        Settlement settlement = new Settlement();
        assertNotNull(settlementConverter.convert(settlementDTO, settlement));

    }

    @Test
    public void convertAdHocLoadSettlement() {

        AdHocLoadSettlementDTO settlementDTO = SettlementTestUtil.getTestAdHocLoadSettlementDTO1();
        settlementDTO.setItem(ProductItemTestUtil.getTestTravelCardProductDTO1());
        Settlement settlement = new Settlement();
        assertNotNull(settlementConverter.convert(settlementDTO, settlement));

    }

    @Test
    public void convertAutoLoadChangeSettlementDTO() {

        AutoLoadChangeSettlementDTO settlementDTO = SettlementTestUtil.getTestAutoLoadChangeSettlementDTO1();
        Settlement settlement = new Settlement();
        assertNotNull(settlementConverter.convert(settlementDTO, settlement));

    }

    @Test
    public void convertBACSSettlementDTO() {

        BACSSettlementDTO settlementDTO = BACSSettlementTestUtil.getTestBACSSettlementDTO1();
        Settlement settlement = new Settlement();
        assertNotNull(settlementConverter.convert(settlementDTO, settlement));

    }

    @Test
    public void convertChequeSettlementDTO() {

        ChequeSettlementDTO settlementDTO = ChequeSettlementTestUtil.getTestChequeSettlementDTO1();
        Settlement settlement = new Settlement();
        assertNotNull(settlementConverter.convert(settlementDTO, settlement));

    }

    @Test
    public void convertPaymentCardSettlementDTO() {

        PaymentCardSettlementDTO settlementDTO = SettlementTestUtil.getTestPaymentCardSettlementDTO1();
        Settlement settlement = new Settlement();
        assertNotNull(settlementConverter.convert(settlementDTO, settlement));

    }

    @Test
    public void convertSettlementDTO() {

        SettlementDTO settlementDTO = WebAccountCreditRefundPaymentTestUtil.getWebCreditSettlementDTO1();
        Settlement convertedWebAccountCreditRefundPaymentSettlement = settlementConverter.convertDTO(settlementDTO);
        assertEquals(convertedWebAccountCreditRefundPaymentSettlement.getId(), settlementDTO.getExternalId());

        AdHocLoadSettlementDTO adHocLoadSettlementDTO = SettlementTestUtil.getTestAdHocLoadSettlementDTO1();
        adHocLoadSettlementDTO.setItem(ProductItemTestUtil.getTestTravelCardProductDTO1());
        Settlement convertedAdHocLoadSettlementSettlement = settlementConverter.convertDTO(adHocLoadSettlementDTO);
        assertEquals(convertedAdHocLoadSettlementSettlement.getId(), settlementDTO.getExternalId());

        settlementDTO = SettlementTestUtil.getTestAutoLoadChangeSettlementDTO1();
        Settlement convertedAutoLoadChangeSettlement = settlementConverter.convertDTO(settlementDTO);
        assertEquals(convertedAutoLoadChangeSettlement.getId(), settlementDTO.getExternalId());

        settlementDTO = BACSSettlementTestUtil.getTestBACSSettlementDTO1();
        Settlement convertedBACSSettlement = settlementConverter.convertDTO(settlementDTO);
        assertEquals(convertedBACSSettlement.getId(), settlementDTO.getExternalId());

        settlementDTO = ChequeSettlementTestUtil.getTestChequeSettlementDTO1();
        Settlement convertedChequeSettlement = settlementConverter.convertDTO(settlementDTO);
        assertEquals(convertedChequeSettlement.getId(), settlementDTO.getExternalId());

        settlementDTO = SettlementTestUtil.getTestPaymentCardSettlementDTO1();
        Settlement convertedPaymentCardSettlement = settlementConverter.convertDTO(settlementDTO);
        assertEquals(convertedPaymentCardSettlement.getId(), settlementDTO.getExternalId());

    }

    @Test
    public void convertSettlementDTOList() {

        List<SettlementDTO> settlementDTOs = new ArrayList<SettlementDTO>();
        settlementDTOs.add(WebAccountCreditRefundPaymentTestUtil.getWebCreditSettlementDTO1());
        AdHocLoadSettlementDTO adHocLoadSettlementDTO = SettlementTestUtil.getTestAdHocLoadSettlementDTO1();
        adHocLoadSettlementDTO.setItem(ProductItemTestUtil.getTestTravelCardProductDTO1());
        settlementDTOs.add(adHocLoadSettlementDTO);
        settlementDTOs.add(SettlementTestUtil.getTestAutoLoadChangeSettlementDTO1());
        settlementDTOs.add(BACSSettlementTestUtil.getTestBACSSettlementDTO1());
        settlementDTOs.add(ChequeSettlementTestUtil.getTestChequeSettlementDTO1());
        settlementDTOs.add(SettlementTestUtil.getTestPaymentCardSettlementDTO1());

        List<Settlement> convertDTOs = settlementConverter.convertDTOs(settlementDTOs);
        assert (convertDTOs.size() == 6);

    }

    @Test
    public void convertPayeeSettlement() {

        String payeeName = null;
        AddressDTO addressDTO = null;
        Long paymentReference = null;
        Settlement settlement = new Settlement();
        Settlement convertedPayeeSettlement = settlementConverter.convertPayeeSettlement(payeeName, addressDTO, paymentReference, settlement);
        assertNotNull(convertedPayeeSettlement);
    }

    @Test
    public void getExternalItemId() {
        ItemDTO itemDto = new ItemDTO();
        itemDto.setExternalId(MOCK_EXTERNAL_ID);
        when(mockItemDataService.findById(anyLong())).thenReturn(itemDto);
        Long externalItemId = settlementConverter.getExternalItemId(MOCKITEMID);
        assertEquals(externalItemId.intValue(), MOCK_EXTERNAL_ID);
    }
}
