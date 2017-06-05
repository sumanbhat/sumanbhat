<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Upload Result</title>
</head>
<body>
<center>
        <h2>${message}</h2>
       
        <form method="post" action="ConvertServlet" >
        <input type="submit" value="Convert" />
        <input type="hidden" value='${filename}' name="filename" />
        </form>
    </center>
</body>
</html>