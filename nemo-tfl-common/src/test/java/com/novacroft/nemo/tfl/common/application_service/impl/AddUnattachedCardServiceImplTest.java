package com.novacroft.nemo.tfl.common.application_service.impl;

import com.novacroft.nemo.test_support.*;
import com.novacroft.nemo.tfl.common.application_service.PersonalDetailsService;
import com.novacroft.nemo.tfl.common.command.impl.AddUnattachedCardCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.PersonalDetailsCmdImpl;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.form_validator.CustomerValidator;
import com.novacroft.nemo.tfl.common.util.JsonResponse;
import org.glassfish.admin.amx.util.StringUtil;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.validation.BindingResult;

import static com.novacroft.nemo.test_support.CustomerTestUtil.CUSTOMER_ID_1;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AddUnattachedCardServiceImplTest {

    private static AddUnattachedCardServiceImpl addUnattachedCardServiceImpl;
    private static CardDataService cardOysterOnlineService;
    private static PersonalDetailsService personalDetailsService;
    private static CustomerDataService customerDataService;
    private static CustomerValidator customerValidator;
    private static AddUnattachedCardCmdImpl addUnattachedCardCmdImpl;

    private PersonalDetailsCmdImpl personalDetailsCmdImpl;

    @BeforeClass
    public static void setup() {
        addUnattachedCardServiceImpl = new AddUnattachedCardServiceImpl();
        cardOysterOnlineService = mock(CardDataService.class);
        personalDetailsService = mock(PersonalDetailsService.class);
        customerDataService = mock(CustomerDataService.class);
        customerValidator = mock(CustomerValidator.class);
        addUnattachedCardCmdImpl = mock(AddUnattachedCardCmdImpl.class);

        addUnattachedCardServiceImpl.cardOysterOnlineService = cardOysterOnlineService;
        addUnattachedCardServiceImpl.personalDetailsService = personalDetailsService;
        addUnattachedCardServiceImpl.customerDataService = customerDataService;
        addUnattachedCardServiceImpl.customerValidator = customerValidator;
    }

    @Before
    public void initialiseTest() {
        personalDetailsCmdImpl = null;
    }

    @Test
    public void retrieveOysterDetailsCorrectDetails() {
        when(cardOysterOnlineService.findByCardNumber(CommonCardTestUtil.OYSTER_NUMBER_1))
                .thenReturn(CardTestUtil.getTestCardDTO1());

        when(customerDataService.findById((CustomerTestUtil.CUSTOMER_ID_1)))
                .thenReturn(CustomerTestUtil.getCustomerDTO(CUSTOMER_ID_1));

        when(personalDetailsService.getPersonalDetailsByCustomerId(CUSTOMER_ID_1))
                .thenReturn(PersonalDetailsCmdTestUtil.getTestPersonalDetailsCmdWithCardNumber());

        personalDetailsCmdImpl = addUnattachedCardServiceImpl.retrieveOysterDetails(CommonCardTestUtil.OYSTER_NUMBER_1);

        assertNotNull(personalDetailsCmdImpl);
        assertNotNull(personalDetailsCmdImpl.getCustomerId());
        assertEquals(CommonCardTestUtil.OYSTER_NUMBER_1, personalDetailsCmdImpl.getCardNumber());

    }

    @Test
    public void retrieveOysterDetailsIncorrectDetails() {
        when(cardOysterOnlineService.findByCardNumber(CommonCardTestUtil.OYSTER_NUMBER_1)).thenReturn(
                CardTestUtil.getTestCard(CommonCardTestUtil.OYSTER_NUMBER_1, CommonCardTestUtil.INVALID_CUSTOMER_ID_1));

        personalDetailsCmdImpl = addUnattachedCardServiceImpl.retrieveOysterDetails(CommonCardTestUtil.OYSTER_NUMBER_1);

        assertNotNull(personalDetailsCmdImpl);
        assertNull(personalDetailsCmdImpl.getCustomerId());
    }

    @Test
    public void retrieveOysterDetailsNoDetails() {
        when(cardOysterOnlineService.findByCardNumber(CommonCardTestUtil.OYSTER_NUMBER_1)).thenReturn(null);

        personalDetailsCmdImpl = addUnattachedCardServiceImpl.retrieveOysterDetails(CommonCardTestUtil.OYSTER_NUMBER_1);
    }

    @Test
    public void retrieveOysterDetailsNoCustomerId() {
        when(cardOysterOnlineService.findByCardNumber(CommonCardTestUtil.OYSTER_NUMBER_1))
                .thenReturn(CardTestUtil.getTestCard(CommonCardTestUtil.OYSTER_NUMBER_1, null));

        personalDetailsCmdImpl = addUnattachedCardServiceImpl.retrieveOysterDetails(CommonCardTestUtil.OYSTER_NUMBER_1);

        assertNotNull(personalDetailsCmdImpl);
        assertEquals(null, personalDetailsCmdImpl.getCustomerId());
    }

    @Test
    public void retrieveOysterDetailsNoCustomer() {
        when(cardOysterOnlineService.findByCardNumber(CommonCardTestUtil.OYSTER_NUMBER_1))
                .thenReturn(CardTestUtil.getTestCardDTO1());

        when(customerDataService.findById((CUSTOMER_ID_1))).thenReturn(null);

        personalDetailsCmdImpl = addUnattachedCardServiceImpl.retrieveOysterDetails(CommonCardTestUtil.OYSTER_NUMBER_1);

        assertNotNull(personalDetailsCmdImpl);
        assert (personalDetailsCmdImpl.getCustomerId() == null);

        when(customerDataService.findById((CUSTOMER_ID_1))).thenReturn(CustomerTestUtil.getCustomerDTO(null));

        personalDetailsCmdImpl = addUnattachedCardServiceImpl.retrieveOysterDetails(CommonCardTestUtil.OYSTER_NUMBER_1);
    }

    @Test
    public void testCompareCubicDataToOyster() {
        JsonResponse jsonResponse = addUnattachedCardServiceImpl
                .compareCubicDataToOyster(AddUnattachedCardServiceImplTestUtil.getHolderDetails(), true,
                        PersonalDetailsCmdTestUtil.getTestPersonalDetailsCmd1());

        assertNotNull(jsonResponse);
        assertNotNull(jsonResponse.getHotlisted());
        assertNotNull(jsonResponse.getComparison());
    }

    @Test
    public void createNewCustomer() {
        BindingResult bindingResult = mock(BindingResult.class);
        when(addUnattachedCardCmdImpl.getCustomerId()).thenReturn(StringUtil.toString(CUSTOMER_ID_1));
        addUnattachedCardServiceImpl.createNewCustomer(addUnattachedCardCmdImpl, bindingResult);
    }

    @Test
    public void saveOysterCardrecord() {
        when(addUnattachedCardCmdImpl.getCardNumber()).thenReturn(CommonCardTestUtil.OYSTER_NUMBER_1);
        when(cardOysterOnlineService.createOrUpdate(CardTestUtil.getTestCardDTO1())).thenReturn(CardTestUtil.getTestCardDTO1());
        addUnattachedCardServiceImpl
                .saveOysterCardRecord(addUnattachedCardCmdImpl, CustomerTestUtil.getCustomerDTO(CUSTOMER_ID_1));
    }

    @Test
    public void retrieveOysterDetailsByCustomerID() {
        when(personalDetailsService.getPersonalDetailsByCustomerId(CUSTOMER_ID_1))
                .thenReturn(PersonalDetailsCmdTestUtil.getTestPersonalDetailsCmd1());
        personalDetailsCmdImpl = addUnattachedCardServiceImpl.retrieveOysterDetailsByCustomerID(CUSTOMER_ID_1);
    }

}
