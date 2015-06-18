package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTO1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.CARD_ID_1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.SECURITY_ANSWER_1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.SECURITY_ANSWER_4;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.SECURITY_QUESTION_1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.SECURITY_QUESTION_4;
import static com.novacroft.nemo.test_support.CustomerTestUtil.CUSTOMER_ID_1;
import static com.novacroft.nemo.test_support.SecurityQuestionCmdTestUtil.getTestSecurityQuestionCmd1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.test_support.CartSessionDataTestUtil;
import com.novacroft.nemo.test_support.SecurityQuestionCmdTestUtil;
import com.novacroft.nemo.tfl.common.command.impl.SecurityQuestionCmdImpl;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CartSessionData;

/**
 * Unit tests for SecurityQuestionService
 */
public class SecurityQuestionServiceTest {

    protected CardDataService mockCardDataService;
    protected SecurityQuestionCmdImpl cmd;
    protected SecurityQuestionServiceImpl service;
    protected SecurityQuestionServiceImpl mockService;

    @Before
    public void setUp() {
        service = new SecurityQuestionServiceImpl();
        mockService = mock(SecurityQuestionServiceImpl.class);
        cmd = new SecurityQuestionCmdImpl();
        mockCardDataService = mock(CardDataService.class);
        service.cardDataService = mockCardDataService;
    }

    @Test
    public void shouldGetSecurityQuestionDetails() {
        CardDTO card = getTestCardDTO1();
        card.setSecurityQuestion(SECURITY_QUESTION_1);
        when(mockCardDataService.findById(anyLong())).thenReturn(card);

        SecurityQuestionCmdImpl resultCmd = service.getSecurityQuestionDetails(CARD_ID_1);
        verify(mockCardDataService, atLeastOnce()).findById(anyLong());
        assertNotNull(resultCmd);
        assertEquals(SECURITY_QUESTION_1, resultCmd.getSecurityQuestion());
    }

    @Test
    public void getSecurityQuestionDetailsShouldNull() {
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());

        SecurityQuestionCmdImpl resultCmd = service.getSecurityQuestionDetails(null);
        assertNull(resultCmd.getSecurityQuestion());
    }

    @Test
    public void getSecurityQuestionDetailsShouldReturnCmdWithCardId() {
        when(mockCardDataService.findById(anyLong())).thenReturn(null);

        SecurityQuestionCmdImpl resultCmd = service.getSecurityQuestionDetails(CARD_ID_1);
        verify(mockCardDataService, atLeastOnce()).findById(anyLong());
        assertNotNull(resultCmd);
        assertEquals(CARD_ID_1, resultCmd.getCardId());
    }
    
    @Test
    public void addSecurityQuestionDetailsShouldUpdateSecurityDetailsAndCallFindByCardNumber() {
        when(mockCardDataService.findByCardNumber(anyString())).thenReturn(getTestCardDTO1());
        cmd.setCardNumber(CardTestUtil.OYSTER_NUMBER_1);
        service.addSecurityQuestionDetails(cmd, CUSTOMER_ID_1, null);
        verify(mockCardDataService, atLeastOnce()).findByCardNumber(anyString());
    }

    @Test
    public void addSecurityQuestionDetailsShouldAddSecurityDetailsToSession() {
        cmd.setSecurityQuestion(SECURITY_QUESTION_1);
        cmd.setSecurityAnswer(SECURITY_ANSWER_1);
        CartSessionData cartSessionData = CartSessionDataTestUtil.getTestCartSessionDataDTO1();
        service.addSecurityQuestionDetails(cmd, CUSTOMER_ID_1, cartSessionData);
        assertEquals(SECURITY_QUESTION_1, cartSessionData.getSecurityQuestion());
        assertEquals(SECURITY_ANSWER_1, cartSessionData.getSecurityAnswer());
    }

    @Test
    public void updateSecurityQuestionDetailsShouldChangeSecurityDetails() {
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        when(mockCardDataService.createOrUpdate((CardDTO) anyObject())).thenReturn(getTestCardDTO1());

        cmd = getTestSecurityQuestionCmd1();
        SecurityQuestionCmdImpl resultCmd = service.updateSecurityQuestionDetails(cmd);
        verify(mockCardDataService, atLeastOnce()).createOrUpdate((CardDTO) anyObject());
        assertNotNull(resultCmd);
        assertEquals(SecurityQuestionCmdTestUtil.SECURITY_QUESTION_1, resultCmd.getSecurityQuestion());
    }

    @Test(expected = AssertionError.class)
    public void updateSecurityQuestionDetailsShouldNotChangeSecurityDetails() {
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        when(mockCardDataService.createOrUpdate((CardDTO) anyObject())).thenReturn(getTestCardDTO1());

        SecurityQuestionCmdImpl resultCmd = service.updateSecurityQuestionDetails(new SecurityQuestionCmdImpl());

        assertNull(resultCmd);
    }

    @Test
    public void verifySecurityQuestionDetailsShouldReturnTrue() {
        CardDTO card = getTestCardDTO1();
        card.setSecurityQuestion(SECURITY_QUESTION_4);
        card.setSecurityAnswer(SECURITY_ANSWER_4);
        when(mockCardDataService.findById(anyLong())).thenReturn(card);
        assertTrue(service.verifySecurityQuestionDetails(getTestSecurityQuestionCmd1(), CARD_ID_1));
    }

    @Test
    public void verifySecurityQuestionDetailsShouldReturnFalse() {
        CardDTO card = getTestCardDTO1();
        card.setSecurityQuestion(SECURITY_QUESTION_1);
        card.setSecurityAnswer(SECURITY_ANSWER_1);
        when(mockCardDataService.findById(anyLong())).thenReturn(card);
        assertFalse(service.verifySecurityQuestionDetails(getTestSecurityQuestionCmd1(), CARD_ID_1));
    }

    @Test
    public void verifySecurityQuestionDetailsShouldReturnFalseWhenQuestionIsIncorrect() {
        CardDTO card = getTestCardDTO1();
        card.setSecurityQuestion(SECURITY_QUESTION_1);
        card.setSecurityAnswer(SECURITY_ANSWER_4);
        when(mockCardDataService.findById(anyLong())).thenReturn(card);
        assertFalse(service.verifySecurityQuestionDetails(getTestSecurityQuestionCmd1(), CARD_ID_1));
    }

    @Test
    public void verifySecurityQuestionDetailsShouldReturnFalseForNullSecurityQuestionAndAnswer() {
        CardDTO card = getTestCardDTO1();
        when(mockCardDataService.findById(anyLong())).thenReturn(card);
        assertFalse(service.verifySecurityQuestionDetails(getTestSecurityQuestionCmd1(), null));
    }

    @Test
    public void verifySecurityQuestionDetailsShouldReturnFalseForNullSecurityQuestion() {
        CardDTO card = getTestCardDTO1();
        card.setSecurityAnswer(SECURITY_ANSWER_1);
        when(mockCardDataService.findById(anyLong())).thenReturn(card);
        assertFalse(service.verifySecurityQuestionDetails(getTestSecurityQuestionCmd1(), null));
    }
}
