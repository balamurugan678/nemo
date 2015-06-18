<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

<nemo-tfl:breadcrumbs pageNames='<%=new String[]{Page.GET_OYSTER_CARD}%>'/>
<div class="r" style="padding-top: 20px;">
    <form:form commandName="<%= PageCommand.CART_ITEM %>" action="<%=PageUrl.BUS_PASS%>" cssClass="form-with-tooltips">
        <div class="box borderless">
            <to:head2 id="busPassOptions"/>
            <form:errors cssClass="field-validation-error"/>
            <nemo-tfl:selectList id="startDate" path="startDate" selectList="${startDates}"
                                 mandatory="true" selectedValue="${cartItemCmd.startDate}" useManagedContentForMeanings="false"/>
            <form:errors path="startDate" cssClass="field-validation-error"/>
            <nemo-tfl:selectList id="emailReminder" path="emailReminder" selectList="${basketEmailReminders}"
                                 mandatory="true" selectedValue="${cartItemCmd.emailReminder}" showPlaceholder="false"/>
            <form:errors path="emailReminder" cssClass="field-validation-error"/>            
            <div class="form-with-tooltips">
		   		<div class="button-container clearfix">
			    	<to:secondaryButton id="cancel" buttonType="submit" targetAction="<%= PageParameterValue.CANCEL %>"/>
			       	<to:primaryButton id="continue" buttonType="submit" targetAction="<%=PageParameterValue.ADD_TO_CART%>"/>
			    </div>
			 	<to:secondaryButton id="shoppingBasket" buttonType="submit" targetAction="<%= PageParameterValue.SHOPPING_BASKET %>"/>
			</div>
        </div>
    </form:form>
</div>