<%@page import="com.novacroft.nemo.tfl.common.constant.ContentCode" %>
<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

<form:form id="transferProductForm" commandName="<%=PageCommand.CART%>" cssClass="form-with-tooltips" action="<%= PageUrl.TRANSFER_PRODUCT %>">
	<input type="hidden" id="cardNumber" name="cardNumber" value="${cart.cardNumber}"/>
	<input type="hidden" id="customerId" name="customerId" value="${cart.customerId}"/>
	<to:header id="transferProduct"/>
	<jsp:include page="manageTargetCards.jsp"/>
</form:form>