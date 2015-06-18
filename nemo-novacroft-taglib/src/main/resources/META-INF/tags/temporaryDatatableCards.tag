<%@ tag language="java" pageEncoding="ISO-8859-1" %>
<div class="dataTable-container">
<table class="grid" cellpadding=0 cellspacing=1 id="cards">
<thead>
<tr class="grid_heading">
    <th>Oyster<br>Number
    </th>
    <th>Mifare<br>Number
    </th>
    <th>Printed<br>Date
    </th>
    <th>Status
    </th>
    <th>Hotlist Reason
    </th>
    <th>History
    </th>
    <th>Hotlist Download Date
    </th>
    <th>TIC
    </th>
    <th>Half Rate/<br>Card Expiry
    </th>
    <th>Free Travel<br>Expiry
    </th>
    <th>Card Relates<br>To This App.
    </th>
    <th>View Details</th>
    <th>Card Admin</th>
</tr>
</thead>
<tbody>
<tr class="grid_row">
    <td>000030000175</td>
    <td>802A228A841E04</td>
    <td>30/04/2013</td>
    <td>Active</td>
    <td><select id="HOTLIST*1576982" name="HOTLIST*1576982"
                style="width: 230px" onchange="checkHotlistReason()">
        <option value="">(None)</option>
        <option value="ADI">Address details incorrect</option>
        <option value="ALTMUT">Altered/Mutilated</option>
        <option value="ABW">Ambiguous hand writing</option>
        <option value="APP">Applicant hotlisted</option>
        <option value="ASBE">AS Behaviour</option>
        <option value="ATC">Authorised by Central TIC</option>
        <option value="AUTO">Auto hotlisted by system</option>
        <option value="CE">Card Expired</option>
        <option value="WC2Y">Card is more than 2 years old</option>
        <option value="CNR">Card not received</option>
        <option value="NOTRCVD">Card not received</option>
        <option value="SURRENDER">Card surrendered by student</option>
        <option value="CWBD">Card withdrawn by Bus Driver</option>
        <option value="CWIT">Card withdrawn by Inspector - TATT</option>
        <option value="CDI">Course details incorrect</option>
        <option value="DAM">Damaged</option>
        <option value="DCE">Data capture error</option>
        <option value="DISABL">Disabled</option>
        <option value="EYTB">Earn Your Travel Back</option>
        <option value="EXPIR">Expired</option>
        <option value="FAIL">Failed Card</option>
        <option value="GOGW">Gesture of goodwill by Novacroft</option>
        <option value="RFG">Gone away</option>
        <option value="DOB">Incorrect / missing date of birth</option>
        <option value="IRR">Incorrect rejection reason</option>
        <option value="IHC">Incorrectly hotlisted card</option>
        <option value="LOST">Lost</option>
        <option value="LSD">Lost/Stolen/Damaged</option>
        <option value="MPSWITHD">MPS Withdrawals</option>
        <option value="NDI">Name details incorrect</option>
        <option value="NONVAL">Non Validation</option>
        <option value="LSO">Ordered Lost Stolen Replacement
            Online
        </option>
        <option value="OTHER">Other</option>
        <option value="OVE">Overridden</option>
        <option value="OPDT">Oyster newPassword details taken</option>
        <option value="POP">Payment Over The Phone</option>
        <option value="PAR">Payment received</option>
        <option value="PRHR">Photocard re-issued as half-rate</option>
        <option value="PHR">Photograph received</option>
        <option value="PORR">Post office receipt received</option>
        <option value="PFRC">Proforma received</option>
        <option value="POBR">Proof of birth received</option>
        <option value="RO18TC">Received O18 T&amp;Cs</option>
        <option value="RU18TC">Received U18 T&amp;Cs</option>
        <option value="ORPHAN">Removed from web account</option>
        <option value="RPR">Replacement photo received</option>
        <option value="STOLEN">Stolen</option>
        <option value="TACR">Terms and conditions received</option>
        <option value="TFLA">TfL authorise</option>
        <option value="TFL">TfL validated postcode</option>
        <option value="TPEDAUTH">TPED Authorised</option>
        <option value="TRANS">Transferred</option>
        <option value="UC">Uncollected</option>
        <option value="SC2">Wrong card carrier</option>
    </select></td>
    <td><input class="button" type="button" value="History"
               onclick="viewCardHistory(1576982,'000030000175')"
               title="Click to view the history for this Oyster card."
               disabled="disabled"></td>
    <td></td>
    <td><input class="button" type="button" value="TIC"
               onclick="loadTICDetails('50100024521','1576982')"
               title="Click to view the input TIC details for this Oyster card."
               disabled="disabled"></td>
    <td>12-APR-15</td>
    <td class="center">N/A</td>
    <td class="center">Yes</td>
    <td><input class="button" type="button" value="Check Failed"
               onclick="checkFailedCard('300001')"
               title="Click to view the details of PAYG and tickets for this Oyster card.">
    </td>
    <td>
        <button>View</button>
    </td>
</tr>
<tr class="grid_row">
    <td>000030000175</td>
    <td>802A238A841E04</td>
    <td>30/04/2014</td>
    <td>Active</td>
    <td><select id="HOTLIST*1576982" name="HOTLIST*1576982"
                style="width: 230px" onchange="checkHotlistReason()">
        <option value="">(None)</option>
        <option value="ADI">Address details incorrect</option>
        <option value="ALTMUT">Altered/Mutilated</option>
        <option value="ABW">Ambiguous hand writing</option>
        <option value="APP">Applicant hotlisted</option>
        <option value="ASBE">AS Behaviour</option>
        <option value="ATC">Authorised by Central TIC</option>
        <option value="AUTO">Auto hotlisted by system</option>
        <option value="CE">Card Expired</option>
        <option value="WC2Y">Card is more than 2 years old</option>
        <option value="CNR">Card not received</option>
        <option value="NOTRCVD">Card not received</option>
        <option value="SURRENDER">Card surrendered by student</option>
        <option value="CWBD">Card withdrawn by Bus Driver</option>
        <option value="CWIT">Card withdrawn by Inspector - TATT</option>
        <option value="CDI">Course details incorrect</option>
        <option value="DAM">Damaged</option>
        <option value="DCE">Data capture error</option>
        <option value="DISABL">Disabled</option>
        <option value="EYTB">Earn Your Travel Back</option>
        <option value="EXPIR">Expired</option>
        <option value="FAIL">Failed Card</option>
        <option value="GOGW">Gesture of goodwill by Novacroft</option>
        <option value="RFG">Gone away</option>
        <option value="DOB">Incorrect / missing date of birth</option>
        <option value="IRR">Incorrect rejection reason</option>
        <option value="IHC">Incorrectly hotlisted card</option>
        <option value="LOST">Lost</option>
        <option value="LSD">Lost/Stolen/Damaged</option>
        <option value="MPSWITHD">MPS Withdrawals</option>
        <option value="NDI">Name details incorrect</option>
        <option value="NONVAL">Non Validation</option>
        <option value="LSO">Ordered Lost Stolen Replacement
            Online
        </option>
        <option value="OTHER">Other</option>
        <option value="OVE">Overridden</option>
        <option value="OPDT">Oyster newPassword details taken</option>
        <option value="POP">Payment Over The Phone</option>
        <option value="PAR">Payment received</option>
        <option value="PRHR">Photocard re-issued as half-rate</option>
        <option value="PHR">Photograph received</option>
        <option value="PORR">Post office receipt received</option>
        <option value="PFRC">Proforma received</option>
        <option value="POBR">Proof of birth received</option>
        <option value="RO18TC">Received O18 T&amp;Cs</option>
        <option value="RU18TC">Received U18 T&amp;Cs</option>
        <option value="ORPHAN">Removed from web account</option>
        <option value="RPR">Replacement photo received</option>
        <option value="STOLEN">Stolen</option>
        <option value="TACR">Terms and conditions received</option>
        <option value="TFLA">TfL authorise</option>
        <option value="TFL">TfL validated postcode</option>
        <option value="TPEDAUTH">TPED Authorised</option>
        <option value="TRANS">Transferred</option>
        <option value="UC">Uncollected</option>
        <option value="SC2">Wrong card carrier</option>
    </select></td>
    <td><input class="button" type="button" value="History"
               onclick="viewCardHistory(1576982,'000030000175')"
               title="Click to view the history for this Oyster card."
               disabled="disabled"></td>
    <td></td>
    <td><input class="button" type="button" value="TIC"
               onclick="loadTICDetails('50100024521','1576982')"
               title="Click to view the input TIC details for this Oyster card."
               disabled="disabled"></td>
    <td>12-APR-15</td>
    <td class="center">N/A</td>
    <td class="center">Yes</td>
    <td><input class="button" type="button" value="Check Failed"
               onclick="checkFailedCard('300001')"
               title="Click to view the details of PAYG and tickets for this Oyster card.">
    </td>
    <td>
        <button>View</button>
    </td>
