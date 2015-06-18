<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<nemo-tfl:breadcrumbs pageNames='<%=new String[]{Page.DASHBOARD, Page.RESETTLE_FAILED_AUTO_TOP_UP}%>'/>

<to:headLine />

<div class="r">
    <div class="main">
	    <form:form action="<%=PageUrl.RESETTLE_FAILED_AUTO_TOP_UP%>" commandName="<%=PageCommand.CART%>" cssClass="form-with-tooltips">
		    <spring:message code="ResettleFailedAutoTopUp.info.amount.message" arguments="${amount}" />
		    <div class="button-container clearfix .plain-button">
			    <to:secondaryButton id="cancel" buttonType="submit" 
		                        targetAction="<%= PageParameterValue.CANCEL%>"/>
			    <to:primaryButton id="resettle" buttonType="submit"
			                    targetAction="<%= PageParameterValue.RESETTLE %>"/>
		    </div>
	    </form:form>
    </div>
    <tiles:insertAttribute name="myAccount"/>
</div>