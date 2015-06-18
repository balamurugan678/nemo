package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.test_support.WebCreditStatementTestUtil.PURCHASE;
import static com.novacroft.nemo.test_support.WebCreditStatementTestUtil.REFUND;
import static com.novacroft.nemo.test_support.WebCreditStatementTestUtil.SETTLEMENT_AMOUNT_1;
import static com.novacroft.nemo.test_support.WebCreditStatementTestUtil.SETTLEMENT_AMOUNT_2;
import static com.novacroft.nemo.test_support.WebCreditStatementTestUtil.getTestWebCreditStatementCmdImpl1;
import static com.novacroft.nemo.test_support.WebCreditStatementTestUtil.getTestWebCreditStatementLineCmdList2;
import static com.novacroft.nemo.test_support.WebCreditStatementTestUtil.getTestWebCreditStatementLineDTO1;
import static com.novacroft.nemo.test_support.WebCreditStatementTestUtil.getTestWebCreditStatementLineDTO2;
import static com.novacroft.nemo.test_support.WebCreditStatementTestUtil.getTestWebCreditStatementLineDTOList2;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Locale;

import org.junit.Test;
import org.springframework.context.ApplicationContext;

import com.novacroft.nemo.tfl.common.command.impl.WebCreditStatementCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.WebCreditStatementLineCmdImpl;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.data_service.WebCreditSettlementDataService;
import com.novacroft.nemo.tfl.common.data_service.impl.WebCreditSettlementDataServiceImpl;
import com.novacroft.nemo.tfl.common.transfer.WebCreditStatementLineDTO;

/**
 * WebCreditStatementService unit tests
 */
@SuppressWarnings("unchecked")
public class WebStatementServiceImplTest {

    @Test
    public void shouldGetMessage() {
        String testMessage = "test-message";
        ApplicationContext mockApplicationContext = mock(ApplicationContext.class);
        when(mockApplicationContext.getMessage(anyString(), any(Object[].class), anyString(), any(Locale.class)))
                .thenReturn(testMessage);
        WebCreditStatementServiceImpl service = new WebCreditStatementServiceImpl();
        service.applicationContext = mockApplicationContext;
        assertEquals(testMessage, service.getMessage("test-code"));
    }

    @Test
    public void nullToZeroShouldReturnZero() {
        WebCreditStatementServiceImpl service = new WebCreditStatementServiceImpl();
        assertEquals(0, service.nullToZero(null));
    }

    @Test
    public void nullToZeroShouldReturnValue() {
        WebCreditStatementServiceImpl service = new WebCreditStatementServiceImpl();
        assertEquals((int) SETTLEMENT_AMOUNT_1, service.nullToZero(SETTLEMENT_AMOUNT_1));
    }

    @Test
    public void shouldCalculateLineAmountShouldCalculateWithDebitValue() {
        WebCreditStatementServiceImpl service = new WebCreditStatementServiceImpl();
        Integer result = service.calculateLineAmount(null, SETTLEMENT_AMOUNT_2);
        assertEquals(SETTLEMENT_AMOUNT_2, result);
    }

    @Test
    public void shouldCalculateLineAmountShouldCalculateWithCreditValue() {
        WebCreditStatementServiceImpl service = new WebCreditStatementServiceImpl();
        Integer result = service.calculateLineAmount(SETTLEMENT_AMOUNT_1, null);
        assertEquals(SETTLEMENT_AMOUNT_1, result);
    }

    @Test
    public void shouldCalculateLineAmountShouldCalculateWithDebitAndCreditValue() {
        WebCreditStatementServiceImpl service = new WebCreditStatementServiceImpl();
        Integer result = service.calculateLineAmount(SETTLEMENT_AMOUNT_1, SETTLEMENT_AMOUNT_2);
        assertEquals((int) (SETTLEMENT_AMOUNT_1 + SETTLEMENT_AMOUNT_2), (int) result);
    }

    @Test
    public void shouldCalculateTotalBalance() {
        List<WebCreditStatementLineCmdImpl> testStatementLines = getTestWebCreditStatementLineCmdList2();
        WebCreditStatementServiceImpl service = new WebCreditStatementServiceImpl();
        int expectedResult = -(SETTLEMENT_AMOUNT_1 + SETTLEMENT_AMOUNT_2);
        int result = service.calculateTotalBalance(testStatementLines);
        assertEquals(expectedResult, result);
    }

