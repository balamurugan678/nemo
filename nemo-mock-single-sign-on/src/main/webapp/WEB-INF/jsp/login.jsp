<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="nemo" uri="http://novacroft.com/taglib/nemo"%>
<%@ taglib prefix="to" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="nemo-tfl" uri="/WEB-INF/nemo-tfl-taglib.tld"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<div style="width: 400px; margin: 0 auto">
	<div style="border: 1px solid red; width: 100%; margin: 2px;">
		<form:form commandName="LoginCmd" cssClass="form-with-tooltips"
			action="Login">
			<to:text id="userName" />
			<br />
			<to:text id="password" />
			<br />

			<nemo-tfl:selectList id="app" path="app"
				selectList="${appList}" mandatory="true"
				selectedValue="${LoginCmd.app}" showLabel="true"
				useManagedContentForMeanings="false" />
			<br />

			<to:text id="returnURL" />
			<br />
			<to:text id="errorURL" />
			<br />
			<to:text id="errorCode" readonly="true" />
			<br />
			<to:button id="submit" buttonType="submit"></to:button>


			<c:forEach var="cookies" items="${cookie}">
				<div>
					<strong><c:out value="${cookies.key}" /></strong>: Object=
					<c:out value="${cookies.value}" />
					, value=
					<c:out value="${cookies.value.value}" />
					<c:if test="${cookies.key == 'token'}">
						<c:set var="hasCookie" value="true" />
					</c:if>
					<br />
				</div>
			</c:forEach>

		</form:form>
	</div>
	<div style="border: 1px solid red; width: 100%; margin: 2px;">
		<form:form commandName="LoginCmd" action="validatetoken">
			<c:if test="${hasCookie == 'true'}">
			Get user data with cookie
	
			<to:button id="submit" buttonType="submit"></to:button>
			</c:if>
		</form:form>
	</div>
</div>