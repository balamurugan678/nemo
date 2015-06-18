<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>
<spring:message var="administrationFee" code="administrationFee.text"/>
<div >
    <to:head3 id="administrationFee" headingCssClass="space-before-head"/>
    <c:choose>
	    <c:when test="${cartCmd.administrationFeeValue ne null}">
	    
	    	<div>
	                <to:label id="administrationFee"/>
	            </div>
	            <div id="administrationFeeValue">
	                <nemo:poundSterlingFormat amount="${cartCmd.administrationFeeValue}" />
	            </div>
	    </c:when>
	<c:otherwise>
	    <c:forEach items="${cartCmd.cartItemList}" var="cartItem" varStatus="status">
	        <c:if test="${fn:contains(cartItem.item, administrationFee)}">
	            <div>
	                <to:label id="administrationFee"/>
	            </div>
	            <div id="administrationFeeValue">
	                <nemo:poundSterlingFormat amount="${cartCmd.cartItemList[status.index].price}" />
	            </div>
	        </c:if>
	    </c:forEach>
	 </c:otherwise>
	</c:choose>
    
    
</div>
<div class="clear"></div>