</tr>
<tr class="grid_row">
    <td>000030000178</td>
    <td>802B228A841E04</td>
    <td>30/04/2015</td>
    <td>Hotlisted</td>
    <td><select id="HOTLIST*1576982" name="HOTLIST*1576982"
                style="width: 230px" onchange="checkHotlistReason()">
        <option value="">(None)</option>
        <option value="ADI" selected="selected">Address details incorrect</option>
        <option value="ALTMUT">Altered/Mutilated</option>
        <option value="ABW">Ambiguous hand writing</option>
        <option value="APP">Applicant hotlisted</option>
        <option value="ASBE">AS Behaviour</option>
        <option value="ATC">Authorised by Central TIC</option>
        <option value="AUTO">Auto hotlisted by system</option>
        <option value="CE">Card Expired</option>
        <option value="WC2Y">Card is more than 2 years old</option>
        <option value="CNR">Card not received</option>
        <option value="NOTRCVD">Card not received</option>
        <option value="SURRENDER">Card surrendered by student</option>
        <option value="CWBD">Card withdrawn by Bus Driver</option>
        <option value="CWIT">Card withdrawn by Inspector - TATT</option>
        <option value="CDI">Course details incorrect</option>
        <option value="DAM">Damaged</option>
        <option value="DCE">Data capture error</option>
        <option value="DISABL">Disabled</option>
        <option value="EYTB">Earn Your Travel Back</option>
        <option value="EXPIR">Expired</option>
        <option value="FAIL">Failed Card</option>
        <option value="GOGW">Gesture of goodwill by Novacroft</option>
        <option value="RFG">Gone away</option>
        <option value="DOB">Incorrect / missing date of birth</option>
        <option value="IRR">Incorrect rejection reason</option>
        <option value="IHC">Incorrectly hotlisted card</option>
        <option value="LOST">Lost</option>
        <option value="LSD">Lost/Stolen/Damaged</option>
        <option value="MPSWITHD">MPS Withdrawals</option>
        <option value="NDI">Name details incorrect</option>
        <option value="NONVAL">Non Validation</option>
        <option value="LSO">Ordered Lost Stolen Replacement
            Online
        </option>
        <option value="OTHER">Other</option>
        <option value="OVE">Overridden</option>
        <option value="OPDT">Oyster newPassword details taken</option>
        <option value="POP">Payment Over The Phone</option>
        <option value="PAR">Payment received</option>
        <option value="PRHR">Photocard re-issued as half-rate</option>
        <option value="PHR">Photograph received</option>
        <option value="PORR">Post office receipt received</option>
        <option value="PFRC">Proforma received</option>
        <option value="POBR">Proof of birth received</option>
        <option value="RO18TC">Received O18 T&amp;Cs</option>
        <option value="RU18TC">Received U18 T&amp;Cs</option>
        <option value="ORPHAN">Removed from web account</option>
        <option value="RPR">Replacement photo received</option>
        <option value="STOLEN">Stolen</option>
        <option value="TACR">Terms and conditions received</option>
        <option value="TFLA">TfL authorise</option>
        <option value="TFL">TfL validated postcode</option>
        <option value="TPEDAUTH">TPED Authorised</option>
        <option value="TRANS">Transferred</option>
        <option value="UC">Uncollected</option>
        <option value="SC2">Wrong card carrier</option>
    </select></td>
    <td><input class="button" type="button" value="History"
               onclick="viewCardHistory(1576982,'000030000175')"
               title="Click to view the history for this Oyster card."
               disabled="disabled"></td>
    <td></td>
    <td><input class="button" type="button" value="TIC"
               onclick="loadTICDetails('50100024521','1576982')"
               title="Click to view the input TIC details for this Oyster card."
               disabled="disabled"></td>
    <td>12-APR-15</td>
    <td class="center">N/A</td>
    <td class="center">Yes</td>
    <td><input class="button" type="button" value="Check Failed"
               onclick="checkFailedCard('300001')"
               title="Click to view the details of PAYG and tickets for this Oyster card.">
    </td>
    <td>
        <button>View</button>
    </td>
</tr>
<tr class="grid_row">
    <td>000030000179</td>
    <td>802B228A841E04</td>
    <td>10/04/2015</td>
    <td>Active</td>
    <td><select id="HOTLIST*1576982" name="HOTLIST*1576982"
                style="width: 230px" onchange="checkHotlistReason()">
        <option value="">(None)</option>
        <option value="ADI">Address details incorrect</option>
        <option value="ALTMUT">Altered/Mutilated</option>
        <option value="ABW">Ambiguous hand writing</option>
        <option value="APP">Applicant hotlisted</option>
        <option value="ASBE" selected="selected">AS Behaviour</option>
        <option value="ATC">Authorised by Central TIC</option>
        <option value="AUTO">Auto hotlisted by system</option>
        <option value="CE">Card Expired</option>
        <option value="WC2Y">Card is more than 2 years old</option>
        <option value="CNR">Card not received</option>
        <option value="NOTRCVD">Card not received</option>
        <option value="SURRENDER">Card surrendered by student</option>
        <option value="CWBD">Card withdrawn by Bus Driver</option>
        <option value="CWIT">Card withdrawn by Inspector - TATT</option>
        <option value="CDI">Course details incorrect</option>
        <option value="DAM">Damaged</option>
        <option value="DCE">Data capture error</option>
        <option value="DISABL">Disabled</option>
        <option value="EYTB">Earn Your Travel Back</option>
        <option value="EXPIR">Expired</option>
        <option value="FAIL">Failed Card</option>
        <option value="GOGW">Gesture of goodwill by Novacroft</option>
        <option value="RFG">Gone away</option>
        <option value="DOB">Incorrect / missing date of birth</option>
        <option value="IRR">Incorrect rejection reason</option>
        <option value="IHC">Incorrectly hotlisted card</option>
        <option value="LOST">Lost</option>
        <option value="LSD">Lost/Stolen/Damaged</option>
        <option value="MPSWITHD">MPS Withdrawals</option>
        <option value="NDI">Name details incorrect</option>
        <option value="NONVAL">Non Validation</option>
        <option value="LSO">Ordered Lost Stolen Replacement
            Online
        </option>
        <option value="OTHER">Other</option>
        <option value="OVE">Overridden</option>
        <option value="OPDT">Oyster newPassword details taken</option>
        <option value="POP">Payment Over The Phone</option>
        <option value="PAR">Payment received</option>
        <option value="PRHR">Photocard re-issued as half-rate</option>
        <option value="PHR">Photograph received</option>
        <option value="PORR">Post office receipt received</option>
        <option value="PFRC">Proforma received</option>
        <option value="POBR">Proof of birth received</option>
        <option value="RO18TC">Received O18 T&amp;Cs</option>
        <option value="RU18TC">Received U18 T&amp;Cs</option>
        <option value="ORPHAN">Removed from web account</option>
        <option value="RPR">Replacement photo received</option>
        <option value="STOLEN">Stolen</option>
        <option value="TACR">Terms and conditions received</option>
        <option value="TFLA">TfL authorise</option>
        <option value="TFL">TfL validated postcode</option>
        <option value="TPEDAUTH">TPED Authorised</option>
        <option value="TRANS">Transferred</option>
        <option value="UC">Uncollected</option>
        <option value="SC2">Wrong card carrier</option>
    </select></td>
    <td><input class="button" type="button" value="History"
               onclick="viewCardHistory(1576982,'000030000175')"
               title="Click to view the history for this Oyster card."
               disabled="disabled"></td>
    <td></td>
    <td><input class="button" type="button" value="TIC"
               onclick="loadTICDetails('50100024521','1576982')"
               title="Click to view the input TIC details for this Oyster card."
               disabled="disabled"></td>
    <td>12-APR-15</td>
    <td class="center">N/A</td>
    <td class="center">Yes</td>
    <td><input class="button" type="button" value="Check Failed"
               onclick="checkFailedCard('300001')"
               title="Click to view the details of PAYG and tickets for this Oyster card.">
    </td>
    <td>
        <button>View</button>
    </td>
