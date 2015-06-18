<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Journey History</title>
    <style>
        * {
            font-family: Arial, sans-serif;
            font-size: 10pt;
        }

        .customer-address {
            float: left;
            text-align: left;
        }

        .tfl-address {
            float: right;
            text-align: right;
        }

        .clear-float {
            clear: both;
        }

        .statement-parameter {
        }

        h1 {
            text-align: center;
            font-size: 10pt;
            font-weight: bold;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            -fs-table-paginate: paginate;
        }

        thead {
            background-color: #e7eff4;
            font-weight: bold;
            display: table-header-group;
        }

        tr.daily {
            background-color: #bedaea;
            font-weight: bold;
        }

        tr.journey {
        }

        td {
            vertical-align: top;
        }

        td.money, th.money {
            text-align: right;
        }

        .icon {
            background-image: url(${icon1});
            background-image: url(${icon2}) \9;
            background-size: 96px 1344px;
            width: 32px;
            height: 32px;
            display: block;
            background-position: -32px -544px;
        }

        .warning-icon {
            background-position: 0px -832px;
        }

        .capped-icon {
            background-position: -64px -480px;
        }

        @page {
            counter-increment: page;
            @bottom-right {
                font-family: Arial, sans-serif;
                font-size: 8pt;
                content: "Page " counter(page);
            }
        }
    </style>
</head>
<body>
<img alt="TfL Logo" src="${logo}" height="65px" width="450px"/> <img class="tfl-address" alt="Oyster  Logo" src="${oyster}" height="55px" width="55px"/> 
<br/>
<br/>

<div>
    <div class="customer-address">
    <#list nameAndAddressLines as nameAndAddressLine>
    ${nameAndAddressLine}<br/>
    </#list>
        <br/>
        <span class="statement-parameter">Oyster journey statement created on :</span> ${now?string("EEE, dd MMMM yyyy")}<br/>
        <span class="statement-parameter">For Oyster card :</span> ${journeyHistory.cardNumber}<br/>
        <span class="statement-parameter">Dates covered :</span> ${journeyHistory.rangeFrom?string("dd/MM/yyyy")}
        to ${journeyHistory.rangeTo?string("dd/MM/yyyy")}
    </div>
    <div class="tfl-address">
        TfL Customer Services<br/>
        4th Floor<br/>
        14 Pier Walk<br/>
        London SE10 0ES<br/>
        Tel: 0343 222 1234<br/>
        www.tfl.gov.uk/oyster
    </div>
</div>
<div class="clear-float"></div>
<div>
    <h1>Oyster Journey Statement</h1>
    <hr/>
    <table>
        <thead>
        <tr>
            <th>Date/Time</th>
            <th>Journey/Action</th>
            <th class="money">Charge</th>
            <th class="money">Credit</th>
            <th class="money">Balance</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <#list journeyHistory.journeyDays as journeyDay>
        <tr class="daily">
            <td colspan="2">${journeyDay.effectiveTrafficOn?string("EEE, dd MMM yyyy")}</td>
            <td colspan="2"></td>
            <td></td>
            <td>&nbsp;</td>
        </tr>
            <#list journeyDay.journeys as journey>
                <#if !journey.suppressCode>
                <#if journey_index=0>
		        <tr class="journey">
		            <td colspan="2"></td>
		            <td colspan="2" class="money">Start Balance:</td>
		            <td class="money">${((journey.storedValueBalance - journey.addedStoredValueBalance)/100)?string("'&pound;'###,###,##0.00")}</td>
		            <td>&nbsp;</td>
		        </tr>
				</#if>
                <tr class="journey">
                    <td>${journey.journeyDisplay.journeyTime?html?replace(" ", "&nbsp;")}</td>
                    <td>${journey.journeyDisplay.journeyDescription?html}</td>
                    <td class="money">
                        <#if journey.journeyDisplay.chargeAmount?has_content >
                        ${(journey.journeyDisplay.chargeAmount/100)?string("'&pound;'###,###,##0.00")}
                    </#if>
                    </td>
                    <td class="money">
                        <#if journey.journeyDisplay.creditAmount?has_content >
                        ${(journey.journeyDisplay.creditAmount/100)?string("'&pound;'###,###,##0.00")}
                    </#if>
                    </td>
                    <td class="money">
                        <#if journey.storedValueBalance?has_content >
                    ${(journey.storedValueBalance/100)?string("'&pound;'###,###,##0.00")}
                    </#if>
                    </td>
                    <td>
                        <#if journey.journeyDisplay.warning >
                            <span class="icon warning-icon"></span>
                        </#if>
                        <#if journey.dailyCappingFlag >
                            <span class="icon capped-icon"></span>
                        </#if>
                        <#if journey.autoCompletionFlag>
                            <span class="icon autocompleted-icon"></span>
                        </#if>
                        <#if journey.journeyDisplay.manuallyCorrected>
                            <span class="icon manually-corrected-icon"></span>
                        </#if>
                    </td>
                </tr>
                </#if>
            </#list>
        </#list>
        </tbody>
    </table>
</div>
</body>
</html>
