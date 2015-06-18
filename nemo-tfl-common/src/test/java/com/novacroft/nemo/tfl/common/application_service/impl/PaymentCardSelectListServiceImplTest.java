package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.test_support.PaymentCardTestUtil.TEST_NICK_NAME_1;
import static com.novacroft.nemo.test_support.PaymentCardTestUtil.TEST_OBFUSCATED_PRIMARY_ACCOUNT_NUMBER_1;
import static com.novacroft.nemo.test_support.PaymentCardTestUtil.TEST_PAYMENT_CARD_ID_1;
import static com.novacroft.nemo.test_support.PaymentCardTestUtil.getTestPaymentCardList1;
import static com.novacroft.nemo.test_support.SelectListTestUtil.getTestSelectListDTO;
import static com.novacroft.nemo.test_support.SelectListTestUtil.getTestSelectListDTOWithAddOption;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.common.transfer.SelectListOptionDTO;
import com.novacroft.nemo.test_support.CustomerTestUtil;
import com.novacroft.nemo.test_support.PaymentCardTestUtil;
import com.novacroft.nemo.tfl.common.constant.AddPaymentCardAction;
import com.novacroft.nemo.tfl.common.constant.PageSelectList;
import com.novacroft.nemo.tfl.common.data_service.PaymentCardDataService;
import com.novacroft.nemo.tfl.common.transfer.PaymentCardDTO;

/**
 * PaymentCardSelectListService unit tests
 */
public class PaymentCardSelectListServiceImplTest {
    private static final Integer TEST_SAVED_PAYMENT_CARD_MAX_NUMBER = 1;
    private static final int EXPECTED_LIST_OPTION_COUNT_1 = 1;
    private static final int EXPECTED_LIST_OPTION_COUNT_2 = 2;
    private static final int EXPECTED_LIST_OPTION_COUNT_3 = 3;
    
    private PaymentCardSelectListServiceImpl service;
    private PaymentCardDataService mockPaymentCardDataService;
    private SelectListService mockSelectListService;
    private SystemParameterService mockSystemParameterService;

    private PaymentCardDTO mockPaymentCardDTO;
    private SelectListDTO mockSelectListDTO;
    private SelectListOptionDTO mockSelectListOptionDTO;
    private List<SelectListOptionDTO> mockSelectListOptionDTOList;

    @Before
    public void setUp() {
        this.service = mock(PaymentCardSelectListServiceImpl.class);
        this.mockPaymentCardDataService = mock(PaymentCardDataService.class);
        this.service.paymentCardDataService = mockPaymentCardDataService;
        this.mockSelectListService = mock(SelectListService.class);
        this.service.selectListService = mockSelectListService;
        this.mockSystemParameterService = mock(SystemParameterService.class);
        this.service.systemParameterService = mockSystemParameterService;

        this.mockPaymentCardDTO = mock(PaymentCardDTO.class);
        this.mockSelectListDTO = mock(SelectListDTO.class);
        this.mockSelectListOptionDTO = mock(SelectListOptionDTO.class);

        this.mockSelectListOptionDTOList = new ArrayList<SelectListOptionDTO>();
        this.mockSelectListOptionDTOList.add(this.mockSelectListOptionDTO);
    }

    @Test
    public void shouldGetPaymentCardDisplayNameWithoutNickName() {
        when(this.service.getPaymentCardDisplayName(any(PaymentCardDTO.class))).thenCallRealMethod();
        when(this.mockPaymentCardDTO.getObfuscatedPrimaryAccountNumber()).thenReturn(TEST_OBFUSCATED_PRIMARY_ACCOUNT_NUMBER_1);
        when(this.mockPaymentCardDTO.getNickName()).thenReturn(null);
        String result = this.service.getPaymentCardDisplayName(this.mockPaymentCardDTO);
        assertTrue(result.contains(TEST_OBFUSCATED_PRIMARY_ACCOUNT_NUMBER_1));
        verify(this.mockPaymentCardDTO).getObfuscatedPrimaryAccountNumber();
        verify(this.mockPaymentCardDTO).getNickName();
    }

    @Test
    public void shouldGetPaymentCardDisplayNameWithNickName() {
        when(this.service.getPaymentCardDisplayName(any(PaymentCardDTO.class))).thenCallRealMethod();
        when(this.mockPaymentCardDTO.getObfuscatedPrimaryAccountNumber()).thenReturn(TEST_OBFUSCATED_PRIMARY_ACCOUNT_NUMBER_1);
        when(this.mockPaymentCardDTO.getNickName()).thenReturn(TEST_NICK_NAME_1);
        String result = this.service.getPaymentCardDisplayName(this.mockPaymentCardDTO);
        assertTrue(result.contains(TEST_OBFUSCATED_PRIMARY_ACCOUNT_NUMBER_1));
        assertTrue(result.contains(TEST_NICK_NAME_1));
        verify(this.mockPaymentCardDTO).getObfuscatedPrimaryAccountNumber();
        verify(this.mockPaymentCardDTO, times(2)).getNickName();
    }