</tr>
<tr class="grid_row">
    <td>000030000180</td>
    <td>802B228A841E04</td>
    <td>10/04/2015</td>
    <td>Active</td>
    <td><select id="HOTLIST*1576982" name="HOTLIST*1576982"
                style="width: 230px" onchange="checkHotlistReason()">
        <option value="">(None)</option>
        <option value="ADI">Address details incorrect</option>
        <option value="ALTMUT">Altered/Mutilated</option>
        <option value="ABW">Ambiguous hand writing</option>
        <option value="APP">Applicant hotlisted</option>
        <option value="ASBE">AS Behaviour</option>
        <option value="ATC">Authorised by Central TIC</option>
        <option value="AUTO">Auto hotlisted by system</option>
        <option value="CE">Card Expired</option>
        <option value="WC2Y">Card is more than 2 years old</option>
        <option value="CNR">Card not received</option>
        <option value="NOTRCVD">Card not received</option>
        <option value="SURRENDER">Card surrendered by student</option>
        <option value="CWBD">Card withdrawn by Bus Driver</option>
        <option value="CWIT">Card withdrawn by Inspector - TATT</option>
        <option value="CDI">Course details incorrect</option>
        <option value="DAM">Damaged</option>
        <option value="DCE">Data capture error</option>
        <option value="DISABL">Disabled</option>
        <option value="EYTB">Earn Your Travel Back</option>
        <option value="EXPIR">Expired</option>
        <option value="FAIL">Failed Card</option>
        <option value="GOGW">Gesture of goodwill by Novacroft</option>
        <option value="RFG">Gone away</option>
        <option value="DOB">Incorrect / missing date of birth</option>
        <option value="IRR">Incorrect rejection reason</option>
        <option value="IHC">Incorrectly hotlisted card</option>
        <option value="LOST">Lost</option>
        <option value="LSD">Lost/Stolen/Damaged</option>
        <option value="MPSWITHD">MPS Withdrawals</option>
        <option value="NDI">Name details incorrect</option>
        <option value="NONVAL">Non Validation</option>
        <option value="LSO">Ordered Lost Stolen Replacement
            Online
        </option>
        <option value="OTHER">Other</option>
        <option value="OVE">Overridden</option>
        <option value="OPDT">Oyster newPassword details taken</option>
        <option value="POP">Payment Over The Phone</option>
        <option value="PAR">Payment received</option>
        <option value="PRHR">Photocard re-issued as half-rate</option>
        <option value="PHR">Photograph received</option>
        <option value="PORR">Post office receipt received</option>
        <option value="PFRC">Proforma received</option>
        <option value="POBR">Proof of birth received</option>
        <option value="RO18TC">Received O18 T&amp;Cs</option>
        <option value="RU18TC">Received U18 T&amp;Cs</option>
        <option value="ORPHAN">Removed from web account</option>
        <option value="RPR">Replacement photo received</option>
        <option value="STOLEN" selected="selected">Stolen</option>
        <option value="TACR">Terms and conditions received</option>
        <option value="TFLA">TfL authorise</option>
        <option value="TFL">TfL validated postcode</option>
        <option value="TPEDAUTH">TPED Authorised</option>
        <option value="TRANS">Transferred</option>
        <option value="UC">Uncollected</option>
        <option value="SC2">Wrong card carrier</option>
    </select></td>
    <td><input class="button" type="button" value="History"
               onclick="viewCardHistory(1576982,'000030000175')"
               title="Click to view the history for this Oyster card."
               disabled="disabled"></td>
    <td></td>
    <td><input class="button" type="button" value="TIC"
               onclick="loadTICDetails('50100024521','1576982')"
               title="Click to view the input TIC details for this Oyster card."
               disabled="disabled"></td>
    <td>12-APR-15</td>
    <td class="center">N/A</td>
    <td class="center">Yes</td>
    <td><input class="button" type="button" value="Check Failed"
               onclick="checkFailedCard('300001')"
               title="Click to view the details of PAYG and tickets for this Oyster card.">
    </td>
    <td>
        <button>View</button>
    </td>
</tr>
<tr class="grid_row">
    <td>000030000181</td>
    <td>802B228A841E04</td>
    <td>15/04/2015</td>
    <td>Active</td>
    <td><select id="HOTLIST*1576982" name="HOTLIST*1576982"
                style="width: 230px" onchange="checkHotlistReason()">
        <option value="">(None)</option>
        <option value="ADI">Address details incorrect</option>
        <option value="ALTMUT">Altered/Mutilated</option>
        <option value="ABW">Ambiguous hand writing</option>
        <option value="APP">Applicant hotlisted</option>
        <option value="ASBE">AS Behaviour</option>
        <option value="ATC">Authorised by Central TIC</option>
        <option value="AUTO">Auto hotlisted by system</option>
        <option value="CE">Card Expired</option>
        <option value="WC2Y">Card is more than 2 years old</option>
        <option value="CNR">Card not received</option>
        <option value="NOTRCVD">Card not received</option>
        <option value="SURRENDER">Card surrendered by student</option>
        <option value="CWBD">Card withdrawn by Bus Driver</option>
        <option value="CWIT">Card withdrawn by Inspector - TATT</option>
        <option value="CDI">Course details incorrect</option>
        <option value="DAM">Damaged</option>
        <option value="DCE">Data capture error</option>
        <option value="DISABL">Disabled</option>
        <option value="EYTB">Earn Your Travel Back</option>
        <option value="EXPIR">Expired</option>
        <option value="FAIL">Failed Card</option>
        <option value="GOGW">Gesture of goodwill by Novacroft</option>
        <option value="RFG">Gone away</option>
        <option value="DOB">Incorrect / missing date of birth</option>
        <option value="IRR">Incorrect rejection reason</option>
        <option value="IHC">Incorrectly hotlisted card</option>
        <option value="LOST">Lost</option>
        <option value="LSD">Lost/Stolen/Damaged</option>
        <option value="MPSWITHD">MPS Withdrawals</option>
        <option value="NDI">Name details incorrect</option>
        <option value="NONVAL">Non Validation</option>
        <option value="LSO">Ordered Lost Stolen Replacement
            Online
        </option>
        <option value="OTHER">Other</option>
        <option value="OVE">Overridden</option>
        <option value="OPDT">Oyster newPassword details taken</option>
        <option value="POP">Payment Over The Phone</option>
        <option value="PAR">Payment received</option>
        <option value="PRHR">Photocard re-issued as half-rate</option>
        <option value="PHR">Photograph received</option>
        <option value="PORR">Post office receipt received</option>
        <option value="PFRC">Proforma received</option>
        <option value="POBR">Proof of birth received</option>
        <option value="RO18TC">Received O18 T&amp;Cs</option>
        <option value="RU18TC" selected="selected">Received U18 T&amp;Cs</option>
        <option value="ORPHAN">Removed from web account</option>
        <option value="RPR">Replacement photo received</option>
        <option value="STOLEN">Stolen</option>
        <option value="TACR">Terms and conditions received</option>
        <option value="TFLA">TfL authorise</option>
        <option value="TFL">TfL validated postcode</option>
        <option value="TPEDAUTH">TPED Authorised</option>
        <option value="TRANS">Transferred</option>
        <option value="UC">Uncollected</option>
        <option value="SC2">Wrong card carrier</option>
    </select></td>
    <td><input class="button" type="button" value="History"
               onclick="viewCardHistory(1576982,'000030000175')"
               title="Click to view the history for this Oyster card."
               disabled="disabled"></td>
    <td></td>
    <td><input class="button" type="button" value="TIC"
               onclick="loadTICDetails('50100024521','1576982')"
               title="Click to view the input TIC details for this Oyster card."
               disabled="disabled"></td>
    <td>12-APR-15</td>
    <td class="center">N/A</td>
    <td class="center">Yes</td>
    <td><input class="button" type="button" value="Check Failed"
               onclick="checkFailedCard('300001')"
               title="Click to view the details of PAYG and tickets for this Oyster card.">
    </td>
    <td>
        <button>View</button>
    </td>
</tr>
<tr class="grid_row">
    <td>000030000182</td>
    <td>802B228A841E04</td>
    <td>15/04/2015</td>
    <td>Active</td>
    <td><select id="HOTLIST*1576982" name="HOTLIST*1576982"
                style="width: 230px" onchange="checkHotlistReason()">
        <option value="">(None)</option>
        <option value="ADI">Address details incorrect</option>
        <option value="ALTMUT">Altered/Mutilated</option>
        <option value="ABW">Ambiguous hand writing</option>
        <option value="APP">Applicant hotlisted</option>
        <option value="ASBE">AS Behaviour</option>
        <option value="ATC">Authorised by Central TIC</option>
        <option value="AUTO">Auto hotlisted by system</option>
        <option value="CE">Card Expired</option>
        <option value="WC2Y" selected="selected">Card is more than 2 years old</option>
        <option value="CNR">Card not received</option>
        <option value="NOTRCVD">Card not received</option>
        <option value="SURRENDER">Card surrendered by student</option>
        <option value="CWBD">Card withdrawn by Bus Driver</option>
        <option value="CWIT">Card withdrawn by Inspector - TATT</option>
        <option value="CDI">Course details incorrect</option>
        <option value="DAM">Damaged</option>
        <option value="DCE">Data capture error</option>
        <option value="DISABL">Disabled</option>
        <option value="EYTB">Earn Your Travel Back</option>
        <option value="EXPIR">Expired</option>
        <option value="FAIL">Failed Card</option>
        <option value="GOGW">Gesture of goodwill by Novacroft</option>
        <option value="RFG">Gone away</option>
        <option value="DOB">Incorrect / missing date of birth</option>
        <option value="IRR">Incorrect rejection reason</option>
        <option value="IHC">Incorrectly hotlisted card</option>
        <option value="LOST">Lost</option>
        <option value="LSD">Lost/Stolen/Damaged</option>
        <option value="MPSWITHD">MPS Withdrawals</option>
        <option value="NDI">Name details incorrect</option>
        <option value="NONVAL">Non Validation</option>
        <option value="LSO">Ordered Lost Stolen Replacement
            Online
        </option>
        <option value="OTHER">Other</option>
        <option value="OVE">Overridden</option>
        <option value="OPDT">Oyster newPassword details taken</option>
        <option value="POP">Payment Over The Phone</option>
        <option value="PAR">Payment received</option>
        <option value="PRHR">Photocard re-issued as half-rate</option>
        <option value="PHR">Photograph received</option>
        <option value="PORR">Post office receipt received</option>
        <option value="PFRC">Proforma received</option>
        <option value="POBR">Proof of birth received</option>
        <option value="RO18TC">Received O18 T&amp;Cs</option>
        <option value="RU18TC">Received U18 T&amp;Cs</option>
        <option value="ORPHAN">Removed from web account</option>
        <option value="RPR">Replacement photo received</option>
        <option value="STOLEN">Stolen</option>
        <option value="TACR">Terms and conditions received</option>
        <option value="TFLA">TfL authorise</option>
        <option value="TFL">TfL validated postcode</option>
        <option value="TPEDAUTH">TPED Authorised</option>
        <option value="TRANS">Transferred</option>
        <option value="UC">Uncollected</option>
        <option value="SC2">Wrong card carrier</option>
    </select></td>
    <td><input class="button" type="button" value="History"
               onclick="viewCardHistory(1576982,'000030000175')"
               title="Click to view the history for this Oyster card."
               disabled="disabled"></td>
    <td></td>
    <td><input class="button" type="button" value="TIC"
               onclick="loadTICDetails('50100024521','1576982')"
               title="Click to view the input TIC details for this Oyster card."
               disabled="disabled"></td>
    <td>12-APR-15</td>
    <td class="center">N/A</td>
    <td class="center">Yes</td>
    <td><input class="button" type="button" value="Check Failed"
               onclick="checkFailedCard('300001')"
               title="Click to view the details of PAYG and tickets for this Oyster card.">
    </td>
    <td>
        <button>View</button>
    </td>
