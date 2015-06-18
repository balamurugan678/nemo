<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

<div>
    <to:head3 id="travelCards" headingCssClass="space-before-head"/>
    <div id="travelCardSection">
        <jsp:include page="showCancelAndSurrenderTravelCards.jsp" />
    </div>
    <div class="clear"></div>
    <div class="accordian-wrapper">
        <div id="travelCardAccordian">
            <h3><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}addMoreTravelCards.label"/></h3>
            <div id="addTravelCardsSection">
                <jsp:include page="addCancelAndSurrenderUnlistedProduct.jsp" />
            </div>
        </div>
    </div>
</div>
<div class="clear"></div>
<script type="text/javascript">
     var pageName = "${pageName}";
     var pageUrl = '<%=PageUrl.INV_FAILED_CARD_REFUND_CART%>';
     var minStartDateForRefundCalendar = "${minStartDateForRefundCalendar}";
 </script>