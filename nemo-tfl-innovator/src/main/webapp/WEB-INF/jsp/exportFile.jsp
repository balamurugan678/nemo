<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

<div>
    <to:header id="exportFile"/>
    <form:form commandName="<%=PageCommand.EXPORT_FILE%>" action="<%= PageUrl.INV_EXPORT_FILE %>">
        <div>
            <nemo-tfl:selectList id="exportFileType" path="exportFileType" selectList="${exportFileTypes}" mandatory="true"
                                 showPlaceholder="false"/>
        </div>

        <div id="button-area">
            <to:button id="export" buttonType="submit"/>
            <to:loadingIcon/>
        </div>
    </form:form>
</div>
