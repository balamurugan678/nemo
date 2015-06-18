<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>
<div>
    <to:primaryButton id="transfer" buttonCssClass="warn-button" buttonType="submit" targetAction="<%= PageParameterValue.REDIRECT_TO_TRANSFERS %>"/>
</div>
<div class="clear"></div>
<script type="text/javascript">
	var sAddress = "${pageContext.request.contextPath}";
    var pageName = "${pageName}";
</script>
<script src="scripts/transferProduct.js"></script>