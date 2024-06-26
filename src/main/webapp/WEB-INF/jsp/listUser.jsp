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
        }

        table, th, td {
            border: 1px solid #ddd;
        }

        th, td {
            padding: 12px;
            text-align: left;
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
        }

        .editLink:hover {
            background-color: green;
            color: white;
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
        function editUser(userId) {
            document.getElementById("userId").value = userId;
            document.getElementById("editForm").submit();
        }
    </script>
</head>
<body>
       
    <a href="index.htm">Home</a>&nbsp;&nbsp;<a href="logout" id="log">Logout</a><br/><br/>

    <fieldset>
        <legend>User List</legend>
          <div id="changes">${msg}${param.msg}</div>
        <table>
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Username</th>
                    <th>Role</th>
                    <th>Speciality List</th>
                    <th>Active</th>
                    <th>Edit</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${userList}" var="user">
                    <tr>
                        <td>${user.name}</td>
                        <td>${user.username}</td>
                        <td>${user.role.label}</td>
                        <td>${user.specialityListLabels}</td>
                        <td>${user.active}</td>
                        <td><a class="editLink" href="javascript:editUser(${user.userId});">Edit</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
   
        <form name="editForm" id="editForm" method="POST" action="showEditUser.htm">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" name="userId" id="userId"/>
        </form>
    </fieldset>
</body>
</html>
