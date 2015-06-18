<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<nemo-tfl:breadcrumbs pageNames='<%=new String[]{Page.DASHBOARD, Page.LOST_OR_STOLEN_CARD, Page.LOST_CARD_REFUND_SUMMARY, Page.CONFIRM_LOST_CARD_REFUND}%>'/>

<to:headLine/>

<div class="r">
    <div class="main">
	    <form:form commandName="<%= PageCommand.CARD %>" cssClass="oo-responsive-form " action="<%= PageUrl.LOST_CARD_REFUND %>" method="POST">
	         <div class="message-container confirmation-container csc-module">
	             <div class="content">
	                 <p><spring:message code="${pageName}.firstLine.text"/></p>
	                 <p><spring:message code="${pageName}.secondLine.text"/></p>
	             </div>
	         </div>
	         
	         
	         <input type="hidden" name="cardNumber" value="${cartCmd.cardNumber}"/>
	         <input type="hidden" name="hotListReasonId" value="${cartCmd.hotListReasonId}"/> 
	          
	         <div class="oo-button-group-spaced">
	         	<to:primaryButton buttonType="submit" id="confirm" targetAction="<%= PageParameterValue.CONFIRM %>"></to:primaryButton>                  
	         	<to:secondaryButton buttonType="submit" id="back" targetAction="<%= PageParameterValue.CANCEL %>"></</to:secondaryButton>
	         </div>	        
	                           
	    </form:form>   
	</div> 
    <tiles:insertAttribute name="myAccount"/>
</div>

