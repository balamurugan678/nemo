<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>
<%@ page import="com.novacroft.nemo.tfl.innovator.controller.AddUnattachedCardController" %>


<style>
<!--
#searchresults .different {background-color:red};
-->
</style>

<to:page name="addUnattachedCard"/>

	
	 <div id="errors">
        <to:text id="cardNumber"></to:text>
        <to:formError />
        <span class="left"><to:button id="searchCard"></to:button></span>
        <span class="left"><to:loadingIcon></to:loadingIcon></span>
        <span id="verified" style="display: none">verified</span>
    </div>
		<div id="applicant">
			<div id="customersFound">
				<label>Customers Found:</label>
			</div>
			<div id="hotlistWarning" style="display: none">
				<h4>This card is hotlisted</h4>
			</div>
			<div id="results" style="float:left; width: 800px;">
			<table style="width: 100%;" id="searchresults">
				<thead>
					<tr>
						<th>Data Item</th>
						<th>Cubic Data</th>
						<th>Oyster Online Data</th>
						<th>Equal</th>
					</tr>
				</thead>
				<tbody id="tbody">
				</tbody>
			</table>
			</div>
		</div>
		<div id="attach" style="float:left">
			<to:button id="attachCard" buttonType="submit"
				targetAction="attachCard"></to:button>

			<to:hidden id="customerId" />
			<to:hidden id="webAccountId" />
		</div>
		<div class="clear"></div>

<script type="text/javascript">
    var sAddress = "${pageContext.request.contextPath}";
    var pageName = "<%=Page.ADD_UNATTACHED_CARD %>";
    var cardSearchURL = "<%=PageUrl.INV_CARD_SEARCH %>";
    var messagetext = '${MessageText}';
 </script>
 
 
<script src="scripts/addUnattachedCard.js"></script>