    @Test
    public void isOptionASavedCardShouldReturnTrue() {
        when(this.service.isOptionASavedCard(any(SelectListOptionDTO.class))).thenCallRealMethod();
        when(this.mockSelectListOptionDTO.getValue()).thenReturn(String.valueOf(TEST_PAYMENT_CARD_ID_1));
        assertTrue(this.service.isOptionASavedCard(this.mockSelectListOptionDTO));
    }

    @Test
    public void isOptionASavedCardShouldReturnFalse() {
        when(this.service.isOptionASavedCard(any(SelectListOptionDTO.class))).thenCallRealMethod();
        when(this.mockSelectListOptionDTO.getValue()).thenReturn(AddPaymentCardAction.ADD_AND_SAVE.code());
        assertFalse(this.service.isOptionASavedCard(this.mockSelectListOptionDTO));
    }

    @Test
    public void isAtMaximumNumberOfSavedCardsShouldReturnTrue() {
        when(this.service.isAtMaximumNumberOfSavedCards(any(SelectListDTO.class))).thenCallRealMethod();
        when(this.service.isOptionASavedCard(any(SelectListOptionDTO.class))).thenReturn(true);
        when(this.mockSystemParameterService.getIntegerParameterValue(anyString()))
                .thenReturn(TEST_SAVED_PAYMENT_CARD_MAX_NUMBER);
        when(this.mockSelectListDTO.getOptions()).thenReturn(this.mockSelectListOptionDTOList);

        assertTrue(this.service.isAtMaximumNumberOfSavedCards(this.mockSelectListDTO));
    }

    @Test
    public void isAtMaximumNumberOfSavedCardsShouldReturnFalse() {
        when(this.service.isAtMaximumNumberOfSavedCards(any(SelectListDTO.class))).thenCallRealMethod();
        when(this.service.isOptionASavedCard(any(SelectListOptionDTO.class))).thenReturn(false);
        when(this.mockSystemParameterService.getIntegerParameterValue(anyString()))
                .thenReturn(TEST_SAVED_PAYMENT_CARD_MAX_NUMBER);
        when(this.mockSelectListDTO.getOptions()).thenReturn(this.mockSelectListOptionDTOList);

        assertFalse(this.service.isAtMaximumNumberOfSavedCards(this.mockSelectListDTO));
    }

    @Test
    public void removeSaveOptionIfAtMaximumNumberOfSavedCardsShouldRemoveOption() {
        when(this.service.removeSaveOptionIfAtMaximumNumberOfSavedCards(any(SelectListDTO.class))).thenCallRealMethod();
        when(this.service.isAtMaximumNumberOfSavedCards(any(SelectListDTO.class))).thenReturn(true);

        List<SelectListOptionDTO> mockOptionsList = mock(List.class);
        when(mockOptionsList.remove(any(SelectListOptionDTO.class))).thenReturn(true);
        when(this.mockSelectListDTO.getOptions()).thenReturn(mockOptionsList);

        this.service.removeSaveOptionIfAtMaximumNumberOfSavedCards(this.mockSelectListDTO);

        verify(mockOptionsList).remove(any(SelectListOptionDTO.class));
    }

    @Test
    public void removeSaveOptionIfAtMaximumNumberOfSavedCardsShouldNotRemoveOption() {
        when(this.service.removeSaveOptionIfAtMaximumNumberOfSavedCards(any(SelectListDTO.class))).thenCallRealMethod();
        when(this.service.isAtMaximumNumberOfSavedCards(any(SelectListDTO.class))).thenReturn(false);

        List<SelectListOptionDTO> mockOptionsList = mock(List.class);
        when(mockOptionsList.remove(any(SelectListOptionDTO.class))).thenReturn(true);
        when(this.mockSelectListDTO.getOptions()).thenReturn(mockOptionsList);

        this.service.removeSaveOptionIfAtMaximumNumberOfSavedCards(this.mockSelectListDTO);

        verify(mockOptionsList, never()).remove(any(SelectListOptionDTO.class));
    }
    
    @Test
    public void shouldGetPaymentCardSelectList() {
        when(service.getPaymentCardSelectList(null)).thenCallRealMethod();
        when(service.getPaymentCardDisplayName(any(PaymentCardDTO.class))).thenReturn("");
        when(mockPaymentCardDataService.findByCustomerId(anyLong())).thenReturn(getTestPaymentCardList1());
        
        SelectListDTO actualResult = service.getPaymentCardSelectList(null);
        assertNotNull(actualResult);
        assertEquals(PageSelectList.PAYMENT_CARDS, actualResult.getName());
        assertEquals(EXPECTED_LIST_OPTION_COUNT_1, actualResult.getOptions().size());
    }
    
