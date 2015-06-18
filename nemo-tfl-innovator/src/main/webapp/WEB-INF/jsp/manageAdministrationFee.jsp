<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>
<%@ page import="com.novacroft.nemo.tfl.common.util.CartUtil" %>
<%@ page import="com.novacroft.nemo.tfl.common.transfer.ItemDTO" %>
<spring:message var="administrationFee" code="administrationFee.text"/>


<div>
    <c:forEach items="${cartDTO.cartItems}" var="cartItem" varStatus="status">
    
    	<% ItemDTO itemDTO = (ItemDTO) pageContext.getAttribute("cartItem"); %>
    	
    	<c:if test="<%=CartUtil.isItemDTOAdministrationFeeItemDTO(itemDTO)%>">
			<to:head3 id="administrationFee" headingCssClass="space-before-head"/>
			<div>
                <to:label id="administrationFee" mandatory="true"/>
            </div>
            <div id="administrationFeeValue">
                <to:text id="administrationFeeValue" showLabel="false" htmlEscape="false" cssClass="administrationFeeValue" maxlength="6"/>
            </div>
        </c:if>
        
	</c:forEach>
</div>
<div class="clear"></div>
<script src="scripts/manageAdministrationFee.js"></script>