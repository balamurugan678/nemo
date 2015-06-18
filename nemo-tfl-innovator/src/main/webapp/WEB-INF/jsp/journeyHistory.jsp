<%@ page import="com.novacroft.nemo.tfl.common.constant.ContentCode" %>
<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>
<link rel="stylesheet" href="styles/innovator-journeyHistory.css">

<c:set var="dailyCappedTip"><spring:message code="<%=ContentCode.JOURNEY_CAPPED.tipCode()%>"/> </c:set>
<c:set var="autoCompletedTip"><spring:message code="<%=ContentCode.JOURNEY_AUTO_COMPLETED.tipCode()%>"/> </c:set>
<c:set var="manuallyCorrectedTip"><spring:message code="<%=ContentCode.JOURNEY_AUTO_COMPLETED.tipCode()%>"/> </c:set>

<form:form commandName="<%=PageCommand.JOURNEY_HISTORY%>" cssClass="form-with-tooltips" action="<%=PageUrl.JOURNEY_HISTORY%>">
    <div id="journeyHistory">
        <to:hidden id="cardId"/>
        <to:header id="journeyHistory"/>
        <div class="box borderless bold">
        	<to:label id="oysterCardNumber" value="${journeyHistoryCmd.cardNumber}"/>
        </div> 
        <div class="button-container clearfix">
        	<label for="startDate"><spring:message code="JourneyHistory.startDate.label"/></label>
        	<to:text id="startDate" cssClass="datePicker" showLabel="false"/>
			<label for="endDate"><spring:message code="JourneyHistory.endDate.label"/></label>
        	<to:text id="endDate" cssClass="datePicker" showLabel="false"/>
        </div>
        <div class="button-container clearfix">
            <to:primaryButton id="getDetails" buttonType="submit" targetAction="<%=PageParameterValue.GET_DETAILS%>"/>
            <to:secondaryButton id="cancel" buttonType="button" targetAction="<%=PageParameterValue.CANCEL%>"/>
            <to:loadingIcon/>
        </div>

        <div id="displayJourneyHistory">

            <c:if test="${journeyHistoryCmd.journeyHistory.journeyDays != null}">
                <div class="dataTable-container mTop">
                    <table id="JourneyHistoryTable">
                        <thead>
                        <tr>
                            <th class="left-aligned">
                                <spring:message code="<%= ContentCode.JOURNEY_DATE_TIME.headingCode()%>"/>
                            </th>
                            <th class="left-aligned">
                                <spring:message code="<%= ContentCode.JOURNEY_DESCRIPTION.headingCode()%>"/>
                            </th>
                            <th class="right-aligned">
                                <spring:message code="<%= ContentCode.JOURNEY_CHARGE.headingCode()%>"/>
                            </th>
                            <th class="right-aligned">
                                <spring:message code="<%= ContentCode.JOURNEY_CREDIT.headingCode()%>"/>
                            </th>
                            <th class="right-aligned">
                                <spring:message code="<%= ContentCode.JOURNEY_BALANCE.headingCode()%>"/>
                            </th>
                            <th class="left-aligned">&nbsp;</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${journeyHistoryCmd.journeyHistory.journeyDays}" var="journeyDay">
                            <tr class="bold">
                                <td class="top-aligned">
                                    <fmt:formatDate value="${journeyDay.effectiveTrafficOn}"
                                                    pattern="<%=DateConstant.SHORT_DATE_PATTERN%>"/>
                                </td>
                                <td></td>
                                <td></td>
                                <td class="right-aligned top-aligned">
                                    <spring:message code="<%= ContentCode.JOURNEY_DAILY_BALANCE.textCode()%>"/>
                                </td>
                                <td class="right-aligned top-aligned">
                                    <nemo-tfl:poundSterlingFormat amount="${journeyDay.dailyBalance}"/>
                                </td>
                                <td class="top-aligned">&nbsp;</td>
                            </tr>
                            <c:forEach items="${journeyDay.journeys}" var="journey">
                                <c:if test="${!journey.suppressCode}">
                                    <tr>
                                        <td class="top-aligned">${journey.journeyDisplay.journeyTime}</td>
                                        <td class="top-aligned">${journey.journeyDisplay.journeyDescription}</td>
                                        <td class="right-aligned top-aligned">
                                            <c:if test="${not empty journey.journeyDisplay.chargeAmount}">
                                                <nemo-tfl:poundSterlingFormat amount="${journey.journeyDisplay.chargeAmount}"/>
                                            </c:if>
                                        </td>
                                        <td class="right-aligned top-aligned">
                                            <c:if test="${not empty journey.journeyDisplay.creditAmount}">
                                                <nemo-tfl:poundSterlingFormat amount="${journey.journeyDisplay.creditAmount}"/>
                                            </c:if>
                                        </td>
                                        <td class="right-aligned top-aligned">
                                            <nemo-tfl:poundSterlingFormat amount="${journey.storedValueBalance}"/>
                                        </td>
                                        <td class="top-aligned">
                                            <c:if test="${journey.journeyDisplay.warning}">
                                                <span class="icon warning-icon"
                                                      title="${journey.journeyDisplay.pseudoTransactionTypeDisplayDescription}"></span>
                                            </c:if>
                                            <c:if test="${journey.dailyCappingFlag}">
                                                <span class="icon capped-icon" title="${dailyCappedTip}"></span>
                                            </c:if>
                                            <c:if test="${journey.autoCompletionFlag}">
                                                <span class="icon autocompleted-icon" title="${autoCompletedTip}"></span>
                                            </c:if>
                                            <c:if test="${journey.journeyDisplay.manuallyCorrected}">
                                                <span class="icon manually-corrected-icon"
                                                      title="${manuallyCorrectedTip}"></span>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>
        </div>
        <form:errors cssClass="field-validation-error"/>
    </div>

</form:form>

<script type="text/javascript">
    var startDateElementId = "${pageName}\\.startDate";
    var endDateElementId = "${pageName}\\.endDate";
</script>
<script src="scripts/journeyHistory.js"></script>