</tr>
<tr class="grid_row">
    <td>000030000183</td>
    <td>802B228A841E04</td>
    <td>15/04/2015</td>
    <td>Active</td>
    <td><select id="HOTLIST*1576982" name="HOTLIST*1576982"
                style="width: 230px" onchange="checkHotlistReason()">
        <option value="">(None)</option>
        <option value="ADI">Address details incorrect</option>
        <option value="ALTMUT">Altered/Mutilated</option>
        <option value="ABW">Ambiguous hand writing</option>
        <option value="APP">Applicant hotlisted</option>
        <option value="ASBE">AS Behaviour</option>
        <option value="ATC">Authorised by Central TIC</option>
        <option value="AUTO">Auto hotlisted by system</option>
        <option value="CE">Card Expired</option>
        <option value="WC2Y">Card is more than 2 years old</option>
        <option value="CNR">Card not received</option>
        <option value="NOTRCVD">Card not received</option>
        <option value="SURRENDER">Card surrendered by student</option>
        <option value="CWBD">Card withdrawn by Bus Driver</option>
        <option value="CWIT">Card withdrawn by Inspector - TATT</option>
        <option value="CDI">Course details incorrect</option>
        <option value="DAM">Damaged</option>
        <option value="DCE">Data capture error</option>
        <option value="DISABL">Disabled</option>
        <option value="EYTB">Earn Your Travel Back</option>
        <option value="EXPIR">Expired</option>
        <option value="FAIL">Failed Card</option>
        <option value="GOGW">Gesture of goodwill by Novacroft</option>
        <option value="RFG">Gone away</option>
        <option value="DOB">Incorrect / missing date of birth</option>
        <option value="IRR">Incorrect rejection reason</option>
        <option value="IHC">Incorrectly hotlisted card</option>
        <option value="LOST">Lost</option>
        <option value="LSD">Lost/Stolen/Damaged</option>
        <option value="MPSWITHD">MPS Withdrawals</option>
        <option value="NDI">Name details incorrect</option>
        <option value="NONVAL">Non Validation</option>
        <option value="LSO">Ordered Lost Stolen Replacement
            Online
        </option>
        <option value="OTHER">Other</option>
        <option value="OVE">Overridden</option>
        <option value="OPDT">Oyster newPassword details taken</option>
        <option value="POP">Payment Over The Phone</option>
        <option value="PAR">Payment received</option>
        <option value="PRHR">Photocard re-issued as half-rate</option>
        <option value="PHR">Photograph received</option>
        <option value="PORR">Post office receipt received</option>
        <option value="PFRC">Proforma received</option>
        <option value="POBR">Proof of birth received</option>
        <option value="RO18TC">Received O18 T&amp;Cs</option>
        <option value="RU18TC">Received U18 T&amp;Cs</option>
        <option value="ORPHAN">Removed from web account</option>
        <option value="RPR">Replacement photo received</option>
        <option value="STOLEN">Stolen</option>
        <option value="TACR">Terms and conditions received</option>
        <option value="TFLA">TfL authorise</option>
        <option value="TFL">TfL validated postcode</option>
        <option value="TPEDAUTH">TPED Authorised</option>
        <option value="TRANS">Transferred</option>
        <option value="UC">Uncollected</option>
        <option value="SC2">Wrong card carrier</option>
    </select></td>
    <td><input class="button" type="button" value="History"
               onclick="viewCardHistory(1576982,'000030000175')"
               title="Click to view the history for this Oyster card."
               disabled="disabled"></td>
    <td></td>
    <td><input class="button" type="button" value="TIC"
               onclick="loadTICDetails('50100024521','1576982')"
               title="Click to view the input TIC details for this Oyster card."
               disabled="disabled"></td>
    <td>12-APR-15</td>
    <td class="center">N/A</td>
    <td class="center">Yes</td>
    <td><input class="button" type="button" value="Check Failed"
               onclick="checkFailedCard('300001')"
               title="Click to view the details of PAYG and tickets for this Oyster card.">
    </td>
    <td>
        <button>View</button>
    </td>
</tr>
<tr class="grid_row">
    <td>000030000184</td>
    <td>802B228A841E04</td>
    <td>15/04/2015</td>
    <td>Active</td>
    <td><select id="HOTLIST*1576982" name="HOTLIST*1576982"
                style="width: 230px" onchange="checkHotlistReason()">
        <option value="">(None)</option>
        <option value="ADI">Address details incorrect</option>
        <option value="ALTMUT">Altered/Mutilated</option>
        <option value="ABW">Ambiguous hand writing</option>
        <option value="APP">Applicant hotlisted</option>
        <option value="ASBE">AS Behaviour</option>
        <option value="ATC">Authorised by Central TIC</option>
        <option value="AUTO">Auto hotlisted by system</option>
        <option value="CE">Card Expired</option>
        <option value="WC2Y">Card is more than 2 years old</option>
        <option value="CNR">Card not received</option>
        <option value="NOTRCVD">Card not received</option>
        <option value="SURRENDER">Card surrendered by student</option>
        <option value="CWBD">Card withdrawn by Bus Driver</option>
        <option value="CWIT">Card withdrawn by Inspector - TATT</option>
        <option value="CDI">Course details incorrect</option>
        <option value="DAM">Damaged</option>
        <option value="DCE">Data capture error</option>
        <option value="DISABL">Disabled</option>
        <option value="EYTB">Earn Your Travel Back</option>
        <option value="EXPIR">Expired</option>
        <option value="FAIL">Failed Card</option>
        <option value="GOGW">Gesture of goodwill by Novacroft</option>
        <option value="RFG">Gone away</option>
        <option value="DOB">Incorrect / missing date of birth</option>
        <option value="IRR">Incorrect rejection reason</option>
        <option value="IHC">Incorrectly hotlisted card</option>
        <option value="LOST">Lost</option>
        <option value="LSD">Lost/Stolen/Damaged</option>
        <option value="MPSWITHD">MPS Withdrawals</option>
        <option value="NDI">Name details incorrect</option>
        <option value="NONVAL">Non Validation</option>
        <option value="LSO">Ordered Lost Stolen Replacement
            Online
        </option>
        <option value="OTHER">Other</option>
        <option value="OVE">Overridden</option>
        <option value="OPDT">Oyster newPassword details taken</option>
        <option value="POP">Payment Over The Phone</option>
        <option value="PAR">Payment received</option>
        <option value="PRHR">Photocard re-issued as half-rate</option>
        <option value="PHR">Photograph received</option>
        <option value="PORR">Post office receipt received</option>
        <option value="PFRC">Proforma received</option>
        <option value="POBR">Proof of birth received</option>
        <option value="RO18TC">Received O18 T&amp;Cs</option>
        <option value="RU18TC">Received U18 T&amp;Cs</option>
        <option value="ORPHAN">Removed from web account</option>
        <option value="RPR">Replacement photo received</option>
        <option value="STOLEN">Stolen</option>
        <option value="TACR">Terms and conditions received</option>
        <option value="TFLA">TfL authorise</option>
        <option value="TFL">TfL validated postcode</option>
        <option value="TPEDAUTH">TPED Authorised</option>
        <option value="TRANS">Transferred</option>
        <option value="UC">Uncollected</option>
        <option value="SC2">Wrong card carrier</option>
    </select></td>
    <td><input class="button" type="button" value="History"
               onclick="viewCardHistory(1576982,'000030000175')"
               title="Click to view the history for this Oyster card."
               disabled="disabled"></td>
    <td></td>
    <td><input class="button" type="button" value="TIC"
               onclick="loadTICDetails('50100024521','1576982')"
               title="Click to view the input TIC details for this Oyster card."
               disabled="disabled"></td>
    <td>12-APR-15</td>
    <td class="center">N/A</td>
    <td class="center">Yes</td>
    <td><input class="button" type="button" value="Check Failed"
               onclick="checkFailedCard('300001')"
               title="Click to view the details of PAYG and tickets for this Oyster card.">
    </td>
    <td>
        <button>View</button>
    </td>
