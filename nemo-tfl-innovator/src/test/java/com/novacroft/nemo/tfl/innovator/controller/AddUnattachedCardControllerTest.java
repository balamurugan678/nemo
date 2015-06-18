package com.novacroft.nemo.tfl.innovator.controller;

import static com.novacroft.nemo.test_support.CardTestUtil.getTestListOfValidationErrors;
import static com.novacroft.nemo.test_support.CustomerTestUtil.CUSTOMER_ID_8;
import static com.novacroft.nemo.test_support.CustomerTestUtil.NULL_CARD_NUMBER;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.domain.cubic.HolderDetails;
import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.test_support.CustomerTestUtil;
import com.novacroft.nemo.tfl.common.application_service.AddUnattachedCardService;
import com.novacroft.nemo.tfl.common.application_service.PersonalDetailsService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.impl.AddUnattachedCardCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.PersonalDetailsCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;
import com.novacroft.nemo.tfl.common.constant.Page;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.form_validator.OysterCardValidator;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CartSessionData;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;
import com.sun.grizzly.http.servlet.HttpSessionImpl;

public class AddUnattachedCardControllerTest {

    private AddUnattachedCardController controller;
    private AddUnattachedCardController mockedcontroller;
    private HttpServletRequest request;
    private CardDataService mockCardOysterOnlineService;
    private GetCardService mockGetCardService;
    private PersonalDetailsService personalDetailsService;
    private CustomerDataService mockCustomerDataService;
    private AddUnattachedCardCmdImpl addUnattachedCardCmdImpl;
    private HttpSession session;
    private OysterCardValidator cardValidator;
    private AddUnattachedCardService mockAddUnattachedCardService;
    private MessageSource mockMessageSource;
    
    private BeanPropertyBindingResult mockBindingResult;
    private ApplicationContext applicationContext;

    @Before
    public void setUp() throws Exception {
        mockedcontroller = mock(AddUnattachedCardController.class);
        controller = new AddUnattachedCardController();
        request = mock(HttpServletRequest.class);
        mockGetCardService = mock(GetCardService.class);
        mockCardOysterOnlineService = mock(CardDataService.class);
        personalDetailsService = mock(PersonalDetailsService.class);
        mockCustomerDataService = mock(CustomerDataService.class);
        session = mock(HttpSessionImpl.class);
        mockBindingResult = mock(BeanPropertyBindingResult.class);
        cardValidator = mock(OysterCardValidator.class);
        mockAddUnattachedCardService = mock(AddUnattachedCardService.class);
        mockMessageSource = mock(MessageSource.class);
        
        applicationContext = mock(ApplicationContext.class);

        controller.getCardService = mockGetCardService;
        controller.cardOysterOnlineService = mockCardOysterOnlineService;
        controller.personalDetailsService = personalDetailsService;
        controller.customerDataService = mockCustomerDataService;
        controller.cardValidator = cardValidator;
        controller.addUnattachedCardService = mockAddUnattachedCardService;
        controller.messageSource = mockMessageSource; 
        
        mockedcontroller.getCardService = mockGetCardService;
        mockedcontroller.cardOysterOnlineService = mockCardOysterOnlineService;
        mockedcontroller.personalDetailsService = personalDetailsService;
        mockedcontroller.customerDataService = mockCustomerDataService;
        mockedcontroller.cardValidator = cardValidator;
        mockedcontroller.addUnattachedCardService = mockAddUnattachedCardService;
        mockedcontroller.messageSource = mockMessageSource; 

        
    }

    @Test
    public void viewTest() {

        ModelAndView result = controller.view(null, addUnattachedCardCmdImpl, session);
        assertEquals(PageView.ADD_UNATTACHED_CARD, result.getViewName());

    }

    @Test
    public void checkmockedModelTestPass() {
        AddUnattachedCardCmdImpl model = new AddUnattachedCardCmdImpl();
        model.setCardNumber("01081");
        controller.setApplicationContext(applicationContext);
        String messages = controller.checkSearch(model, mockBindingResult);
        assertEquals(messages, "{}");

    }

    @Test
    public void checkmockedModelTestFail() {
        AddUnattachedCardCmdImpl model = new AddUnattachedCardCmdImpl();
        model.setCardNumber("0108124307");
        controller.setApplicationContext(applicationContext);
        String messages = controller.checkSearch(model, mockBindingResult);
        assertEquals(messages, "{}");
    }

    @Test
    public void addUnattachedCardControllerTest() {

    }

    @Test
    public void attachCardTestShouldRedirectToCustomer() {
        AddUnattachedCardCmdImpl model = new AddUnattachedCardCmdImpl();
        CustomerDTO customer = new CustomerDTO();
        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        model.setCustomerId("1");
        customer.setId(2L);

        when(mockBindingResult.hasErrors()).thenReturn(Boolean.FALSE);
        when(mockCustomerDataService.findById(anyLong())).thenReturn(null);
        when(mockGetCardService.getCard(anyString())).thenReturn(cardInfoResponseV2DTO);
        when(mockCustomerDataService.createOrUpdate(any(CustomerDTO.class))).thenReturn(customer);

        ModelAndView result = controller.attachCard(model, mockBindingResult, request, session);
        assertEquals(PageUrl.INV_CUSTOMER, ((RedirectView) result.getView()).getUrl());
    }
    
