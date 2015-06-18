<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

<form:form id ="fulfilOrderForm" commandName="<%=PageCommand.FULFILMENT%>" cssClass="form-with-tooltips">
<jsp:include page="orderDetails.jsp"/>	
<jsp:include page="customerDetails.jsp"/>
<jsp:include page="ticketDetails.jsp"/>
<jsp:include page="attachFulfiledOysterCard.jsp"/>

<div id="toolbar">
		<div class="left"></div>
		<div class="right">
			<to:primaryButton id="fulfilOrder" buttonCssClass="rightalignbutton"
				buttonType="submit" targetAction="<%=PageParameterValue.FULFIL_ORDER_CONFIRM%>" />
			<to:primaryButton id="cancel" buttonCssClass="rightalignbutton"
				buttonType="submit" targetAction="<%=PageParameterValue.CANCEL%>" />
		</div>
	</div>
</form:form>
