<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

<form:form commandName="<%=PageCommand.CART%>" cssClass="form-with-tooltips" action="<%= PageUrl.INV_CANCEL_AND_SURRENDER_CARD_REFUND_CART_SUMMARY %>">
	<to:header id="cancelAndSurrenderRefundCart"/>
	<div class="clear"></div>
	
	<jsp:include page="cancelAndSurrenderTravelCardsSummary.jsp" />
	<jsp:include page="showGoodwillsSummary.jsp" />
	<jsp:include page="showPayAsYouGoSummary.jsp" />
    <jsp:include page="viewAdministrationFee.jsp" />
	<jsp:include page="showDateOfRefundSummary.jsp" />
	<jsp:include page="showPaymentStatus.jsp" />
	<jsp:include page="showOrderStatus.jsp" />
	
	<div id="toolbar">
        <div class="left">

        </div>
        <div class="right">
            <to:primaryButton id="backToCustomerPage" buttonCssClass="rightalignbutton" buttonType="submit" targetAction="<%= PageParameterValue.BACK_TO_CUSTOMER_PAGE %>"/>
        </div>
    </div>	
</form:form>