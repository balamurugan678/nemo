<%@page import="com.novacroft.nemo.tfl.common.constant.ProductItemType"%>
<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

<div class="r" style="padding-top: 20px;">
        <div class="box borderless">
            <to:head2 id="busPassOptions"/>
            <form:errors cssClass="field-validation-error"/>
            <input type="hidden" id="travelCardTicketType" name="cartItemCmd.ticketType" value="<%= ProductItemType.BUS_PASS.databaseCode() %>" />
            
            <nemo-tfl:selectList id="startDate" path="cartItemCmd.startDate" selectList="${startDates}" mandatory="true" 
            	selectedValue="${cartCmd.cartItemCmd.startDate}" useManagedContentForMeanings="false"/>
            <form:errors path="cartItemCmd.startDate" cssClass="field-validation-error"/>
            
            <nemo-tfl:selectList id="emailReminder" path="cartItemCmd.emailReminder" selectList="${basketEmailReminders}" mandatory="true" 
            	selectedValue="${cartItemCmd.emailReminder}" showPlaceholder="false"/>
            <form:errors path="cartItemCmd.emailReminder" cssClass="field-validation-error"/>            
            
            <div class="form-with-tooltips" style="clear:both">
                <to:secondaryButton id="busPassContinue" buttonType="submit" targetAction="<%= PageParameterValue.ADD_BUS_PASS_ITEM_TO_CART %>"/>
            </div>
        </div>
</div>