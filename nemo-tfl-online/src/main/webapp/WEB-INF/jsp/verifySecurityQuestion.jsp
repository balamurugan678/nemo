<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

<nemo-tfl:breadcrumbs
        pageNames='<%=new String[]{Page.DASHBOARD,Page.VERIFY_SECURITY_QUESTION}%>'/>
<to:areaheader title="verifySecurityQuestion.heading"/>

<div class="r">
    <form:form action="<%= PageUrl.VERIFY_SECURITY_QUESTION %>" commandName="<%= PageCommand.SECURITY_QUESTION %>"
               cssClass="form-with-tooltips">
        <div class="box borderless">
            <to:head2 id="cardRegistration"/>
            <to:paragraph id="info"/>
            <jsp:include page="commonSecurityQuestion.jsp"></jsp:include>
            <hr>
            <to:buttons targetAction="<%= PageParameterValue.VERIFY %>"></to:buttons>
        </div>
    </form:form>
</div>