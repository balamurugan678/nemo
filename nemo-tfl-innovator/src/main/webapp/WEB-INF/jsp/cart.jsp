<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>
<c:set var="autoTopUp" value="Auto Top-up" />
<c:set var="payAsYouGoCredit" value="Pay as you go credit" />
<c:set var="addMoreItems" value="false" />  

<div class="r">
	<div class="box borderless">
	    <to:head2 id="shoppingBasket"/>
	    <table class="shoppingCart" id="shoppingCart">
	        <thead>
		        <tr>
		            <to:tableHeader id="item"/>
		            <to:tableHeader id="startDate"/>
		            <to:tableHeader id="endDate"/>
		            <to:tableHeader id="reminderDate"/>
		            <to:tableHeader id="price"/>
		        </tr>
	        </thead>
	        <tbody>
		        <c:if test="${cartDTO.cartItems.size() > 0}">
		        	<input type="hidden" id="lineNo" name="lineNo" value="" />
		            <c:forEach items="${cartDTO.cartItems}" var="cartItem" varStatus="status">
            			<c:set var="itemSubclass" value="${cartItem['class'].simpleName}"/>
		                
		                <c:choose>
							<c:when test="${itemSubclass == 'AutoTopUpConfigurationItemDTO' 
							|| itemSubclass == 'CardRefundableDepositItemDTO' 
							|| itemSubclass == 'ShippingMethodItemDTO'}">
							</c:when>
		                	<c:otherwise>
								<tr>
									<td>${cartItem.name}<br /> 
										<input type="hidden" value="${cartItem.id}" />
										<button type="submit" class="buttonastext removeButton" name="targetAction" value="delete">
											<spring:message code="remove.button.label" />
										</button>
									</td>
									<td>
										<c:if test="${itemSubclass=='ProductItemDTO' || itemSubclass=='PayAsYouGoItemDTO'}">
										<fmt:formatDate pattern="<%= DateConstant.SHORT_DATE_PATTERN %>" value="${cartItem.startDate}"/>
										</c:if>
									</td>
									<c:choose>
										<c:when test="${itemSubclass == 'PayAsYouGoItemDTO'}">
											<td></td>
										</c:when>
										<c:otherwise>
											<td>
											<c:if test="${itemSubclass=='ProductItemDTO' || itemSubclass=='PayAsYouGoItemDTO'}">
											<fmt:formatDate pattern="<%= DateConstant.SHORT_DATE_PATTERN %>" value="${cartItem.endDate}"/>
											</c:if>
											</td>
										</c:otherwise>
									</c:choose>
									<td>
										<c:if test="${itemSubclass=='ProductItemDTO' || itemSubclass=='PayAsYouGoItemDTO' || itemSubclass=='TravelCardItemDTO'}">
										${cartItem.reminderDate}
										</c:if>
									</td>
									<td><nemo-tfl:poundSterlingFormat amount="${cartItem.price}" />
									</td>
								</tr>
							</c:otherwise>
		                </c:choose>
		            </c:forEach>
		            <c:set var="cartTotal" value="${cartDTO.cartTotal}"/>
		            <c:set var="refundableDepositAmount" value="${cartDTO.cardRefundableDepositAmount}"/>
		            <c:set var="shippingMethodAmount" value="${cartDTO.shippingMethodAmount}"/>
		            <c:if test="${not empty refundableDepositAmount}">
			            <tr>
			                <td colspan="4" align="left"><span class="bold"><to:label id="subTotal"/></span></td>
		                    <td><nemo-tfl:poundSterlingFormat amount="${cartTotal - refundableDepositAmount - shippingMethodAmount}"/></td>
		                </tr>
			            <tr>
			                <td colspan="4" align="left"><span class="bold"><to:label id="refundableCardDeposit"/></span></td>
		                    <td><nemo-tfl:poundSterlingFormat amount="${refundableDepositAmount}"/></td>
		                </tr>
			            <tr>
			                <td colspan="5" align="left">
			                    <nemo-tfl:radioButtonList id="shippingType" path="shippingType"
			                                              selectList="${cartShippingMethods}"
			                                              mandatory="true" selectedValue="${cartCmd.shippingType}"/>
			                </td>
			            </tr>
			            <tr>
			            	<td colspan="5" align="right">
			            		<to:secondaryButton id="updateTotal" buttonType="submit"
			                    	targetAction="<%= PageParameterValue.UPDATE_TOTAL %>"
			                        buttonCssClass="rightalignbutton"/>
							</td>
			            </tr>
		            </c:if>
		            <tr>
		                <td colspan="2" align="right" class="bold"><to:label id="total"/></td>
		                <td colspan="3" align="right">
		                    <nemo-tfl:poundSterlingFormat amount="${cartTotal}"/>
		                </td>
		            </tr>
		            <tr>
		                <td colspan="1"><to:secondaryButton id="emptyBasket" buttonType="submit"
		                                        targetAction="<%= PageParameterValue.EMPTY_BASKET %>"
		                                        buttonCssClass="warn-button button"/></td>
						<td colspan="1"></td>
		                <td colspan="3"><to:primaryButton id="continue" buttonType="submit"
		                                                  targetAction="<%= PageParameterValue.CONTINUE %>"/></td>
		            </tr>
		        </c:if>
		        <c:if test="${cartDTO.cartItems.size() == 0}">
		            <tr>
		                <td colspan="3"><to:head3 id="noProducts"/></td>
		            </tr>
		            <tr><td colspan="5"></td></tr>
		        </c:if>
	        <tbody>
	    </table>
	</div>
</div>
<script src="scripts/cart.js"></script>