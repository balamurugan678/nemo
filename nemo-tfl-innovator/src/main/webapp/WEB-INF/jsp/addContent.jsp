<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>
<form:form commandName="<%= PageCommand.CONTENT_CREATOR %>" cssClass="form-with-tooltips" action="<%=Page.INV_ADD_CONTENT %>">
    <to:text id="locale"  />
    <to:text id="parentCode" />
    <to:text id="content" />
    <to:checkbox id="label" /><to:text id="labelValue" />
    <to:checkbox id="tip" /><to:text id="tipValue" />
    <to:checkbox id="placeholder" /><to:text id="placeholderValue" />
    <div class="divider"></div>
    <to:button id="create" buttonType="submit" />
    <to:button id="showSQL" buttonType="button" />
    <div id="sqlArea" >
        <label>SQL:</label>
        <textarea id="sql" cols="100" rows="18">
    
        </textarea >
    </div>
    <div class="clear"></div>
    
</form:form>
<script src="scripts/addContent.js"></script>
<script type="text/javascript">
    var pageName = "${pageName}";
    var sAddress = "${pageContext.request.contextPath}";
</script>