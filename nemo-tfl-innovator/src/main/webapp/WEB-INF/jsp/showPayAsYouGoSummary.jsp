<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>
<c:set var="payAsYouGoCredit" value="Pay as you go credit" />

<div >
	<to:head3 id="payAsYouGoCredit" headingCssClass="space-before-head"/>
	<c:forEach items="${cartCmd.cartDTO.cartItems}" var="cartItem" varStatus="status">
		<c:if test="${cartItem['class'].simpleName == 'PayAsYouGoItemDTO'}">
			<div>
	    		<to:label id="payAsYouGoCredit" />
	    	</div>
	    	<div id="payAsYouGoValue">
	    		<td><nemo:poundSterlingFormat amount="${cartItem.price}" /></td>
    		</div>
		</c:if>
	</c:forEach>
	<c:forEach items="${cartCmd.cartItemList}" var="cartItem" varStatus="status">
    	<c:if test="${cartItem.item eq payAsYouGoCredit}">
    		<div>
	    		<to:label id="payAsYouGoCredit" />
	    	</div>
	    	<div id="payAsYouGoValue">
	    		<td><nemo:poundSterlingFormat amount="${cartCmd.cartItemList[status.index].price}" /></td>
    		</div>
		</c:if>
	</c:forEach>
</div>
<div class="clear"></div>