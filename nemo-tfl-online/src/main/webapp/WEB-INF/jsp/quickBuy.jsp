<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

<nemo-tfl:breadcrumbs pageNames='<%=new String[]{Page.DASHBOARD, Page.QUICK_BUY}%>'/>

<div class="r">
	<div>
	    <to:head2 id="renewProduct"/>
		<form:form action="<%= PageUrl.QUICK_BUY%>" commandName="<%= PageCommand.CART%>" cssClass="form-with-tooltips">
			<div class="box borderless">
				<nemo-tfl:selectList id="selectCard" path="cardId" selectList="${cards}" mandatory="true"
	                                 useManagedContentForMeanings="false" selectedValue="${cartCmd.cardId}"/>
	            <form:errors path="cardId" cssClass="field-validation-error"/>
	            <div class="button-container clearfix">
	            	<c:if test="${cartCmd.cartItemList.size() == 0}">
		                <to:secondaryButton id="cancel" buttonType="submit" targetAction="<%= PageParameterValue.CANCEL %>"/>
		            </c:if>
	                <to:primaryButton id="selectCard" buttonType="submit" targetAction="<%= PageParameterValue.SELECT_CARD %>"/>
					<to:hidden id="cardId">${cartCmd.cardId}</to:hidden>
	            </div>
            </div>
			<c:choose>
				<c:when test="${cartCmd.cartItemList.size() > 0 && cartDTO.cartItems.size()>0}">
					<table border="1" class="shoppingcart box borderless">
						<tr>
							<to:tableHeader id="item" />
							<to:tableHeader id="startDate" />
							<to:tableHeader id="endDate" />
							<to:tableHeader id="reminderDate" />
							<to:tableHeader id="price" />
						</tr>
						<c:forEach items="${cartDTO.cartItems}" var="cartItem" varStatus="status">
							<tr>
								<td>${cartItem.name}<br /> 
										<input type="hidden" id="lineNo" name="lineNo" value="${cartItem.id}" />
										<input type="hidden" name="cartItemList[${status.index}].id" value="${cartItem.id}" />
										<button id="delete${cartItem.id}"  type="submit" class="delete buttonastext" name="targetAction" value="delete">
											<spring:message code="delete.button.label" />
										</button>
								</td>
								<td>
								<to:hidden id="cartItemList[${status.index}].ticketType"/> 
								<to:hidden id="cartItemList[${status.index}].travelCardType"/> 
								<to:hidden id="cartItemList[${status.index}].passengerType"/> 
								<to:hidden id="cartItemList[${status.index}].discountType"/> 
								<nemo-tfl:selectList id="startDates${status.index}" path="cartItemList[${status.index}].startDate"
										selectList="${startDates[status.index]}" selectedValue="${cartCmd.cartItemList[status.index].startDate}"
										useManagedContentForMeanings="false" showPlaceholder="false" showLabel="false" />
								</td>
								<td><fmt:formatDate pattern="<%= DateConstant.SHORT_DATE_PATTERN %>" value="${cartItem.endDate}"/></td>
								<td>
								<nemo-tfl:selectList id="emailReminders${status.index}" path="cartItemList[${status.index}].emailReminder" selectList="${basketEmailReminders}"
                                 mandatory="true" selectedValue="${cartCmd.cartItemList[status.index].emailReminder}" showPlaceholder="false" showLabel="false"/>
            					</td>
								<td><nemo-tfl:poundSterlingFormat amount="${cartItem.price}" />
								</td>
							</tr>
						</c:forEach>
						<tr>
							<td colspan="2" align="right" class="bold"><to:label id="total" /></td>
							<td colspan="3" align="right"><nemo-tfl:poundSterlingFormat amount="${cartDTO.cartTotal}" /></td>
						</tr>
					</table>
					<div class="box borderless">
						<div class="button-container clearfix">
			            	<div class="button-container clearfix">
						    	<to:secondaryButton id="cancel" buttonType="submit" targetAction="<%= PageParameterValue.CANCEL %>"/>
						    	<c:if test="${cartDTO.cartTotal > 0}">
			                		<to:primaryButton id="continue" buttonType="submit" targetAction="<%= PageParameterValue.CONTINUE %>"/>
			                	</c:if>
						    </div>
					    </div>
					</div>
					<div class="box info-message borderless topalign">
						<to:paragraph id="endDateChangeInfo" />
					</div>
				</c:when>
				<c:when test="${cartCmd.cartItemList.size() == 0 && not empty cartCmd.cardId}">
					<div class="box borderless">
						<to:label id="noTravelcardsAvailable" showColon="false" />
					</div>
				</c:when>
				<c:when test="${cartDTO.cartItems.size()==0}">
					<div class="box borderless">
						<to:label id="noTravelcardsAvailableInCart" showColon="false" />
					</div>
				</c:when>
			</c:choose>
		</form:form>
	</div>
</div>
<script src="scripts/quickBuy.js"></script>
