<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<nemo-tfl:breadcrumbs pageNames='<%=new String[]{Page.DASHBOARD, Page.CHANGE_PERSONAL_DETAILS}%>'/>

<to:headLine/>

<div class="r">
    <div class="main">
        <form:form commandName="<%= PageCommand.PERSONAL_DETAILS %>" cssClass="form-with-tooltips-width"
                   action="<%= PageUrl.CHANGE_PERSONAL_DETAILS %>">

            <to:head2 id="personalDetails"/>
            <nemo-tfl:selectList id="title" path="title" selectList="${titles}" mandatory="true"
                                 selectedValue="${personalDetailsCmd.title}"/>
            <to:text id="firstName" mandatory="true" maxlength="50"/>
            <to:text id="initials" maxlength="10"/>
            <to:text id="lastName" mandatory="true" maxlength="50"/>

            <to:head2 id="address"/>
            <to:text id="postcode" mandatory="true" maxlength="10"/>
            <to:primaryButton id="findAddress" buttonType="submit" targetAction="<%= PageParameterValue.FIND_ADDRESS %>"/>
            <c:if test="${addressesForPostcode != null}">
                <nemo-tfl:selectList id="addressesForPostcode" path="addressForPostcode" selectList="${addressesForPostcode}"
                                     useManagedContentForMeanings="false"/>
                <to:primaryButton id="selectAddress" buttonType="submit"
                                  targetAction="<%= PageParameterValue.SELECT_ADDRESS %>"/>
            </c:if>
            <to:text id="houseNameNumber" mandatory="true" maxlength="50"/>
            <to:text id="street" mandatory="true" maxlength="50"/>
            <to:text id="town" mandatory="true" maxlength="50"/>
			<nemo-tfl:selectList id="country" path="country" selectList="${countries}"
                     mandatory="true" useManagedContentForMeanings="false" selectedValue="${personalDetailsCmd.country.code}"/>
            <form:errors path="country" cssClass="field-validation-error" />         

            <to:head2 id="contactDetails"/>
            <to:text id="homePhone" mandatory="true" maxlength="50"/>
            <to:text id="mobilePhone" maxlength="50"/>
            <to:text id="emailAddress" mandatory="true" maxlength="150"/>

            <to:head2 id="privacy"/>
            <to:linkButtonAndPopUp id="privacyNotice"/>

            <to:head2 id="contactPreferences"/>

            <to:checkbox id="notCanTflContact"/>
            <to:checkbox id="notCanThirdPartyContact"/>

            <div class="button-container clearfix">
                <to:secondaryButton id="cancel" buttonType="submit" targetAction="<%= PageParameterValue.CANCEL %>"/>
                <to:primaryButton id="saveChange" buttonType="submit" targetAction="<%= PageParameterValue.SAVE_CHANGES %>"/>
            </div>
            <to:hidden id="customerId"/>
            <to:hidden id="webAccountId"/>
            <to:hidden id="addressId"/>
            <to:hidden id="homePhoneContactId"/>
            <to:hidden id="mobilePhoneContactId"/>
            <to:hidden id="customerPreferencesId"/>
            <to:hidden id="tflMasterId" />
        </form:form>
    </div>
    <tiles:insertAttribute name="myAccount"/>
</div>

