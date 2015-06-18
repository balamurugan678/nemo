<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

<nemo-tfl:breadcrumbs
        pageNames='<%=new String[]{Page.DASHBOARD, Page.MANAGE_PAYMENT_CARD, Page.EDIT_PAYMENT_CARD}%>'/>

<to:headLine/>

	<!-- This is a css hack to fix a specific issue on the page. More info OUD-484  -->
	<style>
		.bottomButton{
			top:560px;
		}
	</style>

<div class="r">
    <div class="main">
        <form:form commandName="<%= PageCommand.PAYMENT_CARD%>" cssClass="form-with-tooltips"
                   action="<%=PageUrl.MANAGE_PAYMENT_CARD%>">

            <to:head2 id="paymentCard"/>

            <div>
                <c:if test="${paymentCardCmd.inUseFlag}">
                    <div class="csc-card-status oo-warning">
                        <span class="icon warning-icon"></span>
                        <span class="warning-text"><spring:message code="paymentCardInUse.text"/></span>
                    </div>
                </c:if>
                <to:infoLine labelId="paymentCard.obfuscatedPrimaryAccountNumber"
                             displayValue="${paymentCardCmd.paymentCardDTO.obfuscatedPrimaryAccountNumber}"/>
                <to:infoLine labelId="paymentCard.firstName" displayValue="${paymentCardCmd.paymentCardDTO.firstName}"/>
                <to:infoLine labelId="paymentCard.lastName" displayValue="${paymentCardCmd.paymentCardDTO.lastName}"/>
                <to:infoLine labelId="paymentCard.expiryDate" displayValue="${paymentCardCmd.paymentCardDTO.expiryDate}"/>
            </div>

            <br/>

            <div class='form-control-wrap text-input'>
                <to:label labelCssClass="emphasis" id="paymentCard.nickName" showColon="false"/>
                <div class="form-control ">
                    <form:input path="paymentCardDTO.nickName" class="shaded-input" size="16" maxlength="50" htmlEscape="true"/>
                    <form:errors path="paymentCardDTO.nickName" cssClass="field-validation-error"/>
                </div>
            </div>

            <to:head2 id="address"/>

			<to:primaryButton id="saveChange" buttonType="submit" targetAction="<%= PageParameterValue.SAVE_CHANGES %>" buttonCssClass="bottomButton"/>
            <to:text id="paymentCardDTO.addressDTO.postcode" mandatory="true"/>
            <form:errors path="postcode" cssClass="field-validation-error"/>
            <to:primaryButton id="findAddress" buttonType="submit" targetAction="<%= PageParameterValue.FIND_ADDRESS %>"/>
            <c:if test="${addressesForPostcode != null}">
                <nemo-tfl:selectList id="addressesForPostcode" path="addressForPostcode" selectList="${addressesForPostcode}"
                                     useManagedContentForMeanings="false"/>
                <to:primaryButton id="selectAddress" buttonType="submit"
                                  targetAction="<%= PageParameterValue.SELECT_ADDRESS %>"/>
            </c:if>
            <to:text id="paymentCardDTO.addressDTO.houseNameNumber" mandatory="true"/>
            <to:text id="paymentCardDTO.addressDTO.street" mandatory="true"/>
            <to:text id="paymentCardDTO.addressDTO.town" mandatory="true"/>
            <nemo-tfl:selectList id="paymentCardDTO.addressDTO.country" path="paymentCardDTO.addressDTO.country"
                                 selectList="${countries}"
                                 mandatory="false" showPlaceholder="true" useManagedContentForMeanings="false"
                                 selectedValue="${paymentCardCmd.paymentCardDTO.addressDTO.country.code}"/>

            <div class="oo-button-group-spaced">
                <to:secondaryButton id="cancel" buttonType="submit" targetAction="<%= PageParameterValue.CANCEL_EDIT %>"/>
                <c:if test="${not paymentCardCmd.inUseFlag}">
                    <to:primaryButton id="delete" buttonType="submit" targetAction="<%= PageParameterValue.DELETE %>"/>
                </c:if>
            </div>

            <form:hidden path="inUseFlag"/>
            <form:hidden path="paymentCardDTO.id"/>
            <form:hidden path="paymentCardDTO.customerId"/>
            <form:hidden path="paymentCardDTO.addressId"/>
            <form:hidden path="paymentCardDTO.obfuscatedPrimaryAccountNumber"/>
            <form:hidden path="paymentCardDTO.expiryDate"/>
            <form:hidden path="paymentCardDTO.referenceCode"/>
            <form:hidden path="paymentCardDTO.token"/>
            <form:hidden path="paymentCardDTO.firstName"/>
            <form:hidden path="paymentCardDTO.lastName"/>
            <form:hidden path="paymentCardDTO.status"/>

            <form:hidden path="paymentCardDTO.addressDTO.id"/>
            <form:hidden path="paymentCardDTO.addressDTO.county"/>

        </form:form>
    </div>
    <tiles:insertAttribute name="myAccount"/>
</div>