</tr>
<tr class="grid_row">
    <td>000030000185</td>
    <td>802B228A841E04</td>
    <td>15/05/2015</td>
    <td>Active</td>
    <td><select id="HOTLIST*1576982" name="HOTLIST*1576982"
                style="width: 230px" onchange="checkHotlistReason()">
        <option value="">(None)</option>
        <option value="ADI">Address details incorrect</option>
        <option value="ALTMUT">Altered/Mutilated</option>
        <option value="ABW">Ambiguous hand writing</option>
        <option value="APP">Applicant hotlisted</option>
        <option value="ASBE">AS Behaviour</option>
        <option value="ATC">Authorised by Central TIC</option>
        <option value="AUTO">Auto hotlisted by system</option>
        <option value="CE">Card Expired</option>
        <option value="WC2Y">Card is more than 2 years old</option>
        <option value="CNR">Card not received</option>
        <option value="NOTRCVD">Card not received</option>
        <option value="SURRENDER">Card surrendered by student</option>
        <option value="CWBD">Card withdrawn by Bus Driver</option>
        <option value="CWIT">Card withdrawn by Inspector - TATT</option>
        <option value="CDI">Course details incorrect</option>
        <option value="DAM">Damaged</option>
        <option value="DCE">Data capture error</option>
        <option value="DISABL">Disabled</option>
        <option value="EYTB">Earn Your Travel Back</option>
        <option value="EXPIR">Expired</option>
        <option value="FAIL">Failed Card</option>
        <option value="GOGW">Gesture of goodwill by Novacroft</option>
        <option value="RFG">Gone away</option>
        <option value="DOB">Incorrect / missing date of birth</option>
        <option value="IRR">Incorrect rejection reason</option>
        <option value="IHC">Incorrectly hotlisted card</option>
        <option value="LOST">Lost</option>
        <option value="LSD">Lost/Stolen/Damaged</option>
        <option value="MPSWITHD">MPS Withdrawals</option>
        <option value="NDI">Name details incorrect</option>
        <option value="NONVAL">Non Validation</option>
        <option value="LSO">Ordered Lost Stolen Replacement
            Online
        </option>
        <option value="OTHER">Other</option>
        <option value="OVE">Overridden</option>
        <option value="OPDT">Oyster newPassword details taken</option>
        <option value="POP">Payment Over The Phone</option>
        <option value="PAR">Payment received</option>
        <option value="PRHR">Photocard re-issued as half-rate</option>
        <option value="PHR">Photograph received</option>
        <option value="PORR">Post office receipt received</option>
        <option value="PFRC">Proforma received</option>
        <option value="POBR">Proof of birth received</option>
        <option value="RO18TC">Received O18 T&amp;Cs</option>
        <option value="RU18TC">Received U18 T&amp;Cs</option>
        <option value="ORPHAN">Removed from web account</option>
        <option value="RPR">Replacement photo received</option>
        <option value="STOLEN">Stolen</option>
        <option value="TACR">Terms and conditions received</option>
        <option value="TFLA">TfL authorise</option>
        <option value="TFL">TfL validated postcode</option>
        <option value="TPEDAUTH">TPED Authorised</option>
        <option value="TRANS">Transferred</option>
        <option value="UC">Uncollected</option>
        <option value="SC2">Wrong card carrier</option>
    </select></td>
    <td><input class="button" type="button" value="History"
               onclick="viewCardHistory(1576982,'000030000175')"
               title="Click to view the history for this Oyster card."
               disabled="disabled"></td>
    <td></td>
    <td><input class="button" type="button" value="TIC"
               onclick="loadTICDetails('50100024521','1576982')"
               title="Click to view the input TIC details for this Oyster card."
               disabled="disabled"></td>
    <td>12-APR-15</td>
    <td class="center">N/A</td>
    <td class="center">Yes</td>
    <td><input class="button" type="button" value="Check Failed"
               onclick="checkFailedCard('300001')"
               title="Click to view the details of PAYG and tickets for this Oyster card.">
    </td>
    <td>
        <button>View</button>
    </td>
</tr>
<tr class="grid_row">
    <td>000030000186</td>
    <td>802B228A841E04</td>
    <td>30/05/2015</td>
    <td>Active</td>
    <td><select id="HOTLIST*1576982" name="HOTLIST*1576982"
                style="width: 230px" onchange="checkHotlistReason()">
        <option value="">(None)</option>
        <option value="ADI">Address details incorrect</option>
        <option value="ALTMUT">Altered/Mutilated</option>
        <option value="ABW">Ambiguous hand writing</option>
        <option value="APP">Applicant hotlisted</option>
        <option value="ASBE">AS Behaviour</option>
        <option value="ATC">Authorised by Central TIC</option>
        <option value="AUTO">Auto hotlisted by system</option>
        <option value="CE">Card Expired</option>
        <option value="WC2Y">Card is more than 2 years old</option>
        <option value="CNR">Card not received</option>
        <option value="NOTRCVD">Card not received</option>
        <option value="SURRENDER">Card surrendered by student</option>
        <option value="CWBD">Card withdrawn by Bus Driver</option>
        <option value="CWIT">Card withdrawn by Inspector - TATT</option>
        <option value="CDI">Course details incorrect</option>
        <option value="DAM">Damaged</option>
        <option value="DCE">Data capture error</option>
        <option value="DISABL">Disabled</option>
        <option value="EYTB">Earn Your Travel Back</option>
        <option value="EXPIR">Expired</option>
        <option value="FAIL">Failed Card</option>
        <option value="GOGW">Gesture of goodwill by Novacroft</option>
        <option value="RFG">Gone away</option>
        <option value="DOB">Incorrect / missing date of birth</option>
        <option value="IRR">Incorrect rejection reason</option>
        <option value="IHC">Incorrectly hotlisted card</option>
        <option value="LOST">Lost</option>
        <option value="LSD">Lost/Stolen/Damaged</option>
        <option value="MPSWITHD">MPS Withdrawals</option>
        <option value="NDI">Name details incorrect</option>
        <option value="NONVAL">Non Validation</option>
        <option value="LSO">Ordered Lost Stolen Replacement
            Online
        </option>
        <option value="OTHER">Other</option>
        <option value="OVE">Overridden</option>
        <option value="OPDT">Oyster newPassword details taken</option>
        <option value="POP">Payment Over The Phone</option>
        <option value="PAR">Payment received</option>
        <option value="PRHR">Photocard re-issued as half-rate</option>
        <option value="PHR">Photograph received</option>
        <option value="PORR">Post office receipt received</option>
        <option value="PFRC">Proforma received</option>
        <option value="POBR">Proof of birth received</option>
        <option value="RO18TC">Received O18 T&amp;Cs</option>
        <option value="RU18TC">Received U18 T&amp;Cs</option>
        <option value="ORPHAN">Removed from web account</option>
        <option value="RPR">Replacement photo received</option>
        <option value="STOLEN">Stolen</option>
        <option value="TACR">Terms and conditions received</option>
        <option value="TFLA">TfL authorise</option>
        <option value="TFL">TfL validated postcode</option>
        <option value="TPEDAUTH">TPED Authorised</option>
        <option value="TRANS">Transferred</option>
        <option value="UC">Uncollected</option>
        <option value="SC2">Wrong card carrier</option>
    </select></td>
    <td><input class="button" type="button" value="History"
               onclick="viewCardHistory(1576982,'000030000175')"
               title="Click to view the history for this Oyster card."
               disabled="disabled"></td>
    <td></td>
    <td><input class="button" type="button" value="TIC"
               onclick="loadTICDetails('50100024521','1576982')"
               title="Click to view the input TIC details for this Oyster card."
               disabled="disabled"></td>
    <td>12-APR-15</td>
    <td class="center">N/A</td>
    <td class="center">Yes</td>
    <td><input class="button" type="button" value="Check Failed"
               onclick="checkFailedCard('300001')"
               title="Click to view the details of PAYG and tickets for this Oyster card.">
    </td>
    <td>
        <button>View</button>
    </td>
