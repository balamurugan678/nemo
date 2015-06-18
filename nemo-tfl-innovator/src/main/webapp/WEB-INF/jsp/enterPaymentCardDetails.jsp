<%@ page import="com.novacroft.nemo.tfl.common.constant.ContentCode"%>
<%@include file="/WEB-INF/jspf/pageCommon.jspf"%>
<%@include file="/jspf/setUpJQueryValidatorPlugin.jspf"%>

<c:set var="toPayDisplayAmount">
	<nemo-tfl:poundSterlingFormat amount="${cartCmd.cartDTO.cyberSourceRequest.amount}"
		amountInPence="false" />
</c:set>

<c:set var="webCreditDisplayAmount">
	<nemo-tfl:poundSterlingFormat amount="${cartCmd.webCreditApplyAmount}"
		amountInPence="true" />
</c:set>

<div class="r">
	<form:form id="enterPaymentCardDetails" method="post"
		action="${paymentGatewayUrl}" commandName="<%= PageCommand.CART %>"
		cssClass="form-with-tooltips">

		<c:if test="${cartCmd.cartDTO.cyberSourceRequest.amount > 0.0}">
			<div>
				<spring:message code="<%= ContentCode.CONFIRM_PAYMENT_AMOUNT_USING_NEW_CARD.textCode() %>" arguments="${toPayDisplayAmount}" />
			</div>
		</c:if>
		
		<c:if test="${cartCmd.webCreditApplyAmount > 0.0}">
			<spring:message
				code="<%= ContentCode.CONFIRM_PAYMENT_AMOUNT_USING_WEB_CREDIT.textCode() %>"
				arguments="${webCreditDisplayAmount}" />
			<c:out value=" " />
		</c:if>

		<to:head2 id="cardDetails" />
		<nemo-tfl:selectList id="card_type" path="card_type"
			selectList="${paymentCardTypes}" mandatory="true" showPlaceholder="false" />
		<to:namedTextInput id="card_number" mandatory="true" size="23" maxlength="23"
			additionalAttributes="autocomplete=\"off\"" />
		<to:namedTextInput id="card_expiry_date" mandatory="true" size="7"
			maxlength="7" additionalAttributes="autocomplete=\"off\"" />
		<to:namedTextInput id="card_cvn" mandatory="true" size="4" maxlength="4"
			additionalAttributes="autocomplete=\"off\"" />
		<to:namedTextInput id="bill_to_forename" mandatory="true"
			value="${paymentDetailsCmd.billToFirstName}" />
		<to:namedTextInput id="bill_to_surname" mandatory="true"
			value="${paymentDetailsCmd.billToLastName}" />

		<to:head2 id="billingAddress" />
		<to:namedTextInput id="bill_to_email" value="${paymentDetailsCmd.billToEmail}" />
		<to:namedTextInput id="bill_to_address_line1"
			value="${paymentDetailsCmd.billToAddressLine1}" />
		<to:namedTextInput id="bill_to_address_line2"
			value="${paymentDetailsCmd.billToAddressLine2}" />
		<to:namedTextInput id="bill_to_address_city"
			value="${paymentDetailsCmd.billToAddressCity}" />
		<to:namedTextInput id="bill_to_address_postal_code"
			value="${paymentDetailsCmd.billToAddressPostalCode}" />
		<nemo-tfl:selectList id="bill_to_address_country"
			path="bill_to_address_country" selectList="${countries}" mandatory="false"
			showPlaceholder="true" useManagedContentForMeanings="false"
			selectedValue="${paymentDetailsCmd.billToAddressCountry}" />

		<input type="hidden" name="access_key"
			value="${cartCmd.cartDTO.cyberSourceRequest.accessKey}" />
		<input type="hidden" name="amount"
			value="${cartCmd.cartDTO.cyberSourceRequest.amount}" />
		<input type="hidden" name="currency"
			value="${cartCmd.cartDTO.cyberSourceRequest.currency}" />
		<input type="hidden" name="locale"
			value="${cartCmd.cartDTO.cyberSourceRequest.locale}" />
		<input type="hidden" name="profile_id"
			value="${cartCmd.cartDTO.cyberSourceRequest.profileId}" />
		<input type="hidden" name="reference_number"
			value="${cartCmd.cartDTO.cyberSourceRequest.referenceNumber}" />
		<input type="hidden" name="signature"
			value="${cartCmd.cartDTO.cyberSourceRequest.signature}" />
		<input type="hidden" name="signed_date_time"
			value="${cartCmd.cartDTO.cyberSourceRequest.signedDateTime}" />
		<input type="hidden" name="signed_field_names"
			value="${cartCmd.cartDTO.cyberSourceRequest.signedFieldNames}" />
		<input type="hidden" name="transaction_type"
			value="${cartCmd.cartDTO.cyberSourceRequest.transactionType}" />
		<input type="hidden" name="transaction_uuid"
			value="${cartCmd.cartDTO.cyberSourceRequest.transactionUuid}" />
		<input type="hidden" name="unsigned_field_names"
			value="${cartCmd.cartDTO.cyberSourceRequest.unsignedFieldNames}" />
		<input type="hidden" name="bill_to_address_state" value="" />
		<input type="hidden" name="payment_method"
			value="${cartCmd.cartDTO.cyberSourceRequest.paymentMethod}" />
		<input type="hidden" name="consumer_id"
			value="${cartCmd.cartDTO.cyberSourceRequest.consumerId}" />
		<input type="hidden" name="customer_cookies_accepted"
			value="${cartCmd.cartDTO.cyberSourceRequest.cookiesEnabledOnClient}" />
		<input type="hidden" name="customer_ip_address"
			value="${cartCmd.cartDTO.cyberSourceRequest.clientIpAddress}" />
		<input type="hidden" name="date_of_birth"
			value="${cartCmd.cartDTO.cyberSourceRequest.dateOfBirth}" />
		<input type="hidden" name="device_fingerprint_id"
			value="${cartCmd.cartDTO.cyberSourceRequest.deviceFingerPrint}" />
		<input type="hidden" name="override_custom_cancel_page"
			value="${cartCmd.cartDTO.cyberSourceRequest.overrideCustomCancelPage}" />
		<input type="hidden" name="override_custom_receipt_page"
			value="${cartCmd.cartDTO.cyberSourceRequest.overrideCustomReceiptPage}" />

		<div class="clear"></div>
		<div style="float:right; width: 100px;">
			<to:primaryButton id="proceedWithPayment" buttonType="submit" />
		</div>
	</form:form>
	<form:form id="cancel" action="<%= PageUrl.PAYMENT %>"
		style="float:left; width:100px; padding: 0px;">
		<div class="button-container clearfix">
			<to:secondaryButton id="cancel" buttonType="submit"
				targetAction="<%= PageParameterValue.CANCEL %>" />
		</div>
	</form:form>
</div>

<script src="ContentForJavaScript.htm" type="text/javascript"></script>
<script src="scripts/contentManagement.js" type="text/javascript"></script>
<script src="scripts/paymentDetailsValidation.js" type="text/javascript"></script>
