<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<nemo-tfl:breadcrumbs pageNames='<%=new String[]{Page.DASHBOARD, Page.COMPLETE_JOURNEY_DETAILS}%>'/>
<c:set var="overrideTitle" value=""/>
<c:set var="subtitleText" value=""/>
<c:if test="${not empty completeIncompleteJourneyCmd.journey}">
<c:set var="overrideTitle" value="${completeIncompleteJourneyCmd.journey.journeyDisplay.journeyDescription}"/>
<c:set var="subtitleText"><fmt:formatDate value="${completeIncompleteJourneyCmd.journeyDate}" pattern="<%=DateConstant.DAYINMONTH_SHORTMONTH_YEAR%>"/></c:set>
</c:if>
<to:headLineWithSubtitle headingOverride="${overrideTitle}" subtitleText="${subtitleText}"/>
<div class="r">
    <div class="main">
        <div>
        	<c:set var="journeyAvailable" value="false"/>
	        <form:form commandName="<%=PageCommand.COMPLETE_JOURNEY%>" cssClass="oo-responsive-form" action="<%=PageUrl.COMPLETE_JOURNEY_DETAILS%>">

			<to:hidden id="linkedStationKey"></to:hidden>
			<to:hidden id="linkedTransactionTime"></to:hidden>
			<to:hidden id="cardId"></to:hidden>
			<to:hidden id="preferredStation"></to:hidden>

			<c:if test="${not empty completeIncompleteJourneyCmd.journey}">
	            
 	            <div class="csc-journey-from-to-box">             
	              <c:if  test="${not empty completeIncompleteJourneyCmd.journey && not empty completeIncompleteJourneyCmd.journey.taps}">
	               <c:if  test="${completeIncompleteJourneyCmd.journey.journeyDisplay.warning && empty completeIncompleteJourneyCmd.journey.journeyDisplay.transactionLocationName}">
	                   <div class="csc-journey-from-to no-extra-info warning">
	                       <h2 class="visually-hidden">Via</h2>
	                       <div class="csc-journey-time">
	                           <div class="csc-time-and-icon"><spring:message code="journey.timeUnknown.text"/><span class="icon warning-icon"></span>
	                           </div>
	                       </div>
	                       <c:set var="journeyAvailable" value="true"/>
	                       <form:errors path="*" cssClass="field-validation-error"/>
	                       <nemo-tfl:selectList id="completeJourneyTouchInStation"  path="missingStationId" selectList="${locations}" mandatory="true"  useManagedContentForMeanings="false" selectedValue="${completeIncompleteJourneyCmd.missingStationId}"/>
						    <nemo-tfl:selectList id="completeJourneyMissingTapReason"  path="reasonForMissisng" selectList="${reasonForMissingTap}" mandatory="true"   selectedValue="${completeIncompleteJourneyCmd.reasonForMissisng}"/>
	                        
	                      <c:if  test="${empty completeIncompleteJourneyCmd.preferredStation  }">
	                       		<nemo-tfl:selectList id="completeJourneyPickUpStation"  path="pickUpStation" selectList="${locations}" mandatory="true"  useManagedContentForMeanings="false" selectedValue="${completeIncompleteJourneyCmd.pickUpStation}"/>
	                       </c:if>
	                       <h3 class="station-name unknown "><span class="icon warning-icon "></span><spring:message code="journey.nationalLocationName.unknown.text"/></h3>
	                   </div>
	                   <div class="csc-journey-from-to-line"></div>
	               </c:if>
					<c:set var="showItemFooter" value="false"/>
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
			                           <fmt:formatDate value="${tap.transactionAt}" pattern="<%=DateConstant.TIME_TO_MINUTE_PATTERN%>"/>
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
			                                ${completeIncompleteJourneyCmd.journey.journeyDisplay.journeyDescription}
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
	                       <h2 class="visually-hidden">Via</h2>
	                       <div class="csc-journey-time">
	                           <div class="csc-time-and-icon"><spring:message code="journey.timeUnknown.text"/>
	                               <span class="icon warning-icon"></span>
	                           </div>
	                       </div>
	                        <c:set var="journeyAvailable" value="true"/>
	                       <form:errors path="*" cssClass="field-validation-error"/>
	                       <nemo-tfl:selectList id="completeJourneyTouchOutStation" path="missingStationId" selectList="${locations}" mandatory="true"  useManagedContentForMeanings="false" selectedValue="${completeIncompleteJourneyCmd.missingStationId}"/>
	                       
	                       <nemo-tfl:selectList id="completeJourneyMissingTapReason"  path="reasonForMissisng" selectList="${reasonForMissingTap}" mandatory="true"   selectedValue="${completeIncompleteJourneyCmd.reasonForMissisng}"/>
	                       
	                       		<nemo-tfl:selectList id="completeJourneyPickUpStation"  path="pickUpStation" selectList="${locations}" mandatory="true"  useManagedContentForMeanings="false" selectedValue="${completeIncompleteJourneyCmd.pickUpStation}"/>
	                       
	                       
	                       <h3 class="station-name unknown "><span class="icon warning-icon "></span><spring:message code="journey.nationalLocationName.unknown.text"/></h3>
	                   </div>
	               </c:if>
			 </c:if>
            
             </div>

			</c:if>
             <div class="button-container clearfix">
                <to:secondaryButton id="cancel" buttonType="submit" targetAction="<%= PageParameterValue.CANCEL %>"/>
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
       </div>     	
    </div>
       
</div>

