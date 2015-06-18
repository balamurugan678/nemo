<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

<nemo-tfl:breadcrumbs
			pageNames='<%=new String[] { Page.DASHBOARD, Page.CHOOSE_PAYMENT_CARD }%>' />

<to:headLine/>

<div class="r">
	<div class="main">
	    <form:form id="choosePaymentCard" method="post" action="<%=PageUrl.PAYMENT%>" commandName="<%= PageCommand.CART %>">
	        <form:errors path="*" cssClass="field-validation-error"/>
	        <nemo-tfl:radioButtonList id="paymentCardAction" path="paymentCardAction" selectList="${paymentCards}"
	                                  mandatory="true" selectedValue="${cartCmd.paymentCardAction}" showHint="false"
	                                  useManagedContentForMeanings="false"/>
	        <div class="button-container clearfix">
	            <to:secondaryButton id="cancel" buttonType="submit" targetAction="<%= PageParameterValue.PAYMENT_PAGE_BACK %>"/>
	            <to:primaryButton id="continue" buttonType="submit" targetAction="<%= PageParameterValue.SELECT_PAYMENT_CARD %>"/>
	        </div>
	    </form:form>
	</div>	    
	<tiles:insertAttribute name="myAccount"/>	 
</div>