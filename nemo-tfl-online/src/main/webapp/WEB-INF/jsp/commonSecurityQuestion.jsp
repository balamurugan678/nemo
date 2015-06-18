<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>

<nemo-tfl:selectList id="securityQuestion" path="securityQuestion" selectList="${securityQuestions}"
                     mandatory="true" selectedValue="${securityQuestionCmd.securityQuestion}"/>
<form:errors path="securityQuestion" cssClass="field-validation-error"/>
<to:password id="securityAnswer" mandatory="true"></to:password>
<to:password id="confirmSecurityAnswer" mandatory="true"></to:password>
<to:mandatoryFields/>
            

