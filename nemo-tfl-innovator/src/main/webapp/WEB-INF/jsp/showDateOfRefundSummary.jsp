<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

<div>
	<to:head3 id="dateOfRefund" headingCssClass="space-before-head"/>
	<to:label id="dateOfRefund" />
	<fmt:formatDate pattern="<%= DateConstant.SHORT_DATE_PATTERN %>" value="${cartCmd.cartDTO.dateOfRefund}"/>
</div>
<div class="clear"></div>