</tr>
<tr class="grid_row">
    <td>000030000187</td>
    <td>802B228A841E04</td>
    <td>31/04/2011</td>
    <td>Active</td>
    <td><select id="HOTLIST*1576982" name="HOTLIST*1576982"
                style="width: 230px" onchange="checkHotlistReason()">
        <option value="">(None)</option>
        <option value="ADI">Address details incorrect</option>
        <option value="ALTMUT">Altered/Mutilated</option>
        <option value="ABW">Ambiguous hand writing</option>
        <option value="APP">Applicant hotlisted</option>
        <option value="ASBE">AS Behaviour</option>
        <option value="ATC">Authorised by Central TIC</option>
        <option value="AUTO">Auto hotlisted by system</option>
        <option value="CE">Card Expired</option>
        <option value="WC2Y">Card is more than 2 years old</option>
        <option value="CNR">Card not received</option>
        <option value="NOTRCVD">Card not received</option>
        <option value="SURRENDER">Card surrendered by student</option>
        <option value="CWBD">Card withdrawn by Bus Driver</option>
        <option value="CWIT">Card withdrawn by Inspector - TATT</option>
        <option value="CDI">Course details incorrect</option>
        <option value="DAM">Damaged</option>
        <option value="DCE">Data capture error</option>
        <option value="DISABL">Disabled</option>
        <option value="EYTB">Earn Your Travel Back</option>
        <option value="EXPIR">Expired</option>
        <option value="FAIL">Failed Card</option>
        <option value="GOGW">Gesture of goodwill by Novacroft</option>
        <option value="RFG">Gone away</option>
        <option value="DOB">Incorrect / missing date of birth</option>
        <option value="IRR">Incorrect rejection reason</option>
        <option value="IHC">Incorrectly hotlisted card</option>
        <option value="LOST">Lost</option>
        <option value="LSD">Lost/Stolen/Damaged</option>
        <option value="MPSWITHD">MPS Withdrawals</option>
        <option value="NDI">Name details incorrect</option>
        <option value="NONVAL">Non Validation</option>
        <option value="LSO">Ordered Lost Stolen Replacement
            Online
        </option>
        <option value="OTHER">Other</option>
        <option value="OVE">Overridden</option>
        <option value="OPDT">Oyster newPassword details taken</option>
        <option value="POP">Payment Over The Phone</option>
        <option value="PAR">Payment received</option>
        <option value="PRHR">Photocard re-issued as half-rate</option>
        <option value="PHR">Photograph received</option>
        <option value="PORR">Post office receipt received</option>
        <option value="PFRC">Proforma received</option>
        <option value="POBR">Proof of birth received</option>
        <option value="RO18TC">Received O18 T&amp;Cs</option>
        <option value="RU18TC">Received U18 T&amp;Cs</option>
        <option value="ORPHAN">Removed from web account</option>
        <option value="RPR">Replacement photo received</option>
        <option value="STOLEN">Stolen</option>
        <option value="TACR">Terms and conditions received</option>
        <option value="TFLA">TfL authorise</option>
        <option value="TFL">TfL validated postcode</option>
        <option value="TPEDAUTH">TPED Authorised</option>
        <option value="TRANS">Transferred</option>
        <option value="UC">Uncollected</option>
        <option value="SC2">Wrong card carrier</option>
    </select></td>
    <td><input class="button" type="button" value="History"
               onclick="viewCardHistory(1576982,'000030000175')"
               title="Click to view the history for this Oyster card."
               disabled="disabled"></td>
    <td></td>
    <td><input class="button" type="button" value="TIC"
               onclick="loadTICDetails('50100024521','1576982')"
               title="Click to view the input TIC details for this Oyster card."
               disabled="disabled"></td>
    <td>12-APR-15</td>
    <td class="center">N/A</td>
    <td class="center">Yes</td>
    <td><input class="button" type="button" value="Check Failed"
               onclick="checkFailedCard('300001')"
               title="Click to view the details of PAYG and tickets for this Oyster card.">
    </td>
    <td>
        <button>View</button>
    </td>
</tr>
<tr class="grid_row">
    <td>000030000188</td>
    <td>802B228A841E04</td>
    <td>01/01/2015</td>
    <td>Active</td>
    <td><select id="HOTLIST*1576982" name="HOTLIST*1576982"
                style="width: 230px" onchange="checkHotlistReason()">
        <option value="">(None)</option>
        <option value="ADI">Address details incorrect</option>
        <option value="ALTMUT">Altered/Mutilated</option>
        <option value="ABW">Ambiguous hand writing</option>
        <option value="APP">Applicant hotlisted</option>
        <option value="ASBE">AS Behaviour</option>
        <option value="ATC">Authorised by Central TIC</option>
        <option value="AUTO">Auto hotlisted by system</option>
        <option value="CE">Card Expired</option>
        <option value="WC2Y">Card is more than 2 years old</option>
        <option value="CNR">Card not received</option>
        <option value="NOTRCVD">Card not received</option>
        <option value="SURRENDER">Card surrendered by student</option>
        <option value="CWBD">Card withdrawn by Bus Driver</option>
        <option value="CWIT">Card withdrawn by Inspector - TATT</option>
        <option value="CDI">Course details incorrect</option>
        <option value="DAM">Damaged</option>
        <option value="DCE">Data capture error</option>
        <option value="DISABL">Disabled</option>
        <option value="EYTB">Earn Your Travel Back</option>
        <option value="EXPIR">Expired</option>
        <option value="FAIL">Failed Card</option>
        <option value="GOGW">Gesture of goodwill by Novacroft</option>
        <option value="RFG">Gone away</option>
        <option value="DOB">Incorrect / missing date of birth</option>
        <option value="IRR">Incorrect rejection reason</option>
        <option value="IHC">Incorrectly hotlisted card</option>
        <option value="LOST">Lost</option>
        <option value="LSD">Lost/Stolen/Damaged</option>
        <option value="MPSWITHD">MPS Withdrawals</option>
        <option value="NDI">Name details incorrect</option>
        <option value="NONVAL">Non Validation</option>
        <option value="LSO">Ordered Lost Stolen Replacement
            Online
        </option>
        <option value="OTHER">Other</option>
        <option value="OVE">Overridden</option>
        <option value="OPDT">Oyster newPassword details taken</option>
        <option value="POP">Payment Over The Phone</option>
        <option value="PAR">Payment received</option>
        <option value="PRHR">Photocard re-issued as half-rate</option>
        <option value="PHR">Photograph received</option>
        <option value="PORR">Post office receipt received</option>
        <option value="PFRC">Proforma received</option>
        <option value="POBR">Proof of birth received</option>
        <option value="RO18TC">Received O18 T&amp;Cs</option>
        <option value="RU18TC">Received U18 T&amp;Cs</option>
        <option value="ORPHAN">Removed from web account</option>
        <option value="RPR">Replacement photo received</option>
        <option value="STOLEN">Stolen</option>
        <option value="TACR">Terms and conditions received</option>
        <option value="TFLA">TfL authorise</option>
        <option value="TFL">TfL validated postcode</option>
        <option value="TPEDAUTH">TPED Authorised</option>
        <option value="TRANS">Transferred</option>
        <option value="UC">Uncollected</option>
        <option value="SC2">Wrong card carrier</option>
    </select></td>
    <td><input class="button" type="button" value="History"
               onclick="viewCardHistory(1576982,'000030000175')"
               title="Click to view the history for this Oyster card."
               disabled="disabled"></td>
    <td></td>
    <td><input class="button" type="button" value="TIC"
               onclick="loadTICDetails('50100024521','1576982')"
               title="Click to view the input TIC details for this Oyster card."
               disabled="disabled"></td>
    <td>12-APR-15</td>
    <td class="center">N/A</td>
    <td class="center">Yes</td>
    <td><input class="button" type="button" value="Check Failed"
               onclick="checkFailedCard('300001')"
               title="Click to view the details of PAYG and tickets for this Oyster card.">
    </td>
    <td>
        <button>View</button>
    </td>
