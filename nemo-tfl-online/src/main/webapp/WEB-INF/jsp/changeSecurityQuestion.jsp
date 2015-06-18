<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<nemo-tfl:breadcrumbs pageNames='<%=new String[]{Page.DASHBOARD, Page.VIEW_OYSTER_CARD, Page.CHANGE_SECURITY_QUESTION}%>'/>
<to:areaheader title="heading"/>

<div class="r">
	<div class="main">
	    <div class="page-heading with-border">
	    	<to:oysterCardImageAndCardNumber oysterCardNumber="${securityQuestionCmd.cardNumber}"/>
        </div>
        <form:form action="<%= PageUrl.CHANGE_SECURITY_QUESTION %>" commandName="<%= PageCommand.SECURITY_QUESTION %>"
	               cssClass=".form-with-tooltips-width">
	        <to:hidden id="cardNumber"/>
    		<to:hidden id="cardId"/>
    		<div class="box borderless">
	            <div class='with-message oo-drop-down-info expandable-box '>
		               <a class="text always-visible first-column no-controls more-info-accordian text-only" href="javascript:void(0)"><spring:message code="ChangeCardSecurityQuestion.question.info.val"/>
		                   <span class="icon tooltip-icon info-i-icon"></span>
		               </a>
		               <div class="message-wrapper oo-tooltip-container inline-message info-message start-hidden with-indicator">
		                   <p class="tight-margin"><spring:message code="ChangeCardSecurityQuestion.answer.info.val"/></p>
		               </div>
	            </div>
	            <jsp:include page="commonSecurityQuestion.jsp"></jsp:include>
	            <hr>
	            <div class="button-container clearfix">
	                <to:secondaryButton id="cancel" buttonType="submit" targetAction="<%= PageParameterValue.CANCEL %>"/>
	                <to:primaryButton id="saveChange" buttonType="submit" targetAction="<%= PageParameterValue.SAVE_CHANGES %>"/>
	            </div>
	        </div>
	    </form:form>
    </div>
    <tiles:insertAttribute name="myAccount"/>
</div>

