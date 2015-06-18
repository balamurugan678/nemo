<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="com.novacroft.nemo.mock_cubic.controller.EditAutoLoadChangeController" %>

<h2>Edit Auto Load Change</h2>

<form:form method="post" action="<%= EditAutoLoadChangeController.EDIT_URL %>"
           modelAttribute="<%= EditAutoLoadChangeController.COMMAND_NAME %>">
    <table>
        <tr>
            <td>Prestige ID</td>
            <td>
                <input name="prestigeId" value="${Cmd.prestigeId}" size="12">
                <span class="hint">12 digit card number</span>
            </td>
        </tr>
        <tr>
            <td><h3>Success Response</h3></td>
            <td>
                <span class="hint">Enter either a success response or a fail response</span>
            </td>
        </tr>
        <tr>
            <td>Request Sequence Number</td>
            <td>
                <input name="successResponse.requestSequenceNumber" value="${Cmd.successResponse.requestSequenceNumber}"
                       size="10"/>
                <span class="hint">Will be generated if blank</span>
            </td>
        </tr>
        <tr>
            <td>Auto Load State</td>
            <td>
                <input name="successResponse.autoLoadState" value="${Cmd.successResponse.autoLoadState}" size="1"/>
                <span class="hint">Will be copied from request if blank</span>
            </td>
        </tr>
        <tr>
            <td>Pick Up Location</td>
            <td>
                <input name="successResponse.pickupLocation" value="${Cmd.successResponse.pickupLocation}" size="4"/>
                <span class="hint">Will be copied from request if blank</span>
            </td>
        </tr>
        <tr>
            <td>Available Slots</td>
            <td>
                <input name="successResponse.availableSlots" value="${Cmd.successResponse.availableSlots}" size="10"/>
            </td>
        </tr>
        <tr>
            <td><h3>Fail Response</h3></td>
            <td>
                <span class="hint">Enter either a success response or a fail response</span>
            </td>
        </tr>
        <tr>
            <td>Error Code</td>
            <td>
                <input name="failResponse.errorCode" value="${Cmd.failResponse.errorCode}" size="5">
                <span class="hint">Number</span>
            </td>
        </tr>
        <tr>
            <td>Error Description</td>
            <td>
                <input name="failResponse.errorDescription" value="${Cmd.failResponse.errorDescription}" size="50">
                <span class="hint">Description</span>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <button type="submit">Save</button>
            </td>
        </tr>
    </table>
</form:form>
