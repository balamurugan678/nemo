<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

<nemo-tfl:selectList id="securityQuestion" path="securityQuestion" selectList="${securityQuestions}"
                     mandatory="true" selectedValue="${securityQuestionCmd.securityQuestion}"/>

<form:errors path="securityQuestion" cssClass="field-validation-error"/>
<to:text id="securityAnswer" mandatory="true"/>
<to:text id="confirmSecurityAnswer" mandatory="true"/>
    

