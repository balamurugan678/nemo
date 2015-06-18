<%@include file="/WEB-INF/jspf/pageCommon.jspf"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page import="com.novacroft.nemo.tfl.common.constant.ContentCode" %>


<nemo-tfl:breadcrumbs
	pageNames='<%=new String[]{Page.DASHBOARD, Page.VIEW_OYSTER_CARD, Page.MANAGE_AUTO_TOP_UP,Page.ACTIVATE_CHANGES_TO_AUTO_TOP_UP}%>' />
<to:areaheader title="heading" />

<div class="r">
	<spring:message code="AutoTopUpConfirmationOnPaymentCardChange.paymentCardChanged.text"/>
</div>