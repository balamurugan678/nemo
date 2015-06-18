<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="com.novacroft.nemo.mock_payment_gateway.cyber_source.constant.CommandName" %>
<%@ page import="com.novacroft.nemo.mock_payment_gateway.cyber_source.constant.RequestName" %>
<form:form action="<%= RequestName.POST_SETTINGS%>" commandName="<%= CommandName.COMMAND %>" method="post">
    <h1>POST Settings</h1>
    <table>
        <tr>
            <td>Alive :</td>
            <td>
                <form:checkbox path="alive"/>
            </td>
        </tr>
    </table>
    <form:button name="targetAction" value="cancel">Cancel</form:button>
    <form:button name="targetAction" value="update">Update</form:button>
</form:form>