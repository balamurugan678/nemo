<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

<nemo-tfl:breadcrumbs pageNames='<%=new String[]{Page.OPEN_ACCOUNT}%>'/>
<to:areaheader title="openaccount.heading"/>

<div class="r">
    <div>
        <to:paragraph id="upper"/>
        <form:form action="<%= PageUrl.OPEN_ACCOUNT %>" commandName="<%=PageCommand.CART%>"
                   cssClass="form-with-tooltips">
            <div class="box borderless">
                <to:head2 id="carddetails"/>
                <to:text id="cardNumber" mandatory="true"/>
                <to:buttons targetAction="<%= PageParameterValue.VALIDATE_CARD_NUMBER %>"/>
            </div>
        </form:form>
    </div>
</div>
