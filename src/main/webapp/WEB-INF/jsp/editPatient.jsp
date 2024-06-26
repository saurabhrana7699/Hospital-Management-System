<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
    <head>
        <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
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
        select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
            font-size: 14px;
            margin-bottom: 10px;
        }

        #specialities {
            margin-bottom: 20px;
            text-align: left;
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

        .msg {
            color: #007bff;
            margin-top: 20px;
            text-align: center;
        }
    </style>
    </head>
    <body>
            <div class="container">
        <h1>Edit Patient</h1>
        <form:form name="editPatient" modelAttribute="patient" method="POST" action="editPatient.htm">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <form:hidden path="patientId"/>
            <label>Name: </label><form:input path="name" /><br/><br/>
            <label>Mobile: </label><form:input path="mobile" /><br/><br/>
            <label>Email Id: </label><form:input path="emailId" /><br/><br/>
            <label>Age: </label><form:input path="age" /><br/><br/>
            <label>Gender: </label><form:radiobuttons path="gender" items="${genderList}" itemLabel="label" itemValue="id"/><br/><br/>
            <input type="submit" value="Submit"/>&nbsp;&nbsp;&nbsp;<input type="button" value="Cancel" onclick="window.location.href = 'listPatient.htm?msg=Cancelled';"/>
        </form:form>
        <br/><br/>
        ${msg}${param.msg}
        <div/>
    </body>
</html>
