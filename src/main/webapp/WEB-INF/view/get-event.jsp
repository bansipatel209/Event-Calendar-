<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Event Calendar</title>
<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/1.10.16/css/jquery.dataTables.min.css" />
<style>
body {
	background: #111 no-repeat;
	background-image: -webkit-gradient(radial, 50% 0, 150, 50% 0, 300, from(#444),
		to(#111));
}

h1 {
	text-align: center;
	color: #FFF;
}

.container {
	width: 1000px;
	margin: auto;
	font-size: 25px;
}

#content {
	border: dashed 2px #CCC;
	padding: 10px;
	background-color: #FFF;
}
</style>
</head>

<body>
	<h1>Event Calendar</h1>
	<div class="container">
		<div id="content">
			<table id="table1">
				<thead>
					<tr>
						<th>Id</th>
						<th>Event Name</th>
						<th>Start Date</th>
						<th>End Date</th>
						<th>Start Time</th>
						<th>End Time</th>
						<th>DOW</th>
						<th>Binary DOW</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="event" items="${events}">
						<tr>
							<td>${event.id}</td>
							<td>${event.eventName}</td>
							<td>${event.startDate}</td>
							<td>${event.endDate}</td>
							<td>${event.startTime}</td>
							<td>${event.endTime}</td>
							<td>${event.dayOfWeek}</td>
							<td>${event.binaryDOW}</td>
							<td><a href="delete-event/${event.id}">Delete</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>

	<center>
		<h1>
			<a href="/logout">logout</a>
			<h1>
	</center>

	<!--Reference to jQuery-->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
	<script type="text/javascript"
		src="https://cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js"></script>

	<script>
		$(document).ready(function() {
			$('#table1').DataTable();
		});
	</script>
</body>

</html>