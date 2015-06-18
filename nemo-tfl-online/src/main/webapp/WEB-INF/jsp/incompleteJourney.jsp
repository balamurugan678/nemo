<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

<nemo-tfl:breadcrumbs pageNames='<%=new String[]{Page.DASHBOARD, Page.INCOMPLETE_JOURNEY}%>'/>

<to:headLine/>

<div class="r">
    <form:form commandName="<%= PageCommand.INCOMPLETE_JOURNEY%>" cssClass="form-with-tooltips"
               action="<%= PageUrl.INCOMPLETE_JOURNEY %>">
        <c:set var="resultMessage" value="${completedJourneyResponse}"> </c:set>
        <c:if test="${not empty resultMessage }">
        	 Refund processing for journey  
        		<c:choose>
        			<c:when test="${resultMessage.refundSuccessfull}">
        			was completed  successfully. The amount refunded is <nemo-tfl:poundSterlingFormat amount="${resultMessage.amount}"></nemo-tfl:poundSterlingFormat>
        			</c:when>
        			<c:otherwise>
        				failed.
        			</c:otherwise>
        		</c:choose>
        </c:if>
        <br/>
        <nemo-tfl:selectList id="selectCard" path="cardId" selectList="${cards}" mandatory="true"
                             useManagedContentForMeanings="false" selectedValue="${incompleteJourneyCmd.cardId}"/>
        <form:errors path="cardId" cssClass="field-validation-error"/>
        <button type="submit" class="primary-button button" name="targetAction" value="selectCard"><spring:message
                code="selectCard.button.text"/></button>

        <c:if test="${incompleteJourneyCmd.cardId != null && incompleteJourneyCmd.cardId ne ''}">
            <to:head2 id="journeys"/>
            <c:if test="${!incompleteJourneyCmd.hasIncompleteJourneys()}">
                <to:informationBox id="noJourneys"/>
            </c:if>
            <c:if test="${incompleteJourneyCmd.hasIncompleteJourneys()}">
            	<div>
			       
			        <ul class="csc-payments">
	            
		                <c:forEach items="${incompleteJourneyCmd.incompleteJourneyHistoryDTO.incompleteJourneyMonthDTO}" var="monthData" varStatus="monthStatus">
		                	<li class="expandable-box">
		                		<span class="first-column">
		                			${monthData.journeyMonth}        
                				</span>
                				<span class="second-column">
                					<ul>
                					<c:forEach items="${monthData.incompleteJourneyList}" var="notification" varStatus="journeyStatus">
	                						<c:url var="journeyItemURL" value="<%=PageUrl.COMPLETE_JOURNEY_DETAILS %>">
	                											<c:param name="cardId" value="${incompleteJourneyCmd.cardId}"/>
							                            		<c:param name="linkedStationKey" value="${notification.journeyNotificationDTO.linkedStationKey}"/>
							                            		<c:param name="linkedTransactionTime" ><fmt:formatDate value="${notification.journeyNotificationDTO.linkedTransactionDateTime}" pattern="<%=DateConstant.ISO_8601_DATE_PATTERN%>"/></c:param>
							                 </c:url>
							               
	                						<li>
	                					 		${notification.journeyDisplayDTO.journeyDescription} <c:if test="${notification.allowSSR}"><a href="${journeyItemURL}"> <spring:message code="journey.warningExplanatory.link"/></a></c:if>
	                					 	</li>
                					 	
                					 </c:forEach>
                					 </ul>
                				</span>
                
                			</li>
               			 </c:forEach>
               	    </ul>
                
            </c:if>

            <div class="button-container clearfix">
                <to:secondaryButton id="cancel" buttonType="submit" targetAction="<%= PageParameterValue.CANCEL %>"/>
            </div>
        </c:if>

    </form:form>
</div>
