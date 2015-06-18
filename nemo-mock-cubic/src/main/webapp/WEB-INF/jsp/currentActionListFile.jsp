<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h2>Current Action List</h2>

<form:form method="post" action="CurrentActionListFile.htm" modelAttribute="Cmd">
    <table>
        <tr>
            <th>Prestige ID</th>
            <th>Request Sequence Number</th>
            <th>Product Code</th>
            <th>Fare Paid</th>
            <th>Currency</th>
            <th>Payment Method Code</th>
            <th>Pre-Pay Value</th>
            <th>Pick Up Location</th>
            <th>PPT Start Date</th>
            <th>PPT Expiry Date</th>
            <th>Autoload State</th>
        </tr>
        <c:forEach items="${Cmd.currentActions}" var="currentAction" varStatus="status">
            <tr>
                <td>
                    <input type="number" pattern="[0-9]+" name="currentActions[${status.index}].prestigeId"
                           value="${currentAction.prestigeId}" size="13"/>
                </td>
                <td>
                    <input type="number" pattern="[0-9]+" name="currentActions[${status.index}].requestSequenceNumber"
                           value="${currentAction.requestSequenceNumber}" size="10"/>
                </td>
                <td>
                    <input type="number" pattern="[0-9]*" name="currentActions[${status.index}].productCode"
                           value="${currentAction.productCode}" size="10"/>
                </td>
                <td>
                    <input name="currentActions[${status.index}].farePaid" value="${currentAction.farePaid}" size="8"/>
                </td>
                <td>
                    <input name="currentActions[${status.index}].currency" value="${currentAction.currency}" size="1"/>
                </td>
                <td>
                    <input name="currentActions[${status.index}].paymentMethodCode"
                           value="${currentAction.paymentMethodCode}"
                           size="5"/>
                </td>
                <td>
                    <input name="currentActions[${status.index}].prePayValue" value="${currentAction.prePayValue}"
                           size="8"/>
                </td>
                <td>
                    <input name="currentActions[${status.index}].pickUpLocation" value="${currentAction.pickUpLocation}"
                           size="4"/>
                </td>
                <td>
                    <input type="date" name="currentActions[${status.index}].pptStartDate"
                           value="${currentAction.pptStartDate}" size="11" placeholder="DD-Mon-YYYY"/>
                </td>
                <td>
                    <input type="date" name="currentActions[${status.index}].pptExpiryDate"
                           value="${currentAction.pptExpiryDate}" size="11" placeholder="DD-Mon-YYYY"/>
                </td>
                <td>
                    <input name="currentActions[${status.index}].autoLoadState" value="${currentAction.autoLoadState}"
                           size="1"/>
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