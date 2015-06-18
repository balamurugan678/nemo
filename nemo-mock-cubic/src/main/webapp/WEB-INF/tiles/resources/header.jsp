<%@ page import="com.novacroft.nemo.mock_cubic.controller.EditAutoLoadChangeController" %>
<style type='text/css'>
    #menu {
        font-weight: bold;
        color: darkgray;
        height: 15px;
        width: 90%;
    }

    #menu > li {
        float: left;
        width: auto;
    }
    
</style>

<div>
    <h1>Nemo CUBIC Simulator</h1>
</div>

<div>
    <ul id="menu">
        <li><a href="Home.htm">Home</a></li>
        <li><a href="#">Batch Files</a>
            <ul>
                <li><a href="AdHocDistribution.htm">CUBIC Ad Hoc Distribution</a></li>
                <li><a href="CurrentActionListFile.htm">CUBIC Current Action List</a></li>
                <li><a href="AutoLoadChange.htm">CUBIC Auto Load Change</a></li>
                <li><a href="AutoLoadPerformed.htm">CUBIC Auto Load Performed</a></li>
            </ul>
        </li>
        <li><a href="#">Service Responses</a>
            <ul>
                <%--<li><a href="AddCardResponse.htm">CUBIC Add Card Response</a></li>--%>
                <li><a href="AddCardResponseNew.htm">CUBIC Add Card Response New</a></li>
                <li><a href="getCard.htm">CUBIC Get Card Test</a></li>
                <%--<li><a href="AddCardPrePayTicketResponse.htm">CUBIC Add Update Card Response</a></li>--%>
                <li><a href="AddCardUpdate.htm">CUBIC Update Card</a></li>

                <li><a href="<%= EditAutoLoadChangeController.EDIT_URL %>">Edit Auto Load Change</a></li>
                <li><a href="<%= EditAutoLoadChangeController.LIST_URL %>">List Auto Load Changes</a></li>
            </ul>
        </li>
        <li><a href="#">Experimental</a>
            <ul>
                <li><a href="station.htm">Station Emulator</a></li>
            </ul>
        </li>
    </ul>
</div>

<script type='text/javascript'>
    $(window).load(function () {
        $("#menu").menu();
    });

</script>
