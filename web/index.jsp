<%-- Created by IntelliJ IDEA. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta charset="UTF-8">
  <title>Manage Students</title>
</head>
<body>
<h1 align = "center">Manage Students1</h1>
<form action = "ListStudents" method = "POST">

  <input type = "submit" value = "List Students" name="list_students" />

</form>
<br/>
<br/>
<form action = "AddStudent" method="POST">
  First Name: <input type = "text" name = "first_name">
  <br/>
  Last Name: <input type = "text" name = "last_name" />
  <br/>
  email: <input type = "text" name = "email" />
  <br/>
  <input type = "submit" value = "Add main.Student" name="add_student"/>
</form>
</body>
</html>