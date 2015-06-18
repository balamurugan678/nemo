<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

<to:page name="customer"/>
<to:breadlvl2 lvl1="Fares &amp; Payments" lvl2="Get Oyster Card"/>
<to:areaheader title="Get Oyster Card"/>
<script src="scripts/jquery.dataTables.min.js"></script>
<link rel="stylesheet" href="styles/jquery.dataTables.css">
<link rel="stylesheet" href="styles/jquery.dataTables_themeroller.css">
<script type="text/javascript">
    $(document).ready(function () {
        var oTable = $('#example').dataTable({
            "bProcessing": true, "sAjaxSource": "content/findAll.htm", "aoColumns": [
                { "mData": "code" },
                { "mData": "content" },
                { "mData": "locale" },
            ]
        });
        //doFindAll();
    });
    function doFindAll() {
        alert('start');
        $.ajax({
            type: "POST",
            url: "content/findAll.htm",
            //data : "name=" + name + "&education=" + education,
            success: function (response) {
                // we have the response
                $('#info').html(response);
            },
            error: function (e) {
                alert('Error: ' + e);
            }
        });
        alert('finished');
    }
</script>
<div class="r">
    <div id="info">A</div>
    <table id="example">
        <thead>
        <th>Code</th>
        <th>Content</th>
        <th>Locale</th>
        </thead>
        <tbody></tbody>
    </table>

</div>