<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

	<div>
        <to:head3 id="goodwill" headingCssClass="space-before-head"/>
        <div id="goodwillSection">
            <jsp:include page="showGoodwill.jsp" />
        </div>
        <div class="clear"></div>
        <div class="accordian-wrapper">
            <div id="goodwillAccordian">
                <h3><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}addMoreGoodwill.label"/></h3>
                <div id="addGoodwillSection">
                    <jsp:include page="addGoodwill.jsp" />
                </div>
            </div>
        </div>
        <div id="goodwillVerificationDialog" title="<spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}goodwillVerificationDialogTitle.label"/>" class="dialog-display">
            <p id="goodwillVerificationDialogText"></p>
        </div>
    </div>
    <div class="clear"></div>
    <to:hidden id="cartType"/>

<spring:message var="pleaseSelectLabel" code="${pageName}${pageName ne ' ' ? '.' : ''}selectList.placeholder"/>
<script type="text/javascript">
    var extraValidationMessages = ${GoodwillRefundExtraValidationMessages};
    var pleaseSelectLabel = "${pleaseSelectLabel}";
</script>
<script src="scripts/goodwill.js"></script>