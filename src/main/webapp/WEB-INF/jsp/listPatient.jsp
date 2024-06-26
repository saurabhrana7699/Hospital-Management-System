<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User List</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            line-height: 1.6;
            background-color: #f4f4f4;
            padding: 20px;
        }

        a {
            color: #007bff;
            text-decoration: none;
            padding: 5px 10px;
            border: 1px solid #007bff;
            border-radius: 4px;
            transition: background-color 0.3s ease, color 0.3s ease;
        }
        
        #log{
            color: red;
             border: 1px solid red;
        }

        a:hover {
            color: white;
            background-color: blue;
            text-decoration: none;
        }
        

        fieldset {
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 20px;
            background-color: #fff;
        }

        legend {
            font-size: 24px;
            color: #333;
            padding: 0 10px;
            width: auto;
            border-bottom: none;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
            background-color: #fff;
            border: 1px solid #ddd; /* Added border */
        }

        th, td {
            padding: 12px;
            text-align: left;
            border: 1px solid #ddd; /* Added border */
        }

        th {
            background-color: #f2f2f2;
        }

        tbody tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        tbody tr:hover {
            background-color: #f1f1f1;
        }

        .editLink {
            color: green;
            border: 1px solid green;
            padding: 5px 10px;
            border-radius: 4px;
            transition: background-color 0.3s ease, color 0.3s ease;
        }

        .editLink:hover {
            background-color: green;
            color: white;
            text-decoration: none;
        }

        .msg {
            color: #ff0000;
            margin-top: 20px;
        }
        
        #log:hover{
             background-color: red;
             color: white;
        }
        #changes{
            color: green;
        }
       
        
    </style>
    <script type="text/javascript">
        function editPatient(patientId) {
            document.getElementById("patientId").value = patientId;
            document.getElementById("editForm").submit();
        }
    </script>
</head>
<body>
       
    <a href="index.htm">Home</a>&nbsp;&nbsp;<a href="logout" id="log">Logout</a><br/><br/>

    <fieldset>
        <legend>Patient List</legend>
          <div id="changes">${msg}${param.msg}</div>
        <table>
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
   
        <form name="editForm" id="editForm" method="POST" action="showEditPatient.htm">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" name="patientId" id="patientId"/>
        </form>
    </fieldset>
</body>
</html>
