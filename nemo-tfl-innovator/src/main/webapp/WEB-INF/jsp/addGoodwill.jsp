<%@include file="/WEB-INF/jspf/pageCommon.jspf"%>

<c:forEach items="${errors.allErrors}" var="error">  
	<spring:message code="${error.code}" text="${error.defaultMessage}"/>  
</c:forEach>  

<div>	
	<to:text id="cartItemCmd.goodwillPrice" maxlength="5" htmlEscape="false"/>
	<nemo-tfl:selectList id="selectGoodwillReason" path="cartItemCmd.goodwillPaymentId" selectList="${GoodwillRefundTypes}" mandatory="true" selectedValue="${cartCmd.cartItemCmd.goodwillPaymentId}" useManagedContentForMeanings="false" />
	<div id="goodwillOtherText">
    	<to:text id="cartItemCmd.goodwillOtherText" />
    </div>
	<div class='field-validation-error'><form:errors path="cartItemCmd.goodwillPaymentId" /></div>
	<div class="clear"></div>
	<div id="goodwillReasonMessageDiv" class="field-validation-error dialog-display"></div>
	<to:secondaryButton id="addGoodwill" buttonCssClass="leftalignbutton" buttonType="submit" targetAction="<%= PageParameterValue.ADD_GOODWILL %>"/>
</div>
<div style="clear: both"></div>