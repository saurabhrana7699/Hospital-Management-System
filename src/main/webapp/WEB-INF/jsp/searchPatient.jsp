<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <script type="text/javascript">
            function editPatient(patientId) {
                document.getElementById("patientId").value = patientId;
                document.getElementById("editForm").submit();
            }
        </script>
    <title>Search Patients</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
        }

        fieldset {
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
            padding: 20px;
        }

        legend {
            font-size: 20px;
            font-weight: bold;
            color: #333;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #f2f2f2;
            font-weight: bold;
        }

        tbody tr:hover {
            background-color: #f1f1f1;
        }

        input[type="text"] {
            width: 250px;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
            font-size: 14px;
        }

        input[type="submit"] {
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            background-color: #007bff;
            color: #fff;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        input[type="submit"]:hover {
            background-color: #0056b3;
        }

        a {
            text-decoration: none;
            color: #007bff;
        }

        a:hover {
            color: white;
            background-color: blue;
            text-decoration: none;
        }
         .editLink {
            color: green;
            border: 1px solid green;
        }

        .editLink:hover {
            background-color: green;
            color: white;
        }
    
         a {
            color: #007bff;
            text-decoration: none;
            padding: 5px 10px;
            border: 1px solid #007bff;
            border-radius: 4px;
            transition: background-color 0.3s ease, color 0.3s ease;
        }
         #log:hover{
             background-color: red;
             color: white;
        }
         #log{
            color: red;
             border: 1px solid red;
        }
        
    </style>
</head>
<body>
    <br/>
    <a href="index.htm">Home</a>&nbsp;&nbsp;<a id="log" href="logout">Logout</a><br/><br/>
    <fieldset>
        <legend>Search Patient</legend>
        <form id="searchPatient" name="searchPatient" method="GET">
            Search Patient <input type="text" name="searchString" id="searchString" value="${searchString}"/> <input type="submit" value="Search"/>
        </form>
        <br/>
        <table border="1">
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Mobile</th>
                    <th>Email Id</th>
                    <th>Age</th>
                    <th>Gender</th>
                    <th>Edit</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${patientList}" var="p">
                    <tr>
                        <td>${p.name}</td>
                        <td>${p.mobile}</td>
                        <td>${p.emailId}</td>
                        <td>${p.age}</td>
                        <td>${p.gender.label}</td>
                        <td><a class="editLink" href="javascript:editPatient(${p.patientId});">Edit</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <br/>
        ${msg}${param.msg}
        <form name="editForm" id="editForm" method="POST" action="showEditPatient.htm">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" name="patientId" id="patientId"/>
        </form>
    </fieldset>
</body>
</html>
