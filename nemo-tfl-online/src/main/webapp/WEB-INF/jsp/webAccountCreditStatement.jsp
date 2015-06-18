<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

<nemo-tfl:breadcrumbs pageNames='<%=new String[]{Page.DASHBOARD, Page.WEB_ACCOUNT_CREDIT_STATEMENT}%>'/>

<to:headLine/>

<div class="r">
    <div class="main">
        <spring:message code="WebAccountCreditStatement.notice.text"/>
        <p><to:label id="currentBalance"/><nemo-tfl:poundSterlingFormat
                amount="${webAccountCreditStatementCmd.currentBalance}"/></p>

        <div class="dataTable-container mTop">
            <table id="WebAccountCreditStatementTransactionTable">
                <thead>
                <tr>
                    <th class="left-aligned"><spring:message code="WebAccountCreditStatement.date.label"/></th>
                    <th class="left-aligned"><spring:message code="WebAccountCreditStatement.item.label"/></th>
                    <th class="left-aligned"><spring:message code="WebAccountCreditStatement.referenceNumber.label"/></th>
                    <th class="right-aligned"><spring:message code="WebAccountCreditStatement.credit.label"/></th>
                    <th class="right-aligned"><spring:message code="WebAccountCreditStatement.debit.label"/></th>
                    <th class="right-aligned"><spring:message code="WebAccountCreditStatement.balance.label"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${webAccountCreditStatementCmd.statementLines}" var="statementLine" varStatus="status">
                    <tr>
                        <td><fmt:formatDate pattern="<%= DateConstant.SHORT_DATE_PATTERN %>"
                                            value="${statementLine.transactionDate}"/></td>
                        <td>${statementLine.item}</td>
                        <td>${statementLine.referenceNumber}</td>
                        <td class="right-aligned">
                            <c:if test="${statementLine.absoluteCreditAmount != null}">
                                <nemo-tfl:poundSterlingFormat amount="${statementLine.absoluteCreditAmount}"/>
                            </c:if>
                        </td>
                        <td class="right-aligned">
                            <c:if test="${statementLine.absoluteDebitAmount != null}">
                                <nemo-tfl:poundSterlingFormat amount="${statementLine.absoluteDebitAmount}"/>
                            </c:if>
                        </td>
                        <td class="right-aligned">
                            <nemo-tfl:poundSterlingFormat amount="${statementLine.cumulativeBalanceAmount}"/>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function () {
        $('#WebAccountCreditStatementTransactionTable').dataTable({
            "bSort": false, "bFilter": false, "bInfo": false, "sPaginationType": "full_numbers"
        });
    });
</script>
