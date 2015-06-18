<%@ page import="com.novacroft.nemo.mock_payment_gateway.cyber_source.command.PostCmd" %>
<%@ page import="com.novacroft.nemo.mock_payment_gateway.cyber_source.constant.CommandName" %>
<%@ page import="com.novacroft.nemo.mock_payment_gateway.cyber_source.constant.PostReplyParameterName" %>
<%@ page import="com.novacroft.nemo.mock_payment_gateway.cyber_source.constant.PostRequestParameterName" %>
<%@ page import="com.novacroft.nemo.mock_payment_gateway.cyber_source.constant.RequestName" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<% PostCmd postCmd = (PostCmd) request.getAttribute(CommandName.COMMAND); %>

<h1>CyberSource HTML POST Simulator</h1>

<h2>Standard Transaction</h2>

<form:form id="cyber-source-post-form" commandName="<%= CommandName.COMMAND %>">
    <div id="accordion">
        <h3>Request</h3>

        <div>
            <table>
                <% for (PostRequestParameterName postRequestParameterName : PostRequestParameterName.values()) { %>
                <tr>
                    <td>
                        <label>
                            <%=postRequestParameterName.code()%>:
                        </label>
                    </td>
                    <td><%=postCmd.getRequestModelAttribute(postRequestParameterName.code())%>
                    </td>
                </tr>
                <% } %>
            </table>
        </div>

        <h3>Configuration</h3>

        <div>
            <table>
                <tr>
                    <td><label>Custom Response URL : </label></td>
                    <td>${Cmd.postProfile.customResponseUrl}</td>
                </tr>
            </table>
        </div>

        <h3>Reply</h3>

        <div>
            <table>
                <% for (PostReplyParameterName postReplyParameterName : PostReplyParameterName.values()) { %>
                <tr>
                    <td>
                        <label>
                            <%= postReplyParameterName.code() %>:
                        </label>
                    </td>
                    <td>
                        <% if (postReplyParameterName.hasOptions()) { %>
                        <select id="<%=postReplyParameterName.code()%>" name="<%=postReplyParameterName.code()%>">
                            <% for (String option : postReplyParameterName.options()) { %>
                            <option><%= option%>
                            </option>
                            <% } %>
                            <option></option>
                        </select>
                        <% } else { %>
                        <input type="text" id="<%=postReplyParameterName.code()%>" name="<%=postReplyParameterName.code()%>"
                               value="<%=postCmd.getReplyModelAttribute(postReplyParameterName.code())%>"
                               size="<%= postReplyParameterName.size()%>" maxlength="1024">
                        <% } %>
                    </td>
                </tr>
                <% } %>
            </table>
        </div>
    </div>
    <button id="sign-button" type="button">Update Signature</button>
    <button type="submit" formaction="${Cmd.replyUrl}" formmethod="post">Reply</button>
</form:form>

<script>
    var signUrl = "/nemo-mock-payment-gateway/" + "<%=RequestName.GATEWAY_REQUEST + "?targetAction=sign"%>";
    $(document).ready(function () {
        $("#sign-button").click(function () {
            $.get(signUrl, $("#cyber-source-post-form").serialize(), function (response) {
                $("#signature").val(response.signature);
                $("#signed_date_time").val(response.signed_date_time);
            }, "json");
        });
    });
</script>
