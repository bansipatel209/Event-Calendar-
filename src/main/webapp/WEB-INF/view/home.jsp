<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
body {
	font-family: Arial, Helvetica, sans-serif;
	background-color: black;
}

* {
	box-sizing: border-box;
}

/* Add padding to containers */
.container {
	padding: 16px;
	background-color: white;
}

/* Full-width input fields */
input[type=text], input[type=password] {
	width: 100%;
	padding: 15px;
	margin: 5px 0 22px 0;
	display: inline-block;
	border: none;
	background: #f1f1f1;
}

input[type=text]:focus, input[type=password]:focus {
	background-color: #ddd;
	outline: none;
}

/* Overwrite default styles of hr */
hr {
	border: 1px solid #f1f1f1;
	margin-bottom: 25px;
}

/* Set a style for the submit button */
.registerbtn {
	background-color: #04AA6D;
	color: white;
	padding: 16px 20px;
	margin: 8px 0;
	border: none;
	cursor: pointer;
	width: 100%;
	opacity: 0.9;
}

.registerbtn:hover {
	opacity: 1;
}

/* Add a blue text color to links */
a {
	color: dodgerblue;
}

/* Set a grey background color and center the text of the "sign in" section */
.signin {
	background-color: #f1f1f1;
	text-align: center;
}
</style>
</head>
<body>

	<form action="/add-event" method="post" name="addevent">
		<div class="container">
			<h1>
				<center>Add Calander Event</center>
			</h1>
			<hr>

			<label for="eventName"><b>Event Name</b></label> <input type="text"
				placeholder="Enter Event Name" name="eventName" id="eventName"
				required> <label for="startDate"><b>Start Date</b></label> <input
				type="date" name="startDate" id="startDate" required> <label
				for="endDate"><b>End Date</b></label> <input type="date"
				name="endDate" id="endDate" required> <label for="startTime"><b>Start
					Time</b></label> <input type="time" name="startTime" id="startTime" required>

			<label for="endTime"><b>End Time</b></label> <input type="time"
				name="endTime" id="endTime" required>

			<hr>
			<input type="checkbox" id="day" name="WeekDays" value="WeekDays">
			<label for="day"> Week-Days</label><br> <input type="checkbox"
				id="day" name="WeekEnd" value="WeekEnd"> <label for="day">
				Week-End</label><br>

			<hr>
			<button type="submit" class="registerbtn">Add Event</button>
		</div>

		<center>
			<h1><a href="/logout">logout</a><h1>
		</center>


	</form>

</body>
</html>
