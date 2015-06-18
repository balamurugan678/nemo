<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<nemo-tfl:breadcrumbs pageNames='<%=new String[]{Page.DASHBOARD, Page.JOURNEY_HISTORY}%>'/>
<c:set var="overrideTitle" value=""/>
<c:set var="subtitleText" value=""/>
<c:if test="${not empty journeyHistoryItemCmd.journey}">
    <c:set var="overrideTitle" value="${journeyHistoryItemCmd.journey.journeyDisplay.journeyDescription}"/>
    <c:set var="subtitleText"><fmt:formatDate value="${journeyHistoryItemCmd.journeyDate}"
                                              pattern="<%=DateConstant.DAYINMONTH_SHORTMONTH_YEAR%>"/></c:set>
</c:if>
<to:headLineWithSubtitle headingOverride="${overrideTitle}" subtitleText="${subtitleText}"/>

<div class="r">
    <div class="main">
        <div>
            <form:form commandName="<%=PageCommand.JOURNEY_HISTORY_ITEM%>" cssClass="oo-responsive-form"
                       action="<%=PageUrl.JOURNEY_HISTORY_ITEM%>">

                <form:errors cssClass="field-validation-error"/>

                <c:set var="hasServersideErrors" value="false"/>
                <spring:hasBindErrors name="<%=PageCommand.JOURNEY_HISTORY_ITEM%>">
                    <c:if test="${not empty errors.allErrors}"><c:set var="hasServersideErrors" value="true"/></c:if>
                </spring:hasBindErrors>

                <c:if test="${!hasServersideErrors && not empty journeyHistoryItemCmd.journey}">
                    <c:if test="${journeyHistoryItemCmd.journey.journeyDisplay.topUpActivated}">
                        <div class="activated-product">
                            <div class="oo-module csc-module">
                                <div class="topup-row"><spring:message code="journey.topupActivated.text"/></div>
                                <div class="topup-row">${journeyHistoryItemCmd.journey.journeyDisplay.transactionLocationName}<span> <nemo-tfl:poundSterlingFormat
                                        amount="${journeyHistoryItemCmd.journey.journeyDisplay.creditAmount}"/></span></div>
                            </div>
                        </div>
                    </c:if>

                    <div class="csc-journey-from-to-box">
                        <c:if test="${not empty journeyHistoryItemCmd.journey && not empty journeyHistoryItemCmd.journey.taps}">
                            <c:if test="${journeyHistoryItemCmd.journey.journeyDisplay.warning && empty journeyHistoryItemCmd.journey.journeyDisplay.transactionLocationName}">
                                <div class="csc-journey-from-to no-extra-info warning">
                                    <h2 class="visually-hidden">Via</h2>

                                    <div class="csc-journey-time">
                                        <div class="csc-time-and-icon"><spring:message code="journey.timeUnknown.text"/><span
                                                class="icon warning-icon"></span>
                                        </div>
                                    </div>
                                    <h3 class="station-name unknown "><span class="icon warning-icon "></span><spring:message
                                            code="journey.nationalLocationName.unknown.text"/></h3>
                                </div>
                                <div class="csc-journey-from-to-line"></div>
                            </c:if>
                            <c:set var="showItemFooter" value="false"/>
                            <c:forEach items="${journeyHistoryItemCmd.journey.taps}" var="tap" varStatus="loop">
                                <c:if test="${!tap.tapDisplay.topUpActivated}">
                                    <c:if test="${showItemFooter}">
                                        <div class="csc-journey-from-to-line"></div>
                                    </c:if>
                                    <c:set var="showItemFooter" value="true"/>
                                    <div class="csc-journey-from-to no-extra-info ">
                                        <h2 class="visually-hidden">Via</h2>

                                        <div class="csc-journey-time">
                                            <div class="csc-time-and-icon">
                                                <fmt:formatDate value="${tap.transactionAt}"
                                                                pattern="<%=DateConstant.TIME_TO_MINUTE_PATTERN%>"/>
                                                <c:choose>
                                                    <c:when test="${tap.tapDisplay.locationBusFlag}">
                                                        <span class="icon oyster-tap-icon"></span>
                                                    </c:when>
                                                    <c:when test="${tap.tapDisplay.locationUndergroundFlag}">
                                                        <span class="icon oyster-tap-icon"></span>
                                                    </c:when>
                                                    <c:when test="${tap.tapDisplay.locationNationalRailFlag}">
                                                        <span class="icon oyster-interchange-icon"></span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="icon oyster-tap-icon"></span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                        </div>
                                        <h3 class="station-name">
                                            <c:choose>
                                                <c:when test="${tap.tapDisplay.locationBusFlag}">
                                                    <span class="icon bus-icon"></span>
                                                    ${journeyHistoryItemCmd.journey.journeyDisplay.journeyDescription}
                                                </c:when>
                                                <c:when test="${tap.tapDisplay.locationUndergroundFlag}">
                                                    <span class="icon tube-icon"></span>
                                                    ${tap.tapDisplay.nationalLocationName}
                                                </c:when>
                                                <c:when test="${tap.tapDisplay.locationNationalRailFlag}">
                                                    <span class="icon rail-icon"></span>
                                                    ${tap.tapDisplay.nationalLocationName}
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="icon tube-icon"></span>
                                                    ${tap.tapDisplay.nationalLocationName}
                                                </c:otherwise>
                                            </c:choose>

                                        </h3>
                                    </div>
                                </c:if>
                            </c:forEach>

                            <c:if test="${journeyHistoryItemCmd.journey.journeyDisplay.warning && empty journeyHistoryItemCmd.journey.journeyDisplay.exitLocationName}">
                                <div class="csc-journey-from-to-line"></div>
                                <div class="csc-journey-from-to no-extra-info warning">
                                    <h2 class="visually-hidden">Via</h2>

                                    <div class="csc-journey-time">
                                        <div class="csc-time-and-icon"><spring:message code="journey.timeUnknown.text"/>
                                            <span class="icon warning-icon"></span>
                                        </div>
                                    </div>
                                    <h3 class="station-name unknown "><span class="icon warning-icon "></span><spring:message
                                            code="journey.nationalLocationName.unknown.text"/></h3>
                                </div>
                            </c:if>
                        </c:if>

                    </div>

                    <c:if test="${!journeyHistoryItemCmd.journey.journeyDisplay.topUpActivated}">
                        <div class="payment-total-container message-container csc-module">
                            <div class="content emphasis">
                                <c:if test="${journeyHistoryItemCmd.journey.travelCardUsed}">
                                    <spring:message code="journey.fareCoveredByTravelCardZones.text"/>
                                </c:if>
                                <c:if test="${!journeyHistoryItemCmd.journey.travelCardUsed}">
                                    <spring:message code="journey.fareNotCoveredByTravelCardZones.text"/>
                                </c:if>
                                <span> <nemo-tfl:poundSterlingFormat
                                        amount="${journeyHistoryItemCmd.journey.journeyDisplay.chargeAmount}"/></span>
                            </div>
                            <div class="card-info ">
                                <img src="tfl-ux/assets/oyster/Oyster_card_active.png" class="card-info-img" alt="Oyster Card"/>
		                     <span class="info-line with-card-image ">
		                         <span class="label"><spring:message code="journey.oyster.text"/></span>
		                         <span class="info">${journeyHistoryItemCmd.cardNumber}</span>
		                     </span>
                            </div>
                        </div>
                    </c:if>

                </c:if>

                <div class="single-button">
                    <a href="<%=PageUrl.JOURNEY_HISTORY%>?<%=com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_GET_DETAILS%>"
                       class="secondary-button oo-back-button"><spring:message
                            code="journey.backJourneyHistory.button.label"></spring:message></a>
                </div>
            </form:form>
        </div>
    </div>
    <tiles:insertAttribute name="myAccount"/>
</div>

