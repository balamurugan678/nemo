var customerIdField = "#" + pageName + "\\." + "customerId";
var usernameField = "#" + pageName + "\\." + "username";
var rowIds = [];
var rowCount = 1;
$(document)
		.ready(
				function() {
					$("#customersFound").hide();
					checkCustomerIdForButton('addUnattachedCard');
					checkCustomerIdForButton('addStandaloneGoodwill');
					$("[id^='refunds']")
							.change(
									function() {
										var refundId = this.id;
										var cardId = refundId
												.substring("refunds".length);
										if ($(this).val()) {
											var pageName = $(this).val();
											var idParameter = "id=" + cardId;
											var targetActionParameter = "targetAction=viewCartUsingCubic";
											var url = constructUrl(pageName,
													idParameter,
													targetActionParameter);

											var imageName = "oyster";
											var tabName = "failedcardrefund";
											if (checkRowCardNumberExists(this,
													"refunds")) {
												openLink(
														url,
														failedCardRefundCartHeader
																+ ": " + cardId,
														failedCardRefundCartHeader
																+ ": " + cardId,
														imageName, tabName);
											}

										}
									});

					$('#resettle').dataTable({
						"sPaginationType" : "full_numbers",
						"aoColumns" : [ null, null, {
							"bSortable" : false
						}, {
							"bSortable" : false
						}, {
							"bSortable" : false
						}, {
							"bSortable" : false
						}, {
							"bSortable" : false
						} ]
					});

					if (typeof (window.parent.showTab) != "undefined") {
						$("#cancel").hide();
					} else {
						$("#cancel").click(function() {
							history.back();
						});
					}

					// focus on errored area.
					$(".field-validation-error").each(
							function() {
								var fieldId = "#" + pageName + "\\."
										+ $(this).attr("id").split(".")[0];
								$(fieldId).focus();
							});

					$("#" + pageName + "\\.emailAddress").change(function() {
						checkEmail();
					});

					$("#" + pageName + "\\.firstName").change(function() {
						checkCustomer();
					});

					$("#save-submit").click(function() {
						return true;
					});
					
					if(showWebAccountDeactivationEnableFlag=="true"){
						showCustomerDeactivationAlertIfCustomerIsDeactivated();
					}
					showCustomerDeactivationReasonOtherIfOtherDeactivationReasonIsSelected();

					enableCustomerDeactivationReasonsOnlyWhenCustomerDeactivationIsChecked();
					checkOnloadIfCustomerDeactivationReasonsIsEnabledOnlyWhenCustomerDeactivationIsChecked();
					showDeactivationRulesOnSelectionOfDeactivationReasons();

					$("#customerDeactivationReason").on('change', function() {
						if (this.value == 'Other') {
							$("#customerDeactivationReasonOther").show();
						} else {
							$("#customerDeactivationReasonOther").hide();
						}
					});
				});

function enableCustomerDeactivationReasonsOnlyWhenCustomerDeactivationIsChecked() {
	$("[name=customerDeactivated]")
			.click(
					function() {
						if (!$("[name=customerDeactivated]").is(":checked")) {
							checkOnloadIfCustomerDeactivationReasonsIsEnabledOnlyWhenCustomerDeactivationIsChecked();
						} else {
							$("#customerDeactivationReason").removeAttr(
									'disabled');
							$("[name=customerDeactivationReasonOther]")
									.removeAttr('disabled');

						}
					});
}

function checkOnloadIfCustomerDeactivationReasonsIsEnabledOnlyWhenCustomerDeactivationIsChecked() {
	if (!$("[name=customerDeactivated]").is(":checked")) {
		$('#customerDeactivationReason').val(null);
		$("#customerDeactivationReason").attr('disabled', 'disabled');
		$("[name=customerDeactivationReasonOther]").attr('disabled',
				'disabled');
		$("[name=customerDeactivationReasonOther]").val(null);
		$('#customerDeactivationRules').text("");
	}
}

