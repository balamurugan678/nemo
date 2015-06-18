
<%@ include file="/WEB-INF/jspf/pageCommon.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<form:form id="fulfilment" class="form-with-tooltips"
	commandName="<%=PageCommand.FULFILMENT%>">

	<div>
		<to:head3 id="queueStatus" headingCssClass="space-before-head" />
		<br>
		<to:head4 id="queueChoose" />
		<br>
	</div>
	<br>
	<div>
		<table class="default">
			<tbody>
				<tr>
					<td class="tag-left-column"><to:label id="queuePending" /></td>
					<c:set var="fulfilPendingQueueCount" value="${fulfilmentCmd.fulfilmentPendingQueueCount}" />
					<td class="tag-middle-column">${fulfilPendingQueueCount}</td>
					<c:choose>
						<c:when test="${fulfilPendingQueueCount gt 0}">
							<td><to:button id="queueProcess" buttonCssClass="" buttonType="submit" targetAction="<%=PageParameterValue.FULFILMENT_PENDING_QUEUE%>" /></td>
						</c:when>
						<c:otherwise>
							<td><to:button id="queueProcess" buttonCssClass="" buttonType="submit" buttonDisable=""/></td>
						</c:otherwise>
					</c:choose>
				</tr>
				<tr>
					<td class="tag-left-column"><to:label id="payasyougoPending" /></td>
					<c:set var="payAsYouGoPendingQueueCount" value="${fulfilmentCmd.payAsYouGoPendingQueueCount}" />
					<td class="tag-middle-column">${payAsYouGoPendingQueueCount}</td>
					<c:choose>
						<c:when test="${payAsYouGoPendingQueueCount gt 0}">
							<td><to:button id="queueProcess" buttonCssClass="" buttonType="submit" /></td>
						</c:when>
						<c:otherwise>
							<td><to:button id="queueProcess" buttonCssClass="" buttonType="submit" buttonDisable=""/></td>
						</c:otherwise>
					</c:choose>
				</tr>
				<tr>
					<td class="tag-left-column"><to:label id="autotopupOrderPending" /></td>
					<c:set var="autoTopUpPendingQueueCount" value="${fulfilmentCmd.autoTopUpPendingQueueCount}" />
					<td class="tag-middle-column">${autoTopUpPendingQueueCount}</td>
					<c:choose>
						<c:when test="${autoTopUpPendingQueueCount gt 0}">
							<td><to:button id="queueProcess" buttonCssClass="" buttonType="submit" /></td>
						</c:when>
						<c:otherwise>
							<td><to:button id="queueProcess" buttonCssClass="" buttonType="submit" buttonDisable=""/></td>
						</c:otherwise>
					</c:choose>
				</tr>
				<tr>
					<td class="tag-left-column"><to:label id="replacementCardPending" /></td>
					<c:set var="replacementCardPendingQueueCount" value="${fulfilmentCmd.replacementCardPendingQueueCount}" />
					<td class="tag-middle-column">${replacementCardPendingQueueCount}</td>
					<c:choose>
						<c:when test="${replacementCardPendingQueueCount gt 0}">
							<td><to:button id="queueProcess" buttonCssClass="" buttonType="submit" /></td>
						</c:when>
						<c:otherwise>
							<td><to:button id="queueProcess" buttonCssClass="" buttonType="submit" buttonDisable=""/></td>
						</c:otherwise>
					</c:choose>
				</tr>
				<tr>
					<td class="tag-left-column"><to:label id="autotopupReplacementOrderPending" /></td>
					<c:set var="autoTopUpReplacementCardPendingQueueCount" value="${fulfilmentCmd.autoTopUpReplacementCardPendingQueueCount}" />
					<td class="tag-middle-column">${autoTopUpReplacementCardPendingQueueCount}</td>
					<c:choose>
						<c:when test="${autoTopUpReplacementCardPendingQueueCount gt 0}">
							<td><to:button id="queueProcess" buttonCssClass="" buttonType="submit" /></td>
						</c:when>
						<c:otherwise>
							<td><to:button id="queueProcess" buttonCssClass="" buttonType="submit" buttonDisable=""/></td>
						</c:otherwise>
					</c:choose>
				</tr>
				<tr>
					<td class="tag-left-column"><to:label id="goldCardPending" /></td>
					<c:set var="goldCardPendingQueueCount" value="${fulfilmentCmd.goldCardPendingQueueCount}" />
					<td class="tag-middle-column">${goldCardPendingQueueCount}</td>
					<c:choose>
						<c:when test="${goldCardPendingQueueCount gt 0}">
							<td><to:button id="queueProcess" buttonCssClass="" buttonType="submit" /></td>
						</c:when>
						<c:otherwise>
							<td><to:button id="queueProcess" buttonCssClass="" buttonType="submit" buttonDisable=""/></td>
						</c:otherwise>
					</c:choose>
				</tr>

			</tbody>
		</table>

	</div>

</form:form>