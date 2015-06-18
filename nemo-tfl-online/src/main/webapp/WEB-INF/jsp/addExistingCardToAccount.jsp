<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<nemo-tfl:breadcrumbs pageNames='<%=new String[]{Page.DASHBOARD, Page.ADD_EXISTING_CARD_TO_ACCOUNT}%>'/>

<to:headLine/>

<div class="r">
    <div class="main">
        <div class="instructional-container register-oyster csc-module">
            <div class="content">
                <form:form commandName="<%= PageCommand.CARD %>" cssClass="oo-responsive-form "
                           action="<%= PageUrl.ADD_EXISTING_CARD_TO_ACCOUNT %>">
                    <div class="top-level-message-container"></div>
                    <input type="hidden" name="jsEnabled" value='false'>
                    <ol class="oo-module">
                        <li>
                            <div class="item-content first-column">
                                <h3 class="styled-heading emphasis"><spring:message
                                        code='${pageName}.purchaseNewOysterCard.heading'/></h3>

                                <p><spring:message code='${pageName}.purchaseNewOysterCard.text'/></p>
                            </div>
                            <div class='with-message oo-drop-down-info expandable-box '>
                                <a class="text always-visible first-column no-controls more-info-accordian text-only"
                                   href="javascript:void(0)"> <spring:message
                                        code='${pageName}.whereCanIGetOysterCard.heading'/>
                                    <span class="icon tooltip-icon info-i-icon"></span>
                                </a>

                                <div class="message-wrapper oo-tooltip-container inline-message info-message start-hidden with-indicator">
                                    <p class="tight-margin"><spring:message code='${pageName}.whereCanIGetOysterCard.text'/></p>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="item-content first-column">
                                <h3 class="styled-heading emphasis"><spring:message
                                        code='${pageName}.addNewOysterCard.heading'/></h3>
                            </div>
                        </li>
                    </ol>
                    <div class='form-input-container'>
                        <div class="csc-module card-images unregistered-card oo-module">
                            <span class="highlight-number"></span>
                            <img src="tfl-ux/assets/oyster/unregistered_card.png" alt="unregistered card"
                                 class="card-image unregistered-image "/>
                            <img src="tfl-ux/assets/oyster/registered_card.png" alt="registered card"
                                 class="card-image registered-image"/>
                        </div>
                        <div class="csc-module oyster-number-input-container">
                            <div class='form-control-wrap text-input '>
                                <!--contains the label, input and error messaging-->
                                <to:text id="cardNumber" mandatory="true" labelCssClass="emphasis"
                                         inputCssClass="shaded-input"/>
                            </div>
                        </div>
                    </div>
                    <div class="message-container reminder-container">
                        <h4 class="title"><spring:message code="pleaseNote.text"/></h4>

                        <div class="content">
                            <spring:message code='${pageName}.disclaimerPartOne.text'/>
                            <a href='<spring:message code='${pageName}.termsAndConditions.url' />'><spring:message
                                    code='${pageName}.termsAndConditions.link'/> </a>
                            <spring:message code='${pageName}.disclaimerPartTwo.text'/>
                        </div>
                    </div>
                    <div class="button-container clearfix">
                        <to:primaryButton id="saveChange" buttonType="submit"
                                          targetAction="<%= PageParameterValue.SAVE_CHANGES %>"/>
                        <to:secondaryButton id="cancel" buttonType="submit" 
                                            targetAction="<%= PageParameterValue.CANCEL%>"/>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
    <tiles:insertAttribute name="myAccount"/>
</div>
    
