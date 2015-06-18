<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

<div id="customer">
        <to:header id="customer"/>
        <to:label id="registeredCard"/> ${fulfilmentCmd.registeredCard }
        <to:label id="cardSecurityQuestion"/><br/><br/><spring:message code="securityquestions.${fulfilmentCmd.card.securityQuestion}.option"/>
        <to:label id="cardSecurityAnswer"/> <br/><br/>${fulfilmentCmd.card.securityAnswer}
        <to:label id="customerName"/> <br/><br/>${fulfilmentCmd.customerName}
</div>
