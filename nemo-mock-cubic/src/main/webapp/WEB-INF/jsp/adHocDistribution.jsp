<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h2>Ad Hoc Distribution</h2>

<form:form method="post" action="AdHocDistribution.htm" modelAttribute="Cmd">
    <table>
        <tr>
            <th>Prestige ID</th>
            <th>Pick Up Location</th>
            <th>Pick Up Time</th>
            <th>Request Sequence Number</th>
            <th>Product Code</th>
            <th>PPT Start Date</th>
            <th>PPT Expiry Date</th>
            <th>Pre-Pay Value</th>
            <th>Currency</th>
            <th>Status Of Attempted Action</th>
            <th>Failure Reason Code</th>
        </tr>
        <c:forEach items="${Cmd.adHocDistributions}" var="adHocDistribution" varStatus="status">
            <tr>
                <td>
                    <input type="number" pattern="[0-9]+" name="adHocDistributions[${status.index}].prestigeId"
                           value="${adHocDistribution.prestigeId}" size="13"/>
                </td>
                <td>
                    <input name="adHocDistributions[${status.index}].pickUpLocation" value="${adHocDistribution.pickUpLocation}"
                           size="4"/>
                </td>
                <td>
                    <input name="adHocDistributions[${status.index}].pickUpTime" value="${adHocDistribution.pickUpTime}"
                           size="20" placeholder="DD-Mon-YYYY HH:MM:SS"/>
                </td>
                <td>
                    <input type="number" pattern="[0-9]+" name="adHocDistributions[${status.index}].requestSequenceNumber"
                           value="${adHocDistribution.requestSequenceNumber}" size="10"/>
                </td>
                <td>
                    <input type="number" pattern="[0-9]*" name="adHocDistributions[${status.index}].productCode"
                           value="${adHocDistribution.productCode}" size="10"/>
                </td>
                <td>
                    <input type="date" name="adHocDistributions[${status.index}].pptStartDate"
                           value="${adHocDistribution.pptStartDate}" size="11" placeholder="DD-Mon-YYYY"/>
                </td>
                <td>
                    <input type="date" name="adHocDistributions[${status.index}].pptExpiryDate"
                           value="${adHocDistribution.pptExpiryDate}" size="11" placeholder="DD-Mon-YYYY"/>
                </td>
                <td>
                    <input name="adHocDistributions[${status.index}].prePayValue" value="${adHocDistribution.prePayValue}"
                           size="8"/>
                </td>
                <td>
                    <input name="adHocDistributions[${status.index}].currency" value="${adHocDistribution.currency}" size="1"/>
                </td>
                <td>
                    <input name="adHocDistributions[${status.index}].statusOfAttemptedAction"
                           value="${adHocDistribution.statusOfAttemptedAction}" size="10"/>
                </td>
                <td>
                    <input name="adHocDistributions[${status.index}].failureReasonCode"
                           value="${adHocDistribution.failureReasonCode}" size="5"/>
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