<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>


<a href="content/addContent.htm" class="button">Add Content</a>
<table id="contentTable">
    <thead>
        <tr>
            <th>Locale</th>
            <th>Code</th>
            <th>Content</th>
            <th></th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${content}" var="contentRecord">
        <tr>
        <form action="AddContent.htm" method="POST">
            <td>${contentRecord.locale}</td>
            <td>${contentRecord.code}</td>
            <td>${contentRecord.content}</td>
            <td><button type="submit">Edit</button></td>
            <input type="hidden" name="locale" value="${contentRecord.locale}" />
            <input type="hidden" name="code" value="${contentRecord.code}" />
        </form>
        </tr>
        </c:forEach>
    </tbody>
</table>
<script src="scripts/content.js"></script>