    @Test
    public void shouldPrepareCumulativeBalance() {
        WebCreditStatementCmdImpl testCmd = getTestWebCreditStatementCmdImpl1();
        WebCreditStatementServiceImpl service = new WebCreditStatementServiceImpl();
        service.prepareCumulativeBalance(testCmd);
        assertTrue((int) -(SETTLEMENT_AMOUNT_1 + SETTLEMENT_AMOUNT_2) ==
                (int) testCmd.getStatementLines().get(0).getCumulativeBalanceAmount());
        assertTrue((int) -SETTLEMENT_AMOUNT_2 == (int) testCmd.getStatementLines().get(1).getCumulativeBalanceAmount());
    }

    @Test
    public void shouldPrepareStatementLineForPurchase() {
        ApplicationContext mockApplicationContext = mock(ApplicationContext.class);
        when(mockApplicationContext
                .getMessage(eq(ContentCode.WEB_CREDIT_STATEMENT_ITEM_PURCHASE.textCode()), any(Object[].class),
                        anyString(), any(Locale.class))).thenReturn(PURCHASE);
        WebCreditStatementServiceImpl service = new WebCreditStatementServiceImpl();
        service.applicationContext = mockApplicationContext;
        WebCreditStatementLineCmdImpl result = service.prepareStatementLine(getTestWebCreditStatementLineDTO1());
        assertEquals(PURCHASE, result.getItem());
        assertTrue(SETTLEMENT_AMOUNT_1 == result.getDebitAmount());
    }

    @Test
    public void shouldPrepareStatementLineForRefund() {
        ApplicationContext mockApplicationContext = mock(ApplicationContext.class);
        when(mockApplicationContext
                .getMessage(eq(ContentCode.WEB_CREDIT_STATEMENT_ITEM_REFUND.textCode()), any(Object[].class),
                        anyString(), any(Locale.class))).thenReturn(REFUND);
        WebCreditStatementServiceImpl service = new WebCreditStatementServiceImpl();
        service.applicationContext = mockApplicationContext;
        WebCreditStatementLineCmdImpl result = service.prepareStatementLine(getTestWebCreditStatementLineDTO2());
        assertEquals(REFUND, result.getItem());
        assertTrue(SETTLEMENT_AMOUNT_2 == result.getCreditAmount());
    }

    @Test
    public void shouldPrepareStatementLines() {
        WebCreditStatementServiceImpl service = mock(WebCreditStatementServiceImpl.class);
        when(service.prepareStatementLines(anyList())).thenCallRealMethod();
        when(service.prepareStatementLine(any(WebCreditStatementLineDTO.class)))
                .thenReturn(new WebCreditStatementLineCmdImpl());
        service.prepareStatementLines(getTestWebCreditStatementLineDTOList2());
        verify(service, atLeastOnce()).prepareStatementLine(any(WebCreditStatementLineDTO.class));
    }

    @Test
    public void shouldGetStatement() {
        WebCreditSettlementDataService mockWebAccountCreditSettlementDataService =
                mock(WebCreditSettlementDataServiceImpl.class);
        when(mockWebAccountCreditSettlementDataService.findByCustomerId(anyLong())).thenReturn(null);
        WebCreditStatementServiceImpl service = mock(WebCreditStatementServiceImpl.class);
        when(service.getStatement(anyLong())).thenCallRealMethod();
        when(service.prepareStatementLines(anyList())).thenReturn(null);
        doNothing().when(service).prepareCumulativeBalance(any(WebCreditStatementCmdImpl.class));
        when(service.calculateTotalBalance(anyList())).thenReturn(0);
        service.webCreditSettlementDataService = mockWebAccountCreditSettlementDataService;
        service.getStatement(1L);
        verify(service, atLeastOnce()).getStatement(anyLong());
        verify(service, atLeastOnce()).prepareStatementLines(anyList());
        verify(service, atLeastOnce()).prepareCumulativeBalance(any(WebCreditStatementCmdImpl.class));
        verify(service, atLeastOnce()).calculateTotalBalance(anyList());
    }
}
