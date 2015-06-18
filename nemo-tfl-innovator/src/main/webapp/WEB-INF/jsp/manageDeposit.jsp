<%@include file="/WEB-INF/jspf/pageCommon.jspf"%>
<%@ page import="com.novacroft.nemo.tfl.common.util.CartUtil"%>

<div>
	<to:head3 id="refundDeposit" headingCssClass="space-before-head" />
	<to:label id="refundDeposit" />
	<c:if test="${CartUtil.isDestroyedOrFaildCartType(cartDTO.cartType) == Boolean.TRUE }">
		<nemo-tfl:poundSterlingFormat
			amount="${cartDTO.cardRefundableDepositAmount}" />
		<br />
	</c:if>
</div>
<div class="clear"></div>
<script type="text/javascript">
	var sAddress = "${pageContext.request.contextPath}";
	var pageName = "${pageName}";
</script>
