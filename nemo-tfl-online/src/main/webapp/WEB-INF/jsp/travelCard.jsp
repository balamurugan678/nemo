<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

<nemo-tfl:breadcrumbs pageNames='<%=new String[]{Page.GET_OYSTER_CARD}%>'/>
<div class="r no-js" style="padding-top: 20px;">
    <form:form commandName="<%= PageCommand.CART_ITEM %>" action="<%=PageUrl.TRAVEL_CARD%>"
               cssClass="form-with-tooltips">
        <div class="box borderless">
            <to:head2 id="travelCardOptions"/>
            <form:errors cssClass="field-validation-error"/>
            <nemo-tfl:selectList id="travelCardType" path="travelCardType" selectList="${travelCardTypes}"
                                 mandatory="true" selectedValue="${cartItemCmd.travelCardType}"/>
            <form:errors path="travelCardType" cssClass="field-validation-error"/>
            <nemo-tfl:selectList id="startZone" path="startZone" selectList="${travelCardZones}"
                                 mandatory="true" selectedValue="${cartItemCmd.startZone}"/>
            <form:errors path="startZone" cssClass="field-validation-error"/>
            <nemo-tfl:selectList id="endZone" path="endZone" selectList="${travelCardZones}"
                                 mandatory="true" selectedValue="${cartItemCmd.endZone}"/>
            <form:errors path="endZone" cssClass="field-validation-error"/>
            <nemo-tfl:selectList id="startDate" path="startDate" selectList="${startDates}"
                                 mandatory="true" selectedValue="${cartItemCmd.startDate}"
                                 useManagedContentForMeanings="false"/>
            <form:errors path="startDate" cssClass="field-validation-error"/>

			<div id="customDateContainer" class="custom-date-range without-js">
				<div class='input-with-calendar-container'>
					<div
						class='form-control-wrap text-input with-message expandable-box'>
						<a
							class='form-element-accordian-control always-visible no-controls'>
							<span class="icon calendar-icon hide-text">Calendar</span>
						</a>
						
						<to:label id="endDate" labelCssClass="label-in-accordian"
                                                          showColon="true"/>
                           <div class="form-control">
                             <div style="position: relative;" class="remove-content-container empty float-none">
		                           <input id="TravelCard.endDate" name="endDate" class="shaded-input oo-date-picker" type="text" value="" autocomplete="off">
		                           <a href="javascript:void(0)" style="position: absolute; cursor: pointer; display: none; top: 10px; right: 5px;" class="remove-content hide-text">Clear search</a>
		                     </div>
                           </div>
                           <form:errors path="endDate"
                                        cssClass="field-validation-error"/>
						
						<div class="start-hidden">
							<div id="custom-travel-statement-end-date_calendar"
								class="fc-calendar-container"></div>
						</div>
					</div>
				</div>
			</div>

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

<script type="text/javascript">
	var contextPath="${pageContext.request.contextPath}";
    var pageName = "${pageName}";
    var shortDatePattern = '<%=DateConstant.SHORT_DATE_PATTERN_PARTIAL_YEAR%>';
</script>
<script src="scripts/travelcard.js"></script>
<script type="text/javascript">
	$(document).ready(
			function() {
				TravelCard.initialisePage();
				$(".form-control-wrap").css("width", "90%");
				$(".form-control-wrap .form-element-accordian-control .icon")
						.css("top", "20px");
				$(".form-control-wrap .form-element-accordian-control .icon")
						.css("right", "-550px");
			});
</script>