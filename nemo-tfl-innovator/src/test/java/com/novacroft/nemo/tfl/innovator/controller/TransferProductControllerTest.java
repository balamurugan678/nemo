package com.novacroft.nemo.tfl.innovator.controller;

import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO10;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO19;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO7;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO9;
import static com.novacroft.nemo.test_support.CardPreferencesTestUtil.getTestCardPreferencesCmd1;
import static com.novacroft.nemo.test_support.CardPreferencesTestUtil.getTestCardPreferencesDTO1;
import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTO1;
import static com.novacroft.nemo.test_support.CartSessionDataTestUtil.getTestCartSessionDataDTO1;
import static com.novacroft.nemo.test_support.CartSessionDataTestUtil.getTestCartSessionDataDTO4;
import static com.novacroft.nemo.test_support.CartTestUtil.getNewCartDTOWithItem;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.CARD_ID_1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.test_support.CardPreferencesTestUtil;
import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.test_support.CartTestUtil;
import com.novacroft.nemo.test_support.PersonalDetailsCmdTestUtil;
import com.novacroft.nemo.test_support.SelectListTestUtil;
import com.novacroft.nemo.test_support.TransferTestUtil;
import com.novacroft.nemo.tfl.common.application_service.AddUnattachedCardService;
import com.novacroft.nemo.tfl.common.application_service.CardPreferencesService;
import com.novacroft.nemo.tfl.common.application_service.CardService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.LocationSelectListService;
import com.novacroft.nemo.tfl.common.application_service.TransferSourceCardService;
import com.novacroft.nemo.tfl.common.application_service.TransferTargetCardService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.constant.TransferConstants;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CardPreferencesDataService;
import com.novacroft.nemo.tfl.common.form_validator.PickUpLocationValidator;
import com.novacroft.nemo.tfl.common.form_validator.TransferProductValidator;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.util.CartUtil;

/**
 * Unit tests for TransferProduct Controller
 */
public class TransferProductControllerTest {
    private TransferProductController controller;
    private CartService mockCartService;
    private CardDataService mockCardDataService;
    private CardPreferencesDataService mockcardPreferencesDataService;
    private CardService mockCardService;
    private TransferTargetCardService mockTransferTargetCardService;
    private TransferSourceCardService mockTransferSourceCardService;
    private AddUnattachedCardService mockAddUnattachedCardService;
    private TransferProductValidator mockTransferProductValidator;
    private CardPreferencesService mockCardPreferencesService;
    private LocationSelectListService mockLocationSelectListService;
    private PickUpLocationValidator mockPickUpLocationValidator;
    private GetCardService mockGetCardService;
    private HttpSession mockHttpSession;
    private Model mockModel;
    private BeanPropertyBindingResult mockResult;
    private CartDTO cartDTO;
    private List<String> mockSourceList;
    private List<String> mockRuleBreachesList;
    private Model model = new ExtendedModelMap();
    private CartCmdImpl cartCmd;
    
