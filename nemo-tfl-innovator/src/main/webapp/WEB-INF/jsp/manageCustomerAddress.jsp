<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

<div>
<br/> ${fulfilmentCmd.customerName} <br/>
	${fulfilmentCmd.customerAddress.houseNameNumber} <br/>
	${fulfilmentCmd.customerAddress.street} <br/>
	${fulfilmentCmd.customerAddress.town} <br/>
	${fulfilmentCmd.customerAddress.country.name} <br/>
	${fulfilmentCmd.customerAddress.postcode} 
</div>