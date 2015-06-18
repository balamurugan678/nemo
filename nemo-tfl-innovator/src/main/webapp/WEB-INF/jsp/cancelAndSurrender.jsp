<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>


<form:form id="CancelSurrenderCardRefundCartForm" commandName="<%=PageCommand.CART%>" cssClass="form-with-tooltips" action="<%= PageUrl.INV_CANCEL_AND_SURRENDER_CARD %>">
	<to:header id="cancelAndSurrender"/>
	<jsp:include page="manageCancelAndSurrenderTravelCards.jsp"/>
	<jsp:include page="backDatedOrDeceased.jsp"/>
	<jsp:include page="manageGoodwill.jsp" />
    <jsp:include page="managePayAsYouGo.jsp"/>
    <jsp:include page="manageAdministrationFee.jsp"/>
    <jsp:include page="manageDateOfRefund.jsp"/>
    <jsp:include page="managePayment.jsp"/>

    <div id="toolbar">
        <div class="left">

        </div>
        <div class="right">
        	<to:primaryButton id="cancel" buttonCssClass="rightalignbutton" buttonType="submit" targetAction="<%= PageParameterValue.CANCEL %>"/>
            <to:primaryButton id="refund" buttonCssClass="rightalignbutton" buttonType="submit" targetAction="<%= PageParameterValue.REFUND %>"/>
        </div>
    </div>
</form:form>