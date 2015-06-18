package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.STATION_ID;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO5;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO6;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO7;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTOPrePayTicketIncorrectStationID;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTONullPendingItems;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTONullPpvLocation;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTOPrePayTicketSizeZero;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTONullPrePayTicket;
import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTO1;
import static com.novacroft.nemo.test_support.LocationTestUtil.getTestLocationDTO;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.test_support.LocationTestUtil;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.LocationDataService;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.LocationDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;

@RunWith(Parameterized.class)
public class PickUpLocationValidatorParameterisedTest {

    private PickUpLocationValidator validator;
    private CartCmdImpl cmd;
    private CardDataService mockCardDataService;
    private GetCardService mockGetCardService;
    private SelectStationValidator mockSelectStationValidator;
    private LocationDataService mockLocationDataService;
    private Errors errors;

    private CardDTO cardDto;
    private CardInfoResponseV2DTO cardInfo;
    private LocationDTO locationDTO;
    private int timesFindByIdCalled;
    private int timesGetCardCalled;
    private int timesStationValidatorCalled;
    private int timesGetActiveLocationByIdCalled;
    private boolean hasErrors;

    @Before
    public void setUp() throws Exception {
        validator = new PickUpLocationValidator();
        cmd = new CartCmdImpl();
        mockCardDataService = mock(CardDataService.class);
        mockGetCardService = mock(GetCardService.class);
        mockSelectStationValidator = mock(SelectStationValidator.class);
        mockLocationDataService = mock(LocationDataService.class);
        
        errors = new BeanPropertyBindingResult(cmd, "cmd");

        validator.cardDataService = mockCardDataService;
        validator.getCardService = mockGetCardService;
        validator.locationDataService = mockLocationDataService;
        validator.selectStationValidator = mockSelectStationValidator;
    }

    public PickUpLocationValidatorParameterisedTest(String testName, CardDTO cardDto, CardInfoResponseV2DTO cardInfo, LocationDTO locationDto,
                    int timesFindByIdCalled, int timesGetCardCalled, int timesStationValidatorCalled, int timesGetActiveLocationByIdCalled,
                    boolean hasErrors) {
        
        this.cardDto = cardDto;
        this.cardInfo = cardInfo;
        this.locationDTO = locationDto;
        this.timesFindByIdCalled = timesFindByIdCalled;
        this.timesGetCardCalled = timesGetCardCalled;
        this.timesStationValidatorCalled = timesStationValidatorCalled;
        this.timesGetActiveLocationByIdCalled = timesGetActiveLocationByIdCalled;
        this.hasErrors = hasErrors;
    }

    @Parameters(name = "{index}: test({0}) expected={1}")
    public static Collection<?> testParameters(){
        return Arrays.asList(new Object[][] {
                        {"shouldValidate", getTestCardDTO1(), getTestCardInfoResponseV2DTO5(), null,1,1,1,0,false},
                        {"shouldValidateWithPendingPrePayTickets",getTestCardDTO1(),getTestCardInfoResponseV2DTO6(), null,1,1,1,0,false},
                       // {"shouldNotValidateWithDifferentStation", getTestCardDTO1(),getTestCardInfoResponseV2DTO7(),getTestLocationDTO(LocationTestUtil.LOCATION_ID_1, LocationTestUtil.LOCATION_NAME_1, LocationTestUtil.LOCATION_STATUS_1),1,1,1,1,true},
                        {"shouldNotValidateNullCardInforResponsePendingItems", getTestCardDTO1(),getTestCardInfoResponseV2DTONullPendingItems(),getTestLocationDTO(LocationTestUtil.LOCATION_ID_1, LocationTestUtil.LOCATION_NAME_1, LocationTestUtil.LOCATION_STATUS_1),1,1,1,0, false},
                        {"shouldNotValidateNullPpvLocation", getTestCardDTO1(),getTestCardInfoResponseV2DTONullPpvLocation(),getTestLocationDTO(LocationTestUtil.LOCATION_ID_1, LocationTestUtil.LOCATION_NAME_1, LocationTestUtil.LOCATION_STATUS_1),1,1,1,0,false},
                        {"shouldNotValidatePendingItemStationIdDoeSNotEqualStationId", getTestCardDTO1(),getTestCardInfoResponseV2DTOPrePayTicketIncorrectStationID(),getTestLocationDTO(LocationTestUtil.LOCATION_ID_1, LocationTestUtil.LOCATION_NAME_1, LocationTestUtil.LOCATION_STATUS_1),1,1,1,1,true},
                        {"shouldNotValidateSizeZeroPrePayTicketArray", getTestCardDTO1(),getTestCardInfoResponseV2DTOPrePayTicketSizeZero(),getTestLocationDTO(LocationTestUtil.LOCATION_ID_1, LocationTestUtil.LOCATION_NAME_1, LocationTestUtil.LOCATION_STATUS_1),1,1,1,0,false},
                        {"shouldNotValidateNullFirstPrePayTicket", getTestCardDTO1(), getTestCardInfoResponseV2DTONullPrePayTicket(),getTestLocationDTO(LocationTestUtil.LOCATION_ID_1, LocationTestUtil.LOCATION_NAME_1, LocationTestUtil.LOCATION_STATUS_1),1,1,1,0,false}
        });
    }

    @Test
    public void test() {
        when(mockCardDataService.findById(anyLong())).thenReturn(this.cardDto);
        when(mockGetCardService.getCard(anyString())).thenReturn(this.cardInfo);
        when(mockLocationDataService.getActiveLocationById(anyInt())).thenReturn(this.locationDTO);
        
        cmd.setStationId(STATION_ID);
        validator.validate(cmd, errors);
        
        verify(mockCardDataService, times(this.timesFindByIdCalled)).findById(anyLong());
        verify(mockGetCardService, times(this.timesGetCardCalled)).getCard(anyString());
        verify(mockLocationDataService, times(timesGetActiveLocationByIdCalled)).getActiveLocationById(anyInt());
        verify(mockSelectStationValidator, times(timesStationValidatorCalled)).validate(cmd, errors);
        assertEquals(this.hasErrors, errors.hasErrors());
    }

}
