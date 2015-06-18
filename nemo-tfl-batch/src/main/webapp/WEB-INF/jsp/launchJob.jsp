<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>


<div>
    <h1>Launch Job</h1>
    <form:form action="LaunchJob.htm" commandName="cmd">
        <p>${cmd.message}</p>

        <h2>Jobs</h2>
        <table>
            <tr>
                <td><form:button name="targetAction" value="importCurrentActionListCubicFile">Request</form:button></td>
                <td>Import Current Action List CUBIC Files</td>
            </tr>
            <tr>
                <td><form:button name="targetAction" value="importAdHocDistributionCubicFile">Request</form:button></td>
                <td>Import Ad-Hoc Distribution CUBIC Files</td>
            </tr>
            <tr>
                <td><form:button name="targetAction" value="importAutoLoadChangeCubicFile">Request</form:button></td>
                <td>Import Auto Load Change CUBIC Files</td>
            </tr>
            <tr>
                <td><form:button name="targetAction" value="importAutoLoadPerformedCubicFile">Request</form:button></td>
                <td>Import Auto Load Performed CUBIC Files</td>
            </tr>
            <tr>
                <td><form:button name="targetAction" value="runJourneyHistoryWeeklyEmailStatement">Request</form:button></td>
                <td>Journey History Weekly Email Statement</td>
            </tr>
            <tr>
                <td><form:button name="targetAction" value="runJourneyHistoryMonthlyEmailStatement">Request</form:button></td>
                <td>Journey History Monthly Email Statement</td>
            </tr>
            <tr>
                <td><form:button name="targetAction" value="importFscChequesProduced">Request</form:button></td>
                <td>Import Financial Services Centre Cheques Produced File</td>
            </tr>
            <tr>
                <td><form:button name="targetAction" value="importFscChequeSettlements">Request</form:button></td>
                <td>Import Financial Services Centre Cheque Settlements File</td>
            </tr>
            <tr>
                <td><form:button name="targetAction" value="importFscBacsSettlements">Request</form:button></td>
                <td>Import Financial Services Centre BACS Settlements File</td>
            </tr>
            <tr>
                <td><form:button name="targetAction" value="importFscOutdatedCheques">Request</form:button></td>
                <td>Import Financial Services Centre Outdated Cheques File</td>
            </tr>
            <tr>
                <td><form:button name="targetAction" value="importFscFailedBacsPayments">Request</form:button></td>
                <td>Import Financial Services Centre BACS Failures File</td>
            </tr>
            <tr>
                <td><form:button name="targetAction" value="exportHotlistcardRequestFile">Request</form:button></td>
                <td>Export Hotlist Card Request File</td>
            </tr>
            <tr>
                <td><form:button name="targetAction" value="sendMessages">Request</form:button></td>
                <td>Send Messages</td>
            </tr>

            <tr>
                <td><form:button name="targetAction"
                                 value="importPrePaidTicketPriceData">Load PrePaid Ticket Data </form:button></td>
                <td><form:input path="${cmd.priceEffectiveDate}" id="priceEffectiveDate" name="priceEffectiveDate"/></td>
            </tr>
        </table>

        <script src="scripts/dateCalendar.js"></script>
    </form:form>
</div>