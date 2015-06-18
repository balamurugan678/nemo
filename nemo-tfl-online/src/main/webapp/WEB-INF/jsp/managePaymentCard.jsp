<%@ page import="com.novacroft.nemo.tfl.common.command.impl.PaymentCardCmdImpl" %>
<%@ page import="com.novacroft.nemo.tfl.common.util.AddressFormatUtil" %>
<%@ page import="com.novacroft.nemo.tfl.common.util.PaymentCardUtil" %>
<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

<nemo-tfl:breadcrumbs pageNames='<%=new String[]{Page.DASHBOARD, Page.MANAGE_PAYMENT_CARD}%>'/>

<to:headLine/>

<div class="r">
    <div class="main">

        <c:if test="${empty managePaymentCardCmd.paymentCards}">
            <p><to:textPlain id="noPaymentCardRecordsFound" textCssClass="info-line info"/></p>
        </c:if>

        <c:forEach items="${managePaymentCardCmd.paymentCards}" var="paymentCard" varStatus="paymentCardStatus">
            <div>
                <form:form commandName="<%=PageCommand.MANAGE_PAYMENT_CARD%>" method="post"
                           action="<%=PageUrl.MANAGE_PAYMENT_CARD %>" cssClass="oo-responsive-form">

                    <a href="#" class="link-button">
                        <button id="managePaymentCard" name="<%=PageParameter.TARGET_ACTION %>"
                                value="<%=PageParameterValue.EDIT_PAYMENT_CARD %>" type="submit"
                                class="container link-button csc-card-link">

                            <% PaymentCardCmdImpl paymentCardCmd =
                                    (PaymentCardCmdImpl) pageContext.getAttribute("paymentCard"); %>

                            <c:if test="<%= PaymentCardUtil.isCardExpired(paymentCardCmd.getPaymentCardDTO()) %>">
                                <div class="csc-card-status oo-warning">
                                    <span class="icon error-icon "></span>
                                    <span class="warning-text"><spring:message code="paymentCardExpired.text"/></span>
                                </div>
                            </c:if>

                            <div class="card-summary ">

                                <div class="card-info">
                                    <to:infoLine labelId="paymentCard.obfuscatedPrimaryAccountNumber"
                                                 displayValue="${paymentCard.paymentCardDTO.obfuscatedPrimaryAccountNumber}"/>

                                    <to:infoLine labelId="paymentCard.fullName"
                                                 displayValue="<%= AddressFormatUtil.formatName(paymentCardCmd.getPaymentCardDTO().getFirstName(), paymentCardCmd.getPaymentCardDTO().getLastName())%>"/>

                                    <to:infoLine labelId="paymentCard.expiryDate"
                                                 displayValue="${paymentCard.paymentCardDTO.expiryDate}"/>

                                    <c:if test="${not empty paymentCard.paymentCardDTO.nickName}">
                                        <to:infoLine labelId="PaymentCard.nickName"
                                                     displayValue="${paymentCard.paymentCardDTO.nickName}"/>
                                    </c:if>
                                </div>
                            </div>
                        </button>
                    </a>

                    <input id="paymentCardId" name="paymentCardId" type="hidden" value="${paymentCard.paymentCardDTO.id}"/>

                </form:form>
            </div>
        </c:forEach>

        <form:form commandName="<%=PageCommand.MANAGE_PAYMENT_CARD%>" method="post" action="<%=PageUrl.MANAGE_PAYMENT_CARD %>"
                   cssClass="oo-responsive-form ">
            <div class="button-container clearfix">
                <to:secondaryButton id="cancel" buttonType="submit" targetAction="<%= PageParameterValue.CANCEL %>"/>
            </div>
        </form:form>
    </div>
    <tiles:insertAttribute name="myAccount"/>
</div>
