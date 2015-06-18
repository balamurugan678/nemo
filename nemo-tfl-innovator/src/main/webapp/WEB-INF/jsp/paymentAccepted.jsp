<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

<div class="r">
    <div>
    	<to:informationBox id="paymentAccepted"/>
    	<div class="box info-message borderless csc-module">
        	<span class="bold" ><to:label id="orderNumber" value="${cartCmd.cartDTO.order.orderNumber}" /></span>
        </div>
    </div>
</div>
