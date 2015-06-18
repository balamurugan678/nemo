package com.novacroft.nemo.tfl.online.controller;

import static com.novacroft.nemo.tfl.common.constant.PageAttribute.ADDRESSES_FOR_POSTCODE;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.ModelAndViewAssert.assertModelAttributeAvailable;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.common.application_service.CountrySelectListService;
import com.novacroft.nemo.common.application_service.PAFService;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.common.validator.PostcodeLookUpValidator;
import com.novacroft.nemo.common.validator.PostcodeValidator;
import com.novacroft.nemo.test_support.PaymentCardTestUtil;
import com.novacroft.nemo.tfl.common.application_service.PaymentCardService;
import com.novacroft.nemo.tfl.common.command.impl.ManagePaymentCardCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.PaymentCardCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.form_validator.PaymentCardDeleteValidator;
import com.novacroft.nemo.tfl.common.form_validator.PaymentCardEditValidator;

/**
 * ManagePaymentCardController unit tests
 */
public class ManagePaymentCardControllerTest {
    private ManagePaymentCardController controller;

    private PaymentCardService mockPaymentCardService;
    private PaymentCardDeleteValidator mockPaymentCardDeleteValidator;
    private PaymentCardEditValidator mockPaymentCardEditValidator;
    private PAFService mockPAFService;
    private CountrySelectListService mockCountrySelectListService;
    private PostcodeValidator mockPostcodeValidator;

    private BeanPropertyBindingResult mockResult;
    private ManagePaymentCardCmdImpl mockManagePaymentCardCmd;
    private PaymentCardCmdImpl mockPaymentCardCmd;
    private SelectListDTO mockSelectListDTO;
    private Model mockModel;
    private ModelAndView mockModelAndView;
    private PostcodeLookUpValidator mockPostcodeLookUpValidator;

    @Before
    public void setUp() {
        this.controller = mock(ManagePaymentCardController.class);

        this.mockPaymentCardService = mock(PaymentCardService.class);
        this.controller.paymentCardService = this.mockPaymentCardService;

        this.mockPaymentCardDeleteValidator = mock(PaymentCardDeleteValidator.class);
        this.controller.paymentCardDeleteValidator = this.mockPaymentCardDeleteValidator;

        this.mockPaymentCardEditValidator = mock(PaymentCardEditValidator.class);
        this.controller.paymentCardEditValidator = this.mockPaymentCardEditValidator;

        this.mockPAFService = mock(PAFService.class);
        this.controller.pafService = this.mockPAFService;

        this.mockCountrySelectListService = mock(CountrySelectListService.class);
        this.controller.countrySelectListService = this.mockCountrySelectListService;

        this.mockPostcodeValidator = mock(PostcodeValidator.class);
        this.controller.postcodeValidator = mockPostcodeValidator;
        
        this.mockPostcodeLookUpValidator = mock(PostcodeLookUpValidator.class);
        this.controller.postcodeLookUpValidator = mockPostcodeLookUpValidator;

        this.mockSelectListDTO = mock(SelectListDTO.class);
        this.mockModel = mock(Model.class);
        this.mockManagePaymentCardCmd = mock(ManagePaymentCardCmdImpl.class);
        this.mockModelAndView = mock(ModelAndView.class);
        this.mockPaymentCardCmd = mock(PaymentCardCmdImpl.class);
        this.mockResult = mock(BeanPropertyBindingResult.class);
    }

    @Test
    public void shouldPopulateCountrySelectList() {
        doCallRealMethod().when(this.controller).populateCountrySelectList(any(Model.class));
        when(this.mockModel.addAttribute(anyString(), anyObject())).thenReturn(this.mockModel);
        when(this.mockCountrySelectListService.getSelectList()).thenReturn(this.mockSelectListDTO);

        this.controller.populateCountrySelectList(this.mockModel);

        verify(this.mockModel).addAttribute(anyString(), anyObject());
        verify(this.mockCountrySelectListService).getSelectList();
    }