    @Test
    public void shouldGetPaymentCardSelectListWithAllOptions() {
        when(service.getPaymentCardSelectListWithAllOptions(anyLong())).thenCallRealMethod();
        when(service.getPaymentCardSelectList(anyLong())).thenReturn(getTestSelectListDTO());
        when(mockSelectListService.getSelectList(anyString())).thenReturn(getTestSelectListDTOWithAddOption());
        when(service.removeSaveOptionIfAtMaximumNumberOfSavedCards(any(SelectListDTO.class)))
            .then(returnsFirstArg());
        SelectListDTO actualResult = service.getPaymentCardSelectListWithAllOptions(null);
        assertNotNull(actualResult);
        assertEquals(EXPECTED_LIST_OPTION_COUNT_3, actualResult.getOptions().size());
    }
    
    @Test
    public void shouldGetPaymentCardSelectListWithOnlySaveOption() {
        when(service.getPaymentCardSelectListWithOnlySaveOption(anyLong())).thenCallRealMethod();
        when(service.getPaymentCardSelectListWithAllOptions(anyLong())).thenReturn(getTestSelectListDTO());
        when(mockSelectListService.getSelectList(anyString())).thenReturn(getTestSelectListDTOWithAddOption());
        when(service.removeSaveOptionIfAtMaximumNumberOfSavedCards(any(SelectListDTO.class)))
            .then(returnsFirstArg());
        SelectListDTO actualResult = service.getPaymentCardSelectListWithOnlySaveOption(null);
        assertNotNull(actualResult);
        assertEquals(EXPECTED_LIST_OPTION_COUNT_2, actualResult.getOptions().size());
    }
    
    @Test
    public void shouldGetPaymentCardSelectListForAutoTopUp() {
    	when(mockPaymentCardDataService.findByCustomerId(anyLong())).thenReturn(PaymentCardTestUtil.makePaymentCardsList());
    	when(mockSelectListService.getSelectList(PageSelectList.ADD_PAYMENT_CARD_ACTIONS)).thenReturn(getTestSelectListDTO());
    	when(service.getPaymentCardSelectListForAdHocLoad(anyLong())).thenCallRealMethod();
    	
    	SelectListDTO selectList = service.getPaymentCardSelectListForAdHocLoad(CustomerTestUtil.getCustomer1().getId());
    	
    	assertEquals(EXPECTED_LIST_OPTION_COUNT_1, selectList.getOptions().size());
    }
    
    @Test
    public void shouldGetPaymentCardSelectListWithAllOptionsForAutoTopUp() {
    	when(mockPaymentCardDataService.findByCustomerId(anyLong())).thenReturn(PaymentCardTestUtil.makePaymentCardsList());
    	when(mockSelectListService.getSelectList(PageSelectList.ADD_PAYMENT_CARD_ACTIONS)).thenReturn(getTestSelectListDTO());
    	when(service.getPaymentCardSelectListForAdHocLoad(anyLong())).thenCallRealMethod();
    	when(service.getPaymentCardSelectListForAdHocLoadWithAllOptions(anyLong())).thenCallRealMethod();
    	when(service.removeSaveOptionIfAtMaximumNumberOfSavedCards(any(SelectListDTO.class))).thenCallRealMethod();
    	when(service.getPaymentCardDisplayName(any(PaymentCardDTO.class))).thenCallRealMethod();
    	
    	SelectListDTO selectListWithAllOptions = service.getPaymentCardSelectListForAdHocLoadWithAllOptions(CustomerTestUtil.getCustomer1().getId());
    	
    	assertEquals(EXPECTED_LIST_OPTION_COUNT_3, selectListWithAllOptions.getOptions().size());
    }
    
    @Test
    public void shouldUpdateOptionMeaning() {
    	SelectListOptionDTO mockOption = mock(SelectListOptionDTO.class);
    	SelectListDTO list = new SelectListDTO();
    	list.getOptions().add(mockOption);
    	when(mockOption.getValue()).thenReturn(PaymentCardTestUtil.TEST_NICK_NAME_2);
    	PaymentCardSelectListServiceImpl newService = new PaymentCardSelectListServiceImpl();
    	newService.updateOptionMeaning(list, PaymentCardTestUtil.TEST_NICK_NAME_2, TEST_NICK_NAME_1);
    	verify(mockOption, atLeastOnce()).setMeaning(anyString());
    }
}
