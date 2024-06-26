<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script type="text/JavaScript" src="https://MomentJS.com/downloads/moment-with-locales.js"></script>
<script src="https://cdn.jsdelivr.net/npm/moment-range@4.0.2/dist/moment-range.min.js"></script>

<html>
    <head>
        <script type="text/javascript">
            var rowNo = 0;
            var min30Diff = 0;
            var schedules = [];
            function validateData() {
                var startTime = document.getElementById("startTime").value;
                var stopTime = document.getElementById("stopTime").value;
                var startArray = startTime.split(":");
                var stopArray = stopTime.split(":");
                const startHr = Number(startArray[0]);
                const startMin = Number(startArray[1]);
                const stopHr = Number(stopArray[0]);
                const stopMin = Number(stopArray[1]);

                var startTimeDiff = startHr * 60 + startMin;
                var stopTimeDiff = stopHr * 60 + stopMin;

                min30Diff = stopTimeDiff - startTimeDiff;

                if (startHr > stopHr || (startHr == stopHr && startMin >= stopMin)) {

                    return false;
                } else if (min30Diff < 30) {
                    alert("Stop time must be at least 30 minutes more than start time.");
                    return false;
                } else if (overLappingTime(startTime, stopTime)) {

//                    alert("Timing for job is Invalid..... Overlapping your Timing");
                } else {

                    return true;

                }

            }
            var st = "";
            var et = "";
            var est = "";
            var eet = "";
            function overLappingTime(start, stop) {


                var startTime = moment(start, "HH:mm");
                var stopTime = moment(stop, "HH:mm");

                st += startTime;
                et += stopTime;


//              overlapping time
                var table = document.getElementById("scheduleTimeTableBody");
//                console.log(table.rows.length)
                for (var i = 0; i < table.rows.length; i++) {
                    var existingStartTime = moment(table.rows[i].cells[0].innerHTML, "HH:mm");
                    var existingStopTime = moment(table.rows[i].cells[1].innerHTML, "HH:mm");

                    est += existingStartTime;
                    eet += existingStopTime;

                    if (startTime.isAfter(existingStartTime) || startTime.isSame(existingStartTime) && startTime.isBefore(existingStopTime)) {
                        console.log("your start time not allowed in between of existing start and stop time");
                        return true;
                    } else if (stopTime.isAfter(existingStartTime) && stopTime.isBefore(existingStopTime) || stopTime.isSame(existingStopTime)) {
                        console.log("your stop time not allowed in between of existing start and stop time");

                        return true;
                    } else if (startTime.isBefore(existingStartTime) && stopTime.isAfter(existingStopTime)) {
                        console.log("your Start Time is before  and Stop time  is  after of Existing Start and Stop Time is not Allowed");
                        return true;
                    }
                }

                return false;
            }

            function addData() {
                if (validateData()) {
                    document.getElementById("timeError").innerHTML = "";
                    return addRow(document.getElementById("startTime").value, document.getElementById("stopTime").value);
                } else if (min30Diff < 30) {
                    document.getElementById("timeError").innerHTML = "Job timing must be 30 minutes or greater than 30 minutes";
                } else if (!overLappingTime(startTime, stopTime)) {

                    if (st > est || st === est && st < eet) {
                        document.getElementById("timeError").innerHTML = "Your Start Time not allowed in between of existing Start and Stop Time";

                    } else if (et === eet) {
                        document.getElementById("timeError").innerHTML = "Error";
                    }
                    else if (et > est && et < eet) {
                        document.getElementById("timeError").innerHTML = "Your Stop Time not allowed in between of Existing Start and Stop Time";
                    } else if (st < est && et > eet) {
                        console.log("error")
                        document.getElementById("timeError").innerHTML = "Your Start Time is before and Stop Time is after of Existing Start Time and Stop Time is not Allowed";
                    }


                } else {
                    document.getElementById("timeError").innerHTML = "Stop time must be greater than Start";
                    return true;
                }
            }

            function addRow(startTime, stopTime) {
                var t = document.getElementById("scheduleTimeTableBody");
                var r = t.insertRow(-1);
                r.setAttribute("data-id", rowNo);
                var cell1 = r.insertCell(0);
                var cell2 = r.insertCell(1);
                var cell3 = r.insertCell(2);
                var cell4 = r.insertCell(3);
                cell1.innerHTML = startTime;
                cell2.innerHTML = stopTime
                cell3.innerHTML = "<a href='javascript:editRow(" + rowNo + ");'>Edit</a>";
                cell4.innerHTML = "<a href='javascript:deleteRow(" + rowNo + ");'>Delete</a>";
                document.getElementById("startTime").value = "";
                document.getElementById("stopTime").value = "";
                rowNo++;
                return true;
            }

            function deleteAllRows() {
                var table = document.getElementById("scheduleTimeTableBody");
                console.log("table rows foudn = " + table.rows.length);
                const tableRowCount = table.rows.length;
                for (var x = 0; x < tableRowCount; x++) {
                    console.log('Going to delete rowNo ' + x)
                    table.deleteRow(0);
                }
                rowNo = 0;
            }

            function deleteRow(varRowNo) {
                var table = document.getElementById("scheduleTimeTableBody");
                for (var x = 0; x < table.rows.length; x++) {
                    var r = table.rows[x];
                    var id = r.getAttribute("data-id");
                    if (varRowNo == id) {
                        table.deleteRow(x);
                        break;
                    }
                }
            }

            function editRow(varRowNo) {
                var table = document.getElementById("scheduleTimeTableBody");
                for (var x = 0; x < table.rows.length; x++) {
                    var r = table.rows[x];
                    var id = r.getAttribute("data-id");
                    if (varRowNo == id) {
                        document.getElementById("startTime").value = r.cells[0].innerHTML;
                        document.getElementById("stopTime").value = r.cells[1].innerHTML;
                        table.deleteRow(x);
                        break;
                    }
                }
            }

            function buildScheduleJson() {
                var table = document.getElementById("scheduleTimeTableBody");
                var jsonString = "[";
                for (var x = 0; x < table.rows.length; x++) {
                    var r = table.rows[x];
                    jsonString += "{'startTime':'" + r.cells[0].innerHTML + "', 'stopTime':'" + r.cells[1].innerHTML + "'},";
                }
                if (jsonString.length != 1) {
                    jsonString = jsonString.substring(0, jsonString.length - 1);
                }
                jsonString += "]";
                document.getElementById("scheduleJson").value = jsonString;
                return true;
            }

            function getDrScheduleTimes() {
                deleteAllRows();
                const formData = new FormData();
                formData.append('doctorId', document.getElementById("doctor").value);
                formData.append('scheduleDate', document.getElementById("scheduleDate").value);
                formData.append('${_csrf.parameterName}', '${_csrf.token}');
                // Set up options for the fetch request
                const options = {
                    method: 'POST',
                    body: formData
                };
                // Make the fetch request with the provided options
                fetch('ajaxGetDoctorSchedules.htm', options)
                        .then(response => {
                            // Check if the request was successful
                            if (!response.ok) {
                                throw new Error('Ajax call failed');
                            }
                            // Parse the response as JSON
                            return response.json();
                        })

                        .then(data => {
                            // Handle the JSON data
                            const scheduleTimeList = data;

                            for (var x = 0; x < scheduleTimeList.length; x++) {
                                addRow(scheduleTimeList[x].startTime, scheduleTimeList[x].stopTime);
                            }
                        })
                        .catch(error => {
                            // Handle any errors that occurred during the fetch
                            console.error('Fetch error:', error);
                        });
            }
        </script>
    </head>
    <body>
        <form:form name="addDoctorSchedule" modelAttribute="doctorSchedule" method="POST">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" name="scheduleJson" id="scheduleJson" value=""/>
            Doctor : <form:select path="doctor" onchange="getDrScheduleTimes();">
                <form:option value="0">Please select Dr</form:option>
                <form:options items="${doctorList}" itemValue="id" itemLabel="label"/>
            </form:select><br/><br/>
            Schedule date : <form:input path="scheduleDate" onchange="getDrScheduleTimes();"/><br/><br/><br/>

            Start time : <input type="text" name="startTime" id="startTime"/><br/>
            Stop time : <input type="text" name="stopTime" id="stopTime"/><span id="timeError"></span><br/>
            <input type="button" value="Add" onclick="addData();"/>
            <br/><br/>
            <table border="1">
                <thead>
                    <tr>
                        <th>Start time</th>
                        <th>Stop time</th>
                        <th>Edit</th>
                        <th>Delete</th>
                    </tr>
                </thead>
                <tbody id="scheduleTimeTableBody">

                </tbody>
            </table>
            <br/><br/>
            <input type="submit" name="submit" value="Submit" onclick="buildScheduleJson();"/>
            <input type="button" value="Cancel" onclick="window.location.href = 'index.htm?msg=Cancelled';" />
            <br/><br/>

        </form:form>
    </body>
