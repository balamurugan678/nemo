<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

<c:set var="enabledText"><spring:message code='autoTopUp.enabled.text'/></c:set>
<c:set var="disabledText"><spring:message code='autoTopUp.disabled.text'/></c:set>
<div class="r">
    <div>
        <%@include file="/jspf/manageAutoTopUp.jspf"%> 
    </div>
</div>