</tr>
<tr class="grid_row">
    <td>000030000189</td>
    <td>802B228A841E04</td>
    <td>10/01/2011</td>
    <td>Active</td>
    <td><select id="HOTLIST*1576982" name="HOTLIST*1576982"
                style="width: 230px" onchange="checkHotlistReason()">
        <option value="">(None)</option>
        <option value="ADI">Address details incorrect</option>
        <option value="ALTMUT">Altered/Mutilated</option>
        <option value="ABW">Ambiguous hand writing</option>
        <option value="APP">Applicant hotlisted</option>
        <option value="ASBE">AS Behaviour</option>
        <option value="ATC">Authorised by Central TIC</option>
        <option value="AUTO">Auto hotlisted by system</option>
        <option value="CE">Card Expired</option>
        <option value="WC2Y">Card is more than 2 years old</option>
        <option value="CNR">Card not received</option>
        <option value="NOTRCVD">Card not received</option>
        <option value="SURRENDER">Card surrendered by student</option>
        <option value="CWBD">Card withdrawn by Bus Driver</option>
        <option value="CWIT">Card withdrawn by Inspector - TATT</option>
        <option value="CDI">Course details incorrect</option>
        <option value="DAM">Damaged</option>
        <option value="DCE">Data capture error</option>
        <option value="DISABL">Disabled</option>
        <option value="EYTB">Earn Your Travel Back</option>
        <option value="EXPIR">Expired</option>
        <option value="FAIL">Failed Card</option>
        <option value="GOGW">Gesture of goodwill by Novacroft</option>
        <option value="RFG">Gone away</option>
        <option value="DOB">Incorrect / missing date of birth</option>
        <option value="IRR">Incorrect rejection reason</option>
        <option value="IHC">Incorrectly hotlisted card</option>
        <option value="LOST">Lost</option>
        <option value="LSD">Lost/Stolen/Damaged</option>
        <option value="MPSWITHD">MPS Withdrawals</option>
        <option value="NDI">Name details incorrect</option>
        <option value="NONVAL">Non Validation</option>
        <option value="LSO">Ordered Lost Stolen Replacement
            Online
        </option>
        <option value="OTHER">Other</option>
        <option value="OVE">Overridden</option>
        <option value="OPDT">Oyster newPassword details taken</option>
        <option value="POP">Payment Over The Phone</option>
        <option value="PAR">Payment received</option>
        <option value="PRHR">Photocard re-issued as half-rate</option>
        <option value="PHR">Photograph received</option>
        <option value="PORR">Post office receipt received</option>
        <option value="PFRC">Proforma received</option>
        <option value="POBR">Proof of birth received</option>
        <option value="RO18TC">Received O18 T&amp;Cs</option>
        <option value="RU18TC">Received U18 T&amp;Cs</option>
        <option value="ORPHAN">Removed from web account</option>
        <option value="RPR">Replacement photo received</option>
        <option value="STOLEN">Stolen</option>
        <option value="TACR">Terms and conditions received</option>
        <option value="TFLA">TfL authorise</option>
        <option value="TFL">TfL validated postcode</option>
        <option value="TPEDAUTH">TPED Authorised</option>
        <option value="TRANS">Transferred</option>
        <option value="UC">Uncollected</option>
        <option value="SC2">Wrong card carrier</option>
    </select></td>
    <td><input class="button" type="button" value="History"
               onclick="viewCardHistory(1576982,'000030000175')"
               title="Click to view the history for this Oyster card."
               disabled="disabled"></td>
    <td></td>
    <td><input class="button" type="button" value="TIC"
               onclick="loadTICDetails('50100024521','1576982')"
               title="Click to view the input TIC details for this Oyster card."
               disabled="disabled"></td>
    <td>12-APR-15</td>
    <td class="center">N/A</td>
    <td class="center">Yes</td>
    <td><input class="button" type="button" value="Check Failed"
               onclick="checkFailedCard('300001')"
               title="Click to view the details of PAYG and tickets for this Oyster card.">
    </td>
    <td>
        <button>View</button>
    </td>
</tr>
<tr class="grid_row">
    <td>000030000190</td>
    <td>802B228A841E04</td>
    <td>10/04/2015</td>
    <td>Active</td>
    <td><select id="HOTLIST*1576982" name="HOTLIST*1576982"
                style="width: 230px" onchange="checkHotlistReason()">
        <option value="">(None)</option>
        <option value="ADI">Address details incorrect</option>
        <option value="ALTMUT">Altered/Mutilated</option>
        <option value="ABW">Ambiguous hand writing</option>
        <option value="APP">Applicant hotlisted</option>
        <option value="ASBE">AS Behaviour</option>
        <option value="ATC">Authorised by Central TIC</option>
        <option value="AUTO">Auto hotlisted by system</option>
        <option value="CE">Card Expired</option>
        <option value="WC2Y">Card is more than 2 years old</option>
        <option value="CNR">Card not received</option>
        <option value="NOTRCVD">Card not received</option>
        <option value="SURRENDER">Card surrendered by student</option>
        <option value="CWBD">Card withdrawn by Bus Driver</option>
        <option value="CWIT">Card withdrawn by Inspector - TATT</option>
        <option value="CDI">Course details incorrect</option>
        <option value="DAM">Damaged</option>
        <option value="DCE">Data capture error</option>
        <option value="DISABL">Disabled</option>
        <option value="EYTB">Earn Your Travel Back</option>
        <option value="EXPIR">Expired</option>
        <option value="FAIL">Failed Card</option>
        <option value="GOGW">Gesture of goodwill by Novacroft</option>
        <option value="RFG">Gone away</option>
        <option value="DOB">Incorrect / missing date of birth</option>
        <option value="IRR">Incorrect rejection reason</option>
        <option value="IHC">Incorrectly hotlisted card</option>
        <option value="LOST">Lost</option>
        <option value="LSD">Lost/Stolen/Damaged</option>
        <option value="MPSWITHD">MPS Withdrawals</option>
        <option value="NDI">Name details incorrect</option>
        <option value="NONVAL">Non Validation</option>
        <option value="LSO">Ordered Lost Stolen Replacement
            Online
        </option>
        <option value="OTHER">Other</option>
        <option value="OVE">Overridden</option>
        <option value="OPDT">Oyster newPassword details taken</option>
        <option value="POP">Payment Over The Phone</option>
        <option value="PAR">Payment received</option>
        <option value="PRHR">Photocard re-issued as half-rate</option>
        <option value="PHR">Photograph received</option>
        <option value="PORR">Post office receipt received</option>
        <option value="PFRC">Proforma received</option>
        <option value="POBR">Proof of birth received</option>
        <option value="RO18TC">Received O18 T&amp;Cs</option>
        <option value="RU18TC">Received U18 T&amp;Cs</option>
        <option value="ORPHAN">Removed from web account</option>
        <option value="RPR">Replacement photo received</option>
        <option value="STOLEN">Stolen</option>
        <option value="TACR">Terms and conditions received</option>
        <option value="TFLA">TfL authorise</option>
        <option value="TFL">TfL validated postcode</option>
        <option value="TPEDAUTH">TPED Authorised</option>
        <option value="TRANS">Transferred</option>
        <option value="UC">Uncollected</option>
        <option value="SC2">Wrong card carrier</option>
    </select></td>
    <td><input class="button" type="button" value="History"
               onclick="viewCardHistory(1576982,'000030000175')"
               title="Click to view the history for this Oyster card."
               disabled="disabled"></td>
    <td></td>
    <td><input class="button" type="button" value="TIC"
               onclick="loadTICDetails('50100024521','1576982')"
               title="Click to view the input TIC details for this Oyster card."
               disabled="disabled"></td>
    <td>12-APR-15</td>
    <td class="center">N/A</td>
    <td class="center">Yes</td>
    <td><input class="button" type="button" value="Check Failed"
               onclick="checkFailedCard('300001')"
               title="Click to view the details of PAYG and tickets for this Oyster card.">
    </td>
    <td>
        <button>View</button>
    </td>
</tr>
<tr class="grid_row">
    <td>0000300001791</td>
    <td>802B228A841E04</td>
    <td>30/04/2016</td>
    <td>Active</td>
    <td><select id="HOTLIST*1576982" name="HOTLIST*1576982"
                style="width: 230px" onchange="checkHotlistReason()">
        <option value="">(None)</option>
        <option value="ADI">Address details incorrect</option>
        <option value="ALTMUT">Altered/Mutilated</option>
        <option value="ABW">Ambiguous hand writing</option>
        <option value="APP">Applicant hotlisted</option>
        <option value="ASBE">AS Behaviour</option>
        <option value="ATC">Authorised by Central TIC</option>
        <option value="AUTO">Auto hotlisted by system</option>
        <option value="CE">Card Expired</option>
        <option value="WC2Y">Card is more than 2 years old</option>
        <option value="CNR">Card not received</option>
        <option value="NOTRCVD">Card not received</option>
        <option value="SURRENDER">Card surrendered by student</option>
        <option value="CWBD">Card withdrawn by Bus Driver</option>
        <option value="CWIT">Card withdrawn by Inspector - TATT</option>
        <option value="CDI">Course details incorrect</option>
        <option value="DAM">Damaged</option>
        <option value="DCE">Data capture error</option>
        <option value="DISABL">Disabled</option>
        <option value="EYTB">Earn Your Travel Back</option>
        <option value="EXPIR">Expired</option>
        <option value="FAIL">Failed Card</option>
        <option value="GOGW">Gesture of goodwill by Novacroft</option>
        <option value="RFG">Gone away</option>
        <option value="DOB">Incorrect / missing date of birth</option>
        <option value="IRR">Incorrect rejection reason</option>
        <option value="IHC">Incorrectly hotlisted card</option>
        <option value="LOST">Lost</option>
        <option value="LSD">Lost/Stolen/Damaged</option>
        <option value="MPSWITHD">MPS Withdrawals</option>
        <option value="NDI">Name details incorrect</option>
        <option value="NONVAL">Non Validation</option>
        <option value="LSO">Ordered Lost Stolen Replacement
            Online
        </option>
        <option value="OTHER">Other</option>
        <option value="OVE">Overridden</option>
        <option value="OPDT">Oyster newPassword details taken</option>
        <option value="POP">Payment Over The Phone</option>
        <option value="PAR">Payment received</option>
        <option value="PRHR">Photocard re-issued as half-rate</option>
        <option value="PHR">Photograph received</option>
        <option value="PORR">Post office receipt received</option>
        <option value="PFRC">Proforma received</option>
        <option value="POBR">Proof of birth received</option>
        <option value="RO18TC">Received O18 T&amp;Cs</option>
        <option value="RU18TC">Received U18 T&amp;Cs</option>
        <option value="ORPHAN">Removed from web account</option>
        <option value="RPR">Replacement photo received</option>
        <option value="STOLEN">Stolen</option>
        <option value="TACR">Terms and conditions received</option>
        <option value="TFLA">TfL authorise</option>
        <option value="TFL">TfL validated postcode</option>
        <option value="TPEDAUTH">TPED Authorised</option>
        <option value="TRANS">Transferred</option>
        <option value="UC">Uncollected</option>
        <option value="SC2">Wrong card carrier</option>
    </select></td>
    <td><input class="button" type="button" value="History"
               onclick="viewCardHistory(1576982,'000030000175')"
               title="Click to view the history for this Oyster card."
               disabled="disabled"></td>
    <td></td>
    <td><input class="button" type="button" value="TIC"
               onclick="loadTICDetails('50100024521','1576982')"
               title="Click to view the input TIC details for this Oyster card."
               disabled="disabled"></td>
    <td>12-APR-15</td>
    <td class="center">N/A</td>
    <td class="center">Yes</td>
    <td><input class="button" type="button" value="Check Failed"
               onclick="checkFailedCard('300001')"
               title="Click to view the details of PAYG and tickets for this Oyster card.">
    </td>
    <td>
        <button>View</button>
    </td>
