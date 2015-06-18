<%@include file="/WEB-INF/jspf/pageCommon.jspf"%>
<c:set var="autoTopUp" value="Auto Top-up"/>
<c:set var="payAsYouGoCredit" value="Pay as you go credit"/>

<to:header id="orderAdmin" />
<div class="r">
    <form:form commandName="<%=PageCommand.CART%>" action="<%=PageUrl.CHECKOUT%>" cssClass="form-with-tooltips" method="post">
    	<div class="box borderless">
            <table border="1" class="shoppingCart" id="shoppingCart">
	            <thead>
	                <tr id="shoppingCartHeaderRow">
	                    <to:tableHeader id="item"/>
	                    <to:tableHeader id="startDate"/>
	                    <to:tableHeader id="endDate"/>
	                    <to:tableHeader id="reminderDate"/>
	                    <to:tableHeader id="price"/>
	                </tr>
                </thead>
                <tbody>
                <c:forEach items="${cartDTO.cartItems}" var="cartItem" varStatus="status">
                <c:set var="itemSubclass" value="${cartItem['class'].simpleName}"/>
                    <c:choose>
                        <c:when test="${itemSubclass == 'CardRefundableDepositItemDTO' 
						|| itemSubclass == 'ShippingMethodItemDTO'}">
						</c:when>
                        <c:when test="${itemSubclass == 'AutoTopUpItemDTO' }">
                            <tr>
                            <td>${cartItem.name}(<nemo-tfl:poundSterlingFormat amount="${cartItem.autoTopUpAmount}"/>)</td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td class="right-aligned">
                                <nemo-tfl:poundSterlingFormat amount="${cartItem.price}"/>
                            </td>
							</tr>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td>${cartItem.name}</td>
                                <td>								
                                <c:if test="${itemSubclass=='ProductItemDTO' || itemSubclass=='PayAsYouGoItemDTO'}">
									<fmt:formatDate pattern="<%= DateConstant.SHORT_DATE_PATTERN %>" value="${cartItem.startDate}"/>
								</c:if>
								</td>
                                <c:choose>
                                    <c:when test="${itemSubclass=='PayAsYouGoItemDTO'}">
                                        <td>&nbsp;</td>
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
                                <td align="right">
                                    <nemo-tfl:poundSterlingFormat amount="${cartItem.price}"/>
                                </td>

                            </tr>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <c:set var="cartTotal" value="${cartDTO.cartTotal}"/>
	            <c:set var="refundableDepositAmount" value="${cartDTO.cardRefundableDepositAmount}"/>
	            <c:set var="shippingMethodAmount" value="${cartDTO.shippingMethodAmount}"/>
	            <c:set var="webCreditAvailableAmount" value="${cartCmd.webCreditAvailableAmount}"/>
	            <c:if test="${not empty refundableDepositAmount && refundableDepositAmount > 0}">
                    <tr>
                        <td colspan="4" align="left">
                        	<span class="bold"><to:label id="subTotal"/></span>
                        </td>
                        <td colspan="1" align="right">
                        	<nemo-tfl:poundSterlingFormat amount="${(cartTotal - refundableDepositAmount - shippingMethodAmount)}"/>
						</td>
                    </tr>
                    <tr>
                        <td colspan="4" align="left">
                        	<span class="bold"><to:label id="refundableCardDeposit"/></span>
						</td>
                        <td colspan="1" align="right">
                        	<nemo-tfl:poundSterlingFormat amount="${refundableDepositAmount}"/>
                        </td>
                    </tr>
                    <c:if test="${not empty shippingMethodAmount}">
                        <tr>
                            <td colspan="4" align="left">
                            	<span class="bold">${cartDTO.shippingMethodItem.name} : </span>
                            </td>
                            <td colspan="1" align="right">
                            	<nemo-tfl:poundSterlingFormat amount="${shippingMethodAmount}"/>
                            </td>
                        </tr>
                    </c:if>
                </c:if>
                <tr>
                    <td colspan="4" class="bold" align="left"><to:label id="total"/></td>
                    <td colspan="1" align="right">
                        <nemo-tfl:poundSterlingFormat amount="${cartTotal}"/>
                    </td>
                </tr>
                <c:if test="${webCreditAvailableAmount > 0}">
                <tr>
                    <td colspan="4" align="left"><to:label id="webCreditAvailableAmount"/></td>
                    <td colspan="1" align="right">
                        <nemo-tfl:poundSterlingFormat amount="${webCreditAvailableAmount}"/>
                    </td>
                </tr>
                    <tr>
                        <td colspan="1" align="left"><to:label id="webCreditApplyAmount"/></td>
                        <td colspan="2">
                        	
                        </td>
                        <td colspan="1">
                        	<to:text id="webCreditApplyAmount" showLabel="false" htmlEscape="false"/>
                        	<to:secondaryButton id="updateToPay" buttonType="submit" targetAction="<%= PageParameterValue.UPDATE %>"/>
                        </td>
                        <td colspan="1" align="right">
                        </td>
                    </tr>
                </c:if>
                <tr>
                    <td colspan="4" align="left"><to:label id="toPayAmount"/></td>
                    <td colspan="1" align="right">
                        <nemo-tfl:poundSterlingFormat amount="${cartCmd.toPayAmount}"/>
                    </td>
                </tr>
            </table>

        </div>
        
		<br/>

        <div>
            <to:linkButtonAndPopUp id="paymentTermsNotice"/>
            <to:checkbox id="paymentTermsAccepted"/>
        </div>

        <br/>
		
    	<div class="button-container clearfix">
            <to:secondaryButton id="cancel" buttonType="submit" targetAction="<%= PageParameterValue.INV_CANCEL %>"/>
            <to:primaryButton id="continue" buttonType="submit" targetAction="<%= PageParameterValue.CONTINUE %>"/>
        </div>

    </form:form>
</div>