    @Test
    public void shouldShowPage() {
        when(this.controller.showPage()).thenCallRealMethod();
        when(this.mockPaymentCardService.getPaymentCards(anyLong())).thenReturn(this.mockManagePaymentCardCmd);
        ModelAndView result = this.controller.showPage();
        assertViewName(result, PageView.MANAGE_PAYMENT_CARD);
        assertModelAttributeAvailable(result, PageCommand.MANAGE_PAYMENT_CARD);
    }

    @Test
    public void shouldSelectPaymentCardWithoutId() {
        when(this.controller.selectPaymentCard(any(ManagePaymentCardCmdImpl.class))).thenCallRealMethod();
        when(this.mockManagePaymentCardCmd.getPaymentCardId()).thenReturn(null);
        when(this.controller.showPage()).thenReturn(this.mockModelAndView);

        this.controller.selectPaymentCard(this.mockManagePaymentCardCmd);

        verify(this.controller).showPage();
    }

    @Test
    public void shouldSelectPaymentCardWithId() {
        when(this.controller.selectPaymentCard(any(ManagePaymentCardCmdImpl.class))).thenCallRealMethod();
        when(this.mockManagePaymentCardCmd.getPaymentCardId()).thenReturn(PaymentCardTestUtil.TEST_PAYMENT_CARD_ID_1);
        when(this.mockPaymentCardService.getPaymentCard(anyLong())).thenReturn(this.mockPaymentCardCmd);

        ModelAndView result = this.controller.selectPaymentCard(this.mockManagePaymentCardCmd);

        assertViewName(result, PageView.EDIT_PAYMENT_CARD);
        assertModelAttributeAvailable(result, PageCommand.PAYMENT_CARD);

        verify(this.mockPaymentCardService).getPaymentCard(anyLong());
    }

    @Test
    public void findAddressesForPostcodeShouldPopulateListForValidPostCode() {
        when(this.controller.findAddressesForPostcode(any(PaymentCardCmdImpl.class), any(BindingResult.class)))
                .thenCallRealMethod();

        doNothing().when(this.controller).postcodeToUppercase(any(PaymentCardCmdImpl.class));
        doNothing().when(this.mockPostcodeValidator).validate(anyObject(), any(Errors.class));
        when(this.mockResult.hasErrors()).thenReturn(false);
        when(this.mockPAFService.getAddressesForPostcodeSelectList(anyString())).thenReturn(this.mockSelectListDTO);

        ModelAndView result = this.controller.findAddressesForPostcode(this.mockPaymentCardCmd, this.mockResult);

        assertViewName(result, PageView.EDIT_PAYMENT_CARD);
        assertModelAttributeAvailable(result, ADDRESSES_FOR_POSTCODE);

        verify(this.mockPostcodeValidator).validate(anyObject(), any(Errors.class));
        verify(this.controller).postcodeToUppercase(any(PaymentCardCmdImpl.class));
        verify(this.mockPAFService).getAddressesForPostcodeSelectList(anyString());
    }

    @Test
    public void findAddressesForPostcodeShouldNotAcceptInvalidPostcode() {
        when(this.controller.findAddressesForPostcode(any(PaymentCardCmdImpl.class), any(BindingResult.class)))
                .thenCallRealMethod();

        doNothing().when(this.controller).postcodeToUppercase(any(PaymentCardCmdImpl.class));
        doNothing().when(this.mockPostcodeValidator).validate(anyObject(), any(Errors.class));
        doNothing().when(this.mockPostcodeLookUpValidator).validate(anyObject(), any(Errors.class));
        when(this.mockResult.hasErrors()).thenReturn(true);
        when(this.mockPAFService.getAddressesForPostcodeSelectList(anyString())).thenReturn(this.mockSelectListDTO);

        ModelAndView result = this.controller.findAddressesForPostcode(this.mockPaymentCardCmd, this.mockResult);

        assertViewName(result, PageView.EDIT_PAYMENT_CARD);
        assertModelAttributeAvailable(result, PageCommand.PAYMENT_CARD);

        verify(this.mockPostcodeValidator).validate(anyObject(), any(Errors.class));
        verify(this.controller, never()).postcodeToUppercase(any(PaymentCardCmdImpl.class));
        verify(this.mockPAFService, never()).getAddressesForPostcodeSelectList(anyString());
    }

