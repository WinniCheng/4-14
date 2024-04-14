<!DOCTYPE html>
<html>
<head><title>Online Book Store</title></head>

<body>


<h2>Create a Comment</h2>
<form:form method="POST" enctype="multipart/form-data" modelAttribute="ticketForm">
    <form:label path="subject">Subject</form:label><br/>
    <form:input type="text" path="subject"/><br/><br/>
    <form:label path="body">Body</form:label><br/>
    <form:textarea path="body" rows="5" cols="30"/><br/><br/>
    <input type="submit" value="Submit"/>
</form:form>
</body>
</html>
