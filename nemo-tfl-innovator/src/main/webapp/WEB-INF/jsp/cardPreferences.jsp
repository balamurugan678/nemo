<%@include file="/WEB-INF/jspf/pageCommon.jspf"%>

<form:form id="cardPreferencesSelection" class="form-with-tooltips" action="<%=PageUrl.INV_CARD_PREFERENCES%>"
	commandName="<%=PageCommand.CARD_PREFERENCES%>">
	<div id="cardPreferences">
	   
		<to:hidden id="cardPreferencesId" />
		<to:header id="cardPreferences" />
		<br />
		<to:hidden id="cardId" />
		<to:label id="selectedCardNumber"/>
		<form:input id="selectedCardNumber" path="cardNumber"  disabled="true" value="${cardPreferencesCmd.cardNumber}"/>
		<nemo-tfl:selectList id="selectStation" path="stationId" selectList="${locations}" selectedValue="${cardPreferencesCmd.stationId}"
			useManagedContentForMeanings="false" />
		<form:errors path="stationId" cssClass="field-validation-error" />
		<nemo-tfl:selectList id="emailFrequency" path="emailFrequency" selectList="${statementEmailFrequencies}" mandatory="true"
			selectedValue="${cardPreferencesCmd.emailFrequency}"  showPlaceholder="false"/>
		<form:errors path="emailFrequency" cssClass="field-validation-error" />
		<nemo-tfl:radioButtonList id="attachmentType" path="attachmentType" selectList="${statementAttachmentTypes}" mandatory="true"
			selectedValue="${cardPreferencesCmd.attachmentType}" showHint="true" />
		<form:errors path="attachmentType" cssClass="field-validation-error" />
        <to:head2section id="securityNotice" showLink="false"/>
        <to:checkbox id="statementTermsAccepted" />
		<to:text id="emailAddress" value="${cardPreferencesCmd.emailAddress}" disabled="true" size="50"/>
	</div>
	<div class="clear"></div>
	<div id="toolbar">
		<div class="right">
			<to:primaryButton buttonType="submit" id="save" targetAction="<%=PageParameterValue.SAVE_CHANGES%>"></to:primaryButton>
		</div>

	</div>
</form:form>
<script type="text/javascript" >
  var message = '${message}';
</script>