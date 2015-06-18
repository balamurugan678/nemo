package com.novacroft.nemo.tfl.common.data_service.impl;

import static com.novacroft.nemo.test_support.CardRefundableDepositTestUtil.CARD_REFUNDABLE_DEPOSIT_AMOUNT_1;
import static com.novacroft.nemo.test_support.CardRefundableDepositTestUtil.getTestCardRefundableDeposit1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.novacroft.nemo.tfl.common.converter.impl.CardRefundableDepositConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.CardRefundableDepositDAO;
import com.novacroft.nemo.tfl.common.domain.CardRefundableDeposit;
import com.novacroft.nemo.tfl.common.transfer.CardRefundableDepositDTO;

/**
 * Card refundable deposit data service unit tests.
 */
public class CardRefundableDepositDataServiceTest {

    static final Logger logger = LoggerFactory.getLogger(CardRefundableDepositDataServiceTest.class);

    private CardRefundableDepositDataServiceImpl dataService;
    private CardRefundableDepositDAO mockDao;

    @Before
    public void setUp() {
        dataService = new CardRefundableDepositDataServiceImpl();
        mockDao = mock(CardRefundableDepositDAO.class);

        dataService.setConverter(new CardRefundableDepositConverterImpl());
        dataService.setDao(mockDao);
    }

    @Test
    public void findRefundableDepositAmountShouldFind() {
        when(mockDao.findByExampleUniqueResult((CardRefundableDeposit) anyObject())).thenReturn(getTestCardRefundableDeposit1());

        CardRefundableDepositDTO resultsDTO = dataService.findRefundableDepositAmount();

        verify(mockDao, atLeastOnce()).findByExampleUniqueResult((CardRefundableDeposit) anyObject());
        assertEquals(CARD_REFUNDABLE_DEPOSIT_AMOUNT_1, resultsDTO.getPrice());
    }

    @Test
    public void findRefundableDepositAmountShouldFindNullWithNullCardRefundableDeposit() {
        when(mockDao.findByExampleUniqueResult((CardRefundableDeposit) anyObject())).thenReturn(null);

        CardRefundableDepositDTO resultsDTO = dataService.findRefundableDepositAmount();

        verify(mockDao, atLeastOnce()).findByExampleUniqueResult((CardRefundableDeposit) anyObject());
        assertNull(resultsDTO);
    }
    
    @Test
    public void ShouldFindCardrefundableDepositByPrice() {
        when(mockDao.findByExampleUniqueResult((CardRefundableDeposit) anyObject())).thenReturn(null);

        CardRefundableDepositDTO resultsDTO = dataService.findByPrice(500);

        verify(mockDao, atLeastOnce()).findByExampleUniqueResult((CardRefundableDeposit) anyObject());
        assertNull(resultsDTO);
    }

}
