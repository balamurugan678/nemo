<%@include file="/WEB-INF/jspf/pageCommon.jspf"%>
<form id="systemProperties" class="form-with-tooltips" action="<%=PageUrl.INV_SYSTEM%>" method="POST">
	<div id="systemAdmin">
		<input type="hidden" id="id" name="id" value="${id}" />
		<to:header id="systemAdmin" />
		<br />
		<c:forEach items="${errors}" var="error">
			<div class="field-validation-error float-none">${error}</div>
		</c:forEach>
		<br />
		<c:forEach items="${parameters}" var="parameter">
			<label for="${parameter.code}" style="width: 300px;">${parameter.code}</label>
     	    <input type="text" id="${parameter.code}" name="${parameter.code}" value="${parameter.value}" size="100" ${parameter.purpose eq '' ? 'disabled' : '' }/>
		</c:forEach>
		<div class="button-container clearfix">
			<to:button buttonType="submit" id="add" buttonCssClass="button" />
		</div>
	</div>
</form>