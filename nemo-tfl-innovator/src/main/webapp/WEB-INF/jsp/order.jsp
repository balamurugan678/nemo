
<%@page import="com.novacroft.nemo.tfl.common.constant.PageParameter"%>
<%@include file="/WEB-INF/jspf/pageCommon.jspf"%>
<%@ page import="com.novacroft.nemo.tfl.innovator.controller.CardAdminController"%>


<script src="scripts/order.js"></script>
<script type="text/javascript">
    var sAddress = "${pageContext.request.contextPath}";
    var orderURL = "<%=PageUrl.INV_ORDER%>";
    var pageName = "<%=Page.INV_ORDER%>";
</script>
${session}
<form id="orderAdminSelection" class="form-with-tooltips" action="<%=PageUrl.CART%>" method="POST">

	<div id="orderAdmin">
		<to:header id="orderAdmin" />
		<br />
		<input type="hidden" name="customerId" value="${customerId}"/>
		<div class="clear">
		  <to:linkButton id="addOrder" linkParameters="customerId=${customerId}" />
		  <br /><br />
		</div>
		<div class="dataTable-container"> 
			<table class="grid clickable" cellpadding=0 cellspacing=1 id="orders">
				<thead>
					<tr>
						<th>Number</th>
						<th>Date</th>
						<th>Amount</th>
						<th>Status</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${orders}" var="order">
						<tr>
							<td>${order.orderNumber}</td>
							<td>${order.orderDate}</td>
							<td>${order.formattedTotalAmount}</td>
							<td>${order.status}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<to:loadingIcon />
		</div>
		<to:subHeader id="orderItems" />
		<div class="dataTable-container">
			<table class="grid clickable" cellpadding=0 cellspacing=1 id="items">
				<thead>
					<tr>
						<th>Name</th>
						<th>Price</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>

</form>

