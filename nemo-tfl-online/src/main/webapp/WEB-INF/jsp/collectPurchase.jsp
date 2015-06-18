<%@page import="com.novacroft.nemo.tfl.common.constant.TicketType"%>
<%@page import="com.novacroft.nemo.tfl.common.constant.Page"%>
<%@include file="/WEB-INF/jspf/pageCommon.jspf"%>
<%@ page import="java.util.Date"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<c:set var="autoTopUp" value="Auto Top-up" />
<c:choose>
	<c:when test="${cartCmd.pageName == 'TransferProduct'}">
		<nemo-tfl:breadcrumbs
			pageNames='<%=new String[] { Page.DASHBOARD, Page.VIEW_OYSTER_CARD, Page.TRANSFER_PRODUCT, Page.COLLECT_PURCHASE }%>' />
	</c:when>
	<c:otherwise>
		<nemo-tfl:breadcrumbs
			pageNames='<%=new String[] { Page.DASHBOARD, Page.VIEW_OYSTER_CARD, Page.TOP_UP_TICKET, Page.COLLECT_PURCHASE }%>' />
	</c:otherwise>
</c:choose>
<to:areaheader title="heading" />

<div class="r">
	<div class="main">
		<div class="oo-module">
			<p>
				<spring:message code="${pageName}.upper.text" />
			</p>

			<p>
				<spring:message code="${pageName}.tickedList.heading" />
			</p>
			<ul class="ticked-list">
				<li><span class="icon tick-brushstroke-icon hide-text">tick</span>
					<span class="content"><spring:message
							code="${pageName}.tickedList.itemOne.text" /></span></li>
				<li><span class="icon tick-brushstroke-icon hide-text">tick</span>
					<span class="content"><spring:message
							code="${pageName}.tickedList.itemTwo.text" /></span></li>
			</ul>
			<img alt="gates" src="tfl-ux/assets/oyster/Gates.png"
				class="activation-image gates csc-module" /> <img alt="validators"
				src="tfl-ux/assets/oyster/Validators.png"
				class="activation-image validators csc-module" />

			<p class="clear">
				<spring:message code="${pageName}.crossedList.heading" />
			</p>
			<ul class="crossed-list">
				<li><span class="icon cross-brushstroke-icon hide-text">cross</span>
					<span class="content"><spring:message
							code="${pageName}.crossedList.itemOne.text" /></span></li>
				<li><span class="icon cross-brushstroke-icon hide-text">cross</span>
					<span class="content"><spring:message
							code="${pageName}.crossedList.itemTwo.text" /></span></li>
			</ul>
			<form:form action="<%=PageUrl.COLLECT_PURCHASE%>"
				commandName="<%=PageCommand.CART%>" cssClass="oo-responsive-form">
				<div class='form-control-wrap '>
					<nemo-tfl:selectList id="selectStation" path="stationId"
						selectList="${locations}" mandatory="true"
						selectedValue="${cartCmd.stationId}"
						useManagedContentForMeanings="false" />
					<form:errors path="stationId" cssClass="field-validation-error" />

				</div>
				<div class="message-container instructional-container csc-module">
					<h3 class="title">
						<spring:message code="${pageName}.instruction.heading" />
					</h3>
					<div class="content">
						<c:choose>
							<c:when test="${cartCmd.pageName == 'TransferProduct'}">
								<p>
									<spring:message code="${pageName}.instruction.text"
										arguments="${cartCmd.pageName}" />
									<strong class="new-line-content">
										<td><fmt:formatDate
												pattern="<%= DateConstant.DAYINMONTH_SHORTMONTH_YEAR %>"
												value="${cartCmd.pickUpStartDate}" /> <spring:message
												code="${pageName}.tomorrow.text" /></td>
									</strong>
									<spring:message code="and.text" />
									<strong>
										<td><fmt:formatDate
												pattern="<%= DateConstant.DAYINMONTH_SHORTMONTH_YEAR %>"
												value="${cartCmd.pickUpEndDate}" /></td>
									</strong>
								</p>
							</c:when>
							<c:otherwise>
								<c:forEach items="${cartDTO.cartItems}" var="cartItem"
									varStatus="status">
									<c:set var="itemSubclass"
										value="${cartItem['class'].simpleName}" />
									<c:choose>
										<c:when
											test="${itemSubclass == 'ProductItemDTO' 
												|| itemSubclass == 'PayAsYouGoItemDTO'}">
											<c:set var="tomorrow"
												value="<%=new Date(new Date().getTime() + 60 * 60 * 24 * 1000)%>" />
											<fmt:formatDate var="startdate"
												pattern="<%= DateConstant.DAYINMONTH_SHORTMONTH_YEAR %>"
												value="${cartCmd.pickUpStartDate}" />
											<fmt:formatDate var="tomorrowdate"
												pattern="<%= DateConstant.DAYINMONTH_SHORTMONTH_YEAR %>"
												value="${tomorrow}" />
											<p>
												<spring:message code="${pageName}.instruction.text"
													arguments="${cartItem.name}" />
												<strong class="new-line-content"><td>
														${startdate} <c:if test="${startdate eq tomorrowdate}">
															<spring:message code="${pageName}.tomorrow.text" />
														</c:if>
												</td></strong>
												<spring:message code="and.text" />
												<strong><td><fmt:formatDate
															pattern="<%= DateConstant.DAYINMONTH_SHORTMONTH_YEAR %>"
															value="${cartCmd.pickUpEndDate}" /></td></strong>
											</p>
										</c:when>
									</c:choose>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
				<div class="oo-button-group-spaced">
					<c:set var="pageName" value="${cartCmd.pageName}" />
					<%
					    String pageName = (String) pageContext.getAttribute("pageName");
					%>
					<to:secondaryButton id="cancel" buttonType="submit"
						targetAction="<%=TicketType.PAY_AS_YOU_GO_AUTO_TOP_UP.code().equalsIgnoreCase((String) session.getAttribute(CartAttribute.TICKET_TYPE)) ? PageParameterValue.PAY_AS_YOU_GO_AUTO_TOP_UP_BACK
                                : ((Page.TRANSFER_PRODUCT.equalsIgnoreCase(pageName) ? PageParameterValue.TARGET_ACTION_TRANSFER_PRODUCT_BACK
                                                : PageParameterValue.CANCEL))%>" />
					<to:primaryButton id="continue" buttonType="submit"
						targetAction="<%=PageParameterValue.CONTINUE%>" />
				</div>
			</form:form>
		</div>
	</div>
	<tiles:insertAttribute name="myAccount" />
</div>

