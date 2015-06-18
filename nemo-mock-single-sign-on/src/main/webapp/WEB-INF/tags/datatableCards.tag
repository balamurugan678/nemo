<%@ tag language="java" pageEncoding="ISO-8859-1" %>
<%@include file="/WEB-INF/jspf/tagCommon.jspf" %>
<script type="text/javascript">
  var cardAdmin = '<spring:message code="${pageName}${pageName ne ' ' ? '.' : ''}cardAdmin.url"/>';
</script>

<div class="dataTable-container">
<table class="grid" cellpadding=0 cellspacing=1 id="cards">
<thead>
<tr class="grid_heading">
    <th>Id</th>
    <th>Oyster<br>Number</th>
    <th>Status</th>
    <th>View Details</th>
    <th>Card Admin</th>
    <th>Refund</th>
</tr>
</thead>
<tbody>
<c:forEach items="${customerCards}" var="card" varStatus="status">

	<c:choose> 
		<c:when test='${card.hotlistReason.id > 0}'>
		      <c:set var="hotlisted" value="hotlisted" scope="page"/>
		      <c:set var="tooltip" value="title='This card is hotlisted'" scope="page"/>
		      <c:set var="cardstatus" value="Hotlisted" scope="page"/>
		</c:when>
		<c:otherwise>
		 	<c:set var="hotlisted" value="" scope="page"/>
		 	<c:set var="tooltip" value="" scope="page"/>
		 	<c:set var="cardstatus" value="" scope="page"/>
		</c:otherwise>
	</c:choose>


    <tr class="grid_row" id="row${status.count}">
        <td class="${hotlisted}" ${tooltip}>${card.id}</td>
        <td id="cardNumber${status.count}">${card.cardNumber}</td>
        <td>${cardstatus}</td>
        <td><to:imageButton buttonType="button" id="checkFailed"  secondaryId="checkFailed${status.count}" targetAction="${card.cardNumber}" buttonCssClass="checkFailedCard" imageCssClass="button-check" /></td>
        <td><to:anchor id="cardAdmin" cssClass="button cardAdmin" href="?id=${card.cardNumber}" secondaryId="cardAdmin${status.count}"></to:anchor></td>
        <td><nemo-tfl:selectList path="" id="refunds${card.cardNumber}" selectList="${refunds}" showLabel="false" /></td>
    </tr>
</c:forEach>
    
</tbody>
</table>

</div>