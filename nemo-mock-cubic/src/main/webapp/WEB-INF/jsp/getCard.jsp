<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>Get Card</h1>

<form:form  action="getCard.htm" modelAttribute="getCardCmd">
    <div style="clear: both; float: left;">
    <!-- button type="submit" name="targetAction" value="getCard"><spring:message code="getCardDetails.button.label"/></button -->
    <button type="submit" name="targetAction" value="sendXmlRequest"><spring:message code="getCardDetails.button.label"/></button>
    </div>
    
    <div style="clear: both;"></div>
    <div style="float: left;">
        <h2>Card Details 1</h2>
        <table >
             <tr>
                <td><label>PrestigeID</label></td>
                <td><form:input path="prestigeId"/></td>
            </tr>
            <tr>
                <form:select path="cardAction" items="${cardActions}"></form:select>
            </tr>
            <tr>
                <td><label>Card Capability</label></td>
                <td><form:textarea path="response"  cols="100" rows="45" /></td>
            </tr>
        </table>
    </div>
    
</form:form>