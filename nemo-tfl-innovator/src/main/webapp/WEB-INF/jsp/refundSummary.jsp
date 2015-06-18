<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

<form:form commandName="<%=PageCommand.CART%>" cssClass="form-with-tooltips" action="<%=PageUrl.INV_REFUND_SUMMARY%>">
	<to:header id="refundSummary"/>
	<div class="clear"></div>
	
	<c:choose>
		<c:when test="${cartCmd.cartType == 'StandaloneGoodwillRefund'}">
			<jsp:include page="showGoodwillsSummary.jsp" />
			<jsp:include page="showDateOfRefundSummary.jsp" />
			<jsp:include page="showPaymentStatus.jsp" />
			<jsp:include page="showOrderStatus.jsp" />
		</c:when>
		<c:otherwise>
			<jsp:include page="travelCardsSummary.jsp" />
			<jsp:include page="showGoodwillsSummary.jsp" />
			<jsp:include page="showPayAsYouGoSummary.jsp" />
		    <jsp:include page="viewAdministrationFee.jsp" />
			<jsp:include page="showDateOfRefundSummary.jsp" />
			<jsp:include page="manageDepositRefundSummary.jsp" />
			<jsp:include page="showPaymentStatus.jsp" />
			<jsp:include page="showOrderStatus.jsp" />
		</c:otherwise>
	</c:choose>
	<div id="toolbar">
        <div class="left">

        </div>
        <div class="right">
            <to:primaryButton id="backToCustomerPage" buttonCssClass="rightalignbutton" buttonType="submit" targetAction="<%= PageParameterValue.BACK_TO_CUSTOMER_PAGE %>"/>
        </div>
    </div>	
</form:form>