<%@page import="com.novacroft.nemo.tfl.common.constant.ProductItemType"%>
<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<nemo-tfl:breadcrumbs pageNames='<%=new String[]{Page.DASHBOARD, Page.VIEW_OYSTER_CARD, Page.TOP_UP_TICKET}%>'/>
<to:areaheader title="heading"/>

<div class="r no-js">
    <div class="main">
        <div class="top-level-message-container"></div>
        <div class="radiobutton-list csc-module">
            <form:form action="<%= PageUrl.TOP_UP_TICKET %>" commandName="<%= PageCommand.CART_ITEM %>"
                       cssClass="travel-card oo-responsive-form">
                <to:hidden id="cardNumber"/>
                <div class="page-heading with-border">
                    <div class="content">
                    	<img src="images/oyster.gif" class="oyster-image-icon-display" width="60" height="25"/>
                    	<span class="oysterCardNumber">${cartItemCmd.cardNumber}</span>
                        <span class="failedAutoTopUp-warning-message">
                        	<c:if test="${cartItemCmd.oysterCardWithFailedAutoTopUpCaseCheck eq 'true'}">
                         		<span>
                            		<div class="with-message oo-drop-down-info expandable-box ">
                                   		<a class="text always-visible first-column no-controls more-info-accordian text-only failedAutoTopUp-warning-message"
                                      		href="javascript:void(0)"> <spring:message code='${pageName}.topupTicketBadDept.label'/>
                                       		<span class="icon tooltip-icon info-i-icon"></span>
                                   		</a>
	                                   <div class="message-wrapper oo-tooltip-container inline-message info-message start-hidden with-indicator">
	                                       <p class="tight-margin"><spring:message code='${pageName}.tflcontact.message'/></p>
	                                   </div>
                               		</div>
								</span>
	                   		</c:if>
						</span>
                   </div>                   
                </div>
                <to:head2 id="selectTicketType"/>
                <c:if test="${cartItemCmd.cardId != null && cartItemCmd.cardId ne ''}">
                    <form:errors path="ticketType" cssClass="field-validation-error"/>
                    <input type="hidden" value="${cartItemCmd.cardId }" name="cardId">
                    <ul class="input-list with-extra-content expandable-box">
                        <li class="oo-toggle-content  content accordian-input-container oo-input-container with-learn-more">
                            <form:radiobutton value="payasyougo" path="ticketType" id="payasyougo"/>
                            <label class="" for="payasyougo"><spring:message code="${pageName}.payAsYouGo.label"/></label>

                            <div class="box  info-message start-hidden ">
                                <div class="product-inputs info-message">
                                    <label class="label-in-accordian" for="creditBalance"><spring:message
                                            code="${pageName}.creditBalance.label"/></label>
                                    <nemo-tfl:selectList id="creditBalance" path="creditBalance"
                                                         selectList="${payAsYouGoCreditBalances}"
                                                         mandatory="true" selectedValue="${cartItemCmd.creditBalance}"
                                                         showLabel="false"/>
                                    <form:errors path="creditBalance" cssClass="field-validation-error" htmlEscape="false"/>
                                </div>
                            </div>
                        </li>
                        <c:if test="${cartItemCmd.autoTopUpVisible eq 'false'}">
                            <li class="oo-toggle-content  content accordian-input-container oo-input-container with-learn-more product-group ">
                                <form:radiobutton value="payasyougoAutoTopUp" path="ticketType" id="payasyougoAutoTopUp"/>
                                <label for="payasyougoAutoTopUp"><spring:message
                                        code="${pageName}.payAsYouGoAndAutoTopup.label"/> </label>

                                <div class="box  info-message start-hidden ">
                                    <div class="product-inputs info-message">
                                        <label class="label-in-accordian" for="autoTopUpCreditBalance"><spring:message
                                                code="${pageName}.autoTopUpCreditBalance.label"/></label>
                                        <nemo-tfl:selectList id="autoTopUpCreditBalance" path="autoTopUpCreditBalance"
                                                             selectList="${payAsYouGoCreditBalances}"
                                                             mandatory="true"
                                                             selectedValue="${cartItemCmd.autoTopUpCreditBalance}"
                                                             showLabel="false"/>
                                        <form:errors path="autoTopUpCreditBalance" cssClass="field-validation-error"
                                                     htmlEscape="false"/>
                                        <div class="first-column csc-module">
                                            <h4><spring:message code='${pageName}.autoTopup.heading'/></h4>
                                            <to:paragraph id="autoTopUpInfo"/>
                                        </div>
                                        <div class="with-message oo-drop-down-info expandable-box ">
                                            <a class="text always-visible first-column no-controls more-info-accordian text-only"
                                               href="javascript:void(0)">
                                                <spring:message code='${pageName}.moreAboutAutoTopup.heading'/>
                                                <span class="icon tooltip-icon info-i-icon"></span>
                                            </a>

                                            <div class="message-wrapper oo-tooltip-container inline-message info-message start-hidden with-indicator">
                                                <p class="tight-margin"><spring:message
                                                        code='${pageName}.moreAboutAutoTopup.text'/></p>
                                            </div>
                                        </div>

                                        <div class="first-column">
                                            <h4><to:label id="autoTopUpAmt" mandatory="true"
                                                          labelCssClass="label-in-accordian"/></h4>
                                        </div>
                                        <nemo-tfl:radioButtonList id="autoTopUpAmt" path="autoTopUpAmt"
                                                                  selectList="${payAsYouGoAutoTopUpAmounts}"
                                                                  mandatory="true" selectedValue="${cartItemCmd.autoTopUpAmt}"
                                                                  showHeadingLabel="false"
                                                                  fieldsetCssClass="bottom-stroke-container  first-column"/>
                                        <form:errors path="autoTopUpAmt" cssClass="field-validation-error" htmlEscape="false"/>
                                        <hr/>
                                    </div>
                                </div>
                            </li>
                        </c:if>
                        <li class="oo-toggle-content  content accordian-input-container oo-input-container with-learn-more product-group ">
                            <form:radiobutton value="<%=ProductItemType.TRAVEL_CARD.databaseCode() %>" path="ticketType" id="travelcard"/>
                            <label class="" for="travelcard"><spring:message code="${pageName}.travelcard.label"/></label>

                            <div class="box  info-message start-hidden ">
                                <div class="confirmation"></div>
                                <div class="product-inputs info-message">
                                    <form:errors cssClass="field-validation-error"/>
                                    <to:paragraph id="travelCardInfo"/>
                                    <label class="label-in-accordian" for="travelCardType"><spring:message
                                            code="${pageName}.travelCardType.label"/></label>
                                    <nemo-tfl:selectList id="travelCardType" path="travelCardType"
                                                         selectList="${travelCardTypes}"
                                                         mandatory="true" selectedValue="${cartItemCmd.travelCardType}"
                                                         showLabel="false"/>
                                    <form:errors path="travelCardType" cssClass="field-validation-error"/>
                                    <label class="label-in-accordian" for="startZone"><spring:message
                                            code="${pageName}.startZone.label"/></label>
                                    <nemo-tfl:selectList id="startZone" path="startZone" selectList="${travelCardZones}"
                                                         mandatory="true" selectedValue="${cartItemCmd.startZone}"
                                                         showLabel="false"/>
                                    <form:errors path="startZone" cssClass="field-validation-error"/>
                                    <label class="label-in-accordian" for="endZone"><spring:message
                                            code="${pageName}.endZone.label"/></label>
                                    <nemo-tfl:selectList id="endZone" path="endZone" selectList="${travelCardZones}"
                                                         mandatory="true" selectedValue="${cartItemCmd.endZone}"
                                                         showLabel="false"/>
                                    <form:errors path="endZone" cssClass="field-validation-error"/>
                                    <label class="label-in-accordian" for="startDate"><spring:message
                                            code="${pageName}.startDate.label"/></label>
                                    <nemo-tfl:selectList id="startDate" path="startDate" selectList="${startDates}"
                                                         mandatory="true" selectedValue="${cartItemCmd.startDate}"
                                                         useManagedContentForMeanings="false" showLabel="false"/>
                                    <form:errors path="startDate" cssClass="field-validation-error"/>

                                    <div id="customDateContainer"
                                         class="custom-date-range without-js">
                                        <div class='input-with-calendar-container'>
                                            <div
                                                    class='form-control-wrap text-input with-message expandable-box'>

                                                <a
                                                        class='form-element-accordian-control always-visible no-controls'>
                                                    <span class="icon calendar-icon hide-text">Calendar</span>
                                                </a>
                                                <to:label id="endDate" labelCssClass="label-in-accordian"
                                                          showColon="true"/>
                                                <div class="form-control">
                                                    <form:input path="endDate"
                                                                id="${pageName}${pageName ne ' ' ? '.' : ''}endDate"
                                                                cssClass="shaded-input oo-date-picker"/>
                                                </div>
                                                <form:errors path="endDate"
                                                             cssClass="field-validation-error"/>
                                                <div class="start-hidden">
                                                    <div id="custom-travel-statement-end-date_calendar"
                                                         class="fc-calendar-container"></div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <label class="label-in-accordian" for="emailReminder"><spring:message
                                            code="${pageName}.emailReminder.label"/></label>
                                    <nemo-tfl:selectList id="emailReminder" path="emailReminder"
                                                         selectList="${basketEmailReminders}"
                                                         mandatory="true" selectedValue="${cartItemCmd.emailReminder}"
                                                         showPlaceholder="false" showLabel="false"/>
                                    <form:errors path="emailReminder" cssClass="field-validation-error"/>


                                </div>

                            </div>
                        </li>
                    </ul>
                    <div class="box borderless">
                        <div class="button-container clearfix">
                            <to:secondaryButton id="cancel" buttonType="submit"
                                                targetAction="<%= PageParameterValue.CANCEL %>"/>
                            <to:primaryButton id="continue" buttonType="submit"
                                              targetAction="<%= PageParameterValue.CONTINUE %>"/>
                            <to:secondaryButton id="shoppingBasket" buttonType="submit"
                                                targetAction="<%= PageParameterValue.SHOPPING_BASKET %>"/>
							<c:if test="${cartItemCmd.oysterCardWithFailedAutoTopUpCaseCheck eq 'true' && cartItemCmd.autoTopUpVisible eq 'false'}">
                            	<to:secondaryButton id="resettle" buttonType="submit" targetAction="<%= PageParameterValue.RESETTLE %>"/>
							</c:if>
                                                
                        </div>
                    </div>
                </c:if>
            </form:form>
        </div>
    </div>
    <tiles:insertAttribute name="myAccount"/>
</div>

<script type="text/javascript">
    var contextPath = "${pageContext.request.contextPath}";
    var ticketType = "${cartItemCmd.ticketType}";
    if (ticketType == null || ticketType == "") {
        $('#payasyougo').attr('checked', true);
        $('#payasyougo').parent("li").addClass("expanded ticked");
    }
    var fieldError = $('.field-validation-error:first');
    if (fieldError != null && fieldError != "" && fieldError != undefined) {
        $(fieldError).parents("li").addClass("expanded ticked");

    }
</script>
<script type="text/javascript">
    var pageName = "${pageName}";
    var shortDatePattern = '<%=DateConstant.SHORT_DATE_PATTERN_PARTIAL_YEAR%>';
</script>
<script src="scripts/travelcard.js"></script>
<script type="text/javascript">
    $(document).ready(
            function () {
                TravelCard.initialisePage();
                $(".form-control-wrap .form-element-accordian-control .icon")
                        .css("top", "40px");
                $(".form-control-wrap .form-element-accordian-control .icon")
                        .css("right", "-350px")
            });
</script>

