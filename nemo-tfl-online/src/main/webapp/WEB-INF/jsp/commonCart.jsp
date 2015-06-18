<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>
<%@ page import="com.novacroft.nemo.tfl.common.util.CartUtil" %>
<%@ page import="com.novacroft.nemo.tfl.common.transfer.ItemDTO" %>

<div class="box borderless">
    <to:head2 id="shoppingBasket"/>
    <c:set var="travelCardItemCount" value="0" />
	<c:forEach items="${cartDTO.cartItems}" var="cartItem" varStatus="status">
		<% ItemDTO itemDTO = (ItemDTO) pageContext.getAttribute("cartItem"); %>
		<c:if test="<%=CartUtil.isItemDTOProductItemDTO(itemDTO)%>">
			<c:set var="travelCardItemCount" value="${travelCardItemCount + 1}" />
		</c:if>
	</c:forEach>
    <table border="1" class="shoppingcart">
        <tr>
            <to:tableHeader id="item"/>
            <c:if test="${travelCardItemCount gt 0}">
				<to:tableHeader id="startDate" />
				<to:tableHeader id="endDate" />
			</c:if>
            <to:tableHeader id="reminderDate"/>
            <to:tableHeader id="price"/>
        </tr>
        <c:if test="${cartDTO.cartItems.size() > 0}">
            <c:forEach items="${cartDTO.cartItems}" var="cartItem" varStatus="status">
            <c:set var="itemSubclass" value="${cartItem['class'].simpleName}"/>
            <c:set var="itemName" value="${cartItem.name}"/>
               <c:choose>
					<c:when test="${itemSubclass == 'ProductItemDTO' 
					|| itemSubclass == 'PayAsYouGoItemDTO'}">
						<tr>
							<td>${cartItem.name}<br /> 
							<form:form commandName="<%= PageCommand.CART %>" action="${param.action}">
									<input type="hidden" name="lineNo" value="${cartItem.id}" />
									<button type="submit" class="buttonastext" name="targetAction"
										value="delete">
										<spring:message code="delete.button.label" />
									</button>
							</form:form>
							</td>
							<c:if test="${travelCardItemCount gt 0}">
							<td>
							<c:choose>
								<c:when test="${itemSubclass=='ProductItemDTO'}">
									<fmt:formatDate pattern="<%= DateConstant.SHORT_DATE_PATTERN %>" value="${cartItem.startDate}"/>
								</c:when>
							</c:choose>
							</td>
							<td>
							<c:choose>
								<c:when test="${itemSubclass=='ProductItemDTO'}">
									<fmt:formatDate pattern="<%= DateConstant.SHORT_DATE_PATTERN %>" value="${cartItem.endDate}"/>
								</c:when>
							</c:choose>
							</td>
							</c:if>
							<td>
								<c:choose>
									<c:when test="${fn:startsWith(cartItem.reminderDate, 'null')}">
										<%= CartAttribute.REMINDER_DATE_NONE %>
									</c:when>
									<c:otherwise>
									   ${cartItem.reminderDate}									
									</c:otherwise>
								</c:choose>
							</td>
							<td><nemo-tfl:poundSterlingFormat amount="${cartItem.price}" />
							</td>
						</tr>
					</c:when>
					<c:when test="${itemName == 'Resettlement'}">
						<tr>
							<td>${cartItem.name}<br /> 
							<form:form commandName="<%= PageCommand.CART %>" action="${param.action}">
									<input type="hidden" name="lineNo" value="${cartItem.id}" />
									<button type="submit" class="buttonastext" name="targetAction"
										value="delete">
										<spring:message code="delete.button.label" />
									</button>
							</form:form>
							</td>
							<td>
								<%= CartAttribute.REMINDER_DATE_NONE %>
							</td>
							<td>
								<nemo-tfl:poundSterlingFormat amount="${cartItem.price}" />
							</td>
						</tr>
					</c:when>
				</c:choose>
            </c:forEach>
            <c:set var="cartTotal" value="${cartDTO.cartTotal}"/>
            <c:set var="refundableDepositAmount" value="${cartDTO.cardRefundableDepositAmount}"/>
            <c:set var="shippingMethodAmount" value="${cartDTO.shippingMethodAmount}"/>
            <form:form commandName="<%= PageCommand.CART %>" action="${param.action}">
            <c:if test="${not empty refundableDepositAmount && refundableDepositAmount > 0}">
	            <tr>
	                <td colspan="5" class="right-aligned"><span class="bold"><to:label id="subTotal"/></span>
                        <nemo-tfl:poundSterlingFormat amount="${cartTotal - refundableDepositAmount - shippingMethodAmount}"/>
                    </td>
                </tr>
	            <tr>
	                <td colspan="5" class="right-aligned"><span class="bold"><to:label id="refundableCardDeposit"/></span>
                        <nemo-tfl:poundSterlingFormat amount="${refundableDepositAmount}"/>
                    </td>
                </tr>
	            <tr>
	                <td colspan="5">
	                    <nemo-tfl:radioButtonList id="shippingType" path="shippingType"
	                                              selectList="${cartShippingMethods}"
	                                              mandatory="true" selectedValue="${cartCmd.shippingType}"/>
	                    <div style="width:100%; height:200px;background-color:red;display:none;"></div>
	                    <to:secondaryButton id="updateTotal" buttonType="submit"
	                                        targetAction="<%= PageParameterValue.UPDATE_TOTAL %>"
	                                        buttonCssClass="rightalignbutton"/>
	                </td>
	            </tr>
            </c:if>
            <tr>
                <td colspan="2"  class="right-aligned bold"><to:label id="total"/></td>
                <td colspan="3"  class="right-aligned">
                    <nemo-tfl:poundSterlingFormat amount="${cartTotal}"/>
                </td>
            </tr>
			<c:if test="${isCartContainsBusPassAndTravelCard}">            
	            <tr>
	            	<td>	            	
		            	<span style="color:red;font-weight:bold"><to:label id="busPassAndTravelCardWarning"/></span>	            	
	            	</td>
	            </tr>
            </c:if>
            <tr>
                <td>
                    <to:secondaryButton id="emptyBasket" buttonType="submit" targetAction="<%= PageParameterValue.EMPTY_BASKET %>" buttonCssClass="warn-button button"/>
                </td>
                <td colspan="2">
                    <to:secondaryButton id="addMoreItems" buttonType="submit" targetAction="<%= PageParameterValue.ADD_MORE_ITEMS %>"/>
                </td>
                <td colspan="2">
                    <to:primaryButton id="continue" buttonType="submit" targetAction="<%= PageParameterValue.CONTINUE %>"/>
            	</td>    
            </tr>
         </form:form>   
        </c:if>
        <c:if test="${cartDTO.cartItems.size() == 0}">
        	 <form:form commandName="<%= PageCommand.CART %>" action="${param.action}">
            <tr>
                <td colspan="3"><to:head3 id="noProducts"/></td>
            </tr>
            <tr>
                <td colspan="5">
                <to:secondaryButton id="addMoreItems" buttonCssClass="rightalignbutton" buttonType="submit" targetAction="<%= PageParameterValue.ADD_MORE_ITEMS %>"/>
				</td>
            </tr>
            </form:form>
        </c:if>
    </table>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        var oTable = $('#example').dataTable({
            "bProcessing": true, "sAjaxSource": "content/findAll.htm", "aoColumns": [
                { "mData": "code" },
                { "mData": "content" },
                { "mData": "locale" },
            ]
        });
    });
    }
</script>
