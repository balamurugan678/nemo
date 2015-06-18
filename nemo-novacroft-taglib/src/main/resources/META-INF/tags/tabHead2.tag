<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>

<%@attribute name="id" required="true" type="java.lang.String" %>
<%@attribute name="headingCssClass" required="false" type="java.lang.String" %>
<%@attribute name="headingOverride" required="false" type="java.lang.String" %>

<div class="widget-tab">
    <to:head2 id="login" headingCssClass="widget-heading ${headingCssClass}" headingOverride="${headingOverride}"/>
</div>
