<%@include file="/WEB-INF/jspf/pageCommon.jspf"%>

<div>
	<to:header id="Update Details" />
	
	<form:form commandName="UserDetailsCmd" cssClass="form-with-tooltips" action="Update" method="POST">
		<table class="defaultnoborder">
			<tbody>
				<to:hidden id="customerId" />
				<tr>
					<td class="tag-left-column"><to:label id="username"
							mandatory="true" /></td>
					<td class="tag-middle-column">${UserDetailsCmd.username}<to:hidden id="username" /></td>
				</tr>
				<tr>
					<td class="tag-left-column"><to:label id="password"
							mandatory="true" /></td>
					<td class="tag-middle-column"><to:text id="password"
							showLabel="false" /></td>
				</tr>
				<tr>
					<td>
						<div class="clear">&nbsp;</div>
					</td>
				</tr>
				<tr>
					<td class="tag-left-column"><to:label id="title"
							mandatory="true" /></td>
					<td class="tag-middle-column"><nemo-tfl:selectList id="title"
							path="title" selectList="${titles}" mandatory="true"
							selectedValue="${UserDetailsCmd.title}" showLabel="false" /></td>
				</tr>
				<tr>
					<td class="tag-left-column"><to:label id="firstName"
							mandatory="true" /></td>
					<td class="tag-middle-column"><to:text id="firstName"
							mandatory="true" showLabel="false" /></td>
				</tr>
				<tr>
					<td class="tag-left-column"><to:label id="middleName" /></td>
					<td class="tag-middle-column"><to:text id="middleName"
							mandatory="true" showLabel="false" /></td>
				</tr>
				<tr>
					<td class="tag-left-column"><to:label id="lastName"
							mandatory="true" /></td>
					<td class="tag-middle-column"><to:text id="lastName"
							mandatory="true" showLabel="false" /></td>
				</tr>
				<tr>
					<td>
						<div class="clear">&nbsp;</div>
					</td>
				</tr>
				<tr>
					<td class="tag-left-column"><to:label id="homePhoneNumber"
							mandatory="true" /></td>
					<td class="tag-middle-column"><to:text id="homePhoneNumber"
							mandatory="true" showLabel="false" /></td>
				</tr>
				<tr>
					<td class="tag-left-column"><to:label id="mobilePhoneNumber" /></td>
					<td class="tag-middle-column"><to:text id="mobilePhoneNumber"
							mandatory="true" showLabel="false" /></td>
				</tr>
				<tr>
					<td class="tag-left-column"><to:label id="workPhoneNumber" /></td>
					<td class="tag-middle-column"><to:text id="workPhoneNumber"
							mandatory="true" showLabel="false" /></td>
				</tr>
				<tr>
					<td class="tag-left-column"><to:label id="emailAddress"
							mandatory="true" /></td>
					<td class="tag-middle-column"><to:text id="emailAddress"
							mandatory="true" showLabel="false" /></td>
				</tr>
				<tr>
					<td>
						<div class="clear">&nbsp;</div>
					</td>
				</tr>
				<tr>
					<td class="tag-left-column"><to:label id="houseNo"
							mandatory="true" /></td>
					<td class="tag-middle-column"><to:text id="houseNo"
							mandatory="true" showLabel="false" /></td>
				</tr>
				<tr>
					<td class="tag-left-column"><to:label id="houseName"
							mandatory="true" /></td>
					<td class="tag-middle-column"><to:text id="houseName"
							mandatory="true" showLabel="false" /></td>
				</tr>
				<tr>
					<td class="tag-left-column"><to:label id="streetName"
							mandatory="true" /></td>
					<td class="tag-middle-column"><to:text id="streetName"
							mandatory="true" showLabel="false" /></td>
				</tr>
				<tr>
					<td class="tag-left-column"><to:label id="city"
							mandatory="true" /></td>
					<td class="tag-middle-column"><to:text id="city"
							mandatory="true" showLabel="false" /></td>
				</tr>
				<tr>
					<td class="tag-left-column"><to:label id="county"
							mandatory="true" /></td>
					<td class="tag-middle-column"><to:text id="county"
							mandatory="true" showLabel="false" /></td>
				</tr>
				<tr>
					<td class="tag-left-column"><to:label id="postCode"
							mandatory="true" /></td>
					<td class="tag-middle-column"><to:text id="postCode"
							mandatory="true" showLabel="false" /></td>
				</tr>
				<tr>
					<td class="tag-left-column"><to:label id="country"
							mandatory="true" /></td>
					<td class="tag-middle-column"><nemo-tfl:selectList
							id="country" path="country" selectList="${countries}"
							useManagedContentForMeanings="false"
							selectedValue="${UserDetailsCmd.country}" showLabel="false" />
					</td>
				</tr>
				<tr>
					<td class="tag-left-column"><to:label id="addressType"
							mandatory="true" /></td>
					<td class="tag-middle-column"><to:text id="addressType"
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
					<td class="tag-middle-column"><to:text id="securityQuestion"
							mandatory="true" showLabel="false" /></td>
				</tr>
				<tr>
					<td class="tag-left-column"><to:label id="securityAnswer"
							mandatory="true" /></td>
					<td class="tag-middle-column"><to:text id="securityAnswer"
							mandatory="true" showLabel="false" /></td>
				</tr>

				<tr>
					<td><to:checkbox id="tflMarketingOptIn" /></td>
					<td><to:checkbox id="tocMarketingOptIn" /></td>
				</tr>
				<tr>
					<td><to:primaryButton buttonType="submit" id="submit" /></td>
				</tr>
				<tr>
					<td>${UserDetailsCmd.status}</td>
				</tr>
			</tbody>
		</table>
	</form:form>
</div>