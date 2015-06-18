<%@include file="/WEB-INF/jspf/pageCommon.jspf"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page import="com.novacroft.nemo.tfl.common.constant.ContentCode" %>


<nemo-tfl:breadcrumbs
	pageNames='<%=new String[]{Page.DASHBOARD, Page.VIEW_OYSTER_CARD, Page.MANAGE_AUTO_TOP_UP,Page.ACTIVATE_CHANGES_TO_AUTO_TOP_UP}%>' />
<to:areaheader title="heading" />

<div class="r">
	<div class="main">
		<div class="oo-module">
			<p>
				<spring:message code="CollectPurchase.upper.text" />
			</p>

			<p>
				<spring:message code="CollectPurchase.tickedList.heading" />
			</p>
			<ul class="ticked-list">
				<li><span class="icon tick-brushstroke-icon hide-text">tick</span>
					<span class="content"><spring:message
							code="CollectPurchase.tickedList.itemOne.text" /></span></li>
				<li><span class="icon tick-brushstroke-icon hide-text">tick</span>
					<span class="content"><spring:message
							code="CollectPurchase.tickedList.itemTwo.text" /></span></li>
			</ul>
			<img alt="gates" src="tfl-ux/assets/oyster/Gates.png"
				class="activation-image gates csc-module" /> <img alt="validators"
				src="tfl-ux/assets/oyster/Validators.png"
				class="activation-image validators csc-module" />

			<p class="clear">
				<spring:message code="CollectPurchase.crossedList.heading" />
			</p>
			<ul class="crossed-list">
				<li><span class="icon cross-brushstroke-icon hide-text">cross</span>
					<span class="content"><spring:message
							code="CollectPurchase.crossedList.itemOne.text" /></span></li>
				<li><span class="icon cross-brushstroke-icon hide-text">cross</span>
					<span class="content"><spring:message
							code="CollectPurchase.crossedList.itemTwo.text" /></span></li>
			</ul>
			<form:form commandName="<%=PageCommand.MANAGE_CARD %>"
				cssClass="oo-responsive-form">
				<div class='form-control-wrap '>
					<!--contains the label, input and error messaging-->
					<nemo-tfl:selectList id="selectStation" path="stationId"
						selectList="${locations}" mandatory="true"
						selectedValue="${manageCardCmd.stationId}"
						useManagedContentForMeanings="false" />
					<form:errors path="stationId" cssClass="field-validation-error" />

				</div>
				<div class="message-container instructional-container csc-module">
					<h3 class="title">
						<spring:message code="CollectPurchase.instruction.heading"
							arguments="Card" />
					</h3>

					<div class="content">
						<p>
							<spring:message code="<%= ContentCode.COLLECTPURCHASE_INSTRUCTION.textCode() %>" arguments="${manageCardCmd.paymentMethod}"/>
							${manageCardCmd.stationId} <strong class="new-line-content">${manageCardCmd.startDateforAutoTopUpCardActivate}</strong>
							<spring:message code="and.text" />
							<strong>${manageCardCmd.endDateforAutoTopUpCardActivate}</strong>
						</p>
					</div>
				</div>
				<to:hidden id="StartDateforAutoTopUpCardActivate"></to:hidden>
				<to:hidden id="endDateforAutoTopUpCardActivate"></to:hidden>
				<to:hidden id="cardNumber"></to:hidden>
				<to:hidden id="paymentCardID"></to:hidden>
				<to:hidden id="orderNumber"></to:hidden>
				<to:hidden id="autoTopUpState"></to:hidden>
				<to:hidden id="autoTopUpActivity"></to:hidden>

				<div class="oo-button-group-spaced">
					<to:primaryButton buttonType="submit" id="continue" targetAction="<%=PageParameterValue.ACTIVATE_AUTO_TOP_UP %>"></to:primaryButton>
					<to:secondaryButton id="cancel" buttonType="submit" targetAction="<%= PageParameterValue.MANAGE_AUTO_TOP_UP %>" />
				</div>
			</form:form>
		</div>
	</div>
	<tiles:insertAttribute name="myAccount" />
</div>

