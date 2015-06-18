<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<nemo-tfl:breadcrumbs pageNames='<%=new String[]{Page.DASHBOARD, Page.LOST_OR_STOLEN_CARD, Page.LOST_CARD_REFUND_SUMMARY}%>'/>

<to:headLine/>

<div class="r">
    <div class="main">
    <div class="page-heading with-border">
        <div class="content"><to:textPlain id="oysterText"/> ${cartCmd.cardNumber}</div>
    </div>
    <form:form commandName="<%= PageCommand.CART %>" cssClass="oo-responsive-form " action="<%= PageUrl.LOST_CARD_REFUND %>" method="POST">
         <c:forEach items="${cartDTO.cartItems}" var="cartItem" varStatus="status">
         	<c:if test="${cartItem['class'].simpleName == 'PayAsYouGoItemDTO'}">
         		<div class="receipt">
                    <span class="info-line">
                        <span class="label">${cartItem.name}</span>
                        <span class="right-aligned emphasis">+ <nemo:poundSterlingFormat amount="${cartItem.price}" /></span>
                    </span>
                </div>
             </c:if>
             <c:if test="${cartItem['class'].simpleName == 'ProductItemDTO'}">
         		<div class="receipt">
                    <span class="info-line">
                        <span class="label">${cartItem.name}</span>
                        <span class="right-aligned emphasis">+ <nemo:poundSterlingFormat amount="${cartItem.refund.refundAmount}" /></span>
                    </span>
                </div>
             </c:if>  
             <c:if test="${cartItem['class'].simpleName == 'AdministrationFeeItemDTO'}">
         		<div class="receipt">
                    <span class="info-line">
                        <span class="label"><spring:message code="${pageName}.administrationFee.text"/></span>
                        <span class="right-aligned emphasis">- <nemo:poundSterlingFormat amount="${cartItem.price}" /></span>
                    </span>
                </div>
             </c:if> 
         </c:forEach>   
         <div class="payment-total-container message-container csc-module receipt">
             <div class="info-line">
                 <h2 class="label inline-header emphasis"><spring:message code="${pageName}.refundEstimate.text"/></h2>
                 <span class="info right-aligned"><nemo:poundSterlingFormat amount="${cartDTO.getCartRefundTotal()}" /></span>
             </div>
         </div>
         <div class='with-message oo-drop-down-info expandable-box no-border-bottom'>
             <a class="text always-visible first-column no-controls more-info-accordian text-only"
                href="javascript:void(0)"> <spring:message
                     code='${pageName}.whyistherefundestimated.heading'/>
                 <span class="icon tooltip-icon info-i-icon"></span>
             </a>

             <div class="message-wrapper oo-tooltip-container inline-message info-message start-hidden with-indicator">
                 <p class="tight-margin"><spring:message code='${pageName}.whyistherefundestimated.text'/></p>
             </div>
         </div> 
         
         <div class="page-heading with-border ">
             <to:head3 id="refundMethod"/>
         </div>
         <div class="PaymentTypeContainer">
         	<form:errors path="paymentType" cssClass="field-validation-error"/>
         	
         	            <div class="radiobutton-list csc-module">
                        <ul class="input-list with-extra-content expandable-box">
                        <li class="oo-toggle-content  content accordian-input-container oo-input-container with-learn-more">
                            <form:radiobutton path="paymentType" id="paymentType1" value="WebAccountCredit"/>
                            <label class="" for="paymentType1"><spring:message code="${pageName}.paymentType1.label"/>
	                            <a href="javascript:void(0)" class="learn-more-toggle always-visible no-controls" aria-expanded="true">
	                                        <span class="icon info-icon"></span>
	                            </a>
                            </label>
                            <div class="box  info-message start-hidden ">
                               <div class="instructional-container radio-accordian-list csc-module">
                                        <div class="content">
                                            <ol>
                                                <li>
                                                    <div class="item-content">
                                                        <p><to:textPlain id="paymentType1.description"/></p>
                                                    </div>
                                                </li>
                                           </ol>
                                        </div>
                                    </div>
                            </div>
                        </li>
                      </ul>
                 </div>
         </div>
         
         <div class="message-container confirmation-container csc-module">
             <div class="content">
                 <p><spring:message code="${pageName}.bottomMessage.text"/></p>
             </div>
         </div>   
         <a href="#">Terms and conditions</a>
         <input type="hidden" name="cardNumber" value="${cartCmd.cardNumber}"/>
         <input type="hidden" name="hotListReasonId" value="${cartCmd.hotListReasonId}"/>
         <div class="oo-button-group-spaced">
         	<to:primaryButton buttonType="submit" id="confirm" targetAction="<%= PageParameterValue.CONTINUE %>"></to:primaryButton>                  
         	<to:secondaryButton buttonType="submit" id="back" targetAction="<%= PageParameterValue.CANCEL %>"></</to:secondaryButton>
         </div>	 
    </form:form>                       
</div>
    <tiles:insertAttribute name="myAccount"/>
</div>

