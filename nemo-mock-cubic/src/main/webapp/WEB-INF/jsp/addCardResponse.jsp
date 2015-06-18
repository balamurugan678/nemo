<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h1>Add Card Response</h1>

<p>
    If a prestigeId has already been used this will overwrite the record.
</p>
<!--

<CardInfoResponseV2 xmlns="http://www.innovator-cms.com/Innovator">

<CardCapability>1</CardCapability>
<CardDeposit>0</CardDeposit>
<CardType>10</CardType>
<CCCLostStolenDateTime/>
<HotlistReasons>
<HotlistReasonCode>1</HotlistReasonCode>
</HotlistReasons>
<AutoloadState>1</AutoloadState>
<CardHolderDetails>
<County><![CDATA[?]]></County>
<DayTimePhoneNumber><![CDATA[?]]></DayTimePhoneNumber>
<EmailAddress><![CDATA[?]]></EmailAddress>
<FirstName><![CDATA[Wesley]]></FirstName>
<HouseName><![CDATA[183 Carmelite Road]]></HouseName>
<HouseNumber><![CDATA[?]]></HouseNumber>
<LastName><![CDATA[Maunders]]></LastName>
<MiddleInitial><![CDATA[?]]></MiddleInitial>
<Password><![CDATA[1 8892]]></Password>
<Postcode><![CDATA[HA3 5NG]]></Postcode>
<Street><![CDATA[Harrow]]></Street>
<Title><![CDATA[?]]></Title>
<Town><![CDATA[Middlesex]]></Town>
</CardHolderDetails>
<DiscountEntitlement1><![CDATA[Full Time Education]]></DiscountEntitlement1>
<DiscountEntitlement2><![CDATA[No Discount/na]]></DiscountEntitlement2>
<DiscountEntitlement3><![CDATA[No Discount/na]]></DiscountEntitlement3>
<DiscountExpiry1>30/09/2012</DiscountExpiry1>
<DiscountExpiry2/>
<DiscountExpiry3/>
<PassengerType><![CDATA[16-17]]></PassengerType>
<PendingItems>
</PendingItems>
<PhotocardNumber><![CDATA[XXXX]]></PhotocardNumber>
<PPTDetails>
</PPTDetails>
<PPVDetails>
<Balance>695</Balance>
<Currency>0</Currency>
</PPVDetails>
<PrestigeID>011377434415</PrestigeID>
<Registered>1</Registered>
</CardInfoResponseV2>


PAYG Balance :?6.95
-->

<form:form action="AddCardResponse.htm" modelAttribute="Cmd">
<div style="clear: both; float: left">
    <button type="button" id="populateStudent">Student</button>
    <button type="button" id="populateAdult">Adult</button>
</div>
<div style="clear: both;"></div>
<div style="float: left;">
    <h2>Card Details 1</h2>
    <table>
        <tr>
            <td><label>PrestigeID</label></td>
            <td><input type="text" name="prestigeId" value="" placeholder="Oyster Card Number" size="40"/></td>
        </tr>
        <tr>
            <td><label>Card Capability</label></td>
            <td><input type="number" name="cardCapability" value="" placeholder="1"/></td>
        </tr>
        <tr>
            <td><label>Card Deposit</label></td>
            <td><input type="number" name="cardDeposit" value="" placeholder="0"/></td>
        </tr>
        <tr>
            <td><label>Card Type</label></td>
            <td><input type="number" name="cardType" value="" placeholder="10"/></td>
        </tr>

        <tr>
            <td><label>CCC Lost Stolen Date Time</label></td>
            <td><input type="string" name="cCCLostStolenDateTime" value="" placeholder="text"/></td>
        </tr>

        <tr>
            <td><label>Autoload State</label></td>
            <td><input type="number" name="autoloadState" value="1" placeholder="0"/></td>
        </tr>
        <tr>
            <td><label>PassengerType</label></td>
            <td><input type="text" name="passengerType" value="" placeholder="Adult"/></td>
        </tr>
        <tr>
            <td><label>PhotocardNumber</label></td>
            <td><input type="text" name="photocardNumber" value="" placeholder="XXXX"/></td>
        </tr>
        <tr>
            <td><label>Registered</label></td>
            <td><input type="number" name="registered" value="" placeholder="0"/></td>
        </tr>
    </table>
</div>

