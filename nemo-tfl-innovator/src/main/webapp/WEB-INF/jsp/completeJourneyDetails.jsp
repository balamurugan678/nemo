<%@ page import="com.novacroft.nemo.tfl.common.constant.ContentCode" %>
<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>
<link rel="stylesheet" href="styles/innovator-journeyHistory.css">

<c:set var="dailyCappedTip"><spring:message code="<%=ContentCode.JOURNEY_CAPPED.tipCode()%>"/> </c:set>
<c:set var="autoCompletedTip"><spring:message code="<%=ContentCode.JOURNEY_AUTO_COMPLETED.tipCode()%>"/> </c:set>
<c:set var="manuallyCorrectedTip"><spring:message code="<%=ContentCode.JOURNEY_AUTO_COMPLETED.tipCode()%>"/> </c:set>

<c:set var="journeyAvailable" value="false"/>
<form:form commandName="<%=PageCommand.COMPLETE_JOURNEY%>" cssClass="form-with-tooltips" action="<%=PageUrl.INV_COMPLETE_JOURNEY_DETAILS%>">
            <to:hidden id="linkedStationKey"></to:hidden>
			<to:hidden id="linkedTransactionTime"></to:hidden>
			<to:hidden id="cardId"></to:hidden>
			<to:hidden id="preferredStation"></to:hidden>
			<c:if test="${not empty completeIncompleteJourneyCmd.journey}">
				${completeIncompleteJourneyCmd.journey.journeyDisplay.journeyDescription}
				<div class="csc-journey-from-to-box"> 
 	               <c:if  test="${not empty completeIncompleteJourneyCmd.journey && not empty completeIncompleteJourneyCmd.journey.taps}"> 
	               <c:if  test="${completeIncompleteJourneyCmd.journey.journeyDisplay.warning &&  empty completeIncompleteJourneyCmd.journey.journeyDisplay.transactionLocationName}">
	                   <div class="csc-journey-from-to no-extra-info warning">
	                       <div class="csc-journey-time">
	                          <spring:message code="journey.timeUnknown.text"/>
	                       </div>
	                       <c:set var="journeyAvailable" value="true"/>
	                       <form:errors path="*" cssClass="field-validation-error" />
	                       <nemo-tfl:selectList id="completeJourneyTouchInStation"  path="missingStationId" selectList="${locations}" mandatory="true"  useManagedContentForMeanings="false" selectedValue="${completeIncompleteJourneyCmd.missingStationId}"/>
						   <nemo-tfl:selectList id="completeJourneyMissingTapReason"  path="reasonForMissisng" selectList="${reasonForMissingTap}" mandatory="true"   selectedValue="${completeIncompleteJourneyCmd.reasonForMissisng}"/>
	                        
	                      <c:if  test="${empty completeIncompleteJourneyCmd.preferredStation  }">
								<nemo-tfl:selectList id="completeJourneyPickUpStation"  path="pickUpStation" selectList="${locations}" mandatory="true"  useManagedContentForMeanings="false" selectedValue="${completeIncompleteJourneyCmd.pickUpStation}"/>
	                       </c:if>
	                       <h3 class="station-name unknown "><span class="icon warning-icon "></span><spring:message code="journey.nationalLocationName.unknown.text"/></h3>
	                   </div>
	                   
	               </c:if>
	               </c:if>
	               </div>
					<c:forEach items="${completeIncompleteJourneyCmd.journey.taps}" var="tap" varStatus="loop">
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
	
	               <c:if  test="${completeIncompleteJourneyCmd.journey.journeyDisplay.warning && empty completeIncompleteJourneyCmd.journey.journeyDisplay.exitLocationName}">
	                   <div class="csc-journey-from-to-line"></div>
	                   <div class="csc-journey-from-to no-extra-info warning">
	                       <div class="csc-journey-time">
	                           <div class="csc-time-and-icon"><spring:message code="journey.timeUnknown.text"/>
	                               <span class="icon warning-icon"></span>
	                           </div>
	                       </div>
	                        <c:set var="journeyAvailable" value="true"/>
	                       <form:errors path="*" cssClass="field-validation-error"/>
	                       <nemo-tfl:selectList id="completeJourneyTouchOutStation" path="missingStationId" selectList="${locations}" mandatory="true"  useManagedContentForMeanings="false" selectedValue="${completeIncompleteJourneyCmd.missingStationId}"/>
	                       <nemo-tfl:selectList id="completeJourneyMissingTapReason"  path="reasonForMissisng" selectList="${reasonForMissingTap}" mandatory="true"  selectedValue="${completeIncompleteJourneyCmd.reasonForMissisng}"/>
	                       <c:if  test="${empty completeIncompleteJourneyCmd.preferredStation  }">
								<nemo-tfl:selectList id="completeJourneyPickUpStation"  path="pickUpStation" selectList="${locations}" mandatory="true"  useManagedContentForMeanings="false" selectedValue="${completeIncompleteJourneyCmd.pickUpStation}"/>
	                       </c:if>
	                       
	                       
	                       <h3 class="station-name unknown "><span class="icon warning-icon "></span><spring:message code="journey.nationalLocationName.unknown.text"/></h3>
	                   </div>
	               </c:if>
			 </c:if>
            			
             <div class="button-container clearfix">
                 <c:choose>
	                 <c:when  test="${journeyAvailable}">
	                   <to:primaryButton id="saveChange" buttonType="submit" targetAction="<%= PageParameterValue.COMPLETE_JOURNEY %>"/>
	                </c:when>
	                <c:otherwise>
	                	<spring:message code="unFinishedJourneyNotAvailableForCompletion.description"> </spring:message>
	                </c:otherwise>
	                
                </c:choose>
            </div>

</form:form>
