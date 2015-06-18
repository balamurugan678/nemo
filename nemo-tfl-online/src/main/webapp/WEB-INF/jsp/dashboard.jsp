<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>
<%@ page import="java.util.Date" %>

<nemo-tfl:breadcrumbs pageNames='<%=new String[]{Page.DASHBOARD}%>'/>

<to:headLine/>

<div class="r">
<div class="main">
<div class="oyster-cards-wrapper">

	<c:if test="${amount ne 0}">
		<to:linkButton id="resettleFailedAutoTopUp" />
	</c:if>

    <c:forEach items="${cards}" var="card">
      <c:if test="${not empty card.cardNumber}">	
        <div class="csc-module ">
            <form:form commandName="<%= PageCommand.MANAGE_CARD %>" method="post" action="<%= PageUrl.DASHBOARD %>"
                       cssClass="oo-responsive-form ">
                <input id="cardId" name="cardId" type="hidden" value="${card.id}"/>
                <input id="<%=PageParameterValue.VIEW_OYSTER_CARD%>" name="<%= PageParameter.TARGET_ACTION %>" type="hidden" value="<%=PageParameterValue.VIEW_OYSTER_CARD%>"/>
                <a href="#" class="link-button csc-module">
                    <button id="viewOysterCard" type="submit" class="container link-button csc-card-link">
                            <div class="card-summary ">
                            <span class="status-message"><spring:message code="card.ready.fortravel.text"/> --  <spring:message code="card.addstatus.text"/></span>
                            <div class="card-info with-icon">
                                <span class="icon oyster-card-icon"></span>
                           
                                <div>
	                            <span class="info-line">
	                                <span class="label">Oyster</span>
	                                <span class="info">${card.cardNumber} -- <C:if test="${empty card.cardInfo}">Up to
                                        date data not available.</C:if></span>
	                            </span>
	                            <span class="info-line">
	                                <span class="label">Balance</span>
	                                <span class="info"><nemo-tfl:poundSterlingFormat
                                            amount="${card.cardInfo.ppvDetails.balance}" amountInPence="true"/></span>
	                            </span>
	                            <c:if test="${card.cardInfo.autoTopUpEnabled}">
	                            	<span class="info-line">
	                            		<span class="label">Auto top-up</span>
	                            		<span class="info">Enabled</span> 
	                            	</span>
	                            </c:if>
	                            
                                    <c:forEach items="${card.cardInfo.pptDetails.pptSlots}" var="pptslot">
                                        <c:if test="${!empty pptslot.expiryDate}">
	                                        <span class="info-line">
	                                            <span class="label">Zones</span>
	                                            <span class="info">${fn:replace(pptslot.zone, "Zones", "")}</span>
	                                            <c:set var="fiveDaysFromToday" value="<%=new Date(new Date().getTime() + 5*60*60*24*1000)%>"/>
	                                            <c:set var="today" value="<%=new Date()%>"/>
												<fmt:parseDate value="${pptslot.expiryDate}" var="parsedExpiryDate" pattern="<%= DateConstant.SHORT_DATE_PATTERN %>" />
												<fmt:formatDate var="todaydate" pattern="<%= DateConstant.SHORT_DATE_PATTERN %>"	value="${today}" />
												<c:choose>
													<c:when test="${pptslot.expiryDate eq todaydate}">
		                                            	<span class="expiry-date info ">exp. ${pptslot.expiryDate} </span> <span class="expiry-date info warning-state"> <spring:message code="ticket.expires.today.text"/></span>
		                                            </c:when>
		                                            <c:otherwise>
		                                            	<c:choose>
															<c:when test="${parsedExpiryDate <= fiveDaysFromToday}">	                                            
				                                            	<span class="expiry-date info ">exp. ${pptslot.expiryDate} </span> <span class="expiry-date info warning-state"> <spring:message code="ticket.expires.soon.text"/></span>
				                                            </c:when>
				                                            <c:otherwise>
				                                            	<span class="expiry-date info ">exp. ${pptslot.expiryDate}</span>
				                                            </c:otherwise>
	                                            		</c:choose>
	                                               </c:otherwise>
	                                            </c:choose> 
	                                        </span>
                                        </c:if>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                            </button>
                    </a>
            </form:form>
        </div>
       </c:if> 
    </c:forEach>
</div>
<div class="bottom-stroke-container csc-module">
    <a href="#" class="container-dotted-border link-button add-something-link">
        <div>
                            <span class="centered-icon-wrapper">
                                Web Credit <nemo-tfl:poundSterlingFormat amount="${webAccountCredit}"
                                                                          amountInPence="true"/></span>
        </div>
    </a>
</div>
<div class="bottom-stroke-container csc-module">
    <a href="#" class="container-dotted-border link-button add-something-link">
        <div>
                            <span class="centered-icon-wrapper">
                                <span class="icon plus-icon"></span>Add an Oyster card</span>
        </div>
    </a>
</div>

<div class="vertical-button-container csc-module">
    <a href="#" class="container boxed-link link-button">
        <span>View oyster travel</span>
    </a>
    <a href="#" class="container boxed-link link-button">
        <span>Get an oyster card</span>
    </a>
    <to:linkButtonBlock
            links='<%=new String[]{
                        Page.CHANGE_PERSONAL_DETAILS,
                        Page.ADD_EXISTING_CARD_TO_ACCOUNT, 
                        Page.ORDER_OYSTER_CARD,
                        Page.VIEW_ORDER_HISTORY,
                        Page.WEB_ACCOUNT_CREDIT_STATEMENT,
                        Page.MANAGE_PAYMENT_CARD,
                        Page.QUICK_BUY,
                        Page.INCOMPLETE_JOURNEY,
                        Page.JOURNEY_HISTORY,
                        Page.VIEW_REFUNDS
                        }%>'/>
</div>


</div>
<div class="aside">
    <div data-set="sidenav-container" class="large">
        <div id="right-hand-nav" class="expandable-list moving-source-order">
            <a href="/modes/driving/congestion-charge" class="heading">
                <h2>My Account
                    <span class="visually-hidden">navigation</span>
                </h2>
            </a>
            <ul>
                <li class="parent">
                    <div class="link-wrapper">
                        <a href="/modes/driving/congestion-charge/congestion-charge-zone">Notifications</a>
                    </div>
                </li>
                <li class="selected parent">
                    <div class="link-wrapper">
                        <a href="/modes/driving/congestion-charge/penalties-and-enforcement">Oyster cards</a>
                    </div>
                    <ul>
                        <li>
                            <div class="link-wrapper">
                                <a href="/modes/driving/congestion-charge/penalties-and-enforcement/enforcement-process">Travel
                                    statements</a>
                            </div>
                        </li>
                        <li>
                            <div class="link-wrapper">
                                <a href="/modes/driving/congestion-charge/penalties-and-enforcement/newly-purchased-vehicles">Preferences</a>
                            </div>
                        </li>
                    </ul>
                </li>
                <li class="parent">
                    <div class="link-wrapper">
                        <a href="/modes/driving/congestion-charge/challenge-a-penalty-charge">Contactless payment cards</a>
                    </div>
                </li>
                <li class="parent">
                    <div class="link-wrapper">
                        <a href="/modes/driving/congestion-charge/organisations">Travel alerts</a>
                    </div>
                </li>
                <li class="parent">
                    <div class="link-wrapper">
                        <a href="/modes/driving/congestion-charge/contact-congestion-charge">My favourites</a>
                    </div>
                </li>
            </ul>
        </div>
    </div>
    <div data-set="sidenav-container" class="small-medium"></div>
</div>


</div>
</div>