    @Before
    public void setUp() {
        controller = new TransferProductController();

        mockTransferProductValidator = mock(TransferProductValidator.class);
        mockPickUpLocationValidator = mock(PickUpLocationValidator.class);
        mockCartService = mock(CartService.class);
        mockTransferTargetCardService= mock(TransferTargetCardService.class);
        mockTransferSourceCardService=mock(TransferSourceCardService.class);
        mockHttpSession = mock(HttpSession.class);
        mockModel = mock(Model.class);
        mockResult = mock(BeanPropertyBindingResult.class);
        cartDTO = CartTestUtil.getTestCartDTO1();
        mockCardDataService = mock(CardDataService.class);
        mockCardService  = mock(CardService.class);
        mockGetCardService= mock(GetCardService.class);
        mockAddUnattachedCardService = mock(AddUnattachedCardService.class);
        mockCardPreferencesService = mock(CardPreferencesService.class);
        mockLocationSelectListService = mock(LocationSelectListService.class);
        mockcardPreferencesDataService= mock(CardPreferencesDataService.class);
        mockSourceList=new ArrayList<String>();
        mockRuleBreachesList=new ArrayList<String>();
        cartCmd = new CartCmdImpl();
        
        controller.cartService = mockCartService;
        controller.transferProductValidator = mockTransferProductValidator;
        controller.pickUpLocationValidator = mockPickUpLocationValidator;
        controller.cardDataService = mockCardDataService;
        controller.transferTargetCardService = mockTransferTargetCardService;
        controller.transferSourceCardService=mockTransferSourceCardService;
        controller.addUnattachedCardService = mockAddUnattachedCardService;
        controller.cardPreferencesService = mockCardPreferencesService;
        controller.locationSelectListService=mockLocationSelectListService;
        controller.cardService = mockCardService;
        controller.getCardService = mockGetCardService;
        controller.cardPreferencesDataService = mockcardPreferencesDataService;
        
        doNothing().when(mockTransferProductValidator).validate(anyObject(), any(Errors.class));
        when(CartUtil.getCartSessionDataDTOFromSession(mockHttpSession)).thenReturn(getTestCartSessionDataDTO1());
        when(mockLocationSelectListService.getLocationSelectList()).thenReturn(SelectListTestUtil.getTestSelectListDTO());
        when(mockCardPreferencesService.getPreferencesByCardId(CARD_ID_1)).thenReturn(getTestCardPreferencesCmd1());
        when(mockCartService.findById(anyLong())).thenReturn(cartDTO);
    }

    @Test
    public void shouldGetCartCmd() {
        assertNotNull(controller.getCartCmd());
    }
    
    @Test
	public void populateStationSelectListShouldContainFieldSelectPickupStation() {
    	controller.populateStationSelectList(model);
		model.containsAttribute(PageAttribute.FIELD_SELECT_PICKUP_STATION);
	}
     
    @Test
    public void shouldShowTransferProduct() {
        when(mockHttpSession.getAttribute(CartAttribute.CARD_ID)).thenReturn(CardTestUtil.TRANSFER_CARD_ID);
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        CartDTO cartDTO = CartTestUtil.getNewCartDTOWithItem();
        when(mockCartService.createCartFromCardId(anyLong())).thenReturn(cartDTO);
        when(mockGetCardService.getCard(anyString())).thenReturn(getTestCardInfoResponseV2DTO7());
        when(mockTransferSourceCardService.isSourceCardEligible(anyString())).thenReturn(mockSourceList);
        ModelAndView result = controller.showTransferProduct(mockHttpSession, mockModel, CardTestUtil.TRANSFER_CARD_ID, TransferConstants.LOST_STOLEN_MODE);
        assertEquals(PageView.TRANSFER_PRODUCT, result.getViewName());
    } 
    
    
    @Test
    public void shouldShowTransferProductPptValue() {
        when(mockHttpSession.getAttribute(CartAttribute.CARD_ID)).thenReturn(CardTestUtil.TRANSFER_CARD_ID);
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        CartDTO cartDTO = CartTestUtil.getNewCartDTOWithItem();
        when(mockCartService.createCartFromCardId(anyLong())).thenReturn(cartDTO);
        when(mockGetCardService.getCard(anyString())).thenReturn(getTestCardInfoResponseV2DTO9());
        when(mockTransferSourceCardService.isSourceCardEligible(anyString())).thenReturn(mockSourceList);
        ModelAndView result = controller.showTransferProduct(mockHttpSession, mockModel, CardTestUtil.TRANSFER_CARD_ID, TransferConstants.LOST_STOLEN_MODE);
        assertEquals(PageView.TRANSFER_PRODUCT, result.getViewName());
    } 
    
    
    @Test
    public void shouldShowTransferProductWithRuleBreaches() {
        when(mockHttpSession.getAttribute(CartAttribute.CARD_ID)).thenReturn(CardTestUtil.TRANSFER_CARD_ID);
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        CartDTO cartDTO = CartTestUtil.getNewCartDTOWithItem();
        when(mockCartService.createCartFromCardId(anyLong())).thenReturn(cartDTO);
        mockRuleBreachesList.add(TransferTestUtil.RULE_BREACH_1);
        mockRuleBreachesList.add(TransferTestUtil.RULE_BREACH_2);
        when(mockTransferSourceCardService.isSourceCardEligible(anyString())).thenReturn(mockRuleBreachesList);
        ModelAndView result = controller.showTransferProduct(mockHttpSession, mockModel, CardTestUtil.TRANSFER_CARD_ID, TransferConstants.LOST_STOLEN_MODE);
        assertEquals(PageView.TRANSFER_PRODUCT, result.getViewName());
    }
     