function showDeactivationRulesOnSelectionOfDeactivationReasons() {
	$("#customerDeactivationReason")
			.change(
					function() {
						if ($("#customerDeactivationReason")[0].selectedIndex > 0) {
							jQuery("#customerDeactivationRules")
									.text(
											getMessage(contentCode.ACCOUNT_DEACTIVATION_RULES_POPUP_TEXT));
						}

					});
}

function showCustomerDeactivationAlertIfCustomerIsDeactivated() {
	var customerAccountDeactivationMessageArray = [customerEmailAddress];
	var customerDeactivatedField = "#" + pageName + "\\."
			+ "customerDeactivated";
	var customerDeactivationReasonField = "#customerDeactivationReason";
	var customerDeactivationReasonOtherField = "#" + pageName + "\\."
			+ "customerDeactivationReasonOther";
	if ($(customerDeactivatedField + ":checked").val()
			&& $(customerDeactivationReasonField).val()
			&& ($(customerDeactivationReasonField).val() != 'Other' || ($(
					customerDeactivationReasonField).val() == 'Other' && $(
					customerDeactivationReasonOtherField).val()))) {
		alert(getMessage(contentCode.LINKED_WEB_ACCOUNT_IS_DEACTIVATED_ALERT_MESSAGE_POPUP_TEXT,customerAccountDeactivationMessageArray));
	}
}

function showCustomerDeactivationReasonOtherIfOtherDeactivationReasonIsSelected() {
	var customerDeactivatedField = "#" + pageName + "\\."
			+ "customerDeactivated";
	var customerDeactivationReasonField = "#customerDeactivationReason";
	var customerDeactivationReasonOtherDiv = "#customerDeactivationReasonOther";
	if ($(customerDeactivatedField + ":checked").val()
			&& $(customerDeactivationReasonField).val() == 'Other') {
		$(customerDeactivationReasonOtherDiv).show();
	} else {
		$(customerDeactivationReasonOtherDiv).hide();
	}
}

function checkRowCardNumberExists(item, name) {
	var rowId = $(item).attr('id').replace(/[^0-9]/g, '');
	var val = $("#cardNumber" + rowId).html();
	if (val === '' || rowId === '') {
		return false;
	}
	return true;
}

function openOysterWebaccount(response) {
	var token = response.substring(response.indexOf("SUCCESS?TOKEN=") + 14);
	var customerId = $(customerIdField).val();
	var username = $(usernameField).val();
	window
			.open(
					onlineAddress + "/Login.htm?agentId=SK11&customerId="
							+ customerId + "&token=" + token + "&username=" + username,
					'_blank',
					config = 'toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, directories=no, status=no');
}

function checkEmail() {
	var emailAddress = $("#" + pageName + "\\.emailAddress").val();
	$("input[type='text']").each(function() {
		$(this).prop("disabled", false);
	});
	$("#save-submit").prop("disabled", false).removeClass("ui-state-disabled");
	$("#customerLink").remove();
	$.post(sAddress + '/Customer/checkEmailAvailable.htm', {
		email : emailAddress
	}, function(response) {
		var customerId = (response === 'false');
		$("#availableEmail").attr("class", (customerId ? "tick" : "cross"));
		if (!customerId) {
			var customerLink = $("<button />").attr("id", "customerLink").attr(
					"value", sAddress + "/Customer.htm?id=" + response).attr(
					"class", "customerLink").attr("type", "button")
					.html("Open").click(
							function() {
								openLink($(this).val(), "Edit Customer:"
										+ customerId, "Edit Customer "
										+ customerId + ".", "oyster",
										"oysteredit");
							});
			$("#availableEmail").after(customerLink);
			$("input[type='text']").each(function() {
				$(this).prop("disabled", true);
			});
			$("#" + pageName + "\\.emailAddress").prop("disabled", false);
			$("#save-submit").prop("disabled", true).addClass("ui-state-disabled");
		}
	});
};

function constructUrl(pageName, firstParameter, secondParameter) {
	return pageName + ".htm" + "?" + firstParameter + "&" + secondParameter;
};
