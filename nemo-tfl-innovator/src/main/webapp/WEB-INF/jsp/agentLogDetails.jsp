<%@ page import="com.novacroft.nemo.tfl.common.command.impl.AgentLogSearchCmdImpl" %>
<%@ page import="com.novacroft.nemo.tfl.common.util.JobLogUtil" %>
<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>
<to:page name="agentLogDetails"/>

<% AgentLogSearchCmdImpl cmd = (AgentLogSearchCmdImpl) request.getAttribute("agentLogSearchCmd"); %>

<form:form commandName="<%= PageCommand.AGENTLOG_SEARCH %>" cssClass="form-with-tooltips">
    <div id="jobLogDetails">
        <to:header id="jobLogDetails"/>
        <to:label id="jobName"/><span class="info"><c:out value="${agentLogSearchCmd.jobName}"/></span><br/>
        <to:label id="fileName"/><span class="info"><c:out value="${agentLogSearchCmd.fileName}"/></span><br/>
        <to:label id="startedAt"/><span class="info"><fmt:formatDate value="${agentLogSearchCmd.startedAt}"
                                                                     pattern="<%=DateConstant.SHORT_DATE_AND_TIME_TO_SECOND_PATTERN%>"/></span><br/>
        <to:label id="endedAt"/><span class="info"><fmt:formatDate value="${agentLogSearchCmd.endedAt}"
                                                                   pattern="<%=DateConstant.SHORT_DATE_AND_TIME_TO_SECOND_PATTERN%>"/></span><br/>
        <to:label id="status"/><span class="info"><c:out value="${agentLogSearchCmd.status}"/></span><br/>
        <to:label id="log"/>
        <div class="log-file-box"><%=JobLogUtil.formatLogAsHtml(cmd.getLog())%>
        </div>
        <to:hidden id="id"/>
    </div>

    <div id="toolbar">
        <div class="right">
            <to:primaryButton id="cancel" buttonCssClass="rightalignbutton" buttonType="submit"
                              targetAction="<%= PageParameterValue.CANCEL %>"/>

        </div>
    </div>
</form:form>

<script type="text/javascript">
    var pageName = "<%=Page.INV_AGENTLOG_DETAILS %>";
    var sAddress = "${pageContext.request.contextPath}";
</script>

<script src="scripts/agentLogDetails.js"></script>