    @Test
    public void transferShouldShowCancel() {
        when(mockCartService.findById(anyLong())).thenReturn(getNewCartDTOWithItem());
        when(mockHttpSession.getAttribute(CartAttribute.CARD_ID)).thenReturn(CardTestUtil.TRANSFER_CARD_ID);
        when(mockAddUnattachedCardService.retrieveOysterDetails(anyString()))
        .thenReturn(PersonalDetailsCmdTestUtil.getTestPersonalDetailsCmd1());
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        CartDTO cartDTO = CartTestUtil.getNewCartDTOWithItem();
        when(mockCartService.createCartFromCardId(anyLong())).thenReturn(cartDTO);
        when(mockResult.hasErrors()).thenReturn(Boolean.FALSE);
        ModelAndView result = controller.cancelTransferProduct(mockHttpSession);
        assertEquals(PageUrl.INV_CUSTOMER, ((RedirectView) result.getView()).getUrl());
    }
    
    @Test
    public void shouldShowTransferProductWithDiscountRuleBreaches() {
        when(mockHttpSession.getAttribute(CartAttribute.CARD_ID)).thenReturn(CardTestUtil.TRANSFER_CARD_ID);
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        CartDTO cartDTO = CartTestUtil.getNewCartDTOWithItem();
        when(mockCartService.createCartFromCardId(anyLong())).thenReturn(cartDTO);
        mockRuleBreachesList.add(TransferTestUtil.RULE_BREACH_1);
        mockRuleBreachesList.add(TransferTestUtil.RULE_BREACH_2);
        mockRuleBreachesList.add(ContentCode.TRANSFERS_CARD_DISCOUNTED.textCode());
        when(mockTransferSourceCardService.isSourceCardEligible(anyString())).thenReturn(mockRuleBreachesList);
        ModelAndView result = controller.showTransferProduct(mockHttpSession, mockModel, CardTestUtil.TRANSFER_CARD_ID, TransferConstants.LOST_STOLEN_MODE);
        assertEquals(PageView.TRANSFER_PRODUCT, result.getViewName()); 
    }
    
    @Test
    public void shouldShowTransferProductWithOnlyDiscountRuleBreaches() {
        when(mockHttpSession.getAttribute(CartAttribute.CARD_ID)).thenReturn(CardTestUtil.TRANSFER_CARD_ID);
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        CartDTO cartDTO = CartTestUtil.getNewCartDTOWithItem();
        when(mockCartService.createCartFromCardId(anyLong())).thenReturn(cartDTO);
        mockRuleBreachesList.add(ContentCode.TRANSFERS_CARD_DISCOUNTED.textCode());
        when(mockTransferSourceCardService.isSourceCardEligible(anyString())).thenReturn(mockRuleBreachesList);
        when(mockCartService.createCartFromCardId(anyLong())).thenReturn(cartDTO);
        when(mockGetCardService.getCard(anyString())).thenReturn(getTestCardInfoResponseV2DTO7());
        ModelAndView result = controller.showTransferProduct(mockHttpSession, mockModel, CardTestUtil.TRANSFER_CARD_ID, TransferConstants.LOST_STOLEN_MODE);
        assertEquals(PageView.TRANSFER_PRODUCT, result.getViewName()); 
    }
    
    @Test
    public void shouldGetPreferredStationId() {
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        when(mockGetCardService.getCard(anyString())).thenReturn(getTestCardInfoResponseV2DTO7());
        String stationId= controller.getPreferredStationId(CARD_ID_1);
        assertEquals(CardPreferencesTestUtil.STATION_ID, stationId);
    }
     
