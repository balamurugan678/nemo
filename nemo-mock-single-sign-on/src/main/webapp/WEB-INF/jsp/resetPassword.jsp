<%@include file="/WEB-INF/jspf/pageCommon.jspf"%>

<c:choose>
	<c:when test="${not empty param.id || UserDetailsCmd.customerId != null}">
		<div>
			<to:header id="Reset Password" />
			<form:form commandName="UserDetailsCmd" cssClass="form-with-tooltips"
				action="ResetPassword" method="POST">
				<table class="defaultnoborder">
					<tbody>
						<to:hidden id="customerId" />
						<to:hidden id="username" />
						<to:hidden id="securityQuestion" />
						<to:hidden id="returnURL" />
						<tr>
							<td class="tag-left-column"><to:label id="username"
									mandatory="true" /></td>
							<td class="tag-middle-column">${UserDetailsCmd.username}</td>
						</tr>
						<tr>
							<td class="tag-left-column"><to:label id="password"
									mandatory="true" /></td>
							<td class="tag-middle-column"><to:text id="password"
									mandatory="true" showLabel="false" /></td>
						</tr>

						<tr>
							<td>
								<div class="clear">&nbsp;</div>
							</td>
						</tr>

						<tr>
							<td class="tag-left-column"><to:label id="securityQuestion"
									mandatory="true" /></td>
							<td class="tag-middle-column">${UserDetailsCmd.securityQuestion}</td>
						</tr>
						<tr>
							<td class="tag-left-column"><to:label id="securityAnswer"
									mandatory="true" /></td>
							<td class="tag-middle-column"><to:text id="securityAnswer"
									mandatory="true" showLabel="false" /></td>
						</tr>
						<tr>
							<td><to:primaryButton buttonType="submit" id="submit"
									targetAction="resetPassword" /></td>
							<td><button type="submit" formaction="${UserDetailsCmd.returnURL}" formmethod="post">Return</button></td>
						</tr>
						<tr>
							<td>${UserDetailsCmd.status}</td>
						</tr>
					</tbody>
				</table>
			</form:form>
		</div>
	</c:when>
	<c:otherwise>
		<div>
			<to:header id="Reset Password" />
			<form:form commandName="UserDetailsCmd" cssClass="form-with-tooltips"
				action="ResetPassword" method="POST">
				<to:hidden id="returnURL" />
				<to:text id="customerId" mandatory="true" />
				<to:primaryButton buttonType="submit" id="submit" targetAction="setCustomerId" />
			</form:form>
		</div>
	</c:otherwise>
</c:choose>
