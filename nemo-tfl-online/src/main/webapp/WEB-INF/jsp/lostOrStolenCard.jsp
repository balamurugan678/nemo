<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<nemo-tfl:breadcrumbs pageNames='<%=new String[]{Page.DASHBOARD, Page.VIEW_OYSTER_CARD, Page.LOST_OR_STOLEN_CARD}%>'/>

<to:headLine isHeadingUnderlined="false"/>
<form:form commandName="<%= PageCommand.LOST_OR_STOLEN_CARD %>" cssClass="oo-responsive-form "
           action="<%= PageUrl.LOST_OR_STOLEN_CARD %>">
    <to:hidden id="cardNumber"/>
    <div class="r">
        <div class="main">
            <div class="page-heading with-border">
            	<to:oysterCardImageAndCardNumber oysterCardNumber="${lostOrStolenCardCmd.cardNumber}"/>
            </div>
            <form:errors cssClass="field-validation-error"/>
            <nemo-tfl:selectList id="selectLostOrStolen" path="hotlistReasonId" selectList="${hotlistReasons}" mandatory="true"
                                 useManagedContentForMeanings="false"/>
            <form:errors path="hotlistReasonId" cssClass="field-validation-error"/>
            <div class="page-heading with-border ">
                <to:head3 id="lostOrStolenOptions"/>
            </div>
            <div class="radiobutton-list csc-module">
                <ul class="input-list with-extra-content expandable-box">
                    <li class="oo-toggle-content  content accordian-input-container oo-input-container with-learn-more">
                        <form:radiobutton path="lostStolenOptions" id="lostStolenOption1" value="transferCard"/>
                        <label class="" for="lostStolenOption1"><spring:message code="${pageName}.lostStolenOption1.label"/>
                            <a href="javascript:void(0)" class="learn-more-toggle always-visible no-controls"
                               aria-expanded="true">
                                <span class="icon info-icon"></span>
                            </a>
                        </label>

                        <div class="box  info-message start-hidden ">
                            <div class="instructional-container radio-accordian-list csc-module">
                                <div class="content">
                                    <ol>
                                        <li>
                                            <div class="item-content">
                                                <p><to:textPlain id="lostStolenOption1.bulletPoint1"/></p>
                                            </div>
                                        </li>
                                        <li>
                                            <div class="item-content">
                                                <p><to:textPlain id="lostStolenOption1.bulletPoint2"/></p>
                                            </div>
                                        </li>
                                        <li>
                                            <div class="item-content">
                                                <p><to:textPlain id="lostStolenOption1.bulletPoint3"/></p>
                                            </div>
                                        </li>
                                    </ol>
                                </div>
                            </div>
                        </div>
                    </li>
                    <li class="oo-toggle-content  content accordian-input-container oo-input-container with-learn-more product-group ">
                        <form:radiobutton path="lostStolenOptions" id="lostStolenOption2" value="newCard"/>
                        <label for="lostStolenOption2"><spring:message
                                code="${pageName}.lostStolenOption2.label"/>
                            <a href="javascript:void(0)" class="learn-more-toggle always-visible no-controls"
                               aria-expanded="true">
                                <span class="icon info-icon"></span>
                            </a>
                        </label>

                        <div class="box  info-message start-hidden ">
                            <div class="instructional-container radio-accordian-list csc-module">
                                <div class="content">
                                    <ol>
                                        <li>
                                            <div class="item-content">
                                                <p><to:textPlain id="lostStolenOption2.bulletPoint1"/></p>
                                            </div>
                                        </li>
                                        <li>
                                            <div class="item-content">
                                                <p><to:textPlain id="lostStolenOption2.bulletPoint2"/></p>
                                            </div>
                                        </li>
                                        <li>
                                            <div class="item-content">
                                                <p><to:textPlain id="lostStolenOption1.bulletPoint3"/></p>
                                            </div>
                                        </li>
                                    </ol>
                                </div>
                            </div>
                        </div>
                    </li>
                    <li class="oo-toggle-content  content accordian-input-container oo-input-container with-learn-more product-group ">
                        <form:radiobutton path="lostStolenOptions" id="lostStolenOption3" value="refundCard"/>
                        <label class="" for="lostStolenOption3"><spring:message code="${pageName}.lostStolenOption3.label"/>
                            <a href="javascript:void(0)" class="learn-more-toggle always-visible no-controls"
                               aria-expanded="true">
                                <span class="icon info-icon"></span>
                            </a>
                        </label>

                        <div class="box  info-message start-hidden ">
                            <div class="instructional-container radio-accordian-list csc-module">
                                <div class="content">
                                    <ol>
                                        <li>
                                            <div class="item-content">
                                                <p><to:textPlain id="lostStolenOption3.bulletPoint1"/></p>
                                            </div>
                                        </li>
                                        <li>
                                            <div class="item-content">
                                                <p><to:textPlain id="lostStolenOption1.bulletPoint3"/></p>
                                            </div>
                                        </li>
                                    </ol>
                                </div>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
            <form:errors path="lostStolenOptions" cssClass="field-validation-error"/>
            <div class="button-container clearfix">
            	<to:secondaryButton id="cancel" buttonType="submit" targetAction="<%= PageParameterValue.CANCEL %>"/>
                <to:primaryButton id="continue" buttonType="submit" targetAction="<%= PageParameterValue.CONTINUE %>"/>
            </div>
        </div>
        <tiles:insertAttribute name="myAccount"/>
    </div>
</form:form>

