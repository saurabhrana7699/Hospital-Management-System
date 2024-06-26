<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
    <head>
        <script type="text/javascript">
            function addRow(cal, doctorId, scheduleDate) {
//                console.log("Adding row with " + cal + ", doctorId=" + doctorId + ", scheduleDate=" + scheduleDate);
                var t = document.getElementById("doctorCalendarBody");
                var r = t.insertRow(-1);
                r.setAttribute("data-id", cal.appointmentId);
                var cell1 = r.insertCell(0);
                var cell2 = r.insertCell(1);
                var cell3 = r.insertCell(2);
                cell1.innerHTML = cal.timeSlot;
                if (cal.appointmentId != null) {
                    cell2.innerHTML = cal.patient.label;
                    cell3.innerHTML = "<a href='javascript:editAppt(" + cal.appointmentId + ");'>Edit</a>";
                } else {
                    cell2.innerHTML = "";
                    cell2.innerHTML = "<a href=\"javascript:addAppt(" + doctorId + ", '" + scheduleDate + "', '" + cal.timeSlot + "');\">Add</a>";
                }
                return true;
            }

            function deleteAllRows() {
                var table = document.getElementById("doctorCalendarBody");
                const tableRowCount = table.rows.length;
                for (var x = 0; x < tableRowCount; x++) {
                    table.deleteRow(0);
                }
            }

            function getDoctorCalendar() {
                const doctorId = document.getElementById("doctor").value;
                const scheduleDate = document.getElementById("scheduleDate").value;
                if (scheduleDate != '' && doctorId != 0) {
//                    console.log("Proceed with Ajax");
                    deleteAllRows();
                    const formData = new FormData();

                    formData.append('doctorId', doctorId);
                    formData.append('scheduleDate', scheduleDate);
                    formData.append('${_csrf.parameterName}', '${_csrf.token}');
                    // Set up options for the fetch request
                    const options = {
                        method: 'POST',
                        body: formData
                    };
                    // Make the fetch request with the provided options
                    fetch('ajaxGetDoctorCalendar.htm', options)
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
                                const calendarList = data;

                                for (var x = 0; x < calendarList.length; x++) {
                                    addRow(calendarList[x], doctorId, scheduleDate, );
                                }
                            })
                            .catch(error => {
                                // Handle any errors that occurred during the fetch
                                console.error('Fetch error:', error);
                            });
                }
            }

            function editAppt(apptId) {
                document.getElementById("appointmentId").value = apptId;
                document.getElementById("editAppointmentForm").submit();
            }

            function addAppt(doctorId, scheduleDate, timeSlot) {
                document.getElementById("varDoctorId").value = doctorId;
                document.getElementById("varScheduleDate").value = scheduleDate;
                document.getElementById("varTimeSlot").value = timeSlot;
                document.getElementById("addAppointmentForm").submit();
            }


        </script>
    </head>
    <body>
        <form name="viewDoctorSchedule">
            Doctor : <select id="doctor" name="doctor" onchange="getDoctorCalendar();"><option value="0">Please select Dr</option><c:forEach items="${doctorList}" var="d"><option value="${d.id}">${d.label}</option></c:forEach></select><br/><br/>
                Schedule date : <input id="scheduleDate" name="scheduleDate" onchange="getDoctorCalendar();"/><br/><br/><br/>
                Doctor Calendar
                <table border="1">
                    <thead>
                        <tr>
                            <th>Start time</th>
                            <th>Patient</th>
                            <th>Edit</th>
                             <input type="button" value="HOME" onclick="window.location.href = 'index.htm?msg=Cancelled';" />
                        </tr>
                    </thead>
                    <tbody id="doctorCalendarBody">

                    </tbody>
                </table>
                <br/><br/>
            </form>
            <form name="editAppointmentForm" id="editAppointmentForm" method="POST" action="showEditAppointment.htm">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" name="appointmentId" id="appointmentId"/>
        </form>
        <form name="addAppointmentForm" id="addAppointmentForm" method="POST" action="addAppointmentFromCalendar.htm">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" name="doctorId" id="varDoctorId"/>
            <input type="hidden" name="scheduleDate" id="varScheduleDate"/>
            <input type="hidden" name="timeSlot" id="varTimeSlot"/>
        </form>

    </body>
</html>

  <style>
        body {
            font-family: 'Helvetica Neue', Arial, sans-serif;
            background-color: #eef2f7;
            margin: 0;
            padding: 20px;
            color: #333;
        }

        form {
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
            background: #ffffff;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
        }

        .form-group {
            margin-bottom: 15px;
        }

        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }

        form select,
        form input[type="text"],
        form input[type="button"],
        form input[type="submit"] {
            width: calc(100% - 22px);
            padding: 10px;
            margin: 5px 0;
            border: 1px solid #ccd0d5;
            border-radius: 4px;
            box-sizing: border-box;
            font-size: 14px;
        }

        form select {
            background: #f9f9f9;
        }

        form input[type="button"],
        form input[type="submit"] {
            width: calc(50% - 11px);
            background-color: #5cb85c;
            color: #ffffff;
            border: none;
            cursor: pointer;
            transition: background-color 0.3s ease;
            display: inline-block;
            margin-right: 10px;
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

        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
            background: #fff;
        }

        table, th, td {
            border: 1px solid #ddd;
        }

        table th,
        table td {
            padding: 12px 15px;
            text-align: left;
            font-size: 14px;
        }

        table th {
            background-color: #f7f7f7;
            font-weight: bold;
            text-transform: uppercase;
        }

        table tbody tr:nth-child(odd) {
            background-color: #f9f9f9;
        }

        table tbody tr:hover {
            background-color: #f1f1f1;
        }

        table a {
            color: #007bff;
            text-decoration: none;
        }

        table a:hover {
            text-decoration: underline;
        }

        .form-actions {
            display: flex;
            justify-content: space-between;
            margin-top: 20px;
        }

        .header {
            font-size: 1.8em;
            font-weight: bold;
            margin-bottom: 20px;
            text-align: center;
            color: #333;
        }

        .notification {
            background-color: #e7f3fe;
            color: #31708f;
            padding: 15px;
            margin-bottom: 20px;
            border: 1px solid #bce8f1;
            border-radius: 4px;
        }

        .container {
            padding: 20px;
        }
    </style>
