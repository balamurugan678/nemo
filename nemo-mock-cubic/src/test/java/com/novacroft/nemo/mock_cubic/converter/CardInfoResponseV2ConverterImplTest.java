package com.novacroft.nemo.mock_cubic.converter;

import com.novacroft.nemo.test_support.DateTestUtil;
import com.novacroft.nemo.tfl.common.converter.impl.CardInfoResponseV2ConverterImpl;
import com.novacroft.nemo.tfl.common.domain.cubic.CardInfoResponseV2;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

public class CardInfoResponseV2ConverterImplTest {

    private CardInfoResponseV2EmptyTagConverterImpl converter;
    private CardInfoResponseV2ConverterImpl mockCardInfoResponseV2ConverterImpl;

    private CardInfoResponseV2 testCardInfoResponseV2;

    @Before
    public void setUp() {
        this.converter = mock(CardInfoResponseV2EmptyTagConverterImpl.class);
        this.mockCardInfoResponseV2ConverterImpl = mock(CardInfoResponseV2ConverterImpl.class);
        this.converter.cardInfoResponseV2Converter = this.mockCardInfoResponseV2ConverterImpl;
        setField(this.converter, "castorMappingFileUrl",
                this.getClass().getClassLoader().getResource("nemo-tfl-common-castor-mapping-get-card-with-empty-tags.xml"));
        this.converter.setCastorMappingFileUrl(new ArrayList<URL>());

        this.testCardInfoResponseV2 = new CardInfoResponseV2();
    }

    @Test
    public void shouldConvertEmptyResponse() {
        when(this.converter.convertModelToXml(any(CardInfoResponseV2.class))).thenCallRealMethod();
        String expectedResult =
                "<CardInfoResponseV2><CardCapability></CardCapability><CardDeposit></CardDeposit><CardType></CardType" +
                        "><CCCLostStolenDateTime></CCCLostStolenDateTime><PrestigeID></PrestigeID><PhotocardNumber" +
                        "></PhotocardNumber><Registered></Registered><PassengerType></PassengerType><AutoloadState" +
                        "></AutoloadState><DiscountEntitleMent1></DiscountEntitleMent1><DiscountExpiry1></DiscountExpiry1" +
                        "><DiscountEntitleMent2></DiscountEntitleMent2><DiscountExpiry2></DiscountExpiry2" +
                        "><DiscountEntitleMent3></DiscountEntitleMent3><DiscountExpiry3></DiscountExpiry3><HotlistReasons" +
                        "/><CardHolderDetails><Title></Title><FirstName></FirstName><MiddleInitial></MiddleInitial><LastName" +
                        "></LastName><DayTimePhoneNumber></DayTimePhoneNumber><HouseNumber></HouseNumber><HouseName" +
                        "></HouseName><Street></Street><Town></Town><County></County><Postcode></Postcode><EmailAddress" +
                        "></EmailAddress><Password></Password></CardHolderDetails><PPVDetails/><PPTDetails/><PendingItems" +
                        "/></CardInfoResponseV2>";
        String result = this.converter.convertModelToXml(this.testCardInfoResponseV2);
        assertEquals(expectedResult, result);
    }

    @Test
    public void shouldConvertPartialResponse() {
        when(this.converter.convertModelToXml(any(CardInfoResponseV2.class))).thenCallRealMethod();
        this.testCardInfoResponseV2.setPrestigeId("123456789");
        this.testCardInfoResponseV2.setCardCapability(0);
        this.testCardInfoResponseV2.setCccLostStolenDateTime(DateTestUtil.getAug19());
        String expectedResult =
                "<CardInfoResponseV2><CardCapability>0</CardCapability><CardDeposit></CardDeposit><CardType></CardType" +
                        "><CCCLostStolenDateTime>2013-08-19T12:00:00.000+01:00" +
                        "</CCCLostStolenDateTime><PrestigeID>123456789</PrestigeID><PhotocardNumber></PhotocardNumber" +
                        "><Registered></Registered><PassengerType></PassengerType><AutoloadState></AutoloadState" +
                        "><DiscountEntitleMent1></DiscountEntitleMent1><DiscountExpiry1></DiscountExpiry1" +
                        "><DiscountEntitleMent2></DiscountEntitleMent2><DiscountExpiry2></DiscountExpiry2" +
                        "><DiscountEntitleMent3></DiscountEntitleMent3><DiscountExpiry3></DiscountExpiry3><HotlistReasons" +
                        "/><CardHolderDetails><Title></Title><FirstName></FirstName><MiddleInitial></MiddleInitial><LastName" +
                        "></LastName><DayTimePhoneNumber></DayTimePhoneNumber><HouseNumber></HouseNumber><HouseName" +
                        "></HouseName><Street></Street><Town></Town><County></County><Postcode></Postcode><EmailAddress" +
                        "></EmailAddress><Password></Password></CardHolderDetails><PPVDetails/><PPTDetails/><PendingItems" +
                        "/></CardInfoResponseV2>";
        String result = this.converter.convertModelToXml(this.testCardInfoResponseV2);
        assertEquals(expectedResult, result);
    }
}