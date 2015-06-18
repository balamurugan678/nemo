<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>
<spring:message var="travelCard" code="travelCard.text"/>
<spring:message var="busPass" code="busPass.text"/>

<div>
	<to:head3 id="travelCards" headingCssClass="space-before-head"/>
	<div id="travelCardSection">
		<c:set var="travelCardItemCount" value="0" />
		<c:forEach items="${cartCmd.cartDTO.cartItems}" var="cartItem" varStatus="status">
			<c:if test="${cartItem['class'].simpleName == 'ProductItemDTO'}">
				<c:set var="travelCardItemCount" value="${travelCardItemCount + 1}" />
			</c:if>
		</c:forEach>
		<c:choose>
			<c:when test="${travelCardItemCount gt 0}">
				<table id="travelCardsTable">
					<thead>
		        		<tr>
		        		    <to:tableHeader id="item"/>
		            		<to:tableHeader id="startDate"/>
		            		<to:tableHeader id="endDate"/>
		            		<to:tableHeader id="refundCalculationBasis"/>
		            		<to:tableHeader id="price"/>
		            		<to:tableHeader id="refund.refundAmount"/>
		        		</tr>
		    		</thead>
		    		<tbody>
		        		<c:forEach items="${cartCmd.cartDTO.cartItems}" var="cartItem" varStatus="status">
		        			<c:if test="${cartItem['class'].simpleName == 'ProductItemDTO'}">
		        				<c:set var="cartIndex" value="items[${status.index}]" />
			            		<tr>
									<td>${cartItem.name}</td>
									<td><fmt:formatDate pattern="<%= DateConstant.SHORT_DATE_PATTERN %>" value="${cartItem.startDate}"/></td>
									<td><fmt:formatDate pattern="<%= DateConstant.SHORT_DATE_PATTERN %>" value="${cartItem.endDate}"/></td>
									<td>${cartItem.refundCalculationBasis}</td>
									<td><nemo-tfl:poundSterlingFormat amount="${cartItem.price}" /></td>
									<td><nemo-tfl:poundSterlingFormat amount="${cartItem.refund.refundAmount}" /></td>
								</tr>
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