</html>
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f4f4f9;
        margin: 0;
        padding: 20px;
    }

    form {
        max-width: 600px;
        margin: 0 auto;
        padding: 20px;
        background: #fff;
        border-radius: 8px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }

    form select,
    form input[type="text"],
    form input[type="button"],
    form input[type="submit"],
    form input[type="button"] {
        display: block;
        width: 100%;
        padding: 10px;
        margin: 10px 0;
        border: 1px solid #ccc;
        border-radius: 4px;
        box-sizing: border-box;
    }

    form select {
        background: #f9f9f9;
    }

    form input[type="button"],
    form input[type="submit"] {
        background-color: #5cb85c;
        color: white;
        border: none;
        cursor: pointer;
        transition: background-color 0.3s ease;
    }

    form input[type="button"]:hover,
    form input[type="submit"]:hover {
        background-color: #4cae4c;
    }

    form input[type="button"].cancel {
        background-color: #d9534f;
    }

    form input[type="button"].cancel:hover {
        background-color: #c9302c;
    }

    form span#timeError {
        color: red;
        font-size: 0.9em;
    }

    table {
        width: 100%;
        border-collapse: collapse;
        margin: 20px 0;
    }

    table, th, td {
        border: 1px solid #ddd;
    }

    table th,
    table td {
        padding: 10px;
        text-align: left;
    }

    table th {
        background-color: #f2f2f2;
    }

    table a {
        color: #007bff;
        text-decoration: none;
    }

    table a:hover {
        text-decoration: underline;
    }

    .form-group {
        margin-bottom: 1em;
    }

    .form-group label {
        margin-bottom: 0.5em;
        color: #333333;
        display: block;
    }
</style>