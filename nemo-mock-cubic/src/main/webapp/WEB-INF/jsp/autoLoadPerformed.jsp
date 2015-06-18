<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h2>Auto Load Performed</h2>

<form:form method="post" action="AutoLoadPerformed.htm" modelAttribute="Cmd">
    <table>
        <tr>
            <th>Prestige ID</th>
            <th>Pick Up Location</th>
            <th>Bus Route ID</th>
            <th>Pick Up Time</th>
            <th>Auto Load Configuration</th>
            <th>Top Up Amount Added</th>
            <th>Currency</th>
        </tr>
        <c:forEach items="${Cmd.autoLoadsPerformed}" var="autoLoadPerformed" varStatus="status">
            <tr>
                <td>
                    <form:input path="autoLoadsPerformed[${status.index}].prestigeId" size="13" maxlength="13"/>
                </td>
                <td>
                    <form:input path="autoLoadsPerformed[${status.index}].pickUpLocation" size="4" maxlength="4"/>
                </td>
                <td>
                    <form:input path="autoLoadsPerformed[${status.index}].busRouteId" size="5" maxlength="5"/>
                </td>
                <td>
                    <form:input path="autoLoadsPerformed[${status.index}].pickUpTime" size="20" maxlength="20"
                                placeholder="DD-Mon-YYYY HH:MM:SS"/>
                </td>
                <td>
                    <form:input path="autoLoadsPerformed[${status.index}].autoLoadConfiguration" size="1" maxlength="1"/>
                </td>
                <td>
                    <form:input path="autoLoadsPerformed[${status.index}].topUpAmountAdded" size="8" maxlength="8"/>
                </td>
                <td>
                    <form:input path="autoLoadsPerformed[${status.index}].currency" size="1" maxlength="1"/>
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="11">
                <button type="submit" name="targetAction" value="addRows">Add Rows</button>
                <button type="submit" name="targetAction" value="getCsv">Get CSV</button>
            </td>
        </tr>
    </table>
</form:form>