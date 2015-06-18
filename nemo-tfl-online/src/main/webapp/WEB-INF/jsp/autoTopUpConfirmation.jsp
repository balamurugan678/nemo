<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

<nemo-tfl:breadcrumbs pageNames='<%=new String[]{Page.DASHBOARD}%>'/>
<to:areaheader title="heading"/>

<div class="search-tools" style="display:none;">
    <label for="q" class="visually-hidden">Search the site</label>

    <div style="position: relative;" class="remove-content-container empty">
        <input type="text" placeholder="Search" id="q" name="q" autocomplete="off">
        <a href="javascript:void(0)" style="position: absolute; cursor: pointer; display: none; top: 4px; right: 5px;"
           class="remove-content hide-text">Clear search</a>
    </div>
    <input id="search-button" type="submit" class="hide-text" value="Search">
</div>
<div class="r">
            <div>
                <div class="page-heading variable-heading-indent with-border ">
                    <h1><spring:message code="autotopup.text1"/></h1>
                </div>
            </div>
        </div>
<div class="r">
    <div class="main">
        <div class="oo-module">
            <p>
                <spring:message code="autotopup.text2"/> <span class="bold-font-aligned"> ${manageCardCmd.stationName} </span>
                <spring:message code="autotopup.text3"/>${manageCardCmd.startDateforAutoTopUpCardActivate}
            	<c:if test="${manageCardCmd.isStartDateTomorrow}">
            		(tomorrow)
            	</c:if>
                <spring:message code="autotopup.text4"/>
            <div><spring:message code="autotopup.text5"/>:</div>
            <div>
            	${manageCardCmd.startDateforAutoTopUpCardActivate}
            	<c:if test="${manageCardCmd.isStartDateTomorrow}">
            		(tomorrow)
            	</c:if>
            </div> 
            <div> ${manageCardCmd.endDateforAutoTopUpCardActivate}</div>

            <spring:message code="autotopup.text6"/><br>

            <spring:message code="autotopup.text7"/>: ${manageCardCmd.orderNumber}<br>
            
            <spring:message code="autotopup.text8"/> ${manageCardCmd.emailAddress}
            </p>
        </div>
    </div>
    <tiles:insertAttribute name="myAccount"/>
</div>
        
