<%@include file="/WEB-INF/jspf/pageCommon.jspf"%>
<%@ page import="com.novacroft.nemo.tfl.common.util.CartUtil" %>
<%@ page import="com.novacroft.nemo.tfl.common.transfer.ItemDTO" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<c:set var="autoTopUp" value="Auto Top-up" />
<c:set var="payAsYouGoCredit" value="Pay as you go credit" />

<nemo-tfl:breadcrumbs
			pageNames='<%=new String[] { Page.DASHBOARD, Page.CHECKOUT }%>' />

<to:headLine />

<div class="r">
	<div class="main">
		
			<c:set var="travelCardItemCount" value="0" />
			<c:forEach items="${cartDTO.cartItems}" var="cartItem" varStatus="status">
				<% ItemDTO itemDTO = (ItemDTO) pageContext.getAttribute("cartItem"); %>
				<c:if test="<%=CartUtil.isItemDTOProductItemDTO(itemDTO)%>">
					<c:set var="travelCardItemCount" value="${travelCardItemCount + 1}" />
				</c:if>
			</c:forEach>
		<form:form method="post" action="<%= PageUrl.CHECKOUT %>"
			commandName="<%=PageCommand.CART%>" cssClass="oo-responsive">
			<div class="box borderless">
				<table border="1" class="shoppingcart">
					<tr>
						<to:tableHeader id="item" />
						<c:if test="${travelCardItemCount gt 0}">
							<to:tableHeader id="startDate" />
							<to:tableHeader id="endDate" />
						</c:if>
						<to:tableHeader id="reminderDate" />
						<to:tableHeader id="price" />
					</tr>
					<c:forEach items="${cartDTO.cartItems}" var="cartItem"
						varStatus="status">
						<c:set var="itemSubclass" value="${cartItem['class'].simpleName}" />
						<c:choose>
							<c:when
								test="${itemSubclass == 'ProductItemDTO' || itemSubclass == 'PayAsYouGoItemDTO'}">
								<tr>
									<td>${cartItem.name}</td>
									<c:if test="${travelCardItemCount gt 0}">
										<td>
											<c:choose>
												<c:when test="${itemSubclass=='ProductItemDTO'}">
													<fmt:formatDate pattern="<%= DateConstant.SHORT_DATE_PATTERN %>" value="${cartItem.startDate}" />
												</c:when>
											</c:choose>
										</td>
										<td>
											<c:choose>
												<c:when test="${itemSubclass=='ProductItemDTO'}">
													<fmt:formatDate pattern="<%= DateConstant.SHORT_DATE_PATTERN %>" value="${cartItem.endDate}" />
												</c:when>
											</c:choose>
										</td>
									</c:if>
									<td>
										<c:choose>
											<c:when
												test="${fn:startsWith(cartItem.reminderDate, 'null')}">
												<%= CartAttribute.REMINDER_DATE_NONE %>
											</c:when>
											<c:otherwise>
								   				${cartItem.reminderDate}									
											</c:otherwise>
										</c:choose>
									</td>
									<td><nemo-tfl:poundSterlingFormat
											amount="${cartItem.price}" />
									</td>
								</tr>
							</c:when>
							<c:otherwise>
								<c:if test="${itemSubclass == 'AutoTopUpConfigurationItemDTO' }">
									<tr>
										<td>${cartItem.name} (<nemo-tfl:poundSterlingFormat
												amount="${cartItem.autoTopUpAmount}" />)
										</td>
										<c:if test="${travelCardItemCount gt 0}">
											<td></td>
											<td></td>
										</c:if>
										<td></td>
										<td><nemo-tfl:poundSterlingFormat
												amount="${cartItem.price}" /></td>
									</tr>
								</c:if>
								<c:if test = "${itemSubclass == 'FailedAutoTopUpResettlementDTO' }">
									<tr>
										<td>${cartItem.name}
										</td>
										<td></td>
										<td class="right-aligned"><nemo-tfl:poundSterlingFormat
												amount="${cartItem.price}" /></td>
									</tr>
								</c:if>
							</c:otherwise>
						</c:choose>
					</c:forEach>

					<c:set var="cartTotal" value="${cartDTO.cartTotal}" />
					<c:set var="refundableDepositAmount" value="${cartDTO.cardRefundableDepositAmount}" />
					<c:set var="shippingMethodAmount" value="${cartDTO.shippingMethodAmount}" />
					<c:set var="webCreditAvailableAmount" value="${cartCmd.webCreditAvailableAmount}" />
					
					<c:if test="${not empty refundableDepositAmount && refundableDepositAmount > 0}">
						<tr>
							<td colspan="4" class="bold right-aligned">
								<span class="bold"><to:label id="subTotal" /></span>
							</td>
							<td colspan="3" class="right-aligned">
								<nemo-tfl:poundSterlingFormat amount="${(cartTotal - refundableDepositAmount - shippingMethodAmount)}" />
							</td>
						</tr>
						<tr>
							<td colspan="4" class="bold right-aligned">
								<span class="bold"><to:label id="refundableCardDeposit" /></span> 
							</td>
							<td colspan="3" class="right-aligned">
								<nemo-tfl:poundSterlingFormat amount="${refundableDepositAmount}" />
							</td>
						</tr>
					</c:if>
						
					<c:if test="${not empty cartDTO.shippingMethodItem.name}">
						<tr>
							<td colspan="4" class="bold right-aligned">
								<span class="bold">${cartDTO.shippingMethodItem.name}:</span>
							</td>
							<td colspan="3" class="right-aligned">
								<nemo-tfl:poundSterlingFormat amount="${shippingMethodAmount}" />
							</td>
						</tr>
					</c:if>
					
					<tr>
						<td colspan="4" class="bold right-aligned">
							<to:label id="total" />
						</td>
						<td colspan="3" class="right-aligned">
							<nemo-tfl:poundSterlingFormat amount="${cartTotal}" />
						</td>
					</tr>
						
					<c:if test="${webCreditAvailableAmount > 0}">
						<tr>
							<td colspan="4" class="right-aligned">
								<to:label id="webCreditAvailableAmount" />
							</td>
							<td colspan="3" class="right-aligned">
								<nemo-tfl:poundSterlingFormat amount="${webCreditAvailableAmount}" />
							</td>
						</tr>
						<tr>
							<td colspan="4" class="right-aligned">
								<to:label id="webCreditApplyAmount" />
							</td>
							<td colspan="3" class="right-aligned">
								<to:text id="webCreditApplyAmount" showLabel="false" htmlEscapeError="false" errorCssClass="left-aligned"/>
								<to:secondaryButton id="updateToPay" buttonType="submit" targetAction="<%= PageParameterValue.UPDATE %>" />
							</td>
						</tr>
					</c:if>
					
					<tr>
						<td colspan="4" class="bold right-aligned">
							<to:label id="toPayAmount" />
						</td>
						<td colspan="3" class="right-aligned">
							<nemo-tfl:poundSterlingFormat amount="${cartCmd.toPayAmount}" />
						</td>
					</tr>
				</table>
			</div>

			<br />

			<div>
				<to:linkButtonAndPopUp id="paymentTermsNotice" />
				<to:checkbox id="paymentTermsAccepted" />
			</div>

			<br />

			<div class="oo-responsive">
				<div class="button-container clearfix">
					<c:if test="${'SecurityQuestion' != cartCmd.pageName}">
						<to:secondaryButton id="cancel" buttonType="submit" targetAction="<%= PageParameterValue.CANCEL %>" />
					</c:if>	
					<to:primaryButton id="continue" buttonType="submit" targetAction="<%= PageParameterValue.CONTINUE %>" />
				</div>
			</div>

		</form:form>
	</div>
	<tiles:insertAttribute name="myAccount"/>
	<div class="aside"></div>
</div>