    @Test
    public void shouldGetPreferredStationIdPptValue() {
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        when(mockGetCardService.getCard(anyString())).thenReturn(getTestCardInfoResponseV2DTO9());
        String stationId= controller.getPreferredStationId(CARD_ID_1);
        assertEquals(CardPreferencesTestUtil.STATION_ID, stationId);
    }
    
    @Test
    public void shouldGetPreferredStationIdNoPptValue() {
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        when(mockCardDataService.findByCardNumber(anyString())).thenReturn(getTestCardDTO1());
        when(mockGetCardService.getCard(anyString())).thenReturn(getTestCardInfoResponseV2DTO19());
        when(mockcardPreferencesDataService.findByCardId(anyLong())).thenReturn(getTestCardPreferencesDTO1());
        String stationId= controller.getPreferredStationId(CARD_ID_1);
        assertEquals(CardPreferencesTestUtil.STATION_ID, stationId);
    }
     
    @Test
    public void getPreferredStationIdShouldNotReturnNull() {
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        when(mockGetCardService.getCard(anyString())).thenReturn(getTestCardInfoResponseV2DTO10());
        String stationId= controller.getPreferredStationId(CARD_ID_1);
        assertEquals(CardPreferencesTestUtil.STATION_ID, stationId);
    } 
    
    
    @Test
    public void shouldShowTransferSummaryOnValidInput() {
        when(mockResult.hasErrors()).thenReturn(Boolean.FALSE);
        doNothing().when(mockPickUpLocationValidator).validate(anyObject(), any(Errors.class));
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        when(CartUtil.getCartSessionDataDTOFromSession(mockHttpSession)).thenReturn(getTestCartSessionDataDTO4());
        ModelAndView result = controller.showTransferSummary(mockHttpSession, mockModel, cartCmd, mockResult);
        assertEquals(PageUrl.TRANSFER_SUMMARY, ((RedirectView) result.getView()).getUrl());
    }
    
    @Test
    public void shouldShowTransferProductOnInValidInput() {
        when(mockResult.hasErrors()).thenReturn(Boolean.TRUE);
        doNothing().when(mockPickUpLocationValidator).validate(anyObject(), any(Errors.class));
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        when(mockCardService.getCardIdFromCardNumber(anyString())).thenReturn(CARD_ID_1);
        CartDTO cartDTO = CartTestUtil.getNewCartDTOWithItem();
        when(mockCartService.createCartFromCardId(anyLong())).thenReturn(cartDTO);
        when(CartUtil.getCartSessionDataDTOFromSession(mockHttpSession)).thenReturn(getTestCartSessionDataDTO4());
        cartCmd.setCardId(CARD_ID_1);
        ModelAndView result = controller.showTransferSummary(mockHttpSession, mockModel, cartCmd, mockResult);
        assertEquals(PageView.TRANSFER_PRODUCT, result.getViewName());
    }
    
    @Test
    public void getLostOrStolenModeTrue() {
        CartCmdImpl cartCmd= new CartCmdImpl();
        controller.setLostOrStolenMode(TransferConstants.LOST_STOLEN_MODE, cartCmd);
        assertTrue(cartCmd.isLostOrStolenMode());
    }
    
    @Test
    public void getLostOrStolenModeFalse() {
        CartCmdImpl cartCmd= new CartCmdImpl();
        controller.setLostOrStolenMode(TransferConstants.NOT_APPLICABLE, cartCmd);
        assertFalse(cartCmd.isLostOrStolenMode());
    }
    
    @Test
    public void shouldShowAddExistingCardToAccount() {
    	when(mockAddUnattachedCardService.retrieveOysterDetails(anyString())).thenReturn(PersonalDetailsCmdTestUtil.getTestPersonalDetailsCmd1());
    	ModelAndView result = controller.showAddExistingCardToAccount(mockHttpSession, cartCmd, mockResult);
    	assertEquals(PageUrl.INV_ADD_UNATTACHED_CARD, ((RedirectView) result.getView()).getUrl());
    }
}

