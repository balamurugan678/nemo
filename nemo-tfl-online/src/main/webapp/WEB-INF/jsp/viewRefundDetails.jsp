<%@include file="/WEB-INF/jspf/pageCommon.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<nemo-tfl:breadcrumbs
	pageNames='<%=new String[] { Page.DASHBOARD, Page.VIEW_REFUNDS,
					Page.VIEW_REFUND_DETAILS }%>' />
<to:headLine />
<div class="r">
	<div class="main">
		<form:form action="<%=PageUrl.VIEW_REFUND_DETAILS%>">
			<table>
				<tbody>
					<tr>
						<td class="right-aligned"><to:label id="nameAndAddress"/><br /></td>
						<td>${refund.name}<br />
							<p class="refund-addr">
								${refund.address.houseNameNumber}, ${refund.address.street}<br />
								${refund.address.town}<br /> ${refund.address.postcode}<br />
								${refund.address.country.name}
							</p>
						</td>
					</tr>
					<tr>
						<td class="right-aligned"><to:label id="refundNumber"/><br /></td>
						<td>${refund.orderNumber}<br /></td>
					</tr>
					<tr>
						<td class="right-aligned"><to:label id="oysterCardNumber"/><br /></td>
						<td>${refund.oysterCardNumber}<br /></td>
					</tr>
					<tr>
						<td class="right-aligned"><to:label id="refundDate"/><br /></td>
						<td><fmt:formatDate
								pattern="<%= DateConstant.SHORT_DATE_PATTERN %>"
								value="${refund.dateOfRefund}" /><br /></td>
					</tr>
					<tr>
						<td class="right-aligned"><to:label id="refundAmount"/><br /></td>
						<td><nemo-tfl:poundSterlingFormat amount="${-refund.amount}"
								amountInPence="true" /><br /></td>
					</tr>
					<tr>
						<td class="right-aligned"><to:label id="status"/><br /></td>
						<td>${refund.status}<br /></td>
					</tr>
					<tr>
						<td class="right-aligned"><to:label id="paymentMethod"/><br /></td>
						<td>${refund.paymentMethod}<br /></td>
					</tr>
					<tr>
						<td class="right-aligned"><to:label id="pickUpLocation"/><br /></td>
						<td>${refund.stationName}<br /></td>
					</tr>
					<tr>
						<td class="right-aligned"><to:label id="pickUpDate"/><br /></td>
						<td><fmt:formatDate
								pattern="<%= DateConstant.SHORT_DATE_PATTERN %>"
								value="${refund.dateOfRefund}" /><br /></td>
					</tr>
				</tbody>
			</table>
			<div class="borderless">
				<div class="button-container-width clearfix">
					<to:secondaryButton id="cancel" buttonType="submit"
						targetAction="<%=PageParameterValue.CANCEL%>" />
				</div>
			</div>
		</form:form>
	</div>
</div>