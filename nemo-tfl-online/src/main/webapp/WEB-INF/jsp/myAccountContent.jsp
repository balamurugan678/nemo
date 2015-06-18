<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

<div class="aside">
    <div data-set="sidenav-container" class="large">
        <div id="right-hand-nav" class="expandable-list moving-source-order">
            <a href="<spring:message code="dashboard.url"/>" class="heading">
                <h2><spring:message code="dashboard.link"/></h2>
            </a>
            <ul>
                <li class="parent">
                    <div class="link-wrapper">
                        <a href="<spring:message code="myAccount.notifications.url"/>"><spring:message
                                code="myAccount.notifications.link"/></a>
                    </div>
                </li>
                <li class="selected parent">
                    <div class="link-wrapper">
                        <a href="<spring:message code="myAccount.oysterCards.url"/>"><spring:message
                                code="myAccount.oysterCards.link"/></a>
                    </div>
                    <ul>
                        <li>
                            <div class="link-wrapper">
                                <a href="<spring:message code="myAccount.travelStatements.url"/>"><spring:message
                                        code="myAccount.travelStatements.link"/></a>
                            </div>
                        </li>
                        <li>
                            <div class="link-wrapper">
                                <a href="<spring:message code="myAccount.preferences.url"/>"><spring:message
                                        code="myAccount.preferences.link"/></a>
                            </div>
                        </li>
                    </ul>
                </li>
                <li class="parent">
                    <div class="link-wrapper">
                        <a href="<spring:message code="myAccount.contactLessPaymentCards.url"/>"><spring:message
                                code="myAccount.contactLessPaymentCards.link"/></a>
                    </div>
                </li>
                <li class="parent">
                    <div class="link-wrapper">
                        <a href="<spring:message code="myAccount.travelAlerts.url"/>"><spring:message
                                code="myAccount.travelAlerts.link"/></a>
                    </div>
                </li>
                <li class="parent">
                    <div class="link-wrapper">
                        <a href="<spring:message code="myAccount.myFavourites.url"/>"><spring:message
                                code="myAccount.myFavourites.link"/></a>
                    </div>
                </li>
            </ul>
        </div>
    </div>
    <div data-set="sidenav-container" class="small-medium"></div>
</div>
</div>
