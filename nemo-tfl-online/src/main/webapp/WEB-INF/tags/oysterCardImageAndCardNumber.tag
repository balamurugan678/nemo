<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>

<%@attribute name="oysterCardNumber" required="true" type="java.lang.String" %>

<div class="content">
	<img src="images/oyster.gif" class="oyster-image-icon-display" width="60" height="25"/>
	<span class="oysterCardNumber">${oysterCardNumber}</span>
</div>
