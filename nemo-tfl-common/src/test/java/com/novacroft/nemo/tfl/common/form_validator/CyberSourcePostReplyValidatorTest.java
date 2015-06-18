package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.test_support.PaymentGatewayTestUtil.REQ_TRANSACTION_UUID_2;
import static com.novacroft.nemo.test_support.PaymentGatewayTestUtil.getTestAcceptReply;
import static com.novacroft.nemo.test_support.SettlementTestUtil.getTestPaymentCardSettlementDTO1;
import static com.novacroft.nemo.test_support.SettlementTestUtil.getTestPaymentCardSettlementDTOEmptyTransactionUuid;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.command.impl.CommonLoginCmd;
import com.novacroft.nemo.tfl.common.application_service.cyber_source.CyberSourceSecurityService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.cyber_source.CyberSourcePostReplyField;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.PaymentGatewayReplyDTO;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourcePostReplyDTO;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourceSoapReplyDTO;

/**
 * Unit tests for CyberSourcePostReplyValidator
 */
public class CyberSourcePostReplyValidatorTest {
    private CyberSourcePostReplyValidator validator;
    private CyberSourceSecurityService mockCyberSourceSecurityService;


    @Before
    public void setUp() {
        this.validator = new CyberSourcePostReplyValidator();
        this.mockCyberSourceSecurityService = mock(CyberSourceSecurityService.class);
        this.validator.cyberSourceSecurityService = mockCyberSourceSecurityService;
    }

    @Test
    public void shouldSupportClass() {
        assertTrue(this.validator.supports(PaymentGatewayReplyDTO.class));
    }

    @Test
    public void shouldNotSupportClass() {
        assertFalse(this.validator.supports(CommonLoginCmd.class));
    }

