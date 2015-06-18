<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page import="com.novacroft.nemo.mock_cubic.constant.Constant"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>Add Card Update Response</h1>
<p>
If a prestigeId has already been used this will overwrite the record.

This update response can be used for Pre Pay Ticket and also for Pre Pay Value responses.
</p>
<!-- 

<CardUpdateRequest>
<RealTimeFlag>N</RealTimeFlag>
<PrestigeID>123456789</PrestigeID>
<Action>ADD</Action>
<PPT>
<ProductCode>123</ProductCode>
<StartDate>10/10/2002</StartDate>
<ExpiryDate>11/10/2002</ExpiryDate>
<ProductPrice>360</ProductPrice>
<Currency>0</Currency>
</PPT>
<PickupLocation>740</PickupLocation>
<PaymentMethod>32</PaymentMethod>
<OriginatorInfo>
<UserID>LTWebUser</UserID>
<Password>secrets</Password>
</OriginatorInfo>
</CardUpdateRequest>



<CardUpdateResponse>
<PrestigeID>123456789</PrestigeID>
<RequestSequenceNumber>1234</RequestSequenceNumber>
<LocationInfo>
<PickupLocation>340</PickupLocation>
<AvailableSlots>100</AvailableSlots>
</LocationInfo>
</CardUpdateResponse>

<RequestFailure>
<ErrorCode>40</ErrorCode>
<ErrorDescription><![CDATA[CARD NOT FOUND]]></ErrorDescription>
</RequestFailure>

 -->

<form:form  action="<%=Constant.ADD_CARD_PREPAY_TICKET_RESPONSE_URL %>" modelAttribute="Cmd">
    <div style="clear: both;"></div>
    <div>
        <table>
            <tr>
                <td><label>PrestigeID</label></td>
                <td><input type="text" name="prestigeId" value="" placeholder="Oyster Card Number" size="40" /></td>
            </tr>
        </table>
    </div>
    <p>Fill in only one section below</p>
    <div style="float: left;">
        <table >
            <tr><td><h2>Update Success - Section</h2></td></tr>
            <tr>
                <td><label>Request Sequence Number</label></td>
                <td><input type="text" name="requestSequenceNumber" value="" placeholder="1234" /></td>
            </tr>
            <tr><td colspan="2"> Location Info </td></tr>
            <tr>
                <td><label>PickupLocation</label></td>
                <td><input type="number" name="pickupLocation" value="" placeholder="340" /></td>
            </tr>
            <tr>
                <td><label>AvailableSlots</label></td>
                <td><input type="number" name="availableSlots" value="" placeholder="100" /></td>
            </tr>
        </table>
    </div>
        <div style="float: left;">
        <table >
            <tr><td><h2>Error - Section</h2></td></tr>
            <tr>
                <td><label>Error Code</label></td>
                <td><input type="text" name="errorCode" value="" placeholder="1234" /></td>
            </tr>
            <tr>
                <td><label>Error Description</label></td>
                <td><input type="number" name="errorDescription" value="" placeholder="340" /></td>
            </tr>
        </table>
    </div>
    
    <div style="clear: both; float: left;">
        <button type="submit" name="targetAction" value="addCard">Add Card Response</button>
    </div>
</form:form>