</tr>
<tr class="grid_row">
    <td>0000300001792</td>
    <td>802B228A841E04</td>
    <td>30/04/2016</td>
    <td>Active</td>
    <td><select id="HOTLIST*1576982" name="HOTLIST*1576982"
                style="width: 230px" onchange="checkHotlistReason()">
        <option value="">(None)</option>
        <option value="ADI">Address details incorrect</option>
        <option value="ALTMUT">Altered/Mutilated</option>
        <option value="ABW">Ambiguous hand writing</option>
        <option value="APP">Applicant hotlisted</option>
        <option value="ASBE">AS Behaviour</option>
        <option value="ATC">Authorised by Central TIC</option>
        <option value="AUTO">Auto hotlisted by system</option>
        <option value="CE">Card Expired</option>
        <option value="WC2Y">Card is more than 2 years old</option>
        <option value="CNR">Card not received</option>
        <option value="NOTRCVD">Card not received</option>
        <option value="SURRENDER">Card surrendered by student</option>
        <option value="CWBD">Card withdrawn by Bus Driver</option>
        <option value="CWIT">Card withdrawn by Inspector - TATT</option>
        <option value="CDI">Course details incorrect</option>
        <option value="DAM">Damaged</option>
        <option value="DCE">Data capture error</option>
        <option value="DISABL">Disabled</option>
        <option value="EYTB">Earn Your Travel Back</option>
        <option value="EXPIR">Expired</option>
        <option value="FAIL">Failed Card</option>
        <option value="GOGW">Gesture of goodwill by Novacroft</option>
        <option value="RFG">Gone away</option>
        <option value="DOB">Incorrect / missing date of birth</option>
        <option value="IRR">Incorrect rejection reason</option>
        <option value="IHC">Incorrectly hotlisted card</option>
        <option value="LOST">Lost</option>
        <option value="LSD">Lost/Stolen/Damaged</option>
        <option value="MPSWITHD">MPS Withdrawals</option>
        <option value="NDI">Name details incorrect</option>
        <option value="NONVAL">Non Validation</option>
        <option value="LSO">Ordered Lost Stolen Replacement
            Online
        </option>
        <option value="OTHER">Other</option>
        <option value="OVE">Overridden</option>
        <option value="OPDT">Oyster newPassword details taken</option>
        <option value="POP">Payment Over The Phone</option>
        <option value="PAR">Payment received</option>
        <option value="PRHR">Photocard re-issued as half-rate</option>
        <option value="PHR">Photograph received</option>
        <option value="PORR">Post office receipt received</option>
        <option value="PFRC">Proforma received</option>
        <option value="POBR">Proof of birth received</option>
        <option value="RO18TC">Received O18 T&amp;Cs</option>
        <option value="RU18TC">Received U18 T&amp;Cs</option>
        <option value="ORPHAN">Removed from web account</option>
        <option value="RPR">Replacement photo received</option>
        <option value="STOLEN">Stolen</option>
        <option value="TACR">Terms and conditions received</option>
        <option value="TFLA">TfL authorise</option>
        <option value="TFL">TfL validated postcode</option>
        <option value="TPEDAUTH">TPED Authorised</option>
        <option value="TRANS">Transferred</option>
        <option value="UC">Uncollected</option>
        <option value="SC2">Wrong card carrier</option>
    </select></td>
    <td><input class="button" type="button" value="History"
               onclick="viewCardHistory(1576982,'000030000175')"
               title="Click to view the history for this Oyster card."
               disabled="disabled"></td>
    <td></td>
    <td><input class="button" type="button" value="TIC"
               onclick="loadTICDetails('50100024521','1576982')"
               title="Click to view the input TIC details for this Oyster card."
               disabled="disabled"></td>
    <td>12-APR-15</td>
    <td class="center">N/A</td>
    <td class="center">Yes</td>
    <td><input class="button" type="button" value="Check Failed"
               onclick="checkFailedCard('300001')"
               title="Click to view the details of PAYG and tickets for this Oyster card.">
    </td>
    <td>
        <button>View</button>
    </td>
</tr>
<tr class="grid_row">
    <td>0000300001793</td>
    <td>802B228A841E04</td>
    <td>10/01/2015</td>
    <td>Active</td>
    <td><select id="HOTLIST*1576982" name="HOTLIST*1576982"
                style="width: 230px" onchange="checkHotlistReason()">
        <option value="">(None)</option>
        <option value="ADI">Address details incorrect</option>
        <option value="ALTMUT">Altered/Mutilated</option>
        <option value="ABW">Ambiguous hand writing</option>
        <option value="APP">Applicant hotlisted</option>
        <option value="ASBE">AS Behaviour</option>
        <option value="ATC">Authorised by Central TIC</option>
        <option value="AUTO">Auto hotlisted by system</option>
        <option value="CE">Card Expired</option>
        <option value="WC2Y">Card is more than 2 years old</option>
        <option value="CNR">Card not received</option>
        <option value="NOTRCVD">Card not received</option>
        <option value="SURRENDER">Card surrendered by student</option>
        <option value="CWBD">Card withdrawn by Bus Driver</option>
        <option value="CWIT">Card withdrawn by Inspector - TATT</option>
        <option value="CDI">Course details incorrect</option>
        <option value="DAM">Damaged</option>
        <option value="DCE">Data capture error</option>
        <option value="DISABL">Disabled</option>
        <option value="EYTB">Earn Your Travel Back</option>
        <option value="EXPIR">Expired</option>
        <option value="FAIL">Failed Card</option>
        <option value="GOGW">Gesture of goodwill by Novacroft</option>
        <option value="RFG">Gone away</option>
        <option value="DOB">Incorrect / missing date of birth</option>
        <option value="IRR">Incorrect rejection reason</option>
        <option value="IHC">Incorrectly hotlisted card</option>
        <option value="LOST">Lost</option>
        <option value="LSD">Lost/Stolen/Damaged</option>
        <option value="MPSWITHD">MPS Withdrawals</option>
        <option value="NDI">Name details incorrect</option>
        <option value="NONVAL">Non Validation</option>
        <option value="LSO">Ordered Lost Stolen Replacement
            Online
        </option>
        <option value="OTHER">Other</option>
        <option value="OVE">Overridden</option>
        <option value="OPDT">Oyster newPassword details taken</option>
        <option value="POP">Payment Over The Phone</option>
        <option value="PAR">Payment received</option>
        <option value="PRHR">Photocard re-issued as half-rate</option>
        <option value="PHR">Photograph received</option>
        <option value="PORR">Post office receipt received</option>
        <option value="PFRC">Proforma received</option>
        <option value="POBR">Proof of birth received</option>
        <option value="RO18TC">Received O18 T&amp;Cs</option>
        <option value="RU18TC">Received U18 T&amp;Cs</option>
        <option value="ORPHAN">Removed from web account</option>
        <option value="RPR">Replacement photo received</option>
        <option value="STOLEN">Stolen</option>
        <option value="TACR">Terms and conditions received</option>
        <option value="TFLA">TfL authorise</option>
        <option value="TFL">TfL validated postcode</option>
        <option value="TPEDAUTH">TPED Authorised</option>
        <option value="TRANS">Transferred</option>
        <option value="UC">Uncollected</option>
        <option value="SC2">Wrong card carrier</option>
    </select></td>
    <td><input class="button" type="button" value="History"
               onclick="viewCardHistory(1576982,'000030000175')"
               title="Click to view the history for this Oyster card."
               disabled="disabled"></td>
    <td></td>
    <td><input class="button" type="button" value="TIC"
               onclick="loadTICDetails('50100024521','1576982')"
               title="Click to view the input TIC details for this Oyster card."
               disabled="disabled"></td>
    <td>12-APR-15</td>
    <td class="center">N/A</td>
    <td class="center">Yes</td>
    <td><input class="button" type="button" value="Check Failed"
               onclick="checkFailedCard('300001')"
               title="Click to view the details of PAYG and tickets for this Oyster card.">
    </td>
    <td>
        <button>View</button>
    </td>
</tr>
</tbody>
</table>

</div>