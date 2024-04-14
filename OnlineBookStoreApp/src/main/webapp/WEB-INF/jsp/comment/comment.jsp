<!DOCTYPE html>
<html>
<head><title>Online Book Store</title></head>

<body>

<h2>Comments</h2>
<security:authorize access="hasRole('ADMIN') or hasRole('USER')">
    <a href="<c:url value="/ticket/create" />">Create a Comment</a><br/><br/>
</security:authorize>

<c:choose>
    <c:when test="${fn:length(ticketDatabase) == 0}">
        <i>There are no comment in the system.</i>
    </c:when>
    <c:otherwise>
        <c:forEach items="${ticketDatabase}" var="entry">
            Comment ${entry.id}:
            <a href="<c:url value="/ticket/view/${entry.id}" />">
                <c:out value="${entry.subject}"/></a>
            (customer: <c:out value="${entry.customerName}"/>)

            <security:authorize access="hasRole('ADMIN')">
                [<a href="<c:url value="/ticket/delete/${entry.id}" />">Delete</a>]
            </security:authorize>
            <br />
        </c:forEach>
    </c:otherwise>
</c:choose>
<br />

<a href="<c:url value="/book" />">Return to list books</a>

</body>
</html>
