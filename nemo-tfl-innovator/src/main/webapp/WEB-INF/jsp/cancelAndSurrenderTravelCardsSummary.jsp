<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>
<spring:message var="oddPeriodTicket" code="oddPeriodTicket.text"/>
<spring:message var="travelCard" code="travelCard.text"/>
<spring:message var="busPass" code="busPass.text"/>

<div>
	<to:head3 id="travelCards" headingCssClass="space-before-head"/>
	<div id="travelCardSection">
		<c:set var="travelCardCount" value="0" />
		<c:forEach items="${cartCmd.cartDTO.cartItems}" var="cartItem" varStatus="status">
			<c:if test="${fn:contains(cartItem.name, travelCard) or fn:contains(cartItem.name, busPass) or fn:contains(cartItem.name, oddPeriodTicket)}">
				<c:set var="travelCardCount" value="${travelCardCount + 1}" />
			</c:if>
		</c:forEach>
		<c:choose>
			<c:when test="${travelCardCount gt 0}">
				<table id="travelCardsTable">
					<thead>
		        		<tr>
		            		<to:tableHeader id="item"/>
		            		<to:tableHeader id="startDate"/>
		            		<to:tableHeader id="endDate"/>
		            		<to:tableHeader id="refundCalculationBasis"/>
		            		<to:tableHeader id="additionalInformation"/>
		            		<to:tableHeader id="estimatedRefundAmount"/>
		        		</tr>
		    		</thead>
		    		<tbody>
		        		<c:forEach items="${cartCmd.cartDTO.cartItems}" var="cartItem" varStatus="status">
		            		<c:set var="cartIndex" value="cartItemList[${status.index}]" />
		            		<tr>
		                		<c:if test="${fn:contains(cartItem.name, travelCard) or fn:contains(cartItem.name, busPass) or fn:contains(cartItem.name, oddPeriodTicket)}">
		                			<td>
		                				${cartItem.name}
		                    		</td>
		                    		<td><fmt:formatDate pattern="<%= DateConstant.SHORT_DATE_PATTERN %>" value="${cartItem.startDate}"/></td>
		                       		<td><fmt:formatDate pattern="<%= DateConstant.SHORT_DATE_PATTERN %>" value="${cartItem.endDate}"/></td>
		                       		<td>${cartCmd.cartDTO.cartItems[status.index].refundCalculationBasis}</td>
		                       		<td>
		                    			<c:if test="${not empty cartItem.magneticTicketNumber}"><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''} MagneticTicketNumberLabel"/>${cartItem.magneticTicketNumber}<br></c:if>
			                    		<c:if test="${cartItem.ticketUnused}"><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}unusedTicketMessage"/><br></c:if>
			                    		<c:if test="${cartItem.ticketBackdated}"><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}refundBackdatedMessage"/><br></c:if>
			                    		<c:if test="${cartItem.backdatedWarning}">	<spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}BackdateCeilingWarning"/><br></c:if>
			                    		<c:if test="${cartItem.deceasedCustomer}"><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}refundForDeceasedMessage"/></c:if>
		                    		</td>
		                    		
		                    		<td>
			                       		<c:if test="${not empty cartItem.refund}"> 
			                       			<nemo:poundSterlingFormat amount="${cartItem.refund.refundAmount}" />
			                       		</c:if>
		                       		</td>
		                       		<c:if test="${not empty cartItem.relatedItem}">
					                    <c:set var="traded" value="${cartItem.relatedItem}"></c:set>
					                	<tr>
					                			<td>
					                				${traded.name}
					                				<br/>
					                				${traded.refundCalculationBasis}
					                				
					                    		</td>
					                    		<td><fmt:formatDate pattern="<%= DateConstant.SHORT_DATE_PATTERN %>" value="${traded.startDate}"/></td>
					                    		<td><fmt:formatDate pattern="<%= DateConstant.SHORT_DATE_PATTERN %>" value="${traded.endDate}"/></td>
					                    		<td colspan=3>
					                    			<spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}summaryTradedTicketMessage"/>
					                    		</td>
					                	</tr>
		                 			</c:if>
		                       		
		                 		</c:if>
		                	</tr>
		        		</c:forEach>
		    		</tbody>
		    	</table>
			</c:when>
			<c:otherwise>
				<to:label id="noTravelcardsAvailable" showColon="false"/>
			</c:otherwise>
		</c:choose>
	</div>
</div>
<div class="clear"></div>