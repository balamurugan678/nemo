<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>
<div class="dataTable-container">
<table class="grid" cellpadding=0 cellspacing=1 id="cards">
<thead>
<tr class="grid_heading">
    <th>Id</th>
    <th>Oyster<br>Number</th>
    <th>View Details</th>
    <th>Card Admin</th>
</tr>
</thead>
<tbody>
<c:forEach items="${customerCards}" var="card">
    <tr class="grid_row">
        <td>${card.id}</td>
        <td>${card.cardNumber}</td>
        <td><to:button buttonType="button" id="checkFailed" /></td>
        <td><to:anchor id="cardAdmin" cssClass="button"></to:anchor>
    </tr>
</c:forEach>
    
</tbody>
</table>

</div>