    @Test
    public void shouldDeletePaymentCard() {
        when(this.controller.deletePaymentCard(any(PaymentCardCmdImpl.class), any(BindingResult.class))).thenCallRealMethod();

        doNothing().when(this.mockPaymentCardDeleteValidator).validate(anyObject(), any(Errors.class));
        when(this.mockResult.hasErrors()).thenReturn(false);
        doNothing().when(this.mockPaymentCardService).deletePaymentCard(any(PaymentCardCmdImpl.class));
        when(this.controller.showPage()).thenReturn(this.mockModelAndView);

        this.controller.deletePaymentCard(this.mockPaymentCardCmd, this.mockResult);

        verify(this.mockPaymentCardDeleteValidator).validate(anyObject(), any(Errors.class));
        verify(this.mockPaymentCardService).deletePaymentCard(any(PaymentCardCmdImpl.class));
        verify(this.controller).showPage();
    }

    @Test
    public void deletePaymentCardShouldNotDeleteWithValidationErrors() {
        when(this.controller.deletePaymentCard(any(PaymentCardCmdImpl.class), any(BindingResult.class))).thenCallRealMethod();

        doNothing().when(this.mockPaymentCardDeleteValidator).validate(anyObject(), any(Errors.class));
        when(this.mockResult.hasErrors()).thenReturn(true);
        doNothing().when(this.mockPaymentCardService).deletePaymentCard(any(PaymentCardCmdImpl.class));
        when(this.controller.showPage()).thenReturn(this.mockModelAndView);

        ModelAndView result = this.controller.deletePaymentCard(this.mockPaymentCardCmd, this.mockResult);

        assertViewName(result, PageView.EDIT_PAYMENT_CARD);
        assertModelAttributeAvailable(result, PageCommand.PAYMENT_CARD);

        verify(this.mockPaymentCardDeleteValidator).validate(anyObject(), any(Errors.class));
        verify(this.mockPaymentCardService, never()).deletePaymentCard(any(PaymentCardCmdImpl.class));
        verify(this.controller, never()).showPage();
    }

    @Test
    public void shouldUpdatePaymentCard() {
        when(this.controller.updatePaymentCard(any(PaymentCardCmdImpl.class), any(BindingResult.class))).thenCallRealMethod();

        doNothing().when(this.mockPaymentCardEditValidator).validate(anyObject(), any(Errors.class));
        when(this.mockResult.hasErrors()).thenReturn(false);
        doNothing().when(this.mockPaymentCardService).updatePaymentCard(any(PaymentCardCmdImpl.class));
        when(this.controller.showPage()).thenReturn(this.mockModelAndView);

        this.controller.updatePaymentCard(this.mockPaymentCardCmd, this.mockResult);

        verify(this.mockPaymentCardEditValidator).validate(anyObject(), any(Errors.class));
        verify(this.mockPaymentCardService).updatePaymentCard(any(PaymentCardCmdImpl.class));
        verify(this.controller).showPage();
    }

    @Test
    public void updatePaymentCardShouldNotUpdateWithValidationErrors() {
        when(this.controller.updatePaymentCard(any(PaymentCardCmdImpl.class), any(BindingResult.class))).thenCallRealMethod();

        doNothing().when(this.mockPaymentCardEditValidator).validate(anyObject(), any(Errors.class));
        when(this.mockResult.hasErrors()).thenReturn(true);
        doNothing().when(this.mockPaymentCardService).updatePaymentCard(any(PaymentCardCmdImpl.class));
        when(this.controller.showPage()).thenReturn(this.mockModelAndView);

        ModelAndView result = this.controller.updatePaymentCard(this.mockPaymentCardCmd, this.mockResult);

        assertViewName(result, PageView.EDIT_PAYMENT_CARD);
        assertModelAttributeAvailable(result, PageCommand.PAYMENT_CARD);

        verify(this.mockPaymentCardEditValidator).validate(anyObject(), any(Errors.class));
        verify(this.mockPaymentCardService, never()).updatePaymentCard(any(PaymentCardCmdImpl.class));
        verify(this.controller, never()).showPage();
    }

}
