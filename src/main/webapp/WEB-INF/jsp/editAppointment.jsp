
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
    <head>
        <script type="text/javascript">
            var rowNo = 0;

            function getDrAvailableSlots() {
                var startTime = document.getElementById("startTime");
                startTime.length = 0;
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
                fetch('ajaxGetDrAvailableSlots.htm', options)
                        .then(response => {
                            // Check if the request was successful
                            if (!response.ok) {
                                throw new Error('Ajax call failed');
                            }
                            // Parse the response as JSON
                            console.log(response);
                            return response.json();
                        })

                        .then(data => {
                            // Handle the JSON data
                            const availableSlots = data;
                            console.log(availableSlots.length)
                            for (var x = 0; x < availableSlots.length; x++) {
                                console.log(availableSlots[x]);
                                var opt = document.createElement('option');
                                opt.value = availableSlots[x];
                                opt.innerHTML = availableSlots[x];
                                startTime.appendChild(opt);
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
        <form:form name="editAppointment" modelAttribute="appointment" method="POST" action="editAppointment.htm">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <form:input type="hidden" path="appointmentId"/>

            Patient: <form:select path="patient" onchange="">
            <form:option value="${appointment.patient.id}">${appointment.patient.label}</form:option>
            </form:select><br/><br/>
            Doctor : <form:select path="doctor" onchange="getDrAvailableSlots();">
            <form:options items="${doctorList}" itemValue="id" itemLabel="label"/></form:select><br/><br/>
            Schedule date : <form:input path="scheduleDate" onchange="getDrAvailableSlots();"/><br/><br/>
            Start time : <form:select path="startTime" items="${availableSlots}"/><br/><br/>
            <input type="submit" name="submit" value="Submit"/>
            <input type="button" value="Cancel" onclick="window.location.href = 'viewDoctorCalendar.htm?msg=Cancelled';" />
        </form:form>
        <br/><br/>
        ${msg}${param.msg}
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
            max-width: 600px;
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

        form input[type="button"].cancel {
            background-color: #d9534f;
        }

        form input[type="button"]:hover,
        form input[type="submit"]:hover {
            background-color: #4cae4c;
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