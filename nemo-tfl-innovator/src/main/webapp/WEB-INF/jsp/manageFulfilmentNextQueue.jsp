<%@include file="/WEB-INF/jspf/pageCommon.jspf"%>
<%@ page import="com.novacroft.nemo.tfl.common.constant.OrderStatus" %>
<c:set var="fulfilmentPending" value="<%=OrderStatus.FULFILMENT_PENDING%>"/>
<c:set var="payAsYouGoFulfilmentPending" value="<%=OrderStatus.PAY_AS_YOU_GO_FULFILMENT_PENDING%>"/>
<c:set var="autoTopUpfulfilmentPending" value="<%=OrderStatus.AUTO_TOP_UP_PAYG_FULFILMENT_PENDING%>"/>
<c:set var="replacement" value="<%=OrderStatus.REPLACEMENT%>"/>
<c:set var="autoTopUpReplacementFulfilmentPending" value="<%=OrderStatus.AUTO_TOP_UP_REPLACEMENT_FULFILMENT_PENDING%>"/>
<c:set var="goldCardPending" value="<%=OrderStatus.GOLD_CARD_PENDING%>"/>
<div id="queue">
<form:form id="fulfilmentConfirm" class="form-with-tooltips"
		commandName="<%=PageCommand.FULFILMENT%>">
		<br />
			<table class="default">
				<tbody>
					<tr>
						<c:choose>
							<c:when
								test="${fulfilmentCmd.currentQueue eq fulfilmentPending.code()}">
								<td class="tag-left-column"><to:label id="queuePending" /></td>
							</c:when>
							<c:when
								test="${fulfilmentCmd.currentQueue eq payAsYouGoFulfilmentPending.code()}">
								<td class="tag-left-column"><to:label id="payasyougoPending" /></td>
							</c:when>
							<c:when
								test="${fulfilmentCmd.currentQueue eq autoTopUpfulfilmentPending.code()}">
								<td class="tag-left-column"><to:label id="autotopupOrderPending" /></td>
							</c:when>
							<c:when test="${fulfilmentCmd.currentQueue eq replacement.code()}">
								<td class="tag-left-column"><to:label id="replacementCardPending" /></td>
							</c:when>
							<c:when
								test="${fulfilmentCmd.currentQueue eq autoTopUpReplacementFulfilmentPending.code()}">
								<td class="tag-left-column"><to:label id="autotopupReplacementOrderPending" /></td>
							</c:when>
							<c:when
								test="${fulfilmentCmd.currentQueue eq goldCardPending.code()}">
								<td class="tag-left-column"><to:label id="goldCardPending" /></td>
							</c:when>
						</c:choose>
						<td class="tag-middle-column">${fulfilmentCmd.currentQueueCount}</td>
						<c:choose>
						<c:when test="${fulfilmentCmd.currentQueueCount gt 0}">
							<td><to:button id="queueProcess" buttonCssClass="" buttonType="submit" /></td>
						</c:when>
						<c:otherwise>
							<td><to:button id="queueProcess" buttonCssClass="" buttonType="submit" buttonDisable=""/></td>
						</c:otherwise>
					</c:choose>
					</tr>
				</tbody>
			</table>
		</form:form>
	</div>
<div class="clear"></div>
<script type="text/javascript">
    var pageName =  "<%=Page.FULFIL_ORDER_CONFIRMATION%>";
	var contextPath = "${pageContext.request.contextPath}";
</script>
<script src="scripts/fulfilOrderReceipt.js"></script>