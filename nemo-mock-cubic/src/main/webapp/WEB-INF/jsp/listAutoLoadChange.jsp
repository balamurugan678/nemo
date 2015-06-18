<%@ page import="com.novacroft.nemo.mock_cubic.controller.EditAutoLoadChangeController" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2>List Auto Load Changes</h2>

<div class="dataTable-container mTop">
    <table id="responsesTable">
        <thead>
        <tr>
            <th class="left-aligned">Prestige ID</th>
            <th class="left-aligned">Response</th>
            <th class="left-aligned">Edit</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${Cmd.responses}" var="response" varStatus="status">
            <tr>
                <td>
                    <c:out value="${response.prestigeId}"/>
                </td>
                <td>
                    <c:out value="${response.response}"/>
                </td>
                <td>
                    <c:url value="<%= EditAutoLoadChangeController.EDIT_URL %>" var="editUrl">
                        <c:param name="id" value="${response.id}"/>
                    </c:url>
                    <a href="<c:url value="${editUrl}"/>">Edit</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<script type="text/javascript">
    $(document).ready(function () {
        $('#responsesTable').dataTable();
    });
</script>
