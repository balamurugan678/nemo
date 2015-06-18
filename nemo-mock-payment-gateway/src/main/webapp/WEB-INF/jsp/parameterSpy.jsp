<%@ page import="com.novacroft.nemo.mock_payment_gateway.cyber_source.constant.CommandName" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<style>
    tr:nth-child(even) {
        background-color: #ffffff;
    }

    tr:nth-child(odd) {
        background-color: #c0c0c0;
    }
</style>
<div>
    <h1>Parameter Spy</h1>

    <% Map<String, String> parameterMap = (Map<String, String>) request.getAttribute(CommandName.PARAMETER_MAP); %>

    <div>
        <table>
            <%
                List<String> parameterNames = new ArrayList(parameterMap.keySet());
                Collections.sort(parameterNames);
                for (String parameterName : parameterNames) {
            %>
            <tr>
                <td>
                    <label>
                        <%=parameterName%>:
                    </label>
                </td>
                <td><%=parameterMap.get(parameterName)%>
                </td>
            </tr>
            <% } %>
        </table>
    </div>

</div>