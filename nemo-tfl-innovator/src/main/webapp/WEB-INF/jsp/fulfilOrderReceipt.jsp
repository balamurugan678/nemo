<%@include file="/WEB-INF/jspf/pageCommon.jspf"%>
<link rel="stylesheet" href="styles/innovator-fulfilOrderReceipt.css">
<body>
	<div class="wrapper">
	<img src="images/oyster_logo.gif" class="oyster-image-icon-display"
		width="80" height="25" />
	<jsp:include page="manageOrderDetails.jsp"/>	
	<jsp:include page="manageCustomerDetails.jsp"/>
	<jsp:include page="manageProductDetails.jsp"/>
	<jsp:include page="manageContent.jsp"/>
	<div class="push"></div>
	</div>
	<div class="footer"><b>
	<jsp:include page="manageCustomerAddress.jsp"/>
	</b></div>
</body>