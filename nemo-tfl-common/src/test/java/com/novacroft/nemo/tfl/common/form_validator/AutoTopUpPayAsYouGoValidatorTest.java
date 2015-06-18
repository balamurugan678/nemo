package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO10;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO2;
import static com.novacroft.nemo.test_support.CardTestUtil.AUTO_TOPUP_AMT;
import static com.novacroft.nemo.test_support.CardTestUtil.CARD_ID;
import static com.novacroft.nemo.test_support.CardTestUtil.HIGH_CREDIT_BALANCE;
import static com.novacroft.nemo.test_support.CardTestUtil.MINIMUM_AUTO_TOP_UP_AMT;
import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTO1;
import static com.novacroft.nemo.tfl.common.constant.SystemParameterCode.PAY_AS_YOU_GO_LIMIT;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.test_support.CartItemTestUtil;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.PayAsYouGoCmd;
import com.novacroft.nemo.tfl.common.command.TravelCardCmd;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;

public class AutoTopUpPayAsYouGoValidatorTest {
    private AutoTopUpPayAsYouGoValidator validator;
    private CartItemCmdImpl cmd;
    private CardDataService mockCardDataService;
    private GetCardService mockGetCardService;
    private SystemParameterService mockSystemParameterService;
    
    private static final Integer CREDIT_BALANCE_1 = 1500;
    private static final Integer CREDIT_BALANCE_2 = 700;
    private static final Integer CREDIT_BALANCE_3 = 9000;
    private static final Integer AUTO_TOP_UP_AMOUNT = 2000;
    private static final Integer AUTO_TOP_UP_AMOUNT_ZERO = 0;
    
    @Before
    public void setUp(){
        validator = new AutoTopUpPayAsYouGoValidator();
        cmd = new CartItemCmdImpl();
        mockCardDataService = mock(CardDataService.class);
        mockGetCardService = mock(GetCardService.class);
        mockSystemParameterService = mock(SystemParameterService.class);

        validator.systemParameterService = mockSystemParameterService;
        validator.cardDataService = mockCardDataService;
        validator.getCardService = mockGetCardService;
        
        when(mockSystemParameterService.getIntegerParameterValue(anyString())).thenReturn(MINIMUM_AUTO_TOP_UP_AMT);
    }
    
    @Test
    public void shouldSupportClass() {
        assertTrue(validator.supports(PayAsYouGoCmd.class));
    }

    @Test
    public void shouldNotSupportClass() {
        assertFalse(validator.supports(TravelCardCmd.class));
    }

    @Test
    public void shouldValidateTest(){
        cmd.setAutoTopUpCreditBalance(CREDIT_BALANCE_1);
        cmd.setAutoTopUpAmt(AUTO_TOP_UP_AMOUNT);
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertFalse(errors.hasErrors());
    }
    
    @Test
    public void shouldNotValidateWithNoAutoTopUpCreditBalanceTest(){
        cmd.setAutoTopUpAmt(AUTO_TOP_UP_AMOUNT);
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }
    
    @Test
    public void shouldNotValidateWithNullAutoTopUpAmountTest(){
        cmd.setAutoTopUpCreditBalance(CREDIT_BALANCE_1);
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }
    
    @Test
    public void shouldNotValidateWithLowCreditBalanceTest(){
        cmd.setAutoTopUpCreditBalance(CREDIT_BALANCE_2);
        cmd.setAutoTopUpAmt(AUTO_TOP_UP_AMOUNT);
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }
    
    @Test
    public void shouldNotValidateWithZeroAutoTopUpAmountTest(){
        cmd.setAutoTopUpCreditBalance(CREDIT_BALANCE_1);
        cmd.setAutoTopUpAmt(AUTO_TOP_UP_AMOUNT_ZERO);
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        assertTrue(errors.hasErrors());
    }
    
    @Test
    public void shouldNotValidateWithPayAsYouGoBalanceIsGreaterThanLimitInSystem() {
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        when(mockGetCardService.getCard(anyString())).thenReturn(getTestCardInfoResponseV2DTO10());
        when(mockSystemParameterService.getIntegerParameterValue(PAY_AS_YOU_GO_LIMIT.code())).thenReturn(CartItemTestUtil.PAY_AS_YOU_GO_LIMIT);
        
        cmd.setCreditBalance(HIGH_CREDIT_BALANCE);
        cmd.setAutoTopUpCreditBalance(CREDIT_BALANCE_3);
        cmd.setAutoTopUpAmt(AUTO_TOPUP_AMT);
        cmd.setCardId(CARD_ID);
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        
        verify(mockCardDataService, atLeastOnce()).findById(anyLong());
        verify(mockGetCardService, atLeastOnce()).getCard(anyString());
        verify(mockSystemParameterService, atLeastOnce()).getIntegerParameterValue(PAY_AS_YOU_GO_LIMIT.code());
        assertTrue(errors.hasErrors());
    }
    
    @Test
    public void shouldNotValidateWithPayAsYouGoBalanceIsGreaterThanLimitInSystemNullBalance() {
        CardInfoResponseV2DTO cardInfoResponsetV2DTO = getTestCardInfoResponseV2DTO2();
        cardInfoResponsetV2DTO.getPpvDetails().setBalance(null);
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        when(mockGetCardService.getCard(anyString())).thenReturn(cardInfoResponsetV2DTO);
        when(mockSystemParameterService.getIntegerParameterValue(PAY_AS_YOU_GO_LIMIT.code())).thenReturn(CartItemTestUtil.PAY_AS_YOU_GO_LIMIT);
        
        cmd.setCreditBalance(HIGH_CREDIT_BALANCE);
        cmd.setAutoTopUpCreditBalance(CREDIT_BALANCE_1);
        cmd.setAutoTopUpAmt(AUTO_TOPUP_AMT);
        cmd.setCardId(CARD_ID);
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        
        verify(mockCardDataService, atLeastOnce()).findById(anyLong());
        verify(mockGetCardService, atLeastOnce()).getCard(anyString());
        assertFalse(errors.hasErrors());
    }
    
    @Test
    public void shouldNotValidateWithPayAsYouGoBalanceIsGreaterThanLimitInSystemNoCardIssued() {
        CardDTO cardDTO = getTestCardDTO1();
        cardDTO.setCardNumber(null);
        when(mockCardDataService.findById(anyLong())).thenReturn(cardDTO);
        when(mockGetCardService.getCard(anyString())).thenReturn(getTestCardInfoResponseV2DTO2());
        when(mockSystemParameterService.getIntegerParameterValue(PAY_AS_YOU_GO_LIMIT.code())).thenReturn(CartItemTestUtil.PAY_AS_YOU_GO_LIMIT);
        
        cmd.setCreditBalance(HIGH_CREDIT_BALANCE);
        cmd.setAutoTopUpCreditBalance(CREDIT_BALANCE_1);
        cmd.setAutoTopUpAmt(AUTO_TOPUP_AMT);
        cmd.setCardId(CARD_ID);
        Errors errors = new BeanPropertyBindingResult(cmd, "cmd");
        validator.validate(cmd, errors);
        
        verify(mockCardDataService, atLeastOnce()).findById(anyLong());
        assertFalse(errors.hasErrors());
    }
    
}
