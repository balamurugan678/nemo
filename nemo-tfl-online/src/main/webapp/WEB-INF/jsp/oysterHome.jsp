<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

<nemo-tfl:breadcrumbs pageNames='<%=new String[]{}%>'/>

<to:headLine/>

<div class="r">
    <div class="main">
        <div>
            <to:warningBox id="promo"/>
            <to:head2section id="upper" showLink="true"/>
            <to:head2section id="lower" showLink="true"/>
        </div>
    </div>
    <div class="aside">

        <c:if test="${isUserLoggedIn eq 'false'}">
            <div class="large" data-set="widget-holder">
                <div id="sign-up" class="moving-source-order csc-module">
                    <to:tabHead2 id="login"/>
                    <div class="box">
                        <form:form action="<%= PageUrl.LOGIN %>" commandName="<%= PageCommand.LOGIN %>" method="post">
                            <to:text id="username" inputCssClass="shaded-input" labelCssClass="visually-hidden"/>
                            <to:password id="password" mandatory="true" inputCssClass="shaded-input" labelCssClass="visually-hidden"/>
                            <form:errors cssClass="field-validation-error"/>
                            <to:submitButton id="signIn" buttonCssClass="primary-button"/>
                        </form:form>
                        <to:linkButton id="forgottenPassword" linkParameters="returnURL=${returnURL}"/>
                        <hr/>
                        <to:head2 id="signUp"/>
                        <to:linkButton id="signUp"/>
                    </div>
                </div>
            </div>
        </c:if>

        <c:if test="${isUserLoggedIn eq 'true'}">
            <to:tabHead2 id="username" headingOverride="${username}"/>
            <div class="box csc-module">
                <to:linkButtonBlock links='<%=new String[]{"logout", "dashboard"}%>'/>
            </div>
        </c:if>
		<c:if test="${isUserLoggedIn ne 'true'}">
        	<to:linkButton id="orderOysterCard"/>
        </c:if>
    </div>
</div>           
