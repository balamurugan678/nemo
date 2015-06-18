<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>



Approval required:<br>

Stage : ${approvalCmd.stage } </br>

<div style ="width:500px;margin:auto auto;padding 20px;">

	<form id="approval" class="form-with-tooltips" action="${approvalCmd.formLocation}" method="POST">
		<button type="submit" value="yes" Name="required">Yes</button>
		<button type="submit" value="no" Name="required">No</button>
		<input type="hidden" name="claimTaskId" value="${approvalCmd.taskId}" />
	</form>

</div>