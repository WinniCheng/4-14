<!DOCTYPE html>
<html>
<head>
    <title>Online Book Store</title>
</head>
<body>
<security:authorize access="hasRole('ADMIN') or hasRole('USER')">
    <c:url var="logoutUrl" value="/logout"/>
    <form action="${logoutUrl}" method="post">
        <input type="submit" value="Logout" />
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
</security:authorize>

<h2>Edit Book #${book.id}</h2>
<form:form method="POST" enctype="multipart/form-data"
           modelAttribute="bookForm">

    <b>Cover photo:</b><br />
    <input type="file" name="attachments" accept="image/*"/><br/><br/>

    <c:if test="${!empty book.attachments}">
        <c:forEach items="${book.attachments}" var="attachment" varStatus="status">
            <c:if test="${!status.first}">, </c:if>
            <img src="<c:url value="/book/${bookId}/attachment/${attachment.id}" />"/>
            [<a href="<c:url value="/book/${bookId}/delete/${attachment.id}" />">Delete</a>]
        </c:forEach>
        <br/><br/>
    </c:if>


    <form:label path="bookName">Book Name:</form:label><br/>
    <form:input type="text" path="bookName"/><br/><br/>

    <form:label path="author">Author:</form:label><br/>
    <form:input type="text" path="author"/><br/><br/>

    <form:label path="price">Price ($):</form:label><br/>
    <form:input type="number" path="price" min="0" step="0.1"/><br/><br/>

    <form:label path="description">Description:</form:label><br/>
    <form:textarea path="description" rows="5" cols="30"/><br/><br/>

    <form:label path="availability">Availability:</form:label><br/>
    <form:radiobutton path="availability" value="true"/>Available
    <form:radiobutton path="availability" value="false"/>Unavailable<br/><br/>

    <input type="submit" value="Save"/><br/><br/>
</form:form>
<a href="<c:url value="/book" />">Return to list books</a>
</body>
</html>
