<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>
<%@ page import="com.novacroft.nemo.tfl.common.util.CartUtil" %>
<%@ page import="com.novacroft.nemo.tfl.common.transfer.ItemDTO" %>
<c:set var="goodwill" value="Goodwill" />

<div>
	<to:head3 id="goodwill" headingCssClass="space-before-head"/>
	<div id="goodwillSection">
		<c:set var="goodwillPaymentItemCount" value="0" />
		<c:forEach items="${cartCmd.cartDTO.cartItems}" var="cartItem" varStatus="status">
		
			<% ItemDTO itemDTO = (ItemDTO) pageContext.getAttribute("cartItem"); %>
    	
    		<c:if test="<%=CartUtil.isItemDTOGoodwillPaymentItemDTO(itemDTO)%>">
				<c:set var="goodwillPaymentItemCount" value="${goodwillPaymentItemCount + 1}" />
			</c:if>
		</c:forEach>
		
		<c:if test="${goodwillPaymentItemCount gt 0}">
			<table id="goodwillTable">
				<thead>
					<tr>
						<to:tableHeader id="name" />
						<to:tableHeader id="price" />
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${cartCmd.cartDTO.cartItems}" var="cartItem" varStatus="status">
						<% ItemDTO itemDTO = (ItemDTO) pageContext.getAttribute("cartItem"); %>
    	
    					<c:if test="<%=CartUtil.isItemDTOGoodwillPaymentItemDTO(itemDTO)%>">
							<c:set var="cartIndex" value="items[${status.index}]" />
							<tr>
								<td>${cartItem.name}</td>
								<td><nemo:poundSterlingFormat amount="${cartItem.price}" /></td>
							</tr>
						</c:if>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
		<c:if test="${goodwillPaymentItemCount le 0}">
			<to:label id="noGoodwillAdded" showColon="false" />
		</c:if>
	</div>
</div>
<div class="clear"></div>
