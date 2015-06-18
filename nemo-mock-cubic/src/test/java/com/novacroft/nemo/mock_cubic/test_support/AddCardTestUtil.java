package com.novacroft.nemo.mock_cubic.test_support;

import java.util.Date;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.novacroft.nemo.common.domain.cubic.PrePayTicketSlot;
import com.novacroft.nemo.common.utils.XMLUtil;
import com.novacroft.nemo.mock_cubic.command.AddCardResponseCmd;

/**
 * Test support class Add Card Util.
 */
public final class AddCardTestUtil {
    private static final Integer PICKUP = 582;
    private static final Integer PRE_PAY_VALUE = 220;
    private static final Integer CARD_TYPE = 22;
    private static final String DISCOUNT = "No Discount/na";
    private static final String PASSENGER_TYPE = "Adult";
    public static final String PRESTIGE_ID = "0123456789456";

    private AddCardTestUtil() {

    }

    /**
     * Create a card.
     *
     * @param hotlistReasons - enable hotlistreason codes.
     * @return AddCardCmd
     */
    public static AddCardResponseCmd createAddCardCmd(final boolean hotlistReasons) {
        final String expiryDate = "01/10/2014";
        final AddCardResponseCmd cmd = new AddCardResponseCmd();
        cmd.setCardCapability(1);
        cmd.setCardDeposit(0);
        cmd.setCardType(CARD_TYPE);
        if (hotlistReasons) {
            cmd.setHotlistReasonsCodeNumbers("1,2,3");
        }
        cmd.setAutoloadState(1);
        cmd.setPassengerType(PASSENGER_TYPE);
        cmd.setPhotocardNumber("XXXX");
        cmd.setPrestigeId("012345678902");
        cmd.setRegistered(1);
        cmd.setTitle("Mr");
        cmd.setFirstName("John");
        cmd.setMiddleInitial("");
        cmd.setLastName("Smith");
        cmd.setHouseName("");
        cmd.setHouseNumber("14");
        cmd.setStreet("Ladysmith Avenue");
        cmd.setCounty("");
        cmd.setPostcode("IG2 7AY");
        cmd.setDayTimeTelephoneNumber("123456789");
        cmd.setEmailAddress("test1@novacroft.com");
        cmd.setPassword("Test123");
        /* Discount Entitlement */
        cmd.setDiscountEntitlement1(DISCOUNT);
        cmd.setDiscountEntitlement2(DISCOUNT);
        cmd.setDiscountEntitlement3(DISCOUNT);
        cmd.setDiscountExpiry1(expiryDate);
        cmd.setDiscountExpiry2(expiryDate);
        cmd.setDiscountExpiry3(expiryDate);
        cmd.setName("Test");
        cmd.setCurrency(0);
        cmd.setPickupLocation(PICKUP);
        cmd.setPrePayValue(PRE_PAY_VALUE);
        cmd.setRealTimeFlag("N");
        cmd.setRequestSequenceNumber(0);
        cmd.setBalance(0);
        
        
        cmd.setPendingPptCurrency1(0);
        cmd.setPendingPptExpiryDate1(expiryDate);
        cmd.setPendingPptPickupLocation1(PICKUP);
        
        return cmd;
    }

    public static PrePayTicketSlot createPPTSlot() {
        final PrePayTicketSlot pptSlot = new PrePayTicketSlot();
        pptSlot.setDiscount("12");
        pptSlot.setExpiryDate("01/01/2014");
        pptSlot.setPassengerType(PASSENGER_TYPE);
        pptSlot.setProduct("Test Product");
        pptSlot.setSlotNumber(1);
        pptSlot.setStartDate("01/12/2014");
        pptSlot.setState(1);
        pptSlot.setZone("1");
        return pptSlot;
    }

    public static String getCardRequest() {
        final StringBuffer stringBuffer = new StringBuffer(220);
        stringBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<CardInfoRequestV2>" +
                "<PrestigeID>012345678902</PrestigeID>" +
                "<OriginatorInfo>" +
                "<UserID>N0v4Croft</UserID>" +
                "<Password>t3stconn1</Password>" +
                "</OriginatorInfo>" +
                "</CardInfoRequestV2>");
        return stringBuffer.toString();
    }

    public static final String getPrestigeIdFromDocument(final NodeList list) {
        String prestigeId = "";
        for (int i = 0; i < list.getLength(); i++) {
            final Node node = list.item(i);
            final String nodeName = node.getNodeName();
            if (PRESTIGE_ID.equalsIgnoreCase(nodeName)) {
                prestigeId = XMLUtil.getData(node);
                break;
            }
        }
        return prestigeId;
    }
    
    public static AddCardResponseCmd getAddCardResponseWithBlankPrestigeId(){
        AddCardResponseCmd cmd = createAddCardCmd(false);
        cmd.setPrestigeId("");
        return cmd;
    }
}
