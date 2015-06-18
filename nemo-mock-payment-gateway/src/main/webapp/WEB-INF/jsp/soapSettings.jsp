<%@ page import="com.novacroft.nemo.mock_payment_gateway.cyber_source.constant.CommandName" %>
<%@ page import="com.novacroft.nemo.mock_payment_gateway.cyber_source.constant.RequestName" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<form:form action="<%= RequestName.SOAP_SETTINGS %>" commandName="<%= CommandName.COMMAND %>" method="post">
    <h1>SOAP Settings</h1>
    <table>
        <tr>
            <td>Decision :</td>
            <td>
                <form:select path="decision">
                    <form:option value="ACCEPT"/>
                    <form:option value="ERROR"/>
                    <form:option value="REJECT"/>
                    <form:option value="REVIEW"/>
                </form:select>
            </td>
        </tr>
        <tr>
            <td>Reason Code :</td>
            <td><form:input path="reasonCode" size="3" maxlength="3" placeholder="999"/></td>
        </tr>
        <tr>
            <td>Missing Fields :</td>
            <td><form:input path="missingFields" size="32" maxlength="1024" placeholder="field1,field2,..."/></td>
        </tr>
        <tr>
            <td>Invalid Fields :</td>
            <td><form:input path="invalidFields" size="32" maxlength="1024" placeholder="field1,field2,..."/></td>
        </tr>
    </table>
    <form:button name="targetAction" value="cancel">Cancel</form:button>
    <form:button name="targetAction" value="update">Update</form:button>
</form:form>
