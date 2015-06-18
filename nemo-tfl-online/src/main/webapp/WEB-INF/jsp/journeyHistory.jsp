<%@page import="com.novacroft.nemo.tfl.common.constant.ContentCode"%>
<%@include file="/WEB-INF/jspf/pageCommon.jspf"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page
	import="com.novacroft.nemo.tfl.common.constant.JourneyHistoryOutput"%>

<nemo-tfl:breadcrumbs
	pageNames='<%=new String[]{Page.DASHBOARD,Page.VIEW_OYSTER_CARD, Page.JOURNEY_HISTORY}%>' />
<to:headLine />

<c:set var="dailyCappedTip">
	<spring:message code="<%=ContentCode.JOURNEY_CAPPED.tipCode()%>" />
</c:set>
<c:set var="autoCompletedTip">
	<spring:message
		code="<%=ContentCode.JOURNEY_AUTO_COMPLETED.tipCode()%>" />
</c:set>
<c:set var="manuallyCorrectedTip">
	<spring:message
		code="<%=ContentCode.JOURNEY_AUTO_COMPLETED.tipCode()%>" />
</c:set>

<div class="r">
	<div class="main">
		<div>
			<form:form commandName="<%=PageCommand.JOURNEY_HISTORY%>"
				cssClass="oo-responsive-form" action="<%=PageUrl.JOURNEY_HISTORY%>"
				method="GET">
				<div class="csc-module clearfix no-js">
					<div>
						<div class='form-control-wrap'>
							<form:errors cssClass="field-validation-error" />
							<nemo-tfl:selectList id="selectCard" path="cardId"
								selectList="${cards}" mandatory="true" showPlaceholder="true"
								useManagedContentForMeanings="false"
								selectedValue="${journeyHistoryCmd.cardId}" />
							<form:errors path="cardId" cssClass="field-validation-error" />

						</div>
					</div>
					<div>
						<div class='form-control-wrap'>
							<nemo-tfl:selectList id="selectDate" path="weekNumberFromToday"
								selectList="${weekStartAndEndDates}" mandatory="false"
								showPlaceholder="false" useManagedContentForMeanings="false"
								selectedValue="${journeyHistoryCmd.weekNumberFromToday}" />
						</div>
						<div id="customDateContainer" class="custom-date-range without-js">
							<div class='input-with-calendar-container'>
								<div
									class='form-control-wrap text-input with-message expandable-box'>
									<to:text id="startDate" inputCssClass="oo-date-picker"
										labelCssClass="emphasis" showColon="true" readOnly="true"></to:text>
									<a
										class='form-element-accordian-control always-visible no-controls'>
										<span class="icon calendar-icon hide-text">Calendar</span>
									</a>

									<div class="start-hidden">
										<div id="custom-travel-statement-start-date_calendar"
											class="fc-calendar-container"></div>
									</div>
								</div>
							</div>
							<div class='input-with-calendar-container'>
								<div
									class='form-control-wrap text-input with-message expandable-box'>
									<to:text id="endDate" inputCssClass="oo-date-picker"
										labelCssClass="emphasis" showColon="true" readOnly="true"></to:text>
									<a
										class='form-element-accordian-control always-visible no-controls'>
										<span class="icon calendar-icon hide-text">Calendar</span>
									</a>

									<div class="start-hidden">
										<div id="custom-travel-statement-end-date_calendar"
											class="fc-calendar-container"></div>
									</div>
								</div>
							</div>
						</div>
						<div id="submitButtonContainer" class='without-js'>
							<div class="first-column filter-submit">
								<to:primaryButton id="getDetails" buttonType="submit"
									targetAction="<%=PageParameterValue.GET_DETAILS%>" />
							</div>
						</div>
					</div>
				</div>
				<div id="submitLoadingContainer" class="csc-module hidden">
					<span class="title">loading...</span>
				</div>
			</form:form>


			<div class="message-container reminder-container">
				<h4 class="title">
					<spring:message code="pleaseNote.text" />
				</h4>

				<div class="content">
					<spring:message code="JourneyHistory.reminder.text" />
				</div>
			</div>
			<c:choose>
				<c:when test="${not empty journeyHistoryDTO.journeyDays}">
					<div>
						<div class="csc-payment-row table-header">
							<span class="first-column oo-journey-table-column normal-font"><spring:message
									code="journey.date.heading" /></span> <span
								class="oo-price oo-journey-table-column second-column normal-font"><spring:message
									code="journey.totalSpent.heading" /></span> <span
								class="oo-price oo-journey-table-column third-column normal-font"><spring:message
									code="journey.cardBalance.heading" /></span>
						</div>

						<ul class="csc-payments">
							<c:forEach items="${journeyHistoryDTO.journeyDays}"
								var="journeyDay">
								<li class="date-payment-summary expandable-box">
									<div class="content">
										<c:set var="headerExplanatoryClass" value="" />
										<c:choose>
											<c:when test="${journeyDay.multipleExplanatory}">
												<c:set var="headerExplanatoryClass" value=" multi-state " />
											</c:when>
											<c:when test="${journeyDay.containsExplanatoryWarningFlag}">
												<c:set var="headerExplanatoryClass" value=" warning " />
											</c:when>
											<c:when test="${journeyDay.containsExplanatoryCappingFlag}">
												<c:set var="headerExplanatoryClass"
													value=" capping-message " />
											</c:when>
										</c:choose>
										<div
											class='csc-row-heading clearfix always-visible <c:out value="${headerExplanatoryClass}"/>'>
											<a href="javascript:void(0);"
												class="controls inline-side-control">&nbsp;</a> <a
												href="javascript:void(0)"
												class="link-open-table-content block-link"> <span
												class="oo-journey-table-column  first-column emphasis">
													<fmt:formatDate value="${journeyDay.effectiveTrafficOn}"
														pattern="<%=DateConstant.SHORT_DATE_PATTERN%>" />
											</span> <span class="oo-journey-table-column second-column emphasis">
													<nemo-tfl:poundSterlingFormat
														amount="${journeyDay.totalSpent}" />
											</span> <span
												class="oo-journey-table-column third-column  icon-column">
													<c:choose>
														<c:when
															test="${journeyDay.containsExplanatoryWarningFlag && journeyDay.containsExplanatoryCappingFlag}">
															<span class="icon hidden-text warning-icon capped-icon"></span>
														</c:when>
														<c:when
															test="${journeyDay.containsExplanatoryWarningFlag}">
															<span class="icon hidden-text warning-icon"></span>
														</c:when>
														<c:when
															test="${journeyDay.containsExplanatoryCappingFlag}">
															<span class="icon hidden-text capped-icon"></span>
														</c:when>
													</c:choose>
											</span>
											</a>
										</div>
										<div class="csc-payment-rows start-hidden">
											<!-- loop through each line first to check if there exists warnings and capping messages and render these -->
											<c:forEach items="${journeyDay.journeys}" var="journey"
												varStatus="loop">
												<c:if test="${loop.index==0}">
													<div class="explanitory-rows">
														<c:forEach items="${journeyDay.journeys}"
															var="journeyTemp">
															<c:if test="${journeyTemp.journeyDisplay.warning}">
																<c:url var="historyItemCompleteURL"
																	value="<%=PageUrl.COMPLETE_JOURNEY_DETAILS%>">
																	<c:param name="cardId"
																		value="${journeyHistoryCmd.cardId}" />
																	<c:param name="journeyDate">
																		<fmt:formatDate
																			value="${journeyDay.effectiveTrafficOn}"
																			pattern="<%=DateConstant.SHORT_DATE_PATTERN%>" />
																	</c:param>
																	<c:param name="journeyId"
																		value="${journeyTemp.journeyId}" />
																</c:url>
																<div class="csc-explanatory-row warning">
																	<span class="csc-explanation-description"> <spring:message
																			code="journey.warningExplanatory.text" /> <a
																		href="${historyItemCompleteURL}"> <spring:message
																				code="journey.warningExplanatory.link" /></a> <span
																		class="icon hide-text warning-icon"
																		title="${journey.journeyDisplay.pseudoTransactionTypeDisplayDescription}">
																			${journey.journeyDisplay.pseudoTransactionTypeDisplayDescription}
																	</span>
																	</span>
																</div>
															</c:if>
															<c:if test="${journeyTemp.dailyCappingFlag}">
																<div class="csc-explanatory-row capping-message">
																	<span class="csc-explanation-description">${journey.journeyDisplay.pseudoTransactionTypeDisplayDescription}
																		<a
																		href="<spring:message code="journey.cappingExplanatory.url"/>">
																			<spring:message
																				code="journey.cappingExplanatory.link" />
																	</a> <span class="icon capped-icon"
																		title="${dailyCappedTip}"></span>
																	</span>
																</div>
															</c:if>
															<c:if test="${journeyTemp.autoCompletionFlag}">
																<div class="csc-explanatory-row autocomplete-message">
																	<span class="csc-explanation-description">${journey.journeyDisplay.pseudoTransactionTypeDisplayDescription}
																		<a
																		href="<spring:message code="journey.autoCompletedExplanatory.url"/>">
																			<spring:message
																				code="journey.autoCompletedExplanatory.link" />
																	</a> <span class="icon autocompleted-icon"
																		title="${autoCompletedTip}"></span>
																	</span>
																</div>
															</c:if>
															<c:if
																test="${journeyTemp.journeyDisplay.manuallyCorrected}">
																<div
																	class="csc-explanatory-row manuallyCorrected-message">
																	<span class="csc-explanation-description">${journey.journeyDisplay.pseudoTransactionTypeDisplayDescription}
																		<a
																		href="<spring:message code="journey.manuallyCorrectedExplanatory.url"/>">
																			<spring:message
																				code="journey.manuallyCorrectedExplanatory.link" />
																	</a> <span class="icon manually-corrected-icon"
																		title="${manuallyCorrectedTip}"></span>
																	</span>
																</div>
															</c:if>
														</c:forEach>
													</div>
													<div class="csc-payment-row start-balance">
														<span
															class="first-column oo-journey-table-column color-black"><spring:message
																code="journey.startBalance.heading" /></span> <span
															class="oo-price oo-journey-table-column second-column"></span>
														<span
															class="oo-price oo-journey-table-column third-column color-black"><nemo-tfl:poundSterlingFormat
																amount="${(journey.storedValueBalance - journey.addedStoredValueBalance)}" /></span>
													</div>
												</c:if>

												<c:url var="historyItemURL"
													value="<%=PageUrl.JOURNEY_HISTORY_ITEM%>">
													<c:param name="journeyDate">
														<fmt:formatDate value="${journeyDay.effectiveTrafficOn}"
															pattern="<%=DateConstant.SHORT_DATE_PATTERN%>" />
													</c:param>
													<c:param name="journeyId" value="${journey.journeyId}" />
												</c:url>
												<c:set var="manuallyCorrected" value="" />
												<c:if test="${journey.journeyDisplay.manuallyCorrected}">
													<c:set var="manuallyCorrected"
														value="  (Manually Corrected !!)" />
												</c:if>
												<c:set var="explanatoryClass" value="" />
												<c:choose>
													<c:when test="${journey.journeyDisplay.warning}">
														<c:set var="explanatoryClass" value=" warning " />
													</c:when>
													<c:when test="${journey.dailyCappingFlag}">
														<c:set var="explanatoryClass" value=" capping-message " />
													</c:when>
													<c:when test="${journey.autoCompletionFlag}">
														<c:set var="explanatoryClass"
															value=" >autoComplete-message " />
													</c:when>
													<c:when test="${journey.journeyDisplay.manuallyCorrected}">
														<c:set var="explanatoryClass"
															value=" manuallyCorrected-message " />
													</c:when>
												</c:choose>

												<div
													class='csc-payment-row <c:out value="${explanatoryClass}"/>'>
													<a href="${historyItemURL}" class="clearfix block-link">
														<c:choose>
															<c:when
																test="${not empty journey.journeyDisplay.creditAmount && not empty journey.journeyDisplay.chargeAmount}">
																<span class="topup-activated"> <span
																	class="first-column oo-journey-table-column"> <span
																		class="topup-row color-black"></span> <span
																		class="topup-row color-black">${journey.journeyDisplay.journeyDescription}
																			${manuallyCorrected}</span> <span
																		class="topup-row color-black"></span> <span
																		class="topup-row color-black"></span>
																</span> <span
																	class="oo-price oo-journey-table-column second-column spend">
																</span> <span
																	class="oo-price oo-journey-table-column third-column balance">
																		<span class="csc-payment-price color-black"> <nemo-tfl:poundSterlingFormat
																				amount="${journey.storedValueBalance}" />
																	</span>
																</span> <span class="icon right-arrow hide-text">Go to
																		journey page</span>
																</span>
															</c:when>
															<c:when test="${journey.journeyDisplay.topUpActivated}">
																<span class="topup-activated"> <span
																	class="first-column oo-journey-table-column"> <span
																		class="topup-row color-black"><spring:message
																				code="journey.topupActivated.text" /></span> <span
																		class="topup-row color-black">${journey.journeyDisplay.transactionLocationName}</span>
																		<span class="topup-row color-black"></span> <span
																		class="topup-row color-black"><nemo-tfl:poundSterlingFormat
																				amount="${journey.journeyDisplay.creditAmount}" /></span>
																</span> <span
																	class="oo-price oo-journey-table-column second-column spend">
																</span> <span
																	class="oo-price oo-journey-table-column third-column balance">
																		<span class="csc-payment-price color-black"> <nemo-tfl:poundSterlingFormat
																				amount="${journey.storedValueBalance}" />
																	</span>
																</span> <span class="icon right-arrow hide-text">Go to
																		journey page</span>
																</span>
															</c:when>

															<c:when test="${!journey.journeyDisplay.topUpActivated}">
																<span
																	class="csc-from-and-to oo-journey-table-column first-column">
																	<c:choose>
																		<c:when
																			test="${not empty journey.journeyDisplay.transactionLocationName || not empty journey.journeyDisplay.exitLocationName}">
																			<span class="from-arrow-to"> <span
																				class="csc-payment-from"> <c:choose>
																						<c:when
																							test="${empty journey.journeyDisplay.transactionLocationName}">
																							<spring:message
																								code="journey.nationalLocationName.unknown.text" />
																						</c:when>
																						<c:otherwise>
	                                                                            ${journey.journeyDisplay.transactionLocationName}
	                                                                        </c:otherwise>
																					</c:choose>
																			</span> <span class='arrow-and-to'> <span
																					class="csc-payment-arrow"> <span
																						class="icon to-icon hide-text"> <span>to</span>
																					</span>
																				</span> <span class="csc-payment-to"> <c:choose>
																							<c:when
																								test="${empty journey.journeyDisplay.exitLocationName}">
																								<spring:message
																									code="journey.nationalLocationName.unknown.text" />
																							</c:when>
																							<c:otherwise>
	                                                                                ${journey.journeyDisplay.exitLocationName} ${manuallyCorrected}
	                                                                            </c:otherwise>
																						</c:choose>
																				</span>
																			</span>
																			</span>
																		</c:when>
																		<c:otherwise>
																			<span class="from-arrow-to"> <span
																				class="csc-payment-from">
																					${journey.journeyDisplay.journeyDescription} </span>
																			</span>
																		</c:otherwise>
																	</c:choose> <span
																	class="csc-subtitle color-black csc-row-subtitle ">${journey.journeyDisplay.journeyTime}</span>
																</span>
																<span
																	class="oo-price oo-journey-table-column second-column spend">
																	<span class="csc-payment-price color-black"> <nemo-tfl:poundSterlingFormat
																			amount="${journey.journeyDisplay.chargeAmount}" />
																</span>
																</span>
																<span
																	class="oo-price oo-journey-table-column third-column balance">
																	<span class="csc-payment-price color-black"> <nemo-tfl:poundSterlingFormat
																			amount="${journey.storedValueBalance}" />
																</span>
																</span>
																<span class="icon right-arrow hide-text">Go to
																	journey page</span>
															</c:when>
														</c:choose>
													</a>
												</div>
											</c:forEach>
										</div>
									</div>
								</li>
							</c:forEach>

						</ul>

						<c:url var="historyItemURL"
							value="<%=PageUrl.JOURNEY_HISTORY_ITEM%>">
							<c:param name="cardId" value="${journeyHistoryCmd.cardId}" />
							<c:param name="date">
								<fmt:formatDate value="${journeyDay.effectiveTrafficOn}"
									pattern="<%=DateConstant.SHORT_DATE_PATTERN%>" />
							</c:param>
							<c:param name="journeyId" value="${journeyTemp.journeyId}" />
						</c:url>
						<div class="box link-boxes csc-module clearfix">
							<h2 class="link-boxes-heading">
								<spring:message code="JourneyHistory.downloadStatement.heading" />
							</h2>

							<div class="download-box link-box">
								<c:url var="historyDownloadURL"
									value="<%=PageUrl.JOURNEY_HISTORY%>">
									<c:param name="targetAction" value="pdf" />
								</c:url>
								<a href="${historyDownloadURL}"> <span class="download-type"><%=JourneyHistoryOutput.PDF.code().toUpperCase()%></span>
									<span class="download-title"> <fmt:formatDate
											value="${journeyHistoryDTO.rangeFrom}"
											pattern="<%=DateConstant.DAYINMONTH_SHORTMONTH_YEAR%>" /><span>
											- </span> <fmt:formatDate value="${journeyHistoryDTO.rangeTo}"
											pattern="<%=DateConstant.DAYINMONTH_SHORTMONTH_YEAR%>" />
								</span> <span class="icon document-icon vertically-centred"></span>
								</a>
							</div>
							<div class="download-box link-box">
								<c:url var="historyDownloadURL"
									value="<%=PageUrl.JOURNEY_HISTORY%>">
									<c:param name="targetAction" value="csv" />
								</c:url>
								<a href="${historyDownloadURL}"> <span class="download-type"><%=JourneyHistoryOutput.CSV.code().toUpperCase()%></span>
									<span class="download-title"> <fmt:formatDate
											value="${journeyHistoryDTO.rangeFrom}"
											pattern="<%=DateConstant.DAYINMONTH_SHORTMONTH_YEAR%>" /><span>
											- </span> <fmt:formatDate value="${journeyHistoryDTO.rangeTo}"
											pattern="<%=DateConstant.DAYINMONTH_SHORTMONTH_YEAR%>" />
								</span> <span class="icon spreadsheet-icon vertically-centred"></span>
								</a>
							</div>
						</div>
					</div>
				</c:when>
				<c:otherwise>

					<div class="box-and-link csc-module">
						<div class="message-container error-container ">
							<div class="heading with-icon">
								<span class="icon warning-icon vertically-centred"></span>
								<h3 class="title">
									<spring:message
										code="JourneyHistory.journeyHistory.unavailable.heading" />
								</h3>
							</div>
							<div class="content">
								<p>
									<spring:message
										code="JourneyHistory.journeyHistory.unavailable.content" />
								</p>
							</div>
						</div>
					</div>
				</c:otherwise>
			</c:choose>

			<div class="box link-boxes csc-module clearfix">
				<div class='form-control-wrap'>
					<nemo-tfl:selectList id="emailFrequency" path="emailFrequency"
						selectList="${statementEmailFrequencies}" mandatory="false"
						selectedValue="${journeyHistoryCmd.emailStatementPreference}"
						showPlaceholder="false" />
				</div>
			</div>
			<div class="box link-boxes csc-module clearfix">
				<h2 class="link-boxes-heading">
					<spring:message code='JourneyHistory.orderRefundHistory.heading' />
				</h2>
				<a href="<spring:message code='Dashboard.ViewOrderHistory.url'/>"
					class="container boxed-link  link-button"> <span><spring:message
							code='JourneyHistory.orderRefundHistory.link' /> <span
						class="icon right-arrow vertically-centred"></span> </span>
				</a>
			</div>

		</div>
	</div>
	<tiles:insertAttribute name="myAccount" />
</div>

<script type="text/javascript">
	var pageName = "${pageName}";
</script>

<script src="scripts/journeyHistory.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		JourneyHistory.initialisePage();
	});
</script>
