<%@page import="com.novacroft.nemo.tfl.common.constant.ContentCode"%>
<%@include file="/WEB-INF/jspf/pageCommon.jspf"%>
<div>
	<c:choose>
		<c:when test="${cartCmd.sourceCardNotEligible}">
			<p>
				<spring:message
					code="<%= ContentCode.SOURCE_CARD_INELIGIBLE_TRANSFER_PRODUCT.textCode() %>"
					arguments="${cartCmd.sourceCardNumber}" />
			</p>
			<c:forEach items="${cartCmd.ruleBreaches}" var="ruleBreach">
				<td><spring:message code="${ruleBreach}" /><br /></td>
			</c:forEach>
			<spring:message code="<%= ContentCode.INELIGIBLE_SOURCE_CARD_CONTACT_TFL_MESSAGE.textCode() %>"/><br />
			<div class="button-container clearfix">
				<to:secondaryButton id="cancel" buttonType="submit"
					targetAction="<%=PageParameterValue.CANCEL%>" />
			</div>
		</c:when>
		<c:otherwise>
			<c:choose>
				<c:when test="${empty cards.options}">
					<spring:message code="${pageName}.noTargetCardEligible.text" />
					<spring:message code="<%= ContentCode.INELIGIBLE_SOURCE_CARD_CONTACT_TFL_MESSAGE.textCode() %>"/><br />
				</c:when>
				<c:otherwise>
				<p>
					<spring:message
					code="<%= ContentCode.ELIGIBLE_SOURCE_CARD_INFORMATION_MESSAGE.textCode() %>"
					arguments="${cartCmd.sourceCardNumber}" />
				</p>
					<nemo-tfl:selectList id="selectTargetCard" path="cardId"
						selectList="${cards}" mandatory="true"
						useManagedContentForMeanings="false" selectedValue="${cartCmd.cardId}"/>
					<form:errors path="cardId" cssClass="field-validation-error" />
					<tr></tr>
					<to:loadingIcon />
					<nemo-tfl:selectList id="selectStation" path="stationId"
						selectList="${stationSelectList}" mandatory="true"
						useManagedContentForMeanings="false"
						selectedValue="${cartCmd.stationId}" />
					<form:errors path="stationId" cssClass="field-validation-error" />
					<div class="clear"></div>
				</c:otherwise>
			</c:choose>
			<to:primaryButton id="newOysterCard" buttonType="submit" targetAction="<%= PageParameterValue.ADD_EXISTING_CARD_TO_ACCOUNT%>" />
			<div id="toolbar">
				<div class="left"></div>
				<div class="right">
					<to:primaryButton id="cancel" buttonCssClass="rightalignbutton"
						buttonType="submit" targetAction="<%=PageParameterValue.CANCEL%>" />
					<to:primaryButton id="transferProduct"
						buttonCssClass="rightalignbutton" buttonType="submit"
						targetAction="<%=PageParameterValue.CONTINUE%>" />
				</div>
			</div>
		</c:otherwise>
	</c:choose>
</div>
<div class="clear"></div>
<script type="text/javascript">
    var pageName =  "<%=Page.TRANSFER_PRODUCT%>";
	var contextPath = "${pageContext.request.contextPath}";
</script>
<script src="scripts/transferProduct.js"></script>