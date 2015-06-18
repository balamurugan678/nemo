<%@include file="/WEB-INF/jspf/pageCommon.jspf"%>

<div>
	<to:header id="logout" />

	<form:form commandName="LogoutCmd" cssClass="form-with-tooltips" action="signout" method="POST">
		<table class="defaultnoborder">
			<tbody>
				<to:hidden id="returnURL">${LogoutCmd.returnURL}</to:hidden>
				<to:hidden id="sessionId">${LogoutCmd.sessionId}</to:hidden>
				
				<tr>
					<td class="tag-left-column"><to:label id="username"/></td>
					<td class="tag-middle-column">${LogoutCmd.username }</td>
				</tr>
				
				<c:forEach items="${LogoutCmd.apps}" var="appName">
      				<tr>
						<td class="tag-left-column"><to:label id="app"/></td>
						<td class="tag-middle-column">${appName}</td>
					</tr>
  				</c:forEach>
			</tbody>
		</table>
		<to:primaryButton buttonType="submit" id="logout" />
	</form:form>
</div>