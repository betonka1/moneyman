<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form:form action="/save" enctype="multipart/form-data" method="post">
    <input type="file" name="file">
    <input type="submit" value="Добавить">
</form:form>
</body>
</html>
