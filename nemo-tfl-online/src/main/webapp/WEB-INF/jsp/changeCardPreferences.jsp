<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<nemo-tfl:breadcrumbs pageNames='<%=new String[]{Page.DASHBOARD, Page.VIEW_OYSTER_CARD, Page.CHANGE_CARD_PREFERENCES}%>'/>

<to:headLine/>

<div class="r">
    <div class="main">
        <form:form commandName="<%= PageCommand.CARD_PREFERENCES %>" cssClass="oo-responsive-form "
                   action="<%=PageUrl.CHANGE_CARD_PREFERENCES%>">
			<to:hidden id="cardNumber"/>                
            <c:set var="emailAddress"><c:out value="${cardPreferencesCmd.emailAddress}"/></c:set>
            
            <div class="page-heading with-border">
            	<to:oysterCardImageAndCardNumber oysterCardNumber="${cardPreferencesCmd.cardNumber}"/>
            </div>
            <to:head2 id="station"/>
            <nemo-tfl:selectList id="selectStation" path="stationId" selectList="${locations}" mandatory="false"
                                 selectedValue="${cardPreferencesCmd.stationId}" useManagedContentForMeanings="false"/>
            <form:errors path="stationId" cssClass="field-validation-error"/>

            <to:head2section id="explanation" showLink="true"
                             linkArguments="<%=new String[]{(String)pageContext.getAttribute(\"emailAddress\")}%>"/>
            <nemo-tfl:selectList id="emailFrequency" path="emailFrequency" selectList="${statementEmailFrequencies}"
                                 mandatory="false" selectedValue="${cardPreferencesCmd.emailFrequency}"
                                 showPlaceholder="false"/>
            <form:errors path="emailFrequency" cssClass="field-validation-error"/>

            <nemo-tfl:radioButtonList id="attachmentType" path="attachmentType" selectList="${statementAttachmentTypes}"
                                      mandatory="true" selectedValue="${cardPreferencesCmd.attachmentType}"
                                      showHint="true"/>
            <form:errors path="attachmentType" cssClass="field-validation-error"/>

            <p><spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}noJourneysNoStatement.text"/></p>

            <to:head2section id="securityNotice" showLink="false"/>
            <div class="buttonDiv"></div>
            <to:checkbox id="statementTermsAccepted"
                         labelArguments="<%=new String[]{(String)pageContext.getAttribute(\"emailAddress\")}%>"/>

            <div class="button-container clearfix">
                <to:secondaryButton id="cancel" buttonType="submit" targetAction="<%= PageParameterValue.CANCEL %>"/>
                <to:primaryButton id="saveChange" buttonType="submit"
                                  targetAction="<%= PageParameterValue.SAVE_CHANGES %>"/>
            </div>

            <to:hidden id="cardPreferencesId"/>
            <to:hidden id="emailAddress"/>
            <to:hidden id="stationId"/>
            <to:hidden id="cardId" />
        </form:form>
    </div>
    <tiles:insertAttribute name="myAccount"/>
</div>


