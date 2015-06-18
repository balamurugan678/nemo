<%@include file="/WEB-INF/jspf/pageCommon.jspf"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<nemo-tfl:breadcrumbs
	pageNames='<%=new String[]{Page.DASHBOARD, Page.VIEW_OYSTER_CARD,
					Page.TRANSFER_PRODUCT, Page.TRANSFER_SUMMARY}%>' />
<to:headLine />
<div class="r">
	<div class="main">
		<form:form method="post" action="<%= PageUrl.TRANSFER_SUMMARY %>"
			commandName="<%=PageCommand.CART%>" cssClass="oo-responsive">
			<div class="box borderless">
				<table border="1" class="shoppingcart">
					<tr>
						<to:tableHeader id="sourceCardNumber" />
						<td>${cartCmd.sourceCardNumber}</td>
					</tr>
					<tr>
						<to:tableHeader id="targetCardNumber" />
						<td>${cartCmd.targetCardNumber}</td>
					</tr>
					<tr>
						<to:tableHeader id="pickUpStation" />
						<td>${cartCmd.stationName}</td>
					</tr>
					<tr>
						<spring:message code="${pageName}.discountsNotTransferred.text"/>
					</tr>
				</table>

			</div>
			<br />
			<div class="oo-responsive">
				<div class="button-container clearfix">
					<to:secondaryButton id="cancel" buttonType="submit"
						targetAction="<%=PageParameterValue.CANCEL%>" />
					<to:primaryButton id="continue" buttonType="submit"
						targetAction="<%= PageParameterValue.CONTINUE %>" />
				</div>
			</div>
		</form:form>
	</div>
	<div class="aside"></div>
</div>