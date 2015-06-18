<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

<nemo-tfl:breadcrumbs pageNames='<%=new String[]{Page.GET_OYSTER_CARD}%>'/>

<to:statusMessage messageCode="${statusMessage}"/>

<div class="r">
	<div class="main">
		<div class="oo-responsive">
	        <jsp:include page="commonCart.jsp">
	        	<jsp:param name="action" value="<%=PageUrl.CART%>" />
	        </jsp:include>
		</div>
	</div>
</div>
