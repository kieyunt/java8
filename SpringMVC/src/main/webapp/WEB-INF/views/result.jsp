<%@page contentType = "text/html;charset = UTF-8" language = "java" %>
<%@page isELIgnored = "false" %>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Spring MVC Form Handling</title>
</head>
<body>
      <h2>Submitted Student Information</h2>
      <table border="1">
         <tr>
            <td>Name</td>
            <td>${name}</td>
            <td>Req : <%=request.getParameter("name")%></td>
         </tr>
         <tr>
            <td>Age</td>
            <td>${age}</td>
            <td>Req : <%=request.getParameter("age")%></td>
         </tr>
         <tr>
            <td>ID</td>
            <td>${id}</td>
            <td>Req : <%=request.getParameter("id")%></td>
         </tr>
      </table>  
</body>
</html>