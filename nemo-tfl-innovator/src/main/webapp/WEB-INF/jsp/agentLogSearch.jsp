<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>
<to:page name="agentLogSearch"/>

<form:form commandName="<%= PageCommand.AGENTLOG_SEARCH %>"  cssClass="form form-with-tooltips" action="<%=PageUrl.INV_AGENTLOG_DETAILS %>">
	<to:header id="agentLogSearch"/>
    <table class="display" summary="Display table">
        <tbody>
 	        <nemo-tfl:selectList id="jobName" path="jobName" selectList="${jobName}" selectedValue="${agentLogSearchCmd.jobName}"/> 
		        <div id="jobStartExecutionDateValue">
		    		<to:textTable id="startedAt" mandatory="true" size="20" row="3" readOnly="true" />
		    	</div>
		        <div id="jobEndExecutionDateValue">
		    		<to:textTable id="endedAt" mandatory="true" size="20" row="4" readOnly="true" />
		    	</div>
        </tbody>
    </table>
    <div id="button-area">
        <to:button id="clearCriteria" />
        <to:imageButton id="search" imageCssClass="button-search"/>
        <to:loadingIcon />
    </div>
    <div class="dataTable-container mTop">
        <div id="search-results">
            <table id="searchresults" class="clickable">
                <thead>
                <tr>
                    <th><spring:message code="agentLogSearch.id"/></th>
                    <th><spring:message code="agentLogSearch.jobName"/></th>
                    <th><spring:message code="agentLogSearch.fileName"/></th>
                    <th><spring:message code="agentLogSearch.startedAt"/></th>
                    <th><spring:message code="agentLogSearch.endedAt"/></th>
                    <th><spring:message code="agentLogSearch.status"/></th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
       </div>
    </div>   
</form:form>
<script src="scripts/agentLogSearch.js"></script>
<script type="text/javascript">
    var sAddress = "${pageContext.request.contextPath}";
    var pageName = "<%=Page.INV_AGENTLOG_SEARCH %>";
    var agentShortDatePattern = "<%=DateConstant.SHORT_DATE_PATTERN%>"; 
    var agentLogDetailURL = "<%=PageUrl.INV_AGENTLOG_DETAILS%>";
    var agentLogSearchURL = "<%=PageUrl.INV_AGENTLOG_SEARCH %>";
    var searchCriteriaNonEmptyFlag = "${agentLogSearchCmd.searchCriteriaNonEmptyFlag}";
</script>