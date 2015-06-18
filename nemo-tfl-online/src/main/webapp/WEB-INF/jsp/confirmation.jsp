<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<c:set var="breadcrumb">${confirmationBreadcrumb}</c:set>
<nemo-tfl:breadcrumbs
        pageNames='<%=new String[]{Page.DASHBOARD,(String)pageContext.getAttribute("breadcrumb"),Page.CONFIRMATION}%>'/>

<to:headLine/>

<div class="r">
    <div class="main">
        <form:form action="<%= PageUrl.DASHBOARD %>" commandName="<%=PageCommand.CART%>" cssClass="form-with-tooltips-width">
            <div class="message-container confirmation-container csc-module">
                <h3 class="title"><spring:message code="thankYou.text"/></h3>

                <div class="content">
                    <p><spring:message code="${confirmationMessage}"/></p>
                </div><br/>
                <c:if test="${isAutoTopUpEnabledInTargetCard eq true}">
                <div class="content">
                    <p><spring:message code="${pageName}.autoTopUpMessage.text"/></p>
                </div>
                </c:if>
                
            </div>
            <p></p>

            <div class="oo-button-group-spaced">
                <to:secondaryButton id="cancel" buttonType="submit" targetAction="<%= PageParameterValue.CANCEL %>"/>
            </div>
        </form:form>
    </div>
    <tiles:insertAttribute name="myAccount"/>
</div>