<div style="float: left">
    <h2>Card Holder Details</h2>
    <table>
        <tr>
            <td><label>Title</label></td>
            <td><input type="text" name="title" value="" placeholder="Title"/></td>
        </tr>
        <tr>
            <td><label>First Name</label></td>
            <td><input type="text" name="firstName" value="" placeholder="First Name"/></td>
        </tr>
        <tr>
            <td><label>Middle Initial</label></td>
            <td><input type="text" name="middleInitial" value="" placeholder="Middle Initial"/></td>
        </tr>
        <tr>
            <td><label>Last Name</label></td>
            <td><input type="text" name="lastName" value="" placeholder="Last Name"/></td>
        </tr>
        <tr>
            <td><label>House Name</label></td>
            <td><input type="text" name="houseName" value="" placeholder="House Name"/></td>
        </tr>
        <tr>
            <td><label>House Number</label></td>
            <td><input type="text" name="houseNumber" value="" placeholder="House Number"/></td>
        </tr>
        <tr>
            <td><label>Street</label></td>
            <td><input type="text" name="street" value="" placeholder="Street"/></td>
        </tr>
        <tr>
            <td><label>Town</label></td>
            <td><input type="text" name="town" value="" placeholder="Town"/></td>
        </tr>
        <tr>
            <td><label>County</label></td>
            <td><input type="text" name="county" value="" placeholder="County"/></td>
        </tr>
        <tr>
            <td><label>Postcode</label></td>
            <td><input type="text" name="postcode" value="" placeholder="Postcode"/></td>
        </tr>
        <tr>
            <td><label>Day Time Phone Number</label></td>
            <td><input type="text" name="dayTimeTelephoneNumber" value="" placeholder="Day Time Phone Number"/></td>
        </tr>
        <tr>
            <td><label>Email Address</label></td>
            <td><input type="text" name="emailAddress" value="" placeholder="Email Address" size="30"/></td>
        </tr>
        <tr>
            <td><label>Password</label></td>
            <td><input type="text" name="password" value="" placeholder="Password"/></td>
        </tr>
    </table>
</div>

<div style="float: left">
    <h2>Card Discount</h2>
    <table>
        <tr>
            <td><label>Discount EntitleMent 1</label></td>
            <td><input type="text" name="discountEntitlement1" value="" placeholder="Full Time Education"/></td>
        </tr>
        <tr>
            <td><label>Discount EntitleMent 2</label></td>
            <td><input type="text" name="discountEntitlement2" value="" placeholder=""/></td>
        </tr>
        <tr>
            <td><label>Discount EntitleMent 3</label></td>
            <td><input type="text" name="discountEntitlement3" value="" placeholder=""/></td>
        </tr>
        <tr>
            <td><label>Discount Expiry1</label></td>
            <td><input id="discountExpiry1" type="string" name="discountExpiry1" value="" placeholder="dd/mm/yyyy"
                       class="date"/></td>
        </tr>
        <tr>
            <td><label>Discount Expiry2</label></td>
            <td><input id="discountExpiry2" type="string" name="discountExpiry2" value="" placeholder="dd/mm/yyyy"
                       class="date"/></td>
        </tr>
        <tr>
            <td><label>Discount Expiry3</label></td>
            <td><input id="discountExpiry3" type="string" name="discountExpiry3" value="" placeholder="dd/mm/yyyy"
                       class="date"/></td>
        </tr>
    </table>
</div>


<div style="float: left">
    <h2>Card Pending Items PPV</h2>
    <table>
        <tr>
            <td><label>Currency</label></td>
            <td><input type="number" name="currency" value="0" placeholder="0"/></td>
        </tr>
        <tr>
            <td><label>PickupLocation</label></td>
            <td><input type="number" name="pickupLocation" value="" placeholder="582"/></td>
        </tr>
        <tr>
            <td><label>PrePayValue</label></td>
            <td><input type="number" name="prePayValue" value="" placeholder="220"/></td>
        </tr>
        <tr>
            <td><label>RealTimeFlag</label></td>
            <td><input type="text" name="realTimeFlag" value="" placeholder="N"/></td>
        </tr>
        <tr>
            <td><label>RequestSequenceNumber</label></td>
            <td><input type="number" name="requestSequenceNumber" value="" placeholder="0"/></td>
        </tr>
        <tr>
            <td><label>Balance</label></td>
            <td><input type="number" name="balance" value="220" placeholder="0"/></td>
        </tr>
    </table>