    @Test
    public void shouldValidate() {
        when(this.mockCyberSourceSecurityService.isPostReplySignatureValid(any(CyberSourcePostReplyDTO.class)))
                .thenReturn(true);
        CartCmdImpl cartCmd = new CartCmdImpl();
        CartDTO cartDTO = new CartDTO();
        cartDTO.setPaymentCardSettlement(getTestPaymentCardSettlementDTO1());
        cartDTO.setCyberSourceReply(getTestAcceptReply());
        cartCmd.setCartDTO(cartDTO);
        Errors errors = new BeanPropertyBindingResult(cartCmd, "cmd");
        this.validator.validate(cartDTO, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithNullReply() {
        CartCmdImpl cartCmd = new CartCmdImpl();
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCyberSourceReply(null);
        cartCmd.setCartDTO(cartDTO);
        Errors errors = new BeanPropertyBindingResult(cartCmd, "cmd");
        this.validator.validate(cartDTO, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithMissingReplyAuthAmount() {
        CartCmdImpl cartCmd = new CartCmdImpl();
        CartDTO cartDTO = new CartDTO();
        CyberSourcePostReplyDTO cyberSourcePostReply = getTestAcceptReply();
        cyberSourcePostReply.setReplyArgument(CyberSourcePostReplyField.AUTH_AMOUNT, null);
        cartDTO.setCyberSourceReply(cyberSourcePostReply);
        cartCmd.setCartDTO(cartDTO);
        Errors errors = new BeanPropertyBindingResult(cartCmd, "cmd");
        this.validator.validate(cartDTO, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithMissingReplyAuthTime() {
        CartCmdImpl cartCmd = new CartCmdImpl();
        CartDTO cartDTO = new CartDTO();
        CyberSourcePostReplyDTO cyberSourcePostReply = getTestAcceptReply();
        cyberSourcePostReply.setReplyArgument(CyberSourcePostReplyField.AUTH_TIME, null);
        cartDTO.setCyberSourceReply(cyberSourcePostReply);
        cartCmd.setCartDTO(cartDTO);
        Errors errors = new BeanPropertyBindingResult(cartCmd, "cmd");
        this.validator.validate(cartDTO, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithMissingReplyAuthTransRefNo() {
        CartCmdImpl cartCmd = new CartCmdImpl();
        CartDTO cartDTO = new CartDTO();
        CyberSourcePostReplyDTO cyberSourcePostReply = getTestAcceptReply();
        cyberSourcePostReply.setReplyArgument(CyberSourcePostReplyField.AUTH_TRANS_REF_NO, null);
        cartDTO.setCyberSourceReply(cyberSourcePostReply);
        cartCmd.setCartDTO(cartDTO);
        Errors errors = new BeanPropertyBindingResult(cartCmd, "cmd");
        this.validator.validate(cartDTO, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithMissingReplyDecision() {
        CartCmdImpl cartCmd = new CartCmdImpl();
        CartDTO cartDTO = new CartDTO();
        CyberSourcePostReplyDTO cyberSourcePostReply = getTestAcceptReply();
        cyberSourcePostReply.setReplyArgument(CyberSourcePostReplyField.DECISION, null);
        cartDTO.setCyberSourceReply(cyberSourcePostReply);
        cartCmd.setCartDTO(cartDTO);
        Errors errors = new BeanPropertyBindingResult(cartCmd, "cmd");
        this.validator.validate(cartDTO, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithMissingReplyReqReferenceNumber() {
        CartCmdImpl cartCmd = new CartCmdImpl();
        CartDTO cartDTO = new CartDTO();
        CyberSourcePostReplyDTO cyberSourcePostReply = getTestAcceptReply();
        cyberSourcePostReply.setReplyArgument(CyberSourcePostReplyField.REQ_REFERENCE_NUMBER, null);
        cartDTO.setCyberSourceReply(cyberSourcePostReply);
        cartCmd.setCartDTO(cartDTO);
        Errors errors = new BeanPropertyBindingResult(cartCmd, "cmd");
        this.validator.validate(cartDTO, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithMissingReplyReqTransactionUuid() {
        CartCmdImpl cartCmd = new CartCmdImpl();
        CartDTO cartDTO = new CartDTO();
        CyberSourcePostReplyDTO cyberSourcePostReply = getTestAcceptReply();
        cyberSourcePostReply.setReplyArgument(CyberSourcePostReplyField.REQ_TRANSACTION_UUID, null);
        cartDTO.setCyberSourceReply(cyberSourcePostReply);
        cartCmd.setCartDTO(cartDTO);
        Errors errors = new BeanPropertyBindingResult(cartCmd, "cmd");
        this.validator.validate(cartDTO, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithMissingReplySignedDateTime() {
        CartCmdImpl cartCmd = new CartCmdImpl();
        CartDTO cartDTO = new CartDTO();
        CyberSourcePostReplyDTO cyberSourcePostReply = getTestAcceptReply();
        cyberSourcePostReply.setReplyArgument(CyberSourcePostReplyField.SIGNED_DATE_TIME, null);
        cartDTO.setCyberSourceReply(cyberSourcePostReply);
        cartCmd.setCartDTO(cartDTO);
        Errors errors = new BeanPropertyBindingResult(cartCmd, "cmd");
        this.validator.validate(cartDTO, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithMissingReplySignedFieldNames() {
        CartCmdImpl cartCmd = new CartCmdImpl();
        CartDTO cartDTO = new CartDTO();
        CyberSourcePostReplyDTO cyberSourcePostReply = getTestAcceptReply();
        cyberSourcePostReply.setReplyArgument(CyberSourcePostReplyField.SIGNED_FIELD_NAMES, null);
        cartDTO.setCyberSourceReply(cyberSourcePostReply);
        cartCmd.setCartDTO(cartDTO);
        Errors errors = new BeanPropertyBindingResult(cartCmd, "cmd");
        this.validator.validate(cartDTO, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithMissingReplyTransactionId() {
        CartCmdImpl cartCmd = new CartCmdImpl();
        CartDTO cartDTO = new CartDTO();
        CyberSourcePostReplyDTO cyberSourcePostReply = getTestAcceptReply();
        cyberSourcePostReply.setReplyArgument(CyberSourcePostReplyField.TRANSACTION_ID, null);
        cartDTO.setCyberSourceReply(cyberSourcePostReply);
        cartCmd.setCartDTO(cartDTO);
        Errors errors = new BeanPropertyBindingResult(cartCmd, "cmd");
        this.validator.validate(cartDTO, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldNotValidateWithNonMatchingTransactionUuid() {
        CartCmdImpl cartCmd = new CartCmdImpl();
        CartDTO cartDTO = new CartDTO();
        cartDTO.setPaymentCardSettlement(getTestPaymentCardSettlementDTO1());
        CyberSourcePostReplyDTO cyberSourcePostReply = getTestAcceptReply();
        cyberSourcePostReply.setReplyArgument(CyberSourcePostReplyField.REQ_TRANSACTION_UUID, REQ_TRANSACTION_UUID_2);
        cartDTO.setCyberSourceReply(cyberSourcePostReply);
        cartCmd.setCartDTO(cartDTO);
        Errors errors = new BeanPropertyBindingResult(cartCmd, "cmd");
        this.validator.validate(cartDTO, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void isNotTransactionUuidRequestSameAsReplyShouldReturnTrueWithMissingRequestUuid() {
        assertTrue(this.validator.isNotTransactionUuidRequestSameAsReply("", "X"));
    }

    @Test
    public void isNotTransactionUuidRequestSameAsReplyShouldReturnTrueWithMissingReplyUuid() {
        assertTrue(this.validator.isNotTransactionUuidRequestSameAsReply("X", ""));
    }

    @Test
    public void isNotTransactionUuidRequestSameAsReplyShouldReturnTrueWithMissMatchedUuid() {
        assertTrue(this.validator.isNotTransactionUuidRequestSameAsReply("X", "Y"));
    }

    @Test
    public void isNotTransactionUuidRequestSameAsReplyShouldReturnFalseWithMatchedUuid() {
        assertFalse(this.validator.isNotTransactionUuidRequestSameAsReply("X", "X"));
    }
    
    @Test
    public void shouldNotValidateSignatureWrongInstanceOF(){
        PaymentGatewayReplyDTO mockCmd = mock(PaymentGatewayReplyDTO.class);
        when(mockCmd.getCyberSourceReply()).thenReturn(new CyberSourceSoapReplyDTO());
        Errors errors = mock(Errors.class);
        validator.validateSignature(mockCmd, errors);
        
        verify(errors, never()).rejectValue(anyString(), anyString(), anyString());
    }
    
    @Test
    public void shouldNotValidatetransactionUuidCyberSourceNull(){
        PaymentGatewayReplyDTO mockCmd = mock(PaymentGatewayReplyDTO.class);
        when(mockCmd.getCyberSourceReply()).thenReturn(null);
        Errors errors = mock(Errors.class);
        validator.validateTransactionUuid(mockCmd, errors);
        
        verify(errors, atLeastOnce()).reject(anyString(), (Object[])any(), anyString());
    }
    
    @Test
    public void shouldNotValidatetransactionUuidPaymentCardSettlementNull(){
        PaymentGatewayReplyDTO mockCmd = mock(PaymentGatewayReplyDTO.class);
        when(mockCmd.getCyberSourceReply()).thenReturn(getTestAcceptReply());
        when(mockCmd.getPaymentCardSettlement()).thenReturn(null);
        Errors errors = mock(Errors.class);
        validator.validateTransactionUuid(mockCmd, errors);
        
        verify(errors, atLeastOnce()).reject(anyString(), (Object[])any(), anyString());
    }
    
    @Test
    public void shouldNotValidatetransactionUuidRequestSameAsReply(){
        PaymentGatewayReplyDTO mockCmd = mock(PaymentGatewayReplyDTO.class);
        when(mockCmd.getCyberSourceReply()).thenReturn(getTestAcceptReply());
        when(mockCmd.getPaymentCardSettlement()).thenReturn(getTestPaymentCardSettlementDTOEmptyTransactionUuid());
        Errors errors = mock(Errors.class);
        validator.validateTransactionUuid(mockCmd, errors);
        
        verify(errors, atLeastOnce()).reject(anyString(), (Object[])any(), anyString());
    }
}
