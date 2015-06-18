<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

<nemo-tfl:breadcrumbs pageNames='<%=new String[]{Page.DASHBOARD, Page.TOP_UP_TICKET}%>'/>
<to:areaheader title="heading"/>

<div class="r">
    <form:form action="<%= PageUrl.AUTO_TOP_UP_PURCHASE %>" commandName="<%=PageCommand.CART%>"
               cssClass="form-with-tooltips">
        <div class="box borderless">
            <to:paragraph id="upper"/>
            <to:paragraph id="productInfo"/>
            <to:paragraph id="lower"/>
            <nemo-tfl:selectList id="selectStation" path="stationId" selectList="${locations}" mandatory="true"
                                 selectedValue="${cartCmd.stationId}" useManagedContentForMeanings="false"/>
            <form:errors path="stationId" cssClass="field-validation-error"/>
            <br/>
            <to:buttons targetAction="<%= PageParameterValue.CONTINUE %>"></to:buttons>
        </div>
    </form:form>
</div>

