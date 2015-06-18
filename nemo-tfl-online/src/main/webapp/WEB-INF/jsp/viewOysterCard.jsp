<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<nemo-tfl:breadcrumbs pageNames='<%=new String[]{Page.DASHBOARD, Page.VIEW_OYSTER_CARD}%>'/>
<!-- TODO: IMPORTANT - this is a temporary header to prevent a javascript error. should be removed from production -->
<!--data-->
<div class="r">
    <div class="main">
        <div>
            <div class="page-heading">
                <to:headLineWithParameter headingOverride="${manageCardCmd.cardNumber}"/>
            </div>
            <div class="card-info with-icon">
                <span class="icon oyster-card-icon"></span>

                <div>
                    <c:choose>
                        <c:when test="${!empty card.nickname}">
			                        <span class="info-line">
			                            <span class="underline">${card.nickname}</span>
			                        </span>
                        </c:when>
                        <c:when test="${empty card.nickname}">
			                        <span class="info-line paddingTop">
			                            <to:textPlain id="nickname"/>
			                        </span>
                        </c:when>
                    </c:choose>
                </div>
            </div>
            <div class="page-heading with-border ">
                <to:head2 id="cardManagement"/>
            </div>
            <to:linkButtonBlock
                    links="<%= new String[] {
	                	Page.TOP_UP_TICKET, 
	                	Page.LOST_OR_STOLEN_CARD,
	                	Page.JOURNEY_HISTORY,
	                	Page.CHANGE_SECURITY_QUESTION, 
                        Page.CHANGE_CARD_PREFERENCES,
	                	Page.MANAGE_AUTO_TOP_UP,
	                	Page.TRANSFER_PRODUCT,
	                	Page.VIEW_AUTO_TOP_UP_HISTORY
	                    }%>"/>
        </div>
    </div>
    <tiles:insertAttribute name="myAccount"/>
</div>




