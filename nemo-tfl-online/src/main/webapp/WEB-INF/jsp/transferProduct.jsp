<%@page import="com.novacroft.nemo.tfl.common.constant.ContentCode" %>
<%@include file="/WEB-INF/jspf/pageCommon.jspf"%>
<nemo-tfl:breadcrumbs
	pageNames='<%=new String[]{Page.DASHBOARD, Page.VIEW_OYSTER_CARD, Page.TRANSFER_PRODUCT}%>' />
<to:headLine />
<c:set var="sourceCardNumber" value="${cartCmd.sourceCardNumber}" />
<div class="r">
	<div class="main">
    	<div class="page-heading with-border">
    		<to:oysterCardImageAndCardNumber oysterCardNumber="${cartCmd.sourceCardNumber}"/>
        </div>
		<form:form action="<%=PageUrl.TRANSFER_PRODUCT%>" commandName="<%=PageCommand.CART%>" cssClass="form-with-tooltips">
			<c:choose>
				<c:when test="${cartCmd.sourceCardNotEligible}">
					<p>
					<spring:message code="<%= ContentCode.SOURCE_CARD_INELIGIBLE_TRANSFER_PRODUCT.textCode() %>" arguments="${sourceCardNumber}" />
					</p>
					<c:forEach items="${cartCmd.ruleBreaches}" var="ruleBreach">
						<spring:message code="${ruleBreach}"/><br/>
					</c:forEach>
					<spring:message code="<%= ContentCode.INELIGIBLE_SOURCE_CARD_CONTACT_TFL_MESSAGE.textCode() %>"/><br />
					<div class="button-container clearfix">
						<to:secondaryButton id="cancel" buttonType="submit"
							targetAction="<%= PageParameterValue.CANCEL %>" />
					</div>
				</c:when>
				<c:otherwise>
					<div class='form-control-wrap '>
						<c:choose>
							<c:when test="${empty cards.options}">
									<spring:message code="${pageName}.noTargetCardEligible.text"/>
									<spring:message code="<%= ContentCode.INELIGIBLE_SOURCE_CARD_CONTACT_TFL_MESSAGE.textCode() %>"/><br />
							</c:when>
							<c:otherwise>
							<p>
								<spring:message code="<%= ContentCode.ELIGIBLE_SOURCE_CARD_INFORMATION_MESSAGE.textCode() %>" arguments="${sourceCardNumber}" />
							</p>
									<nemo-tfl:selectList id="selectTargetCard" path="cardId"
										selectList="${cards}" mandatory="true"
										useManagedContentForMeanings="false" />
										<form:errors path="cardId" cssClass="field-validation-error" />
							</c:otherwise>
					</c:choose>
					</div>
					<div class='form-control-wrap '>
						<to:primaryButton id="newOysterCard" buttonType="submit"
							targetAction="<%= PageParameterValue.ADD_EXISTING_CARD_TO_ACCOUNT%>" />
					</div>
						<div class="oo-button-group-spaced">
								<to:secondaryButton id="cancel" buttonType="submit"
									targetAction="<%=PageParameterValue.CANCEL%>" />
								<to:primaryButton id="continue" buttonType="submit"
									targetAction="<%=PageParameterValue.CONTINUE%>" />
						</div>
				</c:otherwise>
			</c:choose>
		</form:form>
	</div>
	<tiles:insertAttribute name="myAccount"/>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		$(".box").css("width", "750px");
	});
</script>
