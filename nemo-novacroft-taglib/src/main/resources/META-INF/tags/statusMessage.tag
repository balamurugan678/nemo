<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>

<%@attribute name="messageCode" required="true" type="java.lang.String" %>

<c:if test="${messageCode != ''}">

    <c:set var="dialogTitleCode"><%= ContentCode.STATUS_MESSAGE.headingCode()%>
    </c:set>
    <c:set var="dialogTitle"><spring:message code="${dialogTitleCode}"/></c:set>

    <script type="text/javascript">
        <!--
        var dialogDisplayTimeInSeconds = 2.5;
        $(function () {
            $("#statusMessage").dialog({ hide: "fade", modal: true, width: 512, height: 256 });
        });
        setTimeout(function () {
            $("#statusMessage").dialog('destroy');
        }, dialogDisplayTimeInSeconds * 1000);
        //-->
    </script>
    <noscript>
        <div id="statusMessageAlt" class="r" title="${dialogTitle}">
            <div>
                <p><spring:message code="${messageCode}"/></p>
            </div>
        </div>
    </noscript>
    <div id="statusMessage" class="r" title="${dialogTitle}" style="display:none;">
        <div>
            <p>
                <span class="ui-icon ui-icon-circle-check" style="float: left; margin: 0 7px 50px 0;"></span>
                <spring:message code="${messageCode}"/>
            </p>
        </div>
    </div>
</c:if>
