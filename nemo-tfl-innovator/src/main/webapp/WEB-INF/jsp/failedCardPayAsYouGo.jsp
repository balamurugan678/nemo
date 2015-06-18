<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>
<c:set var="payAsYouGoCredit" value="Pay as you go credit" />

<div id="payAsYouGoErrorSection"></div>
<div >
	<to:head3 id="payAsYouGoCredit" headingCssClass="space-before-head"/>
	<div id="payAsYouGoSection">
		<c:forEach items="${cartCmd.cartItemList}" var="cartItem" varStatus="status">
			<c:if test="${cartItem.item eq payAsYouGoCredit}">
		   		<div>
		    		<to:label id="payAsYouGoCredit" mandatory="true"/>
		    	</div>
		    	<to:text id="cartItemList[${status.index}].price" showLabel="false" htmlEscape="false"/>
			</c:if>
		</c:forEach>
	</div>
</div>