    @Test
    public void attachCardTestShouldRedirectToTransfers() {
    	AddUnattachedCardCmdImpl model = new AddUnattachedCardCmdImpl();
    	CustomerDTO customer = new CustomerDTO();
    	CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
    	model.setCustomerId("1");
    	customer.setId(2L);
    	CartSessionData cartSessionData = new CartSessionData(3L);
    	cartSessionData.setPageName(Page.TRANSFER_PRODUCT);
    	
    	when(session.getAttribute(CartAttribute.SESSION_ATTRIBUTE_SHOPPING_CART_DATA)).thenReturn(cartSessionData);
    	when(mockBindingResult.hasErrors()).thenReturn(Boolean.FALSE);
    	when(mockCustomerDataService.findById(anyLong())).thenReturn(CustomerTestUtil.getTestCustomerDTO1());
    	when(mockGetCardService.getCard(anyString())).thenReturn(cardInfoResponseV2DTO);
    	when(mockCustomerDataService.createOrUpdate(any(CustomerDTO.class))).thenReturn(customer);
    	when(mockAddUnattachedCardService.createNewCustomer(any(AddUnattachedCardCmdImpl.class), any(BindingResult.class))).thenReturn(CustomerTestUtil.getTestCustomerDTO1());
    	when(mockCardOysterOnlineService.findByCardNumber(anyString())).thenReturn(CardTestUtil.getTestCardDTO1());

        ModelAndView result = controller.attachCard(model, mockBindingResult, request, session);
        assertEquals(PageUrl.TRANSFER_PRODUCT, ((RedirectView) result.getView()).getUrl());
    }

    @Test
    public void mockedModelCardTest() {
        addUnattachedCardCmdImpl = new AddUnattachedCardCmdImpl();
        HolderDetails newHolder = mock(HolderDetails.class);
        PersonalDetailsCmdImpl personalDetails = mock(PersonalDetailsCmdImpl.class);

        addUnattachedCardCmdImpl.setCardNumber("010812430771");

        CardInfoResponseV2DTO cardInfoResponseV2DTO = new CardInfoResponseV2DTO();
        cardInfoResponseV2DTO.setHolderDetails(newHolder);
        CardDTO card = new CardDTO();
        addUnattachedCardCmdImpl.setCustomerId("1");
        CustomerDTO customer = new CustomerDTO();

        when(mockGetCardService.getCard(anyString())).thenReturn(cardInfoResponseV2DTO);
        when(mockCardOysterOnlineService.findByCardNumber(anyString())).thenReturn(card);
        when(mockCustomerDataService.findById(anyLong())).thenReturn(customer);
        when(personalDetailsService.getPersonalDetailsByCustomerId(anyLong())).thenReturn(personalDetails);
        when(mockAddUnattachedCardService.retrieveOysterDetailsByCustomerID(1L)).thenReturn(personalDetails);

        when(personalDetails.getCounty()).thenReturn("");
        when(personalDetails.getPostcode()).thenReturn("");
        when(personalDetails.getFirstName()).thenReturn("");
        when(personalDetails.getLastName()).thenReturn("");

        when(newHolder.getCounty()).thenReturn("");
        when(newHolder.getPostcode()).thenReturn("");
        when(newHolder.getFirstName()).thenReturn("");
        when(newHolder.getLastName()).thenReturn("");

        controller.searchCard(addUnattachedCardCmdImpl);

    }
    
    @Test
    public void viewTestWithCustomerId() {
    	addUnattachedCardCmdImpl = new AddUnattachedCardCmdImpl();
        ModelAndView result = controller.view(CUSTOMER_ID_8, addUnattachedCardCmdImpl, session);
        assertEquals(PageView.ADD_UNATTACHED_CARD, result.getViewName());
    }

    @Test
    public void checkSearchShouldThrowError() {
        AddUnattachedCardCmdImpl model = new AddUnattachedCardCmdImpl();
        model.setCardNumber(NULL_CARD_NUMBER);
        when(mockBindingResult.getAllErrors()).thenReturn(getTestListOfValidationErrors());
        when(mockBindingResult.hasErrors()).thenReturn(true);
        controller.checkSearch(model, mockBindingResult);
        assertTrue(mockBindingResult.hasErrors());
    }
    
    @Test
    public void attachcardTestShouldThrowError() {
        AddUnattachedCardCmdImpl model = new AddUnattachedCardCmdImpl();
        CustomerDTO customer = new CustomerDTO();
        model.setCustomerId("1");
        customer.setId(null);
        when(mockBindingResult.getAllErrors()).thenReturn(getTestListOfValidationErrors());
        when(mockBindingResult.hasErrors()).thenReturn(Boolean.TRUE);
        when(mockCustomerDataService.findById(anyLong())).thenReturn(null);
        when(mockAddUnattachedCardService.createNewCustomer(model, mockBindingResult)).thenReturn(customer);
        controller.attachCard(model, mockBindingResult, request, session);
        assertTrue(mockBindingResult.hasErrors());
    }
    
}

