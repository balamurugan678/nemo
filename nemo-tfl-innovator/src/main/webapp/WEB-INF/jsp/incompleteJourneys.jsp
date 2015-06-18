<%@page import="com.novacroft.nemo.tfl.common.constant.PageAttribute"%>
<%@ page import="com.novacroft.nemo.tfl.common.constant.ContentCode" %>
<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>
<link rel="stylesheet" href="styles/innovator-journeyHistory.css">

<c:set var="dailyCappedTip"><spring:message code="<%=ContentCode.JOURNEY_CAPPED.tipCode()%>"/> </c:set>
<c:set var="autoCompletedTip"><spring:message code="<%=ContentCode.JOURNEY_AUTO_COMPLETED.tipCode()%>"/> </c:set>
<c:set var="manuallyCorrectedTip"><spring:message code="<%=ContentCode.JOURNEY_AUTO_COMPLETED.tipCode()%>"/> </c:set>

<form:form commandName="<%=PageCommand.INCOMPLETE_JOURNEY%>" cssClass="form-with-tooltips" action="<%=PageUrl.INV_INCOMPLETE_JOURNEYS%>">
    
        <to:hidden id="cardId"/>
        <to:header id="journeyHistory"/>
        <div id="displayJourneyHistory">
        
        
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
        
        
        

		 <c:if test="${incompleteJourneyCmd.cardId != null && incompleteJourneyCmd.cardId ne ''}">
            <to:head2 id="journeys"/>
           

			 <div class="dataTable-container mTop">
                   
                    <c:forEach items="${incompleteJourneyCmd.incompleteJourneyHistoryDTO.incompleteJourneyMonthDTO}" var="monthData" varStatus="monthStatus">
                    	
                    		<li class="expandable-box">
		                		<span class="first-column">
		                			<b> ${monthData.journeyMonth} </b>       
                				</span>
                				<span class="second-column">
                					
                					<c:forEach items="${monthData.incompleteJourneyList}" var="notification" varStatus="journeyStatus">
	                						<c:url var="journeyItemURL" value="<%=PageUrl.COMPLETE_JOURNEY_DETAILS %>">
	                											<c:param name="cardId" value="${incompleteJourneyCmd.cardId}"/>
							                            		<c:param name="linkedStationKey" value="${notification.journeyNotificationDTO.linkedStationKey}"/>
							                            		<c:param name="linkedTransactionTime" ><fmt:formatDate value="${notification.journeyNotificationDTO.linkedTransactionDateTime}" pattern="<%=DateConstant.ISO_8601_DATE_PATTERN%>"/></c:param>
							                 </c:url>
							               		<li class="pad7left">                						
	                					 		${notification.journeyDisplayDTO.journeyDescription} <c:if test="${notification.allowSSR}"><a href="${journeyItemURL}"> <spring:message code="journey.warningExplanatory.link"/></a></c:if>
	                					 		<br/>
												</li>
												           					 	
                					 </c:forEach>
                					 
                				</span>
                
                			</li>
                		
               		</c:forEach>
                   
                </div>
            
        </c:if>
        </div>
        
         
            <table id="CompletedJourneyHistoryAuditTable">
                        <thead>
                        <tr>
                            <th class="left-aligned">
                                <spring:message code="ContentCode.COMPLETED_JOURNEY_PROCESS_DATE.headingCode"/>
                            </th>
                            <th class="left-aligned">
                                <spring:message code="ContentCode.COMPLETED_JOURNEY_DATE.headingCode"/>
                            </th>
                            <th class="right-aligned">
                                <spring:message code="ContentCode.COMPLETED_JOURNEY_STATION.headingCode"/>
                            </th>
                            <th class="right-aligned">
                                <spring:message code="ContentCode.COMPLETED_JOURNEY_PROVIDED_STATION.headingCode"/>
                            </th>
                             <th class="right-aligned">
                                <spring:message code="ContentCode.COMPLETED_JOURNEY_PICKUP_STATION.headingCode"/>
                            </th>
                            <th class="right-aligned">
                                <spring:message code="ContentCode.COMPLETED_JOURNEY_REFUND_AMOUNT.headingCode"/>
                            </th>
                             <th class="right-aligned">
                                <spring:message code="ContentCode.COMPLETED_JOURNEY_USER_ID.headingCode"/>
                            </th>
                             <th class="right-aligned">
                                <spring:message code="ContentCode.COMPLETED_JOURNEY_ERROR.headingCode"/>
                            </th>
                            <th class="left-aligned">&nbsp;</th>
                        </tr>
                        </thead>
                        <tbody>
                        	<c:if test="${not empty incompleteJourneyCmd.incompleteJourneyHistoryDTO.journeyCompletedRefundItems}">
		                        <c:forEach items="${incompleteJourneyCmd.incompleteJourneyHistoryDTO.journeyCompletedRefundItems}" var="refundItem">
		                            <tr class="bold">
		                                <td class="top-aligned">
		                                	<fmt:formatDate value="${refundItem.processingDate}"
		                                                    pattern="<%=DateConstant.SHORT_DATE_PATTERN%>"/>
		                                </td>
		                                <td class="top-aligned">
		                                	<fmt:formatDate value="${refundItem.journeyDate}"
		                                                    pattern="<%=DateConstant.SHORT_DATE_PATTERN%>"/>
		                                </td>
		                                
		                                <td class="top-aligned">
		                                	${refundItem.startExitStationName}
		                                </td>
		                                
		                                 <td class="top-aligned">
		                                	${refundItem.providedStationName}
		                                </td>
		                                
		                                 <td class="top-aligned">
		                                	${refundItem.pickUpStationName}
		                                </td>
		                                
		                                <td class="top-aligned">
		                                	<nemo-tfl:poundSterlingFormat amount="${refundItem.price}"/>
		                                </td>
		                                
		                                <td class="top-aligned">
		                                	${refundItem.createdUserId }
		                                </td>
		                                
		                               
		                                <td class="right-aligned top-aligned">
		                                    ${refundItem.errorDescription}
		                                </td>
		                                <td class="top-aligned">&nbsp;</td>
		                            </tr>  
		                            
		                 		</c:forEach>
		                 	</c:if>
                 		
                       </tbody> 
                       
             </table>
            

</form:form>