</div>
<div style="clear:both"></div>
<div style="float: left">
    <h2>Card PPT Details - PPT Slot 1</h2>
    <table>
        <tr>
            <td><label>Discount</label></td>
            <td><input type="text" name="discount" value="" placeholder="No discount/NA"/></td>
        </tr>
        <tr>
            <td><label>ExpiryDate</label></td>
            <td><input id="pptExpiryDate" type="string" name="pptExpiryDate" value="" placeholder="dd/mm/yyyy" class="date"/>
            </td>
        </tr>
        <tr>
            <td><label>PassengerType</label></td>
            <td><input type="text" name="pptPassengerType" value="" placeholder="Adult"/></td>
        </tr>
        <tr>
            <td><label>Product</label></td>
            <td><input type="text" name="product" value="" placeholder="Freedom Pass (Elderly)" size="30"/></td>
        </tr>
        <tr>
            <td><label>SlotNumber</label></td>
            <td><input type="number" name="slotNumber" value="" placeholder="1"/></td>
        </tr>
        <tr>
            <td><label>Start Date</label></td>
            <td><input id="pptstartDate1" type="string" name="startDate" value="" placeholder="dd/mm/yyyy" class="date"/></td>
        </tr>
        <tr>
            <td><label>State</label></td>
            <td><input type="number" name="state" value="" placeholder="1"/></td>
        </tr>
        <tr>
            <td><label>Zone</label></td>
            <td><input type="text" name="zone" value="" placeholder="!"/></td>
        </tr>
    </table>
</div>

<div style="float: left">
    <h2>Card PPT Details - PPT Slot 2</h2>
    <table>
        <tr>
            <td><label>Discount</label></td>
            <td><input type="text" name="discount1" value="" placeholder="No discount/NA"/></td>
        </tr>
        <tr>
            <td><label>ExpiryDate</label></td>
            <td><input id="pptExpiryDate1" type="string" name="pptExpiryDate1" value="" placeholder="dd/mm/yyyy" class="date"/>
            </td>
        </tr>
        <tr>
            <td><label>PassengerType</label></td>
            <td><input type="text" name="pptPassengerType1" value="" placeholder="Adult"/></td>
        </tr>
        <tr>
            <td><label>Product</label></td>
            <td><input type="text" name="product1" value="" placeholder="Freedom Pass (Elderly)" size="30"/></td>
        </tr>
        <tr>
            <td><label>SlotNumber</label></td>
            <td><input type="number" name="slotNumber1" value="" placeholder="1"/></td>
        </tr>
        <tr>
            <td><label>Start Date</label></td>
            <td><input id="pptstartDate1" type="string" name="startDate1" value="" placeholder="dd/mm/yyyy" class="date"/></td>
        </tr>
        <tr>
            <td><label>State</label></td>
            <td><input type="number" name="state1" value="" placeholder="1"/></td>
        </tr>
        <tr>
            <td><label>Zone</label></td>
            <td><input type="text" name="zone1" value="" placeholder="!"/></td>
        </tr>
    </table>
</div>

<div style="float: left">
    <h2>Card PPT Details - PPT Slot 3</h2>
    <table>
        <tr>
            <td><label>Discount</label></td>
            <td><input type="text" name="discount2" value="" placeholder="No discount/NA"/></td>
        </tr>
        <tr>
            <td><label>ExpiryDate</label></td>
            <td><input id="pptExpiryDate2" type="string" name="pptExpiryDate2" value="" placeholder="dd/mm/yyyy" class="date"/>
            </td>
        </tr>
        <tr>
            <td><label>PassengerType</label></td>
            <td><input type="text" name="pptPassengerType2" value="" placeholder="Adult"/></td>
        </tr>
        <tr>
            <td><label>Product</label></td>
            <td><input type="text" name="product2" value="" placeholder="Freedom Pass (Elderly)" size="30"/></td>
        </tr>
        <tr>
            <td><label>SlotNumber</label></td>
            <td><input type="number" name="slotNumber2" value="" placeholder="1"/></td>
        </tr>
        <tr>
            <td><label>Start Date</label></td>
            <td><input id="pptstartDate2" type="string" name="startDate2" value="" placeholder="dd/mm/yyyy" class="date"/></td>
        </tr>
        <tr>
            <td><label>State</label></td>
            <td><input type="number" name="state2" value="" placeholder="1"/></td>
        </tr>
        <tr>
            <td><label>Zone</label></td>
            <td><input type="text" name="zone2" value="" placeholder="!"/></td>
        </tr>
    </table>
</div>

