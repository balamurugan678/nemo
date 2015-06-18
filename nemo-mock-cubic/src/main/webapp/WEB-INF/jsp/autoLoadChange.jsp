<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h2>Auto Load Change</h2>

<form:form method="post" action="AutoLoadChange.htm" modelAttribute="Cmd">
    <table>
        <tr>
            <th>Prestige ID</th>
            <th>Pick Up Location</th>
            <th>Pick Up Time</th>
            <th>Request Sequence Number</th>
            <th>Previous Auto Load Configuration</th>
            <th>New Auto Load Configuration</th>
            <th>Status Of Attempted Action</th>
            <th>Failure Reason Code</th>
        </tr>
        <c:forEach items="${Cmd.autoLoadChanges}" var="autoLoadChange" varStatus="status">
            <tr>
                <td>
                    <input type="number" pattern="[0-9]+" name="autoLoadChanges[${status.index}].prestigeId"
                           value="${autoLoadChange.prestigeId}" size="13"/>
                </td>
                <td>
                    <input name="autoLoadChanges[${status.index}].pickUpLocation" value="${autoLoadChange.pickUpLocation}"
                           size="4"/>
                </td>
                <td>
                    <input name="autoLoadChanges[${status.index}].pickUpTime" value="${autoLoadChange.pickUpTime}"
                           size="20" placeholder="DD-Mon-YYYY HH:MM:SS"/>
                </td>
                <td>
                    <input type="number" pattern="[0-9]+" name="autoLoadChanges[${status.index}].requestSequenceNumber"
                           value="${autoLoadChange.requestSequenceNumber}" size="10"/>
                </td>

                <td>
                    <input name="autoLoadChanges[${status.index}].previousAutoLoadConfiguration"
                           value="${autoLoadChange.previousAutoLoadConfiguration}" size="1"/>
                </td>
                <td>
                    <input name="autoLoadChanges[${status.index}].newAutoLoadConfiguration"
                           value="${autoLoadChange.newAutoLoadConfiguration}" size="1"/>
                </td>
                <td>
                    <input name="autoLoadChanges[${status.index}].statusOfAttemptedAction"
                           value="${autoLoadChange.statusOfAttemptedAction}" size="10"/>
                </td>
                <td>
                    <input name="autoLoadChanges[${status.index}].failureReasonCode"
                           value="${autoLoadChange.failureReasonCode}" size="5"/>
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="11">
                <button type="submit" name="targetAction" value="addRows">Add Rows</button>
                <button type="submit" name="targetAction" value="generateRows">Generate Rows</button>
                <button type="submit" name="targetAction" value="getCsv">Get CSV</button>
            </td>
        </tr>
    </table>
</form:form>