<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>
<div >
    <to:head3 id="payment" headingCssClass="space-before-head"/>

	<form:errors path="PaymentType" cssClass="field-validation-error"/>
	<to:text id="paymentType" value="${cartCmd.paymentType}" readonly="true" />
    <div class="clear"></div>
    
    <to:label id="total"/>
    <nemo-tfl:poundSterlingFormat amount="${cartDTO.cartRefundTotal}"/><br/>
	<form:errors path="cartDTO.cartRefundTotal" cssClass="field-validation-error"/>
	<div class="clear"></div>
     <to:hidden id="cardNumber"  value="${cartCmd.cardNumber}" />
    <div id="paymentTypeAdhocLoad">
     	<to:head4 id="paymentTypeAdhocLoad" headingCssClass="space-before-head"/>
        <nemo-tfl:selectList id="selectStation" path="stationId" selectList="${stationSelectList}" mandatory="true"
                             useManagedContentForMeanings="false" selectedValue="${cartCmd.stationId}"/>
        <form:errors path="stationId" cssClass="field-validation-error"/>
    </div>
    
</div>
<div class="clear"></div>
