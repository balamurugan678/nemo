<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

<div class="r">
	<div class="main">
		<form:form method="post" action="<%= PageUrl.TRANSFER_SUMMARY %>"
			commandName="<%=PageCommand.CART%>" cssClass="oo-responsive">
			<to:header id="transferSummary"/>
			<div class="box borderless">
				<table border="1" class="shoppingcart">
					<tr>
						<td><to:label id="sourceCardNumber" /></td>
						<td>${cartCmd.sourceCardNumber}</td>
					</tr>
					<tr>
						<td><to:label id="targetCardNumber" /></td>
						<td>${cartCmd.targetCardNumber}</td>
					</tr>
					<tr>
						<td><to:label id="pickUpStation" /></td>
						<td>${cartCmd.stationName}</td>
					</tr>
					<tr>
						<spring:message code="${pageName}.discountsNotTransferred.text"/>
						<br/>
						<spring:message code="${pageName}.innovator.summaryConfirmation.text"/>
						<br/>
					</tr>
				</table>
			</div>
			<br />
			<div id="toolbar">
				<div class="left"></div>
				<div class="right">
					<to:primaryButton id="cancel" buttonCssClass="rightalignbutton"
						buttonType="submit"
						targetAction="<%= PageParameterValue.CANCEL %>" />
					<to:primaryButton id="transferProduct"
						buttonCssClass="rightalignbutton" buttonType="submit"
						targetAction="<%= PageParameterValue.CONTINUE %>" />
				</div>
			</div>
		</form:form>
	</div>
	<div class="aside"></div>
</div>