<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add New Patient</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #74ebd5, #acb6e5);
            margin: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .container {
            background: #fff;
            padding: 40px 50px;
            border-radius: 10px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 600px;
            box-sizing: border-box;
        }

        h1 {
            font-size: 24px;
            margin-bottom: 20px;
            color: #333;
            text-align: center;
        }

        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #555;
            text-align: left;
        }

        .form-group {
            margin-bottom: 20px;
        }

        input[type="text"],
        input[type="password"],
        input[type="email"],
        input[type="number"],
        select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
            font-size: 14px;
            margin-bottom: 10px;
        }

        input[type="radio"] {
            margin-right: 10px;
        }

        .form-buttons {
            display: flex;
            justify-content: space-between;
        }

        input[type="submit"],
        input[type="button"] {
            width: 48%;
            padding: 12px;
            border: none;
            border-radius: 5px;
            background: linear-gradient(135deg, #74ebd5, #acb6e5);
            color: #fff;
            font-size: 16px;
            cursor: pointer;
            transition: background 0.3s ease;
        }

        input[type="button"] {
            background: red;
        }

        input[type="submit"]:hover {
            background: linear-gradient(135deg, #acb6e5, #74ebd5);
        }

        input[type="button"]:hover {
           background: darkred;
        }

        .message {
            color: #007bff;
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Add New Patient</h1>
        <form:form name="addPatient" modelAttribute="patient" method="POST">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <div class="form-group">
                <label>Name:</label>
                <form:input path="name" />
            </div>
            <div class="form-group">
                <label>Mobile:</label>
                <form:input path="mobile" />
            </div>
            <div class="form-group">
                <label>Email Id:</label>
                <form:input path="emailId" />
            </div>
            <div class="form-group">
                <label>Age:</label>
                <form:input path="age" />
            </div>
            <div class="form-group">
                <label>Gender:</label>
                <form:radiobuttons path="gender" items="${genderList}" itemLabel="label" itemValue="id" />
            </div>
            <div class="form-buttons">
                <input type="submit" value="Submit" />
                <input type="button" value="Cancel" onclick="window.location.href = 'index.htm?msg=Cancelled';" />
            </div>
        </form:form>
        <div class="message">${msg}${param.msg}</div>
    </div>
</body>
</html>
