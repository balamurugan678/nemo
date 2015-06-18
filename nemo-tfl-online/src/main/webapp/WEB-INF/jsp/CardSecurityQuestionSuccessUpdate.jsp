<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<nemo-tfl:breadcrumbs pageNames='<%=new String[]{Page.DASHBOARD, Page.VIEW_OYSTER_CARD, Page.CHANGE_SECURITY_QUESTION}%>'/>
<to:areaheader title="heading"/>

<div class="r">
    <div class="main">
        <div class="page-heading with-border">
        	<div class="content"><to:textPlain id="oysterText"/> ${securityQuestionCmd.cardNumber}</div>
        </div>
        <form:form action="<%= PageUrl.CHANGE_SECURITY_QUESTION %>" commandName="<%= PageCommand.SECURITY_QUESTION %>"
                   cssClass=".form-with-tooltips-width">
            <div class="box borderless">
                <div class="tight-margin">
                    <p><spring:message code="confirmSecurityAnswer.update.info"/></p>
                </div>
            </div>
            &nbsp;
            <div class="container boxed-link link-button">
                <to:primaryButton id="backToOyesterCard" buttonType="submit" targetAction="<%= PageParameterValue.CANCEL %>"/>
            </div>
        </form:form>
    </div>
    <tiles:insertAttribute name="myAccount"/>
</div>

