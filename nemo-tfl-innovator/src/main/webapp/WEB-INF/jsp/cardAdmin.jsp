<%@page import="com.novacroft.nemo.tfl.common.constant.PageAttribute"%>
<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

<form:form id="cardAdminSelection" class="form-with-tooltips" action="<%=PageUrl.INV_CARD_ADMIN %>" method="POST">
    <div id="cardAdmin">
        <input type="hidden" id="cardNumber" name="cardNumber" value="${cardNumber}"/>
        <input type="hidden" id="customerId" name="customerId" value="${customerId}"/>
        <to:header id="cardAdmin"/>
        <div class="box borderless bold">
        	<to:label id="oysterCardNumber" value="${cardNumber}"/>
        </div> 
        <br/><br/><br/>
        <to:secondaryButton id="viewJourneyHistory" buttonCssClass="button" buttonType="submit" targetAction="<%= PageParameterValue.JOURNEY_HISTORY %>"/> <br/><br/>
        <to:secondaryButton id="viewIncomplete" buttonCssClass="button" buttonType="submit" targetAction="<%= PageParameterValue.INV_INCOMPLETE_JOURNEYS %>"/> <br/><br/>
        <to:secondaryButton id="editCardPreferences" buttonCssClass="button" buttonType="submit" targetAction="<%= PageParameterValue.CARD_PREFERENCES %>"/> <br/><br/>
        <to:secondaryButton id="manageAutoTopUp" buttonCssClass="button" buttonType="submit" targetAction="<%= PageParameterValue.MANAGE_AUTO_TOP_UP %>"/> <br/><br/>
    </div>
</form:form>

