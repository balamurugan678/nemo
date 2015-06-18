<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

<to:header id="AnonymousGoodwillRefund"/>
<div class="clear"></div>

<form:form id="anonymousGoodwillRefundForm" commandName="<%=PageCommand.CART%>" cssClass="form-with-tooltips" action="<%= PageUrl.INV_ANONYMOUS_GOODWILL_REFUND_MAIN %>">
    <jsp:include page="manageOysterCard.jsp"/>
    
    <div id="toolbar">
        <div class="left">

        </div>
        <div class="right">
            <to:primaryButton id="continue" buttonCssClass="rightalignbutton" buttonType="submit" targetAction="<%= PageParameterValue.CONTINUE %>"/>
        </div>
    </div>
</form:form>