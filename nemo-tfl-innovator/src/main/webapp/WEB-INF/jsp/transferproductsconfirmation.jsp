<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

<form:form commandName="<%=PageCommand.CART%>" cssClass="form-with-tooltips" action="<%=PageUrl.TRANSFER_CONFIRMATION%>">
		<div>
		<br/>
			<div class="content">
                   <p><spring:message code="transfers.update.successful.text"/></p>
            </div><br/>
            <c:if test="${isAutoTopUpEnabledInTargetCard eq true}">
            <div class="content">
                <p><spring:message code="${pageName}.autoTopUpMessage.text"/></p>
            </div>
            </c:if>
		</div>
		
		<div id="toolbar">
        <div class="left">

        </div>
        <div class="right">
            <to:primaryButton id="backToCustomerPage" buttonCssClass="rightalignbutton" buttonType="submit" targetAction="<%= PageParameterValue.BACK_TO_CUSTOMER_PAGE %>"/>
        </div>
    </div>
    
    </form:form>	