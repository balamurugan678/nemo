<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

<form:form id="failedCardRefundCartForm" commandName="<%=PageCommand.CART%>" cssClass="form-with-tooltips" action="<%= PageUrl.INV_STANDALONE_GOODWILL %>">
	<to:header id="standaloneGoodwill"/>
	<div class="clear"></div>
	
	<jsp:include page="manageGoodwill.jsp" />
	<jsp:include page="manageRefundDateWithNoAjax.jsp"/>
 	<jsp:include page="managePayment.jsp" /> 
	
	<div id="toolbar">
        <div class="left">

        </div>
        <div class="right">
            <to:primaryButton id="continue" buttonCssClass="rightalignbutton" buttonType="submit" targetAction="<%= PageParameterValue.CONTINUE %>"/>
        </div>
    </div>
<script src="scripts/standAloneGoodWill.js"></script>
</form:form>