<%@include file="/WEB-INF/jspf/pageCommon.jspf"%>



<form:form id="failedCardRefundCartForm"
	commandName="<%=PageCommand.CART%>" cssClass="form-with-tooltips"
	action="<%=PageUrl.INV_FAILED_CARD_REFUND_CART%>">
	<to:header id="failedCardRefundCart" />
	<jsp:include page="manageTravelCards.jsp" />
	<jsp:include page="manageGoodwill.jsp">
		<jsp:param name="pageName" value="${pageName}" />
		<jsp:param name="cartType" value="failedCardRefund" />
	</jsp:include>
	<jsp:include page="managePayAsYouGo.jsp" />
	<jsp:include page="manageAdministrationFee.jsp" />
	<jsp:include page="manageDateOfRefund.jsp" />
	<jsp:include page="manageDeposit.jsp"/>
	<jsp:include page="managePayment.jsp" />
	<jsp:include page="manageTransferProduct.jsp" />

	<div id="toolbar">
		<div class="left"></div>
		<div class="right">
			<to:primaryButton id="cancel" buttonCssClass="rightalignbutton"
				buttonType="submit" targetAction="<%=PageParameterValue.CANCEL%>" />
			<to:primaryButton id="refund" buttonCssClass="rightalignbutton"
				buttonType="submit" targetAction="<%=PageParameterValue.REFUND%>" />
		</div>
	</div>
</form:form>