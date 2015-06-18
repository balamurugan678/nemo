<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<nemo-tfl:breadcrumbs pageNames='<%=new String[]{Page.DASHBOARD, Page.TOP_UP_TICKET}%>'/>

<to:statusMessage messageCode="${statusMessage}"/>

<div class="r" style="padding-top: 20px;">
    <div class="main">
        <form:form commandName="<%=PageCommand.CART%>" action="<%=PageUrl.USER_CART%>"
                   cssClass="oo-responsive-form">
            <div class="box borderless bold">
                <to:label id="oysterCardNumber" value="${cartCmd.cardNumber}"/>
            </div>
            <jsp:include page="commonCart.jsp">
                <jsp:param name="action" value="<%=PageUrl.USER_CART%>"/>
            </jsp:include>
            <to:hidden id="cardId"></to:hidden>
        </form:form>
    </div>
    <tiles:insertAttribute name="myAccount"/>
</div>