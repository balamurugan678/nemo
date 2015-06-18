<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>
<%@ page import="com.novacroft.nemo.tfl.common.util.CartUtil" %>
<%@ page import="com.novacroft.nemo.tfl.common.transfer.ItemDTO" %>
<spring:message var="travelCard" code="travelCard.text"/>
<spring:message var="busPass" code="busPass.text"/>
<spring:message var="oddPeriodTicket" code="oddPeriodTicket.text"/>

<c:set var="travelCardItemCount" value="0" />
<c:forEach items="${cartDTO.cartItems}" var="cartItem" varStatus="status">
	<% ItemDTO itemDTO = (ItemDTO) pageContext.getAttribute("cartItem"); %>
    	
	<c:if test="<%=CartUtil.isItemDTOProductItemDTO(itemDTO)%>">
		<c:set var="travelCardItemCount" value="${travelCardItemCount + 1}" />
	</c:if>
</c:forEach>
<c:choose>
	<c:when test="${travelCardItemCount gt 0}">
		<table id="travelCardsTable">
			<thead>
        		<tr>
        		    <to:tableHeader id="delete"/>
            		<to:tableHeader id="item"/>
            		<to:tableHeader id="startDate"/>
            		<to:tableHeader id="endDate"/>
            		<to:tableHeader id="refundCalculationBasis"/>
            		<to:tableHeader id="additional"/>
            		<to:tableHeader id="price"/>
            		<to:tableHeader id="refund.refundAmount"/>
        		</tr>
    		</thead>
    		<tbody>
        		<c:forEach items="${cartDTO.cartItems}" var="cartItem" varStatus="status">
        			<% ItemDTO itemDTO = (ItemDTO) pageContext.getAttribute("cartItem"); %>
    	
    				<c:if test="<%=CartUtil.isItemDTOProductItemDTO(itemDTO)%>">
        				<c:set var="cartIndex" value="cartItems[${status.index}]" />
        				<c:if test="${empty cartItem.tradedDate}">
	            		<tr>
							<td>
								<to:button id="delete" secondaryId="deleteTravelCard${cartItem.id}" buttonCssClass="deleteTravelCard buttonastext" buttonType="submit" targetAction="<%= PageParameterValue.DELETE_TRAVEL_CARD %>" />
								<input id="cartItemId" name="cartItemId" value="${cartItem.id}" type="hidden" />
							<c:if test="${empty cartItem.relatedItem}">
									<%@include file = "showTradedTicket.jsp" %>
	                			</c:if>	
							</td>
							<td>${cartItem.name}</td>
							<td><fmt:formatDate pattern="<%= DateConstant.SHORT_DATE_PATTERN %>" value="${cartItem.startDate}"/></td>
							<td><fmt:formatDate pattern="<%= DateConstant.SHORT_DATE_PATTERN %>" value="${cartItem.endDate}"/></td>
							<td>
								<nemo-tfl:selectList id="refundCalculationBasis${cartItem.id}" cssClass="refundCalculationBasis" path="cartDTO.cartItems[${status.index}].refundCalculationBasis" selectList="${refundCalculationBasis}" mandatory="true" selectedValue="${cartItem.refundCalculationBasis}" showLabel="false" />
							</td>
							<td>
                    			<c:if test="${not empty cartItem.magneticTicketNumber}"><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}MagneticTicketNumberLabel"/>${cartItem.magneticTicketNumber}<br></c:if>
                    			<c:if test="${cartItem.ticketUnused}"><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}unusedTicketMessage"/><br></c:if>
                    			<c:if test="${cartItem.ticketBackdated}"><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}refundBackdatedMessage"/><br></c:if>
                    			<c:if test="${cartItem.backdatedWarning}">	<spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}BackdateCeilingWarning"/><br></c:if>
                    			<c:if test="${cartItem.deceasedCustomer}"><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}refundForDeceasedMessage"/></c:if>
                    		</td>
							<td><nemo-tfl:poundSterlingFormat amount="${cartItem.price}" /></td>
							<td>
								<nemo-tfl:poundSterlingFormat amount="${cartItem.refund.refundAmount}" /> <to:displayRefundMethod refund="${cartItem.refund}" ordinal="${status.index}" tradedTicket="${not empty cartItem.relatedItem}" />
							</td>
						</tr>
						</c:if>
						<c:if test="${not empty cartItem.relatedItem}">
							<c:set var="traded" value="${cartItem.relatedItem}"></c:set>
	                		<tr>
	                			<td></td>
	                			<td>${traded.name}<br/>${traded.refundCalculationBasis}</td>
	                			<td><fmt:formatDate pattern="<%= DateConstant.SHORT_DATE_PATTERN %>" value="${traded.startDate}"/></td>
		                    	<td><fmt:formatDate pattern="<%= DateConstant.SHORT_DATE_PATTERN %>" value="${traded.endDate}"/></td>
		                    	<td><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}tradedTicketNonAmendable"/></td>
		                    	<td><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}tradedTicketMessage"/></td>
		                    	<td></td>
		                 		<td></td>
	                		</tr>
	                	</c:if>
	                </c:if>
        		</c:forEach>
    		</tbody>
    	</table>
	</c:when>
	<c:otherwise>
		<to:label id="noTravelcardsAvailable" showColon="false"/>
	</c:otherwise>
</c:choose>
<script>
    var pageName = "${pageName}";
</script>
<script src="scripts/showTravelCards.js"></script>
<script src="scripts/displayRefundTag.js"></script>
<script src="scripts/addCancelAndSurrenderUnlistedProduct.js"></script>
<script src="scripts/cancelAndSurrender.js"></script>
<script src="scripts/showTradedTicket.js"></script>
