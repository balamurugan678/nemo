<%@include file="/WEB-INF/jspf/pageCommon.jspf"%>
<%@ page
	import="com.novacroft.nemo.tfl.innovator.controller.ApprovalController"%>


<div style="width: 500px; margin: auto auto;">

	Stage : ${releaseCmd.stage } </br>

	<h1>Assigned to users</h1>

	<h4>FirstStageApprover1</h4>

	<c:choose>
		<c:when test="${empty releaseCmd.tasks }">
There are no tasks assigned of this type
</c:when>
		<c:otherwise>
			<table>
				<form class="form-with-tooltips" action="${releaseCmd.formLocation}"
					method="POST">
					<c:forEach items="${releaseCmd.tasks}" var="task">
						<tr>
							<td>${task.name }</td>
							<td>${task.id }</td>
							<td>${task.description }</td>
							<td>${task.name }</td>
							<td>${task.name }</td>
							<td>${task.name }</td>
							<td><button type="submit" value="${task.id}"
									name="claimTaskId">Release</button>
						<tr>
					</c:forEach>
				</form>
			</table>
		</c:otherwise>
	</c:choose>
	<h4>Sys Approver</h4>
	<c:choose>
		<c:when test="${empty releaseCmd.tasks2 }">
		There are no tasks assigned of this type
		</c:when>
		<c:otherwise>
			<table>
				<form class="form-with-tooltips" action="${releaseCmd.formLocation}"
					method="POST">
					<c:forEach items="${releaseCmd.tasks2}" var="task">
						<tr>
							<td>${task.name }</td>
							<td>${task.id }</td>
							<td>${task.description }</td>
							<td>${task.name }</td>
							<td>${task.name }</td>
							<td>${task.name }</td>
							<td><button type="submit" value="${task.id}"
									name="claimTaskId">Release</button>
						<tr>
					</c:forEach>
				</form>
			</table>
		</c:otherwise>
	</c:choose>
	<h4>SecondStage Approver</h4>
	<c:choose>
		<c:when test="${empty releaseCmd.tasks3 }">
			There are no tasks assigned of this type
			</c:when>
		<c:otherwise>
			<table>
				<form class="form-with-tooltips" action="${releaseCmd.formLocation}"
					method="POST">
					<c:forEach items="${releaseCmd.tasks3}" var="task">
						<tr>
							<td>${task.name }</td>
							<td>${task.id }</td>
							<td>${task.description }</td>
							<td>${task.name }</td>
							<td>${task.name }</td>
							<td>${task.name }</td>
							<td><button type="submit" value="${task.id}"
									name="claimTaskId">Release</button>
						<tr>
					</c:forEach>
				</form>
			</table>
		</c:otherwise>
	</c:choose>
	<h1>Assigned to Groups</h1>
	<h4>First Stage Group unassigned</h4>
	<c:choose>
		<c:when test="${empty releaseCmd.tasks4 }">
		There are no tasks assigned of this type
		</c:when>
		<c:otherwise>
			<table>
				<form class="form-with-tooltips" action="${releaseCmd.formLocation}"
					method="POST">
					<c:forEach items="${releaseCmd.tasks4}" var="task">
						<tr>
							<td>${task.name }</td>
							<td>${task.id }</td>
							<td>${task.description }</td>
							<td>${task.name }</td>
							<td>${task.name }</td>
							<td>${task.name }</td>
							<td><button type="submit" value="${task.id}"
									name="claimTaskId">Roll Back</button>
						<tr>
					</c:forEach>
				</form>
			</table>
		</c:otherwise>
	</c:choose>
	<h4>System Stage Group unassigned</h4>
	<c:choose>
		<c:when test="${empty releaseCmd.tasks5 }">
		There are no tasks assigned of this type
		</c:when>
		<c:otherwise>
			<table>
				<form class="form-with-tooltips" action="${releaseCmd.formLocation}"
					method="POST">
					<c:forEach items="${releaseCmd.tasks5}" var="task">
						<tr>
							<td>${task.name }</td>
							<td>${task.id }</td>
							<td>${task.description }</td>
							<td>${task.name }</td>
							<td>${task.name }</td>
							<td>${task.name }</td>
							<td><button type="submit" value="${task.id}"
									name="claimTaskId">Roll Back</button>
						<tr>
					</c:forEach>
				</form>
			</table>
		</c:otherwise>
	</c:choose>
	<h4>Second Stage Group unassigned</h4>
	<c:choose>
		<c:when test="${empty releaseCmd.tasks6 }">
		There are no tasks assigned of this type
		</c:when>
		<c:otherwise>
			<table>
				<form class="form-with-tooltips" action="${releaseCmd.formLocation}"
					method="POST">
					<c:forEach items="${releaseCmd.tasks6}" var="task">
						<tr>
							<td>${task.name }</td>
							<td>${task.id }</td>
							<td>${task.description }</td>
							<td>${task.name }</td>
							<td>${task.name }</td>
							<td>${task.name }</td>
							<td><button type="submit" value="${task.id}"
									name="claimTaskId">Roll Back</button>
						<tr>
					</c:forEach>
				</form>
			</table>
		</c:otherwise>
	</c:choose>
</div>
</table>