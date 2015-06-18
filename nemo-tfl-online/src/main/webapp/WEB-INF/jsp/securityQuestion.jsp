<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

<nemo-tfl:breadcrumbs
        pageNames='<%=new String[]{Page.DASHBOARD,Page.SECURITY_QUESTION}%>'/>
<to:areaheader title="securityQuestion.heading"/>

<div class="r">
    <div class="instructional-container radio-accordian-list csc-module">
      <div class="content">
          <ol>
              <li>
                  <div class="item-content">
                      <p><spring:message code="SecurityQuestion.upper.text1"/></p>
                  </div>
              </li>
              <li>
                  <div class="item-content">
                      <p><spring:message code="SecurityQuestion.upper.text2"/></p>
                  </div>
              </li>
              <li>
                  <div class="item-content">
                      <p><spring:message code="SecurityQuestion.upper.text3"/></p>
                  </div>
              </li>
              <li>
                  <div class="item-content">
                      <p><spring:message code="SecurityQuestion.upper.text4"/></p>
                  </div>
              </li>
          </ol>
      </div>
   </div>
</div>
<div class="r">
    <form:form action="<%= PageUrl.SECURITY_QUESTION %>" commandName="<%= PageCommand.SECURITY_QUESTION %>"
               cssClass="form-with-tooltips">
        <div class="box borderless">
            <to:head2 id="cardRegistration"/>
            <to:paragraph id="info"/>
            <jsp:include page="commonSecurityQuestion.jsp"></jsp:include>
            <hr>
            <to:buttons targetAction="<%= PageParameterValue.ADD %>"></to:buttons>
        </div>
        <to:hidden id="cardNumber"/>
        <to:hidden id="cardId"/>
    </form:form>
</div>