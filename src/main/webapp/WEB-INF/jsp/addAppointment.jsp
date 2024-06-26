<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
    <head>
        <script type="text/javascript">
            var rowNo = 0;
            function getDrAvailableSlots() {
                var startTime = document.getElementById("startTime");
                startTime.length = 1;
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
        <form:form name="addAppointment" modelAttribute="appointment" method="POST" action="addAppointment.htm">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            Patient: <form:select path="patient" onchange=""><form:option value="0">Please select Patient</form:option>
            <form:options items="${patientList}" itemValue="patientId" itemLabel="name"/></form:select><br/><br/>
            Doctor : <form:select path="doctor" onchange="getDrAvailableSlots();"><form:option value="0">Please select Dr</form:option>
            <form:options items="${doctorList}" itemValue="id" itemLabel="label"/></form:select><br/><br/>
            Schedule date : <form:input path="scheduleDate" onchange="getDrAvailableSlots();"/><br/><br/>
            Start time : <form:select path="startTime" ><form:option value="">Please select a Slot</form:option>
            <form:options items="${availableSlots}"/></form:select><br/><br/>
            <input type="submit" name="submit" value="Submit"/>
            <input type="button" value="Cancel" onclick="window.location.href = 'index.htm?msg=Cancelled';" />
        </form:form>
        <br/><br/>
        ${msg}${param.msg}
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
        form input[type="submit"] {
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
    <!-- Existing JavaScript code -->
    <script type="text/javascript">
        var rowNo = 0;

        function getDrAvailableSlots() {
            var startTime = document.getElementById("startTime");
            startTime.length = 1;
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