<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

<nemo-tfl:breadcrumbs pageNames='<%=new String[]{Page.GET_OYSTER_CARD}%>'/>
<div class="r">
    <div>
        <to:head2section showLink="false" id="upper"></to:head2section>
    </div>
</div>
<div class="r">
    <form:form action="<%= PageUrl.ACCOUNT_DETAILS %>" commandName="<%=PageCommand.CART%>"
               cssClass="form-with-tooltips">
        <div class="box borderless">
            <to:head2 id="mydetails"/>
            <nemo-tfl:selectList id="title" path="title" selectList="${titles}" mandatory="true" showPlaceholder="true" selectedValue="${cartCmd.title}"/>
            <form:errors path="title" cssClass="field-validation-error"/>
            <to:text id="firstName" mandatory="true" maxlength="50"/>
            <to:text id="initials" maxlength="10"/>
            <to:text id="lastName" mandatory="true" maxlength="50"/>
            <to:text id="postcode" mandatory="true" maxlength="10"/>
            <to:primaryButton id="findAddress" buttonType="submit" targetAction="<%= PageParameterValue.FIND_ADDRESS %>"/>
            <c:if test="${addressesForPostcode != null}">
                <nemo-tfl:selectList id="addressesForPostcode" path="addressForPostcode" selectList="${addressesForPostcode}"
                                     useManagedContentForMeanings="false" mandatory="true"/>
                <to:primaryButton id="selectAddress" buttonType="submit"
                                  targetAction="<%= PageParameterValue.SELECT_ADDRESS %>"/>
            </c:if>
            <to:text id="houseNameNumber" mandatory="true" maxlength="50"/>
            <to:text id="street" mandatory="true" maxlength="50"/>
            <to:text id="town" mandatory="true" maxlength="50"/>
            <nemo-tfl:selectList id="country" path="country" selectList="${countries}"
                     mandatory="true" useManagedContentForMeanings="false" selectedValue="${cartCmd.country}"/>
            <to:text id="homePhone" mandatory="true" maxlength="50"/>
            <to:text id="mobilePhone" maxlength="50"/>
            <to:text id="emailAddress" mandatory="true" maxlength="150"/>
            <to:text id="confirmEmailAddress" mandatory="true" maxlength="150"/>
            <to:mandatoryFields/>
        </div>
        <br/>

        <div class="box borderless">
            <to:head2 id="userpass"/>
            <to:text id="username" mandatory="true" hint="true" maxlength="50"/><br/>
            <to:password id="newPassword" mandatory="true" hint="true"/><br/>
            <to:password id="newPasswordConfirmation" mandatory="true"/>
            <to:mandatoryFields/>
        </div>
        <hr>
        <to:buttons targetAction="<%= PageParameterValue.PROCESS %>"/>
        <input id="AccountDetails.pageName" name="pageName" type="hidden" value="<%=Page.OPEN_ACCOUNT%>"/>
    </form:form>
</div>

