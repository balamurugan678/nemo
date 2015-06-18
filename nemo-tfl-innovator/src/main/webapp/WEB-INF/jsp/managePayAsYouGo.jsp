<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>
<%@ page import="com.novacroft.nemo.tfl.common.util.CartUtil" %>
<%@ page import="com.novacroft.nemo.tfl.common.transfer.ItemDTO" %>
<spring:message var="payAsYouGoCredit" code="payAsYouGoCredit.text"/>
<div >
    <to:head3 id="payAsYouGoCredit" headingCssClass="space-before-head"/>
    <c:forEach items="${cartDTO.cartItems}" var="cartItem" varStatus="status">
    
    	<% ItemDTO itemDTO = (ItemDTO) pageContext.getAttribute("cartItem"); %>
    	
    	<c:if test="<%=CartUtil.isItemDTOPayAsYouGoItemDTO(itemDTO)%>">
			<div>
                <to:label id="payAsYouGoCredit" mandatory="true" labelCssClass="payAsYouGoCredit" />
            </div>
            <div id="payAsYouGoValue">
                <to:text id="payAsYouGoValue" showLabel="false" htmlEscape="false" cssClass="payAsYouGoValue" maxlength="6"/>
            </div>
		</c:if>
	</c:forEach>
</div>
<div class="clear"></div>
<script src="scripts/managePayAsYouGo.js"></script>