<div style="float: left">
    <h2>Card Pending Items - PPT 1</h2>
    <table>
        <tr>
            <td><label>Request Sequence Number</label></td>
            <td><input type="number" name="pendingPptRequestSequenceNumber1" value="" placeholder="0"/></td>
        </tr>
        <tr>
            <td><label>RealTime Flag</label></td>
            <td><input type="text" name="pendingPptRealTimeFlag1" value="" placeholder="N"/></td>
        </tr>
        <tr>
            <td><label>Product Code</label></td>
            <td><input type="number" name="pendingPptProductCode1" value="" placeholder="200"/></td>
        </tr>
        <tr>
            <td><label>Product Price</label></td>
            <td><input type="number" name="pendingPptProductPrice1" value="" placeholder="1060"/></td>
        </tr>
        <tr>
            <td><label>Currency</label></td>
            <td><input type="number" name="pendingPptCurrency1" value="0" placeholder="0"/></td>
        </tr>
        <tr>
            <td><label>Start Date</label></td>
            <td><input id="pendingPptStartDate1" type="string" name="pendingPptStartDate1" value="" placeholder="dd/mm/yyyy"
                       class="date"/></td>
        </tr>
        <tr>
            <td><label>Expiry Date</label></td>
            <td><input id="pendingPptExpiryDate1" type="string" name="pendingPptExpiryDate1" value="" placeholder="dd/mm/yyyy"
                       class="date"/></td>
        </tr>
        <tr>
            <td><label>Pickup Location</label></td>
            <td><input type="number" name="pendingPptPickupLocation1" value="" placeholder="582"/></td>
        </tr>
    </table>
</div>

<div style="float: left">
    <h2>Card Pending Items - PPT 2</h2>
    <table>
        <tr>
            <td><label>Request Sequence Number</label></td>
            <td><input type="number" name="pendingPptRequestSequenceNumber2" value="" placeholder="0"/></td>
        </tr>
        <tr>
            <td><label>RealTime Flag</label></td>
            <td><input type="text" name="pendingPptRealTimeFlag2" value="" placeholder="N"/></td>
        </tr>
        <tr>
            <td><label>Product Code</label></td>
            <td><input type="number" name="pendingPptProductCode2" value="" placeholder="200"/></td>
        </tr>
        <tr>
            <td><label>Product Price</label></td>
            <td><input type="number" name="pendingPptProductPrice2" value="" placeholder="2060"/></td>
        </tr>
        <tr>
            <td><label>Currency</label></td>
            <td><input type="number" name="pendingPptCurrency2" value="0" placeholder="0"/></td>
        </tr>
        <tr>
            <td><label>Start Date</label></td>
            <td><input id="pendingPptStartDate2" type="string" name="pendingPptStartDate2" value="" placeholder="dd/mm/yyyy"
                       class="date"/></td>
        </tr>
        <tr>
            <td><label>Expiry Date</label></td>
            <td><input id="pendingPptExpiryDate2" type="string" name="pendingPptExpiryDate2" value="" placeholder="dd/mm/yyyy"
                       class="date"/></td>
        </tr>
        <tr>
            <td><label>Pickup Location</label></td>
            <td><input type="number" name="pendingPptPickupLocation2" value="" placeholder="582"/></td>
        </tr>
    </table>
</div>

<div style="float: left">
    <h2>Card Pending Items - PPT 3</h2>
    <table>
        <tr>
            <td><label>Request Sequence Number</label></td>
            <td><input type="number" name="pendingPptRequestSequenceNumber3" value="" placeholder="0"/></td>
        </tr>
        <tr>
            <td><label>RealTime Flag</label></td>
            <td><input type="text" name="pendingPptRealTimeFlag3" value="" placeholder="N"/></td>
        </tr>
        <tr>
            <td><label>Product Code</label></td>
            <td><input type="number" name="pendingPptProductCode3" value="" placeholder="200"/></td>
        </tr>
        <tr>
            <td><label>Product Price</label></td>
            <td><input type="number" name="pendingPptProductPrice3" value="" placeholder="3060"/></td>
        </tr>
        <tr>
            <td><label>Currency</label></td>
            <td><input type="number" name="pendingPptCurrency3" value="0" placeholder="0"/></td>
        </tr>
        <tr>
            <td><label>Start Date</label></td>
            <td><input id="pendingPptStartDate3" type="string" name="pendingPptStartDate3" value="" placeholder="dd/mm/yyyy"
                       class="date"/></td>
        </tr>
        <tr>
            <td><label>Expiry Date</label></td>
            <td><input id="pendingPptExpiryDate3" type="string" name="pendingPptExpiryDate3" value="" placeholder="dd/mm/yyyy"
                       class="date"/></td>
        </tr>
        <tr>
            <td><label>Pickup Location</label></td>
            <td><input type="number" name="pendingPptPickupLocation3" value="" placeholder="582"/></td>
        </tr>
    </table>
</div>

<div style="clear: both; float: left;">
    <button type="submit" name="targetAction" value="addCard">Add Card Response</button>
</div>
</form:form>

<script type="text/javascript">
    $(document).ready(function () {
        $(".date").each(function (e) {
            $(this).datepicker({ dateFormat: 'dd/mm/yy' });
        });
    });
</script>