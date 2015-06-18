<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>
<form:form commandName="CustomerSearchCmd"
           cssClass="form form-with-tooltips" action="customer.htm">
    <table class="display" summary="Display table">
        <tbody>
	        <to:textTable id="userName" row="1"/>
	        <to:textTable id="cardNumber"/>
	        <to:textTable id="orderNumber"/>
	        <to:textTable id="firstName" row="2"/>
	        <to:textTable id="lastName"/>
	        <to:textTable id="postcode"/>
	        <to:textTable id="emailAddress" size="62" row="3" colspan="3"/>
	        <to:checkboxTable id="exact" row="4" val="true"/>
        </tbody>
    </table>
    <div id="button-area">
        <to:button id="clearCriteria" />
        <to:imageButton id="search" imageCssClass="button-search"/>
        <to:imageButton id="addCustomer" imageCssClass="button-add"/>
        <to:button id="openAnonymousGoodwillRefundPage" />
        <to:loadingIcon />
    </div>

    <div class="dataTable-container mTop">
        <div id="search-results">
            <table id="searchresults" class="clickable">
                <thead>
	                <tr>
	                    <th><spring:message code="customerId"/></th>
	                    <th><spring:message code="customersearch.customerName"/></th>
	                    <th><spring:message code="customersearch.cardNumber"/></th>
	                    <th><spring:message code="customersearch.oysterStatus"/></th>
	                    <th><spring:message code="customersearch.address"/></th>
	                    <th><spring:message code="customersearch.calls"/></th>
	                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
       </div>
    </div>
    <spring:message var="customerSearchAddCustomerDescription" code="customersearch.addCustomer.description"/>
    <spring:message var="customerSearchAddCustomerHint" code="customersearch.addCustomer.hint"/>
    <spring:message var="customerSearchAddCustomerImage" code="customersearch.addCustomer.image"/>
    <spring:message var="customerSearchAddCustomerName" code="customersearch.addCustomer.name"/>
    <spring:message var="customerSearchOpenAnonymousGoodwillPageDescription" code="customersearch.openAnonymousGoodwillPage.description"/>
    <spring:message var="customerSearchOpenAnonymousGoodwillPageHint" code="customersearch.openAnonymousGoodwillPage.hint"/>
    <spring:message var="customerSearchOpenAnonymousGoodwillPageImage" code="customersearch.openAnonymousGoodwillPage.image"/>
    <spring:message var="customerSearchOpenAnonymousGoodwillPageName" code="customersearch.openAnonymousGoodwillPage.name"/>
</form:form>
<script src="scripts/customerSearch.js"></script>
<script type="text/javascript">
    var sAddress = "${pageContext.request.contextPath}";
    var customerURL = "<%=PageUrl.INV_CUSTOMER %>";
    var pageName = "<%=Page.INV_CUSTOMER_SEARCH %>";
    var customerSearchURL = "<%=PageUrl.INV_CUSTOMER_SEARCH %>";
    var anonymousGoodwillRefundURL = "<%=PageUrl.INV_ANONYMOUS_GOODWILL_REFUND_MAIN %>";
    var addCustomerDescription = "${customerSearchAddCustomerDescription}";
    var addCustomerHint = "${customerSearchAddCustomerHint}"
    var addCustomerImage = "${customerSearchAddCustomerImage}"
    var addCustomerName = "${customerSearchAddCustomerName}"
    var openAnonymousGoodwillPageDescription = "${customerSearchOpenAnonymousGoodwillPageDescription}"
    var openAnonymousGoodwillPageHint = "${customerSearchOpenAnonymousGoodwillPageHint}"
    var openAnonymousGoodwillPageImage = "${customerSearchOpenAnonymousGoodwillPageImage}"
    var openAnonymousGoodwillPageName = "${customerSearchOpenAnonymousGoodwillPageName}"
    var cardNumber = "${cardNumber}";
</script>