<%@include file="/WEB-INF/jspf/pageCommon.jspf"%>
<%@ page import="com.novacroft.nemo.tfl.innovator.controller.CardAdminController"%>

<script type="text/javascript">
    var sAddress = "${pageContext.request.contextPath}";
    var orderURL = "<%=PageUrl.INV_ORDER%>";
    var pageName = "<%=Page.INV_ORDER%>";
</script>
<to:header id="orderAdmin" />
<form:form commandName="<%=PageCommand.CART%>" action="<%=PageUrl.CART%>" cssClass="form-with-tooltips">
	<jsp:include page="cart.jsp"/>
	<div class="spacer"></div>
	<div class="divider"></div>
			
	<nemo-tfl:selectList id="ticketType" path="ticketType" selectList="${basketTicketTypes}" mandatory="true" selectedValue="${cartCmd.ticketType}" />
	<form:errors path="ticketType" cssClass="field-validation-error" />
	<to:paragraph id="lower" />

	<div class="divider"></div>
	<div class="spacer"></div>
			
	<div id="payAsYouGoSection">
		<jsp:include page="payAsYouGo.jsp"/>
	</div>
	
	<div id="busPassSection">
		<jsp:include page="busPass.jsp"/>
	</div>
	
	<div id="travelCardSection">
		<jsp:include page="travelCard.jsp"/>
	</div>
				
</form:form>
	
<div class="spacer"></div>
<script src="scripts/orderAdd.js"></script>
<script type="text/javascript">
  var pageName = "${pageName}"
  var sAddress = "${pageContext.request.contextPath}";
</script>