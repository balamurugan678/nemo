<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page import="com.novacroft.nemo.mock_cubic.constant.Constant"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>Add Card Update</h1>
<p>This update can be used for Pre Pay Ticket and also for Pre Pay
	Value requests. The updates can be through a form post to controller or
	an XML request. The Request Sequence Number is generated when using an
	XML request while the entry in the form is used for the post</p>
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

<CardUpdateRequest>
	<RealTimeFlag>N</RealTimeFlag>
	<PrestigeID>123456789</PrestigeID>
	<Action>ADD</Action>
	<PPV>
		<PrePayValue>2000</PrePayValue>
		<Currency>0</Currency>
	</PPV>
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

<CardUpdateRequest>
	<PrestigeID>123456789</PrestigeID>
	<Action>REMOVE</Action>
	<OriginalRequestSequenceNumber>1221</OriginalRequestSequenceNumber>
	<OriginatorInfo>
		<UserID>LTWebUser</UserID>
		<Password>secrets</Password>
	</OriginatorInfo>
</CardUpdateRequest>

<CardUpdateResponse>
	<PrestigeID>123456789</PrestigeID>
	<RequestSequenceNumber>1235</RequestSequenceNumber>
	<RemovedRequestSequenceNumber>1221</RemovedRequestSequenceNumber>
</CardUpdateResponse>

<RequestFailure>
	<ErrorCode>40</ErrorCode>
	<ErrorDescription><![CDATA[CARD NOT FOUND]]></ErrorDescription>
</RequestFailure>

 -->

<form:form method="POST" action="<%=Constant.ADD_CARD_UPDATE_URL%>"
	modelAttribute="updateCmd">
	<div>
		<h1>Add Update Request</h1>
		<table>
			<tr>
				<td><label>PrestigeID</label></td>
				<td><input type="text" name="prestigeId" value=""
					placeholder="Oyster Card Number" size="40" /></td>
			</tr>
		</table>
	</div>
	</table>
	<div style="float: left;">
		<h2>PPT</h2>
		<table>
			<tr>
				<td><label>Product Code</label></td>
				<td><input type="number" name="productCode" value=""
					placeholder="200" /></td>
			</tr>
			<tr>
				<td><label>Product Price</label></td>
				<td><input type="number" name="productPrice" value=""
					placeholder="1060" /></td>
			</tr>
			<tr>
				<td><label>Start Date</label></td>
				<td><input id="startDate" type="text" name="startDate" value=""
					placeholder="dd/mm/yyyy" class="date" /></td>
			</tr>
			<tr>
				<td><label>Expiry Date</label></td>
				<td><input id="expiryDate" type="text" name="expiryDate"
					value="" placeholder="dd/mm/yyyy" class="date" /></td>
			</tr>
		</table>
	</div>
	<div style="float: left;">
		<h2>PPV</h2>
		<table>
			<tr>
				<td><label>Pre Pay Value</label></td>
				<td><input type="number" name="prePayValue" value=""
					placeholder="2000" /></td>
			</tr>
		</table>
	</div>
	</div>
	<div style="clear: both; float: left;">
		<button type="submit" name="update" value="updateCard">Update
			Card</button>
		<button type="submit" name="updateXML" value="updateCardXML">Update
			Card XML</button>
	</div>
</form:form>

<div style="clear: both"></div>

<div style="clear: both">
	<h1>Remove Pending Update Request</h1>
</div>
<form:form method="POST" action="<%=Constant.ADD_CARD_UPDATE_URL%>"
	modelAttribute="removeCmd">
	<table>
		<div style="clear: both">
			<tr>
				<td><label>PrestigeID</label></td>
				<td><input type="text" name="prestigeId" value=""
					placeholder="Oyster Card Number" size="40" /></td>
			</tr>
			<tr>
				<td><label>Original Request Sequence Number</label></td>
				<td><input type="number" name="originalRequestSequenceNumber"
					value="" placeholder="0" /></td>
			</tr>
			<tr>
				<td><label>UserID</label></td>
				<td><input type="text" name="userId" value=""
					placeholder="LTWebUser" /></td>
			</tr>
			<tr>
				<td><label>Password</label></td>
				<td><input type="text" name="password" value=""
					placeholder="secrets" /></td>
			</tr>
		</div>
	</table>
	<div style="clear: both; float: left;">
		<button type="submit" name="remove" value="removePending">Remove
			Pending</button>
		<button type="submit" name="removeXML" value="removePendingXML">Remove
			Pending XML</button>
	</div>
</form:form>

<script type="text/javascript">
	$(document).ready(function() {
		$(".date").each(function(e) {
			$(this).datepicker({
				dateFormat : 'dd/mm/yy'
			});
		});
	});
</script>
