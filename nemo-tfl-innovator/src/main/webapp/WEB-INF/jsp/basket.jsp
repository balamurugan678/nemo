<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

<nemo-tfl:breadcrumbs pageNames='<%=new String[]{Page.GET_OYSTER_CARD}%>'/>
<to:areaheader title="chooseTicket.heading"/>
<to:header id="orderAdmin" />
<br />
<div class="r">
    <div>
        <div>
            <to:paragraph id="warning"/>
        </div>
        <form:form action="<%= PageUrl.BASKET %>" commandName="<%=PageCommand.CART%>"
                   cssClass="form-with-tooltips">
            <div class="box borderless">
                <nemo-tfl:radioButtonList id="ticketType" path="ticketType" selectList="${basketTicketTypes}"
                                          mandatory="true" selectedValue="${cartCmd.ticketType}"/>
                <form:errors path="ticketType" cssClass="field-validation-error"/>
                <to:paragraph id="lower"/>
                <div class="form-with-tooltips">
				    <div class="button-container clearfix">
				       <to:secondaryButton id="cancel" buttonType="submit" targetAction="<%= PageParameterValue.CANCEL %>"/>
				       <to:primaryButton id="continue" buttonType="submit" targetAction="<%= PageParameterValue.CHOOSE_BASKET %>"/>
				    </div>
				    <to:secondaryButton id="shoppingBasket" buttonType="submit" targetAction="<%= PageParameterValue.SHOPPING_BASKET %>"/>
				</div>
            </div>
        </form:form>
    </div>
</div>
