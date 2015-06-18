<%@include file="/WEB-INF/jspf/pageCommon.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div class="content">
	<form:form action="<%=PageUrl.VERIFY_SECURITY_QUESTION%>"
		commandName="<%=PageCommand.SECURITY_QUESTION%>"
		cssClass="form-with-tooltips">
		<div class="box borderless">
			<to:head2 id="cardRegistration" />
			<jsp:include page="commonSecurityQuestion.jsp"></jsp:include>
			<hr>
			<div>
				<to:primaryButton buttonType="submit" id="Continue"
					targetAction="<%=PageParameterValue.VERIFY%>"
					buttonCssClass="save" />
			</div>

		</div>
	</form:form>

</div>