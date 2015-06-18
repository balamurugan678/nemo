<%@include file="/WEB-INF/jspf/pageCommon.jspf"%>

<body onload="javascript:showReceiptPopUp();">
	<form:form id="fulfilmentConfirm" class="form-with-tooltips"
		commandName="<%=PageCommand.FULFILMENT%>">
		<div>
			<to:header id="fulfilment" />
		</div>
		<jsp:include page="manageFulfilOrderReceipt.jsp" />
		<jsp:include page="manageFulfilmentNextQueue.jsp" />
	</form:form>
</body>
<script type="text/javascript">
    var pageName =  "<%=Page.FULFIL_ORDER_CONFIRMATION%>";
	var contextPath = "${pageContext.request.contextPath}";
</script>
<script src="scripts/fulfilOrderReceipt.js"></script>
</html>