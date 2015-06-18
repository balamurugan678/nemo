<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>

<%@attribute name="value" required="true" type="java.lang.String" %>

<fmt:formatNumber type="currency" maxFractionDigits="2" minFractionDigits="2" currencySymbol="£" value="${value/100}"/>