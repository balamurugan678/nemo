<%@include file="/WEB-INF/jspf/pageCommon.jspf"%>
<%@ page import="com.novacroft.nemo.tfl.common.util.CartUtil" %> 
<c:set var="refundDeposit" value="Deposit" />

<div>	
	<c:if test="${CartUtil.isDestroyedOrFaildCartType(cartCmd.cartType) == Boolean.TRUE}">
	<to:head3 id="refundDeposit" headingCssClass="space-before-head" />
		<div>
			<to:label id="refundDeposit" />
		</div>
		<div id="refundDeposit">
			<nemo:poundSterlingFormat amount="${cartCmd.cartDTO.cardRefundableDepositAmount}" />
		</div>
	</c:if>

</div>
<div class="clear"></div>